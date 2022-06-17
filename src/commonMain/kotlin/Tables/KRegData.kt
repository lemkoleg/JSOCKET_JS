@file:Suppress("unused")

package Tables

import atomic.AtomicBoolean
import atomic.AtomicLong
import atomic.AtomicString
import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_client.Jsocket
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myConnectionsID")
val myConnectionsID = AtomicLong(0L)

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myConnectionsCoocki")
val myConnectionsCoocki = AtomicLong(0L)

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myConnectionContext")
val myConnectionContext = AtomicString(Constants.myOS)

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myLang")
val myLang = AtomicString("ENG")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myDeviceId")
var myDeviceId = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myRequestProfile")
val myRequestProfile = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myAccountProfile")
val myAccountProfile = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("isPRO")
val isPRO = AtomicBoolean(false)

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("mailConfirm")
val mailConfirm = AtomicBoolean(false)

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
private val KRegDataLock = Mutex()


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var ACCOUNT_ID = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var ACCOUNT_NAME = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var LANG = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var AVATAR_ID = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var ORIGINAL_AVATAR_SIZE = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var AVATAR_SERVER = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var AVATAR_LINK = AtomicString("")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var BALANCE_OF_CHATS = AtomicLong(0L)

var AVATAR_1: ByteArray? = null
var AVATAR_2: ByteArray? = null
var AVATAR_3: ByteArray? = null


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val NEW_REG_DATA: ArrayDeque<ANSWER_TYPE> = ArrayDeque()


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KRegData")
class KRegData {

    companion object {

        init {
            ensureNeverFrozen()
        }

        val InstanceRef: AtomicReference<KRegData.Companion> = AtomicReference(this)


        suspend fun setCONNECTION_COOCKI(v: Long) {
            try {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    KRegDataLock.withLock {
                        if (v != 0L && Constants.CONNECTION_COOCKI != v) {
                            CONNECTION_COOCKI = v
                            CoroutineScope(NonCancellable).launch {
                                Jsocket.ClearAndfill()
                            }
                        }
                    }

                }
            } catch (ex: Exception) {
            }
        }

        @KorioExperimentalApi
        @JsName("ADD_NEW_REG_DATA")
        fun ADD_NEW_REG_DATA(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeout(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KRegDataLock.lock()
                            while (NEW_REG_DATA.isNotEmpty()) {
                                TODO()
                            }
                            return@withTimeout true
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KRegDataLock",
                                l_function_name = "ADD_NEW_REG_DATA",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KRegDataLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeout false
                }
            }.toPromise(EmptyCoroutineContext)
    }
}


@JsName("getACCOUNT_PROFILE")
fun getACCOUNT_PROFILE(): String {
    return ACCOUNT_PROFILE
}

@JsName("setACCOUNT_PROFILE")
fun setACCOUNT_PROFILE(v: String?) {
    if (v != null && v.isNotEmpty()) {
        ACCOUNT_PROFILE = v
    }
}

@JsName("getREQUEST_PROFILE")
fun getREQUEST_PROFILE(): String {
    return REQUEST_PROFILE
}

@JsName("setREQUEST_PROFILE")
fun setREQUEST_PROFILE(v: String) {
    if (v != null && v.isNotEmpty()) {
        REQUEST_PROFILE = v
    }

}


@JsName("getBALANCE_OF_CHATS")
fun getBALANCE_OF_CHATS(): Long {
    return LONG_3 ?: 0L
}

@JsName("setBALANCE_OF_CHATS")
fun setBALANCE_OF_CHATS(v: Long?) {
    LONG_3 = v ?: 0L
}

}