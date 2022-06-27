package Tables

import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import p_client.CLIENT_JSOCKET_POOL
import p_client.Jsocket
import p_jsocket.Constants
import kotlin.time.ExperimentalTime

class JsocketValues {

    @InternalAPI
    @ExperimentalTime
    @KorioExperimentalApi
    fun GetFileMetaDataForDownload(jSOCKET: Jsocket): Jsocket {

        var jsocket: Jsocket? = CLIENT_JSOCKET_POOL.removeFirstOrNull()

        if (jsocket == null) {
            jsocket = Jsocket()
            Jsocket.fill()
            if (Constants.PRINT_INTO_SCREEN_DEBUG_INFORMATION == 1) {
                println("CLIENT_JSOCKET_POOL is emprty")
            }
        }

        jsocket.just_do_it = jSOCKET.just_do_it
        jsocket.object_extension = jSOCKET.object_extension
        jsocket.object_size = jSOCKET.object_size
        jsocket.value_par1 = jSOCKET.value_par1
        jsocket.AvatarFullPathForSend = jSOCKET.AvatarFullPathForSend
        jsocket.FileFullPathForSend = jSOCKET.FileFullPathForSend

        return jsocket
    }


}