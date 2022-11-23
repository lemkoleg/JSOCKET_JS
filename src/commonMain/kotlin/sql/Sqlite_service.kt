/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("DuplicatedCode", "RedundantSuspendModifier", "unused")

package sql

import Tables.*
import com.soywiz.klock.DateTime
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import p_jsocket.Constants.CLIENT_TIMEOUT
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

/**
 *
 * @author Oleg
 */

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("Sqlite_service")
object Sqlite_service : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default

    val Sqlite_serviceScope = CoroutineScope(coroutineContext) + SupervisorJob()

    val Connection: MySQLConnection = MySQLConnection("AvaClubDB")


    ///////////////////////////////////big avatars///////////////////////////

    private val statBIG_AVATARS = Connection.createStatement()
    private val lockBIG_AVATARS = Mutex()

    @JsName("InsertBigAvatars")
    fun InsertBigAvatars(kBigAvatars: ArrayList<KBigAvatar>) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    kBigAvatars.forEach { statBIG_AVATARS.INSERT_BIG_AVATAR(it) }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertBigAvatars",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertBigAvatars",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }


    @JsName("SelectBigAvatar")
    suspend fun SelectBigAvatar(OBJECTS_ID: String, IS_UPDATE_LAST_USE: Boolean): KBigAvatar? {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    if (IS_UPDATE_LAST_USE) {
                        statBIG_AVATARS.UPDATE_BIG_AVATAR_LAST_USE(OBJECTS_ID, DateTime.nowUnixLong())
                    }
                    return@withTimeoutOrNull statBIG_AVATARS.SELECT_BIG_AVATAR(OBJECTS_ID)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "SelectBigAvatar",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "SelectBigAvatar",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
        return null
    }


    @JsName("LoadBigAvatarsIds")
    fun LoadBigAvatarsIds() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    val arr: ArrayList<String> = statBIG_AVATARS.SELECT_BIGAVATARS_ALL_ID()
                    KBigAvatar.LOAD_BIG_AVATARS_IDS(arr)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadBigAvatarsIds",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadBigAvatarsIds",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("LoadBigAvatars")
    fun LoadBigAvatars() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    val arr: ArrayList<KBigAvatar> = statBIG_AVATARS.SELECT_BIGAVATARS_ALL()
                    KBigAvatar.LOAD_BIG_AVATARS(arr)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadBigAvatars",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadBigAvatars",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("UpdateBigAvatarsLastUse")
    fun UpdateBigAvatarsLastUse(OBJECTS_ID: String) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    statBIG_AVATARS.UPDATE_BIG_AVATAR_LAST_USE(OBJECTS_ID, DateTime.nowUnixLong())
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "UpdateBigAvatarsLastUse",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "UpdateBigAvatarsLastUse",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("DeleteBigAvatars")
    fun DeleteBigAvatars(OBJECTS_ID: String) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    statBIG_AVATARS.DELETE_BIG_AVATAR(OBJECTS_ID)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "DeleteBigAvatars",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "DeleteBigAvatars",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("ClearBigAvatars")
    fun ClearBigAvatars() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockBIG_AVATARS.lock()
                    statBIG_AVATARS.CLEAR_BIG_AVATARS()
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "ClearBigAvatars",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "ClearBigAvatars",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statBIG_AVATARS.clear_parameters()
                lockBIG_AVATARS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    ///////////// commands ///////////////////////////

    private val statCOMMANDS = Connection.createStatement()
    private val lockCOMMANDS = Mutex()

    @JsName("InsertCommands")
    fun InsertCommands(arr: ArrayList<KCommands>) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCOMMANDS.lock()
                    arr.forEach { statCOMMANDS.INSERT_COMMAND(it) }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertCommands",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertCommands",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCOMMANDS.clear_parameters()
                lockCOMMANDS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("LoadCommands")
    fun LoadCommands() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCOMMANDS.lock()
                    val arr: ArrayList<KCommands> = statCOMMANDS.SELECT_COMMANDS_ALL()
                    KCommands.LOAD_COMMANDS(arr)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadCommands",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadCommands",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCOMMANDS.clear_parameters()
                lockCOMMANDS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    ///////////// Exceptions ///////////////////////////

    private val statEXCEPTIONS = Connection.createStatement()
    private val lockEXCEPTIONS = Mutex()


    @JsName("InsertExceptions")
    fun InsertExceptions(ans: ArrayList<KExceptions.KException>) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockEXCEPTIONS.lock()
                    ans.forEach {
                        statEXCEPTIONS.INSERT_EXCEPTION(it)
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertExceptions",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertExceptions",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statEXCEPTIONS.clear_parameters()
                lockEXCEPTIONS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("LoadExceptions")
    fun LoadExceptions() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockEXCEPTIONS.lock()
                    val arr: ArrayList<KExceptions.KException> = statEXCEPTIONS.SELECT_EXCEPTION_ALL()
                    KExceptions.LOAD_EXCEPTIONS(arr)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadExceptions",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadExceptions",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statEXCEPTIONS.clear_parameters()
                lockEXCEPTIONS.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    ///////////// Meta_data ///////////////////////////

    private val statMETA_DATA = Connection.createStatement()
    private val lockMETA_DATA = Mutex()


    @JsName("InsertMetaData")
    fun InsertMetaData(ans: ArrayList<KMetaData>) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockMETA_DATA.lock()
                    ans.forEach {
                        statMETA_DATA.INSERT_METADATA(it)
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertMetaData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertMetaData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statMETA_DATA.clear_parameters()
                lockMETA_DATA.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("LoadMetaData")
    fun LoadMetaData() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockMETA_DATA.lock()
                    val arr: ArrayList<KMetaData> = statMETA_DATA.SELECT_ALL_METADATA()
                    KMetaData.LOAD_META_DATA(arr)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadMetaData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadMetaData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statMETA_DATA.clear_parameters()
                lockMETA_DATA.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    /////////////reg data///////////////////////////

    private val statREG_DATA = Connection.createStatement()
    private val lockREG_DATA = Mutex()


    @JsName("InsertRegData")
    fun InsertRegData() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockREG_DATA.lock()
                    statREG_DATA.INSERT_REGDATA()
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertRegData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertRegData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statREG_DATA.clear_parameters()
                lockREG_DATA.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("LoadRegData")
    fun LoadRegData() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockREG_DATA.lock()
                    statREG_DATA.SELECT_REGDATA_ALL()
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadRegData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadRegData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statREG_DATA.clear_parameters()
                lockREG_DATA.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("ClearRegData")
    fun ClearRegData() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockREG_DATA.lock()
                    statREG_DATA.CLEAR_REGDATA()
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "ClearRegData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "ClearRegData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statREG_DATA.clear_parameters()
                lockREG_DATA.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    ///////////// save_media ///////////////////////////

    private val statSAVE_MEDIA = Connection.createStatement()
    private val lockSAVE_MEDIA = Mutex()


    @JsName("InsertSaveMedia")
    fun InsertSaveMedia(ans: ArrayList<KSaveMedia>) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockSAVE_MEDIA.lock()
                    ans.forEach {
                        statSAVE_MEDIA.INSERT_SAVEMEDIA(it)
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertSaveMedia",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertMetaData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statSAVE_MEDIA.clear_parameters()
                lockSAVE_MEDIA.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("LoadSaveMedia")
    fun LoadSaveMedia() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockSAVE_MEDIA.lock()
                    val arr: ArrayList<KSaveMedia> = statSAVE_MEDIA.SELECT_SAVEMEDIA_ALL(myConnectionsID)
                    KSaveMedia.LOAD_SAVE_MEDIA(arr)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadSaveMedia",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadSaveMedia",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statSAVE_MEDIA.clear_parameters()
                lockSAVE_MEDIA.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("DeleteSaveMedia")
    fun DeleteSaveMedia(ans: ArrayList<KSaveMedia>) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockSAVE_MEDIA.lock()
                    ans.forEach {
                        statSAVE_MEDIA.DELETE_SAVEMEDIA(it.L_OBJECT_LINK)
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "DeleteSaveMedia",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "DeleteSaveMedia",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statSAVE_MEDIA.clear_parameters()
                lockSAVE_MEDIA.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    /////////////cash last update///////////////////////////

    private val statCASHLASTUPDATE = Connection.createStatement()
    private val lockCASHLASTUPDATE = Mutex()


    @JsName("InsertCashLastUpdate")
    fun InsertCashLastUpdate(cash: KCashLastUpdate) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHLASTUPDATE.lock()
                    statCASHLASTUPDATE.INSERT_CASHLASTUPDATE(cash)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertCashLastUpdate",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertCashLastUpdate",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("LoadCashLastUpdate")
    fun LoadCashLastUpdate() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHLASTUPDATE.lock()
                    val arr: ArrayList<KCashLastUpdate> = statCASHLASTUPDATE.SELECT_CASHLASTUPDATE_ALL()
                    KCashLastUpdate.LOAD_CASH_LAST_UPDATE(arr)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadCashLastUpdate",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadCashLastUpdate",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }


    /////////////cash data///////////////////////////

    @JsName("InsertCashData")
    fun InsertCashData(arr: MutableList<ANSWER_TYPE>) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHLASTUPDATE.lock()
                    var current_cas_sum = ""
                    arr.forEach {
                        if (current_cas_sum.isNotEmpty() && current_cas_sum != it.CASH_SUM) {
                            statCASHLASTUPDATE.CASHDATA_SORT_NEW_NUMBER_POSITIONS(current_cas_sum)
                        }
                        current_cas_sum = it.CASH_SUM
                        statCASHLASTUPDATE.INSERT_CASHDATA(it)
                    }
                    statCASHLASTUPDATE.CASHDATA_SORT_NEW_NUMBER_POSITIONS(current_cas_sum)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "InsertCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("SelectCashDataAllOnCashSum")
    suspend fun SelectCashDataAllOnCashSum(cash: String): ArrayDeque<ANSWER_TYPE> {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHLASTUPDATE.lock()
                    return@withTimeoutOrNull statCASHLASTUPDATE.SELECT_CASHDATA_ALL_ON_CASH_SUM(cash)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "SelectCashDataAllOnCashSum",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "SelectCashDataAllOnCashSum",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
        return ArrayDeque()
    }

    @JsName("SelectCashDataChunkOnCashSum")
    suspend fun SelectCashDataChunkOnCashSum(cash: String, record_id_from: String = ""): ArrayDeque<ANSWER_TYPE> {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHLASTUPDATE.lock()
                    return@withTimeoutOrNull statCASHLASTUPDATE.SELECT_CASHDATA_CHUNK_ON_CASH_SUM(cash, record_id_from)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "SelectCashDataAllOnCashSum",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "SelectCashDataAllOnCashSum",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
        return ArrayDeque()
    }

    @JsName("SelectCashData")
    suspend fun SelectCashData(cash: String, record_id: String): ANSWER_TYPE? {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHLASTUPDATE.lock()
                    return@withTimeoutOrNull statCASHLASTUPDATE.SELECT_CASHDATA(cash, record_id)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "SelectCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "SelectCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
        return null
    }

    @JsName("LoadCashData")
    fun LoadCashData() = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    if (Constants.IS_DOWNLOAD_TO_MEMORY_ALL_CASH_ON_CLIENT == 1) {
                        lockCASHLASTUPDATE.lock()
                        val arr: ArrayDeque<ANSWER_TYPE> = statCASHLASTUPDATE.SELECT_CASHDATA_ALL()
                        KCashData.LOAD_CASH_DATA(arr)
                    } else {
                        KCashData.LOAD_CASH_DATA(ArrayDeque())
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("LoadCashDataAllObjectsIdOnCasSum")
    fun LoadCashDataAllObjectsIdOnCasSum(cash: String) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                        lockCASHLASTUPDATE.lock()
                        val arr: ArrayList<String> = statCASHLASTUPDATE.SELECT_CASHDATA_ALL_RECORDS_ID_ON_CASH_SUM(cash)
                        KObjectInfo.LOAD_SAVE_OBJECT_INFO_IDS(arr)

                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "LoadCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }


    @JsName("OrederCashData")
    private fun OrederCashData(CASH_SUM: String) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHLASTUPDATE.lock()
                    //statCASHDATA.UPDATE_CASHDATA_CONTROL_COUNT(CASH_SUM)
                    statCASHLASTUPDATE.CASHDATA_SORT_NEW_NUMBER_POSITIONS(CASH_SUM)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "OrederCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "OrederCashData",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("UpdateCashDataNewLastSelect")
    fun UpdateCashDataNewLastSelect(last_select: Long,
                                    cash_sum: String,
                                    record_table_id_from: String,
                                    limit: Int) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHLASTUPDATE.lock()
                    statCASHLASTUPDATE.UPDATE_CASHDATA_NEW_LAST_SELECT(last_select, cash_sum, record_table_id_from, limit)
                    val c = CASH_LAST_UPDATE[cash_sum]
                    c!!.LAST_USE = last_select
                    statCASHLASTUPDATE.INSERT_CASHLASTUPDATE(c)
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "UpdateCashDataNewLastSelect",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "UpdateCashDataNewLastSelect",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }

    @JsName("DeleteCash")
    fun DeleteCash(cash_sum: String, object_id: String? = null) = Sqlite_serviceScope.launch {
        try {
            try {
                withTimeoutOrNull(CLIENT_TIMEOUT) {
                    lockCASHLASTUPDATE.lock()
                    if(object_id != null){
                        statCASHLASTUPDATE.DELETE_CASHDATA_RECORD(cash_sum, object_id)
                    }else{
                        statCASHLASTUPDATE.DELETE_CASHDATA(cash_sum)
                        statCASHLASTUPDATE.DELETE_LASTUPDATE(cash_sum)
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "DeleteCash",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "DeleteCash",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            } finally {
                statCASHLASTUPDATE.clear_parameters()
                lockCASHLASTUPDATE.unlock()
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private const val TIMEOUTCLEANERSECONDS = 20
    private const val VERIFYTABLES: Long = 60000L //60 seconds
    private var NextTimeVerifyTables: Long = DateTime.nowUnixLong() + VERIFYTABLES


    ////////////////////////////////////////////////////////////////////////////////

    @JsName("Connect")
    internal fun Connect() = Sqlite_serviceScope.launch {
        /*Connection = if (InitJsocket.connection == null) {
            org.sqlite.JDBC.createConnection(CON_STR, properties)
        } else {
            InitJsocket.connection
        }*/
        val statement = Connection.createStatement()
        try {
            try {
                statement.connect()
            } catch (e: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "Connect.connect",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = e.message
                )
            }

            /////////////   BIG_AVATARS   ///////////////////////////////

            try {
                statement.TABLE_BIG_AVATARS()
                statement.INDEX_BIG_AVATARS_LAST_USE()
                statement.TRIGGER_BIG_AVATARS_CONTROL_COUNT()
                KBigAvatar.RE_LOAD_BIG_AVATAR_IDS()
            } catch (e: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "Connect.BIG_AVATARS",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = e.message
                )
            }

            /////////////   COMMANDS   ///////////////////////////////

            try {
                statement.TABLE_COMMANDS()
                KCommands.RE_LOAD_COMMANDS()
            } catch (e: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "Connect.COMMANDS",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = e.message
                )
            }

            /////////////   EXCEPTIONS   ///////////////////////////////

            try {
                statement.TABLE_EXCEPTION()
                KExceptions.RE_LOAD_EXCEPTIONS()
            } catch (e: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "Connect.EXCEPTIONS",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = e.message
                )
            }

            /////////////  META_DATA   ///////////////////////////////

            try {
                statement.TABLE_METADATA()
                KMetaData.RE_LOAD_META_DATA()
            } catch (e: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "Connect.META_DATA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = e.message
                )
            }

            /////////////  REG_DATA   ///////////////////////////////

            try {
                statement.TABLE_REGDATA()
                statement.TRIGGER_REGDATA_INSERT()
                KRegData.RE_LOAD_REG_DATA()
            } catch (e: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "Connect.REG_DATA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = e.message
                )
            }

            /////////////  SAVE_MEDIA   ///////////////////////////////

            try {
                statement.TABLE_SAVEMEDIA()
                statement.INDEX_SAVEMEDIA_LASTUSED()
                statement.INDEX_SAVEMEDIA_ISTEMP()
                statement.INDEX_SAVEMEDIA_AVATAR_ID()
                statement.TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT()
                statement.TRIGGER_SAVEMEDIA_CONTROL_COUNT()
                KSaveMedia.RE_LOAD_SAVE_MEDIA()
            } catch (e: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "Connect.SAVE_MEDIA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = e.message
                )
            }

            /////////////  CASH   ///////////////////////////////

            try {
                statement.TABLE_CASHLASTUPDATE()
                statement.INDEX_CASHLASTUPDATE_LAST_USE()
                statement.INDEX_CASHLASTUPDATE_RECORD_TYPE_CASH_SUM()
                statement.INDEX_CASHLASTUPDATE_RECORD_TYPE_LAST_USE_CASH_SUM()
                statement.TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT()
                statement.TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_LINKS_INSERT()
                statement.TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_CHATS_INSERT()
                statement.TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_MESS_INSERT()
                statement. TABLE_CASHDATA()
                statement.INDEX_CASHDATA_NUMBER_POSITION_ASC()
                statement.TRIGGER_CASHDATA_AFTER_INSERT()
                KCashData.RE_LOAD_CASH_DATA()
                KObjectInfo.RE_LOAD_SAVE_OBJECT_INFO_IDS()
            } catch (e: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Sqlite_service",
                    l_function_name = "Connect.BIG_AVATARS",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = e.message
                )
            }

        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        }

    }

}

