package CrossPlatforms

actual fun WriteExceptionIntoFile(exc: Exception, moduleNmae: String) {
    val s = "Error on $moduleNmae: $exc"
    PrintInformation.PRINT_INFO(s)
}