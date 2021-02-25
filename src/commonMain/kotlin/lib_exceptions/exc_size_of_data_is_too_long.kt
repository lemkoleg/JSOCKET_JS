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

@JsName("exc_size_of_data_is_too_long")
class exc_size_of_data_is_too_long(size: Int?) :
    Exception("Size is packet is to long. Size: ${size?.toString()}")