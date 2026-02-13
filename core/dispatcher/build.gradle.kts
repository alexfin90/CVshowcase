plugins {
    alias(libs.plugins.cvshowcase.library)
}

android {
    namespace = "$applicationPackage.$coreModuleName.$dispatcherModuleName"
}