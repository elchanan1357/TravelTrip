package com.example.traveltrip.model.amadeusClasses


data class TripsAmadeus(
    val data: List<TripItem>
)

data class TripItem(
    val id: String,
    val name: String,
    val description: String?,
    val price: Price?,
    val geoCode: TripGeoCode,
    val category: List<String>?,
    val pictures: List<String>?
)

data class Price(
    val amount: String,
    val currencyCode: String
)

data class TripGeoCode(
    val latitude: Double,
    val longitude: Double
)
