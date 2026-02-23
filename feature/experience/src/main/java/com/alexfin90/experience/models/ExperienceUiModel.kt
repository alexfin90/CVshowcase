package com.alexfin90.experience.models

import androidx.compose.runtime.Immutable
@Immutable
data class ExperienceUiModel(
    val title: String,
    val company: String,
    val companyLogoURl: String,
    val period: String = "",
    val location: String = "",
    val impact: String = "",
    val keyAchievements: List<String> = emptyList(),
    val techStack: List<String> = emptyList(),
)


