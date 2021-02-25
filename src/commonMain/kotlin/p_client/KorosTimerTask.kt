@file:Suppress("unused")

package p_client

import CrossPlatforms.WriteExceptionIntoFile
import atomic.AtomicBoolean
import io.ktor.util.InternalAPI
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Immediately stops the timer task, even if the job is currently running,
 * by cancelling the underlying Koros Job.
 */
/**
 * Initiates an orderly shutdown, where if the timer task is currently running,
 * we will let it finish, but not run it again.
 * Invocation has no additional effect if already shut down.
 */ @InternalAPI @ExperimentalTime
class KorosTimerTask(
    _delay: Duration = Duration.ZERO,
    _repeat: Duration? = null,
    action: suspend () -> Unit
): CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()

    val KorosTimerTask = CoroutineScope(coroutineContext)

    val keepRunning = AtomicBoolean(true)
    var job: Job? = null
    val tryAction = suspend {
        try {
            action()
        } catch (e: Exception) {
            WriteExceptionIntoFile(e, "KorosTimerTask")
        }
    }
    private val delay = _delay
    val repeat = _repeat


    @ExperimentalTime
    fun start() {
        job = KorosTimerTask.launch {
            delay(delay)
            if (repeat != null) {
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
    @InternalAPI
    fun shutdown() {
        keepRunning.setNewValue(true)
    }

    /**
     * Immediately stops the timer task, even if the job is currently running,
     * by cancelling the underlying Koros Job.
     */
    @InternalAPI
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
            delay: Duration = Duration.ZERO,
            repeat: Duration? = null,
            action: suspend () -> Unit
        ): KorosTimerTask =
            KorosTimerTask(delay, repeat,  action).also { it.start() }
    }
}