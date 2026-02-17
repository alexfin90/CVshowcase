plugins {
    alias(libs.plugins.cvshowcase.feature)
}

android {
    namespace = "$applicationPackage.$featureModuleName.$educationModuleName"
}


dependencies{
    implementation(project(":$coreModuleName:$coreDomainModuleName"))
}

