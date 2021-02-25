/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib_exceptions

import io.ktor.util.InternalAPI
import kotlin.js.JsName


/**
 *
 * @author Oleg
 */
@JsName("exc_universal_exception")
object exc_universal_exception {
    @InternalAPI
    fun returnException(number_of_exception: Int, lang: String): String {
        return return_exceptions_text(number_of_exception, lang)?:""
    }
}