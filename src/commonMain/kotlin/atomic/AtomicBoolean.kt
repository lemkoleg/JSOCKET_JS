@file:Suppress("unused")

package atomic

import co.touchlab.stately.ensureNeverFrozen
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
//import kotlin.js.JsName
import kotlin.time.ExperimentalTime


//@JsName("AtomicBoolean")
class AtomicBoolean(v: Boolean){


    private val lock = Mutex()

    var value = v
        private set

    init {
        ensureNeverFrozen()
    }


    //@JsName("setNewValue")
    suspend fun setNewValue(v: Boolean){
        lock.withLock {
            value = v
        }
    }

    //@JsName("getValue")
    suspend fun getValue(): Boolean{
        lock.withLock {
            return value
        }
    }

    //@JsName("getAndSet")
    suspend fun getAndSet(v: Boolean):Boolean{
        return lock.withLock {
            val old = value
            value = v
            return@withLock old
        }
    }
}