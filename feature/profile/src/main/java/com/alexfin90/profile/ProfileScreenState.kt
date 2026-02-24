package com.alexfin90.profile

import androidx.compose.runtime.Stable
import com.alexfin90.domain.entities.Experience

@Stable
data class ProfileScreenState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val query : String = "",
    val items : List<Experience> = emptyList(),
    val filterItems : List<Experience> = emptyList()
)