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
        return answerType.LONG_4?:0L
    }

    @JsName("setCONNECTION_ID")
    fun setCONNECTION_ID(v:Long){
        answerType.LONG_4 = v
    }

    @JsName("getCONNECTION_COOCKI")
    fun getCONNECTION_COOCKI():Long{
        return answerType.LONG_5?:0L
    }

    @JsName("setCONNECTION_COOCKI")
    fun setCONNECTION_COOCKI(v:Long){
        answerType.LONG_5 = v
    }

    @JsName("getMY_DATABASE_ID")
    fun getMY_DATABASE_ID():String{
        return answerType.STRING_6?:""
    }

    @JsName("setMY_DATABASE_ID")
    fun setMY_DATABASE_ID(v: String){
        answerType.STRING_6 = v.trim()
    }

    @JsName("getACCOUNT_ID")
    fun getACCOUNT_ID():String{
        return answerType.IDENTIFICATOR_1?:""
    }

    @JsName("setACCOUNT_ID")
    fun setACCOUNT_ID(v: String?){
        answerType.IDENTIFICATOR_1 = v?.trim()?:""
    }

    @JsName("getACCOUNT_NAME")
    fun getACCOUNT_NAME():String{
        return answerType.STRING_1?:""
    }

    @JsName("setACCOUNT_NAME")
    fun setACCOUNT_NAME(v: String?){
        answerType.STRING_1 = v?.trim()?:""
    }

    @JsName("getACCOUNT_PROFILE")
    fun getACCOUNT_PROFILE():String{
        return answerType.STRING_3?:""
    }

    @JsName("setACCOUNT_PROFILE")
    fun setACCOUNT_PROFILE(v: String){
        answerType.STRING_3 = v.trim()
    }

    @JsName("getREQUEST_PROFILE")
    fun getREQUEST_PROFILE():String{
        return answerType.STRING_4?:""
    }

    @JsName("setREQUEST_PROFILE")
    fun setREQUEST_PROFILE(v: String){
        answerType.STRING_4 = v.trim()
    }

    @JsName("getLANG")
    fun getLANG():String{
        return answerType.STRING_5?:""
    }

    @JsName("setLANG")
    fun setLANG(v: String){
        answerType.STRING_5 = v.trim()
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID():String{
        return answerType.IDENTIFICATOR_2?:""
    }

    @JsName("setAVATAR_ID")
    fun setAVATARID(v: String?){
        answerType.IDENTIFICATOR_2 = v?.trim()?:""
    }

    @JsName("getAVATAR_LINK")
    fun getAVATAR_LINK():String{
        return answerType.STRING_18?:""
    }

    @JsName("setAVATAR_LINK")
    fun setAVATAR_LINK(v:String?){
        answerType.STRING_18 = v?:""
    }

    @JsName("getAVATAR_SERVER")
    fun getAVATAR_SERVER():String{
        return answerType.STRING_16?:""
    }

    @JsName("setAVATAR_SERVER")
    fun setAVATAR_SERVER(v:String?){
        answerType.STRING_16 = v?:""
    }

    @JsName("getORIGINAL_AVATAR_SIZE")
    fun getORIGINAL_AVATAR_SIZE():String{
        return answerType.STRING_17?:"0"
    }

    @JsName("setORIGINAL_AVATAR_SIZE")
    fun setORIGINAL_AVATAR_SIZE(v: String?){
        answerType.STRING_17 = v?.trim()?:"0"
    }

    @JsName("getBALANCE_OF_CHATS")
    fun getBALANCE_OF_CHATS():Long{
        return answerType.LONG_3?:0L
    }

    @JsName("setBALANCE_OF_CHATS")
    fun setBALANCE_OF_CHATS(v:Long?){
        answerType.LONG_3 = v?:0L
    }

}