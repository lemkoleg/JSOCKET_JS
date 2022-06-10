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
private val KChatsCostTypesLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val NEW_CHATS_COST_TYPES: ArrayDeque<ANSWER_TYPE> = ArrayDeque()


@ExperimentalTime
@InternalAPI
@JsName("KChatsCostTypes")
class KChatsCostTypes{


    init {
        ensureNeverFrozen()
    }

    //val InstanceRef: AtomicReference<KChatsCostTypes> = AtomicReference(this)


    @JsName("getCHATS_ID")
    fun getCHATS_ID(): String {
        return IDENTIFICATOR_5 ?: ""
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v: String) {
        IDENTIFICATOR_5 = v
    }

    @JsName("getCOST_ID")
    fun getCOST_ID(): String {
        return STRING_6 ?: ""
    }

    @JsName("setCOST_ID")
    fun setCOST_ID(v: String) {
        STRING_6 = v
    }

    @JsName("getTYPES_OWNER")
    fun getTYPES_OWNER(): String {
        return IDENTIFICATOR_7 ?: ""
    }

    @JsName("setTYPES_OWNER")
    fun setTYPES_OWNER(v: String) {
        IDENTIFICATOR_7 = v
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID(): String {
        return IDENTIFICATOR_6 ?: ""
    }

    @JsName("setAVATAR_ID")
    fun setAVATAR_ID(v: String?) {
        IDENTIFICATOR_6 = v ?: ""
    }

    @JsName("getSTART_TEXT")
    fun getSTART_TEXT(): String {
        return STRING_5 ?: ""
    }

    @JsName("setSTART_TEXT")
    fun setSTART_TEXT(v: String) {
        STRING_5 = v
    }

    @JsName("getHAVE_FULL_TEXT")
    fun getHAVE_FULL_TEXT(): String {
        if (STRING_7 == null || STRING_7!!.length < 5) {
            return ""
        }
        return STRING_7!!.substring(4, 5)
    }

    @JsName("getTYPES_TYPE")
    fun getHAVE_TYPES_TYPE(): String {
        if (STRING_7 == null || STRING_7!!.isEmpty()) {
            return ""
        }
        return STRING_7!!.substring(0, 1)
    }

    @JsName("getTYPES_ACCESS")
    fun getTYPES_ACCESS(): String {
        if (STRING_7 == null || STRING_7!!.length < 2) {
            return ""
        }
        return STRING_7!!.substring(1, 2)
    }

    @JsName("getTYPES_STATUS")
    fun getTYPES_STATUS(): String {
        if (STRING_7 == null || STRING_7!!.length < 3) {
            return ""
        }
        return STRING_7!!.substring(2, 3)
    }

    @JsName("getHAVE_MESSEGES")
    fun getHAVE_MESSEGES(): String {
        if (STRING_7 == null || STRING_7!!.length < 4) {
            return ""
        }
        return STRING_7!!.substring(3, 4)
    }

    @JsName("setPROFILE_LINE")
    fun setPROFILE_LINE(
        l_types_type: String,
        l_types_access: String,
        l_types_status: String,
        l_have_messeges: String,
        l_have_full_text: String
    ) {
        STRING_7 = l_types_type + l_types_access + l_types_status + l_have_messeges + l_have_full_text
    }

    @JsName("getFULL_TEXT")
    fun getFULL_TEXT(): String {
        return STRING_14 ?: ""
    }

    @JsName("setFULL_TEXT")
    fun setFULL_TEXT(v: String) {
        STRING_14 = v
    }

    fun merge(v: KChatsCostTypes?) {
        if (v == null) {
            return
        }
        super.merge(v)
    }


    companion object {

        @KorioExperimentalApi
        @JsName("ADD_NEW_CHATS_COST_TYPES")
        fun ADD_NEW_CHATS_COST_TYPES(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeout(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KChatsCostTypesLock.lock()
                            while (NEW_CHATS_COST_TYPES.isNotEmpty()) {
                                TODO()
                            }
                            return@withTimeout true
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KChatsCostTypes",
                                l_function_name = "ADD_NEW_CHATS_COST_TYPES",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KChatsCostTypesLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeout false
                }
            }.toPromise(EmptyCoroutineContext)
    }
}