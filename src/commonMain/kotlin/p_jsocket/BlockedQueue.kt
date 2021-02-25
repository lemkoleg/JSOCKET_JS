@file:Suppress("unused")

package p_jsocket

import CrossPlatforms.MyCondition
import com.soywiz.kds.Queue
import io.ktor.util.InternalAPI
import io.ktor.util.Lock
import io.ktor.util.withLock
import lib_exceptions.exc_queue_is_full
import kotlin.jvm.Synchronized
import kotlin.time.ExperimentalTime

private const val standartQueueSize = 1000


@InternalAPI
private val <T> Queue<T>.lock: Lock
    get() = Lock()

private val <T> Queue<T>.condition: MyCondition
    get() = MyCondition()

@Synchronized
@ExperimentalTime
@InternalAPI
suspend fun <T> Queue<T>.dequeue(timOut: Long): T? {
    var t: T? = null
    if (timOut > 0 && this.isEmpty()) {
        condition.cAwait(timOut)
    }
    lock.withLock {
        t = peek()
        if (t != null) {
            dequeue()
        }
    }
   return  t
}

@Synchronized
@InternalAPI
fun <T> Queue<T>.enqueue(v:T, size:Int = standartQueueSize, whatQueue: String) {
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