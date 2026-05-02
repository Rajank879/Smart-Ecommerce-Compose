package com.rajan.ecommerce.di

import android.content.Context
import androidx.room.Room
import com.rajan.ecommerce.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides // Tells Hilt how to create the object
    @Singleton // Ensures only one instance of the DB exists
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "coffee_shop_db"  // The name of the file on your phone's storage
        )
            // Add this to handle schema changes by wiping the old DB
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesFavoriteDao(database: AppDatabase) = database.favoriteDao()

    @Provides
    fun providesProductDao(database: AppDatabase) = database.productDao()

    @Provides
    fun providesUserAddressDao(database: AppDatabase) = database.userAddressDao()

}