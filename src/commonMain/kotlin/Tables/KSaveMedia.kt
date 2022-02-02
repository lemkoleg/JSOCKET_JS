/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import CrossPlatforms.slash
import p_client.Jsocket
import p_jsocket.ANSWER_TYPE
import kotlin.js.JsName


/**
 *
 * @author User
 */
@JsName("KSaveMedia")
class KSaveMedia:ANSWER_TYPE {


    constructor(): super()

    constructor(ans : ANSWER_TYPE): super(ans)

    @OptIn(ExperimentalStdlibApi::class,
        io.ktor.utils.io.core.ExperimentalIoApi::class)
    constructor(ljsocket: Jsocket):super(){
        super.setValue(ljsocket.currentANSWER_TYPE!!)
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