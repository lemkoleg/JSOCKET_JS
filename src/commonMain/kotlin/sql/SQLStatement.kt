package sql

import Tables.*
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.util.InternalAPI
import p_jsocket.ANSWER_TYPE
import Tables.KCommands
import kotlin.js.JsName

expect var sqlDriver: SqlDriver?

@JsName("SQLStatement")
expect class SQLStatement() {

    @JsName("SELECT_LASTUPDATE")
    suspend fun SELECT_LASTUPDATE(CASH_SUM : Long):Long?
/////////////////////////////////////////////////////////////////////////
    @JsName("connect")
    suspend fun connect()
    @JsName("TABLE_METADATA")
    suspend fun TABLE_METADATA()
    @JsName("TABLE_CASHDATA")
    suspend fun TABLE_CASHDATA()
    @JsName("TRIGGER_CASHDATA_BLOB_1")
    suspend fun TRIGGER_CASHDATA_BLOB_1()
    @JsName("TRIGGER_CASHDATA_BLOB_2")
    suspend fun TRIGGER_CASHDATA_BLOB_2()
    @JsName("TRIGGER_CASHDATA_BLOB_3")
    suspend fun TRIGGER_CASHDATA_BLOB_3()
    @JsName("TRIGGER_CASHDATA_BLOB_4")
    suspend fun TRIGGER_CASHDATA_BLOB_4()
    @JsName("TRIGGER_CASHDATA_BLOB_5")
    suspend fun TRIGGER_CASHDATA_BLOB_5()
    @JsName("TRIGGER_CASHDATA_BLOB_6")
    suspend fun TRIGGER_CASHDATA_BLOB_6()
    @JsName("TRIGGER_CASHDATA_BLOB_7")
    suspend fun TRIGGER_CASHDATA_BLOB_7()
    @JsName("TRIGGER_CASHDATA_BLOB_8")
    suspend fun TRIGGER_CASHDATA_BLOB_8()
    @JsName("TRIGGER_CASHDATA_BLOB_9")
    suspend fun TRIGGER_CASHDATA_BLOB_9()
    @JsName("TRIGGER_CASHDATA_BLOB_10")
    suspend fun TRIGGER_CASHDATA_BLOB_10()
    @JsName("TRIGGER_CASHDATA_DELETING_RECORDS")
    suspend fun TRIGGER_CASHDATA_DELETING_RECORDS()
    @JsName("TABLE_REGDATA")
    suspend fun TABLE_REGDATA()
    @JsName("TRIGGER_INSERT_REGDATA")
    suspend fun TRIGGER_INSERT_REGDATA()
    @JsName("TABLE_COMMANDS")
    suspend fun TABLE_COMMANDS()
    @JsName("TABLE_SAVEMEDIA")
    suspend fun TABLE_SAVEMEDIA()
    @JsName("INDEX_SAVEMEDIA")
    suspend fun INDEX_SAVEMEDIA()
    @JsName("TABLE_BIG_AVATARS")
    suspend fun TABLE_BIG_AVATARS()
    @JsName("INDEX_BIG_AVATARS")
    suspend fun INDEX_BIG_AVATARS()
    @JsName("TRIGGER_CONTROL_COUNT_BIG_AVATARS")
    suspend fun TRIGGER_CONTROL_COUNT_BIG_AVATARS()
    @JsName("TABLE_SENDMEDIA")
    suspend fun TABLE_SENDMEDIA()
    @JsName("TRIGGER_INSERT_SENDMEDIA")
    suspend fun TRIGGER_INSERT_SENDMEDIA()
    @JsName("TABLE_LASTUPDATE")
    suspend fun TABLE_LASTUPDATE()
    @JsName("INDEX_LASTUPDATE")
    suspend fun INDEX_LASTUPDATE()
    @JsName("TRIGGER_DELETE_LASTUPDATE")
    suspend fun TRIGGER_DELETE_LASTUPDATE()
    @JsName("TRIGGER_INSERT_LASTUPDATE")
    suspend fun TRIGGER_INSERT_LASTUPDATE()
    @JsName("TRIGGER_CONTROL_COUNT_CASH")
    suspend fun TRIGGER_CONTROL_COUNT_CASH()
    @JsName("TABLE_CHATS")
    suspend fun TABLE_CHATS()
    @JsName("TRIGGER_DELETE_CHATS")
    suspend fun TRIGGER_DELETE_CHATS()
    @JsName("TRIGGER_CONTROL_COUNT_CHATS")
    suspend fun TRIGGER_CONTROL_COUNT_CHATS()
    @JsName("TABLE_MESSEGES")
    suspend fun TABLE_MESSEGES()
    @JsName("TRIGGER_CONTROL_COUNT_MESSEGES")
    suspend fun TRIGGER_CONTROL_COUNT_MESSEGES()
///////////////////////////////////////////////////////////////////////

    @JsName("UPDATE_LAST_USE_LASTUPDATE")
    suspend fun UPDATE_LAST_USE_LASTUPDATE(LAST_USE:Long, CASH_SUM: Long)
//////////////////////////////////////////////////////////////////////////
    @JsName("CLEAR_CASHDATA")
    suspend fun CLEAR_CASHDATA()
    @JsName("CLEAR_LASTUPDATE")
    suspend fun CLEAR_LASTUPDATE()
    @JsName("CLEAR_REGDATA")
    suspend fun CLEAR_REGDATA()
    @JsName("CLEAR_COMMANDS")
    suspend fun CLEAR_COMMANDS()
    @JsName("CLEAR_METADATA")
    suspend fun CLEAR_METADATA()
    @JsName("CLEAR_BIG_AVATARS")
    suspend fun CLEAR_BIG_AVATARS()
    @JsName("DELETE_BIG_AVATARS")
    suspend fun DELETE_BIG_AVATARS(OBJECTS_ID: String)
    @JsName("DELETE_LASTUPDATE")
    suspend fun DELETE_LASTUPDATE(CASH_SUM: Long)
    @JsName("DELETE_ALL_SAVEMEDIA")
    suspend fun DELETE_ALL_SAVEMEDIA(CONNECTION_ID: Long, IS_TEMP: Int)
    @JsName("DELETE_SAVEMEDIA")
    suspend fun DELETE_SAVEMEDIA(OBJECTS_ID: String)
    @JsName("DELETE_ALL_SENDMEDIA")
    suspend fun DELETE_ALL_SENDMEDIA(CONNECTION_ID: Long)
    @JsName("DELETE_SENDMEDIA")
    suspend fun DELETE_SENDMEDIA(OBJECTS_ID: String)
    @JsName("DELETE_CHAT")
    suspend fun DELETE_CHAT(CHATS_ID: String)
    @JsName("CLEAR_CHATS")
    suspend fun CLEAR_CHATS()
    @JsName("DELETE_MESSEGE")
    suspend fun DELETE_MESSEGE(CHATS_ID: String, MESSEGES_ID: Long)
//////////////////////////////////////////////////////////////////////
    @JsName("INSERT_CASHDATA")
    suspend fun INSERT_CASHDATA(lANSWER_TYPE: ANSWER_TYPE, lcheck_sum: Long)
    @JsName("INSERT_LASTUPDATE")
    suspend fun INSERT_LASTUPDATE(CASH_SUM: Long, LAST_UPDATE: Long, LAST_USE: Long, IS_TEMP: Int)
    @JsName("INSERT_REGDATA")
    suspend fun INSERT_REGDATA(CONNECTION_ID: Long, CONNECTION_COOCKI: Long, MY_ID: String, LANG: String, REQUEST_PROFILE: String, ACCOUNT_PROFILE: String)
    @JsName("INSERT_COMMANDS")
    suspend fun INSERT_COMMANDS(COMMANDS_ID: Int, COMMANDS_ACCESS: String, COMMANDS_PROFILE: String, COMMANDS_NECESSARILY_FIELDS: String)
    @JsName("INSERT_METADATA")
    suspend fun INSERT_METADATA(VALUE_NAME: String, VALUE_VALUE: Int)
    @JsName("INSERT_SAVEMEDIA")
    suspend fun INSERT_SAVEMEDIA(OBJECTS_ID: String,
                         OBJECTS_EXTENSION: String,
                         CONNECTION_ID: Long,
                         OBJECT_NAME: String,
                         OBJECT_INFO: String,
                         SMALL_AVATAR: ByteArray?,
                         IS_TEMP: Int,
                         IS_DOWNLOAD: Int,
                         TIME_ADDED: Long,
                         OBJECTS_SIZE: Long)
    @JsName("INSERT_BIG_AVATARS")
    suspend fun INSERT_BIG_AVATARS(OBJECTS_ID: String,
                           TIME_ADDED: Long,
                           IS_TEMP: Int,
                           SMALL_AVATAR_SIZE: Int,
                           AVATAR: ByteArray)
    @JsName("INSERT_SENDMEDIA")
    suspend fun INSERT_SENDMEDIA(OBJECTS_ID: String,
                         OBJECTS_EXTENSION: String,
                         FILE_NAME: String,
                         CONNECTION_ID: Long,
                         IS_DOWNLOAD: Int)
    @JsName("INSERT_CHATS")
    suspend fun INSERT_CHATS(CHATS_ID: String,
                     CHATS_OWNER: String,
                     LAST_MESSEGES_ID: Long,
                     CHATS_OPPONENT: String,
                     MESSEGES_COUNT: Long,
                     CHATS_NAME: String,
                     CHATS_SMALL_AVATAR: ByteArray?,
                     CHATS_PROFILE: String,
                     CHATS_ACCESS: String,
                     CHATS_TYPE: String,
                     CHATS_STATUS: String,
                     LAST_UPDATING_DATE: Long,
                     CHATS_SUBSCRIBE: String,
                     CONNECTION_ID: String,
                     IS_UPDATE_BLOB: String)
    @JsName("INSERT_MESSEGES")
    suspend fun INSERT_MESSEGES(CHATS_ID: String,
                        MESSEGES_ID: Long,
                        MESSEGES_CHATS_COUNT: Long,
                        MESSEGES_OWNER: String,
                        MESSEGES_ANSWER: Long,
                        MESSEGES_ADRESSER: String,
                        OBJECTS_LINK: String,
                        MESSEGE_TEXT: String,
                        MESSEGES_AVATAR: ByteArray?,
                        MESSEGES_LIKES: Int,
                        MESSEGES_DISLIKES: Int,
                        NOT_DELIVERIED: Int,
                        NOT_READED: Int,
                        MESSEGES_ACCESS: String,
                        MESSEGES_TYPE: String,
                        DATE_ADDING: Long,
                        LAST_UPDATING_DATE: Long,
                        OBJECTS_LINK_SUBSCRIBE: String,
                        OBJECTS_LINK_SMALL_AVATAR: ByteArray?)
    @JsName("SELECT_COUNT_SAVEMEDIA")
    suspend fun SELECT_COUNT_SAVEMEDIA(lTemp: Int):Int

    @ExperimentalStdlibApi
    @JsName("SELECT_LAST_SAVEMEDIA")
    suspend fun SELECT_LAST_SAVEMEDIA(IS_TEMP: Int): ArrayDeque<String>
    @JsName("SELECT_BIG_AVATARS")
    suspend fun SELECT_BIG_AVATARS(OBJECTS_ID: String):KBigAvatar?
    @JsName("SELECT_ALL_REGDATA")
    suspend fun SELECT_ALL_REGDATA():KRegData?

    @ExperimentalStdlibApi
    @JsName("SELECT_ALL_METADATA")
    suspend fun SELECT_ALL_METADATA():ArrayDeque<KMetaData>
    @JsName("SELECT_METADATA")
    suspend fun SELECT_METADATA(VALUE_NAME: String):Int

    @ExperimentalStdlibApi
    @JsName("SELECT_ALL_COMMANDS")
    suspend fun SELECT_ALL_COMMANDS():ArrayDeque<KCommands>

    @ExperimentalStdlibApi
    @JsName("SELECT_ALL_SAVEMEDIA")
    suspend fun SELECT_ALL_SAVEMEDIA(CONN_ID: Long):ArrayDeque<KSaveMedia>

    @ExperimentalStdlibApi
    @JsName("SELECT_ALL_SENDMEDIA")
    suspend fun SELECT_ALL_SENDMEDIA(CONN_ID: Long):ArrayDeque<KSendMedia>

    @ExperimentalStdlibApi
    @JsName("SELECT_CASHDATA")
    suspend fun SELECT_CASHDATA(CASH_SUM: Long):ArrayDeque<ANSWER_TYPE>

    @InternalAPI
    @ExperimentalStdlibApi
    @JsName("SELECT_ALL_CHATS")
    suspend fun SELECT_ALL_CHATS(CONNECTION_ID: String):ArrayDeque<KChat>

    @ExperimentalStdlibApi
    @JsName("SELECT_MESSEGES")
    suspend fun SELECT_MESSEGES(CHATS_ID: String):ArrayDeque<KMessege>

}