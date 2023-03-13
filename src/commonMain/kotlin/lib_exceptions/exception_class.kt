/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib_exceptions

//import kotlin.js.JsName

/**
 *
 * @author Oleg
 */

//@JsName("exception_class")
class exception_class(eng_text: String, rus_text: String, ukr_text: String) {
    private val Exception_text: Map<String, String> = mapOf("ENG" to eng_text, "RUS" to rus_text, "UKR" to ukr_text)
    fun return_exceptions_text(lang: String): String? {
        return Exception_text[lang]
    }
}