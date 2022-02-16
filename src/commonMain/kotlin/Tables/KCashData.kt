package Tables

import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import p_jsocket.ANSWER_TYPE
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName

@JsName("KCashData")
class KCashData  : ANSWER_TYPE {

    constructor(): super(){
    }

    @ExperimentalStdlibApi
    @InternalAPI
    constructor(ans : ANSWER_TYPE): super(ans){
        MainScope().launch { Sqlite_service.InsertChat(this as KCashData)}
    }
}