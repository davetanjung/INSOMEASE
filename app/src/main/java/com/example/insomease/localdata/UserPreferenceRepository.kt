package com.example.insomease.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        private val WAKE_UP_TIME = stringPreferencesKey("wake_up_time")
    }

    val wakeUpTime: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[WAKE_UP_TIME] ?: "00:00" // Default value
        }

    suspend fun saveWakeUpTime(time: String) {
        context.dataStore.edit { preferences ->
            preferences[WAKE_UP_TIME] = time
        }
    }
}