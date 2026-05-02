package com.rajan.ecommerce.presentation.ui_components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rajan.ecommerce.R
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel
import com.rajan.ecommerce.presentation.navigation.Routes


@Composable
fun MyBottomNavBar(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val colorScheme = MaterialTheme.colorScheme
    val isDark = isSystemInDarkTheme()

    //Get current route safely from backstack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val cartItems by cartViewModel.cartItems.collectAsState(initial = emptyList())
    val totalItems = cartItems.sumOf { it.quantity }

    val navItems = listOf(
        NavItem("Home", R.drawable.regular_outline_home, Routes.HomeScreen()),
        NavItem("My Cart", R.drawable.regular_outline_bag, Routes.MyCart),
        NavItem("Scan", R.drawable.rounded_barcode_scanner_24, Routes.CartScreen()),
        NavItem("Favourite", R.drawable.regular_outline_heart, Routes.FavouriteScreen),
        NavItem("Profile", R.drawable.outline_account_circle_24, Routes.ProfileScreen)
    )

    Box(
        modifier = Modifier
            .background(color = colorScheme.surface)
            .navigationBarsPadding()
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar(
            containerColor = colorScheme.surface,
            modifier = Modifier
                .height(80.dp)
        ) {
            navItems.forEachIndexed { index, navItem ->
                // Check if selected using hierarchy (better for nested navigation)
                if (index == 2) {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            navController.navigate(navItem.routes) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Spacer(Modifier.size(24.dp))
                        },
                        label = {
                            Text(
                                text = navItem.title,
                                style = MaterialTheme.typography.labelSmall,
                                color = colorScheme.onSurfaceVariant
                            )
                        },
                        enabled = false,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )

                    )

                } else {
                    val isSelected = currentDestination?.hierarchy?.any() {
                        it.route == navItem.routes::class.qualifiedName
                    } == true

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(navItem.routes) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (navItem.title == "My Cart" && totalItems > 0) {

                                        Badge(
                                            containerColor = colorScheme.secondary,
                                            contentColor = colorScheme.onSecondary

                                        ) {
                                            Text(
                                                text = totalItems.toString()
                                            )
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = navItem.icon),
                                    contentDescription = navItem.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = navItem.title,
                                style = MaterialTheme.typography.labelSmall

                            )
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorScheme.primary,
                            selectedTextColor = colorScheme.primary,
                            unselectedIconColor = colorScheme.onSurface,
                            unselectedTextColor = colorScheme.onSurface,
                            indicatorColor = Color.Transparent
                        )
                    )

                }

            }
        }

        //Floating Scan Button

        FloatingActionButton(
            onClick = {
                navController.navigate(Routes.CartScreen(true)) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            shape = CircleShape,
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,
            elevation = FloatingActionButtonDefaults.elevation(2.dp),
            modifier = Modifier
                .offset(y = (-36).dp)// Move it up a bit
                .size(60.dp)
                .border(1.dp, colorScheme.surface, CircleShape)//the white notch effect
        ) {
            Icon(
                painter = painterResource(id = R.drawable.rounded_barcode_scanner_24),
                contentDescription = "Scan",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

data class NavItem(
    val title: String,
    val icon: Int,
    val routes: Routes
)