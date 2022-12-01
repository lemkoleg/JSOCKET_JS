/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("DuplicatedCode")
package sql

import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import p_jsocket.Constants.dbLocalName
import kotlin.time.ExperimentalTime


/**
 *
 * @author User
 */


///////////////////////////////////big avatars///////////////////////////
@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val table_big_avatars by lazy { alaSQL.compile(TABLE_BIG_AVATARS, dbLocalName)}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val index_big_avatars_last_use by lazy { alaSQL.compile(INDEX_BIG_AVATARS_LAST_USE, dbLocalName)}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val trigger_big_avatars_control_count by lazy { alaSQL.compile(INDEX_BIG_AVATARS_LAST_USE, dbLocalName)}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val insert_big_avatars by lazy { alaSQL.compile(INSERT_BIG_AVATARS, dbLocalName)}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val select_big_avatar by lazy { alaSQL.compile(SELECT_BIG_AVATAR, dbLocalName)}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val select_bigavatars_all_id by lazy { alaSQL.compile(SELECT_BIGAVATARS_ALL_ID, dbLocalName)}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val select_bigavatars_all by lazy { alaSQL.compile(SELECT_BIGAVATARS_ALL, dbLocalName)}


