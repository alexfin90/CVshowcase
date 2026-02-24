package com.alexfin90.cvshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexfin90.cvshowcase.navigation.Route
import com.alexfin90.designsystem.theme.CvshowcaseTheme
import com.alexfin90.detailexperience.DetailExperienceScreen
import com.alexfin90.experience.ExperienceScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CvshowcaseTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.primary,
                    bottomBar = { },
                    topBar = { }
                ) { innerPadding ->
                    CvNavHost(modifier = Modifier.padding(paddingValues = innerPadding))
                }
            }
        }
    }
}


@Composable
private fun CvNavHost(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = Route.Experience
    ) {
        composable<Route.Experience> {
            ExperienceScreen(
                modifier = modifier,
                onItemClick = { title ->
                    navController.navigate(Route.DetailExperience(title))
                }
            )
        }
        composable<Route.DetailExperience> {
            DetailExperienceScreen(modifier = modifier)
        }
    }
}

