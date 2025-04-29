package com.example.traveltrip.model.googleApi

import com.example.traveltrip.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoogleApiClient {
    val apiService: GoogleApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.GOOGLE_BASE_URL)
//            .client(OkHttp.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GoogleApiService::class.java)
    }
}
