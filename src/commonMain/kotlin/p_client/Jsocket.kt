/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_client


import CrossPlatforms.PrintInformation
import Tables.*
import atomic.AtomicLong
import co.touchlab.stately.concurrency.AtomicBoolean
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_jsocket.*
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
private val CLIENT_JSOCKET_POOL: ArrayDeque<Jsocket> = ArrayDeque()


@InternalAPI
private val JsocketLock = Mutex()

private var fillPOOL_IS_RUNNING: AtomicBoolean = AtomicBoolean(false)


@JsName("Jsocket")
@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
class Jsocket() : JSOCKET(), OnRequestListener{

    //val InstanceRef:AtomicReference<Jsocket> = AtomicReference(this)

    override var startLoading: (() -> Any?) = {}
    override var finishLoading: ((v: Any?) -> Any?) = {}

    @JsName("clientExecutor")
    private var clientExecutor: ClientExecutor = ClientExecutor()

    var FileFullPathForSend: String = ""
    var AvatarFullPathForSend: String = ""

    val lock = Mutex()

    constructor(l_startLoading: (() -> Any?)? = null, l_finishLoading: ((v: Any?) -> Any?)? = null) : this() {
        startLoading = l_startLoading ?: {}
        finishLoading = l_finishLoading ?: {}
        ensureNeverFrozen()

    }


    constructor(jsocket: Jsocket) : this() {
        super.set_value(jsocket as JSOCKET)
        this.startLoading = jsocket.startLoading
        this.finishLoading = jsocket.finishLoading
        ensureNeverFrozen()

    }


    ////////////////////////////////////////////////////////////////////////////////

    @JsName("execute")
    fun execute(l_startLoading: (() -> Any?)? = null, l_finishLoading: ((v: Any?) -> Any?)? = null): Promise<Any> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async{

            if (!InitJsocketJob.isCompleted) {
                InitJsocketJob.join()
            }

            startLoading = l_startLoading ?: {}
            finishLoading = l_finishLoading ?: {}

            startLoading()

            try {
                try {
                    val command: Command = COMMANDS[just_do_it]
                        ?: throw my_user_exceptions_class(
                            l_class_name = "Jsocket",
                            l_function_name = "execute",
                            name_of_exception = "EXC_WRSOCKETTYPE_NOT_FOUND_COMMAND",
                            l_additional_text = "just_do_it not found $just_do_it"
                        )

                    if ((command.commands_access != "2"
                                && command.commands_access != "9")
                        && Constants.myConnectionsCoocki == 0L
                    ) {
                        throw my_user_exceptions_class(
                            l_class_name = "Jsocket",
                            l_function_name = "execute",
                            name_of_exception = "EXC_CONNECTION_COOCKI_IS_WRONG",
                            l_additional_text = "coocki equel null"
                        )
                    }
                    if (command.isForPRO && !Constants.isPRO) {
                        throw my_user_exceptions_class(
                            l_class_name = "Jsocket",
                            l_function_name = "execute",
                            name_of_exception = "EXC_THIS_COMMAND_ONLY_FOR_PRO",
                        )
                    }
                    if (command.isForAcceptedMAIL && !Constants.mailConfirm) {
                        throw my_user_exceptions_class(
                            l_class_name = "Jsocket",
                            l_function_name = "execute",
                            name_of_exception = "EXC_WRSOCKETTYPE_MAIL_NOT_CONFIRM",
                        )
                    }

                    clientExecutor.execute(this@Jsocket)

                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "Jsocket",
                        l_function_name = "execute",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )

                }
            } catch (ex: my_user_exceptions_class) {
                ex.ExceptionHand(this@Jsocket)
                return@async this@Jsocket
            } finally {
                finishLoading(null)
            }
            return@async this@Jsocket
        }.toPromise(EmptyCoroutineContext)

    ////////////////////////////////////////////////////////////////////////////////


    suspend fun send_request(verify_fields: Boolean = true,
                             await_answer: Boolean = true,
                             update_just_do_it_label: Boolean = true) {
        is_new_reg_data = false
        val command: Command = COMMANDS[just_do_it]!!
        this.serialize(verify_fields, update_just_do_it_label).let { Connection.sendData(it, this) }


        if (!command.isDont_answer && await_answer) {
            if (!condition.cAwait(Constants.CLIENT_TIMEOUT)) {
                if (command.commands_access != "B") {
                    throw my_user_exceptions_class(
                        l_class_name = "Jsocket",
                        l_function_name = "send_request",
                        name_of_exception = "EXC_SERVER_IS_NOT_RESPONDING",
                    )
                }
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////

    @JsName("isHaveAnswer")
    fun isHaveAnswer(): Boolean {
        return condition.isAwaited.value
    }

    ///////////////////////////////////////////////////////////////////////////////

    companion object {

        fun GetJsocket(): Jsocket? {
            if (JsocketLock.tryLock()) {
                try {
                    val l: Jsocket? = CLIENT_JSOCKET_POOL.removeFirstOrNull()
                    if (l == null) {
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            PrintInformation.PRINT_INFO("CLIENT_JSOCKET_POOL is empty")
                        }
                        fill()
                        return null
                    }
                    return l
                } finally {
                    JsocketLock.unlock()
                }
            }
            return null
        }


        fun fill() {
            if (fillPOOL_IS_RUNNING.compareAndSet(expected = false, new = true)) {
                CoroutineScope(NonCancellable).launchImmediately {
                    withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                        try {
                            JsocketLock.withLock {
                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("Jsocet's fill pool is run...")
                                }
                                try {
                                    fillPOOL_IS_RUNNING.value = true

                                    while (CLIENT_JSOCKET_POOL.size < Constants.CLIENT_JSOCKET_POOL_SIZE && !Constants.isInterrupted.value) {
                                        CLIENT_JSOCKET_POOL.addLast(Jsocket())
                                    }
                                } catch (e: my_user_exceptions_class) {
                                    throw e
                                } catch (ex: Exception) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "Jsocket",
                                        l_function_name = "fill",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        ex.stackTraceToString()
                                    )
                                } finally {
                                    fillPOOL_IS_RUNNING.value = false
                                }
                            }
                        } catch (e: my_user_exceptions_class) {
                            e.ExceptionHand(null)
                        }
                    } ?: throw my_user_exceptions_class(
                        l_class_name = "Jsocket",
                        l_function_name = "fill",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "Time out is up"
                    )
                }
            }

            //////////////////////////////////////////////////////////////////


            @JsName("setLang")
            suspend fun setLang(lLang: String) {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            JsocketLock.withLock {
                                if (!lLang.equals(Constants.myLang)) {
                                    Constants.myLang = lLang
                                    KRegData.setNEW_REG_DATA()
                                }
                            }
                        } catch (e: my_user_exceptions_class) {
                            throw e
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "Jsocket",
                                l_function_name = "fill",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                ex.stackTraceToString()
                            )
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                } ?: throw my_user_exceptions_class(
                    l_class_name = "Jsocket",
                    l_function_name = "setLang",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }
        }
    }
}