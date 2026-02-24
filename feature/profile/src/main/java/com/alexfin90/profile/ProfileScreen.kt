package com.alexfin90.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.items.isNotEmpty() -> ProfileScreenContent(modifier = modifier,
            uiState = uiState,
            onQueryChange = viewModel::onQueryChange
            )
        uiState.isLoading -> LoadingScreen()
        uiState.error != null -> ErrorScreen()
    }

}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier, uiState: ProfileScreenState,
    onQueryChange: (String) -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TextField(
            modifier = modifier.fillMaxWidth(),
            value = uiState.query,
            onValueChange = onQueryChange,
            label = { Text("search field") },
            placeholder = { Text("search by company or title") },
        )

        LazyColumn(Modifier.fillMaxWidth()) {
            items(uiState.filterItems) { item ->
                ProfileItem(companyName = item.company, title = item.title)
            }
        }

    }


}

@Composable
fun ProfileItem(companyName: String, title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp)
    ) {
        Text(text = companyName, style = MaterialTheme.typography.bodyLarge)
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ErrorScreen() {
}


@Composable
private fun LoadingScreen() {

}