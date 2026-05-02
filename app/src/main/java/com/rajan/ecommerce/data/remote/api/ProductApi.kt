package com.rajan.ecommerce.data.remote.api

import com.rajan.ecommerce.domain.model.products.ProductResponse
import com.rajan.ecommerce.domain.model.products.CategoryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponse

    @GET("products/category/{category}")
    suspend fun getProductByCategory(
        @Path("category") category: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponse
}