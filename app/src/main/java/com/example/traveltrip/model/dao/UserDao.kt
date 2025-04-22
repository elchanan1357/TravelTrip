package com.example.traveltrip.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.traveltrip.model.entity.User
import com.example.traveltrip.utils.Constants

@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE email = :email")
    fun getUserByEmail(email: String): User?


    @Query("SELECT * FROM Users")
    fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(vararg user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}