@file:Suppress("unused")

package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@JsName("KCommands")
class KCommands:ANSWER_TYPE {

    constructor(): super()
    
    constructor(ans : ANSWER_TYPE): super(ans)

    @JsName("getCOMMANDS_ID")
    fun getCOMMANDS_ID():Long{
        return (LONG_1?:0)
    }

    @JsName("setCOMMANDS_ID")
    fun setCOMMANDS_ID(v:Long){
        LONG_1 = v
    }

    @JsName("getCOMMANDS_ACCESS")
    fun getCOMMANDS_ACCESS():String{
        return STRING_1?:""
    }

    @JsName("setCOMMANDS_ACCESS")
    fun setCOMMANDS_ACCESS(v:String){
        STRING_1 = v.trim()
    }

    @JsName("getCOMMANDS_PROFILE")
    fun getCOMMANDS_PROFILE():String{
        return STRING_2?:""
    }

    @JsName("setCOMMANDS_PROFILE")
    fun setCOMMANDS_PROFILE(v:String){
        STRING_2 = v.trim()
    }

    @JsName("getCOMMANDS_NECESSARILY_FIELDS")
    fun getCOMMANDS_NECESSARILY_FIELDS():String{
        return STRING_3?:""
    }

    @JsName("setCOMMANDS_NECESSARILY_FIELDS")
    fun setCOMMANDS_NECESSARILY_FIELDS(v:String){
        STRING_3 = v.trim()
    }

    @JsName("getLAST_UPDATE")
    fun getLAST_UPDATE():Long{
        return LONG_2?:0L
    }

    @JsName("setLAST_UPDATE")
    fun setLAST_UPDATE(v:Long){
        LONG_2 = v
    }

    @JsName("getCOUNT_OF_EXECUTE")
    fun getCOUNT_OF_EXECUTE():Long{
        return LONG_3?:0L
    }

    @JsName("setCOUNT_OF_EXECUTE")
    fun setCOUNT_OF_EXECUTE(v:Long){
        LONG_3 = v
    }

}