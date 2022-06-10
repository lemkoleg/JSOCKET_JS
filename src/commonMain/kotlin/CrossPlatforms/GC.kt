package CrossPlatforms

expect object GC {
    fun collect()
}

/*
for JVM, JS
 actual object GC {
    actual fun collect() = Unit
 }

 for IOS
 actual typealias GC = kotlin.native.internal.GC

 */