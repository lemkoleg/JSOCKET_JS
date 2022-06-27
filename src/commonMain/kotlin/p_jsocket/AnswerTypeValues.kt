package p_jsocket

import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import lib_exceptions.my_user_exceptions_class
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class AnswerTypeValues(l_answerType: ANSWER_TYPE) {

    private var answerType: ANSWER_TYPE = l_answerType



    @JsName("setRECORD_TYPE")
    private fun setRECORD_TYPE() {
        if (answerType.STRING_20 == null || answerType.STRING_20!!.length < 8) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "setRECORD_TYPE",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_20"
            )
        }
        answerType.RECORD_TYPE = answerType.STRING_20!!.substring(7, 8)
    }

    //////////////////////// common ////////////////////////////////////

    @JsName("GetRecordOwnerId")
    var GetRecordOwnerId: ()->String = { getEMPTY_STRING() }

    @JsName("GetSecondAccountId")
    var GetSecondAccountId: ()->String = { getEMPTY_STRING() }

    @JsName("GetMainAvatarId")
    var GetMainAvatarId: ()->String = { getIDENTIFICATOR_2() }

    @JsName("GetSecondAvatarId")
    var GetSecondAvatarId: ()->String = { getIDENTIFICATOR_4() }

    @JsName("GetRecordOwnerName")
    var GetRecordOwnerName: ()->String = { getSTRING_1() }

    @JsName("GetSecondAccountName")
    var GetSecondAccountName: ()->String = { getSTRING_4() }

    @JsName("GetAvatarServer")
    var GetAvatarServer: ()->String = { getSTRING_17() }

    @JsName("GetAvatarLink")
    var GetAvatarLink: ()->String = { getSTRING_18() }

    @JsName("GetAvatarType")
    var GetAvatarType: ()->String = { getSTRING_19() }

    @JsName("GetAvatarOriginalSize")
    var GetAvatarOriginalSize: ()->Int = { getINTEGER_19() }

    @JsName("GetObjectId")
    var GetObjectId: ()->String = { getEMPTY_STRING() }

    @JsName("GetSubscribe")
    var GetSubscribe: ()->String = { getSTRING_14() }

    @JsName("GetMainAccountAccess")
    var GetMainAccountAccess: ()->String = { getSTRING_2() }

    @JsName("GetSecondAccountAccess")
    var GetSecondAccountAccess: ()->String = { getSTRING_4() }

    @JsName("GetObjectProfile")
    var GetObjectProfile: ()->String = { getEMPTY_STRING() }

    @JsName("GetObjectType")
    var GetObjectType: ()->String = { getEMPTY_STRING() }

    @JsName("GetObjectAccess")
    var GetObjectAccess: ()->String = { getEMPTY_STRING() }

    @JsName("GetObjectStatus")
    var GetObjectStatus: ()->String = { getEMPTY_STRING() }

    @JsName("GetObjectLikes")
    var GetObjectLikes: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetObjectDisLikes")
    var GetObjectDisLikes: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetObjectComments")
    var GetObjectComments: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetLinkProfile")
    var GetLinkProfile: ()->String = { getEMPTY_STRING() }

    @JsName("GetLinkType")
    var GetLinkType: ()->String = { getEMPTY_STRING() }

    @JsName("GetLinkAccess")
    var GetLinkAccess: ()->String = { getEMPTY_STRING() }

    @JsName("GetLinkStatus")
    var GetLinkStatus: ()->String = { getEMPTY_STRING() }

    @JsName("GetLinkLikes")
    var GetLinkLikes: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetLinkDisLikes")
    var GetLinkDisLikes: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetLinkComments")
    var GetLinkComments: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetAddingDate")
    var GetAddingDate: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetLinkAddingDate")
    var GetLinkAddingDate: ()-> Long = { getEMPTY_LONG() }

    //////////////////// albums /////////////////////////////

    @JsName("GetAlbumId")
    var GetAlbumId: ()->String = { getEMPTY_STRING() }

    @JsName("GetAlbumName")
    var GetAlbumName: ()->String = { getEMPTY_STRING() }

    @JsName("GetAlbumsSubscibeStartText")
    var GetAlbumsDescribeStartText: ()-> String = { getEMPTY_STRING() }

    @JsName("GetAlbumsTypeContent")
    var GetAlbumsTypeContent: ()-> String = { getEMPTY_STRING() }

    @JsName("GetAlbumsCountOfContents")
    var GetAlbumsCountOfContents: ()-> Int = { getEMPTY_INTEGER() }

    @JsName("GetAlbumsMembers")
    var GetAlbumsMembers: ()-> Int = { getEMPTY_INTEGER() }

    @JsName("GetAlbumsListeners")
    var GetAlbumsListeners: ()-> Int = { getEMPTY_INTEGER() }

    /////////////////////// comments ////////////////////////

    @JsName("GetCommentId")
    var GetCommentId: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetRootCommentId")
    var GetRootCommentId: ()-> Long  = { getEMPTY_LONG() }

    @JsName("GetAnswerCommentId")
    var GetAnswerCommentId: ()-> Long  = { getEMPTY_LONG() }

    @JsName("GetCommentsStartText")
    var GetCommentsStartText: ()->String = { getEMPTY_STRING() }

    @JsName("GetCommentsHaveFullText")
    var GetCommentsHaveFullText: ()->String = { getEMPTY_STRING() }

    @JsName("GetCommentsCountsAnswers")
    var GetCommentsCountsAnswers: ()->Int = { getEMPTY_INTEGER() }

    /////////////////////// objects /////////////////////

    @JsName("GetObjectSize")
    var GetObjectSize: ()-> Int = { getEMPTY_INTEGER() }

    @JsName("GetLengthSeconds")
    var GetLengthSeconds: ()-> Int = { getEMPTY_INTEGER() }

    @JsName("GetObjectName")
    var GetObjectName: ()->String = { getEMPTY_STRING() }

    @JsName("GetObjectServer")
    var GetObjectServer: ()->String = { getEMPTY_STRING() }

    @JsName("GetObjectLink")
    var GetObjectLink: ()->String = { getEMPTY_STRING() }

    @JsName("GetObjectExtension")
    var GetObjectExtension: ()->String = { getEMPTY_STRING() }

    /////////////////////// links ///////////////////////////

    @JsName("GetLinkOwner")
    var GetLinkOwner: ()->String = { getEMPTY_STRING() }

    @JsName("GetAlbumsLinkCountOfNewContent")
    var GetAlbumsLinkCountOfNewContent: ()-> Int = { getEMPTY_INTEGER() }

    ///////////////////// messegess //////////////////////////

    @JsName("GetMessegeId")
    var GetMessegeId: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetAnswerMessegeId")
    var GetAnswerMessegeId: ()-> Long = { getEMPTY_LONG() }

    @JsName("GetChatsCostTypeId")
    var GetChatsCostTypeId: ()-> Int = { getEMPTY_INTEGER() }




    fun initValues(){

        setRECORD_TYPE()

        when (answerType.RECORD_TYPE) {
            "1" //COMMANDS
            -> {

                }
            "2" //METADATA
            -> {

            }
            "3" //CHATS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_7() }
                GetMainAvatarId = { getIDENTIFICATOR_6() }
                GetObjectId = { getIDENTIFICATOR_5() }
                GetAddingDate = { getLONG_4() }
            }
            "4" //MESSEGES
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_12() }
                GetSecondAccountId = { getIDENTIFICATOR_13() }
                when(getMESS_OBJECT_TYPE()){
                    "0" -> { //withot object
                        GetObjectId = { getEMPTY_STRING() }
                    }
                    "1" -> { // MUSIC
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
                    }
                    "2" -> { // VIDEO
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
                    }
                    "3" -> { // PICTURE
                        GetObjectId = { getIDENTIFICATOR_5() }
                        GetObjectProfile = { getSTRING_7().substring(0, 5) }
                        GetObjectType = { getSTRING_7().substring(5, 6) }
                        GetObjectAccess = { getSTRING_7().substring(6, 7) }
                        GetObjectStatus = { getSTRING_7().substring(7, 8) }
                        GetObjectLikes = { getLONG_5() }
                        GetObjectDisLikes = { getLONG_6() }
                        GetObjectComments = { getLONG_7() }
                    }
                    "4" -> { // VOICE
                        GetObjectId = { getIDENTIFICATOR_5() }
                        GetObjectSize = { getINTEGER_4() }
                        GetLengthSeconds = { getINTEGER_5() }
                        GetObjectServer = { getSTRING_6() }
                        GetObjectLink = { getSTRING_8() }
                        GetObjectExtension = { getSTRING_9() }
                    }
                    "5" -> { // FILE
                        GetObjectId = { getIDENTIFICATOR_5() }
                        GetObjectSize = { getINTEGER_4() }
                        GetLengthSeconds = { getINTEGER_5() }
                        GetObjectServer = { getSTRING_6() }
                        GetObjectLink = { getSTRING_8() }
                        GetObjectExtension = { getSTRING_9() }
                    }
                    "6" -> { // GIF
                        GetObjectId = { getIDENTIFICATOR_5() }
                    }
                    "7" -> {  // ALBUMS
                        GetObjectId = { getIDENTIFICATOR_10() }
                        GetAlbumId = { getIDENTIFICATOR_10() }
                        GetAlbumName = { getSTRING_10() }
                        GetLinkOwner = { getIDENTIFICATOR_9() }
                        GetAlbumsDescribeStartText = { getSTRING_6() }
                        GetObjectProfile = { getSTRING_7().substring(0, 5) }
                        GetObjectType = { getSTRING_7().substring(5, 6) }
                        GetObjectAccess = { getSTRING_7().substring(6, 7) }
                        GetObjectStatus = { getSTRING_7().substring(7, 8) }
                        GetAlbumsTypeContent = { getSTRING_7().substring(8, 9) }
                        GetAddingDate = { getLONG_4() }
                        GetLinkAddingDate = { getLONG_11() }
                        GetObjectComments = { getINTEGER_4().toLong() }
                        GetObjectLikes = { getINTEGER_5().toLong() }
                        GetObjectDisLikes = { getINTEGER_6().toLong() }
                        GetAlbumsCountOfContents = { getINTEGER_7() }
                        GetAlbumsMembers = { getINTEGER_8() }
                        GetAlbumsListeners = { getINTEGER_9() }
                    }
                    "8" -> { // avatar
                        GetObjectId = { getEMPTY_STRING() }
                    }
                    "9" -> { // link
                        GetObjectId = { getEMPTY_STRING() }
                    }

                }
                GetMessegeId = { getLONG_12()}
                GetChatsCostTypeId = { getINTEGER_12() }
                GetAnswerMessegeId = { getLONG_14() }
                GetAddingDate = { getLONG_11() }
            }
            "5" //EXCEPTIONS
            -> {

            }
            "6" //AVATAR
            -> {

            }
            "7" //REG_DATA
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
            }
            "8" //CHATS_LIKES
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_7() }
                GetObjectId = { getIDENTIFICATOR_5() }
                GetAddingDate = { getLONG_4() }
            }
            "9" //CHATS_COST_TYPES
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_7() }
                GetMainAvatarId = { getIDENTIFICATOR_6() }
                GetChatsCostTypeId = { getINTEGER_5() }

            }
            "A" //ALBUMS_COMMENTS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
                GetSecondAccountId = { getIDENTIFICATOR_13() }
                GetAlbumId = { getIDENTIFICATOR_9() }
                GetObjectId = { getIDENTIFICATOR_9() }
                GetCommentId = { getLONG_5() }
                GetRootCommentId = { getLONG_6() }
                GetAnswerCommentId = { getLONG_7() }
                GetAddingDate = { getLONG_4() }
                GetCommentsStartText = { getSTRING_5() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                GetCommentsHaveFullText = { getSTRING_7().substring(8, 9) }
                GetCommentsCountsAnswers = { getINTEGER_4() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }
            }
            "B" //ALBUMS_LINKS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
                GetObjectId = { getIDENTIFICATOR_10() }
                GetAlbumId = { getIDENTIFICATOR_10() }
                GetAlbumName = { getSTRING_10() }
                GetLinkOwner = { getIDENTIFICATOR_9() }
                GetAlbumsDescribeStartText = { getSTRING_6() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                GetAlbumsTypeContent = { getSTRING_7().substring(8, 9) }
                GetAddingDate = { getLONG_4() }
                GetLinkAddingDate = { getLONG_11() }
                GetObjectComments = { getINTEGER_4().toLong() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }
                GetAlbumsCountOfContents = { getINTEGER_7() }
                GetAlbumsMembers = { getINTEGER_8() }
                GetAlbumsListeners = { getINTEGER_9() }

                GetLinkProfile = { getSTRING_11().substring(0, 5) }
                GetLinkType = { getSTRING_11().substring(5, 6) }
                GetLinkAccess = { getSTRING_11().substring(6, 7) }
                GetLinkStatus = { getSTRING_11().substring(7, 8) }
                GetLinkLikes = { getINTEGER_11().toLong() }
                GetLinkDisLikes = { getINTEGER_12().toLong() }
                GetAlbumsLinkCountOfNewContent = { getINTEGER_11() }
            }
            "C" //ALBUMS_LINKS_COMMENTS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
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
                GetCommentsHaveFullText = { getSTRING_7().substring(8, 9) }
                GetCommentsCountsAnswers = { getINTEGER_4() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }
            }
            "D" //OBJECTS_LINKS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
                GetObjectId = { getIDENTIFICATOR_10() }
                GetLinkOwner = { getIDENTIFICATOR_9() }
                GetObjectSize = { getINTEGER_4() }
                GetLengthSeconds = { getINTEGER_5() }
                GetAddingDate = { getLONG_4() }
                GetLinkAddingDate = { getLONG_11() }

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

                GetLinkProfile = { getSTRING_11().substring(0, 5) }
                GetLinkType = { getSTRING_11().substring(5, 6) }
                GetLinkAccess = { getSTRING_11().substring(6, 7) }
                GetLinkStatus = { getSTRING_11().substring(7, 8) }
                GetLinkLikes = { getINTEGER_11().toLong() }
                GetLinkDisLikes = { getINTEGER_12().toLong() }
                GetLinkComments = { getINTEGER_11().toLong() }
            }
            "E" //OBJECTS_LINKS_COMMENTS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
                GetSecondAccountId = { getIDENTIFICATOR_2() }
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
                GetCommentsHaveFullText = { getSTRING_7().substring(8, 9) }
                GetCommentsCountsAnswers = { getINTEGER_4() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }
            }
            "F" //OBJECTS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
                GetAddingDate = { getLONG_4() }
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

            }
            "G" //OBJECTS_COMMENTS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
                GetSecondAccountId = { getIDENTIFICATOR_2() }
                GetObjectId = {getIDENTIFICATOR_9() }
                GetCommentId = { getLONG_5() }
                GetRootCommentId = { getLONG_6() }
                GetAnswerCommentId = { getLONG_7() }
                GetAddingDate = { getLONG_4() }
                GetCommentsStartText = { getSTRING_5() }
                GetObjectProfile = { getSTRING_7().substring(0, 5) }
                GetObjectType = { getSTRING_7().substring(5, 6) }
                GetObjectAccess = { getSTRING_7().substring(6, 7) }
                GetObjectStatus = { getSTRING_7().substring(7, 8) }
                GetCommentsHaveFullText = { getSTRING_7().substring(8, 9) }
                GetCommentsCountsAnswers = { getINTEGER_4() }
                GetObjectLikes = { getINTEGER_5().toLong() }
                GetObjectDisLikes = { getINTEGER_6().toLong() }
            }
            "H" //ACCOUNTS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
            }
            "I" //ALBUMS
            -> {
                GetRecordOwnerId = { getIDENTIFICATOR_1() }
                GetObjectId = {getIDENTIFICATOR_8() }
                GetAlbumId = { getIDENTIFICATOR_8() }
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

            }
            else
            -> {

            }

        }
    }

    @JsName("getIS_UPDATE_BLOB")
    fun getIS_UPDATE_BLOB(): String {

        if (answerType.STRING_20 == null || answerType.STRING_20!!.length < 2) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "getIS_UPDATE_BLOB",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_20"
            )
        }
        return answerType.STRING_20!!.substring(1, 2)
    }

    @JsName("getIS_UPDATE_SUBSCRIBE")
    fun getIS_UPDATE_SUBSCRIBE(): String {
        if (answerType.STRING_20 == null || answerType.STRING_20!!.length < 3) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "getIS_UPDATE_SUBSCRIBE",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_20"
            )
        }
        return answerType.STRING_20!!.substring(2, 3)
    }

    @JsName("getIS_DELETE_RECORD")
    fun getIS_DELETE_RECORD(): String {
        if (answerType.STRING_20 == null || answerType.STRING_20!!.length < 7) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "getIS_DELETE_RECORD",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_20"
            )
        }
        return answerType.STRING_20!!.substring(6, 7)
    }



    /////////////////////////////////////////////////////////////////////////

    @JsName("getIDENTIFICATOR_1")
    fun getIDENTIFICATOR_1(): String{
        return answerType.IDENTIFICATOR_1?:""
    }

    @JsName("setIDENTIFICATOR_1")
    fun setIDENTIFICATOR_1(v: String){
        answerType.IDENTIFICATOR_1 = v
    }

    @JsName("getIDENTIFICATOR_2")
    fun getIDENTIFICATOR_2(): String{
        return answerType.IDENTIFICATOR_2?:""
    }

    @JsName("setIDENTIFICATOR_2")
    fun setIDENTIFICATOR_2(v: String){
        answerType.IDENTIFICATOR_2 = v
    }

    @JsName("getIDENTIFICATOR_3")
    fun getIDENTIFICATOR_3(): String{
        return answerType.IDENTIFICATOR_3?:""
    }

    @JsName("setIDENTIFICATOR_3")
    fun setIDENTIFICATOR_3(v: String){
        answerType.IDENTIFICATOR_3 = v
    }

    @JsName("getIDENTIFICATOR_4")
    fun getIDENTIFICATOR_4(): String{
        return answerType.IDENTIFICATOR_4?:""
    }

    @JsName("setIDENTIFICATOR_4")
    fun setIDENTIFICATOR_4(v: String){
        answerType.IDENTIFICATOR_4 = v
    }

    @JsName("getIDENTIFICATOR_5")
    fun getIDENTIFICATOR_5(): String{
        return answerType.IDENTIFICATOR_5?:""
    }

    @JsName("setIDENTIFICATOR_5")
    fun setIDENTIFICATOR_5(v: String){
        answerType.IDENTIFICATOR_5 = v
    }

    @JsName("getIDENTIFICATOR_6")
    fun getIDENTIFICATOR_6(): String{
        return answerType.IDENTIFICATOR_6?:""
    }

    @JsName("setIDENTIFICATOR_6")
    fun setIDENTIFICATOR_6(v: String){
        answerType.IDENTIFICATOR_6 = v
    }

    @JsName("getIDENTIFICATOR_7")
    fun getIDENTIFICATOR_7(): String{
        return answerType.IDENTIFICATOR_7?:""
    }

    @JsName("setIDENTIFICATOR_7")
    fun setIDENTIFICATOR_7(v: String){
        answerType.IDENTIFICATOR_7 = v
    }

    @JsName("getIDENTIFICATOR_8")
    fun getIDENTIFICATOR_8(): String{
        return answerType.IDENTIFICATOR_8?:""
    }

    @JsName("setIDENTIFICATOR_8")
    fun setIDENTIFICATOR_8(v: String){
        answerType.IDENTIFICATOR_8 = v
    }

    @JsName("getIDENTIFICATOR_9")
    fun getIDENTIFICATOR_9(): String{
        return answerType.IDENTIFICATOR_9?:""
    }

    @JsName("setIDENTIFICATOR_9")
    fun setIDENTIFICATOR_9(v: String){
        answerType.IDENTIFICATOR_9 = v
    }

    @JsName("getIDENTIFICATOR_10")
    fun getIDENTIFICATOR_10(): String{
        return answerType.IDENTIFICATOR_10?:""
    }

    @JsName("setIDENTIFICATOR_10")
    fun setIDENTIFICATOR_10(v: String){
        answerType.IDENTIFICATOR_10 = v
    }

    @JsName("getIDENTIFICATOR_11")
    fun getIDENTIFICATOR_11(): String{
        return answerType.IDENTIFICATOR_11?:""
    }

    @JsName("setIDENTIFICATOR_11")
    fun setIDENTIFICATOR_11(v: String){
        answerType.IDENTIFICATOR_11 = v
    }

    @JsName("getIDENTIFICATOR_12")
    fun getIDENTIFICATOR_12(): String{
        return answerType.IDENTIFICATOR_12?:""
    }

    @JsName("setIDENTIFICATOR_12")
    fun setIDENTIFICATOR_12(v: String){
        answerType.IDENTIFICATOR_12 = v
    }

    @JsName("getIDENTIFICATOR_13")
    fun getIDENTIFICATOR_13(): String{
        return answerType.IDENTIFICATOR_13?:""
    }

    @JsName("setIDENTIFICATOR_13")
    fun setIDENTIFICATOR_13(v: String){
        answerType.IDENTIFICATOR_13 = v
    }

    @JsName("getIDENTIFICATOR_14")
    fun getIDENTIFICATOR_14(): String{
        return answerType.IDENTIFICATOR_14?:""
    }

    @JsName("setIDENTIFICATOR_14")
    fun setIDENTIFICATOR_14(v: String){
        answerType.IDENTIFICATOR_14 = v
    }

    @JsName("getIDENTIFICATOR_15")
    fun getIDENTIFICATOR_15(): String{
        return answerType.IDENTIFICATOR_15?:""
    }

    @JsName("setIDENTIFICATOR_15")
    fun setIDENTIFICATOR_15(v: String){
        answerType.IDENTIFICATOR_15 = v
    }

    @JsName("getIDENTIFICATOR_16")
    fun getIDENTIFICATOR_16(): String{
        return answerType.IDENTIFICATOR_16?:""
    }

    @JsName("setIDENTIFICATOR_16")
    fun setIDENTIFICATOR_16(v: String){
        answerType.IDENTIFICATOR_16 = v
    }

    @JsName("getIDENTIFICATOR_17")
    fun getIDENTIFICATOR_17(): String{
        return answerType.IDENTIFICATOR_17?:""
    }

    @JsName("setIDENTIFICATOR_17")
    fun setIDENTIFICATOR_17(v: String){
        answerType.IDENTIFICATOR_17 = v
    }

    @JsName("getIDENTIFICATOR_18")
    fun getIDENTIFICATOR_18(): String{
        return answerType.IDENTIFICATOR_18?:""
    }

    @JsName("setIDENTIFICATOR_18")
    fun setIDENTIFICATOR_18(v: String){
        answerType.IDENTIFICATOR_18 = v
    }

    @JsName("getIDENTIFICATOR_19")
    fun getIDENTIFICATOR_19(): String{
        return answerType.IDENTIFICATOR_19?:""
    }

    @JsName("setIDENTIFICATOR_19")
    fun setIDENTIFICATOR_19(v: String){
        answerType.IDENTIFICATOR_19 = v
    }

    @JsName("getIDENTIFICATOR_20")
    fun getIDENTIFICATOR_20(): String{
        return answerType.IDENTIFICATOR_20?:""
    }

    @JsName("setIDENTIFICATOR_20")
    fun setIDENTIFICATOR_20(v: String){
        answerType.IDENTIFICATOR_20 = v
    }

    @JsName("getINTEGER_1")
    fun getINTEGER_1(): Int{
        return answerType.INTEGER_1?:0
    }

    @JsName("setINTEGER_1")
    fun setINTEGER_1(v: Int?){
        answerType.INTEGER_1 = v?:0
    }

    @JsName("getLONG_1")
    fun getLONG_1(): Long{
        return answerType.LONG_1?:0
    }

    @JsName("setLONG_1")
    fun setLONG_1(v: Long?){
        answerType.LONG_1 = v?:0L
    }

    @JsName("getSTRING_1")
    fun getSTRING_1(): String{
        return answerType.STRING_1?:""
    }

    @JsName("setSTRING_1")
    fun setSTRING_1(v: String?){
        answerType.STRING_1 = v?:""
    }

    @JsName("getINTEGER_2")
    fun getINTEGER_2(): Int{
        return answerType.INTEGER_2?:0
    }

    @JsName("setINTEGER_2")
    fun setINTEGER_2(v: Int?){
        answerType.INTEGER_2 = v?:0
    }

    @JsName("getLONG_2")
    fun getLONG_2(): Long{
        return answerType.LONG_2?:0
    }

    @JsName("setLONG_2")
    fun setLONG_2(v: Long?){
        answerType.LONG_2 = v?:0L
    }

    @JsName("getSTRING_2")
    fun getSTRING_2(): String{
        return answerType.STRING_2?:""
    }

    @JsName("setSTRING_2")
    fun setSTRING_2(v: String?){
        answerType.STRING_2 = v?:""
    }

    @JsName("getINTEGER_3")
    fun getINTEGER_3(): Int{
        return answerType.INTEGER_3?:0
    }

    @JsName("setINTEGER_3")
    fun setINTEGER_3(v: Int?){
        answerType.INTEGER_3 = v?:0
    }

    @JsName("getLONG_3")
    fun getLONG_3(): Long{
        return answerType.LONG_3?:0
    }

    @JsName("setLONG_3")
    fun setLONG_3(v: Long?){
        answerType.LONG_3 = v?:0L
    }

    @JsName("getSTRING_3")
    fun getSTRING_3(): String{
        return answerType.STRING_3?:""
    }

    @JsName("setSTRING_3")
    fun setSTRING_3(v: String?){
        answerType.STRING_3 = v?:""
    }

    @JsName("getINTEGER_4")
    fun getINTEGER_4(): Int{
        return answerType.INTEGER_4?:0
    }

    @JsName("setINTEGER_4")
    fun setINTEGER_4(v: Int?){
        answerType.INTEGER_4 = v?:0
    }

    @JsName("getLONG_4")
    fun getLONG_4(): Long{
        return answerType.LONG_4?:0
    }

    @JsName("setLONG_4")
    fun setLONG_4(v: Long?){
        answerType.LONG_4 = v?:0L
    }

    @JsName("getSTRING_4")
    fun getSTRING_4(): String{
        return answerType.STRING_4?:""
    }

    @JsName("setSTRING_4")
    fun setSTRING_4(v: String?){
        answerType.STRING_4 = v?:""
    }

    @JsName("getINTEGER_5")
    fun getINTEGER_5(): Int{
        return answerType.INTEGER_5?:0
    }

    @JsName("setINTEGER_5")
    fun setINTEGER_5(v: Int?){
        answerType.INTEGER_5 = v?:0
    }

    @JsName("getLONG_5")
    fun getLONG_5(): Long{
        return answerType.LONG_5?:0
    }

    @JsName("setLONG_5")
    fun setLONG_5(v: Long?){
        answerType.LONG_5 = v?:0L
    }

    @JsName("getSTRING_5")
    fun getSTRING_5(): String{
        return answerType.STRING_5?:""
    }

    @JsName("setSTRING_5")
    fun setSTRING_5(v: String?){
        answerType.STRING_5 = v?:""
    }

    @JsName("getINTEGER_6")
    fun getINTEGER_6(): Int{
        return answerType.INTEGER_6?:0
    }

    @JsName("setINTEGER_6")
    fun setINTEGER_6(v: Int?){
        answerType.INTEGER_6 = v?:0
    }

    @JsName("getLONG_6")
    fun getLONG_6(): Long{
        return answerType.LONG_6?:0
    }

    @JsName("setLONG_6")
    fun setLONG_6(v: Long?){
        answerType.LONG_6 = v?:0L
    }

    @JsName("getSTRING_6")
    fun getSTRING_6(): String{
        return answerType.STRING_6?:""
    }

    @JsName("setSTRING_6")
    fun setSTRING_6(v: String?){
        answerType.STRING_6 = v?:""
    }

    @JsName("getINTEGER_7")
    fun getINTEGER_7(): Int{
        return answerType.INTEGER_7?:0
    }

    @JsName("setINTEGER_7")
    fun setINTEGER_7(v: Int?){
        answerType.INTEGER_7 = v?:0
    }

    @JsName("getLONG_7")
    fun getLONG_7(): Long{
        return answerType.LONG_7?:0
    }

    @JsName("setLONG_7")
    fun setLONG_7(v: Long?){
        answerType.LONG_7 = v?:0L
    }

    @JsName("getSTRING_7")
    fun getSTRING_7(): String{
        return answerType.STRING_7?:""
    }

    @JsName("setSTRING_7")
    fun setSTRING_7(v: String?){
        answerType.STRING_7 = v?:""
    }

    @JsName("getINTEGER_8")
    fun getINTEGER_8(): Int{
        return answerType.INTEGER_8?:0
    }

    @JsName("setINTEGER_8")
    fun setINTEGER_8(v: Int?){
        answerType.INTEGER_8 = v?:0
    }

    @JsName("getLONG_8")
    fun getLONG_8(): Long{
        return answerType.LONG_8?:0
    }

    @JsName("setLONG_8")
    fun setLONG_8(v: Long?){
        answerType.LONG_8 = v?:0L
    }

    @JsName("getSTRING_8")
    fun getSTRING_8(): String{
        return answerType.STRING_8?:""
    }

    @JsName("setSTRING_8")
    fun setSTRING_8(v: String?){
        answerType.STRING_8 = v?:""
    }

    @JsName("getINTEGER_9")
    fun getINTEGER_9(): Int{
        return answerType.INTEGER_9?:0
    }

    @JsName("setINTEGER_9")
    fun setINTEGER_9(v: Int?){
        answerType.INTEGER_9 = v?:0
    }

    @JsName("getLONG_9")
    fun getLONG_9(): Long{
        return answerType.LONG_9?:0
    }

    @JsName("setLONG_9")
    fun setLONG_9(v: Long?){
        answerType.LONG_9 = v?:0L
    }

    @JsName("getSTRING_9")
    fun getSTRING_9(): String{
        return answerType.STRING_9?:""
    }

    @JsName("setSTRING_9")
    fun setSTRING_9(v: String?){
        answerType.STRING_9 = v?:""
    }

    @JsName("getINTEGER_10")
    fun getINTEGER_10(): Int{
        return answerType.INTEGER_10?:0
    }

    @JsName("setINTEGER_10")
    fun setINTEGER_10(v: Int?){
        answerType.INTEGER_10 = v?:0
    }

    @JsName("getLONG_10")
    fun getLONG_10(): Long{
        return answerType.LONG_10?:0
    }

    @JsName("setLONG_10")
    fun setLONG_10(v: Long?){
        answerType.LONG_10 = v?:0L
    }

    @JsName("getSTRING_10")
    fun getSTRING_10(): String{
        return answerType.STRING_10?:""
    }

    @JsName("setSTRING_10")
    fun setSTRING_10(v: String?){
        answerType.STRING_10 = v?:""
    }

    @JsName("getINTEGER_11")
    fun getINTEGER_11(): Int{
        return answerType.INTEGER_11?:0
    }

    @JsName("setINTEGER_11")
    fun setINTEGER_11(v: Int?){
        answerType.INTEGER_11 = v?:0
    }

    @JsName("getLONG_11")
    fun getLONG_11(): Long{
        return answerType.LONG_11?:0
    }

    @JsName("setLONG_11")
    fun setLONG_11(v: Long?){
        answerType.LONG_11 = v?:0L
    }

    @JsName("getSTRING_11")
    fun getSTRING_11(): String{
        return answerType.STRING_11?:""
    }

    @JsName("setSTRING_11")
    fun setSTRING_11(v: String?){
        answerType.STRING_11 = v?:""
    }

    @JsName("getINTEGER_12")
    fun getINTEGER_12(): Int{
        return answerType.INTEGER_12?:0
    }

    @JsName("setINTEGER_12")
    fun setINTEGER_12(v: Int?){
        answerType.INTEGER_12 = v?:0
    }

    @JsName("getLONG_12")
    fun getLONG_12(): Long{
        return answerType.LONG_12?:0
    }

    @JsName("setLONG_12")
    fun setLONG_12(v: Long?){
        answerType.LONG_12 = v?:0L
    }

    @JsName("getSTRING_12")
    fun getSTRING_12(): String{
        return answerType.STRING_12?:""
    }

    @JsName("setSTRING_12")
    fun setSTRING_12(v: String?){
        answerType.STRING_12 = v?:""
    }

    @JsName("getINTEGER_13")
    fun getINTEGER_13(): Int{
        return answerType.INTEGER_13?:0
    }

    @JsName("setINTEGER_13")
    fun setINTEGER_13(v: Int?){
        answerType.INTEGER_13 = v?:0
    }

    @JsName("getLONG_13")
    fun getLONG_13(): Long{
        return answerType.LONG_13?:0
    }

    @JsName("setLONG_13")
    fun setLONG_13(v: Long?){
        answerType.LONG_13 = v?:0L
    }

    @JsName("getSTRING_13")
    fun getSTRING_13(): String{
        return answerType.STRING_13?:""
    }

    @JsName("setSTRING_13")
    fun setSTRING_13(v: String?){
        answerType.STRING_13 = v?:""
    }

    @JsName("getINTEGER_14")
    fun getINTEGER_14(): Int{
        return answerType.INTEGER_14?:0
    }

    @JsName("setINTEGER_14")
    fun setINTEGER_14(v: Int?){
        answerType.INTEGER_14 = v?:0
    }

    @JsName("getLONG_14")
    fun getLONG_14(): Long{
        return answerType.LONG_14?:0
    }

    @JsName("setLONG_14")
    fun setLONG_14(v: Long?){
        answerType.LONG_14 = v?:0L
    }

    @JsName("getSTRING_14")
    fun getSTRING_14(): String{
        return answerType.STRING_14?:""
    }

    @JsName("setSTRING_14")
    fun setSTRING_14(v: String?){
        answerType.STRING_14 = v?:""
    }

    @JsName("getINTEGER_15")
    fun getINTEGER_15(): Int{
        return answerType.INTEGER_15?:0
    }

    @JsName("setINTEGER_15")
    fun setINTEGER_15(v: Int?){
        answerType.INTEGER_15 = v?:0
    }

    @JsName("getLONG_15")
    fun getLONG_15(): Long{
        return answerType.LONG_15?:0
    }

    @JsName("setLONG_15")
    fun setLONG_15(v: Long?){
        answerType.LONG_15 = v?:0L
    }

    @JsName("getSTRING_15")
    fun getSTRING_15(): String{
        return answerType.STRING_15?:""
    }

    @JsName("setSTRING_15")
    fun setSTRING_15(v: String?){
        answerType.STRING_15 = v?:""
    }

    @JsName("getINTEGER_16")
    fun getINTEGER_16(): Int{
        return answerType.INTEGER_16?:0
    }

    @JsName("setINTEGER_16")
    fun setINTEGER_16(v: Int?){
        answerType.INTEGER_16 = v?:0
    }

    @JsName("getLONG_16")
    fun getLONG_16(): Long{
        return answerType.LONG_16?:0
    }

    @JsName("setLONG_16")
    fun setLONG_16(v: Long?){
        answerType.LONG_16 = v?:0L
    }

    @JsName("getSTRING_16")
    fun getSTRING_16(): String{
        return answerType.STRING_16?:""
    }

    @JsName("setSTRING_16")
    fun setSTRING_16(v: String?){
        answerType.STRING_16 = v?:""
    }

    @JsName("getINTEGER_17")
    fun getINTEGER_17(): Int{
        return answerType.INTEGER_17?:0
    }

    @JsName("setINTEGER_17")
    fun setINTEGER_17(v: Int?){
        answerType.INTEGER_17 = v?:0
    }

    @JsName("getLONG_17")
    fun getLONG_17(): Long{
        return answerType.LONG_17?:0
    }

    @JsName("setLONG_17")
    fun setLONG_17(v: Long?){
        answerType.LONG_17 = v?:0L
    }

    @JsName("getSTRING_17")
    fun getSTRING_17(): String{
        return answerType.STRING_17?:""
    }

    @JsName("setSTRING_17")
    fun setSTRING_17(v: String?){
        answerType.STRING_17 = v?:""
    }

    @JsName("getINTEGER_18")
    fun getINTEGER_18(): Int{
        return answerType.INTEGER_1?:0
    }

    @JsName("setINTEGER_18")
    fun setINTEGER_18(v: Int?){
        answerType.INTEGER_18 = v?:0
    }

    @JsName("getLONG_18")
    fun getLONG_18(): Long{
        return answerType.LONG_18?:0
    }

    @JsName("setLONG_18")
    fun setLONG_18(v: Long?){
        answerType.LONG_18 = v?:0L
    }

    @JsName("getSTRING_18")
    fun getSTRING_18(): String{
        return answerType.STRING_18?:""
    }

    @JsName("setSTRING_18")
    fun setSTRING_18(v: String?){
        answerType.STRING_18 = v?:""
    }

    @JsName("getINTEGER_19")
    fun getINTEGER_19(): Int{
        return answerType.INTEGER_19?:0
    }

    @JsName("setINTEGER_19")
    fun setINTEGER_19(v: Int?){
        answerType.INTEGER_19 = v?:0
    }

    @JsName("getLONG_19")
    fun getLONG_19(): Long{
        return answerType.LONG_19?:0
    }

    @JsName("setLONG_19")
    fun setLONG_19(v: Long?){
        answerType.LONG_19 = v?:0L
    }

    @JsName("getSTRING_19")
    fun getSTRING_19(): String{
        return answerType.STRING_19?:""
    }

    @JsName("setSTRING_19")
    fun setSTRING_19(v: String?){
        answerType.STRING_19 = v?:""
    }

    @JsName("getINTEGER_20")
    fun getINTEGER_20(): Int{
        return answerType.INTEGER_20?:0
    }

    @JsName("setINTEGER_20")
    fun setINTEGER_20(v: Int?){
        answerType.INTEGER_20 = v?:0
    }

    @JsName("getLONG_20")
    fun getLONG_20(): Long{
        return answerType.LONG_20?:0
    }

    @JsName("setLONG_20")
    fun setLONG_20(v: Long?){
        answerType.LONG_20 = v?:0L
    }

    @JsName("getSTRING_20")
    fun getSTRING_20(): String{
        return answerType.STRING_20?:""
    }

    @JsName("setSTRING_20")
    fun setSTRING_20(v: String?){
        answerType.STRING_20 = v?:""
    }

    @JsName("getEMPTY_STRING")
    fun getEMPTY_STRING(): String{
        return ""
    }

    @JsName("getEMPTY_INTEGER")
    fun getEMPTY_INTEGER(): Int{
        return 0
    }

    @JsName("getEMPTY_LONG")
    fun getEMPTY_LONG(): Long{
        return 0L
    }

    fun INIT_STRING20(){
        initValues()
    }

    @JsName("getMESS_OBJECT_TYPE")
    private fun getMESS_OBJECT_TYPE():String {
        if (answerType.STRING_12 == null || answerType.STRING_12!!.length < 6) {
            throw my_user_exceptions_class(
                l_class_name = "AnswerTypeValues",
                l_function_name = "getMESS_OBJECT_TYPE",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Insufficient length of STRING_12"
            )
        }
        return answerType.STRING_12!!.substring(5, 6)
    }


}