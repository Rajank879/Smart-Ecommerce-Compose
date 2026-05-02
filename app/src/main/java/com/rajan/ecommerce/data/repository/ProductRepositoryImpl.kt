package com.rajan.ecommerce.data.repository

import android.util.Log
import androidx.paging.PagingSource
import com.rajan.ecommerce.data.local.dao.ProductDao
import com.rajan.ecommerce.data.remote.api.ProductApi
import com.rajan.ecommerce.data.remote.paging.ProductPagingSource
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getCategories(): List<String> {
        return try {
            // 1. Try fetching from API
            val categories = productApi.getCategories().map { it.name }
            listOf("All") + categories
        } catch (e: Exception) {
            // 2. If API fails (no internet), return a default list or local cache
            listOf("All")
        }

    }

    override suspend fun getProductsByBarcode(barcode: String): Products? {
        return try {
            // Fetch a large batch of products (DummyJSON has ~194 products total)
            val response = productApi.getAllProducts(limit = 194, skip = 0)

            // Filter locally by checking the meta.barcode field
            val matchedProductDto = response.products.find { productDto ->
                productDto.meta?.barcode == barcode
            }

            // Map the DTO to your Domain Model (Products)
            matchedProductDto

        } catch (e: Exception) {
            Log.e("ProductRepository", "Error filtering barcode: ${e.message}")
            null
        }
    }

    override suspend fun getProductsBySearch(keyword: String): List<Products>?{
        return try {
            // Fetch a large batch of products (DummyJSON has ~194 products total)
            val response = productApi.getAllProducts(limit = 194, skip = 0)


            response.products.filter { productDto ->
                productDto.title?.contains(keyword, ignoreCase = true) == true
            }


        } catch (e: Exception) {
            Log.e("ProductRepository", "Error filtering barcode: ${e.message}")
            null
        }
    }

    override suspend fun getProductsSearch(keyword: String): List<String>? {
        return try {
            // Fetch a large batch of products (DummyJSON has ~194 products total)
            val response = productApi.getAllProducts(limit = 194, skip = 0)

            // Filter locally by checking the meta.barcode field
            response.products.filter { productDto ->
                productDto.title?.contains(keyword, ignoreCase = true) == true
            }.map { it.title ?: "" }

        } catch (e: Exception) {
            Log.e("ProductRepository", "Error filtering barcode: ${e.message}")
            null
        }
    }

    override fun getProductsPagingSource(
        category: String,
        searchKey: String ,
        onTotalCount: (Int?) -> Unit,

    ): PagingSource<Int, Products> {
        return ProductPagingSource(productApi, productDao, category, searchKey,onTotalCount )
    }
}


