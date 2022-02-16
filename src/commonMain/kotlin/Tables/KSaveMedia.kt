/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import CrossPlatforms.slash
import com.soywiz.klock.DateTime
import kotlin.js.JsName


/**
 *
 * @author User
 */
@JsName("KSaveMedia")
class KSaveMedia{

    private var OBJECT_ID: String = ""
    private var CONNECTION_ID: String = ""
    private var AVATAR_ID: String = ""
    private var OBJECT_OWNER: String = ""
    private var OBJECT_NAME: String = ""
    private var OBJECT_SIZE: Int = 0
    private var OBJECT_LENGTH_SECONDS: Int = 0
    private var OBJECT_SERVER: String = ""
    private var OBJECT_PROFILE_STRING: String = ""
    private var OBJECT_LINK: String = ""
    private var OBJECT_EXTENSION: String = ""
    private var AVATAR_LINK: String = ""
    private var AVATAR_SERVER: String = ""
    private var ORIGINAL_AVATAR_SIZE: Int = 0
    private var SMALL_AVATAR: ByteArray? = null
    private var BIG_AVATAR: ByteArray? = null
    private var IS_TEMP: Int = 0
    private var IS_DOWNLOAD: Int = 0
    private var LAST_USED: Long = 0


    private constructor()

    constructor(L_OBJECT_ID: String,
                L_AVATAR_ID: String?,
                L_OBJECT_OWNER: String?,
                L_OBJECT_NAME: String,
                L_OBJECT_SIZE: Int,
                L_OBJECT_LENGTH_SECONDS: Int,
                L_OBJECT_SERVER: String,
                L_OBJECT_PROFILE_STRING: String,
                L_OBJECT_LINK: String,
                L_OBJECT_EXTENSION: String,
                L_AVATAR_LINK: String?,
                L_AVATAR_SERVER: String?,
                L_ORIGINAL_AVATAR_SIZE: Int?,
                L_SMALL_AVATAR: ByteArray?,
                L_BIG_AVATAR: ByteArray?,
                L_IS_TEMP: Int,
                L_IS_DOWNLOAD: Int,
                L_LAST_USED: Long){

         OBJECT_ID = L_OBJECT_ID
         AVATAR_ID = L_AVATAR_ID?:""
         OBJECT_OWNER = L_OBJECT_OWNER?:""
         OBJECT_NAME = L_OBJECT_NAME
         OBJECT_SIZE = L_OBJECT_SIZE
         OBJECT_LENGTH_SECONDS = L_OBJECT_LENGTH_SECONDS
         OBJECT_SERVER = L_OBJECT_SERVER
         OBJECT_PROFILE_STRING = L_OBJECT_PROFILE_STRING
         OBJECT_LINK = L_OBJECT_LINK
         OBJECT_EXTENSION = L_OBJECT_EXTENSION
         AVATAR_LINK = L_AVATAR_LINK?:""
         AVATAR_SERVER = L_AVATAR_SERVER?:""
         ORIGINAL_AVATAR_SIZE = L_ORIGINAL_AVATAR_SIZE?:0
         SMALL_AVATAR = L_SMALL_AVATAR
         BIG_AVATAR = L_BIG_AVATAR
         IS_TEMP = L_IS_TEMP
         IS_DOWNLOAD = L_IS_DOWNLOAD
         LAST_USED = L_LAST_USED
        
    }

    constructor(kMessege : KMessege): this(kMessege.getOBJECT_ID(),
                                           kMessege.getOBJECT_AVATAR_ID(),
                                           kMessege.getOBJECT_OWNER(),
                                           kMessege.getOBJECT_NAME(),
                                           kMessege.getOBJECT_SIZE(),
                                           kMessege.getOBJECT_LENGTH_SECONDS(),
                                           kMessege.getOBJECT_SERVER(),
                                           kMessege.getOBJECT_PROFILE_STRING(),
                                           kMessege.getOBJECT_LINK(),
                                           kMessege.getOBJECT_EXTENSION(),
                                           kMessege.getAVATAR_LINK(),
                                           kMessege.getAVATAR_SERVER(),
                                           kMessege.getORIGINAL_AVATAR_SIZE().toInt(),
                                           kMessege.BLOB_1,
                                           null,
                                           1,
                                           1,
                                           DateTime.nowUnixLong()
     )

    @JsName("getOBJECT_ID")
    fun getOBJECT_ID():String{
        return OBJECT_ID
    }

    @JsName("setOBJECT_ID")
    fun setOBJECT_ID(v:String){
        OBJECT_ID = v
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID():String{
        return AVATAR_ID
    }

    @JsName("setAVATAR_ID")
    fun setAVATAR_ID(v:String){
        AVATAR_ID = v
    }

    @JsName("getOBJECT_OWNER")
    fun getOBJECT_OWNER():String{
        return OBJECT_OWNER
    }

    @JsName("setOBJECT_OWNER")
    fun setOBJECT_OWNER(v:String){
        OBJECT_OWNER = v
    }

    @JsName("getOBJECT_NAME")
    fun getOBJECT_NAME():String{
        return OBJECT_NAME
    }

    @JsName("setOBJECT_NAME")
    fun setOBJECT_NAME(v:String){
        OBJECT_NAME = v
    }

    @JsName("getOBJECT_SIZE")
    fun getOBJECT_SIZE():Int{
        return OBJECT_SIZE
    }

    @JsName("setOBJECT_SIZE")
    fun setOBJECT_SIZE(v:Int){
        OBJECT_SIZE = v
    }

    @JsName("getOBJECT_LENGTH_SECONDS")
    fun getOBJECT_LENGTH_SECONDS():Int{
        return OBJECT_LENGTH_SECONDS
    }

    @JsName("setOBJECT_LENGTH_SECONDS")
    fun setOBJECT_LENGTH_SECONDS(v:Int){
        OBJECT_LENGTH_SECONDS = v
    }

    @JsName("getOBJECT_SERVER")
    fun getOBJECT_SERVER():String{
        return OBJECT_SERVER
    }

    @JsName("setOBJECT_SERVER")
    fun setOBJECT_SERVER(v:String){
        OBJECT_SERVER = v
    }

    @JsName("getOBJECT_PROFILE_STRING")
    fun getOBJECT_PROFILE_STRING():String{
        return OBJECT_PROFILE_STRING
    }

    @JsName("setOBJECT_PROFILE_STRING")
    fun setOBJECT_PROFILE_STRING(v:String){
        OBJECT_PROFILE_STRING = v
    }

    @JsName("getOBJECT_LINK")
    fun getOBJECT_LINK():String{
        return OBJECT_LINK
    }

    @JsName("setOBJECT_LINK")
    fun setOBJECT_LINK(v:String){
        OBJECT_LINK = v
    }

    @JsName("getOBJECT_EXTENSION")
    fun getOBJECT_EXTENSION():String{
        return OBJECT_EXTENSION
    }

    @JsName("setOBJECT_EXTENSION")
    fun setOBJECT_EXTENSION(v:String){
        OBJECT_EXTENSION = v
    }

    @JsName("getAVATAR_LINK")
    fun getAVATAR_LINK():String{
        return AVATAR_LINK
    }

    @JsName("setAVATAR_LINK")
    fun setAVATAR_LINK(v:String?){
        AVATAR_LINK = v?:""
    }

    @JsName("getAVATAR_SERVER")
    fun getAVATAR_SERVER():String{
        return AVATAR_SERVER
    }

    @JsName("setAVATAR_SERVER")
    fun setAVATAR_SERVER(v:String?){
        AVATAR_SERVER = v?:""
    }

    @JsName("getORIGINAL_AVATAR_SIZE")
    fun getORIGINAL_AVATAR_SIZE():Int{
        return ORIGINAL_AVATAR_SIZE
    }

    @JsName("setORIGINAL_AVATAR_SIZE")
    fun setORIGINAL_AVATAR_SIZE(v:Int?){
        ORIGINAL_AVATAR_SIZE = v?:0
    }

    @JsName("getSMALL_AVATAR")
    fun getSMALL_AVATAR():ByteArray?{
        return SMALL_AVATAR
    }

    @JsName("setSMALL_AVATAR")
    fun setSMALL_AVATAR(v:ByteArray?){
        SMALL_AVATAR = v
    }

    @JsName("getBIG_AVATAR")
    fun getBIG_AVATAR():ByteArray?{
        return BIG_AVATAR
    }

    @JsName("setBIG_AVATAR")
    fun setBIG_AVATAR(v:ByteArray?){
        BIG_AVATAR = v
    }

    @JsName("getIS_TEMP")
    fun getIS_TEMP():Int{
        return IS_TEMP
    }

    @JsName("setIS_TEMP")
    fun setIS_TEMP(v:Int?){
        IS_TEMP = v?:1
    }

    @JsName("getIS_DOWNLOAD")
    fun getIS_DOWNLOAD():Int{
        return IS_DOWNLOAD
    }

    @JsName("setIS_DOWNLOAD")
    fun setIS_DOWNLOAD(v:Int?){
        IS_DOWNLOAD = v?:1
    }

    @JsName("getLAST_USED")
    fun getLAST_USED():Long{
        return LAST_USED
    }

    @JsName("setLAST_USED")
    fun setTIME_ADDED(v:Long){
        LAST_USED = v
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