package com.alexfin90.experience


import androidx.compose.runtime.Stable
import com.alexfin90.experience.models.ExperienceUiModel

@Stable
data class ExperienceScreenState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val query : String = "",
    val items: List<ExperienceUiModel> = emptyList(),
    val filtered: List<ExperienceUiModel> = emptyList()
)