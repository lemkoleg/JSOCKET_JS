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

    suspend fun TABLE_BIG_AVATARS()

    suspend fun INDEX_BIG_AVATARS_LAST_USE() // for clear old last use in trigger

    suspend fun TRIGGER_BIG_AVATARS_CONTROL_COUNT()

    suspend fun INSERT_BIG_AVATAR(kBigAvatar: KBigAvatar)

    suspend fun SELECT_BIG_AVATAR(OBJECTS_ID: String):KBigAvatar?

    suspend fun SELECT_BIGAVATARS_ALL_ID():ArrayList<String>  //for save in memory

    suspend fun SELECT_BIGAVATARS_ALL():ArrayList<KBigAvatar>  //for save in memory

    suspend fun DELETE_BIG_AVATAR(OBJECTS_ID: String)

    suspend fun CLEAR_BIG_AVATARS()

    suspend fun UPDATE_BIG_AVATAR_LAST_USE(OBJECTS_ID: String, LAST_USE: Long)

    ///////////// commands ///////////////////////////

    suspend fun TABLE_COMMANDS()

    suspend fun INSERT_COMMAND(kCommand: KCommands)

    suspend fun SELECT_COMMANDS_ALL():ArrayList<KCommands>

    suspend fun CLEAR_COMMANDS()

    /////////////exceptions///////////////////////////

    suspend fun TABLE_EXCEPTION()

    suspend fun INSERT_EXCEPTION(kException: KExceptions.KException)

    suspend fun SELECT_EXCEPTION_ALL():ArrayList<KExceptions.KException>

    suspend fun CLEAR_EXCEPTION()

    /////////////meta data///////////////////////////

    suspend fun TABLE_METADATA()

    suspend fun INSERT_METADATA(kMetaData: KMetaData)

    suspend fun CLEAR_METADATA()

    suspend fun SELECT_ALL_METADATA():ArrayList<KMetaData>

    /////////////reg data///////////////////////////

    suspend fun TABLE_REGDATA()

    suspend fun TRIGGER_REGDATA_INSERT()

    suspend fun INSERT_REGDATA()

    suspend fun CLEAR_REGDATA()

    suspend fun SELECT_REGDATA_ALL()

    /////////////save media///////////////////////////

    suspend fun TABLE_SAVEMEDIA()

    suspend fun INDEX_SAVEMEDIA_LASTUSED()

    suspend fun INDEX_SAVEMEDIA_ISTEMP()

    suspend fun INDEX_SAVEMEDIA_AVATAR_ID()

    suspend fun TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT()

    suspend fun TRIGGER_SAVEMEDIA_CONTROL_COUNT()

    suspend fun INSERT_SAVEMEDIA(kSaveMedia: KSaveMedia)

    suspend fun SELECT_SAVEMEDIA_ALL(conn_id: Long):ArrayList<KSaveMedia>

    suspend fun DELETE_SAVEMEDIA(v: String)

    /////////////last update///////////////////////////

    suspend fun TABLE_CASHLASTUPDATE()

    suspend fun INDEX_CASHLASTUPDATE_LAST_USE()

    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT()

    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_LINKS_INSERT()

    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_OBJECTS_INFO_INSERT()

    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_CHATS_INSERT()

    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_MESS_INSERT()

    suspend fun INSERT_CASHLASTUPDATE(kCashLastUpdate:KCashLastUpdate)

    suspend fun SELECT_CASHLASTUPDATE_ALL(): ArrayList<KCashLastUpdate>

    suspend fun CLEAR_LASTUPDATE()

    suspend fun DELETE_LASTUPDATE(cash_sum:String)

    /////////////cash data///////////////////////////

    suspend fun TABLE_CASHDATA()

    suspend fun INDEX_CASHDATA_NUMBER_POSITION_ASC()

    suspend fun INDEX_CASHDATA_CASH_SUM()

    suspend fun TRIGGER_CASHDATA_AFTER_INSERT()

    suspend fun TRIGGER_CASHDATA_AFTER_INSERT_OBJECTS_INFO()

    suspend fun TRIGGER_CASHDATA_AFTER_UPDATE()

    suspend fun INSERT_CASHDATA(cash_sum: String, lANSWER_TYPE: ANSWER_TYPE)

    suspend fun SELECT_CASHDATA_ALL():ArrayDeque<ANSWER_TYPE>

    suspend fun SELECT_CASHDATA_ALL_ON_CASH_SUM(CASH_SUM: String): ArrayDeque<ANSWER_TYPE>

    suspend fun SELECT_CASHDATA_CHUNK_ON_CASH_SUM(CASH_SUM: String, record_id_from: String): ArrayDeque<ANSWER_TYPE>

    suspend fun SELECT_CASHDATA(CASH_SUM: String, RECORD_TABLE_ID: String): ANSWER_TYPE?

    suspend fun SELECT_CASHDATA_ALL_RECORDS_ID_ON_CASH_SUM(CASH_SUM: String):ArrayList<String>

    suspend fun CLEAR_CASHDATA()

    suspend fun CASHDATA_SORT_NEW_NUMBER_POSITIONS(CASH_SUM: String)

    suspend fun CASHDATA_SORT_NEW_NUMBER_POSITIONS_FINISH(CASH_SUM: String)

    suspend fun UPDATE_CASHDATA_NEW_LAST_SELECT(last_select: Long,
                                                cash_sum:String,
                                                record_table_id_from:String,
                                                limit: Int)

    suspend fun DELETE_CASHDATA(cash_sum:String)

    suspend fun DELETE_CASHDATA_RECORD(cash_sum:String, record_id: String)

    /////////////////////////////////////////////////////////////////////////
    suspend fun connect()

    suspend fun clear_parameters()

}