package com.rajan.ecommerce.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rajan.ecommerce.presentation.ui_components.util.StepItems

@Composable
fun StepCircle(step: StepItems) {
    val primary = MaterialTheme.colorScheme.primary
    val surfaceOutline = MaterialTheme.colorScheme.outline
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(
                    if (step.isCompleted)
                        primary
                    else if (step.isActive)
                        onSurfaceVariant
                    else surfaceOutline
                )
                .border(
                    width = 2.dp,
                    color = if (step.isActive) primary else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (step.isCompleted) {
                Icon(
                    Icons.Outlined.Check,
                    "Completed",
                    tint = Color.White,
                    modifier = Modifier.size(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = step.title,
            style = MaterialTheme.typography.labelSmall,
            color = if (step.isCompleted) primary else if (step.isActive) onSurfaceVariant else surfaceOutline
        )
        Spacer(modifier = Modifier.width(4.dp))

    }
}