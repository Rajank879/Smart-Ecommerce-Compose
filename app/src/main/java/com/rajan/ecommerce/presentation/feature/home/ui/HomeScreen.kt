package com.rajan.ecommerce.presentation.feature.home.ui

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.rajan.ecommerce.presentation.feature.cartscreen.CartEffect
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel
import com.rajan.ecommerce.presentation.feature.home.HomeEvent
import com.rajan.ecommerce.presentation.feature.home.HomeNavigation
import com.rajan.ecommerce.presentation.feature.home.HomeViewModel
import com.rajan.ecommerce.presentation.navigation.Routes
import com.rajan.ecommerce.presentation.ui_components.FullScreenLoader
import com.rajan.ecommerce.presentation.ui_components.MyBottomNavBar
import com.rajan.ecommerce.presentation.ui_components.PaymentConfirmation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    showPaymentSuccess: Boolean
) {

    val context = LocalContext.current
    val activity = context as Activity
    val color = MaterialTheme.colorScheme
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val pagingItems = viewModel.pagingData.collectAsLazyPagingItems()
    val total = pagingItems.itemCount
    val totalProducts by viewModel.totalProducts.collectAsState()
    var showConfirmationSheet by remember {
        mutableStateOf(false)
    }
    val currentBackStackEntry = navController.currentBackStackEntry

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                // 2. Extract the Place object
                val place = Autocomplete.getPlaceFromIntent(data)
                viewModel.onEvent(HomeEvent.OnPlaceSelected(place))
                Log.d("Place", "Selected: ${place.name}, ${place.latLng}")
            }
        } else {
            val status = Autocomplete.getStatusFromIntent(result.data!!)
            Log.e("Place", "Error: ${status.statusMessage}")
        }
    }

    LaunchedEffect(showPaymentSuccess) {
        val isConsumed =
            currentBackStackEntry?.savedStateHandle?.get<Boolean>("consumed_success") ?: false
        if (showPaymentSuccess && !isConsumed) {
            showConfirmationSheet = true
            currentBackStackEntry?.savedStateHandle?.set("consumed_success", true)
            currentBackStackEntry?.savedStateHandle?.set("consumed_success", false)
            cartViewModel.clearCart()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationState.collect { navigation ->
            when (navigation) {
                HomeNavigation.NavigateToSearch -> {
                    navController.navigate(Routes.ProductsSearch)
                }

                HomeNavigation.NavigateToScanner -> {
                    navController.navigate(Routes.CartScreen(true))
                }

                is HomeNavigation.NavigateToPDP -> {
                    navController.navigate(Routes.PDPScreen)
                }

                HomeNavigation.NavigateToPlacesAutocomplete -> {
                    val intent = Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, // or FULLSCREEN
                        listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
                    ).build(context)
                    launcher.launch(intent)
                }

                else -> {}
            }
        }
    }
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
    LaunchedEffect(uiState) {
        uiState.apiError?.let {
            snackBarHostState.showSnackbar(it)
            viewModel.onEvent(HomeEvent.OnRefresh)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeHeader(viewModel::onEvent, uiState, scrollBehavior)
        },
        bottomBar = {
            MyBottomNavBar(navController, cartViewModel)
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = color.background
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomeCategoriesSection(
                    viewModel::onEvent,
                    uiState
                )
            }

            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { product ->
                    ProductCard(product, viewModel::onEvent, cartViewModel)
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = "Showing $total of $totalProducts products",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = color.onBackground
                    ),
                    textAlign = TextAlign.End
                )
            }

        }

        if (showConfirmationSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showConfirmationSheet = false
                    currentBackStackEntry?.savedStateHandle?.set("consumed_success", true)

                },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                // Call your existing UI component
                PaymentConfirmation(
                    onViewOrderClick = {
                        showConfirmationSheet = false
                        currentBackStackEntry?.savedStateHandle?.set("consumed_success", true)
                        navController.navigate(Routes.ProfileScreen)
                    },
                    onContinueShoppingClick = {
                        showConfirmationSheet = false
                        currentBackStackEntry?.savedStateHandle?.set("consumed_success", true)

                    }
                )
            }
        }
        if (uiState.isLoading) {
            FullScreenLoader()
        }
    }
}
