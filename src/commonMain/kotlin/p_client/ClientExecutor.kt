/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("EXPERIMENTAL_API_USAGE")

package p_client

import CrossPlatforms.CrossPlatformFile
import Tables.*
import com.soywiz.korio.async.await
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import lib_exceptions.my_user_exceptions_class
import p_jsocket.*
import sql.Sqlite_service
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

/**
 *
 * @author User
 */


@JsName("ClientExecutor")
@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class ClientExecutor {

    private lateinit var jsocket: Jsocket

    private var curCommand: Command? = null

    suspend fun execute(lJsocket: Jsocket) {
        try {
            jsocket = lJsocket
            curCommand = COMMANDS[jsocket.just_do_it]

            // what dowload

            var file: CrossPlatformFile? = null

            if (jsocket.FileFullPathForSend.isNotEmpty()) {

                file = CrossPlatformFile(jsocket.FileFullPathForSend)

                if (!file.exists() || file.isDirectory()) {
                    throw my_user_exceptions_class(
                        l_class_name = "ClientExecutor",
                        l_function_name = "executer",
                        name_of_exception = "EXC_FILE_IS_NOT_EXISTS"
                    )
                }

                val ext = file.getFileExtension().lowercase()
                val w = (if (MusicSet.contains(ext)) 1   // music
                else if (VideoSet.contains(ext)) 2 // video
                else if (PictureSet.contains(ext)) 3 // picture
                else if (VoiceSet.contains(ext)) 4 // voice
                else if (GifSet.contains(ext)) 6 // gif
                else 5) // file

                if (w.equals(3)) {
                    if (!curCommand!!.whichBlobDataSended.equals("5")) {
                        throw my_user_exceptions_class(
                            l_class_name = "ClientExecutor",
                            l_function_name = "executer",
                            name_of_exception = "EXC_OBJECTS_EXTENSION_NOT_VALID"
                        )
                    }
                } else {
                    if (!curCommand!!.commands_id.equals(1011000093) // LOAD_NEW_OBJECT
                        && !curCommand!!.commands_id.equals(1011000078) // INSERT_MESSEGE
                        && !curCommand!!.commands_id.equals(1011000094) // INSERT_MESSEGE_WITH_OBJECT
                    ) {
                        println("curCommand!!.commands_id = ${curCommand!!.commands_id}")
                        throw my_user_exceptions_class(
                            l_class_name = "ClientExecutor",
                            l_function_name = "executer",
                            name_of_exception = "EXC_WRSOCKETTYPE_COMMAND_NOT_ALLOWED"
                        )
                    }
                }


                if (curCommand!!.commands_id.equals(1011000078)) { // INSERT_MESSEGE
                    if (!w.equals(3)) {
                        jsocket.just_do_it = 1011000094 // INSERT_MESSEGE_WITH_OBJECT
                        curCommand = COMMANDS[jsocket.just_do_it]
                        jsocket.AvatarFullPathForSend = ""  // object in messege not allow have user avatar;
                    }
                }

                if (curCommand!!.commands_id.equals(1011000094)) { // INSERT_MESSEGE_WITH_OBJECT
                    if (w.equals(3)) {
                        jsocket.just_do_it = 1011000078 // INSERT_MESSEGE
                        curCommand = COMMANDS[jsocket.just_do_it]
                    }
                }

                if (w.equals(3)) {
                    jsocket.AvatarFullPathForSend = jsocket.FileFullPathForSend
                    jsocket.FileFullPathForSend = ""
                }

                if (w.equals(4) || w.equals(5) || w.equals(6)) {
                    jsocket.AvatarFullPathForSend = ""
                }
            }

            if (jsocket.AvatarFullPathForSend.isNotEmpty()) {
                if (file == null) {
                    file = CrossPlatformFile(jsocket.AvatarFullPathForSend)
                    if (!file.exists() || file.isDirectory()) {
                        throw my_user_exceptions_class(
                            l_class_name = "ClientExecutor",
                            l_function_name = "execute",
                            name_of_exception = "EXC_FILE_IS_NOT_EXISTS"
                        )
                    }
                }
                jsocket.content = FileService.getImmageAvatarFromFileName(jsocket.AvatarFullPathForSend).await()
                println("jsocket.content = ${jsocket.content?.size}")
                jsocket.AvatarFullPathForSend = ""
            }


            if (curCommand!!.commands_access == "7") {
                self_execute()
                return
            }
            when (jsocket.just_do_it) {

                1011000061 // not execute commands 1011000061 - SELECT_METADATA
                -> {
                }

                1011000010, // RESTORE_PASSWORD
                1011000012, // DELETE_ACCOUNT
                1011000014, // RESTORE_ACCOUNT
                1011000026, // INSERT_ACCOUNT
                1011000027, // CONNECT_ACCOUNT
                1011000046, // UPDATE_ACCOUNT
                1011000072  // UPDATE_ACCOUNTS_MAIL
                -> update_account()

                1011000038
                -> quit_account()

                1011000093, // LOAD_NEW_OBJECT
                1011000094  // INSERT_MESSEGE_WITH_OBJECT
                -> execute_with_send_file()

                else -> default_execute()
            }
        } catch (e: my_user_exceptions_class) {
            throw e
        } catch (ex: Exception) {
            throw my_user_exceptions_class(
                l_class_name = "ClientExecutor",
                l_function_name = "execute",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = ex.stackTraceToString()
            )
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private suspend fun default_execute() {
        jsocket.send_request()
    }

    ////////////////////////////////////////////////////////////////////////////////

    private suspend fun update_account() {
        try {


            jsocket.just_do_it_label = nowNano()

            if (jsocket.value_par7.trim().isEmpty()) {
                if (!curCommand!!.isCrypt) {
                    throw my_user_exceptions_class(
                        l_class_name = "ClientExecutor",
                        l_function_name = "update_account",
                        name_of_exception = "EXC_EMPTY_ACC_MAIL_INSERT"
                    )
                }
            } else {
                jsocket.value_par7 = jsocket.value_par7.trim().uppercase()
            }
            val h = HASH()
            var p = ""
            if (jsocket.value_par8.trim().isNotEmpty()) {
                jsocket.value_par8 = h.getMD5String(jsocket.value_par8.trim())
                p = jsocket.value_par8
            }

            if (jsocket.value_par9.trim().isNotEmpty()) {
                jsocket.value_par9 = jsocket.value_par9.trim().uppercase()
                jsocket.value_par9 = h.getMD5String(jsocket.value_par9)

            }

            if (jsocket.just_do_it == 1011000010  // RESTORE_PASSWORD
                || jsocket.just_do_it == 1011000026 // INSERT_ACCOUNT
            ) {
                if (jsocket.value_par9.trim().isNotEmpty()) {
                    if (jsocket.value_par8.trim().isEmpty()) {
                        throw my_user_exceptions_class(
                            l_class_name = "ClientExecutor",
                            l_function_name = "update_account",
                            name_of_exception = "EXC_EMPTY_ACC_PASS_INSERT"
                        )
                    } else {
                        jsocket.value_par1 = h.getMD5String(jsocket.value_par8.trim() + jsocket.value_par9.trim())
                        jsocket.value_par8 = h.cryptPass(jsocket.value_par8.trim(), jsocket.value_par9.trim(), true)
                    }
                }
            }
            if (jsocket.value_par9.trim().isNotEmpty()) {
                if (jsocket.just_do_it == 1011000046) {
                    if (jsocket.value_par7.trim().isEmpty()) {
                        throw my_user_exceptions_class(
                            l_class_name = "ClientExecutor",
                            l_function_name = "update_account",
                            name_of_exception = "EXC_EMPTY_ACC_MAIL_INSERT"
                        )
                    }
                    newConnectionCoocki.setNewValue(
                        h.getNewTokenLong(jsocket.value_par7, jsocket.value_par9, jsocket.just_do_it_label)
                    )
                }
            }
            if (!curCommand!!.isCrypt) {
                //Sqlite_service.ClearRegData()
                if (jsocket.value_par8.trim().isNotEmpty()) {
                    val l1: Long = h.getNewTokenLong(jsocket.value_par7, p, jsocket.just_do_it_label)
                    if (jsocket.just_do_it != 1011000010 && jsocket.just_do_it != 1011000026) {
                        jsocket.value_par8 = h.getMD5String(l1.toString())
                    }
                    when (jsocket.just_do_it) {
                        1011000010, 1011000026, 1011000027 -> {
                            Constants.myConnectionsCoocki = l1
                        }

                        else -> {
                        }
                    }
                }
                if (jsocket.value_par9.trim().isNotEmpty()) {
                    val l2: Long = h.getNewTokenLong(jsocket.value_par7, jsocket.value_par9, jsocket.just_do_it_label)
                    jsocket.value_par9 = h.getMD5String(l2.toString())
                }
            }

            jsocket.send_request(update_just_do_it_label = false)

            /*
            if (jsocket.connection_id == 0L) {
                if (!curCommand!!.isCrypt) {
                    myConnectionsCoocki.setNewValue(0L)
                }
                return
            }
            if (!curCommand!!.isCrypt
                || jsocket.just_do_it == 1011000046
            ) {
                if (jsocket.connection_id != 0L) {
                    myConnectionsID.setNewValue(jsocket.connection_id)
                    if (jsocket.value_id1.trim().isNotEmpty()) {
                        Jsocket.setMyDataBaseID(jsocket.value_id1)
                    }
                    if (jsocket.request_profile.trim().isNotEmpty()) {
                        myRequestProfile(jsocket.request_profile)
                    }
                    if (jsocket.value_par2.isNotEmpty()) {
                        myAccountProfile.setNewValue(jsocket.value_par2.trim())
                    }
                    Sqlite_service.InsertRegData()
                    try {
                        Sqlite_service.Connect().join()
                    } catch (ex: Exception) {
                    }
                    if (jsocket.ANSWER_TYPEs != null && jsocket.ANSWER_TYPEs!!.isNotEmpty()) {
                        Sqlite_service.InitializeCommands(jsocket)
                    }
                }
            }
             */
        } finally {
            newConnectionCoocki.setNewValue(0L)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    private suspend fun self_execute() {
        when (jsocket.just_do_it) {
            1011000025 -> {// CLEAR_SAVE_MEDIA
                KSaveMedia.ClearSaveMedia()
            }

            1011000007 -> { // SAVE SAVE_MEDIA (DOWNLOAD FILE)
                val f = FileService(jsocket)
                f.open_file_channel()
                f.receive_file().await()
            }

            else -> {
                throw my_user_exceptions_class(
                    l_class_name = "ClientExecutor",
                    l_function_name = "self_execute",
                    name_of_exception = "EXC_WRSOCKETTYPE_NOT_FOUND_COMMAND"
                )
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    private suspend fun execute_with_send_file() {

        if (jsocket.FileFullPathForSend.isNotEmpty()) {
            val f = FileService(jsocket)
            val send = f.open_file_channel().await()
            if (send != null && send.await()) {
                jsocket.value_par4 = f.ServerFileName
                if (jsocket.value_par4.isEmpty()) {
                    throw my_user_exceptions_class(
                        l_class_name = "ClientExecutor",
                        l_function_name = "execute_with_send_file",
                        name_of_exception = "EXC_ERROR_SEND_FILE"
                    )
                }
                jsocket.send_request()
            } else {
                throw my_user_exceptions_class(
                    l_class_name = "ClientExecutor",
                    l_function_name = "update_account",
                    name_of_exception = "EXC_ERROR_SEND_FILE"
                )
            }
        } else {
            jsocket.send_request()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private suspend fun quit_account() {
        if (jsocket.just_do_it_successfull == "0") {
            Sqlite_service.ClearRegData()
            Constants.myConnectionsCoocki = 0L
            Constants.myConnectionsID = 0L
        }
        jsocket.send_request()
    } ////////////////////////////////////////////////////////////////////////////////
}
