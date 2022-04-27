/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_client

import CrossPlatforms.WriteExceptionIntoFile
import CrossPlatforms.getMyDeviceId
import CrossPlatforms.slash
import JSOCKET.AvaClubDB
import Tables.myDeviceId
import atomic.AtomicBoolean
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.lang.substr
import com.soywiz.krypto.md5
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.util.*
import io.ktor.utils.io.core.ExperimentalIoApi
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import kotlinx.coroutines.*
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

val isInitialised : AtomicBoolean = AtomicBoolean(false);


@InternalAPI
@JsName("InitJsocket")
class InitJsocket(_lFileDir: String, _lDeviceId: String?, _sqlDriver: SqlDriver? = null): CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    private val InitJsocketScope = CoroutineScope(coroutineContext)

    private val FileDir = _lFileDir.trim() + slash
    private val DeviceId = _lDeviceId?.trim() ?: ""
    private val SqlDriver = _sqlDriver
    private val listener: Listener? = Listener.get_Instance()


    init {

        if(DeviceId.isNotEmpty()) myDeviceId.setNewValue(DeviceId.replace(":", "").replace(";", "")
                .encodeToByteArray().md5().hex.substr(0, 16).uppercase())

        if(SqlDriver != null){
            sqlDriver = SqlDriver
        }
        InitJsocketJob = InitJsocketScope.launch {
            try{
                if(myDeviceId.value.isEmpty()) {
                        launch {
                            myDeviceId.setNewValue(getMyDeviceId().replace(":", "").replace(";", "")
                                                                .encodeToByteArray().md5().hex.substr(0, 16).uppercase())
                        }.join()
                }
                println("end myDeviceId")
                if(sqlDriver != null) {
                    db = AvaClubDB(sqlDriver!!)
                }
                initDirectories(FileDir)
                println("end initDirectories")
                try {
                    Sqlite_service.Connect().join()
                }
                catch (ex: Exception){}
                println("end Connect")
                //Sqlite_service.InitializeCommands().join()
                //Sqlite_service.removeSyncJsocket().join()
                isInitialised.setNewValue(true)
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "InitJsocket.init")
            }
        }
    }
}