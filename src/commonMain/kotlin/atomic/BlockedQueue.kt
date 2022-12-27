@file:Suppress("unused")

package atomic

import CrossPlatforms.MyCondition
import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.kds.Deque
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import lib_exceptions.my_user_exceptions_class
import p_jsocket.Constants
import kotlin.time.ExperimentalTime


//val <T> Deque<T>.InstanceRef: AtomicReference<Deque<T>>
 //   get() = AtomicReference(this)
val <T> Deque<T>.initFroze: Boolean
    get() = this.ensureNeverFrozenn()
fun <T> Deque<T>.ensureNeverFrozenn():Boolean{
    ensureNeverFrozen()
    return true
}

//val <T> ArrayList<T>.InstanceRef: AtomicReference<ArrayList<T>>
//    get() = AtomicReference(this)
val <T> ArrayList<T>.initFroze: Boolean
    get() = this.ensureNeverFrozenn()
fun <T> ArrayList<T>.ensureNeverFrozenn():Boolean{
    ensureNeverFrozen()
    return true
}

//val <T> Array<T>.InstanceRef: AtomicReference<Array<T>>
//    get() = AtomicReference(this)
val <T> Array<T>.initFroze: Boolean
    get() = this.ensureNeverFrozenn()
fun <T> Array<T>.ensureNeverFrozenn():Boolean{
    ensureNeverFrozen()
    return true
}

//val <T> ArrayDeque<T>.InstanceRef: AtomicReference<ArrayDeque<T>>
//    get() = AtomicReference(this)
val <T> ArrayDeque<T>.initFroze: Boolean
    get() = this.ensureNeverFrozenn()
fun <T> ArrayDeque<T>.ensureNeverFrozenn():Boolean{
    ensureNeverFrozen()
    return true
}

//val ByteArray.InstanceRef: AtomicReference<ByteArray>
//    get() = AtomicReference(this)
val ByteArray.initFroze: Boolean
    get() = this.ensureNeverFrozenn()
fun ByteArray.ensureNeverFrozenn():Boolean{
    ensureNeverFrozen()
    return true
}


private val <T> ArrayDeque<T>.lockOut
    get() = Mutex()

private val <T> ArrayDeque<T>.lockIn
    get() = Mutex()

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
private val <T> ArrayDeque<T>.condition: MyCondition
    get() = MyCondition()


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
suspend fun <T> ArrayDeque<T>.dequeue(timOut: Long): T? {
    var t: T? = removeFirst()
    if(t == null && timOut > 0){
        condition.cAwait(timOut)
    }
    t = removeFirst()
    return t
}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
suspend fun <T> ArrayDeque<T>.lockedDequeue(timOut: Long): T? {
    return withTimeoutOrNull(if (Constants.CLIENT_TIMEOUT > timOut) timOut else Constants.CLIENT_TIMEOUT) {
        lockOut.withLock {
            var t: T? = removeFirst()
            if(t == null && timOut > 0){
                condition.cAwait(timOut)
            }
            t = removeFirst()
            return@withTimeoutOrNull t
        }
    }?:throw my_user_exceptions_class(
        l_class_name = "ArrayDeque",
        l_function_name = "lockedDequeue",
        name_of_exception = "EXC_SYSTEM_ERROR",
        l_additional_text = "Time out is up"
    )
}


@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
fun <T> ArrayDeque<T>.enqueue(v: T, size: Int = Constants.STANDART_QUEUE_SIZE, whatQueue: String) {
    if (this.size > size) {
        condition.cSignal()
        throw my_user_exceptions_class(
            l_class_name = "BlockedQueue",
            l_function_name = "enqueue",
            name_of_exception = "QUEUE FULL",
            whatQueue
        )
    } else {
        this.addLast(v)
        condition.cSignal()
    }
}

@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
suspend fun <T> ArrayDeque<T>.lockedEnqueue(v: T, size: Int = Constants.STANDART_QUEUE_SIZE, whatQueue: String) {
    try {
        val q: ArrayDeque<T> = this
        withTimeoutOrNull(Constants.CLIENT_TIMEOUT) {
            lockIn.withLock {
                if (q.size > size) {
                    condition.cSignal()
                    throw my_user_exceptions_class(
                        l_class_name = "BlockedQueue",
                        l_function_name = "concurentEnqueue",
                        name_of_exception = "QUEUE FULL",
                        whatQueue
                    )
                } else {
                    q.addLast(v)
                    condition.cSignal()
                }
            }
        }?:throw my_user_exceptions_class(
            l_class_name = "ArrayDeque",
            l_function_name = "lockedEnqueue",
            name_of_exception = "EXC_SYSTEM_ERROR",
            l_additional_text = "Time out is up"
        )
    } catch (e: my_user_exceptions_class) {
        throw e
    } catch (e: Exception) {
        throw my_user_exceptions_class(
            l_class_name = "BlockedQueue",
            l_function_name = "concurentEnqueue",
            name_of_exception = e.stackTraceToString(),
            whatQueue
        )
    }
}