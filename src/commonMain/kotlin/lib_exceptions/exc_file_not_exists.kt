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

@JsName("exc_file_not_exists")
class exc_file_not_exists : Exception {
    constructor() : super("File not exists")
    constructor(file_name: String?) : super("File not exists: ${file_name?.trim()}")
}