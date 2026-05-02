package com.rajan.ecommerce.presentation.feature.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel
import com.rajan.ecommerce.presentation.feature.home.HomeEvent
import com.rajan.ecommerce.presentation.ui_components.AddOrRemoveCart
import com.rajan.ecommerce.presentation.ui_components.FavouriteButton
import kotlin.math.roundToInt


@Composable
fun ProductCard(
    product: Products,
    onEvent: (HomeEvent) -> Unit,
    cartViewModel: CartViewModel = hiltViewModel(),
    isFavourite: Boolean = false
) {
    val color = colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(if (isFavourite) 4.dp else 4.dp),
        elevation = CardDefaults.cardElevation(if (isFavourite) 1.dp else 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.surface
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = colorScheme.outlineVariant
        ),
        onClick = {
            onEvent(HomeEvent.OnProductClick(product.id ?: 0, product))
        }
    ) {
        Column {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.thumbnail)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = product.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Favorite/Delete Button Overlay
                    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isFavourite) {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "Delete",
                                        modifier = Modifier.size(18.dp) // Scale the icon inside too
                                    )
                                }
                            } else {
                                FavouriteButton(product, cartViewModel)
                            }
                        }
                    }
                }
                Text(
                    text = product.brand.orEmpty().uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorScheme.primary, // Blue in light mode
                    maxLines = 1
                )

                Text(
                    text = product.title.orEmpty(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                RatingBar(product.rating ?: 5.0)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val actualPrice = product.price ?: 0.0
                    val discount = product.discountPercentage ?: 0.0
                    val originalPrice = ((actualPrice + (actualPrice * (discount / 100))) * 100).roundToInt() / 100.0
                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = colorScheme.onSurface
                    )
                    Text(
                        text = "$${originalPrice}",
                        style = MaterialTheme.typography.bodySmall,
                        color = color.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${discount.toInt()}% OFF",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorScheme.tertiary
                    )

                }

                if (!isFavourite) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        AddToCart(cartViewModel, product)
                    }
                }
            }
            if (isFavourite) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorScheme.outlineVariant
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MOVE TO BAG",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = colorScheme.secondary
                    )
                }

            }
        }
    }
}

@Composable
fun AddToCart(cartViewModel: CartViewModel, product: Products) {
    val colorScheme = MaterialTheme.colorScheme
    val cartItems by cartViewModel.cartItems.collectAsState(emptyList())
//    val currentItem = cartItems.find { it.products.id == product.id }
    val quantity = cartItems.find { it.products.id == product.id }?.quantity?:0

    if (quantity > 0) {
        AddOrRemoveCart(cartViewModel, product, false)
    } else {
        Button(
            onClick = {
                cartViewModel.addToCart(product)
            },
            modifier = Modifier
                .height(36.dp)
                .fillMaxWidth()
            ,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary,
            )
            ) {
            Text(text = "Add to Cart", fontWeight = FontWeight.Bold)
        }
    }
}