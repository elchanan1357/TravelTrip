package com.example.traveltrip.model.remote.googleApi

import com.google.gson.annotations.SerializedName


data class GoogleResponse(
    val results: List<Place>
)

data class Place(
    val name: String,
    @SerializedName("formatted_address")
    val formattedAddress: String,
    val geometry: Geometry,
    val photos: List<Photo>?,
    val types: List<String>
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double
)

data class Photo(
    @SerializedName("photo_reference")
    val photoReference: String
)
