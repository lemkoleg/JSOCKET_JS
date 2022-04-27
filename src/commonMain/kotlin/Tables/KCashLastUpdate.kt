package Tables

import CrossPlatforms.WriteExceptionIntoFile
import io.ktor.util.*
import kotlinx.coroutines.*
import p_jsocket.CLIENT_TIMEOUT
import kotlin.coroutines.CoroutineContext

@InternalAPI
private val CASH_LAST_UPDATE: MutableMap<Long, KCashLastUpdate> = mutableMapOf()

@InternalAPI
class KCashLastUpdate {

    var CASH_SUM: Long = 0
    var LAST_USE: Long = 0
    var LAST_UPDATE: Long = 0
    var OBJECTS_IDS: String = ""
    var CONNECTION_ID: Long = 0
    val CASH_DATA: ArrayList<KCashData> = arrayListOf()
    private val CASH_DATA_IDS: MutableMap<String, Int> = mutableMapOf()  //OBJECT_ID and POSITION
    private val KCashLastUpdate_Lock = Lock()

    private constructor()

    private constructor(L_CASH_SUM: Long,
                        L_LAST_USE: Long,
                        L_LAST_UPDATE: Long,
                        L_OBJECTS_IDS: String){
        CASH_SUM = L_CASH_SUM
        LAST_USE = L_LAST_USE
        LAST_UPDATE = L_LAST_UPDATE
        OBJECTS_IDS = L_OBJECTS_IDS
        CONNECTION_ID = myConnectionsID.value
    }

    private suspend fun INSERT_CASH_DATA(kCashData: KCashData){
        withTimeoutOrNull(CLIENT_TIMEOUT) {
            try {
                KCashLastUpdate_Lock.lock()
                if(CASH_DATA_IDS.containsKey(kCashData.OBJECTS_ID)){
                    val lCashData =  CASH_DATA[CASH_DATA_IDS[kCashData.OBJECTS_ID]!!]
                    if(lCashData.OBJECTS_ID != kCashData.OBJECTS_ID){

                    }
                }
                val kCashData: KCashData? = CASH_DATA_IDS[kCashData.OBJECTS_ID]
                if(kCashLastUpdate != null){
                    kCashLastUpdate.LAST_UPDATE =  L_LAST_UPDATE
                    kCashLastUpdate.LAST_USE =  L_LAST_USE
                }else{
                    KCashLastUpdate(
                        L_CASH_SUM,
                        L_LAST_USE,
                        L_LAST_UPDATE,
                        L_OBJECTS_IDS
                    ).also { CASH_LAST_UPDATE[L_CASH_SUM] = it }
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "KCashLastUpdate.INSERT_CASH_LAST_UPDATE_INTO_MAP")
            } finally {
                KCashLastUpdate_Lock.unlock()}

        }
    }




    @InternalAPI
    companion object : CoroutineScope {

        override val coroutineContext: CoroutineContext = Dispatchers.Default

        private val KCashLastUpdate_Companion_serviceScope = CoroutineScope(coroutineContext) + SupervisorJob()

        private val KCashLastUpdate_Companion_Lock = Lock()

        suspend fun INSERT_CASH_LAST_UPDATE_INTO_MAP(
            L_CASH_SUM: Long,
            L_LAST_USE: Long,
            L_LAST_UPDATE: Long,
            L_OBJECTS_IDS: String
        ) {
            withTimeoutOrNull(CLIENT_TIMEOUT) {
                try {
                    KCashLastUpdate_Companion_Lock.lock()
                    val kCashLastUpdate: KCashLastUpdate? = CASH_LAST_UPDATE[L_CASH_SUM]
                    if(kCashLastUpdate != null){
                        kCashLastUpdate.LAST_UPDATE =  L_LAST_UPDATE
                        kCashLastUpdate.LAST_USE =  L_LAST_USE
                    }else{
                        KCashLastUpdate(
                            L_CASH_SUM,
                            L_LAST_USE,
                            L_LAST_UPDATE,
                            L_OBJECTS_IDS
                        ).also { CASH_LAST_UPDATE[L_CASH_SUM] = it }
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "KCashLastUpdate.INSERT_CASH_LAST_UPDATE_INTO_MAP")
                } finally {
                    KCashLastUpdate_Companion_Lock.unlock()
                }
            }
        }

        suspend fun INSERT_CASH_DATA(kCashData: KCashData) {
            withTimeoutOrNull(CLIENT_TIMEOUT) {
                try {
                    KCashLastUpdate_Companion_Lock.lock()
                    val kCashLastUpdate: KCashLastUpdate? = CASH_LAST_UPDATE[L_CASH_SUM]
                    if(kCashLastUpdate != null){
                        KCashLastUpdate_Companion_serviceScope.launch {

                        }
                        kCashLastUpdate.LAST_UPDATE =  L_LAST_UPDATE
                        kCashLastUpdate.LAST_USE =  L_LAST_USE
                    }else{
                        KCashLastUpdate(
                            L_CASH_SUM,
                            L_LAST_USE,
                            L_LAST_UPDATE,
                            L_OBJECTS_IDS
                        ).also { CASH_LAST_UPDATE[L_CASH_SUM] = it }
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "KCashLastUpdate.INSERT_CASH_LAST_UPDATE_INTO_MAP")
                } finally {
                    KCashLastUpdate_Companion_Lock.unlock()
                }
            }
        }
    }
}