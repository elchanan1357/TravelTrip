package com.example.traveltrip.model.amadeus.amadeusClasses.networking

import com.example.traveltrip.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AmadeusAuthClient {

    private var token: String? = null
    private var tokenExpirationTime: Long = 0

    private val amadeusAuthService: AmadeusAuthService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.AMADEUS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(AmadeusAuthService::class.java)
    }


    fun getValidAccessToken(): String {
        val currentTime = System.currentTimeMillis()

        if (token == null || currentTime >= tokenExpirationTime) {
            val response = amadeusAuthService.getAccessToken(
                clientId = BuildConfig.AMADEUS_API_KEY,
                clientSecret = BuildConfig.AMADEUS_API_SECRET
            ).execute()

            if (response.isSuccessful) {
                val tokenResponse = response.body()!!
                token = tokenResponse.accessToken
                tokenExpirationTime =
                    currentTime + TimeUnit.SECONDS.toMillis(tokenResponse.expiresIn.toLong())
            } else {
                throw Exception("Failed to get access token: ${response.errorBody()?.string()}")
            }
        }
        return token!!
    }
}
