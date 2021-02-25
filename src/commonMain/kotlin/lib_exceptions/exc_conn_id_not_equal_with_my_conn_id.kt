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

@JsName("exc_conn_id_not_equal_with_my_conn_id")
class exc_conn_id_not_equal_with_my_conn_id(connection_id: Long?) :
    Exception("Connection id not my - ${connection_id?.toString()}" as String?)