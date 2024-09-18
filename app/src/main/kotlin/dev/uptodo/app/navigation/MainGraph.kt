package dev.uptodo.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.navigation.Route.Category
import dev.uptodo.app.navigation.Route.TaskDetails
import dev.uptodo.app.navigation.Route.Home
import dev.uptodo.app.navigation.Route.MainGraph
import dev.uptodo.app.ui.screens.home.HomeScreenStateful
import dev.uptodo.app.ui.screens.home.HomeViewModel
import dev.uptodo.app.util.daggerViewModel
import dev.uptodo.app.ui.screens.category.CategoryScreenStateful
import dev.uptodo.app.ui.screens.category.CategoryViewModel
import dev.uptodo.app.ui.screens.task.TaskScreenStateful
import dev.uptodo.app.ui.screens.task.TaskViewModel
import dev.uptodo.domain.model.Task
import kotlin.reflect.typeOf

fun NavGraphBuilder.mainGraph(
    appComponent: AppComponent,
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    navigation<MainGraph>(startDestination = Home) {
        composable<Home> {
            val homeViewModel: HomeViewModel = daggerViewModel {
                appComponent.homeComponent().build().getViewModel()
            }

            HomeScreenStateful(
                viewModel = homeViewModel,
                navController = navController,
                onShowSnackbar = onShowSnackbar
            )
        }

        composable<Category> {
            val categoryViewModel: CategoryViewModel = daggerViewModel {
                appComponent.categoryComponent().build().getViewModel()
            }

            CategoryScreenStateful(
                viewModel = categoryViewModel,
                navController = navController
            )
        }

        composable<TaskDetails>(
            typeMap = mapOf(typeOf<Task>() to CustomNavType.TaskNavType)
        ) {
            val args = it.toRoute<TaskDetails>()

            val taskViewModel: TaskViewModel = daggerViewModel {
                appComponent.taskComponent()
                    .task(args.task)
                    .taskId(args.id)
                    .build()
                    .getViewModel()
            }

            TaskScreenStateful(
                viewModel = taskViewModel,
                navController = navController
            )
        }
    }
}