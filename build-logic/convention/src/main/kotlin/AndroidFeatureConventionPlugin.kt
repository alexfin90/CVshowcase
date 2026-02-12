import com.android.build.gradle.LibraryExtension
import com.technogym.android.absolute.strength.commonModuleName
import com.technogym.android.absolute.strength.coreModuleName
import com.technogym.android.absolute.strength.designSystemModuleName
import com.technogym.android.absolute.strength.libs

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("cvshowcase-library").get().get().pluginId)
                apply(libs.findPlugin("cvshowcase-compose-navigation").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
            }
            dependencies {
                add(
                    "implementation",
                    project(":${project.coreModuleName}:${project.commonModuleName}")
                )
                add(
                    "implementation",
                    project(":${project.coreModuleName}:${project.designSystemModuleName}")
                )
            }
        }
    }
}

