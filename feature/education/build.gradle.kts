plugins {
    alias(libs.plugins.cvshowcase.feature)
}

android {
    namespace = "$applicationPackage.$featureModuleName.$educationModuleName"
}

