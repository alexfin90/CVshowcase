plugins {
    alias(libs.plugins.cvshowcase.feature)
}

android {
    namespace = "$applicationPackage.$featureModuleName.$detailExperienceModuleName"
}

dependencies{
    implementation(project(":$coreModuleName:$coreDomainModuleName"))
}