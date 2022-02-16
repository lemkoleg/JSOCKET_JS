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
import p_jsocket.dbLocalName

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
        //mydb.exec(SqliteCommands.TABLE_METADATA).then(println("table created"))
        // insert_metadata.promise("fsdf", 34).then(println("added"))

    }

    ///////////////////////////////////big avatars///////////////////////////
    @JsName("TABLE_BIG_AVATARS")
    actual suspend fun TABLE_BIG_AVATARS() {
    }

    @JsName("INDEX_BIG_AVATARS_LAST_USE")  // for clear old last use in trigger
    actual suspend fun INDEX_BIG_AVATARS_LAST_USE(){
    }

    @JsName("TRIGGER_BIG_AVATARS_CONTROL_COUNT")
    actual suspend fun TRIGGER_BIG_AVATARS_CONTROL_COUNT(){
    }

    @JsName("INSERT_BIG_AVATARS")
    actual suspend fun INSERT_BIG_AVATARS(kBigAvatar: KBigAvatar){
    }

    @JsName("SELECT_BIG_AVATARS")
    actual suspend fun SELECT_BIG_AVATARS(OBJECTS_ID: String):KBigAvatar?{
        return null
    }

    @JsName("SELECT_BIGAVATARS_ALL_ID")  //for save in memory
    actual suspend fun SELECT_BIGAVATARS_ALL_ID(){
    }

    @JsName("CLEAR_BIG_AVATARS")
    actual suspend fun CLEAR_BIG_AVATARS(){
    }

    @JsName("DELETE_BIG_AVATARS")
    actual suspend fun DELETE_BIG_AVATARS(OBJECTS_ID: String){
    }

    /////////////cash data///////////////////////////

    @JsName("TABLE_CASHDATA")
    actual suspend fun TABLE_CASHDATA(){
    }

    @JsName("INDEX_CASHDATA_CASH_SUM_NUMBER_POSITION")
    actual suspend fun INDEX_CASHDATA_CASH_SUM_NUMBER_POSITION(){
    }

    @JsName("INDEX_CASHDATA_CASH_SUM_OBJECT_ID")
    actual suspend fun INDEX_CASHDATA_CASH_SUM_OBJECT_ID(){
    }

    @JsName("TRIGGER_CASHDATA_CONTROL_COUNT")
    actual suspend fun TRIGGER_CASHDATA_CONTROL_COUNT(){
    }

    @JsName("TRIGGER_CASHDATA_DELETING_RECORDS")
    actual suspend fun TRIGGER_CASHDATA_DELETING_RECORDS(){
    }

    @JsName("INSERT_CASHDATA")
    actual suspend fun INSERT_CASHDATA(lANSWER_TYPE: ANSWER_TYPE){
    }

    @JsName("UPDATE_CASHDATA_NUMBER_POSITION_LAST_UPATE")
    actual suspend fun UPDATE_CASHDATA_NUMBER_POSITION_LAST_UPATE(L_NUMBER_POSITION: Int, L_LAST_UPDATE: Long, L_CASH_SUM: Long, L_OBJECT_ID: Long){
    }

    @JsName("SELECT_CASHDATA_ALL_CASH_SUM_NUMBER_POSITION") //for save in memory
    actual suspend fun SELECT_CASHDATA_ALL_CASH_SUM_NUMBER_POSITION(){
    }

    @ExperimentalStdlibApi
    @JsName("SELECT_CASHDATA_WITH_NUMBER_POSITION_LIMIT") //for select next chunk
    actual suspend fun SELECT_CASHDATA_WITH_NUMBER_POSITION_LIMIT(CASH_SUM: Long, L_NUMBER_POSITION: Int){
    }

    @JsName("CLEAR_CASHDATA")
    actual suspend fun CLEAR_CASHDATA(){
    }

    /////////////last update///////////////////////////

    @JsName("TABLE_CASHLASTUPDATE")
    actual suspend fun TABLE_CASHLASTUPDATE(){
    }

    @JsName("INDEX_CASHLASTUPDATE_LAST_USE")
    actual suspend fun INDEX_CASHLASTUPDATE_LAST_USE(){
    }

    @JsName("INDEX_CASHLASTUPDATE_CONNECTIONID")
    actual suspend fun INDEX_CASHLASTUPDATE_CONNECTIONID(){
    }

    @JsName("TRIGGER_CASHLASTUPDATE_DELETE")
    actual suspend fun TRIGGER_CASHLASTUPDATE_DELETE(){
    }

    @JsName("TRIGGER_CASHLASTUPDATE_INSERT")
    actual suspend fun TRIGGER_CASHLASTUPDATE_INSERT(){
    }

    @JsName("INSERT_CASHLASTUPDATE")
    actual suspend fun INSERT_CASHLASTUPDATE(kCashLastUpdate:KCashLastUpdate){
    }

    @JsName("UPDATE_CASHLASTUPDATE_LAST_USE")
    actual suspend fun UPDATE_CASHLASTUPDATE_LAST_USE(kCashLastUpdate:KCashLastUpdate){
    }

    @JsName("SELECT_CASHLASTUPDATE")
    actual suspend fun SELECT_CASHLASTUPDATE(L_CONNECTION_ID : Long){
    }

    @JsName("CLEAR_LASTUPDATE")
    actual suspend fun CLEAR_LASTUPDATE(){
    }

    /////////////chats///////////////////////////

    @JsName("TABLE_CHATS")
    actual suspend fun TABLE_CHATS(){
    }

    @JsName("INDEX_CHATS_LAST_MESS_ADDING")
    actual suspend fun INDEX_CHATS_LAST_MESS_ADDING(){
    }

    @JsName("INDEX_CHATS_CONNECTIONID")
    actual suspend fun INDEX_CHATS_CONNECTIONID(){
    }

    @JsName("INDEX_CHATS_AVATARID")
    actual suspend fun INDEX_CHATS_AVATARID(){
    }

    @JsName("TRIGGER_CHATS_DELETE")
    actual suspend fun TRIGGER_CHATS_DELETE(){
    }

    @JsName("TRIGGER_CHATS_CONTROL_COUNT")
    actual suspend fun TRIGGER_CHATS_CONTROL_COUNT(){
    }

    @InternalAPI
    @JsName("INSERT_CHATS")
    actual suspend fun INSERT_CHATS(kChat: KChat){
    }

    @InternalAPI
    @JsName("SELECT_CHATS_ALL")
    actual suspend fun SELECT_CHATS_ALL(){
    }

    @JsName("CLEAR_CHATS")
    actual suspend fun CLEAR_CHATS(){
    }

    /////////////chats cost types///////////////////////////

    @JsName("TABLE_CHATS_COST_TYPES")
    actual suspend fun TABLE_CHATS_COST_TYPES(){
    }

    @JsName("INSERT_CHATS_COST_TYPES")
    actual suspend fun INSERT_CHATS_COST_TYPES(kChatsCostTypes: KChatsCostTypes){
    }

    @JsName("SELECT_CHATS_COST_TYPES_ALL")
    actual suspend fun SELECT_CHATS_COST_TYPES_ALL(){
    }

    @JsName("CLEAR_CHATS_COST_TYPES")
    actual suspend fun CLEAR_CHATS_COST_TYPES(){
    }

    /////////////chats likes///////////////////////////

    @JsName("TABLE_CHATS_LIKES")
    actual suspend fun TABLE_CHATS_LIKES(){
    }

    @JsName("INDEX_CHATSLIKES_AVATARID")
    actual suspend fun INDEX_CHATSLIKES_AVATARID(){
    }

    @JsName("INDEX_CHATSLIKES_ACCOUNTSID")
    actual suspend fun INDEX_CHATSLIKES_ACCOUNTSID(){
    }

    @JsName("TRIGGER_CHATSLIKES_DELETED_RECORD")
    actual suspend fun TRIGGER_CHATSLIKES_DELETED_RECORD(){
    }

    @JsName("INSERT_CHATS_LIKES")
    actual suspend fun INSERT_CHATS_LIKES(kChatsLikes: KChatsLikes){
    }

    @JsName("SELECT_CHATSLIKES_ALL")
    actual suspend fun SELECT_CHATSLIKES_ALL(){
    }

    /////////////commands///////////////////////////

    @JsName("TABLE_COMMANDS")
    actual suspend fun TABLE_COMMANDS(){
    }

    @JsName("INSERT_COMMANDS")
    actual suspend fun INSERT_COMMANDS(kCommands: KCommands){
    }

    @JsName("SELECT_COMMANDS_ALL")
    actual suspend fun SELECT_COMMANDS_ALL(){
    }

    @JsName("CLEAR_COMMANDS")
    actual suspend fun CLEAR_COMMANDS(){
    }

    /////////////exceptions///////////////////////////

    @JsName("TABLE_EXCEPTION")
    actual suspend fun TABLE_EXCEPTION(){
    }

    @JsName("INSERT_EXCEPTION")
    actual suspend fun INSERT_EXCEPTION(kExceptions: KExceptions){
    }

    @JsName("SELECT_EXCEPTION_ALL")
    actual suspend fun SELECT_EXCEPTION_ALL(){
    }

    @JsName("CLEAR_EXCEPTION")
    actual suspend fun CLEAR_EXCEPTION(){
    }

    /////////////messeges///////////////////////////

    @JsName("TABLE_MESSEGES")
    actual suspend fun TABLE_MESSEGES(){
    }

    @JsName("INDEX_MESSEGES_ORDER_DESC")
    actual suspend fun INDEX_MESSEGES_ORDER_DESC(){
    }

    @JsName("TRIGGER_MESSEGES_CONTROL_COUNT")
    actual suspend fun TRIGGER_MESSEGES_CONTROL_COUNT(){
    }

    @JsName("TRIGGER_MESSEGES_MESS_WITHOUT_CAHTS")
    actual suspend fun TRIGGER_MESSEGES_MESS_WITHOUT_CAHTS(){
    }

    @JsName("INSERT_MESSEGES")
    actual suspend fun INSERT_MESSEGES(kMessege: KMessege){
    }

    @JsName("SELECT_MESSEGES_LIMIT")
    actual suspend fun SELECT_MESSEGES_LIMIT(L_CHATS_ID: String, L_MESSEGES_COUNT: Long){
    }

    @JsName("CLEAR_MESSEGES")
    actual suspend fun CLEAR_MESSEGES(){
    }

    /////////////meta data///////////////////////////
    @JsName("TABLE_METADATA")
    actual suspend fun TABLE_METADATA(){
    }

    @JsName("INSERT_METADATA")
    actual suspend fun INSERT_METADATA(kMetaData: KMetaData){
    }

    @JsName("CLEAR_METADATA")
    actual suspend fun CLEAR_METADATA(){
    }

    @JsName("SELECT_ALL_METADATA")
    actual suspend fun SELECT_ALL_METADATA(){
    }

    /////////////reg data///////////////////////////

    @JsName("TABLE_REGDATA")
    actual suspend fun TABLE_REGDATA(){
    }

    @JsName("TRIGGER_REGDATA_INSERT")
    actual suspend fun TRIGGER_REGDATA_INSERT(){
    }

    @JsName("TRIGGER_REGDATA_DELETE")
    actual suspend fun TRIGGER_REGDATA_DELETE(){
    }

    @JsName("INSERT_REGDATA")
    actual suspend fun INSERT_REGDATA(kRegData: KRegData){
    }

    @JsName("CLEAR_REGDATA")
    actual suspend fun CLEAR_REGDATA(){
    }

    @JsName("SELECT_REGDATA_ALL")
    actual suspend fun SELECT_REGDATA_ALL(){
    }

    /////////////save media///////////////////////////

    @JsName("TABLE_SAVEMEDIA")
    actual suspend fun TABLE_SAVEMEDIA(){
    }

    @JsName("INDEX_SAVEMEDIA_CONNECTIONID")
    actual suspend fun INDEX_SAVEMEDIA_CONNECTIONID(){
    }

    @JsName("INDEX_SAVEMEDIA_LASTUSED")
    actual suspend fun INDEX_SAVEMEDIA_LASTUSED(){
    }

    @JsName("INDEX_SAVEMEDIA_ISTEMP")
    actual suspend fun INDEX_SAVEMEDIA_ISTEMP(){
    }

    @JsName("INDEX_SAVEMEDIA_AVATARID")
    actual suspend fun INDEX_SAVEMEDIA_AVATARID(){
    }

    @JsName("TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT")
    actual suspend fun TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT(){
    }

    @JsName("TRIGGER_SAVEMEDIA_CONTROL_COUNT")
    actual suspend fun TRIGGER_SAVEMEDIA_CONTROL_COUNT(){
    }

    @JsName("INSERT_SAVEMEDIA")
    actual suspend fun INSERT_SAVEMEDIA(){
    }

    @JsName("SELECT_SAVEMEDIA_ALL")
    actual suspend fun SELECT_SAVEMEDIA_ALL(kSaveMedia: KSaveMedia){
    }

    @JsName("DELETE_SAVEMEDIA")
    actual suspend fun DELETE_SAVEMEDIA() {
    }

    /////////////////////////////////////////////////////////////////////////


}

