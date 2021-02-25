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

@JsName("exc_commands_id_is_wrong")
class exc_commands_id_is_wrong(commands_id: Int?) :
    Exception("Command Id not found: ${commands_id?.toString()}" as String?)