/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package sql


/**
 *
 * @author User
 */


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    const val TABLE_METADATA = """CREATE TABLE IF NOT EXISTS MetaData 
                                (VALUE_NAME TEXT PRIMARY KEY, 
                                 VALUE_VALUE TEXT NOT NULL,
                                 LAST_UPDATE TEXT NOT NULL);"""

    const val INSERT_METADATA = """REPLACE INTO MetaData VALUES (?, ?, ?);"""

    const val CLEAR_METADATA = """DELETE FROM MetaData;"""

    const val SELECT_ALL_METADATA = """SELECT * FROM MetaData;"""

    const val SELECT_METADATA = """SELECT * FROM MetaData WHERE VALUE_NAME = ?;"""

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    const val TABLE_CASHDATA = """CREATE TABLE IF NOT EXISTS CashData
                                 (CASH_SUM INTEGER NOT NULL,
                                  IDENTIFICATORS TEXT NOT NULL,
                                  IDENTIFICATOR_1 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  IDENTIFICATOR_2 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  IDENTIFICATOR_3 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  IDENTIFICATOR_4 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  IDENTIFICATOR_5 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  IDENTIFICATOR_6 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  IDENTIFICATOR_7 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  IDENTIFICATOR_8 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  IDENTIFICATOR_9 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  IDENTIFICATOR_10 TEXT DEFAULT "000000000000000000" NOT NULL,
                                  INTEGER_1 INTEGER DEFAULT 0 NOT NULL,
                                  INTEGER_2 INTEGER DEFAULT 0 NOT NULL,
                                  INTEGER_3 INTEGER DEFAULT 0 NOT NULL,
                                  INTEGER_4 INTEGER DEFAULT 0 NOT NULL,
                                  INTEGER_5 INTEGER DEFAULT 0 NOT NULL,
                                  INTEGER_6 INTEGER DEFAULT 0 NOT NULL,
                                  INTEGER_7 INTEGER DEFAULT 0 NOT NULL,
                                  INTEGER_8 INTEGER DEFAULT 0 NOT NULL,
                                  INTEGER_9 INTEGER DEFAULT 0 NOT NULL,
                                  INTEGER_10 INTEGER DEFAULT 0 NOT NULL,
                                  LONG_1 TEXT DEFAULT "0" NOT NULL,
                                  LONG_2 TEXT DEFAULT "0" NOT NULL,
                                  LONG_3 TEXT DEFAULT "0" NOT NULL,
                                  LONG_4 TEXT DEFAULT "0" NOT NULL,
                                  LONG_5 TEXT DEFAULT "0" NOT NULL,
                                  LONG_6 TEXT DEFAULT "0" NOT NULL,
                                  LONG_7 TEXT DEFAULT "0" NOT NULL,
                                  LONG_8 TEXT DEFAULT "0" NOT NULL,
                                  LONG_9 TEXT DEFAULT "0" NOT NULL,
                                  LONG_10 TEXT DEFAULT "1000000000000000000" NOT NULL,
                                  STRING_1 TEXT DEFAULT "" NOT NULL,
                                  STRING_2 TEXT DEFAULT "" NOT NULL,
                                  STRING_3 TEXT DEFAULT "" NOT NULL,
                                  STRING_4 TEXT DEFAULT "" NOT NULL,
                                  STRING_5 TEXT DEFAULT "" NOT NULL,
                                  STRING_6 TEXT DEFAULT "" NOT NULL,
                                  STRING_7 TEXT DEFAULT "" NOT NULL,
                                  STRING_8 TEXT DEFAULT "" NOT NULL,
                                  STRING_9 TEXT DEFAULT "" NOT NULL,
                                  STRING_10 TEXT DEFAULT "" NOT NULL,
                                  BLOB_1 BLOB ,
                                  BLOB_2 BLOB ,
                                  BLOB_3 BLOB ,
                                  BLOB_4 BLOB ,
                                  BLOB_5 BLOB ,
                                  BLOB_6 BLOB ,
                                  BLOB_7 BLOB ,
                                  BLOB_8 BLOB ,
                                  BLOB_9 BLOB ,
                                  BLOB_10 BLOB ,
                                  CHECK (
                                  length(IDENTIFICATORS) = 180
                                  AND length(IDENTIFICATOR_1) = 18
                                  AND length(IDENTIFICATOR_2) = 18
                                  AND length(IDENTIFICATOR_3) = 18
                                  AND length(IDENTIFICATOR_4) = 18
                                  AND length(IDENTIFICATOR_5) = 18
                                  AND length(IDENTIFICATOR_6) = 18
                                  AND length(IDENTIFICATOR_7) = 18
                                  AND length(IDENTIFICATOR_8) = 18
                                  AND length(IDENTIFICATOR_9) = 18
                                  AND length(IDENTIFICATOR_10) = 18
                                  AND length(LONG_1) < 20
                                  AND length(LONG_2) < 20
                                  AND length(LONG_3) < 20
                                  AND length(LONG_4) < 20
                                  AND length(LONG_5) < 20
                                  AND length(LONG_6) < 20
                                  AND length(LONG_7) < 20
                                  AND length(LONG_8) < 20
                                  AND length(LONG_9) < 20
                                  AND length(LONG_10) < 20
                                  ),
                                  PRIMARY KEY (CASH_SUM, IDENTIFICATORS));"""

    const val TRIGGER_CASHDATA_BLOB_1 = """CREATE TRIGGER TCashData_BLOB_1
       AFTER UPDATE ON CashData
       FOR EACH ROW 
       WHEN new.BLOB_1 IS NULL AND SUBSTR(new.LONG_10,2,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_1 = old.BLOB_1
       WHERE rowid = new.rowid;
       END;"""

    const val TRIGGER_CASHDATA_BLOB_2 = """CREATE TRIGGER TCashData_BLOB_2
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN new.BLOB_2 IS NULL AND SUBSTR(new.LONG_10,3,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_2 = old.BLOB_2
       WHERE rowid = new.rowid;
       END;"""

    const val TRIGGER_CASHDATA_BLOB_3 = """CREATE TRIGGER TCashData_BLOB_3
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN new.BLOB_3 IS NULL AND SUBSTR(new.LONG_10,4,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_3 = old.BLOB_3
       WHERE rowid = new.rowid;
       END;"""

    const val TRIGGER_CASHDATA_BLOB_4 = """CREATE TRIGGER TCashData_BLOB_4
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN new.BLOB_4 IS NULL AND SUBSTR(new.LONG_10,5,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_4 = old.BLOB_4
       WHERE rowid = new.rowid;
       END;"""

    const val TRIGGER_CASHDATA_BLOB_5 = """CREATE TRIGGER TCashData_BLOB_5
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN new.BLOB_5 IS NULL AND SUBSTR(new.LONG_10,6,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_5 = old.BLOB_5
       WHERE rowid = new.rowid;
       END;"""

    const val TRIGGER_CASHDATA_BLOB_6 = """CREATE TRIGGER TCashData_BLOB_6
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN new.BLOB_6 IS NULL AND SUBSTR(new.LONG_10,7,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_6 = old.BLOB_6
       WHERE rowid = new.rowid;
       END;"""

    const val TRIGGER_CASHDATA_BLOB_7 = """CREATE TRIGGER TCashData_BLOB_7
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN new.BLOB_7 IS NULL AND SUBSTR(new.LONG_10,8,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_7 = old.BLOB_7
       WHERE rowid = new.rowid;
       END;"""

    const val TRIGGER_CASHDATA_BLOB_8 = """CREATE TRIGGER TCashData_BLOB_8
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN new.BLOB_8 IS NULL AND SUBSTR(new.LONG_10,9,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_8 = old.BLOB_8
       WHERE rowid = new.rowid;
       END;"""

    const val TRIGGER_CASHDATA_BLOB_9 = """CREATE TRIGGER TCashData_BLOB_9
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN new.BLOB_9 IS NULL AND SUBSTR(new.LONG_10,10,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_9 = old.BLOB_9
       WHERE rowid = new.rowid;
       END"""

    const val TRIGGER_CASHDATA_BLOB_10 = """CREATE TRIGGER TCashData_BLOB_10
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN new.BLOB_10 IS NULL AND SUBSTR(new.LONG_10,11,1) = '0'
       BEGIN
       UPDATE CashData SET BLOB_10 = old.BLOB_10
       WHERE rowid = new.rowid;
       END;"""

    const val TRIGGER_CASHDATA_DELETING_RECORDS = """CREATE TRIGGER TCashData_DeletingRecord
       AFTER UPDATE ON CashData
       FOR EACH ROW WHEN SUBSTR(new.INTEGER_5,12,1) = '1'
       BEGIN
       DELETE FROM CashData WHERE rowid = new.rowid;
       END;"""

    const val INSERT_CASHDATA = """INSERT OR REPLACE INTO CashData
                            VALUES 
                            (?, ?, 
                             ?, ?, ?, ?, ?,
                             ?, ?, ?, ?, ?,
                             ?, ?, ?, ?, ?,
                             ?, ?, ?, ?, ?,
                             ?, ?, ?, ?, ?,
                             ?, ?, ?, ?, ?,
                             ?, ?, ?, ?, ?,
                             ?, ?, ?, ?, ?,
                             ?, ?, ?, ?, ?,
                             ?, ?, ?, ?, ? );"""

    const val UPDATE_CASHDATA = """UPDATE CashData SET 
                            IDENTIFICATOR_1 = ?, IDENTIFICATOR_2 = ?, IDENTIFICATOR_3 = ?, IDENTIFICATOR_4 = ?, IDENTIFICATOR_5 = ?, 
                            IDENTIFICATOR_6 = ?, IDENTIFICATOR_7 = ?, IDENTIFICATOR_8 = ?, IDENTIFICATOR_9 = ?, IDENTIFICATOR_10 = ?, 
                            INTEGER_1 = ?,  INTEGER_2 = ?, INTEGER_3 = ?, INTEGER_4 = ?, INTEGER_5 = ?, 
                            INTEGER_6 = ?,  INTEGER_7 = ?, INTEGER_8 = ?, INTEGER_9 = ?, INTEGER_10 = ?, 
                            LONG_1 = ?, LONG_2 = ?, LONG_3 = ?, LONG_4 = ?, LONG_5 = ?, 
                            LONG_6 = ?, LONG_7 = ?, LONG_8 = ?, LONG_9 = ?, LONG_10 = ?, 
                            STRING_1 = ?, STRING_2 = ?, STRING_3 = ?, STRING_4 = ?, STRING_5 = ?, 
                            STRING_6 = ?, STRING_7 = ?, STRING_8 = ?, STRING_9 = ?, STRING_10 = ?, 
                            BLOB_1 = ?, BLOB_2 = ?, BLOB_3 = ?, BLOB_4 = ?, BLOB_5 = ?,
                            BLOB_6 = ?, BLOB_7 = ?, BLOB_8 = ?, BLOB_9 = ?, BLOB_10 = ? 
                            WHERE CASH_SUM = ? AND IDENTIFICATORS = ?; """

    const val CLEAR_CASHDATA = """DELETE FROM CashData;"""

    const val SELECT_ALL_CASHDATA = """SELECT IDENTIFICATOR_1, IDENTIFICATOR_2, IDENTIFICATOR_3, IDENTIFICATOR_4, IDENTIFICATOR_5, 
                            IDENTIFICATOR_6, IDENTIFICATOR_7, IDENTIFICATOR_8, IDENTIFICATOR_9, IDENTIFICATOR_10,
                            INTEGER_1,INTEGER_2, INTEGER_3, INTEGER_4, INTEGER_5,
                            INTEGER_6, INTEGER_7, INTEGER_8, INTEGER_9, INTEGER_10,
                            LONG_1, LONG_2, LONG_3, LONG_4, LONG_5, 
                            LONG_6, LONG_7, LONG_8, LONG_9, LONG_10,
                            STRING_1, STRING_2, STRING_3, STRING_4, STRING_5,
                            STRING_6, STRING_7, STRING_8, STRING_9, STRING_10,
                            BLOB_1, BLOB_2, BLOB_3, BLOB_4, BLOB_5,
                            BLOB_6, BLOB_7, BLOB_8, BLOB_9, BLOB_10 
                            FROM CashData WHERE CASH_SUM = ?;"""

    const val SELECT_CASHDATA = """SELECT IDENTIFICATOR_1, IDENTIFICATOR_2, IDENTIFICATOR_3, IDENTIFICATOR_4, IDENTIFICATOR_5, 
                            IDENTIFICATOR_6, IDENTIFICATOR_7, IDENTIFICATOR_8, IDENTIFICATOR_9, IDENTIFICATOR_10,
                            INTEGER_1,INTEGER_2, INTEGER_3, INTEGER_4, INTEGER_5,
                            INTEGER_6, INTEGER_7, INTEGER_8, INTEGER_9, INTEGER_10,
                            LONG_1, LONG_2, LONG_3, LONG_4, LONG_5, 
                            LONG_6, LONG_7, LONG_8, LONG_9, LONG_10,
                            STRING_1, STRING_2, STRING_3, STRING_4, STRING_5,
                            STRING_6, STRING_7, STRING_8, STRING_9, STRING_10,
                            BLOB_1, BLOB_2, BLOB_3, BLOB_4, BLOB_5,
                            BLOB_6, BLOB_7, BLOB_8, BLOB_9, BLOB_10 
                            FROM CashData WHERE CASH_SUM = ? 
                            AND IDENTIFICATORS = ?;"""

    const val SELECT_COUNT_CASHDATA = """SELECT count(*) FROM CashData;"""

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    const val TABLE_REGDATA = """CREATE TABLE IF NOT EXISTS RegData(
                                 CONNECTION_ID TEXT PRIMARY KEY,
                                 CONNECTION_COOCKI TEXT NOT NULL,
                                 MY_ID TEXT DEFAULT "" NOT NULL,
                                 LANG TEXT DEFAULT 'ENG' NOT NULL,
                                 REQUEST_PROFILE TEXT DEFAULT "" NOT NULL,
                                 ACCOUNT_PROFILE TEXT DEFAULT "" NOT NULL,
                                 CHECK (
                                  length(CONNECTION_ID)= 18
                                  AND length(CONNECTION_COOCKI)= 18
                                  AND (MY_ID = "" OR length(MY_ID)= 18)
                                  AND length(LANG)= 3
                                  AND (ACCOUNT_PROFILE = "" OR length(ACCOUNT_PROFILE)= 40)
                                  AND (REQUEST_PROFILE = "" OR length(REQUEST_PROFILE)= 30)
                                  AND REQUEST_PROFILE != '------------------------------'));"""

    const val TRIGGER_INSERT_REGDATA  = """CREATE TRIGGER TRegData_Insert
                                           BEFORE INSERT ON RegData
                                           FOR EACH ROW
                                           BEGIN
                                           DELETE FROM RegData;
                                           END;"""

    const val INSERT_REGDATA = """REPLACE INTO RegData 
                                  VALUES 
                                  (?, ?, ?, ?, ?, ?);"""

    const val CLEAR_REGDATA = """DELETE FROM RegData;"""

    const val SELECT_ALL_REGDATA = """SELECT * FROM RegData LIMIT 1;"""

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    const val TABLE_COMMANDS = """CREATE TABLE IF NOT EXISTS Commands
                                  (COMMANDS_ID INTEGER PRIMARY KEY,
                                  COMMANDS_ACCESS TEXT  NOT NULL,
                                  COMMANDS_PROFILE TEXT NOT NULL,
                                  COMMANDS_NECESSARILY_FIELDS TEXT  NOT NULL,
                                  CHECK ((COMMANDS_ID BETWEEN 1011000001 AND 1011999999) 
                                  AND length(COMMANDS_PROFILE)=30 
                                  AND length(COMMANDS_NECESSARILY_FIELDS) = 60
                                  AND length(COMMANDS_ACCESS) = 1
                                  ));"""

    const val INSERT_COMMANDS = """REPLACE INTO Commands (?, ?, ?, ?);"""

    const val CLEAR_COMMANDS = """DELETE FROM Commands;"""

    const val SELECT_ALL_COMMANDS = """SELECT * FROM Commands;"""

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    const val TABLE_SAVEMEDIA = """CREATE TABLE IF NOT EXISTS SaveMedia
                                   (OBJECTS_ID TEXT PRIMARY KEY,
                                   OBJECTS_EXTENSION TEXT NOT NULL,
                                   CONNECTION_ID TEXT NOT NULL,
                                   OBJECT_NAME TEXT NOT NULL,
                                   OBJECT_INFO TEXT DEFAULT "" NOT NULL,
                                   SMALL_AVATAR BLOB,
                                   IS_TEMP INTEGER NOT NULL,
                                   IS_DOWNLOAD INTEGER NOT NULL,
                                   TIME_ADDED TEXT NOT NULL,
                                   OBJECTS_SIZE TEXT NOT NULL,
                                   CHECK (length(CONNECTION_ID) = 18
                                   AND length(TIME_ADDED) < 20
                                   AND length(OBJECTS_SIZE) < 20
                                   AND CAST(OBJECTS_SIZE AS INTEGER) > 0
                                   AND length(OBJECTS_ID) = 18
                                   AND (IS_TEMP BETWEEN 0 AND 1) AND (IS_DOWNLOAD BETWEEN 0 AND 1)));"""

    const val INDEX_SAVEMEDIA =
        """CREATE INDEX ISaveMedia ON SaveMedia(CONNECTION_ID ASC, IS_TEMP  ASC, TIME_ADDED ASC);"""


    const val INSERT_SAVEMEDIA = """REPLACE INTO SaveMedia VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"""

    const val DELETE_ALL_SAVEMEDIA = """DELETE FROM SaveMedia WHERE CONNECTION_ID = ? AND IS_TEMP = ? ;"""

    const val DELETE_SAVEMEDIA = """DELETE FROM SaveMedia WHERE OBJECTS_ID = ?;"""

    const val SELECT_ALL_SAVEMEDIA = """SELECT * FROM SaveMedia WHERE CONNECTION_ID = ?;"""

    const val SELECT_COUNT_SAVEMEDIA = """SELECT count(*) FROM SaveMedia WHERE IS_TEMP = ? ;"""

    const val SELECT_LAST_SAVEMEDIA =
        """SELECT OBJECTS_ID FROM SaveMedia WHERE IS_TEMP = ? AND TIME_ADDED = 
           (SELECT min(CAST(TIME_ADDED AS INTEGER)) FROM SaveMedia WHERE IS_TEMP = ? GROUP BY TIME_ADDED) LIMIT 1;"""

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    const val TABLE_BIG_AVATARS = """CREATE TABLE IF NOT EXISTS BigAvatars
                                    (OBJECTS_ID TEXT PRIMARY KEY,
                                     TIME_ADDED TEXT NOT NULL,
                                     IS_TEMP INTEGER NOT NULL,
                                     SMALL_AVATAR_SIZE INTEGER NOT NULL,
                                     AVATAR BLOB  NOT NULL,
                                     CHECK (
                                     length(OBJECTS_ID)=18
                                     AND length(TIME_ADDED) < 20
                                     AND (IS_TEMP BETWEEN 0 AND 1)));"""

    const val TRIGGER_CONTROL_COUNT_BIG_AVATARS = """CREATE TRIGGER TControlCountsBigAvatars
    AFTER INSERT ON BigAvatars
    FOR EACH ROW WHEN
    (SELECT count(*) FROM BigAvatars)> 
    (SELECT  VALUE_VALUE FROM MetaData WHERE VALUE_NAME = 'MAX_COUNT_OF_BIG_AVATARS')
    BEGIN
    DELETE FROM BigAvatars WHERE OBJECTS_ID not in
    (SELECT OBJECTS_ID FROM BigAvatars ORDER BY CAST(TIME_ADDED AS INTEGER) DESC
    LIMIT (SELECT  VALUE_VALUE FROM MetaData WHERE VALUE_NAME = 'MAX_COUNT_OF_BIG_AVATARS'));
    END;"""

    const val INDEX_BIG_AVATARS = """CREATE INDEX IBigAvatars_TIME_ADDED ON BigAvatars(TIME_ADDED);"""

    const val INSERT_BIG_AVATARS = """REPLACE INTO BigAvatars
    VALUES
    (?, ?, ?, ?, ?);"""

    const val SELECT_BIG_AVATARS  = """SELECT * FROM BigAvatars WHERE OBJECTS_ID = ?;"""

    const val SELECT_COUNT_BIG_AVATARS  = """SELECT count(*) FROM BigAvatars;"""

    const val SELECT_LAST_BIG_AVATARS  = """SELECT OBJECTS_ID FROM BigAvatars WHERE TIME_ADDED = 
|                                           (SELECT min(CAST(TIME_ADDED AS INTEGER)) FROM BigAvatars) LIMIT 1;"""

    const val DELETE_BIG_AVATARS  = """DELETE FROM BigAvatars WHERE OBJECTS_ID = ?;"""

    const val CLEAR_BIG_AVATARS  = """DELETE FROM BigAvatars;"""

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    const val TABLE_SENDMEDIA = """CREATE TABLE IF NOT EXISTS SendMedia
                                   (OBJECTS_ID TEXT PRIMARY KEY,
                                   OBJECTS_EXTENSION TEXT NOT NULL,
                                   FILE_NAME TEXT NOT NULL,
                                   CONNECTION_ID TEXT NOT NULL,
                                   IS_DOWNLOAD INTEGER  NOT NULL,
                                   CHECK (length(CONNECTION_ID) = 18
                                   AND length(OBJECTS_ID)=18
                                   AND (IS_DOWNLOAD BETWEEN 0 AND 1)));"""

    const val TRIGGER_INSERT_SENDMEDIA   = """CREATE TRIGGER TSendMedia_Insert
    BEFORE INSERT ON SendMedia
    FOR EACH ROW
    BEGIN
    DELETE FROM SendMedia;
    END;"""

    const val INSERT_SENDMEDIA = """REPLACE INTO SendMedia
    VALUES
    (?, ?, ?, ?, ?);"""

    const val DELETE_SENDMEDIA = """DELETE FROM SendMedia WHERE OBJECTS_ID = ?;"""

    const val DELETE_ALL_SENDMEDIA = """DELETE FROM SendMedia WHERE CONNECTION_ID = ?;"""

    const val CLEAR_SENDMEDIA = """DELETE FROM SendMedia;"""

    const val SELECT_ALL_SENDMEDIA  = """SELECT * FROM SendMedia WHERE CONNECTION_ID = ?;"""

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
const val TABLE_LASTUPDATE = """CREATE TABLE IF NOT EXISTS CashLastUpdate
                               (CASH_SUM TEXT PRIMARY KEY,
                                LAST_UPDATE TEXT  NOT NULL,
                                LAST_USE TEXT NOT NULL,
                                IS_TEMP INTEGER NOT NULL
                                CHECK (length(CASH_SUM) < 20
                                AND length(LAST_UPDATE) < 20
                                AND length(LAST_USE) < 20
                                AND (IS_TEMP BETWEEN 0 AND 1)
));"""

    const val INDEX_LASTUPDATE = """CREATE INDEX ICashLastUpdate ON CashLastUpdate(LAST_USE ASC);"""

    const val TRIGGER_DELETE_LASTUPDATE = """CREATE TRIGGER TCashLastUpdate_DELETE
    AFTER DELETE ON CashLastUpdate
    FOR EACH ROW
    BEGIN
    DELETE FROM CashData WHERE CASH_SUM = old.CASH_SUM;
    END;"""

    const val TRIGGER_INSERT_LASTUPDATE = """CREATE TRIGGER TCashLastUpdate_Insert
    AFTER INSERT ON CashLastUpdate
    FOR EACH ROW
    BEGIN
    DELETE FROM CashLastUpdate  WHERE CASH_SUM = new.CASH_SUM
    AND ((SELECT count(*) FROM CashData t WHERE t.CASH_SUM = new.CASH_SUM) = 0);
    END;"""

    const val TRIGGER_CONTROL_COUNT_CASH = """CREATE TRIGGER TControlCountsCash
    AFTER INSERT ON CashLastUpdate
    FOR EACH ROW WHEN
    (SELECT count(*) FROM CashData)>(SELECT  VALUE_VALUE FROM MetaData WHERE VALUE_NAME = 'MAX_COUNT_OF_CASHDATA')
    BEGIN
    DELETE FROM CashLastUpdate WHERE CASH_SUM =
    (SELECT CASH_SUM FROM CashLastUpdate WHERE LAST_USE =
    (SELECT min(CAST(LAST_USE AS INTEGER)) FROM CashLastUpdate)
    LIMIT 1);
    DELETE FROM CashData WHERE CASH_SUM not in (SELECT CASH_SUM FROM CashLastUpdate);
    END;"""

    const val INSERT_LASTUPDATE = """REPLACE INTO CashLastUpdate
    VALUES
    (?, ?, ?, ?);"""

    const val UPDATE_LAST_USE_LASTUPDATE = """UPDATE CashLastUpdate SET LAST_USE = ? WHERE CASH_SUM = ?;"""

    const val CLEAR_LASTUPDATE = """DELETE FROM CashLastUpdate;"""

    const val SELECT_LASTUPDATE = """SELECT LAST_UPDATE FROM CashLastUpdate WHERE CASH_SUM = ?;"""

    const val DELETE_LASTUPDATE = """DELETE FROM CashLastUpdate WHERE CASH_SUM = ?;"""

    const val SELECT_LAST_LASTUPDATE  = """SELECT CASH_SUM FROM CashLastUpdate WHERE LAST_USE = 
|                                          (SELECT min(CAST(LAST_USE AS INTEGER)) FROM CashLastUpdate) LIMIT 1;"""

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    const val  TABLE_CHATS  = """CREATE TABLE IF NOT EXISTS Chats
                                (CHATS_ID TEXT PRIMARY KEY,
                                 CHATS_OWNER TEXT NOT NULL,
                                 LAST_MESSEGES_ID TEXT DEFAULT "0" NOT NULL,
                                 CHATS_OPPONENT TEXT,
                                 MESSEGES_COUNT TEXT NOT NULL,
                                 CHATS_NAME TEXT,
                                 CHATS_SMALL_AVATAR BLOB,
                                 CHATS_PROFILE TEXT NOT NULL,
                                 CHATS_ACCESS TEXT NOT NULL,
                                 CHATS_TYPE TEXT NOT NULL,
                                 CHATS_STATUS TEXT NOT NULL,
                                 LAST_UPDATING_DATE INTEGER NOT NULL,
                                 CHATS_SUBSCRIBE TEXT,
                                 CONNECTION_ID TEXT NOT NULL,
                                 IS_UPDATE_BLOB TEXT DEFAULT "0" NOT NULL,
                                 CHECK (length(CHATS_ID) = 18
                                 AND length(CHATS_OWNER) = 18
                                 AND length(IS_UPDATE_BLOB) = 1
                                 AND length(CHATS_PROFILE) = 10
                                 AND length(LAST_MESSEGES_ID) < 20
                                 AND length(MESSEGES_COUNT) < 20
                                 AND length(LAST_UPDATING_DATE) < 20
                                 ));"""

     const val TRIGGER_CHATS_AVATAR_UPDATE = """CREATE TRIGGER TChats_Avatar_Update
       AFTER UPDATE ON Chats
       FOR EACH ROW WHEN new.CHATS_SMALL_AVATAR IS NULL AND new.IS_UPDATE_BLOB = '0'
       BEGIN
       UPDATE Chats SET CHATS_SMALL_AVATAR = old.CHATS_SMALL_AVATAR
       WHERE rowid = new.rowid;
       END"""

    const val  TRIGGER_DELETE_CHATS = """CREATE TRIGGER TChats_DELETE
    AFTER DELETE ON Chats
    FOR EACH ROW
    BEGIN
    DELETE FROM Messeges WHERE CHATS_ID = old.CHATS_ID;
    END;"""

    const val  TRIGGER_CONTROL_COUNT_CHATS = """CREATE TRIGGER TControlCountsChats
    AFTER INSERT ON Chats
    FOR EACH ROW WHEN
    (SELECT count(*) FROM Chats)>(SELECT  VALUE_VALUE FROM MetaData WHERE VALUE_NAME = 'MAX_COUNT_OF_CHATS')
    BEGIN
    DELETE FROM Chats WHERE CHATS_ID not in
    (SELECT CHATS_ID FROM Chats ORDER BY CAST(LAST_UPDATING_DATE AS INTEGER) DESC
    LIMIT (SELECT  VALUE_VALUE FROM MetaData WHERE VALUE_NAME = 'MAX_COUNT_OF_CHATS'));
    END;"""

    const val INSERT_CHATS = """REPLACE INTO Chats 
                            VALUES 
                            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"""

    const val SELECT_COUNT_CHATS = """SELECT count(*) FROM Chats ;"""

    const val SELECT_CHAT = """SELECT * FROM Chats WHERE CHATS_ID = ? AND CONNECTION_ID = ?;"""

    const val SELECT_ALL_CHATS = """SELECT * FROM Chats WHERE CONNECTION_ID = ? ;"""

    const val SELECT_LAST_CHATS = """SELECT CHATS_ID FROM Chats WHERE LAST_UPDATING_DATE = 
    (SELECT min(CAST(LAST_UPDATING_DATE AS INTEGER)) FROM Chats GROUP BY LAST_UPDATING_DATE) LIMIT 1;"""

    const val CLEAR_CHATS = """DELETE FROM Chats;"""

    const val DELETE_CHAT = """DELETE FROM Chats WHERE CHATS_ID = ?;"""

/////////////////////////////////////////////////////////////////////////
    const val TABLE_MESSEGES = """CREATE TABLE IF NOT EXISTS Messeges
                                 (CHATS_ID TEXT NOT NULL,
                                  MESSEGES_ID TEXT NOT NULL,
                                  MESSEGES_CHATS_COUNT TEXT NOT NULL,
                                  MESSEGES_OWNER TEXT NOT NULL,
                                  MESSEGES_ANSWER TEXT DEFAULT 0 NOT NULL,
                                  MESSEGES_ADRESSER TEXT DEFAULT "" NOT NULL,
                                  OBJECTS_LINK TEXT DEFAULT "" NOT NULL,
                                  MESSEGE_TEXT TEXT DEFAULT "" NOT NULL,
                                  MESSEGES_AVATAR BLOB,
                                  MESSEGES_LIKES INTEGER NOT NULL,
                                  MESSEGES_DISLIKES INTEGER NOT NULL,
                                  NOT_DELIVERIED INTEGER NOT NULL,
                                  NOT_READED INTEGER NOT NULL,
                                  MESSEGES_ACCESS TEXT NOT NULL,
                                  MESSEGES_TYPE TEXT NOT NULL,
                                  DATE_ADDING TEXT NOT NULL,
                                  LAST_UPDATING_DATE TEXT NOT NULL,
                                  OBJECTS_LINK_SUBSCRIBE TEXT DEFAULT "" NOT NULL,
                                  OBJECTS_LINK_SMALL_AVATAR BLOB,
                                  CHECK (length(CHATS_ID) = 18
                                  AND length(MESSEGES_OWNER) = 18
                                  AND length(MESSEGES_ID) < 20
                                  AND length(MESSEGES_CHATS_COUNT) < 20
                                  AND length(MESSEGES_ANSWER) < 20
                                  AND length(DATE_ADDING) < 20
                                  AND length(LAST_UPDATING_DATE) < 20
                                  ),
                                  PRIMARY KEY (CHATS_ID, MESSEGES_CHATS_COUNT));"""
    // FOREIGN KEY (CHATS_ID) REFERENCES Chats (CHATS_ID) ON DELETE CASCADE ON UPDATE NO ACTION);""";

    const val TRIGGER_CONTROL_COUNT_MESSEGES = """CREATE TRIGGER TControlCountsMesseges
        AFTER INSERT ON Messeges
        FOR EACH ROW WHEN
        (SELECT count(*) FROM Messeges WHERE CHATS_ID = new.CHATS_ID)> 
        (SELECT  VALUE_VALUE FROM MetaData WHERE VALUE_NAME = 'MAX_COUNT_OF_MESSEGES')
        BEGIN
        DELETE FROM Messeges WHERE CHATS_ID = new.CHATS_ID AND MESSEGES_CHATS_COUNT NOT IN
        (SELECT MESSEGES_CHATS_COUNT FROM Messeges WHERE CHATS_ID = new.CHATS_ID 
        ORDER BY CAST(MESSEGES_CHATS_COUNT AS INTEGER) DESC
        LIMIT (SELECT  VALUE_VALUE FROM MetaData WHERE VALUE_NAME = 'MAX_COUNT_OF_MESSEGES'))
        OR CHATS_ID not in (SELECT CHATS_ID FROM Chats);
        END;"""


    const val INSERT_MESSEGES = """REPLACE INTO Messeges 
                            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"""


    const val SELECT_COUNT_MESSEGES  = """SELECT count(*) FROM Messeges WHERE CHATS_ID = ? ;"""

    const val SELECT_MESSEGES = """SELECT * FROM Messeges WHERE CHATS_ID = ? ;"""

    const val SELECT_LAST_MESSEGES = """SELECT min(MESSEGES_ID) FROM Messeges WHERE CHATS_ID = ? ;"""

    const val CLEAR_MESSEGES = """DELETE FROM Messeges;"""

    const val DELETE_MESSEGE = """DELETE FROM Messeges WHERE CHATS_ID = ? AND MESSEGES_ID = ?;"""

