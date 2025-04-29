package com.example.traveltrip.model.amadeus.amadeusClasses.networking


import com.example.traveltrip.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TripInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val token = runBlocking {
            AmadeusAuthClient.getValidAccessToken()
        }

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}








