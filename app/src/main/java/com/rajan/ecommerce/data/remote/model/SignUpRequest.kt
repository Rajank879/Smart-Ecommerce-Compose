package com.rajan.ecommerce.data.remote.model

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
