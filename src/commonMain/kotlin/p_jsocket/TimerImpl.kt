/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket


import com.soywiz.klock.DateTime
import io.ktor.util.*
import kotlin.js.JsName
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource.Monotonic

/**
 *
 * @author User
 *
 */
private val time_now = DateTime.nowUnixLong() * 1_000_000

@ExperimentalTime
private val clock : TimeMark = Monotonic.markNow()
@InternalAPI
private val lock = Lock()
@InternalAPI
@ExperimentalTime

@JsName("nowNano")
fun nowNano(): Long {
    return lock.withLock {
        return@withLock clock.elapsedNow().inWholeNanoseconds
    }
}

