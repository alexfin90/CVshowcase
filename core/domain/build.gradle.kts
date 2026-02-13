plugins {
    alias(libs.plugins.cvshowcase.jvm.library)
}
dependencies {
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)
    //Test
    testImplementation(libs.kotlinx.coroutines.test)
}