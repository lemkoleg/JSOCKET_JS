@file:Suppress("unused")

package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@JsName("KCommands")
class KCommands {

    private val answerType: ANSWER_TYPE

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

    @JsName("getCOMMANDS_ID")
    fun getCOMMANDS_ID():Int{
        return (answerType.LONG_1?:0) as Int
    }

    @JsName("setCOMMANDS_ID")
    fun setCOMMANDS_ID(v:Long){
        answerType.LONG_1 = v
    }

    @JsName("getCOMMANDS_ACCESS")
    fun getCOMMANDS_ACCESS():String{
        return answerType.STRING_1?:""
    }

    @JsName("setCOMMANDS_ACCESS")
    fun setCOMMANDS_ACCESS(v:String){
        answerType.STRING_1 = v.trim()
    }

    @JsName("getCOMMANDS_PROFILE")
    fun getCOMMANDS_PROFILE():String{
        return answerType.STRING_2?:""
    }

    @JsName("setCOMMANDS_PROFILE")
    fun setCOMMANDS_PROFILE(v:String){
        answerType.STRING_2 = v.trim()
    }

    @JsName("getCOMMANDS_NECESSARILY_FIELDS")
    fun getCOMMANDS_NECESSARILY_FIELDS():String{
        return answerType.STRING_3?:""
    }

    @JsName("setCOMMANDS_NECESSARILY_FIELDS")
    fun setCOMMANDS_NECESSARILY_FIELDS(v:String){
        answerType.STRING_3 = v.trim()
    }

    @JsName("getLAST_UPDATE")
    fun getLAST_UPDATE():Long{
        return answerType.LONG_2?:0L
    }

    @JsName("setLAST_UPDATE")
    fun setLAST_UPDATE(v:Long){
        answerType.LONG_2 = v
    }

    @JsName("getCOUNT_OF_EXECUTE")
    fun getCOUNT_OF_EXECUTE():Long{
        return answerType.LONG_3?:0L
    }

    @JsName("setCOUNT_OF_EXECUTE")
    fun setCOUNT_OF_EXECUTE(v:Long){
        answerType.LONG_3 = v
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