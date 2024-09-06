package dev.uptodo.app.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dev.uptodo.domain.usecase.InitializeTaskCategoriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal fun populateDatabase(
    dataStore: DataStore<Preferences>,
    initializeTaskCategoriesUseCase: InitializeTaskCategoriesUseCase
) {
    CoroutineScope(Dispatchers.IO).launch {
        val DEFAULT_CATEGORIES_INSERTED = booleanPreferencesKey("DEFAULT_CATEGORIES_INSERTED")

        val preferences = dataStore.data.first()

        val inserted = preferences[DEFAULT_CATEGORIES_INSERTED] ?: false
        println(inserted)
        if (!inserted) {
            initializeTaskCategoriesUseCase()

            dataStore.edit { prefs ->
                prefs[DEFAULT_CATEGORIES_INSERTED] = true
            }
        }
    }
}