package com.rajan.ecommerce.presentation.feature.cartscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CartScanSheetHeader(totalItems: Int, totalPrice: Double) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Outlined.ShoppingCart,
            contentDescription = "Cart",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column() {
            Text(text = "My Cart", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium)
            if (totalItems>0){
                Text(text = "$totalItems Items",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        if (totalPrice>0.0){
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                text = "$ ${"%.2f".format(totalPrice)}",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}