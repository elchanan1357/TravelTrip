package com.example.traveltrip.model.amadeusClasses

import com.google.gson.annotations.SerializedName

data class AmadeusTokenResponse(
    val type: String,
    val username: String,
    @SerializedName("application_name")
    val applicationName: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    val state: String,
    val scope: String?
)


