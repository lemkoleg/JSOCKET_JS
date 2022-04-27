package Tables

import io.ktor.util.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import p_jsocket.ANSWER_TYPE
import sql.Sqlite_service
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