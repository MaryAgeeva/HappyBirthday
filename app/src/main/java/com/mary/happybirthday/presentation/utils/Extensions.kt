package com.mary.happybirthday.presentation.utils

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd LLLL yyyy"

fun Date.toDateString() : String {
    return SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(this)
}

fun View.hide() {
    if(visibility != View.GONE)
        visibility = View.GONE
}

fun View.show() {
    if(visibility != View.VISIBLE)
        visibility = View.VISIBLE
}
