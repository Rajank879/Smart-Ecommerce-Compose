package com.rajan.ecommerce.presentation.feature.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rajan.ecommerce.R

@Composable
fun LoginSocialButton(onGoogleClick: () -> Unit, onAppleClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        ElevatedButton(
            onClick = { onGoogleClick() },
            modifier = Modifier.height(48.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.White

            )
        ) {
            Icon(
                painter = painterResource(R.drawable.google),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Google",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp
            )
        }


        ElevatedButton(
            onClick = { onAppleClick() },
            modifier = Modifier.height(48.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.White

            )
        ) {
            Icon(
                painter = painterResource(R.drawable.apple_logo),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Apple",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}