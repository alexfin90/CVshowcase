import com.technogym.android.absolute.strength.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("ksp").get().get().pluginId)
                apply(libs.findPlugin("hilt-android").get().get().pluginId)
            }
            dependencies {
                add("implementation", project.libs.findLibrary("hilt-android").get().get().toString())
                add("ksp", project.libs.findLibrary("hilt-compiler").get().get().toString())
                add("implementation", project.libs.findLibrary("androidx-hilt-navigation-compose").get().get().toString())
            }
        }
    }
}