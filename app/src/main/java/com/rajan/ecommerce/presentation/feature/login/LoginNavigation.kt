package com.rajan.ecommerce.presentation.feature.login

sealed class LoginNavigation {
    object NavigateToHome : LoginNavigation()
    object NavigateToRegister : LoginNavigation()
    object NavigateToForgotPassword : LoginNavigation()

}