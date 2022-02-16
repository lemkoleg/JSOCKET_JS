@file:Suppress("unused")

package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@JsName("KCommands")
class KCommands{

    private var COMMANDS_ID: Int = 0
    private var COMMANDS_ACCESS: String = ""
    private var COMMANDS_PROFILE: String = ""
    private var COMMANDS_NECESSARILY_FIELDS: String = ""
    private var LAST_UPDATE: Long = 0
    private var COUNT_OF_EXECUTE: Int = 0

    private constructor()

    constructor(L_COMMANDS_ID: Int, 
                L_COMMANDS_ACCESS: String,
                L_COMMANDS_PROFILE: String,
                L_OMMANDS_NECESSARILY_FIELDS: String,
                L_LAST_UPDATE: Long,
                L_COUNT_OF_EXECUTE: Int
    ){

        COMMANDS_ID = L_COMMANDS_ID
        COMMANDS_ACCESS = L_COMMANDS_ACCESS
        COMMANDS_PROFILE = L_COMMANDS_PROFILE
        COMMANDS_NECESSARILY_FIELDS = L_OMMANDS_NECESSARILY_FIELDS
        LAST_UPDATE = L_LAST_UPDATE
        COUNT_OF_EXECUTE = L_COUNT_OF_EXECUTE
        
    }

    constructor(ans: ANSWER_TYPE):this(ans.LONG_1!!.toInt(),
                                       ans.STRING_1!!,
                                       ans.STRING_2!!,
                                       ans.STRING_3!!,
                                       ans.LONG_2!!,
                                       ans.LONG_3!!.toInt())

    @JsName("getCOMMANDS_ID")
    fun getCOMMANDS_ID():Int{
        return COMMANDS_ID
    }

    @JsName("setCOMMANDS_ID")
    fun setCOMMANDS_ID(v:Int){
        COMMANDS_ID = v
    }

    @JsName("getCOMMANDS_ACCESS")
    fun getCOMMANDS_ACCESS():String{
        return COMMANDS_ACCESS
    }

    @JsName("setCOMMANDS_ACCESS")
    fun setCOMMANDS_ACCESS(v:String){
        COMMANDS_ACCESS = v
    }

    @JsName("getCOMMANDS_PROFILE")
    fun getCOMMANDS_PROFILE():String{
        return COMMANDS_PROFILE
    }

    @JsName("setCOMMANDS_PROFILE")
    fun setCOMMANDS_PROFILE(v:String){
        COMMANDS_PROFILE = v
    }

    @JsName("getCOMMANDS_NECESSARILY_FIELDS")
    fun getCOMMANDS_NECESSARILY_FIELDS():String{
        return COMMANDS_NECESSARILY_FIELDS
    }

    @JsName("setCOMMANDS_NECESSARILY_FIELDS")
    fun setCOMMANDS_NECESSARILY_FIELDS(v:String){
        COMMANDS_NECESSARILY_FIELDS = v
    }

    @JsName("getLAST_UPDATE")
    fun getLAST_UPDATE():Long{
        return LAST_UPDATE
    }

    @JsName("setLAST_UPDATE")
    fun setLAST_UPDATE(v:Long){
        LAST_UPDATE = v
    }

    @JsName("getCOUNT_OF_EXECUTE")
    fun getCOUNT_OF_EXECUTE():Int{
        return COUNT_OF_EXECUTE
    }

    @JsName("setCOUNT_OF_EXECUTE")
    fun setCOUNT_OF_EXECUTE(v:Int){
        COUNT_OF_EXECUTE = v
    }

    @JsName("UPDATE_COMMANDS")
    fun UPDATE_COMMANDS(L_COMMANDS_ID: Int,
                        L_COMMANDS_ACCESS: String,
                        L_COMMANDS_PROFILE: String,
                        L_OMMANDS_NECESSARILY_FIELDS: String,
                        L_LAST_UPDATE: Long,
                        L_COUNT_OF_EXECUTE: Int
    ){

        COMMANDS_ID = L_COMMANDS_ID
        COMMANDS_ACCESS = L_COMMANDS_ACCESS
        COMMANDS_PROFILE = L_COMMANDS_PROFILE
        COMMANDS_NECESSARILY_FIELDS = L_OMMANDS_NECESSARILY_FIELDS
        LAST_UPDATE = L_LAST_UPDATE
        COUNT_OF_EXECUTE = L_COUNT_OF_EXECUTE

    }

    @JsName("UPDATE_COMMANDS")
    fun UPDATE_COMMANDS(ans: ANSWER_TYPE) {
        UPDATE_COMMANDS(ans.LONG_1!!.toInt(),
                        ans.STRING_1!!,
                        ans.STRING_2!!,
                        ans.STRING_3!!,
                        ans.LONG_2!!,
                        ans.LONG_3!!.toInt())
    }

}