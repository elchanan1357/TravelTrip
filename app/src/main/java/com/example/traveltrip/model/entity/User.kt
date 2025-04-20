package com.example.traveltrip.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    var name: String,
    var phone: String,
    @PrimaryKey var email: String,
    var password: String,
)
