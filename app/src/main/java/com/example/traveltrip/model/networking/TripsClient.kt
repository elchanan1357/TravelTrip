package com.example.traveltrip.model.networking

import com.example.traveltrip.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TripClient {
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(TripInterceptor())
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val TripApiClient: TripService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.AMADEUS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TripService::class.java)
    }
}
