@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

import CrossPlatforms.WriteExceptionIntoFile
import com.soywiz.kds.Queue
import com.soywiz.korio.async.Signal
import com.soywiz.korio.async.addSuspend
import com.soywiz.korio.async.delay
import com.soywiz.korio.net.URL
import com.soywiz.korio.net.createTcpClient
import com.soywiz.korio.net.ws.RawSocketWebSocketClient
import com.soywiz.korio.net.ws.WebSocketClient
import com.soywiz.korio.stream.writeBytes
import io.ktor.util.InternalAPI
import io.ktor.util.Lock
import io.ktor.util.withLock
import io.ktor.utils.io.core.*
import io.ktor.utils.io.core.internal.ChunkBuffer
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import io.ktor.utils.io.errors.IOException
import io.ktor.utils.io.writer
import kotlinx.coroutines.*
import lib_exceptions.exc_JSOCKET_not_readed
import lib_exceptions.exc_size_of_data_is_too_long
import lib_exceptions.exc_socket_not_allowed
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime
import p_jsocket.MAX_REQUEST_SIZE_B



private const val COUNT_OF_0_BYTES = 9
private const val MIN_SIZE_OF_REQUEST: Int = 17

private val QUEUE_OF_RAW_MESSEGES: Queue<ByteArray> = Queue()

@ExperimentalIoApi
private val REQUEST_PREFIX = returnRequestPrefix()


private val REQUEST_POSTFIX = returnRequestPostfix()

@ExperimentalIoApi
private fun returnRequestPrefix(): ByteArray {
    val b = BytePacketBuilder(0, ChunkBuffer.Pool)
    b.writeByte(0.toByte())
    b.writeLong(0L)
    b.writeLong(9223372036854775807L)
    return b.build().readBytes()
}

private fun returnRequestPostfix(): ByteArray {
    val b = BytePacketBuilder(0, ChunkBuffer.Pool)
    b.writeLong(7085774586302733229L)
    return b.build().readBytes()
}

@Suppress("unused")
@InternalAPI
@ExperimentalIoApi
@JsName("Connection")
class Connection(
    _connectionDNSName: String = "",
    _connPort: Int = 0
): CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    val ConnectionScope = CoroutineScope(coroutineContext)

    private val connectionDNSName = _connectionDNSName

    private val ServerConnPort = if (_connPort == 0) {
        22237
    } else {
        _connPort
    }
    private lateinit var MyConnection: Job

    @JsName("MyWebSocketChannel")
    private var MyWebSocketChannel: WebSocketClient? = null

    private lateinit var addressByteArray: ByteReadPacket
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
            MyConnection = ConnectionScope.launch { setConn() }
        }
    }


    private fun returnConnAddress(): ByteReadPacket {
        val bb = BytePacketBuilder(0, ChunkBuffer.Pool)
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
    private val lock = Lock()
    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @ExperimentalIoApi
    private suspend fun setConn() {
        try {
            MyWebSocketChannel = WebSocketClient(url, null, null, "", false)
            signalonOpen = MyWebSocketChannel!!.onOpen
            signalonOpen?.add {
                isConnect = true
                isClosed = false
                println("connect")
            }
            signalonBinaryMessage = MyWebSocketChannel!!.onBinaryMessage
            signalonBinaryMessage?.addSuspend { v ->
                println("read size: ${v.size}")
                QUEUE_OF_RAW_MESSEGES.enqueue(
                    v,
                    MAX_COUNT_OF_REQUEST_ON_CLIENT_REQUESTS_QUEUE,
                    "Connection.QUEUE_OF_RAW_MESSEGES"
                )
            }
            signalonAnyMessage = MyWebSocketChannel!!.onAnyMessage
            signalonStringMessage = MyWebSocketChannel!!.onStringMessage
            signalonClose = MyWebSocketChannel!!.onClose
            signalonClose?.add {
                isConnect = false
                isClosed = true
                println("disconnect")
            }
            signalonError = MyWebSocketChannel!!.onError
            signalonError?.add { v ->
                println("error on connection: $v\n")
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
                addressByteArray = returnConnAddress()
            }
        } catch (e: Exception) {
            isConnect = false
            isClosed = true
            println(e.toString())
            WriteExceptionIntoFile(e, "Connection.setConn")
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @ExperimentalIoApi
    @InternalAPI
    @JsName("send_request")
    fun send_request(MySOCKETSBytes: ByteReadPacket) {
        if (MySOCKETSBytes.isNotEmpty) {
            ConnectionScope.writer {
                try {
                    val buf = BytePacketBuilder(0, ChunkBuffer.Pool)
                    buf.writeFully(REQUEST_PREFIX)
                    buf.writeInt(MySOCKETSBytes.remaining.toInt())
                    buf.writePacket(MySOCKETSBytes)
                    buf.writeFully(REQUEST_POSTFIX)
                    val b = buf.build().readBytes()
                    sendData(b)
                } catch (e: Exception) {
                    WriteExceptionIntoFile(e, "Connection.send_request")
                }
            }
        }
    }

    @JsName("send_raw_data")
    fun send_raw_data(data: ByteArray) {
        if (data.isNotEmpty()) {
            ConnectionScope.writer {
                try {
                    sendData(data)
                } catch (e: Exception) {
                    WriteExceptionIntoFile(e, "Connection.send_request")
                }
            }
        }
    }

    @InternalAPI
    @ExperimentalIoApi
    private suspend fun sendData(b: ByteArray) {
        withTimeoutOrNull(CLIENT_TIMEOUT) {
            /*if (!MyConnection.isCompleted) {
                MyConnection.join()
            }*/
            while(!isConnected()){
                if(isClosed){
                    setConn()
                }
                delay(TIME_SPAN_FOR_LOOP)
            }

            try {
                MyWebSocketChannel!!.send(b)
                println("send size: ${b.size}")
            } catch (ex1: Exception) {
                stringExceptionHandler = ex1.toString()
                //withContext(NonCancellable) {
                    setConn()
                    try {
                        MyWebSocketChannel!!.send(b)
                        println("send size2: ${b.size}")
                    } catch (ex1: Exception) {
                        throw exc_socket_not_allowed("Error in connection.send_data - ".plus(stringExceptionHandler))
                    }
                }
           // }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @ExperimentalTime
    @InternalAPI
    @JsName("read_request")
    suspend fun read_request(timeOut:Long): ByteReadPacket? {
        var ch: ByteReadPacket? = null
        val l = QUEUE_OF_RAW_MESSEGES.dequeue(timeOut)
        if(l != null) {
            ch = DecodeRequest(l)
        }
        return ch
    }


    @ExperimentalTime
    @JsName("read_raw_data")
    suspend fun read_raw_data(timeOut:Long): ByteArray? {
        var ch: ByteArray? = null
        if (QUEUE_OF_RAW_MESSEGES.isNotEmpty()) {
            ch = QUEUE_OF_RAW_MESSEGES.dequeue(timeOut)
        }
        return ch
    }

    ////////////////////////////////////////////////////////////////////////////////



    @InternalAPI
    private suspend fun DecodeRequest(buffer: ByteArray?): ByteReadPacket? {
        var buf: ByteReadPacket? = null
        var Request_size: Int
        var x = 0
        var b: ByteReadPacket? = null
        try {
            withTimeoutOrNull(CLIENT_TIMEOUT) {
                lock.withLock {
                    if (buffer != null && buffer.isNotEmpty()) {
                        globalBuf = if (globalBuf == null || globalBuf!!.isEmpty()) {
                            buffer
                        } else {
                            globalBuf!!.plus(buffer)
                        }
                    }

                    if (globalBuf != null && globalBuf!!.isNotEmpty()) {
                        if (globalBuf!!.size < MIN_SIZE_OF_REQUEST) {
                            return@withLock null
                        }
                        buf = ByteReadPacket(globalBuf!!)
                        FirstLoop@
                        while (x < COUNT_OF_0_BYTES && buf!!.isNotEmpty) {
                            if (buf!!.readByte() == 0.toByte()) {
                                x++
                                if (x == COUNT_OF_0_BYTES) {
                                    x = if (buf!!.readLong() == 9223372036854775807L) {
                                        break@FirstLoop
                                    } else {
                                        0
                                    }
                                }
                            } else {
                                x = 0
                            }
                        }
                        if (x < COUNT_OF_0_BYTES || buf!!.remaining < 4) {
                            return@withLock null
                        }
                        Request_size = buf!!.readInt()
                        if (Request_size > MAX_REQUEST_SIZE_B) {
                            throw exc_size_of_data_is_too_long(Request_size)
                        }

                        if (buf!!.remaining < Request_size + 8) {
                            return@withLock null
                        }

                        b = ByteReadPacket(returnConnAddress().readBytes().plus(buf!!.readBytes(Request_size)))

                        if (buf!!.readLong() != 7085774586302733229L) {
                            throw exc_JSOCKET_not_readed("Final code is wrong.")
                        }
                        else{
                            return@withLock b
                        }
                    }
                    else return@withLock null
                }
            }
        } catch (io: IOException) {
            this.close()
        } catch (e: Exception) {
            b?.close()
            WriteExceptionIntoFile(e, "Connection.DecodeRequest")
        } finally {
            if (buf != null && buf!!.isNotEmpty) {
                globalBuf = buf!!.readBytes()

            } else globalBuf = null
        }
        return b
    }


    fun isConnected(): Boolean {
        if (MyWebSocketChannel == null) {
            return false
        }else{
            return isConnect
        }
    }

    @ExperimentalTime
    suspend fun skip(timeOut:Long):Long {
        return QUEUE_OF_RAW_MESSEGES.dequeue(timeOut)?.size?.toLong()?:0L
    }

    ////////////////////////////////////////////////////////////////////////////////
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