package com.rajan.ecommerce.data.repository

import com.rajan.ecommerce.data.local.dao.FavoriteDao
import com.rajan.ecommerce.data.local.entity.FavouriteItem
import com.rajan.ecommerce.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val favoriteDao: FavoriteDao):
    FavoriteRepository {
    override fun getAllFavourites(): Flow<List<FavouriteItem>> {
       return favoriteDao.getAllFavourites()
    }
}