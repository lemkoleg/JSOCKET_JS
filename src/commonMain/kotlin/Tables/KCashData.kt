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
import p_client.Jsocket
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
    var SORT: String = "0"
    var OTHER_CONDITIONS_1: String = ""
    var OTHER_CONDITIONS_2: String = ""
    var OTHER_CONDITIONS_3: String = ""
    var COURSE: String = "0"

    constructor(
        P_OBJECT_ID: String,
        P_LINK_OWNER: String = "",
        P_MESS_COUNT_FROM: String = "",
        P_RECORD_TYPE: String,
        P_SORT: String = "0",
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

    val ORDERED_CASH_DATA: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

    val CASH_DATA_RECORDS: MutableMap<String, ANSWER_TYPE> = mutableMapOf()

    val REQUESTS: MutableMap<Long, KCashDataUpdateParameters> = mutableMapOf()

    var kCashLastUpdate: KCashLastUpdate? = CASH_LAST_UPDATE[CASH_SUM]

    //var current_number_of_block = 0

    val currentViewCashData: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

    var currentViewCashDataRecordId: String = ""
    var currentViewCashDataRecordItem: Int = 0

    var updatedCashData: ((v: Any?) -> Any?) = {}

    var time_out_first_select = DateTime.nowUnixLong()

    var currentJobForGet: Job? = null

    var NextArrayLastSelect: String = ""

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
                    var kCashDataUpdateParameters: KCashDataUpdateParameters? = null
                    try {
                        KCashDataLock.lock()
                        kCashDataUpdateParameters = REQUESTS[l_just_do_it_label]
                        if (kCashDataUpdateParameters == null) {
                            kCashDataUpdateParameters = KCashDataUpdateParameters(
                                last_update = l_last_update,
                                limit = l_limit.toInt(),
                                count_of_all_records = l_count_of_all_records.toInt()
                            )
                            REQUESTS[l_just_do_it_label] = kCashDataUpdateParameters
                        }

                        if (l_number_of_block == "1") {
                            when (kCashLastUpdate!!.RECORD_TYPE) {
                                "4", "M" //MESSEGES
                                -> {
                                    kCashDataUpdateParameters.start_record_id = l_mess_id_from
                                }
                                "A", "C", "E", "G" //ALBUMS_COMMENTS, ALBUMS_LINKS_COMMENTS, OBJECTS_LINKS_COMMENTS, OBJECTS_COMMENTS
                                -> {
                                    kCashDataUpdateParameters.start_record_id = l_mess_id_from
                                }
                                else
                                -> {
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
                                    l_additional_text = "kCashLastUpdate!!.RECORD_TYPE ${kCashLastUpdate!!.RECORD_TYPE} is not equal adding reord type ${
                                        it.STRING_20.substring(
                                            7,
                                            8
                                        )
                                    }"
                                )
                            }
                            var l = CASH_DATA_RECORDS[it.RECORD_TABLE_ID]
                            if (l != null) {
                                if (l.INTEGER_20 != it.INTEGER_20) {
                                    val n = ORDERED_CASH_DATA.getOrNull(l.INTEGER_20.minus(1))?:
                                    throw my_user_exceptions_class(
                                        l_class_name = "KMetaData",
                                        l_function_name = "ADD_NEW_META_DATA",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "ORDERED_CASH_DATA item ${l.INTEGER_20.minus(1)} is null"
                                    )
                                    if (n.RECORD_TABLE_ID == it.RECORD_TABLE_ID) {
                                        ORDERED_CASH_DATA.removeAt(l.INTEGER_20.minus(1))
                                    }
                                }
                                l.merge(it)
                            } else {
                                l = it
                                CASH_DATA_RECORDS[l.RECORD_TABLE_ID] = l
                            }
                            if (ORDERED_CASH_DATA.size < l.INTEGER_20.minus(1)) {
                                l.INTEGER_20 = ORDERED_CASH_DATA.size.plus(1)
                            }
                            l.IS_UPDATED_BY_MERGE = true
                            ORDERED_CASH_DATA.add(l.INTEGER_20.minus(1), l)
                            kCashDataUpdateParameters.count_of_all_records--
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
                        if (Constants.CALL_UPDATED_CASH_DATA_FOR_EACH_INCOMING_BLOCK == 1) {
                            SetLastBlock()
                        } else {
                            if (kCashDataUpdateParameters!!.count_of_all_records == 0) {
                                SetLastBlock()
                            }
                        }
                        if (kCashDataUpdateParameters != null && kCashDataUpdateParameters.count_of_all_records == 0) {
                            Connection.removeRequest(l_just_do_it_label)
                            REQUESTS.remove(l_just_do_it_label)
                            UPDATE_LAST_SELECT(
                                lastSelect = kCashDataUpdateParameters.last_update,
                                object_recod_id_from = kCashDataUpdateParameters.start_record_id
                            )
                        }
                        KCashDataLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeoutOrNull false
            }?:throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "ADD_NEW_CASH_DATA",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }.toPromise(EmptyCoroutineContext)


    @JsName("UPDATE_LAST_SELECT")
    fun UPDATE_LAST_SELECT(
        lastSelect: Long,
        object_recod_id_from: String,
        kCashData: KCashData = this
    ): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    val arrUpdatedCashData: MutableList<ANSWER_TYPE>?
                    try {
                        KCashDataLock.lock()
                        var is_update_cash = false
                        val l = CASH_DATA_RECORDS[object_recod_id_from]
                        if (l != null) {
                            val n = ORDERED_CASH_DATA.getOrNull(l.INTEGER_20.minus(1))?:
                            throw my_user_exceptions_class(
                                l_class_name = "KMetaData",
                                l_function_name = "UPDATE_LAST_SELECT",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "ORDERED_CASH_DATA item ${l.INTEGER_20.minus(1)} is null"
                            )
                            if (n.RECORD_TABLE_ID == l.RECORD_TABLE_ID) {
                                val limit: Int =
                                    if (ORDERED_CASH_DATA.size < (l.INTEGER_20.plus(Constants.LIMIT_FOR_SELECT))) ORDERED_CASH_DATA.size
                                    else (l.INTEGER_20.plus(Constants.LIMIT_FOR_SELECT))

                                arrUpdatedCashData = ORDERED_CASH_DATA.subList(l.INTEGER_20, limit)

                                if (arrUpdatedCashData.size > 0) {
                                    arrUpdatedCashData.forEach {
                                        if (it.answerTypeValues.getIS_DELETE_RECORD()) {
                                            val k = CASH_DATA_RECORDS.remove(it.RECORD_TABLE_ID)
                                            if (k != null) {
                                                if (k.RECORD_TABLE_ID == ORDERED_CASH_DATA[k.INTEGER_20.minus(1)].RECORD_TABLE_ID) {
                                                    ORDERED_CASH_DATA.removeAt(k.INTEGER_20.minus(1))
                                                }
                                            }
                                        }
                                        it.LONG_20 = lastSelect
                                        it.answerTypeValues.setOBJECT_ID_LAST_SELECT()
                                    }
                                    when (kCashData.kCashLastUpdate!!.RECORD_TYPE) {
                                        "B", "D", "F", "H", "I" -> {
                                            if (l.INTEGER_20 <= Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS) {
                                                is_update_cash = true
                                            }
                                        }
                                        "J", "K", "L" -> {
                                            if (l.INTEGER_20 <= Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO) {
                                                is_update_cash = true
                                            }
                                        }
                                        "4", "A", "C", "E", "G" -> {
                                            if (l.INTEGER_20 <= Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS) {
                                                is_update_cash = true
                                            }
                                        }
                                    }
                                    if (is_update_cash) {
                                        Sqlite_service.InsertCashData(arrUpdatedCashData)
                                        //Sqlite_service.OrederCashData(kCashData.CASH_SUM)
                                    }
                                }
                            }else{
                                throw my_user_exceptions_class(
                                    l_class_name = "KMetaData",
                                    l_function_name = "UPDATE_LAST_SELECT",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "ORDERED_CASH_DATA.ECORD_TABLE_ID ${n.RECORD_TABLE_ID} not equal CASH_DATA_RECORDS.ECORD_TABLE_ID ${l.RECORD_TABLE_ID} "
                                )
                            }
                            kCashData.kCashLastUpdate!!.INSERT_CASH_LASTUPDATE()
                        }
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "ADD_NEW_CASH_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        if(currentViewCashData.size > 0){
                            val l = if (kCashLastUpdate!!.COURSE == "1") currentViewCashData.firstOrNull() else currentViewCashData.lastOrNull()
                            if(l != null){
                                val k = CASH_DATA_RECORDS[l.RECORD_TABLE_ID]
                                if(k != null){
                                    val n = ORDERED_CASH_DATA.getOrNull(k.INTEGER_20.minus(1))
                                        ?: throw my_user_exceptions_class(
                                            l_class_name = "KCashData",
                                            l_function_name = "ADD_NEW_CASH_DATA",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "ORDERED_CASH_DATA.RECORD_TABLE_ID ${k.INTEGER_20} is null"
                                        )
                                    if(n.RECORD_TABLE_ID != k.RECORD_TABLE_ID){
                                        throw my_user_exceptions_class(
                                            l_class_name = "KCashData",
                                            l_function_name = "ADD_NEW_CASH_DATA",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "ORDERED_CASH_DATA.RECORD_TABLE_ID ${n.RECORD_TABLE_ID} not equal CASH_DATA_RECORDS.RECORD_TABLE_ID ${k.RECORD_TABLE_ID}"
                                        )
                                    }
                                    NextArrayLastSelect = ""
                                    for(x in n.INTEGER_20..n.INTEGER_20.plus(Constants.LIMIT_FOR_SELECT)){
                                        if(ORDERED_CASH_DATA.size < x) break
                                        NextArrayLastSelect += ORDERED_CASH_DATA[x].OBJECT_ID_LAST_SELECT
                                    }
                                }
                            }
                        }
                        KCashDataLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeoutOrNull false
            }?:throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "UPDATE_LAST_SELECT",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }.toPromise(EmptyCoroutineContext)

    private fun Get(
        kCashData: KCashData = this,
        l_record_table_id_from: String = currentViewCashDataRecordId
    ): Promise<Unit?> = CoroutineScope(Dispatchers.Default).async {

        if (currentJobForGet != null && currentJobForGet!!.isActive) {
            currentJobForGet!!.join()
        }
        currentJobForGet = CoroutineScope(Dispatchers.Default).launch {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    val socket: Jsocket = Jsocket.GetJsocket() ?: Jsocket()
                    try {
                        socket.value_id4 = kCashData.OBJECT_ID
                        socket.value_id5 = kCashData.LINK_OWNER
                        socket.value_par1 = kCashData.MESS_COUNT_FROM
                        socket.value_par2 = kCashData.SORT
                        socket.value_par3 = kCashData.kCashLastUpdate!!.COURSE
                        socket.value_par4 = kCashData.OTHER_CONDITIONS_1  // mess count ofsset
                        socket.value_par5 = kCashData.OTHER_CONDITIONS_2
                        socket.value_par6 = kCashData.OTHER_CONDITIONS_3
                        socket.value_par9 = kCashData.NextArrayLastSelect
                        socket.check_sum = kCashData.CASH_SUM
                        when (kCashData.kCashLastUpdate!!.RECORD_TYPE) {
                            "B", "D", "F", "H", "I" -> {
                                socket.value_id1 = l_record_table_id_from
                            }
                            "J", "K", "L" -> {
                                socket.value_id1 = l_record_table_id_from
                            }
                            "4", "A", "C", "E", "G" -> {
                                socket.value_par4 = l_record_table_id_from
                            }
                        }
                    } catch (ex: Exception) {
                        Connection.removeRequest(socket.just_do_it_label)
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "ADD_NEW_CASH_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            }?:throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "Get",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }
    }.toPromise(EmptyCoroutineContext)


    private fun SetLastBlock() {
        try {

            if (ORDERED_CASH_DATA.size == 0) {
                currentViewCashData.clear()
                return
            }

            var offset = 0
            var limit: Int

            val l: ANSWER_TYPE?
            if (currentViewCashDataRecordId.isNotEmpty()) {
                if (kCashLastUpdate!!.COURSE == "1") {
                    l = currentViewCashData.getOrNull(currentViewCashData.size.minus(currentViewCashData.size.minus(currentViewCashDataRecordItem)).minus(1))?:
                            throw my_user_exceptions_class(
                                l_class_name = "KCashData",
                                l_function_name = "SetLastBlock",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "currentViewCashData is null with item: ${currentViewCashDataRecordItem.minus(1)}"
                            )
                }else{
                    l = currentViewCashData.getOrNull(currentViewCashDataRecordItem.minus(1))?:
                            throw my_user_exceptions_class(
                                l_class_name = "KCashData",
                                l_function_name = "SetLastBlock",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "currentViewCashData is null with item: ${currentViewCashDataRecordItem.minus(1)}"
                            )
                }

                if (l.RECORD_TABLE_ID != currentViewCashDataRecordId) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashData",
                        l_function_name = "SetLastBlock",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "l.RECORD_TABLE_ID: ${l.RECORD_TABLE_ID} is not equal currentViewCashDataRecordId: $currentViewCashDataRecordId"
                    )
                }
                offset = CASH_DATA_RECORDS[currentViewCashDataRecordId]!!.INTEGER_20
            }

            if (offset >= ORDERED_CASH_DATA.size) {
                throw my_user_exceptions_class(
                    l_class_name = "KCashData",
                    l_function_name = "SetLastBlock",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "offset: $offset more then ORDERED_CASH_DATA.size: ${ORDERED_CASH_DATA.size}"
                )
            }

            limit = offset.plus(Constants.LIMIT_FOR_SELECT)

            if (limit > ORDERED_CASH_DATA.size) {
                limit = ORDERED_CASH_DATA.size
            }
            if(limit == offset) return

            val cash = ORDERED_CASH_DATA.subList(offset, limit)

            if(cash.isEmpty()) return

            if (kCashLastUpdate!!.COURSE == "1") {
                val v = currentViewCashData.subList(currentViewCashData.size.minus(currentViewCashDataRecordItem).minus(1),currentViewCashData.size)
                currentViewCashData.clear()
                currentViewCashData.addAll(v)
                if (currentViewCashData.first().RECORD_TABLE_ID != currentViewCashDataRecordId) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashData",
                        l_function_name = "SetLastBlock",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "currentViewCashData.first().RECORD_TABLE_ID: ${currentViewCashData.first().RECORD_TABLE_ID} is not equal currentViewCashDataRecordId: $currentViewCashDataRecordId"
                    )
                }
                val r = ArrayDeque<ANSWER_TYPE>()
                r.addAll(cash.asReversed())
                r.addAll(currentViewCashData)
                currentViewCashData.clear()
                currentViewCashData.addAll(r)
            }else{
                val v = currentViewCashData.subList(0, currentViewCashDataRecordItem)
                currentViewCashData.clear()
                currentViewCashData.addAll(v)
                if (currentViewCashData.last().RECORD_TABLE_ID != currentViewCashDataRecordId) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashData",
                        l_function_name = "SetLastBlock",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "currentViewCashData.last().RECORD_TABLE_ID: ${currentViewCashData.last().RECORD_TABLE_ID} is not equal currentViewCashDataRecordId: $currentViewCashDataRecordId"
                    )
                }
                currentViewCashData.addAll(cash)
            }

        } finally {
            updatedCashData(null)
        }
    }


    fun GetNext(): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDataLock.lock()

                        if (currentJobForGet != null && currentJobForGet!!.isActive) {
                            currentJobForGet!!.join()
                        }
                        var l: ANSWER_TYPE? = null
                        if (!currentViewCashData.isEmpty()) {
                            l = if (kCashLastUpdate!!.COURSE == "1") {
                                currentViewCashData.first()
                            } else {
                                currentViewCashData.last()
                            }
                        }
                        if (l != null) {
                            currentViewCashDataRecordId = l.RECORD_TABLE_ID
                            currentViewCashDataRecordItem = currentViewCashData.size
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
                        Get()
                        SetLastBlock()
                        KCashDataLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeoutOrNull mutableListOf<ANSWER_TYPE>()
            }?:throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "GetNext",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
            return@async false
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
            L_COURSE: String = "0",
            l_updatedCashData: ((v: Any?) -> Any?)? = null
        ): KCashData? {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    var k: KCashData? = null
                    try {
                        KCashDatasLock.lock()
                        val cash = L_OBJECT_ID +
                                L_LINK_OWNER +
                                L_MESS_COUNT_FROM +
                                L_RECORD_TYPE + L_SORT +
                                L_OTHER_CONDITIONS_1 +
                                L_OTHER_CONDITIONS_2 +
                                L_OTHER_CONDITIONS_3 +
                                L_COURSE
                        k = CASH_DATAS[cash]
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
                            if (k.kCashLastUpdate == null) {
                                k.kCashLastUpdate = CASH_LAST_UPDATE[k.CASH_SUM]
                            }
                            if (k.kCashLastUpdate == null) {
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
                            k.NextArrayLastSelect = ""
                            for(x in 0..Constants.LIMIT_FOR_SELECT){
                                if(k.ORDERED_CASH_DATA.size >= x) break
                                k.NextArrayLastSelect = k.ORDERED_CASH_DATA[x].OBJECT_ID_LAST_SELECT
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
                        k.NextArrayLastSelect = ""
                        CASH_DATAS[cash] = k
                        if (Constants.IS_DOWNLOAD_TO_MEMORY_ALL_CASH_ON_CLIENT == 0 // not downloading
                            && CASH_LAST_UPDATE.containsKey(cash)  // and exists
                        ) {
                            val arr = Sqlite_service.SelectCashData(k.CASH_SUM)
                            if (arr.isNotEmpty()) {
                                arr.forEach {
                                    it.answerTypeValues.setOBJECT_ID_LAST_SELECT()
                                    if (it.RECORD_TYPE != L_RECORD_TYPE) {
                                        throw my_user_exceptions_class(
                                            l_class_name = "KCashData",
                                            l_function_name = "GET_CASH_DATA",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "RECORD_TYPE of CASH_DATA ${it.RECORD_TYPE} not equals RECORD_TYPE by select $L_RECORD_TYPE "
                                        )
                                    }
                                    k.ORDERED_CASH_DATA.addLast(it)
                                    it.INTEGER_20 = (k.ORDERED_CASH_DATA.size)
                                    k.CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                    if(it.INTEGER_20 < Constants.LIMIT_FOR_SELECT){
                                        k.NextArrayLastSelect = k.NextArrayLastSelect + it.OBJECT_ID_LAST_SELECT
                                    }
                                }
                            }
                        }
                        if (k.kCashLastUpdate == null) {
                            k.kCashLastUpdate = CASH_LAST_UPDATE[k.CASH_SUM]
                        }
                        if (k.kCashLastUpdate == null) {
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
                        k.updatedCashData = l_updatedCashData ?: k.updatedCashData
                        return@withTimeoutOrNull k

                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "GET_CASH_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {

                        if (k != null) {
                            k.currentViewCashData.clear()
                            k.currentViewCashDataRecordId = ""
                            k.currentViewCashDataRecordItem = 0
                            if (k.time_out_first_select <= DateTime.nowUnixLong()) {
                                k.GetNext()
                            }
                        }
                        KCashDatasLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            }?:throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "GET_CASH_DATA",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
            return null
        }

        @JsName("LOAD_CASH_DATA")
        suspend fun LOAD_CASH_DATA(arr: ArrayDeque<ANSWER_TYPE>) {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDatasLock.lock()
                        var v: KCashData? = null
                        KCashLastUpdate.RE_LOAD_CASH_LAST_UPDATE().join()
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            println("LOAD_CASH_DATA is running")
                        }
                        arr.forEach {
                            it.answerTypeValues.setOBJECT_ID_LAST_SELECT()
                            if (v == null || v!!.CASH_SUM != it.CASH_SUM) {
                                v = KCashData(it.CASH_SUM)
                            }
                            if (v!!.kCashLastUpdate == null) {
                                v!!.kCashLastUpdate = CASH_LAST_UPDATE[v!!.CASH_SUM]
                            }
                            if (v!!.kCashLastUpdate != null) {
                                v!!.COURSE = v!!.kCashLastUpdate!!.COURSE
                            }
                            if (v!!.kCashLastUpdate != null
                                && v!!.kCashLastUpdate!!.RECORD_TYPE == it.RECORD_TYPE
                                && v!!.COURSE == v!!.kCashLastUpdate!!.COURSE
                            ) {
                                v!!.ORDERED_CASH_DATA.addLast(it)
                                it.INTEGER_20 = v!!.ORDERED_CASH_DATA.size
                                v!!.CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                if(it.INTEGER_20 < Constants.LIMIT_FOR_SELECT){
                                    v!!.NextArrayLastSelect = v!!.NextArrayLastSelect + it.OBJECT_ID_LAST_SELECT
                                }
                            }
                        }
                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "LOAD_CASH_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KCashDatasLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            }?:throw my_user_exceptions_class(
                    l_class_name = "KCashData",
                    l_function_name = "LOAD_CASH_DATA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
            )
        }

        @JsName("RE_LOAD_CASH_DATA")
        fun RE_LOAD_CASH_DATA(): Job {
            return Sqlite_service.LoadCashData()
        }

    }
}