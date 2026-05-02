package com.rajan.ecommerce.presentation.feature.profilescreen

import android.content.Context
import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajan.ecommerce.data.local.datastore.OnboardingDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor() : ViewModel() {
    fun toggleTheme(isDark: Boolean, context: Context) {
        viewModelScope.launch {
            OnboardingDataStore.setDarkMode(context, isDark)
        }
    }
}