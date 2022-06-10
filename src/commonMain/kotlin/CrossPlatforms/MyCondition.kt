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
import com.soywiz.korio.util.OS
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


    val lock: Lock? = if (!OS.isJs) {
        Lock()
    } else null


    val cond: Condition? = if (!OS.isJs) {
        lock?.newCondition()
    } else null


    @JsName("cAwait")
    suspend fun cAwait(t: Long): Boolean{
        isAwaited.value = false
        if(OS.isJs) {
            time = t + DateTime.nowUnixLong()
            while (!isAwaited.value && time > DateTime.nowUnixLong()) {
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
        if(!OS.isJs){
            lock!!.synchronized {cond!!.signal() }
        }
    }

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
