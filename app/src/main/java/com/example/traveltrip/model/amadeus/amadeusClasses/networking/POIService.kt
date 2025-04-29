package com.example.traveltrip.model.amadeus.amadeusClasses.networking

import com.example.traveltrip.model.amadeus.amadeusClasses.POIItem
import com.example.traveltrip.model.amadeus.amadeusClasses.POIResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface POIService {
    @GET("v1/reference-data/locations/pois")
    fun searchPOIs(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int,
        @Query("categories") categories: String?,
        @Query("keyword") keyword: String?,
        @Query("page[limit]") limit: Int = 50
    ): Call<POIResponse>


    @GET("v1/reference-data/locations/pois/{poiId}")
    fun getPOIDetails(
        @retrofit2.http.Path("poiId") poiId: String
    ): Call<POIItem>

}