@file:Suppress("DuplicatedCode", "RedundantSuspendModifier")

package sql

import CrossPlatforms.slash
import Tables.*
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.ktor.util.InternalAPI
import p_jsocket.ANSWER_TYPE
import Tables.KCommands
import p_jsocket.rootPath


actual var sqlDriver: SqlDriver? = JdbcSqliteDriver("""jdbc:sqlite:${rootPath}${slash}$dbLocalName.db""")


actual class SQLStatement actual constructor() {


    val bigAvatars = db!!.bIGAVATARSQueries
    val cashData = db!!.cASHDATAQueries
    val cashLastUpdate = db!!.cASHLASTUPDATEQueries
    val chats = db!!.cHATSQueries
    val chatsCostTypes = db!!.cHATS_COST_TYPESQueries
    val chatsLikes = db!!.cHATS_LIKESQueries
    val commands = db!!.cOMMANDSQueries
    val exceptions = db!!.eXCEPTIONSQueries
    val messeges = db!!.mESSEGESQueries
    val metaData = db!!.mETADATAQueries
    val regData = db!!.rEGDATAQueries
    val saveMedia = db!!.sAVEMEDIAQueries

    ///////////////////////////////////big avatars///////////////////////////
    actual suspend fun TABLE_BIG_AVATARS(){
        bigAvatars.table_BigAvatars()
    }

    actual suspend fun INDEX_BIG_AVATARS_LAST_USE(){
        bigAvatars.index_BigAvatars_lastUse()
    }

    actual suspend fun TRIGGER_BIG_AVATARS_CONTROL_COUNT(){
        bigAvatars.trigger_BigAvatars_control_count()
    }

    actual suspend fun INSERT_BIG_AVATAR(kBigAvatar: KBigAvatar){
        kBigAvatar.getAVATAR()?.let {
            bigAvatars.insert_BigAvatar(kBigAvatar.getAVATAR_ID(),
                kBigAvatar.getLAST_USE(),
                it
            )
        }
    }

    actual suspend fun SELECT_BIG_AVATAR(OBJECTS_ID: String):KBigAvatar?{
        var kBigAvatar: KBigAvatar? = BIG_AVATARS[OBJECTS_ID]
        if(kBigAvatar == null){

        }
        return kBigAvatar

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

    actual suspend fun INSERT_CHATS(kChat: KChat){

    }

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

    actual suspend fun SELECT_MESSEGES_LIMIT(L_CHATS_ID: String, L_MESSEGES_COUNT: Long = 999999999999999999){

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

    actual suspend fun SELECT_ALL_METADATA() {

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

    actual suspend fun DELETE_SAVEMEDIA(){

    }

    /////////////////////////////////////////////////////////////////////////
    actual suspend fun connect(){

    }

    actual suspend fun clear_parameters(){
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_METADATA(){
        val res = metaData.select_MetaData_all().execute()
        while (res.next()) {
            KMetaData.setMETA_DATA(res.getString(0)!!, res.getLong(1)!!, res.getLong(2)!!)
        }
    }

    actual suspend fun SELECT_METADATA(VALUE_NAME: String){
        val res = metaData.select_MetaData(VALUE_NAME).execute()
        while (res.next()) {
            KMetaData.setMETA_DATA(res.getString(0)!!, res.getLong(1)!!, res.getLong(2)!!)
        }
    }


    actual suspend fun INSERT_CASHDATA(lANSWER_TYPE: ANSWER_TYPE, lcheck_sum: Long) {
        lANSWER_TYPE.createIDENTIFICATOTS()
        cashData.insert_cashdata(
            lcheck_sum,
            lANSWER_TYPE.IDENTIFICATORS!!,
            lANSWER_TYPE.IDENTIFICATOR_1!!,
            lANSWER_TYPE.IDENTIFICATOR_2!!,
            lANSWER_TYPE.IDENTIFICATOR_3!!,
            lANSWER_TYPE.IDENTIFICATOR_4!!,
            lANSWER_TYPE.IDENTIFICATOR_5!!,
            lANSWER_TYPE.IDENTIFICATOR_6!!,
            lANSWER_TYPE.IDENTIFICATOR_7!!,
            lANSWER_TYPE.IDENTIFICATOR_8!!,
            lANSWER_TYPE.IDENTIFICATOR_9!!,
            lANSWER_TYPE.IDENTIFICATOR_10!!,
            lANSWER_TYPE.INTEGER_1!!,
            lANSWER_TYPE.INTEGER_2!!,
            lANSWER_TYPE.INTEGER_3!!,
            lANSWER_TYPE.INTEGER_4!!,
            lANSWER_TYPE.INTEGER_5!!,
            lANSWER_TYPE.INTEGER_6!!,
            lANSWER_TYPE.INTEGER_7!!,
            lANSWER_TYPE.INTEGER_8!!,
            lANSWER_TYPE.INTEGER_9!!,
            lANSWER_TYPE.INTEGER_10!!,
            lANSWER_TYPE.LONG_1!!.toString(),
            lANSWER_TYPE.LONG_2!!.toString(),
            lANSWER_TYPE.LONG_3!!.toString(),
            lANSWER_TYPE.LONG_4!!.toString(),
            lANSWER_TYPE.LONG_5!!.toString(),
            lANSWER_TYPE.LONG_6!!.toString(),
            lANSWER_TYPE.LONG_7!!.toString(),
            lANSWER_TYPE.LONG_8!!.toString(),
            lANSWER_TYPE.LONG_9!!.toString(),
            lANSWER_TYPE.LONG_10!!.toString(),
            lANSWER_TYPE.STRING_1!!,
            lANSWER_TYPE.STRING_2!!,
            lANSWER_TYPE.STRING_3!!,
            lANSWER_TYPE.STRING_4!!,
            lANSWER_TYPE.STRING_5!!,
            lANSWER_TYPE.STRING_6!!,
            lANSWER_TYPE.STRING_7!!,
            lANSWER_TYPE.STRING_8!!,
            lANSWER_TYPE.STRING_9!!,
            lANSWER_TYPE.STRING_10!!,
            lANSWER_TYPE.BLOB_1,
            lANSWER_TYPE.BLOB_2,
            lANSWER_TYPE.BLOB_3,
            lANSWER_TYPE.BLOB_4,
            lANSWER_TYPE.BLOB_5,
            lANSWER_TYPE.BLOB_6,
            lANSWER_TYPE.BLOB_7,
            lANSWER_TYPE.BLOB_8,
            lANSWER_TYPE.BLOB_9,
            lANSWER_TYPE.BLOB_10

        )
    }

    actual suspend fun INSERT_LASTUPDATE(
        CASH_SUM: Long,
        LAST_UPDATE: Long,
        LAST_USE: Long,
        IS_TEMP: Int
    ) {
        lastUpdate.insert_lastupdate(CASH_SUM.toString(), LAST_UPDATE.toString(), LAST_USE.toString(), IS_TEMP)
    }

    actual suspend fun INSERT_REGDATA(
        CONNECTION_ID: Long,
        CONNECTION_COOCKI: Long,
        MY_ID: String,
        LANG: String,
        REQUEST_PROFILE: String,
        ACCOUNT_PROFILE: String
    ) {
        regData.insert_regdata(CONNECTION_ID.toString(), CONNECTION_COOCKI.toString(), MY_ID, LANG, REQUEST_PROFILE, ACCOUNT_PROFILE)
    }

    actual suspend fun INSERT_COMMANDS(
        COMMANDS_ID: Int,
        COMMANDS_ACCESS: String,
        COMMANDS_PROFILE: String,
        COMMANDS_NECESSARILY_FIELDS: String
    ) {
        commands.insert_commands(COMMANDS_ID, COMMANDS_ACCESS, COMMANDS_PROFILE, COMMANDS_NECESSARILY_FIELDS)
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
        saveMedia.insert_savemedia(
            OBJECTS_ID,
            OBJECTS_EXTENSION,
            CONNECTION_ID.toString(),
            OBJECT_NAME,
            OBJECT_INFO,
            SMALL_AVATAR,
            IS_TEMP,
            IS_DOWNLOAD,
            TIME_ADDED.toString(),
            OBJECTS_SIZE.toString()
        )
    }

    actual suspend fun INSERT_BIG_AVATARS(
        OBJECTS_ID: String,
        TIME_ADDED: Long,
        IS_TEMP: Int,
        SMALL_AVATAR_SIZE: Int,
        AVATAR: ByteArray
    ) {
        bigAvatars.insert_big_avatars(
            OBJECTS_ID,
            TIME_ADDED.toString(),
            IS_TEMP,
            SMALL_AVATAR_SIZE,
            AVATAR
        )
    }

    actual suspend fun INSERT_SENDMEDIA(
        OBJECTS_ID: String,
        OBJECTS_EXTENSION: String,
        FILE_NAME: String,
        CONNECTION_ID: Long,
        IS_DOWNLOAD: Int
    ) {
        sendMedia.insert_sendmedia(
            OBJECTS_ID,
            OBJECTS_EXTENSION,
            FILE_NAME,
            CONNECTION_ID.toString(),
            IS_DOWNLOAD
        )
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
        chats.insert_chats(
            CHATS_ID,
            CHATS_OWNER,
            LAST_MESSEGES_ID.toString(),
            CHATS_OPPONENT,
            MESSEGES_COUNT.toString(),
            CHATS_NAME,
            CHATS_SMALL_AVATAR,
            CHATS_PROFILE,
            CHATS_ACCESS,
            CHATS_TYPE,
            CHATS_STATUS,
            LAST_UPDATING_DATE,
            CHATS_SUBSCRIBE,
            CONNECTION_ID,
            IS_UPDATE_BLOB
        )
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
        messeges.insert_messeges(
            CHATS_ID,
            MESSEGES_ID.toString(),
            MESSEGES_CHATS_COUNT.toString(),
            MESSEGES_OWNER,
            MESSEGES_ANSWER.toString(),
            MESSEGES_ADRESSER,
            OBJECTS_LINK,
            MESSEGE_TEXT,
            MESSEGES_AVATAR,
            MESSEGES_LIKES,
            MESSEGES_DISLIKES,
            NOT_DELIVERIED,
            NOT_READED,
            MESSEGES_ACCESS,
            MESSEGES_TYPE,
            DATE_ADDING.toString(),
            LAST_UPDATING_DATE.toString(),
            OBJECTS_LINK_SUBSCRIBE,
            OBJECTS_LINK_SMALL_AVATAR
        )
    }


    actual suspend fun SELECT_COUNT_SAVEMEDIA(lTemp: Int): Int {
        return saveMedia.select_count_savemedia(lTemp).executeAsOne().toInt()
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_LAST_SAVEMEDIA(IS_TEMP: Int): ArrayDeque<String> {
        return ArrayDeque(saveMedia.select_last_savemedia(IS_TEMP, IS_TEMP).executeAsList())
    }

    actual suspend fun SELECT_BIG_AVATARS(OBJECTS_ID: String): KBigAvatar? {
        val res = bigAvatars.select_big_avatars(OBJECTS_ID).execute()
        return if (res.next()) {
            KBigAvatar(
                res.getString(0)!!,
                res.getLong(1)!!,
                res.getLong(2)!!.toInt(),
                res.getLong(3)!!.toInt(),
                res.getBytes(4)!!
            )
        } else null
    }

    actual suspend fun SELECT_ALL_REGDATA(): KRegData? {
        val res = regData.select_all_regdata().execute()
        return if (res.next()) {
            val ret = KRegData()
            ret.setCONNECTION_ID(res.getLong(0)!!)
            ret.setCONNECTION_COOCKI(res.getLong(1)!!)
            ret.setMY_DATABASE_ID(res.getString(2)!!)
            ret.setLANG(res.getString(3)!!)
            ret.setREQUEST_PROFILE(res.getString(4)!!)
            ret.setACCOUNT_PROFILE(res.getString(5)!!)
            return ret
        } else null
    }



    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_COMMANDS(): ArrayDeque<KCommands> {
        val res = commands.select_all_commands().execute()
        val ret = ArrayDeque<KCommands>()
        while (res.next()) {
            val c = KCommands()
            c.setCOMMANDS_ID(res.getString(0)!!.toInt())
            c.setCOMMANDS_ACCESS(res.getString(1)!!)
            c.setCOMMANDS_PROFILE(res.getString(2)!!)
            c.setCOMMANDS_NECESSARILY_FIELDS(res.getString(3)!!)
            ret.add(c)
        }
        return ret
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_SAVEMEDIA(CONN_ID: Long): ArrayDeque<KSaveMedia> {
        val res = saveMedia.select_all_savemedia(CONN_ID.toString()).execute()
        val ret = ArrayDeque<KSaveMedia>()
        while (res.next()) {
            val s = KSaveMedia()
            s.setOBJECTS_ID(res.getString(0)!!)
            s.setOBJECTS_EXTENSION(res.getString(1)!!)
            s.setOBJECT_NAME(res.getString(3)!!)
            s.setOBJECT_INFO(res.getString(4)!!)
            s.setSMALL_AVATAR(res.getBytes(5))
            s.setIS_TEMP(res.getLong(6)!!.toInt())
            s.setIS_DOWNLOAD(res.getLong(7)!!.toInt())
            s.setTIME_ADDED(res.getLong(8)!!)
            s.setOBJECTS_SIZE(res.getLong(9)!!)
            ret.add(s)
        }
        return ret
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_SENDMEDIA(CONN_ID: Long): ArrayDeque<KSendMedia> {
        val res = sendMedia.select_all_sendmedia(CONN_ID.toString()).execute()
        val ret = ArrayDeque<KSendMedia>()
        while (res.next()) {
            val s = KSendMedia()
            s.setOBJECTS_ID(res.getString(0)!!)
            s.setOBJECTS_EXTENSION(res.getString(1)!!)
            s.setOBJECT_NAME(res.getString(2)!!)
            s.setIS_DOWNLOAD(res.getLong(4)!!.toInt())
            ret.add(s)
        }
        return ret
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_CASHDATA(CASH_SUM: Long): ArrayDeque<ANSWER_TYPE> {
        val res = cashData.select_all_cashdata(CASH_SUM).execute()
        val ret = ArrayDeque<ANSWER_TYPE>()
        while (res.next()) {
            val s = ANSWER_TYPE()
            s.IDENTIFICATOR_1 = res.getString(0)
            s.IDENTIFICATOR_2 = res.getString(1)
            s.IDENTIFICATOR_3 = res.getString(2)
            s.IDENTIFICATOR_4 = res.getString(3)
            s.IDENTIFICATOR_5 = res.getString(4)
            s.IDENTIFICATOR_6 = res.getString(5)
            s.IDENTIFICATOR_7 = res.getString(6)
            s.IDENTIFICATOR_8 = res.getString(7)
            s.IDENTIFICATOR_9 = res.getString(8)
            s.IDENTIFICATOR_10 = res.getString(9)
            s.INTEGER_1 = res.getLong(10)?.toInt()?:0
            s.INTEGER_2 = res.getLong(11)?.toInt()?:0
            s.INTEGER_3 = res.getLong(12)?.toInt()?:0
            s.INTEGER_4 = res.getLong(13)?.toInt()?:0
            s.INTEGER_5 = res.getLong(14)?.toInt()?:0
            s.INTEGER_6 = res.getLong(15)?.toInt()?:0
            s.INTEGER_7 = res.getLong(16)?.toInt()?:0
            s.INTEGER_8 = res.getLong(17)?.toInt()?:0
            s.INTEGER_9 = res.getLong(18)?.toInt()?:0
            s.INTEGER_10 = res.getLong(19)?.toInt()?:0
            s.LONG_1 = res.getLong(20)
            s.LONG_2 = res.getLong(21)
            s.LONG_3 = res.getLong(22)
            s.LONG_4 = res.getLong(23)
            s.LONG_5 = res.getLong(24)
            s.LONG_6 = res.getLong(25)
            s.LONG_7 = res.getLong(26)
            s.LONG_8 = res.getLong(27)
            s.LONG_9 = res.getLong(28)
            s.LONG_10 = res.getLong(29)
            s.STRING_1 = res.getString(30)
            s.STRING_2 = res.getString(31)
            s.STRING_3 = res.getString(32)
            s.STRING_4 = res.getString(33)
            s.STRING_5 = res.getString(34)
            s.STRING_6 = res.getString(35)
            s.STRING_7 = res.getString(36)
            s.STRING_8 = res.getString(37)
            s.STRING_9 = res.getString(38)
            s.STRING_10 = res.getString(39)
            s.BLOB_1 = res.getBytes(40)
            s.BLOB_2 = res.getBytes(41)
            s.BLOB_3 = res.getBytes(42)
            s.BLOB_4 = res.getBytes(43)
            s.BLOB_5 = res.getBytes(44)
            s.BLOB_6 = res.getBytes(45)
            s.BLOB_7 = res.getBytes(46)
            s.BLOB_8 = res.getBytes(47)
            s.BLOB_9 = res.getBytes(48)
            s.BLOB_10 = res.getBytes(49)
            ret.add(s)
        }
        return ret
    }

    @InternalAPI
    @ExperimentalStdlibApi
    actual suspend fun SELECT_ALL_CHATS(CONNECTION_ID: String): ArrayDeque<KChat> {
        val res = chats.select_all_chats(CONNECTION_ID).execute()
        val ret = ArrayDeque<KChat>()
        while (res.next()) {
            val s = KChat()
            s.setCHATS_ID(res.getString(0)!!)
            s.setCHATS_OWNER(res.getString(1)!!)
            s.setLAST_MESSEGES_ID(res.getLong(2)!!)
            s.setCHATS_OPPONENT(res.getString(3)!!)
            s.setMESSEGES_COUNT(res.getLong(4)!!)
            s.setCHATS_NAME(res.getString(5)!!)
            s.setCHATS_SMALL_AVATAR(res.getBytes(6))
            s.setCHATS_PROFILE(res.getString(7)!!)
            s.setCHATS_ACCESS(res.getString(8)!!)
            s.setCHATS_TYPE(res.getString(9)!!)
            s.setCHATS_STATUS(res.getString(10)!!)
            s.setLAST_UPDATING_DATE(res.getLong(11)!!)
            s.setCHATS_SUBSCRIBE(res.getString(12)!!)
            ret.add(s)
        }
        return ret
    }

    @ExperimentalStdlibApi
    actual suspend fun SELECT_MESSEGES(CHATS_ID: String): ArrayDeque<KMessege> {
        val res = messeges.select_messeges(CHATS_ID).execute()
        val ret = ArrayDeque<KMessege>()
        while (res.next()) {
            val s = KMessege()
            s.setCHATS_ID(res.getString(0)!!)
            s.setMESSEGES_ID(res.getLong(1)!!)
            s.setMESSEGES_CHATS_COUNT(res.getLong(2)!!)
            s.setMESSEGES_OWNER(res.getString(3)!!)
            s.setMESSEGES_ANSWER(res.getLong(4)!!)
            s.setMESSEGES_ADRESSER(res.getString(5)!!)
            s.setOBJECTS_LINK(res.getString(6)!!)
            s.setMESSEGE_TEXT(res.getString(7)!!)
            s.setMESSEGES_AVATAR(res.getBytes(8))
            s.setMESSEGES_LIKES(res.getLong(9)!!.toInt())
            s.setMESSEGES_DISLIKES(res.getLong(10)!!.toInt())
            s.setNOT_DELIVERIED(res.getLong(11)!!.toInt())
            s.setNOT_READED(res.getLong(12)!!.toInt())
            s.setMESSEGES_ACCESS(res.getString(13)!!)
            s.setMESSEGES_TYPE(res.getString(14)!!)
            s.setDATE_ADDING(res.getLong(15)!!)
            s.setLAST_UPDATING_DATE(res.getLong(16)!!)
            s.setOBJECTS_LINK_SUBSCRIBE(res.getString(17)!!)
            s.setOBJECTS_LINK_SMALL_AVATAR(res.getBytes(18))
            ret.add(s)
        }
        return ret
    }



}