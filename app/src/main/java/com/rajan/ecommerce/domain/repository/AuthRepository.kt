package com.rajan.ecommerce.domain.repository

import com.rajan.ecommerce.common.utils.NetworkResult
import com.rajan.ecommerce.data.remote.model.LoginResponse
import com.rajan.ecommerce.data.remote.model.SignupResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): NetworkResult<LoginResponse>

    suspend fun signup( firstName: String, lastName: String, email: String, password: String): NetworkResult<SignupResponse>


}