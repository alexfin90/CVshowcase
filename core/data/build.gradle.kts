import com.technogym.android.absolute.strength.coreDomainModuleName

plugins {
    alias(libs.plugins.cvshowcase.library)
}

android {
    namespace = "$applicationPackage.$coreModuleName.$coreDataModuleName"
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

dependencies{
    implementation(project(":$coreModuleName:$commonModuleName"))
    implementation(project(":$coreModuleName:$coreDomainModuleName"))
}

