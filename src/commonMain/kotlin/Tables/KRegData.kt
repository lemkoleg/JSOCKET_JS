@file:Suppress("unused")

package Tables

import CrossPlatforms.PrintInformation
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
@JsName("KRegData")
class KRegData {

    companion object {

        private val KRegDataLock = Mutex()

        val SelfAnswerType: ANSWER_TYPE = ANSWER_TYPE()


        suspend fun setNEW_REG_DATA(v: JSOCKET? = null) {

            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KRegDataLock.withLock {
                            if(v != null){
                                if(v.request_profile.isNotEmpty()
                                    && v.request_profile.length == 30
                                    && !v.request_profile.equals("------------------------------") ){
                                    Constants.myRequestProfile = v.request_profile
                                    Constants.isPRO = Constants.myRequestProfile.substring(0, 1) == "1"
                                    Constants.mailConfirm = Constants.myRequestProfile.substring(2, 3) == "1"

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
                    }finally {
                        KRegDataLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            }?: throw my_user_exceptions_class(
                l_class_name = "KRegData",
                l_function_name = "setNEW_REG_DATA",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }

        @JsName("ADD_NEW_REG_DATA")
        fun ADD_NEW_REG_DATA(arr: ArrayDeque<ANSWER_TYPE>): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KRegDataLock.lock()
                            
                            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                                PrintInformation.PRINT_INFO("ADD_NEW_REG_DATA is running")
                            }

                            if (arr.size > 1) {
                                throw my_user_exceptions_class(
                                    l_class_name = "KRegData",
                                    l_function_name = "ADD_NEW_REG_DATA",
                                    name_of_exception = "EXC_SYSTEM_ERROR",
                                    l_additional_text = "NEW_REG_DATA.size > 1"
                                )
                            }

                            arr.forEach {
                                if (it.RECORD_TYPE.equals("7")) {
                                    throw my_user_exceptions_class(
                                        l_class_name = "KRegData",
                                        l_function_name = "ADD_NEW_REG_DATA",
                                        name_of_exception = "EXC_SYSTEM_ERROR",
                                        l_additional_text = "Record is not reg_data"
                                    )
                                }
                                SelfAnswerType.merge(it)
                                if (it.STRING_5 != null && it.STRING_5!!.isNotEmpty()) {
                                    Constants.myAccountProfile = it.STRING_5!!
                                }

                                if (it.IDENTIFICATOR_1 != null && it.IDENTIFICATOR_1!!.isNotEmpty()) {
                                    Constants.Account_Id = it.IDENTIFICATOR_1!!
                                }

                                if (it.IDENTIFICATOR_2 != null && it.IDENTIFICATOR_2!!.isNotEmpty()) {
                                    Constants.Avatar_Id = it.IDENTIFICATOR_2!!
                                }

                                if (it.STRING_1 != null && it.STRING_1!!.isNotEmpty()) {
                                    Constants.Account_Name = it.STRING_1!!
                                }

                                if (it.STRING_2 != null && it.STRING_2!!.isNotEmpty()) {
                                    Constants.Account_Access = it.STRING_2!!
                                }

                                if (it.LONG_1 != null && it.LONG_1!! > Constants.LAST_UPDATE) {
                                    Constants.LAST_UPDATE = it.LONG_1!!
                                    meta_data_last_update.setGreaterValue(Constants.LAST_UPDATE)
                                }

                                Constants.BALANCE_OF_CHATS = it.INTEGER_1 ?: 0

                                if (it.answerTypeValues.getIS_UPDATE_BLOB() == "1") {
                                    Constants.ORIGINAL_AVATAR_SIZE =
                                        it.answerTypeValues.GetAvatarOriginalSize().toString()
                                    Constants.AVATAR_SERVER = it.answerTypeValues.GetAvatarServer()
                                    Constants.AVATAR_LINK = it.answerTypeValues.GetAvatarLink()
                                    Constants.AVATAR_1 = it.BLOB_1
                                    Constants.AVATAR_2 = it.BLOB_2
                                    Constants.AVATAR_3 = it.BLOB_3
                                }

                            }
                            return@withTimeoutOrNull true
                        } catch (e: my_user_exceptions_class){
                            throw e
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
                    return@withTimeoutOrNull false
                }?: throw my_user_exceptions_class(
                    l_class_name = "KRegData",
                    l_function_name = "ADD_NEW_REG_DATA",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }.toPromise(EmptyCoroutineContext)


        @JsName("RE_LOAD_REG_DATA")
        fun RE_LOAD_REG_DATA(): Job {
            return Sqlite_service.LoadRegData()
        }
    }
}
