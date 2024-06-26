plugins {
    id("com.android.library")
    id("kotlin-android")
}


android {
    namespace = "com.rey.lib.cleanarch"
    compileSdk = 34

    defaultConfig {
        minSdk = 16
//        versionCode = 15
//        versionName = "0.8.0"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }

//    testOptions {
//        unitTests.all {
//            testLogging.events = listOf("passed", "skipped", "failed")
//        }
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.20")
    api("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    api("androidx.core:core-ktx:1.12.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    api("com.google.code.gson:gson:2.10.1")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("androidx.security:security-crypto:1.0.0")

    testImplementation("com.google.truth:truth:1.1.4")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.19")
    testImplementation("org.spekframework.spek2:spek-runner-junit5:2.0.19")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
}