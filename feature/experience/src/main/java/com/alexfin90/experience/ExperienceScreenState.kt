package com.alexfin90.experience

import androidx.compose.runtime.Immutable
import com.alexfin90.experience.models.ExperienceUiModel

@Immutable
data class ExperienceScreenState (
    val isLoading: Boolean = true,
    val error: String? = null,
    val query : String = "",
    val items: List<ExperienceUiModel> = emptyList(),
    val filtered: List<ExperienceUiModel> = emptyList()
)