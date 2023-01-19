package p_jsocket

import Tables.KCashData
import com.soywiz.korio.async.await
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlin.time.ExperimentalTime


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class AnswerTypeCashDatas(l_answerType: ANSWER_TYPE) {

    val answerType = l_answerType

    suspend fun GetChatMesseges (l_startLoading: (() -> Any?)? = null): KCashData? {
        if(answerType.RECORD_TYPE.equals("4")){
           return KCashData.GET_CASH_DATA(
               L_OBJECT_ID = answerType.answerTypeValues.GetChatId(),
               L_RECORD_TYPE = "4",
               L_COURSE = "1",
               l_updatedCashData = l_startLoading,
               l_request_updates = true,
               l_select_all_records = false,
               l_is_SetLastBlock = true,
               l_reset_cash_data = false,
               l_ignore_timeout = true).await()
        }
        return null
    }

    suspend fun GetChatMembers (p_updatedCashData: (() -> Any?)? = null): KCashData? {
        if(answerType.RECORD_TYPE.equals("4")){
            return KCashData.GET_CASH_DATA(
                L_OBJECT_ID = answerType.answerTypeValues.GetChatId(),
                L_RECORD_TYPE = "8",
                L_COURSE = "0",
                l_updatedCashData = p_updatedCashData,
                l_request_updates = true,
                l_select_all_records = false,
                l_is_SetLastBlock = true,
                l_reset_cash_data = false,
                l_ignore_timeout = true).await()
        }
        return null
    }
}