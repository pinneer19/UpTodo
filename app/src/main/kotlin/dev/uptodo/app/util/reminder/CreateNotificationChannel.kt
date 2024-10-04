package dev.uptodo.app.util.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import dev.uptodo.app.R

fun createNotificationChannel(context: Context) {
    val channelImportance = NotificationManager.IMPORTANCE_DEFAULT
    val channelName = context.getString(R.string.task_notification_channel_name)
    val channelDescription = context.getString(R.string.task_notification_channel_description)
    val channelId = context.getString(R.string.task_notification_channel_id)

    val channel = NotificationChannel(
        channelId,
        channelName,
        channelImportance
    ).apply {
        description = channelDescription
    }

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}