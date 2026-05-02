package com.rajan.ecommerce.presentation.feature.signup

sealed class SignupNavigation {
    object NavigateToLogin : SignupNavigation()
    object NavigateToHome : SignupNavigation()
}