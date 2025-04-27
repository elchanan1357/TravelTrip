package com.example.traveltrip.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.traveltrip.model.entity.Trip

@Dao
interface TripDao {
    @Query("SELECT * FROM Trip WHERE title = :title")
    fun getTravelByTitle(title: String): Trip?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTravels(vararg travels: Trip)

    @Query("SELECT * FROM Trip")
    fun getTravels(): List<Trip>

    @Delete
    fun deleteTravel(travel: Trip)
}