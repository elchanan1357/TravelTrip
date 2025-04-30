package com.example.traveltrip.utils

import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.model.room.entity.Travel
import com.example.traveltrip.model.room.entity.User

typealias TravelsCallback = (List<Travel>) -> Unit
typealias TravelCallback = (Travel?) -> Unit
typealias PostsCallback = (success: Boolean, List<Post>) -> Unit
typealias PostCallback = (success: Boolean, Post?) -> Unit
typealias UsersCallback = (List<User>) -> Unit
typealias UserCallback = (User?) -> Unit
typealias EmptyCallback = () -> Unit
typealias ResultCallback = (success: Boolean) -> Unit
typealias UriCallback = (String?) -> Unit
typealias AuthCallback = (Boolean, String?) -> Unit
