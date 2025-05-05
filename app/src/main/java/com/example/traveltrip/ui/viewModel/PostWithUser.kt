package com.example.traveltrip.ui.viewModel

import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.model.room.entity.User

data class PostWithUser(
    val post: Post,
    val user: User
)
