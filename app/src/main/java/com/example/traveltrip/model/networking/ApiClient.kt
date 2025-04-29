package com.example.traveltrip.model.networking

import com.example.traveltrip.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val poiApiClient: POIService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.AMADEUS_BASE_URL)
            .client(OkHttp.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(POIService::class.java)
    }

    val tripApiClient: TripService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.AMADEUS_BASE_URL)
            .client(OkHttp.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TripService::class.java)
    }
}