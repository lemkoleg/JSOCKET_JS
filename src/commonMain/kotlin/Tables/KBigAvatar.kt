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
import kotlinx.coroutines.sync.withLock
import lib_exceptions.my_user_exceptions_class
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
val BIG_AVATARS: MutableMap<String, KBigAvatar> = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val BIG_AVATARS_IDS: MutableMap<String, String> = mutableMapOf()

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
    private var IS_HAVE: Boolean = false
    private var IS_INIT: Boolean = false

    private constructor()

    constructor(
        L_AVATAR_ID: String,
        L_LAST_USE: Long = DateTime.nowUnixLong(),
        L_AVATAR: ByteArray
    ) {
        AVATAR_ID = L_AVATAR_ID
        LAST_USE = L_LAST_USE
        AVATAR = L_AVATAR
        IS_HAVE = true
        IS_INIT = true
    }


    constructor(jsocket: Jsocket) {
        if (jsocket.value_par1.isEmpty() || jsocket.content == null || jsocket.content!!.isEmpty()) {
            throw my_user_exceptions_class(
                l_class_name = "KBigAvatar",
                l_function_name = "constructor",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Empty jsocket's data for KBirAvatar"
            )
        }
        AVATAR_ID = jsocket.value_id3
        LAST_USE = DateTime.nowUnixLong()
        AVATAR = jsocket.content
        IS_HAVE = true
        IS_INIT = true
    }


    constructor(answerType: ANSWER_TYPE) {
        if (answerType.IDENTIFICATOR_2 == null || answerType.IDENTIFICATOR_2!!.isEmpty() || answerType.BLOB_4 == null || answerType.BLOB_4!!.isEmpty()) {
            throw my_user_exceptions_class(
                l_class_name = "KBigAvatar",
                l_function_name = "constructor",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Empty jsocket's data for KBirAvatar"
            )
        }
        AVATAR_ID = answerType.IDENTIFICATOR_2!!
        LAST_USE = DateTime.nowUnixLong()
        AVATAR = answerType.BLOB_4!!
        IS_HAVE = true
        IS_INIT = true
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

    @JsName("PROMISE_SELECT_BIG_AVATAR")
    fun PROMISE_SELECT_BIG_AVATAR(answerType: ANSWER_TYPE): Job = CoroutineScope(Dispatchers.Default).launch {

        KBigAvatarLock.withLock {
            if (BIG_AVATARS_IDS.containsKey(AVATAR_ID)) {
                AVATAR = BIG_AVATARS[AVATAR_ID]?.AVATAR
                if (AVATAR != null) {
                    Sqlite_service.UpdateBigAvatarsLastUse(AVATAR_ID)
                } else {
                    AVATAR = Sqlite_service.SelectBigAvatar(AVATAR_ID, true)?.AVATAR
                }
            }
        }
        if (AVATAR != null) {
            IS_HAVE = true
            IS_INIT = true
        } else {

            var jsocket: Jsocket? = Jsocket.GetJsocket()

            if (jsocket == null) {
                jsocket = Jsocket()
                Jsocket.fill()
                if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                    PrintInformation.PRINT_INFO("CLIENT_JSOCKET_POOL is emprty")
                }
            }

            jsocket.value_id4 = answerType.answerTypeValues.GetObjectId()
            jsocket.value_id3 = answerType.answerTypeValues.GetMainAvatarId()
            jsocket.value_id5 = answerType.answerTypeValues.GetChatId()
            jsocket.value_par1 = answerType.answerTypeValues.GetMessegeId().toString()
            jsocket.value_par3 = if (answerType.answerTypeValues.GetAvatarOriginalSize() == 0) "1" else "2"
            jsocket.value_par5 = answerType.answerTypeValues.GetAvatarLink()
            jsocket.value_par6 = answerType.answerTypeValues.GetAvatarServer()

            jsocket.execute().await()
            if (jsocket.content != null && jsocket.content!!.isNotEmpty()) {
                AVATAR = jsocket.content
                INSERT_BIG_AVATAR_INTO_MAP(this@KBigAvatar)
                val arr: ArrayList<KBigAvatar> = ArrayList()
                arr.add(this@KBigAvatar)
                Sqlite_service.InsertBigAvatars(arr)
            }
        }
    }

    companion object : CoroutineScope {

        override val coroutineContext: CoroutineContext = Dispatchers.Default

        private val KBigAvatar_serviceScope = CoroutineScope(coroutineContext) + SupervisorJob()


        @JsName("ADD_NEW_BIG_AVATAR")
        fun ADD_NEW_BIG_AVATAR(avatar: KBigAvatar): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KBigAvatarLock.lock()
                            if (!BIG_AVATARS_IDS.containsKey(avatar.AVATAR_ID)) {
                                BIG_AVATARS_IDS[avatar.AVATAR_ID] = avatar.AVATAR_ID
                                val arr: ArrayList<KBigAvatar> = ArrayList()
                                arr.add(avatar)
                                Sqlite_service.InsertBigAvatars(arr)
                            }
                            if (!BIG_AVATARS.containsKey(avatar.AVATAR_ID)) {
                                BIG_AVATARS[avatar.AVATAR_ID] = avatar
                            }
                            return@withTimeoutOrNull true
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KBigAvatar",
                                l_function_name = "ADD_NEW_BIG_AVATAR",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KBigAvatarLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeoutOrNull false
                }?: throw my_user_exceptions_class(
                    l_class_name = "KBigAvatar",
                    l_function_name = "ADD_NEW_BIG_AVATAR",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }.toPromise(EmptyCoroutineContext)

        @JsName("RETURN_PROMISE_SELECT_BIG_AVATAR")
        suspend fun RETURN_PROMISE_SELECT_BIG_AVATAR(
            P_ANSWER_TYPE: ANSWER_TYPE
        ): Promise<KBigAvatar?> = KBigAvatar_serviceScope.async {
            withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
                try {
                    try {
                        KBigAvatarLock.lock()
                        val kBigAvatar: KBigAvatar?
                        if (BIG_AVATARS_IDS.containsKey(P_ANSWER_TYPE.answerTypeValues.GetMainAvatarId())) {
                            if (BIG_AVATARS.containsKey(P_ANSWER_TYPE.answerTypeValues.GetMainAvatarId())) {
                                kBigAvatar = BIG_AVATARS[P_ANSWER_TYPE.answerTypeValues.GetMainAvatarId()]
                                Sqlite_service.UpdateBigAvatarsLastUse(kBigAvatar!!.AVATAR_ID)
                                return@withTimeoutOrNull kBigAvatar
                            } else {
                                kBigAvatar = Sqlite_service.SelectBigAvatar(
                                    P_ANSWER_TYPE.answerTypeValues.GetMainAvatarId(),
                                    true
                                )
                                if (kBigAvatar != null) {
                                    BIG_AVATARS_IDS[kBigAvatar.getAVATAR_ID()] = kBigAvatar.getAVATAR_ID()
                                    return@withTimeoutOrNull kBigAvatar
                                }
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

                val jsocket = P_ANSWER_TYPE.GetJsocket()
                jsocket.value_par5 = P_ANSWER_TYPE.answerTypeValues.GetAvatarLink()
                jsocket.value_par6 = P_ANSWER_TYPE.answerTypeValues.GetAvatarServer()
                jsocket.execute().await()
                if(jsocket.content != null && jsocket.content!!.isNotEmpty()){
                    val bigAvatar =  KBigAvatar(L_AVATAR_ID = jsocket.value_id3, L_AVATAR = jsocket.content!!)
                    ADD_NEW_BIG_AVATAR(bigAvatar)
                    return@withTimeoutOrNull bigAvatar
                }else return@withTimeoutOrNull null
            }?: throw my_user_exceptions_class(
                l_class_name = "KBigAvatar",
                l_function_name = "RETURN_PROMISE_SELECT_BIG_AVATAR",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
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
            }?: throw my_user_exceptions_class(
                l_class_name = "KBigAvatar",
                l_function_name = "IS_HAVE_LOCAL_AVATAR_AND_RESERVE",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = "Time out is up"
            )
        }


        @JsName("LOAD_BIG_AVATARS_IDS")
        suspend fun LOAD_BIG_AVATARS_IDS(ids: ArrayList<String>) {
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


        @JsName("LOAD_BIG_AVATARS")
        suspend fun LOAD_BIG_AVATARS(irr: ArrayList<KBigAvatar>) {
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
                }?: throw my_user_exceptions_class(
                    l_class_name = "KBigAvatar",
                    l_function_name = "INSERT_BIG_AVATAR_INTO_MAP",
                    name_of_exception = "EXC_SYSTEM_ERROR",
                    l_additional_text = "Time out is up"
                )
            }
        }

        @JsName("RE_LOAD_BIG_AVATAR_IDS")
        fun RE_LOAD_BIG_AVATAR_IDS(): Job {
            return Sqlite_service.LoadBigAvatarsIds()
        }

        @JsName("RE_LOAD_BIG_AVATAR")
        fun RE_LOAD_BIG_AVATAR(): Job {
            return Sqlite_service.LoadBigAvatars()
        }
    }
}