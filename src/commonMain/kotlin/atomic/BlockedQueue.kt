@file:Suppress("unused")

package atomic

import CrossPlatforms.MyCondition
import com.soywiz.kds.Queue
import io.ktor.util.InternalAPI
import io.ktor.util.Lock
import io.ktor.util.withLock
import lib_exceptions.exc_queue_is_full
import p_jsocket.STANDART_QUEUE_SIZE
import kotlin.jvm.Synchronized

@InternalAPI
private val <T> Queue<T>.lock: Lock
    get() = Lock()

private val <T> Queue<T>.condition: MyCondition
    get() = MyCondition()

@Synchronized
@InternalAPI
suspend fun <T> Queue<T>.dequeue(timOut: Long): T? {
    var t: T? = null
    if (timOut > 0 && this.isEmpty()) {
        condition.cAwait(timOut)
    }
    lock.withLock {
        if (peek() != null) {
            t = dequeue()
        }
    }
   return  t
}

@Synchronized
@InternalAPI
fun <T> Queue<T>.enqueue(v:T, size:Int = STANDART_QUEUE_SIZE, whatQueue: String) {
    lock.withLock {
        if (this.size > size) {
            condition.cSignal()
            throw exc_queue_is_full(whatQueue)
        } else {
            this.enqueue(v)
            condition.cSignal()
        }
    }

}