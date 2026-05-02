package com.rajan.ecommerce.domain.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponse(
    @SerializedName("products") var products: List<Products> = listOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("skip") var skip: Int? = null,
    @SerializedName("limit") var limit: Int? = null

) : Parcelable

@Parcelize
data class Products(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("price") var price: Double? = null,
    @SerializedName("discountPercentage") var discountPercentage: Double? = null,
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("stock") var stock: Int? = null,
    @SerializedName("tags") var tags: List<String> = listOf(),
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("sku") var sku: String? = null,
    @SerializedName("weight") var weight: Int? = null,
    @SerializedName("dimensions") var dimensions: Dimensions? = Dimensions(),
    @SerializedName("warrantyInformation") var warrantyInformation: String? = null,
    @SerializedName("shippingInformation") var shippingInformation: String? = null,
    @SerializedName("availabilityStatus") var availabilityStatus: String? = null,
    @SerializedName("reviews") var reviews: List<Reviews> = listOf(),
    @SerializedName("returnPolicy") var returnPolicy: String? = null,
    @SerializedName("minimumOrderQuantity") var minimumOrderQuantity: Int? = null,
    @SerializedName("meta") var meta: Meta? = Meta(),
    @SerializedName("images") var images: List<String> = listOf(),
    @SerializedName("thumbnail") var thumbnail: String? = null
) : Parcelable

@Parcelize
data class Meta(
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("barcode") var barcode: String? = null,
    @SerializedName("qrCode") var qrCode: String? = null
) : Parcelable

@Parcelize
data class Dimensions(
    @SerializedName("width") var width: Double? = null,
    @SerializedName("height") var height: Double? = null,
    @SerializedName("depth") var depth: Double? = null

) : Parcelable

@Parcelize
data class Reviews(
    @SerializedName("rating") var rating: Int? = null,
    @SerializedName("comment") var comment: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("reviewerName") var reviewerName: String? = null,
    @SerializedName("reviewerEmail") var reviewerEmail: String? = null

) : Parcelable
