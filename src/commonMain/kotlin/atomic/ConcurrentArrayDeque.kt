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
suspend fun <K> ArrayDeque<K>.lockedRemoveFirstOrNull(): K? {
    return this.lock.withLock {
        return@withLock this.removeFirstOrNull()
    }
}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
fun <K> ArrayDeque<K>.lockedTryRemoveFirstOrNull(): K? {
    var l: K? = null
    lock.tryLock{
        l = this.removeFirstOrNull()
    }
    return l
}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
suspend fun <K> ArrayDeque<K>.lockedAddLast(v: K){
    this.lock.withLock{
        this.addLast(v)
    }
}