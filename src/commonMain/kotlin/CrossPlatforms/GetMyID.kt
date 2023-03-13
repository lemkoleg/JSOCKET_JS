/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("PackageName")

package CrossPlatforms

//import kotlin.js.JsName


//@JsName("getMyDeviceId")
expect suspend fun getMyDeviceId(): String
//@JsName("getMyOS")
expect fun getMyOS(): String

