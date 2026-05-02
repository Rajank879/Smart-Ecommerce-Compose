package com.rajan.ecommerce.presentation.feature.cartscreen

data class CartUiState (
    val isExpanded: Boolean = false,
    val isScanning: Boolean = false,
    val hasCameraPermission: Boolean = false,
    val scannedBarCode: String = "",
    val isLoading: Boolean = false,
    val apiError: String? = null
)