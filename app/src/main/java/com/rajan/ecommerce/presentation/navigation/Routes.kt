package com.rajan.ecommerce.presentation.navigation

import com.rajan.ecommerce.domain.model.products.Products
import kotlinx.serialization.Serializable


sealed interface Routes {
    @Serializable
    object WelcomeScreen : Routes

    @Serializable
    data class HomeScreen(val showPaymentSuccess: Boolean = false) : Routes

    @Serializable
    data class DetailsScreen(val productId: Int) : Routes

    @Serializable
    object MyCart : Routes


    @Serializable
    data class CartScreen(val isScan : Boolean = false) : Routes

    @Serializable
    object ProfileScreen : Routes

    @Serializable
    object FavouriteScreen : Routes

    @Serializable
    object LoginScreen : Routes

    @Serializable
    object SignupScreen : Routes
    @Serializable
    object SearchScreen : Routes
    @Serializable
    object PDPScreen : Routes

    @Serializable
    object MapAddress: Routes

    @Serializable
    data class AddAddress(val pinCode: String ="", val city: String = "", val state: String = "", val address: String = "")

    @Serializable
    object ProductsSearch : Routes
}

