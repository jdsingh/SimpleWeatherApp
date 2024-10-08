package me.jagdeep.playground.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class LastSearchCityRepository(private val dataStore: DataStore<Preferences>) {

    private val lastSearchCityKey = stringPreferencesKey("last_search_city")

    suspend fun getLastSearchCity(): String? {
        return dataStore.data.first()[lastSearchCityKey]
    }

    suspend fun saveLastSearchCity(city: String) {
        dataStore.edit { preferences ->
            preferences[lastSearchCityKey] = city
        }
    }
}
