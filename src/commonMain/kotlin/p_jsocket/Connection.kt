@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

import CrossPlatforms.GC
import CrossPlatforms.PrintInformation
import Tables.*
import atomic.lockedGet
import atomic.lockedPut
import atomic.lockedRemove
import atomic.AtomicLong
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.*
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.net.ws.DEFAULT_WSKEY
import com.soywiz.korio.net.ws.WebSocketClient
import io.ktor.util.InternalAPI
import io.ktor.utils.io.core.*
import io.ktor.utils.io.core.internal.ChunkBuffer
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_client.*
import sql.Sqlite_service
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


private const val COUNT_OF_0_BYTES = 9
private const val MIN_SIZE_OF_REQUEST: Int = 17


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
@JsName("BetweenJSOCKETs")
private val BetweenJSOCKETs: HashMap<Long, Jsocket> = hashMapOf()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
private val LastReSendRequestProfile: AtomicLong = AtomicLong(DateTime.nowUnixMillisLong())


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
@JsName("Connection")
object Connection {

    private var connectionDNSName = Constants.SERVER_DNS_NAME

    private val ServerConnPort = 22237

    private lateinit var SetConnJob: Job

    @JsName("MyWebSocketChannel")
    private var MyWebSocketChannel: WebSocketClient? = null

    public var addressByteArray: ByteArray? = null
    private var connectionIpAddress: String = ""
    private var signalonBinaryMessage: Signal<ByteArray>? = null
    private var signalonAnyMessage: Signal<Any>? = null
    private var signalonStringMessage: Signal<String>? = null
    private var signalonClose: Signal<WebSocketClient.CloseInfo>? = null
    private var signalonError: Signal<Throwable>? = null
    private var signalonOpen: Signal<Unit>? = null
    private val url = "ws://".plus(connectionDNSName).plus(":").plus(ServerConnPort.toString())
    private var isConnect: Boolean = false
    private var isClosed: Boolean = true
    private var isRun: Boolean = false

    private var countOfCleaner = 0

    private var LastTimeCleanOutJSOCKETs = 0L


    init {
        if (connectionDNSName.isNotEmpty()) {
            setConn()
        }
    }


    private fun returnConnAddress(): ByteReadPacket {
        val bb = BytePacketBuilder(ChunkBuffer.Pool)
        bb.writeInt(ServerConnPort)
        val current_position = bb.size + 23
        val ipArray = connectionIpAddress.encodeToByteArray()
        bb.writePacket(ByteReadPacket(ipArray))
        while (bb.size < current_position) {
            bb.writeByte(0.toByte())
        }
        return bb.build()
    }

    private var stringExceptionHandler = ""
    private var globalBuf: ByteArray? = null

    @InternalAPI
    private val ConnectionLock = Mutex()

    private var time: Long = 0L

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @ExperimentalTime
    @JsName("setConn")
    private fun setConn() {
        SetConnJob = CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately {

            isRun = true

            var count_of_try = Constants.MAX_COUNT_TRYING_SET_CONNECTION

            while (count_of_try > 0 && !isConnect) {
                count_of_try--

                try {
                    MyWebSocketChannel = WebSocketClient(
                        url = url,
                        protocols = null,
                        origin = null,
                        wskey = DEFAULT_WSKEY,
                        debug = false
                    )
                    signalonOpen = MyWebSocketChannel!!.onOpen
                    signalonOpen?.add {
                        isConnect = true
                        isClosed = false
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            PrintInformation.PRINT_INFO("WebSocket connect. Port: ${MyWebSocketChannel!!.url.toString()}")
                        }
                    }
                    signalonBinaryMessage = MyWebSocketChannel!!.onBinaryMessage
                    signalonBinaryMessage?.invoke { v ->
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            PrintInformation.PRINT_INFO("Get paccket: ${v.size}")
                        }
                        decode(v)
                    }
                    signalonAnyMessage = MyWebSocketChannel!!.onAnyMessage
                    signalonStringMessage = MyWebSocketChannel!!.onStringMessage
                    signalonClose = MyWebSocketChannel!!.onClose
                    signalonClose?.add {
                        isConnect = false
                        isClosed = true
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            PrintInformation.PRINT_INFO("WebSocket disconnect")
                        }
                    }
                    signalonError = MyWebSocketChannel!!.onError
                    signalonError?.add { v ->
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            PrintInformation.PRINT_INFO("WebSocket error on connection: $v\n")
                        }
                        isConnect = false
                        isClosed = true
                        isRun = false
                        MyWebSocketChannel?.close()
                    }
                    connectionIpAddress = MyWebSocketChannel!!.url.replace("ws://", "").substringBefore(':')


                    if (connectionIpAddress.isNotEmpty()) {
                        if (connectionIpAddress.length > 23) {
                            connectionIpAddress = connectionIpAddress.substring(0, 23)
                        }
                        addressByteArray = returnConnAddress().readBytes()
                    }
                    delay(Constants.TIME_SPAN_FOR_LOOP * 10)
                } catch (e: Exception) {

                    isConnect = false
                    isClosed = true
                    isRun = false
                    if (count_of_try < 1) {
                        throw my_user_exceptions_class(
                            "Connection",
                            "setConn",
                            "EXC_SOCKET_NOT_ALLOWED",
                            e.stackTraceToString()
                        )
                    }
                }
            }
        }
    }

    @InternalAPI
    public fun sendData(b: ByteArray, j: Jsocket) {
        CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                ConnectionLock.withLock {
                    BetweenJSOCKETs.lockedPut(j.just_do_it_label, j)

                    while (!isConnect) {
                        if (!isRun) {
                            setConn()
                        }
                        delay(Constants.TIME_SPAN_FOR_LOOP)
                    }

                    if(j.just_do_it == 1011000068  // RE_SEND_REQUEST_PROFILE;
                        || j.just_do_it == 1011000053 // SELECTOR.SELECT_CHATS;
                        ){
                        LastReSendRequestProfile.setGreaterValue(DateTime.nowUnixMillisLong())
                    }

                    try {
                        MyWebSocketChannel!!.send(b)
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            PrintInformation.PRINT_INFO("send size: ${b.size}; command: ${j.just_do_it}")
                        }
                    } catch (ex1: Exception) {
                        setConn()
                        try {
                            MyWebSocketChannel!!.send(b)
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                PrintInformation.PRINT_INFO("send size: ${b.size}; command: ${j.just_do_it}")
                            }
                        } catch (ex1: Exception) {
                            throw my_user_exceptions_class(
                                "Connection",
                                "sendData",
                                "EXC_SOCKET_NOT_ALLOWED",
                                ex1.stackTraceToString()
                            )
                        }
                    } finally {
                        //println(DateTime.nowUnixMillisLong())
                    }
                }
            }
        }
    }

    @InternalAPI
    @JsName("close")
    fun close() {
        isConnect = false
        isClosed = true
        if (MyWebSocketChannel != null) {
            try {
                MyWebSocketChannel?.close()
            } catch (e: Exception) {
            }
        }
    }


////////////////////////////////////////////////////////////////////////////////


    fun decode(buffer: ByteArray) =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).launchImmediately {
            try {
                try {
                    val buf: ByteReadPacket = ByteReadPacket(buffer)
                    var Request_size: Int
                    var x = 0
                    var b: ByteReadPacket?

                    if (!buf.isEmpty) {
                        if (buf.remaining < MIN_SIZE_OF_REQUEST) {
                            return@launchImmediately
                        }
                        FirstLoop@
                        while (x < COUNT_OF_0_BYTES && buf.isNotEmpty) {
                            if (buf.readByte() == 0.toByte()) {
                                x++
                                if (x == COUNT_OF_0_BYTES) {
                                    x = if (buf.readLong() == 9223372036854775807L) {
                                        break@FirstLoop
                                    } else {
                                        0
                                    }
                                }
                            } else {
                                x = 0
                            }
                        }
                        if (x < COUNT_OF_0_BYTES || buf.remaining < 4) {
                            return@launchImmediately
                        }
                        Request_size = buf.readInt()
                        if (Request_size > Constants.MAX_REQUEST_SIZE_B) {
                            throw my_user_exceptions_class(
                                l_class_name = "Connection",
                                l_function_name = "decode",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "Request size is to big: ${Request_size.toString()}"
                            )
                        }

                        if (buf.remaining < Request_size + 8) {
                            throw my_user_exceptions_class(
                                l_class_name = "Connection",
                                l_function_name = "decode",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "Request size is to big: ${Request_size.toString()}"
                            )
                        }

                        b = ByteReadPacket(Connection.addressByteArray!!.plus(buf.readBytes(Request_size)))

                        if (buf.readLong() != 7085774586302733229L) {
                            throw my_user_exceptions_class(
                                l_class_name = "Connection",
                                l_function_name = "decode",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = "Final code is wrong!"
                            )
                        } else {
                            var jsocketRet: Jsocket? = Jsocket.GetJsocket()
                            if (jsocketRet == null) {
                                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                    PrintInformation.PRINT_INFO("CLIENT_JSOCKET_POOL is empty")
                                }
                                Jsocket.fill()
                                jsocketRet = Jsocket()
                            }
                            var jsocket: Jsocket?
                            jsocketRet.deserialize(b, Constants.myConnectionsCoocki, true, newConnectionCoocki.value)



                            if (jsocketRet.just_do_it != 0) {

                                val c = COMMANDS.lockedGet(jsocketRet.just_do_it)!!

                                if (c.commands_access != "B") {
                                    jsocket = BetweenJSOCKETs.lockedRemove(jsocketRet.just_do_it_label)
                                } else {
                                    jsocket = BetweenJSOCKETs.lockedGet(jsocketRet.just_do_it_label)
                                }

                                if (jsocket != null) {

                                    when (jsocketRet.just_do_it) {

                                        1011000086 -> {  // new messeges, notices;
                                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                                PrintInformation.PRINT_INFO("Get command 1011000086")
                                            }
                                            if(jsocketRet.last_messege_update > CHATS!!.CashLastUpdate.GET_LAST_SELECT()){
                                                KChat.VERIFY_UPDATES(jsocketRet.last_messege_update)
                                            }
                                        }

                                        1011000058 -> {
                                            jsocket.send_request()
                                        }

                                        1011000069 -> {
                                            Constants.myConnectionsID = 0L
                                            Constants.myConnectionsCoocki = 0L
                                            Sqlite_service.ClearRegData()
                                            throw my_user_exceptions_class(
                                                l_class_name = "Connection",
                                                l_function_name = "decode",
                                                name_of_exception = "EXC_WRSOCKETTYPE_CONN_ID_OR_COOCKI_NOT_VALID"
                                            )
                                        }

                                        else -> {

                                            try {

                                                println("get request: jsocketRet.just_do_it: ${jsocketRet.just_do_it}; jsocket.check_sum = ${jsocket.check_sum}")

                                                if (jsocketRet.last_messege_update > CHATS!!.CashLastUpdate.GET_LAST_SELECT()) {
                                                    KChat.VERIFY_UPDATES(jsocketRet.last_messege_update)
                                                }

                                                if(!jsocketRet.just_do_it_successfull.equals("0")){
                                                    jsocket.db_massage = jsocketRet.db_massage
                                                    jsocket.just_do_it_successfull = jsocketRet.just_do_it_successfull
                                                    throw my_user_exceptions_class(
                                                        l_class_name = "Connection",
                                                        l_function_name = "decode",
                                                        name_of_exception = "DB_ERROR",
                                                        l_additional_text = jsocketRet.db_massage
                                                    )
                                                }

                                                if (c.commands_access == "B") {
                                                    withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                                                        val l = jsocket!!.lock
                                                        l.withLock {
                                                            jsocketRet.contrMerge(jsocket!!)
                                                            jsocket = jsocketRet
                                                        }
                                                    } ?: throw my_user_exceptions_class(
                                                        l_class_name = "Connection",
                                                        l_function_name = "decode",
                                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                                        l_additional_text = "Time out is up"
                                                    )
                                                } else {
                                                    jsocket!!.merge(jsocketRet)
                                                }
                                                jsocket!!.is_new_reg_data = false
                                                if (c.whichBlobDataReturned == "4") {
                                                    jsocket!!.deserialize_ANSWERS_TYPES()
                                                }
                                                if (jsocket!!.is_new_reg_data) {
                                                    KRegData.setNEW_REG_DATA(jsocket)
                                                }

                                            } finally {
                                                jsocket!!.condition.cSignal()
                                            }
                                        }
                                    }
                                } else {
                                    if (jsocketRet.just_do_it != 1011000086) { // new messeges, notices;
                                        throw my_user_exceptions_class(
                                            l_class_name = "Connection",
                                            l_function_name = "decode",
                                            name_of_exception = "EXC_SYSTEM_ERROR",
                                            l_additional_text = "Answer not have request and command is not SET_NEW_MESSEGES"
                                        )
                                    } else {
                                        if(jsocketRet.last_messege_update > CHATS!!.CashLastUpdate.GET_LAST_SELECT()){
                                            KChat.VERIFY_UPDATES(jsocketRet.last_messege_update)
                                        }
                                    }

                                }
                            }
                        }
                    }
                } catch (e: my_user_exceptions_class) {
                    throw e
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "Connection",
                        l_function_name = "decode",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.stackTraceToString()
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }
        }


    private val Cleaner = KorosTimerTask.start(
        delay = Constants.TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES,
        repeat = Constants.TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES
    ) {
        removeOldAll()
    }

    @InternalAPI
    public suspend fun removeRequest(just_do_it_label: Long) {
        BetweenJSOCKETs.lockedRemove(just_do_it_label)?.condition?.cSignal()
    }

    suspend fun removeOldAll() {
        try {
            try {

                if(Constants.RE_SEND_REQUEST_PROFILE_BY_TIMEOUT == 1){
                    if(LastReSendRequestProfile.value < (DateTime.nowUnixMillisLong() - Constants.TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES)){
                        val socket: Jsocket = Jsocket.GetJsocket() ?: Jsocket()
                        socket.just_do_it = 1011000068  // RE_SEND_REQUEST_PROFILE;
                        socket.send_request(await_answer = false)
                    }
                }

                LastTimeCleanOutJSOCKETs = nowNano() - Constants.TIME_OUT_FOR_CLIENT_JSOCKETS

                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                    val d: DateTime = DateTime.now()
                    PrintInformation.PRINT_INFO("Start Connection.removeOldAll(): (hh: ${d.hours};mm: ${d.minutes};ss: ${d.seconds}));")
                }

                countOfCleaner = 0
                BetweenJSOCKETs.filterKeys { k -> (k < LastTimeCleanOutJSOCKETs) }.forEach {
                    countOfCleaner++
                    BetweenJSOCKETs.lockedRemove(it.key)
                }
                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                    if (countOfCleaner > 0) {
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            PrintInformation.PRINT_INFO("$countOfCleaner removed from Conection.BetweenJSOCKETs.")
                        }
                    }
                }


            } catch (e: my_user_exceptions_class) {
                throw e
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Connection",
                    l_function_name = "removeOldAll",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.stackTraceToString()
                )
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        } finally {
            CoroutineScope(NonCancellable).launchImmediately { GC.collect() }
        }

    }
}