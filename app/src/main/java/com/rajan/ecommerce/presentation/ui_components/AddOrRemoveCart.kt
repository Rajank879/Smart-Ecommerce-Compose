package com.rajan.ecommerce.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel

@Composable
fun AddOrRemoveCart(cartViewModel: CartViewModel, product: Products, isPDP: Boolean = true) {
    val colorScheme = MaterialTheme.colorScheme
    val cartItems by cartViewModel.cartItems.collectAsState(emptyList())
    val currentItem = cartItems.find { it.products.id == product.id }
    val quantity = currentItem?.quantity ?: 0
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(if (isPDP) 44.dp else 36.dp)
            .fillMaxWidth(if (isPDP) 0.6f else 1f)
            .clip(RoundedCornerShape(4.dp))
            .background(colorScheme.surface)
            .border(
                width = 1.dp,
                color = colorScheme.primary,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = if (isPDP) 8.dp else 0.dp)

    ) {
        IconButton(
            modifier = Modifier
                .size(if (isPDP) 40.dp else 32.dp),
            onClick = { cartViewModel.removeFromCart(product) }
        ) {
            Icon(
                Icons.Default.Remove,
                contentDescription = "Remove",
                tint = colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = "$quantity",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = if (isPDP) 18.sp else 14.sp,
            ),
            fontWeight = FontWeight.ExtraBold,
            color = colorScheme.onSurface
        )
        IconButton(
            modifier = Modifier
                .size(if (isPDP) 40.dp else 32.dp),
            onClick = { cartViewModel.addToCart(product) }
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}