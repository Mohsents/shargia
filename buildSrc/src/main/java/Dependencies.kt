/**
 * Define libraries dependencies.
 */
object Dependencies {

    object Plugins {
        const val AGP = "com.android.tools.build:gradle:${Versions.AGP}"
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    }

    object Libs {
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val LIBSU = "com.github.topjohnwu.libsu:core:${Versions.LIBSU}"
    }
}