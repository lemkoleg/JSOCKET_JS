/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("DuplicatedCode")
package sql

import p_client.dbLocalName



/**
 *
 * @author User
 */


/////////////////////////////////////////////////////////////////////////////////////////////////////
val table_metadata by lazy { alaSQL.compile(TABLE_METADATA, dbLocalName)}

val insert_metadata by lazy { alaSQL.compile(INSERT_METADATA, dbLocalName)}

val select_metadata by lazy { alaSQL.compile(SELECT_METADATA)}

/////////////////////////////////////////////////////////////////////////////////////////////////////

val insert_cashdata by lazy { alaSQL.compile(INSERT_CASHDATA)}

val update_cashdata by lazy { alaSQL.compile(UPDATE_CASHDATA)}

val select_cashdata by lazy { alaSQL.compile(SELECT_CASHDATA)}

val select_all_cashdata by lazy { alaSQL.compile(SELECT_ALL_CASHDATA)}

val select_count_cashdata by lazy { alaSQL.compile(SELECT_COUNT_CASHDATA)}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

val insert_regdata by lazy { alaSQL.compile(INSERT_REGDATA)}

val select_all_regdata by lazy { alaSQL.compile(SELECT_ALL_REGDATA)}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
val insert_commands by lazy { alaSQL.compile(INSERT_COMMANDS)}

val select_all_commands by lazy { alaSQL.compile(SELECT_ALL_COMMANDS)}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
val insert_savemedia by lazy { alaSQL.compile(INSERT_SAVEMEDIA)}

val delete_savemedia by lazy { alaSQL.compile(DELETE_SAVEMEDIA)}

val select_all_savemedia by lazy { alaSQL.compile(SELECT_ALL_SAVEMEDIA)}

val select_count_savemedia by lazy { alaSQL.compile(SELECT_COUNT_SAVEMEDIA)}

val select_last_savemedia by lazy { alaSQL.compile(SELECT_LAST_SAVEMEDIA)}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
val insert_big_avatars by lazy { alaSQL.compile(INSERT_BIG_AVATARS)}

val select_big_avatars by lazy { alaSQL.compile(SELECT_BIG_AVATARS)}

val select_count_big_avatars by lazy { alaSQL.compile(SELECT_COUNT_BIG_AVATARS)}

val select_last_big_avatars by lazy { alaSQL.compile(SELECT_LAST_BIG_AVATARS)}

val delete_big_avatars by lazy { alaSQL.compile(DELETE_BIG_AVATARS)}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

val insert_sendmedia by lazy { alaSQL.compile(INSERT_SENDMEDIA)}

val delete_sendmedia by lazy { alaSQL.compile(DELETE_SENDMEDIA)}

val delete_all_sendmedia by lazy { alaSQL.compile(DELETE_ALL_SENDMEDIA)}

val select_all_sendmedia by lazy { alaSQL.compile(SELECT_ALL_SENDMEDIA)}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

val insert_lastupdate by lazy { alaSQL.compile(INSERT_LASTUPDATE)}

val update_last_use_lastupdate by lazy { alaSQL.compile(UPDATE_LAST_USE_LASTUPDATE)}

val select_lastupdate by lazy { alaSQL.compile(SELECT_LASTUPDATE)}

val delete_lastupdate by lazy { alaSQL.compile(DELETE_LASTUPDATE)}

val select_last_lastupdate by lazy { alaSQL.compile(SELECT_LAST_LASTUPDATE)}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

val insert_chats by lazy { alaSQL.compile(INSERT_CHATS)}

val select_count_chats by lazy { alaSQL.compile(SELECT_COUNT_CHATS)}

val select_chat by lazy { alaSQL.compile(SELECT_CHAT)}

val select_all_chats by lazy { alaSQL.compile(SELECT_ALL_CHATS)}

val select_last_chats by lazy { alaSQL.compile(SELECT_LAST_CHATS)}

val delete_chat by lazy { alaSQL.compile(DELETE_CHAT)}

/////////////////////////////////////////////////////////////////////////
val insert_messeges by lazy { alaSQL.compile(INSERT_MESSEGES)}

val select_count_messeges by lazy { alaSQL.compile(SELECT_COUNT_MESSEGES)}

val select_messeges by lazy { alaSQL.compile(SELECT_MESSEGES)}

val select_last_messeges by lazy { alaSQL.compile(SELECT_LAST_MESSEGES)}

val delete_messege by lazy { alaSQL.compile(DELETE_MESSEGE)}

