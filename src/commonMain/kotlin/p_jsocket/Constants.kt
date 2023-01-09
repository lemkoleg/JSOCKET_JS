package p_jsocket

import CrossPlatforms.getMyOS
import Tables.META_DATA
import atomic.AtomicBoolean
import com.soywiz.klock.TimeSpan
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
object Constants {
    
    private val lock = Mutex()

    @JsName("SERVER_DNS_NAME")
    val SERVER_DNS_NAME: String = "MINI"

    @JsName("myOS")
    val myOS = getMyOS()

    @JsName("isInterrupted")
    val isInterrupted = AtomicBoolean(false)

    @JsName("dbLocalName")
    const val dbLocalName = "AUFDB"

    @JsName("CLIENT_TIMEOUT")
    var CLIENT_TIMEOUT = 20000L

    @JsName("TIME_SPAN_FOR_LOOP")
    var TIME_SPAN_FOR_LOOP = TimeSpan(10.0)

    @JsName("STANDART_QUEUE_SIZE")
    var STANDART_QUEUE_SIZE = 1000

    @JsName("TIME_OUT_VERIFY_MESSEGES")
    var TIME_OUT_VERIFY_MESSEGES = 10000

    @JsName("MAX_COUNT_OF_REQUEST_ON_CLIENT_REQUESTS_QUEUE")
    var MAX_COUNT_OF_REQUEST_ON_CLIENT_REQUESTS_QUEUE = 200

    @JsName("MAX_REQUEST_SIZE_B")
    var MAX_REQUEST_SIZE_B = 51205000

    @JsName("CLIENT_ANSWER_TYPE_POOL_SIZE")
    var CLIENT_ANSWER_TYPE_POOL_SIZE = 100000

    @JsName("CLIENT_ANSWER_TYPE_POOLS_SIZE")
    var CLIENT_ANSWER_TYPE_POOLS_SIZE = 5000

    @JsName("CLIENT_ANSWER_TYPE_POOLS_CHUNK_SIZE")
    var CLIENT_ANSWER_TYPE_POOLS_CHUNK_SIZE = 20

    @JsName("MAX_SMALL_AVATAR_SIZE_B")
    var MAX_SMALL_AVATAR_SIZE_B = 6400

    @JsName("MAX_CUT_BIG_AVATAR_SIZE_B")
    var MAX_CUT_BIG_AVATAR_SIZE_B = 12800

    @JsName("MAX_BIG_AVATAR_SIZE_B")
    var MAX_BIG_AVATAR_SIZE_B = 19200

    @JsName("TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES")
    var TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES = 30000L

    @JsName("TIME_OUT_FOR_CLIENT_JSOCKETS")
    var TIME_OUT_FOR_CLIENT_JSOCKETS = TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES * 1000000

    @JsName("MAX_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT")
    var MAX_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT = 200L

    @JsName("MIN_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT")
    var MIN_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT = 10L

    @JsName("MAX_TIME_FOR_WAIT_CLIENT_LISTENER_INPUT")
    var MAX_TIME_FOR_WAIT_CLIENT_LISTENER_INPUT = 100L

    @JsName("CLIENT_JSOCKET_POOL_SIZE")
    var CLIENT_JSOCKET_POOL_SIZE = 10000

    @JsName("LISTENER_QUEUE_SIZE")
    var LISTENER_QUEUE_SIZE = 10000

    @JsName("FIX_INTO_DB_MESSEGE_SYSTEM_ERRORS")
    var FIX_INTO_DB_MESSEGE_SYSTEM_ERRORS = 1

    @JsName("FIX_INTO_DB_MESSEGE_USER_ERRORS_BY_FILE")
    var FIX_INTO_DB_MESSEGE_USER_ERRORS_BY_FILE = 1

    @JsName("FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME")
    var FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME = 1

    @JsName("FIX_INTO_SCREEN_ERRORS")
    var FIX_INTO_SCREEN_ERRORS = 1

    @JsName("USE_SINGLE_FILE_FOR_FIX_ERRORS")
    var USE_SINGLE_FILE_FOR_FIX_ERRORS = 1

    @JsName("PRINT_INTO_SCREEN_DEBUG_INFORMATION")
    var PRINT_INTO_SCREEN_DEBUG_INFORMATION = 1

    @JsName("MAX_FILES_SIZE_B")
    var MAX_FILES_SIZE_B = 52428800

    @JsName("SEND_AVATAR_SIZE")
    var SEND_AVATAR_SIZE = 196608 //1310400

    @JsName("AVATAR_MAX_SIZE_FOR_LOADING")
    var AVATAR_MAX_SIZE_FOR_LOADING = 33554432 // 32Mb

    @JsName("AVATARSIZE")
    var AVATARSIZE = 65520

    @JsName("CHUNK_SIZE")
    var CHUNK_SIZE: Int = 1048576

    @JsName("MAX_COUNT_TRYING_SEND_RECEIVE_FILE_CHUNKS")
    var MAX_COUNT_TRYING_SEND_RECEIVE_FILE_CHUNKS: Int = 10000

    @JsName("MAX_COUNT_TRYING_SET_CONNECTION")
    var MAX_COUNT_TRYING_SET_CONNECTION: Int = 500

    @JsName("IS_VERIFY_FILES_DOWNLOADED_FOR_SAVE_MEDIA")
    var IS_VERIFY_FILES_DOWNLOADED_FOR_SAVE_MEDIA: Int = 1

    @JsName("LIMIT_FOR_SELECT")
    var LIMIT_FOR_SELECT: Int = 100

    @JsName("TIME_OUT_OF_USE_FILE_SERVICE_FOR_CLIENT")
    var TIME_OUT_OF_USE_FILE_SERVICE_FOR_CLIENT: Long = 55000L

    @JsName("TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR")
    var TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR: Long = 2000L

    @JsName("IS_DOWNLOAD_TO_MEMORY_ALL_CASH_ON_CLIENT")
    var IS_DOWNLOAD_TO_MEMORY_ALL_CASH_ON_CLIENT: Int = 0

    @JsName("TIMEOUT_FOR_CLIENT_CASH_UPDATE")
    var TIMEOUT_FOR_CLIENT_CASH_UPDATE: Long = 5000

    @JsName("MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS")
    var MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS: Int = 2000

    @JsName("MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO")
    var MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO: Int = 2000

    @JsName("MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS")
    var MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS: Int = 5000

    @JsName("CALL_UPDATED_CASH_DATA_FOR_EACH_INCOMING_BLOCK")
    var CALL_UPDATED_CASH_DATA_FOR_EACH_INCOMING_BLOCK: Int = 1

    @JsName("MAX_COUNT_OF_CASHDATA_BLOCKS_OF_MESSEGES")
    var MAX_COUNT_OF_CASHDATA_BLOCKS_OF_MESSEGES: Int = 1999999999

    @JsName("MAX_COUNT_OF_CASHDATA_OF_LINKS")
    var MAX_COUNT_OF_CASHDATA_OF_LINKS: Int = 1000

    @JsName("MAX_COUNT_OF_CASHDATA_OF_OBJECT_INFO")
    var MAX_COUNT_OF_CASHDATA_OF_OBJECT_INFO: Int = 10000

    @JsName("MAX_COUNT_OF_CASHDATA_OF_TEXT_LISTS")
    var MAX_COUNT_OF_CASHDATA_OF_TEXT_LISTS: Int = 1000

    @JsName("MAX_COUNT_OF_CASHDATA_BLOCKS_OF_CHATS")
    var MAX_COUNT_OF_CASHDATA_BLOCKS_OF_CHATS: Int = 1999999999

    @JsName("MAX_COUNT_OF_CASHDATA_SAVE_OBJECT_INFO")
    var MAX_COUNT_OF_CASHDATA_SAVE_OBJECT_INFO: Int = 10000

    @JsName("RE_SEND_REQUEST_PROFILE_BY_TIMEOUT")
    var RE_SEND_REQUEST_PROFILE_BY_TIMEOUT = 1

    /////////////////////// reg data //////////////////////
    @JsName("myConnectionsID")
    var myConnectionsID = 0L

    @JsName("myConnectionsCoocki")
    var myConnectionsCoocki = 0L

    @JsName("myConnectionContext")
    var myConnectionContext = ""

    @JsName("myLang")
    var myLang = "ENG"

    @JsName("myDeviceId")
    var myDeviceId = ""

    @JsName("myRequestProfile")
    var myRequestProfile = ""

    @JsName("myAccountProfile")
    var myAccountProfile = ""

    @JsName("isPRO")
    var isPRO = false

    @JsName("mailConfirm")
    var mailConfirm = false

    @JsName("Account_Id")
    var Account_Id = ""

    @JsName("Account_Name")
    var Account_Name = ""

    @JsName("Account_Access")
    var Account_Access = ""

    @JsName("Avatar_Id")
    var Avatar_Id = ""

    @JsName("ORIGINAL_AVATAR_SIZE")
    var ORIGINAL_AVATAR_SIZE = ""

    @JsName("AVATAR_SERVER")
    var AVATAR_SERVER = ""

    @JsName("AVATAR_LINK")
    var AVATAR_LINK = ""

    @JsName("BALANCE_OF_CHATS")
    var BALANCE_OF_CHATS = 0

    @JsName("LAST_UPDATE")
    var LAST_UPDATE = 0L

    @JsName("AVATAR_1")
    var AVATAR_1: ByteArray? = null

    @JsName("AVATAR_2")
    var AVATAR_2: ByteArray? = null

    @JsName("AVATAR_3")
    var AVATAR_3: ByteArray? = null



    

    fun initialise() {
        CLIENT_TIMEOUT = META_DATA["CLIENT_TIMEOUT"]?:CLIENT_TIMEOUT

        TIME_SPAN_FOR_LOOP  = if(META_DATA["TIME_SPAN_FOR_LOOP"] != null)  TimeSpan(META_DATA["TIME_SPAN_FOR_LOOP"]!!.toDouble()) else TIME_SPAN_FOR_LOOP
        
        STANDART_QUEUE_SIZE = META_DATA["STANDART_QUEUE_SIZE"]?.toInt()?:STANDART_QUEUE_SIZE
        
        TIME_OUT_VERIFY_MESSEGES = META_DATA["TIME_OUT_VERIFY_MESSEGES"]?.toInt()?:TIME_OUT_VERIFY_MESSEGES
        
        MAX_COUNT_OF_REQUEST_ON_CLIENT_REQUESTS_QUEUE = META_DATA["MAX_COUNT_OF_REQUEST_ON_CLIENT_REQUESTS_QUEUE"]?.toInt()?:MAX_COUNT_OF_REQUEST_ON_CLIENT_REQUESTS_QUEUE
        
        MAX_REQUEST_SIZE_B = META_DATA["MAX_REQUEST_SIZE_B"]?.toInt()?:MAX_REQUEST_SIZE_B
        
        CLIENT_ANSWER_TYPE_POOL_SIZE = META_DATA["CLIENT_ANSWER_TYPE_POOL_SIZE"]?.toInt()?:CLIENT_ANSWER_TYPE_POOL_SIZE
        
        CLIENT_ANSWER_TYPE_POOLS_SIZE = META_DATA["CLIENT_ANSWER_TYPE_POOLS_SIZE"]?.toInt()?:CLIENT_ANSWER_TYPE_POOLS_SIZE
        
        CLIENT_ANSWER_TYPE_POOLS_CHUNK_SIZE = META_DATA["CLIENT_ANSWER_TYPE_POOLS_CHUNK_SIZE"]?.toInt()?:CLIENT_ANSWER_TYPE_POOLS_CHUNK_SIZE
        
        MAX_SMALL_AVATAR_SIZE_B = META_DATA["MAX_SMALL_AVATAR_SIZE_B"]?.toInt()?:MAX_SMALL_AVATAR_SIZE_B
        
        MAX_CUT_BIG_AVATAR_SIZE_B = META_DATA["MAX_CUT_BIG_AVATAR_SIZE_B"]?.toInt()?:MAX_CUT_BIG_AVATAR_SIZE_B
        
        MAX_BIG_AVATAR_SIZE_B = META_DATA["MAX_BIG_AVATAR_SIZE_B"]?.toInt()?:MAX_BIG_AVATAR_SIZE_B
        
        TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES = META_DATA["TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES "]?:TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES

        TIME_OUT_FOR_CLIENT_JSOCKETS = TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES * 1000000

        MAX_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT = META_DATA["MAX_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT"]?:MAX_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT
        
        MIN_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT = META_DATA["MIN_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT"]?:MIN_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT
        
        MAX_TIME_FOR_WAIT_CLIENT_LISTENER_INPUT = META_DATA["MMAX_TIME_FOR_WAIT_CLIENT_LISTENER_INPUT"]?:MAX_TIME_FOR_WAIT_CLIENT_LISTENER_INPUT
        
        CLIENT_JSOCKET_POOL_SIZE = META_DATA["CLIENT_JSOCKET_POOL_SIZE "]?.toInt()?:CLIENT_JSOCKET_POOL_SIZE

        LISTENER_QUEUE_SIZE = META_DATA["LISTENER_QUEUE_SIZE"]?.toInt()?:LISTENER_QUEUE_SIZE
        
        FIX_INTO_DB_MESSEGE_SYSTEM_ERRORS = META_DATA["FIX_INTO_DB_MESSEGE_SYSTEM_ERRORS"]?.toInt()?:FIX_INTO_DB_MESSEGE_SYSTEM_ERRORS
        
        FIX_INTO_DB_MESSEGE_USER_ERRORS_BY_FILE = META_DATA["FIX_INTO_DB_MESSEGE_USER_ERRORS_BY_FILE"]?.toInt()?:FIX_INTO_DB_MESSEGE_USER_ERRORS_BY_FILE
        
        FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME = META_DATA["FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME"]?.toInt()?:FIX_INTO_DB_MESSEGE_FULL_ERRORS_NAME
        
        FIX_INTO_SCREEN_ERRORS = META_DATA["FIX_INTO_SCREEN_ERRORS"]?.toInt()?:FIX_INTO_SCREEN_ERRORS
        
        USE_SINGLE_FILE_FOR_FIX_ERRORS = META_DATA["USE_SINGLE_FILE_FOR_FIX_ERRORS"]?.toInt()?:USE_SINGLE_FILE_FOR_FIX_ERRORS
        
        PRINT_INTO_SCREEN_DEBUG_INFORMATION = META_DATA["PRINT_INTO_SCREEN_DEBUG_INFORMATION"]?.toInt()?:PRINT_INTO_SCREEN_DEBUG_INFORMATION
        
        MAX_FILES_SIZE_B = META_DATA["MAX_FILES_SIZE_B"]?.toInt()?:MAX_FILES_SIZE_B
        
        SEND_AVATAR_SIZE = META_DATA["SEND_AVATAR_SIZE"]?.toInt()?:SEND_AVATAR_SIZE
        
        AVATAR_MAX_SIZE_FOR_LOADING = META_DATA["AVATAR_MAX_SIZE_FOR_LOADING"]?.toInt()?:AVATAR_MAX_SIZE_FOR_LOADING
        
        AVATARSIZE = META_DATA["AVATARSIZE"]?.toInt()?:AVATARSIZE
        
        CHUNK_SIZE = META_DATA["CHUNK_SIZE"]?.toInt()?:CHUNK_SIZE
        
        MAX_COUNT_TRYING_SEND_RECEIVE_FILE_CHUNKS= META_DATA["MAX_COUNT_TRYING_SEND_RECEIVE_FILE_CHUNKS"]?.toInt()?:MAX_COUNT_TRYING_SEND_RECEIVE_FILE_CHUNKS
        
        MAX_COUNT_TRYING_SET_CONNECTION = META_DATA["MAX_COUNT_TRYING_SET_CONNECTION"]?.toInt()?:MAX_COUNT_TRYING_SET_CONNECTION
        
        IS_VERIFY_FILES_DOWNLOADED_FOR_SAVE_MEDIA= META_DATA["IS_VERIFY_FILES_DOWNLOADED_FOR_SAVE_MEDIA"]?.toInt()?:IS_VERIFY_FILES_DOWNLOADED_FOR_SAVE_MEDIA
        
        LIMIT_FOR_SELECT = META_DATA["LIMIT_FOR_SELECT"]?.toInt()?:LIMIT_FOR_SELECT
        
        TIME_OUT_OF_USE_FILE_SERVICE_FOR_CLIENT = META_DATA["TIME_OUT_OF_USE_FILE_SERVICE_FOR_CLIENT"]?:TIME_OUT_OF_USE_FILE_SERVICE_FOR_CLIENT
        
        TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR = META_DATA["TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR"]?:TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR
        
        IS_DOWNLOAD_TO_MEMORY_ALL_CASH_ON_CLIENT = META_DATA["IS_DOWNLOAD_TO_MEMORY_ALL_CASH_ON_CLIENT"]?.toInt()?:IS_DOWNLOAD_TO_MEMORY_ALL_CASH_ON_CLIENT
        
        TIMEOUT_FOR_CLIENT_CASH_UPDATE = META_DATA["TIMEOUT_FOR_CLIENT_CASH_UPDATE"]?:TIMEOUT_FOR_CLIENT_CASH_UPDATE

        MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS = META_DATA["MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS"]?.toInt()?:MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS

        MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO = META_DATA["MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO"]?.toInt()?:MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO

        MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS = META_DATA["MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS"]?.toInt()?:MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS

        CALL_UPDATED_CASH_DATA_FOR_EACH_INCOMING_BLOCK = META_DATA["CALL_UPDATED_CASH_DATA_FOR_EACH_INCOMING_BLOCK"]?.toInt()?:CALL_UPDATED_CASH_DATA_FOR_EACH_INCOMING_BLOCK

        MAX_COUNT_OF_CASHDATA_BLOCKS_OF_MESSEGES = META_DATA["MAX_COUNT_OF_CASHDATA_BLOCKS_OF_MESSEGES"]?.toInt()?:MAX_COUNT_OF_CASHDATA_BLOCKS_OF_MESSEGES

        MAX_COUNT_OF_CASHDATA_OF_LINKS = META_DATA["MAX_COUNT_OF_CASHDATA_OF_LINKS"]?.toInt()?:MAX_COUNT_OF_CASHDATA_OF_LINKS

        MAX_COUNT_OF_CASHDATA_OF_OBJECT_INFO = META_DATA["MAX_COUNT_OF_CASHDATA_OF_OBJECT_INFO"]?.toInt()?:MAX_COUNT_OF_CASHDATA_OF_OBJECT_INFO

        MAX_COUNT_OF_CASHDATA_OF_TEXT_LISTS = META_DATA["MAX_COUNT_OF_CASHDATA_OF_TEXT_LISTS"]?.toInt()?:MAX_COUNT_OF_CASHDATA_OF_TEXT_LISTS

        MAX_COUNT_OF_CASHDATA_BLOCKS_OF_CHATS = META_DATA["MAX_COUNT_OF_CASHDATA_BLOCKS_OF_CHATS"]?.toInt()?:MAX_COUNT_OF_CASHDATA_BLOCKS_OF_CHATS

        MAX_COUNT_OF_CASHDATA_SAVE_OBJECT_INFO = META_DATA["MAX_COUNT_OF_CASHDATA_SAVE_OBJECT_INFO"]?.toInt()?:MAX_COUNT_OF_CASHDATA_SAVE_OBJECT_INFO

        RE_SEND_REQUEST_PROFILE_BY_TIMEOUT = META_DATA["RE_SEND_REQUEST_PROFILE_BY_TIMEOUT"]?.toInt()?:RE_SEND_REQUEST_PROFILE_BY_TIMEOUT


    }

}