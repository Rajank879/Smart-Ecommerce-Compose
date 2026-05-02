package com.rajan.ecommerce.presentation.feature.pdp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.presentation.feature.cartscreen.CartEffect
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel
import com.rajan.ecommerce.presentation.ui_components.AddOrRemoveCart
import com.rajan.ecommerce.presentation.ui_components.FavouriteButton
import com.rajan.ecommerce.presentation.ui_components.MyTopAppBar

@Composable
fun PdpScreen(navController: NavHostController, product: Products, cartViewModel: CartViewModel) {
    val colorScheme = MaterialTheme.colorScheme
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        cartViewModel.effectFlow.collect { effect ->
            when(effect){
                is CartEffect.ShowMessage->{
                    snackBarHostState.showSnackbar(effect.message)
                }
                else -> {}
            }
        }
    }
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Product Details",
                onBackClick = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Outlined.Share, contentDescription = "Share")
                    }
                    FavouriteButton(product, cartViewModel)
                }
            )
        },
        bottomBar = {
            BottomCartBar(product, cartViewModel)
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorScheme.background)
        ) {
            // Image Carousel
            item {
                ImageCarousel(product.images)
            }

            // Product Info
            item {
                ProductHeaderSection(product)
            }

            // Tags
            item {
                TagSection(product.tags)
            }

            // Description
            item {
                DescriptionSection(product.description ?: "")
            }

            // Specifications
            item {
                SpecificationSection(product)
            }

            // Shipping & Policy
            item {
                PolicySection(product)
            }

            // Reviews
            item {
                ReviewHeader(product.rating ?: 0.0, product.reviews.size)
            }

            items(product.reviews) { review ->
                ReviewItem(review)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ImageCarousel(images: List<String>) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        // Indicator
        if (images.size > 1) {
            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(images.size) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductHeaderSection(product: Products) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = product.brand ?: "",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = product.title ?: "",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingBar(rating = product.rating ?: 0.0)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "(${product.rating})",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = product.availabilityStatus ?: "",
                style = MaterialTheme.typography.labelMedium,
                color = if (product.stock ?: 0 > 0) Color(0xFF4CAF50) else Color.Red,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            val discountPrice =
                (product.price ?: 0.0) * (1 - (product.discountPercentage ?: 0.0) / 100)
            Text(
                text = "$${String.format("%.2f", discountPrice)}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                textDecoration = TextDecoration.LineThrough
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${product.discountPercentage}% OFF",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFFE91E63),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagSection(tags: List<String>) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            SuggestionChip(
                onClick = { },
                label = { Text(tag) }
            )
        }
    }
}

@Composable
fun DescriptionSection(description: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun SpecificationSection(product: Products) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Specifications",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        SpecItem("SKU", product.sku ?: "N/A")
        SpecItem("Weight", "${product.weight}g")
        product.dimensions?.let {
            SpecItem("Dimensions", "${it.width} x ${it.height} x ${it.depth} cm")
        }
    }
}

@Composable
fun SpecItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun PolicySection(product: Products) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = 0.3f
            )
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Shipping: ${product.shippingInformation}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Warranty: ${product.warrantyInformation}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Returns: ${product.returnPolicy}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ReviewHeader(rating: Double, count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Reviews ($count)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = { /* View All */ }) {
            Text("View All")
        }
    }
}

@Composable
fun ReviewItem(review: com.rajan.ecommerce.domain.model.products.Reviews) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = review.reviewerName ?: "Anonymous",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            RatingBar(rating = review.rating?.toDouble() ?: 0.0, size = 12.dp)
        }
        Text(
            text = review.comment ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray
        )
        Divider(
            modifier = Modifier.padding(top = 8.dp),
            thickness = 0.5.dp,
            color = Color.LightGray
        )
    }
}

@Composable
fun RatingBar(rating: Double, size: androidx.compose.ui.unit.Dp = 16.dp) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.size(size),
                tint = if (index < rating.toInt()) Color(0xFFFFC107) else Color.LightGray
            )
        }
    }
}

@Composable
fun BottomCartBar(product: Products, cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.cartItems.collectAsState(emptyList())
    val currentItem = cartItems.find { it.products.id == product.id }
    val quantity = currentItem?.quantity ?: 0

    Surface(
        tonalElevation = 8.dp,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            //Price Section

            Column {
                Text(
                    text = "Price",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                val discountPrice =
                    (product.price ?: 0.0) * (1 - (product.discountPercentage ?: 0.0) / 100)
                Text(
                    text = "$${String.format("%.2f", discountPrice)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            //quantity section
            if (quantity > 0) {
                AddOrRemoveCart(cartViewModel, product)
            } else {
                Button(
                    onClick = {
                        cartViewModel.addToCart(product)
                    },
                    modifier = Modifier
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp)

                ) {
                    Text(text = "Add to Cart", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
