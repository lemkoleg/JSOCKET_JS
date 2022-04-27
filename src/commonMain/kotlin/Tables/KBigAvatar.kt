@file:Suppress("UNUSED_VARIABLE", "UNREACHABLE_CODE", "unused")

/*
 * To change answerType license header, choose License Headers in Project Properties.
 * To change answerType template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables

import CrossPlatforms.WriteExceptionIntoFile
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.async.toPromise
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import p_client.Jsocket
import p_jsocket.CLIENT_TIMEOUT
import sql.Sqlite_service
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

/**
 *
 * @author User
 */

@InternalAPI
private val BIG_AVATARS: MutableMap<String, KBigAvatar> = mutableMapOf()
private val BIG_AVATARS_IDS: MutableMap<String, String> = mutableMapOf()
@InternalAPI
private val KBigAvatarLock = Lock()




@JsName("BigAvatar")
open class KBigAvatar {

    private var AVATAR_ID: String = ""
    private var LAST_USE: Long = 0L
    private var AVATAR: ByteArray? = null

    private constructor()

    constructor(L_AVATAR_ID: String,
                L_LAST_USE: Long,
                L_AVATAR: ByteArray){
        AVATAR_ID = L_AVATAR_ID
        LAST_USE = L_LAST_USE
        AVATAR = L_AVATAR
    }

    @InternalAPI
    constructor(jsocket: Jsocket){
        if(jsocket.value_par1.isEmpty() || jsocket.content == null || jsocket.content!!.isEmpty()){
            return
        }
        AVATAR_ID = jsocket.value_id3
        LAST_USE = DateTime.nowUnixLong()
        AVATAR = jsocket.content
    }


    @JsName("getAVATAR_ID")
    fun getAVATAR_ID(): String {
        return AVATAR_ID
    }

    @JsName("getLAST_USE")
    fun getLAST_USE(): Long{
        return LAST_USE
    }

    @JsName("getAVATAR")
    fun getAVATAR(): ByteArray? {
        return AVATAR
    }

    companion object : CoroutineScope {

        override val coroutineContext: CoroutineContext = Dispatchers.Default

        private val KBigAvatar_serviceScope = CoroutineScope(coroutineContext) + SupervisorJob()



        @ExperimentalTime
        @ExperimentalStdlibApi
        @KorioExperimentalApi
        @InternalAPI
        suspend fun RETURN_PROMISE_SELECT_BIG_AVATAR(
            L_OBJECT_ID: String? = null,
            L_AVATAR_ID: String,
            L_CHATS_ID: String? = null,
            L_FROM_SELECT: String = "1",   // (none, server, DB)
            L_MESS_COUNT: String? = null,
            L_AVATARS_LINK: String,
            L_AVATARS_SERVER: String
        ): Promise<KBigAvatar?>

        = KBigAvatar_serviceScope.async {

            var kBigAvatar: KBigAvatar? = null

            withTimeoutOrNull(CLIENT_TIMEOUT){
                try{
                    KBigAvatarLock.lock()
                    if(BIG_AVATARS_IDS.containsKey(L_AVATAR_ID)){
                        if(BIG_AVATARS.containsKey(L_AVATAR_ID)){
                            kBigAvatar = BIG_AVATARS[L_AVATAR_ID]
                            Sqlite_service.UpdateBigAvatarsLastUse(L_AVATAR_ID)
                        }else{
                            kBigAvatar =  Sqlite_service.SelectBigAvatar(L_AVATAR_ID, true)
                            kBigAvatar?.let { INSERT_BIG_AVATAR_INTO_MAP(it) }
                        }
                  }
              }catch (ex: Exception){
                    WriteExceptionIntoFile(ex, "KBigAvatar.RETURN_PROMISE_SELECT_BIG_AVATAR")
              }finally {
                    KBigAvatarLock.unlock()
              }
            }
            if(kBigAvatar == null){
               val jsocket: Jsocket = Jsocket.getJsocket()
                jsocket.value_id1 = L_OBJECT_ID?:""
                jsocket.value_id3 = L_AVATAR_ID
                jsocket.value_id4 = L_CHATS_ID?:""
                jsocket.value_par2 = L_FROM_SELECT
                jsocket.value_par3 = L_MESS_COUNT?:""
                jsocket.value_par5 = L_AVATARS_LINK
                jsocket.value_par6 = L_AVATARS_SERVER
                jsocket.just_do_it = 1011000028
                jsocket.execute().await()
                if(jsocket.content != null && jsocket.content!!.size > 0){
                    kBigAvatar = KBigAvatar(jsocket)
                    INSERT_BIG_AVATAR_INTO_MAP(kBigAvatar!!)
                    Sqlite_service.InsertBigAvatars(kBigAvatar!!)
                }
            }
            return@async kBigAvatar
        }.toPromise()


        @InternalAPI
        @ExperimentalStdlibApi
        suspend fun IS_HAVE_LOCAL_AVATAR_AND_RESERVE(L_AVATAR_ID: String): Boolean{
            return withTimeoutOrNull(CLIENT_TIMEOUT){
                try{
                    KBigAvatarLock.lock()
                    if(BIG_AVATARS_IDS.containsKey(L_AVATAR_ID)){
                        Sqlite_service.UpdateBigAvatarsLastUse(L_AVATAR_ID)
                        return@withTimeoutOrNull true
                    }else return@withTimeoutOrNull false
                }catch (ex: Exception){
                    WriteExceptionIntoFile(ex, "KBigAvatar.IS_HAVE_LOCAL_AVATAR_AND_RESERVE")
                    return@withTimeoutOrNull false
                }finally {
                    KBigAvatarLock.unlock()
                }
            } == true
        }

        @InternalAPI
        fun LOAD_BIG_AVATAR_ADS(ids:ArrayList<String>){
            KBigAvatar_serviceScope.launch {
                withTimeoutOrNull(CLIENT_TIMEOUT){
                    try{
                        KBigAvatarLock.lock()
                        ids.forEach {
                            BIG_AVATARS_IDS[it] = it
                        }
                    }catch (ex: Exception){
                        WriteExceptionIntoFile(ex, "KBigAvatar.LOAD_BIG_AVATAR_ADS")
                    }finally {
                        KBigAvatarLock.unlock()
                    }
                }
            }
        }

        @InternalAPI
        fun LOAD_BIG_AVATARS(irr:ArrayList<KBigAvatar>){
            KBigAvatar_serviceScope.launch {
                withTimeoutOrNull(CLIENT_TIMEOUT){
                    try{
                        KBigAvatarLock.lock()
                        irr.forEach {
                            BIG_AVATARS_IDS[it.AVATAR_ID] = it.AVATAR_ID
                            BIG_AVATARS[it.AVATAR_ID] = it
                        }
                    }catch (ex: Exception){
                        WriteExceptionIntoFile(ex, "KBigAvatar.LOAD_BIG_AVATARS")
                    }finally {
                        KBigAvatarLock.unlock()
                    }
                }
            }
        }

        @InternalAPI
        fun INSERT_BIG_AVATAR_INTO_MAP(kBigAvatar: KBigAvatar){
            KBigAvatar_serviceScope.launch {
                withTimeoutOrNull(CLIENT_TIMEOUT){
                    try{
                        KBigAvatarLock.lock()
                        BIG_AVATARS_IDS[kBigAvatar.getAVATAR_ID()] = kBigAvatar.getAVATAR_ID()
                        BIG_AVATARS[kBigAvatar.getAVATAR_ID()] = kBigAvatar
                    }catch (ex: Exception){
                        WriteExceptionIntoFile(ex, "KBigAvatar.INSERT_BIG_AVATAR_INTO_MAP")
                    }finally {
                        KBigAvatarLock.unlock()
                    }
                }
            }
        }
    }
}