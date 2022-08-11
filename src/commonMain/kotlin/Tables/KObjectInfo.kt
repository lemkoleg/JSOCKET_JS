package Tables

import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
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

    var answerType: ANSWER_TYPE = l_answerType

    val localFileSevice = FileService()

    var updateObjectInfo: ((v: Any?) -> Any?)? = null

    init {
        ensureNeverFrozen()
        if(answerType.RECORD_TABLE_ID.isEmpty()){
            throw my_user_exceptions_class(
                l_class_name = "KObjectInfo",
                l_function_name = "constructor",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Object Id is empty"
            )
        }

        if(OBJECTS_INFO.containsKey(answerType.RECORD_TABLE_ID)){
            throw my_user_exceptions_class(
                l_class_name = "KObjectInfo",
                l_function_name = "constructor",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Object is already loaded"
            )
        }
        OBJECTS_INFO[answerType.RECORD_TABLE_ID]
    }

    fun SetCallBackUpdate(lupdateObjectInfo: ((v: Any?) -> Any?)? = null){
        updateObjectInfo = lupdateObjectInfo
    }

    fun UpdateObjectInfo{

    }

    companion object {

    }
}
