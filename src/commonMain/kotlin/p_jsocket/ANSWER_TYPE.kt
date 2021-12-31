@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

import kotlin.js.JsName

@JsName("BLOB_SIZE")
private const val BLOB_SIZE = 32768

@JsName("FIELDS_SUBSCRIBE_ANSWER_TYPES")
val FIELDS_SUBSCRIBE_ANSWER_TYPES: Map<Int, ANSWER_TYPE_Subscribe?> = mapOf(
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
    81 to ANSWER_TYPE_Subscribe(81, "BLOB_1", BLOB_SIZE, false, 4),
    82 to ANSWER_TYPE_Subscribe(82, "BLOB_2", BLOB_SIZE, false, 4),
    83 to ANSWER_TYPE_Subscribe(83, "BLOB_3", BLOB_SIZE, false, 4),
    84 to ANSWER_TYPE_Subscribe(84, "BLOB_4", BLOB_SIZE, false, 4),
    85 to ANSWER_TYPE_Subscribe(85, "BLOB_5", BLOB_SIZE, false, 4),
    86 to ANSWER_TYPE_Subscribe(86, "BLOB_6", BLOB_SIZE, false, 4),
    87 to ANSWER_TYPE_Subscribe(87, "BLOB_7", BLOB_SIZE, false, 4),
    88 to ANSWER_TYPE_Subscribe(88, "BLOB_8", BLOB_SIZE, false, 4),
    89 to ANSWER_TYPE_Subscribe(89, "BLOB_9", BLOB_SIZE, false, 4),
    90 to ANSWER_TYPE_Subscribe(90, "BLOB_10", BLOB_SIZE, false, 4)
)

/**
 *
 * @author Oleg
 */
@JsName("ANSWER_TYPE")
class ANSWER_TYPE(){

    @JsName("IDENTIFICATORS")
    var IDENTIFICATORS: String? = ""
        private set

    @JsName("IDENTIFICATOR_1")
    var IDENTIFICATOR_1: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_2")
    var IDENTIFICATOR_2: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_3")
    var IDENTIFICATOR_3: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_4")
    var IDENTIFICATOR_4: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_5")
    var IDENTIFICATOR_5: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_6")
    var IDENTIFICATOR_6: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_7")
    var IDENTIFICATOR_7: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_8")
    var IDENTIFICATOR_8: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_9")
    var IDENTIFICATOR_9: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_10")
    var IDENTIFICATOR_10: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }
    @JsName("IDENTIFICATOR_11")
    var IDENTIFICATOR_11: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_12")
    var IDENTIFICATOR_12: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_13")
    var IDENTIFICATOR_13: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_14")
    var IDENTIFICATOR_14: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_15")
    var IDENTIFICATOR_15: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_16")
    var IDENTIFICATOR_16: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_17")
    var IDENTIFICATOR_17: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_18")
    var IDENTIFICATOR_18: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_19")
    var IDENTIFICATOR_19: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("IDENTIFICATOR_20")
    var IDENTIFICATOR_20: String? = ""
        set(value) {
            field = value?.trim() ?: ""
            if (field!!.length != 18) {
                field = ""
            }
        }

    @JsName("INTEGER_1")
    var INTEGER_1: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_2")
    var INTEGER_2: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_3")
    var INTEGER_3: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_4")
    var INTEGER_4: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_5")
    var INTEGER_5: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_6")
    var INTEGER_6: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_7")
    var INTEGER_7: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_8")
    var INTEGER_8: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_9")
    var INTEGER_9: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_10")
    var INTEGER_10: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_11")
    var INTEGER_11: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_12")
    var INTEGER_12: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_13")
    var INTEGER_13: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_14")
    var INTEGER_14: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_15")
    var INTEGER_15: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_16")
    var INTEGER_16: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_17")
    var INTEGER_17: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_18")
    var INTEGER_18: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_19")
    var INTEGER_19: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("INTEGER_20")
    var INTEGER_20: Int? = 0
        set(value) {
            field = value ?: 0
        }

    @JsName("LONG_1")
    var LONG_1: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_2")
    var LONG_2: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_3")
    var LONG_3: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_4")
    var LONG_4: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_5")
    var LONG_5: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_6")
    var LONG_6: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_7")
    var LONG_7: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_8")
    var LONG_8: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_9")
    var LONG_9: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_10")  // reserved fo parameters of request in SQLite (replacment BLOB)
    var LONG_10: Long? = 0L
        set(value) {
            field = value ?: 0L
        }
    @JsName("LONG_11")
    var LONG_11: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_12")
    var LONG_12: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_13")
    var LONG_13: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_14")
    var LONG_14: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_15")
    var LONG_15: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_16")
    var LONG_16: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_17")
    var LONG_17: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_18")
    var LONG_18: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_19")
    var LONG_19: Long? = 0L
        set(value) {
            field = value ?: 0L
        }

    @JsName("LONG_20")  // reserved fo parameters of request in SQLite (replacment BLOB)
    var LONG_20: Long? = 1000000000000000000L
        set(value) {
            field = value ?: 0L
        }

    @JsName("STRING_1")
    var STRING_1: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_2")
    var STRING_2: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_3")
    var STRING_3: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_4")
    var STRING_4: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_5")
    var STRING_5: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_6")
    var STRING_6: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_7")
    var STRING_7: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_8")
    var STRING_8: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_9")
    var STRING_9: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_10")
    var STRING_10: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }
    @JsName("STRING_11")
    var STRING_11: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_12")
    var STRING_12: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_13")
    var STRING_13: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_14")
    var STRING_14: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_15")
    var STRING_15: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_16")
    var STRING_16: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_17")
    var STRING_17: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_18")
    var STRING_18: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_19")
    var STRING_19: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("STRING_20")
    var STRING_20: String? = ""
        set(value) {
            field = value?.trim() ?: ""
        }

    @JsName("BLOB_1")
    var BLOB_1: ByteArray? = null

    @JsName("BLOB_2")
    var BLOB_2: ByteArray? = null

    @JsName("BLOB_3")
    var BLOB_3: ByteArray? = null

    @JsName("BLOB_4")
    var BLOB_4: ByteArray? = null

    @JsName("BLOB_5")
    var BLOB_5: ByteArray? = null

    @JsName("BLOB_6")
    var BLOB_6: ByteArray? = null

    @JsName("BLOB_7")
    var BLOB_7: ByteArray? = null

    @JsName("BLOB_8")
    var BLOB_8: ByteArray? = null

    @JsName("BLOB_9")
    var BLOB_9: ByteArray? = null

    @JsName("BLOB_10")
    var BLOB_10: ByteArray? = null

    constructor(
        lIDENTIFICATOR_1: String? = "",
        lIDENTIFICATOR_2: String? = "",
        lIDENTIFICATOR_3: String? = "",
        lIDENTIFICATOR_4: String? = "",
        lIDENTIFICATOR_5: String? = "",
        lIDENTIFICATOR_6: String? = "",
        lIDENTIFICATOR_7: String? = "",
        lIDENTIFICATOR_8: String? = "",
        lIDENTIFICATOR_9: String? = "",
        lIDENTIFICATOR_10: String? = "",
        lIDENTIFICATOR_11: String? = "",
        lIDENTIFICATOR_12: String? = "",
        lIDENTIFICATOR_13: String? = "",
        lIDENTIFICATOR_14: String? = "",
        lIDENTIFICATOR_15: String? = "",
        lIDENTIFICATOR_16: String? = "",
        lIDENTIFICATOR_17: String? = "",
        lIDENTIFICATOR_18: String? = "",
        lIDENTIFICATOR_19: String? = "",
        lIDENTIFICATOR_20: String? = "",
        lInt_1: Int? = 0,
        lInt_2: Int? = 0,
        lInt_3: Int? = 0,
        lInt_4: Int? = 0,
        lInt_5: Int? = 0,
        lInt_6: Int? = 0,
        lInt_7: Int? = 0,
        lInt_8: Int? = 0,
        lInt_9: Int? = 0,
        lInt_10: Int? = 0,
        lInt_11: Int? = 0,
        lInt_12: Int? = 0,
        lInt_13: Int? = 0,
        lInt_14: Int? = 0,
        lInt_15: Int? = 0,
        lInt_16: Int? = 0,
        lInt_17: Int? = 0,
        lInt_18: Int? = 0,
        lInt_19: Int? = 0,
        lInt_20: Int? = 0,
        lLONG_1: Long? = 0L,
        lLONG_2: Long? = 0L,
        lLONG_3: Long? = 0L,
        lLONG_4: Long? = 0L,
        lLONG_5: Long? = 0L,
        lLONG_6: Long? = 0L,
        lLONG_7: Long? = 0L,
        lLONG_8: Long? = 0L,
        lLONG_9: Long? = 0L,
        lLONG_10: Long? = 0L,
        lLONG_11: Long? = 0L,
        lLONG_12: Long? = 0L,
        lLONG_13: Long? = 0L,
        lLONG_14: Long? = 0L,
        lLONG_15: Long? = 0L,
        lLONG_16: Long? = 0L,
        lLONG_17: Long? = 0L,
        lLONG_18: Long? = 0L,
        lLONG_19: Long? = 0L,
        lLONG_20: Long? = 0L,
        lSTRING_1: String? = "",
        lSTRING_2: String? = "",
        lSTRING_3: String? = "",
        lSTRING_4: String? = "",
        lSTRING_5: String? = "",
        lSTRING_6: String? = "",
        lSTRING_7: String? = "",
        lSTRING_8: String? = "",
        lSTRING_9: String? = "",
        lSTRING_10: String? = "",
        lSTRING_11: String? = "",
        lSTRING_12: String? = "",
        lSTRING_13: String? = "",
        lSTRING_14: String? = "",
        lSTRING_15: String? = "",
        lSTRING_16: String? = "",
        lSTRING_17: String? = "",
        lSTRING_18: String? = "",
        lSTRING_19: String? = "",
        lSTRING_20: String? = "",
        lBLOB_1: ByteArray?,
        lBLOB_2: ByteArray?,
        lBLOB_3: ByteArray?,
        lBLOB_4: ByteArray?,
        lBLOB_5: ByteArray?,
        lBLOB_6: ByteArray?,
        lBLOB_7: ByteArray?,
        lBLOB_8: ByteArray?,
        lBLOB_9: ByteArray?,
        lBLOB_10: ByteArray?
    ) : this() {
        IDENTIFICATOR_1 = lIDENTIFICATOR_1?.trim() ?: ""
        if (IDENTIFICATOR_1!!.length != 18) {
            IDENTIFICATOR_1 = ""
        }
        IDENTIFICATOR_2 = lIDENTIFICATOR_2?.trim() ?: ""
        if (IDENTIFICATOR_2!!.length != 18) {
            IDENTIFICATOR_2 = ""
        }
        IDENTIFICATOR_3 = lIDENTIFICATOR_3?.trim() ?: ""
        if (IDENTIFICATOR_3!!.length != 18) {
            IDENTIFICATOR_3 = ""
        }
        IDENTIFICATOR_4 = lIDENTIFICATOR_4?.trim() ?: ""
        if (IDENTIFICATOR_4!!.length != 18) {
            IDENTIFICATOR_4 = ""
        }
        IDENTIFICATOR_5 = lIDENTIFICATOR_5?.trim() ?: ""
        if (IDENTIFICATOR_5!!.length != 18) {
            IDENTIFICATOR_5 = ""
        }
        IDENTIFICATOR_6 = lIDENTIFICATOR_6?.trim() ?: ""
        if (IDENTIFICATOR_6!!.length != 18) {
            IDENTIFICATOR_6 = ""
        }
        IDENTIFICATOR_7 = lIDENTIFICATOR_7?.trim() ?: ""
        if (IDENTIFICATOR_7!!.length != 18) {
            IDENTIFICATOR_7 = ""
        }
        IDENTIFICATOR_8 = lIDENTIFICATOR_8?.trim() ?: ""
        if (IDENTIFICATOR_8!!.length != 18) {
            IDENTIFICATOR_8 = ""
        }
        IDENTIFICATOR_9 = lIDENTIFICATOR_9?.trim() ?: ""
        if (IDENTIFICATOR_9!!.length != 18) {
            IDENTIFICATOR_9 = ""
        }
        IDENTIFICATOR_10 = lIDENTIFICATOR_10?.trim() ?: ""
        if (IDENTIFICATOR_10!!.length != 18) {
            IDENTIFICATOR_10 = ""
        }
        IDENTIFICATOR_11 = lIDENTIFICATOR_11?.trim() ?: ""
        if (IDENTIFICATOR_11!!.length != 18) {
            IDENTIFICATOR_11 = ""
        }
        IDENTIFICATOR_12 = lIDENTIFICATOR_12?.trim() ?: ""
        if (IDENTIFICATOR_12!!.length != 18) {
            IDENTIFICATOR_12 = ""
        }
        IDENTIFICATOR_13 = lIDENTIFICATOR_13?.trim() ?: ""
        if (IDENTIFICATOR_13!!.length != 18) {
            IDENTIFICATOR_13 = ""
        }
        IDENTIFICATOR_14 = lIDENTIFICATOR_14?.trim() ?: ""
        if (IDENTIFICATOR_14!!.length != 18) {
            IDENTIFICATOR_14 = ""
        }
        IDENTIFICATOR_15 = lIDENTIFICATOR_15?.trim() ?: ""
        if (IDENTIFICATOR_15!!.length != 18) {
            IDENTIFICATOR_15 = ""
        }
        IDENTIFICATOR_16 = lIDENTIFICATOR_16?.trim() ?: ""
        if (IDENTIFICATOR_16!!.length != 18) {
            IDENTIFICATOR_16 = ""
        }
        IDENTIFICATOR_17 = lIDENTIFICATOR_17?.trim() ?: ""
        if (IDENTIFICATOR_17!!.length != 18) {
            IDENTIFICATOR_17 = ""
        }
        IDENTIFICATOR_18 = lIDENTIFICATOR_18?.trim() ?: ""
        if (IDENTIFICATOR_18!!.length != 18) {
            IDENTIFICATOR_18 = ""
        }
        IDENTIFICATOR_19 = lIDENTIFICATOR_19?.trim() ?: ""
        if (IDENTIFICATOR_19!!.length != 18) {
            IDENTIFICATOR_19 = ""
        }
        IDENTIFICATOR_20 = lIDENTIFICATOR_20?.trim() ?: ""
        if (IDENTIFICATOR_20!!.length != 18) {
            IDENTIFICATOR_20 = ""
        }
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
        STRING_20 = lSTRING_20?.trim() ?: ""
        BLOB_1 = lBLOB_1
        BLOB_2 = lBLOB_2
        BLOB_3 = lBLOB_3
        BLOB_4 = lBLOB_4
        BLOB_5 = lBLOB_5
        BLOB_6 = lBLOB_6
        BLOB_7 = lBLOB_7
        BLOB_8 = lBLOB_8
        BLOB_9 = lBLOB_9
        BLOB_10 = lBLOB_10
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
        STRING_20 = MyANSWER_TYPE.STRING_20?.trim() ?: ""
        BLOB_1 = MyANSWER_TYPE.BLOB_1
        BLOB_2 = MyANSWER_TYPE.BLOB_2
        BLOB_3 = MyANSWER_TYPE.BLOB_3
        BLOB_4 = MyANSWER_TYPE.BLOB_4
        BLOB_5 = MyANSWER_TYPE.BLOB_5
        BLOB_6 = MyANSWER_TYPE.BLOB_6
        BLOB_7 = MyANSWER_TYPE.BLOB_7
        BLOB_8 = MyANSWER_TYPE.BLOB_8
        BLOB_9 = MyANSWER_TYPE.BLOB_9
        BLOB_10 = MyANSWER_TYPE.BLOB_10
    }

    @JsName("merge")
    fun merge(v :ANSWER_TYPE? ){
        if(v == null ){
            return
        }

        if(v.IDENTIFICATOR_1 != null && v.IDENTIFICATOR_1!!.isNotEmpty()){
            this.IDENTIFICATOR_1 = v.IDENTIFICATOR_1
        }

        if(v.IDENTIFICATOR_2 != null && v.IDENTIFICATOR_2!!.isNotEmpty()){
            this.IDENTIFICATOR_2 = v.IDENTIFICATOR_2
        }

        if(v.IDENTIFICATOR_3 != null && v.IDENTIFICATOR_3!!.isNotEmpty()){
            this.IDENTIFICATOR_3 = v.IDENTIFICATOR_3
        }

        if(v.IDENTIFICATOR_4 != null && v.IDENTIFICATOR_4!!.isNotEmpty()){
            this.IDENTIFICATOR_4 = v.IDENTIFICATOR_4
        }

        if(v.IDENTIFICATOR_5 != null && v.IDENTIFICATOR_5!!.isNotEmpty()){
            this.IDENTIFICATOR_5 = v.IDENTIFICATOR_5
        }

        if(v.IDENTIFICATOR_6 != null && v.IDENTIFICATOR_6!!.isNotEmpty()){
            this.IDENTIFICATOR_6 = v.IDENTIFICATOR_6
        }


        if(v.IDENTIFICATOR_19 != null && v.IDENTIFICATOR_19!!.isNotEmpty()){
            this.IDENTIFICATOR_19 = v.IDENTIFICATOR_19
        }

        if(v.IDENTIFICATOR_20 != null && v.IDENTIFICATOR_20!!.isNotEmpty()){
            this.IDENTIFICATOR_20 = v.IDENTIFICATOR_20
        }


        if(v.LONG_1 != null){
            this.LONG_1 = v.LONG_1
        }

        if(v.LONG_2 != null){
            this.LONG_2 = v.LONG_2
        }

        if(v.LONG_3 != null){
            this.LONG_3 = v.LONG_3
        }

        if(v.LONG_4 != null){
            this.LONG_4 = v.LONG_4
        }

        if(v.STRING_1 != null && v.STRING_1!!.isNotEmpty()){
            this.STRING_1 = v.STRING_1
        }

        if(v.STRING_2 != null && v.STRING_2!!.isNotEmpty()){
            this.STRING_2 = v.STRING_2
        }

        if(v.STRING_3 != null && v.STRING_3!!.isNotEmpty()){
            this.STRING_3 = v.STRING_3
        }

        if(v.STRING_4 != null && v.STRING_4!!.isNotEmpty()){
            this.STRING_4 = v.STRING_4
        }

        if(v.STRING_20 != null && v.STRING_20!!.isNotEmpty()){
            this.STRING_20 = v.STRING_20
        }

        if(getIS_UPDATE_BLOB1() == "1"){
            this.BLOB_1 = v.BLOB_1
        }

        if(getIS_UPDATE_BLOB2() == "2"){
            this.BLOB_2 = v.BLOB_2
        }

        if(getIS_UPDATE_BLOB3() == "3"){
            this.BLOB_3 = v.BLOB_3
        }
        
    }

    @JsName("getIS_UPDATE_BLOB1")
    fun getIS_UPDATE_BLOB1():String{
        if(this.STRING_20 == null || this.STRING_20!!.length < 2){
            return "0"
        }
        return this.STRING_20!!.substring(1, 2)
    }

    @JsName("getIS_UPDATE_BLOB2")
    fun getIS_UPDATE_BLOB2():String{
        if(this.STRING_20 == null || this.STRING_20!!.length < 3){
            return "0"
        }
        return this.STRING_20!!.substring(2, 3)
    }

    @JsName("getIS_UPDATE_BLOB3")
    fun getIS_UPDATE_BLOB3():String{
        if(this.STRING_20 == null || this.STRING_20!!.length < 4){
            return "0"
        }
        return this.STRING_20!!.substring(3, 4)
    }

    @JsName("getRECORD_TYPE")
    fun getRECORD_TYPE():String{
        if(this.STRING_20 == null || this.STRING_20!!.length < 8){
            return ""
        }
        return this.STRING_20!!.substring(7, 8)
    }

    @JsName("getAVATAR_LINK")
    fun getAVATAR_LINK():String{
        return this.STRING_18?:""
    }

    @JsName("setAVATAR_LINK")
    fun setAVATAR_LINK(v:String?){
        this.STRING_18 = v?:""
    }

    @JsName("getAVATAR_SERVER")
    fun getAVATAR_SERVER():String{
        return this.STRING_16?:""
    }

    @JsName("setAVATAR_SERVER")
    fun setAVATAR_SERVER(v:String?){
        this.STRING_16 = v?:""
    }

    @JsName("getORIGINAL_AVATAR_SIZE")
    fun getORIGINAL_AVATAR_SIZE():String{
        return this.STRING_17?:"0"
    }

    @JsName("setORIGINAL_AVATAR_SIZE")
    fun setORIGINAL_AVATAR_SIZE(v: String?){
        this.STRING_17 = v?.trim()?:"0"
    }

    @JsName("getAVATAR1")
    fun getAVATAR1():ByteArray?{
        return this.BLOB_1
    }

    @JsName("setAVATAR1")
    fun setAVATAR1(v:ByteArray?){
        this.BLOB_1 = v
    }

    @JsName("getAVATAR2")
    fun getAVATAR2():ByteArray?{
        return this.BLOB_2
    }

    @JsName("setAVATAR2")
    fun setAVATAR2(v:ByteArray?){
        this.BLOB_2 = v
    }

    @JsName("getAVATAR3")
    fun getAVATAR3():ByteArray?{
        return this.BLOB_3
    }

    @JsName("setAVATAR3")
    fun setAVATAR3(v:ByteArray?){
        this.BLOB_3 = v
    }

    @JsName("setSTRING_20")
    fun setSTRING_20(v:String?){
        this.STRING_20 = v?:""
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

    @JsName("get_BLOB_4_size")
    fun get_BLOB_4_size(): Int {
        return BLOB_4?.size ?: 0
    }

    @JsName("get_BLOB_5_size")
    fun get_BLOB_5_size(): Int {
        return BLOB_5?.size ?: 0
    }

    @JsName("get_BLOB_6_size")
    fun get_BLOB_6_size(): Int {
        return BLOB_6?.size ?: 0
    }

    @JsName("get_BLOB_7_size")
    fun get_BLOB_7_size(): Int {
        return BLOB_7?.size ?: 0
    }

    @JsName("get_BLOB_8_size")
    fun get_BLOB_8_size(): Int {
        return BLOB_8?.size ?: 0
    }

    @JsName("get_BLOB_9_size")
    fun get_BLOB_9_size(): Int {
        return BLOB_9?.size ?: 0
    }

    @JsName("get_BLOB_10_size")
    fun get_BLOB_10_size(): Int {
        return BLOB_10?.size ?: 0
    }

    fun setValue(MyANSWER_TYPE: ANSWER_TYPE) {
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
        STRING_20 = MyANSWER_TYPE.STRING_20?.trim() ?: ""
        BLOB_1 = MyANSWER_TYPE.BLOB_1
        BLOB_2 = MyANSWER_TYPE.BLOB_2
        BLOB_3 = MyANSWER_TYPE.BLOB_3
        BLOB_4 = MyANSWER_TYPE.BLOB_4
        BLOB_5 = MyANSWER_TYPE.BLOB_5
        BLOB_6 = MyANSWER_TYPE.BLOB_6
        BLOB_7 = MyANSWER_TYPE.BLOB_7
        BLOB_8 = MyANSWER_TYPE.BLOB_8
        BLOB_9 = MyANSWER_TYPE.BLOB_9
        BLOB_10 = MyANSWER_TYPE.BLOB_10
    }

    fun createIDENTIFICATOTS() {
        if (IDENTIFICATOR_1!!.isEmpty()) IDENTIFICATOR_1 = "000000000000000000"
        if (IDENTIFICATOR_2!!.isEmpty()) IDENTIFICATOR_2 = "000000000000000000"
        if (IDENTIFICATOR_3!!.isEmpty()) IDENTIFICATOR_3 = "000000000000000000"
        if (IDENTIFICATOR_4!!.isEmpty()) IDENTIFICATOR_4 = "000000000000000000"
        if (IDENTIFICATOR_5!!.isEmpty()) IDENTIFICATOR_5 = "000000000000000000"
        if (IDENTIFICATOR_6!!.isEmpty()) IDENTIFICATOR_6 = "000000000000000000"
        if (IDENTIFICATOR_7!!.isEmpty()) IDENTIFICATOR_7 = "000000000000000000"
        if (IDENTIFICATOR_8!!.isEmpty()) IDENTIFICATOR_8 = "000000000000000000"
        if (IDENTIFICATOR_9!!.isEmpty()) IDENTIFICATOR_9 = "000000000000000000"
        if (IDENTIFICATOR_10!!.isEmpty()) IDENTIFICATOR_10 = "000000000000000000"
        if (IDENTIFICATOR_11!!.isEmpty()) IDENTIFICATOR_11 = "000000000000000000"
        if (IDENTIFICATOR_12!!.isEmpty()) IDENTIFICATOR_12 = "000000000000000000"
        if (IDENTIFICATOR_13!!.isEmpty()) IDENTIFICATOR_13 = "000000000000000000"
        if (IDENTIFICATOR_14!!.isEmpty()) IDENTIFICATOR_14 = "000000000000000000"
        if (IDENTIFICATOR_15!!.isEmpty()) IDENTIFICATOR_15 = "000000000000000000"
        if (IDENTIFICATOR_16!!.isEmpty()) IDENTIFICATOR_16 = "000000000000000000"
        if (IDENTIFICATOR_17!!.isEmpty()) IDENTIFICATOR_17 = "000000000000000000"
        if (IDENTIFICATOR_18!!.isEmpty()) IDENTIFICATOR_18 = "000000000000000000"
        IDENTIFICATORS = IDENTIFICATOR_1 + IDENTIFICATOR_2 + IDENTIFICATOR_3 +
        IDENTIFICATOR_4 + IDENTIFICATOR_5 + IDENTIFICATOR_6 +IDENTIFICATOR_7 +
        IDENTIFICATOR_8 + IDENTIFICATOR_9 + IDENTIFICATOR_10 +
        IDENTIFICATOR_11 + IDENTIFICATOR_12 + IDENTIFICATOR_13 +
        IDENTIFICATOR_14 + IDENTIFICATOR_15 + IDENTIFICATOR_16 +IDENTIFICATOR_17 +
        IDENTIFICATOR_18
    }
}
