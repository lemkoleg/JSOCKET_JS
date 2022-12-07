package Tables

import com.soywiz.klock.DateTime
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import p_jsocket.Constants
import kotlin.time.ExperimentalTime

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class KCashDataUpdateParameters(
    val last_update: Long,
    val limit: Int,
    var count_of_all_records: Int
) {
    val time_out = DateTime.nowUnixMillisLong() + Constants.CLIENT_TIMEOUT
    var start_record_id = ""
}