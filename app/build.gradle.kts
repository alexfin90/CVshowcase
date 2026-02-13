plugins {
    alias(libs.plugins.cvshowcase.application)
    alias(libs.plugins.cvshowcase.flavors)
}

android {
    namespace = project.applicationPackage
}
