package p_jsocket

import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan
import com.soywiz.korio.async.delay
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

        val lock = Mutex()


        CoroutineScope(Dispatchers.Default).launch {

            val rr = CrossPlatforms.JavaRunBlocking()


            InitJsocket("", "AAAAAAAAAAA", "IOS",null)





            val time = DateTime.nowUnixMillisLong()

            /*
            var bb = FileService.getImmageAvatarFromFileName("F:\\FotoDebug\\001 (1).jpg").await()
            var file = localVfs("F:\\FotoDebug\\001_kotlin.jpg")
            if(bb != null){
                    file.write(bb)
                }


             */



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
            rr.RunBlocking(l.execute(null, null))

            System.out.println("time execute procedure: " + (System.currentTimeMillis() - time))

            println("Db_massage: " + l.db_massage)
        }
        sleep(200000)
        for(x in 1..100){
            runBlocking {
                println("Wait for 5sec")
                delay(TimeSpan(5000.0))
                println("Done waiting for 5sec")
            }
        }
    }
}