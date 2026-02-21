package com.alexfin90.experience

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            value = state.query,
            onValueChange = viewModel::onQueryChange,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            leadingIcon = {
                Image(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "experience",
                   )
            },
            label = {
                Text(text = "Search by company name or roles")
            },
            placeholder = {
                Text(text = "ex: Google, Senior Android Developer")
            }
        )
        LazyColumn(Modifier.fillMaxWidth()) {
            items(state.filtered) { exp ->
                ExperienceItem(exp.company, exp.title)
            }
        }
    }
}

@Composable
fun ExperienceItem(companyName: String, title: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
        Text(text = companyName, style = MaterialTheme.typography.bodyLarge,)
        Text(text = title, style = MaterialTheme.typography.bodyMedium,)
    }
}

