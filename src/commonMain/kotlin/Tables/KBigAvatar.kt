@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")

/*
 * To change answerType license header, choose License Headers in Project Properties.
 * To change answerType template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables

import com.soywiz.klock.DateTime
import io.ktor.util.*
import p_client.Jsocket
import sql.SELECT_BIG_AVATARS
import kotlin.js.JsName

/**
 *
 * @author User
 */

val BIG_AVATARS: MutableMap<String, KBigAvatar> = mutableMapOf()

@InternalAPI
private val lock = Lock()

@JsName("BigAvatar")
open class KBigAvatar {

    private var AVATAR_ID: String = ""
    private var LAST_USE: Long = 0L
    private var AVATAR: ByteArray? = null

    private constructor()

    constructor(L_AVATAR_ID: String,
                L_LAST_USE: Long,
                L_AVATAR: ByteArray){
        AVATAR_ID = L_AVATAR_ID
        LAST_USE = L_LAST_USE
        AVATAR = L_AVATAR
    }

    @InternalAPI
    constructor(jsocket: Jsocket){
        if(jsocket.value_par1.isEmpty() || jsocket.content == null || jsocket.content!!.isEmpty()){
            return
        }
        AVATAR_ID = jsocket.value_id3
        LAST_USE = DateTime.nowUnixLong()
        AVATAR = jsocket.content
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID(): String {
        return AVATAR_ID
    }

    @JsName("getLAST_USE")
    fun getLAST_USE(): Long{
        return LAST_USE
    }

    @JsName("getAVATAR")
    fun getAVATAR(): ByteArray? {
        return AVATAR
    }


    companion object {

        @InternalAPI
        suspend fun GET_AVATAR(jsocket: Jsocket){
            var kBigAvatar: KBigAvatar? = BIG_AVATARS[jsocket.value_id3]
            if(kBigAvatar == null){
                kBigAvatar = SELECT_BIG_AVATARS(jsocket.value_id3)
            }
        }


    }
}