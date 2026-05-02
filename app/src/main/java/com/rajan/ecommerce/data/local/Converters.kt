package com.rajan.ecommerce.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rajan.ecommerce.domain.model.products.Dimensions
import com.rajan.ecommerce.domain.model.products.Meta
import com.rajan.ecommerce.domain.model.products.Reviews

class Converters {
    private val gson = Gson()

    // For ArrayList<String> (tags and images)
    @TypeConverter
    fun fromStringList(value: List<String>?): String = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: listOf()
    }

    // For Dimensions object
    @TypeConverter
    fun fromDimensions(value: Dimensions?): String = gson.toJson(value)

    @TypeConverter
    fun toDimensions(value: String): Dimensions? = gson.fromJson(value, Dimensions::class.java)

    // For Meta object
    @TypeConverter
    fun fromMeta(value: Meta?): String = gson.toJson(value)

    @TypeConverter
    fun toMeta(value: String): Meta? = gson.fromJson(value, Meta::class.java)

    // For ArrayList<Reviews>
    @TypeConverter
    fun fromReviewsList(value: List<Reviews>?): String = gson.toJson(value)

    @TypeConverter
    fun toReviewsList(value: String): List<Reviews> {
        val listType = object : TypeToken<List<Reviews>>() {}.type
        return gson.fromJson(value, listType) ?: listOf()
    }
}