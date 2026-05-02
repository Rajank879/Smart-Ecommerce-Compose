package com.rajan.ecommerce.domain.repository

import com.rajan.ecommerce.data.local.entity.UserAddress
import kotlinx.coroutines.flow.Flow

interface UserAddressRepository {

    fun getAllAddresses(): Flow<List<UserAddress>>

    suspend fun insertAddress(address: UserAddress)

    suspend fun deleteAddress(address: UserAddress)

}