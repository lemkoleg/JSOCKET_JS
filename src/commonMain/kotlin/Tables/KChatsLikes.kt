package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName


@JsName("KChatsLikes")
class KChatsLikes:ANSWER_TYPE {

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

    @JsName("getACCOUNTS_ID")
    fun getACCOUNTS_ID():String{
        return IDENTIFICATOR_7?:""
    }

    @JsName("setACCOUNTS_ID")
    fun setACCOUNTS_ID(v:String){
        IDENTIFICATOR_7 = v
    }

    @JsName("getRELATIONS")
    fun getRELATIONS():String{
        return STRING_5?:""
    }

    @JsName("setRELATIONS")
    fun setRELATIONS(v:String){
        STRING_5 = v
    }

    @JsName("getCHATS_LIKES_PROFILE")
    fun getCHATS_LIKES_PROFILE():String{
        return STRING_7?:""
    }

    @JsName("setCHATS_LIKES_PROFILE")
    fun setCHATS_LIKES_PROFILE(v:String){
        STRING_7 = v
    }

    @JsName("getADDING_DATE")
    fun getADDING_DATE():Long{
        return LONG_4?:0L
    }

    @JsName("setADDING_DATE")
    fun setADDING_DATE(v:Long){
        LONG_4 = v
    }

    @JsName("getIS_ONLINE")
    fun getIS_ONLINE():String{
        return STRING_6?:""
    }

    @JsName("setIS_ONLINE")
    fun setIS_ONLINE(v:String){
        STRING_6 = v
    }

    @JsName("getFIRST_MESS_COUNT")
    fun getFIRST_MESS_COUNT():Long{
        return LONG_8?:0L
    }

    @JsName("setFIRST_MESS_COUNT")
    fun setFIRST_MESS_COUNT(v:Long){
        LONG_8 = v
    }

    @JsName("getLAST_MESS_COUNT")
    fun getLAST_MESS_COUNT():Long{
        return LONG_9?:0L
    }

    @JsName("setLAST_MESS_COUNT")
    fun setLAST_MESS_COUNT(v:Long){
        LONG_9 = v
    }

    @JsName("getLAST_DATE_DELIVERED")
    fun getLAST_DATE_DELIVERED():Long{
        return LONG_10?:0L
    }

    @JsName("setLAST_DATE_DELIVERED")
    fun setLAST_DATE_DELIVERED(v:Long){
        LONG_10 = v
    }

    @JsName("getLAST_MESS_DATE_DELIVERED")
    fun getLAST_MESS_DATE_DELIVERED():Long{
        return LONG_11?:0L
    }

    @JsName("setLAST_MESS_DATE_DELIVERED")
    fun setLAST_MESS_DATE_DELIVERED(v:Long){
        LONG_11 = v
    }
    

    @JsName("getLAST_READED_MESS_ID")
    fun getLAST_READED_MESS_ID():Long{
        return LONG_12?:0L
    }

    @JsName("setLAST_READED_MESS_ID")
    fun setLAST_READED_MESS_ID(v:Long){
        LONG_12 = v
    }

    @JsName("getBALANCE")
    fun getBALANCE():Long{
        return LONG_7?:0L
    }

    @JsName("setBALANCE")
    fun setBALANCE(v:Long){
        LONG_7 = v
    }

    @JsName("getLAST_CONNECT")
    fun getLAST_CONNECT():Long{
        return LONG_6?:0L
    }

    @JsName("setLAST_CONNECT")
    fun setLAST_CONNECT(v:Long){
        LONG_6 = v
    }

    @JsName("getDATE_DELETE")
    fun getDATE_DELETE():Long{
        return LONG_5?:0L
    }

    @JsName("setDATE_DELETE")
    fun setDATE_DELETE(v:Long){
        LONG_5 = v
    }

    @JsName("getACCOUNTS_NAME")
    fun getACCOUNTS_NAME():String{
        return STRING_1?:""
    }

    @JsName("setACCOUNTS_NAME")
    fun setACCOUNTS_NAME(v:String){
        STRING_1 = v
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID():String{
        return IDENTIFICATOR_2?:""
    }

    @JsName("setAVATAR_ID")
    fun setAVATAR_ID(v:String?){
        IDENTIFICATOR_2 = v?:""
    }

    fun merge(v :KChatsLikes? ){
        if(v == null ){
            return
        }
        super.merge(v)
    }


}