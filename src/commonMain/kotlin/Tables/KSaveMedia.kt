/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import CrossPlatforms.CrossPlatformFile
import CrossPlatforms.PrintInformation
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
import p_jsocket.JSOCKET_Instance
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
val SAVE_MEDIA: MutableMap<String, KSaveMedia> = mutableMapOf()


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
private val KSaveMediaLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KSaveMedia")
class KSaveMedia(
    val L_OBJECT_LINK: String,
    val L_OBJECT_SIZE: Long,
    val L_OBJECT_LENGTH_SECONDS: Int,
    val L_OBJECT_EXTENSION: String,
    L_AVATAR_ID: String?,
    var L_IS_TEMP: Int = 0,
    var L_LAST_USED: Long = 0L,
    var IS_DOWNLOAD: Int = 0
) {

    var FILE_FULL_NAME: String = ""

    var AVATAR_ID: String? = L_AVATAR_ID

    init {
        ensureNeverFrozen()

        FILE_FULL_NAME = if (IS_DOWNLOAD == 0) {
            createTempFullFileName(L_OBJECT_LINK, L_OBJECT_EXTENSION)
        } else {
            createFullFileName(L_OBJECT_LINK, L_OBJECT_EXTENSION)
        }
    }


    fun createFullFileName(LFileName: String, LFileExtencion: String): String {
        return JSOCKET_Instance.RootPath.plus(LFileName.substring(0, 2)).plus(slash).plus(LFileName).plus(".").plus(LFileExtencion)
    }

    private fun createTempFullFileName(LFileName: String, LFileExtencion: String): String {
        return JSOCKET_Instance.pathTemp.plus(LFileName.substring(0, 2)).plus(slash).plus(LFileName).plus(".").plus(LFileExtencion)
    }

    fun ReturnDownloadedFullFileName(): String {
        return createFullFileName(L_OBJECT_LINK, L_OBJECT_EXTENSION)
    }


    fun setIsPerminent() {
        if (L_IS_TEMP == 1) {
            L_IS_TEMP = 0
        }
        L_LAST_USED = DateTime.nowUnixLong()
        AddNewSaveMedia(this)
    }

    fun setIsNotPerminent() {
        if (L_IS_TEMP == 0) {
            L_IS_TEMP = 1
        }
        AddNewSaveMedia(this)
    }

    @JsName("getIS_TEMP")
    fun getIS_TEMP(): Boolean {
        return L_IS_TEMP == 1

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
                            kSaveMedia.L_LAST_USED = DateTime.nowUnixLong()
                            KSaveMediaLock.withLock {
                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("AddNewSaveMedia is running")
                                }
                                SAVE_MEDIA[kSaveMedia.L_OBJECT_LINK] = kSaveMedia
                                val arr: ArrayList<KSaveMedia> = ArrayList()
                                arr.add(kSaveMedia)
                                Sqlite_service.InsertSaveMedia(arr)
                                return@withTimeoutOrNull true
                            }

                        } catch (e: my_user_exceptions_class) {
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
                } ?: throw my_user_exceptions_class(
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
                    KSaveMediaLock.withLock {
                        irr.forEach {

                            if (Constants.IS_VERIFY_FILES_DOWNLOADED_FOR_SAVE_MEDIA == 1) {
                                if (it.verifysDownLoaded()) {
                                    SAVE_MEDIA[it.L_OBJECT_LINK] = it
                                } else {
                                    it.deleteFile()
                                }
                            } else {
                                SAVE_MEDIA[it.L_OBJECT_LINK] = it
                            }
                        }
                    }
                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KSaveMedia",
                        l_function_name = "LOAD_SAVE_MEDIA",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.message
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        }


        fun DeleteSaveMedia(object_link: String): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KSaveMediaLock.withLock {
                                val s = SAVE_MEDIA[object_link]
                                if (s != null) {
                                    val arr: ArrayList<KSaveMedia> = ArrayList()
                                    arr.add(s)
                                    Sqlite_service.DeleteSaveMedia(arr)
                                    s.deleteFile()
                                }
                                return@withTimeoutOrNull true
                            }
                        } catch (e: my_user_exceptions_class) {
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
                } ?: throw my_user_exceptions_class(
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

                        } catch (e: my_user_exceptions_class) {
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
                } ?: throw my_user_exceptions_class(
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