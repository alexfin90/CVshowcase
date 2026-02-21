package com.alexfin90.experience


import com.alexfin90.experience.models.ExperienceUiModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf


data class ExperienceScreenState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val query : String = "",
    val items: PersistentList<ExperienceUiModel> = persistentListOf(),
    val filtered: PersistentList<ExperienceUiModel> = persistentListOf()
)