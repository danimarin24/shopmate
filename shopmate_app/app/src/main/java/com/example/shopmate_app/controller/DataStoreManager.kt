package com.example.shopmate_app.controller

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "THEME_KEY")
    private val dataStore = context.dataStore

    companion object {
        val darkModeKey = booleanPreferencesKey("DARK_MODE_KEY")
        val userIdKey = intPreferencesKey("USER_ID_KEY")
    }

    suspend fun setTheme(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[darkModeKey] = isDarkMode
        }
    }

    fun getTheme(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val uiMode = preferences[darkModeKey] ?: false
                uiMode
            }
    }

    suspend fun setUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[userIdKey] = userId
        }
    }

    fun getUserId() = dataStore.data.map {preferences ->
        preferences[userIdKey]
    }

}