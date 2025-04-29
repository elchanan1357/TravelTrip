package com.example.traveltrip.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.traveltrip.model.entity.MyTrips


@Dao
    interface MyTripDao {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun saveTrip(myTrip:MyTrips)

        @Delete
        suspend fun deleteTrip(myTrip: MyTrips)

        @Query("SELECT * FROM my_trips WHERE userId = :userId")
        suspend fun getSavedTripsForUser(userId: String): List<MyTrips>
    }


