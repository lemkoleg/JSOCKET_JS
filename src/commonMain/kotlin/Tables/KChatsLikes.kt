package Tables


import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@InternalAPI
private val KChatsLikesLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val NEW_CHATS_LIKES: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KChatsLikes")
class KChatsLikes {

    init {
        ensureNeverFrozen()
    }


    constructor(ans: ANSWER_TYPE)

    @JsName("getCHATS_ID")
    fun getCHATS_ID(): String {
        return IDENTIFICATOR_5 ?: ""
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v: String) {
        IDENTIFICATOR_5 = v
    }

    @JsName("getRELATIONS")
    fun getRELATIONS(): String {
        return STRING_5 ?: ""
    }

    @JsName("setRELATIONS")
    fun setRELATIONS(v: String) {
        STRING_5 = v
    }

    @JsName("getCHATS_LIKES_PROFILE")
    fun getCHATS_LIKES_PROFILE(): String {
        return STRING_7 ?: ""
    }

    @JsName("setCHATS_LIKES_PROFILE")
    fun setCHATS_LIKES_PROFILE(v: String) {
        STRING_7 = v
    }

    @JsName("getADDING_DATE")
    fun getADDING_DATE(): Long {
        return LONG_4 ?: 0L
    }

    @JsName("setADDING_DATE")
    fun setADDING_DATE(v: Long) {
        LONG_4 = v
    }

    @JsName("getIS_ONLINE")
    fun getIS_ONLINE(): String {
        return STRING_6 ?: ""
    }

    @JsName("setIS_ONLINE")
    fun setIS_ONLINE(v: String) {
        STRING_6 = v
    }

    @JsName("getFIRST_MESS_COUNT")
    fun getFIRST_MESS_COUNT(): Long {
        return LONG_8 ?: 0L
    }

    @JsName("setFIRST_MESS_COUNT")
    fun setFIRST_MESS_COUNT(v: Long) {
        LONG_8 = v
    }

    @JsName("getLAST_MESS_COUNT")
    fun getLAST_MESS_COUNT(): Long {
        return LONG_9 ?: 0L
    }

    @JsName("setLAST_MESS_COUNT")
    fun setLAST_MESS_COUNT(v: Long) {
        LONG_9 = v
    }

    @JsName("getLAST_DATE_DELIVERED")
    fun getLAST_DATE_DELIVERED(): Long {
        return LONG_10 ?: 0L
    }

    @JsName("setLAST_DATE_DELIVERED")
    fun setLAST_DATE_DELIVERED(v: Long) {
        LONG_10 = v
    }


    @JsName("getLAST_READED_MESS_ID")
    fun getLAST_READED_MESS_ID(): Long {
        return LONG_11 ?: 0L
    }

    @JsName("setLAST_READED_MESS_ID")
    fun setLAST_READED_MESS_ID(v: Long) {
        LONG_11 = v
    }

    @JsName("getBALANCE")
    fun getBALANCE(): Long {
        return LONG_7 ?: 0L
    }

    @JsName("setBALANCE")
    fun setBALANCE(v: Long) {
        LONG_7 = v
    }

    @JsName("getLAST_CONNECT")
    fun getLAST_CONNECT(): Long {
        return LONG_6 ?: 0L
    }

    @JsName("setLAST_CONNECT")
    fun setLAST_CONNECT(v: Long) {
        LONG_6 = v
    }

    @JsName("getDATE_DELETE")
    fun getDATE_DELETE(): Long {
        return LONG_5 ?: 0L
    }

    @JsName("setDATE_DELETE")
    fun setDATE_DELETE(v: Long) {
        LONG_5 = v
    }


    fun merge(v: KChatsLikes?) {
        if (v == null) {
            return
        }
        super.merge(v)
    }


    companion object {

        @KorioExperimentalApi
        @JsName("ADD_NEW_CHATS_LIKES")
        fun ADD_NEW_CHATS_LIKES(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeout(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KChatsLikesLock.lock()
                            while (NEW_CHATS_LIKES.isNotEmpty()) {
                                TODO()
                            }
                            return@withTimeout true
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KChatsLikes",
                                l_function_name = "ADD_NEW_CHATS_LIKES",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KChatsLikesLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeout false
                }
        }.toPromise(EmptyCoroutineContext)
    }


}