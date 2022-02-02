/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

/**
 *
 * @author User
 */
@JsName("KMessege")
class KMessege:ANSWER_TYPE {


    constructor(): super()

    constructor(ans : ANSWER_TYPE): super(ans)

    @JsName("getCHATS_ID")
    fun getCHATS_ID():String{
        return IDENTIFICATOR_11?:""
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v:String){
        IDENTIFICATOR_11 = v
    }

    @JsName("getMESSEGES_COUNT")
    fun getMESSEGES_COUNT():Long{
        return LONG_12?:0L
    }

    @JsName("setMESSEGES_COUNT")
    fun setMESSEGES_COUNT(v:Long){
        LONG_12 = v
    }

    @JsName("getADDING_DATE")
    fun getADDING_DATE():Long{
        return LONG_11?:0L
    }

    @JsName("setADDING_DATE")
    fun setADDING_DATE(v:Long){
        LONG_11 = v
    }

    @JsName("getPERIOD_FOR")
    fun getPERIOD_FOR():Long{
        return LONG_13?:0L
    }

    @JsName("setPERIOD_FOR")
    fun setPERIOD_FOR(v:Long){
        LONG_13 = v
    }

    @JsName("getMESSEGES_OWNER")
    fun getMESSEGES_OWNER():String{
        return IDENTIFICATOR_12?:""
    }

    @JsName("setMESSEGES_OWNER")
    fun setMESSEGES_OWNER(v:String){
        IDENTIFICATOR_12 = v
    }

    @JsName("getMESSEGES_ANSWER")
    fun getMESSEGES_ANSWER():Long{
        return LONG_14?:0L
    }

    @JsName("setMESSEGES_ANSWER")
    fun setMESSEGES_ANSWER(v:Long?){
        LONG_14 = v?:0L
    }

    @JsName("getMESSEGE_ANSWER_START_TEXT")
    fun getMESSEGE_ANSWER_START_TEXT():String{
        return STRING_13?:""
    }

    @JsName("setMESSEGE_ANSWER_START_TEXT")
    fun setMESSEGE_ANSWER_START_TEXT(v:String?){
        STRING_13 = v?:""
    }

    @JsName("getMESSEGES_ADRESSER")
    fun getMESSEGES_ADRESSER():String{
        return IDENTIFICATOR_13?:""
    }

    @JsName("setMESSEGES_ADRESSER")
    fun setMESSEGES_ADRESSER(v:String?){
        IDENTIFICATOR_13 = v?:""
    }

    @JsName("getOBJECT_TYPE")
    fun getOBJECT_TYPE():String{
        if(STRING_12 == null || STRING_12!!.length < 6){
            return ""
        }
        return STRING_12!!.substring(5, 6)
    }

    @JsName("getMESSEGE_START_TEXT")
    fun getMESSEGE_START_TEXT():String{
        return STRING_11?:""
    }

    @JsName("setMESSEGE_START_TEXT")
    fun setMESSEGE_START_TEXT(v:String?){
        STRING_11 = v?:""
    }

    @JsName("getCOST")
    fun getCOST():Int{
        return INTEGER_11?:0
    }

    @JsName("setCOST")
    fun setCOST(v:Int?){
        INTEGER_11 = v?:0
    }

    @JsName("getCOST_TYPE")
    fun getCOST_TYPE():Int{
        return INTEGER_12?:0
    }

    @JsName("setCOST_TYPE")
    fun setCOST_TYPE(v:Int?){
        INTEGER_12 = v?:0
    }

    @JsName("getMESSEGES_TYPE")
    fun getMESSEGES_TYPE():String?{
        if(STRING_12 == null || STRING_12!!.isEmpty()){
            return null
        }
        return STRING_12!!.substring(0, 1)
    }

    @JsName("getMESSEGES_ACCESS")
    fun getMESSEGES_ACCESS():String?{
        if(STRING_12 == null || STRING_12!!.length < 2){
            return null
        }
        return STRING_12!!.substring(1, 2)
    }

    @JsName("getMESSEGES_STATUS")
    fun getMESSEGES_STATUS():String?{
        if(STRING_12 == null || STRING_12!!.length < 3){
            return null
        }
        return STRING_12!!.substring(2, 3)
    }

    @JsName("getHAVE_A_FULL_TEXT")
    fun getHAVE_A_FULL_TEXT():String?{
        if(STRING_12 == null || STRING_12!!.length < 4){
            return null
        }
        return STRING_12!!.substring(3, 4)
    }

    @JsName("getHAVE_ANSWER")
    fun getHAVE_ANSWER():String?{
        if(STRING_12 == null || STRING_12!!.length < 5){
            return null
        }
        return STRING_12!!.substring(4, 5)
    }

    @JsName("setPROFILE_LINE")
    fun setPROFILE_LINE(l_messeges_type: String,
                        l_messeges_access: String,
                        l_messeges_status: String,
                        l_have_full_text: String,
                        l_have_answer: String,
                        l_object_type: String){
        STRING_12 = l_messeges_type + l_messeges_access + l_messeges_status + l_have_full_text + l_have_answer + l_object_type
    }

    @JsName("getLAST_CHANGED")
    fun getLAST_CHANGED():Long{
        return LONG_15?:0L
    }

    @JsName("setLAST_CHANGED")
    fun setLAST_CHANGED(v:Long?){
        LONG_15 = v?:0L
    }

    @JsName("getMESSEGE_FULL_TEXT")
    fun getMESSEGE_FULL_TEXT():String{
        return super.getSUBSCRIBE()
    }

    @JsName("setMESSEGE_FULL_TEXT")
    fun setMESSEGE_FULL_TEXT(v:String?){
        super.setSUBSCRIBE(v)
    }

    @JsName("setMESSEGES_TYPE_ACCESS_STATUS_HAVEFULLTEXT_HAVEANSWER_OBJECTTYPE")
    fun setMESSEGES_TYPE_ACCESS_STATUS_HAVEFULLTEXT_HAVEANSWER_OBJECTTYPE(v:String){
        STRING_12 = v
    }


    @JsName("getANSWER_OBJECT_AVATAR")
    fun getANSWER_OBJECT_AVATAR():ByteArray?{
        return BLOB_1
    }

    @JsName("setANSWER_OBJECT_AVATAR")
    fun setANSWER_OBJECT_AVATAR(v:ByteArray?){
        BLOB_1 = v
    }

    @JsName("getOBJECT_AVATAR")
    fun getOBJECT_AVATAR():ByteArray?{
        return BLOB_2
    }

    @JsName("setOBJECT_AVATAR")
    fun setOBJECT_AVATAR(v:ByteArray?){
        BLOB_2 = v
    }

    @JsName("getMESSEGES_AVATAR")
    fun getMESSEGES_AVATAR():ByteArray?{
        return BLOB_3
    }

    @JsName("setAVATAR")
    fun setMESSEGES_AVATAR(v:ByteArray?){
        this.BLOB_3 = v
    }

    fun merge(v :KMessege? ){
        if(v == null ){
            return
        }
        super.merge(v)

        if(v.LONG_4 != null && v.LONG_4 != 0L){
            this.LONG_4 = v.LONG_4
        }

        if(v.LONG_5 != null && v.LONG_5 != 0L){
            this.LONG_5 = v.LONG_5
        }

        if(v.LONG_6 != null && v.LONG_6 != 0L){
            this.LONG_6 = v.LONG_6
        }

        if(v.LONG_7 != null && v.LONG_7 != 0L){
            this.LONG_7 = v.LONG_7
        }

        if(v.LONG_8 != null && v.LONG_8 != 0L){
            this.LONG_8 = v.LONG_8
        }

        if(v.LONG_9 != null && v.LONG_9 != 0L){
            this.LONG_9 = v.LONG_9
        }

        if(v.LONG_10 != null && v.LONG_10 != 0L){
            this.LONG_10 = v.LONG_10
        }

        if(v.STRING_13 != null && v.STRING_13!!.isNotEmpty()){
            this.STRING_13 = v.STRING_13   //answer start text
        }

    }
}