package com.alexfin90.experience

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExperienceScreen(
    modifier: Modifier = Modifier,
    viewModel: ExperienceViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    when {
        state.items.isNotEmpty() -> ExperienceContent(
            modifier = modifier,
            viewModel = viewModel,
            state = state
        )

        state.isLoading -> LoadingScreen(modifier = modifier)
        state.error != null -> ErrorButton(error = state.error, modifier = modifier)
    }
}

@Composable
fun ExperienceContent(
    modifier: Modifier,
    viewModel: ExperienceViewModel,
    state: ExperienceScreenState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = companyName, style = MaterialTheme.typography.bodyLarge)
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun ErrorButton(error: String?, modifier: Modifier = Modifier) {
    error?.let {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary),
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


