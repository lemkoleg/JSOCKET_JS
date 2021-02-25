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

@JsName("exc_user_coocki_not_found")
class exc_user_coocki_not_found(connection_id: Long?) :
    Exception("User not found: ${connection_id?.toString()}")