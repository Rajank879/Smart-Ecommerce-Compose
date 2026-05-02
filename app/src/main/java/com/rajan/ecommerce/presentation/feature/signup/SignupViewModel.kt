package com.rajan.ecommerce.presentation.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajan.ecommerce.common.utils.NetworkResult
import com.rajan.ecommerce.domain.usecase.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState = MutableSharedFlow<SignupNavigation>()
    val navigationState = _navigationState.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnFirstnameChanged -> {
                _uiState.value = uiState.value.copy(
                    firstName = event.firstname,
                    firstNameError = null
                )
            }

            is SignUpEvent.OnLastnameChanged -> {
                _uiState.value = uiState.value.copy(
                    lastName = event.lastname,
                    lastNameError = null
                )
            }

            is SignUpEvent.OnEmailChanged -> {
                _uiState.value = uiState.value.copy(
                    email = event.email,
                    emailError = null
                )
            }

            is SignUpEvent.OnPasswordChanged -> {
                _uiState.value = uiState.value.copy(
                    password = event.newPassword,
                    passwordError = null
                )
            }

            is SignUpEvent.OnConfirmPasswordChanged -> {
                _uiState.value = uiState.value.copy(
                    confirmPassword = event.newConfirmPassword,
                    confirmPasswordError = null
                )
            }

            is SignUpEvent.OnSignUpClicked -> validateSignUp()
            is SignUpEvent.ClearError -> {
                _uiState.value = uiState.value.copy(
                    error = null
                )
            }
            is SignUpEvent.OnLoginClicked -> {
                viewModelScope.launch {
                    _navigationState.emit(SignupNavigation.NavigateToHome)
                }
            }

        }
    }

    private fun validateSignUp() {
        val state = _uiState.value

        if (state.firstName.isBlank()) {
            _uiState.value = state.copy(firstNameError = "First name cannot be empty")
            return
        }

        if (state.lastName.isBlank()){
            _uiState.value = state.copy(lastNameError = "Last name cannot be empty")
            return
        }

        if (state.email.isBlank()){
            _uiState.value = state.copy(emailError = "Email cannot be empty")
            return
        }

        if (state.password.isBlank()){
            _uiState.value = state.copy(passwordError = "Password cannot be empty")
            return
        }

        if (state.password.length<6){
            _uiState.value = state.copy(passwordError = "Password must be at least 6 characters long")
            return
        }

        if (state.password != state.confirmPassword){
            _uiState.value = state.copy(confirmPasswordError = "Password and confirm password do not match")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)
            when(val result = signupUseCase(firstName = state.firstName,
                lastName = state.lastName,
                email = state.email,
                password = state.password)){
                is NetworkResult.Success->{
                    _uiState.value = state.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                    _navigationState.emit(SignupNavigation.NavigateToLogin)
                }
                is NetworkResult.Error->{
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
                is NetworkResult.Loading -> {
                    _uiState.value = state.copy(
                        isLoading = true,
                        isSuccess = false
                    )
                }
                else -> Unit
            }

        }

    }
}