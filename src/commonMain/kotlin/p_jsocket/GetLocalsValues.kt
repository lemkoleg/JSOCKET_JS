package p_jsocket

import CrossPlatforms.WriteExceptionIntoFile
import Tables.KBigAvatar
import Tables.META_DATA
import Tables.META_DATA_condition
import com.soywiz.klock.DateTime
import io.ktor.util.*
import kotlinx.coroutines.*
import sql.SQLStatement
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName
import com.soywiz.kds.Queue


private val CLIENT_GET_LOCAL_VALUES_POOL: Queue<GetLocalsValues> = Queue()

@InternalAPI
private val lock = Lock()

class GetLocalsValues: CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default

    private val serviceScope = CoroutineScope(coroutineContext) + SupervisorJob()

    //////////////////////////////big avatars//////////////////////////////////

    @InternalAPI
    @JsName("InsertBigAvata")
    fun InsertBigAvatar(lKBigAvatar: KBigAvatar) =
        serviceScope.launch {
            if (lKBigAvatar.getAVATAR()?.size ?: 0 > 0) {
                try {
                    withTimeoutOrNull(CLIENT_TIMEOUT) {
                        lock.lock()
                        lKBigAvatar.getAVATAR()?.let {
                            sqlStatement.INSERT_BIG_AVATARS(
                                lKBigAvatar.getAVATAR_ID(),
                                lKBigAvatar.getLAST_USE(),
                                lKBigAvatar.getAVATAR()
                            )
                        }
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Sqlite_service.InsertBigAvatar")
                }
                finally {
                    Sqlite_service.lock.unlock()
                }
            }
        }



    @OptIn(InternalAPI::class, kotlin.time.ExperimentalTime::class, kotlin.ExperimentalStdlibApi::class)
    @JsName("getMETA_DATA")
    suspend fun getMETA_DATA(n: String):Long {
        try {
                var v:Long? = null
                v = META_DATA[n]
                if(v == null){
                    try {
                        withContext(this.coroutineContext) {
                            sqlStatement.SELECT_METADATA(n)
                            v = META_DATA[n]
                            if(v != null){
                                return@withContext v
                            }else {
                                val time = DateTime.nowUnixLong() + CLIENT_TIMEOUT
                                while(v == null && time > DateTime.nowUnixLong()){
                                    META_DATA_condition.cAwait(CLIENT_TIMEOUT)
                                }
                            }
                        }
                    } catch (ex: Exception) {
                        WriteExceptionIntoFile(ex, "Sqlite_service.SelectMetaData")
                        0
                    }


                }


                while(v == null && time > DateTime.nowUnixLong()){
                    META_DATA_condition.cAwait(CLIENT_TIMEOUT)
                }
                META_DATA[n] = meta_data.getVALUE_VALUE()
                if (last_update < meta_data.getLATS_UPDATE()) {
                    last_update = meta_data.getLATS_UPDATE()
                }

        } finally {
            lock.unlock()
        }
    }


    companion object {

        @JsName("getANSWER_TYPE")
        @InternalAPI
        fun getGET_LOCAL_VALUES(): GetLocalsValues {
            val getLocalsValues: GetLocalsValues? =
                try {
                    lock.lock()
                    if (CLIENT_GET_LOCAL_VALUES_POOL.peek() == null) {
                        CoroutineScope(NonCancellable).launch {
                            fill()
                        }
                        GetLocalsValues()
                    } else {
                        CLIENT_GET_LOCAL_VALUES_POOL.dequeue()
                    }
                } catch (ex: Exception) {
                    CoroutineScope(NonCancellable).launch {
                        fill()
                    }
                    return GetLocalsValues()
                } finally {
                    lock.unlock()
                }
            return getLocalsValues ?: GetLocalsValues()
        }

        private fun fill() {
            while (CLIENT_GET_LOCAL_VALUES_POOL.size < CLIENT_ANSWER_TYPE_POOL_SIZE && !isInterrupted.value) {
                CLIENT_GET_LOCAL_VALUES_POOL.enqueue(GetLocalsValues())
            }
        }

        @InternalAPI
        fun close(){
            CLIENT_GET_LOCAL_VALUES_POOL.clear()
        }



}