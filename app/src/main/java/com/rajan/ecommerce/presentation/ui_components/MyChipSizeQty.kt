package com.rajan.ecommerce.presentation.ui_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rajan.ecommerce.domain.model.products.Products


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyChipSizeQty(label: String, updateQuantiles: (Products, Int) -> Unit, product: Products) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showSheet by remember { mutableStateOf(false) }
    var displayLabel  by remember { mutableStateOf(label) }
    AssistChip(
        modifier = Modifier
            .height(24.dp)
            .padding(horizontal = 4.dp, vertical = 0.dp),
        onClick = {
            showSheet = true
        },
        label = {
            Text(displayLabel, style = MaterialTheme.typography.bodySmall)
        },
        trailingIcon = {
            Icon(
                Icons.Outlined.KeyboardArrowDown,
                contentDescription = null,
                Modifier.size(12.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        },
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant),



    )


    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            dragHandle = null,
            contentColor = MaterialTheme.colorScheme.surface
        ) {
            SizeQtyBottomSheetContent(
                onDone = { selectedValue ->
                    // 3. Logic to preserve the prefix (like "Qty" or "Size")
                    val prefix = label.substringBefore(":")
                    displayLabel = if (label.contains(":")) {
                        "$prefix: $selectedValue"
                    } else {
                        "Qty: $selectedValue"
                    }
                    // 1. Update your logic/ViewModel with selectedValue if needed
                    // 2. Dismiss the sheet
                    updateQuantiles(product,selectedValue.toInt() )
                    showSheet = false
                },
                size = displayLabel.substringAfter(":").trim()
            )
        }
    }

}