import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.alex90fin.build_logic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("hilt") {
            id = libs.plugins.cvshowcase.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
        register("composeNavigation") {
            id = libs.plugins.cvshowcase.compose.navigation.get().pluginId
            implementationClass = "ComposeNavigationConventionPlugin"
        }
        register("flavors") {
            id = libs.plugins.cvshowcase.flavors.get().pluginId
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.cvshowcase.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("featureModule"){
            id = libs.plugins.cvshowcase.feature.get().pluginId
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("application") {
            id = libs.plugins.cvshowcase.application.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.cvshowcase.jvm.library.get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}


