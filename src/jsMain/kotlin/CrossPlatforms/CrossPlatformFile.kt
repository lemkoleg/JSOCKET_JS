package CrossPlatforms


actual class CrossPlatformFile actual constructor(fullName: String, mode: Int) {


    actual suspend fun create(size: Long){
        TODO("Not yet implemented")
    }

    actual suspend fun size():Long {
        TODO("Not yet implemented")
    }

    actual suspend fun exists():Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun isDirectory():Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun isFile():Boolean {
        TODO("Not yet implemented")
    }

    actual suspend  fun delete():Boolean {
        TODO("Not yet implemented")
    }

    actual suspend fun readAll():ByteArray {
        TODO("Not yet implemented")
    }

    actual suspend fun read(offset: Long, size: Int):ByteArray {
        TODO("Not yet implemented")
    }

    actual suspend fun write(data: ByteArray, offset: Long) {
        TODO("Not yet implemented")
    }

    actual fun appendExtension(v: String) {
        TODO("Not yet implemented")
    }

    actual suspend fun renameTo(path: String): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getFileName(): String {
        TODO("Not yet implemented")
    }

    actual fun getFileExtension(): String {
        TODO("Not yet implemented")
    }


}