package Tables

import CrossPlatforms.PrintInformation
import Tables.KBigAvatar.Companion.RETURN_PROMISE_SELECT_BIG_AVATAR
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import p_jsocket.FileService
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

@InternalAPI
private val KObjectInfoLock = Mutex()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val OBJECTS_INFO: MutableMap<String, KObjectInfo> = mutableMapOf()


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var SAVE_OBJECT_INFO: KCashData? = null


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class KObjectInfo(l_answerType: ANSWER_TYPE) {

    private val LocalLock = Mutex()

    val answerType: ANSWER_TYPE = l_answerType

    var VerifyUpdatesJob: Job? = null

    val answerTypeValues = l_answerType.answerTypeValues

    val answerTypeConstants = answerTypeValues.answerTypeConstants

    val localFileSevice: FileService? =
        if (answerTypeConstants.IsMediaContent || answerTypeConstants.IsFile) FileService(answerType = answerType) else null

    var promiseDowloadFile: Promise<Boolean>? = null
    private var updateObjectInfo: ((v: Any?) -> Any?) = {}

    fun SetCallBackUpdate(lupdateObjectInfo: ((v: Any?) -> Any?)? = null) {
        updateObjectInfo = lupdateObjectInfo ?: {}
    }

    fun VerifyUpdates() {
        if (VerifyUpdatesJob == null || VerifyUpdatesJob!!.isActive) {
            VerifyUpdatesJob = CoroutineScope(Dispatchers.Default).launch {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            LocalLock.lock()
                            if (answerTypeConstants.IsDBObject) {
                                if (answerType.answerTypeValues.GetMainAvatarId().isNotEmpty()) {
                                    if (answerType.answerTypeValues.GetAvatarOriginalSize() > 0) {
                                        if (answerType.BLOB_4 == null) {
                                            if (!KBigAvatar.IS_HAVE_LOCAL_AVATAR_AND_RESERVE(answerType.answerTypeValues.GetMainAvatarId())) {
                                                SendRequestForUpdate("1")
                                            } else {
                                                answerType.BLOB_4 =
                                                    KBigAvatar.RETURN_PROMISE_SELECT_BIG_AVATAR(answerType).await()
                                                        ?.getAVATAR()
                                                updateObjectInfo(null)
                                                if ((answerType.LONG_20 + Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < DateTime.nowUnixLong()) {
                                                    SendRequestForUpdate("0")
                                                }
                                            }
                                        } else {
                                            if ((answerType.LONG_20 + Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < DateTime.nowUnixLong()) {
                                                SendRequestForUpdate("0")
                                            }
                                        }

                                    } else {
                                        if (answerType.BLOB_2 == null) {
                                            SendRequestForUpdate("2")
                                        } else {
                                            if ((answerType.LONG_20 + Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < DateTime.nowUnixLong()) {
                                                SendRequestForUpdate("0")
                                            }
                                        }
                                    }
                                } else {
                                    if ((answerType.LONG_20 + Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < DateTime.nowUnixLong()) {
                                        SendRequestForUpdate("0")
                                    }
                                }
                            } else if (answerTypeConstants.IsMessege) {
                                if (answerType.answerTypeValues.GetMainAvatarId().isNotEmpty()) {
                                    if (answerType.answerTypeValues.GetAvatarOriginalSize() > 0) {
                                        if (answerType.BLOB_4 == null) {
                                            answerType.BLOB_4 =
                                                RETURN_PROMISE_SELECT_BIG_AVATAR(answerType).await()?.getAVATAR()
                                        }
                                    } else if (answerType.BLOB_2 == null) {
                                        answerType.BLOB_4 =
                                            RETURN_PROMISE_SELECT_BIG_AVATAR(answerType).await()?.getAVATAR()
                                    }
                                }
                                updateObjectInfo(null)
                            }

                            promiseDowloadFile = localFileSevice?.open_file_channel()

                        } catch (e: my_user_exceptions_class) {
                            throw e
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KObjectInfo",
                                l_function_name = "VerifyUpdates",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            LocalLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeoutOrNull false
                } ?: throw my_user_exceptions_class(
                    l_class_name = "KObjectInfo",
                    l_function_name = "VerifyUpdates",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }
        }
    }

    fun SaveOffLine() = CoroutineScope(Dispatchers.Default).launch {
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            try {
                try {
                    LocalLock.lock()
                    VerifyUpdatesJob!!.join()
                    if (answerTypeConstants.IsMusic || answerTypeConstants.IsVideo) {
                        if (promiseDowloadFile!!.await()) {
                            if (answerType.RECORD_TYPE != "O") {
                                answerType.answerTypeValues.setRECORD_TYPE("O")
                            }
                        }
                    }

                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KObjectInfo",
                        l_function_name = "SaveOffLine",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.message
                    )
                } finally {
                    LocalLock.unlock()
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
            return@withTimeoutOrNull false
        } ?: throw my_user_exceptions_class(
            l_class_name = "KObjectInfo",
            l_function_name = "SaveOffLine",
            name_of_exception = "EXC_SYSTEM_ERROR",
            l_additional_text = "Time out is up"
        )
    }


    suspend fun SendRequestForUpdate(what_avatar_select: String) {

        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
            PrintInformation.PRINT_INFO("KObjectInfo.SendRequestForUpdate: Object: ${answerType.answerTypeValues.GetObjectId()} ; Avatar select: $what_avatar_select")
        }

        val jsocket = answerType.GetJsocket()
        jsocket.just_do_it = 1011000101  //
        jsocket.value_par3 = what_avatar_select
        jsocket.send_request()
    }

    fun merge(v: ANSWER_TYPE) {
        answerType.merge(v)
        updateObjectInfo(this)
    }

    companion object {
        private val GlobalLock = Mutex()

        @JsName("SAVE_OBJECT_INFO")
        fun SAVE_OBJECT_INFO(l_updatedCashData: ((v: Any?) -> Any?)): Promise<ArrayDeque<ANSWER_TYPE>> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            GlobalLock.lock()
                            if (SAVE_OBJECT_INFO == null) {
                                SAVE_OBJECT_INFO = KCashData.GET_CASH_DATA(
                                    L_OBJECT_ID = Account_Id,
                                    L_RECORD_TYPE = "O",
                                    l_updatedCashData = l_updatedCashData,
                                    l_request_updates = false,
                                    l_select_all_records = false,
                                    l_is_SetLastBlock = true,
                                    l_reset_cash_data = false,
                                    l_ignore_timeout = false
                                ).await()
                                return@withTimeoutOrNull SAVE_OBJECT_INFO!!.currentViewCashData
                            } else {
                                return@withTimeoutOrNull SAVE_OBJECT_INFO!!
                            }

                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KObjectInfo",
                                l_function_name = "SAVE_OBJECT_INFO",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            GlobalLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "KObjectInfo",
                    l_function_name = "SAVE_OBJECT_INFO",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )

                throw my_user_exceptions_class(
                    l_class_name = "KObjectInfo",
                    l_function_name = "SAVE_OBJECT_INFO",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }.toPromise(EmptyCoroutineContext)
    }

}
