package lib_exceptions

interface exception_names {

    var class_name: String

    var function_name: String

    val exception_name: String?

    val exception_text: String?

    val exception_status : String // 0 - system exception; 1 - don''t fix; 2 - fixed only file; 3 - fixed only screen; 4 - fixed both;';
}
