buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Plugins.AGP)
        classpath(Dependencies.Plugins.KOTLIN)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

tasks.create("clean", Delete::class) {
    delete(rootProject.buildDir)
}