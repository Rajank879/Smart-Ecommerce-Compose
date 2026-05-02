package com.rajan.ecommerce.presentation.feature.pdp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rajan.ecommerce.presentation.feature.cartscreen.CartEffect
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel
import com.rajan.ecommerce.presentation.feature.home.ui.ProductCard
import com.rajan.ecommerce.presentation.navigation.Routes
import com.rajan.ecommerce.presentation.ui_components.CartItem
import com.rajan.ecommerce.presentation.ui_components.DashedDivider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsSearch(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    cartViewModel: CartViewModel
) {

    val query by viewModel.query.collectAsState()
    val results by viewModel.results.collectAsState()
    val productResults = viewModel.pagingData.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val snackBarHostState = remember { SnackbarHostState() }
    val showResult by viewModel.showResult.collectAsState()

    val haptic = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        cartViewModel.effectFlow.collect { effect ->
            when (effect) {
                is CartEffect.TriggerHaptic -> {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                is CartEffect.ShowMessage -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
            }
        }
    }
    LaunchedEffect(focusRequester) {
        if (!showResult) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack, "", tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    TextField(
                        value = query,
                        onValueChange = {
                            viewModel.queryChanged(it)
                            viewModel.setShowResult(false)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .focusRequester(focusRequester)
                           ,
                        placeholder = {
                            Text(
                                text = "Search",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(50),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(Icons.Default.Search, "", Modifier.size(20.dp))
                        },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        viewModel.queryChanged("")
                                    },
                                    Modifier.size(20.dp)
                                ) {
                                    Icon(Icons.Default.Close, "", Modifier.size(16.dp))
                                }

                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (query.isNotEmpty()) {
                                    viewModel.searchResult(query)
                                    viewModel.queryChanged(query)
                                    viewModel.setShowResult(true)
                                    focusManager.clearFocus()
                                }
                            }
                        )
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        if (showResult) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(vertical = 16.dp)
            ) {

                items(productResults.itemCount) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column {
                            productResults[index]?.let { product ->
                                CartItem(
                                    product,
                                    cartViewModel = cartViewModel,
                                    modifierParent = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                                "selected_product",
                                                product
                                            )
                                            navController.navigate(Routes.PDPScreen)
                                        })
                            }
                            if (index < productResults.itemCount - 1) {
                                DashedDivider(modifier = Modifier.padding(horizontal = 6.dp))
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(it)) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(results) { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    viewModel.searchResult(suggestion)
                                    viewModel.queryChanged(suggestion)
                                    viewModel.setShowResult(true)
                                    focusManager.clearFocus()

                                }
                            ),
                    )

                }
            }
        }
    }


}