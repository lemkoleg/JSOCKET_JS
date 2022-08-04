package Tables

import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import p_jsocket.ANSWER_TYPE
import kotlin.time.ExperimentalTime

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val OBJECTS_INFO: MutableMap<String, KObjectInfo> = mutableMapOf()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class KObjectInfo(l_answerType: ANSWER_TYPE) {

    private var answerType: ANSWER_TYPE = l_answerType

    init {

    }

    fun merge(v: ANSWER_TYPE){
        answerType.merge(v)
    }

}