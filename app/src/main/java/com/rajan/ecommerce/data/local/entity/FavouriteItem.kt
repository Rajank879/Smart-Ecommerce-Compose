package com.rajan.ecommerce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rajan.ecommerce.domain.model.products.Dimensions
import com.rajan.ecommerce.domain.model.products.Reviews

@Entity(tableName = "favourites")
data class FavouriteItem(
    @PrimaryKey val id: Int,
    val title: String?,
    val description: String?,
    val category: String?,
    val price: Double?,
    val rating: Double?,
    val tags: List<String>, // Needs Converter
    val dimensions: Dimensions?, // Needs Converter
    val reviews: List<Reviews>, // Needs Converter
    val images: List<String>, // Needs Converter
    val thumbnail: String?,
    val brand: String?,
    val availabilityStatus: String?,
    val discountPercentage: Double?

)