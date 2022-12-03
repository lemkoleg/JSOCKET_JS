@file:Suppress("DUPLICATE_LABEL_IN_WHEN", "DuplicatedCode")

package lib_exceptions

import CrossPlatforms.PrintInformation
import CrossPlatforms.WriteExceptionIntoFile
import Tables.KExceptions
import Tables.USERS_EXCEPTIONS
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import p_jsocket.Constants
import p_jsocket.JSOCKET
import kotlin.time.ExperimentalTime

@InternalAPI
private val ExceptionClasslock = Mutex()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
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
            USERS_EXCEPTIONS[name_of_exception]?.EXCEPTIONS_CLASSES?.get(Constants.myLang)

        if(kException == null && Constants.myLang != "ENG"){
            USERS_EXCEPTIONS[name_of_exception]?.EXCEPTIONS_CLASSES?.get("ENG")
        }

        if(kException == null){
            kException = KExceptions.KException(
                "Unexpected Exception", "ENG",
                "Unexpected exception encountered: $exception_name", "2", 0
            )
        }

        exception_text = if(!l_additional_text.isNullOrEmpty()){
            kException.TEXT_OF_ECXEPTION + " - " +l_additional_text
        }else{
            kException.TEXT_OF_ECXEPTION
        }
        exception_status = kException.EXCEPTIONS_STATUS
        date_of_exception = DateTime.now().format(DateFormat.FORMAT1)
        exception_full_text =
            "class_name: $class_name ; function_name: $function_name ; exception_text: $exception_text"

        if (Constants.FIX_INTO_SCREEN_ERRORS == 1){
            println(exception_full_text)
        }

    }

    fun ExceptionHand(jsocket: JSOCKET?) {

        if (Constants.FIX_INTO_SCREEN_ERRORS == 1 || Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
            PrintInformation.PRINT_EXCEPTION(exception_full_text)
        }
        when (exception_status) {
            "0", "1" -> return
            "2", "4" -> CoroutineScope(Dispatchers.Default).launch {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    ExceptionClasslock.withLock {
                        val file_name: String =
                            if (Constants.USE_SINGLE_FILE_FOR_FIX_ERRORS == 1) "Errors.log" else (date_of_exception + "_" + class_name)
                        WriteExceptionIntoFile(date_of_exception + "_" + exception_full_text, file_name)

                    }
                }
            }
            "3" -> if (jsocket != null) {
                jsocket.just_do_it_successfull = "9"; jsocket.db_massage =
                    if (Constants.FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME == 1) exception_full_text else exception_text ?: "null"
            }
            "4" -> {
                CoroutineScope(Dispatchers.Default).launch {
                    withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                        ExceptionClasslock.withLock {
                            val file_name: String =
                                if (Constants.USE_SINGLE_FILE_FOR_FIX_ERRORS == 1) "Errors.log" else (date_of_exception + "_" + class_name)
                            WriteExceptionIntoFile(date_of_exception + "_" + exception_full_text, file_name)

                        }
                    }
                }
                if (jsocket != null) {
                    jsocket.just_do_it_successfull = "9"; jsocket.db_massage =
                        if (Constants.FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME == 1) exception_full_text else exception_text ?: "null"
                }
            }
        }
    }
}