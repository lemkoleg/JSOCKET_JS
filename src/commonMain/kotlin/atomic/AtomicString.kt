package atomic

import io.ktor.util.InternalAPI
import io.ktor.util.Lock
import io.ktor.util.withLock
import kotlin.js.JsName

@JsName("AtomicString")
class AtomicString(v: String){

    @InternalAPI
    private val lock = Lock()

    var value = v
        private set

    @InternalAPI
    @JsName("setNewValue")
    fun setNewValue(v: String){
        lock.withLock {
            value = v
        }
    }

    @InternalAPI
    @JsName("getAndSet")
    fun getAndSet(v: String):String {
        return lock.withLock {
            val old = value
            value = v
            return@withLock old
        }
    }

}