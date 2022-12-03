/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_client

import CrossPlatforms.PrintInformation
import CrossPlatforms.getMyDeviceId
import CrossPlatforms.slash
import JSOCKETDB.AUFDB
import atomic.AtomicBoolean
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.lang.substr
import com.soywiz.krypto.md5
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.util.*
import kotlinx.coroutines.*
import lib_exceptions.my_user_exceptions_class
import p_jsocket.Constants
import p_jsocket.initDirectories
import sql.Sqlite_service
import sql.db
import sql.sqlDriver
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author User
 */

@JsName("InitJsocketJob")
var InitJsocketJob: Job = Job()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val isInitialised: AtomicBoolean = AtomicBoolean(false)


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
@JsName("InitJsocket")
class InitJsocket(_lFileDir: String, _lDeviceId: String?, _sqlDriver: SqlDriver? = null) : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    private val InitJsocketScope = CoroutineScope(coroutineContext)

    private val FileDir = _lFileDir.trim() + slash
    private val DeviceId = _lDeviceId?.trim() ?: ""
    private val SqlDriver = _sqlDriver


    init {

        if (DeviceId.isNotEmpty()) Constants.myDeviceId = (DeviceId.replace(":", "").replace(";", "")
            .encodeToByteArray().md5().hex.substr(0, 16).uppercase())

        if (SqlDriver != null) {
            sqlDriver = SqlDriver
        }
        InitJsocketJob = InitJsocketScope.launch {
            try {
                try {
                    if (Constants.myDeviceId.isEmpty()) {
                        launch {
                            Constants.myDeviceId = (getMyDeviceId().replace(":", "").replace(";", "")
                                .encodeToByteArray().md5().hex.substr(0, 16).uppercase())
                        }.join()
                    }
                    PrintInformation.PRINT_INFO("end myDeviceId")
                    if (sqlDriver != null) {
                        db = AUFDB(sqlDriver!!)
                    }
                    initDirectories(FileDir)
                    PrintInformation.PRINT_INFO("end initDirectories")
                    Sqlite_service.Connect().join()
                    PrintInformation.PRINT_INFO("end Connect")
                    //Sqlite_service.InitializeCommands().join()
                    //Sqlite_service.removeSyncJsocket().join()
                    isInitialised.setNewValue(true)
                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "InitJsocket",
                        l_function_name = "Init",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.message
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        }
    }
}