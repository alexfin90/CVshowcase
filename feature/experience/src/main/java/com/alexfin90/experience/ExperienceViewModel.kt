package com.alexfin90.experience

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexfin90.domain.usecases.ObserveCvUseCase
import com.alexfin90.experience.mappers.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@Stable
@HiltViewModel
class ExperienceViewModel @Inject constructor(
    observeCvUseCase: ObserveCvUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow(ExperienceScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        observeCvUseCase()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onEach { cv ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        items = cv.experiences.map { it.toUiModel() },
                        filtered = cv.experiences.map { it.toUiModel() }
                    )
                }
            }
            .catch { e ->
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(query: String) {
        val q = query.lowercase()
        val filtered = _uiState.value.items.filter { exp ->
            exp.company.lowercase().contains(q) ||
                    exp.title.lowercase().contains(q)
        }
        _uiState.update { currentState ->
            currentState.copy(query = query, filtered = filtered)
        }
    }

    /*   val uiState: StateFlow<ExperienceScreenState> =
           observeCvUseCase().flowOn(ioDispatcher).map { cv ->
               ExperienceScreenState(
                   items = cv.experiences.map { it.toUiModel() },
                   filtered = cv.experiences.map { it.toUiModel() },
               )
           }.onStart { emit(ExperienceScreenState()) }
               .catch { e ->
                   emit(ExperienceScreenState(isLoading = false, error = e.message))
               }
               .stateIn(
                   scope = viewModelScope,
                   started = SharingStarted.WhileSubscribed(5_000),
                   initialValue = ExperienceScreenState(isLoading = true)
               )*/


}