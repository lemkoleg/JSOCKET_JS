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
(CASH_SUM TEXT NOT NULL, 
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
 INTEGER_20 INTEGER NOT NULL DEFAULT 1,  
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
 LONG_20 INTEGER NOT NULL, 
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
 STRING_20 TEXT NOT NULL, 
 BLOB_1 BLOB , 
 BLOB_2 BLOB , 
 BLOB_3 BLOB, 
 INTEGER_20_LEVEL INTEGER NOT NULL DEFAULT 0,  
 NEXT_RECORD_TABLE_ID TEXT NOT NULL DEFAULT "",  
 PRIMARY KEY (CASH_SUM, RECORD_TABLE_ID) 
 );
"""

const val INDEX_CASHDATA_NUMBER_POSITION_ASC = """
CREATE INDEX CashData_NumberPositionAsc ON CashData(CASH_SUM, INTEGER_20 ASC, INTEGER_20_LEVEL ASC, RECORD_TABLE_ID);
"""

const val TRIGGER_CASHDATA_AFTER_INSERT = """
 CREATE TRIGGER IF NOT EXISTS TCashDataAfterInsert 
  AFTER INSERT ON CashData 
  FOR EACH ROW 
  WHEN EXISTS (SELECT c.RECORD_TABLE_ID 
               FROM   CashData AS c 
               WHERE  c.CASH_SUM = new.CASH_SUM 
               ORDER BY c.CASH_SUM, c.INTEGER_20 ASC, c.INTEGER_20_LEVEL ASC 
               LIMIT 1 OFFSET new.INTEGER_20 - 1) 
  AND new.RECORD_TABLE_ID != (SELECT c.RECORD_TABLE_ID 
                              FROM   CashData AS c 
                              WHERE  c.CASH_SUM = new.CASH_SUM 
                              ORDER BY c.CASH_SUM, c.INTEGER_20 ASC, c.INTEGER_20_LEVEL ASC 
                              LIMIT 1 OFFSET new.INTEGER_20 - 1) 
  BEGIN 
    WITH tab (record_id, integer_20, integer_20_level) 
    AS (SELECT c1.RECORD_TABLE_ID, 
               c1.INTEGER_20, 
               c1.INTEGER_20_LEVEL 
        FROM CashData AS c1 
        WHERE c1.CASH_SUM = new.CASH_SUM 
        ORDER BY c1.CASH_SUM, 
                 c1.INTEGER_20 ASC, 
                 c1.INTEGER_20_LEVEL ASC 
        LIMIT 1 OFFSET new.INTEGER_20 - 1) 

   UPDATE CashData 
     SET    NEXT_RECORD_TABLE_ID = (SELECT record_id FROM tab LIMIT 1), 
            INTEGER_20 = (SELECT integer_20 FROM tab LIMIT 1), 
            INTEGER_20_LEVEL = (SELECT integer_20_level FROM tab LIMIT 1) 
     WHERE  CASH_SUM = new.CASH_SUM 
     AND    RECORD_TABLE_ID = new.RECORD_TABLE_ID; 

   WITH tab2 (record_id, next_record_id, integer_20_level) 
       AS (SELECT c2.RECORD_TABLE_ID, 
                  c2.NEXT_RECORD_TABLE_ID, 
                  c2.INTEGER_20_LEVEL 
           FROM CashData AS c2 
           WHERE c2.CASH_SUM = new.CASH_SUM 
           AND   c2.RECORD_TABLE_ID = new.RECORD_TABLE_ID) 

   UPDATE CashData 
       SET    INTEGER_20_LEVEL = ifnull((SELECT integer_20_level FROM tab2 LIMIT 1), 0) + 1 
       WHERE  CASH_SUM = new.CASH_SUM 
       AND    RECORD_TABLE_ID = (SELECT next_record_id  FROM tab2 LIMIT 1); 

  END; 
"""

const val TRIGGER_CASHDATA_AFTER_UPDATE = """
  CREATE TRIGGER IF NOT EXISTS TCashDataAfterUpdate 
  AFTER UPDATE ON CashData 
  FOR EACH ROW 
  WHEN new.LONG_20 != old.LONG_20 
  AND substr(new.STRING_20, 7,1) = '1' 
  BEGIN 
    DELETE 
    FROM  CashData 
    WHERE rowid = new.rowid; 
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
  BLOB_3) 
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
 ?, ?, ?, ?, ? , ? , ? , ? , ?
 , ? , ? , ? , ? , ? , ? , ? , ?
 , ? , ? , ? , ? , ? , ? , ? , ? , ?
 , ? , ? , ? , ? , ? , ? , ? , ? , ?
 , ? , ? , ?);
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

const val SELECT_CASHDATA_CHUNK_ON_CASH_SUM = """
SELECT  * 
   FROM CashData AS c 
   WHERE c.CASH_SUM = ? 
   ORDER BY c.CASH_SUM, 
            c.INTEGER_20 ASC, 
            c.INTEGER_20_LEVEL ASC 
   LIMIT (SELECT  VALUE_VALUE AS val 
          FROM MetaData 
          WHERE VALUE_NAME = 'LIMIT_FOR_SELECT') 
   OFFSET 0 + ifnull((SELECT tab1.RowNumber FROM (SELECT row_number() OVER ( 
                                                          ORDER BY c1.CASH_SUM, c1.INTEGER_20 ASC, c1.INTEGER_20_LEVEL ASC 
                                                           ) AS RowNumber, 
                                                         c1.RECORD_TABLE_ID AS record_id 
                                                  FROM CashData AS c1 
                                                  WHERE c1.CASH_SUM = ? 
                                                  ORDER BY c1.CASH_SUM, 
                                                           c1.RECORD_TABLE_ID) AS tab1 
                                WHERE record_id = ?), 0); 
"""

const val SELECT_CASHDATA = """
SELECT  * 
FROM CashData 
WHERE CASH_SUM = ? 
AND RECORD_TABLE_ID = ?; 
"""

const val CLEAR_CASHDATA = """
DELETE FROM CashData;
"""


const val CASHDATA_SORT_NEW_NUMBER_POSITIONS = """
 WITH tab AS (SELECT  c.CASH_SUM AS cash_sum, 
                       c.RECORD_TABLE_ID AS record_id, 
                       row_number() OVER ( 
                           ORDER BY CASH_SUM, INTEGER_20 ASC, INTEGER_20_LEVEL ASC 
                       ) AS RowNumber 
              FROM     CashData AS c 
              WHERE     c.CASH_SUM = ? 
             ORDER BY   c.CASH_SUM, 
                        c.RECORD_TABLE_ID) 

  UPDATE CashData 
  SET   INTEGER_20 = (SELECT t.RowNumber 
                      FROM tab AS t 
                      WHERE t.cash_sum = ? 
                      AND   t.record_id = RECORD_TABLE_ID), 
        INTEGER_20_LEVEL = 0, 
        NEXT_RECORD_TABLE_ID = "" 
        WHERE CASH_SUM = ?; 
"""

const val UPADTE_CASHDATA_NEW_LAST_SELECT = """
       UPDATE CashData 
       SET    LONG_20 = ? 
       WHERE  CASH_SUM = ? 
       AND    RECORD_TABLE_ID IN (SELECT c.RECORD_TABLE_ID 
                                  FROM   CashData AS c 
                                  WHERE  c.CASH_SUM = ? 
                                  ORDER BY c.CASH_SUM, 
                                           c.INTEGER_20 ASC, 
                                           c.INTEGER_20_LEVEL ASC 
                                  LIMIT ?
                                  OFFSET 0 + ifnull((SELECT tab1.RowNumber 
                                                  FROM (SELECT c1.RECORD_TABLE_ID AS record_id, 
                                                               row_number() OVER ( 
                                                                          ORDER BY c1.CASH_SUM, c1.INTEGER_20 ASC, c1.INTEGER_20_LEVEL ASC 
                                                                ) AS RowNumber 
                                                         FROM   CashData AS c1 
                                                         WHERE  c1.CASH_SUM = ? 
                                                         ORDER BY c1.RECORD_TABLE_ID) AS tab1 
                                                  WHERE  tab1.record_id = ?), 0) 
                                  ); 
"""


const val DELETE_CASHDATA_DELETED_RECORD = """
DELETE
FROM CashData
WHERE  CASH_SUM = ?
AND substr(STRING_20, 7, 1) = "1";
"""


/////////////last update///////////////////////////

const val TABLE_CASHLASTUPDATE  = """
CREATE TABLE IF NOT EXISTS CashLastUpdate 
(CASH_SUM TEXT NOT NULL, 
 RECORDS_TYPE TEXT NOT NULL, 
 COURSE TEXT NOT NULL DEFAULT "0", 
 LAST_USE TEXT NOT NULL, 
 PRIMARY KEY (CASH_SUM) 
);
"""


const val INDEX_CASHLASTUPDATE_LAST_USE = """
CREATE INDEX IF NOT EXISTS ICashLastUpdateLastUse ON CashLastUpdate(LAST_USE DESC);
"""

const val INDEX_CASHLASTUPDATE_RECORD_TYPE_CASH_SUM = """
CREATE INDEX IF NOT EXISTS ICashLastUpdateRecordTypeCashSum ON CashLastUpdate(RECORDS_TYPE, CASH_SUM);
"""

const val INDEX_CASHLASTUPDATE_RECORD_TYPE_LAST_USE_CASH_SUM = """
CREATE INDEX IF NOT EXISTS ICashLastUpdateRecordTypeLastUseCashSum ON CashLastUpdate(RECORDS_TYPE, LAST_USE DESC, CASH_SUM);
"""

const val TRIGGER_CASHLASTUPDATE_CONTROL_EMPTY_BLOCKS_INSERT = """
CREATE TRIGGER IF NOT EXISTS TCashLastUpdate_Control_Empty_Blocks_Insert 
AFTER INSERT 
ON CashLastUpdate 
WHEN ((SELECT count(*) FROM CashData t WHERE t.CASH_SUM = new.CASH_SUM LIMIT 1) = 0) 
BEGIN 
DELETE FROM CashLastUpdate
WHERE rowid = new.rowid; 
END; 
"""

const val TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_LINKS_INSERT = """
CREATE TRIGGER IF NOT EXISTS TCashLastUpdateControlCountLinksInsert 
AFTER INSERT 
ON CashLastUpdate 
WHEN ((SELECT count(*) FROM CashData t WHERE t.CASH_SUM = new.CASH_SUM LIMIT 1) > 0) 
AND new.RECORDS_TYPE IN ('B', 'D', 'F', 'H', 'I', 'J', 'K', 'L', 'A', 'C', 'E', 'G', 'M') 
BEGIN 

  DELETE FROM CashLastUpdate 
  WHERE CASH_SUM IN (SELECT t1.CASH_SUM 
                     FROM CashLastUpdate t1 
                     WHERE CASE 
                             WHEN new.RECORDS_TYPE IN ('B', 'D', 'F', 'H', 'I')    
                               THEN 
                                  CASE 
                                    WHEN t1.RECORDS_TYPE IN ('B', 'D', 'F', 'H', 'I') 
                                      THEN 1 
                                    ELSE 0 
                                  END 
                             WHEN new.RECORDS_TYPE IN ('J', 'K', 'L')  -- object_info; 
                                THEN 
                                   CASE 
                                      WHEN t1.RECORDS_TYPE IN ('J', 'K', 'L') 
                                         THEN 1 
                                      ELSE 0 
                                  END 

                             WHEN new.RECORDS_TYPE IN ('A', 'C', 'E', 'G', 'M')  
                                   THEN 
                                      CASE 
                                         WHEN t1.RECORDS_TYPE IN ('A', 'C', 'E', 'G', 'M') 
                                            THEN 1 
                                          ELSE 0 
                                    END 

                             ELSE 0 
                           END 
                     ORDER BY t1.LAST_USE DESC 
                     LIMIT 100 00 OFFSET (SELECT  VALUE_VALUE 
                                          FROM MetaData 
                                          WHERE VALUE_NAME = CASE 
                                                              WHEN new.RECORDS_TYPE IN ('B', 'D', 'F', 'H', 'I') 
                                                                  THEN 'MAX_COUNT_OF_CASHDATA_OF_LINKS' 
                                                               WHEN new.RECORDS_TYPE IN ('J', 'K', 'L')  
                                                                  THEN 'MAX_COUNT_OF_CASHDATA_OF_OBJECT_INFO' 
                                                               WHEN new.RECORDS_TYPE IN ('A', 'C', 'E', 'G', 'M') 
                                                                  THEN 'MAX_COUNT_OF_CASHDATA_OF_TEXT_LISTS' 
                                                              ELSE 
                                                                 '' 
                                                            END)); 

  DELETE FROM CashData 
  WHERE CASH_SUM NOT IN (SELECT t2.CASH_SUM FROM CashLastUpdate AS t2); 


  DELETE FROM CashData 
  WHERE CASH_SUM = new.CASH_SUM 
  AND   RECORD_TABLE_ID IN ( 
        SELECT t3.RECORD_TABLE_ID 
        FROM CashData AS t3 
        WHERE t3.CASH_SUM = new.CASH_SUM 
        ORDER BY t3.INTEGER_20 ASC, t3.INTEGER_20_LEVEL ASC 
        LIMIT 100 00 OFFSET (SELECT  VALUE_VALUE 
                             FROM MetaData 
                             WHERE VALUE_NAME = CASE 
                                                  WHEN new.RECORDS_TYPE IN ('B', 'D', 'F', 'H', 'I') 
                                                     THEN 'MAX_COUNT_OF_CASHDATA_BLOCKS_OF_LINKS' 
                                                  WHEN new.RECORDS_TYPE IN ('J', 'K', 'L')   
                                                     THEN 'MAX_COUNT_OF_CASHDATA_BLOCKS_OF_OBJECT_INFO' 
                                                  WHEN new.RECORDS_TYPE IN ('A', 'C', 'E', 'G') 
                                                     THEN 'MAX_COUNT_OF_CASHDATA_BLOCKS_OF_TEXT_LISTS' 
                                                  ELSE 
                                                    '' 
                                               END) 
  ); 

END; 
"""

const val TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_CHATS_INSERT = """
CREATE TRIGGER IF NOT EXISTS TCashLastUpdateControlCountChatsInsert 
AFTER INSERT 
ON CashLastUpdate 
WHEN ((SELECT count(*) FROM CashData t WHERE t.CASH_SUM = new.CASH_SUM LIMIT 1) > 0) 
AND new.RECORDS_TYPE = '3' 
AND 1 == 0 -- not use/ all messegess data save;
BEGIN 

  DELETE FROM CashData 
  WHERE CASH_SUM = new.CASH_SUM 
  AND RECORD_TABLE_ID IN ( 
        SELECT t.RECORD_TABLE_ID 
        FROM CashData AS t 
        WHERE t.CASH_SUM = new.CASH_SUM 
        ORDER BY t.INTEGER_20 ASC, t.INTEGER_20_LEVEL ASC 
        LIMIT 10000 OFFSET (SELECT  VALUE_VALUE 
                            FROM MetaData 
                            WHERE VALUE_NAME =  'MAX_COUNT_OF_CASHDATA_BLOCKS_OF_CHATS') 

  ); 

  DELETE FROM CashLastUpdate 
  WHERE  RECORDS_TYPE = '4' 
  AND CASH_SUM NOT IN ( 
          SELECT t1.RECORD_TABLE_ID||'4' 
          FROM CashData AS t1 
          WHERE t1.CASH_SUM = new.CASH_SUM); 

  DELETE FROM CashData 
  WHERE CASH_SUM NOT IN (SELECT t2.CASH_SUM FROM CashLastUpdate AS t2); 

END; 
"""

const val TRIGGER_CASHLASTUPDATE_CONTROL_COUNT_MESS_INSERT = """
CREATE TRIGGER IF NOT EXISTS TCashLastUpdateControlCountMessInsert 
AFTER INSERT 
ON CashLastUpdate 
WHEN ((SELECT count(*) FROM CashData t WHERE t.CASH_SUM = new.CASH_SUM LIMIT 1) > 0) 
AND new.RECORDS_TYPE = '4' 
AND 1 == 0 -- not use/ all messegess data save;
BEGIN 

  DELETE FROM CashData 
  WHERE CASH_SUM = new.CASH_SUM 
  AND RECORD_TABLE_ID IN ( 
        SELECT t.RECORD_TABLE_ID 
        FROM CashData AS t 
        WHERE t.CASH_SUM = new.CASH_SUM 
        ORDER BY t.INTEGER_20 ASC, t.INTEGER_20_LEVEL ASC 
        LIMIT 10000 OFFSET (SELECT  VALUE_VALUE 
                            FROM MetaData 
                            WHERE VALUE_NAME =  'MAX_COUNT_OF_CASHDATA_BLOCKS_OF_MESSEGES') 

  ); 

END; 
"""

const val INSERT_CASHLASTUPDATE = """
INSERT OR REPLACE INTO CashLastUpdate 
(CASH_SUM, RECORDS_TYPE, COURSE, LAST_USE) 
VALUES 
(?, ?, ?, ?); 
"""

const val SELECT_CASHLASTUPDATE_ALL = """
SELECT * FROM CashLastUpdate;
"""

const val CLEAR_LASTUPDATE = """
DELETE FROM CashLastUpdate;
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
CONNECTION_ID INTEGER PRIMARY KEY,  
CONNECTION_COOCKI TEXT NOT NULL, 
ACCOUNT_ID TEXT DEFAULT "" NOT NULL, 
ACCOUNT_NAME TEXT DEFAULT "" NOT NULL, 
ACCOUNT_ACCESS TEXT DEFAULT "" NOT NULL, 
ACCOUNT_PROFILE TEXT DEFAULT "" NOT NULL, 
REQUEST_PROFILE TEXT DEFAULT "" NOT NULL, 
LANG TEXT DEFAULT 'ENG' NOT NULL, 
AVATAR_ID TEXT DEFAULT "" NOT NULL, 
ORIGINAL_AVATAR_SIZE TEXT, 
AVATAR_SERVER TEXT, 
AVATAR_LINK TEXT, 
BALANCE_OF_CHATS INTEGER DEFAULT 0 NOT NULL, 
LAST_UPDATE INTEGER DEFAULT 0 NOT NULL,  
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
FROM SaveMedia 
WHERE CONNECTION_ID = ?;
"""

const val DELETE_SAVEMEDIA = """
DELETE FROM SaveMedia 
WHERE OBJECT_ID = ?;
"""

