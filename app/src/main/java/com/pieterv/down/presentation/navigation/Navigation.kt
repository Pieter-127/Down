package com.pieterv.down.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pieterv.down.presentation.main.MainScreen
import com.pieterv.down.presentation.main.MainViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val loginViewModel: MainViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen(
                viewModel = loginViewModel
            )
        }
    }
}