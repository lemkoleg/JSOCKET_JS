@file:Suppress("DUPLICATE_LABEL_IN_WHEN", "DuplicatedCode")

package lib_exceptions

import CrossPlatforms.WriteExceptionIntoFile
import Tables.*
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import p_jsocket.*

@InternalAPI
private val ExceptionClasslock = Lock()

class my_user_exceptions_class : exception_names, Exception {

    override var class_name: String

    override var function_name: String

    override val exception_name: String?

    override val exception_text: String?

    override val exception_status: String

    val exception_full_text: String

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

    @InternalAPI
    constructor (
        l_class_name: String,
        l_function_name: String,
        name_of_exception: String,
        l_additional_text: String? = ""
    ) : super() {
        class_name = l_class_name
        function_name = l_function_name
        exception_name = name_of_exception
        var kException: KExceptions.KException? =
            USERS_EXCEPTIONS[name_of_exception]?.EXCEPTIONS_CLASSES?.get(myLang.value)

        if(kException == null && myLang.value != "ENG"){
            USERS_EXCEPTIONS[name_of_exception]?.EXCEPTIONS_CLASSES?.get("ENG")
        }

        if(kException == null){
            kException = KExceptions.KException(
                "Unexpected Exception", "ENG",
                "Unexpected exception encountered: $exception_name", "2", 0
            )
        }

        exception_text = if(l_additional_text != null && l_additional_text.length > 0){
            kException.TEXT_OF_ECXEPTION + " - " +l_additional_text
        }else{
            kException.TEXT_OF_ECXEPTION
        }
        exception_status = kException.EXCEPTIONS_STATUS
        date_of_exception = DateTime.now().format(DateFormat.FORMAT1)
        exception_full_text =
            "class_name: $class_name ; function_name: $function_name ; exception_text: $exception_text"
    }

    @InternalAPI
    fun ExceptionHand(jsocket: JSOCKET?) {

        if (FIX_INTO_SCRENN_ERRORS == 1) {
            println(exception_full_text)
        }
        when (exception_status) {
            "0", "1" -> return
            "2", "4" -> CoroutineScope(Dispatchers.Default).launch {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    try {
                        ExceptionClasslock.lock()
                        val file_name: String =
                            if (USE_SINGLE_FILE_FOR_FIX_ERRORS == 1) "Errors.log" else (date_of_exception + "_" + class_name)
                        WriteExceptionIntoFile(date_of_exception + "_" + exception_full_text, file_name)

                    } catch (ex: Exception) {
                    } finally {
                        ExceptionClasslock.unlock()
                    }
                }
            }
            "3" -> if (jsocket != null) {
                jsocket.just_do_it_successfull = "9"; jsocket.db_massage =
                    if (FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME == 1) exception_full_text else exception_text ?: "null"
            }
            "4" -> {
                CoroutineScope(Dispatchers.Default).launch {
                    withTimeoutOrNull(CLIENT_TIMEOUT) {
                        try {
                            ExceptionClasslock.lock()
                            val file_name: String =
                                if (USE_SINGLE_FILE_FOR_FIX_ERRORS == 1) "Errors.log" else (date_of_exception + "_" + class_name)
                            WriteExceptionIntoFile(date_of_exception + "_" + exception_full_text, file_name)

                        } catch (ex: Exception) {
                        } finally {
                            ExceptionClasslock.unlock()
                        }
                    }
                }
                if (jsocket != null) {
                    jsocket.just_do_it_successfull = "9"; jsocket.db_massage =
                        if (FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME == 1) exception_full_text else exception_text ?: "null"
                }
            }
        }
    }
}