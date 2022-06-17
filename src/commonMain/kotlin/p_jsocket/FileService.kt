@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

import CrossPlatforms.CrossPlatformFile
import CrossPlatforms.MyCondition
import CrossPlatforms.slash
import Tables.KBigAvatar
import Tables.KSaveMedia
import Tables.SAVE_MEDIA
import co.touchlab.stately.concurrency.AtomicInt
import co.touchlab.stately.concurrency.value
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korim.bitmap.Bitmap32
import com.soywiz.korim.color.RGB_555
import com.soywiz.korim.format.ImageEncodingProps
import com.soywiz.korim.format.PNG
import com.soywiz.korim.format.encode
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.asVfsFile
import com.soywiz.korio.stream.openAsync
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_client.CLIENT_JSOCKET_POOL
import p_client.Jsocket
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author User
 */

val colorFormat = RGB_555


@JsName("FileService")
@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class FileService(jsocket: Jsocket? = null) {


    private val FileServiceLock = Mutex()

    private val CurrentChunkReceiveFile = AtomicInt(0)

    private val IsInterrupted = AtomicInt(0)

    val condition: MyCondition = MyCondition()


    var file: CrossPlatformFile? = null

    private var IsDownloaded = false

    val command: Int = jsocket?.just_do_it ?: 0

    val SELF_Jsocket: Jsocket? = if (jsocket == null) {
        null
    } else {
        var j: Jsocket? = CLIENT_JSOCKET_POOL.removeFirstOrNull()
        if (j == null) {
            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                println("CLIENT_JSOCKET_POOL is emprty")
            }
            j = Jsocket()
            j!!.set_value(jsocket)
        }
        j
    }

    val fIleName: String =
        if (command.equals(1011000007) || command.equals(1011000024)) { // SAVE SAVE_MEDIA (DOWNLOAD FILE); PLAY MEDIA;
            if (SELF_Jsocket!!.value_par4.isEmpty()) { // object_link
                throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "constructor",
                    name_of_exception = "EXC_FILE_NAME_IS_EMPTY"
                )
            } else {
                DeleteSymbols(SELF_Jsocket.value_par4) // OBJECT_LINK
            }
        } else {
            if (command.equals(1011000056)) { // take_a_new_file();
                if (SELF_Jsocket!!.value_par8.isEmpty()) { // FILE FULL PATH into CLIENT SIZE
                    throw my_user_exceptions_class(
                        l_class_name = "FileService",
                        l_function_name = "constructor",
                        name_of_exception = "EXC_FILE_NAME_IS_EMPTY"
                    )
                } else {
                    if (file == null) {
                        file = CrossPlatformFile(SELF_Jsocket.value_par8)
                    }
                    file!!.getFileName()
                }

            } else ""
        }

    var fileExtension: String =
        if (command.equals(1011000007) || command.equals(1011000024)) { // SAVE SAVE_MEDIA (DOWNLOAD FILE); PLAY MEDIA;
            if (SELF_Jsocket!!.object_extension.isEmpty()) { // object_link
                throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "constructor",
                    name_of_exception = "EXC_FILE_NAME_IS_EMPTY",
                    l_additional_text = "FileExtension is empty"
                )
            } else {
                DeleteSymbols(SELF_Jsocket.object_extension).lowercase()
            }
        } else {
            if (command.equals(1011000056)) { // take_a_new_file();
                if (SELF_Jsocket!!.value_par8.isEmpty()) { // FILE FULL PATH into CLIENT SIZE
                    throw my_user_exceptions_class(
                        l_class_name = "FileService",
                        l_function_name = "constructor",
                        name_of_exception = "EXC_FILE_NAME_IS_EMPTY"
                    )
                } else {
                    if (file == null) {
                        file = CrossPlatformFile(SELF_Jsocket.value_par8)
                    }
                    file!!.getFileExtension().lowercase()
                }

            } else ""
        }

    val save_media: KSaveMedia? =
        if (command.equals(1011000007) || command.equals(1011000024)) { // SAVE SAVE_MEDIA (DOWNLOAD FILE); PLAY MEDIA;

            var s = SAVE_MEDIA[fIleName]

            if (s != null) {
                if (command.equals(1011000007) && s!!.getIS_TEMP()) {
                    s!!.setIsPerminent()
                }
                s!!.setLAST_USED()
                IsDownloaded = true
            } else {
                s = SELF_Jsocket?.let { KSaveMedia(it) }
            }
            s
        } else null

    val fIleFullName: String = if (SELF_Jsocket == null) {
        ""
    } else {
        if (command.equals(1011000056)) { // take_a_new_file();
            SELF_Jsocket.value_par8.ifEmpty {
                throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "constructor",
                    name_of_exception = "EXC_FILE_NAME_IS_EMPTY"
                )
            }

        } else if (command.equals(1011000007) || command.equals(1011000024)) { // SAVE SAVE_MEDIA (DOWNLOAD FILE); PLAY MEDIA;
            save_media!!.FILE_FULL_NAME
        } else ""
    }

    private var ExpectedFIleSize: Long =
        if (command.equals(1011000007) || command.equals(1011000024)) { // SAVE SAVE_MEDIA (DOWNLOAD FILE); PLAY MEDIA;
            SELF_Jsocket!!.object_size
        } else {
            0L
        }

    var currentFIleSize: Long = 0L
        private set
    private var CURRENT_CHUNK_SIZE: Int =
        if (command.equals(1011000007) || command.equals(1011000024)) { // SAVE SAVE_MEDIA (DOWNLOAD FILE); PLAY MEDIA;
            if (IsDownloaded) Constants.CHUNK_SIZE else 0
        } else 0
    private var SEND_AVATAR_SIZE = Constants.SEND_AVATAR_SIZE
    private var MAX_FILE_SIZE = Constants.MAX_FILES_SIZE_B
    private val AVATAR_MAX_SIZE = Constants.AVATAR_MAX_SIZE_FOR_LOADING
    private var Chunks: IntArray? = null
    private var NotSendedChunks: Int = 0
    private var EndFIleBytes: Long = 0L
    private var CurrentLoopBytes = 0L
    private var AllBytes = 0L
    private var SendedBytes = 0L
    private var ServerFileName = ""  // Fale name into Server for send file
    private var MaxTryingSendReceiveFileChunks = Constants.MAX_TRYING_SEND_RECEIVE_FILE_CHUNKS


    init {
        ensureNeverFrozen()
        if (!command.equals(0)) {
            if (fIleName.isEmpty() || fileExtension.isEmpty()) {
                throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "constructor",
                    name_of_exception = "EXC_FILE_NAME_IS_EMPTY"
                )
            }
        }
        if (SELF_Jsocket != null) {
            if (command.equals(1011000007) || command.equals(1011000024)) { // SAVE SAVE_MEDIA (DOWNLOAD FILE); PLAY MEDIA;
                SELF_Jsocket.just_do_it = 1011000055 // GIVE_ME_THAT_FILE;
            }
        }
    }

    private fun DeleteSymbols(InputString: String): String {
        return InputString.replace("/", "").replace(slash, "").replace(" ", "")
            .replace("'", "").replace("/'", "").replace("..", "").trim()
    }


    private fun createFileExtensionFromFullFIleName(LFileFullName: String): String {
        return try {
            if (save_media != null) {
                return save_media.getOBJECT_EXTENSION()
            }
            val extension: String = DeleteSymbols(LFileFullName.trim()).substringAfterLast(".")
            if (extension.trim().isEmpty()) {
                throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "createFileExtensionFromFullFIleName",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "FileExtension is empty"
                )
            }
            extension.lowercase()
        } catch (e: my_user_exceptions_class) {
            throw e
        } catch (ex: Exception) {
            throw my_user_exceptions_class(
                l_class_name = "FileService",
                l_function_name = "createFileExtensionFromFullFIleName",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = ex.message
            )
        }
    }

    private fun createFileNameFromFullName(LFileFullName: String): String {
        return try {
            return (LFileFullName.trim().substringAfterLast(slash)).substringBeforeLast(".")
        } catch (ex: Exception) {
            throw my_user_exceptions_class(
                l_class_name = "FileService",
                l_function_name = "createFileNameFromName",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = ex.message
            )
        }
    }


/////////////////////////////////////////////////////////////////////////////////////

    // 1-read, 2-re-write 3-random write, 4 - write-append
    @JsName("open_file_channel")
    suspend fun open_file_channel(): Promise<Boolean>? {

        if (file == null) {
            file = CrossPlatformFile(fIleFullName)
        }

        if (command.equals(1011000007) || command.equals(1011000024)) { // SAVE SAVE_MEDIA (DOWNLOAD FILE); PLAY MEDIA;

            if (!IsDownloaded) {

                if (KBigAvatar.IS_HAVE_LOCAL_AVATAR_AND_RESERVE(SELF_Jsocket!!.value_id3)) {
                    save_media!!.SET_BIG_AVATAR(
                        KBigAvatar.RETURN_PROMISE_SELECT_BIG_AVATAR(L_AVATAR_ID = SELF_Jsocket.value_id3).await()
                            ?.getAVATAR(), SELF_Jsocket.value_id3
                    )
                }
                if (save_media!!.getBIG_AVATAR() == null) {
                    SELF_Jsocket.value_par1 = if (SELF_Jsocket.value_par6.equals("NO_ORIGINAL")) "2" else "1"
                }
                SELF_Jsocket.send_request()

                CURRENT_CHUNK_SIZE = SELF_Jsocket.value_par1.toInt()

                file!!.create(ExpectedFIleSize) // create full size file

                val i = (ExpectedFIleSize / CURRENT_CHUNK_SIZE).toInt()
                EndFIleBytes = ExpectedFIleSize % CURRENT_CHUNK_SIZE
                Chunks = if (EndFIleBytes > 0L) {
                    IntArray(i + 1)
                } else {
                    IntArray(i)
                }
                NotSendedChunks = Chunks!!.size

                if (SELF_Jsocket.value_par2.equals("A")) {
                    save_media.SET_BIG_AVATAR(SELF_Jsocket.content, SELF_Jsocket.value_id3)
                } else if (SELF_Jsocket.value_par2.equals("0") && SELF_Jsocket.content != null) {
                    if (Chunks!!.size == 1) {
                        if (SELF_Jsocket.content!!.size != EndFIleBytes.toInt()) {
                            return receive_file()
                        }
                    } else {
                        if (SELF_Jsocket.content!!.size != CURRENT_CHUNK_SIZE) {
                            return receive_file()
                        }
                    }
                    file!!.write(SELF_Jsocket.content!!, 0L)
                    Chunks!![0] = 1
                    NotSendedChunks--
                    if (NotSendedChunks == 0) {
                        IsDownloaded = true
                    }
                }
                return receive_file()
            } else return null

        } else if (command.equals(1011000056)) { // take_a_new_file();
            ExpectedFIleSize = file!!.size()
            SELF_Jsocket!!.send_request()
            ServerFileName = SELF_Jsocket.value_par4
            CURRENT_CHUNK_SIZE = SELF_Jsocket.value_par1.toInt()
            return send_file()
        }

        if (!file!!.exists() || !file!!.isFile() || file!!.size() != ExpectedFIleSize) {
            throw my_user_exceptions_class(
                l_class_name = "FileService",
                l_function_name = "open_file_channel",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "file not exists"
            )
        }
        return null
    }

    fun getMyFile(): CrossPlatformFile? {
        return file
    }

/////////////////////////////////////////////////////////////////////////////////////

    @JsName("send_file")
    fun send_file(): Promise<Boolean> = CoroutineScope(Dispatchers.Default).async {
        try {
            while (IsInterrupted.get() == 0) {

                MaxTryingSendReceiveFileChunks--

                if (MaxTryingSendReceiveFileChunks == 0) {
                    throw my_user_exceptions_class(
                        l_class_name = "FileService",
                        l_function_name = "send_file",
                        name_of_exception = "EXC_MAX_TRYING_SEND_RECEIVE_FILE_CHUNKS"
                    )
                }

                if (ServerFileName.isNotEmpty()) { // clear previous content if not first request
                    SELF_Jsocket!!.content = null
                }

                if (ServerFileName.isEmpty()) {
                    if (CURRENT_CHUNK_SIZE != 0) {
                        throw my_user_exceptions_class(
                            l_class_name = "FileService",
                            l_function_name = "send_file",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "Error send file: ServerFileName is empty"
                        )
                    }


                }

                if (CURRENT_CHUNK_SIZE == 0) {
                    if (ServerFileName.isNotEmpty()) {
                        throw my_user_exceptions_class(
                            l_class_name = "FileService",
                            l_function_name = "send_file",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "Error send file: CURRENT_CHUNK_SIZE is empty"
                        )
                    }
                }

                if (SELF_Jsocket!!.value_par2.equals("B")) {
                    IsDownloaded = true
                    break
                } else {
                    if (ServerFileName.isNotEmpty()) {
                        val offset: Long = SELF_Jsocket.value_par2.toLong() * CURRENT_CHUNK_SIZE
                        if (offset > ExpectedFIleSize) {
                            throw my_user_exceptions_class(
                                l_class_name = "FileService",
                                l_function_name = "send_file",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "The request requested a larger file size."
                            )
                        }
                        val chunk_size: Int = if ((offset + CURRENT_CHUNK_SIZE) > ExpectedFIleSize) {
                            (ExpectedFIleSize - offset).toInt()
                        } else CURRENT_CHUNK_SIZE

                        SELF_Jsocket.content =
                            file!!.read(offset, chunk_size)
                        if (SELF_Jsocket.content!!.isNotEmpty()) {
                            if (SELF_Jsocket.content!!.size != chunk_size) {
                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    println("chunk_size not equal content.size!")
                                }
                                SELF_Jsocket.content = null
                                continue
                            }
                            SELF_Jsocket.value_par4 = ServerFileName
                            SELF_Jsocket.send_request()
                            SELF_Jsocket.content = null
                        }
                    } else {
                        SELF_Jsocket.send_request()
                        ServerFileName = SELF_Jsocket.value_par4
                        CURRENT_CHUNK_SIZE = SELF_Jsocket.value_par1.toInt()
                    }
                }
            }
        } finally {
            this@FileService.close()
        }
        return@async IsDownloaded
    }.toPromise(EmptyCoroutineContext)

////////////////////////////////////////////////////////////////////////////////

    @JsName("receive_file")
    fun receive_file(): Promise<Boolean> = CoroutineScope(Dispatchers.Default).async {
        try {
            while (IsInterrupted.get() == 0) {

                if (IsDownloaded) break

                MaxTryingSendReceiveFileChunks--

                if (MaxTryingSendReceiveFileChunks == 0) {
                    throw my_user_exceptions_class(
                        l_class_name = "FileService",
                        l_function_name = "receive_file",
                        name_of_exception = "EXC_MAX_TRYING_SEND_RECEIVE_FILE_CHUNKS"
                    )
                }

                if (CURRENT_CHUNK_SIZE == 0) {
                    throw my_user_exceptions_class(
                        l_class_name = "FileService",
                        l_function_name = "receive_file",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "Error send file: CURRENT_CHUNK_SIZE is empty"
                    )
                }

                SELF_Jsocket!!.send_request()

                if (SELF_Jsocket.value_par2.equals("A")) {
                    save_media!!.SET_BIG_AVATAR(SELF_Jsocket.content, SELF_Jsocket.value_id3)
                } else {

                    val i = SELF_Jsocket.value_par2.toInt()

                    if (Chunks!![i] == 0) {

                        if (SELF_Jsocket.content != null && SELF_Jsocket.content!!.isNotEmpty()) {

                            if (Chunks!!.size >= i) {
                                throw my_user_exceptions_class(
                                    l_class_name = "FileService",
                                    l_function_name = "receive_file",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "The request recevived a larger file size."
                                )
                            } else {

                                val offset: Long = (i * CURRENT_CHUNK_SIZE).toLong()

                                if (offset > ExpectedFIleSize) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "FileService",
                                        l_function_name = "receive_file",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "The request received a larger file size."
                                    )
                                }
                                val chunk_size: Int = if ((offset + CURRENT_CHUNK_SIZE) > ExpectedFIleSize) {
                                    EndFIleBytes.toInt()
                                } else CURRENT_CHUNK_SIZE

                                if (SELF_Jsocket.content!!.size == chunk_size) {
                                    FileServiceLock.withLock {
                                        file!!.write(SELF_Jsocket.content!!, offset)
                                        Chunks!![i] = 1
                                        if (CurrentChunkReceiveFile.value == i) {
                                            condition.cSignal()
                                        }
                                        val c = ReturnNextNotDownloadedChankNumber()
                                        CurrentChunkReceiveFile.set(c)
                                        SELF_Jsocket.value_par2 = c.toString()
                                    }
                                    NotSendedChunks--
                                    if (NotSendedChunks == 0) {
                                        IsDownloaded = true
                                    }
                                    if (CurrentChunkReceiveFile.compareAndSet(i, i)) {
                                        condition.cSignal()
                                    }
                                }
                            }
                        }
                    }
                }
           }
        } finally {
            IsDownloaded = this@FileService.FinishDownloadedFile()
        }
        return@async IsDownloaded
    }.toPromise(EmptyCoroutineContext)

    @JsName("get_file_chunk")
    suspend fun get_file_chunk(offset: Long,
                                startLoading: (() -> Any?)? = null,
                                finishLoading: ((v: Any?) -> Any?)? = null): ByteArray {

        if(IsInterrupted.get() == 1 && !IsDownloaded){
            throw my_user_exceptions_class(
                l_class_name = "FileService",
                l_function_name = "receive_file",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "File not downloaded but allready closed."
            )
        }
        var b = ByteArray(0)
        try {
            if (IsDownloaded) {
                return file!!.read(offset, CURRENT_CHUNK_SIZE)
            } else {
                if (startLoading != null) {
                    startLoading()
                }
                val chunk_size: Int = (offset / CURRENT_CHUNK_SIZE).toInt()

                val is_download: Boolean = FileServiceLock.withLock {Chunks!![chunk_size] == 1}
                if(is_download){
                    b =  file!!.read(offset, CURRENT_CHUNK_SIZE)
                }else{

                    FileServiceLock.withLock {CurrentChunkReceiveFile.set(chunk_size)}

                    if (condition.cAwait(Constants.CLIENT_TIMEOUT)) {
                        b =  file!!.read(offset, CURRENT_CHUNK_SIZE)
                    } else {
                        throw my_user_exceptions_class(
                            l_class_name = "FileService",
                            l_function_name = "receive_file",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "Error receive file."
                        )
                    }
                }
            }
        }catch (e: my_user_exceptions_class){
            e.ExceptionHand(null)
        }finally {
            if( finishLoading != null){
                finishLoading(b)
            }
            return b
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @JsName("SaveDownloadedFile")
    suspend fun FinishDownloadedFile():Boolean {
        if (IsDownloaded) {
            if(file!!.renameTo(save_media!!.ReturnDownloadedFullFileName())){
                return KSaveMedia.AddNewSaveMedia(save_media)
            } else return false
        }else{
            file!!.delete()
            return false
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @JsName("ReturnNextNotDownloadedChankNumber")
    fun ReturnNextNotDownloadedChankNumber(): Int {
        if (IsDownloaded) {
            return -1
        }
        var current_chank = CurrentChunkReceiveFile.get()

        if (Chunks!![current_chank] == 0) {
            return current_chank
        }else {
            for (x in 0..Chunks!!.size) {
                current_chank++
                if(current_chank >= Chunks!!.size){
                    current_chank = 0
                }
                if (Chunks!![current_chank] == 0) return current_chank
            }
        }
        return -1
    }


    fun IsDownloaded(): Boolean {
        return IsDownloaded
    }

    ////////////////////////////////////////////////////////////////////////////////
    @JsName("getImmageAvatarFromFileName")

    suspend fun getImmageAvatarFromFileName(lFullFileName: String): ByteArray? {
        val fileName = resourcesVfs[lFullFileName]
        fileName.readAsSyncStream()
        if (!fileName.exists() || fileName.isDirectory()) {
            throw my_user_exceptions_class(
                l_class_name = "FileService",
                l_function_name = "getImmageAvatarFromFileName",
                name_of_exception = "EXC_AVATAR_TYPE_OF_FILE_IS_WRONG"
            )
        }
        if (fileExtension.trim().isEmpty()) {
            fileExtension = createFileExtensionFromFullFIleName(lFullFileName)
        }
        if (!PictureSet.contains(fileExtension)) {
            throw my_user_exceptions_class(
                l_class_name = "FileService",
                l_function_name = "getImmageAvatarFromFileName",
                name_of_exception = "EXC_AVATAR_TYPE_OF_FILE_IS_WRONG"
            )
        }
        if (fileName.size() > AVATAR_MAX_SIZE) {
            throw my_user_exceptions_class(
                l_class_name = "FileService",
                l_function_name = "getImmageAvatarFromFileName",
                name_of_exception = "EXC_TOO_MANY_SIZE_OF_OBJECT"
            )
        }

        val l = fileName.readAll()
        if (l.size <= SEND_AVATAR_SIZE) {
            return l
        }
        val file = l.openAsync().asVfsFile()
        val image32 = file.readBitmap().toBMP32()
        return getImmageAvatarFromBitmap32(image32)

    }

    @JsName("getImmageAvatarFromByteArray")
    suspend fun getImmageAvatarFromByteArray(imageData: ByteArray): ByteArray? {
        if (imageData.size > AVATAR_MAX_SIZE) {
            throw my_user_exceptions_class(
                l_class_name = "FileService",
                l_function_name = "getImmageAvatarFromFileName",
                name_of_exception = "EXC_TOO_MANY_SIZE_OF_OBJECT"
            )
        }
        if (imageData.size <= SEND_AVATAR_SIZE) {
            return imageData
        }
        val fileName = imageData.openAsync().asVfsFile()
        val image32 = fileName.readBitmap().toBMP32()
        return getImmageAvatarFromBitmap32(image32)
    }

    @JsName("getImmageAvatarFromByteArrayWithKoord")
    private suspend fun getImmageAvatarFromByteArrayWithKoord(
        imageData: ByteArray,
        x: Int,
        y: Int,
        w: Int,
        h: Int
    ): ByteArray? {
        if (imageData.size > AVATAR_MAX_SIZE) {
            throw my_user_exceptions_class(
                l_class_name = "FileService",
                l_function_name = "getImmageAvatarFromFileName",
                name_of_exception = "EXC_TOO_MANY_SIZE_OF_OBJECT"
            )
        }
        val fileName = imageData.openAsync().asVfsFile()
        var image32 = fileName.readBitmap().toBMP32()
        image32 = image32.copySliceWithSize(x = x, y = y, width = w, height = h)
        return getImmageAvatarFromBitmap32(image32)
    }

    @JsName("getImmageAvatarFormBitmap32")
    private suspend fun getImmageAvatarFromBitmap32(imageData: Bitmap32): ByteArray? {
        var bb: ByteArray? = null
        var image32 = imageData
        val ex = ImageEncodingProps("", 1.0)
        val sliceSIze = SEND_AVATAR_SIZE * 1.4
        //bb = image32.encode(PNG, ex)
        bb = image32.encode(PNG)
        if (bb.size <= SEND_AVATAR_SIZE) return bb
        else bb = null
        var x = 0
        while (x < 1000) {
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
            while (x < 1000) {
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
        IsInterrupted.set(1)
    }

    /////////////////////////////////////////////////////////////////////////////////////
    fun finalize() {
        IsInterrupted.set(1)
    }
/////////////////////////////////////////////////////////////////////////////////////
}