/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import CrossPlatforms.CrossPlatformFile
import CrossPlatforms.slash
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_jsocket.Constants
import p_jsocket.pathTemp
import p_jsocket.rootPath
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author User
 */

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
private val SAVE_MEDIA: MutableMap<String, KSaveMedia> = mutableMapOf()


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
private val KSaveMediaLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KSaveMedia")
class KSaveMedia {


    var OBJECT_ID: String = ""
    var CONNECTION_ID: Long = 0L
    var AVATAR_ID: String = ""
    var OBJECT_NAME: String = ""
    var OBJECT_SIZE: Int = 0
    var OBJECT_LENGTH_SECONDS: Int = 0
    var OBJECT_SERVER: String = ""
    var OBJECT_LINK: String = ""
    var OBJECT_EXTENSION: String = ""
    var AVATAR_LINK: String = ""
    var AVATAR_SERVER: String = ""
    var ORIGINAL_AVATAR_SIZE = 0
    var SMALL_AVATAR: ByteArray? = null
    var BIG_AVATAR: ByteArray? = null
    var IS_TEMP: Int = 0
    var IS_DOWNLOAD: Int = 0
    var LAST_USED: Long = 0

    var FILE_FULL_NAME: String = ""


    private constructor()

    constructor(
        L_OBJECT_ID: String,
        L_AVATAR_ID: String?,
        L_OBJECT_NAME: String,
        L_OBJECT_SIZE: Int,
        L_OBJECT_LENGTH_SECONDS: Int,
        L_OBJECT_SERVER: String,
        L_OBJECT_LINK: String,
        L_OBJECT_EXTENSION: String,
        L_AVATAR_LINK: String?,
        L_AVATAR_SERVER: String?,
        L_ORIGINAL_AVATAR_SIZE: Int?,
        L_SMALL_AVATAR: ByteArray?,
        L_BIG_AVATAR: ByteArray?,
        L_IS_TEMP: Int,
        L_IS_DOWNLOAD: Int,
        L_LAST_USED: Long
    ) {

        OBJECT_ID = L_OBJECT_ID
        AVATAR_ID = L_AVATAR_ID ?: ""
        OBJECT_NAME = L_OBJECT_NAME
        OBJECT_SIZE = L_OBJECT_SIZE
        OBJECT_LENGTH_SECONDS = L_OBJECT_LENGTH_SECONDS
        OBJECT_SERVER = L_OBJECT_SERVER
        OBJECT_LINK = L_OBJECT_LINK
        OBJECT_EXTENSION = L_OBJECT_EXTENSION
        AVATAR_LINK = L_AVATAR_LINK ?: ""
        AVATAR_SERVER = L_AVATAR_SERVER ?: ""
        ORIGINAL_AVATAR_SIZE = L_ORIGINAL_AVATAR_SIZE ?: 0
        SMALL_AVATAR = L_SMALL_AVATAR
        BIG_AVATAR = L_BIG_AVATAR
        IS_TEMP = L_IS_TEMP
        IS_DOWNLOAD = L_IS_DOWNLOAD
        LAST_USED = L_LAST_USED


    }

    init {
        ensureNeverFrozen()

        if (IS_DOWNLOAD == 0) {
            FILE_FULL_NAME = createTempFullFileName(OBJECT_LINK, OBJECT_EXTENSION)
        } else {
            FILE_FULL_NAME = createFullFileName(OBJECT_LINK, OBJECT_EXTENSION)
        }
    }


    fun createFullFileName(LFileName: String, LFileExtencion: String): String {
        return rootPath.plus(LFileName.substring(0, 2)).plus(slash).plus(LFileName).plus(".").plus(LFileExtencion)
    }

    private fun createTempFullFileName(LFileName: String, LFileExtencion: String): String {
        return pathTemp.plus(LFileName.substring(0, 2)).plus(slash).plus(LFileName).plus(".").plus(LFileExtencion)
    }

    fun ReturnDownloadedFullFileName(): String {
        return createFullFileName(OBJECT_LINK, OBJECT_EXTENSION)
    }

    fun SET_BIG_AVATAR(v: ByteArray?, avatar_id: String) {
        if (avatar_id == AVATAR_ID && v != null) {
            BIG_AVATAR = v
        }
    }


    fun setIsPerminent() {
        if (IS_TEMP == 1) {
            IS_TEMP = 0
        }
        AddNewSaveMedia(this)
    }

    @JsName("getIS_TEMP")
    fun getIS_TEMP(): Boolean {
        return IS_TEMP == 1

    }

    @JsName("setLAST_USED")
    fun setLAST_USED() {
        AddNewSaveMedia(this)
    }

    @JsName("verifysDownLoaded")
    private suspend fun verifysDownLoaded(): Boolean {
        val f = CrossPlatformFile(FILE_FULL_NAME)
        if (!f.exists() || f.isDirectory()) {
            return false
        }
        return true
    }

    @JsName("deleteFile")
    private fun deleteFile() {
        CoroutineScope(Dispatchers.Default).launch {
            val f = CrossPlatformFile(FILE_FULL_NAME)
            f.delete()
        }
    }


    companion object {

        fun AddNewSaveMedia(kSaveMedia: KSaveMedia): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {

                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            kSaveMedia.LAST_USED = DateTime.nowUnixLong()
                            KSaveMediaLock.withLock {
                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("AddNewSaveMedia is running")
                                }
                                SAVE_MEDIA[kSaveMedia.OBJECT_ID] = kSaveMedia
                                val arr: ArrayList<KSaveMedia> = ArrayList()
                                arr.add(kSaveMedia)
                                Sqlite_service.InsertSendMedia(arr)
                                return@withTimeoutOrNull true
                            }

                        } catch (e: my_user_exceptions_class){
                            throw e
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KSaveMedia",
                                l_function_name = "AddNewSaveMedia",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                        return@withTimeoutOrNull false
                    }
                }?: throw my_user_exceptions_class(
                    l_class_name = "KSaveMedia",
                    l_function_name = "AddNewSaveMedia",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }.toPromise(EmptyCoroutineContext)


        @JsName("LOAD_SAVE_MEDIA")
        suspend fun LOAD_SAVE_MEDIA(irr: ArrayList<KSaveMedia>) {
            try {
                try {
                    KSaveMediaLock.lock()
                    irr.forEach {

                        if (Constants.IS_VERIFY_FILES_DOWNLOADED_FOR_SAVE_MEDIA == 1) {
                            if (it.verifysDownLoaded()) {
                                SAVE_MEDIA[it.OBJECT_ID] = it
                            } else {
                                it.deleteFile()
                            }
                        } else {
                            SAVE_MEDIA[it.OBJECT_ID] = it
                        }
                    }
                } catch (e: my_user_exceptions_class){
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KSaveMedia",
                        l_function_name = "LOAD_SAVE_MEDIA",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.message
                    )
                } finally {
                    KSaveMediaLock.unlock()
                }

            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        }


        fun DeleteSaveMedia(name: String): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KSaveMediaLock.withLock {
                                val s = SAVE_MEDIA[name]
                                if (s != null) {
                                    val arr: ArrayList<KSaveMedia> = ArrayList()
                                    arr.add(s)
                                    Sqlite_service.DeleteSaveMedia(arr)
                                    s.deleteFile()
                                }
                                return@withTimeoutOrNull true
                            }

                        } catch (e: my_user_exceptions_class){
                            throw e
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KSaveMedia",
                                l_function_name = "DeleteSaveMedia",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                        return@withTimeoutOrNull false
                    }
                }?: throw my_user_exceptions_class(
                    l_class_name = "KSaveMedia",
                    l_function_name = "DeleteSaveMedia",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }.toPromise(EmptyCoroutineContext)

        fun ClearSaveMedia(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KSaveMediaLock.withLock {
                                val arr: ArrayList<KSaveMedia> = ArrayList()
                                SAVE_MEDIA.forEach {
                                    arr.add(it.value)
                                    it.value.deleteFile()
                                }
                                SAVE_MEDIA.clear()
                                Sqlite_service.DeleteSaveMedia(arr)
                                return@withTimeoutOrNull true
                            }

                        } catch (e: my_user_exceptions_class){
                            throw e
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KSaveMedia",
                                l_function_name = "ClearSaveMedia",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                        return@withTimeoutOrNull false
                    }
                }?: throw my_user_exceptions_class(
                    l_class_name = "KSaveMedia",
                    l_function_name = "ClearSaveMedia",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }.toPromise(EmptyCoroutineContext)

        @JsName("RE_LOAD_SAVE_MEDIA")
        fun RE_LOAD_SAVE_MEDIA(): Job {
            return Sqlite_service.LoadSaveMedia()
        }
    }
}