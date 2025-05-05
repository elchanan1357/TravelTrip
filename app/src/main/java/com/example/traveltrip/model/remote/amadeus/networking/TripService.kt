package com.example.traveltrip.model.remote.amadeus.networking

import com.example.traveltrip.model.remote.amadeus.TripItem
import com.example.traveltrip.model.remote.amadeus.TripsAmadeus
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TripService {
    @GET("v1/shopping/activities")
    fun getTrips(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int = 20,
        @Query("page[limit]") limit: Int = 20
    ): Call<TripsAmadeus>

    @GET("v1/shopping/activities")
    fun getTripsByCategory(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int = 20,
        @Query("category") category: String,
        @Query("page[limit]") limit: Int = 20
    ): Call<TripsAmadeus>


    @GET("v1/shopping/activities/{activityId}")
    fun getTripById(
        @Path("activityId") activityId: String
    ): Call<TripItem>


    @GET("v1/shopping/activities")
    fun getTripByCity(
        @Query("cityCode") cityCode: String,
        @Query("page[limit]") limit: Int = 20
    ): Call<TripsAmadeus>

    @GET("v1/shopping/activities")
    fun getTripsByDateRange(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int = 20,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("page[limit]") limit: Int = 20
    ): Call<TripsAmadeus>
}
