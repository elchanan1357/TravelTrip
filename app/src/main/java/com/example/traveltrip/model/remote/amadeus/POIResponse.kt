package com.example.traveltrip.model.remote.amadeus

data class POIResponse(
    val data: List<POIItem>
)

data class POIItem(
    val id: String,
    val name: String,
    val geoCode: GeoCode,
    val category: String,
    val tags: List<String>?,
    val distance: Int?
)

data class GeoCode(
    val latitude: Double,
    val longitude: Double
)