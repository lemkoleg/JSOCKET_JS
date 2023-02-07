package Tables

import CrossPlatforms.PrintInformation
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_client.Jsocket
import p_jsocket.Constants
import sql.Sqlite_service
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
    val OBJECT_ID: String,
    val RECORD_TYPE: String,
    val COURSE: String = "0",
    val SORT: String = "0",
    val LINK_OWNER: String = "",
    val MESS_COUNT_FROM: String = "",
    val OTHER_CONDITIONS_1: String = "",
    val OTHER_CONDITIONS_2: String = "",
    val OTHER_CONDITIONS_3: String = "",
    private var LAST_SELECT: Long = 0L,
    private var LAST_USE: Long = DateTime.nowUnixMillisLong()
) {


    constructor(
        L_OBJECT_ID: String,
        L_RECORD_TYPE: String,
        L_COURSE: String = "0",
        L_SORT: String = "0",
        L_LINK_OWNER: String = "",
        L_MESS_COUNT_FROM: String = "",
        L_OTHER_CONDITIONS_1: String = "",
        L_OTHER_CONDITIONS_2: String = "",
        L_OTHER_CONDITIONS_3: String = ""
    ) : this(
        CASH_SUM = (L_OBJECT_ID + L_RECORD_TYPE + L_COURSE + L_SORT + L_LINK_OWNER +
                L_MESS_COUNT_FROM + L_OTHER_CONDITIONS_1 + L_OTHER_CONDITIONS_2 + L_OTHER_CONDITIONS_3),
        OBJECT_ID = L_OBJECT_ID,
        RECORD_TYPE = L_RECORD_TYPE,
        COURSE = L_COURSE,
        SORT = L_SORT,
        LINK_OWNER = L_LINK_OWNER,
        MESS_COUNT_FROM = L_MESS_COUNT_FROM,
        OTHER_CONDITIONS_1 = L_OTHER_CONDITIONS_1,
        OTHER_CONDITIONS_2 = L_OTHER_CONDITIONS_2,
        OTHER_CONDITIONS_3 = L_OTHER_CONDITIONS_3
    ) {
    }

    init {
        if (!CASH_LAST_UPDATE.containsKey(CASH_SUM)) {
            CASH_LAST_UPDATE[CASH_SUM] = this
        } else {
            throw my_user_exceptions_class(
                l_class_name = "KCashLastUpdate",
                l_function_name = "Constructor",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Instant already exist"
            )
        }
        ensureNeverFrozen()
    }

    //val InstanceRef: KCashLastUpdate> = AtomicReference(this)

    fun GET_LAST_SELECT(): Long {
        return LAST_SELECT
    }

    fun SET_LAST_SELECT(v: Long) {
        if (v > LAST_SELECT) {
            LAST_SELECT = v
            // если это данные чата, то отправляем запрос, для доставки последней даты  выборки;
            if(CHATS!!.CashLastUpdate.CASH_SUM.equals(this.CASH_SUM)){
                CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately{
                    val socket: Jsocket = Jsocket.GetJsocket() ?: Jsocket()
                    socket.just_do_it = 1011000068  // RE_SEND_REQUEST_PROFILE;
                    socket.send_request(await_answer = false)
                }
            }
            LAST_USE = DateTime.nowUnixMillisLong()
            Sqlite_service.InsertCashLastUpdate(this)
        }
    }

    fun GET_LAST_USE(): Long {
        return LAST_USE
    }

    fun SET_LAST_USE() {
        LAST_USE = DateTime.nowUnixMillisLong()
        Sqlite_service.InsertCashLastUpdate(this)
    }


    /*
    @JsName("INSERT_CASH_LASTUPDATE")
    fun INSERT_CASH_LASTUPDATE(value: KCashLastUpdate = this): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            try {
                try {
                    value.LAST_USE = DateTime.nowUnixMillisLong()
                    Sqlite_service.InsertCashLastUpdate(value)

                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashLastUpdate",
                        l_function_name = "UPDATE_LAST_USE",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
            return@async false
        }.toPromise(EmptyCoroutineContext)
     */


    companion object {

        private val KCashLastUpdate_Companion_Lock = Mutex()

        @JsName("LOAD_CASH_LAST_UPDATE")
        suspend fun LOAD_CASH_LAST_UPDATE(@Suppress("UNUSED_PARAMETER") arr: ArrayList<KCashLastUpdate>) {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashLastUpdate_Companion_Lock.withLock {
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                PrintInformation.PRINT_INFO("LOAD_CASH_LAST_UPDATE is running")
                            }
                            /*
                            arr.forEach {
                                CASH_LAST_UPDATE[it.CASH_SUM] = it
                            }
                             */
                        }
                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashLastUpdate",
                            l_function_name = "LOAD_CASH_LAST_UPDATE",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            } ?: throw my_user_exceptions_class(
                l_class_name = "KCasLastUpdate",
                l_function_name = "LOAD_CASH_LAST_UPDATE",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }

        @JsName("RE_LOAD_CASH_LAST_UPDATE")
        fun RE_LOAD_CASH_LAST_UPDATE(): Job {
            return Sqlite_service.LoadCashLastUpdate()
        }

        fun GetCashSum(
            L_OBJECT_ID: String,
            L_RECORD_TYPE: String,
            L_COURSE: String = "0",
            L_SORT: String = "0",
            L_LINK_OWNER: String = "",
            L_MESS_COUNT_FROM: String = "",
            L_OTHER_CONDITIONS_1: String = "",
            L_OTHER_CONDITIONS_2: String = "",
            L_OTHER_CONDITIONS_3: String = ""
        ): String {
            return (L_OBJECT_ID + L_RECORD_TYPE + L_COURSE + L_SORT + L_LINK_OWNER +
                    L_MESS_COUNT_FROM + L_OTHER_CONDITIONS_1 + L_OTHER_CONDITIONS_2 + L_OTHER_CONDITIONS_3)

        }
    }
}