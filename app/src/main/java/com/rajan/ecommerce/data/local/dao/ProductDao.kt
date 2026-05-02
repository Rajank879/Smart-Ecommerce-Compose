package com.rajan.ecommerce.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rajan.ecommerce.data.local.entity.ProductItem

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductItem>)

    @Delete
    suspend fun deleteProducts(products: List<ProductItem>)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductItem>

    @Query("SELECT * FROM products WHERE category = :category")
    suspend fun getAllProductsByCategory(category: String): List<ProductItem>


}