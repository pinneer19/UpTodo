package dev.uptodo.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.navigation.MainRoute.Category
import dev.uptodo.app.navigation.MainRoute.Home
import dev.uptodo.app.navigation.MainRoute.TaskDetails
import dev.uptodo.app.navigation.Route.MainGraph
import dev.uptodo.app.ui.screens.category.CategoryScreenStateful
import dev.uptodo.app.ui.screens.category.viewmodel.CategoryViewModel
import dev.uptodo.app.ui.screens.home.HomeScreenStateful
import dev.uptodo.app.ui.screens.home.viewmodel.HomeViewModel
import dev.uptodo.app.ui.screens.task.TaskScreenStateful
import dev.uptodo.app.ui.screens.task.viewmodel.TaskViewModel
import dev.uptodo.app.di.util.daggerViewModel
import dev.uptodo.domain.model.Task
import dev.uptodo.domain.model.TaskCategory
import kotlin.reflect.typeOf

fun NavGraphBuilder.mainGraph(
    appComponent: AppComponent,
    navController: NavController,
) {
    navigation<MainGraph>(startDestination = Home) {
        composable<Home> {
            val homeViewModel: HomeViewModel = daggerViewModel {
                appComponent.homeComponent().build().getHomeViewModel()
            }

            HomeScreenStateful(
                viewModel = homeViewModel,
                navController = navController,
            )
        }

        composable<Category>(
            typeMap = mapOf(typeOf<TaskCategory?>() to CustomNavType.TaskCategoryNavType)
        ) {
            val args = it.toRoute<Category>()

            val categoryViewModel: CategoryViewModel = daggerViewModel {
                appComponent.categoryComponent()
                    .category(args.category)
                    .categoryId(args.id)
                    .build()
                    .getViewModel()
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