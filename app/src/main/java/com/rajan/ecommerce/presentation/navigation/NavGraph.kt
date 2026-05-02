package com.rajan.ecommerce.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rajan.ecommerce.data.local.datastore.OnboardingDataStore
import com.rajan.ecommerce.domain.model.products.Products
import com.rajan.ecommerce.presentation.feature.address.AddAddress
import com.rajan.ecommerce.presentation.feature.address.MapAddress
import com.rajan.ecommerce.presentation.feature.cartscreen.CartScreen
import com.rajan.ecommerce.presentation.feature.cartscreen.CartViewModel
import com.rajan.ecommerce.presentation.feature.cartscreen.MyCart
import com.rajan.ecommerce.presentation.feature.detailsscreen.DetailsScreen
import com.rajan.ecommerce.presentation.feature.detailsscreen.DetailsScreenViewModel
import com.rajan.ecommerce.presentation.feature.favouritescreen.FavouriteScreen
import com.rajan.ecommerce.presentation.feature.favouritescreen.FavouriteScreenViewModel
import com.rajan.ecommerce.presentation.feature.home.HomeEvent
import com.rajan.ecommerce.presentation.feature.home.ui.HomeScreen
import com.rajan.ecommerce.presentation.feature.home.HomeViewModel
import com.rajan.ecommerce.presentation.feature.home.ui.SearchScreen
import com.rajan.ecommerce.presentation.feature.login.ui.LoginScreen
import com.rajan.ecommerce.presentation.feature.login.LoginScreenViewModel
import com.rajan.ecommerce.presentation.feature.pdp.PdpScreen
import com.rajan.ecommerce.presentation.feature.pdp.ProductsSearch
import com.rajan.ecommerce.presentation.feature.profilescreen.ProfileScreen
import com.rajan.ecommerce.presentation.feature.profilescreen.ProfileScreenViewModel
import com.rajan.ecommerce.presentation.feature.signup.SignupViewModel
import com.rajan.ecommerce.presentation.feature.signup.ui.SignupScreen
import com.rajan.ecommerce.presentation.feature.welcomescreen.WelcomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val hasSeen by OnboardingDataStore.hasSeenWelcomeFlow(context).collectAsState(initial = false)
    val startDestination = remember(hasSeen) {
        if (hasSeen) Routes.HomeScreen() else Routes.WelcomeScreen
    }
//
//
    val cartViewModel: CartViewModel = hiltViewModel(
        viewModelStoreOwner = context as ComponentActivity
    )
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen()
    ) {
        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController, hasSeen)
        }
        composable<Routes.HomeScreen> {
            val viewModel: HomeViewModel = hiltViewModel()
            val args = it.toRoute<Routes.HomeScreen>()
            HomeScreen(navController, viewModel, cartViewModel, args.showPaymentSuccess)
        }
        composable<Routes.DetailsScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Routes.DetailsScreen>()
            val viewModel: DetailsScreenViewModel = hiltViewModel()

            DetailsScreen(
                productId = args.productId,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable<Routes.MyCart> {
//            val viewModel: CartViewModel = hiltViewModel()
            MyCart(navController, cartViewModel)
        }

        composable<Routes.CartScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Routes.CartScreen>()
            CartScreen(navController, cartViewModel, args.isScan)
        }
        composable<Routes.FavouriteScreen> {
            val viewModel: FavouriteScreenViewModel = hiltViewModel()
            FavouriteScreen(navController, viewModel, cartViewModel)
        }
        composable<Routes.ProfileScreen> {
            val viewModel: ProfileScreenViewModel = hiltViewModel()
            ProfileScreen(navController, viewModel, cartViewModel)
        }
        composable<Routes.LoginScreen> {
            val viewModel: LoginScreenViewModel = hiltViewModel()
            LoginScreen(navController, viewModel)
        }

        composable<Routes.SignupScreen> {
            val viewModel: SignupViewModel = hiltViewModel()
            SignupScreen(navController, viewModel)
        }

        composable<Routes.SearchScreen> {
        }

        composable<Routes.PDPScreen> { backStack ->
            // Try to get the product passed via savedStateHandle (from Home or Search)
            val product = navController.previousBackStackEntry?.savedStateHandle?.get<Products>("selected_product")

            // Fallback to your existing HomeViewModel logic if no product was passed directly
            val parentEntry = remember(backStack) {
                navController.getBackStackEntry(Routes.HomeScreen())
            }
            val homeViewModel: HomeViewModel = hiltViewModel(parentEntry)
            val uiStateHome by homeViewModel.uiState.collectAsState()

            PdpScreen(
                navController = navController,
                product = product ?: uiStateHome.selectedProduct,
                cartViewModel
            )
        }

        composable<Routes.MapAddress> {
            MapAddress(navController)
        }

        composable<Routes.AddAddress> {
            val args = it.toRoute<Routes.AddAddress>()
            AddAddress(navController, pinCode=args.pinCode, city=args.city,states = args.state, address = args.address)
        }

        composable<Routes.ProductsSearch> {
            ProductsSearch(navController, cartViewModel =cartViewModel)
        }

    }
}