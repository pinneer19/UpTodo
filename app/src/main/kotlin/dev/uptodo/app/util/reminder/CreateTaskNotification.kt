package dev.uptodo.app.util.reminder

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import dev.uptodo.app.R

fun createTaskNotification(context: Context, taskTitle: String): Notification {
    return NotificationCompat.Builder(
        context,
        context.getString(R.string.task_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(context.getString(R.string.task_notification_title))
        .setContentText(context.getString(R.string.task_notification_text, taskTitle))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}