package Tables

import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
import p_jsocket.ANSWER_TYPE
import p_jsocket.FileService
import kotlin.time.ExperimentalTime

@InternalAPI
private val KObjectInfoLock = Mutex()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val OBJECTS_INFO: MutableMap<String, KObjectInfo> = mutableMapOf()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class KObjectInfo(l_answerType: ANSWER_TYPE) {

    private val LocalLock = Mutex()

    val answerType: ANSWER_TYPE = l_answerType

    val localFileSevice = FileService()

    var updateObjectInfo: ((v: Any?) -> Any?) = {}

    fun SetCallBackUpdate(lupdateObjectInfo: ((v: Any?) -> Any?)? = null){
        updateObjectInfo = lupdateObjectInfo?:{}
    }

    fun merge(v: ANSWER_TYPE){
        answerType.merge(v)
        updateObjectInfo(this)
    }



    companion object {

    }
}
