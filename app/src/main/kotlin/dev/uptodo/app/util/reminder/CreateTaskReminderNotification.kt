package dev.uptodo.app.util.reminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dev.uptodo.app.util.Constants
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import java.util.UUID

@SuppressLint("ScheduleExactAlarm")
fun scheduleTaskReminder(
    context: Context,
    taskId: String,
    taskTitle: String,
    taskDeadline: LocalDateTime
) {
    val intent = Intent(context, TaskReminderReceiver::class.java).apply {
        putExtra(TaskReminderReceiver.TASK_TITLE, taskTitle)
        putExtra(TaskReminderReceiver.NOTIFICATION_ID, taskId)
    }

    val requestCode = UUID.fromString(taskId).hashCode()

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val triggerAtMillis = taskDeadline
        .toInstant(timeZone = TimeZone.currentSystemDefault())
        .minus(Constants.OneHourMillis, DateTimeUnit.MILLISECOND)
        .toEpochMilliseconds()

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
}