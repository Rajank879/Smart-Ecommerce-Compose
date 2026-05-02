package com.rajan.ecommerce.domain.usecase

import com.rajan.ecommerce.common.utils.NetworkResult
import com.rajan.ecommerce.data.remote.model.SignupResponse
import com.rajan.ecommerce.domain.repository.AuthRepository
import javax.inject.Inject

class SignupUseCase
@Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(firstName: String, lastName: String, email: String, password: String)
    : NetworkResult<SignupResponse>{
        if (firstName.isBlank()){
           return NetworkResult.Error("First name cannot be empty")
        }
        if(lastName.isBlank()){
            return  NetworkResult.Error("Last name cannot be empty")
        }
        if(email.isBlank()){
            return   NetworkResult.Error("Email cannot be empty")
        }
        if(password.isBlank()){
            return   NetworkResult.Error("Password cannot be empty")
        }
        if(password.length<6){
            return  NetworkResult.Error("Password must be at least 6 characters long")
        }

        return authRepository.signup(firstName, lastName, email, password)
    }
}