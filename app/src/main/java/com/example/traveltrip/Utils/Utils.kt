package com.example.traveltrip.Utils

import android.util.Log
import android.widget.EditText

fun isNull(et: EditText?): Boolean {
    if (et?.text.isNullOrBlank()) {
        et?.error = "This filed is required"
        return true
    }

    return false
}

fun log(message: String) {
    Log.d("logs", message)
}

fun logError(error: String) {
    Log.e("logs", error)
}
