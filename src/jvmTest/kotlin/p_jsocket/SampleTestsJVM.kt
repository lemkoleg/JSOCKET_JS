package p_jsocket

import CrossPlatforms.PrintInformation
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import io.ktor.utils.io.core.*
import kotlin.time.ExperimentalTime

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class SampleTestsJVM {
    suspend fun testMe()  = run < Unit > {
        val l = JSOCKET()
        val t = JSOCKET()
        val h = HASH()
        val b = byteArrayOf(
            'r'.code.toByte(), 't'.code.toByte(), 'y'.code.toByte(), 'u'.code.toByte(),
            'i'.code.toByte(), 'o'.code.toByte(), 'p'.code.toByte(), 'd'.code.toByte(),
            'f'.code.toByte(), '1'.code.toByte(), '2'.code.toByte(),
            '3'.code.toByte(), '4'.code.toByte(), '5'.code.toByte()
        )
        PrintInformation.PRINT_INFO("ch = ${h.getCheckSumFromByteArray(b, 42423424324324L)}")
        PrintInformation.PRINT_INFO("just_do_it_label = ${l.just_do_it_label}")
        PrintInformation.PRINT_INFO("just_do_it_label = ${t.just_do_it_label}")
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
        val bch = ByteReadPacket(l.serialize(true))
        PrintInformation.PRINT_INFO("bch1 = ${bch.remaining}")
        PrintInformation.PRINT_INFO("l.check_sum = ${l.check_sum}")
        t.deserialize(bch, l.connection_coocki, false)
        PrintInformation.PRINT_INFO("bch1 = ${bch.remaining}")
        PrintInformation.PRINT_INFO("connection_id = ${t.connection_id}")
        PrintInformation.PRINT_INFO("connection_coocki = ${t.connection_coocki}")
        PrintInformation.PRINT_INFO("just_do_it = ${t.just_do_it}")
        PrintInformation.PRINT_INFO("ip_port = ${t.ip_port}")
        PrintInformation.PRINT_INFO("lang = ${t.lang}")
        PrintInformation.PRINT_INFO("value_par1 = ${t.value_par1}")
        PrintInformation.PRINT_INFO("value_par2 = ${t.value_par2}")
        PrintInformation.PRINT_INFO("value_par3 = ${t.value_par3}")
        PrintInformation.PRINT_INFO("value_par4 = ${t.value_par4}")
        PrintInformation.PRINT_INFO("value_par5 = ${t.value_par5}")
        PrintInformation.PRINT_INFO("value_par6 = ${t.value_par6}")
        PrintInformation.PRINT_INFO("value_par7 = ${t.value_par7}")
        PrintInformation.PRINT_INFO("value_par8 = ${t.value_par8}")
        PrintInformation.PRINT_INFO("db_massage = ${t.db_massage}")
    }
}