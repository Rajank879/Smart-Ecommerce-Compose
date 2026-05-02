package com.rajan.ecommerce.presentation.feature.cartscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rajan.ecommerce.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenTopBar(navController: NavController, title: String) {
    TopAppBar(
        title = { Text(text = title, modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        ) },
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.regular_outline_arrow_left),
                contentDescription = "Back",
                modifier = Modifier.padding(start = 16.dp)
                    .clickable(onClick = {navController.navigateUp()},).size(24.dp)
            )
        }
    )
}