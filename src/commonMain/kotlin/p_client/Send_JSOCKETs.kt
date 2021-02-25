/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_client

import Tables.KBigAvatar
import io.ktor.util.InternalAPI
import io.ktor.util.KtorExperimentalAPI
import io.ktor.utils.io.core.ExperimentalIoApi
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
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
    @KtorExperimentalAPI
    @ExperimentalIoApi
    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    @InternalAPI
    @ExperimentalTime
    private val listener: Listener? = Listener.get_Instance()
    private val WAITTIMEOUT= 10000L
    @KtorExperimentalAPI
    @ExperimentalTime
    @ExperimentalIoApi
    @InternalAPI
    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    suspend fun send_JSOCKET_with_TimeOut(lMyJSocket: Jsocket, lConnection: Connection?, send_request: Boolean): Jsocket {
        val myJSocket: Jsocket = lMyJSocket
        if (lConnection != null) {
            Listener.Connections[myJSocket.just_do_it_label] = lConnection
        }
        if (send_request) {
            Listener.InJSOCKETs.enqueue(myJSocket,ListenerQUEUESize, "Listener.InJSOCKETs")
        } else {
            Listener.BetweenJSOCKETs[myJSocket.just_do_it_label] = myJSocket
        }
        if (!Commands[myJSocket.just_do_it]?.isDont_answer!!) {
            try {
                if (myJSocket.condition.cAwait(WAITTIMEOUT)){
                    myJSocket.setValue(Listener.OutJSOCKETs.remove(myJSocket.just_do_it_label)!!)
                    if (myJSocket.content != null && myJSocket.just_do_it_successfull == "0"
                            && myJSocket.content!!.isNotEmpty()) {
                        if (Commands[myJSocket.just_do_it]!!.isDeserialized_answer) {
                            myJSocket.deserialized_ANSWERS_TYPES()
                        }
                        if (Commands[myJSocket.just_do_it]!!.blobIsAvavtar && myJSocket.value_par4.trim().isNotEmpty()) {
                            myJSocket.JSOCKETScope.launch{
                                Sqlite_service.InsertBigAvatar(
                                    KBigAvatar(
                                        myJSocket.value_id1.trim(),
                                        myJSocket.last_date_of_update,
                                        1,
                                        myJSocket.value_par4.trim().toInt(),
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
                    Listener.Connections.remove(myJSocket.just_do_it_label)

                }
            }
        }
        return myJSocket
    } ////////////////////////////////////////////////////////////////////////////////
}