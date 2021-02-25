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

@JsName("exc_JSOCKET_not_readed")
class exc_JSOCKET_not_readed : Exception {
    constructor(exc: String?) : super("Error on read JSOCKET from socket: ${exc?.trim()}")
    constructor(ex: Exception?) : super("Error on read JSOCKET from socket: ${ex?.toString()}")
}