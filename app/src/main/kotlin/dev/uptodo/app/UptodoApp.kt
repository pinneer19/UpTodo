package dev.uptodo.app

import android.app.Application
import dev.uptodo.app.di.AppComponent
import dev.uptodo.app.di.DaggerAppComponent
import dev.uptodo.app.util.reminder.createNotificationChannel

class UpTodoApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()

        createNotificationChannel(context = this)
    }

    fun getAppComponent() = appComponent
}