

package CrossPlatforms



actual val PLATFORM = "IOS"

actual fun getMyOS(): String {
    return ""
}

actual val slash: String = "/"



actual val lineSeparator: String = " \n"

actual suspend fun getMyDeviceId(): String {
    TODO("Not yet implemented")
}