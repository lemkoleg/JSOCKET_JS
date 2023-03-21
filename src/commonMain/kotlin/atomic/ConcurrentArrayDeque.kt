package atomic

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock



val <K> ArrayDeque<K>.lock: Mutex
    get() = Mutex()



suspend fun <K> ArrayDeque<K>.lockedRemoveFirstOrNull(): K? {
    return lock.withLock {
         this.removeFirstOrNull()
    }
}


fun <K> ArrayDeque<K>.lockedTryRemoveFirstOrNull(): K? {
    var l: K? = null
    lock.tryLock{
        l = this.removeFirstOrNull()
    }
    return l
}



suspend fun <K> ArrayDeque<K>.lockedAddLast(v: K){
    this.lock.withLock{
        this.addLast(v)
    }
}