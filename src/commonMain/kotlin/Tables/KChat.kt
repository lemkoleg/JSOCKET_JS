/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import CrossPlatforms.PrintInformation
import atomic.AtomicLong
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_client.Jsocket
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
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
var CHATS: KCashData? = null

/*
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
 */


@InternalAPI
private val KChatsGlobalLock = Mutex()
private val KChatsSelectAllDataOnChatLock = Mutex()
private val KChatsVerifyUpdatesLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("globalLastUpdatingDate")
val globalLastChatsSelect: AtomicLong = AtomicLong(0L)

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("sendedVerifyUpdates")
private var sendedVerifyUpdates: Long = 0L
private var TimeOutendedVerifyUpdates: Long = 0L

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("sendedlSelectAllDataOfChat")
private val sendedlSelectAllDataOfChat: MutableMap<String, Long> = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KChat")
object KChat {

    @JsName("SELECT_ALL_DATA_ON_CHAT")
    fun SELECT_ALL_DATA_ON_CHAT(cats_id: String): Promise<Unit?> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KChatsSelectAllDataOnChatLock.withLock {
                            if (sendedlSelectAllDataOfChat.containsKey(cats_id)) {
                                if (sendedlSelectAllDataOfChat[cats_id]!! > DateTime.nowUnixMillisLong()) {
                                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                        PrintInformation.PRINT_INFO("KChat.SELECT_ALL_DATA_ON_CHAT: time out of sended SELECT_ALL_DATA_ON_CHAT is not ended; Chat ID: $cats_id; Chat name: ${CHATS!!.CASH_DATA_RECORDS[cats_id]?.answerTypeValues?.GetObjectName?.let { it() }}")
                                    }
                                    return@withTimeoutOrNull

                                } else {
                                    sendedlSelectAllDataOfChat[cats_id] =
                                        DateTime.nowUnixMillisLong() + Constants.CLIENT_TIMEOUT
                                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                        PrintInformation.PRINT_INFO("KChat.SELECT_ALL_DATA_ON_CHAT: time out of sended SELECT_ALL_DATA_ON_CHAT is ended. Update new timeout; Chat ID: $cats_id; Chat name: ${CHATS!!.CASH_DATA_RECORDS[cats_id]?.answerTypeValues?.GetObjectName?.let { it() }}")
                                    }
                                }
                            } else {
                                sendedlSelectAllDataOfChat[cats_id] = DateTime.nowUnixMillisLong() + Constants.CLIENT_TIMEOUT

                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("KChat.SELECT_ALL_DATA_ON_CHAT: Set new request; Chat ID: $cats_id; Chat name: ${CHATS!!.CASH_DATA_RECORDS[cats_id]?.answerTypeValues?.GetObjectName?.let { it() }}")
                                }
                            }
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                PrintInformation.PRINT_INFO("KChat.SELECT_ALL_DATA_ON_CHAT: Start new request; Chat ID: $cats_id; Chat name: ${CHATS!!.CASH_DATA_RECORDS[cats_id]?.answerTypeValues?.GetObjectName?.let { it() }}")
                            }
                            val socket: Jsocket = Jsocket.GetJsocket() ?: Jsocket()
                            socket.value_id5 = cats_id
                            socket.just_do_it = 1011000052 //SELECTOR.SELECT_ALL_DATA_ON_CHAT;
                            socket.check_sum = CHATS!!.CashLastUpdate.CASH_SUM
                            socket.send_request()
                        }
                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KChats",
                            l_function_name = "SELECT_ALL_DATA_ON_CHAT",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            } ?: throw my_user_exceptions_class(
                l_class_name = "KChats",
                l_function_name = "SELECT_ALL_DATA_ON_CHAT",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }.toPromise(EmptyCoroutineContext)

    @JsName("VERIFY_UPDATES")
    fun VERIFY_UPDATES(new_updates: Long): Promise<Unit?> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KChatsVerifyUpdatesLock.withLock {
                            when {
                                (sendedVerifyUpdates > new_updates) -> {
                                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                        PrintInformation.PRINT_INFO("KChat.VERIFY_UPDATES: More later updetes is sended;")
                                    }
                                    return@withTimeoutOrNull
                                }

                                (sendedVerifyUpdates == new_updates) -> {
                                    if (TimeOutendedVerifyUpdates > DateTime.nowUnixMillisLong()) {
                                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                            PrintInformation.PRINT_INFO("KChat.VERIFY_UPDATES: Time out of sended updates is not ended;")
                                        }
                                        return@withTimeoutOrNull
                                    } else {
                                        TimeOutendedVerifyUpdates = DateTime.nowUnixMillisLong() + Constants.CLIENT_TIMEOUT
                                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                            PrintInformation.PRINT_INFO("KChat.VERIFY_UPDATES: Time out of sended updates is ended; Set new timeout;")
                                        }
                                    }
                                }

                                (sendedVerifyUpdates < new_updates) -> {
                                    sendedVerifyUpdates = new_updates
                                    TimeOutendedVerifyUpdates = DateTime.nowUnixMillisLong() + Constants.CLIENT_TIMEOUT
                                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                        PrintInformation.PRINT_INFO("KChat.VERIFY_UPDATES: Set new update;")
                                    }

                                }
                            }

                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                PrintInformation.PRINT_INFO("KChat.VERIFY_UPDATES: Sended new request for verify updates $sendedVerifyUpdates; globalLastChatsSelect.value = ${globalLastChatsSelect.value}")
                            }

                            val socket: Jsocket = Jsocket.GetJsocket() ?: Jsocket()
                            socket.just_do_it = 1011000053 // SELECTOR.SELECT_CHATS;
                            socket.check_sum = CHATS!!.CashLastUpdate.CASH_SUM
                            socket.send_request()
                            sendedVerifyUpdates = globalLastChatsSelect.value
                        }
                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KChats",
                            l_function_name = "VERIFY_UPDATES",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            } ?: throw my_user_exceptions_class(
                l_class_name = "KChats",
                l_function_name = "VERIFY_UPDATES",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }.toPromise(EmptyCoroutineContext)

    @JsName("GET_CHATS")
    fun GET_CHATS(l_updatedCashData: ((v: Any?) -> Any?)?): Promise<ArrayDeque<ANSWER_TYPE>> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            var arr: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KChatsGlobalLock.withLock {
                            if (CHATS == null) {
                                CHATS = KCashData.GET_CASH_DATA(
                                    L_OBJECT_ID = Constants.Account_Id,
                                    L_RECORD_TYPE = "3",
                                    l_updatedCashData = l_updatedCashData,
                                    l_request_updates = true,
                                    l_select_all_records = false,
                                    l_is_SetLastBlock = true,
                                    l_reset_cash_data = false,
                                    l_ignore_timeout = true
                                ).await()

                            } else{
                                CHATS = KCashData.GET_CASH_DATA(
                                    L_OBJECT_ID = Constants.Account_Id,
                                    L_RECORD_TYPE = "3",
                                    l_updatedCashData = l_updatedCashData,
                                    l_request_updates = true,
                                    l_select_all_records = false,
                                    l_is_SetLastBlock = true,
                                    l_reset_cash_data = true,
                                    l_ignore_timeout = true
                                ).await()
                            }
                            arr = CHATS!!.currentViewCashData
                            return@withTimeoutOrNull arr
                        }
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KChats",
                            l_function_name = "GET_CHATS",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            } ?: throw my_user_exceptions_class(
                l_class_name = "KChats",
                l_function_name = "GET_CHATS",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
            return@async arr
        }.toPromise(EmptyCoroutineContext)

    @JsName("DELETE_CHATS")
    suspend fun DELETE_CHATS(l_chat_id: String) {
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            try {
                try {
                    KChatsGlobalLock.withLock {
                        CHATS!!.DELETE(l_chat_id)
                    }

                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KChats",
                        l_function_name = "GET_CHATS",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        } ?: throw my_user_exceptions_class(
            l_class_name = "KChats",
            l_function_name = "GET_CHATS",
            name_of_exception = "EXC_SYSTEM_ERROR",
            l_additional_text = "Time out is up"
        )

        throw my_user_exceptions_class(
            l_class_name = "KChats",
            l_function_name = "GET_CHATS",
            name_of_exception = "EXC_SYSTEM_ERROR",
            l_additional_text = "Time out is up"
        )
    }

}
