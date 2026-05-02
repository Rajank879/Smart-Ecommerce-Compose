package com.rajan.ecommerce.domain.usecase

import com.rajan.ecommerce.data.local.entity.UserAddress
import com.rajan.ecommerce.domain.repository.UserAddressRepository
import javax.inject.Inject

class SaveAddressUseCase @Inject constructor(private val repository: UserAddressRepository) {
    suspend operator fun invoke(address: UserAddress) {
        repository.insertAddress(address)
    }

}