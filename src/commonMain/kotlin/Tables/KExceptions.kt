package Tables

import atomic.AtomicLong
import io.ktor.util.*
import kotlinx.coroutines.*
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.CLIENT_TIMEOUT
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName

val USERS_EXCEPTIONS: MutableMap<String, KExceptions> = mutableMapOf()
val EXCEPTION_LAST_UPDATE: AtomicLong = AtomicLong(0L)

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
            "2",
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
            "Connection not available.",
            "2",
            0
        )
    )
    return true
}

private var is_init: Boolean = init()

@JsName("KExceptions")
class KExceptions {

    class KException {
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
            ans.STRING_2!!,
            ans.STRING_3!!,
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
    companion object : CoroutineScope {

        private val KExceptions_Lock = Lock()

        override val coroutineContext: CoroutineContext = Dispatchers.Default

        private val KExceptions_ServiceScope = CoroutineScope(coroutineContext) + SupervisorJob()


        private fun INSERT_EXCEPTION(kException: KException) {
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
            KExceptions_ServiceScope.launch {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    try {
                        try {
                            KExceptions_Lock.lock()
                            ans.forEach {
                                INSERT_EXCEPTION(it)
                            }
                            if (is_Update_DB) {
                                Sqlite_service.InsertExceptions(ans)
                            }
                        } catch (ex: Exception) {
                            throw ex.message?.let { my_user_exceptions_class("KExceptions", "INSERT_EXCEPTIONS", "EXC_SYSTEM_ERROR", it) }!!
                        } finally {
                            KExceptions_Lock.unlock()
                        }
                    } catch (ex: my_user_exceptions_class) {
                        ex.ExceptionHand(null)
                    }
                }
            }
        }
    }
}