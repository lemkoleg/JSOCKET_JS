@file:Suppress("UNUSED_VARIABLE")

package p_jsocket

import com.soywiz.korio.async.await
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.lang.Thread_sleep
import io.ktor.util.InternalAPI
import io.ktor.util.KtorExperimentalAPI
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import p_client.InitJsocket
import p_client.Jsocket
import p_client.myLang
import kotlin.test.Test
import kotlin.time.ExperimentalTime


const val maxTimeSpanForWaitOutPut = 2000L

class SampleTests {
    @ExperimentalUnsignedTypes
    @KorioExperimentalApi
    @KtorExperimentalAPI
    @DangerousInternalIoApi
    @InternalAPI
    @ExperimentalTime
    @ExperimentalStdlibApi
    @Test
    @io.ktor.utils.io.core.ExperimentalIoApi
    fun testMe() = run < Unit > {

        GlobalScope.launch {

            val fileName = "ppppp"
            val picr = FileService()
            val k = picr.getImmageAvatarFromFileName("F:\\Foto\\$fileName.JPG")
            val l16 = resourcesVfs["F:\\Foto\\"+fileName+"_kotlin4.jpg"]
            if (k != null) {
                l16.writeBytes(k)
            }
            println("image size ${k?.size}")
        }

        /*val h = HASH()
        val h1 = HASH()
        val pass = "gfhfjdkkdd"
        val mailCode = "jgjthdb"
        val passMD5: String = h.getMD5String(pass)
        println(passMD5)
        val res: String = h.cryptPass(passMD5, mailCode, true)
        println(res)

        val res1: String = h1.cryptPass(res, mailCode, false)
        println(res1)*/
        //GlobalScope.launch {
        //    val MyWebSocketChannel = WebSocketClient("ws://mini:22237", null, null, "", false)
       // }

        val i = InitJsocket("", "")
        val l = Jsocket()
        //Thread_sleep(2000)
        //c.close()
        GlobalScope.launch {
          /*  Thread_sleep(4000)

            CHATS.forEach { v ->
                if(v.value.getCHATS_SMALL_AVATAR() != null && v.value.getCHATS_SMALL_AVATAR()!!.isNotEmpty()){
                    val l16 = resourcesVfs["C:\\Foto\\${v.key}.jpg"]
                    //l16.open(VfsOpenMode.CREATE)
                    l16.writeBytes(v.value.getCHATS_SMALL_AVATAR()!!)
                }
            }*/

            myLang.setNewValue("RUS")
                l.connection_id = 0L
                l.connection_coocki = 0L
                //l.device_id = "39D91B1549BD5635"
                l.just_do_it = 1011000052
                l.lang = "RUS"
                //l.object_extension = "mp4";
                //l.object_size = 70911108L;
                //l.object_server = "FILE_SERVER_1";
                //l.value_id1 = "106092821675592771"
                l.value_id1 = "7794B849CE9EA58BD3"
                l.value_par1 = "34242423423"
                l.value_par2 = "fsfsfsf"
                l.value_par3 = "gbdfbfd"
                l.value_par4 = "1"
                l.value_par5 = "trthrthtrhrthtrh"
                l.value_par6 = "oleh"
                l.value_par7 = "LEMKOLEG@RAMBLER.RU1"
                //l.value_par7 = "PSEVDOSOUL@GMAIL.COM"
                l.value_par8 = "p"
                l.value_par9 = " C29522CF"
                l.db_massage = "ввівапрнооь.юх"
                val fileName = "DSCF2573.jpg"
                val picr = FileService()
                val l16 = resourcesVfs["F:\\Foto\\$fileName"].readAll()
                l.value_par1 = fileName
                l.content = picr.getImmageAvatarFromByteArray(l16)
                val l166 = resourcesVfs["F:\\Foto\\ppppp.jpg"]
                l.content?.let { l166.writeBytes(it) }
                //l.execute().await()
                println("connection_id = ${l.connection_id}")
                println("connection_coocki = ${l.connection_coocki}")
                println("device_id = ${l.device_id}")
                println("just_do_it = ${l.just_do_it}")
                println("just_do_it_label= ${l.just_do_it_label}")
                println("just_do_it_successfull= ${l.just_do_it_successfull}")
                println("ip_port = ${l.ip_port}")
                println("lang = ${l.lang}")
                println("value_par1 = ${l.value_par1}")
                println("value_par2 = ${l.value_par2}")
                println("value_par3 = ${l.value_par3}")
                println("value_par4 = ${l.value_par4}")
                println("value_par5 = ${l.value_par5}")
                println("value_par6 = ${l.value_par6}")
                println("value_par7 = ${l.value_par7}")
                println("value_par8 = ${l.value_par8}")
                println("db_massage = ${l.db_massage}")

        }
        Thread_sleep(2000000L)
    }

        //Thread_sleep(10000)
}


