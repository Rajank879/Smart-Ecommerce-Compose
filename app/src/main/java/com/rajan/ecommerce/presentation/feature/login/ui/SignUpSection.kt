package com.rajan.ecommerce.presentation.feature.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SignUpSection(onSignUpClick: () -> Unit) {
    Row {
        Text(
            text = "Do not have an account yet? ",
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Sign Up",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onSignUpClick() },
        )
    }
}