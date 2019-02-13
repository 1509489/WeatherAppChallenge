package com.pixelart.weatherappchallenge.common

import java.text.SimpleDateFormat
import java.util.*

enum class Utils {
    INSTANCE;
    constructor()

    fun timestampToDateTime(timestamp: Long): String{
        val calender = Calendar.getInstance()
        val timeZone = calender.timeZone
        val dateFormat = SimpleDateFormat("E, dd MMMM yyyy HH:mm:ss", Locale.ENGLISH)
        calender.timeInMillis = timestamp * 1000L
        dateFormat.timeZone = timeZone

        return dateFormat.format(calender.time)
    }
}