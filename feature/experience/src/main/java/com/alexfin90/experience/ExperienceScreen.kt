package com.alexfin90.experience

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExperienceScreen(
    modifier: Modifier = Modifier,
    viewModel: ExperienceViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),
    ) {
        TextField(
            value = state.query,
            onValueChange = viewModel::onQueryChange,
            placeholder = { Text(text = "Search by company name or roles") }
        )
        LazyColumn {
            items(state.filtered) { exp ->
                ExperienceItem(exp.company, exp.title)
            }
        }
    }
}

@Composable
fun ExperienceItem(companyName: String, title: String) {
    Column {
        Text(text = companyName)
        Text(text = title)
    }
}

