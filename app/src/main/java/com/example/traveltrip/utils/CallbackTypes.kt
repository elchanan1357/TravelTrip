package com.example.traveltrip.utils

import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.model.entity.Trip
import com.example.traveltrip.model.entity.User

typealias TravelsCallback = (List<Trip>) -> Unit
typealias TravelCallback = (Trip?) -> Unit
typealias PostsCallback = (List<Post>) -> Unit
typealias UsersCallback = (List<User>) -> Unit
typealias UserCallback = (User?) -> Unit
typealias EmptyCallback = () -> Unit


