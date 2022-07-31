package p_client

import Tables.*
import Tables.KRegData.Companion.SelfAnswerType
import com.soywiz.klock.DateTime
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import p_jsocket.ANSWER_TYPE
import kotlin.time.ExperimentalTime


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class ActivitiInfo(val object_id: String = "",
                   val list_type_info: String = "", // what select: account info, album info, accounts, albums, objects
                   val list_type_info_sort: String = "1",
                   val other_condition1: String = "",
                   val other_condition2: String = "",
                   val other_condition3: String = "") {


    val cash_sum = "$object_id;$list_type_info;$list_type_info_sort;$other_condition1;$other_condition2;$other_condition3"

    var object_info: ANSWER_TYPE? = null

    var cashData: KCashData? = null

    var currentPosition: Int = 0

    init{

        if(object_id ==  Account_Id){
            object_info = SelfAnswerType
        }else{
            object_info = CASH_DATAS[object_id]?.ORDERED_CASH_DATA?.first()
            if(object_info != null){
                CASH_LAST_UPDATE[object_id]?.UPDATE_LAST_USE(DateTime.nowUnixLong())
            }
        }

        when(list_type_info){
            "3" //CHATS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "4" //MESSEGES
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "A" //ALBUMS_COMMENTS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "B" //ALBUMS_LINKS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "C" //ALBUMS_LINKS_COMMENTS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "D" //OBJECTS_LINKS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "E" //OBJECTS_LINKS_COMMENTS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "F" //OBJECTS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "G" //OBJECTS_COMMENTS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "H" //ACCOUNTS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
            "I" //ALBUMS
            -> {
                cashData = CASH_DATAS[cash_sum]
            }
        }
    }

}