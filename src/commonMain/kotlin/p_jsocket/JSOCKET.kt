
package p_jsocket

import CrossPlatforms.MyCondition
import Tables.*
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.core.internal.*
import lib_exceptions.my_user_exceptions_class
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author Oleg
 */


private val REQUEST_PREFIX = returnRequestPrefix()


private val REQUEST_POSTFIX = returnRequestPostfix()

private fun returnRequestPrefix(): ByteArray {
    val b = BytePacketBuilder(ChunkBuffer.Pool)
    b.writeByte(0.toByte())
    b.writeLong(0L)
    b.writeLong(9223372036854775807L)
    return b.build().readBytes()
}

private fun returnRequestPostfix(): ByteArray {
    val b = BytePacketBuilder(ChunkBuffer.Pool)
    b.writeLong(7085774586302733229L)
    return b.build().readBytes()
}


@JsName("FIELDS_SUBSCRIBE")
@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val FIELDS_SUBSCRIBE: Map<Int, JSOCKET_Subscribe> = mapOf(
    1 to JSOCKET_Subscribe(
        fields_number = 1,
        fields_name = "connection_id",
        fields_size = 8,
        fields_size_is_perminent = true,
        fields_type = 2,
        fields_crypted = 0,
        serialied = false,
        check_suming = true
    ),
    2 to JSOCKET_Subscribe(
        fields_number = 2,
        fields_name = "connection_coocki",
        fields_size = 8,
        fields_size_is_perminent = true,
        fields_type = 2,
        fields_crypted = 2,
        serialied = false,
        check_suming = false
    ),
    3 to JSOCKET_Subscribe(
        fields_number = 3,
        fields_name = "connection_context",
        fields_size = 30,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    4 to JSOCKET_Subscribe(
        fields_number = 4,
        fields_name = "object_size",
        fields_size = 8,
        fields_size_is_perminent = true,
        fields_type = 2,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    5 to JSOCKET_Subscribe(
        fields_number = 5,
        fields_name = "object_extension",
        fields_size = 5,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    6 to JSOCKET_Subscribe(
        fields_number = 6,
        fields_name = "object_server",
        fields_size = 15,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    7 to JSOCKET_Subscribe(
        fields_number = 7,
        fields_name = "device_id",
        fields_size = 16,
        fields_size_is_perminent = true,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    8 to JSOCKET_Subscribe(
        fields_number = 8,
        fields_name = "just_do_it",
        fields_size = 4,
        fields_size_is_perminent = true,
        fields_type = 1,
        fields_crypted = 1,
        serialied = false,
        check_suming = true
    ),
    9 to JSOCKET_Subscribe(
        fields_number = 9,
        fields_name = "just_do_it_label",
        fields_size = 8,
        fields_size_is_perminent = true,
        fields_type = 2,
        fields_crypted = 1,
        serialied = false,
        check_suming = false
    ),
    10 to JSOCKET_Subscribe(
        fields_number = 10,
        fields_name = "just_do_it_successfull",
        fields_size = 4,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    11 to JSOCKET_Subscribe(
        fields_number = 11,
        fields_name = "lang",
        fields_size = 3,
        fields_size_is_perminent = true,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    12 to JSOCKET_Subscribe(
        fields_number = 12,
        fields_name = "value_id1",
        fields_size = 18,
        fields_size_is_perminent = true,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = true
    ),
    13 to JSOCKET_Subscribe(
        fields_number = 13,
        fields_name = "value_id2",
        fields_size = 18,
        fields_size_is_perminent = true,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    14 to JSOCKET_Subscribe(
        fields_number = 14,
        fields_name = "value_id3",
        fields_size = 18,
        fields_size_is_perminent = true,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    15 to JSOCKET_Subscribe(
        fields_number = 15,
        fields_name = "value_id4",
        fields_size = 18,
        fields_size_is_perminent = true,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = true
    ),
    16 to JSOCKET_Subscribe(
        fields_number = 16,
        fields_name = "value_id5",
        fields_size = 18,
        fields_size_is_perminent = true,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = true
    ),
    17 to JSOCKET_Subscribe(
        fields_number = 17,
        fields_name = "value_par1",
        fields_size = 120,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    18 to JSOCKET_Subscribe(
        fields_number = 18,
        fields_name = "value_par2",
        fields_size = 60,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = true
    ),
    19 to JSOCKET_Subscribe(
        fields_number = 19,
        fields_name = "value_par3",
        fields_size = 60,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    20 to JSOCKET_Subscribe(
        fields_number = 20,
        fields_name = "value_par4",
        fields_size = 60,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = true
    ),
    21 to JSOCKET_Subscribe(
        fields_number = 21,
        fields_name = "value_par5",
        fields_size = 60,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = true
    ),
    22 to JSOCKET_Subscribe(
        fields_number = 22,
        fields_name = "value_par6",
        fields_size = 60,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = true
    ),
    23 to JSOCKET_Subscribe(
        fields_number = 23,
        fields_name = "value_par7",
        fields_size = 60,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    24 to JSOCKET_Subscribe(
        fields_number = 24,
        fields_name = "value_par8",
        fields_size = 400,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    25 to JSOCKET_Subscribe(
        fields_number = 25,
        fields_name = "value_par9",
        fields_size = 4000,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    26 to JSOCKET_Subscribe(
        fields_number = 26,
        fields_name = "last_messege_update",
        fields_size = 8,
        fields_size_is_perminent = true,
        fields_type = 2,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    27 to JSOCKET_Subscribe(
        fields_number = 27,
        fields_name = "last_notice_update",
        fields_size = 8,
        fields_size_is_perminent = true,
        fields_type = 2,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    28 to JSOCKET_Subscribe(
        fields_number = 28,
        fields_name = "last_metadata_update",
        fields_size = 8,
        fields_size_is_perminent = true,
        fields_type = 2,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    29 to JSOCKET_Subscribe(
        fields_number = 29,
        fields_name = "request_profile",
        fields_size = 30,
        fields_size_is_perminent = true,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    30 to JSOCKET_Subscribe(
        fields_number = 30,
        fields_name = "request_size",
        fields_size = 8,
        fields_size_is_perminent = false,
        fields_type = 1,
        fields_crypted = 1,
        serialied = false,
        check_suming = false
    ),
    31 to JSOCKET_Subscribe(
        fields_number = 31,
        fields_name = "version",
        fields_size = 5,
        fields_size_is_perminent = true,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    32 to JSOCKET_Subscribe(
        fields_number = 32,
        fields_name = "last_date_of_update",
        fields_size = 8,
        fields_size_is_perminent = true,
        fields_type = 2,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    33 to JSOCKET_Subscribe(
        fields_number = 33,
        fields_name = "db_massage",
        fields_size = 500,
        fields_size_is_perminent = false,
        fields_type = 0,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    ),
    34 to JSOCKET_Subscribe(
        fields_number = 34,
        fields_name = "content",
        fields_size = 32768,
        fields_size_is_perminent = false,
        fields_type = 4,
        fields_crypted = 1,
        serialied = true,
        check_suming = false
    )
)


@JsName("JSOCKET")
@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
open class JSOCKET() {

    constructor(myJsocketClass: JSOCKET) : this() {
        this.jserver_connection_id = myJsocketClass.jserver_connection_id
        this.connection_id = myJsocketClass.connection_id
        this.connection_coocki = myJsocketClass.connection_coocki
        this.connection_context = myJsocketClass.connection_context
        this.object_size = myJsocketClass.object_size
        this.object_extension = myJsocketClass.object_extension
        this.object_server = myJsocketClass.object_server
        this.ip_address = myJsocketClass.ip_address
        this.ip_port = myJsocketClass.ip_port
        this.device_id = myJsocketClass.device_id
        this.just_do_it = myJsocketClass.just_do_it
        this.just_do_it_label = myJsocketClass.just_do_it_label
        this.just_do_it_successfull = myJsocketClass.just_do_it_successfull
        this.lang = myJsocketClass.lang
        this.value_id1 = myJsocketClass.value_id1
        this.value_id2 = myJsocketClass.value_id2
        this.value_id3 = myJsocketClass.value_id3
        this.value_id4 = myJsocketClass.value_id4
        this.value_id5 = myJsocketClass.value_id5
        this.value_par1 = myJsocketClass.value_par1
        this.value_par2 = myJsocketClass.value_par2
        this.value_par3 = myJsocketClass.value_par3
        this.value_par4 = myJsocketClass.value_par4
        this.value_par5 = myJsocketClass.value_par5
        this.value_par6 = myJsocketClass.value_par6
        this.value_par7 = myJsocketClass.value_par7
        this.value_par8 = myJsocketClass.value_par8
        this.value_par9 = myJsocketClass.value_par9
        this.last_messege_update = myJsocketClass.last_messege_update
        this.last_notice_update = myJsocketClass.last_notice_update
        this.last_metadata_update = myJsocketClass.last_metadata_update
        this.request_profile = myJsocketClass.request_profile
        this.request_size = myJsocketClass.request_size
        this.version = myJsocketClass.version
        this.last_date_of_update = myJsocketClass.last_date_of_update
        this.db_massage = myJsocketClass.db_massage
        this.content = myJsocketClass.content
        this.ANSWER_TYPEs = myJsocketClass.ANSWER_TYPEs
        this.check_sum = myJsocketClass.check_sum
    }

    @JsName("setLongValueFromString")
    fun setLongValueFromString(s: String): Long {
        return s.toLong()
    }

    @JsName("getLongValueAsString")
    fun getLongValueAsString(s: Long): String {
        return s.toString()
    }

    @JsName("condition")
    val condition: MyCondition = MyCondition()

    @JsName("ANSWER_TYPEs")
    var ANSWER_TYPEs: ArrayDeque<ANSWER_TYPE>? = null

    @JsName("jserver_connection_id")
    var jserver_connection_id: Long = 0L

    @JsName("connection_id")
    var connection_id: Long = 0L

    @JsName("connection_coocki")
    var connection_coocki: Long = 0L

    @JsName("connection_context")
    var connection_context: String = ""

    @JsName("object_size")
    var object_size: Long = 0L

    @JsName("object_extension")
    var object_extension: String = ""

    @JsName("object_server")
    var object_server: String = ""

    @JsName("ip_address")
    var ip_address: String = ""

    @JsName("ip_port")
    var ip_port: Int = 0

    @JsName("device_id")
    var device_id: String = ""

    @JsName("just_do_it")
    var just_do_it: Int = 0

    @JsName("just_do_it_label")
    var just_do_it_label: Long = 0L

    @JsName("just_do_it_successfull")
    var just_do_it_successfull: String = "0"

    @JsName("lang")
    var lang: String = "ENG"

    @JsName("value_id1")
    var value_id1: String = ""

    @JsName("value_id2")
    var value_id2: String = ""

    @JsName("value_id3")
    var value_id3: String = ""

    @JsName("value_id4")
    var value_id4: String = ""

    @JsName("value_id5")
    var value_id5: String = ""

    @JsName("value_par1")
    var value_par1: String = ""

    @JsName("value_par2")
    var value_par2: String = ""

    @JsName("value_par3")
    var value_par3: String = ""

    @JsName("value_par4")
    var value_par4: String = ""

    @JsName("value_par5")
    var value_par5: String = ""

    @JsName("value_par6")
    var value_par6: String = ""

    @JsName("value_par7")
    var value_par7: String = ""

    @JsName("value_par8")
    var value_par8: String = ""

    @JsName("value_par9")
    var value_par9: String = ""

    @JsName("last_messege_update")
    var last_messege_update: Long = 0L

    @JsName("last_notice_update")
    var last_notice_update: Long = 0L

    @JsName("last_metadata_updatee")
    var last_metadata_update: Long = 0L

    @JsName("request_profile")
    var request_profile: String = ""

    @JsName("request_size")
    var request_size: Long = 0

    @JsName("version")
    var version: String? = "00001"

    @JsName("last_date_of_update")
    var last_date_of_update: Long = 0L

    @JsName("db_massage")
    var db_massage: String = ""

    @JsName("content")
    var content: ByteArray? = null

    @JsName("bb")
    var bb: ByteReadPacket? = null

    @JsName("is_new_reg_data")
    var is_new_reg_data: Boolean = false


    @JsName("local_answer_type")
    var local_answer_type: ANSWER_TYPE? = null


    private var start_position = 0
    private var reverse_start_position = 0
    private var md5String: String = ""
    private var reverseMD5String: String = ""
    private lateinit var md5LongArray: LongArray
    private lateinit var reverseMD5LongArray: LongArray

    private var h: HASH? = null

    @JsName("check_sum")
    var check_sum: String = ""
    private var long_value: Long = 0
    private var nature_connection_coocki: Long = 0L
    private var int_value: Int = 0
    private var nameField_length = 0
    private var nameField_number = 0

    private var bbCONTENT_SIZE: BytePacketBuilder? = null

    private var crypt = false
    private var currentCommand: Command? = null


    fun close() {
        try {
            condition.cDestroy()
        } catch (e: Exception) {
        }
        try {
            ANSWER_TYPEs?.clear()
        } catch (e: Exception) {
        }
        try {
            bbCONTENT_SIZE?.close()
        } catch (e: Exception) {
        }
        try {
            bb?.close()
        } catch (e: Exception) {
        }
        //ip_address_bytes?.close()
        content = null
    }


    @JsName("set_value")
    @InternalAPI
    fun set_value(myJsocketClass: JSOCKET) {
        this.jserver_connection_id = myJsocketClass.jserver_connection_id
        this.connection_id = myJsocketClass.connection_id
        this.connection_coocki = myJsocketClass.connection_coocki
        this.connection_context = myJsocketClass.connection_context
        this.object_size = myJsocketClass.object_size
        this.object_extension = myJsocketClass.object_extension
        this.object_server = myJsocketClass.object_server
        this.ip_address = myJsocketClass.ip_address
        this.ip_port = myJsocketClass.ip_port
        this.device_id = myJsocketClass.device_id
        this.just_do_it = myJsocketClass.just_do_it
        this.just_do_it_label = myJsocketClass.just_do_it_label
        this.just_do_it_successfull = myJsocketClass.just_do_it_successfull
        this.lang = myJsocketClass.lang
        this.value_id1 = myJsocketClass.value_id1
        this.value_id2 = myJsocketClass.value_id2
        this.value_id3 = myJsocketClass.value_id3
        this.value_id4 = myJsocketClass.value_id4
        this.value_id5 = myJsocketClass.value_id5
        this.value_par1 = myJsocketClass.value_par1
        this.value_par2 = myJsocketClass.value_par2
        this.value_par3 = myJsocketClass.value_par3
        this.value_par4 = myJsocketClass.value_par4
        this.value_par5 = myJsocketClass.value_par5
        this.value_par6 = myJsocketClass.value_par6
        this.value_par7 = myJsocketClass.value_par7
        this.value_par8 = myJsocketClass.value_par8
        this.value_par9 = myJsocketClass.value_par9
        this.last_messege_update = myJsocketClass.last_messege_update
        this.last_notice_update = myJsocketClass.last_notice_update
        this.last_metadata_update = myJsocketClass.last_metadata_update
        this.request_profile = myJsocketClass.request_profile
        this.request_size = myJsocketClass.request_size
        this.version = myJsocketClass.version
        this.last_date_of_update = myJsocketClass.last_date_of_update
        this.db_massage = myJsocketClass.db_massage
        this.content = myJsocketClass.content
        //this.ANSWER_TYPEs = myJsocketClass.ANSWER_TYPEs
    }

    @JsName("merge")
    @InternalAPI
    fun merge(myJsocketClass: JSOCKET) {
        this.just_do_it = myJsocketClass.just_do_it
        this.just_do_it_label = myJsocketClass.just_do_it_label
        this.just_do_it_successfull = myJsocketClass.just_do_it_successfull
        this.lang = myJsocketClass.lang

        if (myJsocketClass.value_id1.isNotEmpty()) {
            this.value_id1 = myJsocketClass.value_id1
        }

        if (myJsocketClass.value_id2.isNotEmpty()) {
            this.value_id2 = myJsocketClass.value_id2
        }

        if (myJsocketClass.value_id3.isNotEmpty()) {
            this.value_id3 = myJsocketClass.value_id3
        }

        if (myJsocketClass.value_id4.isNotEmpty()) {
            this.value_id4 = myJsocketClass.value_id4
        }

        if (myJsocketClass.value_id5.isNotEmpty()) {
            this.value_id5 = myJsocketClass.value_id5
        }

        if (myJsocketClass.value_par1.isNotEmpty()) {
            this.value_par1 = myJsocketClass.value_par1
        }

        if (myJsocketClass.value_par2.isNotEmpty()) {
            this.value_par2 = myJsocketClass.value_par2
        }

        if (myJsocketClass.value_par3.isNotEmpty()) {
            this.value_par3 = myJsocketClass.value_par3
        }

        if (myJsocketClass.value_par4.isNotEmpty()) {
            this.value_par4 = myJsocketClass.value_par4
        }

        if (myJsocketClass.value_par5.isNotEmpty()) {
            this.value_par5 = myJsocketClass.value_par5
        }

        if (myJsocketClass.value_par6.isNotEmpty()) {
            this.value_par6 = myJsocketClass.value_par6
        }

        if (myJsocketClass.value_par7.isNotEmpty()) {
            this.value_par7 = myJsocketClass.value_par7
        }

        if (myJsocketClass.value_par8.isNotEmpty()) {
            this.value_par8 = myJsocketClass.value_par8
        }

        if (myJsocketClass.value_par9.isNotEmpty()) {
            this.value_par9 = myJsocketClass.value_par9
        }

        this.request_profile = myJsocketClass.request_profile
        this.last_date_of_update = myJsocketClass.last_date_of_update
        this.db_massage = myJsocketClass.db_massage
        this.content = myJsocketClass.content
        this.check_sum = myJsocketClass.check_sum
    }

    @JsName("contrMerge")
    @InternalAPI
    fun contrMerge(myJsocketClass: JSOCKET) {
        this.just_do_it = myJsocketClass.just_do_it
        this.just_do_it_label = myJsocketClass.just_do_it_label
        this.just_do_it_successfull = myJsocketClass.just_do_it_successfull
        this.lang = myJsocketClass.lang

        if (this.value_id1.isEmpty()) {
            this.value_id1 = myJsocketClass.value_id1
        }

        if (this.value_id2.isEmpty()) {
            this.value_id2 = myJsocketClass.value_id2
        }

        if (this.value_id3.isEmpty()) {
            this.value_id3 = myJsocketClass.value_id3
        }

        if (this.value_id4.isEmpty()) {
            this.value_id4 = myJsocketClass.value_id4
        }

        if (this.value_id5.isEmpty()) {
            this.value_id5 = myJsocketClass.value_id5
        }

        if (this.value_par1.isEmpty()) {
            this.value_par1 = myJsocketClass.value_par1
        }

        if (this.value_par2.isEmpty()) {
            this.value_par2 = myJsocketClass.value_par2
        }

        if (this.value_par3.isEmpty()) {
            this.value_par3 = myJsocketClass.value_par3
        }

        if (this.value_par4.isEmpty()) {
            this.value_par4 = myJsocketClass.value_par4
        }

        if (this.value_par5.isEmpty()) {
            this.value_par5 = myJsocketClass.value_par5
        }

        if (this.value_par6.isEmpty()) {
            this.value_par6 = myJsocketClass.value_par6
        }

        if (this.value_par7.isEmpty()) {
            this.value_par7 = myJsocketClass.value_par7
        }

        if (this.value_par8.isEmpty()) {
            this.value_par8 = myJsocketClass.value_par8
        }

        if (this.value_par9.isEmpty()) {
            this.value_par9 = myJsocketClass.value_par9
        }

        this.request_profile = myJsocketClass.request_profile
        this.last_date_of_update = myJsocketClass.last_date_of_update
        this.check_sum = myJsocketClass.check_sum
    }


    @JsName("serialize")
    fun serialize(verify_fields: Boolean, update_just_do_it_label: Boolean = true): ByteArray {

        try {
            bbCONTENT_SIZE = BytePacketBuilder(ChunkBuffer.Pool)

            connection_id = Constants.myConnectionsID
            connection_coocki = Constants.myConnectionsCoocki
            device_id = Constants.myDeviceId
            lang = Constants.myLang
            last_messege_update = globalLastChatsSelect.value
            last_metadata_update = meta_data_last_update.value
            db_massage = ""
            just_do_it_successfull = "0"
            connection_context = Constants.myConnectionContext
            if(update_just_do_it_label){
                just_do_it_label = nowNano()
            }



            if (h == null) h = HASH()

            if (just_do_it_label == 0L) {
                throw my_user_exceptions_class(
                    l_class_name = "JSOCKET",
                    l_function_name = "serialize",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "just_do_it_label is null"
                )
            }
            if (just_do_it == 0) {
                throw my_user_exceptions_class(
                    l_class_name = "JSOCKET",
                    l_function_name = "serialize",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "just_do_it is null"
                )
            }
            currentCommand = if (!COMMANDS.containsKey(just_do_it)) {
                throw my_user_exceptions_class(
                    l_class_name = "JSOCKET",
                    l_function_name = "serialize",
                    name_of_exception = "EXC_WRSOCKETTYPE_NOT_FOUND_COMMAND",
                    l_additional_text = "just_do_it not found $just_do_it"
                )
            } else {
                COMMANDS[just_do_it] ?: throw my_user_exceptions_class(
                    l_class_name = "JSOCKET",
                    l_function_name = "serialize",
                    name_of_exception = "EXC_WRSOCKETTYPE_NOT_FOUND_COMMAND",
                    l_additional_text = "just_do_it not found $just_do_it"
                )
            }


            /*
            var lcraete_check_sum: Boolean
            if (craete_check_sum) {
                check_sum = 0L
            }
            */

            md5String = ""
            object_extension = object_extension.trim().lowercase()
            value_par7 = value_par7.trim().uppercase()
            start_position = 0
            crypt = currentCommand!!.isCrypt

            if (crypt) {
                if (connection_id.equals(0L)) {
                    throw my_user_exceptions_class(
                        l_class_name = "JSOCKET",
                        l_function_name = "serialize",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "connection_id is null"
                    )
                }
                if (connection_coocki.equals(0L)) {
                    throw my_user_exceptions_class(
                        l_class_name = "JSOCKET",
                        l_function_name = "serialize",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "connection_coocki is null"
                    )
                }
            } else {
                connection_coocki = 0L
            }

            /*
            if (craete_check_sum && connection_id != 0L) {
                check_sum = h!!.getCheckSumFromLong(connection_id, check_sum)
            }
            if (craete_check_sum) {
                check_sum = h!!.getCheckSumFromLong(just_do_it.toLong(), check_sum)
            }
             */

            nature_connection_coocki = connection_coocki
            if (connection_coocki != 0L) {
                md5String = h!!.getNewMD5String(connection_coocki, just_do_it_label)
                reverseMD5String = h!!.getReverseMD5String(md5String)
                connection_coocki = h!!.getNewCoockiLong(md5String)
                md5LongArray = h!!.getNewMD5longArray(md5String)
                reverseMD5LongArray = h!!.getReverseMD5longArray(reverseMD5String)
                start_position = md5String.substring(md5String.length - 1, md5String.length).toInt(16)
                reverse_start_position =
                    reverseMD5String.substring(reverseMD5String.length - 1, reverseMD5String.length).toInt(16)
            }
            ////////////////////////////////////////////////////////////////////////////////

            bbCONTENT_SIZE!!.writeLong(jserver_connection_id)
            bbCONTENT_SIZE!!.writeLong(connection_id)
            bbCONTENT_SIZE!!.writeLong(connection_coocki)
            bbCONTENT_SIZE!!.writeInt(just_do_it)
            bbCONTENT_SIZE!!.writeLong(just_do_it_label)
            ////////////////////////////////////////////////////////////////////////////////
            val nes_fields: String = currentCommand!!.commands_necessarily_fields
            var nes_fields_symbol: String
            loop@ for (x in 1..FIELDS_SUBSCRIBE.size) {
                val subJSOCKET: JSOCKET_Subscribe =
                    FIELDS_SUBSCRIBE[x] ?: throw my_user_exceptions_class(
                        l_class_name = "JSOCKET",
                        l_function_name = "serialize",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "wrong number of field: $x"
                    )
                if (!subJSOCKET.serialied) {
                    continue@loop
                }
                nameField_length = 0
                nes_fields_symbol = nes_fields.substring(x - 1, x)
                if ("0" != nes_fields_symbol || !verify_fields) {

                    /*
                    lcraete_check_sum = false
                    if (craete_check_sum) {
                        lcraete_check_sum =
                            subJSOCKET.check_suming && "1" == nes_fields_symbol
                    }
                     */

                    when (subJSOCKET.fields_type) {
                        0 -> {
                            var bbb: ByteArray
                            var s: String
                            try {
                                //val nameField = nameFields[subJSOCKET.fields_name] as KProperty<String?>
                                s = subJSOCKET.getJSOCKET_FieldsValue(this) as String

                            } catch (e: Exception) {
                                if ("1" == nes_fields_symbol && verify_fields) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "serialize",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loop
                                }
                            }
                            if (s.isEmpty() || (s.length == 4 && s.equals("null", true))) {
                                if ("1" == nes_fields_symbol && verify_fields) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "serialize",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loop
                                }
                            }

                            if (subJSOCKET.fields_size_is_perminent && s.length != subJSOCKET.fields_size) {
                                throw my_user_exceptions_class(
                                    l_class_name = "JSOCKET",
                                    l_function_name = "serialize",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "field length is wrong: $x; ${s.length}"
                                )
                            }

                            bbb = s.encodeToByteArray()
                            nameField_length = bbb.size
                            while (nameField_length > subJSOCKET.fields_size) {
                                s = s.substring(0, s.length - 1)
                                bbb = s.encodeToByteArray()
                                nameField_length = bbb.size
                            }
                            bbCONTENT_SIZE!!.writeInt(x)
                            bbCONTENT_SIZE!!.writeInt(nameField_length)
                            setBytes(bbb, nameField_length, crypt)
                        }
                        1 -> {
                            try {
                                int_value = subJSOCKET.getJSOCKET_FieldsValue(this) as Int
                                //val nameField = nameFields[subJSOCKET.fields_name] as KProperty<Int?>
                                //int_value = nameField.getter.call()?:0
                            } catch (e: Exception) {
                                continue@loop
                            }
                            if (int_value.equals(0)) {
                                if ("1" == nes_fields_symbol && verify_fields) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "serialize",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loop
                                }
                            } else {
                                bbCONTENT_SIZE!!.writeInt(x)
                                bbCONTENT_SIZE!!.writeInt(4)
                                bbCONTENT_SIZE!!.writeInt(int_value)
                            }
                        }
                        2 -> {
                            try {
                                long_value = subJSOCKET.getJSOCKET_FieldsValue(this) as Long
                                //val nameField = nameFields[subJSOCKET.fields_name] as KProperty<Long?>
                                //long_value = nameField.getter.call()?:0L
                            } catch (e: Exception) {
                                continue@loop
                            }
                            if (long_value.equals(0L)) {
                                if ("1" == nes_fields_symbol && verify_fields) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "serialize",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loop
                                }
                            } else {
                                bbCONTENT_SIZE!!.writeInt(x)
                                bbCONTENT_SIZE!!.writeInt(8)
                                setLong(long_value, crypt)
                            }
                        }
                        3 -> {
                            continue@loop
                        }
                        4 -> {
                            if (content == null || content!!.isEmpty()) {
                                if ("1" == nes_fields_symbol && verify_fields) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "serialize",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loop
                                }
                            } else {
                                if (content!!.size <= currentCommand!!.SendBlobSize) {
                                    bbCONTENT_SIZE!!.writeInt(x)
                                    bbCONTENT_SIZE!!.writeInt(content!!.size)
                                    setBytes(
                                        content!!,
                                        content!!.size,
                                        crypt && currentCommand!!.cryptContent
                                    )
                                }
                            }
                        }
                    }
                }
            }
            val send_data = bbCONTENT_SIZE!!.build()
            val buf = BytePacketBuilder(ChunkBuffer.Pool)
            buf.writeFully(REQUEST_PREFIX)
            buf.writeInt(send_data.remaining.toInt())
            buf.writePacket(send_data)
            buf.writeFully(REQUEST_POSTFIX)
            return buf.build().readBytes()
        } catch (e: my_user_exceptions_class) {
            throw e
        } catch (e: Exception) {
            throw my_user_exceptions_class(
                l_class_name = "JSOCKET",
                l_function_name = "serialize",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = e.stackTraceToString()
            )
        } finally {
            connection_coocki = nature_connection_coocki
            try {
                bb?.close()
            } catch (e: Exception) {
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    /*
    @JsName("create_check_sum")
    @InternalAPI
    @ExperimentalTime
    fun create_check_sum(check_fields_lendth: Boolean) {
        if (h == null) h = HASH()
        var lcraete_check_sum: Boolean
        check_sum = 0L
        value_par7 = value_par7.trim().uppercase()
        if (just_do_it_label == 0L) {
            throw my_user_exceptions_class(
                l_class_name = "JSOCKET",
                l_function_name = "create_check_sum",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "just_do_it_label is null"
            )
        }
        if (just_do_it == 0) {
            throw my_user_exceptions_class(
                l_class_name = "JSOCKET",
                l_function_name = "create_check_sum",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "just_do_it is null"
            )
        }
        currentCommand = if (!COMMANDS.containsKey(just_do_it)) {
            throw my_user_exceptions_class(
                l_class_name = "JSOCKET",
                l_function_name = "create_check_sum",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "just_do_it not found: $just_do_it"
            )
        } else {
            COMMANDS[just_do_it] ?: throw my_user_exceptions_class(
                l_class_name = "JSOCKET",
                l_function_name = "create_check_sum",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "just_do_it not found: $just_do_it"
            )
        }

        if (connection_id != 0L) {
            check_sum = h!!.getCheckSumFromLong(connection_id, check_sum)
        }
        check_sum = h!!.getCheckSumFromLong(just_do_it.toLong(), check_sum)
        ////////////////////////////////////////////////////////////////////////////////
        val nes_fields: String = currentCommand!!.commands_necessarily_fields
        var nes_fields_symbol: String
        loopSerr@ for (x in 1..FIELDS_SUBSCRIBE.size) {
            val subJSOCKET: JSOCKET_Subscribe =
                FIELDS_SUBSCRIBE[x] ?: throw my_user_exceptions_class(
                    l_class_name = "JSOCKET",
                    l_function_name = "create_check_sum",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "wrong number of field: $x"
                )
            if (!subJSOCKET.check_suming) {
                continue@loopSerr
            }
            nameField_length = 0
            nes_fields_symbol = nes_fields.substring(x - 1, x)
            if ("0" != nes_fields_symbol) {
                lcraete_check_sum = subJSOCKET.check_suming && "1" == nes_fields_symbol
                if (lcraete_check_sum) {
                    when (subJSOCKET.fields_type) {
                        0 -> {
                            var bbb: ByteArray
                            var s: String
                            try {
                                s = subJSOCKET.getJSOCKET_FieldsValue(this) as String
                            } catch (e: Exception) {
                                if ("1" == nes_fields_symbol && check_fields_lendth) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "create_check_sum",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loopSerr
                                }
                            }
                            if (s.length == 4 && s.equals("null", true)) {
                                if ("1" == nes_fields_symbol && check_fields_lendth) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "create_check_sum",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loopSerr
                                }
                            }

                            if (subJSOCKET.fields_size_is_perminent && s.length != subJSOCKET.fields_size) {
                                throw my_user_exceptions_class(
                                    l_class_name = "JSOCKET",
                                    l_function_name = "create_check_sum",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "field length is wrong: $x; ${s.length}"
                                )
                            }
                            bbb = s.encodeToByteArray()
                            nameField_length = bbb.size
                            while (nameField_length > subJSOCKET.fields_size) {
                                s = s.substring(0, s.length - 1)
                                bbb = s.encodeToByteArray()
                                nameField_length = bbb.size
                            }
                            check_sum = h!!.getCheckSumFromByteArray(bbb, check_sum)
                        }
                        1 -> {
                            int_value = 0
                            try {
                                int_value = subJSOCKET.getJSOCKET_FieldsValue(this) as Int
                            } catch (e: Exception) {
                                continue@loopSerr
                            }
                            if (int_value.equals(0)) {
                                if ("1" == nes_fields_symbol && check_fields_lendth) {
                                    throw  my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "create_check_sum",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loopSerr
                                }
                            } else
                                check_sum = h!!.getCheckSumFromLong(int_value.toLong(), check_sum)
                        }
                        2 -> {
                            long_value = 0L
                            try {
                                long_value = subJSOCKET.getJSOCKET_FieldsValue(this) as Long
                            } catch (e: Exception) {
                                continue@loopSerr
                            }
                            if (long_value.equals(0L)) {
                                if ("1" == nes_fields_symbol && check_fields_lendth) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "create_check_sum",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loopSerr
                                }
                            } else
                                check_sum = h!!.getCheckSumFromLong(long_value, check_sum)
                        }
                        3 -> {
                            continue@loopSerr
                        }
                        4 -> {
                            if (content == null || content!!.isNotEmpty()) {
                                if ("1" == nes_fields_symbol && check_fields_lendth) {
                                    throw  my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "create_check_sum",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "field is empty: $x"
                                    )
                                } else {
                                    continue@loopSerr
                                }
                            } else {
                                h!!.getCheckSumFromByteArray(content!!, check_sum)
                            }
                        }
                    }
                }
            }
        }
    }
     */

    /////////////////////////////////////////////////////////////////////////////////

    @KorioExperimentalApi
    @JsName("desend_datad_ANSWERS_TYPES")
    suspend fun deserialize_ANSWERS_TYPES() {
        var promise: Promise<Boolean>? = null
        var currentCashData: KCashData? = null
        var object_info: ANSWER_TYPE? = null
        var nameField_number = 0
        var arr: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
        try {
            if (content == null || content!!.isEmpty()) {
                return
            }
            var answer_type: ANSWER_TYPE?
            var empty_answer_types: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
            var nameField_length: Int
            var recordSize: Int
            var subJSOCKET: ANSWER_TYPE_Subscribe?
            bb = ByteReadPacket(content!!)
            var record: ByteReadPacket
            var record_type = "0"

            loopChSum@ while (true) {
                if (bb!!.remaining >= 4) {
                    if (empty_answer_types.isEmpty()) {
                        empty_answer_types = ANSWER_TYPE.GetAnswerTypes() ?: ArrayDeque()
                        if (empty_answer_types.isEmpty()) {
                            val ans: ANSWER_TYPE = ANSWER_TYPE.GetAnswerType() ?: ANSWER_TYPE()
                            empty_answer_types.addLast(ans)
                        }
                    }
                    recordSize = bb!!.readInt()
                    record = ByteReadPacket(bb!!.readBytes(recordSize))
                    answer_type = empty_answer_types.removeFirst()
                } else {
                    break@loopChSum
                }
                loopChSum2@ while (record.remaining > 4) {

                    /*PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_1 ${answer_type.IDENTIFICATOR_1}")
                    PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_2 ${answer_type.IDENTIFICATOR_2}")
                    PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_3 ${answer_type.IDENTIFICATOR_3}")
                    PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_4 ${answer_type.IDENTIFICATOR_4}")
                    PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_5 ${answer_type.IDENTIFICATOR_5}")
                    PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_6 ${answer_type.IDENTIFICATOR_6}")
                    PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_7 ${answer_type.IDENTIFICATOR_7}")
                    PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_8 ${answer_type.IDENTIFICATOR_8}")
                    PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_9 ${answer_type.IDENTIFICATOR_9}")
                    PrintInformation.PRINT_INFO("nswer_type.IDENTIFICATOR_10 ${answer_type.IDENTIFICATOR_10}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_1 ${answer_type.INTEGER_1}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_2 ${answer_type.INTEGER_2}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_3 ${answer_type.INTEGER_3}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_4 ${answer_type.INTEGER_4}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_5 ${answer_type.INTEGER_5}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_6 ${answer_type.INTEGER_6}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_7 ${answer_type.INTEGER_7}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_8 ${answer_type.INTEGER_8}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_9 ${answer_type.INTEGER_9}")
                    PrintInformation.PRINT_INFO("nswer_type.INTEGER_10 ${answer_type.INTEGER_10}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_1 ${answer_type.LONG_1}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_2 ${answer_type.LONG_2}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_3 ${answer_type.LONG_3}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_4 ${answer_type.LONG_4}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_5 ${answer_type.LONG_5}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_6 ${answer_type.LONG_6}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_7 ${answer_type.LONG_7}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_8 ${answer_type.LONG_8}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_9 ${answer_type.LONG_9}")
                    PrintInformation.PRINT_INFO("nswer_type.LONG_10 ${answer_type.LONG_10}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_1 ${answer_type.STRING_1}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_2 ${answer_type.STRING_2}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_3 ${answer_type.STRING_3}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_4 ${answer_type.STRING_4}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_5 ${answer_type.STRING_5}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_6 ${answer_type.STRING_6}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_7 ${answer_type.STRING_7}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_8 ${answer_type.STRING_8}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_9 ${answer_type.STRING_9}")
                    PrintInformation.PRINT_INFO("nswer_type.STRING_10 ${answer_type.STRING_10}")*/

                    nameField_number = record.readInt()
                    subJSOCKET = FIELDS_SUBSCRIBE_ANSWER_TYPES[nameField_number]
                    nameField_length = record.readInt()
                    if ((subJSOCKET!!.fields_size_is_perminent && nameField_length != subJSOCKET.fields_size)
                        || (nameField_length > subJSOCKET.fields_size)
                    ) {
                        record.discardExact(nameField_length)
                        continue@loopChSum2
                    }
                    when (subJSOCKET.fields_type) {
                        0 -> {
                            //val l = bb!!.readBytes(nameField_length).decodeToString()
                            //PrintInformation.PRINT_INFO("nameField_number = $nameField_number, l = $l")
                            subJSOCKET.setANSWER_TYPE_FieldsValue(
                                answer_type,
                                record.readBytes(nameField_length).decodeToString()
                            )
                        }
                        1 -> {
                            subJSOCKET.setANSWER_TYPE_FieldsValue(answer_type, record.readInt())
                        }
                        2 -> {
                            subJSOCKET.setANSWER_TYPE_FieldsValue(answer_type, record.readLong())
                        }
                        3 -> {
                            record.discardExact(nameField_length)
                        }
                        4 -> {
                            subJSOCKET.setANSWER_TYPE_FieldsValue(answer_type, record.readBytes(nameField_length))
                        }
                    }


                }

                answer_type.LONG_20 = this.last_date_of_update

                if (nameField_number == 84) {  // big avatar;
                    if (object_info == null) {
                        throw my_user_exceptions_class(
                            l_class_name = "JSOCKET",
                            l_function_name = "deserialize_ANSWERS_TYPES",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "object_info is empty"
                        )
                    }
                    if (answer_type.BLOB_4 != null && answer_type.BLOB_4!!.isNotEmpty()) {
                        object_info.BLOB_4 = answer_type.BLOB_4
                        KBigAvatar.ADD_NEW_BIG_AVATAR(
                            KBigAvatar(
                                L_AVATAR_ID = object_info.answerTypeValues.GetMainAvatarId(),
                                L_AVATAR = answer_type.BLOB_4!!
                            )
                        )
                    }
                    break
                }

                if(answer_type.RECORD_TYPE.isEmpty()) {
                    throw my_user_exceptions_class(
                        l_class_name = "JSOCKET",
                        l_function_name = "deserialize_ANSWERS_TYPES",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "answer_type.RECORD_TYPE is empty"
                    )
                }

                if (record_type == "0") {
                    record_type = answer_type.RECORD_TYPE
                } else if (record_type != answer_type.RECORD_TYPE) {
                    if (currentCommand!!.commands_id != 1011000053) {        //SELECTOR.SELECT_CHATS;

                        if (currentCommand!!.commands_id == 1011000052) { //SELECTOR.SELECT_ALL_DATA_ON_CHAT;
                            var cash_sum: String
                            when (record_type) {
                                "4" -> {  // MESSEGES;
                                    cash_sum = GetCashSum(
                                        L_OBJECT_ID = answer_type.answerTypeValues.GetChatId(),
                                        L_RECORD_TYPE = record_type,
                                        L_COURSE = "1"
                                    )
                                }
                                "8", "9", "N" -> {  // CHATS_LIKES, CHATS_COST_TYPES, NOTICES;
                                    cash_sum = GetCashSum(
                                        L_OBJECT_ID = answer_type.answerTypeValues.GetChatId(),
                                        L_RECORD_TYPE = record_type,
                                        L_COURSE = "0"
                                    )
                                }
                                else -> {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "deserialize_ANSWERS_TYPES",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "RECORD_TYPE ${record_type} not defined"
                                    )
                                }
                            }

                            var cc = CASH_DATAS[cash_sum]
                            if (cc == null) {
                                var kc: KCashLastUpdate? = CASH_LAST_UPDATE[cash_sum]
                                if(kc == null){
                                    when (record_type) {
                                        "4" -> {  // MESSEGES;
                                            kc = KCashLastUpdate(
                                                L_OBJECT_ID = answer_type.answerTypeValues.GetChatId(),
                                                L_RECORD_TYPE = record_type,
                                                L_COURSE = "1"
                                            )
                                        }
                                        "8", "9" -> {  // CHATS_LIKES, CHATS_COST_TYPES, NOTICES;
                                            kc = KCashLastUpdate(
                                                L_OBJECT_ID = answer_type.answerTypeValues.GetChatId(),
                                                L_RECORD_TYPE = record_type,
                                                L_COURSE = "0"
                                            )
                                        }
                                    }
                                    //CASH_LAST_UPDATE[kc!!.CASH_SUM] = kc
                                }
                                cc = KCashData(kc!!)
                            }
                            println("cc.CashLastUpdate.RECORD_TYPE = ${cc.CashLastUpdate.RECORD_TYPE}")
                            cc.SET_RECORDS(arr)

                            record_type = answer_type.RECORD_TYPE
                            arr = ArrayDeque()


                        } else {
                            when (record_type) {
                                "1" -> KCommands.ADD_NEW_COMMANDS(arr)
                                "2" -> KMetaData.ADD_NEW_META_DATA(arr)
                                "5" -> KExceptions.ADD_NEW_EXCEPTIONS(arr)
                                "6" -> {
                                    throw my_user_exceptions_class(
                                        l_class_name = "JSOCKET",
                                        l_function_name = "deserialize_ANSWERS_TYPES",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "big avatar trying to add"
                                    )
                                }
                                "7" -> {
                                    KRegData.ADD_NEW_REG_DATA(arr)
                                    is_new_reg_data = true
                                }
                                "J", "K", "L" -> {
                                }
                                else -> {
                                    if (currentCashData == null) {
                                        currentCashData = CASH_DATAS[this.check_sum]
                                    }
                                    currentCashData?.ADD_NEW_CASH_DATA(
                                        arr = arr,
                                        l_last_select = this.last_date_of_update,
                                        l_just_do_it_label = this.just_do_it_label,
                                        l_limit = this.value_par8,
                                        l_count_of_all_records = this.value_par9,
                                        l_number_of_block = this.value_par7,
                                        l_object_id_from = this.value_id1,
                                        l_mess_id_from = this.value_par4
                                    )
                                }

                            }
                            record_type = answer_type.RECORD_TYPE
                            arr = ArrayDeque()
                        }
                    }else{    // delete chat;
                        if(answer_type.RECORD_TYPE == "8" &&
                            answer_type.answerTypeValues.GetMainAccountId() == Constants.Account_Id &&
                            answer_type.answerTypeValues.GetChatsLikesLastMessegeAdding().equals(0L)
                        ){  // if deleted chat
                             KChat.DELETE_CHATS(answer_type.answerTypeValues.GetChatId())
                            continue
                        }
                    }
                }

                when (answer_type.RECORD_TYPE) {
                    "6" -> {
                        throw my_user_exceptions_class(
                            l_class_name = "JSOCKET",
                            l_function_name = "deserialize_ANSWERS_TYPES",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "big avatar trying to add"
                        )
                    }
                    "J", "K", "L" -> {  // object_info
                        if (currentCommand!!.commands_access != "C") {
                            throw my_user_exceptions_class(
                                l_class_name = "JSOCKET",
                                l_function_name = "deserialize_ANSWERS_TYPES",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "answer is object? but command is not object_info."
                            )
                        }
                        if (object_info != null) {
                            throw my_user_exceptions_class(
                                l_class_name = "JSOCKET",
                                l_function_name = "deserialize_ANSWERS_TYPES",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "object_info is not empty"
                            )
                        }
                        object_info = answer_type
                    }
                }

                arr.addLast(answer_type)
            }

            if (record_type != "0") {
                if (currentCommand!!.commands_id == 1011000052) { //SELECTOR.SELECT_ALL_DATA_ON_CHAT;
                    val cash_sum: String
                    when (record_type) {
                        "4" -> {  // MESSEGES;
                            cash_sum = GetCashSum(
                                L_OBJECT_ID = arr.last().answerTypeValues.GetChatId(),
                                L_RECORD_TYPE = arr.last().RECORD_TYPE,
                                L_COURSE = "1"
                            )
                        }
                        "8", "9" -> {  // CHATS_LIKES, CHATS_COST_TYPES;
                            cash_sum = GetCashSum(
                                L_OBJECT_ID = arr.last().answerTypeValues.GetChatId(),
                                L_RECORD_TYPE = arr.last().RECORD_TYPE,
                                L_COURSE = "0"
                            )
                        }
                        else -> {
                            throw my_user_exceptions_class(
                                l_class_name = "JSOCKET",
                                l_function_name = "deserialize_ANSWERS_TYPES",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "RECORD_TYPE ${arr.last().RECORD_TYPE} not defined"
                            )
                        }
                    }

                    var cc = CASH_DATAS[cash_sum]
                    if (cc == null) {
                        var kc: KCashLastUpdate? = CASH_LAST_UPDATE[cash_sum]
                        if(kc == null){
                            when (arr.last().RECORD_TYPE) {
                                "4" -> {  // MESSEGES;
                                    kc = KCashLastUpdate(
                                        L_OBJECT_ID = arr.last().answerTypeValues.GetChatId(),
                                        L_RECORD_TYPE = arr.last().RECORD_TYPE,
                                        L_COURSE = "1"
                                    )
                                }
                                "8", "9" -> {  // CHATS_LIKES, CHATS_COST_TYPES, NOTICES;
                                    kc = KCashLastUpdate(
                                        L_OBJECT_ID = arr.last().answerTypeValues.GetChatId(),
                                        L_RECORD_TYPE = arr.last().RECORD_TYPE,
                                        L_COURSE = "0"
                                    )
                                }
                            }
                            //CASH_LAST_UPDATE[kc!!.CASH_SUM] = kc
                        }
                        cc = KCashData(kc!!)
                    }
                    cc.SET_RECORDS(arr)
                } else {
                    promise = when (record_type) {
                        "1" -> KCommands.ADD_NEW_COMMANDS(arr)
                        "2" -> KMetaData.ADD_NEW_META_DATA(arr)
                        "5" -> KExceptions.ADD_NEW_EXCEPTIONS(arr)
                        "6" -> {
                            throw my_user_exceptions_class(
                                l_class_name = "JSOCKET",
                                l_function_name = "deserialize_ANSWERS_TYPES",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "big avatar trying to add"
                            )
                        }
                        "7" -> {
                            is_new_reg_data = true
                            KRegData.ADD_NEW_REG_DATA(arr)
                        }
                        "J", "K", "L" -> {  // object_info
                            null
                         }
                        else -> {
                            if (currentCashData == null) {
                                currentCashData = CASH_DATAS[this.check_sum]
                            }
                            currentCashData?.ADD_NEW_CASH_DATA(
                                arr = arr,
                                l_last_select = this.last_date_of_update,
                                l_just_do_it_label = this.just_do_it_label,
                                l_limit = this.value_par8,
                                l_count_of_all_records = this.value_par9,
                                l_number_of_block = this.value_par7,
                                l_object_id_from = this.value_id1,
                                l_mess_id_from = this.value_par4
                            )
                        }
                    }
                }
            }
            promise?.await()

        } catch (e: my_user_exceptions_class) {
            throw e
        } catch (n: Exception) {
            throw my_user_exceptions_class(
                l_class_name = "JSOCKET",
                l_function_name = "deserialize_ANSWERS_TYPES",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = n.toString()
            )
        } finally {
            if (currentCommand!!.commands_access == "B") {
                if (currentCashData == null && just_do_it_successfull.equals("0")) {
                    if (currentCommand!!.commands_id != 1011000052) { //SELECTOR.SELECT_ALL_DATA_ON_CHAT;
                        currentCashData = CASH_DATAS[this.check_sum]

                        if (currentCashData != null) {
                            val start_record_id: String
                            when (currentCashData.CashLastUpdate.RECORD_TYPE) {
                                "4", "M" //MESSEGES
                                -> {
                                    start_record_id = this.value_par4
                                }
                                "A", "C", "E", "G" //ALBUMS_COMMENTS, ALBUMS_LINKS_COMMENTS, OBJECTS_LINKS_COMMENTS, OBJECTS_COMMENTS
                                -> {
                                    start_record_id = this.value_par4
                                }
                                else
                                -> {
                                    start_record_id = this.value_id1
                                }
                            }
                            currentCashData.UPDATE_LAST_SELECT(
                                lastSelect = this.last_date_of_update,
                                object_recod_id_from = start_record_id
                            )
                        }
                    }
                }
            }
            if (currentCommand!!.commands_access == "C") {
                if (object_info != null) {
                    OBJECTS_INFO[object_info.RECORD_TABLE_ID]!!.merge(object_info)
                }
            }
            try {
                bb?.close()
            } catch (e: Exception) {
            }
            content = null
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    @JsName("deserialize")
    @InternalAPI
    fun deserialize(
        lbb: ByteReadPacket,
        p_original_connection_coocki: Long = 0L,
        ip: Boolean = true,
        p_new_connection_coocki: Long = 0L
    ) /////////////////////////////verify/////////////////////////////////////////////
    {
        try {
            is_new_reg_data = false
            bbCONTENT_SIZE = BytePacketBuilder(ChunkBuffer.Pool)
            if (h == null) h = HASH()
            request_size = lbb.remaining
            bb = lbb
            start_position = 0

           // for debug
           //bb!!.readBytes(17)
            // request_size = lbb.readInt().toLong()

            if (ip) {
                ip_port = bb!!.readInt()
                ip_address = bb!!.readBytes(23).decodeToString()
            }
            jserver_connection_id = bb!!.readLong()
            connection_id = bb!!.readLong()

            if (Constants.myConnectionsID != 0L && Constants.myConnectionsID != connection_id) {
                throw my_user_exceptions_class(
                    l_class_name = "JSOCKET",
                    l_function_name = "deserialize",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "(connection_id = $connection_id) not equal (myConnectionsID = ${Constants.myConnectionsID} ;)  "
                )
            }

            if (connection_id != 0L && Constants.myConnectionsID == 0L) {
                Constants.myConnectionsID = connection_id
                is_new_reg_data = true
            }

            connection_coocki = bb!!.readLong()
            just_do_it = bb!!.readInt()
            just_do_it_label = bb!!.readLong()

            if (!COMMANDS.containsKey(just_do_it)) {
                throw my_user_exceptions_class(
                    l_class_name = "JSOCKET",
                    l_function_name = "deserialize",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "just_do_it not found $just_do_it"
                )
            } else {
                currentCommand = COMMANDS[just_do_it] ?: throw my_user_exceptions_class(
                    l_class_name = "JSOCKET",
                    l_function_name = "deserialize",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "just_do_it not found $just_do_it"
                )
            }
            //crypt = currentCommand!!.isCrypt
            crypt = (connection_coocki != 0L || currentCommand!!.isCrypt)
            if (just_do_it_label == 0L) {
                throw my_user_exceptions_class(
                    l_class_name = "JSOCKET",
                    l_function_name = "deserialize",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "just_do_it_label is null"
                )
            }
            if (crypt) {
                if (connection_id == 0L) {
                    throw my_user_exceptions_class(
                        l_class_name = "JSOCKET",
                        l_function_name = "deserialize",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "connection_id is null"
                    )
                }
                if (connection_coocki == 0L) {
                    throw my_user_exceptions_class(
                        l_class_name = "JSOCKET",
                        l_function_name = "deserialize",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "connection_coocki is null"
                    )
                }
                if (p_original_connection_coocki == 0L) {
                    throw my_user_exceptions_class(
                        l_class_name = "JSOCKET",
                        l_function_name = "deserialize",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "exc_user_coocki_not_equal_db_coocki3: db coocki = $p_original_connection_coocki, user coocki = $connection_coocki"
                    )
                }
                if (md5String.isEmpty()) {
                    md5String = h!!.getNewMD5String(p_original_connection_coocki, just_do_it_label)
                    reverseMD5String = h!!.getReverseMD5String(md5String)
                    md5LongArray = h!!.getNewMD5longArray(md5String)
                    reverseMD5LongArray = h!!.getReverseMD5longArray(reverseMD5String)
                }
                if (connection_coocki == h!!.getNewCoockiLong(md5String)) {
                    connection_coocki = p_original_connection_coocki
                } else {
                    if (p_new_connection_coocki != 0L) {

                        md5String = h!!.getNewMD5String(p_new_connection_coocki, just_do_it_label)
                        reverseMD5String = h!!.getReverseMD5String(md5String)
                        md5LongArray = h!!.getNewMD5longArray(md5String)
                        reverseMD5LongArray = h!!.getReverseMD5longArray(reverseMD5String)

                        if (connection_coocki == h!!.getNewCoockiLong(md5String)) {
                            Constants.myConnectionsCoocki = p_new_connection_coocki
                            is_new_reg_data = true
                            connection_coocki = p_new_connection_coocki
                        } else {
                            throw my_user_exceptions_class(
                                l_class_name = "JSOCKET",
                                l_function_name = "deserialize",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "exc_user_coocki_not_equal_db_coocki1: db coocki = $p_original_connection_coocki, user coocki = $connection_coocki"
                            )
                        }
                    } else {
                        throw my_user_exceptions_class(
                            l_class_name = "JSOCKET",
                            l_function_name = "deserialize",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "exc_user_coocki_not_equal_db_coocki2: db coocki = $p_original_connection_coocki, user coocki = $connection_coocki"
                        )
                    }
                }

                start_position = md5String.substring(md5String.length - 1, md5String.length).toInt(16)
                reverse_start_position =
                    reverseMD5String.substring(reverseMD5String.length - 1, reverseMD5String.length).toInt(16)
            }
            ////////////////////////////////////////////////////////////////////////////////
            //val nameFields = this::class.members.asSequence().associateBy { it.name }
            loopDeSerr@ while (bb!!.isNotEmpty) {
            //loopDeSerr@ while (bb!!.remaining > 8L) {  // for debug;
                nameField_length = 0
                nameField_number = 0
                nameField_number = bb!!.readInt()
                val subJSOCKET =
                    FIELDS_SUBSCRIBE[nameField_number] ?: throw my_user_exceptions_class(
                        l_class_name = "JSOCKET",
                        l_function_name = "deserialize",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "wrong number of field: $nameField_number"
                    )
                nameField_length = bb!!.readInt()
                if (subJSOCKET.serialied) {
                    if ((subJSOCKET.fields_size_is_perminent && nameField_length != subJSOCKET.fields_size)
                        || nameField_length > (if (subJSOCKET.fields_type != 4) {
                            subJSOCKET.fields_size
                        } else {
                            currentCommand!!.ReturnBlobSize
                        })
                    ) {
                        bb!!.discardExact(nameField_length)
                        getNewStartPositionIfSkipBytes(nameField_length)
                        continue@loopDeSerr
                    }
                    when (subJSOCKET.fields_type) {
                        0 -> {
                            //val nameField = nameFields[subJSOCKET.fields_name] as KMutableProperty<String?>
                            getStringField(subJSOCKET, nameField_length, crypt)
                        }
                        1 -> {
                            subJSOCKET.setJSOCKET_FieldsValue(this, bb!!.readInt())
                        }
                        2 -> {
                            getLongField(subJSOCKET, crypt)
                        }
                        3 -> bb!!.discardExact(nameField_length)
                        4 -> {
                            getBytesField(subJSOCKET, nameField_length, crypt && currentCommand!!.cryptContent)
                        }
                        else -> {
                            bb!!.discardExact(nameField_length)
                            continue@loopDeSerr
                        }
                    }
                }
            }
        } finally {
            try {
                bbCONTENT_SIZE?.close()
                if (!request_profile.equals(Constants.myRequestProfile)) {
                    is_new_reg_data = true
                }
            } catch (e: Exception) {
            }
            try {
                bb?.close()
            } catch (e: Exception) {
            }
        }
    }

    //////////////////////////write bytes into outStream/////////////////////////////
    private fun setBytes(
        input_bytes: ByteArray,
        size: Int,
        lcrypt: Boolean
    ) {
        /*
        if (lc_craete_check_sum) {
            check_sum = h!!.getCheckSumFromByteArray(data = input_bytes, checksum = check_sum)
        }
         */

        if (lcrypt) {
            setCryptBytes(input_bytes, size)
        } else {
            bbCONTENT_SIZE!!.writeFully(input_bytes, 0, size)
        }
    }


    private fun setLong(input_long: Long, crypt: Boolean) {

        /*
        if (lc_craete_check_sum) {
            check_sum = h!!.getCheckSumFromLong(input_long, check_sum)
        }
         */

        if (crypt) {
            bbCONTENT_SIZE!!.writeLong(input_long xor md5LongArray[start_position] xor reverseMD5LongArray[reverse_start_position])
            getNewStartPosition()
        } else {
            bbCONTENT_SIZE!!.writeLong(input_long)
        }
    }


    private fun setCryptBytes(input_bytesA: ByteArray, size: Int) {
        if (input_bytesA.isEmpty()) return
        val input_bytes = ByteReadPacket(input_bytesA)
        if (size < 8) {
            for (x in 0 until size) {
                bbCONTENT_SIZE!!.writeByte(
                    (input_bytes.readByte()
                        .toLong() xor md5LongArray[start_position] xor reverseMD5LongArray[reverse_start_position]).toByte()
                )
                getNewStartPosition()
            }
            return
        }
        var x = 0
        while (x + 8 <= size) {
            bbCONTENT_SIZE!!.writeLong(input_bytes.readLong() xor md5LongArray[start_position] xor reverseMD5LongArray[reverse_start_position])
            getNewStartPosition()
            x += 8
        }
        while (x < size) {
            bbCONTENT_SIZE!!.writeByte(
                (input_bytes.readByte()
                    .toLong() xor md5LongArray[start_position] xor reverseMD5LongArray[reverse_start_position]).toByte()
            )
            x += 1
            getNewStartPosition()
        }
    }

    /////////////////////get Bytes from Channel////////////////////////////////////
    @InternalAPI

    private fun getStringField(field: JSOCKET_Subscribe, size: Int, crypt: Boolean) {
        if (crypt) {
            field.setJSOCKET_FieldsValue(this, getCryptBytes(size)?.decodeToString())
        } else {
            field.setJSOCKET_FieldsValue(this, bb!!.readBytes(size).decodeToString())
        }
    }

    @InternalAPI
    private fun getBytesField(field: JSOCKET_Subscribe, size: Int, crypt: Boolean) {
        if (crypt) {
            field.setJSOCKET_FieldsValue(this, getCryptBytes(size))
        } else {
            field.setJSOCKET_FieldsValue(this, bb!!.readBytes(size))
        }
    }


    @InternalAPI
    private fun getLongField(field: JSOCKET_Subscribe, crypt: Boolean) {
        if (crypt) {
            field.setJSOCKET_FieldsValue(
                this,
                bb!!.readLong() xor md5LongArray[start_position] xor reverseMD5LongArray[reverse_start_position]
            )
            getNewStartPosition()
        } else {
            field.setJSOCKET_FieldsValue(this, bb!!.readLong())
        }
    }


    private fun getCryptBytes(size: Int): ByteArray? {
        if (size < 1) return null
        val lbb = BytePacketBuilder(ChunkBuffer.Pool)
        var x = 0
        if (size < 8) {
            while (x < size) {
                lbb.writeByte(
                    (bb!!.readByte()
                        .toLong() xor md5LongArray[start_position] xor reverseMD5LongArray[reverse_start_position]).toByte()
                )
                getNewStartPosition()
                x += 1
            }
            return lbb.build().readBytes()
        }
        while (x + 8 <= size) {
            lbb.writeLong(bb!!.readLong() xor md5LongArray[start_position] xor reverseMD5LongArray[reverse_start_position])
            x += 8
            getNewStartPosition()
        }
        while (x < size) {
            lbb.writeByte(
                (bb!!.readByte()
                    .toLong() xor md5LongArray[start_position] xor reverseMD5LongArray[reverse_start_position]).toByte()
            )
            getNewStartPosition()
            x += 1
        }
        return lbb.build().readBytes()
    }

    ////////////////////////////////////////////////////////////////////////////////
    private fun getNewStartPosition() {
        start_position = (start_position + 1) and 0x0000000F
        reverse_start_position = (reverse_start_position xor start_position) and 0x0000000F
    }

    private fun getNewStartPositionIfSkipBytes(size_of_skip: Int) {
        var y = size_of_skip
        if (size_of_skip < 8) {
            while (y > 0) {
                getNewStartPosition()
                y--
            }
            return
        }
        while (y >= 8) {
            getNewStartPosition()
            y -= 8
        }
        while (y > 0) {
            getNewStartPosition()
            y--
        }
    }

    fun getmd5LongArray(): LongArray {
        return md5LongArray
    }


    fun GetCashSum(
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

}