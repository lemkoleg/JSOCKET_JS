/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables

import com.soywiz.korio.lang.substr
import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName

/**
 *
 * @author User
 */
@JsName("Messege")
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
        return answerType.IDENTIFICATOR_1?:""
    }

    @JsName("getMESSEGES_ID")
    fun getMESSEGES_ID():Long{
        return answerType.LONG_1?:0L
    }

    @JsName("getMESSEGES_CHATS_COUNT")
    fun getMESSEGES_CHATS_COUNT():Long{
        return answerType.LONG_4?:0L
    }

    @JsName("getMESSEGES_OWNER")
    fun getMESSEGES_OWNER():String{
        return answerType.IDENTIFICATOR_2?:""
    }

    @JsName("getMESSEGES_ANSWER")
    fun getMESSEGES_ANSWER():Long{
        return answerType.LONG_2?:0L
    }

    @JsName("getMESSEGES_ADRESSER")
    fun getMESSEGES_ADRESSER():String{
        return answerType.IDENTIFICATOR_4?:""
    }

    @JsName("getOBJECTS_LINK")
    fun getOBJECTS_LINK():String{
        return answerType.IDENTIFICATOR_5?:""
    }

    @JsName("getMESSEGE_TEXT")
    fun getMESSEGE_TEXT():String{
        return answerType.STRING_5?:""
    }

    @JsName("getMESSEGES_AVATAR")
    fun getMESSEGES_AVATAR():ByteArray?{
        return answerType.BLOB_1
    }

    @JsName("getMESSEGES_LIKES")
    fun getMESSEGES_LIKES():Int{
        return answerType.INTEGER_1?:0
    }

    @JsName("getMESSEGES_DISLIKES")
    fun getMESSEGES_DISLIKES():Int{
        return answerType.INTEGER_2?:0
    }

    @JsName("getNOT_DELIVERIED")
    fun getNOT_DELIVERIED():Int{
        return answerType.INTEGER_3?:0
    }

    @JsName("getNOT_READED")
    fun getNOT_READED():Int{
        return answerType.INTEGER_4?:0
    }

    @JsName("getMESSEGES_ACCESS")
    fun getMESSEGES_ACCESS():String{
        return answerType.STRING_1?.substring(0, 1)?:""
    }

    @JsName("getMESSEGES_TYPE")
    fun getMESSEGES_TYPE():String{
        return answerType.STRING_1?.substring(1, 2)?:""
    }

    @JsName("getDATE_ADDING")
    fun getDATE_ADDING():Long{
        return answerType.LONG_3?:0L
    }

    @JsName("getLAST_UPDATING_DATE")
    fun getLAST_UPDATING_DATE():Long{
        return answerType.LONG_5?:0L
    }

    @JsName("getOBJECTS_LINK_SUBSCRIBE")
    fun getOBJECTS_LINK_SUBSCRIBE():String{
        return answerType.STRING_2?:""
    }

    @JsName("getOBJECTS_LINK_SMALL_AVATAR")
    fun getOBJECTS_LINK_SMALL_AVATAR():ByteArray?{
        return answerType.BLOB_2
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v:String){
        answerType.IDENTIFICATOR_1 = v
    }

    @JsName("setMESSEGES_ID")
    fun setMESSEGES_ID(v:Long){
        answerType.LONG_1 = v
    }

    @JsName("setMESSEGES_CHATS_COUNT")
    fun setMESSEGES_CHATS_COUNT(v:Long){
        answerType.LONG_4 = v
    }

    @JsName("setMESSEGES_OWNER")
    fun setMESSEGES_OWNER(v:String){
        answerType.IDENTIFICATOR_2 = v.trim()
    }

    @JsName("setMESSEGES_ANSWER")
    fun setMESSEGES_ANSWER(v:Long){
        answerType.LONG_2 = v
    }

    @JsName("setMESSEGES_ADRESSER")
    fun setMESSEGES_ADRESSER(v:String){
        answerType.IDENTIFICATOR_4 = v.trim()
    }

    @JsName("setOBJECTS_LINK")
    fun setOBJECTS_LINK(v:String){
        answerType.IDENTIFICATOR_5 = v.trim()
    }

    @JsName("setMESSEGE_TEXT")
    fun setMESSEGE_TEXT(v:String){
       answerType.STRING_5 = v.trim()
    }

    @JsName("setMESSEGES_AVATAR")
    fun setMESSEGES_AVATAR(v:ByteArray?){
        answerType.BLOB_1 = v
    }

    @JsName("setMESSEGES_LIKES")
    fun setMESSEGES_LIKES(v:Int){
        answerType.INTEGER_1 = v
    }

    @JsName("setMESSEGES_DISLIKES")
    fun setMESSEGES_DISLIKES(v:Int){
       answerType.INTEGER_2 = v
    }

    @JsName("setNOT_DELIVERIED")
    fun setNOT_DELIVERIED(v:Int){
        answerType.INTEGER_3 = v
    }

    @JsName("setNOT_READED")
    fun setNOT_READED(v:Int){
        answerType.INTEGER_4 = v
    }

    @JsName("setMESSEGES_ACCESS")
    fun setMESSEGES_ACCESS(v:String){
        answerType.STRING_1 = v.substring(0, 1)+answerType.STRING_1?.substr(1)
    }

    @JsName("setMESSEGES_TYPE")
    fun setMESSEGES_TYPE(v:String){
        answerType.STRING_1 = answerType.STRING_1?.substring(0, 1) + v.substring(0, 1)+answerType.STRING_1?.substr(2)
    }

    @JsName("setDATE_ADDING")
    fun setDATE_ADDING(v:Long){
        answerType.LONG_3 = v
    }

    @JsName("setLAST_UPDATING_DATE")
    fun setLAST_UPDATING_DATE(v:Long){
        answerType.LONG_5 = v
    }

    @JsName("setOBJECTS_LINK_SUBSCRIBE")
    fun setOBJECTS_LINK_SUBSCRIBE(v:String){
        answerType.STRING_2 = v.trim()
    }

    @JsName("setOBJECTS_LINK_SMALL_AVATAR")
    fun setOBJECTS_LINK_SMALL_AVATAR(v:ByteArray?){
        answerType.BLOB_2 = v
    }

    @JsName("UpdateMessege")
    fun UpdateMessege(lANSWER_TYPE: ANSWER_TYPE) {
        answerType.setValue(lANSWER_TYPE)
    }
}