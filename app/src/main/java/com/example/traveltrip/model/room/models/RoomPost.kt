package com.example.traveltrip.model.room.models

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.model.room.dao.AppLocalDB
import com.example.traveltrip.utils.PostCallback
import com.example.traveltrip.utils.PostsCallback
import com.example.traveltrip.utils.ResultCallback
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError
import java.util.concurrent.Executors


class RoomPost private constructor() {
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val roomDB = AppLocalDB.DB.PostDao()

    companion object {
        val instance: RoomPost = RoomPost()
    }

    fun getAllPosts(callback: PostsCallback) {
        executor.execute {
            try {
                val posts = roomDB.getAllPosts()
                if (posts.value?.isEmpty() == true)
                    callback(false, emptyList())
                else mainHandler.post {
                    log("get all post from room")
                    posts.value?.let { callback(true, it) }
                }
            } catch (err: Exception) {
                logError("Fail get all post from room\n $err")
                callback(false, emptyList())
            }
        }
    }


    fun insertPost(post: Post, callback: ResultCallback) {
        executor.execute {
            try {
                roomDB.insertPost(post)
                mainHandler.post {
                    log("insert post to room")
                    callback(true)
                }
            } catch (err: Exception) {
                logError("Fail insert post from room\n $err")
                callback(false)
            }
        }
    }


    fun updatePost(post: Post, callback: ResultCallback) {
        executor.execute {
            try {
                roomDB.updatePost(post)
                mainHandler.post {
                    log("update post in room")
                    callback(true)
                }
            } catch (err: Exception) {
                logError("Fail update post from room\n $err")
                callback(false)
            }
        }
    }


    fun getPostByID(id: String, callback: PostCallback) {
        executor.execute {
            try {
                val post = roomDB.getPostById(id)
                if (post.value == null)
                    callback(false, null)
                else mainHandler.post {
                    log("get post by id from room")
                    callback(true, post.value)
                }
            } catch (err: Exception) {
                logError("Fail get post by id from room\n $err")
                callback(false, null)
            }
        }
    }


    fun deletePost(post: Post, callback: ResultCallback) {
        executor.execute {
            try {
                roomDB.deletePost(post)
                log("delete post from room")
                mainHandler.post {
                    callback(true)
                }
            } catch (err: Exception) {
                logError("Fail delete post from room\n $err")
                callback(false)
            }
        }
    }


}