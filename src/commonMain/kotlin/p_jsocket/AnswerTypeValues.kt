package p_jsocket

import Tables.*
import atomic.lockedGet
import atomic.lockedPut
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import lib_exceptions.my_user_exceptions_class
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class AnswerTypeValues(l_answerType: ANSWER_TYPE) {

    private var answerType: ANSWER_TYPE = l_answerType

    private var RECORD_TYPE_IS_DEFINED = false

    var IsHaveAlbumInfo = false
    var AlbumInfo: KObjectInfo? = null

    var IsHaveObjectInfo = false
    var ObjectInfo: KObjectInfo? = null

    var IsHaveAccountInfo = false
    var AccountInfo: KObjectInfo? = null

    val answerTypeConstants: AnswerTypeConstants by lazy { AnswerTypeConstants(answerType) }

    val answerTypeCashDatas: AnswerTypeCashDatas by lazy { AnswerTypeCashDatas(answerType) }


    @JsName("DefineRECORD_TYPE")
    fun DefineRECORD_TYPE() {
        if (answerType.STRING_20.length < 8) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "DefineECORD_TYPE",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_20"
            )
        }
        if (answerType.RECORD_TYPE != answerType.STRING_20.substring(7, 8)) {
            if (RECORD_TYPE_IS_DEFINED) {
                throw my_user_exceptions_class(
                    l_class_name = "AnswerTypeValues",
                    l_function_name = "DefineECORD_TYPE",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "RECORD_TYPE IS DEFINED"
                )
            } else {
                answerType.RECORD_TYPE = answerType.STRING_20.substring(7, 8)
            }
        }

        RECORD_TYPE_IS_DEFINED = true

        //initValues()
    }

    @JsName("setRECORD_TYPE")
    fun setRECORD_TYPE(v: String?) {
        if (answerType.STRING_20.length < 8) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "setRECORD_TYPE",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_20"
            )
        }

        if (v == null || v.length != 1) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "setRECORD_TYPE",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of value of record type."
            )
        }

        answerType.STRING_20 = answerType.STRING_20.substring(0, 7) + v + answerType.STRING_20.substring(8)

        DefineRECORD_TYPE()
    }

    //////////////////////// common ////////////////////////////////////

    @JsName("GetMainAccountId")
    var GetMainAccountId: () -> String = { getIDENTIFICATOR_1() }

    @JsName("GetSecondAccountId")
    var GetSecondAccountId: () -> String = { getIDENTIFICATOR_3() }

    @JsName("GetMainAvatarId")
    var GetMainAvatarId: () -> String = { getIDENTIFICATOR_2() }

    @JsName("GetSecondAvatarId")
    var GetSecondAvatarId: () -> String = { getIDENTIFICATOR_4() }

    @JsName("GetMainAccountName")
    var GetMainAccountName: () -> String = { getSTRING_1() }

    @JsName("GetSecondAccountName")
    var GetSecondAccountName: () -> String = { getSTRING_4() }

    @JsName("GetAvatarServer")
    var GetAvatarServer: () -> String = { getSTRING_17() }

    @JsName("GetAvatarLink")
    var GetAvatarLink: () -> String = { getSTRING_18() }

    @JsName("GetAvatarType")
    var GetAvatarType: () -> String = { getSTRING_19() }

    @JsName("GetAvatarOriginalSize")
    var GetAvatarOriginalSize: () -> Int = { getINTEGER_17() }

    @JsName("GetObjectId")
    var GetObjectId: () -> String = { getEMPTY_STRING() }

    @JsName("GetSubscribe")
    var GetSubscribe: () -> String = { getSTRING_14() }

    @JsName("GetMainAccountAccess")
    var GetMainAccountAccess: () -> String = { getSTRING_2() }

    @JsName("GetSecondAccountAccess")
    var GetSecondAccountAccess: () -> String = { getSTRING_4() }

    @JsName("GetObjectProfile")
    var GetObjectProfile: () -> String = { getEMPTY_STRING() }

    @JsName("GetObjectType")
    var GetObjectType: () -> String = { getEMPTY_STRING() }

    @JsName("GetObjectAccess")
    var GetObjectAccess: () -> String = { getEMPTY_STRING() }

    @JsName("GetObjectStatus")
    var GetObjectStatus: () -> String = { getEMPTY_STRING() }

    @JsName("GetObjectLikes")
    var GetObjectLikes: () -> Long = { getEMPTY_LONG() }

    @JsName("GetObjectDisLikes")
    var GetObjectDisLikes: () -> Long = { getEMPTY_LONG() }

    @JsName("GetObjectComments")
    var GetObjectComments: () -> Long = { getEMPTY_LONG() }

    @JsName("GetLinkProfile")
    var GetLinkProfile: () -> String = { getEMPTY_STRING() }

    @JsName("GetLinkType")
    var GetLinkType: () -> String = { getEMPTY_STRING() }

    @JsName("GetLinkAccess")
    var GetLinkAccess: () -> String = { getEMPTY_STRING() }

    @JsName("GetLinkStatus")
    var GetLinkStatus: () -> String = { getEMPTY_STRING() }

    @JsName("GetLinkLikes")
    var GetLinkLikes: () -> Long = { getEMPTY_LONG() }

    @JsName("GetLinkDisLikes")
    var GetLinkDisLikes: () -> Long = { getEMPTY_LONG() }

    @JsName("GetLinkComments")
    var GetLinkComments: () -> Long = { getEMPTY_LONG() }

    @JsName("GetAddingDate")
    var GetAddingDate: () -> Long = { getEMPTY_LONG() }

    @JsName("GetDateDelete")
    var GetDateDelete: () -> Long = { getEMPTY_LONG() }

    @JsName("GetLinkAddingDate")
    var GetLinkAddingDate: () -> Long = { getEMPTY_LONG() }

    @JsName("GetObjectLastUpdate")
    var GetRecordLastUpdate: () -> Long = { getLONG_20() }

    @JsName("GetSmallAvatar")
    var GetSmallAvatar: () -> ByteArray? = { getBLOB_1() }

    @JsName("GetBigAvatar")
    var GetBigAvatar: () -> ByteArray? = { getBLOB_2() }

    //////////////////// albums /////////////////////////////

    @JsName("GetAlbumId")
    var GetAlbumId: () -> String = { getEMPTY_STRING() }

    @JsName("GetAlbumName")
    var GetAlbumName: () -> String = { getEMPTY_STRING() }

    @JsName("GetAlbumsSubscibeStartText")
    var GetAlbumsDescribeStartText: () -> String = { getEMPTY_STRING() }

    @JsName("GetAlbumsTypeContent")
    var GetAlbumsTypeContent: () -> String = { getEMPTY_STRING() }

    @JsName("GetAlbumsCountOfContents")
    var GetAlbumsCountOfContents: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetAlbumsMembers")
    var GetAlbumsMembers: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetAlbumsListeners")
    var GetAlbumsListeners: () -> Int = { getEMPTY_INTEGER() }

    /////////////////////// comments ////////////////////////

    @JsName("GetCommentId")
    var GetCommentId: () -> Long = { getEMPTY_LONG() }

    @JsName("GetRootCommentId")
    var GetRootCommentId: () -> Long = { getEMPTY_LONG() }

    @JsName("GetAnswerCommentId")
    var GetAnswerCommentId: () -> Long = { getEMPTY_LONG() }

    @JsName("GetCommentsStartText")
    var GetCommentsStartText: () -> String = { getEMPTY_STRING() }

    @JsName("HaveFullText")
    var HaveFullText: () -> Boolean = { false }

    @JsName("GetCommentsCountsAnswers")
    var GetCommentsCountsAnswers: () -> Int = { getEMPTY_INTEGER() }

    /////////////////////// objects /////////////////////

    @JsName("GetObjectSize")
    var GetObjectSize: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetLengthSeconds")
    var GetLengthSeconds: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetObjectName")
    var GetObjectName: () -> String = { getEMPTY_STRING() }

    @JsName("GetObjectServer")
    var GetObjectServer: () -> String = { getEMPTY_STRING() }

    @JsName("GetObjectLink")
    var GetObjectLink: () -> String = { getEMPTY_STRING() }

    @JsName("GetObjectExtension")
    var GetObjectExtension: () -> String = { getEMPTY_STRING() }

    @JsName("GetObjectListens")
    var GetObjectListens: () -> Long = { getEMPTY_LONG() }

    @JsName("GetObjectDescriptors")
    var GetObjectDescriptors: () -> Long = { getEMPTY_LONG() }

    @JsName("GetObjectListensPeriod")
    var GetObjectListensPeriod: () -> Int = { getEMPTY_INTEGER() }

    /////////////////////// links ///////////////////////////

    @JsName("GetLinkOwner")
    var GetLinkOwner: () -> String = { getEMPTY_STRING() }

    @JsName("GetAlbumsLinkCountOfNewContent")
    var GetAlbumsLinkCountOfNewContent: () -> Int = { getEMPTY_INTEGER() }

    ///////////////////// messegess //////////////////////////

    @JsName("GetChatId")
    var GetChatId: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegeId")
    var GetMessegeId: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsCostTypeId")
    var GetChatsCostTypeId: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetMessegeStartText")
    var GetMessegeStartText: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegeType")
    var GetMessegeType: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegeAccess")
    var GetMessegeAccess: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegeStatus")
    var GetMessegeStatus: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegeHaveFullText")
    var GetMessegeHaveFullText: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegeHaveAnswer")
    var GetMessegeHaveAnswer: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegeObjectType")
    var GetMessegeObjectType: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegeFileType")
    var GetMessegeFileType: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegePeriodFor")
    var GetMessegePeriodFor: () -> Long = { getEMPTY_LONG() }

    @JsName("GetAnswerMessegeId")
    var GetAnswerMessegeId: () -> Long = { getEMPTY_LONG() }

    @JsName("GetAnswerMessegeStartText")
    var GetAnswerMessegeStartText: () -> String = { getEMPTY_STRING() }

    @JsName("GetMessegeCost")
    var GetMessegeCost: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetAccountSmallAvatar")
    var GetAccountSmallAvatar: () -> ByteArray? = { getBLOB_1() }

    @JsName("GetAccountBigAvatar")
    var GetAccountBigAvatar: () -> ByteArray? = { getBLOB_3() }

    ///////////////////// chats //////////////////////////

    @JsName("GetChatsMessegeCount")
    var GetChatsMessegeCount: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsDateClosed")
    var GetChatsDateClosed: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsLastMessegeAdding")
    var GetChatsLastMessegeAdding: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsCountOfMembers")
    var GetChatsCountOfMembers: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetChatsCountOfAllMembers")
    var GetChatsCountOfAllMembers: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetChatsCountOfConstsTypes")
    var GetChatsCountOfConstsType: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetChatsBalance")
    var GetChatsBalance: () -> Long = { getEMPTY_LONG() }

    @JsName("GetFirstMessegeStartText")
    var GetFirstMessegeStartText: () -> String = { getEMPTY_STRING() }

    @JsName("GetChatsCountNotReadedMess")
    var GetChatsCountNotReadedMess: () -> Long = { getEMPTY_LONG() }

    ///////////////////// chats likes //////////////////////////

    @JsName("GetChatsLikesRelations")
    var GetChatsLikesRelations: () -> String = { getEMPTY_STRING() }

    @JsName("GetChatsLikesLastConnect")
    var GetChatsLikesLastConnect: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsLikesFirstMessegeCount")
    var GetChatsLikesFirstMessegeCount: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsLikesLastMessegeCount")
    var GetChatsLikesLastMessegeCount: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsLikesLastDateDilivered")
    var GetChatsLikesLastDateDilivered: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsLikesLastReadedMessegeId")
    var GetChatsLikesLastReadedMessegeId: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsLikesLastMessegeAdding")
    var GetChatsLikesLastMessegeAdding: () -> Long = { getEMPTY_LONG() }

    @JsName("GetChatsLikesBalance")
    var GetChatsLikesBalance: () -> Int = { getEMPTY_INTEGER() }

    @JsName("GetLastUpdatest")
    var GetLastUpdatest: () -> Long = { getEMPTY_LONG() }

    ////////////////////////// chats_cost_types ////////////////////

    @JsName("GetChatsCostTypesStartText")
    var GetChatsCostTypesStartText: () -> String = { getEMPTY_STRING() }

    @JsName("GetChatsCostTypesHaveFullText")
    var GetChatsCostTypesHaveFullText: () -> String = { getEMPTY_STRING() }

    @JsName("GetChatsCostTypesHaveMesseges")
    var GetChatsCostTypesHaveMesseges: () -> String = { getEMPTY_STRING() }





    fun initValues() {

        when (answerType.RECORD_TYPE) {
            "1" //COMMANDS
            -> {

            }
            "2" //METADATA
            -> {

            }
            "3"  //CHATS
            -> {
                GetMainAccountId = { getIDENTIFICATOR_7() }
                GetMainAvatarId = { getIDENTIFICATOR_6() }
                GetObjectId = { getIDENTIFICATOR_5() }
                GetChatId = { getIDENTIFICATOR_5() }
                GetAddingDate = { getLONG_4() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                GetAddingDate = { getLONG_4() }
                GetChatsMessegeCount = { getLONG_5() }
                GetChatsDateClosed = { getLONG_6() }
                GetChatsLastMessegeAdding = { getLONG_8() }
                GetChatsCountOfMembers = { getINTEGER_5() }
                GetChatsCountOfAllMembers = { getINTEGER_6() }
                GetChatsCountOfConstsType = { getINTEGER_7() }
                GetChatsBalance = { getLONG_9() }

                if (GetObjectType() == "0") {  // CHAT_IS_CHAT;
                    if (GetMainAccountId() == Constants.Account_Id) {
                        GetObjectName = {
                            CASH_DATAS[(getIDENTIFICATOR_5() + "800")]?.CASH_DATA_RECORDS?.get(getSTRING_5())?.answerTypeValues?.getSTRING_1()
                                ?: getEMPTY_STRING()
                        }
                        GetSmallAvatar = {
                            CASH_DATAS[(getIDENTIFICATOR_5() + "800")]?.CASH_DATA_RECORDS?.get(getSTRING_5())?.answerTypeValues?.getBLOB_1()
                                ?: getEMPTY_BLOB()
                        }
                        GetBigAvatar = {
                            CASH_DATAS[(getIDENTIFICATOR_5() + "800")]?.CASH_DATA_RECORDS?.get(getSTRING_5())?.answerTypeValues?.getBLOB_3()
                                ?: getEMPTY_BLOB()
                        }
                    } else {
                        GetObjectName = {
                            CASH_DATAS[(getIDENTIFICATOR_5() + "800")]?.CASH_DATA_RECORDS?.get(getIDENTIFICATOR_7())?.answerTypeValues?.getSTRING_1()
                                ?: getEMPTY_STRING()
                        }
                        GetSmallAvatar = {
                            CASH_DATAS[(getIDENTIFICATOR_5() + "800")]?.CASH_DATA_RECORDS?.get(getIDENTIFICATOR_7())?.answerTypeValues?.getBLOB_1()
                                ?: getEMPTY_BLOB()
                        }
                        GetBigAvatar = {
                            CASH_DATAS[(getIDENTIFICATOR_5() + "800")]?.CASH_DATA_RECORDS?.get(getIDENTIFICATOR_7())?.answerTypeValues?.getBLOB_3()
                                ?: getEMPTY_BLOB()
                        }
                    }
                } else {
                    GetObjectName = { getSTRING_5() }
                }

                GetFirstMessegeStartText = {
                    CASH_DATAS[(getIDENTIFICATOR_5() + "410")]?.ORDERED_CASH_DATA?.firstOrNull()?.answerTypeValues?.GetMessegeStartText?.let { it() }
                        ?: getEMPTY_STRING()
                }

                GetChatsCountNotReadedMess = {
                    GetChatsMessegeCount() - (CASH_DATAS[(getIDENTIFICATOR_5() + "800")]?.CASH_DATA_RECORDS?.get(
                        Constants.Account_Id
                    )?.answerTypeValues?.GetChatsLikesLastReadedMessegeId?.let { it() }
                        ?: getEMPTY_LONG())
                }

                GetLinkOwner = { getIDENTIFICATOR_5() } //chats id;

                answerType.RECORD_TABLE_ID = GetLinkOwner()
            }
            "4", "M" //MESSEGES
            -> {
                GetMainAccountId = { getIDENTIFICATOR_12() }
                GetSecondAccountId = { getIDENTIFICATOR_13() }
                GetMessegeId = { getLONG_12() }
                GetChatId = { getIDENTIFICATOR_11() }
                GetAddingDate = { getLONG_11() }
                GetMessegeStartText = { getSTRING_11() }
                GetMessegeType = { getSTRING_12().substring(0, 1) }
                GetMessegeAccess = { getSTRING_12().substring(1, 2) }
                GetMessegeStatus = { getSTRING_12().substring(2, 3) }
                GetMessegeHaveFullText = { getSTRING_12().substring(3, 4) }
                GetMessegeHaveAnswer = { getSTRING_12().substring(4, 5) }
                GetMessegeObjectType = { getSTRING_12().substring(5, 6) }
                GetMessegePeriodFor = { getLONG_13() }
                GetAnswerMessegeId = { getLONG_14() }
                GetAnswerMessegeStartText = { getSTRING_13() }
                GetMessegeCost = { getINTEGER_11() }
                GetChatsCostTypeId = { getINTEGER_12() }

                GetMainAccountName = {
                    CASH_DATAS[(getIDENTIFICATOR_11() + "800")]?.CASH_DATA_RECORDS?.get(getIDENTIFICATOR_7())?.answerTypeValues?.getSTRING_1()
                        ?: getEMPTY_STRING()
                }
                GetSecondAccountName = {
                    CASH_DATAS[(getIDENTIFICATOR_11() + "800")]?.CASH_DATA_RECORDS?.get(GetSecondAccountId())?.answerTypeValues?.getSTRING_1()
                        ?: getEMPTY_STRING()
                }
                GetAccountSmallAvatar = {
                    CASH_DATAS[(getIDENTIFICATOR_11() + "800")]?.CASH_DATA_RECORDS?.get(getIDENTIFICATOR_7())?.answerTypeValues?.getBLOB_1()
                        ?: getEMPTY_BLOB()
                }
                GetAccountBigAvatar = {
                    CASH_DATAS[(getIDENTIFICATOR_11() + "800")]?.CASH_DATA_RECORDS?.get(getIDENTIFICATOR_7())?.answerTypeValues?.getBLOB_3()
                        ?: getEMPTY_BLOB()
                }

                GetLinkOwner = { getIDENTIFICATOR_11() } //chats id;

                answerType.RECORD_TABLE_ID = GetMessegeId().toString()


                when (GetMessegeObjectType()) {
                    "0" -> { //withot object
                        GetObjectId = { getEMPTY_STRING() }
                    }
                    "1", "2", "3" -> { // MUSIC, VIDEO, PICTURE

                        GetAlbumId = { getIDENTIFICATOR_8() }
                        GetAlbumName = { getSTRING_10() }

                        GetObjectId = { getIDENTIFICATOR_5() }
                        GetObjectSize = { getINTEGER_4() }
                        GetLengthSeconds = { getINTEGER_5() }
                        GetObjectName = { getSTRING_5() }
                        GetObjectServer = { getSTRING_6() }
                        GetObjectLink = { getSTRING_8() }
                        GetObjectExtension = { getSTRING_9() }
                        GetObjectProfile = { getSTRING_7().substring(0, 5) }
                        GetObjectType = { getSTRING_7().substring(5, 6) }
                        GetObjectAccess = { getSTRING_7().substring(6, 7) }
                        GetObjectStatus = { getSTRING_7().substring(7, 8) }
                        GetObjectLikes = { getLONG_5() }
                        GetObjectDisLikes = { getLONG_6() }
                        GetObjectComments = { getLONG_7() }
                        GetObjectListens = { getLONG_8() }
                        GetObjectDescriptors = { getLONG_9() }
                        GetObjectListensPeriod = { getINTEGER_6() }

                        IsHaveObjectInfo = true
                        IsHaveAlbumInfo = true

                    }
                    "4" -> { // VOICE
                        GetObjectId = { getIDENTIFICATOR_5() }
                        GetObjectSize = { getINTEGER_4() }
                        GetLengthSeconds = { getINTEGER_5() }
                        GetObjectServer = { getSTRING_6() }
                        GetObjectLink = { getSTRING_8() }
                        GetObjectExtension = { getSTRING_9() }
                        GetMessegeFileType = { getSTRING_7().substring(0, 1) }

                    }
                    "5" -> { // FILE
                        GetObjectId = { getIDENTIFICATOR_5() }
                        GetObjectSize = { getINTEGER_4() }
                        GetLengthSeconds = { getINTEGER_5() }
                        GetObjectServer = { getSTRING_6() }
                        GetObjectLink = { getSTRING_8() }
                        GetObjectExtension = { getSTRING_9() }
                        GetMessegeFileType = { getSTRING_7().substring(0, 1) }

                    }
                    "6" -> { // GIF
                        GetObjectId = { getIDENTIFICATOR_5() }
                        GetObjectSize = { getINTEGER_4() }
                        GetObjectServer = { getSTRING_6() }
                        GetObjectLink = { getSTRING_8() }
                        GetObjectExtension = { getSTRING_9() }
                        GetMessegeFileType = { getSTRING_7().substring(0, 1) }

                    }
                    "7" -> {  // ALBUMS
                        GetObjectId = { getIDENTIFICATOR_10() }
                        GetAlbumId = { getIDENTIFICATOR_10() }
                        GetAlbumName = { getSTRING_10() }
                        GetAlbumsDescribeStartText = { getSTRING_6() }
                        GetObjectProfile = { getSTRING_7().substring(0, 5) }
                        GetObjectType = { getSTRING_7().substring(5, 6) }
                        GetObjectAccess = { getSTRING_7().substring(6, 7) }
                        GetObjectStatus = { getSTRING_7().substring(7, 8) }
                        GetAlbumsTypeContent = { getSTRING_7().substring(8, 9) }
                        GetObjectComments = { getINTEGER_4().toLong() }
                        GetObjectLikes = { getINTEGER_5().toLong() }
                        GetObjectDisLikes = { getINTEGER_6().toLong() }
                        GetAlbumsCountOfContents = { getINTEGER_7() }
                        GetAlbumsMembers = { getINTEGER_8() }
                        GetAlbumsListeners = { getINTEGER_9() }

                        IsHaveAlbumInfo = true
                    }
                    "8" -> { // avatar
                        GetObjectId = { getEMPTY_STRING() }
                    }
                    "9" -> { // link
                        GetObjectId = { getEMPTY_STRING() }
                    }
                }
            }
            "5" //EXCEPTIONS
            -> {

            }
            "6" //AVATAR
            -> {

            }
            "7" //REG_DATA
            -> {

            }
            "8",  //CHATS_LIKES
            -> {
                GetMainAccountId = { getIDENTIFICATOR_7() }
                GetChatId = { getIDENTIFICATOR_5() }
                GetObjectProfile = { getSTRING_5().substring(0, 5) }
                GetChatsLikesRelations = { getSTRING_5().substring(5, 7) }
                GetAddingDate = { getLONG_4() }
                GetChatsMessegeCount = { getLONG_5() }
                GetChatsLikesLastConnect = { getLONG_6() }
                GetChatsLikesFirstMessegeCount = { getLONG_7() }
                GetChatsLikesLastMessegeCount = { getLONG_8() }
                GetChatsLikesLastDateDilivered = { getLONG_9() }
                GetChatsLikesLastReadedMessegeId = { getLONG_10() }
                GetChatsLikesLastMessegeAdding = { getLONG_11() }
                GetLastUpdatest = { getLONG_12() }
                GetDateDelete = { getLONG_13() }
                GetChatsLikesBalance = { getINTEGER_5() }

                GetLinkOwner = { getIDENTIFICATOR_5() } //chats id;

                answerType.RECORD_TABLE_ID = GetMainAccountId()

            }
            "9" //CHATS_COST_TYPES
            -> {
                GetMainAccountId = { getIDENTIFICATOR_7() }
                GetMainAvatarId = { getIDENTIFICATOR_6() }
                GetChatId = { getIDENTIFICATOR_5() }
                GetChatsCostTypeId = { getINTEGER_5() }
                GetChatsCostTypesStartText = { getSTRING_5() }
                GetObjectType = { getSTRING_7().substring(0, 1) }
                GetObjectAccess = { getSTRING_7().substring(1, 2) }
                GetObjectStatus = { getSTRING_7().substring(2, 3) }
                GetChatsCostTypesHaveFullText = { getSTRING_7().substring(3, 4) }
                GetChatsCostTypesHaveMesseges = { getSTRING_7().substring(4, 5) }

                GetLinkOwner = { getIDENTIFICATOR_5() } //chats id;

                answerType.RECORD_TABLE_ID = GetChatsCostTypeId().toString()

            }
            "A" //ALBUMS_COMMENTS
            -> {
                GetAlbumId = { getIDENTIFICATOR_9() }
                GetLinkOwner = { getIDENTIFICATOR_9() }
                GetObjectId = { getIDENTIFICATOR_9() }
                GetCommentId = { getLONG_5() }
                GetRootCommentId = { getLONG_6() }
                GetAnswerCommentId = { getLONG_7() }
                GetAddingDate = { getLONG_4() }
                GetLinkAddingDate = { getLONG_11() }
                GetCommentsStartText = { getSTRING_5() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                HaveFullText = { getSTRING_7().substring(8, 9) == "1" }
                GetCommentsCountsAnswers = { getINTEGER_4() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }

                answerType.RECORD_TABLE_ID = GetCommentId().toString()

                IsHaveAccountInfo = true
            }
            "B" //ALBUMS_LINKS
            -> {
                GetObjectId = { getIDENTIFICATOR_10() }
                GetAlbumId = { getIDENTIFICATOR_10() }
                GetAlbumName = { getSTRING_10() }
                GetAlbumsDescribeStartText = { getSTRING_6() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                GetAlbumsTypeContent = { getSTRING_7().substring(8, 9) }
                GetAddingDate = { getLONG_4() }
                GetObjectComments = { getINTEGER_4().toLong() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }
                GetAlbumsCountOfContents = { getINTEGER_7() }
                GetAlbumsMembers = { getINTEGER_8() }
                GetAlbumsListeners = { getINTEGER_9() }

                GetLinkOwner = { getIDENTIFICATOR_9() }
                GetLinkAddingDate = { getLONG_11() }
                GetLinkProfile = { getSTRING_11().substring(0, 5) }
                GetLinkType = { getSTRING_11().substring(5, 6) }
                GetLinkAccess = { getSTRING_11().substring(6, 7) }
                GetLinkStatus = { getSTRING_11().substring(7, 8) }
                GetLinkLikes = { getINTEGER_11().toLong() }
                GetLinkDisLikes = { getINTEGER_12().toLong() }
                GetAlbumsLinkCountOfNewContent = { getINTEGER_11() }

                answerType.RECORD_TABLE_ID = GetObjectId()

                IsHaveAlbumInfo = true
                IsHaveAccountInfo = true
            }
            "C" //ALBUMS_LINKS_COMMENTS
            -> {
                GetSecondAccountId = { getIDENTIFICATOR_2() }
                GetObjectId = { getIDENTIFICATOR_10() }
                GetAlbumId = { getIDENTIFICATOR_10() }
                GetLinkOwner = { getIDENTIFICATOR_9() }
                GetCommentId = { getLONG_5() }
                GetRootCommentId = { getLONG_6() }
                GetAnswerCommentId = { getLONG_7() }
                GetAddingDate = { getLONG_4() }
                GetLinkAddingDate = { getLONG_11() }
                GetCommentsStartText = { getSTRING_5() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                HaveFullText = { getSTRING_7().substring(8, 9) == "1"}
                GetCommentsCountsAnswers = { getINTEGER_4() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }

                answerType.RECORD_TABLE_ID = GetCommentId().toString()

                IsHaveAccountInfo = true

            }
            "D" //OBJECTS_LINKS
            -> {
                GetAlbumId = { getIDENTIFICATOR_8() }
                GetAlbumName = { getSTRING_10() }

                GetObjectId = { getIDENTIFICATOR_10() }
                GetObjectSize = { getINTEGER_4() }
                GetLengthSeconds = { getINTEGER_5() }
                GetObjectName = { getSTRING_5() }
                GetObjectServer = { getSTRING_6() }
                GetObjectLink = { getSTRING_8() }
                GetObjectExtension = { getSTRING_9() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                GetObjectLikes = { getLONG_5() }
                GetObjectDisLikes = { getLONG_6() }
                GetObjectComments = { getLONG_7() }
                GetObjectListens = { getLONG_8() }
                GetObjectDescriptors = { getLONG_9() }
                GetObjectListensPeriod = { getINTEGER_6() }

                GetLinkOwner = { getIDENTIFICATOR_9() }
                GetLinkAddingDate = { getLONG_11() }
                GetLinkProfile = { getSTRING_11().substring(0, 5) }
                GetLinkType = { getSTRING_11().substring(5, 6) }
                GetLinkAccess = { getSTRING_11().substring(6, 7) }
                GetLinkStatus = { getSTRING_11().substring(7, 8) }
                GetLinkLikes = { getINTEGER_11().toLong() }
                GetLinkDisLikes = { getINTEGER_12().toLong() }
                GetLinkComments = { getINTEGER_11().toLong() }

                answerType.RECORD_TABLE_ID = GetObjectId()

                IsHaveObjectInfo = true
                IsHaveAlbumInfo = true
                IsHaveAccountInfo = true

            }
            "E" //OBJECTS_LINKS_COMMENTS
            -> {
                GetObjectId = { getIDENTIFICATOR_10() }
                GetLinkOwner = { getIDENTIFICATOR_9() }
                GetCommentId = { getLONG_5() }
                GetRootCommentId = { getLONG_6() }
                GetAnswerCommentId = { getLONG_7() }
                GetAddingDate = { getLONG_4() }
                GetLinkAddingDate = { getLONG_11() }
                GetCommentsStartText = { getSTRING_5() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                HaveFullText = { getSTRING_7().substring(8, 9) == "1"}
                GetCommentsCountsAnswers = { getINTEGER_4() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }

                answerType.RECORD_TABLE_ID = GetCommentId().toString()

                IsHaveAccountInfo = true

            }
            "F" //OBJECTS
            -> {
                GetAlbumId = { getIDENTIFICATOR_8() }
                GetAlbumName = { getSTRING_10() }

                GetObjectId = { getIDENTIFICATOR_5() }
                GetObjectSize = { getINTEGER_4() }
                GetLengthSeconds = { getINTEGER_5() }
                GetObjectName = { getSTRING_5() }
                GetObjectServer = { getSTRING_6() }
                GetObjectLink = { getSTRING_8() }
                GetObjectExtension = { getSTRING_9() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                HaveFullText = { getSTRING_7().substring(10, 11) == "1"}
                GetObjectLikes = { getLONG_5() }
                GetObjectDisLikes = { getLONG_6() }
                GetObjectComments = { getLONG_7() }
                GetObjectListens = { getLONG_8() }
                GetObjectDescriptors = { getLONG_9() }
                GetObjectListensPeriod = { getINTEGER_6() }

                answerType.RECORD_TABLE_ID = GetObjectId()

                IsHaveObjectInfo = true
                IsHaveAlbumInfo = true
            }
            "G" //OBJECTS_COMMENTS
            -> {
                GetSecondAccountId = { getIDENTIFICATOR_2() }
                GetObjectId = { getIDENTIFICATOR_9() }
                GetLinkOwner = { getIDENTIFICATOR_9() }
                GetCommentId = { getLONG_5() }
                GetRootCommentId = { getLONG_6() }
                GetAnswerCommentId = { getLONG_7() }
                GetAddingDate = { getLONG_4() }
                GetLinkAddingDate = { getLONG_11() }
                GetCommentsStartText = { getSTRING_5() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                HaveFullText = { getSTRING_7().substring(8, 9) == "1"}
                GetCommentsCountsAnswers = { getINTEGER_4() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }

                answerType.RECORD_TABLE_ID = GetCommentId().toString()

                IsHaveAccountInfo = true
            }
            "H" //ACCOUNTS
            -> {
                GetObjectId = { GetMainAccountId() }

                IsHaveAccountInfo = true

                answerType.RECORD_TABLE_ID = GetMainAccountId()

            }
            "I" //ALBUMS
            -> {
                GetObjectId = { getIDENTIFICATOR_8() }
                GetAlbumId = { getIDENTIFICATOR_8() }
                GetAlbumName = { getSTRING_10() }
                GetAlbumsDescribeStartText = { getSTRING_6() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                GetAlbumsTypeContent = { getSTRING_7().substring(8, 9) }
                HaveFullText = { getSTRING_7().substring(9, 10) == "1"}
                GetAddingDate = { getLONG_4() }
                GetObjectComments = { getINTEGER_4().toLong() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }
                GetAlbumsCountOfContents = { getINTEGER_7() }
                GetAlbumsMembers = { getINTEGER_8() }
                GetAlbumsListeners = { getINTEGER_9() }

                answerType.RECORD_TABLE_ID = GetObjectId()

                IsHaveAccountInfo = true
                IsHaveAlbumInfo = true

            }
            "J" //ACCOUNT_INFO;
            -> {
                answerType.CASH_SUM = GetMainAccountId()
                answerType.RECORD_TABLE_ID = GetMainAccountId()

                IsHaveAccountInfo = true

            }
            "K" //ALBUM_INFO;
            -> {
                GetObjectId = { getIDENTIFICATOR_8() }
                GetAlbumId = { getIDENTIFICATOR_8() }
                GetAlbumName = { getSTRING_10() }
                GetAlbumsDescribeStartText = { getSTRING_6() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                GetAlbumsTypeContent = { getSTRING_7().substring(8, 9) }
                HaveFullText = { getSTRING_7().substring(9, 10) == "1"}
                GetAddingDate = { getLONG_4() }
                GetObjectComments = { getINTEGER_4().toLong() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }
                GetAlbumsCountOfContents = { getINTEGER_7() }
                GetAlbumsMembers = { getINTEGER_8() }
                GetAlbumsListeners = { getINTEGER_9() }

                answerType.CASH_SUM = GetAlbumId()
                answerType.RECORD_TABLE_ID = GetAlbumId()

                IsHaveAccountInfo = true
            }
            "L" //OBJECT_INFO;
            -> {

                GetAlbumId = { getIDENTIFICATOR_8() }
                GetAlbumName = { getSTRING_10() }

                GetObjectId = { getIDENTIFICATOR_5() }
                GetObjectSize = { getINTEGER_4() }
                GetLengthSeconds = { getINTEGER_5() }
                GetObjectName = { getSTRING_5() }
                GetObjectServer = { getSTRING_6() }
                GetObjectLink = { getSTRING_8() }
                GetObjectExtension = { getSTRING_9() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                HaveFullText = { getSTRING_7().substring(10, 11) == "1"}
                GetObjectLikes = { getLONG_5() }
                GetObjectDisLikes = { getLONG_6() }
                GetObjectComments = { getLONG_7() }
                GetObjectListens = { getLONG_8() }
                GetObjectDescriptors = { getLONG_9() }
                GetObjectListensPeriod = { getINTEGER_6() }

                answerType.CASH_SUM = GetObjectId()
                answerType.RECORD_TABLE_ID = GetObjectId()

                IsHaveAccountInfo = true
                IsHaveAlbumInfo = true

            }
            "N" // NOTICES;
            -> {

            }
            "O" //SAVE OBJECT_INFO;
            -> {
                GetAlbumId = { getIDENTIFICATOR_8() }
                GetAlbumName = { getSTRING_10() }

                GetObjectId = { getIDENTIFICATOR_5() }
                GetObjectSize = { getINTEGER_4() }
                GetLengthSeconds = { getINTEGER_5() }
                GetObjectName = { getSTRING_5() }
                GetObjectServer = { getSTRING_6() }
                GetObjectLink = { getSTRING_8() }
                GetObjectExtension = { getSTRING_9() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                HaveFullText = { getSTRING_7().substring(10, 11) == "1"}
                GetObjectLikes = { getLONG_5() }
                GetObjectDisLikes = { getLONG_6() }
                GetObjectComments = { getLONG_7() }
                GetObjectListens = { getLONG_8() }
                GetObjectDescriptors = { getLONG_9() }
                GetObjectListensPeriod = { getINTEGER_6() }

                answerType.CASH_SUM = GetObjectId()
                answerType.RECORD_TABLE_ID = GetObjectLink()

                IsHaveAccountInfo = true
                IsHaveAlbumInfo = true

            }
            else
            -> {
                throw my_user_exceptions_class(
                    l_class_name = "AnswerTypeValues",
                    l_function_name = "initValues",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Record not defined: ${answerType.RECORD_TYPE}"
                )
            }
        }
    }

    suspend fun getACCOUNT_INFO(l_updatedCashData: (() -> Any?)? = null): Promise<KObjectInfo> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            if (answerType.answerTypeValues.GetMainAccountId().isEmpty()) {
                throw my_user_exceptions_class(
                    l_class_name = "AnswerTypeValues",
                    l_function_name = "getALBUM_INFO",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Account Id is empty"
                )
            }
            var v = OBJECTS_INFO.lockedGet(answerType.answerTypeValues.GetMainAccountId())
            if (v == null) {

                val c = KCashData.GET_CASH_DATA(
                    L_OBJECT_ID = answerType.answerTypeValues.GetMainAccountId(),
                    L_RECORD_TYPE = "J",
                    L_COURSE = "0",
                    l_request_updates = false,
                    l_select_all_records = true,
                    l_is_SetLastBlock = false,
                    l_reset_cash_data = false,
                    l_ignore_timeout = false,
                    l_updatedCashData = l_updatedCashData
                ).await()
                if (c.ORDERED_CASH_DATA.size > 1) {
                    throw my_user_exceptions_class(
                        l_class_name = "AnswerTypeValues",
                        l_function_name = "getACCOUNT_INFO",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "ORDERED_CASH_DATA.size > 1"
                    )
                }
                v = if (c.ORDERED_CASH_DATA.isEmpty()) {
                    KObjectInfo(CreateAccountInfo())
                } else {
                    KObjectInfo(c.ORDERED_CASH_DATA.first())
                }
                v.SetCallBackUpdate(l_updatedCashData)
                OBJECTS_INFO.lockedPut(v.answerType.answerTypeValues.GetMainAccountId(), v)
            }
            v.VerifyUpdates()
            return@async v
        }.toPromise(EmptyCoroutineContext)

    private fun CreateAccountInfo(): ANSWER_TYPE {

        var ans: ANSWER_TYPE = ANSWER_TYPE.GetAnswerType() ?: ANSWER_TYPE()

        when (answerType.RECORD_TYPE) {

            "8" //CHATS_LIKES
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_7 // comment owner id;
                ans.STRING_1 = answerType.STRING_1 // comment owner name;
                ans.STRING_2 = answerType.STRING_2 // comment owner access;
            }

            "A" //ALBUMS_COMMENTS
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // comment owner id;
                ans.STRING_1 = answerType.STRING_1 // comment owner name;
                ans.STRING_2 = answerType.STRING_2 // comment owner access;
            }
            "B" //ALBUMS_LINKS
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // album owner id;
                ans.STRING_1 = answerType.STRING_1 // album owner name;
                ans.STRING_2 = answerType.STRING_2 // album owner access;
            }
            "C" //ALBUMS_LINKS_COMMENTS
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // comment owner id;
                ans.STRING_1 = answerType.STRING_1 // comment owner name;
                ans.STRING_2 = answerType.STRING_2 // comment owner access;
            }
            "D" //OBJECTS_LINKS
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // link owner id;
                ans.STRING_1 = answerType.STRING_1 // link owner name;
                ans.STRING_2 = answerType.STRING_2 // link owner access;
            }
            "E" //OBJECTS_LINKS_COMMENTS
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // comment owner id;
                ans.STRING_1 = answerType.STRING_1 // comment owner name;
                ans.STRING_2 = answerType.STRING_2 // comment owner access;
            }
            "G" //OBJECTS_COMMENTS
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // comment owner id;
                ans.STRING_1 = answerType.STRING_1 // comment owner name;
                ans.STRING_2 = answerType.STRING_2 // comment owner access;
            }
            "H" //ACCOUNTS
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // account id;
                ans.STRING_1 = answerType.STRING_1 // account name;
                ans.STRING_2 = answerType.STRING_2 // account access;
                ans.BLOB_2 = answerType.BLOB_2
                ans.BLOB_3 = answerType.BLOB_3
                ans.BLOB_4 = answerType.BLOB_4
                ans.LONG_20 = answerType.LONG_20
            }
            "I" //ALBUMS
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // album owner id;
                ans.STRING_1 = answerType.STRING_1 // album owner name;
                ans.STRING_2 = answerType.STRING_2 // album owner access;
            }
            "J" //ACCOUNT_INFO;
            -> {
                ans = answerType
            }
            "K" //ALBUM_INFO;
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // album owner id;
                ans.STRING_1 = answerType.STRING_1 // album owner name;
                ans.STRING_2 = answerType.STRING_2 // album owner access;
            }
            "L" //OBJECT_INFO;
            -> {
                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // object owner id;
                ans.STRING_1 = answerType.STRING_1 // object owner name;
                ans.STRING_2 = answerType.STRING_2 // object owner access;
            }
            else
            -> {
                throw my_user_exceptions_class(
                    l_class_name = "AnswerTypeValues",
                    l_function_name = "CreateAccountInfo",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Record dont have account info"
                )
            }
        }
        ans.STRING_20 = "100000000000000000"
        setRECORD_TYPE("J")
        return ans
    }

    suspend fun getALBUM_INFO(l_updatedCashData: (() -> Any?)? = null): Promise<KObjectInfo> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {
            if (answerType.answerTypeValues.GetAlbumId().isEmpty()) {
                throw my_user_exceptions_class(
                    l_class_name = "AnswerTypeValues",
                    l_function_name = "getALBUM_INFO",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Album Id is empty"
                )
            }
            var v = OBJECTS_INFO.lockedGet(answerType.answerTypeValues.GetAlbumId())
            if (v == null) {
                val c = KCashData.GET_CASH_DATA(
                    L_OBJECT_ID = answerType.answerTypeValues.GetAlbumId(),
                    L_RECORD_TYPE = "K",
                    L_COURSE = "0",
                    l_request_updates = false,
                    l_select_all_records = true,
                    l_is_SetLastBlock = false,
                    l_reset_cash_data = false,
                    l_ignore_timeout = false,
                    l_updatedCashData = l_updatedCashData
                ).await()
                if (c.ORDERED_CASH_DATA.size > 1) {
                    throw my_user_exceptions_class(
                        l_class_name = "AnswerTypeValues",
                        l_function_name = "getALBUM_INFO",
                        name_of_exception = "EXC_SYSTEM_ERROR",
                        l_additional_text = "ORDERED_CASH_DATA.size > 1"
                    )
                }
                v = if (c.ORDERED_CASH_DATA.isEmpty()) {
                    KObjectInfo(CreateAlbumInfo())
                } else {
                    KObjectInfo(c.ORDERED_CASH_DATA.first())
                }
                v.SetCallBackUpdate(l_updatedCashData)
                OBJECTS_INFO.lockedPut(v.answerType.answerTypeValues.GetAlbumId(), v)
            }
            v.VerifyUpdates()
            return@async v
        }.toPromise(EmptyCoroutineContext)

    private fun CreateAlbumInfo(): ANSWER_TYPE {

        var ans: ANSWER_TYPE = ANSWER_TYPE.GetAnswerType() ?: ANSWER_TYPE()

        when (answerType.RECORD_TYPE) {

            "4", "M" //MESSEGES
            -> {
                when (GetMessegeObjectType()) {

                    "1", "2", "3" -> { // MUSIC, VIDEO, PICTURE
                        ans.IDENTIFICATOR_8 = GetAlbumId()
                        ans.STRING_10 = GetAlbumName()
                    }
                    "7" -> {  // ALBUMS
                        ans.IDENTIFICATOR_8 = GetAlbumId()
                        ans.STRING_10 = GetAlbumName()
                        ans.STRING_6 = GetAlbumsDescribeStartText()
                        ans.STRING_7 = answerType.STRING_7  // profile
                        ans.INTEGER_4 = GetObjectComments().toInt()
                        ans.INTEGER_5 = GetObjectLikes().toInt()
                        ans.INTEGER_6 = GetObjectDisLikes().toInt()
                        ans.INTEGER_7 = GetAlbumsCountOfContents()
                        ans.INTEGER_8 = GetAlbumsMembers()
                        ans.INTEGER_9 = GetAlbumsListeners()

                        ans.LONG_20 =
                            if (answerType.LONG_20 == 0L) GetAddingDate() else answerType.LONG_20  // last select

                        ans.IDENTIFICATOR_2 = answerType.IDENTIFICATOR_2 // avatar id;
                        ans.STRING_17 = answerType.STRING_17 // avatar server;
                        ans.STRING_18 = answerType.STRING_18 // avatar link;
                        ans.STRING_19 = answerType.STRING_19 // avatar type;
                        ans.INTEGER_17 = answerType.INTEGER_17 // original_avatar_size;
                        ans.BLOB_2 = answerType.BLOB_2
                        ans.BLOB_3 = answerType.BLOB_3
                        ans.BLOB_4 = answerType.BLOB_4
                    }
                    else -> {
                        throw my_user_exceptions_class(
                            l_class_name = "AnswerTypeValues",
                            l_function_name = "CreateAlbumInfo",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "Record dont have album info"
                        )
                    }
                }
            }

            "B" //ALBUMS_LINKS
            -> {
                ans.IDENTIFICATOR_8 = GetAlbumId()
                ans.STRING_10 = GetAlbumName()
                ans.STRING_6 = GetAlbumsDescribeStartText()
                ans.STRING_7 = answerType.STRING_7  // profile
                ans.INTEGER_4 = GetObjectComments().toInt()
                ans.INTEGER_5 = GetObjectLikes().toInt()
                ans.INTEGER_6 = GetObjectDisLikes().toInt()
                ans.INTEGER_7 = GetAlbumsCountOfContents()
                ans.INTEGER_8 = GetAlbumsMembers()
                ans.INTEGER_9 = GetAlbumsListeners()

                ans.LONG_20 = answerType.LONG_20  // last select

                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // album owner id;
                ans.STRING_1 = answerType.STRING_1 // album owner name;
                ans.STRING_2 = answerType.STRING_2 // album owner access;
                ans.BLOB_1 = answerType.BLOB_1 // album owner small avatar;

                ans.IDENTIFICATOR_2 = answerType.IDENTIFICATOR_2 // avatar id;
                ans.STRING_17 = answerType.STRING_17 // avatar server;
                ans.STRING_18 = answerType.STRING_18 // avatar link;
                ans.STRING_19 = answerType.STRING_19 // avatar type;
                ans.INTEGER_17 = answerType.INTEGER_17 // original_avatar_size;
                ans.BLOB_2 = answerType.BLOB_2
                ans.BLOB_3 = answerType.BLOB_3
                ans.BLOB_4 = answerType.BLOB_4
            }

            "D" //OBJECTS_LINKS
            -> {
                ans.IDENTIFICATOR_8 = GetAlbumId()  // root album id;
                ans.STRING_10 = GetAlbumName()
            }

            "F" //OBJECTS
            -> {
                ans.IDENTIFICATOR_8 = GetAlbumId() // root album id;
                ans.STRING_10 = GetAlbumName()
            }

            "I" //ALBUMS
            -> {
                ans.IDENTIFICATOR_8 = GetAlbumId()
                ans.STRING_10 = GetAlbumName()
                ans.STRING_6 = GetAlbumsDescribeStartText()
                ans.STRING_7 = answerType.STRING_7  // profile
                ans.INTEGER_4 = GetObjectComments().toInt()
                ans.INTEGER_5 = GetObjectLikes().toInt()
                ans.INTEGER_6 = GetObjectDisLikes().toInt()
                ans.INTEGER_7 = GetAlbumsCountOfContents()
                ans.INTEGER_8 = GetAlbumsMembers()
                ans.INTEGER_9 = GetAlbumsListeners()

                ans.LONG_20 = answerType.LONG_20  // last select

                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // album owner id;
                ans.STRING_1 = answerType.STRING_1 // album owner name;
                ans.STRING_2 = answerType.STRING_2 // album owner access;
                ans.BLOB_1 = answerType.BLOB_1 // album owner small avatar;

                ans.IDENTIFICATOR_2 = answerType.IDENTIFICATOR_2 // avatar id;
                ans.STRING_17 = answerType.STRING_17 // avatar server;
                ans.STRING_18 = answerType.STRING_18 // avatar link;
                ans.STRING_19 = answerType.STRING_19 // avatar type;
                ans.INTEGER_17 = answerType.INTEGER_17 // original_avatar_size;

                ans.BLOB_2 = answerType.BLOB_2
                ans.BLOB_3 = answerType.BLOB_3
                ans.BLOB_4 = answerType.BLOB_4
            }
            "K" //ALBUM_INFO;
            -> {
                ans = answerType
            }
            "L" //OBJECT_INFO;
            -> {
                ans.IDENTIFICATOR_8 = GetAlbumId()
                ans.STRING_10 = GetAlbumName()
            }

            else
            -> {
                throw my_user_exceptions_class(
                    l_class_name = "AnswerTypeValues",
                    l_function_name = "CreateAlbumInfo",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Record dont have album info"
                )
            }
        }
        ans.STRING_20 = "100000000000000000"
        setRECORD_TYPE("K")
        return ans
    }

    fun getOBJECT_INFO(l_updatedCashData: (() -> Any?)? = null): Promise<KObjectInfo> =
        CoroutineScope(Dispatchers.Default + SupervisorJob()).async {

            if (answerType.answerTypeValues.GetObjectId().isEmpty()) {
                throw my_user_exceptions_class(
                    l_class_name = "AnswerTypeValues",
                    l_function_name = "getOBJECT_INFO",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Object Id is empty"
                )
            }
            var v = OBJECTS_INFO.lockedGet(answerType.answerTypeValues.GetObjectId())
            if (v == null) {
                if (answerType.answerTypeValues.GetMessegeFileType().isEmpty()) {
                    val c = KCashData.GET_CASH_DATA(
                        L_OBJECT_ID = answerType.answerTypeValues.GetObjectId(),
                        L_RECORD_TYPE = "L",
                        L_COURSE = "0",
                        l_request_updates = false,
                        l_select_all_records = true,
                        l_is_SetLastBlock = false,
                        l_reset_cash_data = false,
                        l_ignore_timeout = false,
                        l_updatedCashData = l_updatedCashData
                    ).await()
                    if (c.ORDERED_CASH_DATA.size > 1) {
                        throw my_user_exceptions_class(
                            l_class_name = "AnswerTypeValues",
                            l_function_name = "getOBJECT_INFO",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "ORDERED_CASH_DATA.size > 1"
                        )
                    }
                    v = if (c.ORDERED_CASH_DATA.isEmpty()) {
                        KObjectInfo(CreateObjectInfo())
                    } else {
                        KObjectInfo(c.ORDERED_CASH_DATA.first())
                    }
                } else {
                    v = KObjectInfo(answerType)  // for messeges FILES, AVATARS;
                }
                v.SetCallBackUpdate(l_updatedCashData)
                OBJECTS_INFO.lockedPut(v.answerType.answerTypeValues.GetObjectId(), v)
            }
            v.VerifyUpdates()
            return@async v
        }.toPromise(EmptyCoroutineContext)

    private fun CreateObjectInfo(): ANSWER_TYPE {

        var ans: ANSWER_TYPE = ANSWER_TYPE.GetAnswerType() ?: ANSWER_TYPE()

        when (answerType.RECORD_TYPE) {
            "4", "M" //MESSEGES
            -> {
                when (GetMessegeObjectType()) {

                    "1", "2", "3" -> { // MUSIC,

                        ans.IDENTIFICATOR_8 = GetAlbumId()
                        ans.STRING_10 = GetAlbumName()
                        ans.BLOB_1 = answerType.BLOB_1 // object root album small avatar;

                        ans.IDENTIFICATOR_5 = GetObjectId()
                        ans.INTEGER_4 = GetObjectSize()
                        ans.INTEGER_5 = GetLengthSeconds()
                        ans.STRING_5 = GetObjectName()
                        ans.STRING_6 = GetObjectServer()
                        ans.STRING_8 = GetObjectLink()
                        ans.STRING_9 = GetObjectExtension()
                        ans.STRING_7 = answerType.STRING_7  // object profile;
                        ans.LONG_5 = GetObjectLikes()
                        ans.LONG_6 = GetObjectDisLikes()
                        ans.LONG_7 = GetObjectComments()
                        ans.LONG_8 = GetObjectListens()
                        ans.LONG_9 = GetObjectDescriptors()
                        ans.INTEGER_6 = GetObjectListensPeriod()

                        ans.LONG_20 =
                            if (answerType.LONG_20 == 0L) GetAddingDate() else answerType.LONG_20  // last select

                        ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // object owner id;
                        ans.STRING_1 = answerType.STRING_1 // object owner name;
                        ans.STRING_2 = answerType.STRING_2 // object owner access;

                        ans.IDENTIFICATOR_2 = answerType.IDENTIFICATOR_2 // avatar id;
                        ans.STRING_17 = answerType.STRING_17 // avatar server;
                        ans.STRING_18 = answerType.STRING_18 // avatar link;
                        ans.STRING_19 = answerType.STRING_19 // avatar type;
                        ans.INTEGER_17 = answerType.INTEGER_17 // original_avatar_size;
                        ans.BLOB_2 = answerType.BLOB_2
                        ans.BLOB_3 = answerType.BLOB_3
                        ans.BLOB_4 = answerType.BLOB_4

                    }

                    else -> { // avatar
                        throw my_user_exceptions_class(
                            l_class_name = "AnswerTypeValues",
                            l_function_name = "CreateObjectInfo",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = "Record dont have object info"
                        )
                    }
                }
            }
            "D" //OBJECTS_LINKS
            -> {
                ans.IDENTIFICATOR_8 = GetAlbumId()
                ans.STRING_10 = GetAlbumName()

                ans.IDENTIFICATOR_5 = GetObjectId()
                ans.INTEGER_4 = GetObjectSize()
                ans.INTEGER_5 = GetLengthSeconds()
                ans.STRING_5 = GetObjectName()
                ans.STRING_6 = GetObjectServer()
                ans.STRING_8 = GetObjectLink()
                ans.STRING_9 = GetObjectExtension()
                ans.STRING_7 = answerType.STRING_7  // object profile;
                ans.LONG_5 = GetObjectLikes()
                ans.LONG_6 = GetObjectDisLikes()
                ans.LONG_7 = GetObjectComments()
                ans.LONG_8 = GetObjectListens()
                ans.LONG_9 = GetObjectDescriptors()
                ans.INTEGER_6 = GetObjectListensPeriod()

                ans.LONG_20 = answerType.LONG_20  // last select


                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // object link owner id;
                ans.STRING_1 = answerType.STRING_1 // object link owner name;
                ans.STRING_2 = answerType.STRING_2 // object link owner access;
                ans.BLOB_1 = answerType.BLOB_1 // object link owner small avatar;

                ans.IDENTIFICATOR_2 = answerType.IDENTIFICATOR_2 // avatar id;
                ans.STRING_17 = answerType.STRING_17 // avatar server;
                ans.STRING_18 = answerType.STRING_18 // avatar link;
                ans.STRING_19 = answerType.STRING_19 // avatar type;
                ans.INTEGER_17 = answerType.INTEGER_17 // original_avatar_size;
                ans.BLOB_2 = answerType.BLOB_2
                ans.BLOB_3 = answerType.BLOB_3
                ans.BLOB_4 = answerType.BLOB_4

            }
            "F" //OBJECTS
            -> {
                ans.IDENTIFICATOR_8 = GetAlbumId()
                ans.STRING_10 = GetAlbumName()
                ans.BLOB_1 = answerType.BLOB_1 // object root album small avatar;

                ans.IDENTIFICATOR_5 = GetObjectId()
                ans.INTEGER_4 = GetObjectSize()
                ans.INTEGER_5 = GetLengthSeconds()
                ans.STRING_5 = GetObjectName()
                ans.STRING_6 = GetObjectServer()
                ans.STRING_8 = GetObjectLink()
                ans.STRING_9 = GetObjectExtension()
                ans.STRING_7 = answerType.STRING_7  // object profile;
                ans.LONG_5 = GetObjectLikes()
                ans.LONG_6 = GetObjectDisLikes()
                ans.LONG_7 = GetObjectComments()
                ans.LONG_8 = GetObjectListens()
                ans.LONG_9 = GetObjectDescriptors()
                ans.INTEGER_6 = GetObjectListensPeriod()

                ans.LONG_20 = answerType.LONG_20  // last select

                ans.IDENTIFICATOR_1 = answerType.IDENTIFICATOR_1 // object owner id;
                ans.STRING_1 = answerType.STRING_1 // object owner name;
                ans.STRING_2 = answerType.STRING_2 // object owner access;

                ans.IDENTIFICATOR_2 = answerType.IDENTIFICATOR_2 // avatar id;
                ans.STRING_17 = answerType.STRING_17 // avatar server;
                ans.STRING_18 = answerType.STRING_18 // avatar link;
                ans.STRING_19 = answerType.STRING_19 // avatar type;
                ans.INTEGER_17 = answerType.INTEGER_17 // original_avatar_size;
                ans.BLOB_2 = answerType.BLOB_2
                ans.BLOB_3 = answerType.BLOB_3
                ans.BLOB_4 = answerType.BLOB_4
            }
            "L" //OBJECT_INFO;
            -> {
                ans = answerType
            }
            else
            -> {
                throw my_user_exceptions_class(
                    l_class_name = "AnswerTypeValues",
                    l_function_name = "CreateObjectInfo",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Record dont have object info"
                )
            }
        }
        ans.STRING_20 = "100000000000000000"
        setRECORD_TYPE("L")
        return ans
    }

    @JsName("getIS_UPDATE_BLOB")
    fun getIS_UPDATE_BLOB(): String {

        if (answerType.STRING_20.length < 2) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "getIS_UPDATE_BLOB",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_20"
            )
        }
        return answerType.STRING_20.substring(1, 2)
    }

    @JsName("getIS_UPDATE_SUBSCRIBE")
    fun getIS_UPDATE_SUBSCRIBE(): String {
        if (answerType.STRING_20.length < 3) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "getIS_UPDATE_SUBSCRIBE",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_20"
            )
        }
        return answerType.STRING_20.substring(2, 3)
    }

    @JsName("getIS_DELETE_RECORD")
    fun getIS_DELETE_RECORD(): Boolean {
        if (answerType.STRING_20.length < 7) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "getIS_DELETE_RECORD",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_20"
            )
        }
        return answerType.STRING_20.substring(6, 7) == "1"
    }

    fun setOBJECT_ID_LAST_SELECT(): String {
        var object_id = ""
        val last_select: String = getLONG_20().toString()
        when (answerType.RECORD_TYPE) {
            "1" //COMMANDS
            -> {

            }
            "2" //METADATA
            -> {

            }
            "3" //CHATS
            -> {
                object_id = GetObjectId()
            }
            "4", "M" //MESSEGES
            -> {
                object_id = GetMessegeId().toString()
            }
            "5" //EXCEPTIONS
            -> {

            }
            "6" //AVATAR
            -> {

            }
            "7" //REG_DATA
            -> {

            }
            "8" //CHATS_LIKES
            -> {
                object_id = GetMainAccountId()
            }
            "9" //CHATS_COST_TYPES
            -> {
                object_id = GetChatsCostTypeId().toString()
            }
            "A" //ALBUMS_COMMENTS
            -> {
                object_id = GetCommentId().toString()
            }
            "B" //ALBUMS_LINKS
            -> {
                object_id = GetObjectId()
            }
            "C" //ALBUMS_LINKS_COMMENTS
            -> {
                object_id = GetCommentId().toString()
            }
            "D" //OBJECTS_LINKS
            -> {
                object_id = GetObjectId()
            }
            "E" //OBJECTS_LINKS_COMMENTS
            -> {
                object_id = GetCommentId().toString()
            }
            "F" //OBJECTS
            -> {
                object_id = GetObjectId()
            }
            "G" //OBJECTS_COMMENTS
            -> {
                object_id = GetCommentId().toString()
            }
            "H" //ACCOUNTS
            -> {
                object_id = GetMainAccountId()
            }
            "I" //ALBUMS
            -> {
                object_id = GetObjectId()
            }
            "J" //ACCOUNT_INFO
            -> {
                object_id = GetMainAccountId()
            }
            "K" //ALBUM_INFO
            -> {
                object_id = GetObjectId()
            }
            "L" //OBJECT_INFO
            -> {
                object_id = GetObjectId()
            }
            else
            -> {

            }
        }
        return craeteOBJECT_ID_LAST_SELECT(object_id, last_select)
    }

    private fun craeteOBJECT_ID_LAST_SELECT(object_id: String, last_select: String): String {
        answerType.OBJECT_ID_LAST_SELECT =
            "000000000000000000".substring(0, (18 - object_id.length)) + object_id + "0000000000000".substring(
                0,
                (13 - last_select.length)
            ) + last_select
        return answerType.OBJECT_ID_LAST_SELECT
    }

    /////////////////////////////////////////////////////////////////////////

    @JsName("getIDENTIFICATOR_1")
    private fun getIDENTIFICATOR_1(): String {
        return answerType.IDENTIFICATOR_1 ?: ""
    }

    @JsName("setIDENTIFICATOR_1")
    private fun setIDENTIFICATOR_1(v: String) {
        answerType.IDENTIFICATOR_1 = v
    }

    @JsName("getIDENTIFICATOR_2")
    private fun getIDENTIFICATOR_2(): String {
        return answerType.IDENTIFICATOR_2 ?: ""
    }

    @JsName("setIDENTIFICATOR_2")
    private fun setIDENTIFICATOR_2(v: String) {
        answerType.IDENTIFICATOR_2 = v
    }

    @JsName("getIDENTIFICATOR_3")
    private fun getIDENTIFICATOR_3(): String {
        return answerType.IDENTIFICATOR_3 ?: ""
    }

    @JsName("setIDENTIFICATOR_3")
    private fun setIDENTIFICATOR_3(v: String) {
        answerType.IDENTIFICATOR_3 = v
    }

    @JsName("getIDENTIFICATOR_4")
    private fun getIDENTIFICATOR_4(): String {
        return answerType.IDENTIFICATOR_4 ?: ""
    }

    @JsName("setIDENTIFICATOR_4")
    private fun setIDENTIFICATOR_4(v: String) {
        answerType.IDENTIFICATOR_4 = v
    }

    @JsName("getIDENTIFICATOR_5")
    private fun getIDENTIFICATOR_5(): String {
        return answerType.IDENTIFICATOR_5 ?: ""
    }

    @JsName("setIDENTIFICATOR_5")
    private fun setIDENTIFICATOR_5(v: String) {
        answerType.IDENTIFICATOR_5 = v
    }

    @JsName("getIDENTIFICATOR_6")
    private fun getIDENTIFICATOR_6(): String {
        return answerType.IDENTIFICATOR_6 ?: ""
    }

    @JsName("setIDENTIFICATOR_6")
    private fun setIDENTIFICATOR_6(v: String) {
        answerType.IDENTIFICATOR_6 = v
    }

    @JsName("getIDENTIFICATOR_7")
    private fun getIDENTIFICATOR_7(): String {
        return answerType.IDENTIFICATOR_7 ?: ""
    }

    @JsName("setIDENTIFICATOR_7")
    private fun setIDENTIFICATOR_7(v: String) {
        answerType.IDENTIFICATOR_7 = v
    }

    @JsName("getIDENTIFICATOR_8")
    private fun getIDENTIFICATOR_8(): String {
        return answerType.IDENTIFICATOR_8 ?: ""
    }

    @JsName("setIDENTIFICATOR_8")
    private fun setIDENTIFICATOR_8(v: String) {
        answerType.IDENTIFICATOR_8 = v
    }

    @JsName("getIDENTIFICATOR_9")
    private fun getIDENTIFICATOR_9(): String {
        return answerType.IDENTIFICATOR_9 ?: ""
    }

    @JsName("setIDENTIFICATOR_9")
    private fun setIDENTIFICATOR_9(v: String) {
        answerType.IDENTIFICATOR_9 = v
    }

    @JsName("getIDENTIFICATOR_10")
    private fun getIDENTIFICATOR_10(): String {
        return answerType.IDENTIFICATOR_10 ?: ""
    }

    @JsName("setIDENTIFICATOR_10")
    private fun setIDENTIFICATOR_10(v: String) {
        answerType.IDENTIFICATOR_10 = v
    }

    @JsName("getIDENTIFICATOR_11")
    private fun getIDENTIFICATOR_11(): String {
        return answerType.IDENTIFICATOR_11 ?: ""
    }

    @JsName("setIDENTIFICATOR_11")
    private fun setIDENTIFICATOR_11(v: String) {
        answerType.IDENTIFICATOR_11 = v
    }

    @JsName("getIDENTIFICATOR_12")
    private fun getIDENTIFICATOR_12(): String {
        return answerType.IDENTIFICATOR_12 ?: ""
    }

    @JsName("setIDENTIFICATOR_12")
    private fun setIDENTIFICATOR_12(v: String) {
        answerType.IDENTIFICATOR_12 = v
    }

    @JsName("getIDENTIFICATOR_13")
    private fun getIDENTIFICATOR_13(): String {
        return answerType.IDENTIFICATOR_13 ?: ""
    }

    @JsName("setIDENTIFICATOR_13")
    private fun setIDENTIFICATOR_13(v: String) {
        answerType.IDENTIFICATOR_13 = v
    }

    @JsName("getIDENTIFICATOR_14")
    private fun getIDENTIFICATOR_14(): String {
        return answerType.IDENTIFICATOR_14 ?: ""
    }

    @JsName("setIDENTIFICATOR_14")
    private fun setIDENTIFICATOR_14(v: String) {
        answerType.IDENTIFICATOR_14 = v
    }

    @JsName("getIDENTIFICATOR_15")
    private fun getIDENTIFICATOR_15(): String {
        return answerType.IDENTIFICATOR_15 ?: ""
    }

    @JsName("setIDENTIFICATOR_15")
    private fun setIDENTIFICATOR_15(v: String) {
        answerType.IDENTIFICATOR_15 = v
    }

    @JsName("getIDENTIFICATOR_16")
    private fun getIDENTIFICATOR_16(): String {
        return answerType.IDENTIFICATOR_16 ?: ""
    }

    @JsName("setIDENTIFICATOR_16")
    private fun setIDENTIFICATOR_16(v: String) {
        answerType.IDENTIFICATOR_16 = v
    }

    @JsName("getIDENTIFICATOR_17")
    private fun getIDENTIFICATOR_17(): String {
        return answerType.IDENTIFICATOR_17 ?: ""
    }

    @JsName("setIDENTIFICATOR_17")
    private fun setIDENTIFICATOR_17(v: String) {
        answerType.IDENTIFICATOR_17 = v
    }

    @JsName("getIDENTIFICATOR_18")
    private fun getIDENTIFICATOR_18(): String {
        return answerType.IDENTIFICATOR_18 ?: ""
    }

    @JsName("setIDENTIFICATOR_18")
    private fun setIDENTIFICATOR_18(v: String) {
        answerType.IDENTIFICATOR_18 = v
    }

    @JsName("getIDENTIFICATOR_19")
    private fun getIDENTIFICATOR_19(): String {
        return answerType.IDENTIFICATOR_19 ?: ""
    }

    @JsName("setIDENTIFICATOR_19")
    private fun setIDENTIFICATOR_19(v: String) {
        answerType.IDENTIFICATOR_19 = v
    }

    @JsName("getIDENTIFICATOR_20")
    private fun getIDENTIFICATOR_20(): String {
        return answerType.IDENTIFICATOR_20 ?: ""
    }

    @JsName("setIDENTIFICATOR_20")
    private fun setIDENTIFICATOR_20(v: String) {
        answerType.IDENTIFICATOR_20 = v
    }

    @JsName("getINTEGER_1")
    private fun getINTEGER_1(): Int {
        return answerType.INTEGER_1 ?: 0
    }

    @JsName("setINTEGER_1")
    private fun setINTEGER_1(v: Int?) {
        answerType.INTEGER_1 = v ?: 0
    }

    @JsName("getLONG_1")
    private fun getLONG_1(): Long {
        return answerType.LONG_1 ?: 0
    }

    @JsName("setLONG_1")
    private fun setLONG_1(v: Long?) {
        answerType.LONG_1 = v ?: 0L
    }

    @JsName("getSTRING_1")
    private fun getSTRING_1(): String {
        return answerType.STRING_1 ?: ""
    }

    @JsName("setSTRING_1")
    private fun setSTRING_1(v: String?) {
        answerType.STRING_1 = v ?: ""
    }

    @JsName("getINTEGER_2")
    private fun getINTEGER_2(): Int {
        return answerType.INTEGER_2 ?: 0
    }

    @JsName("setINTEGER_2")
    private fun setINTEGER_2(v: Int?) {
        answerType.INTEGER_2 = v ?: 0
    }

    @JsName("getLONG_2")
    private fun getLONG_2(): Long {
        return answerType.LONG_2 ?: 0
    }

    @JsName("setLONG_2")
    private fun setLONG_2(v: Long?) {
        answerType.LONG_2 = v ?: 0L
    }

    @JsName("getSTRING_2")
    private fun getSTRING_2(): String {
        return answerType.STRING_2 ?: ""
    }

    @JsName("setSTRING_2")
    private fun setSTRING_2(v: String?) {
        answerType.STRING_2 = v ?: ""
    }

    @JsName("getINTEGER_3")
    private fun getINTEGER_3(): Int {
        return answerType.INTEGER_3 ?: 0
    }

    @JsName("setINTEGER_3")
    private fun setINTEGER_3(v: Int?) {
        answerType.INTEGER_3 = v ?: 0
    }

    @JsName("getLONG_3")
    private fun getLONG_3(): Long {
        return answerType.LONG_3 ?: 0
    }

    @JsName("setLONG_3")
    private fun setLONG_3(v: Long?) {
        answerType.LONG_3 = v ?: 0L
    }

    @JsName("getSTRING_3")
    private fun getSTRING_3(): String {
        return answerType.STRING_3 ?: ""
    }

    @JsName("setSTRING_3")
    private fun setSTRING_3(v: String?) {
        answerType.STRING_3 = v ?: ""
    }

    @JsName("getINTEGER_4")
    private fun getINTEGER_4(): Int {
        return answerType.INTEGER_4 ?: 0
    }

    @JsName("setINTEGER_4")
    private fun setINTEGER_4(v: Int?) {
        answerType.INTEGER_4 = v ?: 0
    }

    @JsName("getLONG_4")
    private fun getLONG_4(): Long {
        return answerType.LONG_4 ?: 0
    }

    @JsName("setLONG_4")
    private fun setLONG_4(v: Long?) {
        answerType.LONG_4 = v ?: 0L
    }

    @JsName("getSTRING_4")
    private fun getSTRING_4(): String {
        return answerType.STRING_4 ?: ""
    }

    @JsName("setSTRING_4")
    private fun setSTRING_4(v: String?) {
        answerType.STRING_4 = v ?: ""
    }

    @JsName("getINTEGER_5")
    private fun getINTEGER_5(): Int {
        return answerType.INTEGER_5 ?: 0
    }

    @JsName("setINTEGER_5")
    private fun setINTEGER_5(v: Int?) {
        answerType.INTEGER_5 = v ?: 0
    }

    @JsName("getLONG_5")
    private fun getLONG_5(): Long {
        return answerType.LONG_5 ?: 0
    }

    @JsName("setLONG_5")
    private fun setLONG_5(v: Long?) {
        answerType.LONG_5 = v ?: 0L
    }

    @JsName("getSTRING_5")
    private fun getSTRING_5(): String {
        return answerType.STRING_5 ?: ""
    }

    @JsName("setSTRING_5")
    private fun setSTRING_5(v: String?) {
        answerType.STRING_5 = v ?: ""
    }

    @JsName("getINTEGER_6")
    private fun getINTEGER_6(): Int {
        return answerType.INTEGER_6 ?: 0
    }

    @JsName("setINTEGER_6")
    private fun setINTEGER_6(v: Int?) {
        answerType.INTEGER_6 = v ?: 0
    }

    @JsName("getLONG_6")
    private fun getLONG_6(): Long {
        return answerType.LONG_6 ?: 0
    }

    @JsName("setLONG_6")
    private fun setLONG_6(v: Long?) {
        answerType.LONG_6 = v ?: 0L
    }

    @JsName("getSTRING_6")
    private fun getSTRING_6(): String {
        return answerType.STRING_6 ?: ""
    }

    @JsName("setSTRING_6")
    private fun setSTRING_6(v: String?) {
        answerType.STRING_6 = v ?: ""
    }

    @JsName("getINTEGER_7")
    private fun getINTEGER_7(): Int {
        return answerType.INTEGER_7 ?: 0
    }

    @JsName("setINTEGER_7")
    private fun setINTEGER_7(v: Int?) {
        answerType.INTEGER_7 = v ?: 0
    }

    @JsName("getLONG_7")
    private fun getLONG_7(): Long {
        return answerType.LONG_7 ?: 0
    }

    @JsName("setLONG_7")
    private fun setLONG_7(v: Long?) {
        answerType.LONG_7 = v ?: 0L
    }

    @JsName("getSTRING_7")
    private fun getSTRING_7(): String {
        return answerType.STRING_7 ?: ""
    }

    @JsName("setSTRING_7")
    private fun setSTRING_7(v: String?) {
        answerType.STRING_7 = v ?: ""
    }

    @JsName("getINTEGER_8")
    private fun getINTEGER_8(): Int {
        return answerType.INTEGER_8 ?: 0
    }

    @JsName("setINTEGER_8")
    private fun setINTEGER_8(v: Int?) {
        answerType.INTEGER_8 = v ?: 0
    }

    @JsName("getLONG_8")
    private fun getLONG_8(): Long {
        return answerType.LONG_8 ?: 0
    }

    @JsName("setLONG_8")
    private fun setLONG_8(v: Long?) {
        answerType.LONG_8 = v ?: 0L
    }

    @JsName("getSTRING_8")
    private fun getSTRING_8(): String {
        return answerType.STRING_8 ?: ""
    }

    @JsName("setSTRING_8")
    private fun setSTRING_8(v: String?) {
        answerType.STRING_8 = v ?: ""
    }

    @JsName("getINTEGER_9")
    private fun getINTEGER_9(): Int {
        return answerType.INTEGER_9 ?: 0
    }

    @JsName("setINTEGER_9")
    private fun setINTEGER_9(v: Int?) {
        answerType.INTEGER_9 = v ?: 0
    }

    @JsName("getLONG_9")
    private fun getLONG_9(): Long {
        return answerType.LONG_9 ?: 0
    }

    @JsName("setLONG_9")
    private fun setLONG_9(v: Long?) {
        answerType.LONG_9 = v ?: 0L
    }

    @JsName("getSTRING_9")
    private fun getSTRING_9(): String {
        return answerType.STRING_9 ?: ""
    }

    @JsName("setSTRING_9")
    private fun setSTRING_9(v: String?) {
        answerType.STRING_9 = v ?: ""
    }

    @JsName("getINTEGER_10")
    private fun getINTEGER_10(): Int {
        return answerType.INTEGER_10 ?: 0
    }

    @JsName("setINTEGER_10")
    private fun setINTEGER_10(v: Int?) {
        answerType.INTEGER_10 = v ?: 0
    }

    @JsName("getLONG_10")
    private fun getLONG_10(): Long {
        return answerType.LONG_10 ?: 0
    }

    @JsName("setLONG_10")
    private fun setLONG_10(v: Long?) {
        answerType.LONG_10 = v ?: 0L
    }

    @JsName("getSTRING_10")
    private fun getSTRING_10(): String {
        return answerType.STRING_10 ?: ""
    }

    @JsName("setSTRING_10")
    private fun setSTRING_10(v: String?) {
        answerType.STRING_10 = v ?: ""
    }

    @JsName("getINTEGER_11")
    private fun getINTEGER_11(): Int {
        return answerType.INTEGER_11 ?: 0
    }

    @JsName("setINTEGER_11")
    private fun setINTEGER_11(v: Int?) {
        answerType.INTEGER_11 = v ?: 0
    }

    @JsName("getLONG_11")
    private fun getLONG_11(): Long {
        return answerType.LONG_11 ?: 0
    }

    @JsName("setLONG_11")
    private fun setLONG_11(v: Long?) {
        answerType.LONG_11 = v ?: 0L
    }

    @JsName("getSTRING_11")
    private fun getSTRING_11(): String {
        return answerType.STRING_11 ?: ""
    }

    @JsName("setSTRING_11")
    private fun setSTRING_11(v: String?) {
        answerType.STRING_11 = v ?: ""
    }

    @JsName("getINTEGER_12")
    private fun getINTEGER_12(): Int {
        return answerType.INTEGER_12 ?: 0
    }

    @JsName("setINTEGER_12")
    private fun setINTEGER_12(v: Int?) {
        answerType.INTEGER_12 = v ?: 0
    }

    @JsName("getLONG_12")
    private fun getLONG_12(): Long {
        return answerType.LONG_12 ?: 0
    }

    @JsName("setLONG_12")
    private fun setLONG_12(v: Long?) {
        answerType.LONG_12 = v ?: 0L
    }

    @JsName("getSTRING_12")
    private fun getSTRING_12(): String {
        return answerType.STRING_12 ?: ""
    }

    @JsName("setSTRING_12")
    private fun setSTRING_12(v: String?) {
        answerType.STRING_12 = v ?: ""
    }

    @JsName("getINTEGER_13")
    private fun getINTEGER_13(): Int {
        return answerType.INTEGER_13 ?: 0
    }

    @JsName("setINTEGER_13")
    private fun setINTEGER_13(v: Int?) {
        answerType.INTEGER_13 = v ?: 0
    }

    @JsName("getLONG_13")
    private fun getLONG_13(): Long {
        return answerType.LONG_13 ?: 0
    }

    @JsName("setLONG_13")
    private fun setLONG_13(v: Long?) {
        answerType.LONG_13 = v ?: 0L
    }

    @JsName("getSTRING_13")
    private fun getSTRING_13(): String {
        return answerType.STRING_13 ?: ""
    }

    @JsName("setSTRING_13")
    private fun setSTRING_13(v: String?) {
        answerType.STRING_13 = v ?: ""
    }

    @JsName("getINTEGER_14")
    private fun getINTEGER_14(): Int {
        return answerType.INTEGER_14 ?: 0
    }

    @JsName("setINTEGER_14")
    private fun setINTEGER_14(v: Int?) {
        answerType.INTEGER_14 = v ?: 0
    }

    @JsName("getLONG_14")
    private fun getLONG_14(): Long {
        return answerType.LONG_14 ?: 0
    }

    @JsName("setLONG_14")
    private fun setLONG_14(v: Long?) {
        answerType.LONG_14 = v ?: 0L
    }

    @JsName("getSTRING_14")
    private fun getSTRING_14(): String {
        return answerType.STRING_14 ?: ""
    }

    @JsName("setSTRING_14")
    private fun setSTRING_14(v: String?) {
        answerType.STRING_14 = v ?: ""
    }

    @JsName("getINTEGER_15")
    private fun getINTEGER_15(): Int {
        return answerType.INTEGER_15 ?: 0
    }

    @JsName("setINTEGER_15")
    private fun setINTEGER_15(v: Int?) {
        answerType.INTEGER_15 = v ?: 0
    }

    @JsName("getLONG_15")
    private fun getLONG_15(): Long {
        return answerType.LONG_15 ?: 0
    }

    @JsName("setLONG_15")
    private fun setLONG_15(v: Long?) {
        answerType.LONG_15 = v ?: 0L
    }

    @JsName("getSTRING_15")
    private fun getSTRING_15(): String {
        return answerType.STRING_15 ?: ""
    }

    @JsName("setSTRING_15")
    private fun setSTRING_15(v: String?) {
        answerType.STRING_15 = v ?: ""
    }

    @JsName("getINTEGER_16")
    private fun getINTEGER_16(): Int {
        return answerType.INTEGER_16 ?: 0
    }

    @JsName("setINTEGER_16")
    private fun setINTEGER_16(v: Int?) {
        answerType.INTEGER_16 = v ?: 0
    }

    @JsName("getLONG_16")
    private fun getLONG_16(): Long {
        return answerType.LONG_16 ?: 0
    }

    @JsName("setLONG_16")
    private fun setLONG_16(v: Long?) {
        answerType.LONG_16 = v ?: 0L
    }

    @JsName("getSTRING_16")
    private fun getSTRING_16(): String {
        return answerType.STRING_16 ?: ""
    }

    @JsName("setSTRING_16")
    private fun setSTRING_16(v: String?) {
        answerType.STRING_16 = v ?: ""
    }

    @JsName("getINTEGER_17")
    private fun getINTEGER_17(): Int {
        return answerType.INTEGER_17 ?: 0
    }

    @JsName("setINTEGER_17")
    private fun setINTEGER_17(v: Int?) {
        answerType.INTEGER_17 = v ?: 0
    }

    @JsName("getLONG_17")
    private fun getLONG_17(): Long {
        return answerType.LONG_17 ?: 0
    }

    @JsName("setLONG_17")
    private fun setLONG_17(v: Long?) {
        answerType.LONG_17 = v ?: 0L
    }

    @JsName("getSTRING_17")
    private fun getSTRING_17(): String {
        return answerType.STRING_17 ?: ""
    }

    @JsName("setSTRING_17")
    private fun setSTRING_17(v: String?) {
        answerType.STRING_17 = v ?: ""
    }

    @JsName("getINTEGER_18")
    private fun getINTEGER_18(): Int {
        return answerType.INTEGER_1 ?: 0
    }

    @JsName("setINTEGER_18")
    private fun setINTEGER_18(v: Int?) {
        answerType.INTEGER_18 = v ?: 0
    }

    @JsName("getLONG_18")
    private fun getLONG_18(): Long {
        return answerType.LONG_18 ?: 0
    }

    @JsName("setLONG_18")
    private fun setLONG_18(v: Long?) {
        answerType.LONG_18 = v ?: 0L
    }

    @JsName("getSTRING_18")
    private fun getSTRING_18(): String {
        return answerType.STRING_18 ?: ""
    }

    @JsName("setSTRING_18")
    private fun setSTRING_18(v: String?) {
        answerType.STRING_18 = v ?: ""
    }

    @JsName("getINTEGER_19")
    private fun getINTEGER_19(): Int {
        return answerType.INTEGER_19 ?: 0
    }

    @JsName("setINTEGER_19")
    private fun setINTEGER_19(v: Int?) {
        answerType.INTEGER_19 = v ?: 0
    }

    @JsName("getLONG_19")
    private fun getLONG_19(): Long {
        return answerType.LONG_19 ?: 0
    }

    @JsName("setLONG_19")
    private fun setLONG_19(v: Long?) {
        answerType.LONG_19 = v ?: 0L
    }

    @JsName("getSTRING_19")
    private fun getSTRING_19(): String {
        return answerType.STRING_19 ?: ""
    }

    @JsName("setSTRING_19")
    private fun setSTRING_19(v: String?) {
        answerType.STRING_19 = v ?: ""
    }

    @JsName("getINTEGER_20")
    private fun getINTEGER_20(): Int {
        return answerType.INTEGER_20
    }

    @JsName("setINTEGER_20")
    private fun setINTEGER_20(v: Int?) {
        answerType.INTEGER_20 = v ?: 0
    }

    @JsName("getLONG_20")
    private fun getLONG_20(): Long {
        return answerType.LONG_20
    }

    @JsName("setLONG_20")
    private fun setLONG_20(v: Long?) {
        answerType.LONG_20 = v ?: 0L
    }

    @JsName("getSTRING_20")
    private fun getSTRING_20(): String {
        return answerType.STRING_20
    }

    @JsName("setSTRING_20")
    private fun setSTRING_20(v: String?) {
        answerType.STRING_20 = v ?: ""
    }

    @JsName("getBLOB_1")
    private fun getBLOB_1(): ByteArray? {
        return answerType.BLOB_1
    }

    @JsName("getBLOB_2")
    private fun getBLOB_2(): ByteArray? {
        return answerType.BLOB_2
    }

    @JsName("getBLOB_3")
    private fun getBLOB_3(): ByteArray? {
        return answerType.BLOB_3
    }

    @JsName("getBLOB_4")
    private fun getBLOB_4(): ByteArray? {
        return answerType.BLOB_4
    }

    @JsName("getEMPTY_STRING")
    private fun getEMPTY_STRING(): String {
        return ""
    }

    @JsName("getEMPTY_INTEGER")
    private fun getEMPTY_INTEGER(): Int {
        return 0
    }

    @JsName("getEMPTY_LONG")
    private fun getEMPTY_LONG(): Long {
        return 0L
    }

    @JsName("getEMPTY_BLOB")
    private fun getEMPTY_BLOB(): ByteArray? {
        return null
    }


}