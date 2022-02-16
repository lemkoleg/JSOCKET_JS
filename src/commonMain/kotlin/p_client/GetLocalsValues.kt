package p_client

import CrossPlatforms.WriteExceptionIntoFile
import Tables.KBigAvatar
import Tables.META_DATA
import Tables.META_DATA_condition
import com.soywiz.klock.DateTime
import io.ktor.util.*
import kotlinx.coroutines.*
import p_jsocket.CLIENT_TIMEOUT
import sql.SQLStatement
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName

class GetLocalsValues: CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default

    private val serviceScope = CoroutineScope(coroutineContext) + SupervisorJob()

    @OptIn(ExperimentalStdlibApi::class)
    val sqlStatement: SQLStatement = Sqlite_service.Connection.createStatement()

    @InternalAPI
    private val lock = Lock()


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



}