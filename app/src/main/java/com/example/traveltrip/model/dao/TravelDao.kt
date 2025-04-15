package com.example.traveltrip.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.traveltrip.model.Travel

@Dao
interface TravelDao {
//    @Query("SELECT * FROM TRAVEL WHERE title = :title")
//    fun getTravelByTitle(title: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTravels(vararg travels: Travel)

    @Query("SELECT * FROM TRAVEL")
    fun getTravels(): List<Travel>

    @Delete
    fun deleteTravel(travel: Travel)
}