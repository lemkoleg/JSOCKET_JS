@file:Suppress("unused")

package Tables

import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import p_jsocket.JSOCKET
import sql.Sqlite_service
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myConnectionsID")
var myConnectionsID = 0L

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myConnectionsCoocki")
var myConnectionsCoocki = 0L

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myConnectionContext")
val myConnectionContext = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myLang")
var myLang = "ENG"

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myDeviceId")
var myDeviceId = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myRequestProfile")
var myRequestProfile = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("myAccountProfile")
var myAccountProfile = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("isPRO")
var isPRO = false

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("mailConfirm")
var mailConfirm = false

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
private val KRegDataLock = Mutex()


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var Account_Id = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var Account_Name = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var Account_Access = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var Avatar_Id = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var ORIGINAL_AVATAR_SIZE = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var AVATAR_SERVER = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var AVATAR_LINK = ""

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var BALANCE_OF_CHATS = 0

var AVATAR_1: ByteArray? = null
var AVATAR_2: ByteArray? = null
var AVATAR_3: ByteArray? = null


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
val NEW_REG_DATA: ArrayDeque<ANSWER_TYPE> = ArrayDeque()


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KRegData")
class KRegData {

    companion object {

        init {
            ensureNeverFrozen()
        }


        suspend fun setNEW_REG_DATA(v: JSOCKET? = null) {

            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KRegDataLock.withLock {
                            if(v != null){
                                if(v.request_profile.isNotEmpty()
                                    && v.request_profile.length == 30
                                    && !v.request_profile.equals("------------------------------") ){
                                    myRequestProfile = v.request_profile
                                    isPRO = myRequestProfile.substring(0, 1) == "1"
                                    mailConfirm = myRequestProfile.substring(2, 3) == "1"

                                }
                            }
                            Sqlite_service.InsertRegData()
                        }

                    } catch (e: my_user_exceptions_class){
                        throw  e
                    }catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KRegData",
                            l_function_name = "ADD_NEW_REG_DATA",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            }
        }

        @JsName("ADD_NEW_REG_DATA")
        fun ADD_NEW_REG_DATA(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeout(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KRegDataLock.lock()
                            if (NEW_REG_DATA.size > 0) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KRegData",
                                    l_function_name = "ADD_NEW_REG_DATA",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "NEW_REG_DATA.size > 0"
                                )
                            }
                            while (NEW_REG_DATA.isNotEmpty()) {
                                val anwer_type = NEW_REG_DATA.removeFirst()
                                if (anwer_type.RECORD_TYPE.equals("7")) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "KRegData",
                                        l_function_name = "ADD_NEW_REG_DATA",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "Record is not RegData"
                                    )
                                }
                                if (anwer_type.STRING_5 != null && anwer_type.STRING_5!!.isNotEmpty()) {
                                    myAccountProfile = anwer_type.STRING_5!!
                                }

                                if (anwer_type.IDENTIFICATOR_1 != null && anwer_type.IDENTIFICATOR_1!!.isNotEmpty()) {
                                    Account_Id = anwer_type.IDENTIFICATOR_1!!
                                }

                                if (anwer_type.IDENTIFICATOR_2 != null && anwer_type.IDENTIFICATOR_2!!.isNotEmpty()) {
                                    Account_Id = anwer_type.IDENTIFICATOR_2!!
                                }

                                if (anwer_type.STRING_1 != null && anwer_type.STRING_1!!.isNotEmpty()) {
                                    Account_Name = anwer_type.STRING_1!!
                                }

                                if (anwer_type.STRING_2 != null && anwer_type.STRING_2!!.isNotEmpty()) {
                                    Account_Access = anwer_type.STRING_2!!
                                }

                                if (anwer_type.answerTypeValues.GetAvatarOriginalSize() > 0) {
                                    Account_Access = anwer_type.STRING_2!!
                                }

                                BALANCE_OF_CHATS = anwer_type.INTEGER_1 ?: 0

                                if (anwer_type.answerTypeValues.getIS_UPDATE_BLOB() == "1") {
                                    ORIGINAL_AVATAR_SIZE =
                                        anwer_type.answerTypeValues.GetAvatarOriginalSize().toString()
                                    AVATAR_SERVER = anwer_type.answerTypeValues.GetAvatarServer()
                                    AVATAR_LINK = anwer_type.answerTypeValues.GetAvatarLink()
                                    AVATAR_1 = anwer_type.BLOB_1
                                    AVATAR_2 = anwer_type.BLOB_2
                                    AVATAR_3 = anwer_type.BLOB_3
                                }
                            }
                            return@withTimeout true
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KRegData",
                                l_function_name = "ADD_NEW_REG_DATA",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KRegDataLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeout false
                }
            }.toPromise(EmptyCoroutineContext)
    }
}
