package com.rajan.ecommerce.presentation.feature.favouritescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rajan.ecommerce.data.mapper.toProducts
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel
import com.rajan.ecommerce.presentation.feature.home.ui.ProductCard
import com.rajan.ecommerce.presentation.ui_components.MyTopAppBar

@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavouriteScreenViewModel,
    cartViewModel: CartViewModel
) {


    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Wishlist",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { it ->
        val favouriteProducts by viewModel.favorites.collectAsState()
        if (favouriteProducts.isEmpty()) {
            // handle empty ui later
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(12.dp),

            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
                items(favouriteProducts) { favouriteItem ->
                    ProductCard(favouriteItem.toProducts(), { }, cartViewModel, true)
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}