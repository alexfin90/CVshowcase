
import com.technogym.android.absolute.strength.configureKotlinJvm
import com.technogym.android.absolute.strength.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlin-jvm").get().get().pluginId)
            }
            configureKotlinJvm()
            dependencies {
                "testImplementation"(libs.findLibrary("kotlin-test-junit").get())
            }
        }
    }
}
