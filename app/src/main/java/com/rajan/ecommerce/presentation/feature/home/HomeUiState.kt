package com.rajan.ecommerce.presentation.feature.home

import com.rajan.ecommerce.R
import com.rajan.ecommerce.domain.model.products.Products

data class HomeUiState(
    val userName: String = "Rajan",
    val greeting: String = "Welcome back, $userName!",
    val address: String = "123 Coffee St, Brewtown",
    val notificationCount: Int = 0,
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "All",
    val selectedProduct: Products = Products(),
    val searchQuery: String = "",
    val products: List<Products>  = emptyList(),
    val isLoading: Boolean = false,
    val apiError: String? = null
)

private fun loadProducts()  = listOf(
    ProductLocal(
            id = 1,
            name = "Espresso",
            description = "Strong & Rich",
            price = 3.80,
            imageRes = R.drawable.coffee_1
        ),
    ProductLocal(
            id = 2,
            name = "Latte",
            description = "Smooth & Creamy",
            price = 4.80,
            imageRes = R.drawable.coffee_2
        ),
    ProductLocal(
            id = 3,
            name = "Copuccino",
            description = "With Chocolate",
            price = 6.80,
            imageRes = R.drawable.coffee_3
        ),
    ProductLocal(
            id = 4,
            name = "Mocha",
            description = "With Cocoa Flavour",
            price = 2.89,
            imageRes = R.drawable.coffee_4
        ),
    ProductLocal(
            id = 5,
            name = "Macchiato",
            description = "Bold & Milky",
            price = 7.50,
            imageRes = R.drawable.coffee_5
        ),
    ProductLocal(
            id = 6,
            name = "Flat White",
            description = "Velvety Smooth",
            price = 2.34,
            imageRes = R.drawable.coffee_6
        ),
    ProductLocal(
            id = 7,
            name = "Iced Mocha",
            description = "Refreshing & Rich",
            price = 3.80,
            imageRes = R.drawable.coffee_3
        ),
    )

data class ProductLocal(val id: Int, val name: String, val description: String, val price: Double, val imageRes: Int)