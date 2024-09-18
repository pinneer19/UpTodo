package dev.uptodo.app.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dev.uptodo.domain.usecase.InitializeTaskCategoriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val DEFAULT_CATEGORIES_INSERTED = booleanPreferencesKey("DEFAULT_CATEGORIES_INSERTED")

internal fun populateDatabase(
    dataStore: DataStore<Preferences>,
    initializeTaskCategoriesUseCase: InitializeTaskCategoriesUseCase
) {
    CoroutineScope(Dispatchers.IO).launch {
        dataStore.edit { preferences ->
            val inserted = preferences[DEFAULT_CATEGORIES_INSERTED] ?: false

            if (!inserted) {
                initializeTaskCategoriesUseCase()
                preferences[DEFAULT_CATEGORIES_INSERTED] = true
            }
        }
    }
}