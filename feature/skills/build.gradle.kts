plugins {
    alias(libs.plugins.cvshowcase.feature)
}

android {
    namespace = "$applicationPackage.$featureModuleName.$skillsModuleName"
}

dependencies{
    implementation(project(":$coreModuleName:$coreDomainModuleName"))
}