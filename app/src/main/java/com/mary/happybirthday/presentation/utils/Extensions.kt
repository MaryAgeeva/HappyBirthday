package com.mary.happybirthday.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd LLLL yyyy"

fun Date.toDateString() : String {
    return SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(this)
}

