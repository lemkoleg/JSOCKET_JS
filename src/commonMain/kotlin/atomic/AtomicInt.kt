@file:Suppress("unused")

package atomic

import co.touchlab.stately.ensureNeverFrozen
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
//import kotlin.js.JsName


//@JsName("AtomicInt")
class AtomicInt(v: Int) {


    private val lock = Mutex()

    var value = v
        private set

    init {
        ensureNeverFrozen()
    }

    //@JsName("setNewValue")
    suspend fun setNewValue(v: Int) {
        lock.withLock {
            value = v
        }
    }

    //@JsName("setGreaterValue")
    suspend fun setGreaterValue(v: Int) {
        lock.withLock {
            if (v > value) {
                value = v
            }
        }
    }

    //@JsName("setLowerValue")
    suspend fun setLowerValue(v: Int) {
        lock.withLock {
            if (v < value) {
                value = v
            }
        }
    }


    //@JsName("getAndSet")
    suspend fun getAndSet(v: Int): Int {
        return lock.withLock {
            val old = value
            value = v
            return@withLock old
        }
    }
}