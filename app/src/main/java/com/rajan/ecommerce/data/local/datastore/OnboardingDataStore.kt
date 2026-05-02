package com.rajan.ecommerce.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

object OnboardingDataStore {
    private val KEY_SEEN_WELCOME = booleanPreferencesKey("has_seen_welcome")

    private val KEY_DARK_MODE = booleanPreferencesKey("is_dark_mode")

    // Flow to observe theme changes
    fun isDarkMode(context: Context): Flow<Boolean?> =
        context.dataStore.data.map { it[KEY_DARK_MODE] }

    // Function to save theme choice
    suspend fun setDarkMode(context: Context, isDark: Boolean) {
        context.dataStore.edit { it[KEY_DARK_MODE] = isDark }
    }
    fun hasSeenWelcomeFlow(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs -> prefs[KEY_SEEN_WELCOME] ?: false }

    suspend fun setHasSeenWelcome(context: Context, seen: Boolean) {
        context.dataStore.edit { prefs -> prefs[KEY_SEEN_WELCOME] = seen }
    }
}
