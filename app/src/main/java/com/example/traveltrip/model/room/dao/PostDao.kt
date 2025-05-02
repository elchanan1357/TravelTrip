package com.example.traveltrip.model.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.traveltrip.model.room.entity.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM Posts")
    fun getAllPosts(): LiveData<MutableList<Post>>

    @Query("SELECT * FROM Posts WHERE id = :id")
    fun getPostById(id: String): LiveData<Post?>

    @Query("SELECT * FROM Posts WHERE userId = :userId")
    fun getPostsByUserId(userId: String): LiveData<MutableList<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)

    @Delete
    fun deletePost(post: Post)

    @Update
    fun updatePost(post: Post)
}