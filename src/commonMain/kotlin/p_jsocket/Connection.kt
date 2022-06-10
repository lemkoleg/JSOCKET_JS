@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

import CrossPlatforms.GC
import Tables.myConnectionsCoocki
import Tables.myConnectionsID
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Signal
import com.soywiz.korio.async.addSuspend
import com.soywiz.korio.async.delay
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.net.ws.WebSocketClient
import io.ktor.util.InternalAPI
import io.ktor.utils.io.core.*
import io.ktor.utils.io.core.internal.ChunkBuffer
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_client.*
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


private const val COUNT_OF_0_BYTES = 9
private const val MIN_SIZE_OF_REQUEST: Int = 17



@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
@JsName("BetweenJSOCKETs")
private val BetweenJSOCKETs: HashMap<Long, Jsocket> = hashMapOf()

@ExperimentalTime
@InternalAPI
@KorioExperimentalApi
private val  DECODER_REQUEST_POOL: ArrayDeque<DecoderRequest> = ArrayDeque()


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
@JsName("Connection")
object Connection : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    val ConnectionScope = CoroutineScope(coroutineContext)

    private var connectionDNSName = Constants.SERVER_DNS_NAME

    private val ServerConnPort = 22237

    private lateinit var MyConnection: Job

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
    private var isConnect: Boolean = true
    private var isClosed: Boolean = false


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

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @ExperimentalTime
    @JsName("setConn")
    private fun setConn() {
        MyConnection = ConnectionScope.launch {
            try {
                MyWebSocketChannel = WebSocketClient(url, null, null, "", false)
                signalonOpen = MyWebSocketChannel!!.onOpen
                signalonOpen?.add {
                    isConnect = true
                    isClosed = false
                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                        println("connect")
                    }

                }
                signalonBinaryMessage = MyWebSocketChannel!!.onBinaryMessage
                signalonBinaryMessage?.addSuspend { v ->
                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                        println("read size: ${v.size}")
                    }
                    var l = DECODER_REQUEST_POOL.removeFirstOrNull()
                    if(l == null){
                        l = DecoderRequest()
                    }
                    Connection.ConnectionScope.launch { l.decode(v)}
                }
                signalonAnyMessage = MyWebSocketChannel!!.onAnyMessage
                signalonStringMessage = MyWebSocketChannel!!.onStringMessage
                signalonClose = MyWebSocketChannel!!.onClose
                signalonClose?.add {
                    isConnect = false
                    isClosed = true
                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                        println("disconnect")
                    }
                }
                signalonError = MyWebSocketChannel!!.onError
                signalonError?.add { v ->
                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                        println("error on connection: $v\n")
                    }
                    //val e = v.printStackTrace()
                    isConnect = false
                    isClosed = true
                    MyWebSocketChannel?.close()
                }
                connectionIpAddress = MyWebSocketChannel!!.url.replace("ws://", "").substringBefore(':')


                if (connectionIpAddress.isNotEmpty()) {
                    if (connectionIpAddress.length > 23) {
                        connectionIpAddress = connectionIpAddress.substring(0, 23)
                    }
                    addressByteArray = returnConnAddress().readBytes()
                }
            } catch (e: Exception) {
                isConnect = false
                isClosed = true
                throw my_user_exceptions_class(
                    "Connection",
                    "setConn",
                    "EXC_SOCKET_NOT_ALLOWED",
                    e.message
                )
            }
        }
    }

    @InternalAPI
    public suspend fun sendData(b: ByteArray, j: Jsocket) {
        ConnectionScope.launch {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                BetweenJSOCKETs[j.just_do_it_label] = j
                /*if (!MyConnection.isCompleted) {
                    MyConnection.join()
                }*/
                while (!isConnected()) {
                    if (isClosed) {
                        setConn()
                    }
                    delay(Constants.TIME_SPAN_FOR_LOOP)
                }

                try {
                    MyWebSocketChannel!!.send(b)
                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                        println("send size: ${b.size}")
                    }
                } catch (ex1: Exception) {
                    setConn()
                    try {
                        MyWebSocketChannel!!.send(b)
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            println("send size2: ${b.size}")
                        }
                    } catch (ex1: Exception) {
                        throw my_user_exceptions_class(
                            "Connection",
                            "sendData",
                            "EXC_SOCKET_NOT_ALLOWED",
                            ex1.message
                        )
                    }
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        if (MyWebSocketChannel == null) {
            return false
        } else {
            return isConnect
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
}


////////////////////////////////////////////////////////////////////////////////


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
private class DecoderRequest() {

    suspend fun decode(buffer: ByteArray) {
        try {
            try {

                val buf: ByteReadPacket = ByteReadPacket(buffer)
                var Request_size: Int
                var x = 0
                var b: ByteReadPacket?


                if (!buf.isEmpty) {
                    if (buf.remaining < MIN_SIZE_OF_REQUEST) {
                        return
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
                        return
                    }
                    Request_size = buf.readInt()
                    if (Request_size > Constants.MAX_REQUEST_SIZE_B) {
                        throw my_user_exceptions_class(
                            l_class_name = "DecoderRequest",
                            l_function_name = "decode",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "Request size is to big: ${Request_size.toString()}"
                        )
                    }

                    if (buf.remaining < Request_size + 8) {
                        throw my_user_exceptions_class(
                            l_class_name = "DecoderRequest",
                            l_function_name = "decode",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "Request size is to big: ${Request_size.toString()}"
                        )
                    }

                    b = ByteReadPacket(Connection.addressByteArray!!.plus(buf.readBytes(Request_size)))

                    if (buf.readLong() != 7085774586302733229L) {
                        throw my_user_exceptions_class(
                            l_class_name = "DecoderRequest",
                            l_function_name = "decode",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "Final code is wrong!"
                        )
                    } else {
                        var jsocketRet: Jsocket? = CLIENT_JSOCKET_POOL.removeFirstOrNull()
                        if (jsocketRet == null) {
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                println("CLIENT_JSOCKET_POOL is emprty")
                            }
                            jsocketRet = Jsocket()
                        }
                        var jsocket: Jsocket?
                        jsocketRet.deserialize(b, myConnectionsCoocki.value, true, newConnectionCoocki.value)
                        if (jsocketRet.just_do_it != 0) {
                            jsocket = BetweenJSOCKETs.remove(jsocketRet.just_do_it_label)

                            if (jsocket != null) {
                                when (jsocketRet.just_do_it) {
                                    1011000058 -> {
                                        jsocket.send_request()
                                    }
                                    1011000069 -> {
                                        myConnectionsID.setNewValue(0L)
                                        myConnectionsCoocki.setNewValue(0L)
                                        Sqlite_service.ClearRegData()
                                        throw my_user_exceptions_class(
                                            l_class_name = "DecoderRequest",
                                            l_function_name = "decode",
                                            name_of_exception = "EXC_WRSOCKETTYPE_CONN_ID_OR_COOCKI_NOT_VALID"
                                        )
                                    }


                                    else -> {
                                        jsocket.merge(jsocketRet)
                                        jsocket.condition.cSignal()
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "Connection",
                    l_function_name = "DecoderRequest",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = e.message
                )
            }
        } catch (e: my_user_exceptions_class) {
            e.ExceptionHand(null)
        } finally {
            DECODER_REQUEST_POOL.addLast(this)
        }
    }

    companion object {

        private var countOfCleaner = 0
        private var CurentTime = DateTime.nowUnixLong()
        private var NextTimeCleanOUT_JSOCKETs = CurentTime + Constants.TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES
        private var LastTimeCleanOutJSOCKETs = nowNano()

        private val Cleaner = KorosTimerTask.start(
            delay = Constants.TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES,
            repeat = Constants.TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES
        ) { removeOldAll()
          }

        fun removeOldAll() {
            try {
                try {
                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                        println("Start Connection.removeOldAll()")
                    }
                    CurentTime = DateTime.nowUnixLong()
                    if (CurentTime > NextTimeCleanOUT_JSOCKETs) {
                        NextTimeCleanOUT_JSOCKETs = CurentTime + Constants.TIME_OUT_FOR_CLEAR_CLIENT_JSOCKETS_QUEUES

                        countOfCleaner = 0
                        BetweenJSOCKETs.filterKeys { k -> k < LastTimeCleanOutJSOCKETs }.forEach {
                            countOfCleaner++
                            it.value.condition.cSignal()
                            BetweenJSOCKETs.remove(it.key)
                        }
                        if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                            if (countOfCleaner > 0) {
                                println("$countOfCleaner removed from Conection.BetweenJSOCKETs.")
                            }
                        }

                        LastTimeCleanOutJSOCKETs = nowNano()
                    }
                } catch (ex: Exception) {
                    throw my_user_exceptions_class(
                        l_class_name = "Connection",
                        l_function_name = "removeOldAll",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = ex.message
                    )
                }
            } catch (e: my_user_exceptions_class) {
                e.ExceptionHand(null)
            }finally {
                CoroutineScope(NonCancellable).launch {GC.collect()}
            }
        }
    }