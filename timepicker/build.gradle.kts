plugins {
    id("android-library-plugin")
    id ("kotlin-parcelize")
}


android {
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = Versions.compose
}

dependencies {
    implementation(project(":mvi-core"))

    kotlin()
    baseAndroid()
    compose()
    coroutines()
//    page()
}