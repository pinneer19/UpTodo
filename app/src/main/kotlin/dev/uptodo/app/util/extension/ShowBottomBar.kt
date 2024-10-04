package dev.uptodo.app.util.extension

import dev.uptodo.app.navigation.AuthRoute
import dev.uptodo.app.navigation.MainRoute
import dev.uptodo.app.navigation.Route

fun String.showBottomBar(): Boolean {
    return this !in listOf(
        Route.Splash::class.qualifiedName.toString(),
        AuthRoute.Login::class.qualifiedName.toString(),
        AuthRoute.Register::class.qualifiedName.toString(),
        AuthRoute.PasswordReset::class.qualifiedName.toString(),
        MainRoute.Category::class.qualifiedName.toString(),
        MainRoute.TaskDetails::class.qualifiedName.toString()
    )
}