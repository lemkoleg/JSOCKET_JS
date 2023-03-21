package CrossPlatforms/*package CrossPlatforms

import com.badoo.reaktive.utils.lock.Condition
import com.badoo.reaktive.utils.lock.Lock
import io.ktor.util.InternalAPI

actual class MyCondition actual constructor() {
    
    val lock: Lock = Lock()
    
    val condition: Condition = lock.newCondition()


    
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

    
    actual fun Signal() {
        isAwaited = true
        condition.signal()
    }

    
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