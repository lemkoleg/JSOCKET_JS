package CrossPlatforms

import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withTimeoutOrNull
import lib_exceptions.my_user_exceptions_class
import p_jsocket.Constants
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("PrintInformation")
object PrintInformation {

    private val PrintInformationLock = Mutex()

    @JsName("PRINT_INFO")
    fun PRINT_INFO(text: String): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        PrintInformationLock.lock()
                        PrintInformation.PRINT_INFO("Information: $text")
                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "PrintInformation",
                            l_function_name = "PRINT_INFO",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        PrintInformationLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeoutOrNull false
            } ?: throw my_user_exceptions_class(
                l_class_name = "PrintInformation",
                l_function_name = "PRINT_INFO",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }.toPromise(EmptyCoroutineContext)
}