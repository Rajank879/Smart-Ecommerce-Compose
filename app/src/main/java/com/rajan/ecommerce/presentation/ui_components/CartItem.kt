package com.rajan.ecommerce.presentation.ui_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.MoveUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel

@Composable
fun CartItem(cartItem: Products,quantity:Int =0, cartViewModel: CartViewModel = hiltViewModel(),
             isScan: Boolean = false, modifierParent: Modifier = Modifier.fillMaxWidth()) {
    Box(
        modifier = modifierParent


    ){
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = cartItem.thumbnail,
                contentDescription = cartItem.title,
                modifier = Modifier
                    .height(if (isScan) 96.dp else 160.dp)
                    .width(96.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.brand.orEmpty(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = cartItem.title.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                )

                Text(
                    text = "Sold by: ${cartItem.brand.orEmpty()}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    MyChipSizeQty("Size: ${cartItem.weight}", cartViewModel::updateCartQuantity,
//                        cartItem
//                    )
                    MyChipSizeQty(
                        label = if (quantity == 0) {
                            "Select Quantity"
                        } else {
                            "Qty: $quantity"
                        },
                        cartViewModel::updateCartQuantity,
                        cartItem

                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val discountPrice =
                        (cartItem.price ?: 0.0) * (1 - (cartItem.discountPercentage ?: 0.0) / 100)
                    Text(
                        text = "$${String.format("%.2f", discountPrice)}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$${cartItem.price}",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.LightGray,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${cartItem.discountPercentage}% OFF",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = "Info",
                        modifier = Modifier.size(6.dp),
                        )
                }

                val shippingText = if (cartItem.shippingInformation.isNullOrEmpty()) {
                    "Free Shipping"
                } else {
                    cartItem.shippingInformation
                }
                if (!isScan){
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(
                            Icons.Outlined.LocalShipping,
                            contentDescription = "Info",
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = buildAnnotatedString {
                                val pattern = Regex("\\d+-\\d+")
                                val match = shippingText?.let { pattern.find(it) }

                                if (match != null) {
                                    // Text before the numbers (e.g., "Ships in ")
                                    append(shippingText.substring(0, match.range.first))

                                    // The bold part (e.g., "3-5")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(match.value)
                                    }

                                    // Text after the numbers (e.g., " business days")
                                    append(shippingText.substring(match.range.last + 1))
                                } else {
                                    // Fallback if no pattern found (e.g., "Free Shipping")
                                    append(shippingText)
                                }
                            },
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(Icons.Outlined.MoveUp, contentDescription = "Info", modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = cartItem.returnPolicy.orEmpty(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }


}