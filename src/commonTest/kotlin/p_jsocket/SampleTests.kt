@file:Suppress("UNUSED_VARIABLE")

package p_jsocket

import CrossPlatforms.PrintInformation
import com.soywiz.korio.async.await
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.lang.Thread_sleep
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import p_client.Jsocket


const val maxTimeSpanForWaitOutPut = 2000L



@OptIn(KorioExperimentalApi::class)
class SampleTests {
    fun testMe() = run < Unit > {

        val h = HASH()

        val m: String = h.getMD5String("AUF")
        val p: String = h.getMD5String("AUF")
        val ll = 24354678876543L

        val g: Long = h.getNewCoockiLong(m)
        PrintInformation.PRINT_INFO(g.toString())


        CoroutineScope(Dispatchers.Default).launchImmediately {

            val fileName = "ppppp"
            val k = FileService.getImmageAvatarFromFileName("F:\\Foto\\$fileName.JPG").await()
            val l16 = resourcesVfs["F:\\Foto\\"+fileName+"_kotlin4.jpg"]
            if (k != null) {
                l16.writeBytes(k)
            }
            PrintInformation.PRINT_INFO("image size ${k?.size}")
        }

        /*val h = HASH()
        val h1 = HASH()
        val pass = "gfhfjdkkdd"
        val mailCode = "jgjthdb"
        val passMD5: String = h.getMD5String(pass)
        PrintInformation.PRINT_INFO(passMD5)
        val res: String = h.cryptPass(passMD5, mailCode, true)
        PrintInformation.PRINT_INFO(res)

        val res1: String = h1.cryptPass(res, mailCode, false)
        PrintInformation.PRINT_INFO(res1)*/
        //GlobalScope.launchImmediately {
        //    val MyWebSocketChannel = WebSocketClient("ws://mini:22237", null, null, "", false)
       // }

        //val i = InitJsocket("", "")
        val l = Jsocket()
        //Thread_sleep(2000)
        //c.close()
        CoroutineScope(Dispatchers.Default).launchImmediately {
          /*  Thread_sleep(4000)

            CHATS.forEach { v ->
                if(v.value.getCHATS_SMALL_AVATAR() != null && v.value.getCHATS_SMALL_AVATAR()!!.isNotEmpty()){
                    val l16 = resourcesVfs["C:\\Foto\\${v.key}.jpg"]
                    //l16.open(VfsOpenMode.CREATE)
                    l16.writeBytes(v.value.getCHATS_SMALL_AVATAR()!!)
                }
            }*/

            Constants.myLang = "RUS"
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
                val l16 = resourcesVfs["F:\\Foto\\$fileName"].readAll()
                l.value_par1 = fileName
                l.content = FileService.getImmageAvatarFromByteArray(l16).await()
                val l166 = resourcesVfs["F:\\Foto\\ppppp.jpg"]
                l.content?.let { l166.writeBytes(it) }
                //l.execute().await()
                PrintInformation.PRINT_INFO("connection_id = ${l.connection_id}")
                PrintInformation.PRINT_INFO("connection_coocki = ${l.connection_coocki}")
                PrintInformation.PRINT_INFO("device_id = ${l.device_id}")
                PrintInformation.PRINT_INFO("just_do_it = ${l.just_do_it}")
                PrintInformation.PRINT_INFO("just_do_it_label= ${l.just_do_it_label}")
                PrintInformation.PRINT_INFO("just_do_it_successfull= ${l.just_do_it_successfull}")
                PrintInformation.PRINT_INFO("ip_port = ${l.ip_port}")
                PrintInformation.PRINT_INFO("lang = ${l.lang}")
                PrintInformation.PRINT_INFO("value_par1 = ${l.value_par1}")
                PrintInformation.PRINT_INFO("value_par2 = ${l.value_par2}")
                PrintInformation.PRINT_INFO("value_par3 = ${l.value_par3}")
                PrintInformation.PRINT_INFO("value_par4 = ${l.value_par4}")
                PrintInformation.PRINT_INFO("value_par5 = ${l.value_par5}")
                PrintInformation.PRINT_INFO("value_par6 = ${l.value_par6}")
                PrintInformation.PRINT_INFO("value_par7 = ${l.value_par7}")
                PrintInformation.PRINT_INFO("value_par8 = ${l.value_par8}")
                PrintInformation.PRINT_INFO("db_massage = ${l.db_massage}")

        }
        Thread_sleep(2000000L)
    }

        //Thread_sleep(10000)
}


