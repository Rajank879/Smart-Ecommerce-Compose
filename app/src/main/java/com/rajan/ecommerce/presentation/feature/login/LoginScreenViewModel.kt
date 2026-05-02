package com.rajan.ecommerce.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajan.ecommerce.common.utils.NetworkResult
import com.rajan.ecommerce.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModel
@Inject constructor(private val loginUseCase: LoginUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _navigationState = MutableSharedFlow<LoginNavigation>()
    val navigationState = _navigationState.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _uiState.value = _uiState.value.copy(
                    email = event.value,
                    emailError = null
                )
            }

            is LoginEvent.OnPasswordChange -> {
                _uiState.value = _uiState.value.copy(
                    password = event.value,
                    passwordError = null
                )
            }

            is LoginEvent.OnLoginClick -> {
                validateAndLogin()
            }

            is LoginEvent.OnRegisterClick -> {
               viewModelScope.launch {
                   _navigationState.emit(LoginNavigation.NavigateToRegister)
               }
            }
            is LoginEvent.OnForgotPasswordClick -> {
                viewModelScope.launch {
                    _navigationState.emit(LoginNavigation.NavigateToForgotPassword)
                }
            }
            is LoginEvent.OnGoogleLoginClick -> {}
            is LoginEvent.OnAppleLoginClick -> {}
        }
    }

    fun validateAndLogin() {
        val email = _uiState.value.email
        val password = _uiState.value.password
        if (email.isBlank()) {
            _uiState.update {
                it.copy(
                    emailError = "Email is required"
                )
            }
        }

        if (password.isBlank()) {
            _uiState.update {
                it.copy(
                    passwordError = "Password is required"
                )
            }

        }

        login(email, password)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, apiError = null)
            }
            when (val result = loginUseCase(email, password)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoginSuccess = true
                        )
                    }
                    _navigationState.emit(LoginNavigation.NavigateToHome)
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            apiError = result.message
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)

                    }
                }

                else -> Unit
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(
                emailError = null,
                passwordError = null,
                apiError = null
            )
        }
    }
}