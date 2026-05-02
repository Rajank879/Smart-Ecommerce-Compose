package com.rajan.ecommerce.presentation.feature.address

sealed class AddressNavigation {

    object NavigateToPayment : AddressNavigation()
    object Back : AddressNavigation()
}