/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package p_client

import CrossPlatforms.WriteExceptionIntoFile
import Tables.KChat
import atomic.AtomicBoolean
import com.soywiz.kds.Queue
import com.soywiz.klock.DateTime
import io.ktor.util.InternalAPI
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.collections.ConcurrentMap
import io.ktor.utils.io.core.ExperimentalIoApi
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import io.ktor.utils.io.core.isNotEmpty
import kotlinx.coroutines.*
import lib_exceptions.exc_channel_already_closed
import lib_exceptions.exc_not_connecting_to_web
import lib_exceptions.exc_socket_not_allowed
import lib_exceptions.exc_universal_exception.returnException
import p_jsocket.*
import sql.Sqlite_service
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

const val ListenerQUEUESize = 1000
const val maxTimeForWaitOutPut = 2000L
const val maxTimeForWaitInPut = 200L
const val minTimeForWait = 100L


/**
 *
 * @author Oleg
 */
@KtorExperimentalAPI
@ExperimentalTime
@ExperimentalIoApi
@InternalAPI
@ExperimentalStdlibApi
@DangerousInternalIoApi
@JsName("Listener")
class Listener
private constructor() {
    private val ListenerJob: Job
    @ExperimentalStdlibApi
    private var jsocket: Jsocket? = null
    private var CurentTime: Long = DateTime.nowUnixLong()
    private var TimeOutPointer: Long = 0L
    private var NextTimeCleanBetWeenJSOCKETs = CurentTime
    private var LastTimeCleanOutJSOCKETs = nowNano()
    private var CountOfCleaner = 0
    private var current_position = 0
    val isInterrupted = AtomicBoolean(false)
    private val commandByteArray = ByteArray(4)
    private var timeVerifyMesseges = DateTime.nowUnixLong() + TIME_OUT_VERIFY_MESSEGES
    @KtorExperimentalAPI
    @ExperimentalTime
    @ExperimentalIoApi
    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    @InternalAPI
    private fun execute() =
        CoroutineScope(NonCancellable).launch {
        while (!isInterrupted.value) {
            //println("run listener connection_coocki ${myConnectionsCoocki.value}")
            jsocket = null
            FileLoader.getTask()
            try {
                jsocket = InJSOCKETs.dequeue(if (BetweenJSOCKETs.isEmpty()) {
                    maxTimeForWaitOutPut
                } else {
                    minTimeForWait
                })
            } catch (ex: Exception) {
                WriteExceptionIntoFile(ex, "Listener.InJSOCKETs.dequeue")
            }
            if (jsocket != null) {
                connection = null
                try {
                    connection = Connections[jsocket!!.just_do_it_label]
                    if (connection == null) {
                        connection = Connections[1L]
                    }
                    if (connection == null || !connection!!.isConnected()) {
                        connection = Connection(SERVER_DNS_NAME)
                        Connections[1L] = connection!!
                    }
                    jsocket!!.ANSWER_TYPEs?.clear()
                    jsocket!!.connection_id = myConnectionsID.value
                    jsocket!!.connection_coocki = myConnectionsCoocki.value
                    jsocket!!.device_id = myDeviceId
                    if (!Commands[jsocket!!.just_do_it]?.canChangeCoocki!!) {
                        jsocket!!.just_do_it_label = nowNano()
                    }
                    jsocket!!.lang = myLang.value
                    jsocket!!.last_messege_update = KChat.globalLastUpdatingDate.value
                    jsocket!!.db_massage = ""
                    jsocket!!.just_do_it_successfull = "0"
                    jsocket!!.connection_context = myConnectionContext.value
                    println("jsocket.value_par7 ${jsocket!!.value_par7}")
                    println("jsocket.value_par8 ${jsocket!!.value_par8}")
                    println("jsocket.value_par9 ${jsocket!!.value_par9}")
                    println("jsocket.just_do_it sended ${jsocket!!.just_do_it}")
                    val data = jsocket!!.serialize(craete_check_sum = false, verify_fields = true)
                    if(data != null && data.size > 0) {
                        timeVerifyMesseges = DateTime.nowUnixLong() + TIME_OUT_VERIFY_MESSEGES
                        connection!!.send_request(data.build())
                        BetweenJSOCKETs[jsocket!!.just_do_it_label] = jsocket!!
                    }
                } catch (e1: exc_socket_not_allowed) {
                    jsocket!!.just_do_it_successfull = "8"
                    jsocket!!.db_massage = returnException(90003, jsocket!!.lang)
                    OutJSOCKETs[jsocket!!.just_do_it_label] = jsocket!!
                    jsocket!!.condition.cSignal()
                } catch (e2: exc_not_connecting_to_web) {
                    jsocket!!.just_do_it_successfull = "9"
                    jsocket!!.db_massage = returnException(90030, jsocket!!.lang)
                    OutJSOCKETs[jsocket!!.just_do_it_label] = jsocket!!
                    jsocket!!.condition.cSignal()
                } catch (ex: Exception) {
                    jsocket!!.just_do_it_successfull = "7"
                    jsocket!!.db_massage = ex.toString()
                    OutJSOCKETs[jsocket!!.just_do_it_label] = jsocket!!
                    jsocket!!.condition.cSignal()
                    WriteExceptionIntoFile(ex, "Listener.send")
                }
                finally {
                    jsocket = null
                }
            }
            val keys = Connections.keys
            keys.forEach {
                try {
                    val conn = Connections[it]
                    jsocket = null
                    val b = conn!!.read_request(if (!InJSOCKETs.isEmpty() || Connections.size > 1) {
                        minTimeForWait
                    } else {
                        maxTimeForWaitInPut
                    })
                    if (b != null && b.isNotEmpty) {
                        val jsocketRet = Jsocket(b, myConnectionsCoocki.value, true, newConnectionCoocki.value)
                        if (jsocketRet.just_do_it != 0) {
                            jsocket = BetweenJSOCKETs.remove(jsocketRet.just_do_it_label)
                            jsocketRet.onRequestListener = jsocket!!.onRequestListener
                            when (jsocketRet.just_do_it) {
                                1011000069 -> {
                                    myConnectionsID.setNewValue(0L)
                                    myConnectionsCoocki.setNewValue(0L)
                                    Sqlite_service.ClearRegData()
                                    jsocketRet.just_do_it_successfull = "4"
                                    jsocketRet.db_massage = returnException(90031, jsocketRet.lang)
                                }
                                1011000055 -> {
                                    Connections[1L]?.let { it1 -> FileLoader.executeTask(jsocketRet, it1) }
                                }
                                1011000086 -> {
                                    if(jsocket!!.just_do_it != 1011000068) { // RE_SEND_REQUEST_PROFILE
                                        InJSOCKETs.enqueue(jsocket!!, ListenerQUEUESize, "Listener.InJSOCKETs")
                                    }
                                    Sqlite_service.InitializeUpdatesChats(jsocketRet)
                                }
                                else -> {
                                    OutJSOCKETs[jsocketRet.just_do_it_label] = jsocketRet

                                }
                            }
                        }
                    }
                } catch (e: NullPointerException) {
                } catch (e: exc_channel_already_closed) {
                    try {
                        Connections.remove(it)?.close()
                    } catch (e1: Exception) {
                    }
                } catch (ex: Exception) {
                    WriteExceptionIntoFile(ex, "Listener.read")
                } finally {
                    jsocket?.condition?.cSignal()
                    current_position = 0
                    jsocket = null
                }
            }
            cleaner()
        }
    }


    ////////////////////////////////////////////////////////////////////////////////   
    @ExperimentalIoApi
    private fun cleaner() {
        CurentTime = DateTime.nowUnixLong()
        if(CurentTime > timeVerifyMesseges){
            timeVerifyMesseges = CurentTime + TIME_OUT_VERIFY_MESSEGES
            if(myConnectionsCoocki.value > 0 && Commands.containsKey(1011000068)){ //RE_SEND_REQUEST_PROFILE
                val j = Jsocket()
                j.just_do_it = 1011000068
                InJSOCKETs.enqueue(j)
            }
        }
        if (CurentTime > NextTimeCleanBetWeenJSOCKETs)
        {
            val l = nowNano()
            NextTimeCleanBetWeenJSOCKETs = CurentTime + 60000 //1 minute
            removeOldBetWeenJSOCKETsAll()
            removeOldOutJSOCKETsAll()
            LastTimeCleanOutJSOCKETs = l
        }
    }

    ////////////////////////////////////////////////////////////////////////////////   
    @ExperimentalIoApi
    private fun removeOldBetWeenJSOCKETsAll() {
        try {
            CountOfCleaner = 0
            BetweenJSOCKETs.filterKeys {k -> k < LastTimeCleanOutJSOCKETs}.forEach {
                CountOfCleaner += 1
                BetweenJSOCKETs.remove(it.key)!!.condition.cDestroy()
            }
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "Listener.removeOldBetWeenJSOCKETsAll")
        }
    }

    ////////////////////////////////////////////////////////////////////////////////    
    @ExperimentalIoApi
    private fun removeOldOutJSOCKETsAll() {
        try {
            CountOfCleaner = 0
            OutJSOCKETs.filterKeys {k -> k < LastTimeCleanOutJSOCKETs}.forEach {
                CountOfCleaner += 1
                OutJSOCKETs.remove(it.key)!!.condition.cDestroy()
            }
        } catch (ex: Exception) {
            WriteExceptionIntoFile(ex, "Listener.removeOldOutJSOCKETsAll")
        }
    }

    companion object {
        private var Instance: Listener? = null
        @ExperimentalIoApi
        @ExperimentalStdlibApi
        val InJSOCKETs: Queue<Jsocket> = Queue()
        @ExperimentalIoApi
        @InternalAPI
        @ExperimentalStdlibApi
        val BetweenJSOCKETs: ConcurrentMap<Long, Jsocket> = ConcurrentMap()
        @ExperimentalIoApi
        @ExperimentalStdlibApi
        @InternalAPI
        val OutJSOCKETs: ConcurrentMap<Long, Jsocket> = ConcurrentMap()
        @ExperimentalStdlibApi
        @InternalAPI
        @DangerousInternalIoApi
        val Connections: ConcurrentMap<Long, Connection> = ConcurrentMap()
        @ExperimentalStdlibApi
        @InternalAPI
        @DangerousInternalIoApi
        private var connection: Connection? = null
        private const val time_out_wait_locked = 5000L


        fun get_Instance(): Listener? {
            if (Instance == null) {
                Instance = Listener()

            }
            if(!Instance!!.ListenerJob.isActive){
                Instance!!.ListenerJob.start()
            }
            return Instance
        }
    }

    init {
        ListenerJob = execute()
    }
}