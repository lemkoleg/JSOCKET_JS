val kotlinVersion = "1.7.22"
val ktorVersion = "2.0.1"
val reactiveVersion = "1.2.1"
val KlockVersion = "2.4.13"
val KorimVersion = "2.2.2"
val KorioVersion = "2.2.1"
val KryptoVersion = "2.2.0"
val SQLDelightVersion = "1.5.4"
val SQLDelightDialect = "sqlite:3.25"  //sqlite:3.25
val AlaSQLVersion = "1.7.2"
val TextEncodingVersion = "0.7.0"
val Fingerprintjs2Version = "2.1.4"
val StatelyVersion = "1.2.2"

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.7.22"
    id("com.squareup.sqldelight")  version "1.5.4"
}

buildscript {
    repositories {
        google()
        //jcenter()
        gradlePluginPortal()
    }
    dependencies {
        classpath ("com.squareup.sqldelight:gradle-plugin:1.5.4")
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




kotlin {

    /*
    kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
        binaries.all {
            binaryOptions["memoryModel"] = "experimental"
        }
    }
     */

    jvm("jvm") {
        withJava()
        compilations["main"].kotlinOptions.jvmTarget = "1.8"
    }

    js(IR) {  // or: LEGACY, BOTH
        browser {

        }

        //nodejs {
        //}
    }

    BOTH

    sqldelight {
        database("AvaClubDB") {
            dialect = SQLDelightDialect
            packageName = "JSOCKETDB"
            //sourceFolders = listOf<String>("src/commonMain/kotlin/sqldelight")
            schemaOutputDirectory = file("src/commonMain/kotlin/sqldelight/databases")
        }
        linkSqlite = false
    }


    /*iosX64("iosX64") {
        binaries {
            staticLib {
                    baseName = "JSOCKET"
            }
        }
    }*/
    sourceSets {
        all {
            languageSettings.optIn("JSOCKET")
        }
    }

    sourceSets["commonMain"].dependencies {
        api ( kotlin("stdlib-jdk8", kotlinVersion))
        api ( kotlin("stdlib-common", kotlinVersion))
        api ("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

        api ("io.ktor:ktor-utils:$ktorVersion")
        api ("io.ktor:ktor-io:$ktorVersion")
        //api("io.ktor:ktor-websocket:$ktorVersion")
        //api("io.ktor:ktor-client-websockets:$ktorVersion")
        //api ("io.ktor:ktor-network:$ktorVersion")
        //api ("io.ktor:ktor-websockets:$ktorVersion")
        //api ("io.ktor:ktor-client-websockets:$ktorVersion")

        api ("com.soywiz.korlibs.klock:klock:$KlockVersion")
        api ("com.soywiz.korlibs.korim:korim:$KorimVersion")
        api ("com.soywiz.korlibs.korio:korio:$KorioVersion")
        api ("com.soywiz.korlibs.krypto:krypto:$KryptoVersion")

        //api ("suparnatural-kotlin-multiplatform:cache-metadata:$cachingVersion")
        //api ("suparnatural-kotlin-multiplatform:fs-metadata:$fsVersion")

        api ("com.badoo.reaktive:reaktive:$reactiveVersion")
        api ("com.badoo.reaktive:reaktive-annotations:$reactiveVersion")
        api ("com.badoo.reaktive:utils:$reactiveVersion")


        //api(npm("alasql","0.0.51"))
        //api(npm("@nano-sql/adapter-sqlite-cordova"))
        //api(npm("@stevegill/cordova-plugin-device"))

        api ("co.touchlab:stately-common:$StatelyVersion")
        api ("co.touchlab:stately-concurrency:$StatelyVersion")
        api ("co.touchlab:stately-isolate:$StatelyVersion")

        api ("co.touchlab:stately-isolate:$StatelyVersion")

    }

    sourceSets["commonTest"].dependencies {
        api ( kotlin("test-common", kotlinVersion))
        api ( kotlin("test-annotations-common", kotlinVersion))
    }

    sourceSets["jvmMain"].dependencies {
        api ( kotlin("stdlib-jdk8", kotlinVersion))
        api ("io.ktor:ktor-utils-jvm:$ktorVersion")
        api ("io.ktor:ktor-io-jvm:$ktorVersion")
        //api ("io.ktor:ktor-client-websockets-jvm:$ktorVersion")
        //api ("io.ktor:ktor-network-jwm:$ktorVersion")
        //api ("io.ktor:ktor-websockets-jwm:$ktorVersion")

        //api ("suparnatural-kotlin-multiplatform:fs-jvm:$fsVersion")

        api ("com.badoo.reaktive:reaktive-jvm:$reactiveVersion")
        api ("com.badoo.reaktive:reaktive-annotations-jvm:$reactiveVersion")
        api ("com.badoo.reaktive:utils-jvm:$reactiveVersion")

        api ("com.soywiz.korlibs.klock:klock-jvm:$KlockVersion")
        api ("com.soywiz.korlibs.korim:korim-jvm:$KorimVersion")
        api ("com.soywiz.korlibs.krypto:krypto-jvm:$KryptoVersion")

        api ("com.squareup.sqldelight:sqlite-driver:$SQLDelightVersion")
        //api ("com.squareup.sqldelight:native-driver:$SQLDelightVersion")
        //api ("com.squareup.sqldelight:android-driver:$SQLDelightVersion")

        api(fileTree(mapOf("dir" to "E:/MyNetBeans/Librery/MY_OSHI", "include" to listOf("*.jar"))))
    }

    sourceSets["jvmTest"].dependencies {
        api(kotlin("test"))
        api(kotlin("test-junit"))
    }


    sourceSets["jsMain"].dependencies {
        api(kotlin("stdlib-js", kotlinVersion))
        api("io.ktor:ktor-utils-js:$ktorVersion")
        api("io.ktor:ktor-io-js:$ktorVersion")
        //api ("io.ktor:ktor-client-websockets-js:$ktorVersion")
        //api ("io.ktor:ktor-network-js:$ktorVersion")
        //api ("io.ktor:ktor-websockets-js:$ktorVersion")

        //api ("suparnatural-kotlin-multiplatform:fs-jvm:$fsVersion")

        //api ("com.badoo.reaktive:reaktive-js:$reactiveVersion")
        //api ("com.badoo.reaktive:reaktive-annotations-js:$reactiveVersion")
        //api ("com.badoo.reaktive:utils-js:$reactiveVersion")

        api("com.soywiz.korlibs.klock:klock-js:$KlockVersion")
        api("com.soywiz.korlibs.korim:korim-js:$KorimVersion")
        api("com.soywiz.korlibs.krypto:krypto-js:$KryptoVersion")

        //api ("com.squareup.sqldelight:sqljs-driver:$SQLDelightVersion")

        //api(fileTree(mapOf("dir" to "E:/MyIntellijIDEA/JSOCKET/src/jsMain/kotlin/js", "include" to listOf("*.js"))))

        //api(npm("@fingerprintjs/fingerprintjs", Fingerprintjs2Version))
        //api(npm("await-signal"))
        //api(npm("text-encoding", TextEncodingVersion))
        //api(npm("alasql", AlaSQLVersion))
        //api(npm("eventemitter3"))
        //api(npm("delay"))

        //api ("org.webjars.npm:macaddress:0.2.9"
        //api(npm("macaddress", "0.2.9"))
    }




    sourceSets["jsTest"].dependencies {
        api ( kotlin("test-js", kotlinVersion))
    }

    /*sourceSets["androidMain"].dependencies {
    api ("io.ktor:ktor-utils-android:$ktorVersion")
    api ("io.ktor:ktor-io-android:$ktorVersion")

    //api ("suparnatural-kotlin-multiplatform:cache-android:$cachingVersion")
    //api ("suparnatural-kotlin-multiplatform:fs-android:$fsVersion")

    api ("com.badoo.reaktive:reaktive-android:$reactiveVersion")
    api ("com.badoo.reaktive:reaktive-annotations-android:$reactiveVersion")
    api ("com.badoo.reaktive:utils-android:$reactiveVersion")

    api ("com.soywiz.korlibs.klock:klock-android:$KlockVersion")
    api ("com.soywiz.korlibs.korim:korim-jwm:$KorimVersion")
    }

    sourceSets["androidTest"].dependencies {
    }*/

/*sourceSets["iosX64Main"].dependencies {
    api ("io.ktor:ktor-utils-iosx64:$ktorVersion")
    api ("io.ktor:ktor-io-iosx64:$ktorVersion")
    //api ("io.ktor:ktor-client-websockets-iosx64:$ktorVersion")
    //api ("io.ktor:ktor-network-iosx64:$ktorVersion")
    //api ("io.ktor:ktor-websockets-iosx64:$ktorVersion")

    api ("suparnatural-kotlin-multiplatform:cache-iosx64:$cachingVersion")
    api ("suparnatural-kotlin-multiplatform:fs-iosx64:$fsVersion")

    //api ("com.badoo.reaktive:reaktive-iosx64:$reactiveVersion")
    //api ("com.badoo.reaktive:reaktive-annotations-iosx64:$reactiveVersion")
    //api ("com.badoo.reaktive:utils-iosx64:$reactiveVersion")

    api "com.squareup.sqldelight:native-driver:$SQLDelightVersion"

    api ("com.soywiz.korlibs.klock:klock-iosx64:$KorlibsKlockVersion")
    api ("com.soywiz.korlibs.korim:korim-iosx64:$KorlibsKorimVersion")
}*/

/*val js = js("js").compilations.all {
    kotlinOptions.sourceMap = true
    kotlinOptions.outputFile = "out/js/packages/${project.name}/lib/${project.name}.js"
    kotlinOptions.main = "noCall"
    kotlinOptions.metaInfo = true
    kotlinOptions.sourceMapEmbedSources = "always"
    //kotlinOptions.noStdlib = false
    kotlinOptions.moduleKind = "amd"
    kotlinOptions.friendModulesDisabled = true
}
val iosX64 = iosX64("iosX64")

configure(listOf(iosX64)) {
    binaries.staticLib()
}

sourceSets {
    val commonMain by getting {
        kotlin.srcDir("src/commonMain/kotlin")
        resources.srcDir("src/commonMain/resources")
           dependencies {
            api ( kotlin("stdlib-jdk8", kotlinVersion))
            api ( kotlin("stdlib-common", kotlinVersion))
            api ("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

            api ("io.ktor:ktor-utils:$ktorVersion")
            api ("io.ktor:ktor-io:$ktorVersion")
            api ("io.ktor:ktor-network:$ktorVersion")
            api ("io.ktor:ktor-websockets:$ktorVersion")

            api ("com.soywiz.korlibs.klock:klock:$KorlibsKlockVersion")
            api ("com.soywiz.korlibs.korim:korim:$KorlibsKorimVersion")

            api ("suparnatural-kotlin-multiplatform:cache-metadata:$cachingVersion")
            api ("suparnatural-kotlin-multiplatform:fs-metadata:$fsVersion")

            api ("com.badoo.reaktive:reaktive:$reactiveVersion")
            api ("com.badoo.reaktive:reaktive-annotations:$reactiveVersion")
            api ("com.badoo.reaktive:utils:$reactiveVersion")

        }
    }
    val commonTest by getting {
        kotlin.srcDir("src/commonTets/kotlin")
        resources.srcDir("src/commonTest/resources")
        dependsOn(getByName("commonMain"))
        dependencies {
            api ( kotlin("test-common", kotlinVersion))
            api ( kotlin("test-annotations-common", kotlinVersion))
        }
    }
    val jvmMain by getting {
        kotlin.srcDir("src/jvmMain/kotlin")
        resources.srcDir("src/jwmMain/resources")
        //dependsOn(getByName("commonMain"))
        dependencies {
            api ( kotlin("stdlib-jdk8", kotlinVersion))
            api ("io.ktor:ktor-utils-jvm:$ktorVersion")
            api ("io.ktor:ktor-io-jvm:$ktorVersion")
            //api ("io.ktor:ktor-network-jwm:$ktorVersion")
            //api ("io.ktor:ktor-websockets-jwm:$ktorVersion")

            api ("suparnatural-kotlin-multiplatform:fs-jvm:$fsVersion")

            api ("com.badoo.reaktive:reaktive-jvm:$reactiveVersion")
            api ("com.badoo.reaktive:reaktive-annotations-jvm:$reactiveVersion")
            api ("com.badoo.reaktive:utils-jvm:$reactiveVersion")

            api ("com.soywiz.korlibs.klock:klock-jvm:$KorlibsKlockVersion")
            api ("com.soywiz.korlibs.korim:korim-jvm:$KorlibsKorimVersion")

            api(fileTree(mapOf("dir" to "E:/MyNetBeans/Librery/MY_OSHI", "include" to listOf("*.jar"))))
        }
    }
    val jvmTest by getting {
        kotlin.srcDir("src/jvmTest/kotlin")
        resources.srcDir("src/jwmTest/resources")
        dependsOn(getByName("jvmMain"))
        dependencies {
            api(kotlin("test"))
            api(kotlin("test-junit"))
        }
    }
    val jsMain by getting {
        kotlin.srcDir("src/jsMain/kotlin")
        resources.srcDir("src/jsMain/resources")
        //dependsOn(getByName("commonMain"))
        dependencies {
            api ( kotlin("stdlib-js", kotlinVersion))
            api ("io.ktor:ktor-utils-js:$ktorVersion")
            api ("io.ktor:ktor-io-js:$ktorVersion")
            //api ("io.ktor:ktor-network-js:$ktorVersion")
            //api ("io.ktor:ktor-websockets-js:$ktorVersion")

            //api ("suparnatural-kotlin-multiplatform:fs-jvm:$fsVersion")

            api ("com.badoo.reaktive:reaktive-js:$reactiveVersion")
            api ("com.badoo.reaktive:reaktive-annotations-js:$reactiveVersion")
            api ("com.badoo.reaktive:utils-js:$reactiveVersion")

            api ("com.soywiz.korlibs.klock:klock-js:$KorlibsKlockVersion")
            api ("com.soywiz.korlibs.korim:korim-js:$KorlibsKorimVersion")

            //api(npm("fingerprintjs2", "2.1.0"))

            //api ("org.webjars.npm:macaddress:0.2.9"
            //api(npm("macaddress", "0.2.9"))
        }
    }
    val jsTest by getting {
        kotlin.srcDir("src/jsTest/kotlin")
        resources.srcDir("src/jsTest/resources")
        dependsOn(getByName("jsMain"))
        dependencies {
            api ( kotlin("test-js", kotlinVersion))
        }
    }

    val iosX64Main by getting {
        kotlin.srcDir("src/iosX64Main/kotlin")
        resources.srcDir("src/iosX64Main/resources")
        //dependsOn(getByName("commonMain"))
        dependencies {
            api ("io.ktor:ktor-utils-iosx64:$ktorVersion")
            api ("io.ktor:ktor-io-iosx64:$ktorVersion")
            //api ("io.ktor:ktor-network-iosx64:$ktorVersion")
            //api ("io.ktor:ktor-websockets-iosx64:$ktorVersion")

            api ("suparnatural-kotlin-multiplatform:cache-iosx64:$cachingVersion")
            api ("suparnatural-kotlin-multiplatform:fs-iosx64:$fsVersion")

            //api ("com.badoo.reaktive:reaktive-iosx64:$reactiveVersion")
            //api ("com.badoo.reaktive:reaktive-annotations-iosx64:$reactiveVersion")
            //api ("com.badoo.reaktive:utils-iosx64:$reactiveVersion")

            api ("com.soywiz.korlibs.klock:klock-iosx64:$KorlibsKlockVersion")
            api ("com.soywiz.korlibs.korim:korim-iosx64:$KorlibsKorimVersion")


        }
    }
    val iosX64Test by getting {
        kotlin.srcDir("src/iosX4Test/kotlin")
        resources.srcDir("src/iosX64Test/resources")
        dependsOn(getByName("iosX64Main"))
        /* ... */
    }
    val androidMain by creating {
        kotlin.srcDir("src/androidMain/kotlin")
        resources.srcDir("src/androidMain/resources")
        //dependsOn(getByName("commonMain"))
        dependencies {
            api ("io.ktor:ktor-utils-jvm:$ktorVersion")
            api ("io.ktor:ktor-io-jvm:$ktorVersion")

            api ("suparnatural-kotlin-multiplatform:cache-android:$cachingVersion")
            api ("suparnatural-kotlin-multiplatform:fs-android:$fsVersion")

            api ("com.badoo.reaktive:reaktive-android:$reactiveVersion")
            api ("com.badoo.reaktive:reaktive-annotations-android:$reactiveVersion")
            api ("com.badoo.reaktive:utils-android:$reactiveVersion")

            api ("com.soywiz.korlibs.klock:klock-jvm:$KorlibsKlockVersion")
            api ("com.soywiz.korlibs.korim:korim-jvm:$KorlibsKorimVersion")

        }
    }
    val androidTest by creating {
        kotlin.srcDir("src/androidTest/kotlin")
        resources.srcDir("src/androidTest/resources")
        dependsOn(getByName("androidMain"))
    }

    jvm.compilations["main"].defaultSourceSet {
        //dependsOn(jvmMain)
    }


}*/

}

