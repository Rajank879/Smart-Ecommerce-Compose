package com.rajan.ecommerce.presentation.feature.signup.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LoginSection(onLoginClick: ()-> Unit) {
    Row {
        Text(
            text = "Already have an account? ",
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Login",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onLoginClick() },
        )
    }
}