/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("DuplicatedCode", "RedundantSuspendModifier", "unused")

package sql

import CrossPlatforms.CrossPlatformFile
import CrossPlatforms.WriteExceptionIntoFile
import Tables.*
import com.soywiz.klock.DateTime
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.InternalAPI
import io.ktor.util.collections.ConcurrentMap
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import p_client.*
import p_jsocket.*
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 *
 * @author Oleg
 */
@ExperimentalStdlibApi
@JsName("Sqlite_service")
object Sqlite_service : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default

    val Sqlite_serviceScope = CoroutineScope(coroutineContext) + SupervisorJob()

    val Connection: MySQLConnection = MySQLConnection("AvaClubDB")

    ///////////////////////////////////big avatars///////////////////////////

    private val statBIG_AVATARS = Connection.createStatement()
    @InternalAPI
    private val lockBIG_AVATARS = Mutex()

    @InternalAPI
    @JsName("InsertBigAvatars")
    fun InsertBigAvatars(kBigAvatar: KBigAvatar) {
        Sqlite_serviceScope.launch {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    statBIG_AVATARS.INSERT_BIG_AVATAR(kBigAvatar)
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.InsertBigAvatars")
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        }
    }

    @InternalAPI
    @JsName("SelectBigAvatar")
    suspend fun SelectBigAvatar(OBJECTS_ID: String, IS_UPDATE_LAST_USE: Boolean): KBigAvatar? {
        try {
            withTimeoutOrNull(CLIENT_TIMEOUT) {
                lockBIG_AVATARS.lock()
                if(IS_UPDATE_LAST_USE){
                    statBIG_AVATARS.UPDATE_BIG_AVATAR_LAST_USE(OBJECTS_ID, DateTime.nowUnixLong())
                }
                return@withTimeoutOrNull statBIG_AVATARS.SELECT_BIG_AVATAR(OBJECTS_ID)
            }
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "Sqlite_service.SelectBigAvatar")
            return null
        } finally {
            statBIG_AVATARS.clear_parameters()
            lockBIG_AVATARS.unlock()
        }
        return null
    }

    @InternalAPI
    @JsName("UpdateBigAvatarsLastUse")
    suspend fun UpdateBigAvatarsLastUse(OBJECTS_ID: String) {
        Sqlite_serviceScope.launch {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    statBIG_AVATARS.UPDATE_BIG_AVATAR_LAST_USE(OBJECTS_ID, DateTime.nowUnixLong())
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.SelectBigAvatar")
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        }
    }

    @InternalAPI
    @JsName("DeleteBigAvatars")
    fun DeleteBigAvatars(kBigAvatar: KBigAvatar) {
        Sqlite_serviceScope.launch {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    statBIG_AVATARS.INSERT_BIG_AVATAR(kBigAvatar)
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.InsertBigAvatars")
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        }
    }

    @InternalAPI
    @JsName("ClearBigAvatars")
    fun ClearBigAvatars() {
        Sqlite_serviceScope.launch {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    statBIG_AVATARS.CLEAR_BIG_AVATARS()
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.InsertBigAvatars")
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        }
    }

    @InternalAPI
    @JsName("LoadBigAvatarsIds")
    private fun LoadBigAvatarsIds() {
        Sqlite_serviceScope.launch {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    var arr:ArrayList<String> = statBIG_AVATARS.SELECT_BIGAVATARS_ALL_ID()
                    KBigAvatar.LOAD_BIG_AVATAR_ADS(arr)
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.LoadBigAvatarsIds")
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        }
    }

    @InternalAPI
    @JsName("LoadBigAvatars")
    private fun LoadBigAvatars() {
        Sqlite_serviceScope.launch {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    var arr:ArrayList<KBigAvatar> = statBIG_AVATARS.SELECT_BIGAVATARS_ALL()
                    KBigAvatar.LOAD_BIG_AVATARS(arr)
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.LoadBigAvatars")
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        }
    }

    ///////////// Exceptions ///////////////////////////

    private val statEXCEPTIONS = Connection.createStatement()
    @InternalAPI
    private val lockEXCEPTIONS = Mutex()

    @InternalAPI
    @JsName("InsertExceptions")
    fun InsertExceptions(ans: ArrayList<KExceptions.KException>) {
        Sqlite_serviceScope.launch {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockEXCEPTIONS.lock()
                    ans.forEach {
                        statEXCEPTIONS.INSERT_EXCEPTION(it)
                    }
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.InsertExceptions")
            } finally {
                statEXCEPTIONS.clear_parameters()
                lockEXCEPTIONS.unlock()
            }
        }
    }

    @InternalAPI
    @JsName("LoadExceptions")
    fun LoadExceptions(){
        Sqlite_serviceScope.launch {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockEXCEPTIONS.lock()
                    val arr: ArrayList<KExceptions.KException> = statEXCEPTIONS.SELECT_EXCEPTION_ALL()
                    KExceptions.INSERT_EXCEPTIONS(arr, false)
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.LoadExceptions")
            } finally {
                statEXCEPTIONS.clear_parameters()
                lockEXCEPTIONS.unlock()
            }
        }
    }

    /////////////cash data///////////////////////////

    private val statCASHDATA = Connection.createStatement()
    @InternalAPI
    private val lockCASHDATA = Mutex()

    @InternalAPI
    @JsName("InsertCashData")
    fun InsertCashData(lANSWER_TYPE: ANSWER_TYPE) {
        Sqlite_serviceScope.launch {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHDATA.lock()
                    statCASHDATA.INSERT_CASHDATA(lANSWER_TYPE)
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.InsertCashData")
            } finally {
                statCASHDATA.clear_parameters()
                lockCASHDATA.unlock()
            }
        }
    }




    @InternalAPI
    internal val SAVEMEDIA: ConcurrentMap<String, KSaveMedia> = ConcurrentMap()

    @InternalAPI
    val CHATS: ConcurrentMap<String, KChat> = ConcurrentMap()


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private const val TIMEOUTCLEANERSECONDS = 20
    private const val VERIFYTABLES: Long = 60000L //60 seconds
    private var NextTimeVerifyTables: Long = DateTime.nowUnixLong() + VERIFYTABLES

    @ExperimentalIoApi
    @KtorExperimentalAPI
    @InternalAPI
    @ExperimentalStdlibApi
    @DangerousInternalIoApi
    @ExperimentalTime
    private val Cleaner = KorosTimerTask.start(
            delay = Duration.seconds(VERIFYTABLES),
            repeat = Duration.seconds(VERIFYTABLES)
    ) { removeOldAll() }

    @ExperimentalIoApi
    @ExperimentalStdlibApi
    private var CurrentTask: Jsocket? = null
    private val MySend_JSOCKETs: Send_JSOCKETs = Send_JSOCKETs()

    @InternalAPI
    private val lock = Mutex()
    private const val time_wait = 10000L
    private var curCommand: Command? = null

    ////////////////////////////////////////////////////////////////////////////////    
    @KtorExperimentalAPI
    @DangerousInternalIoApi
    @ExperimentalTime
    @ExperimentalIoApi
    @ExperimentalStdlibApi
    @InternalAPI

    private suspend fun removeOldAll() {
        try {
            withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                lock.lock()
                if (CurrentTask != null && myConnectionsID.value != 0L) {
                    try {
                        if (CurrentTask!!.ANSWER_TYPEs != null && !CurrentTask!!.ANSWER_TYPEs!!.isEmpty()) {
                            CurrentTask!!.ANSWER_TYPEs?.clear()
                        }
                        if (CurrentTask!!.check_sum > 0L) {
                            try {
                                val lasUpdate =
                                        Connection.createStatement().SELECT_LASTUPDATE(CurrentTask!!.check_sum)
                                CurrentTask!!.last_date_of_update = lasUpdate ?: 0L
                            } catch (e: Exception) {
                                WriteExceptionIntoFile(e, "Sqlite_service.SELECT_LASTUPDATE")
                                CurrentTask!!.last_date_of_update = 0L
                            }
                        }
                        var j =
                                Jsocket(CurrentTask!!)
                        j = MySend_JSOCKETs.send_JSOCKET_with_TimeOut(j, null, true)
                        if (curCommand == null || curCommand!!.commands_id != j.just_do_it) {
                            curCommand = Commands[j.just_do_it]
                        }
                        if (j.just_do_it_successfull == "0"
                                && j.ANSWER_TYPEs != null
                                && j.ANSWER_TYPEs!!.isNotEmpty()
                                && curCommand!!.isCaching
                        ) {
                            CurrentTask!!.ANSWER_TYPEs = j.ANSWER_TYPEs
                            CurrentTask!!.last_date_of_update = j.last_date_of_update
                            lUpdateCashData()
                        }
                        CurrentTask!!.just_do_it_successfull = j.just_do_it_successfull
                        CurrentTask!!.db_massage = j.db_massage
                        CurrentTask!!.setAnswer()
                    } catch (ex: Exception) {
                        WriteExceptionIntoFile(ex, "Sqlite_service.removeOldAll")
                    }
                }
            }
        } catch (ex: Exception) {
        } finally {
            lock.unlock()
            if (NextTimeVerifyTables < DateTime.nowUnixLong()) {
                DeleteLastSaveMedia(0)
                DeleteLastSaveMedia(1)
            }
            NextTimeVerifyTables = DateTime.nowUnixLong() + VERIFYTABLES
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @ExperimentalIoApi
    @ExperimentalStdlibApi
    @InternalAPI
    @JsName("removeSyncJsocket")
    fun removeSyncJsocket() =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                        if (CurrentTask == null) {
                            CurrentTask = Jsocket()
                            CurrentTask!!.connection_id = myConnectionsID.value
                            CurrentTask!!.connection_coocki = myConnectionsCoocki.value
                        }
                        CurrentTask!!.just_do_it = 1011000068 //RE_SEND_REQUEST_PROFILE
                        CurrentTask!!.ANSWER_TYPEs?.clear()
                        CurrentTask!!.content = null
                    }
                } catch (e: Exception) {
                } finally {
                    lock.unlock()
                }
            }

    ////////////////////////////////////////////////////////////////////////////////
    @KtorExperimentalAPI
    @ExperimentalTime
    @DangerousInternalIoApi
    @ExperimentalIoApi
    @InternalAPI
    @ExperimentalStdlibApi
    @KorioExperimentalApi
    @JsName("Connect")
    internal fun Connect() = Sqlite_serviceScope.launch {
        /*Connection = if (InitJsocket.connection == null) {
            org.sqlite.JDBC.createConnection(CON_STR, properties)
        } else {
            InitJsocket.connection
        }*/
        val statement = Connection.createStatement()
        try {
            lock.lock()
            statement.connect()
            try {
                statement.TABLE_METADATA()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_METADATA")
            }
            try {
                statement.TABLE_CASHDATA()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_CASHDATA")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_1()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_1")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_2()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_2")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_3()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_3")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_4()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_4")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_5()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_5")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_6()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_6")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_7()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_7")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_8()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_8")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_9()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_9")
            }
            try {
                statement.TRIGGER_CASHDATA_BLOB_10()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_BLOB_10")
            }
            try {
                statement.TRIGGER_CASHDATA_DELETING_RECORDS()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CASHDATA_DELETING_RECORDS")
            }
            try {
                statement.TABLE_REGDATA()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_REGDATA")
            }
            try {
                statement.TRIGGER_INSERT_REGDATA()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_INSERT_REGDATA")
            }
            try {
                statement.TABLE_COMMANDS()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_COMMANDS")
            }
            try {
                statement.TABLE_SAVEMEDIA()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_SAVEMEDIA")
            }
            try {
                statement.INDEX_SAVEMEDIA()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.INDEX_SAVEMEDIA")
            }
            try {
                statement.TABLE_BIG_AVATARS()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_BIG_AVATARS")
            }
            try {
                statement.INDEX_BIG_AVATARS()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.INDEX_BIG_AVATARS")
            }
            try {
                statement.TRIGGER_CONTROL_COUNT_BIG_AVATARS()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CONTROL_COUNT_BIG_AVATARS")
            }
            try {
                statement.TABLE_SENDMEDIA()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_SENDMEDIA")
            }
            try {
                statement.TRIGGER_INSERT_SENDMEDIA()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_INSERT_SENDMEDIA")
            }
            try {
                statement.TABLE_LASTUPDATE()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_LASTUPDATE")
            }
            try {
                statement.INDEX_LASTUPDATE()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.INDEX_LASTUPDATE")
            }
            try {
                statement.TRIGGER_DELETE_LASTUPDATE()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_DELETE_LASTUPDATE")
            }
            try {
                statement.TRIGGER_INSERT_LASTUPDATE()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_INSERT_LASTUPDATE")
            }
            try {
                statement.TRIGGER_CONTROL_COUNT_CASH()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CONTROL_COUNT_CASH")
            }
            try {
                statement.TABLE_CHATS()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_CHATS")
            }
            try {
                statement.TRIGGER_DELETE_CHATS()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_DELETE_CHATS")
            }
            try {
                statement.TRIGGER_CONTROL_COUNT_CHATS()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CONTROL_COUNT_CHATS")
            }
            try {
                statement.TABLE_MESSEGES()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TABLE_MESSEGES")
            }
            try {
                statement.TRIGGER_CONTROL_COUNT_MESSEGES()
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.TRIGGER_CONTROL_COUNT_MESSEGES")
            }
        } finally {
            lock.unlock()
        }

        SelectAllRegData()
        SelectAllComands()
        removeSyncJsocket().join()
        SelectAllMetaData()
        InitChats()
        /*CHATS.forEach { v ->
            println("CHATS_ID = ${v.value.getCHATS_ID()}")
            v.value.MESSEGES.forEach { k ->
                println("messeges id ${k.key}}")
            }
        }*/
        try {
            lock.lock()
            try {
                val rs = statement.SELECT_ALL_SAVEMEDIA(myConnectionsID.value)
                while (rs.isNotEmpty()) {
                    val s = rs.removeFirst()
                    val file = CrossPlatformFile(s.getOBJECT_FULL_NAME())
                    if (!file.isExists() || file.isPath()) {
                        continue
                    }
                    if (file.size() != s.getOBJECTS_SIZE()) {
                        file.delete()
                        continue
                    }
                    SAVEMEDIA[s.getOBJECTS_ID()] = s
                }

            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.SELECT_ALL_SAVEMEDIA")
            }
            try {
                val rs = statement.SELECT_ALL_SENDMEDIA(myConnectionsID.value)
                while (rs.isNotEmpty()) {
                    val r = rs.removeFirst()
                    SENDMEDIA[r.getOBJECTS_ID()] = r
                }
            } catch (e: Exception) {
                WriteExceptionIntoFile(e, "Sqlite_service.Connect.SELECT_ALL_SENDMEDIA")
            }
        } finally {
            lock.unlock()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    private suspend fun InsertCashData(lANSWER_TYPE: ANSWER_TYPE, lcheck_sum: Long, statement: SQLStatement) {
        lANSWER_TYPE.LONG_9 = lcheck_sum
        statement.INSERT_CASHDATA(lANSWER_TYPE, lcheck_sum)
    }

    ////////////////////////////////////////////////////////////////////////////////
    @DangerousInternalIoApi
    @ExperimentalTime
    @ExperimentalIoApi
    @ExperimentalStdlibApi
    @InternalAPI
    private suspend fun lUpdateCashData() {
        try {
            val statement = Connection.createStatement()
            if (CurrentTask != null && CurrentTask?.ANSWER_TYPEs != null && CurrentTask?.ANSWER_TYPEs!!.isNotEmpty()) {
                while (CurrentTask!!.ANSWER_TYPEs!!.isNotEmpty()) {
                    val v = CurrentTask!!.ANSWER_TYPEs!!.removeFirst()
                    try {
                        InsertCashData(
                                v,
                                CurrentTask!!.check_sum,
                                statement
                        )
                    } catch (ex: Exception) {
                    }
                }
                statement.INSERT_LASTUPDATE(
                        CurrentTask!!.check_sum,
                        CurrentTask!!.last_date_of_update,
                        CurrentTask!!.last_date_of_update,
                        0
                )
                CurrentTask!!.last_date_of_update = 0L
                CurrentTask =
                        lSelectCashData(
                                CurrentTask!!,
                                false
                        )
            }
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "Sqlite_service.lUpdateCashData")
        }
    }

    @KtorExperimentalAPI
    @ExperimentalIoApi
    @InternalAPI
    @DangerousInternalIoApi
    @ExperimentalTime
    @ExperimentalStdlibApi
    @JsName("SelectCashData")
    internal suspend fun SelectCashData(ljsocket: Jsocket): Jsocket? {
        return withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
            return@withTimeoutOrNull try {
                lock.lock()
                lSelectCashData(ljsocket, true)
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.lUpdateCashData")
                ljsocket
            } finally {
                lock.unlock()
            }
        }
    }

    @KtorExperimentalAPI
    @ExperimentalTime
    @DangerousInternalIoApi
    @ExperimentalIoApi
    @InternalAPI
    @ExperimentalStdlibApi
    private suspend fun lSelectCashData(ljsocket: Jsocket, update_cash: Boolean): Jsocket {
        val j: Jsocket = ljsocket
        if (j.ANSWER_TYPEs != null && j.ANSWER_TYPEs!!.size > 0) {
            j.ANSWER_TYPEs!!.clear()
        }
        if (update_cash) {
            j.create_check_sum(true)
            CurrentTask = j
        }
        val statement = Connection.createStatement()
        j.ANSWER_TYPEs =
                withContext(Sqlite_serviceScope.coroutineContext) { statement.SELECT_CASHDATA(j.check_sum) }
        if (update_cash && j.ANSWER_TYPEs != null && !j.ANSWER_TYPEs!!.isEmpty()) {
            statement.UPDATE_LAST_USE_LASTUPDATE(DateTime.nowUnixLong(), j.check_sum)
        }
        if (update_cash) {
            Sqlite_serviceScope.launch { Cleaner.execute() }
        }
        return j
    }

    @ExperimentalStdlibApi
    @InternalAPI
    @JsName("ClearCashData")
    internal fun ClearCashData() =
            Sqlite_serviceScope.launch {
                withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                    try {

                        lock.lock()
                        val statement = Connection.createStatement()
                        statement.CLEAR_CASHDATA()

                        statement.CLEAR_LASTUPDATE()
                    } catch (ex: Exception) {
                        WriteExceptionIntoFile(ex, "Sqlite_service.ClearCashData")
                    } finally {
                        lock.unlock()
                    }
                }
            }

    @ExperimentalStdlibApi
    @InternalAPI
    private fun DeleteLastUdate(lcash_sum: Long) {
        Sqlite_serviceScope.launch {
            try {
                Connection.createStatement().DELETE_LASTUPDATE(lcash_sum)
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Sqlite_service.DeleteLastUdate")
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @ExperimentalIoApi
    @InternalAPI
    @JsName("InsertRegData")
    fun InsertRegData() =
            Sqlite_serviceScope.launch {
                try {
                    Connection.createStatement().INSERT_REGDATA(
                            myConnectionsID.value,
                            myConnectionsCoocki.value,
                            myDataBaseID.value,
                            myLang.value,
                            myRequestProfile.value,
                            myAccountProfile.value
                    )
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.InsertRegData")
                } finally {
                    removeSyncJsocket()
                }
            }

    @ExperimentalIoApi
    @InternalAPI
    @JsName("SelectAllRegData")
    private suspend fun SelectAllRegData() {
        try {
            val rs = withContext(Sqlite_serviceScope.coroutineContext) {
                Connection.createStatement().SELECT_ALL_REGDATA()
            }

            if (rs != null) {
                myConnectionsID.setNewValue(rs.getCONNECTION_ID())
                myConnectionsCoocki.setNewValue(rs.getCONNECTION_COOCKI())
                myDataBaseID.setNewValue(rs.getMY_DATABASE_ID())
                myLang.setNewValue(rs.getLANG())
                myAccountProfile.setNewValue(rs.getACCOUNT_PROFILE())
                Jsocket.setRequestProfile(rs.getREQUEST_PROFILE(), false)
            }
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "Sqlite_service.SelectAllRegData")
        }
    }

    @DangerousInternalIoApi
    @InternalAPI
    @ExperimentalIoApi
    @ExperimentalTime
    @JsName("InitializeRegData")
    internal fun InitializeRegData() =
            Sqlite_serviceScope.launch {
                if (myConnectionsID.value != 0L) {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        try {
                            var l_MyJSocket = Jsocket()
                            lock.lock()
                            l_MyJSocket.just_do_it = 1011000068 // RE_SEND_REQUEST_PROFILE

                            l_MyJSocket = MySend_JSOCKETs.send_JSOCKET_with_TimeOut(l_MyJSocket, null, true)

                            if (l_MyJSocket.just_do_it_successfull == "0") {
                                if (l_MyJSocket.value_id1.trim().isNotEmpty()) {
                                    myDataBaseID.setNewValue(l_MyJSocket.value_id1)
                                }
                                if (l_MyJSocket.value_par2.trim().isNotEmpty()) {
                                    myAccountProfile.setNewValue(l_MyJSocket.value_par2.trim())
                                }
                                if (l_MyJSocket.request_profile.trim().isNotEmpty()) {
                                    Jsocket.setRequestProfile(l_MyJSocket.request_profile, false)
                                }

                                InsertRegData()
                            }

                        } catch (e: Exception) {
                            WriteExceptionIntoFile(e, "Sqlite_service.InitializeRegData")
                        } finally {
                            lock.unlock()
                        }
                    }
                }
            }

    @InternalAPI
    @JsName("ClearRegData")
    internal fun ClearRegData() =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            Connection.createStatement().CLEAR_REGDATA()
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.ClearRegData")
                }
                finally {
                    lock.unlock()
                }
            }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @ExperimentalStdlibApi
    @KtorExperimentalAPI
    @DangerousInternalIoApi
    @InternalAPI
    @ExperimentalTime
    @ExperimentalIoApi
    internal fun InitializeCommands() =
            Sqlite_serviceScope.launch {
                if (myConnectionsID.value != 0L) {
                    try {
                        var l_MyJSocket = Jsocket()
                        l_MyJSocket.just_do_it = 1011000061 // SELECT_COMMANDS
                        l_MyJSocket = MySend_JSOCKETs.send_JSOCKET_with_TimeOut(l_MyJSocket, null, true)
                        if (l_MyJSocket.just_do_it_successfull == "0"
                                && l_MyJSocket.ANSWER_TYPEs != null
                                && !l_MyJSocket.ANSWER_TYPEs!!.isEmpty()
                        ) {
                            InitializeCommands(l_MyJSocket)
                        }
                    } catch (e: Exception) {
                        WriteExceptionIntoFile(e, "Sqlite_service.InitializeCommands()")
                    }
                }
                SelectAllComands()
            }

    @InternalAPI
    @ExperimentalIoApi
    @JsName("InitializeCommands")
    fun InitializeCommands(l_MyJSocket: JSOCKET) =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.withLock {
                            while (l_MyJSocket.ANSWER_TYPEs!!.isNotEmpty()) {
                                val MyANSWER_TYPE: ANSWER_TYPE = l_MyJSocket.ANSWER_TYPEs!!.removeFirst()
                                val comm = KCommands(MyANSWER_TYPE)
                                if (comm.getCOMMANDS_ID() != 0) {
                                    val c = Command(comm)
                                    Commands[c.commands_id] = c
                                } else {
                                    val meta = KMetaData(MyANSWER_TYPE)
                                    META_DATA[meta.getVALUE_NAME()] = meta.getVALUE_VALUE()
                                }
                            }
                            InsertCommands()
                            InsertMetaData()
                        }
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.InitializeCommands(l_MyJSocket: JSOCKET)")
                }
            }

    @InternalAPI
    private suspend fun SelectAllComands() {
        val rs = withContext(Sqlite_serviceScope.coroutineContext) {
            Connection.createStatement().SELECT_ALL_COMMANDS()
        }
        rs.forEach {
            val c = Command(it)
            Commands[c.commands_id] = c
        }
        rs.clear()
    }

    @InternalAPI
    private fun InsertCommands() =
            Sqlite_serviceScope.launch {
                val statement = Connection.createStatement()
                statement.CLEAR_COMMANDS()
                Commands.forEach {
                    try {
                        statement.INSERT_COMMANDS(
                                it.value.commands_id,
                                it.value.commands_access,
                                it.value.commands_profile,
                                it.value.commands_necessarily_fields
                        )
                    } catch (ex: Exception) {
                        WriteExceptionIntoFile(ex, "Sqlite_service.InsertCommands")
                    }
                }
            }

    ////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    private suspend fun SelectAllMetaData() {
        val rs = withContext(Sqlite_serviceScope.coroutineContext) {
            Connection.createStatement().SELECT_ALL_METADATA()
        }
        rs.forEach {
            META_DATA[it.getVALUE_NAME()] = it.getVALUE_VALUE()
        }
        rs.clear()
    }

    @InternalAPI
    private suspend fun SelectMetaData(ValueName: String): Int {
        return try {
            return withContext(Sqlite_serviceScope.coroutineContext) {
                Connection.createStatement().SELECT_METADATA(ValueName)
            }
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "Sqlite_service.SelectMetaData")
            0
        }
    }

    @InternalAPI
    private fun InsertMetaData() =
            Sqlite_serviceScope.launch {
                val statement = Connection.createStatement()
                statement.CLEAR_METADATA()
                META_DATA.forEach {
                    try {
                        statement.INSERT_METADATA(
                                it.key,
                                it.value
                        )
                    } catch (ex: Exception) {
                        WriteExceptionIntoFile(ex, "Sqlite_service.InsertMetaData")
                    }
                }
                KChat.maxCountOfMessegesIntoDB.setNewValue(SelectMetaData("MAX_COUNT_OF_MESSEGES").toLong())
            }

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @JsName("InsertSaveMedia")
    internal fun InsertSaveMedia(lKSaveMedia: KSaveMedia) =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            val crossPlatformFile = CrossPlatformFile(lKSaveMedia.getOBJECT_FULL_NAME())
                            if (!crossPlatformFile.isExists() || crossPlatformFile.isPath()) {
                                throw exc_file_not_exists(lKSaveMedia.getOBJECT_FULL_NAME())
                            }
                            if (crossPlatformFile.size() != lKSaveMedia.getOBJECTS_SIZE()) {
                                crossPlatformFile.delete()
                                throw exc_file_size_is_wrong(
                                        lKSaveMedia.getOBJECT_FULL_NAME(),
                                        lKSaveMedia.getOBJECTS_SIZE()
                                )
                            }
                            SAVEMEDIA[lKSaveMedia.getOBJECTS_ID()] = lKSaveMedia
                            Connection.createStatement().INSERT_SAVEMEDIA(
                                    lKSaveMedia.getOBJECTS_ID(),
                                    lKSaveMedia.getOBJECTS_EXTENSION(),
                                    myConnectionsID.value,
                                    lKSaveMedia.getOBJECT_NAME(),
                                    lKSaveMedia.getOBJECT_INFO(),
                                    lKSaveMedia.getSMALL_AVATAR(),
                                    lKSaveMedia.getIS_TEMP(),
                                    lKSaveMedia.getIS_DOWNLOAD(),
                                    if (lKSaveMedia.getTIME_ADDED() == 0L) {
                                        DateTime.nowUnixLong()
                                    } else {
                                        lKSaveMedia.getTIME_ADDED()
                                    },
                                    lKSaveMedia.getOBJECTS_SIZE()
                            )
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.InsertSaveMedia")
                }
                finally {
                    lock.unlock()
                }
            }

    @InternalAPI
    private fun DeleteLastSaveMedia(lTemp: Int) =
            Sqlite_serviceScope.launch {
                var i = 0
                if (lTemp == 1) {
                    i = META_DATA["MAX_COUNT_OF_TEMP_SAVEMEDIA"]!!
                }
                if (lTemp == 0) {
                    i = META_DATA["MAX_COUNT_OF_SAVEMEDIA"]!!
                }
                if (i != 0) {
                    try {
                        val statement = Connection.createStatement()
                        while (true) {
                            val rs = statement.SELECT_COUNT_SAVEMEDIA(lTemp)
                            if (rs > i) {
                                val rs1 = statement.SELECT_LAST_SAVEMEDIA(lTemp)
                                rs1.forEach {
                                    DeleteSaveMedia(it)
                                }
                                rs1.clear()
                            } else {
                                break
                            }
                        }
                    } catch (ex: Exception) {
                        WriteExceptionIntoFile(ex, "Sqlite_service.DeleteLastSaveMedia")
                    }
                }
            }


    @InternalAPI
    @JsName("DeleteSaveMedia")
    internal fun DeleteSaveMedia(lSAVEMEDIA: String) =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            if (lSAVEMEDIA.isNotEmpty()) {
                                val m: KSaveMedia? = SAVEMEDIA.remove(lSAVEMEDIA)
                                if (m != null) {
                                    val file = CrossPlatformFile(m.getOBJECT_FULL_NAME())
                                    if (file.isExists() && !file.isPath()) {
                                        if (file.delete()) {
                                            Connection.createStatement().DELETE_SAVEMEDIA(lSAVEMEDIA)
                                        } else {
                                            SAVEMEDIA[m.getOBJECTS_ID()] = m
                                        }
                                    } else {
                                        Connection.createStatement().DELETE_SAVEMEDIA(lSAVEMEDIA)
                                    }
                                }
                            }
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.DeleteSaveMedia")
                }
                finally {
                    lock.unlock()
                }
            }


    @InternalAPI
    @JsName("DeleteAllSaveMedia")
    internal fun DeleteAllSaveMedia(IsTemp: Boolean) =
            Sqlite_serviceScope.launch {
                try {
                    val save = SAVEMEDIA.keys
                    save.forEach {
                        val v = SAVEMEDIA[it]
                        if (v != null) {
                            if (v.IsTemp() == IsTemp) {
                                DeleteSaveMedia(v.getOBJECTS_ID())
                            }
                        }
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.DeleteAllSaveMedia")
                }
            }

    @InternalAPI
    @JsName("ClearSaveMedia")
    internal fun ClearSaveMedia() =
            Sqlite_serviceScope.launch {
                try {
                    val save = SAVEMEDIA.keys
                    save.forEach {
                        DeleteSaveMedia(it)
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.DeleteAllSaveMedia")
                }
            }

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @JsName("InsertBigAvata")
    internal fun InsertBigAvatar(lKBigAvatar: KBigAvatar) =
            Sqlite_serviceScope.launch {
                if (lKBigAvatar.getAVATAR()?.size ?: 0 > 0) {
                    try {
                        withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                            lock.lock()
                                lKBigAvatar.getAVATAR()?.let {
                                    Connection.createStatement().INSERT_BIG_AVATARS(
                                            lKBigAvatar.getOBJECTS_ID(),
                                            lKBigAvatar.getTIME_ADDED(),
                                            lKBigAvatar.getIS_TEMP(),
                                            lKBigAvatar.getSMALL_AVATAR_SIZE(),
                                            it
                                    )
                                }
                        }
                    } catch (ex: Exception) {
                        WriteExceptionIntoFile(ex, "Sqlite_service.InsertBigAvatar")
                    }
                    finally {
                        lock.unlock()
                    }
                }
            }


    @InternalAPI
    private fun ClearBigAvatar() =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            Connection.createStatement().CLEAR_BIG_AVATARS()
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.ClearBigAvatar")
                }
                finally {
                    lock.unlock()
                }
            }

    @InternalAPI
    @JsName("DeleteBigAvatar")

    internal fun DeleteBigAvatar(lOBJECTID: String) =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            Connection.createStatement().DELETE_BIG_AVATARS(lOBJECTID)
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.DeleteBigAvatar")
                }
                finally {
                    lock.unlock()
                }
            }

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @JsName("InsertSendMedia")
    internal fun InsertSendMedia(lKSendMedia: KSendMedia) =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            Connection.createStatement().INSERT_SENDMEDIA(
                                    lKSendMedia.getOBJECTS_ID(),
                                    lKSendMedia.getOBJECTS_EXTENSION(),
                                    lKSendMedia.getOBJECT_NAME(),
                                    myConnectionsID.value,
                                    lKSendMedia.getIS_DOWNLOAD()
                            )

                            SENDMEDIA[lKSendMedia.getOBJECTS_ID()] = lKSendMedia
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.InsertSendMedia")
                }
                finally {
                    lock.unlock()
                }
            }

    @InternalAPI
    @JsName("DeleteAllSendMedia")
    private fun DeleteAllSendMedia() =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            val statement = Connection.createStatement()
                            statement.DELETE_ALL_SENDMEDIA(myConnectionsID.value)
                            SENDMEDIA.clear()
                            val rs = statement.SELECT_ALL_SENDMEDIA(myConnectionsID.value)
                            rs.forEach {
                                SENDMEDIA[it.getOBJECTS_ID()] = it
                            }
                            rs.clear()
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.DeleteAllSendMedia")
                }
                finally {
                    lock.unlock()
                }
            }

    @InternalAPI
    @JsName("DeleteSendMedia")
    internal fun DeleteSendMedia(lSAVEMEDIA: String) =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            Connection.createStatement().DELETE_SENDMEDIA(lSAVEMEDIA)
                            SENDMEDIA.remove(lSAVEMEDIA)
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.DeleteSendMedia")
                }
                finally {
                    lock.unlock()
                }
            }

    @InternalAPI
    private fun ClearSendMedia() =
            Sqlite_serviceScope.launch {
                try {
                    val send = SENDMEDIA.keys
                    send.forEach {
                        DeleteSendMedia(it)
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.ClearSendMedia")
                }
            }

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @JsName("InsertChat")
    internal fun InsertChat(KChat: KChat) =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            Connection.createStatement().INSERT_CHATS(
                                    KChat.getCHATS_ID(),
                                    KChat.getCHATS_OWNER(),
                                    KChat.getLAST_MESSEGES_ID(),
                                    KChat.getCHATS_OPPONENT(),
                                    KChat.getMESSEGES_COUNT(),
                                    KChat.getCHATS_NAME(),
                                    KChat.getCHATS_SMALL_AVATAR(),
                                    KChat.getCHATS_PROFILE(),
                                    KChat.getCHATS_ACCESS(),
                                    KChat.getCHATS_TYPE(),
                                    KChat.getCHATS_STATUS(),
                                    KChat.getLAST_UPDATING_DATE(),
                                    KChat.getCHATS_SUBSCRIBE(),
                                    myConnectionsID.value.toString(),
                                    KChat.isUpdateChatBlob()
                            )


                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.InsertChat")
                }
                finally {
                    lock.unlock()
                }
            }

    @InternalAPI
    @JsName("InsertMessege")
    internal fun InsertMessege(ms: KMessege) =
            Sqlite_serviceScope.launch {
                try {
                    withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
                        lock.lock()
                            Connection.createStatement().INSERT_MESSEGES(
                                    ms.getCHATS_ID(),
                                    ms.getMESSEGES_ID(),
                                    ms.getMESSEGES_CHATS_COUNT(),
                                    ms.getMESSEGES_OWNER(),
                                    ms.getMESSEGES_ANSWER(),
                                    ms.getMESSEGES_ADRESSER(),
                                    ms.getOBJECTS_LINK(),
                                    ms.getMESSEGE_TEXT(),
                                    ms.getMESSEGES_AVATAR(),
                                    ms.getMESSEGES_LIKES(),
                                    ms.getMESSEGES_DISLIKES(),
                                    ms.getNOT_DELIVERIED(),
                                    ms.getNOT_READED(),
                                    ms.getMESSEGES_ACCESS(),
                                    ms.getMESSEGES_TYPE(),
                                    ms.getDATE_ADDING(),
                                    ms.getLAST_UPDATING_DATE(),
                                    ms.getOBJECTS_LINK_SUBSCRIBE(),
                                    ms.getOBJECTS_LINK_SMALL_AVATAR()
                            )
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.InsertMessege")
                }
                finally {
                    lock.unlock()
                }
            }

    @InternalAPI
    private suspend fun InitChats() {
        try {
            val dbID = myConnectionsID.value.toString()
            if (dbID.isNotEmpty()) {
                val rs = withContext(Sqlite_serviceScope.coroutineContext) {
                    Connection.createStatement().SELECT_ALL_CHATS(dbID)
                }
                rs.forEach {
                    CHATS[it.getCHATS_ID()] = it
                }
                rs.clear()
            }

        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "Sqlite_service.InitChats")
        }
        CHATS.forEach {
            InitMesseges(
                    it.value
            )
        }
    }

    @InternalAPI
    @JsName("InitMesseges")
    private suspend fun InitMesseges(KChat: KChat) {
        try {
            val rs = withContext(Sqlite_serviceScope.coroutineContext) {
                Connection.createStatement().SELECT_MESSEGES(KChat.getCHATS_ID())
            }
            rs.forEach {
                KChat.InsertMessege(it)
            }
            rs.clear()
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "Sqlite_service.InitMesseges")
        }
    }
    
    @ExperimentalTime
    @DangerousInternalIoApi
    @InternalAPI
    @ExperimentalIoApi
    @JsName("InitializeUpdatesChats")
    internal fun InitializeUpdatesChats(l_MyJSocket: Jsocket) =
            Sqlite_serviceScope.launch {
                if (l_MyJSocket.content != null && l_MyJSocket.content!!.isNotEmpty()) {
                    val updatesChats: MutableMap<String, KChat> = mutableMapOf()
                    try {
                        l_MyJSocket.deserialized_ANSWERS_TYPES()
                        var long_10: String
                        if (l_MyJSocket.ANSWER_TYPEs != null && l_MyJSocket.ANSWER_TYPEs!!.isNotEmpty()) {
                            val mess = ArrayDeque<ANSWER_TYPE>()
                            while (l_MyJSocket.ANSWER_TYPEs!!.isNotEmpty()) {
                                val ch = KChat(l_MyJSocket.ANSWER_TYPEs!!.removeFirst(), false)
                                long_10 = ch.getANSWER_TYPE().LONG_10.toString()
                                if (long_10.substring(12, 13) == "1") {
                                    CHATS[ch.getCHATS_ID()] = ch
                                    updatesChats[ch.getCHATS_ID()] = ch
                                } else {
                                    mess.addLast(ch.getANSWER_TYPE())
                                }
                            }
                            while (mess.isNotEmpty()) {
                                val ms = KMessege(mess.removeFirst())
                                long_10 = ms.getANSWER_TYPE().LONG_10.toString()
                                if (long_10.substring(12, 13) == "2") {
                                    if (!CHATS.containsKey(ms.getCHATS_ID())) {
                                        var jsocket = Jsocket()
                                        jsocket.just_do_it = 1011000089 // SELECT_CHATS_INFO
                                        jsocket.value_id1 = ms.getCHATS_ID()
                                        jsocket = MySend_JSOCKETs.send_JSOCKET_with_TimeOut(jsocket, null, true)
                                        if (jsocket.ANSWER_TYPEs != null && jsocket.ANSWER_TYPEs!!.size > 0) {
                                            val c = KChat(jsocket.ANSWER_TYPEs!!.removeFirst(), false)
                                            CHATS[c.getCHATS_ID()] = c
                                            updatesChats[c.getCHATS_ID()] = c
                                        }
                                    }
                                    if (CHATS.containsKey(ms.getCHATS_ID())) {
                                        CHATS[ms.getCHATS_ID()]!!.InsertNewMessege(ms)
                                    }
                                }
                            }
                        }
                    } catch (ex: Exception) {
                        WriteExceptionIntoFile(ex, "Sqlite_service.InitializeUpdatesChats")
                    } finally {
                        l_MyJSocket.ANSWER_TYPEs?.clear()
                        l_MyJSocket.content = null
                        updatesChats.forEach {
                            InsertChat(
                                    CHATS[it.key]!!
                            )
                        }
                    }
                }
            }

    @InternalAPI
    @JsName("DeleteChat")
    internal suspend fun DeleteChat(lChatsId: String) {
        try {
            Connection.createStatement().DELETE_CHAT(lChatsId)
            CHATS.remove(lChatsId)
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "Sqlite_service.DeleteChat")
        }
    }

    @InternalAPI
    private fun ClearChats() =
            Sqlite_serviceScope.launch {
                try {
                    Connection.createStatement().CLEAR_CHATS()
                    CHATS.clear()
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.ClearChats")
                }
            }

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @JsName("DeleteMesseges")
    internal fun DeleteMesseges(lChatsId: String, lMessegesId: Long) =
            Sqlite_serviceScope.launch {
                try {
                    Connection.createStatement().DELETE_MESSEGE(lChatsId, lMessegesId)
                    CHATS[lChatsId]?.DeleteMessege(lMessegesId)
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.DeleteMesseges")
                }
            }

    ////////////////////////////////////////////////////////////////////////////////
    @KtorExperimentalAPI
    @ExperimentalIoApi
    @ExperimentalTime
    @DangerousInternalIoApi
    @InternalAPI
    @JsName("closeSqlite")
    internal fun closeSqlite() {
        try {
            SAVEMEDIA.clear()
            SENDMEDIA.clear()
            CHATS.clear()
            Connection.close()
            Cleaner.shutdown()
        } catch (ex: Exception) {
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    private fun ClearCash() {
        try {
            ClearSendMedia()
            DeleteAllSaveMedia(false)
            DeleteAllSaveMedia(true)
            ClearCashData()
            ClearChats()
            SAVEMEDIA.clear()
            SENDMEDIA.clear()
            Connection.close()
        } catch (ex: Exception) {
        }
    } ////////////////////////////////////////////////////////////////////////////////

}

