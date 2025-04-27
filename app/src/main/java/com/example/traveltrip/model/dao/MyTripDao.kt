package com.example.traveltrip.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.traveltrip.model.entity.MyTripEntity


@Dao
    interface MyTripDao {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun saveTrip(myTrip: MyTripEntity)

        @Delete
        suspend fun deleteTrip(myTrip: MyTripEntity)

        @Query("SELECT * FROM my_trips WHERE userId = :userId")
        suspend fun getSavedTripsForUser(userId: String): List<MyTripEntity>
    }


