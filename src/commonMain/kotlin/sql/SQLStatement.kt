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

    @JsName("INDEX_SAVEMEDIA_CONNECTIONID")
    suspend fun INDEX_SAVEMEDIA_CONNECTIONID()

    @JsName("INDEX_SAVEMEDIA_LASTUSED")
    suspend fun INDEX_SAVEMEDIA_LASTUSED()

    @JsName("INDEX_SAVEMEDIA_ISTEMP")
    suspend fun INDEX_SAVEMEDIA_ISTEMP()

    @JsName("INDEX_SAVEMEDIA_AVATARID")
    suspend fun INDEX_SAVEMEDIA_AVATARID()

    @JsName("TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT")
    suspend fun TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT()

    @JsName("TRIGGER_SAVEMEDIA_CONTROL_COUNT")
    suspend fun TRIGGER_SAVEMEDIA_CONTROL_COUNT()

    @JsName("INSERT_SAVEMEDIA")
    suspend fun INSERT_SAVEMEDIA(kSaveMedia: KSaveMedia)

    @JsName("SELECT_SAVEMEDIA_ALL")
    suspend fun SELECT_SAVEMEDIA_ALL():ArrayList<KSaveMedia>

    @JsName("DELETE_SAVEMEDIA")
    suspend fun DELETE_SAVEMEDIA(v: String)

    /////////////cash data///////////////////////////

    @JsName("TABLE_CASHDATA")
    suspend fun TABLE_CASHDATA()

    @JsName("INDEX_CASHDATA_NUMBER_POSITION")
    suspend fun INDEX_CASHDATA_NUMBER_POSITION()

    @JsName("INDEX_CASHDATA_NUMBER_POSITION_LAST_UPDATE")
    suspend fun INDEX_CASHDATA_NUMBER_POSITION_LAST_UPDATE()

    @JsName("TRIGGER_CASHDATA_INSERT")
    suspend fun TRIGGER_CASHDATA_INSERT()

    @JsName("TRIGGER_CASHDATA_UPDATE")
    suspend fun TRIGGER_CASHDATA_UPDATE()

    @JsName("INSERT_CASHDATA")
    suspend fun INSERT_CASHDATA(lANSWER_TYPE: ANSWER_TYPE)

    @JsName("INSERT_CASHDATAS")
    suspend fun INSERT_CASHDATAS(lANSWER_TYPES: ArrayDeque<ANSWER_TYPE>)

    @JsName("SELECT_CASHDATA_ALL")
    suspend fun SELECT_CASHDATA_ALL()

    @JsName("SELECT_CASHDATA_ALL_ON_CASH_SUM")
    suspend fun SELECT_CASHDATA_ALL_ON_CASH_SUM(CASH_SUM: Long)

    @JsName("CLEAR_CASHDATA")
    suspend fun CLEAR_CASHDATA()

    @JsName("CASHDATA_SORT_NEW_NUMBER_POSITIONS")
    suspend fun CASHDATA_SORT_NEW_NUMBER_POSITIONS()

    /////////////last update///////////////////////////

    @JsName("TABLE_CASHLASTUPDATE")
    suspend fun TABLE_CASHLASTUPDATE()

    @JsName("INDEX_CASHLASTUPDATE_LAST_USE")
    suspend fun INDEX_CASHLASTUPDATE_LAST_USE()

    @JsName("INDEX_CASHLASTUPDATE_CONNECTIONID")
    suspend fun INDEX_CASHLASTUPDATE_CONNECTIONID()

    @JsName("TRIGGER_CASHLASTUPDATE_DELETE")
    suspend fun TRIGGER_CASHLASTUPDATE_DELETE()

    @JsName("TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT")
    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT()

    @JsName("TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_UPDATE")
    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_UPDATE()

    @JsName("TRIGGER_CASHLASTUPDATE_UPDATE")
    suspend fun TRIGGER_CASHLASTUPDATE_UPDATE()

    @JsName("TRIGGER_CASHLASTUPDATE_CONTROL_COUNT")
    suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT()

    @JsName("INSERT_CASHLASTUPDATE")
    suspend fun INSERT_CASHLASTUPDATE(kCashLastUpdate:KCashLastUpdate)

    @JsName("UPDATE_CASHLASTUPDATE_LAST_USE")
    suspend fun UPDATE_CASHLASTUPDATE_LAST_USE(kCashLastUpdate:KCashLastUpdate)

    @JsName("SELECT_CASHLASTUPDATE")
    suspend fun SELECT_CASHLASTUPDATE(L_CONNECTION_ID : Long)

    @JsName("CLEAR_LASTUPDATE")
    suspend fun CLEAR_LASTUPDATE()

    /////////////chats///////////////////////////

    @JsName("TABLE_CHATS")
    suspend fun TABLE_CHATS()

    @JsName("INDEX_CHATS_LAST_MESS_ADDING")
    suspend fun INDEX_CHATS_LAST_MESS_ADDING()

    @JsName("INDEX_CHATS_CONNECTIONID")
    suspend fun INDEX_CHATS_CONNECTIONID()

    @JsName("INDEX_CHATS_AVATARID")
    suspend fun INDEX_CHATS_AVATARID()

    @JsName("TRIGGER_CHATS_DELETE")
    suspend fun TRIGGER_CHATS_DELETE()

    @JsName("TRIGGER_CHATS_CONTROL_COUNT")
    suspend fun TRIGGER_CHATS_CONTROL_COUNT()

    @InternalAPI
    @JsName("INSERT_CHATS")
    suspend fun INSERT_CHATS(kChat: KChat)

    @InternalAPI
    @JsName("SELECT_CHATS_ALL")
    suspend fun SELECT_CHATS_ALL()

    @JsName("CLEAR_CHATS")
    suspend fun CLEAR_CHATS()

    /////////////chats cost types///////////////////////////

    @JsName("TABLE_CHATS_COST_TYPES")
    suspend fun TABLE_CHATS_COST_TYPES()

    @JsName("INSERT_CHATS_COST_TYPES")
    suspend fun INSERT_CHATS_COST_TYPES(kChatsCostTypes: KChatsCostTypes)

    @JsName("SELECT_CHATS_COST_TYPES_ALL")
    suspend fun SELECT_CHATS_COST_TYPES_ALL()

    @JsName("CLEAR_CHATS_COST_TYPES")
    suspend fun CLEAR_CHATS_COST_TYPES()

    /////////////chats likes///////////////////////////

    @JsName("TABLE_CHATS_LIKES")
    suspend fun TABLE_CHATS_LIKES()

    @JsName("INDEX_CHATSLIKES_AVATARID")
    suspend fun INDEX_CHATSLIKES_AVATARID()

    @JsName("INDEX_CHATSLIKES_ACCOUNTSID")
    suspend fun INDEX_CHATSLIKES_ACCOUNTSID()

    @JsName("TRIGGER_CHATSLIKES_DELETED_RECORD")
    suspend fun TRIGGER_CHATSLIKES_DELETED_RECORD()

    @JsName("INSERT_CHATS_LIKES")
    suspend fun INSERT_CHATS_LIKES(kChatsLikes: KChatsLikes)

    @JsName("SELECT_CHATSLIKES_ALL")
    suspend fun SELECT_CHATSLIKES_ALL()


    /////////////messeges///////////////////////////

    @JsName("TABLE_MESSEGES")
    suspend fun TABLE_MESSEGES()

    @JsName("INDEX_MESSEGES_ORDER_DESC")
    suspend fun INDEX_MESSEGES_ORDER_DESC()

    @JsName("TRIGGER_MESSEGES_CONTROL_COUNT")
    suspend fun TRIGGER_MESSEGES_CONTROL_COUNT()

    @JsName("TRIGGER_MESSEGES_MESS_WITHOUT_CAHTS")
    suspend fun TRIGGER_MESSEGES_MESS_WITHOUT_CAHTS()

    @JsName("INSERT_MESSEGES")
    suspend fun INSERT_MESSEGES(kMessege: KMessege)

    @JsName("SELECT_MESSEGES_LIMIT")
    suspend fun SELECT_MESSEGES_LIMIT(L_CHATS_ID: String, L_MESSEGES_COUNT: Long = 999999999999999999)

    @JsName("CLEAR_MESSEGES")
    suspend fun CLEAR_MESSEGES()



    /////////////////////////////////////////////////////////////////////////
    @JsName("connect")
    suspend fun connect()

    @JsName("clear_parameters(")
    suspend fun clear_parameters()

}