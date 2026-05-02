package com.rajan.ecommerce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserAddress")
data class UserAddress(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val name: String,
    val mobile: String,
    val pinCode: String,
    val address: String,
    val locality: String,
    val city: String,
    val state: String,
    val isDefault: Boolean,
    val addressType: String
)