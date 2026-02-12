import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.technogym.android.absolute.strength.configureAndroidCompose
import com.technogym.android.absolute.strength.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType

class ComposeNavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                //* Apply the Compose compiler plugin Starting in Kotlin 2.0, the Compose Compiler
                // Gradle plugin is required when compose is enabled
                val composeCompilerPlugin = libs.findPlugin("kotlin.compose")
                composeCompilerPlugin.get().apply {
                    if (isPresent) {
                        apply(this.get().pluginId)
                    } else {
                        logger.warn("The 'org.jetbrains.kotlin.plugin.compose' plugin was not found. Please ensure it is included in the project dependencies.")
                    }
                }
                // Apply Kotlin Serialization plugin
                val serializationPlugin = libs.findPlugin("kotlin.serialization")
                serializationPlugin.get().apply {
                    if (isPresent) {
                        apply(this.get().pluginId)
                    } else {
                        logger.warn("The 'org.jetbrains.kotlin.plugin.serialization' plugin was not found. Please ensure it is included in the project dependencies.")
                    }
                }
            }
            dependencies {

                add(
                    "implementation",
                    platform(project.libs.findLibrary("androidx-compose-bom").get())
                )
                add("implementation", project.libs.findLibrary("androidx-compose-ui").get())
                add(
                    "implementation",
                    project.libs.findLibrary("androidx-compose-ui-graphics").get()
                )
                add(
                    "implementation", project.libs.findLibrary
                        ("androidx-compose-ui-tooling-preview")
                        .get()
                )
                add("implementation", project.libs.findLibrary("androidx-compose-material3").get())
                add("implementation", project.libs.findLibrary("androidx-activity-compose").get())
                add(
                    "implementation",
                    project.libs.findLibrary("androidx-navigation-compose").get().get().toString()
                )
                add(
                    "implementation",
                    project.libs.findLibrary("kotlinx-serialization-json").get().get().toString()
                )
                add(
                    "androidTestImplementation", platform(
                        project.libs.findLibrary
                            ("androidx-compose-bom").get()
                    )
                )
                add(
                    "androidTestImplementation", project.libs.findLibrary
                        ("androidx-compose-ui-test-junit4").get()
                )
                add(
                    "debugImplementation", project.libs.findLibrary
                        ("androidx-compose-ui-tooling").get()
                )
                add(
                    "debugImplementation", project.libs.findLibrary
                        ("androidx-compose-ui-test-manifest")
                        .get()
                )
            }
            val applicationExtension = extensions.findByType<ApplicationExtension>()
            val libraryExtension = extensions.findByType<LibraryExtension>()
            applicationExtension?.let { configureAndroidCompose(it) }
            libraryExtension?.let { configureAndroidCompose(it) }
        }
    }
}