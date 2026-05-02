package com.rajan.ecommerce.domain.usecase

import com.rajan.ecommerce.common.utils.NetworkResult
import com.rajan.ecommerce.data.remote.model.LoginResponse
import com.rajan.ecommerce.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): NetworkResult<LoginResponse> {
        if (email.isBlank()) {
            return NetworkResult.Error("Email is required")
        }

        if (password.isBlank()) {
            return NetworkResult.Error("Password is required")
        }

        if (password.length < 6) {
            return NetworkResult.Error("Password should be greater than 6 characters")
        }

//        if (!email.contains("@")) {
//            return NetworkResult.Error("Invalid Email")
//        }
//
//        if (!email.contains(".")) {
//            return NetworkResult.Error("Invalid Email")
//        }

        return authRepository.login(email, password)
    }
}
