/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_client

import CrossPlatforms.WriteExceptionIntoFile
import atomic.AtomicBoolean
import atomic.AtomicLong
import com.soywiz.kds.Queue
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import io.ktor.util.collections.*
import io.ktor.utils.io.core.*
import io.ktor.utils.io.core.internal.*
import kotlinx.coroutines.*
import lib_exceptions.exc_universal_exception.returnException
import p_jsocket.*
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 *
 * @author User
 */


@InternalAPI
@ExperimentalTime
val OUT_JSOCKETS: ConcurrentMap<Long, Jsocket> = ConcurrentMap()

var newConnectionCoocki = AtomicLong(0L)

@InternalAPI
@ExperimentalTime
private val CLIENT_JSOCKET_POOL: Queue<Jsocket> = Queue()
private val isInterrupted = AtomicBoolean(false)

@InternalAPI
private val lock = Lock()



@JsName("Jsocket")
@InternalAPI
class Jsocket : JSOCKET
{
    private constructor():super()

    @JsName("onRequestListener")
    var onRequestListener: OnRequestListener? = null


    @JsName("clientExecutor")
    private var clientExecutor :ClientExecutor = ClientExecutor()

    constructor(onrequestListener: OnRequestListener):this(){
        this.onRequestListener = onrequestListener
    }


    constructor(jsocket: Jsocket):this(){
        super.set_value(jsocket as JSOCKET)
        this.onRequestListener = jsocket.onRequestListener
    }

    constructor(onRequestListener: OnRequestListener?, answer_type: ANSWER_TYPE):this() {
        this.onRequestListener = onRequestListener
        if (ANSWER_TYPEs != null) {
            ANSWER_TYPEs!!.clear()
        } else {
            ANSWER_TYPEs = ArrayDeque()
        }
        ANSWER_TYPEs!!.add(answer_type)
    }



    ////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////    
    @KorioExperimentalApi
    @ExperimentalUnsignedTypes
    @KtorExperimentalAPI
    @DangerousInternalIoApi
    @ExperimentalTime
    @InternalAPI
    @JsName("execute")
    fun execute()= JSOCKETScope.async {

            super.just_do_it_label = nowNano()

            if (!InitJsocketJob.isCompleted) {
                InitJsocketJob.join()
            }
            onRequestListener?.startLoading()
            try {
                just_do_it_label = nowNano()
                just_do_it_successfull = "0"
                db_massage = ""
                val command: Command
                if (!Commands.containsKey(just_do_it)) {
                    Sqlite_service.InitializeCommands().join()
                    if (!Commands.containsKey(just_do_it)) {
                        just_do_it_successfull = "5"
                        db_massage = returnException(90032, lang)
                        return@async null
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
                    just_do_it_successfull = "5"
                    db_massage = returnException(90031, lang)
                    return@async null
                } else {
                    if (myDataBaseID.value.isEmpty() || myRequestProfile.value.isEmpty()) {
                        Sqlite_service.InitializeRegData().join()
                    }
                }
                if (command.isForPRO && !isPRO.value) {
                    just_do_it_successfull = "5"
                    db_massage = returnException(90027, lang)
                    return@async null
                }
                if (command.isForAcceptedMAIL && !mailConfirm.value) {
                    just_do_it_successfull = "5"
                    db_massage = returnException(90028, lang)
                    return@async null
                }
                when (just_do_it) {
                    1011000055 -> return@async FileLoader.setTask(this@Jsocket)
                    else -> clientExecutor.execute()
                }
            } catch (ex: Exception) {
                just_do_it_successfull = "5"
                db_massage = ex.toString()
                WriteExceptionIntoFile(ex, "Jsocket.execute")
            }
            return@async null
        }.toPromise(EmptyCoroutineContext)

    ////////////////////////////////////////////////////////////////////////////////        
    @ExperimentalIoApi
    @ExperimentalTime
    @InternalAPI
    fun setValue(myJsocket: Jsocket?) {
        if (myJsocket != null) {
            super.set_value(myJsocket as JSOCKET)
            onRequestListener = myJsocket.onRequestListener
        }
    }

    @ExperimentalIoApi
    @ExperimentalTime
    @InternalAPI
    fun setValue(myJSOCKET: JSOCKET?) {
        if (myJSOCKET != null) {
            super.set_value(myJSOCKET)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    private fun notifyHaveAnswer() {
        onRequestListener?.finishLoading()
    }

    @InternalAPI
    @JsName("isHaveAnswer")
    fun isHaveAnswer(Ljust_do_it_label: Long?): Boolean {
        return OUT_JSOCKETS.containsKey(Ljust_do_it_label)
    }

    @ExperimentalIoApi
    @InternalAPI
    @ExperimentalTime
    @JsName("getAnswer")
    fun getAnswer(Ljust_do_it_label: Long?) {
        try {
            setValue(OUT_JSOCKETS.remove(Ljust_do_it_label))
            setRequestProfile(request_profile, true)
        } catch (e: Exception) {
            just_do_it_successfull = "5"
            db_massage = e.toString()
        }
    }

    @ExperimentalTime
    @InternalAPI
    @JsName("setAnswer")
    fun setAnswer() {
        just_do_it_label.let { OUT_JSOCKETS.put(it, this) }
        notifyHaveAnswer()
    }

    ////////////////////////////////////////////////////////////////////////////////
    @KorioExperimentalApi
    @JsName("setAvatar")
    fun setAvatar(file_name: String):Promise<Unit> {
        return JSOCKETScope.launch {
            try {
                val f = FileService()
                content = f.getImmageAvatarFromFileName(file_name.trim())
                f.close()
                value_par1 = file_name.trim()
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Jsocket.setAvatar")
            }
        }.toPromise(EmptyCoroutineContext)
    }

    ///////////////////////////////////////////////////////////////////////////
    companion object {
        private var countOfCleaner = 0
        private var CurentTime = DateTime.nowUnixLong()
        private const val timeOutPointer = 0L

        private val Cleaner = KorosTimerTask.start(delay = Duration.milliseconds(TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES), repeat = Duration.milliseconds(TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES)){removeOldAll()}



        @ExperimentalTime
        @InternalAPI
        private var LastTimeCleanOutJSOCKETs = nowNano()
        private var NextTimeCleanOUT_JSOCKETs = CurentTime + TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES

        @ExperimentalTime
        @InternalAPI
        fun removeOldAll() {
            try {
                CurentTime = DateTime.nowUnixLong()
                if (CurentTime > NextTimeCleanOUT_JSOCKETs) {
                    NextTimeCleanOUT_JSOCKETs = CurentTime + TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES
                    countOfCleaner = 0
                    OUT_JSOCKETS.filterKeys { k -> k < LastTimeCleanOutJSOCKETs}.forEach {
                        countOfCleaner += 1
                        OUT_JSOCKETS.remove(it.key)
                    }
                    LastTimeCleanOutJSOCKETs = nowNano()
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Jsocket.removeOldAll")
            }
        }
        ////////////////////////////////////////////////////////////////////////////////

        @JsName("getANSWER_TYPE")
        @InternalAPI
        suspend fun getJsocket(): Jsocket {
            val jsocket:Jsocket? =
                try {
                    withTimeoutOrNull(CLIENT_TIMEOUT) {
                        lock.lock()
                        if (CLIENT_JSOCKET_POOL.peek() == null) {
                            CoroutineScope(NonCancellable).launch {
                                Jsocket.fill()
                            }
                            return@withTimeoutOrNull Jsocket()
                        } else {
                            return@withTimeoutOrNull CLIENT_JSOCKET_POOL.dequeue()
                        }
                    }
                } catch(ex: Exception) {
                    CoroutineScope(NonCancellable).launch {
                        Jsocket.fill()
                    }
                    return Jsocket()
                }finally {
                    lock.unlock()
                }
            return jsocket?:Jsocket()
        }

        private fun fill() {
            while (CLIENT_JSOCKET_POOL.size < CLIENT_JSOCKET_POOL_SIZE && !p_jsocket.isInterrupted.value) {
                CLIENT_JSOCKET_POOL.enqueue(Jsocket())
            }
        }

        //////////////////////////////////////////////////////////////////

        @InternalAPI
        @JsName("setLang")
        fun setLang(lLang: String) {
            try {
                val v = myLang.getAndSet(lLang)
                if (!v.equals(myLang.value, true)) {
                    Sqlite_service.InsertRegData()
                }
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Jsocket.setLang")
            }
        }

        @InternalAPI
        @JsName("setMyDataBaseID")
        fun setMyDataBaseID(lmyId: String, update_cash: Boolean) {
            if (lmyId.trim().length == 18) {
                val s = myDataBaseID.getAndSet(lmyId.trim())
                if (!s.equals(myDataBaseID.value, true) && update_cash) {
                    Sqlite_service.InsertRegData()
                }
            }
        }

        @InternalAPI
        @JsName("setRequestProfile")
        fun setRequestProfile(lrequestProfile: String, update_cash: Boolean) {
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

        @KtorExperimentalAPI
        @DangerousInternalIoApi
        @ExperimentalTime
        @InternalAPI
        @JsName("closeClient")
        fun closeClient() {
            OUT_JSOCKETS.clear()
            Cleaner.shutdown()
            Listener.get_Instance()?.isInterrupted?.setNewValue(true)
        }
    }

}