@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket
import co.touchlab.stately.concurrency.AtomicBoolean
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withTimeoutOrNull
import p_client.CLIENT_JSOCKET_POOL
import p_client.Jsocket
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
@JsName("FIELDS_SUBSCRIBE_ANSWER_TYPES")
val FIELDS_SUBSCRIBE_ANSWER_TYPES: MutableMap<Int, ANSWER_TYPE_Subscribe> = mutableMapOf(
    1 to ANSWER_TYPE_Subscribe(1, "IDENTIFICATOR_1", 18, true, 0),
    2 to ANSWER_TYPE_Subscribe(2, "IDENTIFICATOR_2", 18, true, 0),
    3 to ANSWER_TYPE_Subscribe(3, "IDENTIFICATOR_3", 18, true, 0),
    4 to ANSWER_TYPE_Subscribe(4, "IDENTIFICATOR_4", 18, true, 0),
    5 to ANSWER_TYPE_Subscribe(5, "IDENTIFICATOR_5", 18, true, 0),
    6 to ANSWER_TYPE_Subscribe(6, "IDENTIFICATOR_6", 18, true, 0),
    7 to ANSWER_TYPE_Subscribe(7, "IDENTIFICATOR_7", 18, true, 0),
    8 to ANSWER_TYPE_Subscribe(8, "IDENTIFICATOR_8", 18, true, 0),
    9 to ANSWER_TYPE_Subscribe(9, "IDENTIFICATOR_9", 18, true, 0),
    10 to ANSWER_TYPE_Subscribe(10, "IDENTIFICATOR_10", 18, true, 0),
    11 to ANSWER_TYPE_Subscribe(11, "IDENTIFICATOR_11", 18, true, 0),
    12 to ANSWER_TYPE_Subscribe(12, "IDENTIFICATOR_12", 18, true, 0),
    13 to ANSWER_TYPE_Subscribe(13, "IDENTIFICATOR_13", 18, true, 0),
    14 to ANSWER_TYPE_Subscribe(14, "IDENTIFICATOR_14", 18, true, 0),
    15 to ANSWER_TYPE_Subscribe(15, "IDENTIFICATOR_15", 18, true, 0),
    16 to ANSWER_TYPE_Subscribe(16, "IDENTIFICATOR_16", 18, true, 0),
    17 to ANSWER_TYPE_Subscribe(17, "IDENTIFICATOR_17", 18, true, 0),
    18 to ANSWER_TYPE_Subscribe(18, "IDENTIFICATOR_18", 18, true, 0),
    19 to ANSWER_TYPE_Subscribe(19, "IDENTIFICATOR_19", 18, true, 0),
    20 to ANSWER_TYPE_Subscribe(20, "IDENTIFICATOR_20", 18, true, 0),
    21 to ANSWER_TYPE_Subscribe(21, "INTEGER_1", 4, true, 1),
    22 to ANSWER_TYPE_Subscribe(22, "INTEGER_2", 4, true, 1),
    23 to ANSWER_TYPE_Subscribe(23, "INTEGER_3", 4, true, 1),
    24 to ANSWER_TYPE_Subscribe(24, "INTEGER_4", 4, true, 1),
    25 to ANSWER_TYPE_Subscribe(25, "INTEGER_5", 4, true, 1),
    26 to ANSWER_TYPE_Subscribe(26, "INTEGER_6", 4, true, 1),
    27 to ANSWER_TYPE_Subscribe(27, "INTEGER_7", 4, true, 1),
    28 to ANSWER_TYPE_Subscribe(28, "INTEGER_8", 4, true, 1),
    29 to ANSWER_TYPE_Subscribe(29, "INTEGER_9", 4, true, 1),
    30 to ANSWER_TYPE_Subscribe(30, "INTEGER_10", 4, true, 1),
    31 to ANSWER_TYPE_Subscribe(31, "INTEGER_11", 4, true, 1),
    32 to ANSWER_TYPE_Subscribe(32, "INTEGER_12", 4, true, 1),
    33 to ANSWER_TYPE_Subscribe(33, "INTEGER_13", 4, true, 1),
    34 to ANSWER_TYPE_Subscribe(34, "INTEGER_14", 4, true, 1),
    35 to ANSWER_TYPE_Subscribe(35, "INTEGER_15", 4, true, 1),
    36 to ANSWER_TYPE_Subscribe(36, "INTEGER_16", 4, true, 1),
    37 to ANSWER_TYPE_Subscribe(37, "INTEGER_17", 4, true, 1),
    38 to ANSWER_TYPE_Subscribe(38, "INTEGER_18", 4, true, 1),
    39 to ANSWER_TYPE_Subscribe(39, "INTEGER_19", 4, true, 1),
    40 to ANSWER_TYPE_Subscribe(40, "INTEGER_20", 4, true, 1),
    41 to ANSWER_TYPE_Subscribe(41, "LONG_1", 8, true, 2),
    42 to ANSWER_TYPE_Subscribe(42, "LONG_2", 8, true, 2),
    43 to ANSWER_TYPE_Subscribe(43, "LONG_3", 8, true, 2),
    44 to ANSWER_TYPE_Subscribe(44, "LONG_4", 8, true, 2),
    45 to ANSWER_TYPE_Subscribe(45, "LONG_5", 8, true, 2),
    46 to ANSWER_TYPE_Subscribe(46, "LONG_6", 8, true, 2),
    47 to ANSWER_TYPE_Subscribe(47, "LONG_7", 8, true, 2),
    48 to ANSWER_TYPE_Subscribe(48, "LONG_8", 8, true, 2),
    49 to ANSWER_TYPE_Subscribe(49, "LONG_9", 8, true, 2),
    50 to ANSWER_TYPE_Subscribe(50, "LONG_10", 8, true, 2),
    51 to ANSWER_TYPE_Subscribe(51, "LONG_11", 8, true, 2),
    52 to ANSWER_TYPE_Subscribe(52, "LONG_12", 8, true, 2),
    53 to ANSWER_TYPE_Subscribe(53, "LONG_13", 8, true, 2),
    54 to ANSWER_TYPE_Subscribe(54, "LONG_14", 8, true, 2),
    55 to ANSWER_TYPE_Subscribe(55, "LONG_15", 8, true, 2),
    56 to ANSWER_TYPE_Subscribe(56, "LONG_16", 8, true, 2),
    57 to ANSWER_TYPE_Subscribe(57, "LONG_17", 8, true, 2),
    58 to ANSWER_TYPE_Subscribe(58, "LONG_18", 8, true, 2),
    59 to ANSWER_TYPE_Subscribe(59, "LONG_19", 8, true, 2),
    60 to ANSWER_TYPE_Subscribe(60, "LONG_20", 8, true, 2),
    61 to ANSWER_TYPE_Subscribe(61, "STRING_1", 4000, false, 0),
    62 to ANSWER_TYPE_Subscribe(62, "STRING_2", 4000, false, 0),
    63 to ANSWER_TYPE_Subscribe(63, "STRING_3", 4000, false, 0),
    64 to ANSWER_TYPE_Subscribe(64, "STRING_4", 4000, false, 0),
    65 to ANSWER_TYPE_Subscribe(65, "STRING_5", 4000, false, 0),
    66 to ANSWER_TYPE_Subscribe(66, "STRING_6", 4000, false, 0),
    67 to ANSWER_TYPE_Subscribe(67, "STRING_7", 4000, false, 0),
    68 to ANSWER_TYPE_Subscribe(68, "STRING_8", 4000, false, 0),
    69 to ANSWER_TYPE_Subscribe(69, "STRING_9", 4000, false, 0),
    70 to ANSWER_TYPE_Subscribe(70, "STRING_10", 4000, false, 0),
    71 to ANSWER_TYPE_Subscribe(71, "STRING_11", 4000, false, 0),
    72 to ANSWER_TYPE_Subscribe(72, "STRING_12", 4000, false, 0),
    73 to ANSWER_TYPE_Subscribe(73, "STRING_13", 4000, false, 0),
    74 to ANSWER_TYPE_Subscribe(74, "STRING_14", 4000, false, 0),
    75 to ANSWER_TYPE_Subscribe(75, "STRING_15", 4000, false, 0),
    76 to ANSWER_TYPE_Subscribe(76, "STRING_16", 4000, false, 0),
    77 to ANSWER_TYPE_Subscribe(77, "STRING_17", 4000, false, 0),
    78 to ANSWER_TYPE_Subscribe(78, "STRING_18", 4000, false, 0),
    79 to ANSWER_TYPE_Subscribe(79, "STRING_19", 4000, false, 0),
    80 to ANSWER_TYPE_Subscribe(80, "STRING_20", 4000, false, 0),
    81 to ANSWER_TYPE_Subscribe(81, "BLOB_1", Constants.MAX_SMALL_AVATAR_SIZE_B, false, 4),
    82 to ANSWER_TYPE_Subscribe(82, "BLOB_2", Constants.MAX_CUT_BIG_AVATAR_SIZE_B, false, 4),
    83 to ANSWER_TYPE_Subscribe(83, "BLOB_3", Constants.MAX_BIG_AVATAR_SIZE_B, false, 4)
)

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val CLIENT_ANSWER_TYPE_POOL: ArrayDeque<ANSWER_TYPE> = ArrayDeque()


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val CLIENT_ANSWER_TYPE_POOLS: ArrayDeque<ArrayDeque<ANSWER_TYPE>> = ArrayDeque()


@InternalAPI
private val CLIENT_ANSWER_TYPE_POOL_Lock = Mutex()
private val CLIENT_ANSWER_TYPE_POOLS_Lock = Mutex()
private var fillPOOL_IS_RUNNING: AtomicBoolean = AtomicBoolean(false)
private var fillPOOLS_IS_RUNNING: AtomicBoolean = AtomicBoolean(false)

/**
 *
 * @author Oleg
 */
@Suppress("SetterBackingFieldAssignment")
@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
@JsName("ANSWER_TYPE")
class ANSWER_TYPE() {

    //var SELF_Jsockt: Jsocket? = CLIENT_JSOCKET_POOL.removeFirstOrNull()

    init {
        /*
        if(SELF_Jsockt == null){
            SELF_Jsockt =  Jsocket()
            Jsocket.fill()
            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                println("CLIENT_JSOCKET_POOL is emprty")
            }
        }
         */
        ensureNeverFrozen()
    }

    @JsName("answerTypeValues")
    val answerTypeValues = AnswerTypeValues(this)

    @JsName("IDENTIFICATORS")
    var IDENTIFICATORS: String? = ""

    @JsName("IDENTIFICATOR_1")
    var IDENTIFICATOR_1: String? = ""

    @JsName("IDENTIFICATOR_2")
    var IDENTIFICATOR_2: String? = ""

    @JsName("IDENTIFICATOR_3")
    var IDENTIFICATOR_3: String? = ""


    @JsName("IDENTIFICATOR_4")
    var IDENTIFICATOR_4: String? = ""


    @JsName("IDENTIFICATOR_5")
    var IDENTIFICATOR_5: String? = ""


    @JsName("IDENTIFICATOR_6")
    var IDENTIFICATOR_6: String? = ""


    @JsName("IDENTIFICATOR_7")
    var IDENTIFICATOR_7: String? = ""


    @JsName("IDENTIFICATOR_8")
    var IDENTIFICATOR_8: String? = ""


    @JsName("IDENTIFICATOR_9")
    var IDENTIFICATOR_9: String? = ""


    @JsName("IDENTIFICATOR_10")
    var IDENTIFICATOR_10: String? = ""

    @JsName("IDENTIFICATOR_11")
    var IDENTIFICATOR_11: String? = ""


    @JsName("IDENTIFICATOR_12")
    var IDENTIFICATOR_12: String? = ""


    @JsName("IDENTIFICATOR_13")
    var IDENTIFICATOR_13: String? = ""


    @JsName("IDENTIFICATOR_14")
    var IDENTIFICATOR_14: String? = ""


    @JsName("IDENTIFICATOR_15")
    var IDENTIFICATOR_15: String? = ""


    @JsName("IDENTIFICATOR_16")
    var IDENTIFICATOR_16: String? = ""


    @JsName("IDENTIFICATOR_17")
    var IDENTIFICATOR_17: String? = ""


    @JsName("IDENTIFICATOR_18")
    var IDENTIFICATOR_18: String? = ""


    @JsName("IDENTIFICATOR_19")
    var IDENTIFICATOR_19: String? = ""


    @JsName("IDENTIFICATOR_20")
    var IDENTIFICATOR_20: String? = ""


    @JsName("INTEGER_1")
    var INTEGER_1: Int? = 0


    @JsName("INTEGER_2")
    var INTEGER_2: Int? = 0


    @JsName("INTEGER_3")
    var INTEGER_3: Int? = 0


    @JsName("INTEGER_4")
    var INTEGER_4: Int? = 0


    @JsName("INTEGER_5")
    var INTEGER_5: Int? = 0


    @JsName("INTEGER_6")
    var INTEGER_6: Int? = 0


    @JsName("INTEGER_7")
    var INTEGER_7: Int? = 0


    @JsName("INTEGER_8")
    var INTEGER_8: Int? = 0


    @JsName("INTEGER_9")
    var INTEGER_9: Int? = 0


    @JsName("INTEGER_10")
    var INTEGER_10: Int? = 0


    @JsName("INTEGER_11")
    var INTEGER_11: Int? = 0


    @JsName("INTEGER_12")
    var INTEGER_12: Int? = 0


    @JsName("INTEGER_13")
    var INTEGER_13: Int? = 0


    @JsName("INTEGER_14")
    var INTEGER_14: Int? = 0


    @JsName("INTEGER_15")
    var INTEGER_15: Int? = 0


    @JsName("INTEGER_16")
    var INTEGER_16: Int? = 0


    @JsName("INTEGER_17")
    var INTEGER_17: Int? = 0


    @JsName("INTEGER_18")
    var INTEGER_18: Int? = 0


    @JsName("INTEGER_19")
    var INTEGER_19: Int? = 0


    @JsName("INTEGER_20")
    var INTEGER_20: Int? = 0


    @JsName("LONG_1")
    var LONG_1: Long? = 0L


    @JsName("LONG_2")
    var LONG_2: Long? = 0L


    @JsName("LONG_3")
    var LONG_3: Long? = 0L


    @JsName("LONG_4")
    var LONG_4: Long? = 0L


    @JsName("LONG_5")
    var LONG_5: Long? = 0L


    @JsName("LONG_6")
    var LONG_6: Long? = 0L


    @JsName("LONG_7")
    var LONG_7: Long? = 0L


    @JsName("LONG_8")
    var LONG_8: Long? = 0L


    @JsName("LONG_9")
    var LONG_9: Long? = 0L


    @JsName("LONG_10")
    var LONG_10: Long? = 0L

    @JsName("LONG_11")
    var LONG_11: Long? = 0L


    @JsName("LONG_12")
    var LONG_12: Long? = 0L


    @JsName("LONG_13")
    var LONG_13: Long? = 0L


    @JsName("LONG_14")
    var LONG_14: Long? = 0L


    @JsName("LONG_15")
    var LONG_15: Long? = 0L


    @JsName("LONG_16")
    var LONG_16: Long? = 0L


    @JsName("LONG_17")
    var LONG_17: Long? = 0L


    @JsName("LONG_18")
    var LONG_18: Long? = 0L


    @JsName("LONG_19")
    var LONG_19: Long? = 0L


    @JsName("LONG_20")
    var LONG_20: Long? = 0L


    @JsName("STRING_1")
    var STRING_1: String? = ""


    @JsName("STRING_2")
    var STRING_2: String? = ""


    @JsName("STRING_3")
    var STRING_3: String? = ""


    @JsName("STRING_4")
    var STRING_4: String? = ""


    @JsName("STRING_5")
    var STRING_5: String? = ""


    @JsName("STRING_6")
    var STRING_6: String? = ""


    @JsName("STRING_7")
    var STRING_7: String? = ""


    @JsName("STRING_8")
    var STRING_8: String? = ""


    @JsName("STRING_9")
    var STRING_9: String? = ""


    @JsName("STRING_10")
    var STRING_10: String? = ""

    @JsName("STRING_11")
    var STRING_11: String? = ""


    @JsName("STRING_12")
    var STRING_12: String? = ""


    @JsName("STRING_13")
    var STRING_13: String? = ""


    @JsName("STRING_14")
    var STRING_14: String? = ""


    @JsName("STRING_15")
    var STRING_15: String? = ""


    @JsName("STRING_16")
    var STRING_16: String? = ""


    @JsName("STRING_17")
    var STRING_17: String? = ""


    @JsName("STRING_18")
    var STRING_18: String? = ""


    @JsName("STRING_19")
    var STRING_19: String? = ""

    @JsName("STRING_20")
    var STRING_20: String? = ""
    set(v){
        val v2 = v?:""
        if(v2.isNotEmpty() && !v2.equals(field)){
            field = v2
            answerTypeValues.setRECORD_TYPE(v2)
        }
    }

    @JsName("BLOB_1")
    var BLOB_1: ByteArray? = null

    @JsName("BLOB_2")
    var BLOB_2: ByteArray? = null

    @JsName("BLOB_3")
    var BLOB_3: ByteArray? = null

    @JsName("RECORD_TYPE")
    var RECORD_TYPE: String = ""
        set(v){
            val v2 = v
            if(!v2.equals(field)){
                field = v2
                answerTypeValues.INIT_STRING20()
            }
        }

    @JsName("RECORD_ID")
    var RECORD_ID: String = ""

    @JsName("CASH_SUM")
    var CASH_SUM: Long = 0

    @JsName("NUMBER_POSITION")
    var NUMBER_POSITION: Int = 0

    @JsName("LAST_UPDATE")
    var LAST_UPDATE: Long = 0L

    @JsName("IS_UPDATED_BY_MERGE")
    var IS_UPDATED_BY_MERGE: Boolean = false




    @InternalAPI
    constructor(
        lRECORD_ID: String,
        lCASH_SUM: Long,
        lNUMBER_POSITION: Int?,
        lLAST_UPDATE: Long,
        lIDENTIFICATOR_1: String?,
        lIDENTIFICATOR_2: String?,
        lIDENTIFICATOR_3: String?,
        lIDENTIFICATOR_4: String?,
        lIDENTIFICATOR_5: String?,
        lIDENTIFICATOR_6: String?,
        lIDENTIFICATOR_7: String?,
        lIDENTIFICATOR_8: String?,
        lIDENTIFICATOR_9: String?,
        lIDENTIFICATOR_10: String?,
        lIDENTIFICATOR_11: String?,
        lIDENTIFICATOR_12: String?,
        lIDENTIFICATOR_13: String?,
        lIDENTIFICATOR_14: String?,
        lIDENTIFICATOR_15: String?,
        lIDENTIFICATOR_16: String?,
        lIDENTIFICATOR_17: String?,
        lIDENTIFICATOR_18: String?,
        lIDENTIFICATOR_19: String?,
        lIDENTIFICATOR_20: String?,
        lInt_1: Int?,
        lInt_2: Int?,
        lInt_3: Int?,
        lInt_4: Int?,
        lInt_5: Int?,
        lInt_6: Int?,
        lInt_7: Int?,
        lInt_8: Int?,
        lInt_9: Int?,
        lInt_10: Int?,
        lInt_11: Int?,
        lInt_12: Int?,
        lInt_13: Int?,
        lInt_14: Int?,
        lInt_15: Int?,
        lInt_16: Int?,
        lInt_17: Int?,
        lInt_18: Int?,
        lInt_19: Int?,
        lInt_20: Int?,
        lLONG_1: Long?,
        lLONG_2: Long?,
        lLONG_3: Long?,
        lLONG_4: Long?,
        lLONG_5: Long?,
        lLONG_6: Long?,
        lLONG_7: Long?,
        lLONG_8: Long?,
        lLONG_9: Long?,
        lLONG_10: Long?,
        lLONG_11: Long?,
        lLONG_12: Long?,
        lLONG_13: Long?,
        lLONG_14: Long?,
        lLONG_15: Long?,
        lLONG_16: Long?,
        lLONG_17: Long?,
        lLONG_18: Long?,
        lLONG_19: Long?,
        lLONG_20: Long?,
        lSTRING_1: String?,
        lSTRING_2: String?,
        lSTRING_3: String?,
        lSTRING_4: String?,
        lSTRING_5: String?,
        lSTRING_6: String?,
        lSTRING_7: String?,
        lSTRING_8: String?,
        lSTRING_9: String?,
        lSTRING_10: String?,
        lSTRING_11: String?,
        lSTRING_12: String?,
        lSTRING_13: String?,
        lSTRING_14: String?,
        lSTRING_15: String?,
        lSTRING_16: String?,
        lSTRING_17: String?,
        lSTRING_18: String?,
        lSTRING_19: String?,
        lSTRING_20: String?,
        lBLOB_1: ByteArray?,
        lBLOB_2: ByteArray?,
        lBLOB_3: ByteArray?

    ) : this() {
        IDENTIFICATOR_1 = lIDENTIFICATOR_1?.trim() ?: ""

        IDENTIFICATOR_2 = lIDENTIFICATOR_2?.trim() ?: ""

        IDENTIFICATOR_3 = lIDENTIFICATOR_3?.trim() ?: ""

        IDENTIFICATOR_4 = lIDENTIFICATOR_4?.trim() ?: ""

        IDENTIFICATOR_5 = lIDENTIFICATOR_5?.trim() ?: ""

        IDENTIFICATOR_6 = lIDENTIFICATOR_6?.trim() ?: ""

        IDENTIFICATOR_7 = lIDENTIFICATOR_7?.trim() ?: ""

        IDENTIFICATOR_8 = lIDENTIFICATOR_8?.trim() ?: ""

        IDENTIFICATOR_9 = lIDENTIFICATOR_9?.trim() ?: ""

        IDENTIFICATOR_10 = lIDENTIFICATOR_10?.trim() ?: ""

        IDENTIFICATOR_11 = lIDENTIFICATOR_11?.trim() ?: ""

        IDENTIFICATOR_12 = lIDENTIFICATOR_12?.trim() ?: ""

        IDENTIFICATOR_13 = lIDENTIFICATOR_13?.trim() ?: ""

        IDENTIFICATOR_14 = lIDENTIFICATOR_14?.trim() ?: ""

        IDENTIFICATOR_15 = lIDENTIFICATOR_15?.trim() ?: ""

        IDENTIFICATOR_16 = lIDENTIFICATOR_16?.trim() ?: ""

        IDENTIFICATOR_17 = lIDENTIFICATOR_17?.trim() ?: ""

        IDENTIFICATOR_18 = lIDENTIFICATOR_18?.trim() ?: ""

        IDENTIFICATOR_19 = lIDENTIFICATOR_19?.trim() ?: ""

        IDENTIFICATOR_20 = lIDENTIFICATOR_20?.trim() ?: ""

        INTEGER_1 = lInt_1 ?: 0
        INTEGER_2 = lInt_2 ?: 0
        INTEGER_3 = lInt_3 ?: 0
        INTEGER_4 = lInt_4 ?: 0
        INTEGER_5 = lInt_5 ?: 0
        INTEGER_6 = lInt_6 ?: 0
        INTEGER_7 = lInt_7 ?: 0
        INTEGER_8 = lInt_8 ?: 0
        INTEGER_9 = lInt_9 ?: 0
        INTEGER_10 = lInt_10 ?: 0
        INTEGER_11 = lInt_11 ?: 0
        INTEGER_12 = lInt_12 ?: 0
        INTEGER_13 = lInt_13 ?: 0
        INTEGER_14 = lInt_14 ?: 0
        INTEGER_15 = lInt_15 ?: 0
        INTEGER_16 = lInt_16 ?: 0
        INTEGER_17 = lInt_17 ?: 0
        INTEGER_18 = lInt_18 ?: 0
        INTEGER_19 = lInt_19 ?: 0
        INTEGER_20 = lInt_20 ?: 0
        LONG_1 = lLONG_1 ?: 0L
        LONG_2 = lLONG_2 ?: 0L
        LONG_3 = lLONG_3 ?: 0L
        LONG_4 = lLONG_4 ?: 0L
        LONG_5 = lLONG_5 ?: 0L
        LONG_6 = lLONG_6 ?: 0L
        LONG_7 = lLONG_7 ?: 0L
        LONG_8 = lLONG_8 ?: 0L
        LONG_9 = lLONG_9 ?: 0L
        LONG_10 = lLONG_10 ?: 0L
        LONG_11 = lLONG_11 ?: 0L
        LONG_12 = lLONG_12 ?: 0L
        LONG_13 = lLONG_13 ?: 0L
        LONG_14 = lLONG_14 ?: 0L
        LONG_15 = lLONG_15 ?: 0L
        LONG_16 = lLONG_16 ?: 0L
        LONG_17 = lLONG_17 ?: 0L
        LONG_18 = lLONG_18 ?: 0L
        LONG_19 = lLONG_19 ?: 0L
        LONG_20 = lLONG_20 ?: 0L
        STRING_1 = lSTRING_1?.trim() ?: ""
        STRING_2 = lSTRING_2?.trim() ?: ""
        STRING_3 = lSTRING_3?.trim() ?: ""
        STRING_4 = lSTRING_4?.trim() ?: ""
        STRING_5 = lSTRING_5?.trim() ?: ""
        STRING_6 = lSTRING_6?.trim() ?: ""
        STRING_7 = lSTRING_7?.trim() ?: ""
        STRING_8 = lSTRING_8?.trim() ?: ""
        STRING_9 = lSTRING_9?.trim() ?: ""
        STRING_10 = lSTRING_10?.trim() ?: ""
        STRING_11 = lSTRING_11?.trim() ?: ""
        STRING_12 = lSTRING_12?.trim() ?: ""
        STRING_13 = lSTRING_13?.trim() ?: ""
        STRING_14 = lSTRING_14?.trim() ?: ""
        STRING_15 = lSTRING_15?.trim() ?: ""
        STRING_16 = lSTRING_16?.trim() ?: ""
        STRING_17 = lSTRING_17?.trim() ?: ""
        STRING_18 = lSTRING_18?.trim() ?: ""
        STRING_19 = lSTRING_19?.trim() ?: ""
        BLOB_1 = lBLOB_1
        BLOB_2 = lBLOB_2
        BLOB_3 = lBLOB_3
        RECORD_ID = lRECORD_ID
        CASH_SUM = lCASH_SUM
        NUMBER_POSITION = lNUMBER_POSITION ?: INTEGER_20 ?: 1
        LAST_UPDATE = lLAST_UPDATE
        STRING_20 = lSTRING_20?.trim() ?: ""

    }

    constructor(MyANSWER_TYPE: ANSWER_TYPE) : this() {
        IDENTIFICATOR_1 = MyANSWER_TYPE.IDENTIFICATOR_1?.trim() ?: ""
        IDENTIFICATOR_2 = MyANSWER_TYPE.IDENTIFICATOR_2?.trim() ?: ""
        IDENTIFICATOR_3 = MyANSWER_TYPE.IDENTIFICATOR_3?.trim() ?: ""
        IDENTIFICATOR_4 = MyANSWER_TYPE.IDENTIFICATOR_4?.trim() ?: ""
        IDENTIFICATOR_5 = MyANSWER_TYPE.IDENTIFICATOR_5?.trim() ?: ""
        IDENTIFICATOR_6 = MyANSWER_TYPE.IDENTIFICATOR_6?.trim() ?: ""
        IDENTIFICATOR_7 = MyANSWER_TYPE.IDENTIFICATOR_7?.trim() ?: ""
        IDENTIFICATOR_8 = MyANSWER_TYPE.IDENTIFICATOR_8?.trim() ?: ""
        IDENTIFICATOR_9 = MyANSWER_TYPE.IDENTIFICATOR_9?.trim() ?: ""
        IDENTIFICATOR_10 = MyANSWER_TYPE.IDENTIFICATOR_10?.trim() ?: ""
        IDENTIFICATOR_11 = MyANSWER_TYPE.IDENTIFICATOR_11?.trim() ?: ""
        IDENTIFICATOR_12 = MyANSWER_TYPE.IDENTIFICATOR_12?.trim() ?: ""
        IDENTIFICATOR_13 = MyANSWER_TYPE.IDENTIFICATOR_13?.trim() ?: ""
        IDENTIFICATOR_14 = MyANSWER_TYPE.IDENTIFICATOR_14?.trim() ?: ""
        IDENTIFICATOR_15 = MyANSWER_TYPE.IDENTIFICATOR_15?.trim() ?: ""
        IDENTIFICATOR_16 = MyANSWER_TYPE.IDENTIFICATOR_16?.trim() ?: ""
        IDENTIFICATOR_17 = MyANSWER_TYPE.IDENTIFICATOR_17?.trim() ?: ""
        IDENTIFICATOR_18 = MyANSWER_TYPE.IDENTIFICATOR_18?.trim() ?: ""
        IDENTIFICATOR_19 = MyANSWER_TYPE.IDENTIFICATOR_19?.trim() ?: ""
        IDENTIFICATOR_20 = MyANSWER_TYPE.IDENTIFICATOR_20?.trim() ?: ""
        INTEGER_1 = MyANSWER_TYPE.INTEGER_1 ?: 0
        INTEGER_2 = MyANSWER_TYPE.INTEGER_2 ?: 0
        INTEGER_3 = MyANSWER_TYPE.INTEGER_3 ?: 0
        INTEGER_4 = MyANSWER_TYPE.INTEGER_4 ?: 0
        INTEGER_5 = MyANSWER_TYPE.INTEGER_5 ?: 0
        INTEGER_6 = MyANSWER_TYPE.INTEGER_6 ?: 0
        INTEGER_7 = MyANSWER_TYPE.INTEGER_7 ?: 0
        INTEGER_8 = MyANSWER_TYPE.INTEGER_8 ?: 0
        INTEGER_9 = MyANSWER_TYPE.INTEGER_9 ?: 0
        INTEGER_10 = MyANSWER_TYPE.INTEGER_10 ?: 0
        INTEGER_11 = MyANSWER_TYPE.INTEGER_11 ?: 0
        INTEGER_12 = MyANSWER_TYPE.INTEGER_12 ?: 0
        INTEGER_13 = MyANSWER_TYPE.INTEGER_13 ?: 0
        INTEGER_14 = MyANSWER_TYPE.INTEGER_14 ?: 0
        INTEGER_15 = MyANSWER_TYPE.INTEGER_15 ?: 0
        INTEGER_16 = MyANSWER_TYPE.INTEGER_16 ?: 0
        INTEGER_17 = MyANSWER_TYPE.INTEGER_17 ?: 0
        INTEGER_18 = MyANSWER_TYPE.INTEGER_18 ?: 0
        INTEGER_19 = MyANSWER_TYPE.INTEGER_19 ?: 0
        INTEGER_20 = MyANSWER_TYPE.INTEGER_20 ?: 0
        LONG_1 = MyANSWER_TYPE.LONG_1 ?: 0L
        LONG_2 = MyANSWER_TYPE.LONG_2 ?: 0L
        LONG_3 = MyANSWER_TYPE.LONG_3 ?: 0L
        LONG_4 = MyANSWER_TYPE.LONG_4 ?: 0L
        LONG_5 = MyANSWER_TYPE.LONG_5 ?: 0L
        LONG_6 = MyANSWER_TYPE.LONG_6 ?: 0L
        LONG_7 = MyANSWER_TYPE.LONG_7 ?: 0L
        LONG_8 = MyANSWER_TYPE.LONG_8 ?: 0L
        LONG_9 = MyANSWER_TYPE.LONG_9 ?: 0L
        LONG_10 = MyANSWER_TYPE.LONG_10 ?: 0L
        LONG_11 = MyANSWER_TYPE.LONG_11 ?: 0L
        LONG_12 = MyANSWER_TYPE.LONG_12 ?: 0L
        LONG_13 = MyANSWER_TYPE.LONG_13 ?: 0L
        LONG_14 = MyANSWER_TYPE.LONG_14 ?: 0L
        LONG_15 = MyANSWER_TYPE.LONG_15 ?: 0L
        LONG_16 = MyANSWER_TYPE.LONG_16 ?: 0L
        LONG_17 = MyANSWER_TYPE.LONG_17 ?: 0L
        LONG_18 = MyANSWER_TYPE.LONG_18 ?: 0L
        LONG_19 = MyANSWER_TYPE.LONG_19 ?: 0L
        LONG_20 = MyANSWER_TYPE.LONG_20 ?: 0L
        STRING_1 = MyANSWER_TYPE.STRING_1?.trim() ?: ""
        STRING_2 = MyANSWER_TYPE.STRING_2?.trim() ?: ""
        STRING_3 = MyANSWER_TYPE.STRING_3?.trim() ?: ""
        STRING_4 = MyANSWER_TYPE.STRING_4?.trim() ?: ""
        STRING_5 = MyANSWER_TYPE.STRING_5?.trim() ?: ""
        STRING_6 = MyANSWER_TYPE.STRING_6?.trim() ?: ""
        STRING_7 = MyANSWER_TYPE.STRING_7?.trim() ?: ""
        STRING_8 = MyANSWER_TYPE.STRING_8?.trim() ?: ""
        STRING_9 = MyANSWER_TYPE.STRING_9?.trim() ?: ""
        STRING_10 = MyANSWER_TYPE.STRING_10?.trim() ?: ""
        STRING_11 = MyANSWER_TYPE.STRING_11?.trim() ?: ""
        STRING_12 = MyANSWER_TYPE.STRING_12?.trim() ?: ""
        STRING_13 = MyANSWER_TYPE.STRING_13?.trim() ?: ""
        STRING_14 = MyANSWER_TYPE.STRING_14?.trim() ?: ""
        STRING_15 = MyANSWER_TYPE.STRING_15?.trim() ?: ""
        STRING_16 = MyANSWER_TYPE.STRING_16?.trim() ?: ""
        STRING_17 = MyANSWER_TYPE.STRING_17?.trim() ?: ""
        STRING_18 = MyANSWER_TYPE.STRING_18?.trim() ?: ""
        STRING_19 = MyANSWER_TYPE.STRING_19?.trim() ?: ""
        BLOB_1 = MyANSWER_TYPE.BLOB_1
        BLOB_2 = MyANSWER_TYPE.BLOB_2
        BLOB_3 = MyANSWER_TYPE.BLOB_3
        RECORD_ID = MyANSWER_TYPE.RECORD_ID
        CASH_SUM = MyANSWER_TYPE.CASH_SUM
        NUMBER_POSITION = MyANSWER_TYPE.NUMBER_POSITION
        LAST_UPDATE = MyANSWER_TYPE.LAST_UPDATE
        STRING_20 = MyANSWER_TYPE.STRING_20?.trim() ?: ""

    }


    @JsName("merge")
    fun merge(v: ANSWER_TYPE?) {
        if (v == null) {
            return
        }
        IS_UPDATED_BY_MERGE = false

        if (v.IDENTIFICATOR_1 != null && v.IDENTIFICATOR_1!!.isNotEmpty() && v.IDENTIFICATOR_1 != this.IDENTIFICATOR_1) {
            this.IDENTIFICATOR_1 = v.IDENTIFICATOR_1
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_2 != null && v.IDENTIFICATOR_2!!.isNotEmpty() && v.IDENTIFICATOR_2 != this.IDENTIFICATOR_2) {
            this.IDENTIFICATOR_2 = v.IDENTIFICATOR_2
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_3 != null && v.IDENTIFICATOR_3!!.isNotEmpty() && v.IDENTIFICATOR_3 != this.IDENTIFICATOR_3) {
            this.IDENTIFICATOR_3 = v.IDENTIFICATOR_3
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_4 != null && v.IDENTIFICATOR_4!!.isNotEmpty() && v.IDENTIFICATOR_4 != this.IDENTIFICATOR_4) {
            this.IDENTIFICATOR_4 = v.IDENTIFICATOR_4
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_5 != null && v.IDENTIFICATOR_5!!.isNotEmpty() && v.IDENTIFICATOR_5 != this.IDENTIFICATOR_5) {
            this.IDENTIFICATOR_5 = v.IDENTIFICATOR_5
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_6 != null && v.IDENTIFICATOR_6!!.isNotEmpty() && v.IDENTIFICATOR_6 != this.IDENTIFICATOR_6) {
            this.IDENTIFICATOR_6 = v.IDENTIFICATOR_6
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_7 != null && v.IDENTIFICATOR_7!!.isNotEmpty() && v.IDENTIFICATOR_7 != this.IDENTIFICATOR_7) {
            this.IDENTIFICATOR_7 = v.IDENTIFICATOR_7
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_8 != null && v.IDENTIFICATOR_8!!.isNotEmpty() && v.IDENTIFICATOR_8 != this.IDENTIFICATOR_8) {
            this.IDENTIFICATOR_8 = v.IDENTIFICATOR_8
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_9 != null && v.IDENTIFICATOR_9!!.isNotEmpty() && v.IDENTIFICATOR_9 != this.IDENTIFICATOR_9) {
            this.IDENTIFICATOR_9 = v.IDENTIFICATOR_9
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_10 != null && v.IDENTIFICATOR_10!!.isNotEmpty() && v.IDENTIFICATOR_10 != this.IDENTIFICATOR_10) {
            this.IDENTIFICATOR_10 = v.IDENTIFICATOR_10
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_11 != null && v.IDENTIFICATOR_11!!.isNotEmpty() && v.IDENTIFICATOR_11 != this.IDENTIFICATOR_11) {
            this.IDENTIFICATOR_11 = v.IDENTIFICATOR_11
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_12 != null && v.IDENTIFICATOR_12!!.isNotEmpty() && v.IDENTIFICATOR_12 != this.IDENTIFICATOR_12) {
            this.IDENTIFICATOR_12 = v.IDENTIFICATOR_12
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_13 != null && v.IDENTIFICATOR_13!!.isNotEmpty() && v.IDENTIFICATOR_13 != this.IDENTIFICATOR_13) {
            this.IDENTIFICATOR_13 = v.IDENTIFICATOR_13
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_14 != null && v.IDENTIFICATOR_14!!.isNotEmpty() && v.IDENTIFICATOR_14 != this.IDENTIFICATOR_14) {
            this.IDENTIFICATOR_14 = v.IDENTIFICATOR_14
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_15 != null && v.IDENTIFICATOR_15!!.isNotEmpty() && v.IDENTIFICATOR_15 != this.IDENTIFICATOR_15) {
            this.IDENTIFICATOR_15 = v.IDENTIFICATOR_15
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_16 != null && v.IDENTIFICATOR_16!!.isNotEmpty() && v.IDENTIFICATOR_16 != this.IDENTIFICATOR_16) {
            this.IDENTIFICATOR_16 = v.IDENTIFICATOR_16
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_17 != null && v.IDENTIFICATOR_17!!.isNotEmpty() && v.IDENTIFICATOR_17 != this.IDENTIFICATOR_17) {
            this.IDENTIFICATOR_17 = v.IDENTIFICATOR_17
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_18 != null && v.IDENTIFICATOR_18!!.isNotEmpty() && v.IDENTIFICATOR_18 != this.IDENTIFICATOR_18) {
            this.IDENTIFICATOR_18 = v.IDENTIFICATOR_18
            IS_UPDATED_BY_MERGE = true
        }


        if (v.IDENTIFICATOR_19 != null && v.IDENTIFICATOR_19!!.isNotEmpty() && v.IDENTIFICATOR_19 != this.IDENTIFICATOR_19) {
            this.IDENTIFICATOR_19 = v.IDENTIFICATOR_19
            IS_UPDATED_BY_MERGE = true
        }

        if (v.IDENTIFICATOR_20 != null && v.IDENTIFICATOR_20!!.isNotEmpty() && v.IDENTIFICATOR_20 != this.IDENTIFICATOR_20) {
            this.IDENTIFICATOR_20 = v.IDENTIFICATOR_20
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_1 != null && v.LONG_1 != this.LONG_1) {
            this.LONG_1 = v.LONG_1       // accounts1 last connect
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_2 != null && v.LONG_2 != this.LONG_2) {
            this.LONG_2 = v.LONG_2      // accounts2 last connect
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_3 != null && v.LONG_3 != this.LONG_3) {
            this.LONG_3 = v.LONG_3
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_4 != null && v.LONG_4 != this.LONG_4) {
            this.LONG_4 = v.LONG_4
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_5 != null && v.LONG_5 != this.LONG_5) {
            this.LONG_5 = v.LONG_5
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_6 != null && v.LONG_6 != this.LONG_6) {
            this.LONG_6 = v.LONG_6
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_7 != null && v.LONG_7 != this.LONG_7) {
            this.LONG_7 = v.LONG_7
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_8 != null && v.LONG_8 != this.LONG_8) {
            this.LONG_8 = v.LONG_8
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_9 != null && v.LONG_9 != this.LONG_9) {
            this.LONG_9 = v.LONG_9
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_10 != null && v.LONG_10 != this.LONG_10) {
            this.LONG_10 = v.LONG_10
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_11 != null && v.LONG_11 != this.LONG_11) {
            this.LONG_11 = v.LONG_11
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_12 != null && v.LONG_12 != this.LONG_12) {
            this.LONG_12 = v.LONG_12
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_13 != null && v.LONG_13 != this.LONG_13) {
            this.LONG_13 = v.LONG_13
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_14 != null && v.LONG_14 != this.LONG_14) {
            this.LONG_14 = v.LONG_14
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_15 != null && v.LONG_15 != this.LONG_15) {
            this.LONG_15 = v.LONG_15
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_16 != null && v.LONG_16 != this.LONG_16) {
            this.LONG_16 = v.LONG_16
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_17 != null && v.LONG_17 != this.LONG_17) {
            this.LONG_17 = v.LONG_17
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_18 != null && v.LONG_18 != this.LONG_18) {
            this.LONG_18 = v.LONG_18
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_19 != null && v.LONG_19 != this.LONG_19) {
            this.LONG_19 = v.LONG_19
            IS_UPDATED_BY_MERGE = true
        }

        if (v.LONG_20 != null && v.LONG_20 != this.LONG_20) {
            this.LONG_20 = v.LONG_20
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_1 != null && v.INTEGER_1 != this.INTEGER_1) {
            this.INTEGER_1 = v.INTEGER_1
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_2 != null && v.INTEGER_2 != this.INTEGER_2) {
            this.INTEGER_2 = v.INTEGER_2
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_3 != null && v.INTEGER_3 != this.INTEGER_3) {
            this.INTEGER_3 = v.INTEGER_3
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_4 != null && v.INTEGER_4 != this.INTEGER_4) {
            this.INTEGER_4 = v.INTEGER_4  //object size
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_5 != null && v.INTEGER_5 != this.INTEGER_5) {
            this.INTEGER_5 = v.INTEGER_5  // object length seconds
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_6 != null && v.INTEGER_6 != this.INTEGER_6) {
            this.INTEGER_6 = v.INTEGER_6
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_7 != null && v.INTEGER_7 != this.INTEGER_7) {
            this.INTEGER_7 = v.INTEGER_7
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_8 != null && v.INTEGER_8 != this.INTEGER_8) {
            this.INTEGER_8 = v.INTEGER_8
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_9 != null && v.INTEGER_9 != this.INTEGER_9) {
            this.INTEGER_9 = v.INTEGER_9
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_10 != null && v.INTEGER_10 != this.INTEGER_10) {
            this.INTEGER_10 = v.INTEGER_10
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_11 != null && v.INTEGER_11 != this.INTEGER_11) {
            this.INTEGER_11 = v.INTEGER_11
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_12 != null && v.INTEGER_12 != this.INTEGER_12) {
            this.INTEGER_12 = v.INTEGER_12
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_13 != null && v.INTEGER_13 != this.INTEGER_13) {
            this.INTEGER_13 = v.INTEGER_13
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_14 != null && v.INTEGER_14 != this.INTEGER_14) {
            this.INTEGER_14 = v.INTEGER_14
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_15 != null && v.INTEGER_15 != this.INTEGER_15) {
            this.INTEGER_15 = v.INTEGER_15
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_16 != null && v.INTEGER_16 != this.INTEGER_16) {
            this.INTEGER_16 = v.INTEGER_16
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_17 != null && v.INTEGER_17 != this.INTEGER_17) {
            this.INTEGER_17 = v.INTEGER_17
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_18 != null && v.INTEGER_18 != this.INTEGER_18) {
            this.INTEGER_18 = v.INTEGER_18
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_19 != null && v.INTEGER_19 != this.INTEGER_19) {
            this.INTEGER_19 = v.INTEGER_19
            IS_UPDATED_BY_MERGE = true
        }

        if (v.INTEGER_20 != null && v.INTEGER_20 != this.INTEGER_20) {
            this.INTEGER_20 = v.INTEGER_20
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_1 != null && v.STRING_1!!.isNotEmpty() && v.STRING_1 != this.STRING_1) {
            this.STRING_1 = v.STRING_1    //accounts1 name
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_2 != null && v.STRING_2!!.isNotEmpty() && v.STRING_2 != this.STRING_2) {
            this.STRING_2 = v.STRING_2    //accounts1 access
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_3 != null && v.STRING_3!!.isNotEmpty() && v.STRING_3 != this.STRING_3) {
            this.STRING_3 = v.STRING_3    //accounts2 name
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_4 != null && v.STRING_4!!.isNotEmpty() && v.STRING_4 != this.STRING_4) {
            this.STRING_4 = v.STRING_4    //accounts2 access
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_5 != null && v.STRING_5!!.isNotEmpty() && v.STRING_5 != this.STRING_5) {
            this.STRING_5 = v.STRING_5    //object name
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_6 != null && v.STRING_6!!.isNotEmpty() && v.STRING_6 != this.STRING_6) {
            this.STRING_6 = v.STRING_6    //object server
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_7 != null && v.STRING_7!!.isNotEmpty() && v.STRING_7 != this.STRING_7) {
            this.STRING_7 = v.STRING_7    //object profile string;
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_8 != null && v.STRING_8!!.isNotEmpty() && v.STRING_8 != this.STRING_8) {
            this.STRING_8 = v.STRING_8    //object link;
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_9 != null && v.STRING_9!!.isNotEmpty() && v.STRING_9 != this.STRING_9) {
            this.STRING_9 = v.STRING_9    //object extension;
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_10 != null && v.STRING_10!!.isNotEmpty() && v.STRING_10 != this.STRING_10) {
            this.STRING_10 = v.STRING_10    //object extension;
            IS_UPDATED_BY_MERGE = true
        }

        if (v.STRING_20 != null && v.STRING_20!!.isNotEmpty() && v.STRING_20 != this.STRING_20) {
            this.STRING_20 = v.STRING_20
            IS_UPDATED_BY_MERGE = true
        }

        if (answerTypeValues.getIS_UPDATE_BLOB() == "1") {
            this.IDENTIFICATOR_2 = v.IDENTIFICATOR_2
            this.BLOB_1 = v.BLOB_1
            this.BLOB_2 = v.BLOB_2
            this.BLOB_3 = v.BLOB_3
            this.STRING_17 = v.answerTypeValues.GetAvatarServer()
            this.STRING_18 = v.answerTypeValues.GetAvatarLink()
            this.INTEGER_19 = v.answerTypeValues.GetAvatarOriginalSize()
            IS_UPDATED_BY_MERGE = true
        }

        if (answerTypeValues.getIS_UPDATE_SUBSCRIBE() == "1" || (v.STRING_14 != null && v.STRING_14!!.isNotEmpty())) {
            this.STRING_14 = v.STRING_14
            IS_UPDATED_BY_MERGE = true
        }

    }

    @JsName("get_BLOB_1_size")
    fun get_BLOB_1_size(): Int {
        return BLOB_1?.size ?: 0
    }

    @JsName("get_BLOB_2_size")
    fun get_BLOB_2_size(): Int {
        return BLOB_2?.size ?: 0
    }

    @JsName("get_BLOB_3_size")
    fun get_BLOB_3_size(): Int {
        return BLOB_3?.size ?: 0
    }

    @JsName("getCONNECTION_ID")
    fun getCONNECTION_ID(): Long {
        return LONG_19 ?: 0L
    }

    @JsName("setCONNECTION_ID")
    fun setCONNECTION_ID(v: Long) {
        LONG_19 = v
    }

    ///////////////////////////////////////////////////////////////////////////////

    fun GetJsocket():Jsocket{
        var j: Jsocket? = CLIENT_JSOCKET_POOL.removeFirstOrNull()
        if(j == null){
            j =  Jsocket()
            Jsocket.fill()
            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                println("CLIENT_JSOCKET_POOL is emprty")
            }
        }
        j.value_id1 = answerTypeValues.GetMainAccountId()
        j.value_id2 = answerTypeValues.GetAlbumId()
        j.value_id3 = answerTypeValues.GetMainAvatarId()
        j.value_id4 = answerTypeValues.GetObjectId()
        j.value_id5 = answerTypeValues.GetLinkOwner()
        when (RECORD_TYPE) {

            "4" //MESSEGES
            -> {
                j.value_par1 = answerTypeValues.GetMessegeId().toString()
            }
            "9" //CHATS_COST_TYPES
            -> {
                j.value_par1 = answerTypeValues.GetChatsCostTypeId().toString()
            }
            "A" //ALBUMS_COMMENTS
            -> {
                j.value_par2 = answerTypeValues.GetCommentId().toString()
            }
            "C" //ALBUMS_LINKS_COMMENTS
            -> {
                j.value_par2 = answerTypeValues.GetCommentId().toString()
            }
            "E" //OBJECTS_LINKS_COMMENTS
            -> {
                j.value_par2 = answerTypeValues.GetCommentId().toString()
            }
        }
        return j
    }

    ///////////////////////////////////////////////////////////////////////////////
    companion object {


        @JsName("fillPOOL")
        fun fillPOOL() {
            if (fillPOOL_IS_RUNNING.compareAndSet(expected = false, new = true)) {
                CoroutineScope(NonCancellable).launch {
                    try {
                        fillPOOL_IS_RUNNING.value = true
                        while (CLIENT_ANSWER_TYPE_POOL.size < Constants.CLIENT_ANSWER_TYPE_POOL_SIZE && !Constants.isInterrupted.value) {
                            CLIENT_ANSWER_TYPE_POOL.addLast(ANSWER_TYPE())
                        }
                    } finally {
                        fillPOOL_IS_RUNNING.value = false
                    }
                }
            }
        }

        @JsName("fillPOOLS")
        fun fillPOOLS() {
            if (fillPOOLS_IS_RUNNING.compareAndSet(expected = false, new = true)) {
                CoroutineScope(NonCancellable).launch {
                    withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                        try {
                            fillPOOLS_IS_RUNNING.value = true
                            CLIENT_ANSWER_TYPE_POOLS_Lock.lock()
                            while (CLIENT_ANSWER_TYPE_POOLS.size < Constants.CLIENT_ANSWER_TYPE_POOLS_SIZE && !Constants.isInterrupted.value) {
                                val ar: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

                                while (ar.size < Constants.CLIENT_ANSWER_TYPE_POOLS_CHUNK_SIZE) {
                                    var v: ANSWER_TYPE? = CLIENT_ANSWER_TYPE_POOL.removeFirstOrNull()
                                    if(v == null){
                                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                            println("CLIENT_ANSWER_TYPE_POOL is emprty")
                                        }
                                        fillPOOL()
                                        v = ANSWER_TYPE()
                                    }
                                    ar.addLast(v)
                                }
                                CLIENT_ANSWER_TYPE_POOLS.addLast(ar)
                            }
                        } finally {
                            fillPOOLS_IS_RUNNING.value = false
                            CLIENT_ANSWER_TYPE_POOLS_Lock.unlock()
                        }
                    }
                }
            }
        }

        @InternalAPI
        fun close() {
            CLIENT_ANSWER_TYPE_POOL.clear()
            CLIENT_ANSWER_TYPE_POOLS.clear()
        }
    }

}
