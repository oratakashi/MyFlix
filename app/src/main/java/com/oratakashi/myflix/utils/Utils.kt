package com.oratakashi.myflix.utils

import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Throws(ParseException::class)
fun TextView.dateFormat(date: String, input: String, output: String) {
    var format = SimpleDateFormat(input, Locale.getDefault())
    var newDate: Date? = null

    newDate = format.parse(date)

    format = SimpleDateFormat(output, Locale.getDefault())

    this.text = format.format(newDate)
}