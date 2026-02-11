/**
 * This is shared between modules to provide configurations type safety.
 */
enum class CvShowcaseBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}