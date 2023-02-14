package CrossPlatforms


import com.soywiz.korio.file.VfsFile
import com.soywiz.korio.file.VfsOpenMode
import com.soywiz.korio.file.extension
import com.soywiz.korio.file.fullName
import com.soywiz.korio.file.std.localVfs
import com.soywiz.korio.stream.AsyncStream
import com.soywiz.korio.stream.writeString

// 1-read, 2-re-write 3-random write, 4 - write-append
actual class CrossPlatformFile actual constructor(fullName: String, mode: Int) {

    val file: VfsFile = localVfs(fullName)
    var chnnel: AsyncStream? = null
    val mod = mode
    actual var isInit = false

    actual suspend fun create(size: Long){
        chnnel = file.open(when(mod){
            1 -> VfsOpenMode.READ
            2, 3 -> {VfsOpenMode.CREATE_NEW
            }
            4 -> VfsOpenMode.WRITE
            else -> VfsOpenMode.CREATE
        })
        when(mod){
            2, 3 -> {
                val b = ByteArray(1)
                file.writeChunk(b, (size - 1))
            }
        }
        isInit = true

    }

    actual suspend fun size():Long {
        return file.size()
    }

    actual suspend fun exists():Boolean {
        return file.exists()
    }

    actual suspend fun isDirectory():Boolean {
        return file.isDirectory()
    }

    actual suspend fun isFile():Boolean {
        return file.isFile()
    }

    actual suspend  fun delete():Boolean {
        return file.delete()
    }

    actual suspend fun readAll():ByteArray {
        return file.readAll()
    }

    actual suspend fun read(offset: Long, size: Int):ByteArray {
        return file.readChunk(offset, size)
    }

    actual suspend fun write(data: ByteArray, offset: Long) {
        file.writeChunk(data, offset)
    }

    actual fun appendExtension(v: String) {
        file.appendExtension(v)
    }

    actual suspend fun renameTo(path: String): Boolean {
        return file.renameTo(path)
    }

    actual fun getFileName(): String {
        return file.fullName
    }

    actual fun getFileExtension(): String {
        return file.extension
    }

    actual suspend fun writeLines(s: String) {
        chnnel!!.setPosition(file.size())
        chnnel!!.writeString(s + lineSeparator)
    }
}