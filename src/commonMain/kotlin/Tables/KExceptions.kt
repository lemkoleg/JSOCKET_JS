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
val NEW_EXCEPTIONS: ArrayDeque<ANSWER_TYPE> = ArrayDeque()



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


    @InternalAPI
    companion object {


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

        @ExperimentalStdlibApi
        @InternalAPI
        fun INSERT_EXCEPTIONS(
            ans: ArrayList<KException>,
            is_Update_DB: Boolean
        ) {
            CoroutineScope(NonCancellable).launch {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KExceptionsLock.lock()
                            ans.forEach {
                                INSERT_EXCEPTION(it)
                            }
                            if (is_Update_DB) {
                                Sqlite_service.InsertExceptions(ans)
                            }
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KExceptions",
                                l_function_name = "INSERT_EXCEPTIONS",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KExceptionsLock.unlock()
                        }
                    } catch (ex: my_user_exceptions_class) {
                        ex.ExceptionHand(null)
                    }
                }
            }
        }

        @KorioExperimentalApi
        @JsName("ADD_NEW_EXCEPTIONS")
        fun ADD_NEW_EXCEPTIONS(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeout(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KExceptionsLock.lock()
                            while (NEW_EXCEPTIONS.isNotEmpty()) {
                                TODO()
                            }
                            return@withTimeout true
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KExceptions",
                                l_function_name = "ADD_NEW_EXCEPTIONS",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KExceptionsLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeout false
                }
            }.toPromise(EmptyCoroutineContext)

    }
}