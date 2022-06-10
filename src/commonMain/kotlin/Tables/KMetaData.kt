package Tables

import CrossPlatforms.MyCondition
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


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val META_DATA: MutableMap<String, Long> = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val META_DATA_condition = MyCondition()


@InternalAPI
private val KMetaDataLock = Mutex()


private var last_update: Long = 0L

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val NEW_META_DATA: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

@Suppress("unused")
@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KMetaData")
class KMetaData {

    init {
        ensureNeverFrozen()
    }

    private var VALUE_NAME: String = ""
    private var VALUE_VALUE: Long = 0L
    private var LAST_UPDATE: Long = 0L

    private constructor()

    constructor(L_VALUE_NAME: String, L_VALUE_VALUE: Long, L_LAST_UPDATE: Long) {
        VALUE_NAME = L_VALUE_NAME
        VALUE_VALUE = L_VALUE_VALUE
        LAST_UPDATE = L_LAST_UPDATE
    }

    constructor(ans: ANSWER_TYPE) : this(ans.STRING_1!!, ans.LONG_1!!, ans.LONG_2!!)

    @JsName("getVALUE_NAME")
    fun getVALUE_NAME(): String {
        return VALUE_NAME
    }

    @JsName("setVALUE_NAME")
    fun setVALUE_NAME(v: String) {
        VALUE_NAME = v.trim()
    }

    @JsName("getVALUE_VALUE")
    fun getVALUE_VALUE(): Long {
        return VALUE_VALUE
    }

    @JsName("setVALUE_VALUE")
    fun setVALUE_VALUE(v: Long?) {
        VALUE_VALUE = v ?: 0L
    }

    @JsName("getLATS_UPDATE")
    fun getLATS_UPDATE(): Long {
        return LAST_UPDATE
    }

    @JsName("setLATS_UPDATE")
    fun setLATS_UPDATE(v: Long?) {
        LAST_UPDATE = v ?: 0L
    }

    @JsName("UPDATE_METADATA")
    fun UPDATE_METADATA(L_VALUE_NAME: String, L_VALUE_VALUE: Long, L_LAST_UPDATE: Long) {
        VALUE_NAME = L_VALUE_NAME
        VALUE_VALUE = L_VALUE_VALUE
        LAST_UPDATE = L_LAST_UPDATE
    }

    @JsName("UPDATE_METADATAS")
    fun UPDATE_METADATAS(ans: ANSWER_TYPE) {
        UPDATE_METADATA(ans.STRING_1!!, ans.LONG_1!!, ans.LONG_2!!)
    }

    companion object {

        @InternalAPI
        @JsName("LOAD_META_DATA")
        fun LOAD_META_DATA(kMetaDatas: ArrayList<KMetaData>) {
            CoroutineScope(NonCancellable).launch {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KMetaDataLock.lock()
                            kMetaDatas.forEach {
                                META_DATA[it.getVALUE_NAME()] = it.getVALUE_VALUE()
                                if (last_update < it.getLATS_UPDATE()) {
                                    last_update = it.getLATS_UPDATE()
                                }
                            }
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KMetaData",
                                l_function_name = "LOAD_META_DATA",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KMetaDataLock.unlock()
                            META_DATA_condition.cSignal()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                }
            }
        }


        @KorioExperimentalApi
        @JsName("ADD_NEW_META_DATA")
        fun ADD_NEW_META_DATA(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
            withTimeout(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KMetaDataLock.lock()
                        while (NEW_META_DATA.isNotEmpty()) {
                            TODO()
                        }
                        return@withTimeout true
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCashData",
                            l_function_name = "ADD_NEW_META_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KMetaDataLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeout false
            }
        }.toPromise(EmptyCoroutineContext)
    }

}