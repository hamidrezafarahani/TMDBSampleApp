import java.util.Properties
import java.io.FileInputStream

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.JETBRAINS_KOTILN_ANDROID)
    id(Plugins.NAVIGATION_SAFE_ARGS)
    id(Plugins.DAGGER_HILT_ANDROID)
    kotlin("kapt")
}

android {
    namespace = "com.example.tmdbsampleapp"
    compileSdk = Versions.COMPILE_SDK

    val properties = getProperties("${rootDir}/local.properties")

    defaultConfig {
        applicationId = "com.example.tmdbsampleapp"
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = Versions.VERSION_CODE
        versionName = "1.0"

        buildConfigField("String", "API_KEY", "\"${properties.getProperty("tmdb.api_key")}\"")
        buildConfigField("String", "TOKEN", "\"${properties.getProperty("tmdb.token")}\"")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }

        create("develop") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".develop"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

fun getProperties(file: String) = Properties().apply {
    val propertiesFile = File(file)
    if (propertiesFile.isFile) {
        load(FileInputStream(propertiesFile))
    } else {
        throw Exception("file not found or not instance of properties file.")
    }
}

dependencies {

    implementation(Libs.CORE_KTX)
    implementation(Libs.APPCOMPAT)
    implementation(Libs.GOOGLE_MATERIAL)
    implementation(Libs.CONSTRAINT_LAYOUT)
    testImplementation(Libs.JUNIT)
    androidTestImplementation(Libs.EXT_JUNIT)
    androidTestImplementation(Libs.ESPRESSO_CORE)

    implementation(Libs.COROUTINES)

    implementation(Libs.FRAGMENT_KTX)

    implementation(Libs.LIFE_CYCLE_RUN_TIME_KTX)
    implementation(Libs.LIFE_CYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFE_CYCLE_VIEW_MODEL_KTX)
    kapt(Libs.LIFE_CYCLE_COMPILER)

    implementation(Libs.NAVIGATION_FRAGMENT_KTX)
    implementation(Libs.NAVIGATION_UI_KTX)

    implementation(Libs.PAGING_RUN_TIME)

    implementation(Libs.HILT_ANDROID)
    kapt(Libs.HILT_COMPILER)

    implementation(Libs.ROOM_RUN_TIME)
    kapt(Libs.ROOM_COMPILER)
    implementation(Libs.ROOM_PAGING)

    implementation(Libs.GSON)

    implementation(Libs.OK_HTTP)
    implementation(Libs.OK_HTTP_LOGGING_INTERCEPTOR)

    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_CONVERTER_GSON)

    implementation(Libs.GLIDE)

    implementation(Libs.SHIMMER)

    implementation(Libs.TIMBER)

    implementation(Libs.SWIPE_REFRESH_LAYOUT)
}

kapt {
    correctErrorTypes = true
}