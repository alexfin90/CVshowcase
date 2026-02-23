package com.alexfin90.experience

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexfin90.domain.usecases.ObserveCvUseCase
import com.alexfin90.experience.mappers.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
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
class ExperienceViewModel @Inject constructor(
    observeCvUseCase: ObserveCvUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExperienceScreenState())
    val uiState = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    companion object {
        private const val DEBOUNCE_TIME = 500L
        private val TAG = ExperienceViewModel::javaClass.name
    }

    init {
        observeCvUseCase()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onEach { cv ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = true,
                        items = cv.experience.map { it.toUiModel() }.toPersistentList(),
                        filtered = cv.experience.map { it.toUiModel() }.toPersistentList()
                    )
                }
            }
            .catch { e ->
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
            .launchIn(viewModelScope)

        queryFlow
            .debounce(DEBOUNCE_TIME)
            .onEach { query ->
                performSearch(query)
            }
            .launchIn(viewModelScope)
    }

    fun performSearch(query: String) {
        val q = query.lowercase()
        val filtered = _uiState.value.items.filter { exp ->
            exp.company.lowercase().contains(q) ||
                    exp.title.lowercase().contains(q)
        }.toPersistentList()
        _uiState.update { currentState ->
            currentState.copy(filtered = filtered)
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.value = query
    }
}