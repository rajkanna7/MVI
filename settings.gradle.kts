dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://jitpack.io")
    }
}

rootProject.name = "MVI"

include(":sample")
include(":mvi-test")
include(":mvi-core")
include(":mvi-compose")
include(":reaction-popup")

include(":clickeffect")
include(":searchbar")
include(":snackbar")
include(":dialog")
include(":datepicker")
include(":timepicker")
include(":imagepicker")
include(":bottombar")
include(":bottomsheet")
include(":exoplayer-view")
