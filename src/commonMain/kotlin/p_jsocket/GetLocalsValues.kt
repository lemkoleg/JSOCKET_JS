package p_jsocket

import CrossPlatforms.WriteExceptionIntoFile
import Tables.META_DATA
import Tables.META_DATA_condition
import com.soywiz.klock.DateTime
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("GetLocalsValues")
class GetLocalsValues {


    @JsName("getMETA_DATA")
    suspend fun getMETA_DATA(n: String):Long {
        try {
                var v:Long? = null
                v = META_DATA[n]
                if(v == null){
                    try {
                        withContext(coroutineContext) {
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