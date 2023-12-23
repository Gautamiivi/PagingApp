// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
}

buildscript {
    repositories{
        google()
        mavenCentral()
    }

    dependencies {
         classpath ("com.google.dagger:hilt-android-gradle-plugin:2.41")
    }
}


