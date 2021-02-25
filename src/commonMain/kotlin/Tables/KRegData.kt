@file:Suppress("unused")

package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@JsName("RegData")
class KRegData  {

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

    @JsName("getCONNECTION_ID")
    fun getCONNECTION_ID():Long{
        return answerType.LONG_1?:0L
    }

    @JsName("getCONNECTION_COOCKI")
    fun getCONNECTION_COOCKI():Long{
        return answerType.LONG_2?:0L
    }

    @JsName("getMY_DATABASE_ID")
    fun getMY_DATABASE_ID():String{
        return answerType.IDENTIFICATOR_1?:""
    }

    @JsName("getLANG")
    fun getLANG():String{
        return answerType.STRING_1?:""
    }

    @JsName("getREQUEST_PROFILE")
    fun getREQUEST_PROFILE():String{
        return answerType.STRING_2?:""
    }

    @JsName("getACCOUNT_PROFILE")
    fun getACCOUNT_PROFILE():String{
        return answerType.STRING_3?:""
    }

    @JsName("setCONNECTION_ID")
    fun setCONNECTION_ID( v: Long){
        answerType.LONG_1 = v
    }

    @JsName("setCONNECTION_COOCKI")
    fun setCONNECTION_COOCKI( v: Long){
        answerType.LONG_2 = v
    }

    @JsName("setMY_DATABASE_ID")
    fun setMY_DATABASE_ID(v: String){
        answerType.IDENTIFICATOR_1 = v.trim()
    }

    @JsName("setLANG")
    fun setLANG(v: String){
        answerType.STRING_1 = v.trim()
    }

    @JsName("setREQUEST_PROFILE")
    fun setREQUEST_PROFILE(v: String){
        answerType.STRING_2 = v.trim()
    }

    @JsName("setACCOUNT_PROFILE")
    fun setACCOUNT_PROFILE(v: String){
        answerType.STRING_3 = v.trim()
    }
}