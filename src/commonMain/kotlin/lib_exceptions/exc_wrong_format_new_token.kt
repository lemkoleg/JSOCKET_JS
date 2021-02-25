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

@JsName("exc_wrong_format_new_token")
class exc_wrong_format_new_token(new_token: Long?) :
    Exception("New token not in valid diapazone: ${new_token?.toString()}")