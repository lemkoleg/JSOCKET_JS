@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

import CrossPlatforms.CrossPlatformFile
import CrossPlatforms.MyCondition
import CrossPlatforms.PrintInformation
import CrossPlatforms.slash
import Tables.KSaveMedia
import Tables.SAVE_MEDIA
import co.touchlab.stately.concurrency.AtomicInt
import co.touchlab.stately.concurrency.value
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
import com.soywiz.korio.stream.asVfsFile
import com.soywiz.korio.stream.openAsync
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_client.Jsocket
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author User
 */

val colorFormat = RGB_555

@JsName("FileServiceGlobalLock")
@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
private val FileServiceGlobalLock = Mutex()


@JsName("FileService")
@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class FileService(
    val answerType: ANSWER_TYPE? = null

) {
    var fIleFullName: String? = null

    var save_media: KSaveMedia? = null

    private val IsInterrupted = AtomicInt(0)

    val condition: MyCondition = MyCondition()

    private val FileServiceLock = Mutex()

    private val CurrentChunkReceiveFile = AtomicInt(0)

    var avatar: ByteArray? = null


    var file: CrossPlatformFile? = null

    private var IsDownloaded = false

    var SELF_Jsocket: Jsocket = Jsocket()

    init {


        if (answerType != null) {

            SELF_Jsocket.just_do_it = 1011000055 // GIVE_ME_THAT_FILE
            SELF_Jsocket.object_server = answerType.answerTypeValues.GetObjectServer()
            SELF_Jsocket.object_extension = answerType.answerTypeValues.GetObjectExtension()
            SELF_Jsocket.object_size = answerType.answerTypeValues.GetObjectSize().toLong()
            SELF_Jsocket.value_id2 = answerType.answerTypeValues.GetAlbumId()
            SELF_Jsocket.value_id4 = answerType.answerTypeValues.GetObjectId()
            SELF_Jsocket.value_id5 = answerType.answerTypeValues.GetLinkOwner()
            SELF_Jsocket.value_par1 = answerType.answerTypeValues.GetMessegeId().toString()
            SELF_Jsocket.value_par3 = "0"
            SELF_Jsocket.value_par4 = answerType.answerTypeValues.GetObjectLink()

            var s: KSaveMedia? = SAVE_MEDIA[answerType.answerTypeValues.GetObjectId()]
            if (s == null) {
                s = KSaveMedia(
                    L_OBJECT_LINK = answerType.answerTypeValues.GetObjectId(),
                    L_OBJECT_SIZE = answerType.answerTypeValues.GetObjectSize().toLong(),
                    L_OBJECT_LENGTH_SECONDS = answerType.answerTypeValues.GetLengthSeconds(),
                    L_OBJECT_EXTENSION = answerType.answerTypeValues.GetObjectExtension(),
                    L_AVATAR_ID = answerType.answerTypeValues.GetMainAvatarId()
                )

            } else {
                s.setLAST_USED()
            }
            save_media = s
            save_media!!.AVATAR_ID = answerType.answerTypeValues.GetMainAvatarId()
            IsDownloaded = s.IS_DOWNLOAD == 1
            fIleFullName = save_media!!.FILE_FULL_NAME

            file = CrossPlatformFile(fIleFullName!!)
        }
    }


    constructor(jsocket: Jsocket) : this() {
        fIleFullName = jsocket.FileFullPathForSend
        file = CrossPlatformFile(fIleFullName!!)
        SELF_Jsocket.just_do_it = 1011000056 // TAKE_A_NEW_FILE
    }


    private var ExpectedFIleSize: Long = answerType?.answerTypeValues?.GetObjectSize?.let { it() }?.toLong() ?: 0L

    var currentFIleSize: Long = 0L
        private set

    private var CURRENT_CHUNK_SIZE: Int = if (IsDownloaded) Constants.CHUNK_SIZE else 0


    private var Chunks: IntArray? = null

    private var NotSendedChunks: Int = 0

    private var EndFIleBytes: Long = 0L

    private var CurrentLoopBytes = 0L

    private var AllBytes = 0L

    private var SendedBytes = 0L

    var ServerFileName = ""  // Fale name into Server for send file

    private var MAX_FILE_SIZE = Constants.MAX_FILES_SIZE_B

    private var MaxTryingSendReceiveFileChunks = Constants.MAX_COUNT_TRYING_SEND_RECEIVE_FILE_CHUNKS


    private fun DeleteSymbols(InputString: String): String {
        return InputString.replace("/", "").replace(slash, "").replace(" ", "")
            .replace("'", "").replace("/'", "").replace("..", "").trim()
    }


    private fun createFileExtensionFromFullFIleName(LFileFullName: String): String {
        return try {
            if (save_media != null) {
                return save_media!!.L_OBJECT_EXTENSION
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

        SELF_Jsocket.FileFullPathForSend = ""


        if (answerType != null) { // SAVE SAVE_MEDIA (DOWNLOAD FILE); PLAY MEDIA;

            if (!IsDownloaded) {

                SELF_Jsocket.send_request()

                CURRENT_CHUNK_SIZE = SELF_Jsocket.value_par3.toInt()

                file!!.create(ExpectedFIleSize) // create full size file

                val i = (ExpectedFIleSize / CURRENT_CHUNK_SIZE).toInt()
                EndFIleBytes = ExpectedFIleSize % CURRENT_CHUNK_SIZE
                Chunks = if (EndFIleBytes > 0L) {
                    IntArray(i + 1)
                } else {
                    IntArray(i)
                }

                NotSendedChunks = Chunks!!.size

                if (SELF_Jsocket.content != null) {
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

            }
            return receive_file()

        } else { // take_a_new_file();

            if (!file!!.exists() || !file!!.isFile()) {
                throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "open_file_channel",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "file not exists"
                )
            }

            if (file!!.size() == 0L || file!!.size() > Constants.MAX_FILES_SIZE_B) {
                throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "open_file_channel",
                    name_of_exception = "EXC_TOO_MANY_SIZE_OF_OBJECT"
                )
            }

            ExpectedFIleSize = file!!.size()
            SELF_Jsocket.object_size = ExpectedFIleSize
            SELF_Jsocket.send_request()
            ServerFileName = SELF_Jsocket.value_par4
            CURRENT_CHUNK_SIZE = SELF_Jsocket.value_par3.toInt()
            return send_file()
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

            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                PrintInformation.PRINT_INFO("FileService.send_file: start send file: $ServerFileName")
            }

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
                    SELF_Jsocket.content = null
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

                if (SELF_Jsocket.value_par2.equals("B")) {
                    IsDownloaded = true
                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                        PrintInformation.PRINT_INFO("FileService.send_file: finish send file: $ServerFileName")
                    }
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
                                    PrintInformation.PRINT_INFO("chunk_size not equal content.size!")
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

            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                PrintInformation.PRINT_INFO("FileService.receive_file: start send file: ${save_media!!.FILE_FULL_NAME}")
            }

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

                SELF_Jsocket.send_request()

                if (SELF_Jsocket.value_par2.equals("A")) {
                    throw my_user_exceptions_class(
                        l_class_name = "FileService",
                        l_function_name = "receive_file",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "Server send wrong file chunk."
                    )
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
                                    if (IsDownloaded) {

                                        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                                            FileServiceLock.withLock {
                                                CoroutineScope(Dispatchers.Default).launch {
                                                    SELF_Jsocket.value_par2 = "B"
                                                    SELF_Jsocket.send_request()
                                                }
                                                if (FinishDownloadedFile()) {
                                                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                                        PrintInformation.PRINT_INFO("FileService.receive_file: finish send file: ${save_media!!.FILE_FULL_NAME}")
                                                    }
                                                    save_media!!.IS_DOWNLOAD = 1
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
            return@async false
        }
        return@async IsDownloaded
    }.toPromise(EmptyCoroutineContext)

    @JsName("get_file_chunk")
    fun get_file_chunk(
        offset: Long,
        startLoading: (() -> Any?)? = null,
        finishLoading: ((v: Any?) -> Any?)? = null
    ): Promise<ByteArray?> = CoroutineScope(Dispatchers.Default).async {
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            if (IsInterrupted.get() == 1 && !IsDownloaded) {
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
                    return@withTimeoutOrNull file!!.read(offset, CURRENT_CHUNK_SIZE)
                } else {
                    if (startLoading != null) {
                        startLoading()
                    }
                    val chunk_size: Int = (offset / CURRENT_CHUNK_SIZE).toInt()

                    val is_download: Boolean = FileServiceLock.withLock { Chunks!![chunk_size] == 1 }
                    if (is_download) {
                        b = file!!.read(offset, CURRENT_CHUNK_SIZE)
                    } else {

                        FileServiceLock.withLock { CurrentChunkReceiveFile.set(chunk_size) }

                        if (condition.cAwait(Constants.CLIENT_TIMEOUT)) {
                            b = file!!.read(offset, CURRENT_CHUNK_SIZE)
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
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            } finally {
                if (finishLoading != null) {
                    finishLoading(b)
                }
                return@withTimeoutOrNull b
            }
            return@withTimeoutOrNull null
        } ?: throw my_user_exceptions_class(
            l_class_name = "FileService",
            l_function_name = "get_file_chunk",
            name_of_exception = "EXC_SYSTEM_ERROR",
            l_additional_text = "Time out is up"
        )

    }.toPromise(EmptyCoroutineContext)

    /////////////////////////////////////////////////////////////////////////////////////
    @JsName("SaveDownloadedFile")
    suspend fun FinishDownloadedFile(): Boolean {
        return if (IsDownloaded) {
            if (file!!.renameTo(save_media!!.ReturnDownloadedFullFileName())) {
                KSaveMedia.AddNewSaveMedia(save_media!!).await()
            } else false
        } else {
            file!!.delete()
            false
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
        } else {
            for (x in 0..Chunks!!.size) {
                current_chank++
                if (current_chank >= Chunks!!.size) {
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

    fun close() {
        IsInterrupted.set(1)
    }

    fun finalize() {
        IsInterrupted.set(1)
    }

    ////////////////////////////////////////////////////////////////////////////////

    companion object {

        @JsName("getImmageAvatarFromFileName")
        fun getImmageAvatarFromFileName(lFullFileName: String): Promise<ByteArray?> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            FileServiceGlobalLock.lock()
                            val fileName = CrossPlatformFile(lFullFileName)
                            if (!fileName.exists() || fileName.isDirectory()) {
                                throw my_user_exceptions_class(
                                    l_class_name = "FileService",
                                    l_function_name = "getImmageAvatarFromFileName",
                                    name_of_exception = "EXC_AVATAR_TYPE_OF_FILE_IS_WRONG"
                                )
                            }

                            val fileExtension = createFileExtensionFromFullFIleName(lFullFileName)

                            if (!PictureSet.contains(fileExtension)) {
                                throw my_user_exceptions_class(
                                    l_class_name = "FileService",
                                    l_function_name = "getImmageAvatarFromFileName",
                                    name_of_exception = "EXC_AVATAR_TYPE_OF_FILE_IS_WRONG"
                                )
                            }
                            if (fileName.size() > Constants.AVATAR_MAX_SIZE_FOR_LOADING) {
                                throw my_user_exceptions_class(
                                    l_class_name = "FileService",
                                    l_function_name = "getImmageAvatarFromFileName",
                                    name_of_exception = "EXC_TOO_MANY_SIZE_OF_OBJECT"
                                )
                            }

                            val l = fileName.readAll()
                            if (l.size <= Constants.SEND_AVATAR_SIZE) {
                                return@withTimeoutOrNull l
                            }
                            val file = l.openAsync().asVfsFile()
                            val image32 = file.readBitmap().toBMP32()
                            return@withTimeoutOrNull getImmageAvatarFromBitmap32(image32)

                        } catch (e: my_user_exceptions_class) {
                            throw e
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "FileService",
                                l_function_name = "getImmageAvatarFromFileName",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            FileServiceGlobalLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeoutOrNull false
                } ?: throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "getImmageAvatarFromFileName",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
                return@async null
            }.toPromise(EmptyCoroutineContext)

        @JsName("getImmageAvatarFromByteArray")
        suspend fun getImmageAvatarFromByteArray(imageData: ByteArray): Promise<ByteArray?> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            FileServiceGlobalLock.lock()
                            if (imageData.size > Constants.AVATAR_MAX_SIZE_FOR_LOADING) {
                                throw my_user_exceptions_class(
                                    l_class_name = "FileService",
                                    l_function_name = "getImmageAvatarFromByteArray",
                                    name_of_exception = "EXC_TOO_MANY_SIZE_OF_OBJECT"
                                )
                            }
                            if (imageData.size <= Constants.SEND_AVATAR_SIZE) {
                                return@withTimeoutOrNull imageData
                            }
                            val fileName = imageData.openAsync().asVfsFile()
                            val image32 = fileName.readBitmap().toBMP32()
                            return@withTimeoutOrNull getImmageAvatarFromBitmap32(image32)
                        } catch (e: my_user_exceptions_class) {
                            throw e
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "FileService",
                                l_function_name = "getImmageAvatarFromByteArray",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            FileServiceGlobalLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeoutOrNull false
                } ?: throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "getImmageAvatarFromByteArray",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
                return@async null
            }.toPromise(EmptyCoroutineContext)

        @JsName("getImmageAvatarFromByteArrayWithKoord")
        private suspend fun getImmageAvatarFromByteArrayWithKoord(
            imageData: ByteArray,
            x: Int,
            y: Int,
            w: Int,
            h: Int
        ): ByteArray? {
            if (imageData.size > Constants.AVATAR_MAX_SIZE_FOR_LOADING) {
                throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "getImmageAvatarFromByteArrayWithKoord",
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
            val sliceSIze = Constants.SEND_AVATAR_SIZE * 1.4
            //bb = image32.encode(PNG, ex)
            bb = image32.encode(PNG)
            if (bb.size <= Constants.SEND_AVATAR_SIZE) return bb
            else bb = null
            var x = 0
            while (x < 1000) {
                x += 1
                image32 = image32.mipmap(1)
                bb = image32.encode(PNG)
                if (bb.size <= sliceSIze) break
            }
            if (bb != null) {
                if (bb.size <= Constants.SEND_AVATAR_SIZE) return bb

                val koef = 0.98F
                val avatar = Constants.SEND_AVATAR_SIZE.toFloat()
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
                    if (bb.size <= Constants.SEND_AVATAR_SIZE) break
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
        PrintInformation.PRINT_INFO("bb.size ${bb.size}")
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
            PrintInformation.PRINT_INFO("bb.size2 ${bb.size}")
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

        private fun createFileExtensionFromFullFIleName(LFileFullName: String): String {

            val extension: String = DeleteSymbols(LFileFullName.trim()).substringAfterLast(".")
            if (extension.trim().isEmpty()) {
                throw my_user_exceptions_class(
                    l_class_name = "FileService",
                    l_function_name = "createFileExtensionFromFullFIleName",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "FileExtension is empty"
                )
            }
            return extension.lowercase()
        }

        private fun DeleteSymbols(InputString: String): String {
            return InputString.replace("/", "").replace(slash, "").replace(" ", "")
                .replace("'", "").replace("/'", "").replace("..", "").trim()
        }

    }
/////////////////////////////////////////////////////////////////////////////////////
}