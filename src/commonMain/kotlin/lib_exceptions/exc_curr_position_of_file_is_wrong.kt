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

@JsName("exc_curr_position_of_file_is_wrong")
class exc_curr_position_of_file_is_wrong(file_name: String?, curr_pos: Long?) :
    Exception("Current position of file ${file_name?.trim()} is wrong: ${curr_pos?.toString()}" as String?)