package dev.uptodo.app.util.reminder

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class TaskReminderReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {

        val taskTitle = intent.getStringExtra(TASK_TITLE).orEmpty()
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, DEFAULT_NOTIFICATION_ID)

        val notification = createTaskNotification(context, taskTitle)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationId, notification)
    }

    companion object {
        const val TASK_TITLE = "task_title"
        const val NOTIFICATION_ID = "notification_id"
        const val DEFAULT_NOTIFICATION_ID = 0
    }
}