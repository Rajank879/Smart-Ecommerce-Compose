package com.rajan.ecommerce.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.rajan.ecommerce.data.local.entity.UserAddress
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAddressDao {

    @Upsert
    suspend fun insertAddress(address: UserAddress)

    @Delete
    suspend fun deleteAddress(address: UserAddress)

    @Query("SELECT * FROM UserAddress")
    fun getAllAddresses(): Flow<List<UserAddress>>
}
