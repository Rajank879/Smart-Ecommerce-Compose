package com.rajan.ecommerce.domain.model

import com.rajan.ecommerce.domain.model.products.Products

data class CartItem(
    val products: Products,
    val quantity: Int
)

