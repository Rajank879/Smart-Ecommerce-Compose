package com.rajan.ecommerce.presentation.feature.favouritescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajan.ecommerce.data.local.entity.FavouriteItem
import com.rajan.ecommerce.domain.usecase.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavouriteScreenViewModel
@Inject  constructor(private val getFavoritesUseCase: GetFavoritesUseCase)
    : ViewModel() {

        val favorites : StateFlow<List<FavouriteItem>> = getFavoritesUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

}