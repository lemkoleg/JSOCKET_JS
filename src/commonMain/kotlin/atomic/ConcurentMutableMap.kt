package atomic


import io.ktor.util.*
import kotlin.jvm.Synchronized


@InternalAPI
val <K, V> MutableMap<K, V>.lock: Lock
    get() = Lock()

@Synchronized
@InternalAPI
fun <K, V> MutableMap<K, V>.lockedPut(k:K, v:V) {
    lock.withLock {
        this[k] = v
    }
}

@Synchronized
@InternalAPI
fun <K, V>MutableMap<K, V>.lockedPutAll(v: MutableMap<K, V>) {
    lock.withLock {
        v.keys.forEach{
            this[it] = v[it]!!
        }
    }
}

@Synchronized
@InternalAPI
fun <K, V>MutableMap<K, V>.lockedGet(k:K):V? {
    lock.withLock {
        if(this.contains(k)){
            return@withLock this[k]
        }else{
            return@withLock null
        }
    }
    return null
}

@Synchronized
@InternalAPI
fun <K, V>MutableMap<K, V>.lockedContains(k:K):Boolean {
    lock.withLock {
        return@withLock this.contains(k)
    }
    return false
}