package CrossPlatforms

import com.soywiz.korio.file.VfsFile
import com.soywiz.korio.file.VfsOpenMode
import com.soywiz.korio.file.baseNameWithoutCompoundExtension
import com.soywiz.korio.file.extension
import com.soywiz.korio.file.std.resourcesVfs


// 1-read, 2-re-write 3-random write, 4 - write-append
actual class CrossPlatformFile actual constructor(fullName: String, mode: Int) {

    val file: VfsFile = resourcesVfs[fullName]

    actual suspend fun create(size: Long) {
        if(file.exists() && file.isFile()){
            file.delete()
        }
        file.open(VfsOpenMode.CREATE)
        file.writeChunk(ByteArray(1), size - 2, true)
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

    actual suspend fun delete():Boolean {
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
        return file.pathInfo.baseNameWithoutCompoundExtension.lowercase()
    }

    actual fun getFileExtension(): String {
        return file.pathInfo.extension.lowercase()
    }

}