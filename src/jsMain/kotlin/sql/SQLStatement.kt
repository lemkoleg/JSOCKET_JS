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

    ///////////////////////////////////big avatars///////////////////////////
    actual suspend fun TABLE_BIG_AVATARS() {
    }

    actual suspend fun INDEX_BIG_AVATARS_LAST_USE(){
    }

    actual suspend fun TRIGGER_BIG_AVATARS_CONTROL_COUNT(){
    }

    actual suspend fun INSERT_BIG_AVATAR(kBigAvatar: KBigAvatar){
    }

    actual suspend fun SELECT_BIG_AVATAR(OBJECTS_ID: String):KBigAvatar?{
        return null
    }

    actual suspend fun SELECT_BIGAVATARS_ALL_ID(){
    }

    actual suspend fun SELECT_BIGAVATARS_ALL(){
    }

    actual suspend fun CLEAR_BIG_AVATARS(){
    }

    actual suspend fun DELETE_BIG_AVATAR(OBJECTS_ID: String){
    }

    actual suspend fun UPDATE_BIG_AVATAR_LAST_USE(OBJECTS_ID: String, LAST_USE: Long){
    }

    /////////////cash data///////////////////////////

    actual suspend fun TABLE_CASHDATA(){

    }

    actual suspend fun INDEX_CASHDATA_NUMBER_POSITION(){
    }

    actual suspend fun INSERT_CASHDATA(lANSWER_TYPE: ANSWER_TYPE){

    }

    actual suspend fun INSERT_CASHDATAS(lANSWER_TYPES: ArrayDeque<ANSWER_TYPE>){

    }

    actual suspend fun SELECT_CASHDATA_ALL() {
    }

    actual suspend fun SELECT_CASHDATA_ALL_ON_CASH_SUM(CASH_SUM: Long) {
    }


    actual suspend fun CLEAR_CASHDATA(){

    }

    actual fun CASHDATA_SORT_NEW_NUMBER_POSITIONS(){

    }

    /////////////last update///////////////////////////

    actual suspend fun TABLE_CASHLASTUPDATE(){
    }

    actual suspend fun INDEX_CASHLASTUPDATE_LAST_USE(){
    }

    actual suspend fun INDEX_CASHLASTUPDATE_CONNECTIONID(){
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_DELETE(){
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT(){
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_UPDATE(){
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_UPDATE(){
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT(){
    }

    actual suspend fun INSERT_CASHLASTUPDATE(kCashLastUpdate:KCashLastUpdate){
    }

    actual suspend fun UPDATE_CASHLASTUPDATE_LAST_USE(kCashLastUpdate:KCashLastUpdate){
    }

    actual suspend fun SELECT_CASHLASTUPDATE(L_CONNECTION_ID : Long){
    }

    actual suspend fun CLEAR_LASTUPDATE(){
    }

    /////////////chats///////////////////////////

    actual suspend fun TABLE_CHATS(){
    }

    actual suspend fun INDEX_CHATS_LAST_MESS_ADDING(){
    }

    actual suspend fun INDEX_CHATS_CONNECTIONID(){
    }

    actual suspend fun INDEX_CHATS_AVATARID(){
    }

    actual suspend fun TRIGGER_CHATS_DELETE(){
    }

    actual suspend fun TRIGGER_CHATS_CONTROL_COUNT(){
    }

    @InternalAPI
    actual suspend fun INSERT_CHATS(kChat: KChat){
    }

    @InternalAPI
    actual suspend fun SELECT_CHATS_ALL(){
    }

    actual suspend fun CLEAR_CHATS(){
    }

    /////////////chats cost types///////////////////////////

    actual suspend fun TABLE_CHATS_COST_TYPES(){
    }

    actual suspend fun INSERT_CHATS_COST_TYPES(kChatsCostTypes: KChatsCostTypes){
    }

    actual suspend fun SELECT_CHATS_COST_TYPES_ALL(){
    }

    actual suspend fun CLEAR_CHATS_COST_TYPES(){
    }

    /////////////chats likes///////////////////////////

    actual suspend fun TABLE_CHATS_LIKES(){
    }

    actual suspend fun INDEX_CHATSLIKES_AVATARID(){
    }

    actual suspend fun INDEX_CHATSLIKES_ACCOUNTSID(){
    }

    actual suspend fun TRIGGER_CHATSLIKES_DELETED_RECORD(){
    }

    actual suspend fun INSERT_CHATS_LIKES(kChatsLikes: KChatsLikes){
    }

    actual suspend fun SELECT_CHATSLIKES_ALL(){
    }

    /////////////commands///////////////////////////

    actual suspend fun TABLE_COMMANDS(){
    }

    actual suspend fun INSERT_COMMANDS(kCommands: KCommands){
    }

    actual suspend fun SELECT_COMMANDS_ALL(){
    }

    actual suspend fun CLEAR_COMMANDS(){
    }

    /////////////exceptions///////////////////////////

    actual suspend fun TABLE_EXCEPTION(){
    }

    actual suspend fun INSERT_EXCEPTION(kExceptions: KExceptions){
    }

    actual suspend fun SELECT_EXCEPTION_ALL(){
    }

    actual suspend fun CLEAR_EXCEPTION(){
    }

    /////////////messeges///////////////////////////

    actual suspend fun TABLE_MESSEGES(){
    }

    actual suspend fun INDEX_MESSEGES_ORDER_DESC(){
    }

    actual suspend fun TRIGGER_MESSEGES_CONTROL_COUNT(){
    }

    actual suspend fun TRIGGER_MESSEGES_MESS_WITHOUT_CAHTS(){
    }

    actual suspend fun INSERT_MESSEGES(kMessege: KMessege){
    }

    actual suspend fun SELECT_MESSEGES_LIMIT(L_CHATS_ID: String, L_MESSEGES_COUNT: Long){
    }

    actual suspend fun CLEAR_MESSEGES(){
    }

    /////////////meta data///////////////////////////
    actual suspend fun TABLE_METADATA(){
    }

    actual suspend fun INSERT_METADATA(kMetaData: KMetaData){
    }

    actual suspend fun CLEAR_METADATA(){
    }

    actual suspend fun SELECT_ALL_METADATA(){
    }

    /////////////reg data///////////////////////////

    actual suspend fun TABLE_REGDATA(){
    }

    actual suspend fun TRIGGER_REGDATA_INSERT(){
    }

    actual suspend fun TRIGGER_REGDATA_DELETE(){
    }

    actual suspend fun INSERT_REGDATA(kRegData: KRegData){
    }

    actual suspend fun CLEAR_REGDATA(){
    }

    actual suspend fun SELECT_REGDATA_ALL(){
    }

    /////////////save media///////////////////////////

    actual suspend fun TABLE_SAVEMEDIA(){
    }

    actual suspend fun INDEX_SAVEMEDIA_CONNECTIONID(){
    }

    actual suspend fun INDEX_SAVEMEDIA_LASTUSED(){
    }

    actual suspend fun INDEX_SAVEMEDIA_ISTEMP(){
    }

    actual suspend fun INDEX_SAVEMEDIA_AVATARID(){
    }

    actual suspend fun TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT(){
    }

    actual suspend fun TRIGGER_SAVEMEDIA_CONTROL_COUNT(){
    }

    actual suspend fun INSERT_SAVEMEDIA(){
    }

    actual suspend fun SELECT_SAVEMEDIA_ALL(kSaveMedia: KSaveMedia){
    }

    actual suspend fun DELETE_SAVEMEDIA() {
    }

    /////////////////////////////////////////////////////////////////////////

    actual suspend fun clear_parameters(){

    }


}

