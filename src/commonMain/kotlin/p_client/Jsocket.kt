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

    val getLocalsValues = GetLocalsValues()

    constructor(l_startLoading: (() -> Any?)? = null, l_finishLoading: ((v: Any?) -> Any?)? = null) : super(){
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
    fun execute(l_startLoading:(() -> Any?)? = null, l_finishLoading:((v: Any?) -> Any?)? = null):Promise<Any?> = JSOCKETScope.async {

        if (!InitJsocketJob.isCompleted) {
            InitJsocketJob.join()
        }

        startLoading = l_startLoading
        finishLoading = l_finishLoading

        startLoading?.let { it() }

        try {
            try {
                val command: Command
                if (!Commands.containsKey(just_do_it)) {
                    Sqlite_service.InitializeCommands().join()
                    if (!Commands.containsKey(just_do_it)) {
                        throw my_user_exceptions_class(
                            l_class_name = "Jsocket",
                            l_function_name = "execute",
                            name_of_exception = "EXC_WRSOCKETTYPE_NOT_FOUND_COMMAND",
                            l_additional_text = "just_do_it not found $just_do_it"
                        )
                    } else {
                        command = Commands[just_do_it]!!
                    }
                } else {
                    command = Commands[just_do_it]!!
                }
                if ((command.commands_access != "2"
                            && command.commands_access != "9")
                    && myConnectionsCoocki.value == 0L
                ) {
                    throw my_user_exceptions_class(
                        l_class_name = "Jsocket",
                        l_function_name = "execute",
                        name_of_exception = "EXC_CONNECTION_COOCKI_IS_WRONG",
                        l_additional_text = "coocki equel null"
                    )
                } else {
                    if (myConnectionsCoocki.value == 0L) {
                        Sqlite_service.InitializeRegData().join()
                    }
                }
                if (command.isForPRO && !isPRO.value) {
                    throw my_user_exceptions_class(
                        l_class_name = "Jsocket",
                        l_function_name = "execute",
                        name_of_exception = "EXC_THIS_COMMAND_ONLY_FOR_PRO",
                    )
                }
                if (command.isForAcceptedMAIL && !mailConfirm.value) {
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
        }finally {
            finishLoading?.let {it(this@Jsocket)}
        }
        return@async this@Jsocket
    }.toPromise(EmptyCoroutineContext)

    ////////////////////////////////////////////////////////////////////////////////


    suspend fun send_request(craete_check_sum: Boolean = false, verify_fields: Boolean = true) {
        this.serialize(craete_check_sum, verify_fields).let { Connection.sendData(it, this) }
        if(condition.cAwait(Constants.CLIENT_TIMEOUT)){
            if (Commands[just_do_it]?.whichBlobDataReturned == "4") {
                deserialize_ANSWERS_TYPES()
            }
        }else{
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

    ////////////////////////////////////////////////////////////////////////////////


    @JsName("setAvatar")
    suspend fun setAvatar(file_name: String){
            try {
                val f = FileService()
                content = f.getImmageAvatarFromFileName(file_name.trim())
                f.close()
                value_par1 = file_name.trim()
            } catch (e: my_user_exceptions_class) {
                throw e
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Jsocket",
                    l_function_name = "setAvatar",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
            }
    }

    ///////////////////////////////////////////////////////////////////////////

    companion object {


        fun fill() {
            if (fillPOOL_IS_RUNNING.compareAndSet(expected = false, new = true)) {
                CoroutineScope(NonCancellable).launch {
                    withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                        try {
                            fillPOOL_IS_RUNNING.value = true
                            JsocketLock.lock()
                            while (CLIENT_JSOCKET_POOL.size < Constants.CLIENT_JSOCKET_POOL_SIZE && !Constants.isInterrupted.value) {
                                CLIENT_JSOCKET_POOL.addLast(Jsocket())
                            }
                        } finally {
                            fillPOOL_IS_RUNNING.value = false
                            JsocketLock.unlock()
                        }
                    }
                }
            }

            //////////////////////////////////////////////////////////////////


            @JsName("setLang")
            suspend fun setLang(lLang: String) {
                try {
                    val v = myLang.getAndSet(lLang)
                    if (!v.equals(myLang.value, true)) {
                        Sqlite_service.InsertRegData()
                    }
                } catch (e: my_user_exceptions_class) {
                    throw e
                }catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "Jsocket",
                        l_function_name = "setLang",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.message
                    )
                }
            }


            @JsName("setRequestProfile")
            suspend fun setRequestProfile(lrequestProfile: String, update_cash: Boolean) {
                try {
                    if (lrequestProfile.trim().isNotEmpty()
                        && lrequestProfile.trim() != "------------------------------"
                    ) {
                        myRequestProfile.getAndSet(lrequestProfile.trim())
                        isPRO.setNewValue(myRequestProfile.value.substring(0, 1) == "1")
                        mailConfirm.setNewValue(myRequestProfile.value.substring(2, 3) == "1")
                        if (update_cash) {
                            Sqlite_service.InsertRegData()
                        }
                    }
                } catch (ex: Exception) {
                }
            }

        }
    }
}