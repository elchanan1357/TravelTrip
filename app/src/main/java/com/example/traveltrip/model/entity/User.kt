package com.example.traveltrip.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var name: String,
    var phone: String,
    var email: String,
    var password: String,
)
