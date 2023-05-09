object Libs {

    object Plugins {
        const val buildGradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val junit5 =
            "de.mannodermaus.gradle.plugins:android-junit5:${Versions.junit5Gradle}"
        const val ktlintGradle = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger}"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.ktx}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.ktxFragment}"
        const val vmKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ktxViewModel}"
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ktxLifecycleRuntime}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"


        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.composeUI}"
            const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeUI}"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeUI}"
            const val foundation = "androidx.compose.foundation:foundation:${Versions.composeUI}"
            const val material = "androidx.compose.material:material:${Versions.composeUI}"
            const val constraint =
                "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraint}"
            const val materialIconsCore =
                "androidx.compose.material:material-icons-core:${Versions.composeUI}"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:${Versions.composeUI}"

            const val activity = "androidx.activity:activity-compose:${Versions.composeUI}"
            const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
            const val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"
            const val viewModel =
                "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
            const val navigation = "androidx.navigation:navigation-compose:2.5.1"
            val paging by lazy { "androidx.paging:paging-compose:1.0.0-alpha14" }

            object AndroidTest {
                const val uiTest = "androidx.compose.ui:ui-test-junit4:${Versions.composejunit}"
            }
        }

        object AndroidTest {
            const val junit = "androidx.test.ext:junit:${Versions.androidXJunit}"
            const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        }
    }

    object Orbit {
        const val orbitCore = "org.orbit-mvi:orbit-core:${Versions.orbit}"
        const val orbitViewModel = "org.orbit-mvi:orbit-viewmodel:${Versions.orbit}"
        const val orbitCompose = "org.orbit-mvi:orbit-compose:${Versions.orbit}"
        const val orbitTest = "org.orbit-mvi:orbit-test:${Versions.orbit}"
    }

    object Reaction {
        const val emojipopup = "com.vanniktech:emoji-google:${Versions.emoji}"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
        const val coilVideo = "io.coil-kt:coil-video:${Versions.coil}"
        const val coilGif = "io.coil-kt:coil-gif:${Versions.coil}"
    }

    object Exoplayer {
        const val core = "com.google.android.exoplayer:exoplayer-core:${Versions.exoPlayer}"
        const val playerUi = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoPlayer}"
        const val mediasession = "com.google.android.exoplayer:extension-mediasession:${Versions.exoPlayer}"
    }


    object accompanist {
        const val pagger = "com.google.accompanist:accompanist-pager:${Versions.pagger}"
        const val pagerIndicators = "com.google.accompanist:accompanist-pager-indicators:${Versions.pagger}"
        const val navigationAnimation = "com.google.accompanist:accompanist-navigation-animation:${Versions.pagger}"
        const val permissions = "com.google.accompanist:accompanist-permissions:${Versions.pagger}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.dagger}"

        object Annotation {
            const val dagger = "com.google.dagger:dagger-compiler:${Versions.dagger}"
            const val hilt = "com.google.dagger:hilt-compiler:${Versions.dagger}"
        }
    }

    object Jetbrains {
        const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
            const val android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

            object Test {
                const val coroutines =
                    "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
            }
        }
    }

    object JUnit {

        const val jupiter = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}"
        const val jupiterParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit5}"

        object Runtime {
            const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5}"
        }
    }
}