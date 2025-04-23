package com.example.traveltrip.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.traveltrip.model.entity.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM Posts")
    fun getAllPosts(): List<Post>

    @Query("SELECT * FROM Posts WHERE email = :email")
    fun getAllPostsByEmail(email: String): List<Post>

    @Query("SELECT * FROM Posts WHERE id = :id")
    fun getPostById(id: Int): Post?

    @Insert
    fun insertPost(post: Post)

    @Delete
    fun deletePost(post: Post)

    @Update
    fun updatePost(post: Post)
}