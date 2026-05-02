package com.rajan.ecommerce.domain.usecase

import com.rajan.ecommerce.domain.repository.UserAddressRepository
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(private val repository: UserAddressRepository) {

    operator fun invoke () = repository.getAllAddresses()

}