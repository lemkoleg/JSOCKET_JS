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

@JsName("exc_error_on_write_file")
class exc_error_on_write_file : Exception{
    private constructor() : super()
    constructor(fname: String?, exc: String?) : super("Can't write file:${fname?.trim()} - ${exc?.trim()}")
}