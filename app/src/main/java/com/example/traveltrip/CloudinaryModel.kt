package com.example.traveltrip

import android.graphics.Bitmap
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.policy.GlobalUploadPolicy
import com.example.traveltrip.base.MyApp
import com.example.traveltrip.utils.UriCallback
import com.example.traveltrip.utils.toFile
import java.io.File

class CloudinaryModel {
    private val context = MyApp.Globals.context

    init {
        val config = mapOf(
            "cloud_name" to "dimipopyn",
            "api_key" to "268912183538911",
            "api_secret" to "299D7CaW5lwObEr0MnyfFolFilI"
        )

        context?.let {
            MediaManager.init(it, config)
            MediaManager.get().globalUploadPolicy = GlobalUploadPolicy.defaultPolicy()
        }
    }

    fun uploadImg(
        bitmap: Bitmap,
        name: String,
        onSuccess: UriCallback,
        onError: UriCallback
    ) {
        val context = context ?: return
        val file: File = bitmap.toFile(context, name)

        MediaManager.get().upload(file.path)
            .option("folder", "images")
            .callback(object : UploadCallback {
                override fun onStart(requestId: String?) {}

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

                override fun onSuccess(requestId: String?, resultData: MutableMap<*, *>?) {
                    val url = resultData?.get("secure_url") as? String ?: ""
                    onSuccess(url)
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    onError(error?.description ?: "Unknown error")
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {}

            })
            .dispatch()


    }


}

