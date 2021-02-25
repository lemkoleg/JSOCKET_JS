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

@JsName("exc_field_of_socket_is_empty")
class exc_field_of_socket_is_empty(number_of_field: Int?, size: Int?) : Exception(
    "Field is emprty or wrong format. Number of field - ${number_of_field?.toString()}; Size - ${size?.toString()}" as String?)