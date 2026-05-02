package com.rajan.ecommerce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rajan.ecommerce.domain.model.products.Dimensions
import com.rajan.ecommerce.domain.model.products.Meta
import com.rajan.ecommerce.domain.model.products.Reviews

@Entity(tableName = "products")
data class ProductItem(
    @PrimaryKey val id: Int,
    val title: String?,
    val description: String?,
    val category: String?,
    val price: Double?,
    val discountPercentage: Double?,
    val rating: Double?,
    val stock: Int?,
    val tags: List<String> = listOf(),
    val brand: String? ,
    val sku: String?,
    val weight: Int?,
    val dimensions: Dimensions?,
    val warrantyInformation: String? ,
    val shippingInformation: String? ,
    val availabilityStatus: String? ,
    val reviews: List<Reviews> = listOf(),
    val returnPolicy: String? ,
    val minimumOrderQuantity: Int? ,
    val meta: Meta? = Meta(),
    val images: List<String> = listOf(),
    val thumbnail: String?
)