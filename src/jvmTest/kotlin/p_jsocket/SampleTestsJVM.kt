package p_jsocket

import com.soywiz.klock.DateTime
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import kotlin.test.Test
import kotlin.time.ExperimentalTime

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi

class SampleTestsJVM {

    @Test
    fun testMe()  = run < Unit > {

        CoroutineScope(Dispatchers.Default).launch {
            var tt = DateTime.nowUnixLong()
        val l = JSOCKET()
        val t = JSOCKET()
            Constants.myDeviceId = "AAAAAAAAAAAAAAAA"
            Constants.myConnectionsID = 102000111220000003L
            Constants.myConnectionsCoocki = 102000111225633533L
        val h = HASH()
        val b = byteArrayOf(
            'r'.code.toByte(), 't'.code.toByte(), 'y'.code.toByte(), 'u'.code.toByte(),
            'i'.code.toByte(), 'o'.code.toByte(), 'p'.code.toByte(), 'd'.code.toByte(),
            'f'.code.toByte(), '1'.code.toByte(), '2'.code.toByte(),
            '3'.code.toByte(), '4'.code.toByte(), '5'.code.toByte()
        )
        println("ch = ${h.getCheckSumFromByteArray(b, 42423424324324L)}")
        println("just_do_it_label = ${l.just_do_it_label}")
        println("just_do_it_label = ${t.just_do_it_label}")
        l.connection_id = 102000111220000003L
        l.connection_coocki = 102000111225633533L
        l.just_do_it = 1011000061
        l.ip_address = "192.168.0.1"
        l.device_id="ffffffffffffffff"
        l.ip_port = 22246
        l.lang= "RUS"
        l.object_extension = "mp4"
        l.object_size = 70911108L
        l.object_server = "FILE_SERVER_1"
        l.value_id1 = "106092821675592771"
        //MyJSOCKET.value_id2 = "10496594134378562A";
        l.value_par1 = "34242423423"
        l.value_par2 = "fsfsfsf"
        l.value_par3 = "gbdfbfd"
        l.value_par4 = "1"
        l.value_par5 = "trthrthtrhrthtrh"
        l.value_par6 = "oleh"
        l.value_par7 = "LEMKOLEG@RAMBLER.RU2"
        l.value_par8 = "p"
        l.value_par9 = "9C992BF1"
        l.db_massage = "ввівапрнооь.юх"
        val bch = ByteReadPacket(l.serialize(false))
        println("bch1 = ${bch.remaining}")
            println("just_do_it_label = ${t.just_do_it_label}")
            println("Time of execute 1 part: " + (DateTime.nowUnixLong() - tt))
            tt = DateTime.nowUnixLong()
        t.deserialize(bch, l.connection_coocki, false)
        println("bch1 = ${bch.remaining}")
        println("connection_id = ${t.connection_id}")
        println("connection_coocki = ${t.connection_coocki}")
        println("just_do_it = ${t.just_do_it}")
        println("just_do_it_label = ${t.just_do_it_label}")
        println("ip_port = ${t.ip_port}")
        println("lang = ${t.lang}")
        println("value_par1 = ${t.value_par1}")
        println("value_par2 = ${t.value_par2}")
        println("value_par3 = ${t.value_par3}")
        println("value_par4 = ${t.value_par4}")
        println("value_par5 = ${t.value_par5}")
        println("value_par6 = ${t.value_par6}")
        println("value_par7 = ${t.value_par7}")
        println("value_par8 = ${t.value_par8}")
        println("db_massage = ${t.db_massage}")
            println("Time of execute: " + (DateTime.nowUnixLong() - tt))
    }
        sleep(10000)
    }
}