/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib_exceptions

import kotlin.js.JsName

/**
 *
 * @author User
 */

@JsName("exc_error_on_create_file_name")
class exc_error_on_create_file_name(file_name: String?) :
    Exception("Wrong file name - ${file_name?.trim()}" as String?)