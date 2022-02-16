@file:Suppress("UNUSED_PARAMETER", "PackageName", "MemberVisibilityCanBePrivate", "unused")

package CrossPlatforms

import com.soywiz.korio.file.VfsFile
import com.soywiz.korio.file.VfsOpenMode
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.AsyncStream
import com.soywiz.korio.stream.asVfsFile
import com.soywiz.korio.stream.openAsync
import io.ktor.util.InternalAPI
import io.ktor.utils.io.core.*
import p_jsocket.Connection
import kotlin.js.JsName

expect val slash:String

//expect fun createPath(fullName: String):Boolean

@JsName("MyFile")
class MyFile(_fullName: String = "", _mode : Int = 0, fileData:ByteArray? = null) {
    @Suppress("unused")
    val fullName: String = _fullName
    private val mode: Int = _mode
    val file: VfsFile = fileData?.openAsync()?.asVfsFile() ?: resourcesVfs[fullName]
    var fileChannel: AsyncStream? = null
     private set


    @JsName("openFile")
    suspend fun openFile():Boolean {
        fileChannel =
        file.open(
            (when (mode) {
                1 -> {
                    VfsOpenMode.READ}
                2 -> {
                    VfsOpenMode.READ}
                3 -> {
                    VfsOpenMode.CREATE_NEW}
                4 -> {
                    VfsOpenMode.APPEND}
                else -> {
                    VfsOpenMode.READ}
            })
        )
        if(fileChannel != null) return true
        return false
    }
    @JsName("createPath")
    fun createPath():Boolean
    {return true}

    @JsName("isExists")
    fun isExists():Boolean
     {return true}

    @JsName("isPath")
    fun isPath():Boolean
    {return true}

    fun creteFile():Boolean
    {return true}

    fun creteFile(size: Long = 0L):Boolean
    {return true}

    @Suppress("unused")
    @JsName("readFile")
    fun readFile(from: Long = 0L, count :Long):ByteArray?
    {return null}

    @JsName("writeFile")
    fun writeFile(srs: ByteArray, from: Long = 0L):Long?
    {return null}

    @InternalAPI
    @ExperimentalIoApi
    @JsName("sendChankOfFile")
    fun sendChankOfFile(from: Long = 0L, count: Long, conn: Connection):Long
    {return 0L}


    @InternalAPI
    @ExperimentalIoApi
    @JsName("receiveChankOfFile")
    fun receiveChankOfFile(from: Long = 0L, count: Long, conn: Connection):Long
    {return 0L}

    @JsName("size")
    fun size():Long
    {return 0L}

    @JsName("renameTo")
    fun renameTo(newName: String):Boolean
    {return true}

    @JsName("delete")
    fun delete():Boolean
    {return true}

    @JsName("close")
    fun close(){}

}