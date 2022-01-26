package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@Suppress("unused")
@JsName("KMetaData")
class KMetaData {

    private val answerType:ANSWER_TYPE

    constructor(){
        answerType = ANSWER_TYPE()
    }

    constructor(ans : ANSWER_TYPE){
        answerType = ans
    }

    @JsName("getANSWER_TYPE")
    fun getANSWER_TYPE():ANSWER_TYPE{
        return answerType
    }

    @JsName("getVALUE_NAME")
    fun getVALUE_NAME():String {
        return answerType.STRING_1?:""
    }

    @JsName("setVALUE_NAME")
    fun setVALUE_NAME(v: String) {
        answerType.STRING_1 = v.trim()
    }

    @JsName("getVALUE_VALUE")
    fun getVALUE_VALUE(): Long {
        return answerType.LONG_1?:0L
    }

    @JsName("setVALUE_VALUE")
    fun setVALUE_VALUE(v: Long?) {
        answerType.LONG_1 = v?:0L
    }

    @JsName("getLATS_UPDATE")
    fun getLATS_UPDATE(): Long {
        return answerType.LONG_2?:0L
    }

    @JsName("setLATS_UPDATE")
    fun setLATS_UPDATE(v: Long?) {
        answerType.LONG_2 = v?:0L
    }

}