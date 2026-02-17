plugins {
    alias(libs.plugins.cvshowcase.library)
}

android {
    namespace = "$applicationPackage.$coreModuleName.$dispatcherModuleName"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}