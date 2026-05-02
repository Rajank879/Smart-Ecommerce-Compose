package com.rajan.ecommerce.presentation.feature.address

sealed class AddressUIEvent {
    data class OnNameChange(val name: String): AddressUIEvent()
    data class OnMobileChange(val mobile: String): AddressUIEvent()
    data class OnPinCodeChange(val pinCode: String): AddressUIEvent()
    data class OnAddressChange(val address: String): AddressUIEvent()
    data class OnLocalityChange(val locality: String): AddressUIEvent()
    data class OnDefaultChange(val isDefault: Boolean): AddressUIEvent()
    data class OnCityChange(val city: String): AddressUIEvent()
    data class OnStateChange(val state: String): AddressUIEvent()
    data class IsAddressTypeChange(val addressType: String): AddressUIEvent()
    object OnSaveAddressClick: AddressUIEvent()
    object OnCancelClick: AddressUIEvent()

}