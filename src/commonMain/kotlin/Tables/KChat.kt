/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables
import atomic.AtomicLong
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import p_jsocket.ANSWER_TYPE
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName


/**
 *
 * @author User
 */
@InternalAPI
@JsName("KChat")
class KChat :ANSWER_TYPE, CoroutineScope {

    val MESSEGES: MutableMap<Long?, KMessege?> = mutableMapOf()
    override val coroutineContext: CoroutineContext = Dispatchers.Unconfined
    private val ChatContext = CoroutineScope(coroutineContext)

    constructor(): super(){
    }

    @ExperimentalStdlibApi
    @InternalAPI
    constructor(ans : ANSWER_TYPE, isAddSqLite: Boolean): super(ans){
        setGlobalLastUpdatingDate(getLAST_UPDATE())
        if(isAddSqLite) {
            val m = this
            ChatContext.launch {Sqlite_service.InsertChat(m)}
        }
    }

    @JsName("getCHATS_ID")
    fun getCHATS_ID():String{
        return IDENTIFICATOR_5?:""
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v:String){
        IDENTIFICATOR_5 = v
    }

    @JsName("getMESSEGES_COUNT")
    fun getMESSEGES_COUNT():Long{
        return LONG_5?:0L
    }

    @JsName("setMESSEGES_COUNT")
    fun setMESSEGES_COUNT(v:Long?){
        LONG_5 = v?:0L
    }

    @JsName("getCOUNT_OF_MEMBERS")
    fun getCOUNT_OF_MEMBERS():Long{
        return LONG_8?:0L
    }

    @JsName("setCOUNT_OF_MEMBERS")
    fun setCOUNT_OF_MEMBERS(v:Long){
        LONG_8 = v
    }

    @JsName("getCHATS_OWNER")
    fun getCHATS_OWNER():String{
        return IDENTIFICATOR_7?:""
    }

    @JsName("setCHATS_OWNER")
    fun getCHATS_OWNER(v:String){
        IDENTIFICATOR_7 = v
    }

    @JsName("getCHATS_NAME")
    fun getCHATS_NAME():String{
        return STRING_5?:""
    }

    @JsName("setCHATS_NAME")
    fun setCHATS_NAME(v:String?){
        STRING_5 = v?:""
    }

    @JsName("getAVATAR_ID")
    fun getAVATAR_ID():String{
        return IDENTIFICATOR_6?:""
    }

    @JsName("setAVATAR_ID")
    fun getAVATAR_ID(v:String?){
        IDENTIFICATOR_6 = v?:""
    }

    @JsName("getCHATS_PROFILE")
    fun setCHATS_PROFILE():String?{
        if(STRING_7 == null || STRING_7!!.length < 5){
            return null
        }
        return STRING_7!!.substring(0, 5)
    }

    @JsName("getCHATS_TYPE")
    fun setCHATS_TYPE():String?{
        if(STRING_7 == null || STRING_7!!.length < 6){
            return null
        }
        return STRING_7!!.substring(5, 6)
    }

    @JsName("getCHATS_ACCESS")
    fun setCHATS_ACCESS():String?{
        if(STRING_7 == null || STRING_7!!.length < 7){
            return null
        }
        return STRING_7!!.substring(6, 7)
    }

    @JsName("getCHATS_STATUS")
    fun setCHATS_STATUS():String?{
        if(STRING_7 == null || STRING_7!!.length < 8){
            return null
        }
        return STRING_7!!.substring(7, 8)
    }

    @JsName("setPROFILE_LINE")
    fun setPROFILE_LINE(l_chats_profile: String,
                        l_chats_type: String,
                        l_chats_access: String,
                        l_chats_status: String){
        STRING_7 = l_chats_profile + l_chats_type + l_chats_access + l_chats_status
    }

    @JsName("getADDING_DATE")
    fun getADDING_DATE():Long{
        return LONG_4?:0L
    }

    @JsName("setADDING_DATE")
    fun setADDING_DATE(v:Long){
        LONG_4 = v
    }

    @JsName("getBALANCE")
    fun getBALANCE():Long{
        return LONG_9?:0L
    }

    @JsName("setBALANCE")
    fun setBALANCE(v:Long?){
        LONG_9 = v?:0L
    }

    @JsName("getDATE_CLOSED")
    fun getDATE_CLOSED():Long{
        return LONG_6?:0L
    }

    @JsName("setDATE_CLOSED")
    fun setDATE_CLOSED(v:Long){
        LONG_6 = v
    }

    @JsName("getLAST_UPDATE")
    fun getLAST_UPDATE():Long{
        return LONG_7?:0L
    }

    @JsName("setLAST_UPDATE")
    fun setLAST_UPDATE(v:Long){
        LONG_7 = v
    }

    fun merge(v :KChat? ){
        if(v == null ){
            return
        }
        super.merge(v)
    }


    @ExperimentalStdlibApi
    @InternalAPI
    @JsName("UpdateChat")
    fun UpdateChat(lANSWER_TYPE: ANSWER_TYPE, isAddSqLite: Boolean) {
        merge(lANSWER_TYPE)
        setGlobalLastUpdatingDate(LONG_7 ?: 0L)
        if(isAddSqLite){
            ChatContext.launch {Sqlite_service.InsertChat(this@KChat)}
        }
    }

    @JsName("InsertMessege")
    fun InsertMessege(m: KMessege) {
        if (m.getCHATS_ID() == getCHATS_ID()) {
            MESSEGES[m.getMESSEGES_COUNT()] = m
        }
    }

    private fun LastMessegeCount(): Long {
        return MESSEGES.keys.last()?:0L
    }

    @ExperimentalStdlibApi
    @JsName("InsertNewMessege")
    fun InsertNewMessege(m: KMessege) {
        if (m.getCHATS_ID() == getCHATS_ID()) {
            if (MESSEGES.containsKey(m.getMESSEGES_COUNT())) {
                ChatContext.launch { MESSEGES[m.getMESSEGES_COUNT()]!!.merge(m)}
            } else {
                ChatContext.launch {Sqlite_service.InsertMessege(m)}
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

        @InternalAPI
        private fun setGlobalLastUpdatingDate(lUpdatingDate: Long) {
            globalLastUpdatingDate.setGreaterValue(lUpdatingDate)
        }

        @JsName("maxCountOfMessegesIntoDB")
        val maxCountOfMessegesIntoDB = AtomicLong(100L)
    }
}