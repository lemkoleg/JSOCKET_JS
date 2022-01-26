package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@JsName("KExceptions")
class KExceptions {

    private val answerType: ANSWER_TYPE

    constructor(){
        answerType = ANSWER_TYPE()
    }

    constructor(ans : ANSWER_TYPE){
        answerType = ans
    }

    @JsName("getANSWER_TYPE")
    fun getANSWER_TYPE(): ANSWER_TYPE {
        return answerType
    }

    @JsName("getNUMBER_OF_ECXEPTION")
    fun getNUMBER_OF_ECXEPTION(): Long {
        return answerType.LONG_1?:0L
    }

    @JsName("setNUMBER_OF_ECXEPTION")
    fun setNUMBER_OF_ECXEPTION(v: Long?) {
        answerType.LONG_1 = v?:0L
    }

    @JsName("getLANG_OF_ECXEPTION")
    fun getLANG_OF_ECXEPTION():String {
        return answerType.STRING_2?:""
    }

    @JsName("setLANG_OF_ECXEPTION")
    fun setLANG_OF_ECXEPTION(v: String) {
        answerType.STRING_2 = v.trim()
    }

    @JsName("getTEXT_OF_ECXEPTION")
    fun getTEXT_OF_ECXEPTION():String {
        return answerType.STRING_1?:""
    }

    @JsName("setTEXT_OF_ECXEPTION")
    fun setTEXT_OF_ECXEPTION(v: String) {
        answerType.STRING_1 = v.trim()
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