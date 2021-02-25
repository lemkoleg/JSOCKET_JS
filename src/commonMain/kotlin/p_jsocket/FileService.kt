@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

import CrossPlatforms.MyFile
import CrossPlatforms.slash
import com.soywiz.klock.DateTime
import com.soywiz.korim.bitmap.Bitmap32
import com.soywiz.korim.color.RGB_555
import com.soywiz.korim.format.ImageEncodingProps
import com.soywiz.korim.format.PNG
import com.soywiz.korim.format.encode
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.asVfsFile
import com.soywiz.korio.stream.openAsync
import io.ktor.util.InternalAPI
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.*
import lib_exceptions.*
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author User
 */

const val CHUNK_SIZE = 1024L * 1000L
const val maxTimeWaitRead = 5000L
val colorFormat = RGB_555
const val SEND_AVATAR_SIZE = AVATARSIZE * 10 * 2


@JsName("FileService")
class FileService(): CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    val FileServiceScope = CoroutineScope(coroutineContext)

    var fIleFullName: String = ""
        private set
    var fIleName: String = ""
        private set
    var fileExtension: String = ""
        private set
    private var TempPath: String = ""
    var OpenMode /* 1-read, 2-re-write 3-random write, 4 - write-append*/: Int = 0
    private var ExpectedFIleSize: Long = 0
    var currentFIleSize: Long = 0L
        private set
    private var CURRENT_CHUNK_SIZE = CHUNK_SIZE
    private var MAZ_FILE_SIZE = 1000 * 1000 * 1024L
    private val AVATAR_MAX_SIZE = AVATARSIZE * 1000L
    private var Chunks: IntArray? = null
    private var NotSendedChunks: Long = 0
    private var EndFIleBytes: Long = 0L
    private var CurrentLoopBytes = 0L
    private var AllBytes = 0L
    private var SendedBytes = 0L
    private var TimeOutTime = 0L
    private val SOCKET_TIMEOUT = 5000L
    private var IsDownloaded = false
    private val IsTryAgain = false
    private var myFile: MyFile? = null

    @ExperimentalUnsignedTypes
    constructor(
        lFIleName: String,
        lFIleExtension: String,
        lExpectedFIleSize: Long,
        mode: Int
    ) : this() {
        if (lFIleName.trim().isEmpty()) {
            throw exc_file_id_is_empty()
        }
        if (lFIleExtension.trim().isEmpty()) {
            throw exc_file_extension_is_empty()
        }
        this.fIleName = DeleteSymbols(lFIleName)
        this.OpenMode = mode
        ExpectedFIleSize = lExpectedFIleSize
        fileExtension = DeleteSymbols(lFIleExtension).toLowerCase()
        this.TempPath = if (mode != 1) {
            "Temp".plus(slash)
        } else {
            ""
        }
        this.fIleFullName =
            TempPath.plus(fIleName.substring(0, 2).plus(slash))
                .plus(fIleName).plus(".").plus(fileExtension)
        if (lExpectedFIleSize == 0L) {
            throw exc_file_size_is_wrong(fIleFullName, lExpectedFIleSize)
        }
        FileServiceScope.launch { open_file_channel() }
    }

    ////////////////////////////////////////////////////////////////////////////
    @ExperimentalUnsignedTypes
    constructor(lFullFIleName: String) : this() {
        fIleFullName = lFullFIleName.trim()
        fileExtension = DeleteSymbols(createFileExtensionFromFullFIleName(fIleFullName))
        fIleName = DeleteSymbols(createFileNameFromName(fIleFullName))
        TempPath = ""
        OpenMode = 1
        FileServiceScope.launch { open_file_channel() }
    }

    private fun DeleteSymbols(InputString: String): String {
        return InputString.replace("/", "").replace(slash, "")
            .replace("'", "").replace("/'", "").replace("..", "").trim()
    }


    private fun createFileExtensionFromFullFIleName(LFileFullName: String): String {
        return try {
            val extension: String = DeleteSymbols(LFileFullName.trim()).substringAfterLast(".")
            if (extension.trim().isEmpty()) {
                throw exc_error_on_create_file_name("")
            }
            extension.toLowerCase()
        } catch (ex: Exception) {
            throw exc_error_on_create_file_name(ex.message)
        }
    }

    private fun createFileNameFromName(LFileFullName: String): String {
        return try {
            val file_name = DeleteSymbols(LFileFullName.trim()).substringBefore(".")
            val extension = DeleteSymbols(LFileFullName.trim()).substringAfterLast(".")
            return file_name
        } catch (ex: Exception) {
            throw exc_error_on_create_file_name(ex.message)
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @ExperimentalUnsignedTypes
    @JsName("open_file_channel")
    suspend fun open_file_channel() {
        if (fIleFullName.trim().isEmpty()) {
            throw exc_file_name_is_empty()
        }
        myFile = MyFile(fIleFullName, OpenMode)
        if (OpenMode == 1) //read
        {
            myFile!!.openFile()
            if (!myFile!!.isExists() || myFile!!.isPath() || myFile!!.size() == 0L) {
                throw exc_file_not_exists()
            }
            ExpectedFIleSize = myFile!!.size()
            IsDownloaded = true
        }
        if (OpenMode == 2) //re-write(download)
        {
            myFile!!.creteFile()
        }
        if (OpenMode == 3) //create and write whis RandomAccessFile (play)
        {
            myFile!!.creteFile(ExpectedFIleSize)
        }
        if (OpenMode == 4) //append
        {
            myFile!!.openFile()
            if (!myFile!!.isExists() || myFile!!.isPath() || myFile!!.size() == 0L) {
                throw exc_file_not_exists()
            }
        }
        currentFIleSize = myFile!!.size()

        /*if (OpenMode == 1 || OpenMode == 3) {
            if (currentFIleSize != ExpectedFIleSize) {
                throw exc_file_current_size_not_eqoual_expected_size(fIleFullName, currentFIleSize, ExpectedFIleSize)
            }
        }*/

        if (OpenMode == 3) {
            val i = (ExpectedFIleSize / CHUNK_SIZE).toInt()
            EndFIleBytes = ExpectedFIleSize % CHUNK_SIZE
            Chunks = if (EndFIleBytes > 0L) {
                IntArray(i + 1)
            } else {
                IntArray(i)
            }
            NotSendedChunks = Chunks!!.size.toLong()
        }
    }

    fun getMyFile(): MyFile? {
        return myFile
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    @InternalAPI
    @JsName("send_chunk_of_file")
    suspend fun send_chunk_of_file(number_of_chunk: Int, conn: Connection) {
        SendedBytes = number_of_chunk * CHUNK_SIZE
        if (OpenMode != 1) {
            throw exc_wrong_operation_for_open_mode()
        }
        if (SendedBytes > myFile!!.size()) {
            throw exc_wrong_current_positon_of_file(number_of_chunk.toLong())
        }
        AllBytes = myFile!!.size()
        CURRENT_CHUNK_SIZE = if (AllBytes - SendedBytes >= CHUNK_SIZE) {
            CHUNK_SIZE
        } else {
            AllBytes - SendedBytes
        }
        AllBytes = SendedBytes
        CurrentLoopBytes = 0L
        SendedBytes = 0L
        TimeOutTime = DateTime.nowUnixLong() + SOCKET_TIMEOUT
        try {
            while (SendedBytes < CURRENT_CHUNK_SIZE && TimeOutTime > DateTime.nowUnixLong()) {
                CurrentLoopBytes =
                    myFile!!.sendChankOfFile(AllBytes + SendedBytes, CURRENT_CHUNK_SIZE - SendedBytes, conn)
                if (CurrentLoopBytes >= 0L) {
                    if (CurrentLoopBytes > 0L) {
                        SendedBytes += CurrentLoopBytes
                        CurrentLoopBytes = 0L
                        TimeOutTime = DateTime.nowUnixLong() + SOCKET_TIMEOUT
                    } else {
                        yield()
                    }
                } else {
                    break
                }
            }
        } catch (e: IOException) {
            conn.close()
            myFile?.close()
        }
    }

    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    @InternalAPI
    @JsName("send_file_full")
    suspend fun send_file_full(lCurrentPosition: Long, conn: Connection): Boolean {
        if (OpenMode != 1) {
            throw exc_wrong_operation_for_open_mode()
        }
        CurrentLoopBytes = 0L
        SendedBytes = lCurrentPosition
        AllBytes = myFile!!.size()
        if (SendedBytes > AllBytes) {
            throw exc_wrong_current_positon_of_file(lCurrentPosition)
        }
        return try {
            TimeOutTime = DateTime.nowUnixLong() + SOCKET_TIMEOUT
            while (SendedBytes < AllBytes && TimeOutTime > DateTime.nowUnixLong()) {
                CurrentLoopBytes = myFile!!.sendChankOfFile(SendedBytes, AllBytes - SendedBytes, conn)
                if (CurrentLoopBytes >= 0L) {
                    if (CurrentLoopBytes > 0L) {
                        SendedBytes += CurrentLoopBytes
                        CurrentLoopBytes = 0L
                        TimeOutTime = DateTime.nowUnixLong() + SOCKET_TIMEOUT
                    } else {
                        yield()
                    }
                } else {
                    break
                }
            }
            TimeOutTime = DateTime.nowUnixLong() + SOCKET_TIMEOUT
            try {
                while (TimeOutTime > DateTime.nowUnixLong()) {
                    if (!conn.isConnected()) {
                        return true
                    }
                    yield()
                }
                false
            } catch (e: IOException) {
                true
            }
        } catch (e: IOException) {
            false
        } finally {
            conn.close()
            myFile?.close()
        }
    }
    ///////////////////////////////////////////////////////////////////////////////
    fun getMyFileChannel():MyFile?{
        return myFile
    }
    ////////////////////////////////////////////////////////////////////////////////

    @ExperimentalTime
    @ExperimentalStdlibApi
    @InternalAPI
    @DangerousInternalIoApi
    @JsName("write_chunk_of_file")
    suspend fun write_chunk_of_file(lCurrentChunk: Int, conn: Connection): Boolean {
        if (OpenMode != 3) {
            throw exc_wrong_operation_for_open_mode()
        }
        AllBytes = lCurrentChunk * CHUNK_SIZE
        if (lCurrentChunk + 1 > Chunks!!.size) {
            throw exc_wrong_current_positon_of_file(lCurrentChunk.toLong())
        }
        CurrentLoopBytes = 0L
        SendedBytes = 0L
        CURRENT_CHUNK_SIZE = if (Chunks!!.size == lCurrentChunk + 1) {
            EndFIleBytes
        } else {
            CHUNK_SIZE
        }
        TimeOutTime = DateTime.nowUnixLong() + SOCKET_TIMEOUT
        return try {
            if (Chunks!![lCurrentChunk] == 0) {
                while (SendedBytes < CURRENT_CHUNK_SIZE && TimeOutTime > DateTime.nowUnixLong()) {
                    CurrentLoopBytes = myFile!!.receiveChankOfFile(
                        AllBytes + SendedBytes,
                        CURRENT_CHUNK_SIZE - SendedBytes,
                        conn
                    )
                    if (CurrentLoopBytes >= 0L) {
                        if (CurrentLoopBytes > 0L) {
                            SendedBytes += CurrentLoopBytes
                            CurrentLoopBytes = 0L
                            TimeOutTime = DateTime.nowUnixLong() + SOCKET_TIMEOUT
                        } else {
                            yield()
                        }
                    } else {
                        break
                    }
                }
                if (SendedBytes == CURRENT_CHUNK_SIZE) {
                    Chunks!![lCurrentChunk] = 1
                    NotSendedChunks -= 1
                }
            } else {
                while (SendedBytes < CURRENT_CHUNK_SIZE && TimeOutTime > DateTime.nowUnixLong()) {
                    CurrentLoopBytes = conn.skip(maxTimeWaitRead)
                    if (CurrentLoopBytes >= 0L) {
                        if (CurrentLoopBytes > 0L) {
                            SendedBytes += CurrentLoopBytes
                            CurrentLoopBytes = 0L
                            TimeOutTime = DateTime.nowUnixLong()
                        } else {
                            yield()
                        }
                    } else {
                        break
                    }
                }
            }
            IsDownloaded = NotSendedChunks == 0L
            IsDownloaded
        } catch (e: IOException) {
            conn.close()
            myFile?.close()
            false
        }
    }

    @InternalAPI
    @ExperimentalStdlibApi
    @DangerousInternalIoApi
    @JsName("write_file_full")
    suspend fun write_file_full(conn: Connection): Boolean {
        if (OpenMode == 1 || OpenMode == 3) {
            throw exc_wrong_operation_for_open_mode()
        }
        return try {
            SendedBytes = myFile!!.size()
            CurrentLoopBytes = 0L
            TimeOutTime = DateTime.nowUnixLong() + SOCKET_TIMEOUT
            while (SendedBytes < ExpectedFIleSize && TimeOutTime > DateTime.nowUnixLong()) {
                CurrentLoopBytes =
                    myFile!!.receiveChankOfFile(SendedBytes, ExpectedFIleSize - SendedBytes, conn)
                if (CurrentLoopBytes >= 0L) {
                    if (CurrentLoopBytes > 0L) {
                        SendedBytes += CurrentLoopBytes
                        CurrentLoopBytes = 0L
                        TimeOutTime = DateTime.nowUnixLong() + SOCKET_TIMEOUT
                    } else {
                        yield()
                    }
                } else {
                    break
                }
            }
            IsDownloaded = myFile!!.size() == ExpectedFIleSize
            IsDownloaded
        } catch (e: IOException) {
            IsDownloaded
        } finally {
            conn.close()
            myFile?.close()
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @JsName("SaveDownloadedFile")
    fun SaveDownloadedFile(): Boolean {
        if (OpenMode == 1) {
            throw exc_wrong_operation_for_open_mode()
        }
        if (IsDownloaded) {
            if (myFile != null) {
                myFile!!.close()
            }
            val sourceFile = MyFile(fIleFullName)
            if (sourceFile.isExists() && !sourceFile.isPath()) {
                val f: String = fIleFullName.replace("Temp$slash", "")
                val d = MyFile(f)
                if (d.isExists() && !sourceFile.isPath()) {
                    if (OpenMode == 4) {
                        d.delete()
                    } else {
                        return false
                    }
                }
                if (!sourceFile.renameTo(f)) {
                    sourceFile.delete()
                }
            }
        }
        return IsDownloaded
    }

    @JsName("DeleteDownloadedFile")
    fun DeleteDownloadedFile() {
        if (OpenMode == 1) {
            throw exc_wrong_operation_for_open_mode()
        }
        try {
            if (myFile != null) {
                myFile!!.close()
            }
            val f: String = fIleFullName.replace("Temp\\\\", "")
            val d = MyFile(f)
            if (d.isExists() && !d.isPath()) {
                d.delete()
            }
        } catch (e: Exception) {
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @JsName("ReturnNextNotDownloadedChankNumber")
    fun ReturnNextNotDownloadedChankNumber(currentChank: Int): Int {
        var current_chank = currentChank
        if (IsDownloaded) {
            return -1
        }
        var rec = false
        if (current_chank >= Chunks!!.size) {
            current_chank = 0
            rec = true
        }
        while (Chunks!!.size >= current_chank) {
            if (Chunks!![current_chank] == 0) {
                return current_chank
            } else {
                current_chank += 1
                if (current_chank >= Chunks!!.size && !rec) {
                    current_chank = 0
                    rec = true
                }
            }
        }
        IsDownloaded = true
        return -1
    }

    fun ReturnFirstNotDownloadedChankNumber(): Int {
        var x = 0
        while (Chunks!!.size >= x) {
            if (Chunks!![x] == 0) {
                return x
            }
            x++
        }
        return -1
    }

    @JsName("IsDownLoadedChank")
    fun IsDownLoadedChank(current_chank: Int): Boolean {
        if (current_chank + 1 > Chunks!!.size) {
            throw exc_wrong_current_positon_of_file(current_chank.toLong())
        }
        return Chunks!![current_chank] != 0
    }

    fun IsDownloaded(): Boolean {
        return IsDownloaded
    }

    ////////////////////////////////////////////////////////////////////////////////
    @JsName("getImmageAvatarFromFileName")
    suspend fun getImmageAvatarFromFileName(lFullFileName: String): ByteArray? {
        val fileName = resourcesVfs[lFullFileName]
        if (!fileName.exists() || fileName.isDirectory()) {
            throw exc_fail_picture_extension()
        }
        if (fileExtension.trim().isEmpty()) {
            fileExtension = createFileExtensionFromFullFIleName(lFullFileName)
        }
        if (!PictureSet.contains(fileExtension)) {
            throw exc_fail_picture_extension()
        }
        if(fileName.size() > AVATAR_MAX_SIZE)
          {throw exc_max_file_size("Avatar", fileName.size())}

        val l = fileName.readAll()
        if(l.size <= SEND_AVATAR_SIZE){
            return l
        }
        val file = l.openAsync().asVfsFile()
        val image32 = file.readBitmap().toBMP32()
        return getImmageAvatarFromBitmap32(image32)

    }

    @JsName("getImmageAvatarFromByteArray")
    suspend fun getImmageAvatarFromByteArray(imageData: ByteArray): ByteArray? {
        if(imageData.size > AVATAR_MAX_SIZE)
           {throw exc_max_file_size("Avatar", imageData.size.toLong())}
        if(imageData.size <= SEND_AVATAR_SIZE)
           {return imageData}
        val fileName = imageData.openAsync().asVfsFile()
        val image32 = fileName.readBitmap().toBMP32()
        return getImmageAvatarFromBitmap32(image32)
    }

    @JsName("getImmageAvatarFormBitmap32")
    private suspend fun getImmageAvatarFromByteArrayWithKoord(imageData: ByteArray,
                                                              x: Int,
                                                              y: Int,
                                                              w: Int,
                                                              h: Int): ByteArray? {
        if(imageData.size > AVATAR_MAX_SIZE)
          {throw exc_max_file_size("Avatar", imageData.size.toLong())}
        val fileName = imageData.openAsync().asVfsFile()
        var image32 = fileName.readBitmap().toBMP32()
        image32 = image32.copySliceWithSize(x = x, y = y, width = w, height = h)
        return getImmageAvatarFromBitmap32(image32)
    }

    @JsName("getImmageAvatarFormBitmap32")
    private suspend fun getImmageAvatarFromBitmap32(imageData: Bitmap32): ByteArray? {
        var bb: ByteArray? = null
        var image32 = imageData
        val ex = ImageEncodingProps("",1.0)
        val sliceSIze = SEND_AVATAR_SIZE * 1.4
        //bb = image32.encode(PNG, ex)
        bb = image32.encode(PNG)
        if (bb.size <= SEND_AVATAR_SIZE) return bb
        else bb = null
        var x = 0
        while (x < 1000 ) {
            x += 1
            image32 = image32.mipmap(1)
            bb = image32.encode(PNG)
            if (bb.size <= sliceSIze) break
        }
        if (bb != null) {
            if (bb.size <= SEND_AVATAR_SIZE) return bb

            val koef = 0.98F
            val avatar = SEND_AVATAR_SIZE.toFloat()
            var im_width = (image32.width * koef).toInt()
            var im_heigth = (image32.height * koef).toInt()
            x = 0
            while (x < 1000 ) {
                x += 1
                image32 = image32.copySliceWithSize(
                    (image32.width - im_width) / 2,
                    (image32.height - im_heigth) / 2,
                    im_width,
                    im_heigth
                )
                bb = image32.encode(PNG)
                if (bb.size <= SEND_AVATAR_SIZE) break
                im_width = (image32.width * koef).toInt()
                im_heigth = (image32.height * koef).toInt()
            }
        }
        return bb
    }

    /*@JsName("getImmageAvatarFormBitmap32")
    private suspend fun getImmageAvatarFormBitmap32(imageData: Bitmap32): ByteArray? {
        var bb: ByteArray? = null
        var image32: Bitmap32 = imageData
        val ex = ImageEncodingProps("",1.0)
        //bb = image32.encode(PNG, ex)
        bb = image32.encode(PNG)
        if (bb.size <= SEND_AVATAR_SIZE) return bb
        else bb = null
        var x = 0
        while (x < 1000 ) {
            x += 1
            image32 = image32.mipmap(1)
            bb = image32.encode(PNG)
            println("bb.size ${bb.size}")
            if (bb.size <= (SEND_AVATAR_SIZE * 2)) break
        }
        if (bb != null) {
            if (bb.size <= SEND_AVATAR_SIZE) return bb

            val koef = 0.95F
            val avatar = SEND_AVATAR_SIZE.toFloat()
            var percent = (bb.size / avatar)
            var im_width = (image32.width * koef).toInt()
            var im_heigth = (image32.height * koef).toInt()
            x = 0
            while (x < 1000 ) {
                x += 1
                image32 = image32.copySliceWithSize(
                    (image32.width - im_width) / 2,
                    (image32.height - im_heigth) / 2,
                    im_width,
                    im_heigth
                )
                bb = image32.encode(PNG)
                println("bb.size2 ${bb.size}")
                if (bb.size <= SEND_AVATAR_SIZE) break
                im_width = (image32.width * koef).toInt()
                im_heigth = (image32.height * koef).toInt()
            }
        }
        return bb
    }*/

    /*@JsName("getImmageAvatarFormBitmap32")
    private suspend fun getImmageAvatarFormBitmap32(imageData: Bitmap32): ByteArray? {
        var bb: ByteArray? = null
        var image32: Bitmap32 = imageData
        val ex = ImageEncodingProps("",1.0)
        bb = image32.encode(PNG, ex)
        if (bb.size <= AVATAR_SIZE) return bb
        var image16 = Bitmap16(width = image32.width, height = image32.height, format = colorFormat, premultiplied = false)
        image32.forEach { _, x, y ->
            image16.setRgba(x = x, y = y, v = image32.getRgba(x = x, y = y))
        }
        image32 = image16.toBMP32()
        FileServiceScope.launch {
            var im_width = image32.width
            var im_heigth = image32.height
            var koef: Float
            val avatar = AVATAR_SIZE.toFloat()
            var percent = (bb!!.size / avatar)
            while (true) {
                if (bb!!.size <= AVATAR_SIZE) break
                percent = (bb!!.size / avatar)
                koef = if (percent > 2F) {
                    1F
                } else {
                    0.99F
                }
                if (koef == 1F) {
                    image32 = image32.mipmap(1)

                } else {
                    im_width = (im_width * koef).toInt()
                    im_heigth = (im_heigth * koef).toInt()
                    image32 = image32.copySliceWithSize(
                        (image32.width - im_width) / 2,
                        (image32.height - im_heigth) / 2,
                        im_width,
                        im_heigth
                    )

                }
                im_width = image32.width
                im_heigth = image32.height
                image16 =
                    Bitmap16(width = im_width, height = im_heigth, format = colorFormat, premultiplied = false)
                image32.forEach { _, x, y ->
                    image16.setRgba(x = x, y = y, v = image32.getRgba(x = x, y = y))
                }
                image32 = image16.toBMP32()
                bb = image32.encode(PNG, ex)
                break

            }

        }.join()
        return bb
    }*/

    /////////////////////////////////////////////////////////////////////////////////////
    fun close() {
        if (myFile != null) {
            myFile!!.close()
        }
        if (OpenMode != 1) {
            val f = MyFile(fIleFullName)
            if (f.isExists() && !f.isPath()) {
                f.delete()
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    fun finalize() {
        if (myFile != null) {
            myFile!!.close()
        }
        if (OpenMode != 1) {
            val f = MyFile(fIleFullName)
            if (f.isExists() && !f.isPath()) {
                f.delete()
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////
}