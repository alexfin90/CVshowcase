import org.gradle.api.Project

val Project.androidCompileSdkVersion: Int
    get() = (findProperty("androidCompileSdkVersion") as? String)?.toInt()
        ?: error("Property 'androidCompileSdkVersion' not found")

val Project.androidTargetSdkVersion: Int
    get() = (findProperty("androidTargetSdkVersion") as? String)?.toInt()
        ?: error("Property 'androidTargetSdkVersion' not found")

val Project.androidMinSdkVersion: Int
    get() = (findProperty("androidMinSdkVersion") as? String)?.toInt()
        ?: error("Property 'androidMinSdkVersion' not found")

val Project.applicationPackage: String
    get() = findProperty("applicationPackage") as? String
        ?: error("Property 'applicationPackage' not found")

val Project.prettyProjectName: String
    get() = findProperty("prettyProjectName") as? String
        ?: error("Property 'prettyProjectName' not found")

val Project.versionName: String
    get() = findProperty("versionName") as? String
        ?: error("Property 'versionName' not found")

val Project.projectJavaVersion: String
    get() = findProperty("projectJavaVersion") as? String
        ?: error("Property 'projectJavaVersion' not found")

var Project.versionCode: Int
    get() = (findProperty("versionCode") as? String)?.toInt()
        ?: error("Property 'versionCode' not found")
    set(value) {
        setProperty("versionCode", value.toString())
        val propertiesFile = file("${rootDir}/gradle.properties")
        val fileContent = propertiesFile.readText()
        val updatedContent = fileContent.replace(Regex("(?m)^(versionCode\\s*=\\s*).*$")) { matchResult ->
            "${matchResult.groupValues[1]}$value"
        }
        propertiesFile.writeText(updatedContent)
    }

val Project.coreModuleName: String
    get() = findProperty("coreModuleName") as? String
        ?: error("Property 'coreModuleName' not found")

val Project.commonModuleName: String
    get() = findProperty("commonModuleName") as? String
        ?: error("Property 'commonModuleName' not found")

val Project.designSystemModuleName: String
    get() = findProperty("designSystemModuleName") as? String
        ?: error("Property 'designSystemModuleName' not found")

val Project.featureModuleName: String
    get() = findProperty("featureModuleName") as? String
        ?: error("Property 'featureModuleName' not found")

val Project.educationModuleName: String
    get() = findProperty("educationModuleName") as? String
        ?: error("Property 'educationModuleName' not found")

val Project.profileModuleName: String
    get() = findProperty("profileModuleName") as? String
        ?: error("Property 'profileModuleName' not found")

val Project.skillsModuleName: String
    get() = findProperty("skillsModuleName") as? String
        ?: error("Property 'skillsModuleName' not found")

val Project.experienceModuleName: String
    get() = findProperty("experienceModuleName") as? String
        ?: error("Property 'experienceModuleName' not found")