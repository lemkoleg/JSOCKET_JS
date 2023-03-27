@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

//import kotlin.js.JsName
import CrossPlatforms.PrintInformation
import co.touchlab.stately.concurrency.AtomicBoolean
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_client.Jsocket




//@JsName("FIELDS_SUBSCRIBE_ANSWER_TYPES")
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
    83 to ANSWER_TYPE_Subscribe(83, "BLOB_3", Constants.MAX_BIG_AVATAR_SIZE_B, false, 4),
    84 to ANSWER_TYPE_Subscribe(83, "BLOB_3", Constants.AVATARSIZE, false, 4)
)

private val CLIENT_ANSWER_TYPE_POOL: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
private val CLIENT_ANSWER_TYPE_POOLS: ArrayDeque<ArrayDeque<ANSWER_TYPE>> = ArrayDeque()



private val CLIENT_ANSWER_TYPE_POOL_Lock = Mutex()
private val CLIENT_ANSWER_TYPE_POOLS_Lock = Mutex()
private var fillPOOL_IS_RUNNING: AtomicBoolean = AtomicBoolean(false)
private var fillPOOLS_IS_RUNNING: AtomicBoolean = AtomicBoolean(false)

/**
 *
 * @author Oleg
 */
@Suppress("SetterBackingFieldAssignment")


@OptIn(KorioExperimentalApi::class)
//@JsName("ANSWER_TYPE")
class ANSWER_TYPE {

    //var SELF_Jsockt: Jsocket? = CLIENT_JSOCKET_POOL.removeFirstOrNull()

    init {
        /*
        if(SELF_Jsockt == null){
            SELF_Jsockt =  Jsocket()
            Jsocket.fill()
            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                PrintInformation.PRINT_INFO("CLIENT_JSOCKET_POOL is empty")
            }
        }
         */
        ensureNeverFrozen()
    }

    //@JsName("answerTypeValues")
    val answerTypeValues = AnswerTypeValues(this)


    //@JsName("IDENTIFICATORS")
    var IDENTIFICATORS: String? = null

    //@JsName("IDENTIFICATOR_1")
    var IDENTIFICATOR_1: String? = null

    //@JsName("IDENTIFICATOR_2")
    var IDENTIFICATOR_2: String? = null

    //@JsName("IDENTIFICATOR_3")
    var IDENTIFICATOR_3: String? = null


    //@JsName("IDENTIFICATOR_4")
    var IDENTIFICATOR_4: String? = null


    //@JsName("IDENTIFICATOR_5")
    var IDENTIFICATOR_5: String? = null


    //@JsName("IDENTIFICATOR_6")
    var IDENTIFICATOR_6: String? = null


    //@JsName("IDENTIFICATOR_7")
    var IDENTIFICATOR_7: String? = null


    //@JsName("IDENTIFICATOR_8")
    var IDENTIFICATOR_8: String? = null


    //@JsName("IDENTIFICATOR_9")
    var IDENTIFICATOR_9: String? = null


    //@JsName("IDENTIFICATOR_10")
    var IDENTIFICATOR_10: String? = null

    //@JsName("IDENTIFICATOR_11")
    var IDENTIFICATOR_11: String? = null


    //@JsName("IDENTIFICATOR_12")
    var IDENTIFICATOR_12: String? = null


    //@JsName("IDENTIFICATOR_13")
    var IDENTIFICATOR_13: String? = null


    //@JsName("IDENTIFICATOR_14")
    var IDENTIFICATOR_14: String? = null


    //@JsName("IDENTIFICATOR_15")
    var IDENTIFICATOR_15: String? = null


    //@JsName("IDENTIFICATOR_16")
    var IDENTIFICATOR_16: String? = null


    //@JsName("IDENTIFICATOR_17")
    var IDENTIFICATOR_17: String? = null


    //@JsName("IDENTIFICATOR_18")
    var IDENTIFICATOR_18: String? = null


    //@JsName("IDENTIFICATOR_19")
    var IDENTIFICATOR_19: String? = null


    //@JsName("IDENTIFICATOR_20")
    var IDENTIFICATOR_20: String? = null


    //@JsName("INTEGER_1")
    var INTEGER_1: Int? = null


    //@JsName("INTEGER_2")
    var INTEGER_2: Int? = null


    //@JsName("INTEGER_3")
    var INTEGER_3: Int? = null


    //@JsName("INTEGER_4")
    var INTEGER_4: Int? = null


    //@JsName("INTEGER_5")
    var INTEGER_5: Int? = null


    //@JsName("INTEGER_6")
    var INTEGER_6: Int? = null


    //@JsName("INTEGER_7")
    var INTEGER_7: Int? = null


    //@JsName("INTEGER_8")
    var INTEGER_8: Int? = null


    //@JsName("INTEGER_9")
    var INTEGER_9: Int? = null


    //@JsName("INTEGER_10")
    var INTEGER_10: Int? = null


    //@JsName("INTEGER_11")
    var INTEGER_11: Int? = null


    //@JsName("INTEGER_12")
    var INTEGER_12: Int? = null


    //@JsName("INTEGER_13")
    var INTEGER_13: Int? = null


    //@JsName("INTEGER_14")
    var INTEGER_14: Int? = null


    //@JsName("INTEGER_15")
    var INTEGER_15: Int? = null


    //@JsName("INTEGER_16")
    var INTEGER_16: Int? = null


    //@JsName("INTEGER_17")
    var INTEGER_17: Int? = null


    //@JsName("INTEGER_18")
    var INTEGER_18: Int? = null


    //@JsName("INTEGER_19")
    var INTEGER_19: Int? = null


    //@JsName("INTEGER_20")
    var INTEGER_20: Int = 0


    //@JsName("LONG_1")
    var LONG_1: Long? = null


    //@JsName("LONG_2")
    var LONG_2: Long? = null


    //@JsName("LONG_3")
    var LONG_3: Long? = null


    //@JsName("LONG_4")
    var LONG_4: Long? = null


    //@JsName("LONG_5")
    var LONG_5: Long? = null


    //@JsName("LONG_6")
    var LONG_6: Long? = null


    //@JsName("LONG_7")
    var LONG_7: Long? = null


    //@JsName("LONG_8")
    var LONG_8: Long? = null


    //@JsName("LONG_9")
    var LONG_9: Long? = null


    //@JsName("LONG_10")
    var LONG_10: Long? = null

    //@JsName("LONG_11")
    var LONG_11: Long? = null


    //@JsName("LONG_12")
    var LONG_12: Long? = null


    //@JsName("LONG_13")
    var LONG_13: Long? = null


    //@JsName("LONG_14")
    var LONG_14: Long? = null


    //@JsName("LONG_15")
    var LONG_15: Long? = null


    //@JsName("LONG_16")
    var LONG_16: Long? = null


    //@JsName("LONG_17")
    var LONG_17: Long? = null


    //@JsName("LONG_18")
    var LONG_18: Long? = null


    //@JsName("LONG_19")
    var LONG_19: Long? = null


    //@JsName("LONG_20")
    var LONG_20: Long = 0L
    /*
    set(v) {
        val v2 = v : 0
        if (v2 > 0 && v2 != field) {
            field = v2
            answerTypeValues.setOBJECT_ID_LAST_SELECT()
        }
    }
     */


    //@JsName("STRING_1")
    var STRING_1: String? = null


    //@JsName("STRING_2")
    var STRING_2: String? = null


    //@JsName("STRING_3")
    var STRING_3: String? = null


    //@JsName("STRING_4")
    var STRING_4: String? = null


    //@JsName("STRING_5")
    var STRING_5: String? = null


    //@JsName("STRING_6")
    var STRING_6: String? = null


    //@JsName("STRING_7")
    var STRING_7: String? = null


    //@JsName("STRING_8")
    var STRING_8: String? = null


    //@JsName("STRING_9")
    var STRING_9: String? = null


    //@JsName("STRING_10")
    var STRING_10: String? = null

    //@JsName("STRING_11")
    var STRING_11: String? = null


    //@JsName("STRING_12")
    var STRING_12: String? = null


    //@JsName("STRING_13")
    var STRING_13: String? = null


    //@JsName("STRING_14")
    var STRING_14: String? = null


    //@JsName("STRING_15")
    var STRING_15: String? = null


    //@JsName("STRING_16")
    var STRING_16: String? = null


    //@JsName("STRING_17")
    var STRING_17: String? = null


    //@JsName("STRING_18")
    var STRING_18: String? = null


    //@JsName("STRING_19")
    var STRING_19: String? = null

    //@JsName("STRING_20")
    var STRING_20: String = ""
        set(v) {
            if (v.isNotEmpty() && !v.equals(field)) {
                field = v
                answerTypeValues.DefineRECORD_TYPE()
            }
        }

    //@JsName("BLOB_1")
    var BLOB_1: ByteArray? = null

    //@JsName("BLOB_2")
    var BLOB_2: ByteArray? = null

    //@JsName("BLOB_3")
    var BLOB_3: ByteArray? = null

    //@JsName("BLOB_4")
    var BLOB_4: ByteArray? = null

    //@JsName("RECORD_TYPE")
    var RECORD_TYPE: String = ""
        set(v) {
            if (!v.equals(field)) {
                field = v
                answerTypeValues.initValues()
            }
        }

    //@JsName("RECORD_TABLE_ID")
    var RECORD_TABLE_ID: String = ""

    //@JsName("CASH_SUM")
    var CASH_SUM: String = ""

    //@JsName("IS_UPDATED_BY_MERGE")
    var IS_UPDATED_BY_MERGE: Boolean = false

    //@JsName("OBJECT_ID_LAST_SELECT")
    var OBJECT_ID_LAST_SELECT = ""


    //@JsName("merge")
    fun merge(v: ANSWER_TYPE) {

        callBackUpdatedData = v.callBackUpdatedData

        if (v.IDENTIFICATOR_1 != null) {
            this.IDENTIFICATOR_1 = v.IDENTIFICATOR_1
        }

        if (v.IDENTIFICATOR_2 != null) {
            this.IDENTIFICATOR_2 = v.IDENTIFICATOR_2
        }

        if (v.IDENTIFICATOR_3 != null) {
            this.IDENTIFICATOR_3 = v.IDENTIFICATOR_3
        }

        if (v.IDENTIFICATOR_4 != null) {
            this.IDENTIFICATOR_4 = v.IDENTIFICATOR_4
        }

        if (v.IDENTIFICATOR_5 != null) {
            this.IDENTIFICATOR_5 = v.IDENTIFICATOR_5
        }

        if (v.IDENTIFICATOR_6 != null) {
            this.IDENTIFICATOR_6 = v.IDENTIFICATOR_6
        }

        if (v.IDENTIFICATOR_7 != null) {
            this.IDENTIFICATOR_7 = v.IDENTIFICATOR_7
        }

        if (v.IDENTIFICATOR_8 != null) {
            this.IDENTIFICATOR_8 = v.IDENTIFICATOR_8
        }

        if (v.IDENTIFICATOR_9 != null) {
            this.IDENTIFICATOR_9 = v.IDENTIFICATOR_9
        }

        if (v.IDENTIFICATOR_10 != null) {
            this.IDENTIFICATOR_10 = v.IDENTIFICATOR_10
        }

        if (v.IDENTIFICATOR_11 != null) {
            this.IDENTIFICATOR_11 = v.IDENTIFICATOR_11
        }

        if (v.IDENTIFICATOR_12 != null) {
            this.IDENTIFICATOR_12 = v.IDENTIFICATOR_12
        }

        if (v.IDENTIFICATOR_13 != null) {
            this.IDENTIFICATOR_13 = v.IDENTIFICATOR_13
        }

        if (v.IDENTIFICATOR_14 != null) {
            this.IDENTIFICATOR_14 = v.IDENTIFICATOR_14
        }

        if (v.IDENTIFICATOR_15 != null) {
            this.IDENTIFICATOR_15 = v.IDENTIFICATOR_15
        }

        if (v.IDENTIFICATOR_16 != null) {
            this.IDENTIFICATOR_16 = v.IDENTIFICATOR_16
        }

        if (v.IDENTIFICATOR_17 != null) {
            this.IDENTIFICATOR_17 = v.IDENTIFICATOR_17
        }

        if (v.IDENTIFICATOR_18 != null) {
            this.IDENTIFICATOR_18 = v.IDENTIFICATOR_18
        }

        if (v.IDENTIFICATOR_19 != null) {
            this.IDENTIFICATOR_19 = v.IDENTIFICATOR_19
        }

        if (v.IDENTIFICATOR_20 != null) {
            this.IDENTIFICATOR_20 = v.IDENTIFICATOR_20
        }

        if (v.LONG_1 != null) {
            this.LONG_1 = v.LONG_1       // accounts1 last connect
        }

        if (v.LONG_2 != null) {
            this.LONG_2 = v.LONG_2      // accounts2 last connect
        }

        if (v.LONG_3 != null) {
            this.LONG_3 = v.LONG_3
        }

        if (v.LONG_4 != null) {
            this.LONG_4 = v.LONG_4
        }

        if (v.LONG_5 != null) {
            this.LONG_5 = v.LONG_5
        }

        if (v.LONG_6 != null) {
            this.LONG_6 = v.LONG_6
        }

        if (v.LONG_7 != null) {
            this.LONG_7 = v.LONG_7
        }

        if (v.LONG_8 != null) {
            this.LONG_8 = v.LONG_8
        }

        if (v.LONG_9 != null) {
            this.LONG_9 = v.LONG_9
        }

        if (v.LONG_10 != null) {
            this.LONG_10 = v.LONG_10
        }

        if (v.LONG_11 != null) {
            this.LONG_11 = v.LONG_11
        }

        if (v.LONG_12 != null) {
            this.LONG_12 = v.LONG_12
        }

        if (v.LONG_13 != null) {
            this.LONG_13 = v.LONG_13
        }

        if (v.LONG_14 != null) {
            this.LONG_14 = v.LONG_14
        }

        if (v.LONG_15 != null) {
            this.LONG_15 = v.LONG_15
        }

        if (v.LONG_16 != null) {
            this.LONG_16 = v.LONG_16
        }

        if (v.LONG_17 != null) {
            this.LONG_17 = v.LONG_17
        }

        if (v.LONG_18 != null) {
            this.LONG_18 = v.LONG_18
        }

        if (v.LONG_19 != null) {
            this.LONG_19 = v.LONG_19
        }

        if (v.LONG_20 != this.LONG_20) {
            this.LONG_20 = v.LONG_20
        }

        if (v.INTEGER_1 != null) {
            this.INTEGER_1 = v.INTEGER_1
        }

        if (v.INTEGER_2 != null) {
            this.INTEGER_2 = v.INTEGER_2
        }

        if (v.INTEGER_3 != null) {
            this.INTEGER_3 = v.INTEGER_3
        }

        if (v.INTEGER_4 != null) {
            this.INTEGER_4 = v.INTEGER_4  //object size
        }

        if (v.INTEGER_5 != null) {
            this.INTEGER_5 = v.INTEGER_5  // object length seconds
        }

        if (v.INTEGER_6 != null) {
            this.INTEGER_6 = v.INTEGER_6
        }

        if (v.INTEGER_7 != null) {
            this.INTEGER_7 = v.INTEGER_7
        }

        if (v.INTEGER_8 != null) {
            this.INTEGER_8 = v.INTEGER_8
        }

        if (v.INTEGER_9 != null) {
            this.INTEGER_9 = v.INTEGER_9
        }

        if (v.INTEGER_10 != null) {
            this.INTEGER_10 = v.INTEGER_10
        }

        if (v.INTEGER_11 != null) {
            this.INTEGER_11 = v.INTEGER_11
        }

        if (v.INTEGER_12 != null) {
            this.INTEGER_12 = v.INTEGER_12
        }

        if (v.INTEGER_13 != null) {
            this.INTEGER_13 = v.INTEGER_13
        }

        if (v.INTEGER_14 != null) {
            this.INTEGER_14 = v.INTEGER_14
        }

        if (v.INTEGER_15 != null) {
            this.INTEGER_15 = v.INTEGER_15
        }

        if (v.INTEGER_16 != null) {
            this.INTEGER_16 = v.INTEGER_16
        }

        if (v.INTEGER_17 != null) {
            this.INTEGER_17 = v.INTEGER_17
        }

        if (v.INTEGER_18 != null) {
            this.INTEGER_18 = v.INTEGER_18
        }

        if (v.INTEGER_19 != null) {
            this.INTEGER_19 = v.INTEGER_19
        }

        if (v.INTEGER_20 != this.INTEGER_20) {
            this.INTEGER_20 = v.INTEGER_20
        }

        if (v.STRING_1 != null) {
            this.STRING_1 = v.STRING_1    //accounts1 name
        }

        if (v.STRING_2 != null) {
            this.STRING_2 = v.STRING_2    //accounts1 access
        }

        if (v.STRING_3 != null) {
            this.STRING_3 = v.STRING_3    //accounts2 name
        }

        if (v.STRING_4 != null) {
            this.STRING_4 = v.STRING_4    //accounts2 access
        }

        if (v.STRING_5 != null) {
            this.STRING_5 = v.STRING_5    //object name
        }

        if (v.STRING_6 != null) {
            this.STRING_6 = v.STRING_6    //object server
        }

        if (v.STRING_7 != null) {
            this.STRING_7 = v.STRING_7    //object profile string;
        }

        if (v.STRING_8 != null) {
            this.STRING_8 = v.STRING_8    //object link;
        }

        if (v.STRING_9 != null) {
            this.STRING_9 = v.STRING_9    //object extension;
        }

        if (v.STRING_10 != null) {
            this.STRING_10 = v.STRING_10    //object extension;
        }

        if (v.STRING_20 != this.STRING_20) {
            this.STRING_20 = v.STRING_20
        }

        if (v.BLOB_1 != null) {
            this.BLOB_1 = v.BLOB_1
        }

        if (v.BLOB_2 != null) {
            this.BLOB_2 = v.BLOB_2
        }

        if (v.BLOB_3 != null) {
            this.BLOB_3 = v.BLOB_3
        }

        if (answerTypeValues.getIS_UPDATE_BLOB() == "1") {
            this.IDENTIFICATOR_2 = v.IDENTIFICATOR_2
            this.BLOB_1 = v.BLOB_1
            this.BLOB_2 = v.BLOB_2
            this.BLOB_3 = v.BLOB_3
            this.BLOB_4 = v.BLOB_4
            this.STRING_17 = v.answerTypeValues.GetAvatarServer()
            this.STRING_18 = v.answerTypeValues.GetAvatarLink()
            this.INTEGER_17 = v.answerTypeValues.GetAvatarOriginalSize()

        }

        if (answerTypeValues.getIS_UPDATE_SUBSCRIBE() == "1") {
            this.STRING_14 = v.STRING_14

        }

        //callBackUpdatedData(null)
    }

    //@JsName("get_BLOB_1_size")
    fun get_BLOB_1_size(): Int {
        return BLOB_1?.size ?: 0
    }

    //@JsName("get_BLOB_2_size")
    fun get_BLOB_2_size(): Int {
        return BLOB_2?.size ?: 0
    }

    //@JsName("get_BLOB_3_size")
    fun get_BLOB_3_size(): Int {
        return BLOB_3?.size ?: 0
    }

    //@JsName("get_BLOB_4_size")
    fun get_BLOB_4_size(): Int {
        return BLOB_4?.size ?: 0
    }

    //@JsName("callBackUpdatedData")
    var callBackUpdatedData: ((v: Any?) -> Any?) = {}


    ///////////////////////////////////////////////////////////////////////////////

    fun GetJsocket(): Jsocket {
        var j: Jsocket? = Jsocket.GetJsocket()
        if (j == null) {
            j = Jsocket()
            Jsocket.fill()
            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                PrintInformation.PRINT_INFO("CLIENT_JSOCKET_POOL is empty")
            }
        }
        j.object_size = answerTypeValues.GetObjectSize().toLong()
        j.object_extension = answerTypeValues.GetObjectExtension()
        j.object_server = answerTypeValues.GetObjectServer()
        j.value_id1 = answerTypeValues.GetMainAccountId()
        j.value_id2 = answerTypeValues.GetAlbumId()
        j.value_id3 = answerTypeValues.GetMainAvatarId()
        j.value_id4 = answerTypeValues.GetObjectId()
        j.value_id5 = answerTypeValues.GetLinkOwner()
        j.check_sum = CASH_SUM
        j.local_answer_type = this
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

    fun print() {
        println("Print ANSWER_TYPE:  RECORD_TYPE = ${this.RECORD_TYPE}")
        if (this.IDENTIFICATOR_1 != null && this.IDENTIFICATOR_1!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_1 ${this.IDENTIFICATOR_1}")
        }
        if (this.IDENTIFICATOR_2 != null && this.IDENTIFICATOR_2!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_2 ${this.IDENTIFICATOR_2}")
        }
        if (this.IDENTIFICATOR_3 != null && this.IDENTIFICATOR_3!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_3 ${this.IDENTIFICATOR_3}")
        }
        if (this.IDENTIFICATOR_4 != null && this.IDENTIFICATOR_4!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_4 ${this.IDENTIFICATOR_4}")
        }
        if (this.IDENTIFICATOR_5 != null && this.IDENTIFICATOR_5!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_5 ${this.IDENTIFICATOR_5}")
        }
        if (this.IDENTIFICATOR_6 != null && this.IDENTIFICATOR_6!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_6 ${this.IDENTIFICATOR_6}")
        }
        if (this.IDENTIFICATOR_7 != null && this.IDENTIFICATOR_7!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_7 ${this.IDENTIFICATOR_7}")
        }
        if (this.IDENTIFICATOR_8 != null && this.IDENTIFICATOR_8!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_8 ${this.IDENTIFICATOR_8}")
        }
        if (this.IDENTIFICATOR_9 != null && this.IDENTIFICATOR_9!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_9 ${this.IDENTIFICATOR_9}")
        }
        if (this.IDENTIFICATOR_10 != null && this.IDENTIFICATOR_10!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_10 ${this.IDENTIFICATOR_10}")
        }
        if (this.IDENTIFICATOR_11 != null && this.IDENTIFICATOR_11!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_11 ${this.IDENTIFICATOR_11}")
        }
        if (this.IDENTIFICATOR_12 != null && this.IDENTIFICATOR_12!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_12 ${this.IDENTIFICATOR_12}")
        }
        if (this.IDENTIFICATOR_13 != null && this.IDENTIFICATOR_13!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_13 ${this.IDENTIFICATOR_13}")
        }
        if (this.IDENTIFICATOR_14 != null && this.IDENTIFICATOR_14!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_14 ${this.IDENTIFICATOR_14}")
        }
        if (this.IDENTIFICATOR_15 != null && this.IDENTIFICATOR_15!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_15 ${this.IDENTIFICATOR_15}")
        }
        if (this.IDENTIFICATOR_16 != null && this.IDENTIFICATOR_16!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_16 ${this.IDENTIFICATOR_16}")
        }
        if (this.IDENTIFICATOR_17 != null && this.IDENTIFICATOR_17!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_17 ${this.IDENTIFICATOR_17}")
        }
        if (this.IDENTIFICATOR_18 != null && this.IDENTIFICATOR_18!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_18 ${this.IDENTIFICATOR_18}")
        }
        if (this.IDENTIFICATOR_19 != null && this.IDENTIFICATOR_19!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_19 ${this.IDENTIFICATOR_19}")
        }
        if (this.IDENTIFICATOR_20 != null && this.IDENTIFICATOR_20!!.isNotEmpty()) {
            println("nswer_type.IDENTIFICATOR_20 ${this.IDENTIFICATOR_20}")
        }
        if (this.INTEGER_1 != null && this.INTEGER_1!! > 0) {
            println("nswer_type.INTEGER_1 ${this.INTEGER_1}")
        }
        if (this.INTEGER_2 != null && this.INTEGER_2!! > 0) {
            println("nswer_type.INTEGER_2 ${this.INTEGER_2}")
        }
        if (this.INTEGER_3 != null && this.INTEGER_3!! > 0) {
            println("nswer_type.INTEGER_3 ${this.INTEGER_3}")
        }
        if (this.INTEGER_4 != null && this.INTEGER_4!! > 0) {
            println("nswer_type.INTEGER_4 ${this.INTEGER_4}")
        }
        if (this.INTEGER_5 != null && this.INTEGER_5!! > 0) {
            println("nswer_type.INTEGER_5 ${this.INTEGER_5}")
        }
        if (this.INTEGER_6 != null && this.INTEGER_6!! > 0) {
            println("nswer_type.INTEGER_6 ${this.INTEGER_6}")
        }
        if (this.INTEGER_7 != null && this.INTEGER_7!! > 0) {
            println("nswer_type.INTEGER_7 ${this.INTEGER_7}")
        }
        if (this.INTEGER_8 != null && this.INTEGER_8!! > 0) {
            println("nswer_type.INTEGER_8 ${this.INTEGER_8}")
        }
        if (this.INTEGER_9 != null && this.INTEGER_9!! > 0) {
            println("nswer_type.INTEGER_9 ${this.INTEGER_9}")
        }
        if (this.INTEGER_10 != null && this.INTEGER_10!! > 0) {
            println("nswer_type.INTEGER_10 ${this.INTEGER_10}")
        }
        if (this.INTEGER_11 != null && this.INTEGER_11!! > 0) {
            println("nswer_type.INTEGER_11 ${this.INTEGER_11}")
        }
        if (this.INTEGER_12 != null && this.INTEGER_12!! > 0) {
            println("nswer_type.INTEGER_12 ${this.INTEGER_12}")
        }
        if (this.INTEGER_13 != null && this.INTEGER_13!! > 0) {
            println("nswer_type.INTEGER_13 ${this.INTEGER_13}")
        }
        if (this.INTEGER_14 != null && this.INTEGER_14!! > 0) {
            println("nswer_type.INTEGER_14 ${this.INTEGER_14}")
        }
        if (this.INTEGER_15 != null && this.INTEGER_15!! > 0) {
            println("nswer_type.INTEGER_15 ${this.INTEGER_15}")
        }
        if (this.INTEGER_16 != null && this.INTEGER_16!! > 0) {
            println("nswer_type.INTEGER_16 ${this.INTEGER_16}")
        }
        if (this.INTEGER_17 != null && this.INTEGER_17!! > 0) {
            println("nswer_type.INTEGER_17 ${this.INTEGER_17}")
        }
        if (this.INTEGER_18 != null && this.INTEGER_18!! > 0) {
            println("nswer_type.INTEGER_18 ${this.INTEGER_18}")
        }
        if (this.INTEGER_19 != null && this.INTEGER_19!! > 0) {
            println("nswer_type.INTEGER_19 ${this.INTEGER_19}")
        }
        if (this.INTEGER_20 > 0) {
            println("nswer_type.INTEGER_20 ${this.INTEGER_20}")
        }
        if (this.STRING_1 != null && this.STRING_1!!.isNotEmpty()) {
            println("nswer_type.STRING_1 ${this.STRING_1}")
        }
        if (this.STRING_2 != null && this.STRING_2!!.isNotEmpty()) {
            println("nswer_type.STRING_2 ${this.STRING_2}")
        }
        if (this.STRING_3 != null && this.STRING_3!!.isNotEmpty()) {
            println("nswer_type.STRING_3 ${this.STRING_3}")
        }
        if (this.STRING_4 != null && this.STRING_4!!.isNotEmpty()) {
            println("nswer_type.STRING_4 ${this.STRING_4}")
        }
        if (this.STRING_5 != null && this.STRING_5!!.isNotEmpty()) {
            println("nswer_type.STRING_5 ${this.STRING_5}")
        }
        if (this.STRING_6 != null && this.STRING_6!!.isNotEmpty()) {
            println("nswer_type.STRING_6 ${this.STRING_6}")
        }
        if (this.STRING_7 != null && this.STRING_7!!.isNotEmpty()) {
            println("nswer_type.STRING_7 ${this.STRING_7}")
        }
        if (this.STRING_8 != null && this.STRING_8!!.isNotEmpty()) {
            println("nswer_type.STRING_8 ${this.STRING_8}")
        }
        if (this.STRING_9 != null && this.STRING_9!!.isNotEmpty()) {
            println("nswer_type.STRING_9 ${this.STRING_9}")
        }
        if (this.STRING_10 != null && this.STRING_10!!.isNotEmpty()) {
            println("nswer_type.STRING_10 ${this.STRING_10}")
        }
        if (this.STRING_11 != null && this.STRING_11!!.isNotEmpty()) {
            println("nswer_type.STRING_11 ${this.STRING_11}")
        }
        if (this.STRING_12 != null && this.STRING_12!!.isNotEmpty()) {
            println("nswer_type.STRING_12 ${this.STRING_12}")
        }
        if (this.STRING_13 != null && this.STRING_13!!.isNotEmpty()) {
            println("nswer_type.STRING_13 ${this.STRING_13}")
        }
        if (this.STRING_14 != null && this.STRING_14!!.isNotEmpty()) {
            println("nswer_type.STRING_14 ${this.STRING_14}")
        }
        if (this.STRING_15 != null && this.STRING_15!!.isNotEmpty()) {
            println("nswer_type.STRING_15 ${this.STRING_15}")
        }
        if (this.STRING_16 != null && this.STRING_16!!.isNotEmpty()) {
            println("nswer_type.STRING_16 ${this.STRING_16}")
        }
        if (this.STRING_17 != null && this.STRING_17!!.isNotEmpty()) {
            println("nswer_type.STRING_17 ${this.STRING_17}")
        }
        if (this.STRING_18 != null && this.STRING_18!!.isNotEmpty()) {
            println("nswer_type.STRING_18 ${this.STRING_18}")
        }
        if (this.STRING_19 != null && this.STRING_19!!.isNotEmpty()) {
            println("nswer_type.STRING_19 ${this.STRING_19}")
        }
        if (this.STRING_20.isNotEmpty()) {
            println("nswer_type.STRING_20 ${this.STRING_20}")
        }
        if (this.LONG_1 != null && this.LONG_1!! > 0L) {
            println("nswer_type.LONG_1 ${this.LONG_1}")
        }
        if (this.LONG_2 != null && this.LONG_2!! > 0L) {
            println("nswer_type.LONG_2 ${this.LONG_2}")
        }
        if (this.LONG_3 != null && this.LONG_3!! > 0L) {
            println("nswer_type.LONG_3 ${this.LONG_3}")
        }
        if (this.LONG_4 != null && this.LONG_4!! > 0L) {
            println("nswer_type.LONG_4 ${this.LONG_4}")
        }
        if (this.LONG_5 != null && this.LONG_5!! > 0L) {
            println("nswer_type.LONG_5 ${this.LONG_5}")
        }
        if (this.LONG_6 != null && this.LONG_6!! > 0L) {
            println("nswer_type.LONG_6 ${this.LONG_6}")
        }
        if (this.LONG_7 != null && this.LONG_7!! > 0L) {
            println("nswer_type.LONG_7 ${this.LONG_7}")
        }
        if (this.LONG_8 != null && this.LONG_8!! > 0L) {
            println("nswer_type.LONG_8 ${this.LONG_8}")
        }
        if (this.LONG_9 != null && this.LONG_9!! > 0L) {
            println("nswer_type.LONG_9 ${this.LONG_9}")
        }
        if (this.LONG_10 != null && this.LONG_10!! > 0L) {
            println("nswer_type.LONG_10 ${this.LONG_10}")
        }
        if (this.LONG_11 != null && this.LONG_11!! > 0L) {
            println("nswer_type.LONG_11 ${this.LONG_11}")
        }
        if (this.LONG_12 != null && this.LONG_12!! > 0L) {
            println("nswer_type.LONG_12 ${this.LONG_12}")
        }
        if (this.LONG_13 != null && this.LONG_13!! > 0L) {
            println("nswer_type.LONG_13 ${this.LONG_13}")
        }
        if (this.LONG_14 != null && this.LONG_14!! > 0L) {
            println("nswer_type.LONG_14 ${this.LONG_14}")
        }
        if (this.LONG_15 != null && this.LONG_15!! > 0L) {
            println("nswer_type.LONG_15 ${this.LONG_15}")
        }
        if (this.LONG_16 != null && this.LONG_16!! > 0L) {
            println("nswer_type.LONG_16 ${this.LONG_16}")
        }
        if (this.LONG_17 != null && this.LONG_17!! > 0L) {
            println("nswer_type.LONG_17 ${this.LONG_17}")
        }
        if (this.LONG_18 != null && this.LONG_18!! > 0L) {
            println("nswer_type.LONG_18 ${this.LONG_18}")
        }
        if (this.LONG_19 != null && this.LONG_19!! > 0L) {
            println("nswer_type.LONG_19 ${this.LONG_19}")
        }
        if (this.LONG_20 > 0L) {
            println("nswer_type.LONG_20 ${this.LONG_20}")
        }
        if (this.BLOB_1 != null) {
            println("nswer_type.BLOB_1 ${this.BLOB_1!!.size}")
        }
        if (this.BLOB_2 != null) {
            println("nswer_type.BLOB_2 ${this.BLOB_2!!.size}")
        }
        if (this.BLOB_3 != null) {
            println("nswer_type.BLOB_3 ${this.BLOB_3!!.size}")
        }

    }


    ///////////////////////////////////////////////////////////////////////////////
    companion object {

        fun GetAnswerType(): ANSWER_TYPE? {
            if (CLIENT_ANSWER_TYPE_POOL_Lock.tryLock()) {
                try {
                    var l: ANSWER_TYPE? = CLIENT_ANSWER_TYPE_POOL.removeFirstOrNull()
                    if (l == null) {
                        fillPOOL()
                        l = ANSWER_TYPE()
                    }
                    return l
                } finally {
                    CLIENT_ANSWER_TYPE_POOL_Lock.unlock()
                }
            }
            return null
        }

        fun GetAnswerTypes(): ArrayDeque<ANSWER_TYPE>? {
            if (CLIENT_ANSWER_TYPE_POOLS_Lock.tryLock()) {
                try {
                    val l: ArrayDeque<ANSWER_TYPE>? = CLIENT_ANSWER_TYPE_POOLS.removeFirstOrNull()
                    if (l == null) {
                        fillPOOLS()
                    }
                    return l
                } finally {
                    CLIENT_ANSWER_TYPE_POOLS_Lock.unlock()
                }
            }
            return null
        }


        //@JsName("fillPOOL")
        fun fillPOOL() {
            if (fillPOOL_IS_RUNNING.compareAndSet(expected = false, new = true)) {
                CoroutineScope(NonCancellable).launch {
                    withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                        CLIENT_ANSWER_TYPE_POOL_Lock.withLock {
                            try {
                                fillPOOL_IS_RUNNING.value = true
                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("ANSEWR_TYPE's fill pool is run...")
                                }
                                while (CLIENT_ANSWER_TYPE_POOL.size < Constants.CLIENT_ANSWER_TYPE_POOL_SIZE) {
                                    CLIENT_ANSWER_TYPE_POOL.addLast(ANSWER_TYPE())
                                    if(Constants.isInterrupted.value){
                                        break
                                    }
                                }
                            } finally {
                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("ANSEWR_TYPE's fill pool finished")
                                }
                                fillPOOL_IS_RUNNING.value = false
                            }
                        }
                    } ?: throw my_user_exceptions_class(
                        l_class_name = "ANSWER_TYPE",
                        l_function_name = "fillPOOL",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "Time out is up"
                    )
                }
            }
        }

        //@JsName("fillPOOLS")
        fun fillPOOLS() {
            if (fillPOOLS_IS_RUNNING.compareAndSet(expected = false, new = true)) {
                CoroutineScope(NonCancellable).launch {
                    withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                        CLIENT_ANSWER_TYPE_POOLS_Lock.withLock {
                            try {
                                fillPOOLS_IS_RUNNING.value = true
                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("ANSEWR_TYPE's fill pools is run...")
                                }
                                while (CLIENT_ANSWER_TYPE_POOLS.size < Constants.CLIENT_ANSWER_TYPE_POOLS_SIZE) {
                                    if(Constants.isInterrupted.value) break
                                    val ar: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

                                    while (ar.size < Constants.CLIENT_ANSWER_TYPE_POOLS_CHUNK_SIZE) {
                                        ar.addLast(GetAnswerType() ?: ANSWER_TYPE())
                                    }
                                    CLIENT_ANSWER_TYPE_POOLS.addLast(ar)
                                }
                            } finally {
                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("ANSEWR_TYPE's fill pools finished")
                                }
                                fillPOOLS_IS_RUNNING.value = false
                            }
                        }
                    } ?: throw my_user_exceptions_class(
                        l_class_name = "ANSWER_TYPE",
                        l_function_name = "fillPOOLS",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "Time out is up"
                    )
                }
            }
        }

        
        fun close() {
            CLIENT_ANSWER_TYPE_POOL.clear()
            CLIENT_ANSWER_TYPE_POOLS.clear()
        }
    }

}
