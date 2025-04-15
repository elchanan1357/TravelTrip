package com.example.traveltrip.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Travel(
    @PrimaryKey val title: String,
    val info: String,
    val img: String
)
