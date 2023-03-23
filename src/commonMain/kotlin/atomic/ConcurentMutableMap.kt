package atomic


import co.touchlab.stately.ensureNeverFrozen
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


val <K, V> MutableMap<K, V>.lock: Mutex
    get() = Mutex()



suspend fun <K, V> MutableMap<K, V>.lockedPut(k:K, v:V) {
    lock.withLock {
        this[k] = v
    }
}



suspend fun <K, V>MutableMap<K, V>.lockedPutAll(v: MutableMap<K, V>) {
    lock.withLock {
        v.keys.forEach{
            this[it] = v[it]!!
        }
    }
}



suspend fun <K, V>MutableMap<K, V>.lockedGet(k:K):V? {
    return  lock.withLock {
         this[k]
    }
}


suspend fun <K, V>MutableMap<K, V>.lockedRemove(k:K):V? {
    return lock.withLock {
         this.remove(k)
    }
}



suspend fun <K, V>MutableMap<K, V>.lockedContains(k:K):Boolean {
    return lock.withLock {
        this.contains(k)
    }
}