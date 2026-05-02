package com.rajan.ecommerce.presentation.feature.cartscreen

import com.rajan.ecommerce.domain.model.products.Products

sealed class CartEvent {
    data class OnBarCodeScanned(val barCode: String) : CartEvent()
    data class AddToCart(val products: Products) : CartEvent()
    data class RemoveFromCart(val products: Products) : CartEvent()
    object ToggleExpanded : CartEvent()
    data class ToggleCameraPermission(val isGranted: Boolean) : CartEvent()
}

// ViewModel -> UI (One-time Side Effects)
sealed class CartEffect {
    data class ShowMessage(val message: String) : CartEffect()
    object TriggerHaptic : CartEffect()
}
