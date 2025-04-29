package com.example.traveltrip.model.amadeus.amadeusClasses.networking

import okhttp3.OkHttpClient

object OkHttp {
     val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(TripInterceptor())
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}