/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused", "UnnecessaryOptInAnnotation")

package p_client

import CrossPlatforms.PrintInformation
import CrossPlatforms.getMyDeviceId
import JSOCKETDB.AUFDB
import Tables.KChat
import atomic.AtomicBoolean
import com.soywiz.korio.async.await

import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.lang.substr
import com.soywiz.krypto.md5
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import lib_exceptions.my_user_exceptions_class
import p_jsocket.Connection
import p_jsocket.Constants
import p_jsocket.Constants.myConnectionsID
import p_jsocket.JSOCKET_Instance
import sql.Sqlite_service
import sql.db
import sql.sqlDriver
import kotlin.coroutines.CoroutineContext
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.*


/**
 *
 * @author User
 */

//@JsName("InitJsocketJob")
var InitJsocketJob: Job = Job()



val isInitialised: AtomicBoolean = AtomicBoolean(false)




//@JsName("InitJsocket")
@Suppress("UnnecessaryOptInAnnotation")
@OptIn(ExperimentalTime::class, InternalAPI::class,  KorioExperimentalApi::class)
class InitJsocket(_lFileDir: String, _lDeviceId: String, _OSName: String, _sqlDriver: SqlDriver? = null) : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob() + SupervisorJob()

    private val InitJsocketScope = CoroutineScope(coroutineContext)

    private val FileDir = _lFileDir.trim()
    private val DeviceId = _lDeviceId.trim()
    private val OSName = _OSName.trim().substr(0, 30)
    private val SqlDriver = _sqlDriver


    init {

        if (DeviceId.isNotEmpty()) Constants.myDeviceId = (DeviceId.replace(":", "").replace(";", "")
            .encodeToByteArray().md5().hex.substr(0, 16).uppercase())

        if (OSName.isNotEmpty()) {
            Constants.myConnectionContext = OSName
        }

        if (SqlDriver != null) {
            sqlDriver = SqlDriver
        }
        InitJsocketJob = InitJsocketScope.launch {
            try {
                try {
                    JSOCKET_Instance.initDirectories(FileDir)
                    if (Constants.myDeviceId.isEmpty()) {
                        launch {
                            Constants.myDeviceId = (getMyDeviceId().replace(":", "").replace(";", "")
                                .encodeToByteArray().md5().hex.substr(0, 16).uppercase())
                        }.join()
                    }
                    if (sqlDriver != null) {
                        db = AUFDB(sqlDriver!!)
                    }
                    Sqlite_service.Connect().join()
                    if (Constants.FIX_INTO_SCREEN_ERRORS == 1){
                        PrintInformation.PRINT_INFO("Local DB finish connecting")
                    }
                    //Sqlite_service.InitializeCommands().join()
                    //Sqlite_service.removeSyncJsocket().join()
                    KChat.GET_CHATS(null).await()

                    if(myConnectionsID > 0L){
                        Connection.RE_SEND_REQUEST_PROFILE(await_answer = true)
                    }

                    isInitialised.setNewValue(true)
                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "InitJsocket",
                        l_function_name = "Init",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        }
    }
}