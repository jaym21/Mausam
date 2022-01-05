package dev.jaym21.mausam.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        fun convertUnixToDate(unix: String): String {
            val unixSeconds = unix.toLong()
            //convert seconds to milliseconds
            val date = Date(unixSeconds * 1000L)
            //format of the date
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
            return sdf.format(date)
        }

        fun convertUnixToDay(unix: String): String {
            val unixSeconds = unix.toLong()
            //convert seconds to milliseconds
            val date = Date(unixSeconds * 1000L)
            //format of the date
            val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
            return sdf.format(date)
        }
    }
}