/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_client

import Tables.KBigAvatar
import com.soywiz.klock.DateTime
import io.ktor.util.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.core.internal.*
import kotlinx.coroutines.launch
import lib_exceptions.exc_universal_exception.returnException
import p_jsocket.*
import sql.Sqlite_service
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

/**
 *
 * @author User
 */
@JsName("Send_JSOCKETs")
class Send_JSOCKETs {
    @ExperimentalIoApi
    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    @InternalAPI
    @ExperimentalTime
    private val listener: Listener? = Listener.get_Instance()
    @ExperimentalTime
    @ExperimentalIoApi
    @InternalAPI
    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    suspend fun send_JSOCKET_with_TimeOut(lMyJSocket: Jsocket,
                                          lConnection: Connection?,
                                          send_request: Boolean): Jsocket {

        var myJSocket: Jsocket = lMyJSocket

        if (lConnection != null) {
            Connections[myJSocket.just_do_it_label] = lConnection
        }

        if (send_request) {
            listener!!.setInJSOCKETs(myJSocket)
        } else {
            listener!!.setBetweenJSOCKETs(myJSocket)
        }

        if (!Commands[myJSocket.just_do_it]?.isDont_answer!!) {
            try {
                if (myJSocket.condition.cAwait(CLIENT_TIMEOUT)){

                    myJSocket = OutJSOCKETs.remove(myJSocket.just_do_it_label)!!

                    if (myJSocket.content != null && myJSocket.content!!.isNotEmpty()) {

                        if (Commands[myJSocket.just_do_it]!!.whichBlobDataReturned == "4") {
                            myJSocket.deserialized_ANSWERS_TYPES()
                            return myJSocket
                        }

                        if (Commands[myJSocket.just_do_it]!!.whichBlobDataReturned == "5") {
                            myJSocket.JSOCKETScope.launch{
                                Sqlite_service.InsertBigAvatar(
                                    KBigAvatar(
                                        myJSocket.value_id1.trim(),
                                        DateTime.nowUnixLong(),
                                        myJSocket.content!!
                                    )
                                )
                            }
                        }
                    }
                } else {
                    myJSocket.just_do_it_successfull = "4"
                    myJSocket.db_massage = returnException(90002, myJSocket.lang)
                }
            } catch (ex: Exception) {
                myJSocket.just_do_it_successfull = "4"
                myJSocket.db_massage = ex.toString()
            } finally {
                if (lConnection != null) {
                    Connections.remove(myJSocket.just_do_it_label)
                }
            }
        }
        return myJSocket
    } ////////////////////////////////////////////////////////////////////////////////
}