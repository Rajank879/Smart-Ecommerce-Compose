package com.rajan.ecommerce.data.repository

import com.rajan.ecommerce.domain.model.CartItem
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(): CartRepository {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())

    override fun getCartItems(): Flow<List<CartItem>> = _cartItems.asStateFlow()


    override suspend fun addToCart(products: Products) {
        val currentCartItems = _cartItems.value.toMutableList()
        val existingItem = currentCartItems.find { it.products.id == products.id }
        if (existingItem != null) {
            val index = currentCartItems.indexOf(existingItem)
            currentCartItems[index] = existingItem.copy(quantity = existingItem.quantity+1)
        } else {
            currentCartItems.add(CartItem(products=products, quantity = 1 ))
        }
        _cartItems.value = currentCartItems
    }

    override suspend fun removeFromCart(products: Products) {
        _cartItems.value = _cartItems.value.filter { it.products.id != products.id }
    }

    override suspend fun updateQuantity(
        products: Products,
        newQuantity: Int
    ) {
        _cartItems.value = _cartItems.value.map {
            if (it.products.id == products.id) {
                it.copy(quantity = newQuantity)
            } else it

        }
    }

    override suspend fun clearCart() {
        _cartItems.value = emptyList()
    }
}