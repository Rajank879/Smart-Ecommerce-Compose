package com.rajan.ecommerce.domain.repository

import com.rajan.ecommerce.domain.model.CartItem
import com.rajan.ecommerce.domain.model.products.Products
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(products: Products)
    suspend fun removeFromCart(products: Products)
    suspend fun updateQuantity(products: Products, newQuantity: Int)
    suspend fun clearCart()
}
