package com.rajan.ecommerce.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rajan.ecommerce.data.local.entity.FavouriteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavouriteItem)

    @Delete
    suspend fun removeFavorite(favorite: FavouriteItem)

    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): Flow<List<FavouriteItem>>

    @Query("SELECT EXISTS(SELECT * FROM favourites WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>





}