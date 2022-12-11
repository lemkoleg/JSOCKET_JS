package atomic


import co.touchlab.stately.ensureNeverFrozen
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


//val <K, V> MutableMap<K, V>.InstanceRef: AtomicReference<MutableMap<K, V>>
//    get() = AtomicReference(this)
val <K, V> MutableMap<K, V>.initFroze: Boolean
    get() = this.ensureNeverFrozenn()
fun <K, V> MutableMap<K, V>.ensureNeverFrozenn():Boolean{
    ensureNeverFrozen()
    return true
}

@InternalAPI
val <K, V> MutableMap<K, V>.lock: Mutex
    get() = Mutex()


@InternalAPI
suspend fun <K, V> MutableMap<K, V>.lockedPut(k:K, v:V) {
    lock.withLock {
        this[k] = v
    }
}


@InternalAPI
suspend fun <K, V>MutableMap<K, V>.lockedPutAll(v: MutableMap<K, V>) {
    lock.withLock {
        v.keys.forEach{
            this[it] = v[it]!!
        }
    }
}


@InternalAPI
suspend fun <K, V>MutableMap<K, V>.lockedGet(k:K):V? {
    return  lock.withLock {
         this[k]
    }
}

@InternalAPI
suspend fun <K, V>MutableMap<K, V>.lockedRemove(k:K):V? {
    return lock.withLock {
         this.remove(k)
    }
}


@InternalAPI
suspend fun <K, V>MutableMap<K, V>.lockedContains(k:K):Boolean {
    return lock.withLock {
        this.contains(k)
    }
}