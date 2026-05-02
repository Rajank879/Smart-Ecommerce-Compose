package com.rajan.ecommerce.presentation.feature.home

sealed class HomeNavigation {
    object NavigateToSearch : HomeNavigation()
    object NavigateToPlacesAutocomplete : HomeNavigation()
    data class NavigateToProductDetails(val productId: String) : HomeNavigation()
    data class NavigateToPDP(val id: Int) : HomeNavigation()

    object NavigateToScanner : HomeNavigation()

}