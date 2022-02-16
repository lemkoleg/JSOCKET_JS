package p_jsocket

import CrossPlatforms.getMyOS
import atomic.AtomicBoolean
import atomic.AtomicLong
import atomic.AtomicString
import com.soywiz.klock.TimeSpan
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlin.js.JsName


@InternalAPI
private val lock = Lock()

@JsName("SERVER_DNS_NAME")
val SERVER_DNS_NAME: String = "MINI"

@JsName("dbLocalName")
const val dbLocalName = "avaClub"

@JsName("myDeviceId")
var myDeviceId = ""

@JsName("myOS")
val myOS = getMyOS()

@JsName("myDataBaseID")
val myDataBaseID = AtomicString("")

@JsName("myConnectionsID")
val myConnectionsID = AtomicLong(0L)

@JsName("myConnectionsCoocki")
val myConnectionsCoocki = AtomicLong(0L)

@JsName("myConnectionContext")
val myConnectionContext = AtomicString(myOS)

@JsName("myLang")
val myLang = AtomicString("ENG")

@JsName("myRequestProfile")
val myRequestProfile = AtomicString("")

@JsName("myAccountProfile")
val myAccountProfile = AtomicString("")

@JsName("isPRO")
val isPRO = AtomicBoolean(false)

@JsName("mailConfirm")
val mailConfirm = AtomicBoolean(false)

@JsName("CLIENT_TIMEOUT")
var CLIENT_TIMEOUT = 5000L

@JsName("TIME_SPAN_FOR_LOOP")
var TIME_SPAN_FOR_LOOP = TimeSpan(10.0)

@JsName("STANDART_QUEUE_SIZE")
var STANDART_QUEUE_SIZE = 1000

@JsName("TIME_OUT_VERIFY_MESSEGES")
var TIME_OUT_VERIFY_MESSEGES = 20000

@JsName("MAX_COUNT_OF_REQUEST_ON_CLIENT_REQUESTS_QUEUE")
var MAX_COUNT_OF_REQUEST_ON_CLIENT_REQUESTS_QUEUE = 200

@JsName("MAX_REQUEST_SIZE_B")
var MAX_REQUEST_SIZE_B = 51205000

@JsName("CLIENT_ANSWER_TYPE_POOL_SIZE")
var CLIENT_ANSWER_TYPE_POOL_SIZE = 100000

@JsName("MAX_SMALL_AVATAR_SIZE_B")
var MAX_SMALL_AVATAR_SIZE_B = 6400

@JsName("MAX_CUT_BIG_AVATAR_SIZE_B")
var MAX_CUT_BIG_AVATAR_SIZE_B = 12800

@JsName("MAX_BIG_AVATAR_SIZE_B")
var MAX_BIG_AVATAR_SIZE_B = 19200

@JsName("TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES")
var TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES = 120000L

@JsName("MAX_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT")
var MAX_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT = 1000L

@JsName("MIN_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT")
var MIN_TIME_FOR_WAIT_CLIENT_LISTENER_OUTPUT = 50L

@JsName("MAX_TIME_FOR_WAIT_CLIENT_LISTENER_INPUT")
var MAX_TIME_FOR_WAIT_CLIENT_LISTENER_INPUT = 100L

@JsName("CLIENT_JSOCKET_POOL_SIZE")
var CLIENT_JSOCKET_POOL_SIZE = 10000

@JsName("LISTENER_QUEUE_SIZE")
var LISTENER_QUEUE_SIZE = 10000


class Constans {

    companion object {
       fun initialise(){
           CoroutineScope(NonCancellable).launch {

           }
       }
    }
}