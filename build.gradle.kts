// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.ANDROID_GRADLE_PLUGIN apply false
    id("com.android.library") version Versions.ANDROID_GRADLE_PLUGIN apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN_ANDROID apply false
    id("androidx.navigation.safeargs.kotlin") version Versions.NAVIGATION apply false
    id("com.google.dagger.hilt.android") version Versions.HILT apply false
}