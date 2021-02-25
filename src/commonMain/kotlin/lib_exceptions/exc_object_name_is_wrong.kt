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
@JsName("exc_object_name_is_wrong")
class exc_object_name_is_wrong(file_name: String?) : Exception("Wrong file name: " + file_name?.trim ())