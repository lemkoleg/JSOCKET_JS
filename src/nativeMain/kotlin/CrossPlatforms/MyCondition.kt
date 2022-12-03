package CrossPlatforms/*package CrossPlatforms

import com.badoo.reaktive.utils.lock.Condition
import com.badoo.reaktive.utils.lock.Lock
import io.ktor.util.InternalAPI

actual class MyCondition actual constructor() {
    @InternalAPI
    val lock: Lock = Lock()
    @InternalAPI
    val condition: Condition = lock.newCondition()


    @InternalAPI
    actual fun Await(t: Long):Boolean {
        isAwaited = false
        condition.await(if(t== 0L)
        {t}
        else
        {
            DEFAULT_AWAIT_TIMEOUT
        })
        return isAwaited
    }

    @InternalAPI
    actual fun Signal() {
        isAwaited = true
        condition.signal()
    }

    @InternalAPI
    actual fun Destroy() {
        isAwaited = false
        condition.destroy()
    }

    actual var isAwaited: Boolean = false
        get() = field
        private set(value) {
            field = value}


}

 */