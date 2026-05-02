package com.rajan.ecommerce

import android.app.Application
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EcommerceApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext,"AIzaSyA6e191dVE0EMKbLdwdZ5ONqAh4q2sPn4c")
    }
}
