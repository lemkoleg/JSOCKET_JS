/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_client

//import kotlin.js.JsName


/**
 *
 * @author User
 */


//@JsName("OnRequestListener")
interface OnRequestListener{

    //@JsName("startLoading")
    var startLoading: (() -> Any?)

    //@JsName("finishLoading")
    var finishLoading : ((v: Any?) -> Any?)
}