package Tables


import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withTimeout
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@InternalAPI
private val KCashDataLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val NEW_CASH_DATA: ArrayDeque<ANSWER_TYPE>  = ArrayDeque()


@JsName("KCashData")
@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
class KCashData {

    init {
        ensureNeverFrozen()
    }

    //val InstanceRef: AtomicReference<KCashData> = AtomicReference(this)



    companion object {

        @KorioExperimentalApi
        @JsName("ADD_NEW_CASH_DATA")
        fun ADD_NEW_CASH_DATA(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeout(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KCashDataLock.lock()
                            while (NEW_CASH_DATA.isNotEmpty()) {
                                TODO()
                            }
                            return@withTimeout true
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KCashData",
                                l_function_name = "ADD_NEW_CASH_DATA",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KCashDataLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeout false
                }
            }.toPromise(EmptyCoroutineContext)
    }
}