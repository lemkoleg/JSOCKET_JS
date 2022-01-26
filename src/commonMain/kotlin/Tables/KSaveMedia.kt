/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import CrossPlatforms.slash
import com.soywiz.klock.DateTime
import io.ktor.util.InternalAPI
import io.ktor.utils.io.core.ExperimentalIoApi
import lib_exceptions.exc_file_size_is_wrong
import p_client.Jsocket
import p_client.myConnectionsID
import p_jsocket.ANSWER_TYPE
import sql.Sqlite_service
import kotlin.js.JsName


/**
 *
 * @author User
 */
@JsName("KSaveMedia")
class KSaveMedia {

    private val answerType:ANSWER_TYPE

    constructor(){
        answerType = ANSWER_TYPE()
    }

    constructor(ans : ANSWER_TYPE){
        answerType = ans
        setCONNECTION_ID()
    }

    @InternalAPI
    @ExperimentalIoApi
    @ExperimentalStdlibApi
    constructor(ljsocket: Jsocket){
        answerType = ANSWER_TYPE()
        setCONNECTION_ID()
        setOBJECTS_ID(ljsocket.value_id1)
        setOBJECTS_EXTENSION(ljsocket.object_extension)
        setOBJECT_NAME(ljsocket.value_par8)
        setOBJECT_INFO(ljsocket.value_par9)
        setSMALL_AVATAR(ljsocket.content)
        setIS_TEMP(1)
        setIS_DOWNLOAD(0)
        if (Sqlite_service.SAVEMEDIA.containsKey(getOBJECTS_ID())) {
            setIsTemp(Sqlite_service.SAVEMEDIA[getOBJECTS_ID()]!!.IsTemp())
            setIsDownLoaded(Sqlite_service.SAVEMEDIA[getOBJECTS_ID()]!!.IsDownLoaded())
        }

        setTIME_ADDED(if (ljsocket.last_date_of_update == 0L) {
            ljsocket.last_date_of_update
        } else {
            DateTime.nowUnixLong()
        })

        setOBJECT_FULL_NAME(CreateFileFullName())
        if (ljsocket.object_size == 0L) {
            throw exc_file_size_is_wrong(getOBJECT_FULL_NAME(), 0L)
        }
        setOBJECTS_SIZE(ljsocket.object_size)
    }


    @JsName("getANSWER_TYPE")
    fun getANSWER_TYPE():ANSWER_TYPE{
        return answerType
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

    @JsName("getOBJECT_INFO")
    fun getOBJECT_INFO():String{
        return answerType.STRING_3?:""
    }

    @JsName("getSMALL_AVATAR")
    fun getSMALL_AVATAR():ByteArray?{
        return answerType.BLOB_1
    }

    @JsName("getIS_TEMP")
    fun getIS_TEMP():Int{
        return answerType.INTEGER_1?:0
    }

    @JsName("getIS_DOWNLOAD")
    fun getIS_DOWNLOAD():Int{
        return answerType.INTEGER_2?:0
    }

    @JsName("getTIME_ADDED")
    fun getTIME_ADDED():Long{
        return answerType.LONG_2?:0L
    }

    @JsName("getOBJECTS_SIZE")
    fun getOBJECTS_SIZE():Long{
        return answerType.LONG_3?:0L
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

    @JsName("setOBJECT_INFO")
    fun setOBJECT_INFO(v:String){
        answerType.STRING_3 = v.trim()
    }

    @JsName("setSMALL_AVATAR")
    fun setSMALL_AVATAR(v:ByteArray?){
        answerType.BLOB_1 = v
    }

    @JsName("setIS_TEMP")
    fun setIS_TEMP(v:Int){
        answerType.INTEGER_1 = v
    }

    @JsName("setIS_DOWNLOAD")
    fun setIS_DOWNLOAD(v:Int){
        answerType.INTEGER_2 = v
    }

    @JsName("setTIME_ADDED")
    fun setTIME_ADDED(v:Long){
        answerType.LONG_2 = v
    }

    @JsName("setOBJECTS_SIZE")
    fun setOBJECTS_SIZE(v:Long){
        answerType.LONG_3 = v
    }

    @JsName("setOBJECT_FULL_NAME")
    fun setOBJECT_FULL_NAME(v:String){
        answerType.STRING_4 = v.trim()
    }

    @JsName("getSmallAvatarSize")
    fun getSmallAvatarSize():Int{
        return getSMALL_AVATAR()?.size?:0

    }

   
    @JsName("CreateFileFullName")
    private fun CreateFileFullName(): String {
        return getOBJECTS_ID().substring(0, 2) + slash + getOBJECTS_ID() + "." + getOBJECTS_EXTENSION()
    }

    @JsName("setIsTemp")
    fun setIsTemp(lIsTemp: Boolean) {
        setIS_TEMP(if(lIsTemp)1
        else 0)
    }

    @JsName("setIsDownLoaded")
    fun setIsDownLoaded(lIsDownLoaded: Boolean) {
        setIS_DOWNLOAD(if(lIsDownLoaded)1
        else 0)
    }

    @JsName("IsTemp")
    fun IsTemp(): Boolean {
        return getIS_TEMP() == 1
    }

    @JsName("IsDownLoaded")
    fun IsDownLoaded(): Boolean {
        return getIS_DOWNLOAD() == 1
    }
}