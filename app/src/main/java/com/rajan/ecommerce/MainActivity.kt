package com.rajan.ecommerce

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rajan.ecommerce.presentation.navigation.NavGraph
import com.rajan.ecommerce.presentation.navigation.Routes
import com.rajan.ecommerce.presentation.theme.EcommerceTheme
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            EcommerceTheme {
                navController = rememberNavController() // Initialize here
                NavGraph(navController)
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        // Use the main thread to navigate
        runOnUiThread {
            navController.navigate(Routes.HomeScreen(showPaymentSuccess = true)) {
                // Clear the stack so Home becomes the root
                popUpTo(Routes.HomeScreen(showPaymentSuccess = false)) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    override fun onPaymentError(p0: Int, response: String?) {
        Log.d("Payment", "Eoor: $response")
    }

    fun startPayment() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_Sav0Oju7vyN69b") // Get from Razorpay Dashboard

        try {
            val options = JSONObject().apply {
                put("name", "Ecommerce App")
                put("description", "Charge for Order #123")
                put("currency", "INR")
                put("amount", 10000) // Amount in subunits (e.g., 10000 = ₹100)
                put("prefill.email", "rajank879@gmail.com")
                put("prefill.contact", "+917717755329")
            }
            checkout.open(this, options)
        } catch (e: Exception) {
            Log.e("Razorpay", "Error: ${e.message}")
        }
    }

}

