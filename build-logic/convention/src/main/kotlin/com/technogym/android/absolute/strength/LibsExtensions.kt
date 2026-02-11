package com.technogym.android.absolute.strength


import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * This property provides a convenient way to retrieve the "libs" version catalog, which is commonly used
 * to manage dependencies and their versions centrally.
 */
internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")


