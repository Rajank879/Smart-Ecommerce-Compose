package com.rajan.ecommerce.presentation.feature.cartscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajan.ecommerce.data.local.dao.FavoriteDao
import com.rajan.ecommerce.data.mapper.toFavouriteItem
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.domain.repository.CartRepository
import com.rajan.ecommerce.domain.usecase.GetProductByBarcodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel@Inject  constructor(
    private val getProductByBarcodeUseCase: GetProductByBarcodeUseCase,
    private val cartRepository: CartRepository,
    private val favouriteDao: FavoriteDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    private val _effectFlow = MutableSharedFlow<CartEffect>()
    val effectFlow = _effectFlow.asSharedFlow()


    val cartItems = cartRepository.getCartItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())



    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.OnBarCodeScanned -> {
                handleBarcodeScan(event.barCode)
            }
            is CartEvent.AddToCart -> {
                addToCart(event.products)
            }

            is CartEvent.RemoveFromCart -> {
                removeFromCart(event.products)
            }

            is CartEvent.ToggleExpanded -> {
                _uiState.update { it.copy(isExpanded = !it.isExpanded) }
            }

            is CartEvent.ToggleCameraPermission -> {
                _uiState.update { it.copy(hasCameraPermission = event.isGranted) }
            }

        }
    }
    fun addToCart(products: Products) {
        viewModelScope.launch {
            cartRepository.addToCart(products)
        }
    }

    fun handleBarcodeScan(barcode: String){
        // Prevent re-scanning the same item if it's currently loading
        if (_uiState.value.isLoading) return

        viewModelScope.launch{
            _uiState.update { it.copy(isLoading = true, scannedBarCode = barcode) }
            val products = getProductByBarcodeUseCase(barcode)
            if (products != null){
                cartRepository.addToCart(products)
                _effectFlow.emit(CartEffect.TriggerHaptic)
                _effectFlow.emit(CartEffect.ShowMessage("Added ${products.title}"))
            } else{
                _effectFlow.emit(CartEffect.ShowMessage("Product not found: $barcode"))
                // Error: Product not found
                Log.e("CartViewModel", "No product found for barcode: $barcode")
            }
            _uiState.update { it.copy(isLoading = false, scannedBarCode = "") }
        }
    }

    fun removeFromCart(products: Products){
        viewModelScope.launch {
            val currentCartItems = cartItems.value.find { it.products.id == products.id }
            if (currentCartItems != null) {
                if (currentCartItems.quantity>1){
                    cartRepository.updateQuantity(products,currentCartItems.quantity-1)
                }else{
                    cartRepository.removeFromCart(products)
                }
            }
        }
    }

    fun updateCartQuantity(products: Products, newQuantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(products, newQuantity)
            if (newQuantity > 0) {
                _effectFlow.emit(CartEffect.ShowMessage("$newQuantity items added to the cart"))
            } else {
                _effectFlow.emit(CartEffect.ShowMessage("Item removed from the cart"))
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }


    fun isFavourite(id: Int): Flow<Boolean> = favouriteDao.isFavorite(id)

    fun toggleFavourite(products: Products, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                favouriteDao.removeFavorite(products.toFavouriteItem())
                _effectFlow.emit(CartEffect.ShowMessage("Remove from Wishlist"))
            }
            else {
                favouriteDao.addFavorite(products.toFavouriteItem())
                _effectFlow.emit(CartEffect.ShowMessage("Added to Wishlist"))
            }
        }
    }
}