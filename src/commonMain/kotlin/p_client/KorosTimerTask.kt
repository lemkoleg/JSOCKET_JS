@file:Suppress("unused")

package p_client

import atomic.AtomicBoolean
import com.soywiz.korio.experimental.KorioExperimentalApi
import io.ktor.util.*
import kotlinx.coroutines.*
import lib_exceptions.my_user_exceptions_class
import kotlin.coroutines.CoroutineContext
import kotlin.time.ExperimentalTime

/**
 * Immediately stops the timer task, even if the job is currently running,
 * by cancelling the underlying Koros Job.
 */
/**
 * Initiates an orderly shutdown, where if the timer task is currently running,
 * we will let it finish, but not run it again.
 * Invocation has no additional effect if already shut down.
 */
@InternalAPI
@ExperimentalTime
@KorioExperimentalApi
class KorosTimerTask(
    _delay: Long = 0,
    _repeat: Long = 0,
    action: suspend () -> Unit
) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    val KorosTimerTask = CoroutineScope(coroutineContext)

    val keepRunning = AtomicBoolean(true)
    var job: Job? = null
    val tryAction = suspend {
        try {
            action()
        } catch (e: Exception) {
            throw my_user_exceptions_class(
                l_class_name = "KorosTimerTask",
                l_function_name = "constructor",
                name_of_exception = "EXC_SYSTEM_ERROR",
                l_additional_text = e.message
            )
        }
    }
    private val delay = _delay
    val repeat = _repeat


    fun start() {
        job = KorosTimerTask.launch {
            delay(delay)
            if (repeat > 0L) {
                while (keepRunning.value) {
                    tryAction()
                    delay(repeat)
                }
            } else {
                if (keepRunning.value) {
                    tryAction()
                }
            }
        }
    }

    suspend fun execute() {
        tryAction()
    }

    /**
     * Initiates an orderly shutdown, where if the timer task is currently running,
     * we will let it finish, but not run it again.
     * Invocation has no additional effect if already shut down.
     */

    fun shutdown() {
        KorosTimerTask.launch {
            keepRunning.setNewValue(true)
        }
    }

    /**
     * Immediately stops the timer task, even if the job is currently running,
     * by cancelling the underlying Koros Job.
     */

    fun cancel() {
        shutdown()
        job?.cancel("cancel() called")
    }

    companion object {
        /**
         * Runs the given `action` after the given `delay`,
         * once the `action` completes, waits the `repeat` duration
         * and runs again, until `shutdown` is called.
         *
         * if action() throws an exception, it will be swallowed and a warning will be logged.
         */
        @InternalAPI
        @ExperimentalTime
        fun start(
            delay: Long = 0L,
            repeat: Long = 0L,
            action: suspend () -> Unit
        ): KorosTimerTask =
            KorosTimerTask(delay, repeat, action).also { it.start() }
    }
}