package com.rajan.ecommerce.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rajan.ecommerce.presentation.ui_components.util.StepItems

@Composable
fun StepProgressBar(steps: List<StepItems>) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            if (index <= steps.lastIndex) {
                Box(modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .background(
                        if (step.isCompleted || step.isActive)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    )
                )
            }
            StepCircle(step)
        }
    }
}

