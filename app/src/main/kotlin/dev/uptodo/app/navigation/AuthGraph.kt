package dev.uptodo.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.navigation.AuthRoute.Login
import dev.uptodo.app.navigation.AuthRoute.PasswordReset
import dev.uptodo.app.navigation.AuthRoute.Register
import dev.uptodo.app.navigation.Route.AuthGraph
import dev.uptodo.app.ui.screens.login.LoginScreenStateful
import dev.uptodo.app.ui.screens.login.viewmodel.LoginViewModel
import dev.uptodo.app.ui.screens.passwordReset.PasswordResetScreenStateful
import dev.uptodo.app.ui.screens.passwordReset.viewmodel.PasswordResetViewModel
import dev.uptodo.app.ui.screens.register.RegisterScreenStateful
import dev.uptodo.app.ui.screens.register.viewmodel.RegisterViewModel
import dev.uptodo.app.di.util.daggerViewModel

fun NavGraphBuilder.authGraph(
    appComponent: AppComponent,
    navController: NavController
) {
    navigation<AuthGraph>(startDestination = Login) {
        composable<Login> {
            val loginViewModel: LoginViewModel = daggerViewModel {
                appComponent.loginComponent().build().getViewModel()
            }

            LoginScreenStateful(
                viewModel = loginViewModel,
                navController = navController
            )
        }

        composable<Register> {
            val registerViewModel: RegisterViewModel = daggerViewModel {
                appComponent.registerComponent().build().getViewModel()
            }

            RegisterScreenStateful(
                viewModel = registerViewModel,
                navController = navController
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