buildscript {

    dependencies {
        classpath("com.google.gms:google-services:4.3.13")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    }
}

plugins {
    kotlin("android") version "1.6.10" apply false
    id("com.android.application") version "7.1.1" apply false
    id("com.android.library") version "7.1.1" apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}