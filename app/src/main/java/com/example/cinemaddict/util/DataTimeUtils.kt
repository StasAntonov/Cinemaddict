package com.example.cinemaddict.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

enum class DateTimePattern(val value: String) {
    DEFAULT("yyyy-MM-dd"),
}

object DateTimeUtils {

    fun formatDateTime(
        milliseconds: Long,
        pattern: DateTimePattern = DateTimePattern.DEFAULT
    ): String {
        return formatDateTime(Date(milliseconds), pattern)
    }

    fun formatDateTime(date: Date, pattern: DateTimePattern = DateTimePattern.DEFAULT): String {
        val format = SimpleDateFormat(pattern.value, Locale.ROOT)
        return format.format(date)
    }

    fun toLocalDate(dateString: String?): LocalDate? {
        return if (!dateString.isNullOrEmpty()) {
            val serverDateFormat = DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT.value)
            LocalDate.parse(dateString, serverDateFormat)
        } else null
    }
}

