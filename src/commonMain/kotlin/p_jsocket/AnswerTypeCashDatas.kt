package p_jsocket

import Tables.KCashData
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlin.coroutines.EmptyCoroutineContext




@OptIn(KorioExperimentalApi::class)
class AnswerTypeCashDatas(l_answerType: ANSWER_TYPE) {

    val answerType = l_answerType

    fun GetChatMesseges (l_updated: (() -> Any?)? = null): Promise<KCashData?> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
        if(answerType.RECORD_TYPE.equals("3")){
            return@async KCashData.GET_CASH_DATA(
                L_OBJECT_ID = answerType.answerTypeValues.GetChatId(),
                L_RECORD_TYPE = "4",
                L_COURSE = "1",
                l_updatedCashData = l_updated,
                l_request_updates = true,
                l_select_all_records = false,
                l_is_SetLastBlock = true,
                l_reset_cash_data = false,
                l_ignore_timeout = true).await()
        }
            return@async null
    }.toPromise(EmptyCoroutineContext)

    fun GetChatMembers (l_updated: (() -> Any?)? = null): Promise<KCashData?> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            if(answerType.RECORD_TYPE.equals("3")){
                return@async KCashData.GET_CASH_DATA(
                    L_OBJECT_ID = answerType.answerTypeValues.GetChatId(),
                    L_RECORD_TYPE = "8",
                    L_COURSE = "0",
                    l_updatedCashData = l_updated,
                    l_request_updates = true,
                    l_select_all_records = false,
                    l_is_SetLastBlock = true,
                    l_reset_cash_data = false,
                    l_ignore_timeout = true).await()
            }
            return@async null
        }.toPromise(EmptyCoroutineContext)

}