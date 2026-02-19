package com.alexfin90.experience

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexfin90.designsystem.theme.CvshowcaseTheme

@Composable
fun ExperienceScreen(
    modifier: Modifier = Modifier,
    viewModel: ExperienceViewModel = hiltViewModel(),
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.TopCenter
    ) {
        Greeting(name = "Alex")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "$name Resume!",
        style = MaterialTheme.typography.displayLarge
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CvshowcaseTheme {
        Greeting("Alex")
    }
}