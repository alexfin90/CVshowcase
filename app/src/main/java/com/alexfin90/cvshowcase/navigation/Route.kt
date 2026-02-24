package com.alexfin90.cvshowcase.navigation

import kotlinx.serialization.Serializable

interface Route {

    @Serializable
    data object Experience : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data class DetailExperience(val experienceName : String) : Route
}