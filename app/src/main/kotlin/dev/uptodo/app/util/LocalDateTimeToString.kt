package dev.uptodo.app.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.toUiString(considerDate: Boolean = false) = with(this) {
    val formattedHours = hour.toString().padStart(2, '0')
    val formattedMinutes = minute.toString().padStart(2, '0')

    if (considerDate && this.date != Clock.System.now().toLocalDateTime(TimeZone.UTC).date) {
        "$date at ${formattedHours}:${formattedMinutes}"
    } else {
        "Today at ${formattedHours}:${formattedMinutes}"
    }
}