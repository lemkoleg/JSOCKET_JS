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

@JsName("exc_file_size_is_wrong")
class exc_file_size_is_wrong(file_name: String?, file_size: Long?) :
    Exception("Wrong file size - ${file_name?.trim()}: ${file_size?.toString()}")