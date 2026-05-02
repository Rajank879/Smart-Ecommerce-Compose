package com.rajan.ecommerce.presentation.feature.address

data class AddressUiState(
    val name: String = "",
    val mobile: String = "",
    val pinCode: String = "",
    val address: String = "",
    val locality: String = "",
    val city: String = "",
    val state: String = "",
    val isDefault: Boolean = false,
    val isLoadings: Boolean = false,
    val error: String = "",
    val addressType: String = "Home"
)
