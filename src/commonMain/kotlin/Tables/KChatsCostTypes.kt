package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName


@JsName("KChatsCostTypes")
class KChatsCostTypes:ANSWER_TYPE {


    constructor(): super()

    constructor(ans : ANSWER_TYPE): super(ans)

    @JsName("getCHATS_ID")
    fun getCHATS_ID():String{
        return IDENTIFICATOR_5?:""
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v:String){
        IDENTIFICATOR_5 = v
    }

    @JsName("getCOST_ID")
    fun getCOST_ID():String{
        return STRING_6?:""
    }

    @JsName("setCOST_ID")
    fun setCOST_ID(v:String){
        STRING_6 = v
    }

    @JsName("getTYPES_OWNER")
    fun getTYPES_OWNER():String{
        return IDENTIFICATOR_7?:""
    }

    @JsName("setTYPES_OWNER")
    fun setTYPES_OWNER(v:String){
        IDENTIFICATOR_7 = v
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID():String{
        return IDENTIFICATOR_6?:""
    }

    @JsName("setAVATAR_ID")
    fun setAVATAR_ID(v:String?){
        IDENTIFICATOR_6 = v?:""
    }

    @JsName("getSTART_TEXT")
    fun getSTART_TEXT():String{
        return STRING_5?:""
    }

    @JsName("setSTART_TEXT")
    fun setSTART_TEXT(v:String){
        STRING_5 = v
    }

    @JsName("getHAVE_FULL_TEXT")
    fun getHAVE_FULL_TEXT():String{
        if(STRING_7 == null || STRING_7!!.length < 5){
            return ""
        }
        return STRING_7!!.substring(4, 5)
    }

    @JsName("getTYPES_TYPE")
    fun getHAVE_TYPES_TYPE():String{
        if(STRING_7 == null || STRING_7!!.isEmpty()){
            return ""
        }
        return STRING_7!!.substring(0, 1)
    }

    @JsName("getTYPES_ACCESS")
    fun getTYPES_ACCESS():String{
        if(STRING_7 == null || STRING_7!!.length < 2){
            return ""
        }
        return STRING_7!!.substring(1, 2)
    }

    @JsName("getTYPES_STATUS")
    fun getTYPES_STATUS():String{
        if(STRING_7 == null || STRING_7!!.length < 3){
            return ""
        }
        return STRING_7!!.substring(2, 3)
    }

    @JsName("getHAVE_MESSEGES")
    fun getHAVE_MESSEGES():String{
        if(STRING_7 == null || STRING_7!!.length < 4){
            return ""
        }
        return STRING_7!!.substring(3, 4)
    }

    @JsName("setPROFILE_LINE")
    fun setPROFILE_LINE(l_types_type: String,
                        l_types_access: String,
                        l_types_status: String,
                        l_have_messeges: String,
                        l_have_full_text: String){
        STRING_7 = l_types_type + l_types_access + l_types_status + l_have_messeges + l_have_full_text
    }

    @JsName("getFULL_TEXT")
    fun getFULL_TEXT():String{
        return STRING_14?:""
    }

    @JsName("setFULL_TEXT")
    fun setFULL_TEXT(v:String){
        STRING_14 = v
    }

    fun merge(v :KChatsCostTypes? ){
        if(v == null ){
            return
        }
        super.merge(v)
    }
}