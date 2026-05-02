package com.rajan.ecommerce.presentation.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rajan.ecommerce.presentation.feature.home.HomeEvent
import com.rajan.ecommerce.presentation.feature.home.HomeUiState


@Composable
fun HomeCategoriesSection(onEvent: (HomeEvent) -> Unit, uiState: HomeUiState) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
//
//        AnimatedVisibility(visible = uiState.isLoading) {
//            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//        }
        Text(
            text = "Categories",
            style = typography.titleMedium,
            color = colors.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.categories) { category ->
                FilterChip(
                    selected = category == uiState.selectedCategory,
                    onClick = {
                        onEvent(HomeEvent.OnCategorySelected(category))
                    },
                    label = {
                        Text(
                            text = category,
                            style = typography.labelLarge
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colors.primary,
                        selectedLabelColor = colors.onPrimary,
                        containerColor = colors.surfaceVariant,
                        labelColor = colors.onSurfaceVariant
                    )
                )
            }
        }
    }
}