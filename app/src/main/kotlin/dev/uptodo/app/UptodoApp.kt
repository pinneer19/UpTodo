package dev.uptodo.app

import android.app.Application
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.di.DaggerAppComponent

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