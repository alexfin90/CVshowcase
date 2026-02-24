package com.alexfin90.profile

import androidx.compose.runtime.Stable
import com.alexfin90.profile.uiModel.UiProfileItem

@Stable
data class ProfileScreenState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val query : String = "",
    val items : List<UiProfileItem> = emptyList(),
    val filterItems : List<UiProfileItem> = emptyList()
)