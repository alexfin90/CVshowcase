import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppExtension
import com.technogym.android.absolute.strength.CvShowcaseBuildType
import com.technogym.android.absolute.strength.androidMinSdkVersion
import com.technogym.android.absolute.strength.androidTargetSdkVersion
import com.technogym.android.absolute.strength.applicationPackage

import com.technogym.android.absolute.strength.configureKotlinAndroid
import com.technogym.android.absolute.strength.libs
import com.technogym.android.absolute.strength.prettyProjectName
import com.technogym.android.absolute.strength.versionCode
import com.technogym.android.absolute.strength.versionName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies


class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android-application").get().get().pluginId)
                apply(libs.findPlugin("kotlin-android").get().get().pluginId)
                apply(libs.findPlugin("cvshowcase-compose-navigation").get().get().pluginId)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                buildFeatures {
                    buildConfig = true
                }

                defaultConfig {
                    applicationId = project.applicationPackage
                    minSdk = project.androidMinSdkVersion
                    targetSdk = project.androidTargetSdkVersion
                    versionCode = project.versionCode
                    versionName = project.versionName
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    manifestPlaceholders["targetApi"] = project.androidTargetSdkVersion.toString()
                }

                configureBuildTypes()
                configureApkNaming()
            }

            configureIncrementVersionCodeTask()

            dependencies {
                add(
                    "implementation",
                    libs.findLibrary("timber").get().get().toString()
                )
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())

                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())
            }
        }
    }

    private fun ApplicationExtension.configureBuildTypes() {
        buildTypes {
            release {
                applicationIdSuffix = CvShowcaseBuildType.RELEASE.applicationIdSuffix
                isMinifyEnabled = true
                isShrinkResources = true
                isDebuggable = false
                isJniDebuggable = false
                isProfileable = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                signingConfig = signingConfigs.getByName("debug")
            }

            debug {
                applicationIdSuffix = CvShowcaseBuildType.DEBUG.applicationIdSuffix
                isMinifyEnabled = false
                isShrinkResources = false
                isDebuggable = true
                isJniDebuggable = true
            }
        }
    }

    private fun Project.configureApkNaming() {
        extensions.configure<AppExtension> {
            applicationVariants.all {
                val variant = this
                variant.outputs
                    .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                    .forEach { output ->
                        val outputFileName = when (variant.flavorName) {
                            "real" -> "${project.prettyProjectName}_v${project.versionName}_c${project.versionCode}.apk"
                            else -> "${project.prettyProjectName}_v${project.versionName}_c${project.versionCode}_${variant.flavorName}.apk"
                        }
                        output.outputFileName = outputFileName
                    }
            }
        }
    }

    private fun Project.configureIncrementVersionCodeTask() {
        tasks.register("incrementVersionCode") {
            doLast {
                val newVersionCode = project.versionCode + 1
                println("Incrementing version code to $newVersionCode")
                project.versionCode = newVersionCode
            }
        }
    }
}