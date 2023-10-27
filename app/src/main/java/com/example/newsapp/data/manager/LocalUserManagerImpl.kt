package com.example.newsapp.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsapp.domain.manager.LocalUserManager
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Constants.APP_ENTRY
import com.example.newsapp.utils.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context: Context // the data preference use Context to interact with app lifecycle
): LocalUserManager {
    override suspend fun saveAppEntry() { // when the app begins the first time this will be used to set the key value
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true //the key value will be set to true and persist
        }
    }

    override fun readAppEntry(): Flow<Boolean> { // this returns the value of the preference at every moment the app begins
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false // this will set the key value to false if it is null and if it is not null it will be set to the save value
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS) // This should only be called once and it Creates a property delegate for a single process DataStore

private object PreferencesKeys {
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)//This makes the key know to the data preference and sets it have a boolean value
}