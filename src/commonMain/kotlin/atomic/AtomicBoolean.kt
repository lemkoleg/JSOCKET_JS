@file:Suppress("unused")

package atomic

import io.ktor.util.InternalAPI
import io.ktor.util.Lock
import io.ktor.util.withLock
import kotlin.js.JsName


@JsName("AtomicBoolean")
class AtomicBoolean(v: Boolean){

    @InternalAPI
    private val lock = Lock()

    var value = v
        private set

    @InternalAPI
    @JsName("setNewValue")
    fun setNewValue(v: Boolean){
        lock.withLock {
            value = v
        }
    }

    @InternalAPI
    @JsName("getAndSet")
    fun getAndSet(v: Boolean):Boolean{
        return lock.withLock {
            val old = value
            value = v
            return@withLock old
        }
    }
}