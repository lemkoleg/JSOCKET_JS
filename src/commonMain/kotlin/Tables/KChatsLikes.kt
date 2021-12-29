package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName


@JsName("KChatsLikes")
class KChatsLikes {

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

    @JsName("getCHATS_ID")
    fun getCHATS_ID():String{
        return answerType.IDENTIFICATOR_5?:""
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v:String){
        answerType.IDENTIFICATOR_5 = v
    }

    @JsName("getACCOUNTS_ID")
    fun getACCOUNTS_ID():String{
        return answerType.IDENTIFICATOR_7?:""
    }

    @JsName("setACCOUNTS_ID")
    fun setACCOUNTS_ID(v:String){
        answerType.IDENTIFICATOR_7 = v
    }

    @JsName("getRELATIONS")
    fun getRELATIONS():String{
        return answerType.STRING_5?:""
    }

    @JsName("setRELATIONS")
    fun setRELATIONS(v:String){
        answerType.STRING_5 = v
    }

    @JsName("getADDING_DATE")
    fun getADDING_DATE():Long{
        return answerType.LONG_4?:0L
    }

    @JsName("setADDING_DATE")
    fun setADDING_DATE(v:Long){
        answerType.LONG_4 = v
    }

    @JsName("getIS_ONLINE")
    fun getIS_ONLINE():String{
        return answerType.STRING_6?:""
    }

    @JsName("setIS_ONLINE")
    fun setIS_ONLINE(v:String){
        answerType.STRING_6 = v
    }

    @JsName("getFIRST_MESS_COUNT")
    fun getFIRST_MESS_COUNT():Long{
        return answerType.LONG_8?:0L
    }

    @JsName("setFIRST_MESS_COUNT")
    fun setFIRST_MESS_COUNT(v:Long){
        answerType.LONG_8 = v
    }

    @JsName("getLAST_MESS_COUNT")
    fun getLAST_MESS_COUNT():Long{
        return answerType.LONG_9?:0L
    }

    @JsName("setLAST_MESS_COUNT")
    fun setLAST_MESS_COUNT(v:Long){
        answerType.LONG_9 = v
    }

    @JsName("getFIRST_MESS_COUNT")
    fun getLAST_DATE_DELIVERED():Long{
        return answerType.LONG_10?:0L
    }

    @JsName("setFIRST_MESS_COUNT")
    fun setLAST_DATE_DELIVERED(v:Long){
        answerType.LONG_10 = v
    }

    @JsName("getLAST_READED_MESS_ID")
    fun getLAST_READED_MESS_ID():Long{
        return answerType.LONG_11?:0L
    }

    @JsName("setLAST_READED_MESS_ID")
    fun setLAST_READED_MESS_ID(v:Long){
        answerType.LONG_11 = v
    }

    @JsName("getBALANCE")
    fun getBALANCE():Long{
        return answerType.LONG_7?:0L
    }

    @JsName("setBALANCE")
    fun setBALANCE(v:Long){
        answerType.LONG_7 = v
    }

    @JsName("getLAST_CONNECT")
    fun getLAST_CONNECT():Long{
        return answerType.LONG_6?:0L
    }

    @JsName("setLAST_CONNECT")
    fun setLAST_CONNECT(v:Long){
        answerType.LONG_6 = v
    }

    @JsName("getDATE_DELETE")
    fun getDATE_DELETE():Long{
        return answerType.LONG_5?:0L
    }

    @JsName("setDATE_DELETE")
    fun setDATE_DELETE(v:Long){
        answerType.LONG_5 = v
    }

    @JsName("getACCOUNTS_NAME")
    fun getACCOUNTS_NAME():String{
        return answerType.STRING_1?:""
    }

    @JsName("setACCOUNTS_NAME")
    fun setACCOUNTS_NAME(v:String){
        answerType.STRING_1 = v
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID():String{
        return answerType.IDENTIFICATOR_2?:""
    }

    @JsName("setAVATAR_ID")
    fun getAVATAR_ID(v:String?){
        answerType.IDENTIFICATOR_2 = v?:""
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

    @JsName("getAVATAR")
    fun getAVATAR():ByteArray?{
        return answerType.BLOB_1
    }

    @JsName("setAVATAR")
    fun setAVATAR(v:ByteArray?){
        answerType.BLOB_1 = v
    }

    @JsName("getIS_UPDATE_BLOB")
    fun getIS_UPDATE_BLOB():String{
        if(answerType.STRING_20 == null || answerType.STRING_20!!.length < 2){
            return "0"
        }
        return answerType.STRING_20!!.substring(1, 2)
    }

    @JsName("getRECORD_TYPE")
    fun getRECORD_TYPE():String{
        if(answerType.STRING_20 == null || answerType.STRING_20!!.length < 8){
            return ""
        }
        return answerType.STRING_20!!.substring(7, 8)
    }

    @JsName("setSTRING_20")
    fun setSTRING_20(v:String?){
        answerType.STRING_20 = v?:""
    }



}