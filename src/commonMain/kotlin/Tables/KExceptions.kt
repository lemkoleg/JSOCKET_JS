package Tables

import CrossPlatforms.PrintInformation
import atomic.AtomicLong
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.async
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
val USERS_EXCEPTIONS: MutableMap<String, KExceptions> = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val EXCEPTION_LAST_UPDATE = AtomicLong(0L)

@InternalAPI
private val KExceptionsLock = Mutex()


@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
fun init(): Boolean {

    USERS_EXCEPTIONS["EXC_SYSTEM_ERROR"] = KExceptions(
        KExceptions.KException(
            "EXC_SYSTEM_ERROR",
            "ENG",
            "System error: ",
            "2",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_WRSOCKETTYPE_NOT_FOUND_COMMAND"] = KExceptions(
        KExceptions.KException(
            "EXC_WRSOCKETTYPE_NOT_FOUND_COMMAND",
            "ENG",
            "Command not found",
            "4",
            0
        )
    )
    USERS_EXCEPTIONS["EXC_CONNECTION_ID_IS_WRONG"] = KExceptions(
        KExceptions.KException(
            "EXC_CONNECTION_ID_IS_WRONG",
            "ENG",
            "Connection ID is invalid or empty.",
            "2",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_CONNECTION_COOCKI_IS_WRONG"] = KExceptions(
        KExceptions.KException(
            "EXC_CONNECTION_COOCKI_IS_WRONG",
            "ENG",
            "Connection coocki is invalid or empty.",
            "2",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_TOO_MANY_SIZE_OF_REQUEST"] = KExceptions(
        KExceptions.KException(
            "EXC_TOO_MANY_SIZE_OF_REQUEST",
            "ENG",
            "The request size is larger than allowed or zero.",
            "2",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_SOCKET_NOT_ALLOWED"] = KExceptions(
        KExceptions.KException(
            "EXC_SOCKET_NOT_ALLOWED",
            "ENG",
            "No connection.",
            "3",
            0
        )
    )

    USERS_EXCEPTIONS["QUEUE FULL"] = KExceptions(
        KExceptions.KException(
            "QUEUE FULL",
            "ENG",
            "Queue is full.",
            "2",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_AVATAR_TYPE_OF_FILE_IS_WRONG"] = KExceptions(
        KExceptions.KException(
            "EXC_AVATAR_TYPE_OF_FILE_IS_WRONG",
            "ENG",
            "Invalid file name.",
            "3",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_TOO_MANY_SIZE_OF_OBJECT"] = KExceptions(
        KExceptions.KException(
            "EXC_TOO_MANY_SIZE_OF_OBJECT",
            "ENG",
            "Invalid file size.",
            "3",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_FILE_IS_NOT_EXISTS"] = KExceptions(
        KExceptions.KException(
            "EXC_FILE_IS_NOT_EXISTS",
            "ENG",
            "File not found.",
            "4",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_WRSOCKETTYPE_CONN_ID_OR_COOCKI_NOT_VALID"] = KExceptions(
        KExceptions.KException(
            "EXC_WRSOCKETTYPE_CONN_ID_OR_COOCKI_NOT_VALID",
            "ENG",
            "Connection is not valid. Re-login required!",
            "4",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_MAX_TRYING_SEND_RECEIVE_FILE_CHUNKS"] = KExceptions(
        KExceptions.KException(
            "EXC_MAX_TRYING_SEND_RECEIVE_FILE_CHUNKS",
            "ENG",
            "The maximum number of attempts to transfer a file has been exceeded.",
            "4",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_EMPTY_ACC_MAIL_INSERT"] = KExceptions(
        KExceptions.KException(
            "EXC_EMPTY_ACC_MAIL_INSERT",
            "ENG",
            "Mail canot be is empty.",
            "4",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_EMPTY_ACC_PASS_INSERT"] = KExceptions(
        KExceptions.KException(
            "EXC_EMPTY_ACC_PASS_INSERT",
            "ENG",
            "Password canot be is empty.",
            "4",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_OBJECTS_EXTENSION_NOT_VALID"] = KExceptions(
        KExceptions.KException(
            "EXC_OBJECTS_EXTENSION_NOT_VALID",
            "ENG",
            "Invalid file extension.",
            "4",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_WRSOCKETTYPE_COMMAND_NOT_ALLOWED"] = KExceptions(
        KExceptions.KException(
            "EXC_WRSOCKETTYPE_COMMAND_NOT_ALLOWED",
            "ENG",
            "Command not allowed.",
            "4",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_ERROR_SEND_FILE"] = KExceptions(
        KExceptions.KException(
            "EXC_ERROR_SEND_FILE",
            "ENG",
            "Failed to send file. Please try again.",
            "4",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_ERROR_LOAD_FILE"] = KExceptions(
        KExceptions.KException(
            "EXC_ERROR_LOAD_FILE",
            "ENG",
            "An error occurred while loading the file. Try again.",
            "4",
            0
        )
    )

    USERS_EXCEPTIONS["EXC_ERROR_CUTE_IMAGE"] = KExceptions(
        KExceptions.KException(
            "EXC_ERROR_CUTE_IMAGE",
            "ENG",
            "Failed to crop image.",
            "4",
            0
        )
    )

    return true
}

@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
private var is_init: Boolean = init()

@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
@JsName("KExceptions")
class KExceptions {

    init {
        ensureNeverFrozen()
    }

    class KException {

        init {
            ensureNeverFrozen()
        }

        val NAME_OF_ECXEPTION: String
        val LANG_OF_ECXEPTION: String
        var TEXT_OF_ECXEPTION: String = ""
        var EXCEPTIONS_STATUS: String = ""
        var LAST_UPDATE: Long = 0

        private constructor() {
            NAME_OF_ECXEPTION = ""
            LANG_OF_ECXEPTION = ""
        }

        constructor(
            L_NAME_OF_ECXEPTION: String,
            L_LANG_OF_ECXEPTION: String,
            L_TEXT_OF_ECXEPTION: String,
            L_EXCEPTIONS_STATUS: String,
            L_LAST_UPDATE: Long
        ) {
            NAME_OF_ECXEPTION = L_NAME_OF_ECXEPTION
            LANG_OF_ECXEPTION = L_LANG_OF_ECXEPTION
            TEXT_OF_ECXEPTION = L_TEXT_OF_ECXEPTION
            EXCEPTIONS_STATUS = L_EXCEPTIONS_STATUS
            LAST_UPDATE = L_LAST_UPDATE
        }

        constructor(ans: ANSWER_TYPE) : this(
            ans.STRING_1!!,
            ans.STRING_2!!,
            ans.STRING_3!!,
            ans.STRING_4!!,
            ans.LONG_1!!
        )

    }

    private val NAME_OF_ECXEPTION: String
    val EXCEPTIONS_CLASSES: MutableMap<String, KException> = mutableMapOf() //LANG_OF_ECXEPTION and TEXT_OF_ECXEPTION
    private var EXCEPTIONS_STATUS: String = ""
    private var LAST_UPDATE: Long = 0

    private constructor() {
        NAME_OF_ECXEPTION = ""
    }

    constructor(
        kException: KException
    ) {

        NAME_OF_ECXEPTION = kException.NAME_OF_ECXEPTION
        EXCEPTIONS_CLASSES[kException.LANG_OF_ECXEPTION] = kException
        EXCEPTIONS_STATUS = kException.EXCEPTIONS_STATUS
        LAST_UPDATE = kException.LAST_UPDATE

    }


    companion object {

        @JsName("ADD_NEW_EXCEPTIONS")
        fun ADD_NEW_EXCEPTIONS(arr: ArrayDeque<ANSWER_TYPE>): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        KExceptionsLock.withLock {
                            val exc: ArrayList<KException> = ArrayList()
                            try {

                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("ADD_NEW_EXCEPTIONS is running")
                                }

                                arr.forEach {
                                    if (!it.RECORD_TYPE.equals("5")) {
                                        throw my_user_exceptions_class(
                                            l_class_name = "KException",
                                            l_function_name = "ADD_NEW_EXCEPTIONS",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "Record is not Exception"
                                        )
                                    }
                                    val e = KException(it)
                                    meta_data_last_update.setGreaterValue(e.LAST_UPDATE)
                                    INSERT_EXCEPTION(e)
                                    exc.add(e)
                                }
                                return@withTimeoutOrNull true
                            } catch (e: my_user_exceptions_class) {
                                throw e
                            } catch (ex: Exception) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KException",
                                    l_function_name = "ADD_NEW_EXCEPTIONS",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = ex.stackTraceToString()
                                )
                            } finally {
                                if (!exc.isEmpty()) {
                                    Sqlite_service.InsertExceptions(exc)
                                }
                            }
                        }
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KException",
                            l_function_name = "ADD_NEW_EXCEPTIONS",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeoutOrNull false
                } ?: throw my_user_exceptions_class(
                    l_class_name = "KExceptions",
                    l_function_name = "ADD_NEW_EXCEPTIONS",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }.toPromise(EmptyCoroutineContext)


        private suspend fun INSERT_EXCEPTION(kException: KException) {
            val kExceptions: KExceptions? = USERS_EXCEPTIONS[kException.NAME_OF_ECXEPTION]
            if (kExceptions != null) {
                kExceptions.EXCEPTIONS_CLASSES[kException.LANG_OF_ECXEPTION] = kException
                kExceptions.EXCEPTIONS_STATUS = kException.EXCEPTIONS_STATUS
                if (kExceptions.LAST_UPDATE < kException.LAST_UPDATE) {
                    kExceptions.LAST_UPDATE = kException.LAST_UPDATE
                    EXCEPTION_LAST_UPDATE.setGreaterValue(kExceptions.LAST_UPDATE)
                }
            } else {
                USERS_EXCEPTIONS[kException.NAME_OF_ECXEPTION] = KExceptions(kException)
                EXCEPTION_LAST_UPDATE.setGreaterValue(kException.LAST_UPDATE)
            }
        }

        suspend fun LOAD_EXCEPTIONS(arr: ArrayList<KException>) {
            try {
                try {
                    KExceptionsLock.withLock {
                        arr.forEach {
                            INSERT_EXCEPTION(it)
                            meta_data_last_update.setGreaterValue(it.LAST_UPDATE)
                        }
                    }
                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KExceptions",
                        l_function_name = "INSERT_EXCEPTIONS",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
                }
            } catch (ex: my_user_exceptions_class) {
                ex.ExceptionHand(null)
            }
        }

        @JsName("RE_LOAD_EXCEPTIONS")
        fun RE_LOAD_EXCEPTIONS(): Job {
            return Sqlite_service.LoadExceptions()
        }
    }
}