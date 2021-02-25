@file:Suppress("MemberVisibilityCanBePrivate", "PackageName")

package CrossPlatforms

import com.badoo.reaktive.utils.lock.Condition
import com.badoo.reaktive.utils.lock.Lock
import com.badoo.reaktive.utils.lock.synchronized
import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan
import com.soywiz.korio.async.delay
import com.soywiz.korio.util.OS
import io.ktor.util.InternalAPI
import kotlin.js.JsName
import kotlin.time.ExperimentalTime

@JsName("DEFAULT_AWAIT_TIMEOUT")
const val DEFAULT_AWAIT_TIMEOUT = 10000L

val timeSpanForLoop = TimeSpan(50.0)


/*
expect class MyCondition()  {
    var isAwaited: Boolean
        private set

    suspend fun cAwait(t: Long):Boolean
    fun cSignal()
    fun cDestroy()
}

 */




@JsName("MyCondition")
class MyCondition  {

    var isAwaited: Boolean = false
        private set
    private var time = 0L

    @InternalAPI
    val lock: Lock? = if (!OS.isJs) {
        Lock()
    } else null

    @InternalAPI
    val cond: Condition? = if (!OS.isJs) {
        lock?.newCondition()
    } else null

    @InternalAPI
    @ExperimentalTime
    @JsName("cAwait")
    suspend fun cAwait(t: Long): Boolean{
        isAwaited = false
        if(OS.isJs) {
            time = t + DateTime.nowUnixLong()
            while (!isAwaited && time > DateTime.nowUnixLong()) {
                delay(timeSpanForLoop)
            }
        }
        else{
            lock!!.synchronized { cond!!.await(t * 1000000) }
        }
         return isAwaited
    }


    @InternalAPI
    @JsName("cSignal")
    fun cSignal(){
        isAwaited = true
        if(!OS.isJs){
            lock!!.synchronized {cond!!.signal() }
        }
    }

    @InternalAPI
    @ExperimentalTime
    @JsName("cDestroy")
    fun cDestroy(){
        if(OS.isJs){
            time = DateTime.nowUnixLong()
            }
        else{
            lock!!.synchronized { cond!!.destroy() }
        }

    }
}
