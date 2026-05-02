package com.rajan.ecommerce.presentation.feature.cartscreen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rajan.ecommerce.presentation.ui_components.BarCodeScannerView
import com.rajan.ecommerce.presentation.ui_components.CartItem
import com.rajan.ecommerce.presentation.ui_components.DashedDivider
import kotlinx.coroutines.launch

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel, isScan: Boolean) {
    val uiState by cartViewModel.uiState.collectAsStateWithLifecycle()
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val totalItems = cartItems.sumOf { it.quantity }
    val totalPrice = cartItems.sumOf { it.products.price?.times(it.quantity) ?: 0.0 }
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
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    // Theme Colors (Simplified)
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val secondary = MaterialTheme.colorScheme.secondary
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainer

    // 2. Handle Side Effects (Toasts and Haptics)
    LaunchedEffect(Unit) {
        cartViewModel.effectFlow.collect { effect ->
            when (effect) {
                is CartEffect.TriggerHaptic ->
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                is CartEffect.ShowMessage ->
                    scope.launch {
                        snackBarHostState.showSnackbar(effect.message)
                    }
            }
        }
    }

    // 3. Permission Management
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            cartViewModel.onEvent(CartEvent.ToggleCameraPermission(granted))
        }
    )

    LaunchedEffect(isScan) {
        val isGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        cartViewModel.onEvent(CartEvent.ToggleCameraPermission(isGranted))

        if (isScan && !isGranted) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        primaryColor,
                        tertiaryColor
                    )
                )
            )
    ) {
        // --- SCANNER SECTION ---
        if (isScan) {
            if (uiState.hasCameraPermission) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.45f)
                ) {
                    BarCodeScannerView(
                        onBarcodeScanned = { barcode ->
                            cartViewModel.onEvent(CartEvent.OnBarCodeScanned(barcode))
                        }
                    )

                    // Instruction Overlay
                    ScannerOverlay(onPrimaryColor)

                    // Back Button
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                            .background(onSurfaceColor.copy(alpha = 0.2f), CircleShape)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            "Back",
                            tint = onPrimaryColor
                        )
                    }
                }
            }
        }

        // --- BOTTOM SHEET ---
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(
                    if (uiState.isExpanded) 0.9f
                    else if (isScan) 0.58f
                    else 0.18f
                )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(top = 32.dp)) {
                    CartScanSheetHeader(totalItems, totalPrice)

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        item {
                            Column() {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                        cartItems.forEachIndexed { index, item ->
                                            CartItem(
                                                cartItem = item.products,
                                                quantity = item.quantity,
                                                cartViewModel = cartViewModel,
                                                isScan = isScan
                                            )
                                            if (index < cartItems.size - 1) {
                                                DashedDivider(modifier = Modifier.padding(horizontal = 12.dp))
                                            }
                                        }

                                    }
                                }
                                if (uiState.isExpanded) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    CartPriceSection(
                                        totalItems,
                                        totalMrp,
                                        totalDiscount,
                                        totalPrice
                                    )
                                }
                            }
                        }
                    }
                    // Product section


                }
            }

            // Toggle Button
            IconButton(
                onClick = { cartViewModel.onEvent(CartEvent.ToggleExpanded) },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-28).dp)
                    .size(56.dp)
                    .background(primaryColor, CircleShape)
                    .border(4.dp, surfaceContainer, CircleShape)

            ) {
                Icon(
                    imageVector = if (uiState.isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    tint = onPrimaryColor
                )
            }
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 196.dp)
        )

        // Show Loading Indicator during API call
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = secondary
            )
        }
    }
}

@Composable
fun ScannerOverlay(color: Color) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Scan Barcode", color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(100.dp)
                .border(2.dp, color.copy(alpha = 0.8f), RoundedCornerShape(12.dp))
        ) {
            HorizontalDivider(
                color = Color.Red,
                thickness = 2.dp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(
            text = "Align barcode with the red line",
            color = color.copy(alpha = 0.8f),
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}