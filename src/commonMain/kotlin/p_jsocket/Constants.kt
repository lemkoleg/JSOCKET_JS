package p_jsocket

import CrossPlatforms.getMyOS
import atomic.AtomicBoolean
import com.soywiz.klock.TimeSpan
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
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
    const val dbLocalName = "AUF"

    @JsName("CLIENT_TIMEOUT")
    var CLIENT_TIMEOUT = 5000L

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
    var TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES = 20000L

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
    var SEND_AVATAR_SIZE = 1310400

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
    var TIME_OUT_OF_ACTUAL_DATA_FOR_SELECTOR: Long = 10000L




    fun initialise() {
        CoroutineScope(NonCancellable).launch {

        }
    }

}