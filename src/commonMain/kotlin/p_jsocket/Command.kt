/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("DuplicatedCode")

package p_jsocket

import Tables.KCommands
import kotlin.js.JsName
import kotlin.properties.Delegates

/**
 *
 * @author Oleg
 */

const val AVATARSIZE = 32760

@JsName("Command")
class Command {
    @JsName("commands_id")
    var commands_id by Delegates.notNull<Int>()

    @JsName("commands_access")
    var commands_access by Delegates.notNull<String>()

    @JsName("commands_profile")
    var commands_profile by Delegates.notNull<String>()

    @JsName("commands_necessarily_fields")
    var commands_necessarily_fields by Delegates.notNull<String>()

    @JsName("SendBlobSize")
    var SendBlobSize by Delegates.notNull<Int>()

    @JsName("ReturnBlobSize")
    var ReturnBlobSize by Delegates.notNull<Int>()

    @JsName("isDeserialized_answer")
    var isDeserialized_answer by Delegates.notNull<Boolean>()

    @JsName("canChangeCoocki")
    var canChangeCoocki by Delegates.notNull<Boolean>()

    @JsName("isDont_answer")
    var isDont_answer by Delegates.notNull<Boolean>()

    @JsName("isCaching")
    var isCaching by Delegates.notNull<Boolean>()

    @JsName("isCrypt")
    var isCrypt by Delegates.notNull<Boolean>()

    @JsName("isForPRO")
    var isForPRO by Delegates.notNull<Boolean>()

    @JsName("isForAcceptedMAIL")
    var isForAcceptedMAIL by Delegates.notNull<Boolean>()

    @JsName("HaveBigAvatar")
    var DontCryptContent by Delegates.notNull<Boolean>()

    @JsName("isUpdateMesseges")
    var isUpdateMesseges by Delegates.notNull<Boolean>()

    @JsName("isDontWaitTimeOut")
    var isDontWaitTimeOut by Delegates.notNull<Boolean>()

    @JsName("isSeparateThread")
    var isSeparateThread by Delegates.notNull<Boolean>()

    @JsName("isSDontSaveONServerConnections")
    var isSDontSaveONServerConnections by Delegates.notNull<Boolean>()

    @JsName("blobIsAvavtar")
    var blobIsAvavtar by Delegates.notNull<Boolean>()

    @JsName("dontCreateSmallAvatar")
    var dontCreateSmallAvatar by Delegates.notNull<Boolean>()

    private constructor()
    constructor (v: KCommands, isServer: Boolean) : this() {
        commands_id = v.getCOMMANDS_ID()
        commands_access = v.getCOMMANDS_ACCESS()
        commands_profile = v.getCOMMANDS_PROFILE()
        commands_necessarily_fields = v.getCOMMANDS_NECESSARILY_FIELDS()
        isDeserialized_answer = commands_profile.substring(9, 10) == "4"
        canChangeCoocki = commands_profile.substring(10, 11) != "0"
        isDont_answer = commands_profile.substring(6, 7) != "0"
        isCaching = commands_profile.substring(7, 8) != "0"
        isCrypt = commands_profile.substring(11, 12) != "0"
        isForPRO = commands_profile.substring(2, 3) != "1"
        isForAcceptedMAIL = commands_profile.substring(1, 2) != "1"
        DontCryptContent = commands_profile.substring(12, 13) == "1"
        isUpdateMesseges = commands_profile.substring(14, 15) == "1"
        isDontWaitTimeOut = commands_profile.substring(16, 17) == "1"
        isSeparateThread = commands_profile.substring(17, 18) == "1"
        isSDontSaveONServerConnections = commands_profile.substring(18, 19) == "1"
        blobIsAvavtar = commands_profile.substring(19, 20) == "1"
        dontCreateSmallAvatar = commands_profile.substring(20, 21) == "1"
        InitializeBlobSize(isServer)
    }


    constructor(commnd: Command) : this() {
        commands_id = commnd.commands_id
        commands_access = commnd.commands_access
        commands_profile = commnd.commands_profile
        commands_necessarily_fields = commnd.commands_necessarily_fields
        SendBlobSize = commnd.SendBlobSize
        ReturnBlobSize = commnd.ReturnBlobSize
        isDeserialized_answer = commnd.isDeserialized_answer
        canChangeCoocki = commnd.canChangeCoocki
        isDont_answer = commnd.isDont_answer
        isCaching = commnd.isCaching
        isCrypt = commnd.isCrypt
        isForPRO = commnd.isForPRO
        isForAcceptedMAIL = commnd.isForAcceptedMAIL
        DontCryptContent = commnd.DontCryptContent
        isUpdateMesseges = commnd.isUpdateMesseges
        isDontWaitTimeOut = commnd.isDontWaitTimeOut
        isSeparateThread = commnd.isSeparateThread
        isSDontSaveONServerConnections = commnd.isSDontSaveONServerConnections
        blobIsAvavtar = commnd.blobIsAvavtar
        dontCreateSmallAvatar = commnd.dontCreateSmallAvatar
    }

    constructor(
        p_commands_id: Int,
        p_commands_access: String,
        p_commands_profile: String,
        p_commands_necessarily_fields: String,
        p_isServer: Boolean
    ) : super() {
        commands_id = p_commands_id
        commands_access = p_commands_access.trim()
        commands_profile = p_commands_profile.trim()
        commands_necessarily_fields = p_commands_necessarily_fields.trim()

        this.isDeserialized_answer = commands_profile.substring(9, 10) == "4"

        canChangeCoocki = commands_profile.substring(10, 11) != "0"

        isDont_answer = commands_profile.substring(6, 7) != "0"

        isCaching = commands_profile.substring(7, 8) != "0"

        isCrypt = commands_profile.substring(11, 12) != "0"

        isForPRO = commands_profile.substring(2, 3) != "1"

        isForAcceptedMAIL = commands_profile.substring(1, 2) != "1"

        DontCryptContent = commands_profile.substring(12, 13) == "1"

        isUpdateMesseges = commands_profile.substring(14, 15) == "1"

        isDontWaitTimeOut = commands_profile.substring(16, 17) == "1"

        isSeparateThread = commands_profile.substring(17, 18) == "1"

        isSDontSaveONServerConnections = commands_profile.substring(18, 19) == "1"

        blobIsAvavtar = commands_profile.substring(19, 20) == "1"

        dontCreateSmallAvatar = commands_profile.substring(20, 21) == "1"

        InitializeBlobSize(p_isServer)

    }

    fun InitializeBlobSize(p_isServer: Boolean){
        val s1 = commands_profile.substring(4, 5)
        val s2 = commands_profile.substring(5, 6)

        if (p_isServer) {
            when (s1) {
                "A" -> {
                    this.SendBlobSize = AVATARSIZE * 1000
                } // max size 32Mb
                "B" -> {
                    this.SendBlobSize = SEND_AVATAR_SIZE
                }//Avatar
                else -> {
                    this.SendBlobSize = s1.toInt() * AVATARSIZE
                }
            }

            when (s2) {
                "A" -> {
                    this.ReturnBlobSize = AVATARSIZE * 1000
                } // max size 32Mb
                "B" -> {
                    this.ReturnBlobSize = SEND_AVATAR_SIZE
                }//Avatar
                else -> {
                    this.ReturnBlobSize = s2.toInt() * AVATARSIZE
                }
            }
        } else {
            when (s2) {
                "A" -> {
                    this.SendBlobSize = AVATARSIZE * 1000
                } // max size 32Mb
                "B" -> {
                    this.SendBlobSize = SEND_AVATAR_SIZE
                }//Avatar
                else -> {
                    this.SendBlobSize = s2.toInt() * AVATARSIZE
                }
            }
            when (s1) {
                "A" -> {
                    this.ReturnBlobSize = AVATARSIZE * 1000
                } // max size 32Mb
                "B" -> {
                    this.ReturnBlobSize = SEND_AVATAR_SIZE
                }//Avatar
                else -> {
                    this.ReturnBlobSize = s1.toInt() * AVATARSIZE
                }
            }
        }
    }

}
