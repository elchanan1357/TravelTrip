package com.example.traveltrip.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Travel")
data class Travel(
    @PrimaryKey var title: String,
    var info: String,
    var img: String
)
