/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@file:Suppress("unused")

package Tables

import atomic.AtomicLong
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/**
 *
 * @author User
 */


@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
var CHATS: KCashData?  = null

/*
@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val MESSEGES: MutableMap<String, KCashData>  = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val CHATS_LIKES: MutableMap<String, KCashData>  = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val CHATS_COST_TYPES: MutableMap<String, KCashData>  = mutableMapOf()
 */


@InternalAPI
private val KChatsGlobalLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KChat")
class KChat(val ans: ANSWER_TYPE){

    init {
        ensureNeverFrozen()
    }

    private val KChatsLock = Mutex()

    //val InstanceRef: AtomicReference<KChat> = AtomicReference(this)


    companion object {

        @JsName("globalLastUpdatingDate")
        val globalLastUpdatingDate: AtomicLong = AtomicLong(0L)


        @JsName("maxCountOfMessegesIntoDB")
        val maxCountOfMessegesIntoDB = AtomicLong(100L)


        @JsName("ADD_NEW_CHATS")
        fun ADD_NEW_CHATS(arr: ArrayDeque<ANSWER_TYPE>): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
            withTimeout(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KChatsGlobalLock.lock()
                        while (arr.isNotEmpty()) {
                            TODO()
                        }
                        return@withTimeout true
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KChats",
                            l_function_name = "ADD_NEW_CHATS",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KChatsGlobalLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
                return@withTimeout false
            }
        }.toPromise(EmptyCoroutineContext)
    }

    @JsName("GET_NEXT")
    fun GET_NEXT( l_updatedCashData: ((v: Any?) -> Any?)): Promise<ArrayDeque<ANSWER_TYPE>> =
        CoroutineScope(Dispatchers.Default).async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KChatsGlobalLock.lock()
                        if(CHATS == null){
                            CHATS = KCashData.GET_CASH_DATA(L_OBJECT_ID = Account_Id,
                                                            L_RECORD_TYPE = "3",
                                                            L_COURSE = "0",
                                                            l_request_updates = false,
                                                            l_updatedCashData = l_updatedCashData).await()
                            return@withTimeoutOrNull CHATS!!.currentViewCashData
                        }else{
                            return@withTimeoutOrNull  CHATS!!.GetNext()
                        }

                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KChats",
                            l_function_name = "GET_NEXT",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KChatsGlobalLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            }?: throw my_user_exceptions_class(
                l_class_name = "KChats",
                l_function_name = "GET_NEXT",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )

            throw my_user_exceptions_class(
                l_class_name = "KChats",
                l_function_name = "GET_NEXT",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }.toPromise(EmptyCoroutineContext)
}
