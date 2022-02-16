package Tables

import CrossPlatforms.MyCondition
import io.ktor.util.*
import io.ktor.util.collections.*
import kotlinx.coroutines.withTimeoutOrNull
import p_jsocket.ANSWER_TYPE
import p_jsocket.CLIENT_TIMEOUT
import kotlin.js.JsName


@InternalAPI
val META_DATA: ConcurrentMap<String, Long> = ConcurrentMap()

val META_DATA_condition: MyCondition = MyCondition()


@InternalAPI
private val lock = Lock()

private var last_update: Long = 0L

@Suppress("unused")
@JsName("KMetaData")
class KMetaData {

    private var VALUE_NAME: String = ""
    private var VALUE_VALUE: Long = 0L
    private var LAST_UPDATE: Long = 0L

    private constructor()

    constructor(L_VALUE_NAME: String, L_VALUE_VALUE: Long, L_LAST_UPDATE: Long){
        VALUE_NAME = L_VALUE_NAME
        VALUE_VALUE = L_VALUE_VALUE
        LAST_UPDATE = L_LAST_UPDATE
    }

    constructor(ans: ANSWER_TYPE):this(ans.STRING_1!!, ans.LONG_1!!, ans.LONG_2!!)

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
    fun UPDATE_METADATA(L_VALUE_NAME: String, L_VALUE_VALUE: Long, L_LAST_UPDATE: Long){
        VALUE_NAME = L_VALUE_NAME
        VALUE_VALUE = L_VALUE_VALUE
        LAST_UPDATE = L_LAST_UPDATE
    }

    @JsName("UPDATE_METADATA")
    fun UPDATE_METADATA(ans: ANSWER_TYPE){
        UPDATE_METADATA(ans.STRING_1!!, ans.LONG_1!!, ans.LONG_2!!)
    }

    companion object {

        @InternalAPI
        @JsName("setMETA_DATA")
        suspend fun setIN_COLLECTION(kMetaData: KMetaData) {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lock.lock()
                    META_DATA[kMetaData.getVALUE_NAME()] = kMetaData.getVALUE_VALUE()
                    if (last_update < kMetaData.getLATS_UPDATE()) {
                        last_update = kMetaData.getLATS_UPDATE()
                    }

                }
            } finally {
                lock.unlock()
                META_DATA_condition.cSignal()
            }
        }

        @OptIn(InternalAPI::class)
        @JsName("setMETA_DATA")
        suspend fun setMETA_DATA(n: String, v: Long, d: Long) {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lock.lock()
                    META_DATA[n] = v
                    if (last_update < d) {
                        last_update = d
                    }
                }
            } finally {
                lock.unlock()
                META_DATA_condition.cSignal()
            }
        }
    }

}