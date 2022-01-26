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
class KMessege{

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

    @JsName("getCHATS_ID")
    fun getCHATS_ID():String{
        return answerType.IDENTIFICATOR_11?:""
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v:String){
        answerType.IDENTIFICATOR_11 = v
    }

    @JsName("getMESSEGES_COUNT")
    fun getMESSEGES_COUNT():Long{
        return answerType.LONG_12?:0L
    }

    @JsName("setMESSEGES_COUNT")
    fun setMESSEGES_COUNT(v:Long){
        answerType.LONG_12 = v
    }

    @JsName("getADDING_DATE")
    fun getADDING_DATE():Long{
        return answerType.LONG_11?:0L
    }

    @JsName("setADDING_DATE")
    fun setADDING_DATE(v:Long){
        answerType.LONG_11 = v
    }

    @JsName("getPERIOD_FOR")
    fun getPERIOD_FOR():Long{
        return answerType.LONG_13?:0L
    }

    @JsName("setPERIOD_FOR")
    fun setPERIOD_FOR(v:Long){
        answerType.LONG_13 = v
    }

    @JsName("getMESSEGES_OWNER")
    fun getMESSEGES_OWNER():String{
        return answerType.IDENTIFICATOR_12?:""
    }

    @JsName("setMESSEGES_OWNER")
    fun setMESSEGES_OWNER(v:String){
        answerType.IDENTIFICATOR_12 = v
    }

    @JsName("getMESSEGES_ANSWER")
    fun getMESSEGES_ANSWER():Long{
        return answerType.LONG_14?:0L
    }

    @JsName("setMESSEGES_ANSWER")
    fun setMESSEGES_ANSWER(v:Long?){
        answerType.LONG_14 = v?:0L
    }

    @JsName("getMESSEGE_ANSWER_START_TEXT")
    fun getMESSEGE_ANSWER_START_TEXT():String{
        return answerType.STRING_13?:""
    }

    @JsName("setMESSEGE_ANSWER_START_TEXT")
    fun setMESSEGE_ANSWER_START_TEXT(v:String?){
        answerType.STRING_13 = v?:""
    }

    @JsName("getMESSEGES_ADRESSER")
    fun getMESSEGES_ADRESSER():String{
        return answerType.IDENTIFICATOR_13?:""
    }

    @JsName("setMESSEGES_ADRESSER")
    fun setMESSEGES_ADRESSER(v:String?){
        answerType.IDENTIFICATOR_13 = v?:""
    }

    @JsName("getOBJECT_ID")
    fun getOBJECTS_ID():String{
        return answerType.IDENTIFICATOR_5?:""
    }

    @JsName("setOBJECT_ID")
    fun setOBJECTS_ID(v:String?){
        answerType.IDENTIFICATOR_5 = v?:""
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID():String{
        return answerType.IDENTIFICATOR_6?:""
    }

    @JsName("setAVATAR_ID")
    fun setAVATAR_ID(v:String?){
        answerType.IDENTIFICATOR_6 = v?:""
    }

    @JsName("getOBJECT_TYPE")
    fun getOBJECT_TYPE():String{
        if(answerType.STRING_12 == null || answerType.STRING_12!!.length < 6){
            return ""
        }
        return answerType.STRING_12!!.substring(5, 6)
    }

    @JsName("getMESSEGE_START_TEXT")
    fun getMESSEGE_START_TEXT():String{
        return answerType.STRING_11?:""
    }

    @JsName("setMESSEGE_START_TEXT")
    fun setMESSEGE_START_TEXT(v:String?){
        answerType.STRING_11 = v?:""
    }

    @JsName("getCOST")
    fun getCOST():Int{
        return answerType.INTEGER_11?:0
    }

    @JsName("setCOST")
    fun setCOST(v:Int?){
        answerType.INTEGER_11 = v?:0
    }

    @JsName("getCOST_TYPE")
    fun getCOST_TYPE():Int{
        return answerType.INTEGER_12?:0
    }

    @JsName("setCOST_TYPE")
    fun setCOST_TYPE(v:Int?){
        answerType.INTEGER_12 = v?:0
    }

    @JsName("getMESSEGES_TYPE")
    fun getMESSEGES_TYPE():String?{
        if(answerType.STRING_12 == null || answerType.STRING_12!!.isEmpty()){
            return null
        }
        return answerType.STRING_12!!.substring(0, 1)
    }

    @JsName("getMESSEGES_ACCESS")
    fun getMESSEGES_ACCESS():String?{
        if(answerType.STRING_12 == null || answerType.STRING_12!!.length < 2){
            return null
        }
        return answerType.STRING_12!!.substring(1, 2)
    }

    @JsName("getMESSEGES_STATUS")
    fun getMESSEGES_STATUS():String?{
        if(answerType.STRING_12 == null || answerType.STRING_12!!.length < 3){
            return null
        }
        return answerType.STRING_12!!.substring(2, 3)
    }

    @JsName("getHAVE_A_FULL_TEXT")
    fun getHAVE_A_FULL_TEXT():String?{
        if(answerType.STRING_12 == null || answerType.STRING_12!!.length < 4){
            return null
        }
        return answerType.STRING_12!!.substring(3, 4)
    }

    @JsName("getHAVE_ANSWER")
    fun getHAVE_ANSWER():String?{
        if(answerType.STRING_12 == null || answerType.STRING_12!!.length < 5){
            return null
        }
        return answerType.STRING_12!!.substring(4, 5)
    }

    @JsName("setPROFILE_LINE")
    fun setPROFILE_LINE(l_messeges_type: String,
                        l_messeges_access: String,
                        l_messeges_status: String,
                        l_have_full_text: String,
                        l_have_answer: String,
                        l_object_type: String){
        answerType.STRING_12 = l_messeges_type + l_messeges_access + l_messeges_status + l_have_full_text + l_have_answer + l_object_type
    }

    @JsName("getLAST_CHANGED")
    fun getLAST_CHANGED():Long{
        return answerType.LONG_15?:0L
    }

    @JsName("setLAST_CHANGED")
    fun setLAST_CHANGED(v:Long?){
        answerType.LONG_15 = v?:0L
    }

    @JsName("getMESSEGE_FULL_TEXT")
    fun getMESSEGE_FULL_TEXT():String{
        return answerType.STRING_14?:""
    }

    @JsName("setMESSEGE_FULL_TEXT")
    fun setMESSEGE_FULL_TEXT(v:String?){
        answerType.STRING_14 = v?:""
    }


    @JsName("getOBJECT_NAME")
    fun getOBJECT_NAME():String{
        return answerType.STRING_5?:""
    }

    @JsName("setMOBJECT_NAME")
    fun setOBJECT_NAME(v:String?){
        answerType.STRING_5 = v?:""
    }

    @JsName("getOBJECT_SERVER")
    fun getOBJECT_SERVER():String{
        return answerType.STRING_6?:""
    }

    @JsName("setOBJECT_SERVER")
    fun setOBJECT_SERVER(v:String?){
        answerType.STRING_6 = v?:""
    }

    @JsName("setMESSEGES_TYPE_ACCESS_STATUS_HAVEFULLTEXT_HAVEANSWER_OBJECTTYPE")
    fun setMESSEGES_TYPE_ACCESS_STATUS_HAVEFULLTEXT_HAVEANSWER_OBJECTTYPE(v:String){
        answerType.STRING_12 = v
    }

    @JsName("getOBJECT_LINK")
    fun getOBJECT_LINK():String{
        return answerType.STRING_8?:""
    }

    @JsName("setOBJECT_LINK")
    fun setOBJECT_LINK(v:String?){
        answerType.STRING_8 = v?:""
    }


    @JsName("getANSWER_OBJECT_AVATAR")
    fun getANSWER_OBJECT_AVATAR():ByteArray?{
        return answerType.BLOB_1
    }

    @JsName("setANSWER_OBJECT_AVATAR")
    fun setANSWER_OBJECT_AVATAR(v:ByteArray?){
        answerType.BLOB_1 = v
    }

    @JsName("getOBJECT_AVATAR")
    fun getOBJECT_AVATAR():ByteArray?{
        return answerType.BLOB_2
    }

    @JsName("setOBJECT_AVATAR")
    fun setOBJECT_AVATAR(v:ByteArray?){
        answerType.BLOB_2 = v
    }

    @JsName("getMESSEGES_AVATAR")
    fun getMESSEGES_AVATAR():ByteArray?{
        return answerType.BLOB_3
    }

    @JsName("setAVATAR")
    fun setMESSEGES_AVATAR(v:ByteArray?){
        this.answerType.BLOB_3 = v
    }

    fun merge(v :KMessege? ){
        if(v == null ){
            return
        }
        this.answerType.merge(v.answerType)

        if(v.answerType.LONG_4 != null && v.answerType.LONG_4 != 0L){
            this.answerType.LONG_4 = v.answerType.LONG_4
        }

        if(v.answerType.LONG_5 != null && v.answerType.LONG_5 != 0L){
            this.answerType.LONG_5 = v.answerType.LONG_5
        }

        if(v.answerType.LONG_6 != null && v.answerType.LONG_6 != 0L){
            this.answerType.LONG_6 = v.answerType.LONG_6
        }

        if(v.answerType.LONG_7 != null && v.answerType.LONG_7 != 0L){
            this.answerType.LONG_7 = v.answerType.LONG_7
        }

        if(v.answerType.LONG_8 != null && v.answerType.LONG_8 != 0L){
            this.answerType.LONG_8 = v.answerType.LONG_8
        }

        if(v.answerType.LONG_9 != null && v.answerType.LONG_9 != 0L){
            this.answerType.LONG_9 = v.answerType.LONG_9
        }

        if(v.answerType.LONG_10 != null && v.answerType.LONG_10 != 0L){
            this.answerType.LONG_10 = v.answerType.LONG_10
        }

        if(v.answerType.STRING_5 != null && v.answerType.STRING_5!!.isNotEmpty()){
            this.answerType.STRING_5 = v.answerType.STRING_5
        }

        if(v.answerType.STRING_6 != null && v.answerType.STRING_6!!.isNotEmpty()){
            this.answerType.STRING_6 = v.answerType.STRING_6
        }

        if(v.answerType.STRING_7 != null && v.answerType.STRING_7!!.isNotEmpty()){
            this.answerType.STRING_7 = v.answerType.STRING_7
        }

        if(v.answerType.STRING_8 != null && v.answerType.STRING_8!!.isNotEmpty()){
            this.answerType.STRING_8 = v.answerType.STRING_8
        }

        if(v.answerType.STRING_9 != null && v.answerType.STRING_9!!.isNotEmpty()){
            this.answerType.STRING_9 = v.answerType.STRING_9
        }

        if(v.answerType.STRING_13 != null && v.answerType.STRING_13!!.isNotEmpty()){
            this.answerType.STRING_13 = v.answerType.STRING_13   //answer start text
        }

    }
}