package atomic

import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
//import kotlin.js.JsName

//@JsName("AtomicString")
class AtomicString(v: String){

    private val lock = Mutex()

    var value = v
        private set

    init {
        ensureNeverFrozen()
    }

    //@JsName("setNewValue")
    suspend fun setNewValue(v: String) {
        lock.withLock {
            value = v
        }
    }

    //@JsName("getAndSet")
    suspend fun getAndSet(v: String): String {
        return lock.withLock {
            val old = value
            value = v
            return@withLock old
        }
    }

}