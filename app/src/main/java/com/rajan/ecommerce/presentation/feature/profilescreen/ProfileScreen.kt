package com.rajan.ecommerce.presentation.feature.profilescreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rajan.ecommerce.R
import com.rajan.ecommerce.data.local.datastore.OnboardingDataStore
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel
import com.rajan.ecommerce.presentation.ui_components.MyBottomNavBar
import com.rajan.ecommerce.presentation.ui_components.MyTopAppBar

@Composable
fun ProfileScreen( navController: NavController, viewModel: ProfileScreenViewModel,cartViewModel: CartViewModel) {
    val colorScheme = MaterialTheme.colorScheme

    val context = LocalContext.current
    val isDarkModePref by OnboardingDataStore.isDarkMode(context).collectAsState(initial = null)
    val currentIsChecked = isDarkModePref ?: isSystemInDarkTheme()


    Scaffold(
        contentColor = colorScheme.background,
        bottomBar = { MyBottomNavBar(navController,  cartViewModel) },
        topBar = {MyTopAppBar(
            title = "",
            onBackClick = {navController.popBackStack()},
            topBarColor = Color.Transparent
        )}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp),

            ) {
                //Profile Image
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Image",
                        modifier = Modifier.size(80.dp),
                        tint = colorScheme.onSurfaceVariant
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(colorScheme.primary)
                        .clickable { }
                        .padding(8.dp),

                    contentAlignment = Alignment.Center

                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "",
                        tint = colorScheme.onPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Rajan Kumar",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Email Tag
            Surface(
                color = colorScheme.secondaryContainer, // Subtle background for the email
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "rajank879@gmail.com",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = colorScheme.onSecondaryContainer
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            //Menu Option
            Card (
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ){
                val menuItems = listOf(
                    Icons.Outlined.ShoppingCart to "My Cart",
                    Icons.Outlined.FavoriteBorder to "Favourite",
                    Icons.Outlined.RateReview to "Rate Us",
                    Icons.Outlined.LightMode to "Dark Theme",
                    Icons.Outlined.Share to "Invite a friend",
                    Icons.Outlined.Logout to "Logout",
                )
                Column{
                    menuItems.forEachIndexed { index, pair ->
                        ProfileLabel(pair.first,
                            pair.second,
                            index ==menuItems.lastIndex,
                            isChecked = currentIsChecked, // Pass the hoisted state
                            onCheckedChange = { viewModel.toggleTheme(it, context) })
                     }
                }
            }
        }
    }
}


@Composable
fun ProfileLabel(vector: ImageVector, profileLabel: String, isLast: Boolean,isChecked: Boolean,onCheckedChange: (Boolean) -> Unit    ) {
    val colorScheme = MaterialTheme.colorScheme
    val isLogout = profileLabel == "Logout"

    val contentColor = if (isLogout) colorScheme.error else colorScheme.onSurface
    val iconColor = if (isLogout) colorScheme.error else colorScheme.primary


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = vector,
            contentDescription = profileLabel,
            tint =iconColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = profileLabel,
            style = MaterialTheme.typography.bodyLarge,
            color =  contentColor,
            fontWeight = if (isLogout) FontWeight.Bold else FontWeight.Normal
        )

        if (profileLabel.contains("Theme", false)) {

            Switch(
                checked = isChecked, // Set based on current theme
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.height(22.dp)
            )
        } else if (!isLogout) {
            // Only show the arrow if it's not the Logout and not the Dark Theme toggle
            Icon(
                painter = painterResource(R.drawable.regular_outline_arrow_right),
                contentDescription = "",
                tint = colorScheme.outline,
                modifier = Modifier.size(16.dp)
            )
        }
    }
    if (!isLast){
        HorizontalDivider(
            thickness = 0.5.dp,
            color = colorScheme.outlineVariant
        )
    }

}