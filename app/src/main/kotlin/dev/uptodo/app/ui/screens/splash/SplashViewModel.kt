package dev.uptodo.app.ui.screens.splash

import androidx.lifecycle.ViewModel
import dev.uptodo.app.di.splash.SplashScope
import dev.uptodo.domain.repository.AccountService
import javax.inject.Inject

@SplashScope
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    fun onAppStart(
        onNavigateToAuthGraph: () -> Unit,
        onNavigateToMainGraph: () -> Unit
    ) {
        if (accountService.hasUser()) {
            onNavigateToMainGraph()
        } else {
            onNavigateToAuthGraph()
        }
    }
}