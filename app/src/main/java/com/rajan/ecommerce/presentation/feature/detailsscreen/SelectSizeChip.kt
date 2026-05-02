package com.rajan.ecommerce.presentation.feature.detailsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SelectSizeChip(
    sizeText: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (selected)  MaterialTheme.colorScheme.surface else Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = if (selected) Color.White else  MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .width(96.dp)
            .height(32.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = sizeText,
            color = if(selected) Color.White  else Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}