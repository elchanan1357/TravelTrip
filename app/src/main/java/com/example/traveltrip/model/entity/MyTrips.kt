package com.example.traveltrip.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_trips")
data class MyTrips(
    @PrimaryKey val tripId: String,
    val userId: String
)




