import com.android.build.gradle.LibraryExtension
import com.technogym.android.absolute.strength.configureFlavors
import com.technogym.android.absolute.strength.configureKotlinAndroid
import com.technogym.android.absolute.strength.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies


class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android-library").get().get().pluginId)
                apply(libs.findPlugin("kotlin-android").get().get().pluginId)
                apply(libs.findPlugin("cvshowcase-hilt").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true
                configureFlavors(this)
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("timber").get())

                add("testImplementation", libs.findLibrary("junit").get())

                add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())

            }
        }
    }
}

