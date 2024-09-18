package dev.uptodo.app.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime

val LocalDateTime.Companion.default: LocalDateTime
    get() = Clock.System.now()
        .toLocalDateTime(TimeZone.UTC)
        .date
        .atTime(LocalTime(23, 59))