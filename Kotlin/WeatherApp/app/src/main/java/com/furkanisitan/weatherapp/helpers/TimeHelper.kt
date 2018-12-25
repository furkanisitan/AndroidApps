package com.furkanisitan.weatherapp.helpers

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimeHelper {

    companion object {
        fun getCurrentDate(pattern: String = "EEEE , dd MMMM yyyy", language: String = "tr"): String {
            val calendar = Calendar.getInstance()
            val format = SimpleDateFormat(pattern, Locale(language))
            return format.format(calendar.time)
        }
    }
}