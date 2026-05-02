package com.rajan.ecommerce.presentation.ui_components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel
import com.rajan.ecommerce.R

@Composable
fun FavouriteButton(products: Products, cartViewModel: CartViewModel = hiltViewModel()) {

    val isFavourite by cartViewModel.isFavourite(products.id ?: 0).collectAsState(initial = false)

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_heart))

    val clipSpec = if (isFavourite) {
        LottieClipSpec.Frame(0, 20)
    } else {
        LottieClipSpec.Frame(20, 60)
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        clipSpec = clipSpec,
        iterations = 1, // Only play the transition once per click
        restartOnPlay = false,
        speed = 1.2f
    )

    IconButton(
        onClick = {
            cartViewModel.toggleFavourite(products, isFavourite)
        }
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(148.dp) // Large size so the heart is prominent

        )
    }
}