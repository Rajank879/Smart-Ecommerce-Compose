package com.rajan.ecommerce.presentation.feature.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val apiError: String? = null,
    val isLoginSuccess: Boolean = false
)