package com.rajan.ecommerce.presentation.feature.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajan.ecommerce.data.local.entity.UserAddress
import com.rajan.ecommerce.domain.usecase.GetAddressesUseCase
import com.rajan.ecommerce.domain.usecase.SaveAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAddressViewModel
@Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val saveAddressUseCase: SaveAddressUseCase
) : ViewModel()
{

    private val _uiState = MutableStateFlow(AddressUiState())
    val uiState: StateFlow<AddressUiState> = _uiState.asStateFlow()

    private val _navigationState = MutableSharedFlow<AddressNavigation>()
    val navigationState = _navigationState.asSharedFlow()


    fun onEvent(event: AddressUIEvent){
        when(event){
           is AddressUIEvent.OnNameChange->{
                _uiState.update { it.copy(name = event.name) }
            }
            is AddressUIEvent.OnMobileChange->{
                _uiState.update { it.copy(mobile = event.mobile) }
            }

            is AddressUIEvent.OnPinCodeChange->{
                _uiState.update { it.copy(pinCode = event.pinCode) }
            }

            is AddressUIEvent.OnLocalityChange ->{
                _uiState.update { it.copy(locality = event.locality) }
            }

            is AddressUIEvent.OnAddressChange ->{
                _uiState.update { it.copy(address = event.address) }
            }
            is AddressUIEvent.OnCityChange ->{
                _uiState.update { it.copy(city = event.city) }
            }
            is AddressUIEvent.OnStateChange -> {
                _uiState.update { it.copy(state = event.state) }
            }

            is AddressUIEvent.OnDefaultChange -> {
                _uiState.update { it.copy(isDefault = event.isDefault) }
            }

            is AddressUIEvent.IsAddressTypeChange -> {
                _uiState.update { it.copy(addressType = event.addressType) }
            }

            is AddressUIEvent.OnCancelClick -> {
                viewModelScope.launch {
                    _navigationState.emit(AddressNavigation.Back)
                }
            }
            is AddressUIEvent.OnSaveAddressClick -> {
                addressSave()
                viewModelScope.launch {
                    _navigationState.emit(AddressNavigation.NavigateToPayment)
                }
            }

        }
    }

    fun addressSave(){
        viewModelScope.launch {
            saveAddressUseCase(
                UserAddress(
                    name= _uiState.value.name,
                    mobile = _uiState.value.mobile,
                    pinCode = _uiState.value.pinCode,
                    address = _uiState.value.address,
                    locality = _uiState.value.locality,
                    city = _uiState.value.city,
                    state = _uiState.value.state,
                    isDefault = _uiState.value.isDefault,
                    addressType = _uiState.value.addressType
                )
            )
        }
    }
}