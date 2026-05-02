package com.rajan.ecommerce.presentation.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rajan.ecommerce.presentation.feature.home.HomeEvent
import com.rajan.ecommerce.presentation.feature.home.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(
    onEvent: (HomeEvent) -> Unit,
    uiState: HomeUiState,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val colors = MaterialTheme.colorScheme
    val density = LocalDensity.current

    //Get the actual status bar height
    val statusBarHeightPx = with(density) {
        WindowInsets.statusBars.asPaddingValues().calculateTopPadding().toPx()
    }
    //Define variables to track heights
    var totalHeight by remember { mutableStateOf(0f) }
    var searchBarHeight by remember { mutableStateOf(0f) }

    Surface(
        color = colors.primary,
        tonalElevation = 4.dp,
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                // IMPORTANT: Tell the scroll behavior how tall the header is
                totalHeight = coordinates.size.height.toFloat()
                //The limit is the height of the search bar
                val limit = totalHeight - statusBarHeightPx - searchBarHeight
                if (scrollBehavior.state.heightOffsetLimit != -limit) {
                    scrollBehavior.state.heightOffsetLimit = -limit
                }
            }
            .graphicsLayer {
                translationY = scrollBehavior.state.heightOffset
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(vertical = 12.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .graphicsLayer {
                        alpha = 1f - scrollBehavior.state.collapsedFraction
                    }
            ) {
                //Greeting and Notification
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Good Morning *",
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.onPrimary.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "Rajan",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = colors.onPrimary
                            )
                        }

                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    colors.onPrimary.copy(alpha = 0.15f),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = colors.onPrimary
                            )
                        }
                    }

                //

                Spacer(modifier = Modifier.height(16.dp))

                //Location

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        onEvent(HomeEvent.OnAddressClick())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = colors.onPrimary.copy(alpha = 0.9f),
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = uiState.address,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onPrimary.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardDoubleArrowDown,
                        contentDescription = null,
                        tint = colors.onPrimary
                    )

                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            //
            // 4. Wrap SearchBar to measure its height
            Box(modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                .onGloballyPositioned {
                    searchBarHeight = 48+it.size.height.toFloat()
                }) {
                ModernSearchBar(uiState.searchQuery, onEvent)
            }
        }
    }

}