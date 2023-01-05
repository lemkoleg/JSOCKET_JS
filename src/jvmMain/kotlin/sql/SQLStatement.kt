@file:Suppress("DuplicatedCode", "RedundantSuspendModifier")

package sql

import Tables.*
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.ktor.util.*
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import p_jsocket.JSOCKET_Instance
import kotlin.time.ExperimentalTime

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
actual var sqlDriver: SqlDriver? = JdbcSqliteDriver("""jdbc:sqlite:${JSOCKET_Instance.RootPath}${Constants.dbLocalName}.db""")


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
actual class SQLStatement actual constructor() {


    val bigAvatars = db!!.bIGAVATARSQueries
    val cashData = db!!.cASHDATAQueries
    val cashLastUpdate = db!!.cASHLASTUPDATEQueries
    val commands = db!!.cOMMANDSQueries
    val exceptions = db!!.eXCEPTIONSQueries
    val metaData = db!!.mETADATAQueries
    val regData = db!!.rEGDATAQueries
    val saveMedia = db!!.sAVEMEDIAQueries


    /////////////////////////////////////////////////////////////////////////
    actual suspend fun connect() {
    }

    actual suspend fun clear_parameters() {
    }

    ///////////////////////////////////big avatars///////////////////////////

    actual suspend fun TABLE_BIG_AVATARS() {
        bigAvatars.table_BigAvatars()
    }

    actual suspend fun INDEX_BIG_AVATARS_LAST_USE() { // for clear old last use in trigger
        bigAvatars.index_BigAvatars_lastUse()
    }

    actual suspend fun TRIGGER_BIG_AVATARS_CONTROL_COUNT() {
        bigAvatars.trigger_BigAvatars_control_count()
    }

    actual suspend fun INSERT_BIG_AVATAR(kBigAvatar: KBigAvatar) {
        kBigAvatar.getAVATAR()?.let {
            bigAvatars.insert_BigAvatar(
                kBigAvatar.getAVATAR_ID(),
                kBigAvatar.getLAST_USE(),
                it
            )
        }
    }

    actual suspend fun SELECT_BIG_AVATAR(OBJECTS_ID: String): KBigAvatar? {
        val res = bigAvatars.select_BigAvatar(OBJECTS_ID).execute()
        var kBigAvatar: KBigAvatar? = null
        if (res.next()) {
            kBigAvatar = KBigAvatar(
                L_AVATAR_ID = res.getString(0)!!,
                L_LAST_USE = res.getLong(1)!!,
                L_AVATAR = res.getBytes(2)!!
            )
        }
        return kBigAvatar
    }

    actual suspend fun SELECT_BIGAVATARS_ALL_ID(): ArrayList<String> {  //for save in memory
        val res = bigAvatars.select_BigAvatars_all_id().execute()
        val arr: ArrayList<String> = ArrayList()
        while (res.next()) {
            val s = res.getString(1)
            arr.add(s!!)
        }
        return arr
    }

    actual suspend fun SELECT_BIGAVATARS_ALL(): ArrayList<KBigAvatar> {  //for save in memory
        val res = bigAvatars.select_BigAvatars_all().execute()
        val arr: ArrayList<KBigAvatar> = ArrayList()
        while (res.next()) {
            val kBigAvatar = KBigAvatar(
                L_AVATAR_ID = res.getString(0)!!,
                L_LAST_USE = res.getLong(1)!!,
                L_AVATAR = res.getBytes(2)!!
            )
            arr.add(kBigAvatar)
        }
        return arr
    }

    actual suspend fun DELETE_BIG_AVATAR(OBJECTS_ID: String) {
        bigAvatars.delete_BigAvatar(OBJECTS_ID)
    }

    actual suspend fun CLEAR_BIG_AVATARS() {
        bigAvatars.clear_BigAvatars()
    }

    actual suspend fun UPDATE_BIG_AVATAR_LAST_USE(OBJECTS_ID: String, LAST_USE: Long) {
        bigAvatars.update_BigAvatars_last_use(LAST_USE, OBJECTS_ID)
    }

///////////// commands ///////////////////////////

    actual suspend fun TABLE_COMMANDS() {
        commands.table_Commands()
    }

    actual suspend fun INSERT_COMMAND(kCommand: KCommands) {
        commands.insert_commands(
            kCommand.getCOMMANDS_ID().toLong(),
            kCommand.getCOMMANDS_ACCESS(),
            kCommand.getCOMMANDS_PROFILE(),
            kCommand.getCOMMANDS_NECESSARILY_FIELDS(),
            kCommand.getLAST_UPDATE(),
            kCommand.getCOUNT_OF_EXECUTE().toLong()
        )
    }

    actual suspend fun SELECT_COMMANDS_ALL(): ArrayList<KCommands> {
        val res = commands.select_Commands_all().execute()
        val arr: ArrayList<KCommands> = ArrayList()
        while (res.next()) {
            val kCommands = KCommands(
                L_COMMANDS_ID = res.getLong(0)!!.toInt(),
                L_COMMANDS_ACCESS = res.getString(1)!!,
                L_COMMANDS_PROFILE = res.getString(2)!!,
                L_OMMANDS_NECESSARILY_FIELDS = res.getString(3)!!,
                L_LAST_UPDATE = res.getLong(4)!!,
                L_COUNT_OF_EXECUTE = res.getLong(5)!!.toInt()
            )
            arr.add(kCommands)
        }
        return arr
    }

    actual suspend fun CLEAR_COMMANDS() {
        commands.clear_Commands()
    }

/////////////exceptions///////////////////////////

    actual suspend fun TABLE_EXCEPTION() {
        exceptions.table_Exception()
    }

    actual suspend fun INSERT_EXCEPTION(kException: KExceptions.KException) {
        exceptions.insert_Exception(
            kException.NAME_OF_ECXEPTION,
            kException.LANG_OF_ECXEPTION,
            kException.TEXT_OF_ECXEPTION,
            kException.EXCEPTIONS_STATUS,
            kException.LAST_UPDATE
        )
    }

    actual suspend fun SELECT_EXCEPTION_ALL(): ArrayList<KExceptions.KException> {
        val res = exceptions.select_Exception_all().execute()
        val arr: ArrayList<KExceptions.KException> = ArrayList()
        while (res.next()) {
            val kException = KExceptions.KException(
                L_NAME_OF_ECXEPTION = res.getString(0)!!,
                L_LANG_OF_ECXEPTION = res.getString(1)!!,
                L_TEXT_OF_ECXEPTION = res.getString(2)!!,
                L_EXCEPTIONS_STATUS = res.getString(3)!!,
                L_LAST_UPDATE = res.getLong(4)!!
            )
            arr.add(kException)
        }
        return arr
    }

    actual suspend fun CLEAR_EXCEPTION() {
        exceptions.clear_Exception()
    }

/////////////meta data///////////////////////////

    actual suspend fun TABLE_METADATA() {
        metaData.table_MetaData()
    }

    actual suspend fun INSERT_METADATA(kMetaData: KMetaData) {
        metaData.insert_MetaData(
            kMetaData.getVALUE_NAME(),
            kMetaData.getVALUE_VALUE(),
            kMetaData.getLATS_UPDATE()
        )
    }

    actual suspend fun CLEAR_METADATA() {
        metaData.clear_MetaData()
    }

    actual suspend fun SELECT_ALL_METADATA(): ArrayList<KMetaData> {
        val res = metaData.select_MetaData_all().execute()
        val arr: ArrayList<KMetaData> = ArrayList()
        while (res.next()) {
            val kMetaData = KMetaData(
                L_VALUE_NAME = res.getString(0)!!,
                L_VALUE_VALUE = res.getLong(1)!!,
                L_LAST_UPDATE = res.getLong(2)!!
            )
            arr.add(kMetaData)
        }
        return arr
    }

/////////////reg data///////////////////////////

    actual suspend fun TABLE_REGDATA() {
        regData.table_regdata()
    }

    actual suspend fun TRIGGER_REGDATA_INSERT() {
        regData.trigger_RegData_insert()
    }

    actual suspend fun INSERT_REGDATA() {

        regData.insert_RegData(
            Constants.myConnectionsID,
            Constants.myConnectionsCoocki,
            Constants.Account_Id,
            Constants.Account_Name,
            Constants.Account_Access,
            Constants.myAccountProfile,
            Constants.myRequestProfile,
            Constants.myLang,
            Constants.Avatar_Id,
            Constants.ORIGINAL_AVATAR_SIZE,
            Constants.AVATAR_SERVER,
            Constants.AVATAR_LINK,
            Constants.BALANCE_OF_CHATS.toLong(),
            Constants.LAST_UPDATE,
            Constants.AVATAR_1,
            Constants.AVATAR_2,
            Constants.AVATAR_3
        )
    }

    actual suspend fun CLEAR_REGDATA() {
        regData.clear_RegData()
    }

    actual suspend fun SELECT_REGDATA_ALL() {
        val res = regData.select_RegData_all().execute()
        if (res.next()) {
            Constants.myConnectionsID = res.getLong(0) ?: 0L
            Constants.myConnectionsCoocki = res.getLong(1) ?: 0L
            Constants.Account_Id = res.getString(2) ?: ""
            Constants.Account_Name = res.getString(3) ?: ""
            Constants.Account_Access = res.getString(4) ?: ""
            Constants.myAccountProfile = res.getString(5) ?: ""
            Constants.myRequestProfile = res.getString(6) ?: ""
            Constants.myLang = res.getString(7) ?: ""
            Constants.Avatar_Id = res.getString(8) ?: ""
            Constants.ORIGINAL_AVATAR_SIZE = res.getString(9) ?: ""
            Constants.AVATAR_SERVER = res.getString(10) ?: ""
            Constants.AVATAR_LINK = res.getString(11) ?: ""
            Constants.BALANCE_OF_CHATS = res.getLong(12)?.toInt() ?: 0
            Constants.LAST_UPDATE = res.getLong(13) ?: 0L
            Constants.AVATAR_1 = res.getBytes(14)
            Constants.AVATAR_2 = res.getBytes(15)
            Constants.AVATAR_3 = res.getBytes(16)
        }
    }

/////////////save media///////////////////////////

    actual suspend fun TABLE_SAVEMEDIA() {
        saveMedia.table_SaveMedia()
    }

    actual suspend fun INDEX_SAVEMEDIA_LASTUSED() {
        saveMedia.index_SaveMedia_LastUsed()
    }

    actual suspend fun INDEX_SAVEMEDIA_ISTEMP() {
        saveMedia.index_SaveMedia_IsTemp()
    }

    actual suspend fun INDEX_SAVEMEDIA_AVATAR_ID() {
        saveMedia.index_SaveMedia_AvatarId()
    }

    actual suspend fun TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT() {
        saveMedia.trigger_SaveMedia_control_temp_count()
    }

    actual suspend fun TRIGGER_SAVEMEDIA_CONTROL_COUNT() {
        saveMedia.trigger_SaveMedia_control_count()
    }

    actual suspend fun INSERT_SAVEMEDIA(kSaveMedia: KSaveMedia) {
        saveMedia.insert_SaveMedia(
            OBJECT_LINK = kSaveMedia.L_OBJECT_LINK,
            OBJECT_SIZE = kSaveMedia.L_OBJECT_SIZE,
            OBJECT_LENGTH_SECONDS = kSaveMedia.L_OBJECT_LENGTH_SECONDS.toLong(),
            OBJECT_EXTENSION = kSaveMedia.L_OBJECT_EXTENSION,
            AVATAR_ID = kSaveMedia.AVATAR_ID,
            IS_TEMP = kSaveMedia.L_IS_TEMP.toLong(),
            LAST_USED = kSaveMedia.L_LAST_USED
        )
    }

    actual suspend fun SELECT_SAVEMEDIA_ALL(conn_id: Long): ArrayList<KSaveMedia> {
        val res = saveMedia.select_SaveMedia_all().execute()
        val arr: ArrayList<KSaveMedia> = ArrayList()
        while (res.next()) {
            val kSaveMedia = KSaveMedia(
                L_OBJECT_LINK = res.getString(0)!!,
                L_OBJECT_SIZE = res.getLong(1)!!,
                L_OBJECT_LENGTH_SECONDS = res.getLong(2)?.toInt() ?: 0,
                L_OBJECT_EXTENSION = res.getString(3)!!,
                L_AVATAR_ID = res.getString(4),
                L_IS_TEMP = res.getLong(5)?.toInt() ?: 1,
                L_LAST_USED = res.getLong(6)!!
            )
            arr.add(kSaveMedia)
        }
        return arr
    }

    actual suspend fun DELETE_SAVEMEDIA(v: String) {
        saveMedia.delete_SaveMedia(v)
    }

/////////////last update///////////////////////////

    actual suspend fun TABLE_CASHLASTUPDATE() {
        cashLastUpdate.table_CashLastUpdate()
    }

    actual suspend fun INDEX_CASHLASTUPDATE_LAST_USE() {
        cashLastUpdate.index_CashLastUpdate_last_use()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT() {
        cashLastUpdate.trigger_CashLastUpdate_Control_Empty_Blocks_Insert()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_LINKS_INSERT() {
        cashLastUpdate.trigger_CashLastUpdate_Control_Count_Links_Insert()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_OBJECTS_INFO_INSERT() {
        cashLastUpdate.trigger_CashLastUpdate_Control_Count_ObjectsInfo_Insert()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_CHATS_INSERT() {
        cashLastUpdate.trigger_CashLastUpdate_Control_Count_Chats_Insert()
    }

    actual suspend fun TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_MESS_INSERT() {
        cashLastUpdate.trigger_CashLastUpdate_Control_Count_Mess_Insert()
    }

    actual suspend fun INSERT_CASHLASTUPDATE(kCashLastUpdate: KCashLastUpdate) {
        cashLastUpdate.insert_CashLastUpdate(
            CASH_SUM = kCashLastUpdate.CASH_SUM,
            OBJECT_ID = kCashLastUpdate.OBJECT_ID,
            RECORD_TYPE = kCashLastUpdate.RECORD_TYPE,
            COURSE = kCashLastUpdate.COURSE,
            SORT = kCashLastUpdate.SORT,
            LINK_OWNER = kCashLastUpdate.LINK_OWNER,
            MESS_COUNT_FROM = kCashLastUpdate.MESS_COUNT_FROM,
            OTHER_CONDITIONS_1 = kCashLastUpdate.OTHER_CONDITIONS_1,
            OTHER_CONDITIONS_2 = kCashLastUpdate.OTHER_CONDITIONS_2,
            OTHER_CONDITIONS_3 = kCashLastUpdate.OTHER_CONDITIONS_3,
            LAST_USE = kCashLastUpdate.LAST_USE
        )
    }

    actual suspend fun SELECT_CASHLASTUPDATE_ALL(): ArrayList<KCashLastUpdate> {
        val res = cashLastUpdate.select_CashLastUpdateAll().execute()
        val arr: ArrayList<KCashLastUpdate> = ArrayList()
        while (res.next()) {
            val kCashLastUpdate = KCashLastUpdate(
                CASH_SUM = res.getString(1)!!,
                OBJECT_ID = res.getString(2)!!,
                RECORD_TYPE = res.getString(3)!!,
                COURSE = res.getString(4)!!,
                SORT = res.getString(5)!!,
                LINK_OWNER = res.getString(6)!!,
                MESS_COUNT_FROM = res.getString(7)!!,
                OTHER_CONDITIONS_1 = res.getString(8)!!,
                OTHER_CONDITIONS_2 = res.getString(9)!!,
                OTHER_CONDITIONS_3 = res.getString(10)!!
            )
            arr.add(kCashLastUpdate)
        }
        return arr
    }

    actual suspend fun CLEAR_LASTUPDATE() {
        cashLastUpdate.clear_CashLastUpdate()
    }

    actual suspend fun DELETE_LASTUPDATE(cash_sum: String) {
        cashLastUpdate.delete_CashLastUpdate(cash_sum)
    }

/////////////cash data///////////////////////////

    actual suspend fun TABLE_CASHDATA() {
        cashData.table_CashData()
    }

    actual suspend fun INDEX_CASHDATA_NUMBER_POSITION_ASC() {
        cashData.index_CashData_NumberPositionAsc()
    }

    actual suspend fun INDEX_CASHDATA_CASH_SUM() {
        cashData.index_CashData_CashSum()
    }

    actual suspend fun TRIGGER_CASHDATA_AFTER_INSERT() {
        cashData.trigger_CashData_After_Insert()
    }

    actual suspend fun TRIGGER_CASHDATA_AFTER_INSERT_OBJECTS_INFO() {
        cashData.trigger_CashData_After_Insert_Objects_Info()
    }

    actual suspend fun TRIGGER_CASHDATA_AFTER_UPDATE() {
        cashData.trigger_CashData_After_Update()
    }

    actual suspend fun INSERT_CASHDATA(cash_sum: String, lANSWER_TYPE: ANSWER_TYPE) {
        cashData.insert_CashData(
            CASH_SUM = cash_sum,
            RECORD_TABLE_ID = lANSWER_TYPE.RECORD_TABLE_ID,
            IDENTIFICATOR_1 = lANSWER_TYPE.IDENTIFICATOR_1,
            IDENTIFICATOR_2 = lANSWER_TYPE.IDENTIFICATOR_2,
            IDENTIFICATOR_3 = lANSWER_TYPE.IDENTIFICATOR_3,
            IDENTIFICATOR_4 = lANSWER_TYPE.IDENTIFICATOR_4,
            IDENTIFICATOR_5 = lANSWER_TYPE.IDENTIFICATOR_5,
            IDENTIFICATOR_6 = lANSWER_TYPE.IDENTIFICATOR_6,
            IDENTIFICATOR_7 = lANSWER_TYPE.IDENTIFICATOR_7,
            IDENTIFICATOR_8 = lANSWER_TYPE.IDENTIFICATOR_8,
            IDENTIFICATOR_9 = lANSWER_TYPE.IDENTIFICATOR_9,
            IDENTIFICATOR_10 = lANSWER_TYPE.IDENTIFICATOR_10,
            IDENTIFICATOR_11 = lANSWER_TYPE.IDENTIFICATOR_11,
            IDENTIFICATOR_12 = lANSWER_TYPE.IDENTIFICATOR_12,
            IDENTIFICATOR_13 = lANSWER_TYPE.IDENTIFICATOR_13,
            IDENTIFICATOR_14 = lANSWER_TYPE.IDENTIFICATOR_14,
            IDENTIFICATOR_15 = lANSWER_TYPE.IDENTIFICATOR_15,
            IDENTIFICATOR_16 = lANSWER_TYPE.IDENTIFICATOR_16,
            IDENTIFICATOR_17 = lANSWER_TYPE.IDENTIFICATOR_17,
            IDENTIFICATOR_18 = lANSWER_TYPE.IDENTIFICATOR_18,
            IDENTIFICATOR_19 = lANSWER_TYPE.IDENTIFICATOR_19,
            IDENTIFICATOR_20 = lANSWER_TYPE.IDENTIFICATOR_20,
            INTEGER_1 = lANSWER_TYPE.INTEGER_1?.toLong() ?: 0L,
            INTEGER_2 = lANSWER_TYPE.INTEGER_2?.toLong() ?: 0L,
            INTEGER_3 = lANSWER_TYPE.INTEGER_3?.toLong() ?: 0L,
            INTEGER_4 = lANSWER_TYPE.INTEGER_4?.toLong() ?: 0L,
            INTEGER_5 = lANSWER_TYPE.INTEGER_5?.toLong() ?: 0L,
            INTEGER_6 = lANSWER_TYPE.INTEGER_6?.toLong() ?: 0L,
            INTEGER_7 = lANSWER_TYPE.INTEGER_7?.toLong() ?: 0L,
            INTEGER_8 = lANSWER_TYPE.INTEGER_8?.toLong() ?: 0L,
            INTEGER_9 = lANSWER_TYPE.INTEGER_9?.toLong() ?: 0L,
            INTEGER_10 = lANSWER_TYPE.INTEGER_10?.toLong() ?: 0L,
            INTEGER_11 = lANSWER_TYPE.INTEGER_11?.toLong() ?: 0L,
            INTEGER_12 = lANSWER_TYPE.INTEGER_12?.toLong() ?: 0L,
            INTEGER_13 = lANSWER_TYPE.INTEGER_13?.toLong() ?: 0L,
            INTEGER_14 = lANSWER_TYPE.INTEGER_14?.toLong() ?: 0L,
            INTEGER_15 = lANSWER_TYPE.INTEGER_15?.toLong() ?: 0L,
            INTEGER_16 = lANSWER_TYPE.INTEGER_16?.toLong() ?: 0L,
            INTEGER_17 = lANSWER_TYPE.INTEGER_17?.toLong() ?: 0L,
            INTEGER_18 = lANSWER_TYPE.INTEGER_18?.toLong() ?: 0L,
            INTEGER_19 = lANSWER_TYPE.INTEGER_19?.toLong() ?: 0L,
            INTEGER_20 = lANSWER_TYPE.INTEGER_20.toLong(),
            LONG_1 = lANSWER_TYPE.LONG_1 ?: 0L,
            LONG_2 = lANSWER_TYPE.LONG_2 ?: 0L,
            LONG_3 = lANSWER_TYPE.LONG_3 ?: 0L,
            LONG_4 = lANSWER_TYPE.LONG_4 ?: 0L,
            LONG_5 = lANSWER_TYPE.LONG_5 ?: 0L,
            LONG_6 = lANSWER_TYPE.LONG_6 ?: 0L,
            LONG_7 = lANSWER_TYPE.LONG_7 ?: 0L,
            LONG_8 = lANSWER_TYPE.LONG_8 ?: 0L,
            LONG_9 = lANSWER_TYPE.LONG_9 ?: 0L,
            LONG_10 = lANSWER_TYPE.LONG_10 ?: 0L,
            LONG_11 = lANSWER_TYPE.LONG_11 ?: 0L,
            LONG_12 = lANSWER_TYPE.LONG_12 ?: 0L,
            LONG_13 = lANSWER_TYPE.LONG_13 ?: 0L,
            LONG_14 = lANSWER_TYPE.LONG_14 ?: 0L,
            LONG_15 = lANSWER_TYPE.LONG_15 ?: 0L,
            LONG_16 = lANSWER_TYPE.LONG_16 ?: 0L,
            LONG_17 = lANSWER_TYPE.LONG_17 ?: 0L,
            LONG_18 = lANSWER_TYPE.LONG_18 ?: 0L,
            LONG_19 = lANSWER_TYPE.LONG_19 ?: 0L,
            LONG_20 = lANSWER_TYPE.LONG_20,
            STRING_1 = lANSWER_TYPE.STRING_1 ?: "",
            STRING_2 = lANSWER_TYPE.STRING_2 ?: "",
            STRING_3 = lANSWER_TYPE.STRING_3 ?: "",
            STRING_4 = lANSWER_TYPE.STRING_4 ?: "",
            STRING_5 = lANSWER_TYPE.STRING_5 ?: "",
            STRING_6 = lANSWER_TYPE.STRING_6 ?: "",
            STRING_7 = lANSWER_TYPE.STRING_7 ?: "",
            STRING_8 = lANSWER_TYPE.STRING_8 ?: "",
            STRING_9 = lANSWER_TYPE.STRING_9 ?: "",
            STRING_10 = lANSWER_TYPE.STRING_10 ?: "",
            STRING_11 = lANSWER_TYPE.STRING_11 ?: "",
            STRING_12 = lANSWER_TYPE.STRING_12 ?: "",
            STRING_13 = lANSWER_TYPE.STRING_13 ?: "",
            STRING_14 = lANSWER_TYPE.STRING_14 ?: "",
            STRING_15 = lANSWER_TYPE.STRING_15 ?: "",
            STRING_16 = lANSWER_TYPE.STRING_16 ?: "",
            STRING_17 = lANSWER_TYPE.STRING_17 ?: "",
            STRING_18 = lANSWER_TYPE.STRING_18 ?: "",
            STRING_19 = lANSWER_TYPE.STRING_19 ?: "",
            STRING_20 = lANSWER_TYPE.STRING_20,
            BLOB_1 = lANSWER_TYPE.BLOB_1,
            BLOB_2 = lANSWER_TYPE.BLOB_2,
            BLOB_3 = lANSWER_TYPE.BLOB_3
        )
    }

    actual suspend fun SELECT_CASHDATA_ALL(): ArrayDeque<ANSWER_TYPE> {
        val res = cashData.select_CashData_All().execute()
        var arr: ArrayDeque<ANSWER_TYPE> = ANSWER_TYPE.GetAnswerTypes() ?: ArrayDeque()
        val arr_res: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
        while (res.next()) {
            if (arr.isEmpty()) {
                arr = ANSWER_TYPE.GetAnswerTypes() ?: ArrayDeque()
            }
            val answer_type = arr.removeFirstOrNull() ?: ANSWER_TYPE()
            answer_type.IDENTIFICATOR_1 = res.getString(3)
            answer_type.IDENTIFICATOR_2 = res.getString(4)
            answer_type.IDENTIFICATOR_3 = res.getString(5)
            answer_type.IDENTIFICATOR_4 = res.getString(6)
            answer_type.IDENTIFICATOR_5 = res.getString(7)
            answer_type.IDENTIFICATOR_6 = res.getString(8)
            answer_type.IDENTIFICATOR_7 = res.getString(9)
            answer_type.IDENTIFICATOR_8 = res.getString(10)
            answer_type.IDENTIFICATOR_9 = res.getString(11)
            answer_type.IDENTIFICATOR_10 = res.getString(12)
            answer_type.IDENTIFICATOR_11 = res.getString(13)
            answer_type.IDENTIFICATOR_12 = res.getString(14)
            answer_type.IDENTIFICATOR_13 = res.getString(15)
            answer_type.IDENTIFICATOR_14 = res.getString(16)
            answer_type.IDENTIFICATOR_15 = res.getString(17)
            answer_type.IDENTIFICATOR_16 = res.getString(18)
            answer_type.IDENTIFICATOR_17 = res.getString(19)
            answer_type.IDENTIFICATOR_18 = res.getString(20)
            answer_type.IDENTIFICATOR_19 = res.getString(21)
            answer_type.IDENTIFICATOR_20 = res.getString(22)
            answer_type.INTEGER_1 = res.getLong(23)?.toInt()
            answer_type.INTEGER_2 = res.getLong(24)?.toInt()
            answer_type.INTEGER_3 = res.getLong(25)?.toInt()
            answer_type.INTEGER_4 = res.getLong(26)?.toInt()
            answer_type.INTEGER_5 = res.getLong(27)?.toInt()
            answer_type.INTEGER_6 = res.getLong(28)?.toInt()
            answer_type.INTEGER_7 = res.getLong(29)?.toInt()
            answer_type.INTEGER_8 = res.getLong(30)?.toInt()
            answer_type.INTEGER_9 = res.getLong(31)?.toInt()
            answer_type.INTEGER_10 = res.getLong(32)?.toInt()
            answer_type.INTEGER_11 = res.getLong(33)?.toInt()
            answer_type.INTEGER_12 = res.getLong(34)?.toInt()
            answer_type.INTEGER_13 = res.getLong(35)?.toInt()
            answer_type.INTEGER_14 = res.getLong(36)?.toInt()
            answer_type.INTEGER_15 = res.getLong(37)?.toInt()
            answer_type.INTEGER_16 = res.getLong(38)?.toInt()
            answer_type.INTEGER_17 = res.getLong(39)?.toInt()
            answer_type.INTEGER_18 = res.getLong(40)?.toInt()
            answer_type.INTEGER_19 = res.getLong(41)?.toInt()
            answer_type.INTEGER_20 = res.getLong(42)!!.toInt()
            answer_type.LONG_1 = res.getLong(43)
            answer_type.LONG_2 = res.getLong(44)
            answer_type.LONG_3 = res.getLong(45)
            answer_type.LONG_4 = res.getLong(46)
            answer_type.LONG_5 = res.getLong(47)
            answer_type.LONG_6 = res.getLong(48)
            answer_type.LONG_7 = res.getLong(49)
            answer_type.LONG_8 = res.getLong(50)
            answer_type.LONG_9 = res.getLong(51)
            answer_type.LONG_10 = res.getLong(52)
            answer_type.LONG_11 = res.getLong(53)
            answer_type.LONG_12 = res.getLong(54)
            answer_type.LONG_13 = res.getLong(55)
            answer_type.LONG_14 = res.getLong(56)
            answer_type.LONG_15 = res.getLong(57)
            answer_type.LONG_16 = res.getLong(58)
            answer_type.LONG_17 = res.getLong(59)
            answer_type.LONG_18 = res.getLong(60)
            answer_type.LONG_19 = res.getLong(61)
            answer_type.LONG_20 = res.getLong(62)!!
            answer_type.STRING_1 = res.getString(63)
            answer_type.STRING_2 = res.getString(64)
            answer_type.STRING_3 = res.getString(65)
            answer_type.STRING_4 = res.getString(66)
            answer_type.STRING_5 = res.getString(67)
            answer_type.STRING_6 = res.getString(68)
            answer_type.STRING_7 = res.getString(69)
            answer_type.STRING_8 = res.getString(70)
            answer_type.STRING_9 = res.getString(71)
            answer_type.STRING_10 = res.getString(72)
            answer_type.STRING_11 = res.getString(73)
            answer_type.STRING_12 = res.getString(74)
            answer_type.STRING_13 = res.getString(75)
            answer_type.STRING_14 = res.getString(76)
            answer_type.STRING_15 = res.getString(77)
            answer_type.STRING_16 = res.getString(78)
            answer_type.STRING_17 = res.getString(79)
            answer_type.STRING_18 = res.getString(80)
            answer_type.STRING_19 = res.getString(81)
            answer_type.STRING_20 = res.getString(82)!!
            answer_type.BLOB_1 = res.getBytes(83)
            answer_type.BLOB_2 = res.getBytes(84)
            answer_type.BLOB_3 = res.getBytes(85)
            arr_res.addLast(answer_type)
        }
        return arr_res
    }

    actual suspend fun SELECT_CASHDATA_ALL_ON_CASH_SUM(CASH_SUM: String): ArrayDeque<ANSWER_TYPE> {
        val res = cashData.select_CashData_All_ON_CASH_SUM(CASH_SUM).execute()
        var arr: ArrayDeque<ANSWER_TYPE> = ANSWER_TYPE.GetAnswerTypes() ?: ArrayDeque()
        val arr_res: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
        while (res.next()) {
            if (arr.isEmpty()) {
                arr = ANSWER_TYPE.GetAnswerTypes() ?: ArrayDeque()
            }
            val answer_type = arr.removeFirstOrNull() ?: ANSWER_TYPE()
            answer_type.IDENTIFICATOR_1 = res.getString(3)
            answer_type.IDENTIFICATOR_2 = res.getString(4)
            answer_type.IDENTIFICATOR_3 = res.getString(5)
            answer_type.IDENTIFICATOR_4 = res.getString(6)
            answer_type.IDENTIFICATOR_5 = res.getString(7)
            answer_type.IDENTIFICATOR_6 = res.getString(8)
            answer_type.IDENTIFICATOR_7 = res.getString(9)
            answer_type.IDENTIFICATOR_8 = res.getString(10)
            answer_type.IDENTIFICATOR_9 = res.getString(11)
            answer_type.IDENTIFICATOR_10 = res.getString(12)
            answer_type.IDENTIFICATOR_11 = res.getString(13)
            answer_type.IDENTIFICATOR_12 = res.getString(14)
            answer_type.IDENTIFICATOR_13 = res.getString(15)
            answer_type.IDENTIFICATOR_14 = res.getString(16)
            answer_type.IDENTIFICATOR_15 = res.getString(17)
            answer_type.IDENTIFICATOR_16 = res.getString(18)
            answer_type.IDENTIFICATOR_17 = res.getString(19)
            answer_type.IDENTIFICATOR_18 = res.getString(20)
            answer_type.IDENTIFICATOR_19 = res.getString(21)
            answer_type.IDENTIFICATOR_20 = res.getString(22)
            answer_type.INTEGER_1 = res.getLong(23)?.toInt()
            answer_type.INTEGER_2 = res.getLong(24)?.toInt()
            answer_type.INTEGER_3 = res.getLong(25)?.toInt()
            answer_type.INTEGER_4 = res.getLong(26)?.toInt()
            answer_type.INTEGER_5 = res.getLong(27)?.toInt()
            answer_type.INTEGER_6 = res.getLong(28)?.toInt()
            answer_type.INTEGER_7 = res.getLong(29)?.toInt()
            answer_type.INTEGER_8 = res.getLong(30)?.toInt()
            answer_type.INTEGER_9 = res.getLong(31)?.toInt()
            answer_type.INTEGER_10 = res.getLong(32)?.toInt()
            answer_type.INTEGER_11 = res.getLong(33)?.toInt()
            answer_type.INTEGER_12 = res.getLong(34)?.toInt()
            answer_type.INTEGER_13 = res.getLong(35)?.toInt()
            answer_type.INTEGER_14 = res.getLong(36)?.toInt()
            answer_type.INTEGER_15 = res.getLong(37)?.toInt()
            answer_type.INTEGER_16 = res.getLong(38)?.toInt()
            answer_type.INTEGER_17 = res.getLong(39)?.toInt()
            answer_type.INTEGER_18 = res.getLong(40)?.toInt()
            answer_type.INTEGER_19 = res.getLong(41)?.toInt()
            answer_type.INTEGER_20 = res.getLong(42)!!.toInt()
            answer_type.LONG_1 = res.getLong(43)
            answer_type.LONG_2 = res.getLong(44)
            answer_type.LONG_3 = res.getLong(45)
            answer_type.LONG_4 = res.getLong(46)
            answer_type.LONG_5 = res.getLong(47)
            answer_type.LONG_6 = res.getLong(48)
            answer_type.LONG_7 = res.getLong(49)
            answer_type.LONG_8 = res.getLong(50)
            answer_type.LONG_9 = res.getLong(51)
            answer_type.LONG_10 = res.getLong(52)
            answer_type.LONG_11 = res.getLong(53)
            answer_type.LONG_12 = res.getLong(54)
            answer_type.LONG_13 = res.getLong(55)
            answer_type.LONG_14 = res.getLong(56)
            answer_type.LONG_15 = res.getLong(57)
            answer_type.LONG_16 = res.getLong(58)
            answer_type.LONG_17 = res.getLong(59)
            answer_type.LONG_18 = res.getLong(60)
            answer_type.LONG_19 = res.getLong(61)
            answer_type.LONG_20 = res.getLong(62)!!
            answer_type.STRING_1 = res.getString(63)
            answer_type.STRING_2 = res.getString(64)
            answer_type.STRING_3 = res.getString(65)
            answer_type.STRING_4 = res.getString(66)
            answer_type.STRING_5 = res.getString(67)
            answer_type.STRING_6 = res.getString(68)
            answer_type.STRING_7 = res.getString(69)
            answer_type.STRING_8 = res.getString(70)
            answer_type.STRING_9 = res.getString(71)
            answer_type.STRING_10 = res.getString(72)
            answer_type.STRING_11 = res.getString(73)
            answer_type.STRING_12 = res.getString(74)
            answer_type.STRING_13 = res.getString(75)
            answer_type.STRING_14 = res.getString(76)
            answer_type.STRING_15 = res.getString(77)
            answer_type.STRING_16 = res.getString(78)
            answer_type.STRING_17 = res.getString(79)
            answer_type.STRING_18 = res.getString(80)
            answer_type.STRING_19 = res.getString(81)
            answer_type.STRING_20 = res.getString(82)!!
            answer_type.BLOB_1 = res.getBytes(83)
            answer_type.BLOB_2 = res.getBytes(84)
            answer_type.BLOB_3 = res.getBytes(85)
            arr_res.addLast(answer_type)
        }
        return arr_res
    }

    actual suspend fun SELECT_CASHDATA_CHUNK_ON_CASH_SUM(
        CASH_SUM: String,
        record_id_from: String
    ): ArrayDeque<ANSWER_TYPE> {
        val res = cashData.select_CashData_CHUNK_ON_CASH_SUM(CASH_SUM, CASH_SUM, record_id_from).execute()
        var arr: ArrayDeque<ANSWER_TYPE> = ANSWER_TYPE.GetAnswerTypes() ?: ArrayDeque()
        val arr_res: ArrayDeque<ANSWER_TYPE> = ArrayDeque()
        while (res.next()) {
            if (arr.isEmpty()) {
                arr = ANSWER_TYPE.GetAnswerTypes() ?: ArrayDeque()
            }
            val answer_type = arr.removeFirstOrNull() ?: ANSWER_TYPE()
            answer_type.IDENTIFICATOR_1 = res.getString(4)
            answer_type.IDENTIFICATOR_2 = res.getString(5)
            answer_type.IDENTIFICATOR_3 = res.getString(6)
            answer_type.IDENTIFICATOR_4 = res.getString(7)
            answer_type.IDENTIFICATOR_5 = res.getString(8)
            answer_type.IDENTIFICATOR_6 = res.getString(9)
            answer_type.IDENTIFICATOR_7 = res.getString(10)
            answer_type.IDENTIFICATOR_8 = res.getString(11)
            answer_type.IDENTIFICATOR_9 = res.getString(12)
            answer_type.IDENTIFICATOR_10 = res.getString(13)
            answer_type.IDENTIFICATOR_11 = res.getString(14)
            answer_type.IDENTIFICATOR_12 = res.getString(15)
            answer_type.IDENTIFICATOR_13 = res.getString(16)
            answer_type.IDENTIFICATOR_14 = res.getString(17)
            answer_type.IDENTIFICATOR_15 = res.getString(18)
            answer_type.IDENTIFICATOR_16 = res.getString(19)
            answer_type.IDENTIFICATOR_17 = res.getString(20)
            answer_type.IDENTIFICATOR_18 = res.getString(21)
            answer_type.IDENTIFICATOR_19 = res.getString(22)
            answer_type.IDENTIFICATOR_20 = res.getString(23)
            answer_type.INTEGER_1 = res.getLong(24)?.toInt()
            answer_type.INTEGER_2 = res.getLong(25)?.toInt()
            answer_type.INTEGER_3 = res.getLong(26)?.toInt()
            answer_type.INTEGER_4 = res.getLong(27)?.toInt()
            answer_type.INTEGER_5 = res.getLong(28)?.toInt()
            answer_type.INTEGER_6 = res.getLong(29)?.toInt()
            answer_type.INTEGER_7 = res.getLong(30)?.toInt()
            answer_type.INTEGER_8 = res.getLong(31)?.toInt()
            answer_type.INTEGER_9 = res.getLong(32)?.toInt()
            answer_type.INTEGER_10 = res.getLong(33)?.toInt()
            answer_type.INTEGER_11 = res.getLong(34)?.toInt()
            answer_type.INTEGER_12 = res.getLong(35)?.toInt()
            answer_type.INTEGER_13 = res.getLong(36)?.toInt()
            answer_type.INTEGER_14 = res.getLong(37)?.toInt()
            answer_type.INTEGER_15 = res.getLong(38)?.toInt()
            answer_type.INTEGER_16 = res.getLong(39)?.toInt()
            answer_type.INTEGER_17 = res.getLong(40)?.toInt()
            answer_type.INTEGER_18 = res.getLong(41)?.toInt()
            answer_type.INTEGER_19 = res.getLong(42)?.toInt()
            answer_type.INTEGER_20 = res.getLong(43)!!.toInt()
            answer_type.LONG_1 = res.getLong(44)
            answer_type.LONG_2 = res.getLong(45)
            answer_type.LONG_3 = res.getLong(46)
            answer_type.LONG_4 = res.getLong(47)
            answer_type.LONG_5 = res.getLong(48)
            answer_type.LONG_6 = res.getLong(49)
            answer_type.LONG_7 = res.getLong(50)
            answer_type.LONG_8 = res.getLong(51)
            answer_type.LONG_9 = res.getLong(52)
            answer_type.LONG_10 = res.getLong(53)
            answer_type.LONG_11 = res.getLong(54)
            answer_type.LONG_12 = res.getLong(55)
            answer_type.LONG_13 = res.getLong(56)
            answer_type.LONG_14 = res.getLong(57)
            answer_type.LONG_15 = res.getLong(58)
            answer_type.LONG_16 = res.getLong(59)
            answer_type.LONG_17 = res.getLong(60)
            answer_type.LONG_18 = res.getLong(61)
            answer_type.LONG_19 = res.getLong(62)
            answer_type.LONG_20 = res.getLong(63)!!
            answer_type.STRING_1 = res.getString(64)
            answer_type.STRING_2 = res.getString(65)
            answer_type.STRING_3 = res.getString(66)
            answer_type.STRING_4 = res.getString(67)
            answer_type.STRING_5 = res.getString(68)
            answer_type.STRING_6 = res.getString(69)
            answer_type.STRING_7 = res.getString(70)
            answer_type.STRING_8 = res.getString(71)
            answer_type.STRING_9 = res.getString(72)
            answer_type.STRING_10 = res.getString(73)
            answer_type.STRING_11 = res.getString(74)
            answer_type.STRING_12 = res.getString(75)
            answer_type.STRING_13 = res.getString(76)
            answer_type.STRING_14 = res.getString(77)
            answer_type.STRING_15 = res.getString(78)
            answer_type.STRING_16 = res.getString(79)
            answer_type.STRING_17 = res.getString(80)
            answer_type.STRING_18 = res.getString(81)
            answer_type.STRING_19 = res.getString(82)
            answer_type.STRING_20 = res.getString(83)!!
            answer_type.BLOB_1 = res.getBytes(84)
            answer_type.BLOB_2 = res.getBytes(85)
            answer_type.BLOB_3 = res.getBytes(86)
            arr_res.addLast(answer_type)
        }
        return arr_res
    }

    actual suspend fun SELECT_CASHDATA(CASH_SUM: String, RECORD_TABLE_ID: String): ANSWER_TYPE? {
        val res = cashData.select_CashData(CASH_SUM, RECORD_TABLE_ID).execute()
        val answer_type = ANSWER_TYPE.GetAnswerType() ?: ANSWER_TYPE()
        if (res.next()) {
            answer_type.IDENTIFICATOR_1 = res.getString(4)
            answer_type.IDENTIFICATOR_2 = res.getString(5)
            answer_type.IDENTIFICATOR_3 = res.getString(6)
            answer_type.IDENTIFICATOR_4 = res.getString(7)
            answer_type.IDENTIFICATOR_5 = res.getString(8)
            answer_type.IDENTIFICATOR_6 = res.getString(9)
            answer_type.IDENTIFICATOR_7 = res.getString(10)
            answer_type.IDENTIFICATOR_8 = res.getString(11)
            answer_type.IDENTIFICATOR_9 = res.getString(12)
            answer_type.IDENTIFICATOR_10 = res.getString(13)
            answer_type.IDENTIFICATOR_11 = res.getString(14)
            answer_type.IDENTIFICATOR_12 = res.getString(15)
            answer_type.IDENTIFICATOR_13 = res.getString(16)
            answer_type.IDENTIFICATOR_14 = res.getString(17)
            answer_type.IDENTIFICATOR_15 = res.getString(18)
            answer_type.IDENTIFICATOR_16 = res.getString(19)
            answer_type.IDENTIFICATOR_17 = res.getString(20)
            answer_type.IDENTIFICATOR_18 = res.getString(21)
            answer_type.IDENTIFICATOR_19 = res.getString(22)
            answer_type.IDENTIFICATOR_20 = res.getString(23)
            answer_type.INTEGER_1 = res.getLong(24)?.toInt()
            answer_type.INTEGER_2 = res.getLong(25)?.toInt()
            answer_type.INTEGER_3 = res.getLong(26)?.toInt()
            answer_type.INTEGER_4 = res.getLong(27)?.toInt()
            answer_type.INTEGER_5 = res.getLong(28)?.toInt()
            answer_type.INTEGER_6 = res.getLong(29)?.toInt()
            answer_type.INTEGER_7 = res.getLong(30)?.toInt()
            answer_type.INTEGER_8 = res.getLong(31)?.toInt()
            answer_type.INTEGER_9 = res.getLong(32)?.toInt()
            answer_type.INTEGER_10 = res.getLong(33)?.toInt()
            answer_type.INTEGER_11 = res.getLong(34)?.toInt()
            answer_type.INTEGER_12 = res.getLong(35)?.toInt()
            answer_type.INTEGER_13 = res.getLong(36)?.toInt()
            answer_type.INTEGER_14 = res.getLong(37)?.toInt()
            answer_type.INTEGER_15 = res.getLong(38)?.toInt()
            answer_type.INTEGER_16 = res.getLong(39)?.toInt()
            answer_type.INTEGER_17 = res.getLong(40)?.toInt()
            answer_type.INTEGER_18 = res.getLong(41)?.toInt()
            answer_type.INTEGER_19 = res.getLong(42)?.toInt()
            answer_type.INTEGER_20 = res.getLong(43)!!.toInt()
            answer_type.LONG_1 = res.getLong(44)
            answer_type.LONG_2 = res.getLong(45)
            answer_type.LONG_3 = res.getLong(46)
            answer_type.LONG_4 = res.getLong(47)
            answer_type.LONG_5 = res.getLong(48)
            answer_type.LONG_6 = res.getLong(49)
            answer_type.LONG_7 = res.getLong(50)
            answer_type.LONG_8 = res.getLong(51)
            answer_type.LONG_9 = res.getLong(52)
            answer_type.LONG_10 = res.getLong(53)
            answer_type.LONG_11 = res.getLong(54)
            answer_type.LONG_12 = res.getLong(55)
            answer_type.LONG_13 = res.getLong(56)
            answer_type.LONG_14 = res.getLong(57)
            answer_type.LONG_15 = res.getLong(58)
            answer_type.LONG_16 = res.getLong(59)
            answer_type.LONG_17 = res.getLong(60)
            answer_type.LONG_18 = res.getLong(61)
            answer_type.LONG_19 = res.getLong(62)
            answer_type.LONG_20 = res.getLong(63)!!
            answer_type.STRING_1 = res.getString(64)
            answer_type.STRING_2 = res.getString(65)
            answer_type.STRING_3 = res.getString(66)
            answer_type.STRING_4 = res.getString(67)
            answer_type.STRING_5 = res.getString(68)
            answer_type.STRING_6 = res.getString(69)
            answer_type.STRING_7 = res.getString(70)
            answer_type.STRING_8 = res.getString(71)
            answer_type.STRING_9 = res.getString(72)
            answer_type.STRING_10 = res.getString(73)
            answer_type.STRING_11 = res.getString(74)
            answer_type.STRING_12 = res.getString(75)
            answer_type.STRING_13 = res.getString(76)
            answer_type.STRING_14 = res.getString(77)
            answer_type.STRING_15 = res.getString(78)
            answer_type.STRING_16 = res.getString(79)
            answer_type.STRING_17 = res.getString(80)
            answer_type.STRING_18 = res.getString(81)
            answer_type.STRING_19 = res.getString(82)
            answer_type.STRING_20 = res.getString(83)!!
            answer_type.BLOB_1 = res.getBytes(84)
            answer_type.BLOB_2 = res.getBytes(85)
            answer_type.BLOB_3 = res.getBytes(86)
            return answer_type
        } else {
            return null
        }
    }

    actual suspend fun SELECT_CASHDATA_ALL_RECORDS_ID_ON_CASH_SUM(CASH_SUM: String): ArrayList<String> {
        val res = cashData.select_CashDataAllRecordsIdOnCashSum(CASH_SUM).execute()
        val arr: ArrayList<String> = ArrayList()
        while (res.next()) {
            val s = res.getString(1)
            arr.add(s!!)
        }
        return arr
    }

    actual suspend fun CLEAR_CASHDATA() {
        cashData.clear_CashData()
    }

    actual suspend fun CASHDATA_SORT_NEW_NUMBER_POSITIONS(CASH_SUM: String) {
        cashData.update_CashData_SortNewNumbersPositions(CASH_SUM, CASH_SUM)
    }

    actual suspend fun CASHDATA_SORT_NEW_NUMBER_POSITIONS_FINISH(CASH_SUM: String) {
        cashData.update_CashData_SortNewNumbersPositionsFinish(CASH_SUM)
    }

    actual suspend fun UPDATE_CASHDATA_NEW_LAST_SELECT(
        last_select: Long,
        cash_sum: String,
        record_table_id_from: String,
        limit: Int
    ) {
        cashData.update_CashData_NEW_LAST_SELECT(
            last_select,
            cash_sum,
            cash_sum,
            limit.toLong(),
            cash_sum,
            record_table_id_from
        )
    }

    actual suspend fun DELETE_CASHDATA(cash_sum: String) {
        cashData.delete_CashData(cash_sum)
    }

    actual suspend fun DELETE_CASHDATA_RECORD(cash_sum: String, record_id: String) {
        cashData.delete_CashDataRecord(cash_sum, record_id)
    }

}