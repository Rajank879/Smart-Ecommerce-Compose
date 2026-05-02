package com.rajan.ecommerce.presentation.feature.cartscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rajan.ecommerce.R

@Composable
fun PaymentMethodCard(totalAmount: Double) {
    var expanded by remember { mutableStateOf(false) }
    var selectedMode by remember { mutableStateOf("Online") }
    val paymentModes = listOf("Online", "Cash On Delivery")
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(if (selectedMode == "Online") R.drawable.mobile_banking else R.drawable.wallet),
                    contentDescription = selectedMode,
                    Modifier.size(32.dp),
                    tint =  MaterialTheme.colorScheme.surface
                )

                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = selectedMode,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$ $totalAmount",
                        style = MaterialTheme.typography.bodyLarge.copy(color =  MaterialTheme.colorScheme.surface)
                    )
                }
                Box {
                    Icon(
                        painter = painterResource(R.drawable.regular_outline_arrow_down),
                        contentDescription = "Expand",
                        modifier = Modifier.size(20.dp).
                        clickable(onClick = {expanded = true}),
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        paymentModes.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                onClick = {
                                    selectedMode = it
                                    expanded = false
                                },
                                leadingIcon = {
                                    Icon(painter = painterResource(if (it == "Online") R.drawable.mobile_banking else R.drawable.wallet),
                                        contentDescription = it, tint =  MaterialTheme.colorScheme.surface, modifier = Modifier.size(24.dp))
                                },
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .background(
                                    color = if (selectedMode == it)  MaterialTheme.colorScheme.surface.copy(alpha = 0.2f) else Color.Transparent
                                )
                            )
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =  MaterialTheme.colorScheme.surface,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Place Order")
            }
        }
    }
}