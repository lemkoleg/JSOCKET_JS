/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import atomic.AtomicLong
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author User
 */

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val CHATS: MutableMap<String, KChat>  = mutableMapOf()


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val NEW_CHATS: ArrayDeque<ANSWER_TYPE>  = ArrayDeque()

@InternalAPI
private val KChatsLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KChat")
class KChat{

    init {
        ensureNeverFrozen()
    }

    //val InstanceRef: AtomicReference<KChat> = AtomicReference(this)


    constructor(ans: ANSWER_TYPE) : super(ans) {
        setGlobalLastUpdatingDate(getLAST_UPDATE())
        MainScope().launch { Sqlite_service.InsertChat(this as KChat) }
    }

    constructor(
        CHATS_ID: String,
        L_MESSEGES_COUNT: Long,
        L_COUNT_OF_MEMBERS: Long,
        L_CHATS_OWNER: String,
        L_CHATS_TWINS: String,
        L_CHATS_NAME: String?,
        L_AVATAR_ID: String?,
        L_CHATS_PROFILE: String,
        L_CHATS_TYPE: String,
        L_CHATS_ACCESS: String,
        L_CHATS_STATUS: String,
        L_ADDING_DATE: Long,
        L_BALANCE: Long?,
        L_LAST_MESSEGES_ADDING: Long,
        L_DATE_CLOSED: Long,
        L_CHATS_SUBSCRIBE: String?,
        L_LAST_UPDATE: Long,
        L_ORIGINAL_AVATAR_SIZE: Int?,
        L_AVATAR_SERVER: String?,
        L_AVATAR_LINK: String?,
        L_AVATAR: ByteArray?,
        L_STRING_20: String
    ) : super() {
        setCHATS_ID(CHATS_ID)
        setMESSEGES_COUNT(L_MESSEGES_COUNT)
        setCOUNT_OF_MEMBERS(L_COUNT_OF_MEMBERS)
        setCHATS_OWNER(L_CHATS_OWNER)
        setCHATS_TWINS(L_CHATS_TWINS)
        setCHATS_NAME(L_CHATS_NAME)
        setAVATAR_ID(L_AVATAR_ID)
        setPROFILE_LINE(L_CHATS_PROFILE, L_CHATS_TYPE, L_CHATS_ACCESS, L_CHATS_STATUS)
        setADDING_DATE(L_ADDING_DATE)
        setBALANCE(L_BALANCE)
        setLAST_MESSEGES_ADDING(L_LAST_MESSEGES_ADDING)
        setDATE_CLOSED(L_DATE_CLOSED)
        setSUBSCRIBE(L_CHATS_SUBSCRIBE)
        setLAST_UPDATE(L_LAST_UPDATE)
        setORIGINAL_AVATAR_SIZE(L_ORIGINAL_AVATAR_SIZE)
        setAVATAR_SERVER(L_AVATAR_SERVER)
        setAVATAR_LINK(L_AVATAR_LINK)
        setAVATAR1(L_AVATAR)
        setSTRING_20(L_STRING_20)
    }

    @JsName("getCHATS_ID")
    fun getCHATS_ID(): String {
        return IDENTIFICATOR_5 ?: ""
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v: String) {
        IDENTIFICATOR_5 = v
    }

    @JsName("getMESSEGES_COUNT")
    fun getMESSEGES_COUNT(): Long {
        return LONG_5 ?: 0L
    }

    @JsName("setMESSEGES_COUNT")
    fun setMESSEGES_COUNT(v: Long?) {
        LONG_5 = v ?: 0L
    }

    @JsName("getCOUNT_OF_MEMBERS")
    fun getCOUNT_OF_MEMBERS(): Long {
        return LONG_8 ?: 0L
    }

    @JsName("setCOUNT_OF_MEMBERS")
    fun setCOUNT_OF_MEMBERS(v: Long) {
        LONG_8 = v
    }

    @JsName("getCHATS_OWNER")
    fun getCHATS_OWNER(): String {
        return IDENTIFICATOR_7 ?: ""
    }

    @JsName("setCHATS_OWNER")
    fun setCHATS_OWNER(v: String) {
        IDENTIFICATOR_7 = v
    }

    @JsName("getCHATS_TWINS")
    fun getCHATS_TWINS(): String {
        return IDENTIFICATOR_8 ?: ""
    }

    @JsName("setCHATS_TWINS")
    fun setCHATS_TWINS(v: String) {
        IDENTIFICATOR_8 = v
    }

    @JsName("getCHATS_NAME")
    fun getCHATS_NAME(): String {
        return STRING_5 ?: ""
    }

    @JsName("setCHATS_NAME")
    fun setCHATS_NAME(v: String?) {
        STRING_5 = v ?: ""
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID(): String {
        return IDENTIFICATOR_6 ?: ""
    }

    @JsName("setAVATAR_ID")
    fun setAVATAR_ID(v: String?) {
        IDENTIFICATOR_6 = v ?: ""
    }

    @JsName("getCHATS_PROFILE")
    fun getCHATS_PROFILE(): String? {
        if (STRING_7 == null || STRING_7!!.length < 5) {
            return null
        }
        return STRING_7!!.substring(0, 5)
    }

    @JsName("getCHATS_TYPE")
    fun getCHATS_TYPE(): String? {
        if (STRING_7 == null || STRING_7!!.length < 6) {
            return null
        }
        return STRING_7!!.substring(5, 6)
    }

    @JsName("getCHATS_ACCESS")
    fun getCHATS_ACCESS(): String? {
        if (STRING_7 == null || STRING_7!!.length < 7) {
            return null
        }
        return STRING_7!!.substring(6, 7)
    }

    @JsName("getCHATS_STATUS")
    fun getCHATS_STATUS(): String? {
        if (STRING_7 == null || STRING_7!!.length < 8) {
            return null
        }
        return STRING_7!!.substring(7, 8)
    }

    @JsName("setPROFILE_LINE")
    fun setPROFILE_LINE(
        l_chats_profile: String,
        l_chats_type: String,
        l_chats_access: String,
        l_chats_status: String
    ) {
        STRING_7 = l_chats_profile + l_chats_type + l_chats_access + l_chats_status
    }

    @JsName("getADDING_DATE")
    fun getADDING_DATE(): Long {
        return LONG_4 ?: 0L
    }

    @JsName("setADDING_DATE")
    fun setADDING_DATE(v: Long) {
        LONG_4 = v
    }

    @JsName("getBALANCE")
    fun getBALANCE(): Long {
        return LONG_9 ?: 0L
    }

    @JsName("setBALANCE")
    fun setBALANCE(v: Long?) {
        LONG_9 = v ?: 0L
    }

    @JsName("getLAST_MESSEGES_ADDING")
    fun getLAST_MESSEGES_ADDING(): Long {
        return LONG_10 ?: 0L
    }

    @JsName("setLAST_MESSEGES_ADDING")
    fun setLAST_MESSEGES_ADDING(v: Long?) {
        LONG_10 = v ?: 0L
    }

    @JsName("getDATE_CLOSED")
    fun getDATE_CLOSED(): Long {
        return LONG_6 ?: 0L
    }

    @JsName("setDATE_CLOSED")
    fun setDATE_CLOSED(v: Long) {
        LONG_6 = v
    }

    @JsName("getLAST_UPDATE")
    fun getLAST_UPDATE(): Long {
        return LONG_7 ?: 0L
    }

    @JsName("setLAST_UPDATE")
    fun setLAST_UPDATE(v: Long) {
        LONG_7 = v
    }

    fun merge(v: KChat?) {
        if (v == null) {
            return
        }
        super.merge(v)
    }


    @JsName("UpdateChat")
    fun UpdateChat(lANSWER_TYPE: ANSWER_TYPE, isAddSqLite: Boolean) {
        merge(lANSWER_TYPE)
        setGlobalLastUpdatingDate(LONG_7 ?: 0L)
        if (isAddSqLite) {
            CoroutineScope(Dispatchers.Default).launch { Sqlite_service.InsertChat(this@KChat) }
        }
    }

    @JsName("InsertMessege")
    fun InsertMessege(m: KMessege) {
        if (m.getCHATS_ID() == getCHATS_ID()) {
            MESSEGES[m.getMESSEGES_COUNT()] = m
        }
    }

    private fun LastMessegeCount(): Long {
        return MESSEGES.keys.last() ?: 0L
    }

    @ExperimentalStdlibApi
    @JsName("InsertNewMessege")
    fun InsertNewMessege(m: KMessege) {
        if (m.getCHATS_ID() == getCHATS_ID()) {
            if (MESSEGES.containsKey(m.getMESSEGES_COUNT())) {
                CoroutineScope(Dispatchers.Default).launch { MESSEGES[m.getMESSEGES_COUNT()]!!.merge(m) }
            } else {
                CoroutineScope(Dispatchers.Default).launch { Sqlite_service.InsertMessege(m) }
            }
            MESSEGES[m.getMESSEGES_COUNT()] = m
        }
    }

    /*private fun SynchronizeMessege() {
        if (verifyMesseges != 0) {
            try {
                var lowerKey: Int = (MESSEGES.size - maxCountOfMessegesIntoDB.value)
                lowerKey = if (lowerKey < 0) 0 else lowerKey
                val mapSize = if (lowerKey == 0) 0 else MESSEGES.keys.lastIndexOf(lowerKey)
                if (mapSize != if (lowerKey > 0L) maxCountOfMessegesIntoDB else MESSEGES.size) {
                    var jsocket = Jsocket()
                    jsocket.just_do_it = 1011000090
                    jsocket.value_id1 = chatsID
                    jsocket.last_messege_update = getGlobalLastUpdatingDate()
                    jsocket.last_date_of_update = MESSEGES.lastKey()
                    jsocket.value_par2 = "1"
                    jsocket.value_par3 = "2"
                    jsocket.value_par4 = "1"
                    jsocket = send_JSOCKETs!!.send_JSOCKET_with_TimeOut(jsocket, null, true)
                    if (jsocket.ANSWER_TYPEs != null && jsocket.ANSWER_TYPEs.size() > 0) {
                        jsocket.ANSWER_TYPEs.forEach { k -> InsertNewMessege(Messege(k)) }
                    }
                }
            } catch (ex: Exception) {
                try {
                    PrintWriter(FileOutputStream(java.io.File(JSOCKET_Instance.getPathErrors(), JSOCKET_Instance.getFileErrorName().concat("_chat_SynchronizeMessege_errors.txt")))).use({ pw -> ex.printStackTrace(pw) })
                } catch (e2: Exception) {
                }
            } finally {
                verifyMesseges = 0
            }
        }
    }

    protected fun FlushMesseges() {
        if (flushMesseges != 0) {
            try {
                var p: TreeMap? = null
                val lowerKey: Long = java.lang.Long.valueOf(MESSEGES.size - maxCountOfMessegesIntoDB!!.toLong())
                p = if (lowerKey > 0L) {
                    MESSEGES.tailMap(lowerKey, true) as TreeMap
                } else {
                    MESSEGES
                }
                p.forEach(BiConsumer { k: Any?, v: Any? -> Sqlite_service.InsertMessege(v as Messege?) })
            } catch (ex: Exception) {
                try {
                    PrintWriter(FileOutputStream(java.io.File(JSOCKET_Instance.getPathErrors(), JSOCKET_Instance.getFileErrorName().concat("_chat_FlushMesseges_errors.txt")))).use({ pw -> ex.printStackTrace(pw) })
                } catch (e2: Exception) {
                }
            } finally {
                flushMesseges = 0
            }
        }
    }*/

    @JsName("DeleteMessege")
    fun DeleteMessege(lMessegeId: Long?) {
        MESSEGES.minus(lMessegeId)
    }

    companion object {
        @JsName("globalLastUpdatingDate")
        val globalLastUpdatingDate: AtomicLong = AtomicLong(0L)


        private fun setGlobalLastUpdatingDate(lUpdatingDate: Long) {
            globalLastUpdatingDate.setGreaterValue(lUpdatingDate)
        }

        @JsName("maxCountOfMessegesIntoDB")
        val maxCountOfMessegesIntoDB = AtomicLong(100L)


        @KorioExperimentalApi
        @JsName("ADD_NEW_CHATS")
        fun ADD_NEW_CHATS(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
            withTimeout(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KChatsLock.lock()
                        while (NEW_CHATS.isNotEmpty()) {
                            TODO()
                        }
                        return@withTimeout true
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KChats",
                            l_function_name = "ADD_NEW_CHATS",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KChatsLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeout false
            }
        }.toPromise(EmptyCoroutineContext)
    }
}
