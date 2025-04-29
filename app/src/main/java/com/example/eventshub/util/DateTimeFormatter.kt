package com.example.eventshub.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatMillisToReadableDateTime(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a")
    val dateTime = Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    return dateTime.format(formatter)
}
