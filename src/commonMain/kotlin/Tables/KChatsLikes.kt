package Tables


import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.async.Promise
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


@InternalAPI
private val KChatsLikesLock = Mutex()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val NEW_CHATS_LIKES: ArrayDeque<ANSWER_TYPE> = ArrayDeque()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
val CHATS_LIKES: MutableMap<String, KChatsLikes> = mutableMapOf()

@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("KChatsLikes")
class KChatsLikes(L_CHATS_ID: String) {

    val CHAT_LIKES: MutableMap<String, KChatsLike> = mutableMapOf()


    init {
        ensureNeverFrozen()
    }


    companion object {

        @KorioExperimentalApi
        @JsName("ADD_NEW_CHATS_LIKES")
        fun ADD_NEW_CHATS_LIKES(): Promise<Boolean> =
            CoroutineScope(Dispatchers.Default).async {
                withTimeout(Constants.CLIENT_TIMEOUT) {
                    try {
                        try {
                            KChatsLikesLock.lock()
                            while (NEW_CHATS_LIKES.isNotEmpty()) {
                                TODO()
                            }
                            return@withTimeout true
                        } catch (ex: Exception) {
                            throw my_user_exceptions_class(
                                l_class_name = "KChatsLikes",
                                l_function_name = "ADD_NEW_CHATS_LIKES",
                                name_of_exception = "EXC_SYSTEM_ERROR",
                                l_additional_text = ex.message
                            )
                        } finally {
                            KChatsLikesLock.unlock()
                        }
                    } catch (e: my_user_exceptions_class) {
                        e.ExceptionHand(null)
                    }
                    return@withTimeout false
                }
        }.toPromise(EmptyCoroutineContext)
    }

    class KChatsLike(L_CHATS_ID: String,
                     L_ACCOUNTS_ID: String,
                     L_RELATIONS: String,
                     L_CHATS_LIKES_PROFILE: String,
                     L_ADDING_DATE: Long,
                     L_FIRST_MESS_COUNT: Long,
                     L_LAST_MESS_COUNT: Long,
                     L_LAST_DATE_DELIVERED: Long,
                     L_LAST_READED_MESS_ID: Long,
                     L_BALANCE: Int,
                     L_LAST_CONNECT : Long,
                     L_DATE_DELETE : Long,
                     L_ACCOUNTS_NAME : String,
                     L_AVATAR_ID : String?,
                     L_ORIGINAL_AVATAR_SIZE : Int?,
                     L_AVATAR_SERVER : String?,
                     L_VATAR_LINK : String?,
                     L_AVATAR : ByteArray?,
                     L_STRING_20 : String){
        val CHATS_ID = L_CHATS_ID
        val ACCOUNTS_ID = L_ACCOUNTS_ID
        val RELATIONS = L_RELATIONS
        val CHATS_LIKES_PROFILE = L_CHATS_LIKES_PROFILE
        val ADDING_DATE = L_ADDING_DATE
        val FIRST_MESS_COUNT = L_FIRST_MESS_COUNT
        val LAST_MESS_COUNT = L_LAST_MESS_COUNT
        val LAST_DATE_DELIVERED = L_LAST_DATE_DELIVERED
        val LAST_READED_MESS_ID = L_LAST_READED_MESS_ID
        val BALANCE = L_BALANCE
        val LAST_CONNECT = L_LAST_CONNECT
        val DATE_DELETE = L_DATE_DELETE
        val ACCOUNTS_NAME = L_ACCOUNTS_NAME
        val AVATAR_ID = L_AVATAR_ID?:""
        val ORIGINAL_AVATAR_SIZE = L_ORIGINAL_AVATAR_SIZE?:0
        val AVATAR_SERVER = L_AVATAR_SERVER?:""
        val AVATAR_LINK = L_VATAR_LINK?:""
        val AVATAR = L_AVATAR
        val STRING_20 = L_STRING_20

        constructor(l_answer_type: ANSWER_TYPE):this(l_answer_type.){
        }
    }


}