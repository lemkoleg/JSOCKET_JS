/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import p_client.myConnectionsID
import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName


/**
 *
 * @author User
 */
@JsName("KSendMedia")
class KSendMedia  {

    private val answerType:ANSWER_TYPE

    constructor(){
        answerType = ANSWER_TYPE()
    }

    constructor(ans : ANSWER_TYPE){
        answerType = ans
    }
    constructor(lObjectID: String,
                lObjectExtension: String,
                lFullFileName: String,
                lIsDownLoaded: Int):super(){
        answerType = ANSWER_TYPE()
        setOBJECTS_ID(lObjectID)
        setOBJECTS_EXTENSION(lObjectExtension)
        setOBJECT_FULL_NAME(lFullFileName)
        setIS_DOWNLOAD(lIsDownLoaded)

    }

    @JsName("getOBJECTS_ID")
    fun getOBJECTS_ID():String{
        return answerType.IDENTIFICATOR_1?:""
    }

    @JsName("getOBJECTS_EXTENSION")
    fun getOBJECTS_EXTENSION():String{
        return answerType.STRING_1?:""
    }

    @JsName("getCONNECTION_ID")
    fun getCONNECTION_ID():Long{
        return myConnectionsID.value
    }

    @JsName("getOBJECT_NAME")
    fun getOBJECT_NAME():String{
        return answerType.STRING_2?:""
    }

    @JsName("getIS_DOWNLOAD")
    fun getIS_DOWNLOAD():Int{
        return answerType.INTEGER_1?:0
    }


    @JsName("getOBJECT_FULL_NAME")
    fun getOBJECT_FULL_NAME():String{
        return answerType.STRING_4?:""
    }

    @JsName("setOBJECTS_ID")
    fun setOBJECTS_ID(v:String){
        answerType.IDENTIFICATOR_1 = v.trim()
    }

    @JsName("setOBJECTS_EXTENSION")
    fun setOBJECTS_EXTENSION(v:String){
        answerType.STRING_1 = v.trim()
    }

    @JsName("setCONNECTION_ID")
    private fun setCONNECTION_ID(){
        answerType.LONG_1 = myConnectionsID.value
    }

    @JsName("setOBJECT_NAME")
    fun setOBJECT_NAME(v:String){
        answerType.STRING_2 = v.trim()
    }

    @JsName("setIS_DOWNLOAD")
    fun setIS_DOWNLOAD(v:Int){
        answerType.INTEGER_1 = v
    }


    @JsName("setOBJECT_FULL_NAME")
    fun setOBJECT_FULL_NAME(v:String){
        answerType.STRING_4 = v.trim()
    }

}