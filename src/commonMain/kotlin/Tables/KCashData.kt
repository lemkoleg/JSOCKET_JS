@file:Suppress("UNUSED_VARIABLE")

package Tables


import CrossPlatforms.PrintInformation
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_client.Jsocket
import p_jsocket.ANSWER_TYPE
import p_jsocket.Connection
import p_jsocket.Constants
import p_jsocket.Constants.Account_Id
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
class KCashData(lCashLastUpdate: KCashLastUpdate) {

    val CashLastUpdate = lCashLastUpdate

    var count_of_cashing_records = 0

    var l_just_do_it = 0

    init {
        when (CashLastUpdate.RECORD_TYPE) {
            "3"  //CHATS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_CHATS
                l_just_do_it = 1011000053
            }

            "4" //MESSEGES
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_MESSEGES
                if (CashLastUpdate.COURSE == "1"
                    && CashLastUpdate.SORT == "0"
                    && CashLastUpdate.OTHER_CONDITIONS_1.isEmpty()
                    && CashLastUpdate.OTHER_CONDITIONS_2.isEmpty()
                    && CashLastUpdate.OTHER_CONDITIONS_3.isEmpty()
                ) {
                    l_just_do_it = 1011000051 //SELECT_MESSEGESS_STANDART;
                }
            }

            "8",  //CHATS_LIKES
            -> {
                count_of_cashing_records = 1999999999
            }

            "9" //CHATS_COST_TYPES
            -> {
                count_of_cashing_records = 1999999999
            }

            "A" //ALBUMS_COMMENTS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS
            }

            "B" //ALBUMS_LINKS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS
            }

            "C" //ALBUMS_LINKS_COMMENTS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS
            }

            "D" //OBJECTS_LINKS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS
            }

            "E" //OBJECTS_LINKS_COMMENTS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS
            }

            "F" //OBJECTS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS
            }

            "G" //OBJECTS_COMMENTS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS
            }

            "H" //ACCOUNTS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS
            }

            "I" //ALBUMS
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS
            }

            "J" //ACCOUNT_INFO;
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO
            }

            "K" //ALBUM_INFO;
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO
            }

            "L" //OBJECT_INFO;
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO
            }

            "M" //MESSEGES FOR NOT STANDART SELECT;
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS
            }

            "N" //NOTICES;
            -> {
                count_of_cashing_records = Constants.MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS
            }

            else
            -> {
                throw my_user_exceptions_class(
                    l_class_name = "KCAshData",
                    l_function_name = "Constructor",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Record not defined"
                )
            }
        }
        if (!CASH_DATAS.containsKey(CashLastUpdate.CASH_SUM)) {
            CASH_DATAS[CashLastUpdate.CASH_SUM] = this
        }
    }

    constructor(
        L_CashLastUpdate: KCashLastUpdate,
        arr: ArrayDeque<ANSWER_TYPE>
    ) : this(L_CashLastUpdate) {

        if (arr.isNotEmpty()) {
            arr.forEach {
                CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
            }
            ORDERED_CASH_DATA.addAll(arr)
        }
    }


    //val InstanceRef: AtomicReference<KCashData> = AtomicReference(this)

    val KCashDataLock = Mutex()

    @JsName("ORDERED_CASH_DATA")
    val ORDERED_CASH_DATA: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

    @JsName("CASH_DATA_RECORDS")
    val CASH_DATA_RECORDS: MutableMap<String, ANSWER_TYPE> = mutableMapOf()

    @JsName("REQUESTS")
    val REQUESTS: MutableMap<Long, KCashDataUpdateParameters> = mutableMapOf()

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

    @JsName("GET_RECORDS")
    suspend fun GET_RECORD(id: String): ANSWER_TYPE? {
        var res: ANSWER_TYPE? = null
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            try {
                try {
                    KCashDataLock.withLock {
                        res = CASH_DATA_RECORDS[id]
                        if (res == null) {
                            res = Sqlite_service.SelectCashData(CashLastUpdate.CASH_SUM, id)
                        }
                        return@withTimeoutOrNull res
                    }
                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashData",
                        l_function_name = "ADD_NEW_CASH_DATA",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
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
        return res
    }

    @JsName("SET_RECORDS")
    suspend fun SET_RECORDS(arr: ArrayDeque<ANSWER_TYPE>) {
        KCashDataLock.withLock {
            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                PrintInformation.PRINT_INFO("Start KCashData.SET_RECORDS; arr.size = ${arr.size}")
            }
            val chenged_records: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
            try {
                arr.forEach {

                    if (!it.RECORD_TYPE.equals(CashLastUpdate.RECORD_TYPE)) {
                        println("it.RECORD_TYPE = ${it.RECORD_TYPE} ; CashLastUpdate.RECORD_TYPE = ${CashLastUpdate.RECORD_TYPE}")
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "SET_RECORDS",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "CashLastUpdate.RECORD_TYPE ${CashLastUpdate.RECORD_TYPE} is not equal adding reord type ${
                                it.STRING_20.substring(
                                    7,
                                    8
                                )
                            }"
                        )
                    }


                    if (it.RECORD_TYPE.equals("8")) {  // CHATS_LIKES;
                        if (it.answerTypeValues.GetMainAccountId().equals(Account_Id)) {
                            globalLastChatsSelect.setGreaterValue(it.answerTypeValues.GetRecordLastUpdate())
                        }
                    }

                    var t = CASH_DATA_RECORDS[it.RECORD_TABLE_ID]

                    if (t != null) {
                        t.merge(it)
                        val index = ORDERED_CASH_DATA.indexOf(t).plus(1)
                        if (index == 0) {
                            throw my_user_exceptions_class(
                                l_class_name = "KMetaData",
                                l_function_name = "SET_RECORDS",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "ORDERED_CASH_DATA1 item ${it.INTEGER_20.minus(1)} is null"
                            )
                        }

                        if (index != t.INTEGER_20) {
                            ORDERED_CASH_DATA.remove(t)
                            if (ORDERED_CASH_DATA.size < t.INTEGER_20.minus(1)) {
                                t.INTEGER_20 = ORDERED_CASH_DATA.size.plus(1)
                            }
                            ORDERED_CASH_DATA.add(t.INTEGER_20.minus(1), t)
                        }

                    } else {
                        t = it
                        CASH_DATA_RECORDS[t.RECORD_TABLE_ID] = t
                        if (ORDERED_CASH_DATA.size.plus(1) < it.INTEGER_20) {
                            t.INTEGER_20 = ORDERED_CASH_DATA.size.plus(1)
                        }
                        ORDERED_CASH_DATA.add(it.INTEGER_20.minus(1), it)
                    }

                    it.IS_UPDATED_BY_MERGE = true

                    if (it.INTEGER_20 <= count_of_cashing_records) {
                        chenged_records.addLast(it)
                    }
                    println("chenged_records.size = ${chenged_records.size}; count_of_cashing_records = $count_of_cashing_records")

                    if (chenged_records.isNotEmpty()) {
                        Sqlite_service.InsertCashData(CashLastUpdate.CASH_SUM, chenged_records)
                        CashLastUpdate.INSERT_CASH_LASTUPDATE()
                    }
                }
            } catch (e: my_user_exceptions_class) {
                throw e
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "KCashData",
                    l_function_name = "SET_RECORDS",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.stackTraceToString()
                )
            }
        }
    }


    @JsName("ADD_NEW_CASH_DATA")
    fun ADD_NEW_CASH_DATA(
        arr: ArrayDeque<ANSWER_TYPE>,
        l_last_select: Long,
        l_just_do_it_label: Long,
        l_limit: String,
        l_count_of_all_records: String,
        l_number_of_block: String,
        l_object_id_from: String = "",
        l_mess_id_from: String = ""
    ): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    KCashDataLock.withLock {

                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            PrintInformation.PRINT_INFO("Start KCashData.ADD_NEW_CASH_DATA")
                        }

                        var kCashDataUpdateParameters: KCashDataUpdateParameters? = null
                        val chenged_records: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
                        var alien_cash_last_update: KCashLastUpdate? = null
                        var alien_records: ArrayDeque<ANSWER_TYPE>? = null
                        try {

                            kCashDataUpdateParameters = REQUESTS[l_just_do_it_label]
                            if (kCashDataUpdateParameters == null) {
                                kCashDataUpdateParameters = KCashDataUpdateParameters(
                                    last_update = l_last_select,
                                    limit = l_limit.toInt(),
                                    count_of_all_records = l_count_of_all_records.toInt()
                                )
                                REQUESTS[l_just_do_it_label] = kCashDataUpdateParameters
                            }

                            if (l_number_of_block == "1") {
                                when (CashLastUpdate.RECORD_TYPE) {
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
                            arr.forEach lit@{
                                if (!it.RECORD_TYPE.equals(CashLastUpdate.RECORD_TYPE)) {
                                    if (CashLastUpdate.RECORD_TYPE == "3") {  // CHATS;
                                        val cash_sum: String
                                        when (it.RECORD_TYPE) {
                                            "4" -> {  // MESSEGES;
                                                cash_sum = GetCashSum(
                                                    L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                                    L_RECORD_TYPE = it.RECORD_TYPE,
                                                    L_COURSE = "1"
                                                )
                                            }

                                            "8", "9" -> {  // CHATS_LIKES, CHATS_COST_TYPES;
                                                cash_sum = GetCashSum(
                                                    L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                                    L_RECORD_TYPE = it.RECORD_TYPE,
                                                    L_COURSE = "0"
                                                )
                                            }

                                            "N" -> {  // NOTICES;
                                                cash_sum = GetCashSum(
                                                    L_OBJECT_ID = Constants.Account_Id,
                                                    L_RECORD_TYPE = it.RECORD_TYPE,
                                                    L_COURSE = "0"
                                                )
                                            }

                                            else -> {
                                                throw my_user_exceptions_class(
                                                    l_class_name = "KCashData",
                                                    l_function_name = "ADD_NEW_META_DATA",
                                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                                    l_additional_text = "CashLastUpdate.RECORD_TYPE ${CashLastUpdate.RECORD_TYPE} is not equal adding reord type ${
                                                        it.STRING_20.substring(
                                                            7,
                                                            8
                                                        )
                                                    }"
                                                )
                                            }
                                        }

                                        if (alien_cash_last_update == null || alien_cash_last_update!!.CASH_SUM != cash_sum) {
                                            if (alien_cash_last_update != null) {
                                                var cc = CASH_DATAS[alien_cash_last_update!!.CASH_SUM]
                                                if (cc == null) {
                                                    cc = KCashData(alien_cash_last_update!!)
                                                }
                                                cc.SET_RECORDS(alien_records!!)
                                            }
                                            alien_cash_last_update = CASH_LAST_UPDATE[cash_sum]
                                            if (alien_cash_last_update == null) {
                                                when (it.RECORD_TYPE) {
                                                    "4" -> {  // MESSEGES;
                                                        alien_cash_last_update = KCashLastUpdate(
                                                            L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                                            L_RECORD_TYPE = it.RECORD_TYPE,
                                                            L_COURSE = "1"
                                                        )
                                                    }

                                                    "8", "9" -> {  // CHATS_LIKES, CHATS_COST_TYPES, NOTICES;
                                                        alien_cash_last_update = KCashLastUpdate(
                                                            L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                                            L_RECORD_TYPE = it.RECORD_TYPE,
                                                            L_COURSE = "0"
                                                        )
                                                    }

                                                    "N" -> {  // NOTICES;
                                                        alien_cash_last_update = KCashLastUpdate(
                                                            L_OBJECT_ID = Constants.Account_Id,
                                                            L_RECORD_TYPE = it.RECORD_TYPE,
                                                            L_COURSE = "0"
                                                        )
                                                    }
                                                }
                                            }
                                            CASH_LAST_UPDATE[cash_sum] = alien_cash_last_update!!
                                            alien_records = ArrayDeque()
                                        }

                                        alien_records!!.addLast(it)
                                        kCashDataUpdateParameters.count_of_all_records--

                                        return@lit

                                    } else {
                                        throw my_user_exceptions_class(
                                            l_class_name = "KCashData",
                                            l_function_name = "ADD_NEW_META_DATA",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "CashLastUpdate.RECORD_TYPE ${CashLastUpdate.RECORD_TYPE} is not equal adding reord type ${
                                                it.STRING_20.substring(
                                                    7,
                                                    8
                                                )
                                            }"
                                        )
                                    }
                                }

                                if (it.RECORD_TYPE.equals("8")) {  // CHATS_LIKES;
                                    if (it.answerTypeValues.GetMainAccountId().equals(Account_Id)) {
                                        globalLastChatsSelect.setGreaterValue(it.answerTypeValues.GetRecordLastUpdate())
                                    }
                                }

                                var l = CASH_DATA_RECORDS[it.RECORD_TABLE_ID]

                                if (l != null) {
                                    l.merge(it)
                                    val index = ORDERED_CASH_DATA.indexOf(l).plus(1)
                                    if (index == 0) {
                                        throw my_user_exceptions_class(
                                            l_class_name = "KCashData",
                                            l_function_name = "ADD_NEW_META_DATA",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "ORDERED_CASH_DATA2 item ${l.INTEGER_20.minus(1)} is null"
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
                                    if (CashLastUpdate.RECORD_TYPE == "3") {
                                        SELECT_ALL_DATA_OF_CHAT(it.answerTypeValues.GetChatId())
                                    }
                                }

                                if (l.INTEGER_20 <= count_of_cashing_records) {
                                    chenged_records.addLast(l)
                                }

                                l.IS_UPDATED_BY_MERGE = true

                                kCashDataUpdateParameters.count_of_all_records--
                            }
                            return@withTimeoutOrNull true
                        } catch (e: my_user_exceptions_class) {
                            kCashDataUpdateParameters?.have_errors = true
                            throw e
                        } catch (ex: Exception) {
                            kCashDataUpdateParameters?.have_errors = true
                            throw my_user_exceptions_class(
                                l_class_name = "KCashData",
                                l_function_name = "ADD_NEW_CASH_DATA",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.stackTraceToString()
                            )
                        } finally {
                            if (alien_cash_last_update != null && alien_records != null && alien_records!!.isNotEmpty()) {
                                var cc2 = CASH_DATAS[alien_cash_last_update!!.CASH_SUM]
                                if (cc2 == null) {
                                    cc2 = KCashData(alien_cash_last_update!!)
                                }
                                cc2.SET_RECORDS(alien_records!!)
                            }

                            if (Constants.CALL_UPDATED_CASH_DATA_FOR_EACH_INCOMING_BLOCK == 1) {
                                SetLastBlock()
                            } else {
                                if (kCashDataUpdateParameters!!.count_of_all_records == 0) {
                                    SetLastBlock()
                                }
                            }
                            if (chenged_records.isNotEmpty()) {
                                Sqlite_service.InsertCashData(CashLastUpdate.CASH_SUM, chenged_records)
                            }
                            if (kCashDataUpdateParameters != null && kCashDataUpdateParameters.count_of_all_records == 0) {
                                Connection.removeRequest(l_just_do_it_label)
                                REQUESTS.remove(l_just_do_it_label)
                                if (!kCashDataUpdateParameters.have_errors) {
                                    println("ADD NEW CASH start UPDATE_LAST_SELECT")
                                    UPDATE_LAST_SELECT(
                                        lastSelect = kCashDataUpdateParameters.last_update,
                                        object_recod_id_from = kCashDataUpdateParameters.start_record_id
                                    )
                                }
                            }
                        }
                    }
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashData",
                        l_function_name = "ADD_NEW_CASH_DATA",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
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
        object_recod_id_from: String,
        udpdate_all: Boolean = false
    ): Promise<Boolean> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDataLock.withLock {
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                PrintInformation.PRINT_INFO("Start KCashData.UPDATE_LAST_SELECT, lastSelect = $lastSelect ; object_recod_id_from = $object_recod_id_from ; udpdate_all = $udpdate_all")
                            }

                            var index =
                                if (object_recod_id_from.isEmpty()) 0 else ORDERED_CASH_DATA.indexOf(
                                    CASH_DATA_RECORDS[object_recod_id_from]
                                )
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

                            Sqlite_service.UpdateCashDataNewLastSelect(
                                last_select = lastSelect,
                                cash_sum = CashLastUpdate.CASH_SUM,
                                record_table_id_from = if (udpdate_all) "" else object_recod_id_from,
                                limit = if (udpdate_all) 1999999999 else Constants.LIMIT_FOR_SELECT
                            )


                            var limit = minOf(Constants.LIMIT_FOR_SELECT.plus(index), ORDERED_CASH_DATA.size)

                            if (udpdate_all) {// CHATS_COST_TYPES;
                                index = 0
                                limit = CASH_DATA_RECORDS.size
                            }

                            ORDERED_CASH_DATA.subList(index, limit).forEach {


                                if (CashLastUpdate.RECORD_TYPE == "3") {  // chats;
                                    val chats_likes_last_update = GetCashSum(
                                        L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                        L_RECORD_TYPE = "8"
                                    )
                                    println("UPDATE_LAST_SELECT1 start UPDATE_LAST_SELECT")
                                    CASH_DATAS[chats_likes_last_update]?.UPDATE_LAST_SELECT(
                                        lastSelect = lastSelect,
                                        object_recod_id_from = "",
                                        udpdate_all = true
                                    )


                                    val chats_cost_types_last_update = GetCashSum(
                                        L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                        L_RECORD_TYPE = "9",
                                        L_COURSE = "0"
                                    )
                                    println("UPDATE_LAST_SELECT2 start UPDATE_LAST_SELECT")
                                    CASH_DATAS[chats_cost_types_last_update]?.UPDATE_LAST_SELECT(
                                        lastSelect = lastSelect,
                                        object_recod_id_from = "",
                                        udpdate_all = true
                                    )

                                    val messeges_last_update = GetCashSum(
                                        L_OBJECT_ID = it.answerTypeValues.GetChatId(),
                                        L_RECORD_TYPE = "4",
                                        L_COURSE = "1"
                                    )
                                    println("UPDATE_LAST_SELECT3 start UPDATE_LAST_SELECT")
                                    CASH_DATAS[messeges_last_update]?.UPDATE_LAST_SELECT(
                                        lastSelect = lastSelect,
                                        object_recod_id_from = "",
                                        udpdate_all = false
                                    )
                                }
                            }

                            if (CashLastUpdate.RECORD_TYPE == "N" && object_recod_id_from.isEmpty()) {
                                val notices_last_update = GetCashSum(
                                    L_OBJECT_ID = Constants.Account_Id,
                                    L_RECORD_TYPE = "R",
                                    L_COURSE = "0"
                                )
                                println("UPDATE_LAST_SELECT4 start UPDATE_LAST_SELECT")
                                CASH_DATAS[notices_last_update]?.UPDATE_LAST_SELECT(
                                    lastSelect = lastSelect,
                                    object_recod_id_from = "",
                                    udpdate_all = false
                                )
                            }
                        }
                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "UPDATE_LAST_SELECT",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
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


    @JsName("DELETE")
    suspend fun DELETE(object_id: String) {
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            try {
                try {
                    KCashDataLock.withLock {
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            PrintInformation.PRINT_INFO("KCashData.DELETE is run")
                        }

                        val cash = CASH_DATA_RECORDS.remove(object_id)

                        if (cash != null) {
                            ORDERED_CASH_DATA.remove(cash)
                        }

                        Sqlite_service.DeleteCash(CashLastUpdate.CASH_SUM, object_id)

                        if (CashLastUpdate.RECORD_TYPE == "3") { // chats
                            val chats_likes_last_update = GetCashSum(
                                L_OBJECT_ID = object_id,
                                L_RECORD_TYPE = "8"
                            )
                            Sqlite_service.DeleteCash(chats_likes_last_update)
                            CASH_DATAS.remove(chats_likes_last_update)
                            CASH_LAST_UPDATE.remove(chats_likes_last_update)


                            val chats_cost_types_last_update = GetCashSum(
                                L_OBJECT_ID = object_id,
                                L_RECORD_TYPE = "9",
                                L_COURSE = "0"
                            )
                            Sqlite_service.DeleteCash(chats_cost_types_last_update)
                            CASH_DATAS.remove(chats_cost_types_last_update)
                            CASH_LAST_UPDATE.remove(chats_cost_types_last_update)


                            val messeges_last_update = GetCashSum(
                                L_OBJECT_ID = object_id,
                                L_RECORD_TYPE = "4",
                                L_COURSE = "1"
                            )
                            Sqlite_service.DeleteCash(messeges_last_update)
                            CASH_DATAS.remove(messeges_last_update)
                            CASH_LAST_UPDATE.remove(messeges_last_update)
                        }
                    }
                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCashData",
                        l_function_name = "DELETE",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
            return@withTimeoutOrNull false
        } ?: throw my_user_exceptions_class(
            l_class_name = "KCashData",
            l_function_name = "DELETE",
            name_of_exception = "EXC_SYSTEM_ERROR",
            l_additional_text = "Time out is up"
        )
    }


    fun VerifyFirsBlock() {
        Get(l_record_table_id_from = "")
    }

    private fun Get(
        l_record_table_id_from: String
    ): Promise<Unit?> = CoroutineScope(Dispatchers.Default + SupervisorJob()).async {

        if (currentJobForGet != null && currentJobForGet!!.isActive) {
            currentJobForGet!!.join()
        }
        currentJobForGet = CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    val socket: Jsocket = Jsocket.GetJsocket() ?: Jsocket()
                    val record = CASH_DATA_RECORDS[l_record_table_id_from]
                    var RecordIdLastSelect = ""
                    var index = 0
                    if (record != null) {
                        index = ORDERED_CASH_DATA.indexOf(record).plus(1)
                        if (index == 0) {
                            throw my_user_exceptions_class(
                                l_class_name = "KCashData",
                                l_function_name = "Get",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "indes = -1"
                            )
                        }

                    }

                    val sub = ORDERED_CASH_DATA.subList(
                        index,
                        minOf(index.plus(Constants.LIMIT_FOR_SELECT), ORDERED_CASH_DATA.size)
                    )
                    sub.forEach {
                        RecordIdLastSelect += it.answerTypeValues.setOBJECT_ID_LAST_SELECT()
                    }

                    try {
                        socket.value_id4 = CashLastUpdate.OBJECT_ID
                        socket.value_id5 = CashLastUpdate.LINK_OWNER
                        socket.value_par1 = CashLastUpdate.MESS_COUNT_FROM
                        socket.value_par2 = CashLastUpdate.SORT
                        socket.value_par3 = CashLastUpdate.COURSE
                        socket.value_par4 = CashLastUpdate.OTHER_CONDITIONS_1  // mess count ofsset
                        socket.value_par5 = CashLastUpdate.OTHER_CONDITIONS_2
                        socket.value_par6 = CashLastUpdate.OTHER_CONDITIONS_3
                        socket.check_sum = CashLastUpdate.CASH_SUM
                        socket.value_par9 = RecordIdLastSelect
                        socket.just_do_it = l_just_do_it
                        if (record != null) {
                            when (CashLastUpdate.RECORD_TYPE) {
                                "B", "D", "F", "H", "I" -> {
                                    socket.value_id1 = record.RECORD_TABLE_ID
                                }

                                "J", "K", "L" -> {
                                    socket.value_id1 = record.RECORD_TABLE_ID
                                }

                                "4", "A", "C", "E", "G" -> {
                                    socket.value_par4 = record.RECORD_TABLE_ID
                                }
                            }
                        }
                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        Connection.removeRequest(socket.just_do_it_label)
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "Get",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
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
                if (CashLastUpdate.COURSE == "1") {
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

            if (CashLastUpdate.COURSE == "1") {
                l_currentViewCashData.addAll(
                    currentViewCashData.reversed().subList(0, minOf(index, currentViewCashData.size))
                )
            } else {
                l_currentViewCashData.addAll(
                    currentViewCashData.subList(
                        0,
                        minOf(index, currentViewCashData.size)
                    )
                )
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

            if (CashLastUpdate.COURSE == "1") {
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
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            var arr: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDataLock.withLock {
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                PrintInformation.PRINT_INFO("KCashData.GetNext is run")
                            }
                            if (currentJobForGet != null && currentJobForGet!!.isActive) {
                                currentJobForGet!!.join()
                            }
                            if (!currentViewCashData.isEmpty()) {
                                currentViewCashDataRecord = if (CashLastUpdate.COURSE == "1") {
                                    currentViewCashData.firstOrNull()
                                } else {
                                    currentViewCashData.lastOrNull()
                                }
                            }

                            arr =
                                if (CashLastUpdate.RECORD_TYPE == "8"      // CHATS_LIKES;
                                    || CashLastUpdate.RECORD_TYPE == "9"   // CHATS_COST_TYPES;
                                ) ORDERED_CASH_DATA
                                else Sqlite_service.SelectCashDataChunkOnCashSum(
                                    CashLastUpdate.CASH_SUM,
                                    currentViewCashDataRecord?.RECORD_TABLE_ID ?: ""
                                )

                            var minLastSelect = 99999999999999L
                            if (arr.isNotEmpty()) {
                                if (CashLastUpdate.RECORD_TYPE != "8"      // CHATS_LIKES;
                                    && CashLastUpdate.RECORD_TYPE != "9"   // CHATS_COST_TYPES;
                                ) {
                                    arr.forEach {

                                        minLastSelect = minOf(it.LONG_20, minLastSelect)

                                        if (it.RECORD_TYPE != CashLastUpdate.RECORD_TYPE) {
                                            throw my_user_exceptions_class(
                                                l_class_name = "KCashData",
                                                l_function_name = "GetNext",
                                                name_of_exception = "EXC_SYSTEM_ERROR",
                                                l_additional_text = "RECORD_TYPE of CASH_DATA ${it.RECORD_TYPE} not equals RECORD_TYPE by select ${CashLastUpdate.RECORD_TYPE} "
                                            )
                                        }

                                        if (!CASH_DATA_RECORDS.containsKey(it.RECORD_TABLE_ID)) {
                                            CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                            ORDERED_CASH_DATA.addLast(it)

                                            if (it.RECORD_TYPE == "3") { // CHATS;
                                                SELECT_ALL_DATA_OF_CHAT(it.answerTypeValues.GetChatId())
                                            }
                                        }
                                    }
                                }
                                arr = SetLastBlock()
                                return@withTimeoutOrNull arr
                            }

                            if (minLastSelect == 99999999999999L) {
                                minLastSelect = 0
                            }

                            when (CashLastUpdate.RECORD_TYPE) {
                                "3", "4", "8", "9", "N" -> {
                                    Get(l_record_table_id_from = currentViewCashDataRecord?.RECORD_TABLE_ID ?: "")
                                }

                                else -> {
                                    if (minLastSelect.plus(Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < (DateTime.nowUnixMillisLong())) {
                                        Get(
                                            l_record_table_id_from = currentViewCashDataRecord?.RECORD_TABLE_ID
                                                ?: ""
                                        )
                                    }
                                }
                            }
                            return@withTimeoutOrNull arr
                        }
                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "GetNext",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeoutOrNull arr
            } ?: throw my_user_exceptions_class(
                l_class_name = "KCashData",
                l_function_name = "GetNext",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
            return@async arr
        }.toPromise(EmptyCoroutineContext)

    suspend fun SELECT_ALL_DATA_OF_CHAT(chat_id: String) {

        val chats_cash_sum = GetCashSum(
            L_OBJECT_ID = Account_Id,
            L_RECORD_TYPE = "3"
        )

        val chats_likes_last_update_sum = GetCashSum(
            L_OBJECT_ID = chat_id,
            L_RECORD_TYPE = "8"
        )

        val chats_likes_last_update = CASH_LAST_UPDATE[chats_likes_last_update_sum] ?: KCashLastUpdate(
            L_OBJECT_ID = chat_id,
            L_RECORD_TYPE = "8"
        )

        var chats_likes_KCashData = CASH_DATAS[chats_likes_last_update.CASH_SUM]
        val arr_chats_likes: ArrayDeque<ANSWER_TYPE> =
            chats_likes_KCashData?.ORDERED_CASH_DATA ?: Sqlite_service.SelectCashDataAllOnCashSum(
                chats_likes_last_update.CASH_SUM
            )

        if (chats_likes_KCashData == null) {
            chats_likes_KCashData = KCashData(
                L_CashLastUpdate = chats_likes_last_update,
                arr = arr_chats_likes
            )
        }

        if (arr_chats_likes.size != CASH_DATAS[chats_cash_sum]!!.CASH_DATA_RECORDS[chat_id]!!.answerTypeValues.GetChatsCountOfAllMembers()) {
            println("chats_likes: ${arr_chats_likes.size} not equal count of chats likes ${CASH_DATAS[chats_cash_sum]!!.CASH_DATA_RECORDS[chat_id]!!.answerTypeValues.GetChatsCountOfAllMembers()}")
            KChat.SELECT_ALL_DATA_ON_CHAT(chat_id)
            return
        }

        globalLastChatsSelect.setGreaterValue(chats_likes_KCashData.CASH_DATA_RECORDS[Account_Id]!!.answerTypeValues.GetRecordLastUpdate())

        val chats_cost_types_last_update_name = GetCashSum(
            L_OBJECT_ID = chat_id,
            L_RECORD_TYPE = "9"
        )
        val chats_cost_types_last_update = CASH_LAST_UPDATE[chats_cost_types_last_update_name] ?: KCashLastUpdate(
            L_OBJECT_ID = chat_id,
            L_RECORD_TYPE = "9"
        )

        val chats_cost_types_KCashData = CASH_DATAS[chats_cost_types_last_update.CASH_SUM]

        val arr_chats_cost_types: ArrayDeque<ANSWER_TYPE> =
            chats_cost_types_KCashData?.ORDERED_CASH_DATA ?: Sqlite_service.SelectCashDataAllOnCashSum(
                chats_cost_types_last_update.CASH_SUM
            )

        if (chats_cost_types_KCashData == null) {
            KCashData(
                L_CashLastUpdate = chats_cost_types_last_update,
                arr = arr_chats_cost_types
            )
        }

        if (arr_chats_cost_types.size != CASH_DATAS[chats_cash_sum]!!.CASH_DATA_RECORDS[chat_id]!!.answerTypeValues.GetChatsCountOfConstsType()) {
            println("chats_cost_types: ${arr_chats_cost_types.size} not equal count of chats cost types ${CASH_DATAS[chats_cash_sum]!!.CASH_DATA_RECORDS[chat_id]!!.answerTypeValues.GetChatsCountOfConstsType()}")
            KChat.SELECT_ALL_DATA_ON_CHAT(chat_id)
            return
        }

        val messeges_last_update_name = GetCashSum(
            L_OBJECT_ID = chat_id,
            L_RECORD_TYPE = "4",
            L_COURSE = "1"
        )
        val messeges_last_update = CASH_LAST_UPDATE[messeges_last_update_name] ?: KCashLastUpdate(
            L_OBJECT_ID = chat_id,
            L_RECORD_TYPE = "4",
            L_COURSE = "1"
        )

        var messeges_KCashData = CASH_DATAS[messeges_last_update.CASH_SUM]

        val arr_messeges: ArrayDeque<ANSWER_TYPE> = messeges_KCashData?.ORDERED_CASH_DATA
            ?: Sqlite_service.SelectCashDataChunkOnCashSum(messeges_last_update.CASH_SUM)
        if (messeges_KCashData == null) {
            messeges_KCashData = KCashData(
                L_CashLastUpdate = messeges_last_update,
                arr = arr_messeges
            )
        }

        if (CASH_DATAS[chats_cash_sum]!!.CASH_DATA_RECORDS[chat_id]!!.answerTypeValues.GetChatsMessegeCount() > 0) {
            if (arr_messeges.isEmpty()) {
                KChat.SELECT_ALL_DATA_ON_CHAT(chat_id)
                return
            }
            if (arr_messeges.first().answerTypeValues.GetMessegeId() != CASH_DATAS[chats_cash_sum]!!.CASH_DATA_RECORDS[chat_id]!!.answerTypeValues.GetChatsMessegeCount()) {
                println("messegess: ${arr_messeges.size} not equal count of chats mess ${CASH_DATAS[chats_cash_sum]!!.CASH_DATA_RECORDS[chat_id]!!.answerTypeValues.GetChatsMessegeCount()}")
                messeges_KCashData.VerifyFirsBlock()
                return
            }
        }
    }

    private fun GetCashSum(
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


    companion object {

        private val KCashDatasLock = Mutex()

        @JsName("GET_CASH_DATA")
        fun GET_CASH_DATA(
            L_OBJECT_ID: String,
            L_RECORD_TYPE: String,
            L_COURSE: String = "0",
            L_SORT: String = "0",
            L_LINK_OWNER: String = "",
            L_MESS_COUNT_FROM: String = "",
            L_OTHER_CONDITIONS_1: String = "",
            L_OTHER_CONDITIONS_2: String = "",
            L_OTHER_CONDITIONS_3: String = "",
            l_updatedCashData: ((v: Any?) -> Any?)? = null,
            l_request_updates: Boolean,
            l_select_all_records: Boolean,
            l_is_SetLastBlock: Boolean,
            l_reset_cash_data: Boolean,
            l_ignore_timeout: Boolean
        ): Promise<KCashData> =
            CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
                var k: KCashData? = null
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        KCashDatasLock.withLock {
                            val cash_name: String
                            val cash: KCashLastUpdate?
                            var minLastSelect = 99999999999999L
                            try {
                                cash_name = GetCashSum(
                                    L_OBJECT_ID = L_OBJECT_ID,
                                    L_RECORD_TYPE = L_RECORD_TYPE,
                                    L_COURSE = L_COURSE,
                                    L_SORT = L_SORT,
                                    L_LINK_OWNER = L_LINK_OWNER,
                                    L_MESS_COUNT_FROM = L_MESS_COUNT_FROM,
                                    L_OTHER_CONDITIONS_1 = L_OTHER_CONDITIONS_1,
                                    L_OTHER_CONDITIONS_2 = L_OTHER_CONDITIONS_2,
                                    L_OTHER_CONDITIONS_3 = L_OTHER_CONDITIONS_3
                                )

                                cash = CASH_LAST_UPDATE[cash_name] ?: KCashLastUpdate(
                                    L_OBJECT_ID = L_OBJECT_ID,
                                    L_RECORD_TYPE = L_RECORD_TYPE,
                                    L_COURSE = L_COURSE,
                                    L_SORT = L_SORT,
                                    L_LINK_OWNER = L_LINK_OWNER,
                                    L_MESS_COUNT_FROM = L_MESS_COUNT_FROM,
                                    L_OTHER_CONDITIONS_1 = L_OTHER_CONDITIONS_1,
                                    L_OTHER_CONDITIONS_2 = L_OTHER_CONDITIONS_2,
                                    L_OTHER_CONDITIONS_3 = L_OTHER_CONDITIONS_3
                                )

                                k = CASH_DATAS[cash.CASH_SUM]
                                if (k != null) {
                                    if (k!!.CashLastUpdate.RECORD_TYPE != L_RECORD_TYPE) {
                                        throw my_user_exceptions_class(
                                            l_class_name = "KCashData",
                                            l_function_name = "GET_CASH_DATA",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "RECORD_TYPE of CASH_DATA ${k!!.CashLastUpdate.RECORD_TYPE} not equals RECORD_TYPE by select $L_RECORD_TYPE "
                                        )
                                    }
                                    if (k!!.CashLastUpdate.COURSE != L_COURSE) {
                                        throw my_user_exceptions_class(
                                            l_class_name = "KCashData",
                                            l_function_name = "GET_CASH_DATA",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "COURSE of CASH_DATA ${k!!.CashLastUpdate.COURSE} not equals COURSE by select $L_COURSE "
                                        )
                                    }

                                    if (l_reset_cash_data) {
                                        if (k!!.ORDERED_CASH_DATA.size > Constants.LIMIT_FOR_SELECT) {
                                            val v = k!!.ORDERED_CASH_DATA.subList(0, Constants.LIMIT_FOR_SELECT)
                                            k!!.ORDERED_CASH_DATA.clear()
                                            k!!.ORDERED_CASH_DATA.addAll(v)
                                            k!!.CASH_DATA_RECORDS.clear()
                                            k!!.ORDERED_CASH_DATA.forEach {
                                                k!!.CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                            }
                                        }

                                        k!!.ORDERED_CASH_DATA.forEach {
                                            minLastSelect = minOf(it.LONG_20, minLastSelect)
                                        }
                                    }


                                    if (k!!.CashLastUpdate.RECORD_TYPE == "4") { // MASSEGES;
                                        if (CHATS!!.CASH_DATA_RECORDS[(L_OBJECT_ID + "3" + "0")]?.answerTypeValues!!.GetChatsCountNotReadedMess() > 0L) {
                                            CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately {
                                                val w =
                                                    CHATS!!.CASH_DATA_RECORDS[(L_OBJECT_ID + "3" + "0")]?.GetJsocket()
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

                                val arr: ArrayDeque<ANSWER_TYPE> = if (l_select_all_records) {
                                    Sqlite_service.SelectCashDataAllOnCashSum(k!!.CashLastUpdate.CASH_SUM)
                                } else {
                                    Sqlite_service.SelectCashDataChunkOnCashSum(k!!.CashLastUpdate.CASH_SUM)
                                }



                                if (arr.isNotEmpty()) {
                                    arr.forEach {
                                        minLastSelect = minOf(it.LONG_20, minLastSelect)
                                        if (it.RECORD_TYPE != L_RECORD_TYPE) {
                                            throw my_user_exceptions_class(
                                                l_class_name = "KCashData",
                                                l_function_name = "GET_CASH_DATA",
                                                name_of_exception = "EXC_SYSTEM_ERROR",
                                                l_additional_text = "RECORD_TYPE of CASH_DATA ${it.RECORD_TYPE} not equals RECORD_TYPE by select $L_RECORD_TYPE "
                                            )
                                        }

                                        k!!.CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it

                                        if (it.RECORD_TYPE == "3") { // CHATS;
                                            k!!.SELECT_ALL_DATA_OF_CHAT(it.answerTypeValues.GetChatId())
                                        }

                                        if (it.RECORD_TYPE.equals("8")) {  // CHATS_LIKES;
                                            if (it.answerTypeValues.GetMainAccountId().equals(Account_Id)) {
                                                globalLastChatsSelect.setGreaterValue(it.answerTypeValues.GetRecordLastUpdate())
                                            }
                                        }
                                    }
                                    k!!.ORDERED_CASH_DATA.addAll(arr)
                                }



                                if (k!!.CashLastUpdate.RECORD_TYPE == "4") { // MASSEGES;
                                    if (CHATS!!.CASH_DATA_RECORDS[(L_OBJECT_ID + "300")]?.answerTypeValues!!.GetChatsCountNotReadedMess() > 0L) {
                                        CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately {
                                            val w = CHATS!!.CASH_DATA_RECORDS[(L_OBJECT_ID + "300")]?.GetJsocket()
                                            if (w != null) {
                                                w.value_par1 =
                                                    k!!.ORDERED_CASH_DATA.firstOrNull()?.answerTypeValues!!.GetMessegeId()
                                                        .toString()
                                                w.send_request()
                                            }
                                        }
                                    }
                                }

                                if (L_COURSE != k!!.CashLastUpdate.COURSE) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "KCashData",
                                        l_function_name = "GET_CASH_DATA",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "COURSE of CASH_LAST_UPDATE ${k!!.CashLastUpdate.COURSE} not equals COURSE by select $L_COURSE"
                                    )
                                }
                                if (k!!.CashLastUpdate.RECORD_TYPE != L_RECORD_TYPE) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "KCashData",
                                        l_function_name = "GET_CASH_DATA",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "RECORD_TYPE of CASH_LAST_UPDATE ${k!!.CashLastUpdate.RECORD_TYPE} not equals RECORD_TYPE by select $L_RECORD_TYPE "
                                    )
                                }

                                return@withTimeoutOrNull k

                            } catch (e: my_user_exceptions_class) {
                                throw e
                            } catch (ex: Exception) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KCashData",
                                    l_function_name = "GET_CASH_DATA",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = ex.stackTraceToString()
                                )
                            } finally {

                                if (k != null) {
                                    k!!.callBackUpdatedCashData = l_updatedCashData ?: { }
                                    k!!.currentViewCashData.clear()
                                    k!!.currentViewCashDataRecord = null
                                    if (l_is_SetLastBlock) {
                                        k!!.SetLastBlock()
                                    }
                                    if (minLastSelect == 99999999999999L) {
                                        minLastSelect = 0
                                    }

                                    if (l_request_updates) {
                                        if (l_ignore_timeout) {
                                            k!!.Get(
                                                l_record_table_id_from = k!!.currentViewCashDataRecord?.RECORD_TABLE_ID
                                                    ?: ""
                                            )
                                        } else {
                                            if (minLastSelect.plus(Constants.TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR) < (DateTime.nowUnixMillisLong())) {
                                                k!!.Get(
                                                    l_record_table_id_from = k!!.currentViewCashDataRecord?.RECORD_TABLE_ID
                                                        ?: ""
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "GET_CASH_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "KCashData",
                    l_function_name = "GET_CASH_DATA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
                return@async k!!
            }.toPromise(EmptyCoroutineContext)

        private fun GetCashSum(
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

        @JsName("LOAD_CASH_DATA")
        suspend fun LOAD_CASH_DATA(arr: ArrayDeque<ANSWER_TYPE>) {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCashDatasLock.withLock {
                            var v: KCashData? = null
                            //KCashLastUpdate.RE_LOAD_CASH_LAST_UPDATE().join()
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                PrintInformation.PRINT_INFO("LOAD_CASH_DATA is running")
                            }
                            arr.forEach {
                                if (v == null || v!!.CashLastUpdate.CASH_SUM != it.CASH_SUM) {
                                    /*
                                    if (v != null) {
                                        CASH_DATAS[v!!.CashLastUpdate.CASH_SUM] = v!!
                                    }
                                     */
                                    val k = CASH_LAST_UPDATE[it.CASH_SUM]
                                    v = KCashData(k!!)
                                }
                                if (v!!.CashLastUpdate.RECORD_TYPE == it.RECORD_TYPE
                                    && v!!.CashLastUpdate.COURSE == v!!.CashLastUpdate.COURSE
                                ) {
                                    v!!.ORDERED_CASH_DATA.addLast(it)
                                    it.INTEGER_20 = v!!.ORDERED_CASH_DATA.size
                                    v!!.CASH_DATA_RECORDS[it.RECORD_TABLE_ID] = it
                                }
                            }

                            /*
                            if (v != null) {
                                CASH_DATAS[v!!.CashLastUpdate.CASH_SUM] = v!!
                            }
                             */
                        }
                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "LOAD_CASH_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
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