// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version Versions.ANDROID_GRADLE_PLUGIN apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.ANDROID_GRADLE_PLUGIN apply false
    id(Plugins.JETBRAINS_KOTILN_ANDROID) version Versions.KOTLIN_ANDROID apply false
    id(Plugins.NAVIGATION_SAFE_ARGS) version Versions.NAVIGATION apply false
    id(Plugins.DAGGER_HILT_ANDROID) version Versions.HILT apply false
}