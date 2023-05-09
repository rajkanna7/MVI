plugins {
    id("android-application-plugin")
    id ("kotlin-parcelize")
}

android {
    defaultConfig {
        applicationId ="com.rk.sample"
    }
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = Versions.compose
}

dependencies {
    implementation(project(":mvi-core"))
    implementation(project(":mvi-compose"))
    implementation(project(":reaction-popup"))
    implementation(project(":clickeffect"))
    implementation(project(":searchbar"))
    implementation(project(":snackbar"))
    implementation(project(":dialog"))
    implementation(project(":datepicker"))
    implementation(project(":timepicker"))
    implementation(project(":imagepicker"))
    implementation(project(":bottombar"))
    implementation(project(":bottomsheet"))
    implementation("androidx.test:monitor:1.6.1")
//    implementation ("io.github.huhx:compose-image-picker:1.0.8")
    kotlin()
    dependencyInjection()
    baseAndroid()
    coroutines()
    compose()
    orbit()
    emoji()
    accompanist()
    test()
    testImplementation(project(":mvi-test"))
}
