/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_client

import CrossPlatforms.DEFAULT_AWAIT_TIMEOUT
import CrossPlatforms.WriteExceptionIntoFile
import Tables.KBigAvatar
import Tables.KSaveMedia
import io.ktor.util.InternalAPI
import kotlinx.coroutines.*
import lib_exceptions.exc_file_not_exists
import p_jsocket.FileService
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName


/**
 *
 * @author User
 */
@JsName("ClientFileService")
class ClientFileService: CoroutineScope {


    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    private val ClientFileServiceScope = CoroutineScope(coroutineContext)

    @JsName("MyFileService")
    val MyFileService: FileService
    @JsName("MySaveMedia")
    var myKSaveMedia: KSaveMedia? = null
    @JsName("MyBigAvatar")
    private var myKBigAvatar: KBigAvatar? = null

    private var ClientFileServiceJob: Job? = null


    ////////////////////////////////////////////////////////////////////////////////

    @InternalAPI
    @ExperimentalStdlibApi
    @ExperimentalUnsignedTypes
    constructor(lKSaveMedia: KSaveMedia,
                mode: Int) {
        var lmode = mode
        myKSaveMedia = lKSaveMedia
        if (mode == 3) {
            if (myKSaveMedia!!.IsDownLoaded()) {
                lmode = 1
            }
        }
        if (myKSaveMedia!!.getOBJECT_SIZE() != 0) {
            ClientFileServiceJob = ClientFileServiceScope.launch {
                try {
                    myKBigAvatar = Sqlite_service.SelectBigAvatar(myKSaveMedia!!.getOBJECT_ID())
                    if (myKBigAvatar!!.getSMALL_AVATAR_SIZE() != myKSaveMedia!!.getSmallAvatarSize()) {
                        myKBigAvatar = null
                        Sqlite_service.DeleteBigAvatar(myKSaveMedia!!.getOBJECTS_ID())
                    }
                } catch (e: Exception) {
                }
            }
        }
        try {
            MyFileService = FileService(
                myKSaveMedia!!.getOBJECTS_ID(),
                    myKSaveMedia!!.getOBJECTS_EXTENSION(),
                    myKSaveMedia!!.getOBJECTS_SIZE(),
                    lmode)
        } catch (e: exc_file_not_exists) {
            Sqlite_service.DeleteSaveMedia(myKSaveMedia!!.getOBJECTS_ID())
            throw exc_file_not_exists(myKSaveMedia!!.getOBJECTS_ID())
        }
    }

    @ExperimentalUnsignedTypes
    constructor(lFullFIleName: String?) {
        MyFileService = FileService(lFullFIleName!!)
        myKSaveMedia = null

    }

    suspend fun  getmyKBigAvatar():KBigAvatar? {
        withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
            if (!ClientFileServiceJob!!.isCompleted) {
                ClientFileServiceJob!!.join()
            }
        }
        return myKBigAvatar
    }

    @InternalAPI
    @ExperimentalStdlibApi
    fun close() {
            try {
                if (myKSaveMedia != null) {
                    MyFileService.FileServiceScope.launch {
                        if (!MyFileService.IsDownloaded()) {
                            Sqlite_service.DeleteSaveMedia(myKSaveMedia!!.getOBJECTS_ID())
                            return@launch
                        }
                        if (MyFileService.OpenMode != 1
                            && MyFileService.IsDownloaded()
                        ) {
                            if (MyFileService.OpenMode == 2 || MyFileService.OpenMode == 4) {
                                myKSaveMedia!!.setIsTemp(false)
                            } else {
                                myKSaveMedia!!.setIsTemp(true)
                            }
                            myKSaveMedia!!.setIsDownLoaded(true)
                            try {
                                if (MyFileService.currentFIleSize
                                    in (MIN_FILE_SIZE_FOR_SEVE + 1)
                                    until MAX_FILE_SIZE_FOR_SEVE
                                    && MyFileService.SaveDownloadedFile()
                                ) {
                                    Sqlite_service.InsertSaveMedia(myKSaveMedia!!)
                                } else {
                                    Sqlite_service.DeleteSaveMedia(myKSaveMedia!!.getOBJECTS_ID())
                                }
                            } catch (ex: Exception) {
                                try {
                                    WriteExceptionIntoFile(ex, "ClientFileService.close")
                                } catch (e2: Exception) {
                                }
                            }
                        }
                    }
                }
            } finally {
                MyFileService.close()
                myKBigAvatar = null
            }
    }
////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        private val WAITTIMEOUT: Long? = 20L
        private const val MIN_FILE_SIZE_FOR_SEVE = 1024 * 1000 //1 MB
                .toLong()
        private const val MAX_FILE_SIZE_FOR_SEVE = 1024 * 1000 * 1000 //1 GB
                .toLong()
    }
}