package com.rajan.ecommerce.presentation.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun RecentSearchesSection() {
    val recent: List<String> = listOf( "Cappuccino", "Mocha")

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Recent Searches",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))

        recent.forEach {
            AssistChip(
                onClick = {
                    //Todo
                },
                label = {
                    Text(text = it)
                },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}