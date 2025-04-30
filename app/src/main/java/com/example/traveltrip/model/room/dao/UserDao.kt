package com.example.traveltrip.model.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.traveltrip.model.room.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE uid = :uid")
    fun getUserByUid(uid: String): User?


    @Query("SELECT * FROM Users")
    fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}