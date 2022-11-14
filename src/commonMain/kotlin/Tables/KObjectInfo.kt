package Tables

import Tables.KBigAvatar.Companion.RETURN_PROMISE_SELECT_BIG_AVATAR
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.await
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import p_jsocket.FileService
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
class KObjectInfo(l_answerType: ANSWER_TYPE) {

    private val LocalLock = Mutex()

    val answerType: ANSWER_TYPE = l_answerType

    var VerifyUpdatesJob: Job? = null

    val answerTypeValues = l_answerType.answerTypeValues

    val answerTypeConstants = answerTypeValues.answerTypeConstants

    val localFileSevice: FileService? =
        if (answerTypeConstants.IsMediaContent || answerTypeConstants.IsFile) FileService(answerType = answerType) else null

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

                            localFileSevice?.open_file_channel()

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

    suspend fun SendRequestForUpdate(what_avatar_select: String) {
        val jsocket = answerType.GetJsocket()
        jsocket.just_do_it = 1011000101  //
        jsocket.value_par3 = what_avatar_select
        jsocket.send_request()
    }

    fun merge(v: ANSWER_TYPE) {
        answerType.merge(v)
        updateObjectInfo(this)
    }

}
