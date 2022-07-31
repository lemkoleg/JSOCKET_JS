package Tables


import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
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




@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val CASH_DATAS: MutableMap<String, KCashData> = mutableMapOf()


@JsName("KCashData")
@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
class KCashData(val CASH_SUM: String) {


    //val InstanceRef: AtomicReference<KCashData> = AtomicReference(this)

    private val KCashDataLock = Mutex()

    val NEW_CASH_DATA: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

    val ORDERED_CASH_DATA: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

    val CASH_DATA_OBJECTS: MutableMap<String, ANSWER_TYPE> = mutableMapOf()

    val deletedCashData: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

    init {
        ensureNeverFrozen()
        if(CASH_DATAS.containsKey(CASH_SUM)){
            throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "construktor",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "CASH_DATAS alredy contain that Key"
            )
        }

        CASH_DATAS[CASH_SUM] = this
    }

    var currentJobOrder: Job? = null

    var currentJobUpdateLastSelect: Job? = null

    @JsName("ADD_NEW_CASH_DATA")
    fun ADD_NEW_CASH_DATA(lastSelect: Long): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDataLock.lock()

                        var currentJob: Job? = null

                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            println("ADD_NEW_CASH_DATA is running")
                        }

                        if (ORDERED_CASH_DATA.size != CASH_DATA_OBJECTS.size) {
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                println("ADD_NEW_CASH_DATA error: ORDERED_CASH_DATA.size != CASH_DATA_OBJECTS.size")
                            }
                            RE_LOAD_CASH_DATA(CASH_SUM).await()
                        }
                        var isUpdatedOrder = false

                        while (NEW_CASH_DATA.isNotEmpty()) {

                            val s = NEW_CASH_DATA.first()

                            if (lastSelect == s.LONG_20) {
                                NEW_CASH_DATA.removeFirst()
                            } else {
                                break
                            }

                            if (s.answerTypeValues.getIS_DELETE_RECORD() == "1") {
                                deletedCashData.addLast(s)
                            }

                            var s1 = CASH_DATA_OBJECTS[s.RECORD_TABLE_ID]

                            if (s1 != null) {

                                if (s1.INTEGER_20 != s.INTEGER_20) {

                                    isUpdatedOrder = true

                                    val sDel = ORDERED_CASH_DATA[s1.INTEGER_20!!]

                                    if (sDel.RECORD_TABLE_ID != s1.RECORD_TABLE_ID) {
                                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                            println("ADD_NEW_CASH_DATA error: sDel.RECORD_TABLE_ID != s1.RECORD_TABLE_ID;")
                                        }
                                    } else {
                                        ORDERED_CASH_DATA.removeAt(s1.INTEGER_20!!)

                                        s1.merge(s)

                                        if (s1.INTEGER_20 != 1 && ORDERED_CASH_DATA.size < (s1.INTEGER_20!! - 1)) {
                                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                                println("ADD_NEW_CASH_DATA error: ORDERED_CASH_DATA.size < (s.INTEGER_20;")
                                            }
                                            s1.INTEGER_20 = ORDERED_CASH_DATA.size
                                        }

                                        ORDERED_CASH_DATA.add(s1.INTEGER_20!!, s1)
                                    }
                                } else {
                                    s1.merge(s)
                                }

                                if (currentJob != null && currentJob.isActive) {
                                    currentJob.join()
                                }
                                currentJob = Sqlite_service.InsertCashData(s1)
                            } else {
                                if (CASH_DATA_OBJECTS.isNotEmpty() &&) {
                                    if ()
                                        isUpdatedOrder = true
                                }
                                CASH_DATA_OBJECTS[s.RECORD_TABLE_ID] = s
                                ORDERED_CASH_DATA.add(s.INTEGER_20!!, s)
                                if (currentJob != null && currentJob.isActive) {
                                    currentJob.join()
                                }
                                currentJob = Sqlite_service.InsertCashData(s)
                            }

                            if (s.answerTypeValues.getIS_DELETE_RECORD() == "1") {
                                deletedCashData.addLast(s)
                            }
                        }
                        if (isUpdatedOrder) {
                            if (currentJobOrder != null && currentJobOrder!!.isActive) {
                                currentJobOrder!!.join()
                            }
                            currentJobOrder = Sqlite_service.OrederCashData()
                        }

                        if (isUpdatedLastSelect) {
                            if (currentJobUpdateLastSelect != null && currentJobUpdateLastSelect!!.isActive) {
                                currentJobUpdateLastSelect!!.join()
                            }
                            currentJobUpdateLastSelect = Sqlite_service.CashDataUpdateLastSelect()
                        }

                        return@withTimeoutOrNull true
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
                return@withTimeoutOrNull false
            }
        }.toPromise(EmptyCoroutineContext)


    @JsName("UPDATE_LAST_SELECT")
    fun UPDATE_LAST_SELECT(lastSelect: Long, object_recod_id_from: String): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeout(Constants.CLIENT_TIMEOUT) {
                try {
                    val deletedCashData: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
                    try {
                        KCashDataLock.lock()

                        var currentJob: Job? = null

                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            println("ADD_NEW_CASH_DATA is running")
                        }
                        var isUpdatedOrder = false

                        var isUpdatedLastSelect = false

                        while (NEW_CASH_DATA.isNotEmpty()) {

                            isUpdatedLastSelect = true

                            val s = NEW_CASH_DATA.first()

                            if (lastSelect == s.LONG_20) {
                                NEW_CASH_DATA.removeFirst()
                            } else {
                                break
                            }

                            var s1 = CASH_DATA_OBJECTS[s.RECORD_TABLE_ID]

                            if (s1 != null) {

                                if (s1.INTEGER_20 != s.INTEGER_20) {
                                    isUpdatedOrder = true
                                    val sDel = ORDERED_CASH_DATA[s1.INTEGER_20!!]

                                    if (sDel.RECORD_TABLE_ID != s1.RECORD_TABLE_ID) {
                                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                            println("ADD_NEW_CASH_DATA error: sDel.RECORD_TABLE_ID != s1.RECORD_TABLE_ID;")
                                        }
                                    } else {
                                        ORDERED_CASH_DATA.removeAt(s1.INTEGER_20!!)
                                        s1.merge(s)
                                        ORDERED_CASH_DATA.add(s1.INTEGER_20!!, s1)
                                    }
                                } else {
                                    s1.merge(s)
                                }

                                if (currentJob != null && currentJob.isActive) {
                                    currentJob.join()
                                }
                                currentJob = Sqlite_service.InsertCashData(s1)
                            } else {
                                if (CASH_DATA_OBJECTS.isNotEmpty()) {
                                    isUpdatedOrder = true
                                }
                                CASH_DATA_OBJECTS[s.RECORD_TABLE_ID] = s
                                ORDERED_CASH_DATA.add(s.INTEGER_20!!, s)
                                if (currentJob != null && currentJob.isActive) {
                                    currentJob.join()
                                }
                                currentJob = Sqlite_service.InsertCashData(s)
                            }

                            if (s.answerTypeValues.getIS_DELETE_RECORD() == "1") {
                                deletedCashData.addLast(s)
                            }
                        }
                        if (isUpdatedOrder) {
                            if (currentJobOrder != null && currentJobOrder!!.isActive) {
                                currentJobOrder!!.join()
                            }
                            currentJobOrder = Sqlite_service.OrederCashData()
                        }

                        if (isUpdatedLastSelect) {
                            if (currentJobUpdateLastSelect != null && currentJobUpdateLastSelect!!.isActive) {
                                currentJobUpdateLastSelect!!.join()
                            }
                            currentJobUpdateLastSelect = Sqlite_service.CashDataUpdateLastSelect()
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


    companion object {

        private val KCashDatasLock = Mutex()

        @JsName("GET_CASH_DATA")
        suspend fun GET_CASH_DATA(checkSum: Long): KCashData? {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDatasLock.lock()
                        return@withTimeoutOrNull CASH_DATAS[checkSum] ?: KCashData(checkSum)

                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "GET_CASH_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KCashDatasLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            }
            return null
        }

        @JsName("LOAD_CASH_DATA")
        fun LOAD_CASH_DATA(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeout(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KCashDatasLock.lock()
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                println("LOAD_CASH_DATA is running")
                            }
                            TODO()
                            return@withTimeout true
                        } catch (e: my_user_exceptions_class) {
                            throw e
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KCashData",
                                l_function_name = "RE_LOAD_CASH_DATA",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KCashDatasLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeout false
                }
            }.toPromise(EmptyCoroutineContext)

    }
}