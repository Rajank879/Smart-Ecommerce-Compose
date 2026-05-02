package com.rajan.ecommerce.domain.repository

import androidx.paging.PagingSource
import com.rajan.ecommerce.domain.model.products.Products

interface ProductRepository {
    suspend fun getCategories(): List<String>

    suspend fun getProductsByBarcode(barcode: String): Products?

    suspend fun getProductsBySearch(barcode: String):  List<Products>?

    suspend fun getProductsSearch(keyword: String): List<String>?

    fun getProductsPagingSource(category: String,searchKey: String, onTotalCount: (Int?) -> Unit): PagingSource<Int, Products>
}