@file:Suppress("RedundantSuspendModifier", "UnsafeCastFromDynamic")

package sql

import CrossPlatforms.require
import Tables.*
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import p_client.dbLocalName
import p_jsocket.ANSWER_TYPE
import Tables.KCommands

actual var sqlDriver: SqlDriver? = null

val alaSQL = require("alasql")
val arg = require("Arguments")
val request = require("JsSQLStatement")
var mydb: dynamic = null
val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
//val sql = require("MyAlaSQL")
//js("debugger;")


actual class SQLStatement actual constructor() {

    @KorioExperimentalApi
    actual suspend fun connect() {

        /*val createDB = arrayOf("""CREATE INDEXEDDB DATABASE IF NOT EXISTS $dbLocalName;""",
                               """ATTACH INDEXEDDB DATABASE $dbLocalName;""",
                               """USE $dbLocalName; """)

        request.DBrequest(alaSQL, createDB)*/

        //val l: Promise<*> = alaSQL.promise("""CREATE INDEXEDDB DATABASE IF NOT EXISTS $dbLocalName;""")
        //l.await()
        val y: Promise<*> = alaSQL.promise("""ATTACH INDEXEDDB DATABASE $dbLocalName;""")
        y.await()
        val u: Promise<*> = alaSQL.promise("""USE $dbLocalName; """)
        u.await()
        mydb = alaSQL.Database(dbLocalName)
        console.log(mydb)
        //mydb.exec(TABLE_METADATA)
        //alaSQL.promise(TABLE_METADATA)
        request.DBrequest(alaSQL, TABLE_METADATA)




        //Thread_sleep(10000)
        console.log("222222222222222")


        console.log("rewrewrrew")
        /*try {
            c.await()
        }
        catch (e:Exception){}*/
        console.log("3333333333333333")

        //insert_metadata(arg.createArguments("fsdf", 34))
        //val re: Promise<*> = alaSQL.promise(INSERT_METADATA, arg.createArguments("fsdf", 34))
        //re.await()
        //val res = select_metadata()
        //var res: dynamic
        //alaSQL.promise(SELECT_METADATA).then{v: dynamic -> console.log("res = $v");}
        //console.log("res = $res");


        //console.log(alaSQL)
        //mydb.exec(SqliteCommands.TABLE_METADATA)
        //val u:Promise<*> = alaSQL.promise(SqliteCommands.TABLE_METADATA, println("created"))
        //u.await()
        //val y:Promise<*> = alaSQL.promise(SqliteCommands.INSERT_METADATA,{"fsdf" to  34}, println("added"))
        // y.await()
        //var rel = mydb.exec("SELECT * FROM MetaData;")
        //println(rel)
        //insert_metadata.promise("fsdf", 34).then(println("added"))

    }

    actual suspend fun TABLE_METADATA() {
        //mydb.exec(SqliteCommands.TABLE_METADATA).then(println("table created"))
        // insert_metadata.promise("fsdf", 34).then(println("added"))
    }

    actual suspend fun TABLE_CASHDATA() {
        /*
        alaSQL(SqliteCommands.TABLE_CASHDATA)
        val l = ANSWER_TYPE()
        l.IDENTIFICATOR_1 = "783778178384785377"
        l.createIDENTIFICATOTS()
        //js("debugger;")
        insert_cashdata(34535353L, l.IDENTIFICATORS,
            l.IDENTIFICATOR_1, l.IDENTIFICATOR_2, l.IDENTIFICATOR_3,
            l.IDENTIFICATOR_4, l.IDENTIFICATOR_5, l.IDENTIFICATOR_6,
            l.IDENTIFICATOR_7, l.IDENTIFICATOR_8, l.IDENTIFICATOR_9, l.IDENTIFICATOR_10,
             l.INTEGER_1, l.INTEGER_2, l.INTEGER_3, l.INTEGER_4, l.INTEGER_5,
            l.INTEGER_6, l.INTEGER_7, l.INTEGER_8, l.INTEGER_9, l.INTEGER_10,
            l.LONG_1, l.LONG_2, l.LONG_3, l.LONG_4, l.LONG_5, l.LONG_6, l.LONG_7,
            l.LONG_8, l.LONG_9, l.LONG_10, l.STRING_1, l.STRING_2, l.STRING_3,
            l.STRING_4, l.STRING_5,l.STRING_6, l.STRING_7,l.STRING_8,l.STRING_9,
            l.STRING_10, l.BLOB_1, l.BLOB_2, l.BLOB_3, l.BLOB_4, l.BLOB_5, l.BLOB_6,
            l.BLOB_7, l.BLOB_8, l.BLOB_9, l.BLOB_10
        )*/
    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_1() {
        /* alaSQL.fn.TRIGGER_CASHDATA_BLOB_1 = { t:dynamic->
             println("catch trigger {$t.IDENTIFICATORS}")
             if(t.BLOB_1 == null && (t.LONG_10.substring(1,2) == '0')) {

                 val oldRecord:dynamic = select_cashdata(t.CASH_SUM, t.IDENTIFICATORS)
                 t.BLOB_1 == oldRecord.BLOB_1
             }
         }

         alaSQL("CREATE TRIGGER TCashData_BLOB_1 BEFORE UPDATE ON CashData CALL TRIGGER_CASHDATA_BLOB_1()")
     */
    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_2() {

    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_3() {

    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_4() {

    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_5() {

    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_6() {

    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_7() {

    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_8() {

    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_9() {

    }

    actual suspend fun TRIGGER_CASHDATA_BLOB_10() {

    }

    actual suspend fun TRIGGER_CASHDATA_DELETING_RECORDS() {

    }

    actual suspend fun TABLE_REGDATA() {
    }

    actual suspend fun TRIGGER_INSERT_REGDATA() {
    }

    actual suspend fun TABLE_COMMANDS() {
    }

    actual suspend fun TABLE_SAVEMEDIA() {
    }

    actual suspend fun INDEX_SAVEMEDIA() {
    }

    actual suspend fun TABLE_BIG_AVATARS() {
    }

    actual suspend fun INDEX_BIG_AVATARS() {
    }

    actual suspend fun TRIGGER_CONTROL_COUNT_BIG_AVATARS() {
    }

    actual suspend fun TABLE_SENDMEDIA() {
    }

    actual suspend fun TRIGGER_INSERT_SENDMEDIA() {
    }

    actual suspend fun TABLE_LASTUPDATE() {
    }

    actual suspend fun INDEX_LASTUPDATE() {
    }

    actual suspend fun TRIGGER_DELETE_LASTUPDATE() {
    }

    actual suspend fun TRIGGER_INSERT_LASTUPDATE() {
    }

    actual suspend fun TRIGGER_CONTROL_COUNT_CASH() {
    }

    actual suspend fun TABLE_CHATS() {
    }

    actual suspend fun TRIGGER_DELETE_CHATS() {
    }

    actual suspend fun TRIGGER_CONTROL_COUNT_CHATS() {
    }

    actual suspend fun TABLE_MESSEGES() {
    }

    actual suspend fun TRIGGER_CONTROL_COUNT_MESSEGES() {
    }

    actual suspend fun UPDATE_LAST_USE_LASTUPDATE(LAST_USE: Long, CASH_SUM: Long) {
    }

    actual suspend fun CLEAR_CASHDATA() {
    }

    actual suspend fun CLEAR_LASTUPDATE() {
    }

    actual suspend fun CLEAR_REGDATA() {
    }

    actual suspend fun CLEAR_COMMANDS() {
    }

    actual suspend fun CLEAR_METADATA() {
    }

    actual suspend fun CLEAR_BIG_AVATARS() {
    }

    actual suspend fun DELETE_BIG_AVATARS(OBJECTS_ID: String) {
    }

    actual suspend fun DELETE_LASTUPDATE(CASH_SUM: Long) {
    }

    actual suspend fun DELETE_ALL_SAVEMEDIA(CONNECTION_ID: Long, IS_TEMP: Int) {
    }

    actual suspend fun DELETE_SAVEMEDIA(OBJECTS_ID: String) {
    }

    actual suspend fun DELETE_ALL_SENDMEDIA(CONNECTION_ID: Long) {
    }

    actual suspend fun DELETE_SENDMEDIA(OBJECTS_ID: String) {
    }

    actual suspend fun DELETE_CHAT(CHATS_ID: String) {
    }

    actual suspend fun CLEAR_CHATS() {
    }

    actual suspend fun DELETE_MESSEGE(CHATS_ID: String, MESSEGES_ID: Long) {
    }

    actual suspend fun INSERT_CASHDATA(lANSWER_TYPE: ANSWER_TYPE, lcheck_sum: Long) {
    }

    actual suspend fun INSERT_LASTUPDATE(
        CASH_SUM: Long,
        LAST_UPDATE: Long,
        LAST_USE: Long,
        IS_TEMP: Int
    ) {
    }

    actual suspend fun INSERT_REGDATA(
        CONNECTION_ID: Long,
        CONNECTION_COOCKI: Long,
        MY_ID: String,
        LANG: String,
        REQUEST_PROFILE: String,
        ACCOUNT_PROFILE: String
    ) {
    }

    actual suspend fun INSERT_COMMANDS(
        COMMANDS_ID: Int,
        COMMANDS_ACCESS: String,
        COMMANDS_PROFILE: String,
        COMMANDS_NECESSARILY_FIELDS: String
    ) {
    }

    actual suspend fun INSERT_METADATA(VALUE_NAME: String, VALUE_VALUE: Int) {
    }

    actual suspend fun INSERT_SAVEMEDIA(
        OBJECTS_ID: String,
        OBJECTS_EXTENSION: String,
        CONNECTION_ID: Long,
        OBJECT_NAME: String,
        OBJECT_INFO: String,
        SMALL_AVATAR: ByteArray?,
        IS_TEMP: Int,
        IS_DOWNLOAD: Int,
        TIME_ADDED: Long,
        OBJECTS_SIZE: Long
    ) {
    }

    actual suspend fun INSERT_BIG_AVATARS(
        OBJECTS_ID: String,
        TIME_ADDED: Long,
        IS_TEMP: Int,
        SMALL_AVATAR_SIZE: Int,
        AVATAR: ByteArray
    ) {
    }

    actual suspend fun INSERT_SENDMEDIA(
        OBJECTS_ID: String,
        OBJECTS_EXTENSION: String,
        FILE_NAME: String,
        CONNECTION_ID: Long,
        IS_DOWNLOAD: Int
    ) {
    }

    actual suspend fun INSERT_CHATS(
        CHATS_ID: String,
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
        IS_UPDATE_BLOB: String
    ) {
    }

    actual suspend fun INSERT_MESSEGES(
        CHATS_ID: String,
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
        OBJECTS_LINK_SMALL_AVATAR: ByteArray?
    ) {
    }

    actual suspend fun SELECT_LASTUPDATE(CASH_SUM: Long): Long? {
        TODO("Not yet implemented")
    }

    actual suspend fun SELECT_COUNT_SAVEMEDIA(lTemp: Int): Int {
        TODO("Not yet implemented")
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_LAST_SAVEMEDIA(IS_TEMP: Int): ArrayDeque<String> {
        TODO("Not yet implemented")
    }

    actual suspend fun SELECT_BIG_AVATARS(OBJECTS_ID: String): KBigAvatar? {
        TODO("Not yet implemented")
    }

    actual suspend fun SELECT_ALL_REGDATA(): KRegData? {
        TODO("Not yet implemented")
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_METADATA(): ArrayDeque<KMetaData> {
        TODO("Not yet implemented")
    }

    actual suspend fun SELECT_METADATA(VALUE_NAME: String): Int {
        TODO("Not yet implemented")
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_COMMANDS(): ArrayDeque<KCommands> {
        TODO("Not yet implemented")
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_SAVEMEDIA(CONN_ID: Long): ArrayDeque<KSaveMedia> {
        TODO("Not yet implemented")
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_SENDMEDIA(CONN_ID: Long): ArrayDeque<KSendMedia> {
        TODO("Not yet implemented")
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_CASHDATA(CASH_SUM: Long): ArrayDeque<ANSWER_TYPE> {
        TODO("Not yet implemented")
    }

    @InternalAPI
    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_CHATS(CONNECTION_ID: String): ArrayDeque<KChat> {
        TODO("Not yet implemented")
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_MESSEGES(CHATS_ID: String): ArrayDeque<KMessege> {
        TODO("Not yet implemented")
    }

}

