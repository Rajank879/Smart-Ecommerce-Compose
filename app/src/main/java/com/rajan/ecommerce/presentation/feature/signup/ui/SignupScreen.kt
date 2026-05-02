package com.rajan.ecommerce.presentation.feature.signup.ui

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
import com.rajan.ecommerce.presentation.feature.signup.SignUpEvent
import com.rajan.ecommerce.presentation.feature.signup.SignupNavigation
import com.rajan.ecommerce.presentation.feature.signup.SignupViewModel
import com.rajan.ecommerce.presentation.navigation.Routes
import com.rajan.ecommerce.presentation.ui_components.FullScreenLoader

@Composable
fun SignupScreen(navController: NavController, viewModel: SignupViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    //Navigation
    LaunchedEffect(Unit) {
        viewModel.navigationState.collect { navigation ->
            when (navigation) {
                SignupNavigation.NavigateToLogin,SignupNavigation.NavigateToHome -> {
                    navController.navigate(Routes.LoginScreen) {
                        popUpTo(Routes.SignupScreen) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    //Api Error
    LaunchedEffect(uiState) {
        if (uiState.isSuccess){
            navController.navigate(Routes.LoginScreen){
                popUpTo(Routes.SignupScreen){
                    inclusive = true
                }
            }
        }
        uiState.error?.let {
            snackBarHostState.showSnackbar(it)
            viewModel.onEvent(SignUpEvent.ClearError)
        }
    }
    Scaffold(
        snackbarHost = {  SnackbarHost(snackBarHostState) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SignupContent(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )
            if (uiState.isLoading) {
                FullScreenLoader()
            }
        }
    }
}