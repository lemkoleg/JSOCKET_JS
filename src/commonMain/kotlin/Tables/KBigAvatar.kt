@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")

/*
 * To change answerType license header, choose License Headers in Project Properties.
 * To change answerType template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables

import com.soywiz.klock.DateTime
import io.ktor.util.*
import lib_exceptions.exc_avatar_size_is_null
import p_jsocket.ANSWER_TYPE
import sql.Sqlite_service
import kotlin.js.JsName

/**
 *
 * @author User
 */
@JsName("BigAvatar")
open class KBigAvatar {

    private val answerType:ANSWER_TYPE

    constructor(){
        answerType = ANSWER_TYPE()
    }

    constructor(ObjectID:String,
                TimeAdded: Long,
                IsTemp: Int,
                SmallAvatarSize: Int,
                Avatar: ByteArray){
        answerType = ANSWER_TYPE()
        setOBJECTS_ID(ObjectID.trim())
        setTIME_ADDED(TimeAdded)
        setIS_TEMP(IsTemp)
        setSMALL_AVATAR_SIZE(SmallAvatarSize)
        setAVATAR(Avatar)

    }

    constructor (ans : ANSWER_TYPE){
        answerType = ans
    }

    @JsName("getANSWER_TYPE")
    fun getANSWER_TYPE():ANSWER_TYPE{
        return answerType
    }

    @JsName("getAVATAR")
    fun getAVATAR(): ByteArray? {
        return answerType.BLOB_1
    }

    @JsName("getOBJECTS_ID")
    fun getOBJECTS_ID(): String {
        return answerType.IDENTIFICATOR_1 ?: ""
    }

    @JsName("getSMALL_AVATAR_SIZE")
    fun getSMALL_AVATAR_SIZE(): Int {
        return answerType.INTEGER_2 ?: 0
    }

    @JsName("getIS_TEMP")
    fun getIS_TEMP(): Int {
        return answerType.INTEGER_1 ?: 0
    }

    @JsName("getTIME_ADDED")
    fun getTIME_ADDED(): Long {
        return answerType.LONG_1 ?: 0L
    }

    @JsName("setAVATAR")
    fun setAVATAR(v: ByteArray?) {
        answerType.BLOB_1 = v
    }

    @JsName("setOBJECTS_ID")
    fun setOBJECTS_ID(v: String) {
        answerType.IDENTIFICATOR_1 = v.trim()
    }

    @JsName("setSMALL_AVATAR_SIZE")
    fun setSMALL_AVATAR_SIZE(v: Int) {
        if (v == 0) {
            throw exc_avatar_size_is_null()
        }
        answerType.INTEGER_2 = v
    }

    @JsName("setIS_TEMP")
    fun setIS_TEMP(v: Int) {
        answerType.INTEGER_1 = v
    }

    @JsName("setTIME_ADDED")
    fun setTIME_ADDED(v: Long) {
        answerType.LONG_1 = if (v == 0L) DateTime.nowUnixLong()
        else v
    }



    companion object {
        @InternalAPI
        val lock = Lock()

        @ExperimentalStdlibApi
        @InternalAPI
        @JsName("IsExistBigAvatar")
        protected suspend fun IsExistBigAvatar(lObjectID: String): Boolean {
            try{
            lock.lock()
                val b =
                    Sqlite_service.SelectBigAvatar(lObjectID.trim())
                return (b != null)
            }
            finally {
                lock.unlock()
            }
        }

        @ExperimentalStdlibApi
        @InternalAPI
        @JsName("ReturnExistingTimeAdded")
        suspend fun ReturnExistingTimeAdded(lObjectID: String): Long {
            try{
            lock.lock()
                val b =
                    Sqlite_service.SelectBigAvatar(lObjectID.trim())
                return b?.getTIME_ADDED() ?: 0L
            }
            finally {
                lock.unlock()
            }
        }
    }
}