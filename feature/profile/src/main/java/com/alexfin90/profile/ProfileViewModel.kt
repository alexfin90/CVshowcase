package com.alexfin90.profile

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Stable
@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
}