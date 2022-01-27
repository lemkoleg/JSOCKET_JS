package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@Suppress("unused")
@JsName("KMetaData")
class KMetaData:ANSWER_TYPE {


    constructor(): super()

    constructor(ans : ANSWER_TYPE): super(ans)

    @JsName("getVALUE_NAME")
    fun getVALUE_NAME():String {
        return STRING_1?:""
    }

    @JsName("setVALUE_NAME")
    fun setVALUE_NAME(v: String) {
        STRING_1 = v.trim()
    }

    @JsName("getVALUE_VALUE")
    fun getVALUE_VALUE(): Long {
        return LONG_1?:0L
    }

    @JsName("setVALUE_VALUE")
    fun setVALUE_VALUE(v: Long?) {
        LONG_1 = v?:0L
    }

    @JsName("getLATS_UPDATE")
    fun getLATS_UPDATE(): Long {
        return LONG_2?:0L
    }

    @JsName("setLATS_UPDATE")
    fun setLATS_UPDATE(v: Long?) {
        LONG_2 = v?:0L
    }

}