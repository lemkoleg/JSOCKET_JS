@file:Suppress("unused")

package Tables

import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

@JsName("KRegData")
class KRegData:ANSWER_TYPE {
    

    constructor(): super()

    constructor(ans : ANSWER_TYPE): super(ans)


    @JsName("getACCOUNT_PROFILE")
    fun getACCOUNT_PROFILE():String{
        return STRING_5?:""
    }

    @JsName("setACCOUNT_PROFILE")
    fun setACCOUNT_PROFILE(v: String){
        STRING_5 = v.trim()
    }

    @JsName("getREQUEST_PROFILE")
    fun getREQUEST_PROFILE():String{
        return STRING_6?:""
    }

    @JsName("setREQUEST_PROFILE")
    fun setREQUEST_PROFILE(v: String){
        STRING_6 = v.trim()
    }


    @JsName("getBALANCE_OF_CHATS")
    fun getBALANCE_OF_CHATS():Long{
        return LONG_3?:0L
    }

    @JsName("setBALANCE_OF_CHATS")
    fun setBALANCE_OF_CHATS(v:Long?){
        LONG_3 = v?:0L
    }

}