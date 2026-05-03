package com.rajan.ecommerce.presentation.feature.cartscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rajan.ecommerce.presentation.navigation.Routes
import com.rajan.ecommerce.presentation.ui_components.CartItem
import com.rajan.ecommerce.presentation.ui_components.DashedDivider
import com.rajan.ecommerce.presentation.ui_components.MyCartBottomCart
import com.rajan.ecommerce.presentation.ui_components.MyTopAppBar

@Composable
fun MyCart(navController: NavController, cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()
    val totalItems = cartItems.sumOf { it.quantity }
    val totalPrice = cartItems.sumOf { it.products.price?.times(it.quantity) ?: 0.0 }

// Total MRP (Original price before discount)
    val totalMrp = cartItems.sumOf { item ->
        val price = item.products.price ?: 0.0
        val discount = item.products.discountPercentage ?: 0.0

        // Formula: Original Price = Selling Price / (1 - Discount%)
        val originalPrice = if (discount > 0) {
            price / (1 - (discount / 100))
        } else {
            price
        }

        originalPrice * item.quantity
    }
    val totalDiscount = totalMrp - totalPrice
    Scaffold(
        topBar = {
            MyTopAppBar(
                modifier = Modifier.shadow(
                    elevation = 2.dp,
                    clip = false
                ),
                title = "My Cart",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },

        bottomBar = {
            MyCartBottomCart(totalItems, navController)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.Top // Remove spacedBy to avoid gaps between card parts
        ) {
            itemsIndexed(cartItems) { index, item ->
                // Determine the shape based on position to simulate a single Card
                val shape = when {
                    cartItems.size == 1 -> RoundedCornerShape(16.dp)
                    index == 0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    index == cartItems.size - 1 -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    else -> RectangleShape
                }

                Surface(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    shape = shape,
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 0.dp
                ) {
                    Column {
                        CartItem(item.products, item.quantity, cartViewModel)
                        if (index < cartItems.size - 1) {
                            DashedDivider(Modifier.padding(horizontal = 12.dp))
                        }
                    }
                }
            }

            if (cartItems.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    CartPriceSection(totalItems, totalMrp, totalDiscount, totalPrice)
                }
            }

        }
    }
}
