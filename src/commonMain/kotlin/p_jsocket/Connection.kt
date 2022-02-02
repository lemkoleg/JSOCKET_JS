@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket

import CrossPlatforms.DEFAULT_AWAIT_TIMEOUT
import CrossPlatforms.WriteExceptionIntoFile
import CrossPlatforms.timeSpanForLoop
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


private const val SOCKET_BUFFER_SIZE = 16 * 1024
private const val COUNT_OF_0_BYTES = 9
private const val MAXMASSAGESIZE: Int = AVATARSIZE * 50000
private const val MAXCOUNT_OF_REQUEST: Int = 200
private const val MIN_SIZE_OF_REQUEST: Int = 17

@DangerousInternalIoApi
private val REQUEST_PREFIX = returnRequestPrefix()

@DangerousInternalIoApi
private val REQUEST_POSTFIX = returnRequestPostfix()

@OptIn(ExperimentalIoApi::class)
@DangerousInternalIoApi
private fun returnRequestPrefix(): ByteArray {
    val b = BytePacketBuilder(0, ChunkBuffer.Pool)
    b.writeByte(0.toByte())
    b.writeLong(0L)
    b.writeLong(9223372036854775807L)
    return b.build().readBytes()
}

@DangerousInternalIoApi
private fun returnRequestPostfix(): ByteArray {
    val b = BytePacketBuilder(0, ChunkBuffer.Pool)
    b.writeLong(7085774586302733229L)
    return b.build().readBytes()
}

@Suppress("unused")
@DangerousInternalIoApi
@InternalAPI
@ExperimentalStdlibApi
@JsName("Connection")
class Connection(
    _connectionDNSName: String = "",
    _isWebConnection: Boolean = true,
    _connPort: Int = 0
): CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    val ConnectionScope = CoroutineScope(coroutineContext)

    private val connectionDNSName = _connectionDNSName
    private val isWebConnection = _isWebConnection
    private val ServerConnPort = if (_connPort == 0) {
        if (isWebConnection) {
            22237
        } else {
            22236
        }
    } else {
        _connPort
    }
    private lateinit var MyConnection: Job

    @JsName("MyRawSocketChannel")
    private var MyRawSocketChannel: RawSocketWebSocketClient? = null

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

    //@InternalAPI
    //private val lockForQUEUE_OF_REQUEST = Lock()

    @InternalAPI
    //private val QUEUE_OF_REQUEST: ConcurrentCollection<ByteReadPacket> = ConcurrentCollection(mutableSetOf(), lockForQUEUE_OF_REQUEST)
    private val QUEUE_OF_RAW_MESSEGES: Queue<ByteArray> = Queue()

    init {
        if (connectionDNSName.isNotEmpty()) {
            MyConnection = ConnectionScope.launch { setConn() }
        }
    }

    @OptIn(ExperimentalIoApi::class)
    @ExperimentalStdlibApi
    @DangerousInternalIoApi
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
    @DangerousInternalIoApi
    @ExperimentalStdlibApi
    private suspend fun setConn() {
        try {
            if (isWebConnection) {
                //MyWebSocketChannel = RawSocketWebSocketClient(url,null, null, "", false)
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
                    QUEUE_OF_RAW_MESSEGES.enqueue(v ,MAXCOUNT_OF_REQUEST, "Connection.QUEUE_OF_RAW_MESSEGES")
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
                    MyWebSocketChannel?.close()}
                connectionIpAddress = MyWebSocketChannel!!.url.replace("ws://", "").substringBefore(':')
            }
            if (!isWebConnection) {
                val cl = createTcpClient()
                cl.connect(connectionDNSName, ServerConnPort)
                MyRawSocketChannel = RawSocketWebSocketClient(
                    ConnectionScope.coroutineContext, cl, URL.invoke(""), null, false, null, ""
                )
                connectionIpAddress = MyRawSocketChannel!!.host
                isConnect = MyRawSocketChannel!!.client.connected
            }
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
    @OptIn(ExperimentalIoApi::class)
    @InternalAPI
    @DangerousInternalIoApi
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
    @DangerousInternalIoApi
    private suspend fun sendData(b: ByteArray) {
        withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
            /*if (!MyConnection.isCompleted) {
                MyConnection.join()
            }*/
            while(!isConnected()){
                if(isClosed){
                    setConn()
                }
                delay(timeSpanForLoop)
            }

            try {
                if (isWebConnection && MyWebSocketChannel != null) {
                    MyWebSocketChannel!!.send(b)
                    println("send size: ${b.size}")
                }
                if (!isWebConnection && MyRawSocketChannel != null) {
                    MyRawSocketChannel!!.client.writeBytes(b)
                    println("send size: ${b.size}")
                }
            } catch (ex1: IOException) {
                stringExceptionHandler = ex1.toString()
                //withContext(NonCancellable) {
                    setConn()
                    try {
                        if (isWebConnection && MyWebSocketChannel != null) {
                            MyWebSocketChannel!!.send(b)
                            println("send size2: ${b.size}")
                        }
                        if (!isWebConnection && MyRawSocketChannel != null) {
                            MyRawSocketChannel!!.client.writeBytes(b)
                            println("send size2: ${b.size}")
                        }
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
        if (isWebConnection) {
            val l = QUEUE_OF_RAW_MESSEGES.dequeue(timeOut)
            if(l != null) {
                ch = DecodeRequest(l)
            }
        }
        return ch
    }


    @ExperimentalTime
    @JsName("read_raw_data")
    suspend fun read_raw_data(timeOut:Long): ByteArray? {
        var ch: ByteArray? = null
        if (isWebConnection) {
            if (QUEUE_OF_RAW_MESSEGES.isNotEmpty()) {
                ch = QUEUE_OF_RAW_MESSEGES.dequeue(timeOut)
            }
        }
        return ch
    }

    ////////////////////////////////////////////////////////////////////////////////


    @DangerousInternalIoApi
    @InternalAPI
    private suspend fun DecodeRequest(buffer: ByteArray?): ByteReadPacket? {
        var buf: ByteReadPacket? = null
        var Request_size: Int
        var x = 0
        var b: ByteReadPacket? = null
        try {
            withTimeoutOrNull(DEFAULT_AWAIT_TIMEOUT) {
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
                        if (Request_size > MAXMASSAGESIZE) {
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
        return return if (isWebConnection) {
            if (MyWebSocketChannel == null) {
                false
            }else{
                isConnect
            }
        } else {
            if (MyRawSocketChannel == null) {
                false
            } else {
                MyRawSocketChannel!!.client.connected
            }
        }
    }

    @ExperimentalTime
    suspend fun skip(timeOut:Long):Long {
        return QUEUE_OF_RAW_MESSEGES.dequeue(timeOut)?.size?.toLong()?:0L
    }


    /*
    @InternalAPI
    @DangerousInternalIoApi
    private suspend fun readLoop(time_out: Long) {
        if (!MyConnection.isCompleted) {
            MyConnection.join()
        }

        val timeOut = TimeSpan(time_out.toDouble())

        ReadLoop@
        while (true) {
            val localBuf = readData(timeOut)
            if(localBuf != null && localBuf.isNotEmpty())
                {DecodeRequestAndSet(localBuf)}

        }
    }

        @InternalAPI
    private suspend fun readData(tSpan: TimeSpan): ByteArray? {
        var buf: ByteArray? = null
        try {
            if (isWebConnection && MyWebSocketChannel != null) {
                buf = signalonBinaryMessage?.waitOne(timeout = tSpan)

                println("readed from web: ${buf?.size}")
            }
            if (!isWebConnection && MyRawSocketChannel != null) {
                buf = MyRawSocketChannel!!.onBinaryMessage.waitOne(tSpan)
                println("readed from raw: ${buf?.size}")
            }
            return buf
        } catch (io: IOException)
                {this.close()}
           catch (e: Exception) {
            //if (MyReadLoop != null && MyReadLoop!!.isCancelled)
            println("Error in DecodeRequestAndSet: $e")
            WriteExceptionIntoFile(e)
        }
        return null
    }


    private suspend fun readData(t: TimeSpan): ByteArray? {
        var buf: ByteArray? = null
        MyContext.reader {
            if (!MyConnection.isCompleted) {
                MyConnection.join()
            }
            if (isWebConnection && MyWebSocketChannel != null) {
                buf = signalonBinaryMessage?.waitOne(timeout = t)
                println("readed from web: ${buf?.size}")
            }
            if (!isWebConnection && MyRawSocketChannel != null) {
                buf = MyRawSocketChannel!!.client.readAll()
                //buf = MyRawSocketChannel!!.client.withLength(16384L).readAll()
                //buf = MyRawSocketChannel!!.onBinaryMessage.waitOne(t)
                println("readed from raw: ${buf?.size}")
            }
        }.join()
        return buf
    }*/

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @JsName("close")
    fun close() {
        isConnect = false
        isClosed = true
        if (isWebConnection && MyWebSocketChannel != null) {
            try {
                MyWebSocketChannel?.close()
            } catch (e: Exception) {
            }
        }
        if (!isWebConnection && MyRawSocketChannel != null) {
            try {
                MyRawSocketChannel!!.close()
            } catch (e: Exception) {
            }
        }
        QUEUE_OF_RAW_MESSEGES.clear()
        /*if(MyReadLoop != null && MyReadLoop!!.isActive) {
            try {
                MyReadLoop!!.cancel()
            }
            catch (e:Exception){}
        }
        QUEUE_OF_JSOCKETS.clear()*/
    }

    //////////////////////////Not work/////////////////////////////////////////
    /*  private fun peek(): Int {
         return try {
             MySocketChannel.onAnyMessage.listenerCount
             //MySocketChannel.client.bufferedInput().bufferSize
         } catch (e: Exception) {
             0
         }
     }

     /////////////////////////////////////////////////////////////////////////////////////
     private suspend fun blocking_peek(): Int {
         val c =
             ConnectionContext.async{
                 return@async try {
                     while (peek() == 0) {
                         yield()
                     }
                     peek()
                 } catch (e: Exception) {
                     0
                 }
             }
         return c.await()}

     /////////////////////////////////////////////////////////////////////////////////////
     private suspend fun blocking_peek_with_timeout(mili_seconds: Long):Deferred<Int>{
         val c =
             ConnectionContext.async{
                 return@async try {
                     val l_timeout: Long = DateTime.nowUnixLong() + mili_seconds
                     while (peek() == 0 && l_timeout > DateTime.nowUnixLong()) {
                         yield()
                     }
                     peek()
                 } catch (e: Exception) {
                     0
                 }
             }
         return c}

     /////////////////////////////////////////////////////////////////////////////////////
     private suspend fun have_data(mili_seconds: Long): Boolean {
         return if (!mili_seconds.equals(0L)) {
             val t = blocking_peek_with_timeout(mili_seconds)
             return t.await() > 0
         } else {
             peek() > 0
         }
     }

     ////////////////////////////////////////////////////////////////////////////////

     @ExperimentalStdlibApi
      @InternalAPI
      @DangerousInternalIoApi
      private suspend fun read_data_from_inputstream(): Deferred<ByteReadPacket?>?{
          Request_size = 0
          Object_size = 0
          x = 0
          bb = null
          val channel = MySocketChannel.client
          val c =
              ConnectionContext.async{
                  return@async try {
                      while (blocking_peek_with_timeout(SOCKET_TIMEOUT).also {
                              Object_size = it.await()
                          }.await() > COUNT_OF_0_BYTES && x < COUNT_OF_0_BYTES) {
                          if (channel.readS8()== 0) {
                              x++
                              if (x == COUNT_OF_0_BYTES) {
                                  x = if (channel.readS64LE() == 9223372036854775807L) {
                                      continue
                                  } else {
                                      0
                                  }
                              }
                          } else {
                              x = 0
                          }
                      }
                      read_data(channel)
                      bb?.build()
                  } catch (e: Exception){
                      bb = null
                      println("Ошибка в Connection (read_data): ".plus(e.message))
                      WriteExceptionIntoFile(e)
                      null
                  }
      }
      return c}*/
}