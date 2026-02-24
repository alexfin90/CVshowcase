package com.alexfin90.detailexperience

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.alexfin90.common.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@Stable
@HiltViewModel
class DetailExperienceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailExperienceScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        val title: String = savedStateHandle.toRoute<Route.DetailExperience>().title
        _uiState.update {
            it.copy(title = title)
        }
    }
}