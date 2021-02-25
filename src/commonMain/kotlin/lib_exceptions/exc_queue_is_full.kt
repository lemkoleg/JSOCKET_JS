package lib_exceptions

import kotlin.js.JsName

@JsName("exc_queue_is_full")
class exc_queue_is_full(whatQueue: String): Exception(
    "Queue of $whatQueue is full"
)