package com.example.filmkuapps.ui.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(
    inputFormat: String = "yyyy-MM-dd",
    outputFormat: String = "EEEE, d MMMM yyyy",
    locale: Locale = Locale("us", "US")
): String {
    return try {
        val inputSdf = SimpleDateFormat(inputFormat, Locale.US)
        val date = inputSdf.parse(this) ?: return this
        val outputSdf = SimpleDateFormat(outputFormat, locale)
        outputSdf.format(date)
    } catch (e: Exception) {
        this // Return original jika error
    }
}
