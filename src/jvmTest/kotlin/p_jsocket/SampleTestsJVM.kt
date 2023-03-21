package p_jsocket

import CrossPlatforms.slash
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Signal
import com.soywiz.korio.async.await
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.file.std.localVfs
import com.soywiz.korio.net.ws.DEFAULT_WSKEY
import com.soywiz.korio.net.ws.WebSocketClient
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import p_client.InitJsocket
import p_client.Jsocket
import java.lang.Thread.sleep
import kotlin.test.Test



@Suppress("UNUSED_VARIABLE", "CanBeVal")
@OptIn( KorioExperimentalApi::class)

class SampleTestsJVM {

    @Test
    fun testMe() = run<Unit> {

        println(DEFAULT_WSKEY)
        val initJsocket = InitJsocket("D:${slash}DebugAUF", "5555555", "Win")

        val lock = Mutex()


        CoroutineScope(Dispatchers.Default).launchImmediately {

            var time = DateTime.nowUnixMillisLong()

            var bb = FileService.getImmageAvatarFromFileName("F:\\FotoDebug\\001 (1).jpg").await()
            var file = localVfs("F:\\FotoDebug\\001_kotlin.jpg")
            if(bb != null){
                    file.write(bb)
                }




            var myWebSocketChannel: WebSocketClient? = null
            var signalonOpen: Signal<Unit>?
            /*
                        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
                            withContext(EmptyCoroutineContext){
                            myWebSocketChannel = WebSocketClient(url = "ws://mini:22237", protocols = null, origin = null, wskey = DEFAULT_WSKEY, debug = false)

                            signalonOpen = myWebSocketChannel!!.onOpen
                            signalonOpen!!.add {
                                PrintInformation.PRINT_INFO("WebSocket connect 1111")
                            }

                            PrintInformation.PRINT_INFO("end connect 1111")}.toDeferred()

                        }.toPromise(EmptyCoroutineContext).await()


                        System.out.println("time wait join: " + (System.currentTimeMillis() - time))




                         */




            println("time: " + time)
            val constants = Constants
            //constants.setMyConnectionsID(102000111220000003l);
            //constants.setMyConnectionsCoocki(102000111225633533l);
            //constants.setMyDeviceId("AAAAAAAAAAAAAAAA");
            //constants.setMyConnectionsID(102000111220000003l);
            //constants.setMyConnectionsCoocki(102000111225633533l);
            //constants.setMyDeviceId("AAAAAAAAAAAAAAAA");
            val l = Jsocket()
            //l.setConnection_id(100000987605445435l);
            //l.setConnection_id(100000987605445435l);
            l.just_do_it = 1011000027
            l.lang = "UKR"
            l.value_par5 = "Debug 1"
            l.value_par6 = "Debug 1"
            l.value_par7 = "lemkoleg82@gmail.com"
            l.value_par8 = "80951113395"
            //val b = l.serialize(false)
            //System.out.println("b.size: " + b.size)
            //l.execute(null, null).await()

            System.out.println("time execute procedure: " + (System.currentTimeMillis() - time))

            println("Db_massage: " + l.db_massage)
        }
        sleep(200000)
    }
}