package Tables


import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Connection
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
class KCashData(lCASH_SUM: String) {

    val CASH_SUM: String = lCASH_SUM

    var OBJECT_ID: String = ""
    var LINK_OWNER: String = ""
    var MESS_COUNT_FROM: String = ""
    var RECORD_TYPE = ""
    var SORT: String = ""
    var OTHER_CONDITIONS_1: String = ""
    var OTHER_CONDITIONS_2: String = ""
    var OTHER_CONDITIONS_3: String = ""
    var COURSE: String = ""

    constructor(
        P_OBJECT_ID: String,
        P_LINK_OWNER: String = "",
        P_MESS_COUNT_FROM: String = "",
        P_RECORD_TYPE: String,
        P_SORT: String = "",
        P_OTHER_CONDITIONS_1: String = "",
        P_OTHER_CONDITIONS_2: String = "",
        P_OTHER_CONDITIONS_3: String = "",
        P_COURSE: String = "0"
    ) : this(
                P_OBJECT_ID +
                P_LINK_OWNER +
                P_MESS_COUNT_FROM +
                P_RECORD_TYPE +
                P_SORT +
                P_OTHER_CONDITIONS_1 +
                P_OTHER_CONDITIONS_2 +
                P_OTHER_CONDITIONS_3 +
                P_COURSE
    ) {
        OBJECT_ID = P_OBJECT_ID
        LINK_OWNER = P_LINK_OWNER
        MESS_COUNT_FROM = P_MESS_COUNT_FROM
        RECORD_TYPE = P_RECORD_TYPE
        SORT = P_SORT
        OTHER_CONDITIONS_1 = P_OTHER_CONDITIONS_1
        OTHER_CONDITIONS_2 = P_OTHER_CONDITIONS_2
        OTHER_CONDITIONS_3 = P_OTHER_CONDITIONS_3
        COURSE = P_COURSE
    }


    //val InstanceRef: AtomicReference<KCashData> = AtomicReference(this)

    private val KCashDataLock = Mutex()
    private val KCashDataLastUpdateLock = Mutex()

    val ORDERED_CASH_DATA: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

    val CASH_DATA_RECORDS: MutableMap<String, ANSWER_TYPE> = mutableMapOf()

    val REQUESTS: MutableMap<Long, KCashDataUpdateParameters> = mutableMapOf()

    var kCashLastUpdate :KCashLastUpdate? = CASH_LAST_UPDATE[CASH_SUM]

    var current_record_id = ""

    init {
        ensureNeverFrozen()
    }

    @JsName("ADD_NEW_CASH_DATA")
    fun ADD_NEW_CASH_DATA(
        arr: ArrayDeque<ANSWER_TYPE>,
        l_last_update: Long,
        l_just_do_it_label: Long,
        l_limit: String,
        l_count_of_all_records: String,
        l_number_of_block: String,
        l_object_id_from: String = "",
        l_mess_id_from: String = ""
    ): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDataLock.lock()
                        var kCashDataUpdateParameters = REQUESTS[l_just_do_it_label]
                        if (kCashDataUpdateParameters == null) {
                            kCashDataUpdateParameters = KCashDataUpdateParameters(
                                 last_update = l_last_update,
                                 limit = l_limit.toInt(),
                                 count_of_all_records = l_count_of_all_records.toInt()
                            )
                            REQUESTS[l_just_do_it_label] = kCashDataUpdateParameters
                        }

                        if(l_number_of_block == "1"){
                            when(kCashLastUpdate!!.RECORD_TYPE){
                                "4", "M" //MESSEGES
                                -> {
                                    kCashDataUpdateParameters.start_record_id = l_mess_id_from
                                }
                                "A", "C", "E", "G" //ALBUMS_COMMENTS, ALBUMS_LINKS_COMMENTS, OBJECTS_LINKS_COMMENTS, OBJECTS_COMMENTS
                                -> {
                                    kCashDataUpdateParameters.start_record_id = l_mess_id_from
                                }
                                else
                                ->{
                                    kCashDataUpdateParameters.start_record_id = l_object_id_from
                                }
                            }
                        }
                        arr.forEach {
                            if (!it.RECORD_TYPE.equals(kCashLastUpdate!!.RECORD_TYPE)) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KMetaData",
                                    l_function_name = "ADD_NEW_META_DATA",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "kCashLastUpdate!!.RECORD_TYPE ${kCashLastUpdate!!.RECORD_TYPE} is not equal adding reord type ${it.STRING_20.substring(7, 8)}"
                                )
                            }
                            var l = CASH_DATA_RECORDS[it.RECORD_TABLE_ID]
                            if(l != null){
                                if(l.INTEGER_20 != it.INTEGER_20){
                                    val n = ORDERED_CASH_DATA[(l.INTEGER_20.minus(1))]
                                    if(n.RECORD_TABLE_ID == it.RECORD_TABLE_ID){
                                        ORDERED_CASH_DATA.removeAt(l.INTEGER_20.minus(1))
                                    }
                                }
                                l.merge(it)
                            }else{
                                l = it
                                CASH_DATA_RECORDS[l.RECORD_TABLE_ID] = l
                            }
                            if(ORDERED_CASH_DATA.size < l.INTEGER_20){
                               l.INTEGER_20 = ORDERED_CASH_DATA.size.plus(1)
                            }
                            l.IS_UPDATED_BY_MERGE = true
                            ORDERED_CASH_DATA.add(l.INTEGER_20.minus(1), l)
                            kCashDataUpdateParameters.count_of_all_records--
                        }

                        if(kCashDataUpdateParameters.count_of_all_records == 0){
                            Connection.removeRequest(l_just_do_it_label)
                            REQUESTS.remove(l_just_do_it_label)
                            UPDATE_LAST_SELECT(lastSelect = kCashDataUpdateParameters.last_update,
                                               object_recod_id_from = kCashDataUpdateParameters.start_record_id)
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
            } ?: false
        }.toPromise(EmptyCoroutineContext)


    @JsName("UPDATE_LAST_SELECT")
    fun UPDATE_LAST_SELECT(lastSelect: Long,
                           object_recod_id_from: String,
                           kCashData: KCashData = this): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeout(Constants.CLIENT_TIMEOUT) {
                try {
                    val updatedCashData: MutableList<ANSWER_TYPE>?
                    try {
                        KCashDataLastUpdateLock.lock()

                        val l = CASH_DATA_RECORDS[object_recod_id_from]
                        if(l != null){
                            val n = ORDERED_CASH_DATA[l.INTEGER_20.minus(1)]
                            if(n.RECORD_TABLE_ID == l.RECORD_TABLE_ID){
                                updatedCashData = ORDERED_CASH_DATA.subList(l.INTEGER_20.minus(1), l.INTEGER_20.minus(1).plus(Constants.LIMIT_FOR_SELECT))
                                if(updatedCashData.size > 0){
                                    updatedCashData.forEach {
                                        it.LONG_20 = lastSelect
                                    }
                                    Sqlite_service.InsertCashData(updatedCashData)
                                    Sqlite_service.OrederCashData(kCashData.CASH_SUM)
                                }
                            }
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
                        KCashDataLastUpdateLock.unlock()
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
        suspend fun GET_CASH_DATA(
            L_OBJECT_ID: String,
            L_LINK_OWNER: String = "",
            L_MESS_COUNT_FROM: String = "",
            L_RECORD_TYPE: String,
            L_SORT: String = "",
            L_OTHER_CONDITIONS_1: String = "",
            L_OTHER_CONDITIONS_2: String = "",
            L_OTHER_CONDITIONS_3: String = "",
            L_COURSE: String = "0"
        ): KCashData? {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDatasLock.lock()
                        var position = 0
                        val cash =  L_OBJECT_ID +
                                    L_LINK_OWNER +
                                    L_MESS_COUNT_FROM +
                                    L_RECORD_TYPE + L_SORT +
                                    L_OTHER_CONDITIONS_1 +
                                    L_OTHER_CONDITIONS_2 +
                                    L_OTHER_CONDITIONS_3 +
                                    L_COURSE
                        var k = CASH_DATAS[cash]
                        if (k != null) {
                            if (k.RECORD_TYPE != L_RECORD_TYPE) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KCashData",
                                    l_function_name = "GET_CASH_DATA",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "RECORD_TYPE of CASH_DATA ${k.RECORD_TYPE} not equals RECORD_TYPE by select $L_RECORD_TYPE "
                                )
                            }
                            if (k.COURSE != L_COURSE) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KCashData",
                                    l_function_name = "GET_CASH_DATA",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "COURSE of CASH_DATA ${k.COURSE} not equals COURSE by select $L_COURSE "
                                )
                            }
                            k.OBJECT_ID = L_OBJECT_ID
                            k.LINK_OWNER = L_LINK_OWNER
                            k.MESS_COUNT_FROM = L_MESS_COUNT_FROM
                            k.RECORD_TYPE = L_RECORD_TYPE
                            k.SORT = L_SORT
                            k.OTHER_CONDITIONS_1 = L_OTHER_CONDITIONS_1
                            k.OTHER_CONDITIONS_2 = L_OTHER_CONDITIONS_2
                            k.OTHER_CONDITIONS_3 = L_OTHER_CONDITIONS_3
                            if(k.kCashLastUpdate == null){
                                k.kCashLastUpdate = CASH_LAST_UPDATE[k.CASH_SUM]
                            }
                            if(k.kCashLastUpdate == null){
                                k.kCashLastUpdate = KCashLastUpdate(k.CASH_SUM, k.RECORD_TYPE, L_COURSE)
                                CASH_LAST_UPDATE[k.CASH_SUM] = k.kCashLastUpdate!!
                            }
                            if (k.COURSE != k.kCashLastUpdate!!.COURSE) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KCashData",
                                    l_function_name = "GET_CASH_DATA",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "COURSE of CASH_DATA ${k.COURSE} not equals COURSE by CASH_LAST_UPDATE ${k.kCashLastUpdate!!.COURSE} "
                                )
                            }
                            k.kCashLastUpdate!!.INSERT_CASH_LASTUPDATE()
                            return@withTimeoutOrNull k
                        }

                        k = KCashData(cash)
                        k.OBJECT_ID = L_OBJECT_ID
                        k.LINK_OWNER = L_LINK_OWNER
                        k.MESS_COUNT_FROM = L_MESS_COUNT_FROM
                        k.RECORD_TYPE = L_RECORD_TYPE
                        k.SORT = L_SORT
                        k.OTHER_CONDITIONS_1 = L_OTHER_CONDITIONS_1
                        k.OTHER_CONDITIONS_2 = L_OTHER_CONDITIONS_2
                        k.OTHER_CONDITIONS_3 = L_OTHER_CONDITIONS_3
                        k.COURSE = L_COURSE
                        CASH_DATAS[cash] = k
                        if (Constants.IS_DOWNLOAD_TO_MEMORY_ALL_CASH_ON_CLIENT == 0 // not downloading
                            && CASH_LAST_UPDATE.containsKey(cash)  // and exists
                        ) {
                            val arr = Sqlite_service.SelectCashData(k.CASH_SUM)
                            if(arr.isNotEmpty()){
                                arr.forEach {
                                    it.answerTypeValues.DefineRECORD_TYPE()
                                    if (it.RECORD_TYPE != L_RECORD_TYPE) {
                                        throw my_user_exceptions_class(
                                            l_class_name = "KCashData",
                                            l_function_name = "GET_CASH_DATA",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "RECORD_TYPE of CASH_DATA ${it.RECORD_TYPE} not equals RECORD_TYPE by select $L_RECORD_TYPE "
                                        )
                                    }
                                    it.INTEGER_20 = position + 1
                                    k.ORDERED_CASH_DATA.add(it.INTEGER_20, it)
                                    k.CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                    position++
                                }
                            }
                        }
                        if(k.kCashLastUpdate == null){
                            k.kCashLastUpdate = CASH_LAST_UPDATE[k.CASH_SUM]
                        }
                        if(k.kCashLastUpdate == null){
                            k.kCashLastUpdate = KCashLastUpdate(k.CASH_SUM, k.RECORD_TYPE, L_COURSE)
                            CASH_LAST_UPDATE[k.CASH_SUM] = k.kCashLastUpdate!!
                        }
                        if (L_COURSE != k.kCashLastUpdate!!.COURSE) {
                            throw my_user_exceptions_class(
                                l_class_name = "KCashData",
                                l_function_name = "GET_CASH_DATA",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "COURSE of CASH_LAST_UPDATE ${k.kCashLastUpdate!!.COURSE} not equals COURSE by select $L_COURSE"
                            )
                        }
                        if (k.kCashLastUpdate!!.RECORD_TYPE != L_RECORD_TYPE) {
                            throw my_user_exceptions_class(
                                l_class_name = "KCashData",
                                l_function_name = "GET_CASH_DATA",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "RECORD_TYPE of CASH_LAST_UPDATE ${k.kCashLastUpdate!!.RECORD_TYPE} not equals RECORD_TYPE by select $L_RECORD_TYPE "
                            )
                        }
                        k.kCashLastUpdate!!.INSERT_CASH_LASTUPDATE()
                        return@withTimeoutOrNull k

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
        suspend fun LOAD_CASH_DATA(arr: ArrayList<ANSWER_TYPE>) {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDatasLock.lock()
                        var v: KCashData? = null
                        var position = 0
                        KCashLastUpdate.RE_LOAD_CASH_LAST_UPDATE().join()
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            println("LOAD_CASH_DATA is running")
                        }
                        arr.forEach {
                            it.answerTypeValues.setOBJECT_ID_LAST_SELECT()
                            if(v == null || v!!.CASH_SUM != it.CASH_SUM){
                                v = KCashData(it.CASH_SUM)
                                position = 0
                            }
                            if(v!!.kCashLastUpdate == null){
                                v!!.kCashLastUpdate = CASH_LAST_UPDATE[v!!.CASH_SUM]
                            }
                            if(v!!.kCashLastUpdate != null){
                                v!!.COURSE = v!!.kCashLastUpdate!!.COURSE
                            }
                            if(v!!.kCashLastUpdate != null
                                && v!!.kCashLastUpdate!!.RECORD_TYPE == it.RECORD_TYPE
                                && v!!.COURSE == v!!.kCashLastUpdate!!.COURSE){
                                it.INTEGER_20 = position + 1
                                v!!.ORDERED_CASH_DATA.add(it.INTEGER_20, it)
                                v!!.CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                position++
                            }
                        }
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
            }
        }

        @JsName("RE_LOAD_CASH_DATA")
        fun RE_LOAD_CASH_DATA(): Job {
            return Sqlite_service.LoadCashData()
        }

    }
}