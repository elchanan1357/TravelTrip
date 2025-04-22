package com.example.traveltrip.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL


@Entity(tableName = "Posts")
data class Posts(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imgURL: String,
    val location: String,
    val likes: Int,
    val title: String,
    val post: String
)
