package com.example.traveltrip.utils

import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding
import com.example.traveltrip.R
import com.squareup.picasso.Picasso

fun isNull(et: EditText?): Boolean {
    if (et?.text.isNullOrBlank()) {
        et?.error = "This filed is required"
        return true
    }

    return false
}

fun validateFields(vararg fields: FieldValidation): Boolean {
    var allValid = true
    for (field in fields) {
        val input = field.editText?.text ?: ""
        if (input.isBlank()) {
            field.editText?.error = field.errorMessage
            allValid = false
        }
    }

    return allValid
}


fun log(message: String) {
    Log.d("logs", message)
}

fun logError(error: String) {
    Log.e("logs", error)
}

fun getPicFromPicasso(img: ImageView?, url: String?) {
    if (url?.isNotBlank() == true) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.profile)
            .into(img)
    }
}