@file:Suppress("MemberVisibilityCanBePrivate", "PackageName")

package CrossPlatforms


import co.touchlab.stately.concurrency.AtomicBoolean
import co.touchlab.stately.ensureNeverFrozen
import com.badoo.reaktive.utils.lock.Condition
import com.badoo.reaktive.utils.lock.Lock
import com.badoo.reaktive.utils.lock.synchronized
import com.soywiz.klock.DateTime
import com.soywiz.korio.async.delay
import com.soywiz.korio.experimental.KorioExperimentalApi
import com.soywiz.kmem.Platform
import com.soywiz.kmem.isJs
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
import p_jsocket.Constants
import kotlin.js.JsName
import kotlin.time.ExperimentalTime


/*
expect class MyCondition()  {
    var isAwaited: Boolean
        private set

    suspend fun cAwait(t: Long):Boolean
    fun cSignal()
    fun cDestroy()
}

 */



@KorioExperimentalApi
@ExperimentalTime
@InternalAPI
@JsName("MyCondition")
class MyCondition {

    init {
        ensureNeverFrozen()
    }


    private val mutex = Mutex()

    var isAwaited: AtomicBoolean = AtomicBoolean(false)
        private set
    private var time = 0L


    val lock: Lock? = if (!Platform.isJs) {
        Lock()
    } else null


    val cond: Condition? = if (!Platform.isJs) {
        lock?.newCondition()
    } else null


    @JsName("cAwait")
    suspend fun cAwait(t: Long): Boolean{
        isAwaited.value = false
        if(Platform.isJs) {
            time = t + DateTime.nowUnixMillisLong()
            while (!isAwaited.value && time > DateTime.nowUnixMillisLong()) {
                delay(Constants.TIME_SPAN_FOR_LOOP)
            }
        }
        else{
            lock!!.synchronized { cond!!.await(t * 1000000) }
        }
         return isAwaited.value
    }


    @JsName("cSignal")
    fun cSignal(){
        isAwaited.value = true
        if(!Platform.isJs){

            lock!!.synchronized {cond!!.signal() }
        }
    }

    @JsName("cDestroy")
    fun cDestroy(){
        if(Platform.isJs){
            time = DateTime.nowUnixMillisLong()
            }
        else{
            lock!!.synchronized { cond!!.destroy() }
        }
    }
}
