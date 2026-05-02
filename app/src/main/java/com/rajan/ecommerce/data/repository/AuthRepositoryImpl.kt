package com.rajan.ecommerce.data.repository

import com.rajan.ecommerce.common.utils.NetworkResult
import com.rajan.ecommerce.data.remote.api.AuthApi
import com.rajan.ecommerce.data.remote.model.LoginRequest
import com.rajan.ecommerce.data.remote.model.LoginResponse
import com.rajan.ecommerce.data.remote.model.SignUpRequest
import com.rajan.ecommerce.data.remote.model.SignupResponse
import com.rajan.ecommerce.domain.repository.AuthRepository
import org.json.JSONObject
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun login(username: String, password: String): NetworkResult<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(username, password))
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    NetworkResult.Success(response.body()!!)
                } ?: NetworkResult.Error("Something went wrong")
            } else {
                val errorBody = response.errorBody()?.string()
                val message = try {
                    JSONObject(errorBody ?: "{}")
                        .optString("message", "Something went wrong")
                } catch (e: Exception) {
                    "Something went wrong"
                }

                NetworkResult.Error(message)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Network Error")
        }

    }

    override suspend fun signup(
        firstName: String, lastName: String, email: String, password: String
    ): NetworkResult<SignupResponse> {
        return try {
            val response = api.signup(SignUpRequest(firstName, lastName, email, password))
            NetworkResult.Success(response.body()!!)

        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Some thing went wrong")
        }
    }

}