/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables
import atomic.AtomicLong
import com.soywiz.korio.lang.substr
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
@JsName("Chat")
class KChat : CoroutineScope {

    val MESSEGES: MutableMap<Long?, KMessege?> = mutableMapOf()
    override val coroutineContext: CoroutineContext = Dispatchers.Unconfined
    private val ChatContext = CoroutineScope(coroutineContext)

    private val answerType:ANSWER_TYPE


    constructor(){
        answerType = ANSWER_TYPE()
    }

    @ExperimentalStdlibApi
    @InternalAPI
    constructor(ans : ANSWER_TYPE, isAddSqLite: Boolean){
        answerType = ans
        setGlobalLastUpdatingDate(getLAST_UPDATING_DATE())
        if(isAddSqLite) {
            val m = this
            ChatContext.launch {Sqlite_service.InsertChat(m) }
        }
    }

    @JsName("getANSWER_TYPE")
    fun getANSWER_TYPE():ANSWER_TYPE{
        return answerType
    }

    @JsName("getANSWER_TYPE")
    fun isUpdateChatBlob():String{
        return if(answerType.LONG_10!! < 10L){
            "0"
        } else {
            answerType.LONG_10.toString().substring(1,2)
        }
    }

    @JsName("getCHATS_ID")
    fun getCHATS_ID():String{
        return answerType.IDENTIFICATOR_1?:""
    }

    @JsName("getCHATS_OWNER")
    fun getCHATS_OWNER():String{
        return answerType.IDENTIFICATOR_2?:""
    }

    @JsName("getLAST_MESSEGES_ID")
    fun getLAST_MESSEGES_ID():Long{
        return answerType.LONG_1?:0L
    }

    @JsName("getCHATS_OPPONENT")
    fun getCHATS_OPPONENT():String{
        return answerType.IDENTIFICATOR_3?:""
    }

    @JsName("getMESSEGES_COUNT")
    fun getMESSEGES_COUNT():Long{
        return answerType.LONG_2?:0L
    }

    @JsName("getCHATS_NAME")
    fun getCHATS_NAME():String{
        return answerType.STRING_5?:""
    }

    @JsName("getCHATS_SMALL_AVATAR")
    fun getCHATS_SMALL_AVATAR():ByteArray?{
        return answerType.BLOB_1
    }

    @JsName("getCHATS_PROFILE")
    fun getCHATS_PROFILE():String{
        return answerType.STRING_1?:""
    }

    @JsName("getCHATS_ACCESS")
    fun getCHATS_ACCESS():String{
        return answerType.STRING_2?.substring(0, 1)?:""
    }

    @JsName("getCHATS_TYPE")
    fun getCHATS_TYPE():String{
        return answerType.STRING_2?.substring(1, 2)?:""
    }

    @JsName("getCHATS_STATUS")
    fun getCHATS_STATUS():String{
        return answerType.STRING_2?.substring(2, 3)?:""
    }

    @JsName("getLAST_UPDATING_DATE")
    fun getLAST_UPDATING_DATE():Long{
        return answerType.LONG_3?:0L
    }

    @JsName("getCHATS_SUBSCRIBE")
    fun getCHATS_SUBSCRIBE():String{
        return answerType.STRING_4?:""
    }

    @JsName("setCHATS_ID")
    fun setCHATS_ID(v:String){
       answerType.IDENTIFICATOR_1 = v.trim()
    }

    @JsName("setCHATS_OWNER")
    fun setCHATS_OWNER(v:String){
        answerType.IDENTIFICATOR_2 = v.trim()
    }

    @JsName("setLAST_MESSEGES_ID")
    fun setLAST_MESSEGES_ID(v:Long){
        answerType.LONG_1 = v
    }

    @JsName("setCHATS_OPPONENT")
    fun setCHATS_OPPONENT(v:String){
        answerType.IDENTIFICATOR_3 = v.trim()
    }

    @JsName("setMESSEGES_COUNT")
    fun setMESSEGES_COUNT(v:Long){
        answerType.LONG_2 = v
    }

    @JsName("setCHATS_NAME")
    fun setCHATS_NAME(v:String){
        answerType.STRING_5 = v.trim()
    }

    @JsName("setCHATS_SMALL_AVATAR")
    fun setCHATS_SMALL_AVATAR(v:ByteArray?){
        answerType.BLOB_1 = v
    }

    @JsName("setCHATS_PROFILE")
    fun setCHATS_PROFILE(v:String){
        answerType.STRING_1 = v.trim()
    }

    @JsName("setCHATS_ACCESS")
    fun setCHATS_ACCESS(v:String){
        answerType.STRING_2 = v.substring(0, 1)+answerType.STRING_2?.substr(1)
    }

    @JsName("setCHATS_TYPE")
    fun setCHATS_TYPE(v:String){
        answerType.STRING_2 = answerType.STRING_2?.substring(0, 1) + v.substring(0, 1)+answerType.STRING_2?.substr(2)
    }

    @JsName("setCHATS_STATUS")
    fun setCHATS_STATUS(v:String){
        answerType.STRING_2 = answerType.STRING_2?.substring(0, 2) + v.substring(0, 1)+answerType.STRING_2?.substr(3)
    }

    @JsName("setLAST_UPDATING_DATE")
    fun setLAST_UPDATING_DATE(v:Long){
        answerType.LONG_3 = v
        setGlobalLastUpdatingDate(getLAST_UPDATING_DATE())
    }

    @JsName("setCHATS_SUBSCRIBE")
    fun setCHATS_SUBSCRIBE(v:String){
        answerType.STRING_4 = v
    }

    @ExperimentalStdlibApi
    @InternalAPI
    @JsName("UpdateChat")
    fun UpdateChat(lANSWER_TYPE: ANSWER_TYPE, isAddSqLite: Boolean) {
        answerType.setValue(lANSWER_TYPE)
        setGlobalLastUpdatingDate(lANSWER_TYPE.LONG_3 ?: 0L)
        if(isAddSqLite){
            ChatContext.launch {Sqlite_service.InsertChat(this@KChat)}
        }
    }

    @JsName("InsertMessege")
    fun InsertMessege(m: KMessege) {
        if (m.getCHATS_ID() == getCHATS_ID()) {
            MESSEGES[m.getMESSEGES_CHATS_COUNT()] = m
        }
    }

    private fun LastMessegeCount(): Long {
        return MESSEGES.keys.last()?:0L
    }

    @ExperimentalStdlibApi
    @JsName("InsertNewMessege")
    fun InsertNewMessege(m: KMessege) {
        if (m.getCHATS_ID() == getCHATS_ID()) {
            if (MESSEGES.containsKey(m.getMESSEGES_CHATS_COUNT())) {
                ChatContext.launch {m.UpdateMessege(m.getANSWER_TYPE())}
            } else {
                ChatContext.launch {Sqlite_service.InsertMessege(m)}
            }
            MESSEGES[m.getMESSEGES_CHATS_COUNT()] = m
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