package com.rajan.ecommerce.data.remote.model

data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val token: String
)