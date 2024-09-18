package dev.uptodo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.uptodo.app.navigation.UpTodoGraph
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.app.util.populateDatabase
import dev.uptodo.domain.usecase.InitializeTaskCategoriesUseCase
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Inject
    lateinit var initializeTaskCategoriesUseCase: InitializeTaskCategoriesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        val appComponent = (application as UpTodoApp).getAppComponent()
        appComponent.inject(this)

        populateDatabase(
            dataStore = dataStore,
            initializeTaskCategoriesUseCase = initializeTaskCategoriesUseCase
        )

        setContent {
            UpTodoTheme {
               UpTodoGraph(appComponent)
            }
        }
    }
}