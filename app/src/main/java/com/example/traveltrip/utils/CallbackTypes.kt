package com.example.traveltrip.utils

import com.example.traveltrip.model.entity.Travel
import com.example.traveltrip.model.entity.User

typealias TravelsCallback = (List<Travel>) -> Unit
typealias TravelCallback = (Travel?) -> Unit
typealias UsersCallback = (List<User>) -> Unit
typealias UserCallback = (User?) -> Unit
typealias EmptyCallback = () -> Unit


