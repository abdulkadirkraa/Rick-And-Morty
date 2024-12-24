package com.abdulkadirkara.rickandmorty.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

// Extension function to format 'created' to a user-friendly format
fun String.toUserFriendlyDate(locale: Locale = Locale.getDefault()): String {
    return try {
        val isoFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
        val userFriendlyFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy EEEE, HH:mm", locale)
        ZonedDateTime.parse(this, isoFormatter).format(userFriendlyFormatter)
    } catch (e: Exception) {
        "Unknown Date" // In case of any parsing error
    }
}