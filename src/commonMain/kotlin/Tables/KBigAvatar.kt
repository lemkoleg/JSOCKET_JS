@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")

/*
 * To change answerType license header, choose License Headers in Project Properties.
 * To change answerType template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables

import com.soywiz.klock.DateTime
import io.ktor.util.*
import p_jsocket.ANSWER_TYPE
import sql.Sqlite_service
import kotlin.js.JsName

/**
 *
 * @author User
 */
@JsName("BigAvatar")
open class KBigAvatar :ANSWER_TYPE {


    constructor(): super()

    constructor(ans : ANSWER_TYPE): super(ans)

    @JsName("getAVATAR")
    fun getAVATAR(): ByteArray? {
        return BLOB_3
    }

    @JsName("setAVATAR")
    fun setAVATAR(v: ByteArray) {
        BLOB_1 = v
    }

    @JsName("getTIME_ADDED")
    fun getTIME_ADDED(): Long {
        return LONG_18 ?: 0L
    }

    @JsName("setTIME_ADDED")
    fun setTIME_ADDED(v: Long) {
        LONG_18 = if (v == 0L) DateTime.nowUnixLong()
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