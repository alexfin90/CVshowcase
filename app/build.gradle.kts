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
    //App dependencies
    implementation(project(":$coreModuleName:$designSystemModuleName"))
    implementation(project(":$coreModuleName:$commonModuleName"))
    //Feature module dependencies
    implementation(platform(libs.firebase.bom))
    //Others dependencies
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)
}