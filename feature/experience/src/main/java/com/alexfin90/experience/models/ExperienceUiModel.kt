package com.alexfin90.experience.models

import androidx.compose.runtime.Immutable
import com.alexfin90.domain.entities.Experience

@Immutable
data class ExperienceUiModel(
    val title: String,
    val company: String,
    val companyLogoURl: String,
)


