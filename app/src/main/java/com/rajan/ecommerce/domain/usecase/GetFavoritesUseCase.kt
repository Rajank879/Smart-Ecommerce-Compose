package com.rajan.ecommerce.domain.usecase

import com.rajan.ecommerce.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoritesUseCase
@Inject constructor(private val repository: FavoriteRepository) {
    operator fun invoke() = repository.getAllFavourites()
}