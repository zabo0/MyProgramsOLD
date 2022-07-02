package com.saboon.myprograms.Utils

import java.text.SimpleDateFormat
import java.util.*

class DayConverter {


    fun getDay(dayInMillis: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = dayInMillis!!
        return SimpleDateFormat("dd MMMM yyyy").format(c.time)
        //SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
    }
}