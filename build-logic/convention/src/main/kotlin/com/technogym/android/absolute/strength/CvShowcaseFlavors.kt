package com.technogym.android.absolute.strength

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavorDimension {
    version
}

@Suppress("EnumEntryName")
enum class AppFlavor(val dimension: FlavorDimension, val isDefault: Boolean = false) {
    mock(FlavorDimension.version),
    real(FlavorDimension.version, isDefault = true),
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: AppFlavor) -> Unit = {},
) {
    commonExtension.apply {
        FlavorDimension.values().forEach { flavorDimension ->
            flavorDimensions += flavorDimension.name
        }

        productFlavors {
            AppFlavor.values().forEach { appFlavor ->
                register(appFlavor.name) {
                    dimension = appFlavor.dimension.name
                    flavorConfigurationBlock(this, appFlavor)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (appFlavor.isDefault) {
                            isDefault = true
                        }
                    }
                }
            }
        }

        sourceSets {
            AppFlavor.values().forEach { appFlavor ->
                getByName(appFlavor.name) {
                    kotlin.srcDir("src/${appFlavor.name}/kotlin")
                }
            }
        }
    }
}