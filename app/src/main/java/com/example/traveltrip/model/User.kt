package com.example.traveltrip.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val name: String,
    val phone: String,
    val email: String,
    val password: String,
)
