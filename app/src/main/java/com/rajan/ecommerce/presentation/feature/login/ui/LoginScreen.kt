package com.rajan.ecommerce.presentation.feature.login.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.rajan.ecommerce.presentation.feature.login.LoginNavigation
import com.rajan.ecommerce.presentation.feature.login.LoginScreenViewModel
import com.rajan.ecommerce.presentation.navigation.Routes
import com.rajan.ecommerce.presentation.ui_components.FullScreenLoader

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginScreenViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    //SideEffect
    LaunchedEffect(uiState) {
        uiState.apiError?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
        viewModel.navigationState.collect { event ->
            when (event) {
                is LoginNavigation.NavigateToRegister -> {
                    navController.navigate(Routes.SignupScreen)
                }

                is LoginNavigation.NavigateToHome,LoginNavigation.NavigateToForgotPassword -> {
                    navController.navigate(Routes.HomeScreen) {
                        popUpTo(Routes.LoginScreen) { inclusive = true }
                    }
                }
            }

        }
    }

    LaunchedEffect(uiState) {
        uiState.apiError?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
        {
            LoginContent(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )
            if (uiState.isLoading) {
                FullScreenLoader()
            }
        }
    }
}





