@file:Suppress("MemberVisibilityCanBePrivate", "PackageName")

package CrossPlatforms

import com.badoo.reaktive.utils.lock.Condition
import com.badoo.reaktive.utils.lock.Lock
import com.badoo.reaktive.utils.lock.synchronized
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.delay
import com.soywiz.korio.util.OS
import io.ktor.util.*
import p_jsocket.TIME_SPAN_FOR_LOOP
import kotlin.js.JsName


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
    @JsName("cAwait")
    suspend fun cAwait(t: Long): Boolean{
        isAwaited = false
        if(OS.isJs) {
            time = t + DateTime.nowUnixLong()
            while (!isAwaited && time > DateTime.nowUnixLong()) {
                delay(TIME_SPAN_FOR_LOOP)
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
