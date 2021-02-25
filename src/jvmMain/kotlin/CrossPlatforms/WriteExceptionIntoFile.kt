package CrossPlatforms

actual fun WriteExceptionIntoFile(exc: Exception, moduleNmae: String) {
    val s = "Error on $moduleNmae: $exc"
    println(s)

    /*println("Error on $moduleNmae: ${System.lineSeparator()}")
    val e = exc.stackTrace.iterator()
    e.forEach {v -> println("$v${System.lineSeparator()}")}*/
}