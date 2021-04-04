package com.simplechat.android.framework.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun LocalDateTime.toEpochMillis(): Long {
    return this.atZone(
        ZoneId.systemDefault()
    ).toInstant().toEpochMilli()
}

fun ofEpochMillisToLocalDateTime(millis: Long): LocalDateTime {
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun LocalDateTime.getBestFormat(): String {

    val dateNow = LocalDate.now()

    return if(dateNow.equals(this.toLocalDate())) {
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            .format(this)
    }
    else {
        DateTimeFormatter.ISO_DATE.format(this)
    }
}