package com.rajan.ecommerce.domain.usecase

import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByBarcodeUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(barcode: String): Products?{
        return repository.getProductsByBarcode(barcode)
    }
}