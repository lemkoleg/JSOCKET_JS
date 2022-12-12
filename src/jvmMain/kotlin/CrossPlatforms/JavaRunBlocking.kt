package CrossPlatforms


import kotlinx.coroutines.runBlocking
import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.experimental.KorioExperimentalApi

@KorioExperimentalApi
class JavaRunBlocking {

    fun <T> RunBlocking(pr: Promise<T>) = runBlocking {
        pr.await()
    }
}