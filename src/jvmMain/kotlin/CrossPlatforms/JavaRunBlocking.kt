package CrossPlatforms


import com.soywiz.korio.async.Promise
import com.soywiz.korio.async.await
import com.soywiz.korio.experimental.KorioExperimentalApi
import kotlinx.coroutines.runBlocking

@KorioExperimentalApi
class JavaRunBlocking {

    fun <T> RunBlocking(pr: Promise<T>) = runBlocking {
        return@runBlocking pr.await()
    }

    fun <T> RunBlocking(f: (() -> T)) = runBlocking {
        return@runBlocking f()
    }
}