package com.rajan.ecommerce.presentation.feature.pdp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.domain.repository.ProductRepository
import com.rajan.ecommerce.domain.usecase.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _showResult = MutableStateFlow(false)
    val showResult = _showResult.asStateFlow()

    private val _pagingData = MutableStateFlow<PagingData<Products>>(PagingData.empty())
    val pagingData: StateFlow<PagingData<Products>> = _pagingData
    private val _totalProducts = MutableStateFlow(0)
    val totalProducts: StateFlow<Int> = _totalProducts

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val results = _query
        .debounce(500)//wait for typing
        .distinctUntilChanged()
        .flatMapLatest { q ->
            if (q.isBlank()) {
                flowOf(emptyList())
            } else {
                searchMock(q)
            }
        }.catch { e ->
            emit(emptyList())
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    fun setShowResult(show: Boolean){
        _showResult.value =show
    }
    fun queryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun searchResult(searchKey: String) {
        _showResult.value = true
        viewModelScope.launch {
            getProductUseCase("All", searchKey) { total ->
                _totalProducts.value = total ?: 0
            }.cachedIn(viewModelScope)
                .collect { paging ->
                    _pagingData.value = paging

                }
        }
    }

    private fun searchMock(query: String): Flow<List<String>> = flow {
        emit(productRepository.getProductsSearch(query) ?: emptyList())
    }


}