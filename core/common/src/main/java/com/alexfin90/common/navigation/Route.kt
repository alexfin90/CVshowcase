package com.alexfin90.common.navigation

import kotlinx.serialization.Serializable

interface Route {

    @Serializable
    data object Experience : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data class DetailExperience(val title : String) : Route
}