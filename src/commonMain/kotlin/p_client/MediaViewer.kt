/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("RedundantSuspendModifier", "EXPERIMENTAL_API_USAGE")

package p_client

import CrossPlatforms.WriteExceptionIntoFile
import Tables.KBigAvatar.Companion.IS_HAVE_LOCAL_AVATAR_AND_RESERVE
import Tables.KSaveMedia
import com.soywiz.korio.async.await
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.stream.AsyncStream
import io.ktor.util.InternalAPI
import kotlinx.coroutines.*
import p_jsocket.Connection
import p_jsocket.nowNano
import sql.Sqlite_service.SAVEMEDIA
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

/**
 *
 * @author User
 */

@InternalAPI
@ExperimentalTime
@JsName("FileLoader")
object MediaViewer : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    val FileLoaderScope = CoroutineScope(coroutineContext)


    private var newTask: Jsocket? = null


    private var currentTask: Jsocket? = null


    private var errorTask: Jsocket? = null
    private var clientFileService: ClientFileService? = null

    private var currentChunk: Int? = 0


    private val FileLoaderLock = Lock()

    private val listener: Listener? = Listener.get_Instance()

    ////////////////////////////////////////////////////////////////////////////////


    @JsName("setTask")
    suspend fun setTask(ljsocket: Jsocket): AsyncStream? {
        return try {
            withTimeoutOrNull(CLIENT_TIMEOUT) {
                try {
                    FileLoaderLock.lock()
                    errorTask = null
                    newTask = ljsocket
                    currentTask = null
                    if (newTask!!.value_id1.isEmpty()) { // ИД треуемого файла
                        if (clientFileService != null) {
                            clientFileService!!.close()
                            return@withTimeoutOrNull null
                        }
                    }
                    if (clientFileService != null && newTask!!.value_id1 == clientFileService!!.MyFileService.fIleName) {

                        if(newTask!!.value_par1 == "0"){ // если начальная загрузка - возвращаем аватарку
                            if(newTask!!.content == null ){
                                newTask!!.content = clientFileService!!.getmyKBigAvatar()?.getAVATAR()
                            }
                        }

                        if (clientFileService!!.MyFileService.IsDownloaded()) { // если загруже
                            if (clientFileService!!.MyFileService.OpenMode != 1) { // если открыт не для чте чтения, то открываем для чтения
                                val m: KSaveMedia = clientFileService!!.myKSaveMedia as KSaveMedia
                                clientFileService!!.close()
                                clientFileService = ClientFileService(m, 1)
                            }
                            return@withTimeoutOrNull clientFileService!!.MyFileService.getMyFile()!!.fileChannel
                        }
                        if (clientFileService!!.MyFileService.IsDownLoadedChank(
                                newTask!!.value_par1.toInt() // требуемая часть файла
                            )
                        ) {
                            currentTask = newTask
                            return@withTimeoutOrNull clientFileService!!.MyFileService.getMyFile()!!.fileChannel
                        }

                    } else { //если требуеться новый файл создаем его
                        clientFileService?.close()

                        clientFileService = if (SAVEMEDIA.containsKey(newTask!!.value_id1)) {
                            ClientFileService(
                                SAVEMEDIA[newTask!!.value_id1]!!,
                                1
                            ) //если уже есть в базе получаем только чтение
                        } else {
                            ClientFileService(KSaveMedia(newTask!!), 3) //если нет, то создаем новый
                        }

                        if (clientFileService!!.MyFileService.OpenMode == 1) { //усли файл уже загружен
                            newTask!!.content = clientFileService!!.getmyKBigAvatar()?.getAVATAR()
                            return@withTimeoutOrNull clientFileService!!.MyFileService.getMyFile()!!.fileChannel
                        }

                        newTask!!.value_par1 = "0"
                        newTask!!.value_par2 = if (IS_HAVE_LOCAL_AVATAR_AND_RESERVE(newTask!!.value_id3)) "0" else newTask!!.value_par2

                    }
                    newTask!!.ANSWER_TYPEs?.clear()

                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "FileLoader.setTask1")
                    return@withTimeoutOrNull null
                } finally {
                    FileLoaderLock.unlock()
                }
                val s: Jsocket = newTask?.let { sendTask(it) }?.await()!!
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    try {
                        FileLoaderLock.lock()
                        if (s.just_do_it_successfull != "0") {
                            currentTask = null
                            errorTask = s
                            return@withTimeoutOrNull null
                        }
                        if (clientFileService!!.MyFileService.getMyFile()!!.fileChannel != null) {
                            if (clientFileService!!.MyFileService.IsDownloaded()) {
                                currentTask = null
                            }else{
                                currentTask = newTask
                            }
                            clientFileService!!.MyFileService.getMyFile()!!.fileChannel
                        } else {
                            null
                        }
                    } catch (ex: Exception) {
                        WriteExceptionIntoFile(ex, "FileLoader.setTask2")
                        null
                    } finally {
                        FileLoaderLock.unlock()
                    }
                }
            }
        } catch (ex: Exception) {
            ljsocket.just_do_it_successfull = "4"
            ljsocket.db_massage = ex.toString()
            errorTask = ljsocket
            null
        } finally {
            if (newTask != null && newTask!!.content != null && newTask!!.content!!.isNotEmpty()) {
                ljsocket.content = newTask!!.content
            }
            newTask = null
        }
    }


    ////////////////////////////////////////////////////////////////////////////////

    @DangerousInternalIoApi
    @ExperimentalTime
    @InternalAPI
    @ExperimentalStdlibApi
    @JsName("sendTask")
    private suspend fun sendTask(ljsocket: Jsocket) = FileLoaderScope.async {
        try {
            ljsocket.just_do_it_label = nowNano()
            listener!!.setInJSOCKETs(ljsocket)
            if (!ljsocket.condition.cAwait(CLIENT_TIMEOUT)) {
                ljsocket.just_do_it_successfull = "4"
                ljsocket.db_massage = returnException(90031, ljsocket.lang)
            }
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "FileLoader.sendTask")
            ljsocket.just_do_it_successfull = "4"
            ljsocket.db_massage = ex.toString()
            return@async ljsocket
        }
        return@async ljsocket
    }.toPromise(EmptyCoroutineContext)

    ////////////////////////////////////////////////////////////////////////////////
    @DangerousInternalIoApi
    @ExperimentalTime
    @ExperimentalIoApi
    @ExperimentalStdlibApi
    @InternalAPI
    @JsName("getTask")
    suspend fun getTask() {
        try {
            withTimeoutOrNull(CLIENT_TIMEOUT) {
                FileLoaderLock.lock()
                if (currentTask != null && clientFileService != null && !clientFileService!!.MyFileService.IsDownloaded()) {
                    currentChunk = clientFileService!!.MyFileService.ReturnNextNotDownloadedChankNumber(
                        currentTask!!.value_par1.toInt()
                    )
                    if (currentChunk!! > -1) {
                        currentTask!!.value_par1 = currentChunk.toString()
                        currentTask!!.value_par2 = "0"
                        listener!!.setInJSOCKETs(currentTask)
                    } else {
                        currentTask = null
                    }
                }
            }
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "FileLoader.task")
        } finally {
            FileLoaderLock.unlock()
        }
    }

    @ExperimentalTime
    @ExperimentalIoApi
    @InternalAPI
    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    @JsName("executeTask")
    suspend fun executeTask(
        ljsocket: Jsocket,
        Connection: Connection
    ) {
        try {
            withTimeoutOrNull(CLIENT_TIMEOUT) {
                FileLoaderLock.lock()
                if (ljsocket.value_id1 != clientFileService!!.myKSaveMedia!!.getOBJECT_ID()) {
                    throw exc_object_name_is_wrong(ljsocket.value_id1)
                }
                currentTask = ljsocket
                if (ljsocket.just_do_it_successfull == "0"
                    && currentTask!!.value_par1.trim().toInt() < Long.MAX_VALUE
                ) {
                    if (clientFileService!!.MyFileService.write_chunk_of_file(
                            currentTask!!.value_par1.trim().toInt(),
                            Connection
                        )
                    ) {
                        currentTask = null
                    }
                }

            }
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "FileLoader.executeTask")
        } finally {
            FileLoaderLock.unlock()
            newTask?.condition?.cSignal()
        }
    }
}
