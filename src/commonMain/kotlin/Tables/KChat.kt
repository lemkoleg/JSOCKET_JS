/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import atomic.AtomicLong
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author User
 */

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var CHATS: KCashData?  = null

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val MESSEGES: MutableMap<String, KCashData>  = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val CHATS_LIKES: MutableMap<String, KCashData>  = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val CHATS_COST_TYPES: MutableMap<String, KCashData>  = mutableMapOf()


@InternalAPI
private val KChatsGlobalLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KChat")
class KChat(val ans: ANSWER_TYPE){

    init {
        ensureNeverFrozen()
    }

    private val KChatsLock = Mutex()

    //val InstanceRef: AtomicReference<KChat> = AtomicReference(this)



    private fun LastMessegeCount(): Long {
        return MESSEGES.keys.last() ?: 0L
    }

    @ExperimentalStdlibApi
    @JsName("InsertNewMessege")
    fun InsertNewMessege(m: KMessege) {
        if (m.getCHATS_ID() == getCHATS_ID()) {
            if (MESSEGES.containsKey(m.getMESSEGES_COUNT())) {
                CoroutineScope(Dispatchers.Default).launch { MESSEGES[m.getMESSEGES_COUNT()]!!.merge(m) }
            } else {
                CoroutineScope(Dispatchers.Default).launch { Sqlite_service.InsertMessege(m) }
            }
            MESSEGES[m.getMESSEGES_COUNT()] = m
        }
    }

    /*private fun SynchronizeMessege() {
        if (verifyMesseges != 0) {
            try {
                var lowerKey: Int = (MESSEGES.size - maxCountOfMessegesIntoDB.value)
                lowerKey = if (lowerKey < 0) 0 else lowerKey
                val mapSize = if (lowerKey == 0) 0 else MESSEGES.keys.lastIndexOf(lowerKey)
                if (mapSize != if (lowerKey > 0L) maxCountOfMessegesIntoDB else MESSEGES.size) {
                    var jsocket = Jsocket()
                    jsocket.just_do_it = 1011000090
                    jsocket.value_id1 = chatsID
                    jsocket.last_messege_update = getGlobalLastUpdatingDate()
                    jsocket.last_date_of_update = MESSEGES.lastKey()
                    jsocket.value_par2 = "1"
                    jsocket.value_par3 = "2"
                    jsocket.value_par4 = "1"
                    jsocket = send_JSOCKETs!!.send_JSOCKET_with_TimeOut(jsocket, null, true)
                    if (jsocket.ANSWER_TYPEs != null && jsocket.ANSWER_TYPEs.size() > 0) {
                        jsocket.ANSWER_TYPEs.forEach { k -> InsertNewMessege(Messege(k)) }
                    }
                }
            } catch (ex: Exception) {
                try {
                    PrintWriter(FileOutputStream(java.io.File(JSOCKET_Instance.getPathErrors(), JSOCKET_Instance.getFileErrorName().concat("_chat_SynchronizeMessege_errors.txt")))).use({ pw -> ex.printStackTrace(pw) })
                } catch (e2: Exception) {
                }
            } finally {
                verifyMesseges = 0
            }
        }
    }

    protected fun FlushMesseges() {
        if (flushMesseges != 0) {
            try {
                var p: TreeMap? = null
                val lowerKey: Long = java.lang.Long.valueOf(MESSEGES.size - maxCountOfMessegesIntoDB!!.toLong())
                p = if (lowerKey > 0L) {
                    MESSEGES.tailMap(lowerKey, true) as TreeMap
                } else {
                    MESSEGES
                }
                p.forEach(BiConsumer { k: Any?, v: Any? -> Sqlite_service.InsertMessege(v as Messege?) })
            } catch (ex: Exception) {
                try {
                    PrintWriter(FileOutputStream(java.io.File(JSOCKET_Instance.getPathErrors(), JSOCKET_Instance.getFileErrorName().concat("_chat_FlushMesseges_errors.txt")))).use({ pw -> ex.printStackTrace(pw) })
                } catch (e2: Exception) {
                }
            } finally {
                flushMesseges = 0
            }
        }
    }*/

    @JsName("DeleteMessege")
    fun DeleteMessege(lMessegeId: Long?) {
        MESSEGES.minus(lMessegeId)
    }

    companion object {

        @JsName("globalLastUpdatingDate")
        val globalLastUpdatingDate: AtomicLong = AtomicLong(0L)


        private suspend fun setGlobalLastUpdatingDate(lUpdatingDate: Long) {
            globalLastUpdatingDate.setGreaterValue(lUpdatingDate)
        }

        @JsName("maxCountOfMessegesIntoDB")
        val maxCountOfMessegesIntoDB = AtomicLong(100L)


        @JsName("ADD_NEW_CHATS")
        fun ADD_NEW_CHATS(arr: ArrayDeque<ANSWER_TYPE>): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
            withTimeout(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KChatsGlobalLock.lock()
                        while (arr.isNotEmpty()) {
                            TODO()
                        }
                        return@withTimeout true
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KChats",
                            l_function_name = "ADD_NEW_CHATS",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KChatsGlobalLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeout false
            }
        }.toPromise(EmptyCoroutineContext)
    }

    @JsName("INIT_CHATS")
    fun INIT_CHATS(): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeout(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KChatsGlobalLock.lock()
                        while (arr.isNotEmpty()) {
                            TODO()
                        }
                        return@withTimeout true
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KChats",
                            l_function_name = "ADD_NEW_CHATS",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KChatsGlobalLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeout false
            }
        }.toPromise(EmptyCoroutineContext)
}
}
