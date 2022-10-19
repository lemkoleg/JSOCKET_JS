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
@JsName("CASH_DATAS")
val CASH_DATAS: MutableMap<String, KCashData> = mutableMapOf()


@JsName("KCashData")
@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
class KCashData(lCASH_SUM: String) {

    val CASH_SUM: String = lCASH_SUM
    var count_of_cashing_records = 0
    var OBJECT_ID: String = ""
    var LINK_OWNER: String = ""
    var MESS_COUNT_FROM: String = ""
    var RECORD_TYPE = ""
        set(v) {
            if (field != v) {
                when (v) {
                    "B", "D", "F", "H", "I" -> {
                        count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS
                    }
                    "J", "K", "L" -> {
                        count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO
                    }
                    "A", "C", "E", "G", "M" -> {
                        count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS
                    }
                    "3" -> {
                        count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_CHATS
                    }
                    "4" -> {  // MESSEGESS;
                        count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_MESSEGES
                    }
                    "8", "9" -> { // CHATS_LIKES, CHATS_COST_TYPES;
                        count_of_cashing_records = 1000000
                    }
                }
            }
            field = v
        }
    var SORT: String = "0"
    var OTHER_CONDITIONS_1: String = ""
    var OTHER_CONDITIONS_2: String = ""
    var OTHER_CONDITIONS_3: String = ""
    var COURSE: String = "0"

    constructor(
        lCASH_SUM: String,
        L_OBJECT_ID: String,
        L_RECORD_TYPE: String,
        L_COURSE: String,
        arr: ArrayDeque<ANSWER_TYPE>
    ) : this(lCASH_SUM){

        this.OBJECT_ID = L_OBJECT_ID
        this.RECORD_TYPE = L_RECORD_TYPE
        this.COURSE = L_COURSE
        if (arr.isNotEmpty()) {
            arr.forEach {
                it.answerTypeValues.setOBJECT_ID_LAST_SELECT()
                if (it.RECORD_TYPE == "8" && it.answerTypeValues.GetMainAccountId() == Account_Id) { //CHATS_LIKES;

                    CoroutineScope(Dispatchers.Default).launch {
                        KChat.globalLastUpdatingDate.setGreaterValue(it.answerTypeValues.GetRecordLastUpdate())
                    }
                }
                CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
            }
            ORDERED_CASH_DATA.addAll(arr)
        }
    }

    /*
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
    */


    //val InstanceRef: AtomicReference<KCashData> = AtomicReference(this)

    private val KCashDataLock = Mutex()

    @JsName("ORDERED_CASH_DATA")
    val ORDERED_CASH_DATA: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

    @JsName("CASH_DATA_RECORDS")
    val CASH_DATA_RECORDS: MutableMap<String, ANSWER_TYPE> = mutableMapOf()

    @JsName("REQUESTS")
    val REQUESTS: MutableMap<Long, KCashDataUpdateParameters> = mutableMapOf()

    var kCashLastUpdate: KCashLastUpdate? = CASH_LAST_UPDATE[CASH_SUM]

    var currentViewCashData: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

    var currentViewCashDataRecord: ANSWER_TYPE? = null
    //var currentViewCashDataRecordId: String = ""
    //var currentViewCashDataRecordItem: Int = 0

    var callBackUpdatedCashData: ((v: Any?) -> Any?) = {}

    var currentJobForGet: Job? = null

    //var NextArrayLastSelect: String = ""

    init {
        ensureNeverFrozen()
    }

    @JsName("IS_HAVE_RECORD")
    fun IS_HAVE_RECORD(id: String): Boolean {
        return CASH_DATA_RECORDS.containsKey(id)
    }

    @JsName("GET_RECORDS")
    suspend fun GET_RECORD(id: String): ANSWER_TYPE? {
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            try {
                try {
                    KCashDataLock.lock()
                    var v = CASH_DATA_RECORDS[id]
                    if (v == null) {
                        v = Sqlite_service.SelectCashData(CASH_SUM, id)
                    }
                    return@withTimeoutOrNull v
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
        } ?: throw my_user_exceptions_class(
            l_class_name = "KCashData",
            l_function_name = "GET_RECORD",
            name_of_exception = "EXC_SYSTEM_ERROR",
            l_additional_text = "Time out is up"
        )
        return null
    }

    @JsName("SET_RECORDS")
    suspend fun SET_RECORDS(arr: ArrayDeque<ANSWER_TYPE>) {
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            try {
                try {
                    KCashDataLock.lock()
                    arr.forEach {

                        if (!it.RECORD_TYPE.equals(kCashLastUpdate!!.RECORD_TYPE)) {
                            throw my_user_exceptions_class(
                                l_class_name = "KMetaData",
                                l_function_name = "SET_RECORDSA",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "kCashLastUpdate!!.RECORD_TYPE ${kCashLastUpdate!!.RECORD_TYPE} is not equal adding reord type ${
                                    it.STRING_20.substring(
                                        7,
                                        8
                                    )
                                }"
                            )
                        }

                        if (it.INTEGER_20 <= ORDERED_CASH_DATA.size) {
                            if (CASH_DATA_RECORDS.containsKey(it.RECORD_TABLE_ID)) {
                                val index = ORDERED_CASH_DATA.indexOf(it).plus(1)
                                if (index == 0) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "KMetaData",
                                        l_function_name = "SET_RECORDS",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "ORDERED_CASH_DATA item ${it.INTEGER_20.minus(1)} is null"
                                    )
                                }

                                if (index != it.INTEGER_20) {
                                    ORDERED_CASH_DATA.remove(it)
                                    if (ORDERED_CASH_DATA.size < it.INTEGER_20.minus(1)) {
                                        it.INTEGER_20 = ORDERED_CASH_DATA.size.plus(1)
                                    }
                                    ORDERED_CASH_DATA.add(it.INTEGER_20.minus(1), it)
                                }
                            } else {
                                CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                ORDERED_CASH_DATA.add(it.INTEGER_20.minus(1), it)
                            }
                        }
                    }
                    Sqlite_service.InsertCashData(arr)

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
        } ?: throw my_user_exceptions_class(
            l_class_name = "KCashData",
            l_function_name = "SET_RECORD",
            name_of_exception = "EXC_SYSTEM_ERROR",
            l_additional_text = "Time out is up"
        )
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
                    val chenged_records: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
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
                                l.merge(it)
                                val index = ORDERED_CASH_DATA.indexOf(l).plus(1)
                                if (index == 0) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "KMetaData",
                                        l_function_name = "ADD_NEW_META_DATA",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "ORDERED_CASH_DATA item ${l.INTEGER_20.minus(1)} is null"
                                    )
                                }


                                if (index != l.INTEGER_20) {
                                    ORDERED_CASH_DATA.remove(l)
                                    if (ORDERED_CASH_DATA.size < l.INTEGER_20.minus(1)) {
                                        l.INTEGER_20 = ORDERED_CASH_DATA.size.plus(1)
                                    }
                                    ORDERED_CASH_DATA.add(l.INTEGER_20.minus(1), l)
                                }

                            } else {
                                l = it
                                CASH_DATA_RECORDS[l.RECORD_TABLE_ID] = l
                                if (ORDERED_CASH_DATA.size < l.INTEGER_20.minus(1)) {
                                    l.INTEGER_20 = ORDERED_CASH_DATA.size.plus(1)
                                }
                                ORDERED_CASH_DATA.add(l.INTEGER_20.minus(1), l)
                            }

                            chenged_records.addLast(l)

                            l.IS_UPDATED_BY_MERGE = true

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
                        if (chenged_records.isNotEmpty()) {
                            Sqlite_service.InsertCashData(chenged_records)
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
            } ?: throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "ADD_NEW_CASH_DATA",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }.toPromise(EmptyCoroutineContext)


    @JsName("UPDATE_LAST_SELECT")
    fun UPDATE_LAST_SELECT(
        lastSelect: Long,
        object_recod_id_from: String
    ): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDataLock.lock()

                        var index =
                            if (object_recod_id_from.isEmpty()) 0 else ORDERED_CASH_DATA.indexOf(CASH_DATA_RECORDS[object_recod_id_from])
                        if (index == -1) {
                            if (object_recod_id_from.isEmpty()) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KCashData",
                                    l_function_name = "UPDATE_LAST_SELECT",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "index == -1"
                                )
                            }
                        }
                        if (index > 0) {
                            index++
                        }
                        if (index <= count_of_cashing_records) {
                            Sqlite_service.UpdateCashDataNewLastSelect(lastSelect, CASH_SUM, object_recod_id_from)
                        }

                        val limit = minOf(Constants.LIMIT_FOR_SELECT.plus(index), ORDERED_CASH_DATA.size)
                        ORDERED_CASH_DATA.subList(index, limit).forEach {
                            it.LONG_20 = lastSelect
                            it.answerTypeValues.setOBJECT_ID_LAST_SELECT()
                        }

                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "UPDATE_LAST_SELECT",
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
            } ?: throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "UPDATE_LAST_SELECT",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }.toPromise(EmptyCoroutineContext)

    private fun Get(
        kCashData: KCashData = this,
        lastSelectObjectId: String,
        l_record_table_id: ANSWER_TYPE? = currentViewCashDataRecord
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
                        socket.check_sum = kCashData.CASH_SUM
                        socket.value_par9 = lastSelectObjectId
                        if (l_record_table_id != null) {
                            when (kCashData.kCashLastUpdate!!.RECORD_TYPE) {
                                "B", "D", "F", "H", "I" -> {
                                    socket.value_id1 = l_record_table_id.RECORD_TABLE_ID
                                }
                                "J", "K", "L" -> {
                                    socket.value_id1 = l_record_table_id.RECORD_TABLE_ID
                                }
                                "4", "A", "C", "E", "G" -> {
                                    socket.value_par4 = l_record_table_id.RECORD_TABLE_ID
                                }
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
            } ?: throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "Get",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }
    }.toPromise(EmptyCoroutineContext)


    private fun SetLastBlock(): ArrayDeque<ANSWER_TYPE> {
        val l_currentViewCashData: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
        val result: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
        try {

            if (ORDERED_CASH_DATA.size == 0) {
                currentViewCashData.clear()
                return result
            }

            if (ORDERED_CASH_DATA.size <= Constants.LIMIT_FOR_SELECT) {
                currentViewCashData.clear()
                if (kCashLastUpdate!!.COURSE == "1") {
                    result.addAll(ORDERED_CASH_DATA.reversed())
                } else {
                    result.addAll(ORDERED_CASH_DATA)
                }
                currentViewCashData.addAll(result)
                return result
            }


            val offset: Int
            val index: Int
            var limit: Int

            if (currentViewCashDataRecord != null) {

                index = currentViewCashData.indexOf(currentViewCashDataRecord!!).plus(1)
                offset = ORDERED_CASH_DATA.indexOf(currentViewCashDataRecord!!).plus(1)

                if (index == -1) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashData",
                        l_function_name = "SetLastBlock",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "currentViewCashData is null with item: ${
                            currentViewCashDataRecord!!.INTEGER_20
                        }"
                    )
                }

                if (offset == -1) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashData",
                        l_function_name = "SetLastBlock",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "ORDERED_CASH_DATA is null with item: ${
                            currentViewCashDataRecord!!.INTEGER_20
                        }"
                    )
                }
            } else {
                index = Constants.LIMIT_FOR_SELECT
                offset = Constants.LIMIT_FOR_SELECT
            }

            if (kCashLastUpdate!!.COURSE == "1") {
                l_currentViewCashData.addAll(
                    currentViewCashData.reversed().subList(0, minOf(index, currentViewCashData.size))
                )
            } else {
                l_currentViewCashData.addAll(currentViewCashData.subList(0, minOf(index, currentViewCashData.size)))
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
            if (limit == offset) return result

            val cash = ORDERED_CASH_DATA.subList(offset, limit)

            if (cash.isEmpty()) return result

            l_currentViewCashData.addAll(cash)

            currentViewCashData.clear()

            if (kCashLastUpdate!!.COURSE == "1") {
                currentViewCashData.addAll(l_currentViewCashData.reversed())
                result.addAll(cash.reversed())
            } else {
                currentViewCashData.addAll(l_currentViewCashData)
                result.addAll(cash)
            }
            cash.clear()
            return result
        } finally {
            l_currentViewCashData.clear()
            callBackUpdatedCashData(null)
        }
    }


    fun GetNext(): Promise<ArrayDeque<ANSWER_TYPE>> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDataLock.lock()

                        if (currentJobForGet != null && currentJobForGet!!.isActive) {
                            currentJobForGet!!.join()
                        }
                        if (!currentViewCashData.isEmpty()) {
                            currentViewCashDataRecord = if (kCashLastUpdate!!.COURSE == "1") {
                                currentViewCashData.firstOrNull()
                            } else {
                                currentViewCashData.lastOrNull()
                            }
                        }

                        val arr = Sqlite_service.SelectCashDataChunkOnCashSum(
                            CASH_SUM,
                            currentViewCashDataRecord?.RECORD_TABLE_ID ?: ""
                        )
                        var minLastSelect = 99999999999999L
                        var lastSelectObjectId = ""
                        if (arr.isNotEmpty()) {
                            arr.forEach {
                                it.answerTypeValues.setOBJECT_ID_LAST_SELECT()
                                lastSelectObjectId += it.OBJECT_ID_LAST_SELECT
                                minLastSelect = minOf(it.LONG_20, minLastSelect)
                                if (!CASH_DATA_RECORDS.containsKey(it.RECORD_TABLE_ID)) {
                                    if (it.RECORD_TYPE != RECORD_TYPE) {
                                        throw my_user_exceptions_class(
                                            l_class_name = "KCashData",
                                            l_function_name = "GetNext",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "RECORD_TYPE of CASH_DATA ${it.RECORD_TYPE} not equals RECORD_TYPE by select $RECORD_TYPE "
                                        )
                                    }
                                    CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                    ORDERED_CASH_DATA.addLast(it)
                                }
                            }
                            return@withTimeoutOrNull SetLastBlock()
                        }

                        if (minLastSelect == 99999999999999L) {
                            minLastSelect = 0
                        }

                        if (minLastSelect.plus(Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < (DateTime.nowUnixLong())) {
                            Get(lastSelectObjectId = lastSelectObjectId)
                        }
                        return@withTimeoutOrNull ArrayDeque<ANSWER_TYPE>()

                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "GetNext",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KCashDataLock.unlock()
                    }

                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeoutOrNull mutableListOf<ANSWER_TYPE>()
            } ?: throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "GetNext",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
            return@async ArrayDeque<ANSWER_TYPE>()
        }.toPromise(EmptyCoroutineContext)


    companion object {

        private val KCashDatasLock = Mutex()

        @JsName("GET_CASH_DATA")
        fun GET_CASH_DATA(
            L_OBJECT_ID: String,
            L_LINK_OWNER: String = "",
            L_MESS_COUNT_FROM: String = "",
            L_RECORD_TYPE: String,
            L_SORT: String = "",
            L_OTHER_CONDITIONS_1: String = "",
            L_OTHER_CONDITIONS_2: String = "",
            L_OTHER_CONDITIONS_3: String = "",
            L_COURSE: String,
            l_updatedCashData: ((v: Any?) -> Any?)? = null,
            l_request_updates: Boolean = true,
            l_select_all_records: Boolean = false,
            l_is_SetLastBlock: Boolean = true
        ): Promise<KCashData> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        var k: KCashData? = null
                        var minLastSelect = 99999999999999L
                        var lastSelectObjectId = ""
                        try {
                            KCashDatasLock.lock()
                            val cash = L_OBJECT_ID +
                                    L_LINK_OWNER +
                                    L_RECORD_TYPE +
                                    L_COURSE +
                                    L_MESS_COUNT_FROM +
                                    L_SORT +
                                    L_OTHER_CONDITIONS_1 +
                                    L_OTHER_CONDITIONS_2 +
                                    L_OTHER_CONDITIONS_3
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

                                if (k.ORDERED_CASH_DATA.size > Constants.LIMIT_FOR_SELECT) {
                                    val v = k.ORDERED_CASH_DATA.subList(0, Constants.LIMIT_FOR_SELECT)
                                    k.ORDERED_CASH_DATA.clear()
                                    k.ORDERED_CASH_DATA.addAll(v)
                                    k.CASH_DATA_RECORDS.clear()
                                    k.ORDERED_CASH_DATA.forEach {
                                        k!!.CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                    }
                                }

                                if (l_request_updates) {
                                    k.ORDERED_CASH_DATA.forEach {
                                        minLastSelect = minOf(it.LONG_20, minLastSelect)
                                        lastSelectObjectId += it.OBJECT_ID_LAST_SELECT
                                    }
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

                                if (k.RECORD_TYPE == "4") { // MASSEGES;
                                    if (CHATS!!.CASH_DATA_RECORDS[(L_OBJECT_ID + "3" + "0")]?.answerTypeValues!!.GetChatsCountNotReadedMess() > 0L) {
                                        CoroutineScope(Dispatchers.Default).launch {
                                            val w = CHATS!!.CASH_DATA_RECORDS[(L_OBJECT_ID + "3" + "0")]?.GetJsocket()
                                            if (w != null) {
                                                w.value_par1 =
                                                    k!!.ORDERED_CASH_DATA.firstOrNull()?.answerTypeValues!!.GetMessegeId()
                                                        .toString()
                                                w.send_request()
                                            }
                                        }
                                    }
                                }
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
                                val arr: ArrayDeque<ANSWER_TYPE> = if (l_select_all_records) {
                                    Sqlite_service.SelectCashDataAllOnCashSum(k.CASH_SUM)
                                } else {
                                    Sqlite_service.SelectCashDataChunkOnCashSum(k.CASH_SUM)
                                }

                                if (arr.isNotEmpty()) {
                                    arr.forEach {
                                        it.answerTypeValues.setOBJECT_ID_LAST_SELECT()
                                        minLastSelect = minOf(it.LONG_20, minLastSelect)
                                        lastSelectObjectId += it.OBJECT_ID_LAST_SELECT
                                        if (it.RECORD_TYPE != L_RECORD_TYPE) {
                                            throw my_user_exceptions_class(
                                                l_class_name = "KCashData",
                                                l_function_name = "GET_CASH_DATA",
                                                name_of_exception = "EXC_SYSTEM_ERROR",
                                                l_additional_text = "RECORD_TYPE of CASH_DATA ${it.RECORD_TYPE} not equals RECORD_TYPE by select $L_RECORD_TYPE "
                                            )
                                        }
                                        if (it.RECORD_TYPE == "3") { // CHATS;

                                            val chats_likes_cash_sum = it.answerTypeValues.GetChatId() + "8" + "0"

                                            val arr_chats_likes: ArrayDeque<ANSWER_TYPE> =
                                                Sqlite_service.SelectCashDataAllOnCashSum(chats_likes_cash_sum)
                                            val chats_likes_KCashData = KCashData(
                                                lCASH_SUM = chats_likes_cash_sum,
                                                L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                                L_RECORD_TYPE = "8",
                                                L_COURSE = "0",
                                                arr = arr_chats_likes)

                                            CASH_DATAS[it.answerTypeValues.GetChatId()] = chats_likes_KCashData

                                            if (arr_chats_likes.isEmpty()) {
                                                val w = it.GetJsocket()
                                                w.value_par1 = "8"
                                                w.just_do_it = 1011000052 // SELECTOR.SELECT_ALL_DATA_ON_OBJECT;
                                                w.send_request()
                                            }

                                            val chats_cost_types_cash_sum = it.answerTypeValues.GetChatId() + "9" + "0"

                                            val arr_chats_cost_types_likes: ArrayDeque<ANSWER_TYPE> =
                                                Sqlite_service.SelectCashDataAllOnCashSum(chats_cost_types_cash_sum)
                                            val chats_cost_types_KCashData = KCashData(
                                                lCASH_SUM = chats_cost_types_cash_sum,
                                                L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                                L_RECORD_TYPE = "9",
                                                L_COURSE = "0",
                                                arr = arr_chats_cost_types_likes)

                                            CASH_DATAS[it.answerTypeValues.GetChatId()] = chats_cost_types_KCashData

                                            if (arr_chats_cost_types_likes.isEmpty()) {
                                                val w = it.GetJsocket()
                                                w.value_par1 = "9"
                                                w.just_do_it = 1011000052 // SELECTOR.SELECT_ALL_DATA_ON_OBJECT;
                                                w.send_request()
                                            }

                                            val messeges_cash_sum = it.answerTypeValues.GetChatId() + "4" + "1"

                                            val arr_messeges: ArrayDeque<ANSWER_TYPE> =
                                                Sqlite_service.SelectCashDataChunkOnCashSum(messeges_cash_sum)
                                            val messeges_KCashData = KCashData(
                                                lCASH_SUM = messeges_cash_sum,
                                                L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                                L_RECORD_TYPE = "4",
                                                L_COURSE = "1",
                                                arr = arr_messeges)

                                            CASH_DATAS[it.answerTypeValues.GetChatId()] = messeges_KCashData

                                        }
                                        if (it.RECORD_TYPE == "8" && it.answerTypeValues.GetMainAccountId() == Account_Id) { //CHATS_LIKES;

                                            KChat.globalLastUpdatingDate.setGreaterValue(it.answerTypeValues.GetRecordLastUpdate())
                                        }
                                        k.CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                    }
                                    k.ORDERED_CASH_DATA.addAll(arr)
                                }
                            }

                            if (k.RECORD_TYPE == "4") { // MASSEGES;
                                if (CHATS!!.CASH_DATA_RECORDS[(L_OBJECT_ID + "3" + "0")]?.answerTypeValues!!.GetChatsCountNotReadedMess() > 0L) {
                                    CoroutineScope(Dispatchers.Default).launch {
                                        val w = CHATS!!.CASH_DATA_RECORDS[(L_OBJECT_ID + "3" + "0")]?.GetJsocket()
                                        if (w != null) {
                                            w.value_par1 =
                                                k.ORDERED_CASH_DATA.firstOrNull()?.answerTypeValues!!.GetMessegeId()
                                                    .toString()
                                            w.send_request()
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
                                k.callBackUpdatedCashData = l_updatedCashData ?: { }
                                k.currentViewCashData.clear()
                                k.currentViewCashDataRecord = null
                                if (l_is_SetLastBlock) {
                                    k.SetLastBlock()
                                }
                                if (minLastSelect == 99999999999999L) {
                                    minLastSelect = 0
                                }

                                if (l_request_updates) {
                                    if (minLastSelect.plus(Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < (DateTime.nowUnixLong())) {
                                        k.Get(lastSelectObjectId = lastSelectObjectId)
                                    }
                                }
                            }
                            KCashDatasLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "KCashData",
                    l_function_name = "GET_CASH_DATA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
                throw my_user_exceptions_class(
                    l_class_name = "KCashData",
                    l_function_name = "GET_CASH_DATA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }.toPromise(EmptyCoroutineContext)

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
            } ?: throw my_user_exceptions_class(
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