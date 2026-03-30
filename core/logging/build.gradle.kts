plugins {
    alias(libs.plugins.cvshowcase.library)
}

android {
    namespace = "$applicationPackage.$coreModuleName.$coreLoggingModuleName"

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
