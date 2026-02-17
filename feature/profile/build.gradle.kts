plugins {
    alias(libs.plugins.cvshowcase.feature)
}

android {
    namespace = "$applicationPackage.$featureModuleName.$profileModuleName"
}

dependencies{
    implementation(project(":$coreModuleName:$coreDomainModuleName"))
}