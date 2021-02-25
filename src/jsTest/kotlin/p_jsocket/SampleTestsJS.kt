package p_jsocket

import io.ktor.util.InternalAPI
import io.ktor.util.KtorExperimentalAPI
import io.ktor.utils.io.core.BytePacketBuilder
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import kotlin.test.Test
import kotlin.time.ExperimentalTime

class SampleTestsJS {
    @KtorExperimentalAPI
    @DangerousInternalIoApi
    @InternalAPI
    @ExperimentalTime
    @ExperimentalStdlibApi
    @Test
    @io.ktor.utils.io.core.ExperimentalIoApi
    fun testMe()  = run < Unit > {
        val l = JSOCKET()
        val t = JSOCKET()
        val h = HASH()
        val b = byteArrayOf('r'.toByte(),'t'.toByte(),'y'.toByte(),'u'.toByte(),
            'i'.toByte(),'o'.toByte(),'p'.toByte(),'d'.toByte(),'f'.toByte(),'1'.toByte(),'2'.toByte(),
            '3'.toByte(),'4'.toByte(),'5'.toByte())
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
        val bch: BytePacketBuilder
        bch = l.serialize(craete_check_sum = true, verify_fields = true)!!
        println("bch1 = ${bch.size}")
        println("l.check_sum = ${l.check_sum}")
        t.deserialize(bch.build(), l.connection_coocki, false)
        println("bch1 = ${bch.size}")
        println("connection_id = ${t.connection_id}")
        println("connection_coocki = ${t.connection_coocki}")
        println("just_do_it = ${t.just_do_it}")
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
    }
}