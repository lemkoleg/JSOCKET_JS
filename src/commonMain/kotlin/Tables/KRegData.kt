@file:Suppress("unused")

package Tables

import atomic.AtomicBoolean
import atomic.AtomicLong
import atomic.AtomicString
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import p_client.Jsocket
import p_jsocket.CLIENT_TIMEOUT
import p_jsocket.myOS
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@JsName("myConnectionsID")
val myConnectionsID = AtomicLong(0L)

@JsName("myConnectionsCoocki")
val myConnectionsCoocki = AtomicLong(0L)

@JsName("myConnectionContext")
val myConnectionContext = AtomicString(myOS)

@JsName("myLang")
val myLang = AtomicString("ENG")

@JsName("myDeviceId")
var myDeviceId = AtomicString("")

@JsName("myRequestProfile")
val myRequestProfile = AtomicString("")

@JsName("myAccountProfile")
val myAccountProfile = AtomicString("")

@JsName("isPRO")
val isPRO = AtomicBoolean(false)

@JsName("mailConfirm")
val mailConfirm = AtomicBoolean(false)

@InternalAPI
private val lock = Lock()

 var ACCOUNT_ID:String = ""
 var ACCOUNT_NAME:String = ""
 var LANG:String = ""
 var AVATAR_ID:String = ""
 var ORIGINAL_AVATAR_SIZE:String = ""
 var AVATAR_SERVER:String = ""
 var AVATAR_LINK:String = ""
 var BALANCE_OF_CHATS: Long = 0
 var AVATAR_1: ByteArray? = null
 var AVATAR_2: ByteArray? = null
 var AVATAR_3: ByteArray? = null



@JsName("KRegData")
class KRegData{

    companion object{

        @InternalAPI
        suspend fun getRegData(): Long {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lock.lock()
                    return@withTimeoutOrNull CONNECTION_COOCKI
                }
            } catch (ex: Exception) {
                return CONNECTION_COOCKI
            }
            return CONNECTION_COOCKI
        }

        @ExperimentalTime
        @InternalAPI
        suspend fun setCONNECTION_COOCKI(v: Long) {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lock.withLock {
                        if(v != 0L && CONNECTION_COOCKI != v){
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
    }


    @JsName("getACCOUNT_PROFILE")
    fun getACCOUNT_PROFILE():String{
        return ACCOUNT_PROFILE
    }

    @JsName("setACCOUNT_PROFILE")
    fun setACCOUNT_PROFILE(v: String?){
        if(v != null && v.isNotEmpty()){
            ACCOUNT_PROFILE = v
        }
    }

    @JsName("getREQUEST_PROFILE")
    fun getREQUEST_PROFILE():String{
        return REQUEST_PROFILE
    }

    @JsName("setREQUEST_PROFILE")
    fun setREQUEST_PROFILE(v: String){
        if(v != null && v.isNotEmpty()){
            REQUEST_PROFILE = v
        }

    }


    @JsName("getBALANCE_OF_CHATS")
    fun getBALANCE_OF_CHATS():Long{
        return LONG_3?:0L
    }

    @JsName("setBALANCE_OF_CHATS")
    fun setBALANCE_OF_CHATS(v:Long?){
        LONG_3 = v?:0L
    }

}