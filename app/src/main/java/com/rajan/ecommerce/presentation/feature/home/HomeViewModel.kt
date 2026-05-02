package com.rajan.ecommerce.presentation.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.domain.usecase.GetCategoriesUseCase
import com.rajan.ecommerce.domain.usecase.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState = MutableSharedFlow<HomeNavigation>()
    val navigationState = _navigationState.asSharedFlow()

    private val _pagingData = MutableStateFlow<PagingData<Products>>(PagingData.empty())
    val pagingData: StateFlow<PagingData<Products>> = _pagingData
    private val _totalProducts = MutableStateFlow(0)
    val totalProducts: StateFlow<Int> = _totalProducts

    init {
        loadCategories()
        loadProduct()
    }

    private fun loadProduct() {
        _uiState.value = _uiState.value.copy(isLoading = true, apiError = null)
        viewModelScope.launch {
            getProductUseCase(_uiState.value.selectedCategory) { total ->
                _totalProducts.value = total ?: 0
            }.cachedIn(viewModelScope)
                .collect { paging ->
                    _pagingData.value = paging
                    _uiState.value = _uiState.value.copy(isLoading = false, apiError = null)
                }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val result = getCategoriesUseCase()
                Log.v("getCategories", "getCategories = $result")
                _uiState.value = _uiState.value.copy(
                    categories = result,
                    isLoading = false,
                    apiError = null
                )
            } catch (e: Exception) {
                Log.v("getCategories", "getCategories = ${e.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    apiError = e.message ?: "Failed to load categories"
                )
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnAddressClick -> {
                viewModelScope.launch {
                    _navigationState.emit(HomeNavigation.NavigateToPlacesAutocomplete)
                }

            }

            is HomeEvent.OnNavigationHandled -> {
                // Reset your navigation state to Idle/Null
//                _navigationState.value = HomeNavigation.Idle
            }

            is HomeEvent.OnPlaceSelected -> {
                _uiState.value = _uiState.value.copy(
                    address = event.place.address ?: _uiState.value.address
                )
            }

            is HomeEvent.OnSearchQueryChange -> {
                _uiState.value = _uiState.value.copy(
                    searchQuery = event.query
                )
                viewModelScope.launch {
                    _navigationState.emit(HomeNavigation.NavigateToSearch)
                }
            }

            is HomeEvent.OnCategorySelected -> {
                _uiState.value = _uiState.value.copy(
                    selectedCategory = event.category
                )
                loadProduct()
            }

            is HomeEvent.OnProductClick -> {
                Log.v("onProductClick", "onProductClick = ${event.id}")
                _uiState.value = _uiState.value.copy(
                    selectedProduct = event.selectedProduct
                )
                viewModelScope.launch {
                    _navigationState.emit(HomeNavigation.NavigateToPDP(event.id))
                }
                // Handle product click event, e.g., navigate to product details screen
            }

            is HomeEvent.OnRefresh -> {
                // Handle refresh event, e.g., reload products from the server
            }

            is HomeEvent.NavigateToScanner -> {

                viewModelScope.launch {
                    _navigationState.emit(HomeNavigation.NavigateToScanner)
                }

            }


        }
    }
}
