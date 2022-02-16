package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@JsName("KExceptions")
class KExceptions{

    private var NUMBER_OF_ECXEPTION: Int = 0
    private var LANG_OF_ECXEPTION: String = ""
    private var TEXT_OF_ECXEPTION: String = ""
    private var LAST_UPDATE: Long = 0

    private constructor()

    constructor(L_NUMBER_OF_ECXEPTION: Int,
                L_LANG_OF_ECXEPTION: String,
                L_TEXT_OF_ECXEPTION: String,
                L_LAST_UPDATE: Long){

        NUMBER_OF_ECXEPTION = L_NUMBER_OF_ECXEPTION
        LANG_OF_ECXEPTION = L_LANG_OF_ECXEPTION
        TEXT_OF_ECXEPTION = L_TEXT_OF_ECXEPTION
        LAST_UPDATE = L_LAST_UPDATE

    }

    constructor(ans : ANSWER_TYPE):this(ans.LONG_1!!.toInt(),
                                        ans.STRING_2!!,
                                        ans.STRING_1!!,
                                        ans.LONG_2!!)

    @JsName("getNUMBER_OF_ECXEPTION")
    fun getNUMBER_OF_ECXEPTION(): Int{
        return NUMBER_OF_ECXEPTION
    }

    @JsName("setNUMBER_OF_ECXEPTION")
    fun setNUMBER_OF_ECXEPTION(v: Int) {
        NUMBER_OF_ECXEPTION = v
    }

    @JsName("getLANG_OF_ECXEPTION")
    fun getLANG_OF_ECXEPTION():String {
        return LANG_OF_ECXEPTION
    }

    @JsName("setLANG_OF_ECXEPTION")
    fun setLANG_OF_ECXEPTION(v: String) {
        LANG_OF_ECXEPTION = v
    }

    @JsName("getTEXT_OF_ECXEPTION")
    fun getTEXT_OF_ECXEPTION():String {
        return TEXT_OF_ECXEPTION
    }

    @JsName("setTEXT_OF_ECXEPTION")
    fun setTEXT_OF_ECXEPTION(v: String) {
        TEXT_OF_ECXEPTION = v
    }

    @JsName("getLAST_UPDATE")
    fun getLATS_UPDATE(): Long {
        return LAST_UPDATE
    }

    @JsName("setLAST_UPDATE")
    fun setLAST_UPDATE(v: Long) {
        LAST_UPDATE = v
    }

    @JsName("UPDATE_EXCEPTIONS")
    fun UPDATE_EXCEPTIONS(L_NUMBER_OF_ECXEPTION: Int,
                          L_LANG_OF_ECXEPTION: String,
                          L_TEXT_OF_ECXEPTION: String,
                          L_LAST_UPDATE: Long){

        NUMBER_OF_ECXEPTION = L_NUMBER_OF_ECXEPTION
        LANG_OF_ECXEPTION = L_LANG_OF_ECXEPTION
        TEXT_OF_ECXEPTION = L_TEXT_OF_ECXEPTION
        LAST_UPDATE = L_LAST_UPDATE

    }

    @JsName("UPDATE_EXCEPTIONS")
    fun UPDATE_EXCEPTIONS(ans: ANSWER_TYPE) {
        UPDATE_EXCEPTIONS(ans.LONG_1!!.toInt(),
                          ans.STRING_2!!,
                          ans.STRING_1!!,
                          ans.LONG_2!!)
    }
}