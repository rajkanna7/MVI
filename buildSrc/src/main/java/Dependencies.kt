import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.kotlin() {
    implementation(Libs.Jetbrains.kotlinStdlib)
}

fun DependencyHandler.baseAndroid() {
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.fragmentKtx)
    implementation(Libs.AndroidX.vmKtx)
    implementation(Libs.AndroidX.lifecycleRuntimeKtx)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.Google.material)
//    implementation(Libs.AndroidX.material3)
}

fun DependencyHandler.dependencyInjection() {
    implementation(Libs.Google.dagger)
    implementation(Libs.Google.hilt)
    kapt(Libs.Google.Annotation.dagger)
    kapt(Libs.Google.Annotation.hilt)
}

fun DependencyHandler.emoji() {
    implementation(Libs.Reaction.emojipopup)
}

fun DependencyHandler.coil() {
    implementation(Libs.Coil.coilCompose)
    implementation(Libs.Coil.coilVideo)
    implementation(Libs.Coil.coilGif)
}

fun DependencyHandler.exoplayer() {
    implementation(Libs.Exoplayer.core)
    implementation(Libs.Exoplayer.playerUi)
    implementation(Libs.Exoplayer.mediasession)
}

fun DependencyHandler.accompanist() {
    implementation(Libs.accompanist.pagger)
    implementation(Libs.accompanist.pagerIndicators)
    implementation(Libs.accompanist.permissions)
    implementation(Libs.accompanist.navigationAnimation)
}
fun DependencyHandler.orbit() {
    implementation(Libs.Orbit.orbitCore)
    implementation(Libs.Orbit.orbitCompose)
    implementation(Libs.Orbit.orbitViewModel)
    implementation(Libs.Orbit.orbitTest)
}

fun DependencyHandler.compose() {
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.uiTooling)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.constraint)
    implementation(Libs.AndroidX.Compose.materialIconsCore)
    implementation(Libs.AndroidX.Compose.materialIconsExtended)
    implementation(Libs.AndroidX.Compose.activity)
    implementation(Libs.AndroidX.Compose.lottie)
    implementation(Libs.AndroidX.Compose.viewModel)
    implementation(Libs.AndroidX.Compose.navigation)
    implementation(Libs.AndroidX.Compose.material3)
    implementation(Libs.AndroidX.Compose.paging)
//    androidTestImplementation(Libs.AndroidX.Compose.AndroidTest.uiTest)
}

fun DependencyHandler.coroutines() {
    implementation(Libs.Jetbrains.Coroutines.core)
    implementation(Libs.Jetbrains.Coroutines.android)
}

fun DependencyHandler.test() {
    testImplementation(Libs.Jetbrains.Coroutines.Test.coroutines)
    testImplementation(Libs.JUnit.jupiter)
    testImplementation(Libs.JUnit.jupiterParams)
    testRuntimeOnly(Libs.JUnit.Runtime.jupiterEngine)
}

fun DependencyHandler.androidTest() {
    androidTestImplementation(Libs.AndroidX.AndroidTest.junit)
    androidTestImplementation(Libs.AndroidX.AndroidTest.espresso)
}
