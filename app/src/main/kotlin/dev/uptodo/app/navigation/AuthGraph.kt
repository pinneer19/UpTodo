package dev.uptodo.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.ui.screens.login.LoginViewModel
import dev.uptodo.app.ui.screens.register.RegisterViewModel
import dev.uptodo.app.util.daggerViewModel
import dev.uptodo.app.navigation.Route.AuthGraph
import dev.uptodo.app.navigation.Route.Login
import dev.uptodo.app.navigation.Route.Register
import dev.uptodo.app.navigation.Route.PasswordReset
import dev.uptodo.app.ui.screens.login.LoginScreenStateful
import dev.uptodo.app.ui.screens.passwordReset.PasswordResetScreenStateful
import dev.uptodo.app.ui.screens.passwordReset.PasswordResetViewModel
import dev.uptodo.app.ui.screens.register.RegisterScreenStateful

fun NavGraphBuilder.authGraph(
    appComponent: AppComponent,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    navigation<AuthGraph>(startDestination = Login) {
        composable<Login> {
            val loginViewModel: LoginViewModel = daggerViewModel {
                appComponent.loginComponent().build().getViewModel()
            }

            LoginScreenStateful(
                viewModel = loginViewModel,
                navController = navController,
                onShowSnackbar = onShowSnackbar
            )
        }

        composable<Register> {
            val registerViewModel: RegisterViewModel = daggerViewModel {
                appComponent.registerComponent().build().getViewModel()
            }

            RegisterScreenStateful(
                viewModel = registerViewModel,
                navController = navController,
                onShowSnackbar = onShowSnackbar
            )
        }

        composable<PasswordReset> {
            val passwordResetViewModel: PasswordResetViewModel = daggerViewModel {
                appComponent.passwordResetComponent().build().getViewModel()
            }

            PasswordResetScreenStateful(
                viewModel = passwordResetViewModel,
                navController = navController
            )
        }
    }
}