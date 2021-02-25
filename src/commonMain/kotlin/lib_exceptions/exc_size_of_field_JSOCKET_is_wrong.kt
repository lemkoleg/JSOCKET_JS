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

@JsName("exc_size_of_field_JSOCKET_is_wrong")
class exc_size_of_field_JSOCKET_is_wrong(field_name: String?, field_size: Int?) :
    Exception("Fields name - ${field_name?.trim()}; Fields size - ${field_size?.toString()};")