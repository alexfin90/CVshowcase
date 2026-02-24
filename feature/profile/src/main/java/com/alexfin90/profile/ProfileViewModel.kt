package com.alexfin90.profile

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.alexfin90.domain.usecases.ObserveCv2UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@Stable
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeCv2UseCase: ObserveCv2UseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileScreenState())
    val uiState = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {

    }



}