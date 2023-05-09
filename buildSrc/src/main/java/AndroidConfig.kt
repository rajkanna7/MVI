import org.gradle.api.JavaVersion

object AndroidConfig {

    object Plugin {
        const val androidApp = "com.android.application"
        const val androidLib = "com.android.library"
        const val kotlinAndroid = "kotlin-android"
        const val kapt = "kotlin-kapt"
        const val javaLib = "java-library"
        const val kotlin = "kotlin"
        const val junit5 = "de.mannodermaus.android-junit5"
        const val ktlint = "org.jlleitschuh.gradle.ktlint"
        const val hilt = "dagger.hilt.android.plugin"
    }

    const val sdkVersion = 33
    const val minSdkVersion = 26
    const val appId = "com.rk.sample"
    val javaVersion = JavaVersion.VERSION_11
}
