package dev.uptodo.app.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

object Constants {

    const val SPLASH_TIMEOUT = 1000L

    const val DATASTORE_FILE = "app_prefs"

    val DEFAULT_CATEGORIES_INSERTED = booleanPreferencesKey("DEFAULT_CATEGORIES_INSERTED")

    const val DEFAULT_ICON_PACKAGE = "Icons.Default"

    @OptIn(ExperimentalMaterial3Api::class)
    val SelectableDates: SelectableDates
        get() = object : SelectableDates {

            val timeZone = TimeZone.currentSystemDefault()
            val currentInstant = Clock.System.todayIn(timeZone).atStartOfDayIn(timeZone)

            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return currentInstant.toEpochMilliseconds() <= utcTimeMillis
            }

            override fun isSelectableYear(year: Int): Boolean {
                val currentYear = currentInstant
                    .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
                    .year

                return currentYear <= year
            }
        }

    val CurrentDate: LocalDate
        get() = Clock.System.now()
            .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
            .date

    val DefaultDeadline: LocalDateTime
        get() = CurrentDate.atTime(time = LocalTime(23, 59))

    val OneHourMillis: Long
        get() = 1 * 60 * 60 * 1000L
}