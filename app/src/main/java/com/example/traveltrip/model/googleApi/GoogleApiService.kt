package com.example.traveltrip.model.googleApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApiService {
    @GET("place/nearbysearch/json")
    fun getAttractions(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "tourist_attraction",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>


    @GET("place/nearbysearch/json")
    fun getParks(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "park",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>


    @GET("place/nearbysearch/json")
    fun getMuseums(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "museum",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>


    @GET("place/nearbysearch/json")
    fun getRestaurants(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "restaurant",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>


    @GET("place/nearbysearch/json")
    fun getCafes(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "cafe",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>


    @GET("place/nearbysearch/json")
    fun getHotels(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "lodging",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>


    @GET("place/nearbysearch/json")
    fun getBeaches(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "beach",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>


    @GET("place/nearbysearch/json")
    fun getBars(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "bar",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>


    @GET("place/nearbysearch/json")
    fun getShoppingCenters(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "shopping_mall",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>


    @GET("place/nearbysearch/json")
    fun getZoos(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "zoo",
        @Query("key") apiKey: String
    ): Call<GoogleResponse>
}
