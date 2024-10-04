package dev.uptodo.app.util.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.uptodo.app.util.Constants.DEFAULT_CATEGORIES_INSERTED
import dev.uptodo.domain.usecase.InitializeTaskCategoriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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