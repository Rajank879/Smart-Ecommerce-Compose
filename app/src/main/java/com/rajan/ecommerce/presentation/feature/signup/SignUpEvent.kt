package com.rajan.ecommerce.presentation.feature.signup

sealed class SignUpEvent {
    data class OnFirstnameChanged(val firstname: String) : SignUpEvent()
    data class OnLastnameChanged(val lastname: String) : SignUpEvent()
    data class OnEmailChanged(val email: String) : SignUpEvent()
    data class OnPasswordChanged(val newPassword: String) : SignUpEvent()
    data class OnConfirmPasswordChanged(val newConfirmPassword: String) : SignUpEvent()
    object OnSignUpClicked : SignUpEvent()
    object OnLoginClicked : SignUpEvent()
    object ClearError : SignUpEvent()
}