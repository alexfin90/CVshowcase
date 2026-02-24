import com.technogym.android.absolute.strength.coreDomainModuleName

plugins {
    alias(libs.plugins.cvshowcase.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "$applicationPackage.$coreModuleName.$coreDataModuleName"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
        //BASE_URL
        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://cvshowcase-c7ebb.web.app/\""
        )
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
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

}

