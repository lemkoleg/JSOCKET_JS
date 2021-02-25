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

@JsName("exc_file_already_exists")
class exc_file_already_exists(file_name: String?) : Exception("File already exists: ${file_name?.trim()}" as String?)