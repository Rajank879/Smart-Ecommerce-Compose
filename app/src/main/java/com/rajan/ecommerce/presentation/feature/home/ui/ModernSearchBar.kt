package com.rajan.ecommerce.presentation.feature.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rajan.ecommerce.presentation.feature.home.HomeEvent
import com.rajan.ecommerce.R

@Composable
fun ModernSearchBar( query: String,onEvent: (HomeEvent) -> Unit) {
    val colors = MaterialTheme.colorScheme
//    var query by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                onEvent(HomeEvent.OnSearchQueryChange(query))
            },
        shape = RoundedCornerShape(40.dp),
        tonalElevation = 0.dp,
        color = colors.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = colors.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            if (query.isEmpty()) {
                Text(
                    text = "Search ...",
                    color = colors.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                )
            } else {
                Text(
                    text = query,
                    color = colors.onSurfaceVariant,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .align(alignment = Alignment.CenterVertically),
                onClick = {
                    onEvent(HomeEvent.NavigateToScanner)
                },
            ) {
                Icon(
                    Icons.Outlined.QrCodeScanner,
                    contentDescription = "Search",
                    tint = colors.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}