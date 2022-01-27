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
class KSaveMedia:ANSWER_TYPE {


    constructor(): super()

    constructor(ans : ANSWER_TYPE): super(ans)

    @JsName("getOBJECT_ID")
    fun getOBJECT_ID():String{
        return IDENTIFICATOR_5?:""
    }

    @JsName("setOBJECT_ID")
    fun setOBJECT_ID(v:String){
        IDENTIFICATOR_5 = v.trim()
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID():String{
        return IDENTIFICATOR_6?:""
    }

    @JsName("setAVATAR_ID")
    fun setAVATARID(v: String?){
        IDENTIFICATOR_6 = v?.trim()?:""
    }

    @JsName("getOBJECT_OWNER")
    fun getOBJECT_OWNER():String{
        return IDENTIFICATOR_7?:""
    }

    @JsName("setOBJECT_OWNER")
    fun setOBJECT_OWNER(v: String?){
        IDENTIFICATOR_7 = v?.trim()?:""
    }

    @JsName("getOBJECT_NAME")
    fun getOBJECT_NAME():String{
        return STRING_5?:""
    }

    @JsName("setOBJECT_NAME")
    fun setOBJECT_NAME(v:String){
        STRING_5 = v.trim()
    }

    @JsName("getOBJECT_SERVER")
    fun getOBJECT_SERVER():String{
        return STRING_6?:""
    }

    @JsName("setOBJECT_SERVER")
    fun setOBJECT_SERVER(v:String){
        STRING_6 = v.trim()
    }

    @JsName("getOBJECT_PROFILE_STRING")
    fun getOBJECT_PROFILE_STRING():String{
        return STRING_7?:""
    }

    @JsName("setOBJECT_PROFILE_STRING")
    fun setOBJECT_PROFILE_STRING(v:String){
        STRING_7 = v.trim()
    }

    @JsName("getOBJECT_LINK")
    fun getOBJECT_LINK():String{
        return STRING_8?:""
    }

    @JsName("setOBJECT_LINK")
    fun setOBJECT_LINK(v:String){
        STRING_8 = v.trim()
    }

    @JsName("getOBJECT_EXTENSION")
    fun getOBJECT_EXTENSION():String{
        return STRING_9?:""
    }

    @JsName("setOBJECT_EXTENSION")
    fun setOBJECT_EXTENSION(v:String?){
        STRING_9 = v?:"".trim()
    }

    @JsName("getIS_TEMP")
    fun getIS_TEMP():Int{
        return this.INTEGER_6?:0
    }

    @JsName("setIS_TEMP")
    fun setIS_TEMP(v:Int?){
        this.INTEGER_6 = v?:0
    }

    @JsName("getIS_DOWNLOAD")
    fun getIS_DOWNLOAD():Int{
        return this.INTEGER_7?:0
    }

    @JsName("setIS_DOWNLOAD")
    fun setIS_DOWNLOAD(v:Int?){
        this.INTEGER_7 = v?:0
    }

    @JsName("getTIME_ADDED")
    fun getTIME_ADDED():Long{
        return LONG_20?:0L
    }

    @JsName("setTIME_ADDED")
    fun setTIME_ADDED(v:Long){
        LONG_20 = v
    }

   
    @JsName("CreateFileFullName")
    private fun CreateFileFullName(): String {
        return getOBJECT_ID().substring(0, 2) + slash + getOBJECT_ID() + "." + getOBJECT_EXTENSION()
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