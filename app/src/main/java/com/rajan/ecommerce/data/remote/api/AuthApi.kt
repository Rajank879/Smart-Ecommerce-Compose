package com.rajan.ecommerce.data.remote.api

import com.rajan.ecommerce.data.remote.model.LoginRequest
import com.rajan.ecommerce.data.remote.model.LoginResponse
import com.rajan.ecommerce.data.remote.model.SignUpRequest
import com.rajan.ecommerce.data.remote.model.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("user/add")
    suspend fun signup(
        @Body request: SignUpRequest
    ): Response<SignupResponse>
}