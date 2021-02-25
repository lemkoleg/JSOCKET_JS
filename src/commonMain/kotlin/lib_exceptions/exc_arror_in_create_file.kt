@file:Suppress("ClassName", "unused")

package lib_exceptions

import kotlin.js.JsName


@JsName("exc_arror_in_create_file")
class exc_arror_in_create_file(file_name: String?) : Exception("Error create file: ${file_name?.trim()}")
