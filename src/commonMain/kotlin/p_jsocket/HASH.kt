@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")
package p_jsocket

import com.soywiz.krypto.AES
import com.soywiz.krypto.MD5
import com.soywiz.krypto.Padding
import com.soywiz.krypto.md5
import io.ktor.util.*
import io.ktor.utils.io.core.*
import lib_exceptions.my_user_exceptions_class
import kotlin.js.JsName


/*
 *
 * @author Oleg
 */

private const val valid = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
private const val POLY64 = 0x42F0E1EBA9EA3693L
private val xor_map: Map<String, String> = mapOf(
    "00" to "0",
    "01" to "1",
    "02" to "2",
    "03" to "3",
    "04" to "4",
    "05" to "5",
    "06" to "6",
    "07" to "7",
    "08" to "8",
    "09" to "9",
    "0A" to "A",
    "0B" to "B",
    "0C" to "C",
    "0D" to "D",
    "0E" to "E",
    "0F" to "F",
    "10" to "1",
    "11" to "0",
    "12" to "3",
    "13" to "2",
    "14" to "5",
    "15" to "4",
    "16" to "7",
    "17" to "6",
    "18" to "9",
    "19" to "8",
    "1A" to "B",
    "1B" to "A",
    "1C" to "D",
    "1D" to "C",
    "1E" to "F",
    "1F" to "E",
    "20" to "2",
    "21" to "3",
    "22" to "0",
    "23" to "1",
    "24" to "6",
    "25" to "7",
    "26" to "4",
    "27" to "5",
    "28" to "A",
    "29" to "B",
    "2A" to "8",
    "2B" to "9",
    "2C" to "E",
    "2D" to "F",
    "2E" to "C",
    "2F" to "D",
    "30" to "3",
    "31" to "2",
    "32" to "1",
    "33" to "0",
    "34" to "7",
    "35" to "6",
    "36" to "5",
    "37" to "4",
    "38" to "B",
    "39" to "A",
    "3A" to "9",
    "3B" to "8",
    "3C" to "F",
    "3D" to "E",
    "3E" to "D",
    "3F" to "C",
    "40" to "4",
    "41" to "5",
    "42" to "6",
    "43" to "7",
    "44" to "0",
    "45" to "1",
    "46" to "2",
    "47" to "3",
    "48" to "C",
    "49" to "D",
    "4A" to "E",
    "4B" to "F",
    "4C" to "8",
    "4D" to "9",
    "4E" to "A",
    "4F" to "B",
    "50" to "5",
    "51" to "4",
    "52" to "7",
    "53" to "6",
    "54" to "1",
    "55" to "0",
    "56" to "3",
    "57" to "2",
    "58" to "D",
    "59" to "C",
    "5A" to "F",
    "5B" to "E",
    "5C" to "9",
    "5D" to "8",
    "5E" to "B",
    "5F" to "A",
    "60" to "6",
    "61" to "7",
    "62" to "4",
    "63" to "5",
    "64" to "2",
    "65" to "3",
    "66" to "0",
    "67" to "1",
    "68" to "E",
    "69" to "F",
    "6A" to "C",
    "6B" to "D",
    "6C" to "A",
    "6D" to "B",
    "6E" to "8",
    "6F" to "9",
    "70" to "7",
    "71" to "6",
    "72" to "5",
    "73" to "4",
    "74" to "3",
    "75" to "2",
    "76" to "1",
    "77" to "0",
    "78" to "F",
    "79" to "E",
    "7A" to "D",
    "7B" to "C",
    "7C" to "B",
    "7D" to "A",
    "7E" to "9",
    "7F" to "8",
    "80" to "8",
    "81" to "9",
    "82" to "A",
    "83" to "B",
    "84" to "C",
    "85" to "D",
    "86" to "E",
    "87" to "F",
    "88" to "0",
    "89" to "1",
    "8A" to "2",
    "8B" to "3",
    "8C" to "4",
    "8D" to "5",
    "8E" to "6",
    "8F" to "7",
    "90" to "9",
    "91" to "8",
    "92" to "B",
    "93" to "A",
    "94" to "D",
    "95" to "C",
    "96" to "F",
    "97" to "E",
    "98" to "1",
    "99" to "0",
    "9A" to "3",
    "9B" to "2",
    "9C" to "5",
    "9D" to "4",
    "9E" to "7",
    "9F" to "6",
    "A0" to "A",
    "A1" to "B",
    "A2" to "8",
    "A3" to "9",
    "A4" to "E",
    "A5" to "F",
    "A6" to "C",
    "A7" to "D",
    "A8" to "2",
    "A9" to "3",
    "AA" to "0",
    "AB" to "1",
    "AC" to "6",
    "AD" to "7",
    "AE" to "4",
    "AF" to "5",
    "B0" to "B",
    "B1" to "A",
    "B2" to "9",
    "B3" to "8",
    "B4" to "F",
    "B5" to "E",
    "B6" to "D",
    "B7" to "C",
    "B8" to "3",
    "B9" to "2",
    "BA" to "1",
    "BB" to "0",
    "BC" to "7",
    "BD" to "6",
    "BE" to "5",
    "BF" to "4",
    "C0" to "C",
    "C1" to "D",
    "C2" to "E",
    "C3" to "F",
    "C4" to "8",
    "C5" to "9",
    "C6" to "A",
    "C7" to "B",
    "C8" to "4",
    "C9" to "5",
    "CA" to "6",
    "CB" to "7",
    "CC" to "0",
    "CD" to "1",
    "CE" to "2",
    "CF" to "3",
    "D0" to "D",
    "D1" to "C",
    "D2" to "F",
    "D3" to "E",
    "D4" to "9",
    "D5" to "8",
    "D6" to "B",
    "D7" to "A",
    "D8" to "5",
    "D9" to "4",
    "DA" to "7",
    "DB" to "6",
    "DC" to "1",
    "DD" to "0",
    "DE" to "3",
    "DF" to "2",
    "E0" to "E",
    "E1" to "F",
    "E2" to "C",
    "E3" to "D",
    "E4" to "A",
    "E5" to "B",
    "E6" to "8",
    "E7" to "9",
    "E8" to "6",
    "E9" to "7",
    "EA" to "4",
    "EB" to "5",
    "EC" to "2",
    "ED" to "3",
    "EE" to "0",
    "EF" to "1",
    "F0" to "F",
    "F1" to "E",
    "F2" to "D",
    "F3" to "C",
    "F4" to "B",
    "F5" to "A",
    "F6" to "9",
    "F7" to "8",
    "F8" to "7",
    "F9" to "6",
    "FA" to "5",
    "FB" to "4",
    "FC" to "3",
    "FD" to "2",
    "FE" to "1",
    "FF" to "0"
)

private fun r(t: Int)
        : Long {
    var l: Long = t.toLong()
    for (j in 1..8) {
        l = if ((l and 1) == 1L) {
            ((l ushr 1) xor POLY64)
        } else {
            (l ushr 1)
        }
    }
    return l
}

private val LOOKUPTABLE2 = Array(0x100) { i -> r(i) }

///////////////////////////////////////////////////////////////////////////////

@JsName("HASH")
class HASH {

    private val md5 : MD5 = MD5()

    private fun getIntFromHehSymbol(l_char: String): Int {
        return l_char.toInt(16)
    }

    ////////////////////////////////////////////////////////////////////////////////
    private fun getIntFromHehSymbol(l_char: String, length_of_token: Int): Int {
        var r = l_char.toInt(radix = 16)
        if (r >= length_of_token) {
            r = length_of_token - 1
        }
        return r
    }

    ////////////////////////////////////////////////////////////////////////////////
    private fun getXorHexSymbolFrom2HexSymbols(symbol1: String, symbol2: String): String {
        return xor_map[symbol1.uppercase().plus(symbol2.uppercase())] ?: error("error on seek getXorHexSymbolFrom2HexSymbols")
    }

    ////////////////////////////////////////////////////////////////////////////////

    @InternalAPI
    @JsName("getNewTokenLong")
    fun getNewTokenLong(user_name: String, pass: String, time: Long): Long {
            var newStringMD5 = ""
            val md5Hex: String = getMD5String(user_name.trim().plus(pass).trim().plus(time.toString()))
////diapazone for 18 digits UnsignedLong: 16345785d8a0001 - de0b6b3a763ffff (15 chars)//////////////////
        val stringMD5String1: Long = md5Hex.substring(0, 15).toLong(16)
        val stringMD5String2: Long = md5Hex.substring(15, 30).toLong(16)
        var TokenLong: Long = stringMD5String1 xor stringMD5String2
            if (TokenLong > 999999999999999999L) {
                newStringMD5 = "ddf".plus(
                        newStringMD5.substring(3)
                )
                TokenLong = newStringMD5.toLong(16)
            }
            if (TokenLong < 100000000000000001L) {
                if(TokenLong < 72057594037927936L){
                    TokenLong += TokenLong
                }
                newStringMD5 = "164".plus(
                        newStringMD5.substring(3)
                )
                TokenLong = newStringMD5.toLong(16)
            }
            if (TokenLong > 999999999999999999L || TokenLong < 100000000000000001L) {
                throw my_user_exceptions_class(
                    l_class_name = "HASH",
                    l_function_name = "getNewTokenLong",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Wrong format new token: $TokenLong"
                )
            }
            return TokenLong
    }

    ////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @JsName("getNewCoockiLong")
    fun getNewCoockiLong(stringMD5: String): Long {
        val stringMD5String1: Long = stringMD5.substring(0, 15).toLong(16)
        val stringMD5String2: Long = stringMD5.substring(15, 30).toLong(16)
        var TokenLong: Long = stringMD5String1 xor stringMD5String2
        var newStringMD5: String
        if (TokenLong > 999999999999999999L) {
                newStringMD5 = TokenLong.toString(16)
                newStringMD5 = "ddf".plus(
                        newStringMD5.substring(3, 15)
                )
                TokenLong = newStringMD5.toLong(16)
            }
            if (TokenLong < 100000000000000001L) {
                if(TokenLong < 72057594037927936L){
                    TokenLong += TokenLong
                }
                newStringMD5 = TokenLong.toString(16)
                newStringMD5 = "164".plus(other = newStringMD5.substring(3, 15))
                TokenLong = newStringMD5.toLong(16)
            }
            if (TokenLong > 999999999999999999L || TokenLong < 100000000000000001L) {
                throw my_user_exceptions_class(
                    l_class_name = "HASH",
                    l_function_name = "getNewCoockiLong",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Wrong format new token: $TokenLong"
                )
            }
            return TokenLong
    }

    ////////////////////////////////////////////////////////////////////////////////
    @JsName("getNewMD5String")
    fun getNewMD5String(l_token: Long, time: Long): String {
        return getMD5String(l_token.toString().plus(other = time.toString()))
    }

    ////////////////////////////////////////////////////////////////////////////////
    @JsName("getNewMD5LongArray")
    fun getNewMD5LongArray(MD5String: String): LongArray {
            val l = LongArray(size = 16) { 0L }
            for (x in 0..15) {
                l[x] = MD5String.substring(x, x + 15).toLong(16)
            }
            return l
    }

    ////////////////////////////////////////////////////////////////////////////////

    @JsName("getMD5String")
    fun getMD5String(input_string: String): String {
        return input_string.encodeToByteArray().md5().hexUpper

    }

    @JsName("getReverseMD5String")
    fun getReverseMD5String(input_string: String): String {
        return input_string.reversed()

    }
////////////////////////////////////////////////////////////////////////////////////
    @JsName("getNewMD5longArray")
    fun getNewMD5longArray(MD5String: String): LongArray {
        val l = LongArray(16)
        for (x in 0..15) {
            l[x] = MD5String.substring(x, x + 15).toLong(16)
        }
        return l
}
    @JsName("getReverseMD5longArray")
    fun getReverseMD5longArray(reverseMD5String: String): LongArray {
        val l = LongArray(16)
        for (x in 0..15) {
            l[x] = reverseMD5String.substring(x, x + 15).toLong(16)
        }
        return l
    }
////////////////////////////////////////////////////////////////////////////////
    @JsName("getCheckSumFromByteArray")
     fun getCheckSumFromByteArray(data: ByteArray, checksum: Long): Long {
            var l: Long = checksum
            for (element in data) {
                val lookupidx: Int = ((checksum.toInt() xor element.toInt()) and 0xff)
                l = ((l ushr 8) xor LOOKUPTABLE2[lookupidx])
            }
            return l
    }

////////////////////////////////////////////////////////////////////////////////
    @JsName("getCheckSumFromLong")
    fun getCheckSumFromLong(data: Long, checksum: Long): Long {
        var l : Long = checksum
        for(i in 0..7) {
            val lookupidx : Int = ((l.toInt() xor (data shr (i * 8)).toByte().toInt()) and 0xff)
            l = (l ushr  8) xor LOOKUPTABLE2[lookupidx]
        }
        return l
    }


////////////////////////////////////////////////////////////////////////////////
    @InternalAPI
    @JsName("cryptPass")
    fun cryptPass(pass: String, mail_code: String, isCrypt : Boolean): String {
            val mail_codeMD5 = getMD5String(mail_code).substring(0, 16).toByteArray()
            val passCode = pass.decodeBase64Bytes()
            return try {
                if (isCrypt) {
                    AES.encryptAesCbc(data = passCode, key = mail_codeMD5,
                        padding = Padding.PKCS7Padding, iv = mail_codeMD5
                    ).encodeBase64()
                } else {
                    AES.decryptAesCbc(data = passCode, key = mail_codeMD5,
                        padding = Padding.PKCS7Padding, iv = mail_codeMD5
                    ).encodeBase64()
                }
            } catch (ex: Exception) {
                throw my_user_exceptions_class(
                    l_class_name = "HASH",
                    l_function_name = "cryptPass",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = ex.message
                )
                return ""
            }
    }
}