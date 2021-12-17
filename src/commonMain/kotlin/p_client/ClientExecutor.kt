/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_client

import CrossPlatforms.WriteExceptionIntoFile
import Tables.KSaveMedia
import Tables.KSendMedia
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import io.ktor.utils.io.core.ExperimentalIoApi
import io.ktor.utils.io.core.internal.DangerousInternalIoApi
import lib_exceptions.exc_error_on_create_Client_File_Service
import lib_exceptions.exc_universal_exception.returnException
import p_jsocket.Command
import p_jsocket.Commands
import p_jsocket.Connection
import p_jsocket.HASH
import sql.Sqlite_service
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

/**
 *
 * @author User
 */

@ExperimentalStdlibApi
@InternalAPI
@DangerousInternalIoApi
@JsName("ClientExecutor")
class ClientExecutor @ExperimentalIoApi constructor(lJsocket: Jsocket) {
    @ExperimentalIoApi
    @ExperimentalStdlibApi
    private var jsocket: Jsocket = lJsocket
    private val MySend_JSOCKETs: Send_JSOCKETs = Send_JSOCKETs()

    private var connection: Connection? = null
    private var clientFileService: ClientFileService? = null
    private var curCommand: Command? = null


    @OptIn(KtorExperimentalAPI::class)
    @KorioExperimentalApi
    @ExperimentalUnsignedTypes
    @ExperimentalIoApi
    @ExperimentalTime
    suspend fun execute() {
        try {
            curCommand = Commands[jsocket.just_do_it]
            if (curCommand!!.commands_access == "7") {
                self_execute()
                return
            }
            when (jsocket.just_do_it) {
                1011000061 // not execute commands 1011000061 - SELECT_COMMANDS
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
                1011000056
                -> take_a_new_file()
                1011000064
                -> download_file()
                1011000066
                -> selectBigAvatar()
                else -> default_execute()
            }
        } catch (ex: Exception) {
            jsocket.just_do_it_successfull.equals("3", true)
            jsocket.db_massage = ex.toString()
            WriteExceptionIntoFile(ex, "ClientExecutor.run")
        } finally {
            if (!curCommand!!.isDont_answer) {
                jsocket.setAnswer()
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @KtorExperimentalAPI
    @ExperimentalTime
    @ExperimentalIoApi
    private suspend fun default_execute() {
        val j :Jsocket? = if (curCommand!!.isCaching) {
            Sqlite_service.SelectCashData(jsocket)
        } else {
            MySend_JSOCKETs!!.send_JSOCKET_with_TimeOut(jsocket, null, true)
        }
        if(j != null ){
            jsocket = j
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @KorioExperimentalApi
    @ExperimentalTime
    @ExperimentalIoApi
    private suspend fun update_account() {
        try {
            if (jsocket.value_par7.trim().isEmpty()) {
                if (!curCommand!!.isCrypt) {
                    jsocket.just_do_it_successfull.equals("3", true)
                    jsocket.db_massage = returnException(90035, jsocket.lang)
                    return
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
            if (jsocket.just_do_it == 1011000010  // RESTORE_PASSWORD
                || jsocket.just_do_it == 1011000026 // INSERT_ACCOUNT
            ) {
                if (jsocket.value_par9.trim().isNotEmpty()) {
                    if (jsocket.value_par8.trim().isEmpty()) {
                        jsocket.just_do_it_successfull.equals("3", true)
                        jsocket.db_massage = returnException(90036, jsocket.lang)
                        return
                    } else {
                        jsocket.value_par1 = h.getMD5String(jsocket.value_par8.trim() + jsocket.value_par9.trim())
                        jsocket.value_par8 = h.cryptPass(jsocket.value_par8.trim(), jsocket.value_par9.trim(), true)
                    }
                }
            }
            if (jsocket.value_par9.trim().isNotEmpty()) {
                jsocket.value_par9 = h.getMD5String(jsocket.value_par9.trim())
                if (jsocket.just_do_it == 1011000046) {
                    if (jsocket.value_par7.trim().isEmpty()) {
                        jsocket.just_do_it_successfull.equals("3", true)
                        jsocket.db_massage = returnException(90035, jsocket.lang)
                        return
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
                            myConnectionsCoocki.setNewValue(l1)
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
            jsocket = MySend_JSOCKETs!!.send_JSOCKET_with_TimeOut(jsocket, null, true)
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
                        Jsocket.setMyDataBaseID(jsocket.value_id1, false)
                    }
                    if (jsocket.request_profile.trim().isNotEmpty()) {
                        Jsocket.setRequestProfile(jsocket.request_profile, false)
                    }
                    if (jsocket.value_par2.isNotEmpty()) {
                        myAccountProfile.setNewValue(jsocket.value_par2.trim())
                    }
                    Sqlite_service.InsertRegData()
                    try {
                        Sqlite_service.Connect().join()
                    }
                    catch (ex: Exception){}
                    if (jsocket.ANSWER_TYPEs != null && jsocket.ANSWER_TYPEs!!.isNotEmpty()) {
                        Sqlite_service.InitializeCommands(jsocket)
                    }
                }
            }
        } finally {
            newConnectionCoocki.setNewValue(0L)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @ExperimentalUnsignedTypes
    @ExperimentalTime
    @ExperimentalIoApi
    private suspend fun take_a_new_file() {
        try {
            connection?.close()
            clientFileService = ClientFileService(jsocket.value_par9.trim())
            if (clientFileService == null) {
                throw exc_error_on_create_Client_File_Service()
            }
            jsocket.value_par9 = ""
            jsocket.value_par8 = clientFileService!!.MyFileService.fIleName
            jsocket.object_size = clientFileService!!.MyFileService.currentFIleSize
            jsocket.object_extension = clientFileService!!.MyFileService.fileExtension
            /*if (p_jsocket.PictureSet.contains(jsocket.object_extension)) {
                jsocket.setAvatar(clientFileService!!.MyFileService!!.fIleFullName)
            }*/
            if (jsocket.just_do_it_successfull != "0") {
                return
            }
            SetConnection()
            if (connection == null) {
                return
            }
            jsocket = MySend_JSOCKETs!!.send_JSOCKET_with_TimeOut(jsocket, connection, true)

            if (jsocket.just_do_it_successfull != "0" || jsocket.value_id1.trim().isEmpty()
            ) {
                return
            }
            Sqlite_service.InsertSendMedia(
                KSendMedia(
                    jsocket.value_id1.trim(),
                    jsocket.object_extension,
                    clientFileService!!.MyFileService.fIleFullName,
                    0
                )
            )
            if (jsocket.just_do_it_successfull != "0") {
                return
            }
            if (clientFileService!!.MyFileService.send_file_full(jsocket.object_size, connection!!)) {
                jsocket.just_do_it_successfull = "0"
                jsocket.db_massage = ""
            } else {
                jsocket.just_do_it_successfull = "3"
                jsocket.db_massage = returnException(90034, jsocket.lang)
            }
        } finally {
            connection?.close()
            clientFileService?.close()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @ExperimentalUnsignedTypes
    @ExperimentalTime
    @ExperimentalIoApi
    private suspend fun download_file() {
        try {
            connection?.close()
            val s = KSaveMedia(jsocket)
            if (s.IsDownLoaded()) {
                s.setIsTemp(false)
                Sqlite_service.InsertSaveMedia(s)
                return
            }
            clientFileService = ClientFileService(s, 4)
            if (clientFileService == null) {
                throw exc_error_on_create_Client_File_Service()
            }

            if (clientFileService!!.myKSaveMedia!!.IsDownLoaded()) {
                return
            }
            jsocket.value_par1 = clientFileService!!.MyFileService.currentFIleSize.toString()
            SetConnection()
            if (connection == null) {
                return
            }
            jsocket = MySend_JSOCKETs!!.send_JSOCKET_with_TimeOut(jsocket, connection, true)

            if (jsocket.just_do_it_successfull != "0") {
                return
            }
            if (clientFileService!!.MyFileService.write_file_full(connection!!)) {
                jsocket.just_do_it_successfull = "0"
                jsocket.db_massage = ""
            } else {
                jsocket.just_do_it_successfull = "3"
                jsocket.db_massage = returnException(80021, jsocket.lang)
            }
        } finally {
            connection?.close()
            clientFileService?.close()
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    @KtorExperimentalAPI
    @ExperimentalTime
    @ExperimentalIoApi
    private suspend fun selectBigAvatar() {
        if(jsocket.content != null && jsocket.content!!.isNotEmpty() && jsocket.value_id1.isNotEmpty()){
            val myKBigAvatar = Sqlite_service.SelectBigAvatar(jsocket.value_id1)
            if(myKBigAvatar?.getSMALL_AVATAR_SIZE() != jsocket.content!!.size){
                jsocket = MySend_JSOCKETs!!.send_JSOCKET_with_TimeOut(jsocket, null, true)
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////
    private fun SetConnection() {
        connection = Connection(
            SERVER_DNS_NAME, true
        )
    }

    ////////////////////////////////////////////////////////////////////////////////
    @ExperimentalIoApi
    private fun self_execute() {
        when (jsocket.just_do_it) {
            1011000057 -> Sqlite_service.DeleteSaveMedia(jsocket.value_id1)
            else -> {
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @KtorExperimentalAPI
    @ExperimentalTime
    @ExperimentalIoApi
    private suspend fun quit_account() {
        jsocket = MySend_JSOCKETs!!.send_JSOCKET_with_TimeOut(jsocket, null, true)
        if (jsocket.just_do_it_successfull == "0") {
            Sqlite_service.ClearRegData()
            myConnectionsCoocki.setNewValue(0L)
            myConnectionsID.setNewValue(0L)
        }
    } ////////////////////////////////////////////////////////////////////////////////
}