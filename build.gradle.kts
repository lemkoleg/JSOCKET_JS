val kotlinVersion = "1.6.10"
val ktorVersion = "1.6.7"
val reactiveVersion = "1.2.1"
val KlockVersion = "2.2.2"
val KorimVersion = "2.2.2"
val KorioVersion = "2.2.1"
val KryptoVersion = "2.2.0"
val SQLDelightVersion = "1.5.3"
val AlaSQLVersion = "1.7.2"
val TextEncodingVersion = "0.7.0"
val Fingerprintjs2Version = "2.1.4"

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.6.10"
    id("com.squareup.sqldelight")  version "1.5.1"
}

buildscript {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
    }
    dependencies {
        classpath ("com.squareup.sqldelight:gradle-plugin:1.5.1")
    }


}

//apply(plugin = "com.squareup.sqldelight")


allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
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
    jvm("jvm") {
        withJava()
        compilations["main"].kotlinOptions.jvmTarget = "11"
    }

    js("js") {
        browser {

        }

        //nodejs {
        //}
    }

    sqldelight {
        database("AvaClubDB") {
            packageName = "JSOCKET"
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
        implementation ( kotlin("stdlib-jdk8", kotlinVersion))
        implementation ( kotlin("stdlib-common", kotlinVersion))
        implementation ("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

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

    }

    sourceSets["commonTest"].dependencies {
        implementation ( kotlin("test-common", kotlinVersion))
        implementation ( kotlin("test-annotations-common", kotlinVersion))
    }

    sourceSets["jvmMain"].dependencies {
        implementation ( kotlin("stdlib-jdk8", kotlinVersion))
        implementation ("io.ktor:ktor-utils-jvm:$ktorVersion")
        implementation ("io.ktor:ktor-io-jvm:$ktorVersion")
        //implementation ("io.ktor:ktor-client-websockets-jvm:$ktorVersion")
        //implementation ("io.ktor:ktor-network-jwm:$ktorVersion")
        //implementation ("io.ktor:ktor-websockets-jwm:$ktorVersion")

        //implementation ("suparnatural-kotlin-multiplatform:fs-jvm:$fsVersion")

        implementation ("com.badoo.reaktive:reaktive-jvm:$reactiveVersion")
        implementation ("com.badoo.reaktive:reaktive-annotations-jvm:$reactiveVersion")
        implementation ("com.badoo.reaktive:utils-jvm:$reactiveVersion")

        implementation ("com.soywiz.korlibs.klock:klock-jvm:$KlockVersion")
        implementation ("com.soywiz.korlibs.korim:korim-jvm:$KorimVersion")
        implementation ("com.soywiz.korlibs.krypto:krypto-jvm:$KryptoVersion")

        implementation ("com.squareup.sqldelight:sqlite-driver:$SQLDelightVersion")
        //implementation ("com.squareup.sqldelight:native-driver:$SQLDelightVersion")
        //implementation ("com.squareup.sqldelight:android-driver:$SQLDelightVersion")

        implementation(fileTree(mapOf("dir" to "E:/MyNetBeans/Librery/MY_OSHI", "include" to listOf("*.jar"))))
    }

    sourceSets["jvmTest"].dependencies {
        implementation(kotlin("test"))
        implementation(kotlin("test-junit"))
    }


    sourceSets["jsMain"].dependencies {
        implementation(kotlin("stdlib-js", kotlinVersion))
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

        //implementation(npm("@fingerprintjs/fingerprintjs", Fingerprintjs2Version))
        //implementation(npm("await-signal"))
        //implementation(npm("text-encoding", TextEncodingVersion))
        //implementation(npm("alasql", AlaSQLVersion))
        //implementation(npm("eventemitter3"))
        //implementation(npm("delay"))

        //implementation ("org.webjars.npm:macaddress:0.2.9"
        //implementation(npm("macaddress", "0.2.9"))
    }




    sourceSets["jsTest"].dependencies {
        implementation ( kotlin("test-js", kotlinVersion))
    }

    /*sourceSets["androidMain"].dependencies {
    implementation ("io.ktor:ktor-utils-android:$ktorVersion")
    implementation ("io.ktor:ktor-io-android:$ktorVersion")

    //implementation ("suparnatural-kotlin-multiplatform:cache-android:$cachingVersion")
    //implementation ("suparnatural-kotlin-multiplatform:fs-android:$fsVersion")

    implementation ("com.badoo.reaktive:reaktive-android:$reactiveVersion")
    implementation ("com.badoo.reaktive:reaktive-annotations-android:$reactiveVersion")
    implementation ("com.badoo.reaktive:utils-android:$reactiveVersion")

    implementation ("com.soywiz.korlibs.klock:klock-android:$KlockVersion")
    implementation ("com.soywiz.korlibs.korim:korim-jwm:$KorimVersion")
    }

    sourceSets["androidTest"].dependencies {
    }*/

/*sourceSets["iosX64Main"].dependencies {
    implementation ("io.ktor:ktor-utils-iosx64:$ktorVersion")
    implementation ("io.ktor:ktor-io-iosx64:$ktorVersion")
    //implementation ("io.ktor:ktor-client-websockets-iosx64:$ktorVersion")
    //implementation ("io.ktor:ktor-network-iosx64:$ktorVersion")
    //implementation ("io.ktor:ktor-websockets-iosx64:$ktorVersion")

    implementation ("suparnatural-kotlin-multiplatform:cache-iosx64:$cachingVersion")
    implementation ("suparnatural-kotlin-multiplatform:fs-iosx64:$fsVersion")

    //implementation ("com.badoo.reaktive:reaktive-iosx64:$reactiveVersion")
    //implementation ("com.badoo.reaktive:reaktive-annotations-iosx64:$reactiveVersion")
    //implementation ("com.badoo.reaktive:utils-iosx64:$reactiveVersion")

    implementation "com.squareup.sqldelight:native-driver:$SQLDelightVersion"

    implementation ("com.soywiz.korlibs.klock:klock-iosx64:$KorlibsKlockVersion")
    implementation ("com.soywiz.korlibs.korim:korim-iosx64:$KorlibsKorimVersion")
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
            implementation ( kotlin("stdlib-jdk8", kotlinVersion))
            implementation ( kotlin("stdlib-common", kotlinVersion))
            implementation ("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

            implementation ("io.ktor:ktor-utils:$ktorVersion")
            implementation ("io.ktor:ktor-io:$ktorVersion")
            implementation ("io.ktor:ktor-network:$ktorVersion")
            implementation ("io.ktor:ktor-websockets:$ktorVersion")

            implementation ("com.soywiz.korlibs.klock:klock:$KorlibsKlockVersion")
            implementation ("com.soywiz.korlibs.korim:korim:$KorlibsKorimVersion")

            implementation ("suparnatural-kotlin-multiplatform:cache-metadata:$cachingVersion")
            implementation ("suparnatural-kotlin-multiplatform:fs-metadata:$fsVersion")

            implementation ("com.badoo.reaktive:reaktive:$reactiveVersion")
            implementation ("com.badoo.reaktive:reaktive-annotations:$reactiveVersion")
            implementation ("com.badoo.reaktive:utils:$reactiveVersion")

        }
    }
    val commonTest by getting {
        kotlin.srcDir("src/commonTets/kotlin")
        resources.srcDir("src/commonTest/resources")
        dependsOn(getByName("commonMain"))
        dependencies {
            implementation ( kotlin("test-common", kotlinVersion))
            implementation ( kotlin("test-annotations-common", kotlinVersion))
        }
    }
    val jvmMain by getting {
        kotlin.srcDir("src/jvmMain/kotlin")
        resources.srcDir("src/jwmMain/resources")
        //dependsOn(getByName("commonMain"))
        dependencies {
            implementation ( kotlin("stdlib-jdk8", kotlinVersion))
            implementation ("io.ktor:ktor-utils-jvm:$ktorVersion")
            implementation ("io.ktor:ktor-io-jvm:$ktorVersion")
            //implementation ("io.ktor:ktor-network-jwm:$ktorVersion")
            //implementation ("io.ktor:ktor-websockets-jwm:$ktorVersion")

            implementation ("suparnatural-kotlin-multiplatform:fs-jvm:$fsVersion")

            implementation ("com.badoo.reaktive:reaktive-jvm:$reactiveVersion")
            implementation ("com.badoo.reaktive:reaktive-annotations-jvm:$reactiveVersion")
            implementation ("com.badoo.reaktive:utils-jvm:$reactiveVersion")

            implementation ("com.soywiz.korlibs.klock:klock-jvm:$KorlibsKlockVersion")
            implementation ("com.soywiz.korlibs.korim:korim-jvm:$KorlibsKorimVersion")

            implementation(fileTree(mapOf("dir" to "E:/MyNetBeans/Librery/MY_OSHI", "include" to listOf("*.jar"))))
        }
    }
    val jvmTest by getting {
        kotlin.srcDir("src/jvmTest/kotlin")
        resources.srcDir("src/jwmTest/resources")
        dependsOn(getByName("jvmMain"))
        dependencies {
            implementation(kotlin("test"))
            implementation(kotlin("test-junit"))
        }
    }
    val jsMain by getting {
        kotlin.srcDir("src/jsMain/kotlin")
        resources.srcDir("src/jsMain/resources")
        //dependsOn(getByName("commonMain"))
        dependencies {
            implementation ( kotlin("stdlib-js", kotlinVersion))
            implementation ("io.ktor:ktor-utils-js:$ktorVersion")
            implementation ("io.ktor:ktor-io-js:$ktorVersion")
            //implementation ("io.ktor:ktor-network-js:$ktorVersion")
            //implementation ("io.ktor:ktor-websockets-js:$ktorVersion")

            //implementation ("suparnatural-kotlin-multiplatform:fs-jvm:$fsVersion")

            implementation ("com.badoo.reaktive:reaktive-js:$reactiveVersion")
            implementation ("com.badoo.reaktive:reaktive-annotations-js:$reactiveVersion")
            implementation ("com.badoo.reaktive:utils-js:$reactiveVersion")

            implementation ("com.soywiz.korlibs.klock:klock-js:$KorlibsKlockVersion")
            implementation ("com.soywiz.korlibs.korim:korim-js:$KorlibsKorimVersion")

            //implementation(npm("fingerprintjs2", "2.1.0"))

            //implementation ("org.webjars.npm:macaddress:0.2.9"
            //implementation(npm("macaddress", "0.2.9"))
        }
    }
    val jsTest by getting {
        kotlin.srcDir("src/jsTest/kotlin")
        resources.srcDir("src/jsTest/resources")
        dependsOn(getByName("jsMain"))
        dependencies {
            implementation ( kotlin("test-js", kotlinVersion))
        }
    }

    val iosX64Main by getting {
        kotlin.srcDir("src/iosX64Main/kotlin")
        resources.srcDir("src/iosX64Main/resources")
        //dependsOn(getByName("commonMain"))
        dependencies {
            implementation ("io.ktor:ktor-utils-iosx64:$ktorVersion")
            implementation ("io.ktor:ktor-io-iosx64:$ktorVersion")
            //implementation ("io.ktor:ktor-network-iosx64:$ktorVersion")
            //implementation ("io.ktor:ktor-websockets-iosx64:$ktorVersion")

            implementation ("suparnatural-kotlin-multiplatform:cache-iosx64:$cachingVersion")
            implementation ("suparnatural-kotlin-multiplatform:fs-iosx64:$fsVersion")

            //implementation ("com.badoo.reaktive:reaktive-iosx64:$reactiveVersion")
            //implementation ("com.badoo.reaktive:reaktive-annotations-iosx64:$reactiveVersion")
            //implementation ("com.badoo.reaktive:utils-iosx64:$reactiveVersion")

            implementation ("com.soywiz.korlibs.klock:klock-iosx64:$KorlibsKlockVersion")
            implementation ("com.soywiz.korlibs.korim:korim-iosx64:$KorlibsKorimVersion")


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
            implementation ("io.ktor:ktor-utils-jvm:$ktorVersion")
            implementation ("io.ktor:ktor-io-jvm:$ktorVersion")

            implementation ("suparnatural-kotlin-multiplatform:cache-android:$cachingVersion")
            implementation ("suparnatural-kotlin-multiplatform:fs-android:$fsVersion")

            implementation ("com.badoo.reaktive:reaktive-android:$reactiveVersion")
            implementation ("com.badoo.reaktive:reaktive-annotations-android:$reactiveVersion")
            implementation ("com.badoo.reaktive:utils-android:$reactiveVersion")

            implementation ("com.soywiz.korlibs.klock:klock-jvm:$KorlibsKlockVersion")
            implementation ("com.soywiz.korlibs.korim:korim-jvm:$KorlibsKorimVersion")

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

