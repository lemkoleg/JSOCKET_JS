package Tables

import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.klock.DateTime
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
    val RECORD_TYPE: String,
    val COURSE: String
) {

    init {
        ensureNeverFrozen()
    }

    var LAST_USE: Long = DateTime.nowUnixLong()

    //val InstanceRef: KCashLastUpdate> = AtomicReference(this)

    @JsName("INSERT_CASH_LASTUPDATE")
    fun INSERT_CASH_LASTUPDATE(value: KCashLastUpdate = this): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            try {
                try {
                    value.LAST_USE = DateTime.nowUnixLong()
                    Sqlite_service.InsertCashLastUpdate(value)

                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashLastUpdate",
                        l_function_name = "UPDATE_LAST_USE",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.message
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
            return@async false
        }.toPromise(EmptyCoroutineContext)


    companion object {

        private val KCashLastUpdate_Companion_Lock = Mutex()

        /*
        @JsName("GET_CASH_LASTUPDATE")
        suspend fun GET_CASH_LASTUPDATE(checkSum: String, record_type: String): KCashLastUpdate {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashLastUpdate_Companion_Lock.lock()
                        var k = CASH_LAST_UPDATE[checkSum]
                        if (k == null) {
                            k = KCashLastUpdate(checkSum, record_type)
                            CASH_LAST_UPDATE[k.CASH_SUM] = k
                        }
                        if (k.RECORD_TYPE != record_type) {
                            throw my_user_exceptions_class(
                                l_class_name = "KCashLastUpdate",
                                l_function_name = "GET_CASH_LASTUPDATE",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "RECORD_TYPE of CASH_DATA ${k.RECORD_TYPE} not equals RECORD_TYPE by select $record_type "
                            )
                        }
                        return@withTimeoutOrNull k

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
                    return@withTimeoutOrNull KCashLastUpdate(checkSum, record_type)
                }
            }
            return KCashLastUpdate(checkSum, record_type)
        }
        */

        @JsName("LOAD_CASH_LAST_UPDATE")
        suspend fun LOAD_CASH_LAST_UPDATE(arr: ArrayList<KCashLastUpdate>) {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashLastUpdate_Companion_Lock.lock()
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            println("LOAD_CASH_LAST_UPDATE is running")
                        }
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
        }

        @JsName("RE_LOAD_CASH_LAST_UPDATE")
        fun RE_LOAD_CASH_LAST_UPDATE(): Job {
            return Sqlite_service.LoadCashLastUpdate()
        }
    }
}