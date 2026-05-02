package com.rajan.ecommerce.presentation.feature.login

sealed class LoginEvent {
    data class OnEmailChange(val value: String) : LoginEvent()
    data class OnPasswordChange(val value: String) : LoginEvent()
    object OnLoginClick : LoginEvent()
    object OnRegisterClick : LoginEvent()
    object OnForgotPasswordClick : LoginEvent()
    object OnGoogleLoginClick : LoginEvent()
    object OnAppleLoginClick : LoginEvent()
}
