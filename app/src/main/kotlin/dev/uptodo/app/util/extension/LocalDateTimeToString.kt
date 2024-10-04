package dev.uptodo.app.util.extension

import dev.uptodo.app.util.Constants
import kotlinx.datetime.LocalDateTime

fun LocalDateTime.toUiString(considerDate: Boolean = false) = with(this) {

    val formattedHours = hour.toTimeNumberString()
    val formattedMinutes = minute.toTimeNumberString()

    if (considerDate && this.date != Constants.CurrentDate) {
        "$date at ${formattedHours}:${formattedMinutes}"
    } else {
        "Today at ${formattedHours}:${formattedMinutes}"
    }
}

private fun Int.toTimeNumberString(): String =
    this.toString().padStart(length = 2, padChar = '0')