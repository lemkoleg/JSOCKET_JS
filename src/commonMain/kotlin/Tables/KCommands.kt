@file:Suppress("unused")

package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@JsName("Commands")
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
        return answerType.INTEGER_1?:0
    }

    @JsName("getCOMMANDS_ACCESS")
    fun getCOMMANDS_ACCESS():String{
        return answerType.STRING_1?:""
    }

    @JsName("getCOMMANDS_PROFILE")
    fun getCOMMANDS_PROFILE():String{
        return answerType.STRING_2?:""
    }

    @JsName("getCOMMANDS_NECESSARILY_FIELDS")
    fun getCOMMANDS_NECESSARILY_FIELDS():String{
        return answerType.STRING_3?:""
    }

    @JsName("setCOMMANDS_ID")
    fun setCOMMANDS_ID(v: Int){
        answerType.INTEGER_1 = v
    }

    @JsName("setCOMMANDS_ACCESS")
    fun setCOMMANDS_ACCESS(v:String){
        answerType.STRING_1 = v.trim()
    }

    @JsName("setCOMMANDS_PROFILE")
    fun setCOMMANDS_PROFILE(v:String){
        answerType.STRING_2 = v.trim()
    }

    @JsName("setCOMMANDS_NECESSARILY_FIELDS")
    fun setCOMMANDS_NECESSARILY_FIELDS(v:String){
        answerType.STRING_3 = v.trim()
    }




}