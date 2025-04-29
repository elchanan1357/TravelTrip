package com.example.traveltrip.model.amadeus.amadeusClasses.networking

import com.example.traveltrip.model.amadeus.amadeusClasses.AmadeusTokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AmadeusAuthService {
    @FormUrlEncoded
    @POST("v1/security/oauth2/token")
    fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Call<AmadeusTokenResponse>
}
