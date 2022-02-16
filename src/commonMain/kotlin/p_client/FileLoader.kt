/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("RedundantSuspendModifier", "EXPERIMENTAL_API_USAGE")

package p_client

import CrossPlatforms.WriteExceptionIntoFile
import Tables.KSaveMedia
import com.soywiz.korio.stream.AsyncStream
import io.ktor.util.InternalAPI
import io.ktor.util.Lock
import io.ktor.util.withLock
import io.ktor.utils.io.core.ExperimentalIoApi
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import kotlinx.coroutines.withTimeoutOrNull
import lib_exceptions.exc_object_name_is_wrong
import lib_exceptions.exc_universal_exception.returnException
import p_jsocket.CLIENT_TIMEOUT
import p_jsocket.Connection
import p_jsocket.nowNano
import sql.Sqlite_service.SAVEMEDIA
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

/**
 *
 * @author User
 */
@JsName("FileLoader")
object FileLoader {
    @InternalAPI
    private var newTask: Jsocket? = null

    @InternalAPI
    private var currentTask: Jsocket? = null

    @InternalAPI
    private var errorTask: Jsocket? = null
    private var clientFileService: ClientFileService? = null

    private var currentChunk: Int? = 0

    @InternalAPI
    private val lock = Lock()

    private val listener: Listener? = Listener.get_Instance()

    ////////////////////////////////////////////////////////////////////////////////
    @DangerousInternalIoApi
    @ExperimentalTime
    @ExperimentalUnsignedTypes
    @ExperimentalIoApi
    @InternalAPI
    @ExperimentalStdlibApi
    @JsName("setTask")
    suspend fun setTask(ljsocket: Jsocket): AsyncStream? {
        return try {
            withTimeoutOrNull(CLIENT_TIMEOUT) {
                try {
                    lock.lock()
                        errorTask = null
                        newTask = ljsocket
                        currentTask = null
                        if (ljsocket.value_id1.isEmpty()) { // ИД треуемого файла
                            if (clientFileService != null) {
                                clientFileService!!.close()
                                return@withTimeoutOrNull null
                            }
                        }
                        if (clientFileService != null && ljsocket.value_id1 == clientFileService!!.MyFileService.fIleName) {
                            if (clientFileService!!.MyFileService.IsDownloaded()) { // если загруже
                                currentTask = null
                                if (clientFileService!!.MyFileService.OpenMode != 1) { // если открыт не для чте чтения, то открываем для чтения
                                    val m: KSaveMedia = clientFileService!!.myKSaveMedia as KSaveMedia
                                    clientFileService!!.close()
                                    clientFileService = ClientFileService(m, 1)
                                }
                                return@withTimeoutOrNull clientFileService!!.MyFileService.getMyFile()!!.fileChannel
                            }
                            if (clientFileService!!.MyFileService.IsDownLoadedChank(
                                            ljsocket.value_par1.toInt() // требуемая часть файла
                                    )
                            ) {
                                currentTask = newTask
                                return@withTimeoutOrNull clientFileService!!.MyFileService.getMyFile()!!.fileChannel
                            }

                        } else { //если требуеться новый файл создаем его
                            clientFileService?.close()

                            clientFileService = if (SAVEMEDIA.containsKey(newTask!!.value_id1)) {
                                ClientFileService(SAVEMEDIA[newTask!!.value_id1]!!, 1) //если уже есть в базе получаем только чтение
                            } else {
                                ClientFileService(KSaveMedia(newTask!!), 3) //если нет, то создаем новый
                            }

                            newTask!!.content = clientFileService!!.getmyKBigAvatar()?.getAVATAR()

                            if (clientFileService!!.MyFileService.OpenMode == 1) { //усли файл уже загружен
                                return@withTimeoutOrNull clientFileService!!.MyFileService.getMyFile()!!.fileChannel
                            }

                            newTask!!.value_par1 = "0"

                            /*if (newTask!!.ANSWER_TYPEs!!.size > 1) {
                                newTask!!.just_do_it_successfull = "4"
                                newTask!!.db_massage = returnException(90033, ljsocket.lang)
                                errorTask = newTask
                                return@withLock null
                            }*/
                        }
                        newTask!!.ANSWER_TYPEs?.clear()

                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "FileLoader.setTask1")
                    return@withTimeoutOrNull null
                } finally {
                    lock.unlock()
                }
                val s: Jsocket = newTask?.let { sendTask(it) }!!
                try {
                    lock.withLock {
                        if (s.just_do_it_successfull != "0") {
                            currentTask = null
                            errorTask = s
                            return@withLock null
                        }
                        if (clientFileService!!.MyFileService.getMyFile()!!.fileChannel != null) {
                            if (clientFileService!!.MyFileService.IsDownloaded()) {
                                currentTask = null
                            }
                            clientFileService!!.MyFileService.getMyFile()!!.fileChannel
                        } else {
                            null
                        }
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "FileLoader.setTask2")
                    null
                } finally {
                    lock.unlock()
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
    @ExperimentalIoApi
    @ExperimentalStdlibApi
    @JsName("sendTask")
    private suspend fun sendTask(ljsocket: Jsocket): Jsocket {
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
            return ljsocket
        }
        return ljsocket
    }

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
                lock.lock()
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
        }finally {
            lock.unlock()
        }
    }

    @ExperimentalTime
    @ExperimentalIoApi
    @InternalAPI
    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    @JsName("executeTask")
    suspend fun executeTask(ljsocket: Jsocket,
                            Connection: Connection) {
        try {
            withTimeoutOrNull(CLIENT_TIMEOUT) {
                lock.lock()
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
            newTask?.condition?.cSignal()
            lock.unlock()
        }
    }
}
