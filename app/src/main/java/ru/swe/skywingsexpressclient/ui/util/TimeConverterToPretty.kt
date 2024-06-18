package ru.swe.skywingsexpressclient.ui.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class TimeConverterToPretty {
    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        fun getTime(date: LocalDateTime) : String{
            return LocalDateTime.parse(date.toString()).format(DateTimeFormatter.ofPattern("HH:mm", Locale("ru")))
        }
        @RequiresApi(Build.VERSION_CODES.O)
        fun getDate(date: LocalDateTime) : String{
            return LocalDateTime.parse(date.toString()).format(DateTimeFormatter.ofPattern("d MMMM", Locale("ru")))
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getTimeBetween(start: LocalDateTime, end: LocalDateTime) : String{
            val duration = Duration.between(LocalDateTime.parse(start.toString()), LocalDateTime.parse(end.toString()))
            val hours = duration.toHours()
            val minutes = duration.toMinutes() % 60
            return "${hours}ч ${minutes}м"
        }
    }
}