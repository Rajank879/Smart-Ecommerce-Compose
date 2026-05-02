package com.rajan.ecommerce.presentation.ui_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DashedDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
    thickness: androidx.compose.ui.unit.Dp = 0.7.dp,
    dashLength: Float = 10f,
    gapLength: Float = 10f
) {
    Canvas(modifier.fillMaxWidth().height(thickness)) {
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(0f, 0f),
            end = androidx.compose.ui.geometry.Offset(size.width, 0f),
            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                intervals = floatArrayOf(dashLength, gapLength),
                phase = 0f
            ),
            strokeWidth = thickness.toPx()
        )
    }
}