/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p_jsocket


import com.soywiz.klock.DateTime
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex
//import kotlin.js.JsName
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource.Monotonic

/**
 *
 * @author User
 *
 */
private val time_now = DateTime.nowUnixMillisLong() * 1_000_000


@OptIn(ExperimentalTime::class)
private val clock : TimeMark = Monotonic.markNow()


private val lock = Mutex()



//@JsName("nowNano")
@OptIn(ExperimentalTime::class)
fun nowNano(): Long {

        return time_now + clock.elapsedNow().inWholeNanoseconds

}

