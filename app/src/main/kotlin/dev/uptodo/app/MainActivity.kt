package dev.uptodo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import dev.uptodo.app.model.AuthState
import dev.uptodo.app.model.LocalAuthState
import dev.uptodo.app.navigation.UpTodoGraph
import dev.uptodo.app.ui.theme.UpTodoTheme
import dev.uptodo.app.util.populateDatabase
import dev.uptodo.domain.model.User
import dev.uptodo.domain.usecase.GetCurrentUserUseCase
import dev.uptodo.domain.usecase.InitializeTaskCategoriesUseCase
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Inject
    lateinit var initializeTaskCategoriesUseCase: InitializeTaskCategoriesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val appComponent = (application as UpTodoApp).getAppComponent()
        appComponent.inject(this)

        populateDatabase(
            dataStore = dataStore,
            initializeTaskCategoriesUseCase = initializeTaskCategoriesUseCase
        )

        setContent {
            var authState by remember {
                mutableStateOf(
                    AuthState(
                        isAuthenticated = false,
                        user = null
                    )
                )
            }

            LaunchedEffect(Unit) {
                repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                    getCurrentUserUseCase().collect { user: User? ->
                        authState = AuthState(user != null, user)
                    }
                }
            }

            UpTodoTheme {
                CompositionLocalProvider(LocalAuthState provides authState) {
                    UpTodoGraph(appComponent)
                }
            }
        }
    }
}