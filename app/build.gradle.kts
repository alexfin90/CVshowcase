plugins {
    alias(libs.plugins.cvshowcase.application)
    alias(libs.plugins.cvshowcase.flavors)
    alias(libs.plugins.google.services)
}

android {
    namespace = project.applicationPackage
}


dependencies{
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}