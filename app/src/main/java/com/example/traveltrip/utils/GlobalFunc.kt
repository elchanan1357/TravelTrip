package com.example.traveltrip.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.traveltrip.R
import com.example.traveltrip.base.MyApp
import com.squareup.picasso.Picasso


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
            .resize(300, 300)
            .centerCrop()
            .placeholder(R.color.black)
            .into(img)
    }
}

fun launchCameraForImage(
    fragment: Fragment,
    imageView: ImageView?,
    click: View?,
    onComplete: (Bitmap?) -> Unit
) {
    val launcher =
        fragment.registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            imageView?.setImageBitmap(bitmap)
            onComplete(bitmap)
        }

    click?.setOnClickListener {
        launcher.launch(null)
    }
}


@SuppressLint("InflateParams")
fun createToast(message: String) {
    val context = MyApp.Globals.context
    val inflater = LayoutInflater.from(context)
    val layout = inflater.inflate(R.layout.custom_toast, null, false)

    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    val toast = Toast(context)
    toast.duration = Toast.LENGTH_LONG
    toast.view = layout
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

