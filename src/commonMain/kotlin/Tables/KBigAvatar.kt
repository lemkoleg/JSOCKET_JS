@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")

/*
 * To change answerType license header, choose License Headers in Project Properties.
 * To change answerType template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables

import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import lib_exceptions.my_user_exceptions_class
import p_client.CLIENT_JSOCKET_POOL
import p_client.Jsocket
import p_jsocket.ANSWER_TYPE
import p_jsocket.Constants
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
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
private val BIG_AVATARS: MutableMap<String, KBigAvatar> = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
private val BIG_AVATARS_IDS: MutableMap<String, String> = mutableMapOf()

@InternalAPI
private val KBigAvatarLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val NEW_BIG_AVATARS: ArrayDeque<ANSWER_TYPE> = ArrayDeque()



@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("BigAvatar")
class KBigAvatar {

    init {
        ensureNeverFrozen()
    }

    //val InstanceRef: AtomicReference<KBigAvatar> = AtomicReference(this)

    private var AVATAR_ID: String = ""
    private var LAST_USE: Long = 0L
    private var AVATAR: ByteArray? = null

    private constructor()

    constructor(
        L_AVATAR_ID: String,
        L_LAST_USE: Long,
        L_AVATAR: ByteArray
    ) {
        AVATAR_ID = L_AVATAR_ID
        LAST_USE = L_LAST_USE
        AVATAR = L_AVATAR
    }


    constructor(jsocket: Jsocket) {
        if (jsocket.value_par1.isEmpty() || jsocket.content == null || jsocket.content!!.isEmpty()) {
            return
        }
        AVATAR_ID = jsocket.value_id3
        LAST_USE = DateTime.nowUnixLong()
        AVATAR = jsocket.content
    }


    constructor(answerType: ANSWER_TYPE) {
        AVATAR_ID = answerType.IDENTIFICATOR_2!!
        LAST_USE = DateTime.nowUnixLong()
        AVATAR = answerType.BLOB_3
    }


    @JsName("getAVATAR_ID")
    fun getAVATAR_ID(): String {
        return AVATAR_ID
    }

    @JsName("getLAST_USE")
    fun getLAST_USE(): Long {
        return LAST_USE
    }

    @JsName("getAVATAR")
    fun getAVATAR(): ByteArray? {
        return AVATAR
    }

    companion object : CoroutineScope {

        override val coroutineContext: CoroutineContext = Dispatchers.Default

        private val KBigAvatar_serviceScope = CoroutineScope(coroutineContext) + SupervisorJob()



        @KorioExperimentalApi
        @JsName("ADD_NEW_BIG_AVATARS")
        fun ADD_NEW_BIG_AVATARS():Promise<Boolean>  =
            CoroutineScope(Dispatchers.Default).async {
                withTimeout(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KBigAvatarLock.lock()
                            while (NEW_BIG_AVATARS.isNotEmpty()) {
                                val anwer_type = NEW_BIG_AVATARS.removeFirst()
                                if (!BIG_AVATARS_IDS.containsKey(anwer_type.IDENTIFICATOR_2!!)) {
                                    val bigAvatar = KBigAvatar(anwer_type)
                                    Sqlite_service.InsertBigAvatar(bigAvatar)
                                    BIG_AVATARS_IDS[bigAvatar.AVATAR_ID] = bigAvatar.AVATAR_ID
                                    BIG_AVATARS[bigAvatar.AVATAR_ID] = bigAvatar
                                }
                            }
                            return@withTimeout true
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KBigAvatar",
                                l_function_name = "ADD_NEW_BIG_AVATARS",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KBigAvatarLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeout false
                }
            }.toPromise(EmptyCoroutineContext)



        @KorioExperimentalApi
        @JsName("RETURN_PROMISE_SELECT_BIG_AVATAR")
        suspend fun RETURN_PROMISE_SELECT_BIG_AVATAR(
            L_OBJECT_ID: String? = null,
            L_AVATAR_ID: String,
            L_CHATS_ID: String? = null,
            L_FROM_SELECT: String = "1",   // (none, server, DB)
            L_MESS_COUNT: String? = null,
            L_AVATARS_LINK: String? = null,
            L_AVATARS_SERVER: String? = null
        ): Promise<KBigAvatar?> = KBigAvatar_serviceScope.async {

            var kBigAvatar: KBigAvatar? = null

            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KBigAvatarLock.lock()
                        if (BIG_AVATARS_IDS.containsKey(L_AVATAR_ID)) {
                            if (BIG_AVATARS.containsKey(L_AVATAR_ID)) {
                                kBigAvatar = BIG_AVATARS[L_AVATAR_ID]
                                Sqlite_service.UpdateBigAvatarsLastUse(L_AVATAR_ID)
                            } else {
                                kBigAvatar = Sqlite_service.SelectBigAvatar(L_AVATAR_ID, true)
                                kBigAvatar?.let { INSERT_BIG_AVATAR_INTO_MAP(it) }
                            }
                        }
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KBigAvatar",
                            l_function_name = "RETURN_PROMISE_SELECT_BIG_AVATAR",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KBigAvatarLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                }
            }
            if (kBigAvatar == null && L_AVATARS_LINK != null && L_AVATARS_SERVER != null) {
                var jsocket: Jsocket? = CLIENT_JSOCKET_POOL.removeFirstOrNull()
                if(jsocket == null){
                    jsocket =  Jsocket()
                    Jsocket.fill()
                    if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                        println("CLIENT_JSOCKET_POOL is emprty")
                    }
                }
                jsocket.value_id1 = L_OBJECT_ID ?: ""
                jsocket.value_id3 = L_AVATAR_ID
                jsocket.value_id4 = L_CHATS_ID ?: ""
                jsocket.value_par2 = L_FROM_SELECT
                jsocket.value_par3 = L_MESS_COUNT ?: ""
                jsocket.value_par5 = L_AVATARS_LINK
                jsocket.value_par6 = L_AVATARS_SERVER
                jsocket.just_do_it = 1011000028
                jsocket.execute().await()
                if (jsocket.content != null && jsocket.content!!.size > 0) {
                    kBigAvatar = KBigAvatar(jsocket)
                    INSERT_BIG_AVATAR_INTO_MAP(kBigAvatar!!)
                    Sqlite_service.InsertBigAvatars(kBigAvatar!!)
                }
            }
            return@async kBigAvatar
        }.toPromise()



        @JsName("IS_HAVE_LOCAL_AVATAR_AND_RESERVE")
        suspend fun IS_HAVE_LOCAL_AVATAR_AND_RESERVE(L_AVATAR_ID: String): Boolean {
            return withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KBigAvatarLock.lock()
                        if (BIG_AVATARS_IDS.containsKey(L_AVATAR_ID)) {
                            Sqlite_service.UpdateBigAvatarsLastUse(L_AVATAR_ID)
                            return@withTimeoutOrNull true
                        } else return@withTimeoutOrNull false
                    } catch (ex: Exception) {
                        throw my_user_exceptions_class(
                            l_class_name = "KBigAvatar",
                            l_function_name = "IS_HAVE_LOCAL_AVATAR_AND_RESERVE",
                            name_of_exception = "EXC_SYSTEM_ERROR",
                            l_additional_text = ex.message
                        )
                    } finally {
                        KBigAvatarLock.unlock()
                    }
                } catch (e: my_user_exceptions_class) {
                    e.ExceptionHand(null)
                    return@withTimeoutOrNull false
                }
            } == true
        }

        @InternalAPI
        @JsName("LOAD_BIG_AVATAR_ADS")
        fun LOAD_BIG_AVATAR_ADS(ids: ArrayList<String>) {
            KBigAvatar_serviceScope.launch {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KBigAvatarLock.lock()
                            ids.forEach {
                                BIG_AVATARS_IDS[it] = it
                            }
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KBigAvatar",
                                l_function_name = "LOAD_BIG_AVATAR_ADS",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KBigAvatarLock.unlock()
                        }

                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                }
            }
        }


        @JsName("LOAD_BIG_AVATARS")
        fun LOAD_BIG_AVATARS(irr: ArrayList<KBigAvatar>) {
            KBigAvatar_serviceScope.launch {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KBigAvatarLock.lock()
                            irr.forEach {
                                BIG_AVATARS_IDS[it.AVATAR_ID] = it.AVATAR_ID
                                BIG_AVATARS[it.AVATAR_ID] = it
                            }
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KBigAvatar",
                                l_function_name = "LOAD_BIG_AVATARS",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KBigAvatarLock.unlock()
                        }

                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                }
            }
        }


        @JsName("INSERT_BIG_AVATAR_INTO_MAP")
        fun INSERT_BIG_AVATAR_INTO_MAP(kBigAvatar: KBigAvatar) {
            KBigAvatar_serviceScope.launch {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KBigAvatarLock.lock()
                            BIG_AVATARS_IDS[kBigAvatar.getAVATAR_ID()] = kBigAvatar.getAVATAR_ID()
                            BIG_AVATARS[kBigAvatar.getAVATAR_ID()] = kBigAvatar
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KBigAvatar",
                                l_function_name = "INSERT_BIG_AVATAR_INTO_MAP",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KBigAvatarLock.unlock()
                        }

                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                }
            }
        }
    }
}