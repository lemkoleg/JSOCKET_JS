@file:Suppress("unused")

package atomic

import io.ktor.util.InternalAPI
import io.ktor.util.Lock
import io.ktor.util.withLock
import kotlin.js.JsName

@JsName("AtomicLong")
class AtomicLong(v: Long){

    @InternalAPI
    private val lock = Lock()

    var value = v
        private set

    @InternalAPI
    @JsName("setNewValue")
    fun setNewValue(v: Long){
        lock.withLock {
            value = v
        }
    }

    @InternalAPI
    @JsName("setGreaterValue")
    fun setGreaterValue(v: Long){
        lock.withLock {
            if(v > value){value =  v}
        }
    }

    @InternalAPI
    @JsName("setLowerValue")
    fun setLowerValue(v: Long){
        lock.withLock {
            if(v < value){value =  v}
        }
    }

    @InternalAPI
    @JsName("getAndSet")
    fun getAndSet(v: Long):Long{
        return lock.withLock {
            val old = value
            value = v
            return@withLock old
        }
    }
}