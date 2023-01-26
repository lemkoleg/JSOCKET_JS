package Tables

import CrossPlatforms.PrintInformation
import Tables.KBigAvatar.Companion.RETURN_PROMISE_SELECT_BIG_AVATAR
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import p_jsocket.FileService
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

@InternalAPI
private val KObjectInfoLock = Mutex()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val OBJECTS_INFO: MutableMap<String, KObjectInfo> = mutableMapOf()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val SAVE_OBJECT_INFO_IDS: MutableMap<String, String> = mutableMapOf()

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
    private var updateObjectInfo: (() -> Any?) = {}

    fun SetCallBackUpdate(lupdateObjectInfo: (() -> Any?)? = null) {
        updateObjectInfo = lupdateObjectInfo ?: {}
    }

    fun VerifyUpdates() {
        if (VerifyUpdatesJob == null || !VerifyUpdatesJob!!.isActive) {
            VerifyUpdatesJob = CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            LocalLock.withLock {
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
                                                    updateObjectInfo()
                                                    if ((answerType.LONG_20 + Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < DateTime.nowUnixMillisLong()) {
                                                        SendRequestForUpdate("0")
                                                    }
                                                }
                                            } else {
                                                if ((answerType.LONG_20 + Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < DateTime.nowUnixMillisLong()) {
                                                    SendRequestForUpdate("0")
                                                }
                                            }

                                        } else {
                                            if (answerType.BLOB_2 == null) {
                                                SendRequestForUpdate("2")
                                            } else {
                                                if ((answerType.LONG_20 + Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < DateTime.nowUnixMillisLong()) {
                                                    SendRequestForUpdate("0")
                                                }
                                            }
                                        }
                                    } else {
                                        if ((answerType.LONG_20 + Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < DateTime.nowUnixMillisLong()) {
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
                                    updateObjectInfo()
                                }

                                promiseDowloadFile = localFileSevice?.open_file_channel()

                            }
                        } catch (e: my_user_exceptions_class) {
                            throw e
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KObjectInfo",
                                l_function_name = "VerifyUpdates",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.stackTraceToString()
                            )
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

    fun SaveOffLine() = CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately {
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            try {
                try {
                    VerifyUpdatesJob!!.join()
                    LocalLock.withLock {
                        if (answerType.RECORD_TYPE != "O") {
                            if (answerTypeConstants.IsMusic || answerTypeConstants.IsVideo) {
                                promiseDowloadFile!!.await()
                                if (!localFileSevice!!.IsDownloaded()) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "KObjectInfo",
                                        l_function_name = "SaveOffLine",
                                        name_of_exception = "EXC_ERROR_LOAD_FILE"
                                    )
                                }
                                answerType.answerTypeValues.setRECORD_TYPE("O")
                                answerType.INTEGER_20 = 1
                                val d = ArrayDeque<ANSWER_TYPE>()
                                d.add(answerType)
                                SAVE_OBJECT_INFO!!.SET_RECORDS(d)
                                localFileSevice.save_media!!.setIsPerminent()
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
                        l_additional_text = ex.stackTraceToString()
                    )
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

    fun DeleteOffLine() = CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately {
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            try {
                try {
                    LocalLock.withLock {
                        VerifyUpdatesJob!!.join()
                        if (answerType.RECORD_TYPE == "O") {
                            SAVE_MEDIA[answerType.answerTypeValues.GetObjectLink()]?.setIsNotPerminent()
                            SAVE_OBJECT_INFO_IDS.remove(answerType.answerTypeValues.GetObjectLink())
                            SAVE_OBJECT_INFO!!.DELETE(answerType.answerTypeValues.GetObjectLink())
                        }
                    }
                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KObjectInfo",
                        l_function_name = "DeleteOffLine",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
            return@withTimeoutOrNull false
        } ?: throw my_user_exceptions_class(
            l_class_name = "KObjectInfo",
            l_function_name = "DeleteOffLine",
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
        val save_media = SAVE_MEDIA[answerType.answerTypeValues.GetObjectLink()]
        if (save_media != null && save_media.IS_DOWNLOAD == 1) {
            jsocket.value_par1 = "0"
        } else {
            jsocket.value_par1 = "1"
        }
        jsocket.value_par3 = what_avatar_select
        jsocket.send_request()
    }

    fun merge(v: ANSWER_TYPE) {
        answerType.merge(v)
        updateObjectInfo()
        val arr = ArrayDeque<ANSWER_TYPE>()
        arr.addLast(answerType)
        Sqlite_service.InsertCashData(GetCashSum(v.RECORD_TABLE_ID, v.RECORD_TYPE, v.RECORD_TABLE_ID), arr)
    }

    companion object {
        private val GlobalLock = Mutex()

        @JsName("GET_SAVE_OBJECT_INFO")
        fun GET_SAVE_OBJECT_INFO(l_updatedCashData: (() -> Any?)): Promise<ArrayDeque<ANSWER_TYPE>> =
            CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
                var arr: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            GlobalLock.withLock {
                                if (SAVE_OBJECT_INFO == null) {
                                    SAVE_OBJECT_INFO = KCashData.GET_CASH_DATA(
                                        L_OBJECT_ID = Constants.Account_Id,
                                        L_RECORD_TYPE = "O",
                                        l_updatedCashData = l_updatedCashData,
                                        l_request_updates = false,
                                        l_select_all_records = false,
                                        l_is_SetLastBlock = true,
                                        l_reset_cash_data = false,
                                        l_ignore_timeout = false
                                    ).await()
                                }
                                arr = SAVE_OBJECT_INFO!!.currentViewCashData
                            }
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KObjectInfo",
                                l_function_name = "SAVE_OBJECT_INFO",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.stackTraceToString()
                            )
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
                return@async arr
            }.toPromise(EmptyCoroutineContext)


        @JsName("LOAD_SAVE_OBJECT_INFO_IDS")
        suspend fun LOAD_SAVE_OBJECT_INFO_IDS(ids: ArrayList<String>) {
            try {
                try {
                    withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                        GlobalLock.withLock {
                            ids.forEach {
                                SAVE_OBJECT_INFO_IDS[it] = it
                            }
                        }

                    } ?: throw my_user_exceptions_class(
                        l_class_name = "KObjectInfo",
                        l_function_name = "LOAD_SAVE_OBJECT_INFO_IDS",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "Time out is up"
                    )
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KObjectInfo",
                        l_function_name = "LOAD_SAVE_OBJECT_INFO_IDS",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        }

        @JsName("RE_LOAD_SAVE_OBJECT_INFO_IDS")
        fun RE_LOAD_SAVE_OBJECT_INFO_IDS(): Job {
            return Sqlite_service.LoadCashDataAllObjectsIdOnCasSum(GetCashSum(Constants.Account_Id, "O"))
        }

        private fun GetCashSum(
            L_OBJECT_ID: String,
            L_RECORD_TYPE: String,
            L_COURSE: String = "0",
            L_SORT: String = "0",
            L_LINK_OWNER: String = "",
            L_MESS_COUNT_FROM: String = "",
            L_OTHER_CONDITIONS_1: String = "",
            L_OTHER_CONDITIONS_2: String = "",
            L_OTHER_CONDITIONS_3: String = ""
        ): String {
            return (L_OBJECT_ID + L_RECORD_TYPE + L_COURSE + L_SORT + L_LINK_OWNER +
                    L_MESS_COUNT_FROM + L_OTHER_CONDITIONS_1 + L_OTHER_CONDITIONS_2 + L_OTHER_CONDITIONS_3)

        }
    }

}
