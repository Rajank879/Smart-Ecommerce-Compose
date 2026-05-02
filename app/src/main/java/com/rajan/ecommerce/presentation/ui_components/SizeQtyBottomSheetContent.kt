package com.rajan.ecommerce.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun SizeQtyBottomSheetContent(onDone: (String) -> Unit, size: String) {
    val sizes = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
    var selectedSize by remember { mutableStateOf(size) }

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = selectedSize) {
        val index = sizes.indexOf(selectedSize)
        if (index>=0){
            listState.animateScrollToItem(index)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Select Quantity",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable{
                        onDone(selectedSize)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(sizes){size->
                    SizeItem(
                        size = size,
                        isSelected = size == selectedSize,
                        onClick = { selectedSize = size }
                    )
                }


            }
            Spacer(modifier = Modifier.height(24.dp))



        }
        Button(
            onClick = { onDone(selectedSize) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary
            )
        ) {
            Text(
                text = "Done",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }

}


@Composable
fun SizeItem(size: String, isSelected: Boolean = false, onClick: () -> Unit) {
    // Background for unselected items (Light grey in light mode, Dark grey in dark mode)
    val unselectedBg = colorScheme.onSurface.copy(alpha = 0.05f)
    val unselectedBorder = colorScheme.outlineVariant

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.Transparent else unselectedBg)
            .border(
                width = 1.dp,
                color = if (isSelected) colorScheme.primary else unselectedBorder,
                shape = CircleShape
            )
            .clickable{onClick()},
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = size,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) colorScheme.primary else colorScheme.onSurface
        )
    }

}