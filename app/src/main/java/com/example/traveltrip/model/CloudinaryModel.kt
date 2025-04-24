package com.example.traveltrip.model

import android.graphics.Bitmap
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.policy.GlobalUploadPolicy
import com.example.traveltrip.BuildConfig
import com.example.traveltrip.base.MyApp
import com.example.traveltrip.utils.UriCallback
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.toFile
import java.io.File

class CloudinaryModel {
    private val context = MyApp.Globals.context

    init {
        val config = mapOf(
            "cloud_name" to BuildConfig.CLOUD_NAME,
            "api_key" to BuildConfig.API_KEY,
            "api_secret" to BuildConfig.API_SECRET
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
        log("step1")
        val context = context ?: return
        val file: File = bitmap.toFile(context, name)
        log("step2")
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

