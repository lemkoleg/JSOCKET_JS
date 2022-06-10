/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_jsocket

import io.ktor.util.*
import lib_exceptions.my_user_exceptions_class
import kotlin.js.JsName

/**
 *
 * @author Oleg
 */
@InternalAPI
@JsName("JSOCKET_Subscribe")
class JSOCKET_Subscribe(
    fields_number: Int,
    val fields_name: String,
    val fields_size: Int,
    val fields_size_is_perminent: Boolean,
    //0 - string, 1 - int, 2 - long, 3 - date, 4 - blob
    val fields_type: Int,
    // 0 - not crypted, 1 - crypted, 2 - crypted by MD5
    val fields_crypted: Int,
    val serialied: Boolean,
    val check_suming: Boolean,

    @JsName("getJSOCKET_FieldsValue")
    val getJSOCKET_FieldsValue: (JSOCKET) -> Any? = when (fields_number) {
        1 -> { j: JSOCKET -> j.connection_id }
        2 -> { j: JSOCKET -> j.connection_coocki }
        3 -> { j: JSOCKET -> j.connection_context }
        4 -> { j: JSOCKET -> j.object_size }
        5 -> { j: JSOCKET -> j.object_extension }
        6 -> { j: JSOCKET -> j.object_server }
        7 -> { j: JSOCKET -> j.device_id }
        8 -> { j: JSOCKET -> j.just_do_it }
        9 -> { j: JSOCKET -> j.just_do_it_label }
        10 -> { j: JSOCKET -> j.just_do_it_successfull }
        11 -> { j: JSOCKET -> j.lang }
        12 -> { j: JSOCKET -> j.value_id1 }
        13 -> { j: JSOCKET -> j.value_id2 }
        14 -> { j: JSOCKET -> j.value_id3 }
        15 -> { j: JSOCKET -> j.value_id4 }
        16 -> { j: JSOCKET -> j.value_id5 }
        17 -> { j: JSOCKET -> j.value_par1 }
        18 -> { j: JSOCKET -> j.value_par2 }
        19 -> { j: JSOCKET -> j.value_par3 }
        20 -> { j: JSOCKET -> j.value_par4 }
        21 -> { j: JSOCKET -> j.value_par5 }
        22 -> { j: JSOCKET -> j.value_par6 }
        23 -> { j: JSOCKET -> j.value_par7 }
        24 -> { j: JSOCKET -> j.value_par8 }
        25 -> { j: JSOCKET -> j.value_par9 }
        26 -> { j: JSOCKET -> j.last_messege_update }
        27 -> { j: JSOCKET -> j.last_notice_update }
        28 -> { j: JSOCKET -> j.last_metadata_update }
        29 -> { j: JSOCKET -> j.request_profile }
        30 -> { j: JSOCKET -> j.request_size }
        31 -> { j: JSOCKET -> j.version }
        32 -> { j: JSOCKET -> j.last_date_of_update }
        33 -> { j: JSOCKET -> j.db_massage }
        34 -> { j: JSOCKET -> j.content }
        else -> {
            throw my_user_exceptions_class(
                l_class_name = "JSOCKET_Subscribe",
                l_function_name = "getFieldsValue",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "number of field not found"
            )
        }
    },


    @JsName("setJSOCKET_FieldsValue")
    val setJSOCKET_FieldsValue: (JSOCKET, Any?) -> Unit = when (fields_number) {
        1 -> { j: JSOCKET, v: Any? -> j.connection_id = (v as Long?) ?: 0L }
        2 -> { j: JSOCKET, v: Any? -> j.connection_coocki = (v as Long?) ?: 0L }
        3 -> { j: JSOCKET, v: Any? -> j.connection_context = (v as String?) ?: "" }
        4 -> { j: JSOCKET, v: Any? -> j.object_size = (v as Long?) ?: 0L }
        5 -> { j: JSOCKET, v: Any? -> j.object_extension = (v as String?) ?: "" }
        6 -> { j: JSOCKET, v: Any? -> j.object_server = (v as String?) ?: "" }
        7 -> { j: JSOCKET, v: Any? -> j.device_id = (v as String?) ?: "" }
        8 -> { j: JSOCKET, v: Any? -> j.just_do_it = (v as Int?) ?: 0 }
        9 -> { j: JSOCKET, v: Any? -> j.just_do_it_label = ((v as Long?) ?: 0L) }
        10 -> { j: JSOCKET, v: Any? -> j.just_do_it_successfull = (v as String?) ?: "" }
        11 -> { j: JSOCKET, v: Any? -> j.lang = (v as String?) ?: "" }
        12 -> { j: JSOCKET, v: Any? -> j.value_id1 = (v as String?) ?: "" }
        13 -> { j: JSOCKET, v: Any? -> j.value_id2 = (v as String?) ?: "" }
        14 -> { j: JSOCKET, v: Any? -> j.value_id3 = (v as String?) ?: "" }
        15 -> { j: JSOCKET, v: Any? -> j.value_id4 = (v as String?) ?: "" }
        16 -> { j: JSOCKET, v: Any? -> j.value_id5 = (v as String?) ?: "" }
        17 -> { j: JSOCKET, v: Any? -> j.value_par1 = (v as String?) ?: "" }
        18 -> { j: JSOCKET, v: Any? -> j.value_par2 = (v as String?) ?: "" }
        19 -> { j: JSOCKET, v: Any? -> j.value_par3 = (v as String?) ?: "" }
        20 -> { j: JSOCKET, v: Any? -> j.value_par4 = (v as String?) ?: "" }
        21 -> { j: JSOCKET, v: Any? -> j.value_par5 = (v as String?) ?: "" }
        22 -> { j: JSOCKET, v: Any? -> j.value_par6 = (v as String?) ?: "" }
        23 -> { j: JSOCKET, v: Any? -> j.value_par7 = (v as String?) ?: "" }
        24 -> { j: JSOCKET, v: Any? -> j.value_par8 = (v as String?) ?: "" }
        25 -> { j: JSOCKET, v: Any? -> j.value_par9 = (v as String?) ?: "" }
        26 -> { j: JSOCKET, v: Any? -> j.last_messege_update = (v as Long?) ?: 0L }
        27 -> { j: JSOCKET, v: Any? -> j.last_notice_update = (v as Long?) ?: 0L }
        28 -> { j: JSOCKET, v: Any? -> j.last_metadata_update = (v as Long?) ?: 0L }
        29 -> { j: JSOCKET, v: Any? -> j.request_profile = (v as String?) ?: "" }
        30 -> { j: JSOCKET, v: Any? -> j.request_size = (v as Long?) ?: 0 }
        31 -> { j: JSOCKET, v: Any? -> j.version = (v as String?) ?: "" }
        32 -> { j: JSOCKET, v: Any? -> j.last_date_of_update = (v as Long?) ?: 0L }
        33 -> { j: JSOCKET, v: Any? -> j.db_massage = (v as String?) ?: "" }
        34 -> { j: JSOCKET, v: Any? -> j.content = (v as ByteArray?) }
        else -> throw my_user_exceptions_class(
            l_class_name = "JSOCKET_Subscribe",
            l_function_name = "setFieldsValue",
            name_of_exception = "EXC_SYSTEM_ERROR",
            l_additional_text = "number of field not found"
        )

    }
)