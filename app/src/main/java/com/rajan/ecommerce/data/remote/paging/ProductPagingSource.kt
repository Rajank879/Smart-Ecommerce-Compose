package com.rajan.ecommerce.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rajan.ecommerce.data.local.dao.ProductDao
import com.rajan.ecommerce.data.mapper.toDomainProduct
import com.rajan.ecommerce.data.mapper.toProductItem
import com.rajan.ecommerce.data.remote.api.ProductApi
import com.rajan.ecommerce.domain.model.products.Products

class ProductPagingSource(
    private val productApi: ProductApi,
    private val productDao: ProductDao,
    private val category: String,
    private val searchKey: String = "",
    private val onTotalCount: (Int?) -> Unit,
): PagingSource<Int, Products> (){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Products> {
        return try {
            val page = params.key ?: 0
            val limit = params.loadSize
            val skip = page * limit

            val response = if (category.equals("all", ignoreCase = true)) {
                productApi.getAllProducts(limit, skip)
            } else productApi.getProductByCategory(category.replace(" ","-"), limit, skip)

            val entities = response.products.map { it.toProductItem() }
                productDao.insertProducts(entities)


            onTotalCount(response.total) // update total count
            LoadResult.Page(
                data = if (searchKey.isNotEmpty()){
                    response.products.filter { prod->
                        prod.title?.contains(searchKey, true) == true
                    }
                }else response.products,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.products.isEmpty()) null else page + 1
            )

        }catch (e: Exception) {
            val localEntities = if (category.equals("all", ignoreCase = true)){
                productDao.getAllProducts()
            }else{
                productDao.getAllProductsByCategory(category)

            }
            if (localEntities.isNotEmpty()){
                LoadResult.Page(
                    data = localEntities.map{it.toDomainProduct()},
                    prevKey = null,
                    nextKey = null
                )
            }else LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Products>): Int? {
        return state.anchorPosition?.let { position->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: state.closestPageToPosition(position)?.nextKey?.minus(1)

        }
    }

}