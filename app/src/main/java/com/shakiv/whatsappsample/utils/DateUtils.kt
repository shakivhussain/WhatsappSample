package com.shakiv.whatsappsample.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun getFormattedDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

    fun getFormattedTime(timestamp: Long): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(Date(timestamp))
    }
}