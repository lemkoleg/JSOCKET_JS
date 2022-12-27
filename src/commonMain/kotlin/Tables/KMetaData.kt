package Tables

import CrossPlatforms.PrintInformation
import atomic.AtomicLong
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val META_DATA: MutableMap<String, Long> = mutableMapOf()


@InternalAPI
private val KMetaDataLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var meta_data_last_update: AtomicLong = AtomicLong(0L)

@Suppress("unused")
@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KMetaData")
class KMetaData {

    init {
        ensureNeverFrozen()
    }

    private var VALUE_NAME: String = ""
    private var VALUE_VALUE: Long = 0L
    private var LAST_UPDATE: Long = 0L

    private constructor()

    constructor(L_VALUE_NAME: String, L_VALUE_VALUE: Long, L_LAST_UPDATE: Long) {
        VALUE_NAME = L_VALUE_NAME
        VALUE_VALUE = L_VALUE_VALUE
        LAST_UPDATE = L_LAST_UPDATE
    }

    constructor(ans: ANSWER_TYPE) : this(ans.STRING_1!!, ans.LONG_1!!, ans.LONG_2!!)

    @JsName("getVALUE_NAME")
    fun getVALUE_NAME(): String {
        return VALUE_NAME
    }

    @JsName("setVALUE_NAME")
    fun setVALUE_NAME(v: String) {
        VALUE_NAME = v.trim()
    }

    @JsName("getVALUE_VALUE")
    fun getVALUE_VALUE(): Long {
        return VALUE_VALUE
    }

    @JsName("setVALUE_VALUE")
    fun setVALUE_VALUE(v: Long?) {
        VALUE_VALUE = v ?: 0L
    }

    @JsName("getLATS_UPDATE")
    fun getLATS_UPDATE(): Long {
        return LAST_UPDATE
    }

    @JsName("setLATS_UPDATE")
    fun setLATS_UPDATE(v: Long?) {
        LAST_UPDATE = v ?: 0L
    }

    @JsName("UPDATE_METADATA")
    fun UPDATE_METADATA(L_VALUE_NAME: String, L_VALUE_VALUE: Long, L_LAST_UPDATE: Long) {
        VALUE_NAME = L_VALUE_NAME
        VALUE_VALUE = L_VALUE_VALUE
        LAST_UPDATE = L_LAST_UPDATE
    }

    @JsName("UPDATE_METADATAS")
    fun UPDATE_METADATAS(ans: ANSWER_TYPE) {
        UPDATE_METADATA(ans.STRING_1!!, ans.LONG_1!!, ans.LONG_2!!)
    }

    companion object {

        @InternalAPI
        @JsName("LOAD_META_DATA")
        suspend fun LOAD_META_DATA(kMetaDatas: ArrayList<KMetaData>) {
            try {
                KMetaDataLock.withLock {
                    try {

                        kMetaDatas.forEach {
                            META_DATA[it.getVALUE_NAME()] = it.getVALUE_VALUE()
                            meta_data_last_update.setGreaterValue(it.LAST_UPDATE)
                        }

                    } catch (e: my_user_exceptions_class) {
                        throw e
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KMetaData",
                            l_function_name = "LOAD_META_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
                    } finally {
                        Constants.initialise()
                    }
                }
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "KMetaData",
                    l_function_name = "LOAD_META_DATA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.stackTraceToString()
                )
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        }


        @KorioExperimentalApi
        @JsName("ADD_NEW_META_DATA")
        fun ADD_NEW_META_DATA(arr: ArrayDeque<ANSWER_TYPE>): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        KMetaDataLock.withLock {
                            val met: ArrayList<KMetaData> = ArrayList()
                            try {


                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("ADD_NEW_META_DATA is running")
                                }

                                arr.forEach {
                                    if (!it.RECORD_TYPE.equals("2")) {
                                        throw my_user_exceptions_class(
                                            l_class_name = "KMetaData",
                                            l_function_name = "ADD_NEW_META_DATA",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "Record is not meta_data"
                                        )
                                    }
                                    val k = KMetaData(it)
                                    meta_data_last_update.setGreaterValue(k.LAST_UPDATE)
                                    META_DATA[k.VALUE_NAME] = k.VALUE_VALUE
                                    met.add(k)
                                }
                                return@withTimeoutOrNull true
                            } catch (e: my_user_exceptions_class) {
                                throw e
                            } catch (ex: Exception) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KMetaData",
                                    l_function_name = "ADD_NEW_META_DATA",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = ex.stackTraceToString()
                                )
                            } finally {
                                if (!arr.isEmpty()) {
                                    Sqlite_service.InsertMetaData(met)
                                }
                                Constants.initialise()
                            }
                        }
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KMetaData",
                            l_function_name = "ADD_NEW_META_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.stackTraceToString()
                        )
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeoutOrNull false
                } ?: throw my_user_exceptions_class(
                    l_class_name = "KMetaData",
                    l_function_name = "ADD_NEW_META_DATA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }.toPromise(EmptyCoroutineContext)

        @JsName("RE_LOAD_META_DATA")
        fun RE_LOAD_META_DATA(): Job {
            return Sqlite_service.LoadMetaData()
        }
    }

}