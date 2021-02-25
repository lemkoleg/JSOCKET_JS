package lib_exceptions

import kotlin.js.JsName


@JsName("exc_max_file_size")
class exc_max_file_size(typeOfData: String, size: Long) :
    Exception("Maximum file size exceeded: ${typeOfData.trim()} - $size")