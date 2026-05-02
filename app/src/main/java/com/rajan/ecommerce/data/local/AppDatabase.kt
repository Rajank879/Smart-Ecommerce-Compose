package com.rajan.ecommerce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rajan.ecommerce.data.local.dao.FavoriteDao
import com.rajan.ecommerce.data.local.dao.ProductDao
import com.rajan.ecommerce.data.local.dao.UserAddressDao
import com.rajan.ecommerce.data.local.entity.FavouriteItem
import com.rajan.ecommerce.data.local.entity.ProductItem
import com.rajan.ecommerce.data.local.entity.UserAddress


@Database(entities = [FavouriteItem::class, ProductItem::class, UserAddress::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    abstract fun productDao(): ProductDao

    abstract fun userAddressDao(): UserAddressDao
}

