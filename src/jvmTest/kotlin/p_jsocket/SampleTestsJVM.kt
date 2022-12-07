package p_jsocket

import CrossPlatforms.MyCondition
import CrossPlatforms.PrintInformation
import CrossPlatforms.slash
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Signal
import com.soywiz.korio.async.addSuspend
import com.soywiz.korio.async.await
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.net.ws.DEFAULT_WSKEY
import com.soywiz.korio.net.ws.WebSocketClient
import io.ktor.util.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import p_client.InitJsocket
import p_client.Jsocket
import java.lang.Thread.sleep
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi

class SampleTestsJVM {

    @Test
    fun testMe() = run<Unit> {

        println(DEFAULT_WSKEY)
        val initJsocket = InitJsocket("D:${slash}DebugAUF", null, null)

        val lock = Mutex()

        CoroutineScope(Dispatchers.Default).launch {



            var time = DateTime.nowUnixMillisLong()


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
            l.just_do_it = 1011000026
            l.value_par5 = "Debug 1"
            l.value_par6 = "Debug 1"
            l.value_par7 = "lemkoleg82@gmail.com"
            val b = l.serialize(false)
            System.out.println("b.size: " + b.size)
            sleep(5000)
            l.execute(null, null).await()

            System.out.println("time execute procedure: " + (System.currentTimeMillis() - time))

            println("Db_massage: " + l.db_massage)
        }
        sleep(10000)
    }
}