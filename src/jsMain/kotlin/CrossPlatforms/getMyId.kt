package CrossPlatforms

import kotlinx.coroutines.await
import kotlin.js.Promise


//@JsModule("fingerprintjs2")
//@JsNonModule

actual val PLATFORM: String = "JS"

external fun require(module: String): dynamic


actual suspend fun getMyDeviceId(): String {
    val finger = require("fingerprintjs2")
    val finger2 = require("getBrowserFinger")
    var t = ""
    val f = finger2.getBrowserFinger(finger) as Promise<*>
    f.await()
    f.then{ v: Any? -> t = v as String}
    return t
}


@JsName("getMyOS")
actual fun getMyOS(): String {
    return js("return window.navigator.userAgent") as String
}

actual val slash: String = "/"
