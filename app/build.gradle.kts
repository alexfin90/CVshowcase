plugins {
    alias(libs.plugins.cvshowcase.application)
    alias(libs.plugins.cvshowcase.flavors)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = project.applicationPackage
}


dependencies{
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)
}