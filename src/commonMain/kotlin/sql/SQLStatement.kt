package sql

import Tables.*
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.util.InternalAPI
import p_jsocket.ANSWER_TYPE
import Tables.KCommands
import com.soywiz.korio.experimental.KorioExperimentalApi
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

expect var sqlDriver: SqlDriver?

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("SQLStatement")
expect class SQLStatement() {

    ///////////////////////////////////big avatars///////////////////////////
    @JsName("TABLE_BIG_AVATARS")
    suspend fun TABLE_BIG_AVATARS()

    @JsName("INDEX_BIG_AVATARS_LAST_USE")  // for clear old last use in trigger
    suspend fun INDEX_BIG_AVATARS_LAST_USE()

    @JsName("TRIGGER_BIG_AVATARS_CONTROL_COUNT")
    suspend fun TRIGGER_BIG_AVATARS_CONTROL_COUNT()

    @JsName("INSERT_BIG_AVATAR")
    suspend fun INSERT_BIG_AVATAR(kBigAvatar: KBigAvatar)

    @JsName("SELECT_BIG_AVATAR")
    suspend fun SELECT_BIG_AVATAR(OBJECTS_ID: String):KBigAvatar?

    @JsName("SELECT_BIGAVATARS_ALL_ID")  //for save in memory
    suspend fun SELECT_BIGAVATARS_ALL_ID():ArrayList<String>

    @JsName("SELECT_BIGAVATARS_ALL")  //for save in memory
    suspend fun SELECT_BIGAVATARS_ALL():ArrayList<KBigAvatar>

    @JsName("UPDATE_BIG_AVATAR_LAST_USE")
    suspend fun UPDATE_BIG_AVATAR_LAST_USE(OBJECTS_ID: String, LAST_USE: Long)

    @JsName("CLEAR_BIG_AVATARS")
    suspend fun CLEAR_BIG_AVATARS()

    @JsName("DELETE_BIG_AVATARS")
    suspend fun DELETE_BIG_AVATAR(OBJECTS_ID: String)

    ///////////// commands ///////////////////////////

    @JsName("TABLE_COMMANDS")
    suspend fun TABLE_COMMANDS()

    @JsName("INSERT_COMMAND")
    suspend fun INSERT_COMMAND(kCommand: KCommands)

    @JsName("SELECT_COMMANDS_ALL")
    suspend fun SELECT_COMMANDS_ALL():ArrayList<KCommands>

    @JsName("CLEAR_COMMANDS")
    suspend fun CLEAR_COMMANDS()

    /////////////exceptions///////////////////////////

    @JsName("TABLE_EXCEPTION")
    suspend fun TABLE_EXCEPTION()

    @JsName("INSERT_EXCEPTION")
    suspend fun INSERT_EXCEPTION(kException: KExceptions.KException)

    @JsName("SELECT_EXCEPTION_ALL")
    suspend fun SELECT_EXCEPTION_ALL():ArrayList<KExceptions.KException>

    @JsName("CLEAR_EXCEPTION")
    suspend fun CLEAR_EXCEPTION()

    /////////////meta data///////////////////////////

    @JsName("TABLE_METADATA")
    suspend fun TABLE_METADATA()

    @JsName("INSERT_METADATA")
    suspend fun INSERT_METADATA(kMetaData: KMetaData)

    @JsName("CLEAR_METADATA")
    suspend fun CLEAR_METADATA()

    @JsName("SELECT_ALL_METADATA")
    suspend fun SELECT_ALL_METADATA():ArrayList<KMetaData>

    /////////////reg data///////////////////////////

    @JsName("TABLE_REGDATA")
    suspend fun TABLE_REGDATA()

    @JsName("TRIGGER_REGDATA_INSERT")
    suspend fun TRIGGER_REGDATA_INSERT()

    @JsName("TRIGGER_REGDATA_DELETE")
    suspend fun TRIGGER_REGDATA_DELETE()

    @JsName("INSERT_REGDATA")
    suspend fun INSERT_REGDATA()

    @JsName("CLEAR_REGDATA")
    suspend fun CLEAR_REGDATA()

    @JsName("SELECT_REGDATA_ALL")
    suspend fun SELECT_REGDATA_ALL()

    /////////////save media///////////////////////////

    @JsName("TABLE_SAVEMEDIA")
    suspend fun TABLE_SAVEMEDIA()

    @JsName("INDEX_SAVEMEDIA_LASTUSED")
    suspend fun INDEX_SAVEMEDIA_LASTUSED()

    @JsName("INDEX_SAVEMEDIA_ISTEMP")
    suspend fun INDEX_SAVEMEDIA_ISTEMP()

    @JsName("TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT")
    suspend fun TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT()

    @JsName("TRIGGER_SAVEMEDIA_CONTROL_COUNT")
    suspend fun TRIGGER_SAVEMEDIA_CONTROL_COUNT()

    @JsName("INSERT_SAVEMEDIA")
    suspend fun INSERT_SAVEMEDIA(kSaveMedia: KSaveMedia)

    @JsName("SELECT_SAVEMEDIA_ALL")
    suspend fun SELECT_SAVEMEDIA_ALL(conn_id: Long):ArrayList<KSaveMedia>

    @JsName("DELETE_SAVEMEDIA")
    suspend fun DELETE_SAVEMEDIA(v: String)

    /////////////last update///////////////////////////

    @JsName("TABLE_CASHLASTUPDATE")
    suspend fun TABLE_CASHLASTUPDATE()

    @JsName("INDEX_CASHLASTUPDATE_LAST_USE")
    suspend fun INDEX_CASHLASTUPDATE_LAST_USE()

    @JsName("INDEX_CASHLASTUPDATE_RECORD_TYPE_CASH_SUM")
    suspend fun INDEX_CASHLASTUPDATE_RECORD_TYPE_CASH_SUM()

    @JsName("INDEX_CASHLASTUPDATE_RECORD_TYPE_LAST_USE_CASH_SUM")
    suspend fun INDEX_CASHLASTUPDATE_RECORD_TYPE_LAST_USE_CASH_SUM()

    @JsName("TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT")
    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT()

    @JsName("TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_LINKS_INSERT")
    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_LINKS_INSERT()

    @JsName("TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_CHATS_INSERT")
    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_CHATS_INSERT()

    @JsName("TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_MESS_INSERT")
    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_MESS_INSERT()

    @JsName("INSERT_CASHLASTUPDATE")
    suspend fun INSERT_CASHLASTUPDATE(kCashLastUpdate:KCashLastUpdate)

    @JsName("SELECT_CASHLASTUPDATE_ALL")
    suspend fun SELECT_CASHLASTUPDATE_ALL(): ArrayList<KCashLastUpdate>

    @JsName("CLEAR_LASTUPDATE")
    suspend fun CLEAR_LASTUPDATE()

    @JsName("DELETE_LASTUPDATE")
    suspend fun DELETE_LASTUPDATE(cash_sum:String)

    /////////////cash data///////////////////////////

    @JsName("TABLE_CASHDATA")
    suspend fun TABLE_CASHDATA()

    @JsName("INDEX_CASHDATA_NUMBER_POSITION_ASC")
    suspend fun INDEX_CASHDATA_NUMBER_POSITION_ASC()

    @JsName("TRIGGER_CASHDATA_AFTER_INSERT")
    suspend fun TRIGGER_CASHDATA_AFTER_INSERT()

    @JsName("TRIGGER_CASHDATA_AFTER_UPDATE")
    suspend fun TRIGGER_CASHDATA_AFTER_UPDATE()

    @JsName("INSERT_CASHDATA")
    suspend fun INSERT_CASHDATA(lANSWER_TYPE: ANSWER_TYPE)

    @JsName("SELECT_CASHDATA_ALL")
    suspend fun SELECT_CASHDATA_ALL():ArrayDeque<ANSWER_TYPE>

    @JsName("SELECT_CASHDATA_ALL_ON_CASH_SUM")
    suspend fun SELECT_CASHDATA_ALL_ON_CASH_SUM(CASH_SUM: String): ArrayDeque<ANSWER_TYPE>

    @JsName("SELECT_CASHDATA_CHUNK_ON_CASH_SUM")
    suspend fun SELECT_CASHDATA_CHUNK_ON_CASH_SUM(CASH_SUM: String, record_id_from: String): ArrayDeque<ANSWER_TYPE>

    @JsName("SELECT_CASHDATA")
    suspend fun SELECT_CASHDATA(CASH_SUM: String, RECORD_TABLE_ID: String): ANSWER_TYPE?

    @JsName("CLEAR_CASHDATA")
    suspend fun CLEAR_CASHDATA()

    @JsName("CASHDATA_SORT_NEW_NUMBER_POSITIONS")
    suspend fun CASHDATA_SORT_NEW_NUMBER_POSITIONS(CASH_SUM: String)

    @JsName("UPADTE_CASHDATA_NEW_LAST_SELECT")
    suspend fun UPDATE_CASHDATA_NEW_LAST_SELECT(last_select: Long,
                                                cash_sum:String,
                                                record_table_id_from:String,
                                                limit: Int)

    @JsName("DELETE_CASHDATA_DELETED_RECORDS")
    suspend fun DELETE_CASHDATA_DELETED_RECORDS(cash_sum:String)

    @JsName("DELETE_CASHDATA")
    suspend fun DELETE_CASHDATA(cash_sum:String)

    @JsName("DELETE_CASHDATA_RECORD")
    suspend fun DELETE_CASHDATA_RECORD(cash_sum:String, record_id: String)

    /////////////////////////////////////////////////////////////////////////
    @JsName("connect")
    suspend fun connect()

    @JsName("clear_parameters")
    suspend fun clear_parameters()

}