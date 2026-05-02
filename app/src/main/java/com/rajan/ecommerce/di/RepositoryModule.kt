package com.rajan.ecommerce.di

import com.rajan.ecommerce.domain.repository.AuthRepository
import com.rajan.ecommerce.data.repository.AuthRepositoryImpl
import com.rajan.ecommerce.data.repository.CartRepositoryImpl
import com.rajan.ecommerce.data.repository.FavoriteRepositoryImpl
import com.rajan.ecommerce.data.repository.ProductRepositoryImpl
import com.rajan.ecommerce.data.repository.UserAddressRepositoryImpl
import com.rajan.ecommerce.domain.repository.CartRepository
import com.rajan.ecommerce.domain.repository.FavoriteRepository
import com.rajan.ecommerce.domain.repository.ProductRepository
import com.rajan.ecommerce.domain.repository.UserAddressRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
       productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindUserAddressRepository(
        userAddressRepositoryImpl: UserAddressRepositoryImpl
    ): UserAddressRepository


}