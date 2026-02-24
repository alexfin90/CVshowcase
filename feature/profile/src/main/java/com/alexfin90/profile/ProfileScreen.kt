package com.alexfin90.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel


@Composable
fun ProfileScreen(
    modifier: Modifier= Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    Box(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.TopCenter
    ) {

        Text(text = "ProfileScreen")


    }

}