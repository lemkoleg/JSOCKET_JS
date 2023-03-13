/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import lib_exceptions.my_user_exceptions_class
//import kotlin.js.JsName
import kotlin.time.ExperimentalTime

/**
 *
 * @author Oleg
 */
@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
//@JsName("ANSWER_TYPE_Subscribe")
class ANSWER_TYPE_Subscribe(

    fields_number: Int,

    //@JsName("fields_name")
    val fields_name: String,

    //@JsName("fields_size")
    val fields_size: Int,

    //@JsName("fields_size_is_perminent")
    val fields_size_is_perminent: Boolean,

    //0 - string, 1 - int, 2 - long, 3 - date, 4 - blob
    //@JsName("fields_type")
    val fields_type: Int,

    //@JsName("getANSWER_TYPE_FieldsValue")
    val getANSWER_TYPE_FieldsValue: (ANSWER_TYPE) -> Any? = when (fields_number) {
        1 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_1 }
        2 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_2 }
        3 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_3 }
        4 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_4 }
        5 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_5 }
        6 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_6 }
        7 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_7 }
        8 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_8 }
        9 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_9 }
        10 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_10 }
        11 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_11 }
        12 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_12 }
        13 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_13 }
        14 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_14 }
        15 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_15 }
        16 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_16 }
        17 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_17 }
        18 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_18 }
        19 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_19 }
        20 -> { j: ANSWER_TYPE -> j.IDENTIFICATOR_20 }
        21 -> { j: ANSWER_TYPE -> j.INTEGER_1 }
        22 -> { j: ANSWER_TYPE -> j.INTEGER_2 }
        23 -> { j: ANSWER_TYPE -> j.INTEGER_3 }
        24 -> { j: ANSWER_TYPE -> j.INTEGER_4 }
        25 -> { j: ANSWER_TYPE -> j.INTEGER_5 }
        26 -> { j: ANSWER_TYPE -> j.INTEGER_6 }
        27 -> { j: ANSWER_TYPE -> j.INTEGER_7 }
        28 -> { j: ANSWER_TYPE -> j.INTEGER_8 }
        29 -> { j: ANSWER_TYPE -> j.INTEGER_9 }
        30 -> { j: ANSWER_TYPE -> j.INTEGER_10 }
        31 -> { j: ANSWER_TYPE -> j.INTEGER_11 }
        32 -> { j: ANSWER_TYPE -> j.INTEGER_12 }
        33 -> { j: ANSWER_TYPE -> j.INTEGER_13 }
        34 -> { j: ANSWER_TYPE -> j.INTEGER_14 }
        35 -> { j: ANSWER_TYPE -> j.INTEGER_15 }
        36 -> { j: ANSWER_TYPE -> j.INTEGER_16 }
        37 -> { j: ANSWER_TYPE -> j.INTEGER_17 }
        38 -> { j: ANSWER_TYPE -> j.INTEGER_18 }
        39 -> { j: ANSWER_TYPE -> j.INTEGER_19 }
        40 -> { j: ANSWER_TYPE -> j.INTEGER_20 }
        41 -> { j: ANSWER_TYPE -> j.LONG_1 }
        42 -> { j: ANSWER_TYPE -> j.LONG_2 }
        43 -> { j: ANSWER_TYPE -> j.LONG_3 }
        44 -> { j: ANSWER_TYPE -> j.LONG_4 }
        45 -> { j: ANSWER_TYPE -> j.LONG_5 }
        46 -> { j: ANSWER_TYPE -> j.LONG_6 }
        47 -> { j: ANSWER_TYPE -> j.LONG_7 }
        48 -> { j: ANSWER_TYPE -> j.LONG_8 }
        49 -> { j: ANSWER_TYPE -> j.LONG_9 }
        50 -> { j: ANSWER_TYPE -> j.LONG_10 }
        51 -> { j: ANSWER_TYPE -> j.LONG_11 }
        52 -> { j: ANSWER_TYPE -> j.LONG_12 }
        53 -> { j: ANSWER_TYPE -> j.LONG_13 }
        54 -> { j: ANSWER_TYPE -> j.LONG_14 }
        55 -> { j: ANSWER_TYPE -> j.LONG_15 }
        56 -> { j: ANSWER_TYPE -> j.LONG_16 }
        57 -> { j: ANSWER_TYPE -> j.LONG_17 }
        58 -> { j: ANSWER_TYPE -> j.LONG_18 }
        59 -> { j: ANSWER_TYPE -> j.LONG_19 }
        60 -> { j: ANSWER_TYPE -> j.LONG_20 }
        61 -> { j: ANSWER_TYPE -> j.STRING_1 }
        62 -> { j: ANSWER_TYPE -> j.STRING_2 }
        63 -> { j: ANSWER_TYPE -> j.STRING_3 }
        64 -> { j: ANSWER_TYPE -> j.STRING_4 }
        65 -> { j: ANSWER_TYPE -> j.STRING_5 }
        66 -> { j: ANSWER_TYPE -> j.STRING_6 }
        67 -> { j: ANSWER_TYPE -> j.STRING_7 }
        68 -> { j: ANSWER_TYPE -> j.STRING_8 }
        69 -> { j: ANSWER_TYPE -> j.STRING_9 }
        70 -> { j: ANSWER_TYPE -> j.STRING_10 }
        71 -> { j: ANSWER_TYPE -> j.STRING_11 }
        72 -> { j: ANSWER_TYPE -> j.STRING_12 }
        73 -> { j: ANSWER_TYPE -> j.STRING_13 }
        74 -> { j: ANSWER_TYPE -> j.STRING_14 }
        75 -> { j: ANSWER_TYPE -> j.STRING_15 }
        76 -> { j: ANSWER_TYPE -> j.STRING_16 }
        77 -> { j: ANSWER_TYPE -> j.STRING_17 }
        78 -> { j: ANSWER_TYPE -> j.STRING_18 }
        79 -> { j: ANSWER_TYPE -> j.STRING_19 }
        80 -> { j: ANSWER_TYPE -> j.STRING_20 }
        81 -> { j: ANSWER_TYPE -> j.BLOB_1 }
        82 -> { j: ANSWER_TYPE -> j.BLOB_2 }
        83 -> { j: ANSWER_TYPE -> j.BLOB_3 }
        84 -> { j: ANSWER_TYPE -> j.BLOB_4 }
        else -> {
            throw my_user_exceptions_class(
                l_class_name = "ANSWER_TYPE_Subscribe",
                l_function_name = "getFieldsValue",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "number of field not found"
            )
        }
    },

    //@JsName("setANSWER_TYPE_FieldsValue")
    val setANSWER_TYPE_FieldsValue: (ANSWER_TYPE, Any?) -> Unit = when (fields_number) {
        1 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_1 = (v as String?) ?: "" }
        2 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_2 = (v as String?) ?: "" }
        3 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_3 = (v as String?) ?: "" }
        4 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_4 = (v as String?) ?: "" }
        5 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_5 = (v as String?) ?: "" }
        6 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_6 = (v as String?) ?: "" }
        7 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_7 = (v as String?) ?: "" }
        8 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_8 = (v as String?) ?: "" }
        9 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_9 = (v as String?) ?: "" }
        10 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_10 = (v as String?) ?: "" }
        11 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_11 = (v as String?) ?: "" }
        12 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_12 = (v as String?) ?: "" }
        13 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_13 = (v as String?) ?: "" }
        14 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_14 = (v as String?) ?: "" }
        15 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_15 = (v as String?) ?: "" }
        16 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_16 = (v as String?) ?: "" }
        17 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_17 = (v as String?) ?: "" }
        18 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_18 = (v as String?) ?: "" }
        19 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_19 = (v as String?) ?: "" }
        20 -> { j: ANSWER_TYPE, v: Any? -> j.IDENTIFICATOR_20 = (v as String?) ?: "" }
        21 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_1 = (v as Int?) ?: 0 }
        22 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_2 = (v as Int?) ?: 0 }
        23 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_3 = (v as Int?) ?: 0 }
        24 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_4 = (v as Int?) ?: 0 }
        25 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_5 = (v as Int?) ?: 0 }
        26 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_6 = (v as Int?) ?: 0 }
        27 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_7 = (v as Int?) ?: 0 }
        28 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_8 = (v as Int?) ?: 0 }
        29 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_9 = (v as Int?) ?: 0 }
        30 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_10 = (v as Int?) ?: 0 }
        31 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_11 = (v as Int?) ?: 0 }
        32 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_12 = (v as Int?) ?: 0 }
        33 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_13 = (v as Int?) ?: 0 }
        34 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_14 = (v as Int?) ?: 0 }
        35 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_15 = (v as Int?) ?: 0 }
        36 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_16 = (v as Int?) ?: 0 }
        37 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_17 = (v as Int?) ?: 0 }
        38 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_18 = (v as Int?) ?: 0 }
        39 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_19 = (v as Int?) ?: 0 }
        40 -> { j: ANSWER_TYPE, v: Any? -> j.INTEGER_20 = (v as Int?) ?: 0 }
        41 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_1 = (v as Long?) ?: 0L }
        42 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_2 = (v as Long?) ?: 0L }
        43 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_3 = (v as Long?) ?: 0L }
        44 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_4 = (v as Long?) ?: 0L }
        45 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_5 = (v as Long?) ?: 0L }
        46 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_6 = (v as Long?) ?: 0L }
        47 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_7 = (v as Long?) ?: 0L }
        48 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_8 = (v as Long?) ?: 0L }
        49 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_9 = (v as Long?) ?: 0L }
        50 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_10 = (v as Long?) ?: 0L }
        51 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_11 = (v as Long?) ?: 0L }
        52 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_12 = (v as Long?) ?: 0L }
        53 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_13 = (v as Long?) ?: 0L }
        54 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_14 = (v as Long?) ?: 0L }
        55 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_15 = (v as Long?) ?: 0L }
        56 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_16 = (v as Long?) ?: 0L }
        57 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_17 = (v as Long?) ?: 0L }
        58 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_18 = (v as Long?) ?: 0L }
        59 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_19 = (v as Long?) ?: 0L }
        60 -> { j: ANSWER_TYPE, v: Any? -> j.LONG_20 = (v as Long?) ?: 0L }
        61 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_1 = (v as String?) ?: "" }
        62 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_2 = (v as String?) ?: "" }
        63 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_3 = (v as String?) ?: "" }
        64 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_4 = (v as String?) ?: "" }
        65 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_5 = (v as String?) ?: "" }
        66 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_6 = (v as String?) ?: "" }
        67 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_7 = (v as String?) ?: "" }
        68 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_8 = (v as String?) ?: "" }
        69 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_9 = (v as String?) ?: "" }
        70 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_10 = (v as String?) ?: "" }
        71 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_11 = (v as String?) ?: "" }
        72 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_12 = (v as String?) ?: "" }
        73 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_13 = (v as String?) ?: "" }
        74 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_14 = (v as String?) ?: "" }
        75 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_15 = (v as String?) ?: "" }
        76 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_16 = (v as String?) ?: "" }
        77 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_17 = (v as String?) ?: "" }
        78 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_18 = (v as String?) ?: "" }
        79 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_19 = (v as String?) ?: "" }
        80 -> { j: ANSWER_TYPE, v: Any? -> j.STRING_20 = (v as String? ?: "") }
        81 -> { j: ANSWER_TYPE, v: Any? -> j.BLOB_1 = (v as ByteArray?) }
        82 -> { j: ANSWER_TYPE, v: Any? -> j.BLOB_2 = (v as ByteArray?) }
        83 -> { j: ANSWER_TYPE, v: Any? -> j.BLOB_3 = (v as ByteArray?) }
        84 -> { j: ANSWER_TYPE, v: Any? -> j.BLOB_4 = (v as ByteArray?) }
        else -> {
            throw my_user_exceptions_class(
                l_class_name = "ANSWER_TYPE_Subscribe",
                l_function_name = "setFieldsValue",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "number of field not found"
            )
        }
    })
