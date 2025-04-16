package com.example.traveltrip.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.traveltrip.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE name = :name")
    fun getUserByName(name: String): User?

    @Query("SELECT * FROM User")
    fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user: User)

    @Delete
    fun deleteUser(user: User)
}