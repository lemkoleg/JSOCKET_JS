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
import p_jsocket.ANSWER_TYPE
import Tables.KCommands
import p_jsocket.Constants.dbLocalName
import kotlin.time.ExperimentalTime

actual var sqlDriver: SqlDriver? = null

val alaSQL = require("alasql")
val arg = require("Arguments")
val request = require("JsSQLStatement")
var mydb: dynamic = null
val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
//val sql = require("MyAlaSQL")
//js("debugger;")

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
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
        //val u:Promise<*> = alaSQL.promise(SqliteCommands.TABLE_METADATA, PrintInformation.PRINT_INFO("created"))
        //u.await()
        //val y:Promise<*> = alaSQL.promise(SqliteCommands.INSERT_METADATA,{"fsdf" to  34}, PrintInformation.PRINT_INFO("added"))
        // y.await()
        //var rel = mydb.exec("SELECT * FROM MetaData;")
        //PrintInformation.PRINT_INFO(rel)
        //insert_metadata.promise("fsdf", 34).then(PrintInformation.PRINT_INFO("added"))
        //mydb.exec(SqliteCommands.TABLE_METADATA).then(PrintInformation.PRINT_INFO("table created"))
        // insert_metadata.promise("fsdf", 34).then(PrintInformation.PRINT_INFO("added"))

    }



    actual suspend fun clear_parameters() {
    }

    ///////////////////////////////////big avatars///////////////////////////

    actual suspend fun TABLE_BIG_AVATARS() {
        TODO()
    }

    actual suspend fun INDEX_BIG_AVATARS_LAST_USE() { // for clear old last use in trigger
        TODO()
    }

    actual suspend fun TRIGGER_BIG_AVATARS_CONTROL_COUNT() {
        TODO()
    }

    actual suspend fun INSERT_BIG_AVATAR(kBigAvatar: KBigAvatar) {
        TODO()
    }

    actual suspend fun SELECT_BIG_AVATAR(OBJECTS_ID: String): KBigAvatar? {
        TODO()
    }

    actual suspend fun SELECT_BIGAVATARS_ALL_ID(): ArrayList<String> {  //for save in memory
        TODO()
    }

    actual suspend fun SELECT_BIGAVATARS_ALL(): ArrayList<KBigAvatar> {  //for save in memory
        TODO()
    }

    actual suspend fun DELETE_BIG_AVATAR(OBJECTS_ID: String) {
        TODO()
    }

    actual suspend fun CLEAR_BIG_AVATARS() {
        TODO()
    }

    actual suspend fun UPDATE_BIG_AVATAR_LAST_USE(OBJECTS_ID: String, LAST_USE: Long) {
        TODO()
    }

///////////// commands ///////////////////////////

    actual suspend fun TABLE_COMMANDS() {
        TODO()
    }

    actual suspend fun INSERT_COMMAND(kCommand: KCommands) {
        TODO()
    }

    actual suspend fun SELECT_COMMANDS_ALL(): ArrayList<KCommands> {
        TODO()
    }

    actual suspend fun CLEAR_COMMANDS() {
        TODO()
    }

/////////////exceptions///////////////////////////

    actual suspend fun TABLE_EXCEPTION() {
        TODO()
    }

    actual suspend fun INSERT_EXCEPTION(kException: KExceptions.KException) {
        TODO()
    }

    actual suspend fun SELECT_EXCEPTION_ALL(): ArrayList<KExceptions.KException> {
        TODO()
    }

    actual suspend fun CLEAR_EXCEPTION() {
        TODO()
    }

/////////////meta data///////////////////////////

    actual suspend fun TABLE_METADATA() {
        TODO()
    }

    actual suspend fun INSERT_METADATA(kMetaData: KMetaData) {
        TODO()
    }

    actual suspend fun CLEAR_METADATA() {
        TODO()
    }

    actual suspend fun SELECT_ALL_METADATA(): ArrayList<KMetaData> {
        TODO()
    }

/////////////reg data///////////////////////////

    actual suspend fun TABLE_REGDATA() {
        TODO()
    }

    actual suspend fun TRIGGER_REGDATA_INSERT() {
        TODO()
    }

    actual suspend fun INSERT_REGDATA() {
        TODO()
    }

    actual suspend fun CLEAR_REGDATA() {
        TODO()
    }

    actual suspend fun SELECT_REGDATA_ALL() {
        TODO()
    }

/////////////save media///////////////////////////

    actual suspend fun TABLE_SAVEMEDIA() {
        TODO()
    }

    actual suspend fun INDEX_SAVEMEDIA_LASTUSED() {
        TODO()
    }

    actual suspend fun INDEX_SAVEMEDIA_ISTEMP() {
        TODO()
    }

    actual suspend fun INDEX_SAVEMEDIA_AVATAR_ID() {
        TODO()
    }

    actual suspend fun TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT() {
        TODO()
    }

    actual suspend fun TRIGGER_SAVEMEDIA_CONTROL_COUNT() {
        TODO()
    }

    actual suspend fun INSERT_SAVEMEDIA(kSaveMedia: KSaveMedia) {
        TODO()
    }

    actual suspend fun SELECT_SAVEMEDIA_ALL(conn_id: Long): ArrayList<KSaveMedia> {
        TODO()
    }

    actual suspend fun DELETE_SAVEMEDIA(v: String) {
        TODO()
    }

/////////////last update///////////////////////////

    actual suspend fun TABLE_CASHLASTUPDATE() {
        TODO()
    }

    actual suspend fun INDEX_CASHLASTUPDATE_LAST_USE() {
        TODO()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT() {
        TODO()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_LINKS_INSERT() {
        TODO()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_OBJECTS_INFO_INSERT() {
        TODO()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_CHATS_INSERT() {
        TODO()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_MESS_INSERT() {
        TODO()
    }

    actual suspend fun INSERT_CASHLASTUPDATE(kCashLastUpdate: KCashLastUpdate) {
        TODO()
    }

    actual suspend fun SELECT_CASHLASTUPDATE_ALL(): ArrayList<KCashLastUpdate> {
        TODO()
    }

    actual suspend fun CLEAR_LASTUPDATE() {
        TODO()
    }

    actual suspend fun DELETE_LASTUPDATE(cash_sum: String) {
        TODO()
    }

/////////////cash data///////////////////////////

    actual suspend fun TABLE_CASHDATA() {
        TODO()
    }

    actual suspend fun INDEX_CASHDATA_NUMBER_POSITION_ASC() {
        TODO()
    }

    actual suspend fun INDEX_CASHDATA_CASH_SUM() {
        TODO()
    }

    actual suspend fun TRIGGER_CASHDATA_AFTER_INSERT() {
        TODO()
    }

    actual suspend fun TRIGGER_CASHDATA_AFTER_INSERT_OBJECTS_INFO() {
        TODO()
    }

    actual suspend fun TRIGGER_CASHDATA_AFTER_UPDATE() {
        TODO()
    }

    actual suspend fun INSERT_CASHDATA(cash_sum: String, lANSWER_TYPE: ANSWER_TYPE) {
        TODO()
    }

    actual suspend fun SELECT_CASHDATA_ALL(): ArrayDeque<ANSWER_TYPE> {
        TODO()
    }

    actual suspend fun SELECT_CASHDATA_ALL_ON_CASH_SUM(CASH_SUM: String): ArrayDeque<ANSWER_TYPE> {
        TODO()
    }

    actual suspend fun SELECT_CASHDATA_CHUNK_ON_CASH_SUM(
        CASH_SUM: String,
        record_id_from: String
    ): ArrayDeque<ANSWER_TYPE> {
        TODO()
    }

    actual suspend fun SELECT_CASHDATA(CASH_SUM: String, RECORD_TABLE_ID: String): ANSWER_TYPE? {
        TODO()
    }

    actual suspend fun SELECT_CASHDATA_ALL_RECORDS_ID_ON_CASH_SUM(CASH_SUM: String): ArrayList<String> {
        TODO()
    }

    actual suspend fun CLEAR_CASHDATA() {
        TODO()
    }

    actual suspend fun CASHDATA_SORT_NEW_NUMBER_POSITIONS(CASH_SUM: String) {
        TODO()
    }

    actual suspend fun CASHDATA_SORT_NEW_NUMBER_POSITIONS_FINISH(CASH_SUM: String) {
        TODO()
    }

    actual suspend fun UPDATE_CASHDATA_NEW_LAST_SELECT(
        last_select: Long,
        cash_sum: String,
        record_table_id_from: String,
        limit: Int
    ) {
        TODO()
    }

    actual suspend fun DELETE_CASHDATA(cash_sum: String) {
        TODO()
    }

    actual suspend fun DELETE_CASHDATA_RECORD(cash_sum: String, record_id: String) {
        TODO()
    }




}

