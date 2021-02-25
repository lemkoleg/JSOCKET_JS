@file:Suppress("DuplicatedCode")

package sql

import CrossPlatforms.slash
import Tables.*
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.ktor.util.InternalAPI
import p_client.dbLocalName
import p_jsocket.ANSWER_TYPE
import p_jsocket.rootPath


actual var sqlDriver: SqlDriver? = JdbcSqliteDriver("""jdbc:sqlite:${rootPath}${slash}$dbLocalName""")


actual class SQLStatement actual constructor() {

    val metaData = db!!.mETADATAQueries
    val bigAvatars = db!!.bIGAVATARSQueries
    val cashData = db!!.cASHDATAQueries
    val lastUpdate = db!!.cASHLASTUPDATEQueries
    val chats = db!!.cHATSQueries
    val commands = db!!.cOMMANDSQueries
    val messeges = db!!.mESSEGESQueries
    val regData = db!!.rEGDATAQueries
    val saveMedia = db!!.sAVEMEDIAQueries
    val sendMedia = db!!.sENDMEDIAQueries

    ///////////////////////////////////////////////////////////////////////
    actual fun SELECT_LASTUPDATE(CASH_SUM: Long): Long? {
        return lastUpdate.select_lastupdate(CASH_SUM).executeAsOne()
    }

    actual fun connect() {

    }

    actual fun TABLE_METADATA() {
        metaData.table_metadata()
    }

    actual fun TABLE_CASHDATA() {
        cashData.table_cashdata()
    }

    actual fun TRIGGER_CASHDATA_BLOB_1() {
        cashData.trigger_cashdata_blob_1()
    }

    actual fun TRIGGER_CASHDATA_BLOB_2() {
        cashData.trigger_cashdata_blob_2()
    }

    actual fun TRIGGER_CASHDATA_BLOB_3() {
        cashData.trigger_cashdata_blob_3()
    }

    actual fun TRIGGER_CASHDATA_BLOB_4() {
        cashData.trigger_cashdata_blob_4()
    }

    actual fun TRIGGER_CASHDATA_BLOB_5() {
        cashData.trigger_cashdata_blob_5()
    }

    actual fun TRIGGER_CASHDATA_BLOB_6() {
        cashData.trigger_cashdata_blob_6()
    }

    actual fun TRIGGER_CASHDATA_BLOB_7() {
        cashData.trigger_cashdata_blob_7()
    }

    actual fun TRIGGER_CASHDATA_BLOB_8() {
        cashData.trigger_cashdata_blob_8()
    }

    actual fun TRIGGER_CASHDATA_BLOB_9() {
        cashData.trigger_cashdata_blob_9()
    }

    actual fun TRIGGER_CASHDATA_BLOB_10() {
        cashData.trigger_cashdata_blob_10()
    }

    actual fun TRIGGER_CASHDATA_DELETING_RECORDS() {
        cashData.trigger_cashdata_deleting_records()
    }

    actual fun TABLE_REGDATA() {
        regData.table_regdata()
    }

    actual fun TRIGGER_INSERT_REGDATA() {
        regData.trigger_insert_regdata()
    }

    actual fun TABLE_COMMANDS() {
        commands.table_commands()
    }

    actual fun TABLE_SAVEMEDIA() {
        saveMedia.table_savemedia()
    }

    actual fun INDEX_SAVEMEDIA() {
        saveMedia.index_savemedia()
    }

    actual fun TABLE_BIG_AVATARS() {
        bigAvatars.table_big_avatars()
    }

    actual fun INDEX_BIG_AVATARS() {
        bigAvatars.index_big_avatars()
    }

    actual fun TRIGGER_CONTROL_COUNT_BIG_AVATARS() {
        bigAvatars.trigger_control_count_big_avatars()
    }

    actual fun TABLE_SENDMEDIA() {
        sendMedia.table_sendmedia()
    }

    actual fun TRIGGER_INSERT_SENDMEDIA() {
        sendMedia.trigger_insert_sendmedia()
    }

    actual fun TABLE_LASTUPDATE() {
        lastUpdate.table_lastupdate()
    }

    actual fun INDEX_LASTUPDATE() {
        lastUpdate.index_lastupdate()
    }

    actual fun TRIGGER_DELETE_LASTUPDATE() {
        lastUpdate.trigger_delete_lastupdate()
    }

    actual fun TRIGGER_INSERT_LASTUPDATE() {
        lastUpdate.trigger_insert_lastupdate()
    }

    actual fun TRIGGER_CONTROL_COUNT_CASH() {
        lastUpdate.trigger_control_count_cash()
    }

    actual fun TABLE_CHATS() {
        chats.table_chats()
    }

    actual fun TRIGGER_DELETE_CHATS() {
        chats.trigger_delete_chats()
    }

    actual fun TRIGGER_CONTROL_COUNT_CHATS() {
        chats.trigger_control_count_chats()
    }

    actual fun TABLE_MESSEGES() {
        messeges.table_messeges()
    }

    actual fun TRIGGER_CONTROL_COUNT_MESSEGES() {
        messeges.trigger_control_count_messeges()
    }

    actual fun UPDATE_LAST_USE_LASTUPDATE(LAST_USE: Long, CASH_SUM: Long) {
        lastUpdate.update_last_use_lastupdate(LAST_USE, CASH_SUM)
    }

    actual fun CLEAR_CASHDATA() {
        cashData.clear_cashdata()
    }

    actual fun CLEAR_LASTUPDATE() {
        lastUpdate.clear_lastupdate()
    }

    actual fun CLEAR_REGDATA() {
        regData.clear_regdata()
    }

    actual fun CLEAR_COMMANDS() {
        commands.clear_commands()
    }

    actual fun CLEAR_METADATA() {
        metaData.clear_metadata()
    }

    actual fun CLEAR_BIG_AVATARS() {
        bigAvatars.clear_big_avatars()
    }

    actual fun DELETE_BIG_AVATARS(OBJECTS_ID: String) {
        bigAvatars.delete_big_avatars(OBJECTS_ID)
    }

    actual fun DELETE_LASTUPDATE(CASH_SUM: Long) {
        lastUpdate.delete_lastupdate(CASH_SUM)
    }

    actual fun DELETE_ALL_SAVEMEDIA(CONNECTION_ID: Long, IS_TEMP: Int) {
        saveMedia.delete_all_savemedia(CONNECTION_ID, IS_TEMP)
    }

    actual fun DELETE_SAVEMEDIA(OBJECTS_ID: String) {
        saveMedia.delete_savemedia(OBJECTS_ID)
    }

    actual fun DELETE_ALL_SENDMEDIA(CONNECTION_ID: Long) {
        sendMedia.delete_all_sendmedia(CONNECTION_ID)
    }

    actual fun DELETE_SENDMEDIA(OBJECTS_ID: String) {
        sendMedia.delete_sendmedia(OBJECTS_ID)
    }

    actual fun DELETE_CHAT(CHATS_ID: String) {
        chats.delete_chat(CHATS_ID)
    }

    actual fun CLEAR_CHATS() {
        chats.clear_chats()
    }

    actual fun DELETE_MESSEGE(CHATS_ID: String, MESSEGES_ID: Long) {
        messeges.delete_messege(CHATS_ID, MESSEGES_ID)
    }

    actual fun INSERT_CASHDATA(lANSWER_TYPE: ANSWER_TYPE, lcheck_sum: Long) {
        if (lANSWER_TYPE.IDENTIFICATOR_1!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_1 = "000000000000000000"
        if (lANSWER_TYPE.IDENTIFICATOR_2!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_2 = "000000000000000000"
        if (lANSWER_TYPE.IDENTIFICATOR_3!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_3 = "000000000000000000"
        if (lANSWER_TYPE.IDENTIFICATOR_4!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_4 = "000000000000000000"
        if (lANSWER_TYPE.IDENTIFICATOR_5!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_5 = "000000000000000000"
        if (lANSWER_TYPE.IDENTIFICATOR_6!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_6 = "000000000000000000"
        if (lANSWER_TYPE.IDENTIFICATOR_7!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_7 = "000000000000000000"
        if (lANSWER_TYPE.IDENTIFICATOR_8!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_8 = "000000000000000000"
        if (lANSWER_TYPE.IDENTIFICATOR_9!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_9 = "000000000000000000"
        if (lANSWER_TYPE.IDENTIFICATOR_10!!.isEmpty()) lANSWER_TYPE.IDENTIFICATOR_10 = "000000000000000000"
        cashData.insert_cashdata(
            lcheck_sum,
            (lANSWER_TYPE.IDENTIFICATOR_1 +
                    lANSWER_TYPE.IDENTIFICATOR_2 +
                    lANSWER_TYPE.IDENTIFICATOR_3 +
                    lANSWER_TYPE.IDENTIFICATOR_4 +
                    lANSWER_TYPE.IDENTIFICATOR_5 +
                    lANSWER_TYPE.IDENTIFICATOR_6 +
                    lANSWER_TYPE.IDENTIFICATOR_7 +
                    lANSWER_TYPE.IDENTIFICATOR_8 +
                    lANSWER_TYPE.IDENTIFICATOR_9 +
                    lANSWER_TYPE.IDENTIFICATOR_10
                    ),
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
            lANSWER_TYPE.LONG_1!!,
            lANSWER_TYPE.LONG_2!!,
            lANSWER_TYPE.LONG_3!!,
            lANSWER_TYPE.LONG_4!!,
            lANSWER_TYPE.LONG_5!!,
            lANSWER_TYPE.LONG_6!!,
            lANSWER_TYPE.LONG_7!!,
            lANSWER_TYPE.LONG_8!!,
            lANSWER_TYPE.LONG_9!!,
            lANSWER_TYPE.LONG_10!!,
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

    actual fun INSERT_LASTUPDATE(
        CASH_SUM: Long,
        LAST_UPDATE: Long,
        LAST_USE: Long,
        IS_TEMP: Int
    ) {
        lastUpdate.insert_lastupdate(CASH_SUM, LAST_UPDATE, LAST_USE, IS_TEMP)
    }

    actual fun INSERT_REGDATA(
        CONNECTION_ID: Long,
        CONNECTION_COOCKI: Long,
        MY_ID: String,
        LANG: String,
        REQUEST_PROFILE: String
    ) {
        regData.insert_regdata(CONNECTION_ID, CONNECTION_COOCKI, MY_ID, LANG, REQUEST_PROFILE)
    }

    actual fun INSERT_COMMANDS(
        COMMANDS_ID: Int,
        COMMANDS_ACCESS: String,
        COMMANDS_PROFILE: String,
        COMMANDS_NECESSARILY_FIELDS: String
    ) {
        commands.insert_commands(COMMANDS_ID, COMMANDS_ACCESS, COMMANDS_PROFILE, COMMANDS_NECESSARILY_FIELDS)
    }

    actual fun INSERT_METADATA(VALUE_NAME: String, VALUE_VALUE: Int) {
        metaData.insert_metadata(VALUE_NAME, VALUE_VALUE)
    }

    actual fun INSERT_SAVEMEDIA(
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
            CONNECTION_ID,
            OBJECT_NAME,
            OBJECT_INFO,
            SMALL_AVATAR,
            IS_TEMP,
            IS_DOWNLOAD,
            TIME_ADDED,
            OBJECTS_SIZE
        )
    }

    actual fun INSERT_BIG_AVATARS(
        OBJECTS_ID: String,
        TIME_ADDED: Long,
        IS_TEMP: Int,
        SMALL_AVATAR_SIZE: Int,
        AVATAR: ByteArray
    ) {
        bigAvatars.insert_big_avatars(
            OBJECTS_ID,
            TIME_ADDED,
            IS_TEMP,
            SMALL_AVATAR_SIZE,
            AVATAR
        )
    }

    actual fun INSERT_SENDMEDIA(
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
            CONNECTION_ID,
            IS_DOWNLOAD
        )
    }

    actual fun INSERT_CHATS(
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
        CHATS_SUBSCRIBE: String
    ) {
        chats.insert_chats(
            CHATS_ID,
            CHATS_OWNER,
            LAST_MESSEGES_ID,
            CHATS_OPPONENT,
            MESSEGES_COUNT,
            CHATS_NAME,
            CHATS_SMALL_AVATAR,
            CHATS_PROFILE,
            CHATS_ACCESS,
            CHATS_TYPE,
            CHATS_STATUS,
            LAST_UPDATING_DATE,
            CHATS_SUBSCRIBE
        )
    }

    actual fun INSERT_MESSEGES(
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
            MESSEGES_ID,
            MESSEGES_CHATS_COUNT,
            MESSEGES_OWNER,
            MESSEGES_ANSWER,
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
            DATE_ADDING,
            LAST_UPDATING_DATE,
            OBJECTS_LINK_SUBSCRIBE,
            OBJECTS_LINK_SMALL_AVATAR
        )
    }


    actual fun SELECT_COUNT_SAVEMEDIA(lTemp: Int): Int {
        return saveMedia.select_count_savemedia(lTemp).executeAsOne().toInt()
    }

    @ExperimentalStdlibApi
    actual fun SELECT_LAST_SAVEMEDIA(IS_TEMP: Int): ArrayDeque<String> {
        return ArrayDeque(saveMedia.select_last_savemedia(IS_TEMP, IS_TEMP).executeAsList())
    }

    actual fun SELECT_BIG_AVATARS(OBJECTS_ID: String): KBigAvatar? {
        val res = bigAvatars.select_big_avatars(OBJECTS_ID).execute()
        return if (res.next()) {
            KBigAvatar(
                res.getString(1)!!,
                res.getLong(2)!!,
                res.getLong(3)!!.toInt(),
                res.getLong(4)!!.toInt(),
                res.getBytes(5)!!
            )
        } else null
    }

    actual fun SELECT_ALL_REGDATA(): KRegData? {
        val res = regData.select_all_regdata().execute()
        return if (res.next()) {
            val ret = KRegData()
            ret.setCONNECTION_ID(res.getLong(1)!!)
            ret.setCONNECTION_COOCKI(res.getLong(2)!!)
            ret.setMY_DATABASE_ID(res.getString(3)!!)
            ret.setLANG(res.getString(4)!!)
            ret.setREQUEST_PROFILE(res.getString(5)!!)
            return ret
        } else null
    }

    @ExperimentalStdlibApi
    actual fun SELECT_ALL_METADATA(): ArrayDeque<KMetaData> {
        val res = metaData.select_all_metadata().execute()
        val ret = ArrayDeque<KMetaData>()
        while (res.next()) {
            val m = KMetaData()
            m.setVALUE_NAME(res.getString(1)!!)
            m.setVALUE_VALUE(res.getLong(2)!!.toInt())
            ret.add(m)
        }
        return ret
    }

    actual fun SELECT_METADATA(VALUE_NAME: String): KMetaData? {
        val res = metaData.select_metadata(VALUE_NAME).execute()
        return if (res.next()) {
            val ret = KMetaData()
            ret.setVALUE_NAME(res.getString(1)!!)
            ret.setVALUE_VALUE(res.getLong(2)!!.toInt())
            ret
        } else null
    }

    @ExperimentalStdlibApi
    actual fun SELECT_ALL_COMMANDS(): ArrayDeque<KCommands> {
        val res = commands.select_all_commands().execute()
        val ret = ArrayDeque<KCommands>()
        while (res.next()) {
            val c = KCommands()
            c.setCOMMANDS_ID(res.getLong(1)!!.toInt())
            c.setCOMMANDS_ACCESS(res.getString(2)!!)
            c.setCOMMANDS_PROFILE(res.getString(3)!!)
            c.setCOMMANDS_NECESSARILY_FIELDS(res.getString(4)!!)
            ret.add(c)
        }
        return ret
    }

    @ExperimentalStdlibApi
    actual fun SELECT_ALL_SAVEMEDIA(CONN_ID: Long): ArrayDeque<KSaveMedia> {
        val res = saveMedia.select_all_savemedia(CONN_ID).execute()
        val ret = ArrayDeque<KSaveMedia>()
        while (res.next()) {
            val s = KSaveMedia()
            s.setOBJECTS_ID(res.getString(1)!!)
            s.setOBJECTS_EXTENSION(res.getString(2)!!)
            s.setOBJECT_NAME(res.getString(4)!!)
            s.setOBJECT_INFO(res.getString(5)!!)
            s.setSMALL_AVATAR(res.getBytes(6))
            s.setIS_TEMP(res.getLong(7)!!.toInt())
            s.setIS_DOWNLOAD(res.getLong(8)!!.toInt())
            s.setTIME_ADDED(res.getLong(9)!!)
            s.setOBJECTS_SIZE(res.getLong(10)!!)
            ret.add(s)
        }
        return ret
    }

    @ExperimentalStdlibApi
    actual fun SELECT_ALL_SENDMEDIA(CONN_ID: Long): ArrayDeque<KSendMedia> {
        val res = sendMedia.select_all_sendmedia(CONN_ID).execute()
        val ret = ArrayDeque<KSendMedia>()
        while (res.next()) {
            val s = KSendMedia()
            s.setOBJECTS_ID(res.getString(1)!!)
            s.setOBJECTS_EXTENSION(res.getString(2)!!)
            s.setOBJECT_NAME(res.getString(3)!!)
            s.setIS_DOWNLOAD(res.getLong(5)!!.toInt())
            ret.add(s)
        }
        return ret
    }

    @ExperimentalStdlibApi
    actual fun SELECT_CASHDATA(CASH_SUM: Long): ArrayDeque<ANSWER_TYPE> {
        val res = cashData.select_cashdata(CASH_SUM).execute()
        val ret = ArrayDeque<ANSWER_TYPE>()
        while (res.next()) {
            val s = ANSWER_TYPE()
            s.IDENTIFICATOR_1 = res.getString(3)
            s.IDENTIFICATOR_2 = res.getString(4)
            s.IDENTIFICATOR_3 = res.getString(5)
            s.IDENTIFICATOR_4 = res.getString(6)
            s.IDENTIFICATOR_5 = res.getString(7)
            s.IDENTIFICATOR_6 = res.getString(8)
            s.IDENTIFICATOR_7 = res.getString(9)
            s.IDENTIFICATOR_8 = res.getString(10)
            s.IDENTIFICATOR_9 = res.getString(11)
            s.IDENTIFICATOR_10 = res.getString(12)
            s.INTEGER_1 = res.getLong(13)?.toInt()
            s.INTEGER_2 = res.getLong(14)?.toInt()
            s.INTEGER_3 = res.getLong(15)?.toInt()
            s.INTEGER_4 = res.getLong(16)?.toInt()
            s.INTEGER_5 = res.getLong(17)?.toInt()
            s.INTEGER_6 = res.getLong(18)?.toInt()
            s.INTEGER_7 = res.getLong(19)?.toInt()
            s.INTEGER_8 = res.getLong(20)?.toInt()
            s.INTEGER_9 = res.getLong(21)?.toInt()
            s.INTEGER_10 = res.getLong(22)?.toInt()
            s.LONG_1 = res.getLong(23)
            s.LONG_2 = res.getLong(24)
            s.LONG_3 = res.getLong(25)
            s.LONG_4 = res.getLong(26)
            s.LONG_5 = res.getLong(27)
            s.LONG_6 = res.getLong(28)
            s.LONG_7 = res.getLong(29)
            s.LONG_8 = res.getLong(30)
            s.LONG_9 = res.getLong(31)
            s.LONG_10 = res.getLong(32)
            s.STRING_1 = res.getString(33)
            s.STRING_2 = res.getString(34)
            s.STRING_3 = res.getString(35)
            s.STRING_4 = res.getString(36)
            s.STRING_5 = res.getString(37)
            s.STRING_6 = res.getString(38)
            s.STRING_7 = res.getString(39)
            s.STRING_8 = res.getString(40)
            s.STRING_9 = res.getString(41)
            s.STRING_10 = res.getString(42)
            s.BLOB_1 = res.getBytes(43)
            s.BLOB_2 = res.getBytes(44)
            s.BLOB_3 = res.getBytes(45)
            s.BLOB_4 = res.getBytes(46)
            s.BLOB_5 = res.getBytes(47)
            s.BLOB_6 = res.getBytes(48)
            s.BLOB_7 = res.getBytes(49)
            s.BLOB_8 = res.getBytes(50)
            s.BLOB_9 = res.getBytes(51)
            s.BLOB_10 = res.getBytes(52)
            ret.add(s)
        }
        return ret
    }

    @InternalAPI
    @ExperimentalStdlibApi
    actual fun SELECT_ALL_CHATS(): ArrayDeque<KChat> {
        val res = chats.select_all_chats().execute()
        val ret = ArrayDeque<KChat>()
        while (res.next()) {
            val s = KChat()
            s.setCHATS_ID(res.getString(1)!!)
            s.setCHATS_OWNER(res.getString(2)!!)
            s.setLAST_MESSEGES_ID(res.getLong(3)!!)
            s.setCHATS_OPPONENT(res.getString(4)!!)
            s.setMESSEGES_COUNT(res.getLong(5)!!)
            s.setCHATS_NAME(res.getString(6)!!)
            s.setCHATS_SMALL_AVATAR(res.getBytes(7))
            s.setCHATS_PROFILE(res.getString(8)!!)
            s.setCHATS_ACCESS(res.getString(9)!!)
            s.setCHATS_TYPE(res.getString(10)!!)
            s.setCHATS_STATUS(res.getString(11)!!)
            s.setLAST_UPDATING_DATE(res.getLong(12)!!)
            s.setCHATS_SUBSCRIBE(res.getString(13)!!)
            ret.add(s)
        }
        return ret
    }

    @ExperimentalStdlibApi
    actual fun SELECT_MESSEGES(CHATS_ID: String): ArrayDeque<KMessege> {
        val res = messeges.select_messeges(CHATS_ID).execute()
        val ret = ArrayDeque<KMessege>()
        while (res.next()) {
            val s = KMessege()
            s.setCHATS_ID(res.getString(1)!!)
            s.setMESSEGES_ID(res.getLong(2)!!)
            s.setMESSEGES_CHATS_COUNT(res.getLong(3)!!)
            s.setMESSEGES_OWNER(res.getString(4)!!)
            s.setMESSEGES_ANSWER(res.getLong(5)!!)
            s.setMESSEGES_ADRESSER(res.getString(6)!!)
            s.setOBJECTS_LINK(res.getString(7)!!)
            s.setMESSEGE_TEXT(res.getString(8)!!)
            s.setMESSEGES_AVATAR(res.getBytes(9))
            s.setMESSEGES_LIKES(res.getLong(10)!!.toInt())
            s.setMESSEGES_DISLIKES(res.getLong(11)!!.toInt())
            s.setNOT_DELIVERIED(res.getLong(12)!!.toInt())
            s.setNOT_READED(res.getLong(13)!!.toInt())
            s.setMESSEGES_ACCESS(res.getString(14)!!)
            s.setMESSEGES_TYPE(res.getString(15)!!)
            s.setDATE_ADDING(res.getLong(16)!!)
            s.setLAST_UPDATING_DATE(res.getLong(17)!!)
            s.setOBJECTS_LINK_SUBSCRIBE(res.getString(18)!!)
            s.setOBJECTS_LINK_SMALL_AVATAR(res.getBytes(19))
            ret.add(s)
        }
        return ret
    }

}