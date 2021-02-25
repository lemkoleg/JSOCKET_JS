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

@JsName("exc_wrong_current_positon_of_file")
class exc_wrong_current_positon_of_file(position: Long?) :
    Exception("CUrrent position of file is wrong: ${position?.toString()}")