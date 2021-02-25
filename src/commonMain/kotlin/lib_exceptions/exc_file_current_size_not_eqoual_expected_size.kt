/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib_exceptions

import kotlin.js.JsName


@JsName("exc_file_current_size_not_eqoual_expected_size")
class exc_file_current_size_not_eqoual_expected_size(
    file_name: String?,
    real_file_size: Long?,
    expected_file_size: Long?
) : Exception(
    "File name - ${file_name?.trim()}; Real file size - ${real_file_size?.toString()}; Expected file size - ${expected_file_size?.toString()}" as String?)