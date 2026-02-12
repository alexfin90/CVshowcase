plugins {
    alias(libs.plugins.cvshowcase.library)
    alias(libs.plugins.cvshowcase.compose.navigation)
}

android {
    namespace = "$applicationPackage.$coreModuleName.$designSystemModuleName"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}