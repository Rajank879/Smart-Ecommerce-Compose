package com.rajan.ecommerce.presentation.feature.detailsscreen


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.rajan.ecommerce.R
import com.rajan.ecommerce.presentation.feature.home.ProductLocal

@Composable
fun DetailsScreen(productId: Int, navController: NavController, viewModel: DetailsScreenViewModel) {
    val products = listOf(
        ProductLocal(
            id = 1,
            name = "Espresso",
            description = "Strong & Rich",
            price = 3.80,
            imageRes = R.drawable.coffee_1
        ),
        ProductLocal(
            id = 2,
            name = "Latte",
            description = "Smooth & Creamy",
            price = 4.80,
            imageRes = R.drawable.coffee_2
        ),
        ProductLocal(
            id = 3,
            name = "Copuccino",
            description = "With Chocolate",
            price = 6.80,
            imageRes = R.drawable.coffee_3
        ),
        ProductLocal(
            id = 4,
            name = "Mocha",
            description = "With Cocoa Flavour",
            price = 2.89,
            imageRes = R.drawable.coffee_4
        ),
        ProductLocal(
            id = 5,
            name = "Macchiato",
            description = "Bold & Milky",
            price = 7.50,
            imageRes = R.drawable.coffee_5
        ),
        ProductLocal(
            id = 6,
            name = "Flat White",
            description = "Velvety Smooth",
            price = 2.34,
            imageRes = R.drawable.coffee_6
        ),
        ProductLocal(
            id = 7,
            name = "Iced Mocha",
            description = "Refreshing & Rich",
            price = 3.80,
            imageRes = R.drawable.coffee_3
        ),
    )
    val selectedProduct = products.find { it.id == productId }
    if (selectedProduct == null) {
        navController.popBackStack()
        return
    }
    Scaffold(
        topBar = { DetailsScreenTopbar(navController) },
        bottomBar = { DetailsScreenBottomBar() }) { innerPadding ->
        ProductDetailContent(selectedProduct, innerPadding)
    }
}