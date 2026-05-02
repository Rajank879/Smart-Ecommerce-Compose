package com.rajan.ecommerce.domain.repository

import com.rajan.ecommerce.data.local.entity.FavouriteItem
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavourites(): Flow<List<FavouriteItem>>
}