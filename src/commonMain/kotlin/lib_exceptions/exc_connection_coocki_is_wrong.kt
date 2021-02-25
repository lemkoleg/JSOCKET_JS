/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib_exceptions

import kotlin.js.JsName

/**
 *
 * @author Oleg
 */

@JsName("exc_connection_coocki_is_wrong")
class exc_connection_coocki_is_wrong(conn_coocki: Long?) :
    Exception("Wrong connection coocki: ${conn_coocki?.toString()}" as String?)