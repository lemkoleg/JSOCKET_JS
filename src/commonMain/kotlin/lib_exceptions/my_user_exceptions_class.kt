@file:Suppress("DUPLICATE_LABEL_IN_WHEN", "DuplicatedCode")

package lib_exceptions

import CrossPlatforms.CrossPlatformFile
import CrossPlatforms.PrintInformation
import Tables.KExceptions
import Tables.USERS_EXCEPTIONS
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime

import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import p_jsocket.Constants
import p_jsocket.JSOCKET
import p_jsocket.JSOCKET_Instance
import kotlinx.coroutines.*




@OptIn(KorioExperimentalApi::class)
class my_user_exceptions_class : exception_names, Exception {

    override var class_name: String

    override var function_name: String

    override val exception_name: String?

    override val exception_text: String?

    override val exception_status: String

    var exception_full_text: String = ""

    val date_of_exception: String

    private constructor() : super() {
        class_name = "null"
        function_name = "null"
        exception_name = "null"
        exception_text = "null"
        exception_status = "0"
        date_of_exception = ""
        exception_full_text =
            "class_name: $class_name ; function_name: $function_name ; exception_text: $exception_text"
    }


    constructor (
        l_class_name: String,
        l_function_name: String,
        name_of_exception: String,
        l_additional_text: String? = ""
    ) : super("l_class_name: $l_class_name; l_function_name: $l_function_name; name_of_exception: $name_of_exception; l_additional_text: $l_additional_text;") {
        class_name = l_class_name
        function_name = l_function_name
        exception_name = name_of_exception
        var kException: KExceptions.KException? =
            USERS_EXCEPTIONS[name_of_exception]?.EXCEPTIONS_CLASSES?.get(Constants.myLang)

        if (kException == null && Constants.myLang != "ENG") {
            USERS_EXCEPTIONS[name_of_exception]?.EXCEPTIONS_CLASSES?.get("ENG")
        }

        if (kException == null) {
            kException = KExceptions.KException(
                "Unexpected Exception", "ENG",
                "Unexpected exception encountered: $exception_name", "2", 0
            )
        }

        exception_text = if (!l_additional_text.isNullOrEmpty()) {
            kException.TEXT_OF_ECXEPTION + " - " + l_additional_text
        } else {
            kException.TEXT_OF_ECXEPTION
        }
        exception_status = kException.EXCEPTIONS_STATUS
        date_of_exception = DateTime.now().format(DateFormat.FORMAT1)
        exception_full_text =
            "class_name: $class_name ; function_name: $function_name ; exception_text: $exception_text"

    }

    constructor (
        ex: my_user_exceptions_class
    ) : super() {
        class_name = ex.class_name

        function_name = ex.function_name

        exception_name = ex.exception_name

        exception_text = ex.exception_text

        exception_status = ex.exception_status

        exception_full_text = ex.exception_full_text

        date_of_exception = ex.date_of_exception

    }


    fun ExceptionHand(jsocket: JSOCKET?) {

        if (Constants.FIX_INTO_SCREEN_ERRORS == 1 || Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
            PrintInformation.PRINT_EXCEPTION(exception_full_text)
        }
        when (exception_status) {
            "0", "1" -> return
            "2", "4" -> WriteExceptionIntoFile(date_of_exception , exception_full_text)

            "3" -> if (jsocket != null) {
                jsocket.just_do_it_successfull = "9"
                jsocket.db_massage =
                    if (Constants.FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME == 1) exception_full_text else exception_text
                        ?: "null"
            }

            "4" -> {
                WriteExceptionIntoFile(date_of_exception , exception_full_text)
                if (jsocket != null) {
                    jsocket.just_do_it_successfull = "9"; jsocket.db_massage =
                        if (Constants.FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME == 1) exception_full_text else exception_text
                            ?: "null"
                }
            }
        }
    }

    companion object {
        private val WriteExceptionIntoFileLock = Mutex()
        private val file: CrossPlatformFile by lazy { CrossPlatformFile(fullName = "${JSOCKET_Instance.pathErrors}Errors.log", mode = 4) }
        fun WriteExceptionIntoFile(date_of_exception: String, exception: String) {
            CoroutineScope(Dispatchers.Default + SupervisorJob()).launch {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    WriteExceptionIntoFileLock.withLock {
                        if(!file.isInit){
                            file.create(1L)
                        }
                        file.writeLines("$date_of_exception: $exception")
                    }
                }
            }
        }
    }
}
