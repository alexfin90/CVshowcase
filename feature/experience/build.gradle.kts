plugins {
    alias(libs.plugins.cvshowcase.feature)
}

android {
    namespace = "$applicationPackage.$featureModuleName.$experienceModuleName"
}

dependencies{
    implementation(project(":$coreModuleName:$coreDomainModuleName"))
}