@file:Suppress("unused")

package Tables

import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withTimeout
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@InternalAPI
private val KCommandsLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val NEW_COMMANDS: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KCommands")
class KCommands {

    init {
        ensureNeverFrozen()
    }


    private var COMMANDS_ID: Int = 0
    private var COMMANDS_ACCESS: String = ""
    private var COMMANDS_PROFILE: String = ""
    private var COMMANDS_NECESSARILY_FIELDS: String = ""
    private var LAST_UPDATE: Long = 0
    private var COUNT_OF_EXECUTE: Int = 0

    private constructor()

    constructor(
        L_COMMANDS_ID: Int,
        L_COMMANDS_ACCESS: String,
        L_COMMANDS_PROFILE: String,
        L_OMMANDS_NECESSARILY_FIELDS: String,
        L_LAST_UPDATE: Long,
        L_COUNT_OF_EXECUTE: Int
    ) {

        COMMANDS_ID = L_COMMANDS_ID
        COMMANDS_ACCESS = L_COMMANDS_ACCESS
        COMMANDS_PROFILE = L_COMMANDS_PROFILE
        COMMANDS_NECESSARILY_FIELDS = L_OMMANDS_NECESSARILY_FIELDS
        LAST_UPDATE = L_LAST_UPDATE
        COUNT_OF_EXECUTE = L_COUNT_OF_EXECUTE

    }

    constructor(ans: ANSWER_TYPE) : this(
        ans.LONG_1!!.toInt(),
        ans.STRING_1!!,
        ans.STRING_2!!,
        ans.STRING_3!!,
        ans.LONG_2!!,
        ans.LONG_3!!.toInt()
    )

    @JsName("getCOMMANDS_ID")
    fun getCOMMANDS_ID(): Int {
        return COMMANDS_ID
    }

    @JsName("setCOMMANDS_ID")
    fun setCOMMANDS_ID(v: Int) {
        COMMANDS_ID = v
    }

    @JsName("getCOMMANDS_ACCESS")
    fun getCOMMANDS_ACCESS(): String {
        return COMMANDS_ACCESS
    }

    @JsName("setCOMMANDS_ACCESS")
    fun setCOMMANDS_ACCESS(v: String) {
        COMMANDS_ACCESS = v
    }

    @JsName("getCOMMANDS_PROFILE")
    fun getCOMMANDS_PROFILE(): String {
        return COMMANDS_PROFILE
    }

    @JsName("setCOMMANDS_PROFILE")
    fun setCOMMANDS_PROFILE(v: String) {
        COMMANDS_PROFILE = v
    }

    @JsName("getCOMMANDS_NECESSARILY_FIELDS")
    fun getCOMMANDS_NECESSARILY_FIELDS(): String {
        return COMMANDS_NECESSARILY_FIELDS
    }

    @JsName("setCOMMANDS_NECESSARILY_FIELDS")
    fun setCOMMANDS_NECESSARILY_FIELDS(v: String) {
        COMMANDS_NECESSARILY_FIELDS = v
    }

    @JsName("getLAST_UPDATE")
    fun getLAST_UPDATE(): Long {
        return LAST_UPDATE
    }

    @JsName("setLAST_UPDATE")
    fun setLAST_UPDATE(v: Long) {
        LAST_UPDATE = v
    }

    @JsName("getCOUNT_OF_EXECUTE")
    fun getCOUNT_OF_EXECUTE(): Int {
        return COUNT_OF_EXECUTE
    }

    @JsName("setCOUNT_OF_EXECUTE")
    fun setCOUNT_OF_EXECUTE(v: Int) {
        COUNT_OF_EXECUTE = v
    }

    @JsName("UPDATE_COMMAND")
    fun UPDATE_COMMAND(
        L_COMMANDS_ID: Int,
        L_COMMANDS_ACCESS: String,
        L_COMMANDS_PROFILE: String,
        L_OMMANDS_NECESSARILY_FIELDS: String,
        L_LAST_UPDATE: Long,
        L_COUNT_OF_EXECUTE: Int
    ) {

        COMMANDS_ID = L_COMMANDS_ID
        COMMANDS_ACCESS = L_COMMANDS_ACCESS
        COMMANDS_PROFILE = L_COMMANDS_PROFILE
        COMMANDS_NECESSARILY_FIELDS = L_OMMANDS_NECESSARILY_FIELDS
        LAST_UPDATE = L_LAST_UPDATE
        COUNT_OF_EXECUTE = L_COUNT_OF_EXECUTE

    }

    @JsName("UPDATE_COMMANDS")
    fun UPDATE_COMMANDS(ans: ANSWER_TYPE) {
        UPDATE_COMMAND(
            ans.LONG_1!!.toInt(),
            ans.STRING_1!!,
            ans.STRING_2!!,
            ans.STRING_3!!,
            ans.LONG_2!!,
            ans.LONG_3!!.toInt()
        )
    }

    companion object {

        @KorioExperimentalApi
        @JsName("ADD_NEW_COMMANDS")
        fun ADD_NEW_COMMANDS():Promise<Boolean> = CoroutineScope(Dispatchers.Default).async {
            withTimeout(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KCommandsLock.lock()
                        while (NEW_COMMANDS.isNotEmpty()) {
                            TODO()
                        }
                        return@withTimeout true
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCommands",
                            l_function_name = "ADD_NEW_COMMANDS",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KCommandsLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeout false
            }
        }.toPromise(EmptyCoroutineContext)
    }

}