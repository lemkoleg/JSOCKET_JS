@file:Suppress("UNUSED_VARIABLE")

val kotlinVersion = "1.8.0"
val kotlinCorotinesVersion = "1.6.4"
val ktorVersion = "2.2.3"
val reactiveVersion = "1.2.3"
val KlockVersion = "3.4.0"
val KorimVersion = "3.4.0"
val KorioVersion = "3.4.0"
val KryptoVersion = "3.4.0"
val SQLDelightVersion = "1.5.5"
val SQLDelightDialect = "sqlite:3.25"  //sqlite:3.25
val AlaSQLVersion = "1.7.2"
val TextEncodingVersion = "0.7.0"
val Fingerprintjs2Version = "2.1.4"
val StatelyVersion = "1.2.2"

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.8.0"
    id("com.squareup.sqldelight")  version "1.5.5"
    java
}

group = "AUF"
version = "1.0"

buildscript {
    repositories {
        google()
        //jcenter()
        gradlePluginPortal()
    }
    dependencies {
        classpath ("com.squareup.sqldelight:gradle-plugin:1.5.5")
    }
}

//apply(plugin = "com.squareup.sqldelight")


allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        //jcenter()
        google()
        maven { url = uri("https://kotlin.bintray.com/ktor")}
        maven { url = uri("https://dl.bintray.com/badoo/maven")}
        maven { url = uri("https://dl.bintray.com/suparnatural/kotlin-multiplatform")}
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin")}
        maven { url = uri("https://kotlin.bintray.com/kotlinx")}
        maven { url = uri("https://jetbrains.bintray.com/kotlin-native-dependencies")}
        maven { url = uri("https://maven.fabric.io/public")}
        maven { url = uri("https://dl.bintray.com/icerockdev/plugins")}
        maven { url = uri("https://plugins.gradle.org/m2/")}
        maven { url = uri("https://dl.bintray.com/korlibs/korlibs/")}
    }


}

dependencies {
    implementation("commons-io:commons-io:2.11.0")
}


kotlin {

    /*
    kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
        binaries.all {
            binaryOptions["memoryModel"] = "experimental"
        }
    }
     */

    jvm("jvm") {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8" // "11"
        }
        //compilations["main"].kotlinOptions.jvmTarget = "11"
        withJava()



    }

    js(BOTH) {
        browser {

        }
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    /*
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
     */

    sqldelight {
        database("AUFDB") {
            dialect = SQLDelightDialect
            packageName = "JSOCKETDB"
            //sourceFolders = listOf("sqldelight")
            schemaOutputDirectory = file("sqldelight/databases")
        }
        //linkSqlite = true
    }




    /*iosX64("iosX64") {
        binaries {
            staticLib {
                    baseName = "JSOCKET"
            }
        }
    }*/
    /*
    sourceSets {
        all {
            languageSettings.optIn("JSOCKET")
        }
    }
     */

    sourceSets["commonMain"].dependencies {
        //implementation ( kotlin("stdlib-jdk8", kotlinVersion))
        //implementation ( kotlin("stdlib-common", kotlinVersion))
        //implementation ("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCorotinesVersion")

        implementation ("io.ktor:ktor-utils:$ktorVersion")
        implementation ("io.ktor:ktor-io:$ktorVersion")
        //implementation("io.ktor:ktor-websocket:$ktorVersion")
        //implementation("io.ktor:ktor-client-websockets:$ktorVersion")
        //implementation ("io.ktor:ktor-network:$ktorVersion")
        //implementation ("io.ktor:ktor-websockets:$ktorVersion")
        //implementation ("io.ktor:ktor-client-websockets:$ktorVersion")

        implementation ("com.soywiz.korlibs.klock:klock:$KlockVersion")
        implementation ("com.soywiz.korlibs.korim:korim:$KorimVersion")
        implementation ("com.soywiz.korlibs.korio:korio:$KorioVersion")
        implementation ("com.soywiz.korlibs.krypto:krypto:$KryptoVersion")

        //implementation ("suparnatural-kotlin-multiplatform:cache-metadata:$cachingVersion")
        //implementation ("suparnatural-kotlin-multiplatform:fs-metadata:$fsVersion")

        implementation ("com.badoo.reaktive:reaktive:$reactiveVersion")
        implementation ("com.badoo.reaktive:reaktive-annotations:$reactiveVersion")
        implementation ("com.badoo.reaktive:utils:$reactiveVersion")


        //implementation(npm("alasql","0.0.51"))
        //implementation(npm("@nano-sql/adapter-sqlite-cordova"))
        //implementation(npm("@stevegill/cordova-plugin-device"))

        implementation ("co.touchlab:stately-common:$StatelyVersion")
        implementation ("co.touchlab:stately-concurrency:$StatelyVersion")
        implementation ("co.touchlab:stately-isolate:$StatelyVersion")

        implementation("com.squareup.sqldelight:runtime:$SQLDelightVersion")
        implementation("com.squareup.sqldelight:coroutines-extensions:$SQLDelightVersion")

    }

    sourceSets["commonTest"].dependencies {
        implementation ( kotlin("test-common", kotlinVersion))
        implementation ( kotlin("test-annotations-common", kotlinVersion))
    }

    sourceSets["jvmMain"].dependencies {
        //implementation ( kotlin("stdlib-jdk8", kotlinVersion))
        implementation ("io.ktor:ktor-utils-jvm:$ktorVersion")
        implementation ("io.ktor:ktor-io-jvm:$ktorVersion")
        //implementation ("io.ktor:ktor-client-websockets-jvm:$ktorVersion")
        //implementation ("io.ktor:ktor-network-jwm:$ktorVersion")
        //implementation ("io.ktor:ktor-websockets-jwm:$ktorVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinCorotinesVersion")


        //implementation ("suparnatural-kotlin-multiplatform:fs-jvm:$fsVersion")

        implementation ("com.badoo.reaktive:reaktive-jvm:$reactiveVersion")
        implementation ("com.badoo.reaktive:reaktive-annotations-jvm:$reactiveVersion")
        implementation ("com.badoo.reaktive:utils-jvm:$reactiveVersion")

        implementation ("com.soywiz.korlibs.klock:klock-jvm:$KlockVersion")
        implementation ("com.soywiz.korlibs.korim:korim-jvm:$KorimVersion")
        implementation ("com.soywiz.korlibs.krypto:krypto-jvm:$KryptoVersion")
        implementation ("com.soywiz.korlibs.korio:korio-jvm:$KorioVersion")

        implementation ("com.squareup.sqldelight:sqlite-driver:$SQLDelightVersion")
        //implementation ("com.squareup.sqldelight:native-driver:$SQLDelightVersion")
        //implementation ("com.squareup.sqldelight:android-driver:$SQLDelightVersion")

        //implementation(fileTree(mapOf("dir" to "E:/MyNetBeans/Librery/MY_OSHI", "include" to listOf("*.jar"))))
    }

    sourceSets["jvmTest"].dependencies {
        implementation(kotlin("test"))
        implementation(kotlin("test-junit"))
    }


    sourceSets["jsMain"].dependencies {
        //implementation(kotlin("stdlib-js", kotlinVersion))
        implementation("io.ktor:ktor-utils-js:$ktorVersion")
        implementation("io.ktor:ktor-io-js:$ktorVersion")
        //implementation ("io.ktor:ktor-client-websockets-js:$ktorVersion")
        //implementation ("io.ktor:ktor-network-js:$ktorVersion")
        //implementation ("io.ktor:ktor-websockets-js:$ktorVersion")

        //implementation ("suparnatural-kotlin-multiplatform:fs-jvm:$fsVersion")

        //implementation ("com.badoo.reaktive:reaktive-js:$reactiveVersion")
        //implementation ("com.badoo.reaktive:reaktive-annotations-js:$reactiveVersion")
        //implementation ("com.badoo.reaktive:utils-js:$reactiveVersion")

        implementation("com.soywiz.korlibs.klock:klock-js:$KlockVersion")
        implementation("com.soywiz.korlibs.korim:korim-js:$KorimVersion")
        implementation("com.soywiz.korlibs.krypto:krypto-js:$KryptoVersion")

        //implementation ("com.squareup.sqldelight:sqljs-driver:$SQLDelightVersion")

        //implementation(fileTree(mapOf("dir" to "E:/MyIntellijIDEA/JSOCKET/src/jsMain/kotlin/js", "include" to listOf("*.js"))))

       //implementation (npm("@fingerprintjs/fingerprintjs", Fingerprintjs2Version))
        //implementation (npm("await-signal"))
        //implementation (npm("text-encoding", TextEncodingVersion))
        //implementation (npm("alasql", AlaSQLVersion))
        //implementation (npm("eventemitter3"))
        //implementation(npm("delay"))

        //implementation ("org.webjars.npm:macaddress:0.2.9"
        //implementation(npm("macaddress", "0.2.9"))
    }

    sourceSets["jsTest"].dependencies {
        implementation ( kotlin("test-js", kotlinVersion))
    }

}

tasks.register<Jar>("uberJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveClassifier.set("uber")

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

