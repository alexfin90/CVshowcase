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

val Project.coreDataModuleName: String
    get() = findProperty("coreDataModuleName") as? String
        ?: error("Property 'coreDataModuleName' not found")

val Project.coreDomainModuleName: String
    get() = findProperty("coreDomainModuleName") as? String
        ?: error("Property 'coreDomainModuleName' not found")

val Project.commonModuleName: String
    get() = findProperty("commonModuleName") as? String
        ?: error("Property 'commonModuleName' not found")

val Project.uiCommonModuleName: String
    get() = findProperty("uiCommonModuleName") as? String
        ?: error("Property 'uiCommonModuleName' not found")

val Project.dashboardModuleName: String
    get() = findProperty("dashboardModuleName") as? String
        ?: error("Property 'dashboardModuleName' not found")

val Project.dashboardDataModuleName: String
    get() = findProperty("dashboardDataModuleName") as? String
        ?: error("Property 'dashboardDataModuleName' not found")

val Project.dashboardDomainModuleName: String
    get() = findProperty("dashboardDomainModuleName") as? String
        ?: error("Property 'dashboardDomainModuleName' not found")

val Project.homepageModuleName: String
    get() = findProperty("homepageModuleName") as? String
        ?: error("Property 'homepageModuleName' not found")



val Project.splashModuleName: String
    get() = findProperty("splashModuleName") as? String
        ?: error("Property 'splashModuleName' not found")

val Project.setupWorkoutModuleName: String
    get() = findProperty("setupWorkoutModuleName") as? String
        ?: error("Property 'setupWorkoutModuleName' not found")

val Project.exercisesModuleName: String
    get() = findProperty("exercisesModuleName") as? String
        ?: error("Property 'exercisesModuleName' not found")

val Project.biofeedbackModuleName: String
    get() = findProperty("biofeedbackModuleName") as? String
        ?: error("Property 'biofeedbackModuleName' not found")

val Project.restModuleName: String
    get() = findProperty("restModuleName") as? String
        ?: error("Property 'restModuleName' not found")

val Project.resultsModuleName: String
    get() = findProperty("resultsModuleName") as? String
        ?: error("Property 'resultsModuleName' not found")

val Project.trainingModuleName: String
    get() = findProperty("trainingModuleName") as? String
        ?: error("Property 'trainingModuleName' not found")

val Project.trainingDataModuleName: String
    get() = findProperty("trainingDataModuleName") as? String
        ?: error("Property 'trainingDataModuleName' not found")

val Project.trainingDomainModuleName: String
    get() = findProperty("trainingDomainModuleName") as? String
        ?: error("Property 'trainingDomainModuleName' not found")

val Project.platformProviderModuleName: String
    get() = findProperty("platformProviderModuleName") as? String
        ?: error("Property 'platformProviderModuleName' not found")

val Project.diDispatchersModuleName: String
    get() = findProperty("diDispatchersModuleName") as? String
        ?: error("Property 'diDispatchersModuleName' not found")

val Project.appInitializerModuleName: String
    get() = findProperty("appInitializerModuleName") as? String
        ?: error("Property 'appInitializerModuleName' not found")


