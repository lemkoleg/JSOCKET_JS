/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("DuplicatedCode", "unused")

package p_jsocket

import Tables.KCommands
//import kotlin.js.JsName
import kotlin.properties.Delegates

/**
 *
 * @author Oleg
 */

//@JsName("Command")
class Command {
    //@JsName("commands_id")
    var commands_id by Delegates.notNull<Int>()

    //@JsName("commands_access")
    var commands_access by Delegates.notNull<String>()

    //@JsName("commands_profile")
    var commands_profile by Delegates.notNull<String>()

    //@JsName("commands_necessarily_fields")
    var commands_necessarily_fields by Delegates.notNull<String>()

    //@JsName("isForAcceptedMAIL")
    var isForAcceptedMAIL by Delegates.notNull<Boolean>()

    //@JsName("isForPRO")
    var isForPRO by Delegates.notNull<Boolean>()

    //@JsName("canEdit")
    var canEdit by Delegates.notNull<String>()

    //@JsName("whichBlobDataReturned")
    var whichBlobDataReturned by Delegates.notNull<String>()

    //@JsName("whichSmallAvatarCreate")
    var whichSmallAvatarCreate by Delegates.notNull<String>()

    //@JsName("isDont_answer")
    var isDont_answer by Delegates.notNull<Boolean>()

    //@JsName("whichBlobDataSended")
    var whichBlobDataSended by Delegates.notNull<String>()

    //@JsName("canChangeCoocki")
    var canChangeCoocki by Delegates.notNull<Boolean>()

    //@JsName("isCrypt")
    var isCrypt by Delegates.notNull<Boolean>()

    //@JsName("cryptContent")
    var cryptContent by Delegates.notNull<Boolean>()

    //@JsName("isUpdateMesseges")
    var isUpdateMesseges by Delegates.notNull<Boolean>()

    //@JsName("isDontWaitTimeOut")
    var isDontWaitTimeOut by Delegates.notNull<Boolean>()

    //@JsName("isSeparateThread")
    var isSeparateThread by Delegates.notNull<Boolean>()

    //@JsName("isSDontSaveONServerConnections")
    var isSDontSaveONServerConnections by Delegates.notNull<Boolean>()

    //@JsName("saveOnClientRequestsQueue")
    var saveOnClientRequestsQueue by Delegates.notNull<Boolean>()

    //@JsName("allowFileByLink")
    var allowFileByLink by Delegates.notNull<Boolean>()

    //@JsName("cryptMediaContent")
    var cryptMediaContent by Delegates.notNull<Boolean>()

    //@JsName("saveOrirginalAvatar")
    var saveOrirginalAvatar by Delegates.notNull<Boolean>()


    //@JsName("SendBlobSize")
    var SendBlobSize by Delegates.notNull<Int>()

    //@JsName("ReturnBlobSize")
    var ReturnBlobSize by Delegates.notNull<Int>()


    private constructor()

    constructor (v: KCommands) : this() {
        commands_id = v.getCOMMANDS_ID()
        commands_access = v.getCOMMANDS_ACCESS()
        commands_profile = v.getCOMMANDS_PROFILE()
        commands_necessarily_fields = v.getCOMMANDS_NECESSARILY_FIELDS()

        isForAcceptedMAIL = !commands_profile.substring(1, 2).equals("1")
        isForPRO = !commands_profile.substring(2, 3).equals("1")
        canEdit = commands_profile.substring(3, 4)
        whichBlobDataReturned = commands_profile.substring(4, 5)
        whichSmallAvatarCreate = commands_profile.substring(5, 6)
        isDont_answer = commands_profile.substring(6, 7) != "0"
        whichBlobDataSended = commands_profile.substring(9, 10)
        canChangeCoocki = !commands_profile.substring(10, 11).equals("0")
        isCrypt = !commands_profile.substring(11, 12).equals("0")
        cryptContent = commands_profile.substring(12, 13).equals("1")
        isUpdateMesseges = commands_profile.substring(14, 15).equals("1")
        isDontWaitTimeOut = commands_profile.substring(16, 17).equals("1")
        isSeparateThread = commands_profile.substring(17, 18).equals("1")
        isSDontSaveONServerConnections = commands_profile.substring(18, 19).equals("1")
        saveOnClientRequestsQueue = commands_profile.substring(20, 21).equals("1")
        allowFileByLink = commands_profile.substring(21, 22).equals("1")
        cryptMediaContent = commands_profile.substring(22, 23).equals("1")
        saveOrirginalAvatar = commands_profile.substring(23, 24).equals("1")
        InitializeBlobSize()
    }


    constructor(commnd: Command) : this() {
        commands_id = commnd.commands_id
        commands_access = commnd.commands_access
        commands_profile = commnd.commands_profile
        commands_necessarily_fields = commnd.commands_necessarily_fields
        isForAcceptedMAIL = commnd.isForAcceptedMAIL
        isForPRO = commnd.isForPRO
        canEdit = commnd.canEdit
        whichBlobDataReturned = commnd.whichBlobDataReturned
        whichSmallAvatarCreate = commnd.whichSmallAvatarCreate
        isDont_answer = commnd.isDont_answer
        whichBlobDataSended = commnd.whichBlobDataSended
        canChangeCoocki = commnd.canChangeCoocki
        isCrypt = commnd.isCrypt
        cryptContent = commnd.cryptContent
        isUpdateMesseges = commnd.isUpdateMesseges
        isDontWaitTimeOut = commnd.isDontWaitTimeOut
        isSeparateThread = commnd.isSeparateThread
        isSDontSaveONServerConnections = commnd.isSDontSaveONServerConnections
        saveOnClientRequestsQueue = commnd.saveOnClientRequestsQueue
        allowFileByLink = commnd.allowFileByLink
        cryptMediaContent = commnd.cryptMediaContent
        saveOrirginalAvatar = commnd.saveOrirginalAvatar
        InitializeBlobSize()
    }

    constructor(
        p_commands_id: Int,
        p_commands_access: String,
        p_commands_profile: String,
        p_commands_necessarily_fields: String
    ) : super() {
        commands_id = p_commands_id
        commands_access = p_commands_access.trim()
        commands_profile = p_commands_profile.trim()
        commands_necessarily_fields = p_commands_necessarily_fields.trim()

        isForAcceptedMAIL = !commands_profile.substring(1, 2).equals("1")
        isForPRO = !commands_profile.substring(2, 3).equals("1")
        canEdit = commands_profile.substring(3, 4)
        whichBlobDataReturned = commands_profile.substring(4, 5)
        whichSmallAvatarCreate = commands_profile.substring(5, 6)
        isDont_answer = !commands_profile.substring(6, 7).equals("0")
        whichBlobDataSended = commands_profile.substring(9, 10)
        canChangeCoocki = !commands_profile.substring(10, 11).equals("0")
        isCrypt = !commands_profile.substring(11, 12).equals("0")
        cryptContent = commands_profile.substring(12, 13).equals("1")
        isUpdateMesseges = commands_profile.substring(14, 15).equals("1")
        isDontWaitTimeOut = commands_profile.substring(16, 17).equals("1")
        isSeparateThread = commands_profile.substring(17, 18).equals("1")
        isSDontSaveONServerConnections = commands_profile.substring(18, 19).equals("1")
        saveOnClientRequestsQueue = commands_profile.substring(20, 21).equals("1")
        allowFileByLink = commands_profile.substring(21, 22).equals("1")
        cryptMediaContent = commands_profile.substring(22, 23).equals("1")
        saveOrirginalAvatar = commands_profile.substring(23, 24).equals("1")
        InitializeBlobSize()

    }

    private fun InitializeBlobSize() {
        when (whichBlobDataSended) {
            "0" -> {
                this.SendBlobSize = 0
            }
            "4" -> { // ANSWER_TYPE
                this.SendBlobSize = Constants.MAX_REQUEST_SIZE_B
            }
            "5" -> { // AVATAR
                this.SendBlobSize = Constants.SEND_AVATAR_SIZE
            }
            "6" -> {
                this.SendBlobSize = Constants.MAX_REQUEST_SIZE_B
            }// BINARY
        }

        when (whichBlobDataReturned) {
            "0" -> {
                this.ReturnBlobSize = 0
            }
            "4" -> { // ANSWER_TYPE
                this.ReturnBlobSize = Constants.MAX_REQUEST_SIZE_B
            }//Avatar
            "5" -> { // AVATAR
                this.ReturnBlobSize = Constants.AVATARSIZE
            }
            "6" -> {
                this.ReturnBlobSize = Constants.MAX_REQUEST_SIZE_B
            }// BINARY
        }
    }

}
