@file:Suppress("UNUSED_PARAMETER", "PackageName", "MemberVisibilityCanBePrivate", "unused")

package CrossPlatforms

import kotlin.js.JsName

expect val slash:String
expect val lineSeparator:String

//expect fun createPath(fullName: String):Boolean
// 1-read, 2-re-write 3-random write, 4 - write-append

@JsName("MyFile")
expect class CrossPlatformFile(fullName: String = "", mode : Int = 2) {

    var isInit: Boolean
    @JsName("craete")
    suspend fun create(size: Long)

    @JsName("size")
    suspend fun size():Long

    @JsName("exists")
    suspend fun exists():Boolean

    @JsName("isDirectory")
    suspend fun isDirectory():Boolean

    @JsName("isFile")
    suspend fun isFile():Boolean

    @JsName("delete")
    suspend fun delete():Boolean

    @JsName("readAll")
    suspend fun readAll():ByteArray

    @JsName("read")
    suspend fun read(offset: Long, size: Int):ByteArray

    @JsName("write")
    suspend fun write(data: ByteArray, offset: Long)

    @JsName("appendExtension")
    fun appendExtension(v: String)

    @JsName("renameTo")
    suspend fun renameTo(path: String): Boolean

    @JsName("getFileName")
    fun getFileName(): String

    @JsName("getFileExtension")
    fun getFileExtension(): String

    @JsName("writeLines")
    suspend fun writeLines(s: String)



}