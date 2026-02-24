package com.alexfin90.profile

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexfin90.domain.usecases.ObserveCv2UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(FlowPreview::class)
@Stable
@HiltViewModel
class ProfileViewModel @Inject constructor(
    observeCv2UseCase: ObserveCv2UseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileScreenState())
    val uiState = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {

        observeCv2UseCase()
            .onStart {
                _uiState.update {
                    it.copy(isLoading = true)
                }
            }.onEach { cv ->
                _uiState.update {
                    it.copy(
                        error = null,
                        isLoading = false, items = cv.experience,
                        filterItems = cv.experience
                    )
                }
            }
            .catch { e ->
                _uiState.update {
                    it.copy(isLoading = false, error = e.message)
                }

            }.launchIn(viewModelScope)

        queryFlow.debounce(500L).onEach { query ->
            performSearch(query)
        }.launchIn(viewModelScope)

    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.value = query
    }

    fun performSearch(query: String) {

        val filtersItem = _uiState.value.items.filter {
            it.company.contains(query)
                    || it.title.contains(query)
        }

        _uiState.update {
            it.copy(filterItems = filtersItem)
        }

    }


}