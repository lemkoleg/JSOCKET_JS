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
///////////////////////////////////big avatars///////////////////////////

const val TABLE_BIG_AVATARS = """
CREATE TABLE IF NOT EXISTS BigAvatars
(AVATAR_ID TEXT PRIMARY KEY,      
 LAST_USE INTEGER NOT NULL,      
 AVATAR BLOB  NOT NULL,
CHECK (
length(AVATAR_ID) = 18));
"""

const val INDEX_BIG_AVATARS_LAST_USE = """
CREATE INDEX IBigAvatarsLastUse ON BigAvatars(LAST_USE DESC);
"""

const val TRIGGER_BIG_AVATARS_CONTROL_COUNT = """
CREATE TRIGGER TBigAvatarsControlCounts 
AFTER INSERT ON BigAvatars 
FOR EACH ROW 
WHEN 
(SELECT count(*) 
 FROM BigAvatars)> 
(SELECT  VALUE_VALUE 
 FROM MetaData 
 WHERE VALUE_NAME = 'MAX_COUNT_OF_BIG_AVATARS') 
BEGIN 
DELETE FROM BigAvatars WHERE AVATAR_ID NOT IN 
(SELECT AVATAR_ID FROM BigAvatars 
 WHERE AVATAR_ID NOT IN (SELECT AVATAR_ID 
                         FROM SaveMedia) 
 AND   AVATAR_ID NOT IN (SELECT AVATAR_ID 
                         FROM RegData) 
 AND   AVATAR_ID NOT IN (SELECT AVATAR_ID 
                         FROM Chats 
                         WHERE 1 = (SELECT VALUE_VALUE 
                                     FROM MetaData 
                                     WHERE VALUE_NAME = 'IS_SAVE_CHATS_BIG_AVATARS_IN_CASHE_PERMINENTLY')) 
 AND   AVATAR_ID NOT IN (SELECT AVATAR_ID 
                         FROM ChatsLikes 
                         WHERE 1 = (SELECT VALUE_VALUE 
                         FROM MetaData 
                         WHERE VALUE_NAME = 'IS_SAVE_CHATS_LIKES_BIG_AVATARS_IN_CASHE_PERMINENTLY')) 
 ORDER BY LAST_USE DESC 
 LIMIT (SELECT  VALUE_VALUE 
        FROM MetaData 
        WHERE VALUE_NAME = 'MAX_COUNT_OF_BIG_AVATARS')); 
END;
"""

const val INSERT_BIG_AVATARS = """
INSERT OR REPLACE INTO BigAvatars 
(AVATAR_ID, LAST_USE,  AVATAR) 
VALUES
(?, ?,  ?);
"""

const val SELECT_BIG_AVATAR  = """
SELECT * FROM BigAvatars 
WHERE AVATAR_ID = ?;
"""

const val SELECT_BIGAVATARS_ALL_ID  = """
SELECT AVATAR_ID FROM BigAvatars;
"""

const val SELECT_BIGAVATARS_ALL  = """
SELECT * FROM BigAvatars;
"""

const val CLEAR_BIG_AVATARS  = """
DELETE FROM BigAvatars;
"""

const val DELETE_BIG_AVATAR  = """
DELETE FROM BigAvatars WHERE OBJECTS_ID = ?;
"""

const val UPDATE_BIG_AVATAR_LAST_USE  = """
UPDATE BigAvatars 
SET LAST_USE = ? 
WHERE AVATAR_ID = ?;
"""

/////////////cash data///////////////////////////

const val TABLE_CASHDATA = """
CREATE TABLE IF NOT EXISTS CashData 
(CASH_SUM INTEGER NOT NULL,
 RECORD_TABLE_ID TEXT NOT NULL,
 IDENTIFICATOR_1 TEXT,
 IDENTIFICATOR_2 TEXT,
 IDENTIFICATOR_3 TEXT,
 IDENTIFICATOR_4 TEXT,
 IDENTIFICATOR_5 TEXT,
 IDENTIFICATOR_6 TEXT,
 IDENTIFICATOR_7 TEXT,
 IDENTIFICATOR_8 TEXT,
 IDENTIFICATOR_9 TEXT,
 IDENTIFICATOR_10 TEXT,
 IDENTIFICATOR_11 TEXT,
 IDENTIFICATOR_12 TEXT,
 IDENTIFICATOR_13 TEXT,
 IDENTIFICATOR_14 TEXT,
 IDENTIFICATOR_15 TEXT,
 IDENTIFICATOR_16 TEXT,
 IDENTIFICATOR_17 TEXT,
 IDENTIFICATOR_18 TEXT,
 IDENTIFICATOR_19 TEXT,
 IDENTIFICATOR_20 TEXT,
 INTEGER_1 INTEGER,
 INTEGER_2 INTEGER,
 INTEGER_3 INTEGER,
 INTEGER_4 INTEGER,
 INTEGER_5 INTEGER,
 INTEGER_6 INTEGER,
 INTEGER_7 INTEGER,
 INTEGER_8 INTEGER,
 INTEGER_9 INTEGER,
 INTEGER_10 INTEGER,
 INTEGER_11 INTEGER,
 INTEGER_12 INTEGER,
 INTEGER_13 INTEGER,
 INTEGER_14 INTEGER,
 INTEGER_15 INTEGER,
 INTEGER_16 INTEGER,
 INTEGER_17 INTEGER,
 INTEGER_18 INTEGER,
 INTEGER_19 INTEGER,
 INTEGER_20 INTEGER NOT NULL DEFAULT 1,     -- NUMBER position
 LONG_1 INTEGER,
 LONG_2 INTEGER,
 LONG_3 INTEGER,
 LONG_4 INTEGER,
 LONG_5 INTEGER,
 LONG_6 INTEGER,
 LONG_7 INTEGER,
 LONG_8 INTEGER,
 LONG_9 INTEGER,
 LONG_10 INTEGER,
 LONG_11 INTEGER,
 LONG_12 INTEGER,
 LONG_13 INTEGER,
 LONG_14 INTEGER,
 LONG_15 INTEGER,
 LONG_16 INTEGER,
 LONG_17 INTEGER,
 LONG_18 INTEGER,
 LONG_19 INTEGER,
 LONG_20 INTEGER,
 STRING_1 TEXT,
 STRING_2 TEXT,
 STRING_3 TEXT,
 STRING_4 TEXT,
 STRING_5 TEXT,
 STRING_6 TEXT,
 STRING_7 TEXT,
 STRING_8 TEXT,
 STRING_9 TEXT,
 STRING_10 TEXT,
 STRING_11 TEXT,
 STRING_12 TEXT,
 STRING_13 TEXT,
 STRING_14 TEXT,
 STRING_15 TEXT,
 STRING_16 TEXT,
 STRING_17 TEXT,
 STRING_18 TEXT,
 STRING_19 TEXT,
 STRING_20 TEXT,
 BLOB_1 BLOB ,
 BLOB_2 BLOB ,
 BLOB_3 BLOB,
 INTEGER_20_LAST_UPDATE INTEGER NOT NULL DEFAULT 1, -- for sort NEW NUMBERS POSITION
 IS_UPDATE_NEXT_INTEGER_20 INTEGER NOT NULL DEFAULT 0,
 PRIMARY KEY (CASH_SUM, RECORD_TABLE_ID)
 );
"""

const val INDEX_CASHDATA_NUMBER_POSITION = """
CREATE INDEX CashData_NumberPosition ON CashData(CASH_SUM, INTEGER_20 ASC);
"""

const val INDEX_CASHDATA_NUMBER_POSITION_LAST_UPDATE = """
CREATE INDEX CashData_NumberPositionLastUpdate ON CashData(CASH_SUM, INTEGER_20 ASC, INTEGER_20_LAST_UPDATE DESC, RECORD_TABLE_ID);
"""

const val TRIGGER_CASHDATA_INSERT = """
 CREATE TRIGGER TCashDataInsert
 BEFORE INSERT ON CashData
 FOR EACH ROW
 BEGIN
  UPDATE CashData
  SET    INTEGER_20 = INTEGER_20 + 1,
         INTEGER_20_LAST_UPDATE = new.INTEGER_20_LAST_UPDATE,
         IS_UPDATE_NEXT_INTEGER_20 = 0
  WHERE  CASH_SUM = new.CASH_SUM
  AND    INTEGER_20 = new.INTEGER_20;
 END;
"""

const val TRIGGER_CASHDATA_UPDATE = """
  CREATE TRIGGER TCashDataUpdate
  BEFORE UPDATE ON CashData
  FOR EACH ROW
  WHEN new.IS_UPDATE_NEXT_INTEGER_20 = 1
  BEGIN
   UPDATE CashData
   SET    INTEGER_20 = INTEGER_20 + 1,
          INTEGER_20_LAST_UPDATE = new.INTEGER_20_LAST_UPDATE,
          IS_UPDATE_NEXT_INTEGER_20 = 0
   WHERE  CASH_SUM = new.CASH_SUM
   AND    INTEGER_20 = new.INTEGER_20;
  END;
"""

const val INSERT_CASHDATA = """
INSERT OR REPLACE INTO CashData 
( CASH_SUM,
  RECORD_TABLE_ID,
  IDENTIFICATOR_1,
  IDENTIFICATOR_2,
  IDENTIFICATOR_3,
  IDENTIFICATOR_4,
  IDENTIFICATOR_5,
  IDENTIFICATOR_6,
  IDENTIFICATOR_7,
  IDENTIFICATOR_8,
  IDENTIFICATOR_9,
  IDENTIFICATOR_10,
  IDENTIFICATOR_11,
  IDENTIFICATOR_12,
  IDENTIFICATOR_13,
  IDENTIFICATOR_14,
  IDENTIFICATOR_15,
  IDENTIFICATOR_16,
  IDENTIFICATOR_17,
  IDENTIFICATOR_18,
  IDENTIFICATOR_19,
  IDENTIFICATOR_20,
  INTEGER_1,
  INTEGER_2,
  INTEGER_3,
  INTEGER_4,
  INTEGER_5,
  INTEGER_6,
  INTEGER_7,
  INTEGER_8,
  INTEGER_9,
  INTEGER_10,
  INTEGER_11,
  INTEGER_12,
  INTEGER_13,
  INTEGER_14,
  INTEGER_15,
  INTEGER_16,
  INTEGER_17,
  INTEGER_18,
  INTEGER_19,
  INTEGER_20,
  LONG_1,
  LONG_2,
  LONG_3,
  LONG_4,
  LONG_5,
  LONG_6,
  LONG_7,
  LONG_8,
  LONG_9,
  LONG_10,
  LONG_11,
  LONG_12,
  LONG_13,
  LONG_14,
  LONG_15,
  LONG_16,
  LONG_17,
  LONG_18,
  LONG_19,
  LONG_20,
  STRING_1,
  STRING_2,
  STRING_3,
  STRING_4,
  STRING_5,
  STRING_6,
  STRING_7,
  STRING_8,
  STRING_9,
  STRING_10,
  STRING_11,
  STRING_12,
  STRING_13,
  STRING_14,
  STRING_15,
  STRING_16,
  STRING_17,
  STRING_18,
  STRING_19,
  STRING_20,
  BLOB_1,
  BLOB_2,
  BLOB_3,
  INTEGER_20_LAST_UPDATE,
  IS_UPDATE_NEXT_INTEGER_20)
VALUES 
(?, ?, ?, ?,
 ?, ?, ?, ?, ?,
 ?, ?, ?, ?, ?,
 ?, ?, ?, ?, ?,
 ?, ?, ?, ?, ?,
 ?, ?, ?, ?, ?,
 ?, ?, ?, ?, ?,
 ?, ?, ?, ?, ?,
 ?, ?, ?, ?, ?,
 ?, ?, ?, ?, ?,
 ?, ?, ?, ?, ? , ? , ? , ? , ?
 , ? , ? , ? , ? , ? , ? , ? , ?
 , ? , ? , ? , ? , ? , ? , ? , ? , ?
 , ? , ? , ? , ? , ? , ? , ? , ? , ?
 , ? , ? , ? );
"""

const val SELECT_CASHDATA_ALL = """
SELECT  *
FROM CashData
ORDER BY CASH_SUM, INTEGER_20 ASC;
"""

const val SELECT_CASHDATA_ALL_ON_CASH_SUM = """
SELECT  *
FROM CashData
WHERE CASH_SUM = ?
ORDER BY CASH_SUM, INTEGER_20 ASC;
"""

const val CLEAR_CASHDATA = """
DELETE FROM CashData;
"""

const val CASHDATA_SORT_NEW_NUMBER_POSITIONS = """
 WITH s (cash_sum, record_table_id, row_num)
  AS (SELECT
        CASH_SUM,
        RECORD_TABLE_ID,
        row_number() OVER (ORDER BY CASH_SUM, INTEGER_20 ASC, INTEGER_20_LAST_UPDATE DESC)
      FROM
        CashData
      WHERE CASH_SUM = ?
      ORDER BY CASH_SUM, INTEGER_20 ASC, INTEGER_20_LAST_UPDATE DESC)

  UPDATE CashData
  SET   INTEGER_20 = s.row_num,
        INTEGER_20_LAST_UPDATE = 0,
        IS_UPDATE_NEXT_INTEGER_20 = 0
  WHERE CASH_SUM = s.cash_sum
  AND   RECORD_TABLE_ID = s.record_table_id;
"""

/////////////last update///////////////////////////

const val TABLE_CASHLASTUPDATE  = """
CREATE TABLE IF NOT EXISTS CashLastUpdate 
(CASH_SUM INTEGER NOT NULL, 
 LAST_USE TEXT NOT NULL, 
 COUNT_OF_ROWS INTEGER NOT NULL, 
 CONNECTION_ID INTEGER NOT NULL, 
 PRIMARY KEY (CASH_SUM) 
);
"""


const val INDEX_CASHLASTUPDATE_LAST_USE = """
CREATE INDEX ICashLastUpdateLastUse ON CashLastUpdate(LAST_USE DESC);
"""

const val INDEX_CASHLASTUPDATE_CONNECTIONID = """
CREATE INDEX CashLastUpdateConnectionId ON CashLastUpdate(CONNECTION_ID);
"""

const val TRIGGER_CASHLASTUPDATE_DELETE = """
CREATE TRIGGER TCashLastUpdate_DELETE 
AFTER DELETE ON CashLastUpdate 
FOR EACH ROW 
BEGIN 
DELETE FROM CashData WHERE CASH_SUM = old.CASH_SUM; 
END;
"""

const val TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT = """
CREATE TRIGGER TCashLastUpdate_Control_Empty_Blocks_Insert
AFTER INSERT
ON CashLastUpdate
BEGIN
DELETE FROM CashLastUpdate
WHERE CASH_SUM NOT IN
(SELECT CASH_SUM FROM CashData t GROUP BY CASH_SUM);
END;
"""

const val TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_UPDATE = """
CREATE TRIGGER TCashLastUpdate_Control_Empty_Blocks_Insert
AFTER UPDATE
ON CashLastUpdate
BEGIN
DELETE FROM CashLastUpdate
WHERE CASH_SUM NOT IN
(SELECT CASH_SUM FROM CashData t GROUP BY CASH_SUM);
END;
"""

const val TRIGGER_CASHLASTUPDATE_UPDATE = """
CREATE TRIGGER TCashLastUpdate_Update
BEFORE UPDATE
ON CashLastUpdate
FOR EACH ROW
WHEN new.LAST_USE < new.LAST_UPDATE
BEGIN
  UPDATE CashLastUpdate
  SET    LAST_USE = new.LAST_UPDATE
  WHERE  CASH_SUM = new.CASH_SUM
  AND    LAST_USE < new.LAST_UPDATE;
END;
"""

const val TRIGGER_CASHLASTUPDATE_CONTROL_COUNT = """
CREATE TRIGGER TCashLastUpdate_Control_Count
AFTER INSERT
ON CashLastUpdate
WHEN
(SELECT count(*)
 FROM CashLastUpdate)>
(SELECT  VALUE_VALUE
 FROM MetaData
 WHERE VALUE_NAME = 'MAX_COUNT_OF_CASHDATA_BLOCKS')
BEGIN

DELETE FROM CashData
WHERE CASH_SUM =
(SELECT CASH_SUM FROM CashLastUpdate t ORDER BY LAST_USE DESC LIMIT 1);

DELETE FROM CashLastUpdate
WHERE CASH_SUM NOT IN
(SELECT CASH_SUM FROM CashData t GROUP BY CASH_SUM);

END;
"""

const val INSERT_CASHLASTUPDATE = """
INSERT OR REPLACE INTO CashLastUpdate 
(CASH_SUM, CONNECTION_ID, COUNT_OF_ROWS, LAST_USE) 
VALUES 
(?, ?, ?, ?);
"""

const val SELECT_CASHLASTUPDATE = """
SELECT * 
FROM CashLastUpdate 
WHERE CONNECTION_ID = ?;
"""

const val CLEAR_LASTUPDATE = """
DELETE FROM CashLastUpdate;
"""

const val UPDATE_CASHLASTUPDATE_LAST_USE = """
UPDATE CashLastUpdate 
SET    LAST_USE = ? 
WHERE  CASH_SUM = ?;
"""

/////////////chats///////////////////////////

const val  TABLE_CHATS  = """
CREATE TABLE IF NOT EXISTS Chats 
(CHATS_ID TEXT PRIMARY KEY, 
 MESSEGES_COUNT TEXT DEFAULT "0" NOT NULL, 
 COUNT_OF_MEMBERS INTEGER DEFAULT "0" NOT NULL,
 CHATS_OWNER TEXT NOT NULL, 
 CHATS_TWINS TEXT NOT NULL, 
 CHATS_NAME TEXT, 
 AVATAR_ID TEXT, 
 CHATS_PROFILE TEXT NOT NULL, 
 CHATS_TYPE TEXT NOT NULL, 
 CHATS_ACCESS TEXT NOT NULL, 
 CHATS_STATUS TEXT NOT NULL, 
 ADDING_DATE TEXT NOT NULL, 
 BALANCE INTEGER NOT NULL, 
 LAST_MESSEGES_ADDING TEXT NOT NULL, 
 DATE_CLOSED TEXT NOT NULL, 
 CHATS_SUBSCRIBE TEXT, 
 LAST_UPDATE TEXT NOT NULL, 
 ORIGINAL_AVATAR_SIZE TEXT, 
 AVATAR_SERVER TEXT, 
 AVATAR_LINK TEXT, 
 AVATAR BLOB, 
 CONNECTION_ID TEXT NOT NULL, 
 STRING_20 TEXT NOT NULL, 
CHECK (length(CHATS_ID) = 18 
AND length(CHATS_OWNER) = 18 
AND length(CHATS_PROFILE) = 5 
));
"""

const val INDEX_CHATS_LAST_MESS_ADDING = """
CREATE INDEX IChatsLastMessAdding ON Chats(LAST_MESSEGES_ADDING DESC);
"""

const val  INDEX_CHATS_CONNECTIONID = """
CREATE INDEX IChatsConnectionId ON Chats(CONNECTION_ID);
"""

const val  INDEX_CHATS_AVATARID = """
CREATE INDEX IChatsAvatarId ON Chats(AVATAR_ID);
"""

const val TRIGGER_CHATS_DELETE = """
CREATE TRIGGER TChats_DELETE 
AFTER DELETE ON Chats 
FOR EACH ROW 
BEGIN 
DELETE FROM Messeges  WHERE CHATS_ID = old.CHATS_ID; 
DELETE FROM ChatsLikes WHERE CHATS_ID = old.CHATS_ID; 
DELETE FROM ChatsCostTypes WHERE CHATS_ID = old.CHATS_ID; 
END;
"""

const val TRIGGER_CHATS_CONTROL_COUNT = """
CREATE TRIGGER TControlCountsChats 
AFTER INSERT ON Chats 
WHEN 
(SELECT count(*) FROM Chats) > (SELECT  VALUE_VALUE FROM MetaData WHERE VALUE_NAME = 'MAX_COUNT_OF_CHATS') 
BEGIN 
DELETE FROM Chats WHERE CHATS_ID NOT IN 
(SELECT CHATS_ID 
 FROM Chats 
 ORDER BY LAST_MESSEGES_ADDING DESC 
 LIMIT (SELECT  VALUE_VALUE 
        FROM MetaData 
        WHERE VALUE_NAME = 'MAX_COUNT_OF_CHATS')); 
END;
"""

const val INSERT_CHATS = """
INSERT OR REPLACE INTO Chats
( CHATS_ID, 
  MESSEGES_COUNT, 
  COUNT_OF_MEMBERS, 
  CHATS_OWNER, 
  CHATS_TWINS, 
  CHATS_NAME, 
  AVATAR_ID, 
  CHATS_PROFILE, 
  CHATS_ACCESS, 
  CHATS_TYPE, 
  CHATS_STATUS, 
  ADDING_DATE, 
  BALANCE, 
  LAST_MESSEGES_ADDING, 
  DATE_CLOSED, 
  CHATS_SUBSCRIBE, 
  LAST_UPDATE, 
  AVATAR_LINK, 
  AVATAR_SERVER, 
  AVATAR, 
  CONNECTION_ID, 
  STRING_20) 
VALUES 
(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
"""

const val SELECT_CHATS_ALL = """
SELECT * FROM Chats 
ORDER BY LAST_MESSEGES_ADDING DESC;
"""

const val CLEAR_CHATS = """
DELETE FROM Chats;
"""

/////////////chats cost types///////////////////////////

const val TABLE_CHATS_COST_TYPES = """
CREATE TABLE IF NOT EXISTS ChatsCostTypes 
(CHATS_ID TEXT NOT NULL, 
 COST_ID TEXT NOT NULL, 
 TYPES_OWNER TEXT NOT NULL, 
 AVATAR_ID TEXT, 
 START_TEXT TEXT, 
 HAVE_FULL_TEXT TEXT NOT NULL, 
 TYPES_TYPE TEXT NOT NULL, 
 TYPES_ACCESS TEXT NOT NULL, 
 TYPES_STATUS TEXT NOT NULL, 
 HAVE_MESSEGES TEXT NOT NULL, 
 FULL_TEXT TEXT, 
 AVATAR_LINK TEXT, 
 AVATAR_SERVER TEXT, 
 ORIGINAL_AVATAR_SIZE TEXT, 
 STRING_20 TEXT NOT NULL, 
 AVATAR BLOB, 
 CHECK (length(CHATS_ID) = 18 
 AND length(TYPES_OWNER) = 18 
), 
PRIMARY KEY (CHATS_ID, COST_ID));
"""

const val INSERT_CHATS_COST_TYPES = """
INSERT OR REPLACE INTO ChatsCostTypes 
( CHATS_ID, 
  COST_ID , 
  TYPES_OWNER, 
  AVATAR_ID, 
  START_TEXT, 
  HAVE_FULL_TEXT, 
  TYPES_TYPE, 
  TYPES_ACCESS, 
  TYPES_STATUS, 
  HAVE_MESSEGES, 
  FULL_TEXT, 
  AVATAR_LINK, 
  AVATAR_SERVER, 
  ORIGINAL_AVATAR_SIZE, 
  STRING_20, 
  AVATAR) 
VALUES 
(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
"""

const val SELECT_CHATS_COST_TYPES_ALL = """
SELECT * FROM ChatsCostTypes
ORDER BY  CHATS_ID, COST_ID;
"""


const val CLEAR_CHATS_COST_TYPES = """
DELETE FROM ChatsCostTypes;
"""

/////////////chats likes///////////////////////////

const val TABLE_CHATS_LIKES = """
CREATE TABLE IF NOT EXISTS ChatsLikes 
(CHATS_ID TEXT NOT NULL, 
 ACCOUNTS_ID TEXT NOT NULL, 
 RELATIONS TEXT NOT NULL, 
 CHATS_LIKES_PROFILE TEXT NOT NULL, 
 ADDING_DATE TEXT NOT NULL, 
 IS_ONLINE TEXT NOT NULL, 
 FIRST_MESS_COUNT TEXT NOT NULL, 
 LAST_MESS_COUNT TEXT NOT NULL, 
 LAST_DATE_DELIVERED TEXT NOT NULL, 
 LAST_READED_MESS_ID TEXT NOT NULL, 
 BALANCE INTEGER NOT NULL, 
 LAST_CONNECT TEXT NOT NULL, 
 DATE_DELETE TEXT NOT NULL, 
 ACCOUNTS_NAME TEXT NOT NULL, 
 AVATAR_ID TEXT, 
 ORIGINAL_AVATAR_SIZE TEXT, 
 AVATAR_SERVER TEXT, 
 AVATAR_LINK TEXT, 
 AVATAR BLOB, 
 STRING_20 TEXT NOT NULL, 
 CHECK (length(CHATS_ID) = 18 
 AND length(ACCOUNTS_ID) = 18 
), 
PRIMARY KEY (CHATS_ID, ACCOUNTS_ID));
"""

const val INDEX_CHATSLIKES_AVATARID = """
CREATE INDEX IChatsLikessAvatarId ON ChatsLikes(AVATAR_ID);
"""

const val INDEX_CHATSLIKES_ACCOUNTSID = """
CREATE INDEX IChatsLikessAccountsId ON ChatsLikes(ACCOUNTS_ID);
"""

const val TRIGGER_CHATSLIKES_DELETED_RECORD = """
CREATE TRIGGER TChatsLikesDeletedRecocord 
AFTER UPDATE ON ChatsLikes 
FOR EACH ROW 
WHEN 
new.ACCOUNTS_ID = (SELECT ACCOUNT_ID FROM RegData LIMIT 1) 
AND 
new.RELATIONS = "39" --delete member; 
BEGIN 
DELETE FROM Chats WHERE CHATS_ID = new.CHATS_ID; 
END; 
"""

const val INSERT_CHATS_LIKES = """
INSERT OR REPLACE INTO ChatsLikes 
( CHATS_ID, 
   ACCOUNTS_ID, 
   RELATIONS, 
   CHATS_LIKES_PROFILE, 
   ADDING_DATE, 
   IS_ONLINE, 
   FIRST_MESS_COUNT, 
   LAST_MESS_COUNT, 
   LAST_DATE_DELIVERED, 
   LAST_READED_MESS_ID, 
   BALANCE, 
   LAST_CONNECT, 
   DATE_DELETE, 
   ACCOUNTS_NAME, 
   AVATAR_ID, 
   ORIGINAL_AVATAR_SIZE, 
   AVATAR_SERVER, 
   AVATAR_LINK, 
   AVATAR, 
   STRING_20) 
VALUES 
(?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ? );
"""

const val SELECT_CHATSLIKES_ALL = """
SELECT * FROM ChatsLikes 
ORDER BY CHATS_ID, ACCOUNTS_ID;
"""

/////////////commands///////////////////////////

const val TABLE_COMMANDS = """
CREATE TABLE IF NOT EXISTS Commands 
(COMMANDS_ID INTEGER PRIMARY KEY, 
 COMMANDS_ACCESS TEXT  NOT NULL, 
 COMMANDS_PROFILE TEXT NOT NULL, 
 COMMANDS_NECESSARILY_FIELDS TEXT  NOT NULL, 
 LAST_UPDATE INTEGER NOT NULL, 
 COUNT_OF_EXECUTE INTEGER DEFAULT 0 NOT NULL, 
CHECK ((COMMANDS_ID BETWEEN 1011000001 AND 1011999999) 
AND length(COMMANDS_PROFILE) = 40 
AND length(COMMANDS_NECESSARILY_FIELDS) = 60 
AND length(COMMANDS_ACCESS) = 1 
));
"""

const val INSERT_COMMANDS = """
INSERT OR REPLACE INTO Commands 
(COMMANDS_ID, 
COMMANDS_ACCESS, 
COMMANDS_PROFILE, 
COMMANDS_NECESSARILY_FIELDS, 
LAST_UPDATE, 
COUNT_OF_EXECUTE 
) 
VALUES  
(?, ?, ?, ?, ?, ?);
"""

const val SELECT_COMMANDS_ALL = """
SELECT * FROM Commands;
"""

const val CLEAR_COMMANDS = """
DELETE FROM Commands;
"""

/////////////exceptions///////////////////////////

const val TABLE_EXCEPTION = """
CREATE TABLE IF NOT EXISTS Exception 
(NUMBER_OF_ECXEPTION INTEGER NOT NULL, 
 LANG_OF_ECXEPTION TEXT NOT NULL, 
 TEXT_OF_ECXEPTION TEXT NOT NULL, 
 LAST_UPDATE INTEGER  NOT NULL, 
 PRIMARY KEY (NUMBER_OF_ECXEPTION, LANG_OF_ECXEPTION));
"""

const val INSERT_EXCEPTION = """
INSERT OR REPLACE INTO Exception 
(NUMBER_OF_ECXEPTION, 
 LANG_OF_ECXEPTION, 
 TEXT_OF_ECXEPTION, 
 LAST_UPDATE) 
 VALUES (?, ?, ?, ?);
"""

const val SELECT_EXCEPTION_ALL = """
SELECT * FROM Exception;
"""

const val CLEAR_EXCEPTION = """
DELETE FROM Exception;
"""

/////////////messeges///////////////////////////

const val TABLE_MESSEGES = """
CREATE TABLE IF NOT EXISTS Messeges 
(CHATS_ID TEXT NOT NULL, 
 MESSEGES_COUNT TEXT NOT NULL, 
 ADDING_DATE TEXT NOT NULL, 
 PERIOD_FOR TEXT NOT NULL, 
 MESSEGES_OWNER TEXT NOT NULL, 
 MESSEGES_ANSWER TEXT, 
 MESSEGES_ANSWER_START_TEXT TEXT, 
 MESSEGES_ADRESSER TEXT, 
 OBJECT_ID TEXT, 
 AVATAR_ID TEXT, 
 OBJECT_OWNER TEXT, 
 OBJECT_TYPE TEXT NOT NULL, 
 START_TEXT TEXT, 
 MESSEGES_COST INTEGER, 
 COST_TYPE INTEGER, 
 MESSEGES_TYPE TEXT NOT NULL, 
 MESSEGES_ACCESS TEXT NOT NULL, 
 MESSEGES_STATUS TEXT NOT NULL, 
 HAVE_FULL_TEXT TEXT NOT NULL, 
 HAVE_ANSWER TEXT NOT NULL, 
 LAST_CHANGED TEXT DEFAULT "0" NOT NULL, 
 FULL_TEXT TEXT, 
 OBJECT_NAME TEXT, 
 OBJECT_SERVER TEXT, 
 OBJECT_PROFILE_STRING TEXT, 
 OBJECT_LINK TEXT, 
 OBJECT_EXTENSION TEXT, 
 AVATAR_LINK TEXT, 
 AVATAR_SERVER TEXT, 
 ORIGINAL_AVATAR_SIZE TEXT, 
 MESSEGES_AVATAR BLOB, 
 OBJECT_AVATAR BLOB, 
 ANSWER_OBJECT_AVATAR BLOB, 
 STRING_20 TEXT, 
 CHECK (length(CHATS_ID) = 18 
 AND length(MESSEGES_OWNER) = 18 
), 
PRIMARY KEY (CHATS_ID, MESSEGES_COUNT));
"""

const val INDEX_MESSEGES_ORDER_DESC = """
CREATE INDEX IMessegesOrderDesc ON Messeges(CHATS_ID, MESSEGES_COUNT DESC);
"""

const val TRIGGER_MESSEGES_CONTROL_COUNT = """
CREATE TRIGGER TControlCountsMesseges 
AFTER INSERT ON Messeges 
FOR EACH ROW 
WHEN 
(SELECT count(*) FROM Messeges WHERE CHATS_ID = new.CHATS_ID)> 
(SELECT  VALUE_VALUE FROM MetaData WHERE VALUE_NAME = 'MAX_COUNT_OF_MESSEGES') 
BEGIN 
  DELETE FROM Messeges 
  WHERE CHATS_ID = new.CHATS_ID 
  AND MESSEGES_COUNT < 
  (SELECT min(MESSEGES_COUNT) 
   FROM Messeges 
   WHERE CHATS_ID = new.CHATS_ID 
   ORDER BY CHATS_ID, MESSEGES_COUNT DESC 
   LIMIT (SELECT  VALUE_VALUE 
          FROM MetaData 
          WHERE VALUE_NAME = 'MAX_COUNT_OF_MESSEGES')); 
END;
"""

const val TRIGGER_MESSEGES_MESS_WITHOUT_CAHTS = """
CREATE TRIGGER TControlMessWithOutChats 
AFTER INSERT ON Messeges 
BEGIN 
  DELETE FROM Messeges 
  WHERE CHATS_ID NOT IN (SELECT CHATS_ID FROM Chats); 
END;
"""


const val INSERT_MESSEGES = """
INSERT OR REPLACE INTO Messeges 
( CHATS_ID, 
  MESSEGES_COUNT, 
  ADDING_DATE, 
  PERIOD_FOR, 
  MESSEGES_OWNER, 
  MESSEGES_ANSWER, 
  MESSEGES_ANSWER_START_TEXT, 
  MESSEGES_ADRESSER, 
  OBJECT_ID, 
  AVATAR_ID, 
  OBJECT_OWNER, 
  OBJECT_TYPE, 
  START_TEXT, 
  MESSEGES_COST, 
  COST_TYPE, 
  MESSEGES_TYPE, 
  MESSEGES_ACCESS, 
  MESSEGES_STATUS, 
  HAVE_FULL_TEXT, 
  HAVE_ANSWER, 
  LAST_CHANGED, 
  FULL_TEXT, 
  OBJECT_NAME, 
  OBJECT_SERVER, 
  OBJECT_PROFILE_STRING, 
  OBJECT_LINK, 
  OBJECT_EXTENSION, 
  AVATAR_LINK, 
  OBJECT_SERVER, 
  AVATAR_SERVER, 
  ORIGINAL_AVATAR_SIZE, 
  MESSEGES_AVATAR, 
  OBJECT_AVATAR, 
  ANSWER_OBJECT_AVATAR, 
  STRING_20) 
VALUES  
( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
"""


const val SELECT_MESSEGES_LIMIT  = """
SELECT * 
FROM Messeges 
WHERE CHATS_ID = ? 
AND MESSEGES_COUNT < ? 
ORDER BY CHATS_ID, MESSEGES_COUNT DESC 
LIMIT (SELECT VALUE_VALUE 
        FROM   MetaData 
        WHERE  VALUE_NAME = 'LIMIT_FOR_SELECT');
"""

const val CLEAR_MESSEGES = """
DELETE FROM Messeges;
"""

/////////////meta data///////////////////////////

const val TABLE_METADATA = """
CREATE TABLE IF NOT EXISTS MetaData 
(VALUE_NAME TEXT PRIMARY KEY, 
 VALUE_VALUE INTEGER  NOT NULL, 
 LAST_UPDATE INTEGER  NOT NULL  
);
"""

const val INSERT_METADATA = """
INSERT OR REPLACE INTO MetaData 
(VALUE_NAME, 
 VALUE_VALUE, 
 LAST_UPDATE) VALUES (?, ?, ?);
"""

const val CLEAR_METADATA = """
DELETE FROM MetaData;
"""

const val SELECT_ALL_METADATA = """
SELECT * FROM MetaData;
"""

/////////////reg data///////////////////////////

const val TABLE_REGDATA = """
CREATE TABLE IF NOT EXISTS RegData(
CONNECTION_ID INTEGER PRIMARY KEY,           --LONG_4
CONNECTION_COOCKI TEXT NOT NULL,             --LONG_5
ACCOUNT_ID TEXT DEFAULT "" NOT NULL,         --IDENTIFICATOR_1
ACCOUNT_NAME TEXT DEFAULT "" NOT NULL,       --STRING_1
ACCOUNT_ACCESS TEXT DEFAULT "" NOT NULL,     --STRING_2
ACCOUNT_PROFILE TEXT DEFAULT "" NOT NULL,    --STRING_5
REQUEST_PROFILE TEXT DEFAULT "" NOT NULL,    --STRING_6
LANG TEXT DEFAULT 'ENG' NOT NULL,            --STRING_7
AVATAR_ID TEXT DEFAULT "" NOT NULL,          --IDENTIFICATOR_2
ORIGINAL_AVATAR_SIZE TEXT,                   --STRING_17
AVATAR_SERVER TEXT,                          --STRING_16
AVATAR_LINK TEXT,                            --STRING_18
BALANCE_OF_CHATS INTEGER DEFAULT 0 NOT NULL, --LONG_3
LAST_UPDATE INTEGER DEFAULT 0 NOT NULL,      --LONG_1
AVATAR_1 BLOB,
AVATAR_2 BLOB,
AVATAR_3 BLOB,
STRING_20 TEXT,

CHECK (
 length(CONNECTION_ID) = 18
 AND length(CONNECTION_COOCKI) = 18
 AND (ACCOUNT_ID = "" OR length(ACCOUNT_ID)= 18)
 AND (AVATAR_ID = "" OR length(AVATAR_ID)= 18)
 AND (AVATAR_LINK = "" OR length(AVATAR_LINK)= 18)
 AND length(LANG) = 3
 AND (ACCOUNT_PROFILE = "" OR length(ACCOUNT_PROFILE)= 40)
 AND (REQUEST_PROFILE = "" OR length(REQUEST_PROFILE)= 30)));
"""

const val TRIGGER_REGDATA_INSERT  = """
CREATE TRIGGER TRegData_Insert 
 AFTER INSERT ON RegData 
 BEGIN 
 DELETE FROM RegData 
 WHERE CONNECTION_ID != new.CONNECTION_ID; 
END;
"""

const val TRIGGER_REGDATA_DELETE= """
CREATE TRIGGER TRegData_Delete 
 AFTER DELETE ON RegData 
 FOR EACH ROW 
 BEGIN 
 DELETE FROM Chats; 
 DELETE FROM CashLastUpdate; 
END;
"""

const val INSERT_REGDATA = """
INSERT OR REPLACE INTO RegData
(CONNECTION_ID,
 CONNECTION_COOCKI,
 ACCOUNT_ID,
 ACCOUNT_NAME,
 ACCOUNT_ACCESS,
 ACCOUNT_PROFILE,
 REQUEST_PROFILE,
 LANG,
 AVATAR_ID,
 ORIGINAL_AVATAR_SIZE,
 AVATAR_SERVER,
 AVATAR_LINK,
 BALANCE_OF_CHATS,
 LAST_UPDATE,
 AVATAR_1,
 AVATAR_2,
 AVATAR_3,
 STRING_20)
VALUES 
(?, ?, ?,?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
"""

const val CLEAR_REGDATA = """
DELETE FROM RegData;
"""

const val SELECT_REGDATA_ALL = """
SELECT * FROM RegData LIMIT 1;
"""
/////////////save media///////////////////////////

const val TABLE_SAVEMEDIA = """
CREATE TABLE IF NOT EXISTS SaveMedia
(OBJECT_ID TEXT PRIMARY KEY,
 CONNECTION_ID INTEGER NOT NULL,
 AVATAR_ID TEXT,
 OBJECT_NAME TEXT,
 OBJECT_SIZE INTEGER,
 OBJECT_LENGTH_SECONDS INTEGER,
 OBJECT_SERVER TEXT,
 OBJECT_LINK TEXT,
 OBJECT_EXTENSION TEXT,
 AVATAR_LINK TEXT,
 AVATAR_SERVER TEXT,
 ORIGINAL_AVATAR_SIZE INTEGER,
 SMALL_AVATAR BLOB,
 BIG_AVATAR BLOB,
 IS_TEMP INTEGER NOT NULL,
 LAST_USED INTEGER NOT NULL,
 CHECK (length(CONNECTION_ID) = 18
 AND length(OBJECT_ID) = 18
 AND (IS_TEMP BETWEEN 0 AND 1)));
"""

const val INDEX_SAVEMEDIA_CONNECTIONID = """
CREATE INDEX ISaveMediaConnectionId ON SaveMedia(CONNECTION_ID);
"""

const val INDEX_SAVEMEDIA_LASTUSED = """
CREATE INDEX ISaveMediaLastUsed ON SaveMedia(LAST_USED, OBJECT_ID);
"""

const val INDEX_SAVEMEDIA_ISTEMP = """
CREATE INDEX ISaveMediaIsTemp ON SaveMedia(IS_TEMP, OBJECT_ID);
"""

const val INDEX_SAVEMEDIA_AVATARID = """
CREATE INDEX ISaveMediaAvatarId ON SaveMedia(AVATAR_ID);
"""

const val TRIGGER_SAVEMEDIA_CONTROL_TEMP_COUNT = """
CREATE TRIGGER TSaveMediaControlTempCounts 
AFTER INSERT ON SaveMedia 
FOR EACH ROW 
WHEN 
new.IS_TEMP = 1 
AND 
(SELECT count(*) 
 FROM SaveMedia 
 WHERE IS_TEMP = 1)> 
(SELECT  VALUE_VALUE 
 FROM MetaData 
 WHERE VALUE_NAME = 'MAX_COUNT_OF_TEMP_SAVEMEDIA') 
BEGIN 
DELETE FROM SaveMedia WHERE OBJECT_ID NOT IN 
(SELECT OBJECT_ID FROM SaveMedia 
 WHERE IS_TEMP = 1 
 ORDER BY LAST_USED 
 LIMIT (SELECT VALUE_VALUE 
        FROM  MetaData 
        WHERE VALUE_NAME = 'MAX_COUNT_OF_TEMP_SAVEMEDIA')) 
 AND IS_TEMP = 1; 
END;
"""

const val TRIGGER_SAVEMEDIA_CONTROL_COUNT = """
CREATE TRIGGER TSaveMediaControlCounts 
AFTER INSERT ON SaveMedia 
FOR EACH ROW 
WHEN 
new.IS_TEMP = 0 
AND 
(SELECT count(*) 
 FROM SaveMedia 
 WHERE IS_TEMP = 0)> 
(SELECT  VALUE_VALUE 
 FROM MetaData 
 WHERE VALUE_NAME = 'MAX_COUNT_OF_SAVEMEDIA') 
BEGIN 
DELETE FROM SaveMedia WHERE OBJECT_ID NOT IN 
(SELECT OBJECT_ID FROM SaveMedia 
 WHERE IS_TEMP = 0 
 ORDER BY LAST_USED 
 LIMIT (SELECT VALUE_VALUE 
        FROM  MetaData 
        WHERE VALUE_NAME = 'MAX_COUNT_OF_SAVEMEDIA')) 
 AND IS_TEMP = 0; 
END;
"""

const val INSERT_SAVEMEDIA = """
INSERT OR REPLACE INTO SaveMedia
(OBJECT_ID,
  CONNECTION_ID,
  AVATAR_ID,
  OBJECT_NAME,
  OBJECT_SIZE,
  OBJECT_LENGTH_SECONDS,
  OBJECT_SERVER,
  OBJECT_LINK,
  OBJECT_EXTENSION,
  AVATAR_LINK,
  AVATAR_SERVER,
  ORIGINAL_AVATAR_SIZE,
  SMALL_AVATAR,
  BIG_AVATAR,
  IS_TEMP,
  LAST_USED)
VALUES 
(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
"""

const val SELECT_SAVEMEDIA_ALL = """
SELECT * 
FROM SaveMedia;
"""

const val DELETE_SAVEMEDIA = """
DELETE FROM SaveMedia 
WHERE OBJECT_ID = ?;
"""

