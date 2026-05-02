package com.rajan.ecommerce.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(
        category: String,
        searchKey: String = "",
        onTotalCount: (Int?) -> Unit
    ): Flow<PagingData<Products>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { repository.getProductsPagingSource(category, searchKey,onTotalCount ) }
        ).flow
    }
}