@file:Suppress("PackageName", "FunctionName")

package CrossPlatforms

import kotlin.js.JsName

@JsName("WriteExceptionIntoFile")
expect fun WriteExceptionIntoFile(exc: Exception, moduleNmae: String)
