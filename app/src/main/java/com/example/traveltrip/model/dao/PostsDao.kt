package com.example.traveltrip.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.traveltrip.model.entity.Posts


@Dao
interface PostDao {
    @Query("SELECT * FROM Posts")
    fun getAllPosts(): List<Posts>

    @Query("SELECT * FROM Posts WHERE id = :id")
    fun getPostById(id: Int): Posts?

    @Insert
    fun insertPost(post: Posts): Long

    @Delete
    fun deletePost(post: Posts)

    @Update
    fun updatePost(post: Posts)
}
