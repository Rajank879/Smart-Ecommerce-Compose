package com.rajan.ecommerce.data.mapper

import com.rajan.ecommerce.data.local.entity.FavouriteItem
import com.rajan.ecommerce.data.local.entity.ProductItem
import com.rajan.ecommerce.domain.model.products.Products

fun Products.toFavouriteItem(): FavouriteItem{
    return FavouriteItem(
        id = this.id?:0,
       title = this.title,
        description = this.description,
        category = this.category,
        price = this.price,
        rating= this.rating,
        tags = this.tags,
        dimensions = this.dimensions,
        reviews = this.reviews,
        images = this.images,
        thumbnail = this.thumbnail,
        brand= this.brand,
        availabilityStatus = this.availabilityStatus,
        discountPercentage = this.discountPercentage?:0.0
    )
}

fun FavouriteItem.toProducts(): Products {
    return Products(
        id = this.id ,
        title = this.title,
        description = this.description,
        category = this.category,
        price = this.price,
        rating = this.rating,
        tags = this.tags,
        dimensions = this.dimensions,
        reviews = this.reviews,
        images = this.images,
        thumbnail = this.thumbnail,
        brand = this.brand,
        availabilityStatus = this.availabilityStatus,
        discountPercentage = this.discountPercentage?:0.0
    )
}

fun Products.toProductItem(): ProductItem{
    return ProductItem(
        id = this.id?:0,
        title = this.title,
        description = this.description,
        category = this.category,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating= this.rating,
        stock = this.stock,
        tags = this.tags,
        brand = this.brand,
        sku = this.sku,
        weight = this.weight,
        dimensions = this.dimensions,
        warrantyInformation = this.warrantyInformation,
        shippingInformation = this.shippingInformation,
        availabilityStatus = this.availabilityStatus,
        reviews = this.reviews,
        returnPolicy = this.returnPolicy,
        minimumOrderQuantity = this.minimumOrderQuantity,
        meta = this.meta,
        images = this.images,
        thumbnail = this.thumbnail
)
}

fun ProductItem.toDomainProduct(): Products{
    return Products(
        id = this.id?:0,
        title = this.title,
        description = this.description,
        category = this.category,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating= this.rating,
        stock = this.stock,
        tags = this.tags,
        brand = this.brand,
        sku = this.sku,
        weight = this.weight,
        dimensions = this.dimensions,
        warrantyInformation = this.warrantyInformation,
        shippingInformation = this.shippingInformation,
        availabilityStatus = this.availabilityStatus,
        reviews = this.reviews,
        returnPolicy = this.returnPolicy,
        minimumOrderQuantity = this.minimumOrderQuantity,
        meta = this.meta,
        images = this.images,
        thumbnail = this.thumbnail
    )
}
