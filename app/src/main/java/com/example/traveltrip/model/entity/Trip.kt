package com.example.traveltrip.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Trip")
data class Trip(
    @PrimaryKey var title: String,
    var info: String,
    var img: String
)
