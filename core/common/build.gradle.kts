plugins {
    alias(libs.plugins.cvshowcase.library)
}

android {
    namespace = "$applicationPackage.$coreModuleName.$commonModuleName"
}

dependencies{
    api(project(":$coreModuleName:$dispatcherModuleName"))
}