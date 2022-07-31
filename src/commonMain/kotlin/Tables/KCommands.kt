@file:Suppress("unused")

package Tables

import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.async
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Command
import p_jsocket.Constants
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@InternalAPI
private val KCommandsLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val NEW_COMMANDS: ArrayDeque<ANSWER_TYPE> = ArrayDeque()


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
@JsName("COMMANDS")
val COMMANDS: MutableMap<Int, Command> = mutableMapOf(
    1011000010 to Command(
        1011000010, "9", "111010001410000000000000000000",
        "202000111222000020000012222202222000000000000000000000000000"
    ), // RESTORE_PASSWORD
    1011000026 to Command(
        1011000026, "9", "111010001410000000000000000000",
        "202000111222000020002212222202222000000000000000000000000000"
    ), // INSERT_ACCOUNT
    1011000027 to Command(
        1011000027, "2", "111011001410000000000000000000",
        "202000111222000000000011222202022000000000000000000000000000"
    ),// CONNECT_ACCOUNT
    1011000049 to Command(
        1011000049, "2", "011000000000000000000000000000",
        "002000111222000000000020022002022000000000000000000000000000"
    ), // RE_SEND_MAIL_CONFIRM_CODE
    1011000061 to Command(
        1011000061, "3", "011011001401000000000000000000",
        "112000111220000000000000022202022000000000000000000000000000"
    ), // SELECT_COMMANDS
    1011000069 to Command(
        1011000069, "5", "111100101000010000000000000000",
        "102000111220000000000000022202220000000000000000000000000000"
    )// QUIT FROM CLIENT
)

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

        @JsName("ADD_NEW_COMMANDS")
        fun ADD_NEW_COMMANDS(): Promise<Boolean> = CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    val arr: ArrayList<KCommands> = ArrayList()
                    try {
                        KCommandsLock.lock()
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            println("ADD_NEW_COMMANDS is running")
                        }
                        while (NEW_COMMANDS.isNotEmpty()) {
                            val anwer_type = NEW_COMMANDS.removeFirst()
                            if (anwer_type.RECORD_TYPE.equals("1")) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KCommands",
                                    l_function_name = "ADD_NEW_COMMANDS",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "Record is not Command"
                                )
                            }
                            val k = KCommands(anwer_type)
                            val c = Command(k)
                            meta_data_last_update.setGreaterValue(k.LAST_UPDATE)
                            COMMANDS[c.commands_id] = c
                            arr.add(k)
                        }
                        return@withTimeoutOrNull true
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KCommands",
                            l_function_name = "ADD_NEW_COMMANDS",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KCommandsLock.unlock()
                        if (!arr.isEmpty()) {
                            Sqlite_service.InsertCommands(arr)
                        }
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeoutOrNull false
            } ?: false
        }.toPromise(EmptyCoroutineContext)

        @JsName("LOAD_COMMANDS")
        suspend fun LOAD_COMMANDS(ids: ArrayList<KCommands>) {
            try {
                try {
                    KCommandsLock.lock()
                    ids.forEach {
                        val c = Command(it)
                        COMMANDS[c.commands_id] = c
                        meta_data_last_update.setGreaterValue(it.LAST_UPDATE)
                    }
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "KCommands",
                        l_function_name = "LOAD_COMMANDS",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.message
                    )
                } finally {
                    KCommandsLock.unlock()
                }

            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        }

        @JsName("RE_LOAD_COMMANDS")
        fun RE_LOAD_COMMANDS():Job {
            return Sqlite_service.LoadCommands()
        }
    }
}