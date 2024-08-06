package dev.uptodo.app

import android.app.Application
import androidx.compose.runtime.compositionLocalOf
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.di.DaggerAppComponent
import dev.uptodo.domain.model.User

class UpTodoApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    fun getAppComponent() = appComponent
}