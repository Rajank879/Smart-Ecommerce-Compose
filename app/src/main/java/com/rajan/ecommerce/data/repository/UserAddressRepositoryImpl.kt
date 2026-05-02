package com.rajan.ecommerce.data.repository

import com.rajan.ecommerce.data.local.dao.UserAddressDao
import com.rajan.ecommerce.data.local.entity.UserAddress
import com.rajan.ecommerce.domain.repository.UserAddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAddressRepositoryImpl @Inject constructor(private val userAddressDao: UserAddressDao) :
    UserAddressRepository {
    override fun getAllAddresses(): Flow<List<UserAddress>> {
        return userAddressDao.getAllAddresses()
    }

    override suspend fun insertAddress(address: UserAddress) {
       userAddressDao.insertAddress(address)
    }

    override suspend fun deleteAddress(address: UserAddress) {
        userAddressDao.deleteAddress(address)
    }
}