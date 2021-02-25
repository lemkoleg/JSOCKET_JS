/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib_exceptions

import kotlin.js.JsName

/**
 *
 * @author Oleg
 */

@JsName("exc_user_coocki_not_equal_db_coocki")
class exc_user_coocki_not_equal_db_coocki(
    connection_id: Long?,
    connection_coocki: Long?,
    just_do_it_label: Long?
) : Exception(
    "Coockies not equals: user id - ${connection_id?.toString()}; user_coocki - ${connection_coocki?.toString()}; just_do_it_label - ${just_do_it_label?.toString()}")