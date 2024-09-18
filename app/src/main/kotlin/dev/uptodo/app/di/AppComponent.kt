package dev.uptodo.app.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.uptodo.app.MainActivity
import dev.uptodo.app.di.main.home.HomeComponent
import dev.uptodo.app.di.auth.login.LoginComponent
import dev.uptodo.app.di.auth.passwordReset.PasswordResetComponent
import dev.uptodo.app.di.auth.register.RegisterComponent
import dev.uptodo.app.di.main.category.CategoryComponent
import dev.uptodo.app.di.main.task.TaskComponent
import dev.uptodo.app.di.splash.SplashComponent
import dev.uptodo.data.di.DataModule
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class])
@Singleton
interface AppComponent {

    fun splashComponent(): SplashComponent.Builder

    fun loginComponent(): LoginComponent.Builder

    fun registerComponent(): RegisterComponent.Builder

    fun homeComponent(): HomeComponent.Builder

    fun passwordResetComponent(): PasswordResetComponent.Builder

    fun categoryComponent(): CategoryComponent.Builder

    fun taskComponent(): TaskComponent.Builder

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}