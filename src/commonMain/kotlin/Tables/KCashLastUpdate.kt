package Tables

import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.Constants
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val CASH_LAST_UPDATE: MutableMap<String, KCashLastUpdate> = mutableMapOf()


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
class KCashLastUpdate(
    val CASH_SUM: String,
    val RECORDS_TYPE: String,
    var LAST_USE: Long
) {

    init {
        ensureNeverFrozen()
    }

    //val InstanceRef: KCashLastUpdate> = AtomicReference(this)

    private val KCashLastUpdate_Lock = Mutex()

    val CashData: KCashData = CASH_DATAS[CASH_SUM] ?: KCashData(CASH_SUM)

    fun UPDATE_LAST_USE(): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashLastUpdate_Lock.lock()

                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashLastUpdate",
                            l_function_name = "UPDATE_LAST_USE",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KCashLastUpdate_Lock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeoutOrNull false
            } ?: false
        }.toPromise(EmptyCoroutineContext)


    companion object {

        private val KCashLastUpdate_Companion_Lock = Mutex()

        @JsName("GET_CASH_LAST_UPDATE")
        suspend fun GET_CASH_LAST_UPDATE(checkSum: String): KCashLastUpdate? {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashLastUpdate_Companion_Lock.lock()
                        return@withTimeoutOrNull CASH_LAST_UPDATE[checkSum] ?: KCashLastUpdate(checkSum)

                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashLastUpdate",
                            l_function_name = "GET_CASHLAST_UPDATE",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KCashLastUpdate_Companion_Lock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            }
            return null
        }

        @JsName("LOAD_CASH_LAST_UPDATE")
        suspend fun LOAD_CASH_LAST_UPDATE(arr: ArrayList<KCashLastUpdate>) {
            try {
                try {
                    KCashLastUpdate_Companion_Lock.lock()
                    arr.forEach {
                        CASH_LAST_UPDATE[it.CASH_SUM] = it
                    }
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashLastUpdate",
                        l_function_name = "LOAD_CASH_LAST_UPDATE",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.message
                    )
                } finally {
                    KCashLastUpdate_Companion_Lock.unlock()
                }

            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        }

        @JsName("RE_LOAD_CASH_LAST_UPDATE")
        fun RE_LOAD_CASH_LAST_UPDATE(): Job {
            return Sqlite_service.LoadCashLastUpdate()
        }
    }
}