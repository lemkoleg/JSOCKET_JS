@file:Suppress("unused")

package atomic

import io.ktor.util.InternalAPI
import io.ktor.util.Lock
import io.ktor.util.withLock
import kotlin.js.JsName

@JsName("AtomicInt")
class AtomicInt(v: Int){

    @InternalAPI
    private val lock = Lock()

    var value = v
        private set

    @InternalAPI
    @JsName("setNewValue")
    fun setNewValue(v: Int){
        lock.withLock {
            value = v
        }
    }

    @InternalAPI
    @JsName("setGreaterValue")
    fun setGreaterValue(v: Int){
        lock.withLock {
            if(v > value){value =  v}
        }
    }

    @InternalAPI
    @JsName("setLowerValue")
    fun setLowerValue(v: Int){
        lock.withLock {
            if(v < value){value =  v}
        }
    }

    @InternalAPI
    @JsName("getAndSet")
    fun getAndSet(v: Int):Int{
        return lock.withLock {
            val old = value
            value = v
            return@withLock old
        }
    }
}