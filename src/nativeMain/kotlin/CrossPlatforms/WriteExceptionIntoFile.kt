package CrossPlatforms

actual fun WriteExceptionIntoFile(exc: Exception, moduleNmae: String) {
    val s = "Error on $moduleNmae: $exc"
    println(s)
}