package com.example.traveltrip.utils

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.traveltrip.R
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
            .placeholder(R.drawable.profile)
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

