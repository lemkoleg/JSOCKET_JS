/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_client


import Tables.*
import atomic.AtomicLong
import co.touchlab.stately.concurrency.AtomicBoolean
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.*
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

/**
 *
 * @author User
 */


@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
var newConnectionCoocki = AtomicLong(0L)

@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
val CLIENT_JSOCKET_POOL: ArrayDeque<Jsocket> = ArrayDeque()


@InternalAPI
private val JsocketLock = Mutex()

private var fillPOOL_IS_RUNNING: AtomicBoolean = AtomicBoolean(false)


@JsName("Jsocket")
@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
class Jsocket : JSOCKET, OnRequestListener, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    val JSOCKETScope = CoroutineScope(coroutineContext)

    //val InstanceRef:AtomicReference<Jsocket> = AtomicReference(this)

    override var startLoading: (() -> Any?)?
    override var finishLoading: ((v: Any?) -> Any?)?

    @JsName("clientExecutor")
    private var clientExecutor: ClientExecutor = ClientExecutor()

    var FileFullPathForSend: String = ""
    var AvatarFullPathForSend: String = ""

    val getLocalsValues = GetLocalsValues()

    constructor(l_startLoading: (() -> Any?)? = null, l_finishLoading: ((v: Any?) -> Any?)? = null) : super() {
        startLoading = l_startLoading
        finishLoading = l_finishLoading
        ensureNeverFrozen()

    }


    constructor(jsocket: Jsocket) : this() {
        super.set_value(jsocket as JSOCKET)
        this.startLoading = jsocket.startLoading
        this.finishLoading = jsocket.finishLoading
        ensureNeverFrozen()

    }


    ////////////////////////////////////////////////////////////////////////////////

    @KorioExperimentalApi
    @JsName("execute")
    fun execute(l_startLoading: (() -> Any?)? = null, l_finishLoading: ((v: Any?) -> Any?)? = null): Promise<Any?> =
        JSOCKETScope.async {

            if (!InitJsocketJob.isCompleted) {
                InitJsocketJob.join()
            }

            startLoading = l_startLoading
            finishLoading = l_finishLoading

            startLoading?.let { it() }

            try {
                try {
                    val command: Command
                    if (!COMMANDS.containsKey(just_do_it)) {
                        Sqlite_service.InitializeCommands().join()
                        if (!COMMANDS.containsKey(just_do_it)) {
                            throw my_user_exceptions_class(
                                l_class_name = "Jsocket",
                                l_function_name = "execute",
                                name_of_exception = "EXC_WRSOCKETTYPE_NOT_FOUND_COMMAND",
                                l_additional_text = "just_do_it not found $just_do_it"
                            )
                        } else {
                            command = COMMANDS[just_do_it]!!
                        }
                    } else {
                        command = COMMANDS[just_do_it]!!
                    }
                    if ((command.commands_access != "2"
                                && command.commands_access != "9")
                        && myConnectionsCoocki == 0L
                    ) {
                        throw my_user_exceptions_class(
                            l_class_name = "Jsocket",
                            l_function_name = "execute",
                            name_of_exception = "EXC_CONNECTION_COOCKI_IS_WRONG",
                            l_additional_text = "coocki equel null"
                        )
                    } else {
                        if (myConnectionsCoocki == 0L) {
                            Sqlite_service.InitializeRegData().join()
                        }
                    }
                    if (command.isForPRO && !isPRO) {
                        throw my_user_exceptions_class(
                            l_class_name = "Jsocket",
                            l_function_name = "execute",
                            name_of_exception = "EXC_THIS_COMMAND_ONLY_FOR_PRO",
                        )
                    }
                    if (command.isForAcceptedMAIL && !mailConfirm) {
                        throw my_user_exceptions_class(
                            l_class_name = "Jsocket",
                            l_function_name = "execute",
                            name_of_exception = "EXC_WRSOCKETTYPE_MAIL_NOT_CONFIRM",
                        )
                    }
                    when (just_do_it) {
                        1011000024 -> { //PLAY MEDIA;
                            val f = FileService(this@Jsocket)
                            f.open_file_channel()
                            finishLoading = null
                            return@async f
                        }
                        else -> clientExecutor.execute(this@Jsocket)
                    }

                } catch (ex: my_user_exceptions_class) {
                    throw ex
                } catch (e: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "Jsocket",
                        l_function_name = "execute",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = e.message
                    )

                }
            } catch (ex: my_user_exceptions_class) {
                ex.ExceptionHand(this@Jsocket)
                return@async this@Jsocket
            } finally {
                finishLoading?.let { it(this@Jsocket) }
            }
            return@async this@Jsocket
        }.toPromise(EmptyCoroutineContext)

    ////////////////////////////////////////////////////////////////////////////////


    suspend fun send_request(craete_check_sum: Boolean = false, verify_fields: Boolean = true) {
        is_new_reg_data = false
        this.serialize(craete_check_sum, verify_fields).let { Connection.sendData(it, this) }
        if (condition.cAwait(Constants.CLIENT_TIMEOUT)) {
            if (COMMANDS[just_do_it]?.whichBlobDataReturned == "4") {
                deserialize_ANSWERS_TYPES()
            }
            if (is_new_reg_data) {
                KRegData.setNEW_REG_DATA(this)
            }
        } else {
            throw my_user_exceptions_class(
                l_class_name = "Jsocket",
                l_function_name = "send_request",
                name_of_exception = "EXC_SERVER_IS_NOT_RESPONDING",
            )
        }
    }


    ////////////////////////////////////////////////////////////////////////////////

    @JsName("isHaveAnswer")
    fun isHaveAnswer(): Boolean {
        return condition.isAwaited.value
    }

    ///////////////////////////////////////////////////////////////////////////////

    companion object {


        fun fill() {
            if (fillPOOL_IS_RUNNING.compareAndSet(expected = false, new = true)) {
                CoroutineScope(NonCancellable).launch {
                    withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                        try {
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                println("Jsocet's fill pool is run...")
                            }
                            try {
                                fillPOOL_IS_RUNNING.value = true
                                JsocketLock.lock()
                                while (CLIENT_JSOCKET_POOL.size < Constants.CLIENT_JSOCKET_POOL_SIZE && !Constants.isInterrupted.value) {
                                    CLIENT_JSOCKET_POOL.addLast(Jsocket())
                                }
                            } catch (ex: Exception) {
                                throw my_user_exceptions_class(
                                    l_class_name = "Jsocket",
                                    l_function_name = "fill",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    ex.message
                                )
                            } finally {
                                fillPOOL_IS_RUNNING.value = false
                                JsocketLock.unlock()
                            }
                        } catch (e: my_user_exceptions_class) {
                            e.ExceptionHand(null)
                        }
                    }
                }
            }

            //////////////////////////////////////////////////////////////////


            @JsName("setLang")
            suspend fun setLang(lLang: String) {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            JsocketLock.lock()
                            if (!lLang.equals(myLang)) {
                                myLang = lLang
                                KRegData.setNEW_REG_DATA()
                            }
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "Jsocket",
                                l_function_name = "fill",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                ex.message
                            )
                        } finally {
                            JsocketLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                }
            }
        }
    }
}