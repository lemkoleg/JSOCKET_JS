package atomic

import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.ExperimentalTime


@InternalAPI
val <K> ArrayDeque<K>.lock: Mutex
    get() = Mutex()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
suspend fun <K> ArrayDeque<K>.lockedRemoveFirstOrNull(): Any? {
    this.lock.withLock(){
        return@withLock this.removeFirstOrNull()
    }
    return null
}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
fun <K> ArrayDeque<K>.lockedTryRemoveFirstOrNull(): Any? {
    this.lock.tryLock(){
        return@tryLock this.removeFirstOrNull()
    }
    return null
}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
suspend fun <K> ArrayDeque<K>.lockedAddLast(v: K){
    this.lock.withLock(){
        this.addLast(v)
    }
}