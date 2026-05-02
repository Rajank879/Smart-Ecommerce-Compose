package com.rajan.ecommerce.presentation.feature.home

import com.google.android.libraries.places.api.model.Place
import com.rajan.ecommerce.domain.model.products.Products

sealed class HomeEvent {
        data class OnAddressClick(val address: String= "") : HomeEvent()
        data class OnSearchQueryChange(val query: String = "") : HomeEvent()
        data class OnCategorySelected(val category: String) : HomeEvent()
        data class OnProductClick(val id: Int, val selectedProduct: Products) : HomeEvent()
        data class OnPlaceSelected(val place: Place) : HomeEvent()

        object OnNavigationHandled : HomeEvent()

        object NavigateToScanner : HomeEvent()
        object OnRefresh : HomeEvent()
}