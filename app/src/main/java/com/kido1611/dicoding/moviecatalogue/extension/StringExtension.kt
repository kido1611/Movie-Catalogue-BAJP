package com.kido1611.dicoding.moviecatalogue.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.toReadableDateFormat(): String {
    val dateFormatGmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    dateFormatGmt.timeZone = TimeZone.getTimeZone("GMT")

    val dateFormatIndonesia = SimpleDateFormat("d MMMM yyyy", Locale("ind", "IDN"))

    val gmtDate = dateFormatGmt.parse(this)
    gmtDate?.let {
        return dateFormatIndonesia.format(it)
    }

    return this
}