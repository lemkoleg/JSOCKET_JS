package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@Suppress("unused")
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
    fun getVALUE_NAME(): String {
        return answerType.STRING_2?:""
    }

    @JsName("getVALUE_VALUE")
    fun getVALUE_VALUE(): Int {
        return answerType.INTEGER_2?:0
    }

    @JsName("setVALUE_NAME")
    fun setVALUE_NAME(v: String) {
        answerType.STRING_2 = v.trim()
    }

    @JsName("setVALUE_VALUE")
    fun setVALUE_VALUE(v: Int) {
        answerType.INTEGER_2 = v
    }
}