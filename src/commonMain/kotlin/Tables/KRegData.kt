@file:Suppress("unused")

package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@JsName("KRegData")
class KRegData:ANSWER_TYPE {
    

    constructor(): super()

    constructor(ans : ANSWER_TYPE): super(ans)

    @JsName("getCONNECTION_COOCKI")
    fun getCONNECTION_COOCKI():Long{
        return LONG_5?:0L
    }

    @JsName("setCONNECTION_COOCKI")
    fun setCONNECTION_COOCKI(v:Long){
        LONG_5 = v
    }

    @JsName("getMY_DATABASE_ID")
    fun getMY_DATABASE_ID():String{
        return STRING_6?:""
    }

    @JsName("setMY_DATABASE_ID")
    fun setMY_DATABASE_ID(v: String){
        STRING_6 = v.trim()
    }

    @JsName("getACCOUNT_ID")
    fun getACCOUNT_ID():String{
        return IDENTIFICATOR_1?:""
    }

    @JsName("setACCOUNT_ID")
    fun setACCOUNT_ID(v: String?){
        IDENTIFICATOR_1 = v?.trim()?:""
    }

    @JsName("getACCOUNT_NAME")
    fun getACCOUNT_NAME():String{
        return STRING_1?:""
    }

    @JsName("setACCOUNT_NAME")
    fun setACCOUNT_NAME(v: String?){
        STRING_1 = v?.trim()?:""
    }

    @JsName("getACCOUNT_PROFILE")
    fun getACCOUNT_PROFILE():String{
        return STRING_5?:""
    }

    @JsName("setACCOUNT_PROFILE")
    fun setACCOUNT_PROFILE(v: String){
        STRING_5 = v.trim()
    }

    @JsName("getREQUEST_PROFILE")
    fun getREQUEST_PROFILE():String{
        return STRING_6?:""
    }

    @JsName("setREQUEST_PROFILE")
    fun setREQUEST_PROFILE(v: String){
        STRING_6 = v.trim()
    }

    @JsName("getLANG")
    fun getLANG():String{
        return STRING_7?:""
    }

    @JsName("setLANG")
    fun setLANG(v: String){
        STRING_7 = v.trim()
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID():String{
        return IDENTIFICATOR_2?:""
    }

    @JsName("setAVATAR_ID")
    fun setAVATARID(v: String?){
        IDENTIFICATOR_2 = v?.trim()?:""
    }

    @JsName("getBALANCE_OF_CHATS")
    fun getBALANCE_OF_CHATS():Long{
        return LONG_3?:0L
    }

    @JsName("setBALANCE_OF_CHATS")
    fun setBALANCE_OF_CHATS(v:Long?){
        LONG_3 = v?:0L
    }

}