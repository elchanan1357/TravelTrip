package com.example.traveltrip.model.repository

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.example.traveltrip.base.MyApp
import com.example.traveltrip.model.remote.cloudinary.CloudinaryModel
import com.example.traveltrip.model.remote.firebase.FirebaseAuth
import com.example.traveltrip.model.remote.firebase.FirebaseModelPost
import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.model.room.models.RoomPost
import com.example.traveltrip.utils.PostCallback
import com.example.traveltrip.utils.PostsCallback
import com.example.traveltrip.utils.ResultCallback
import com.example.traveltrip.utils.isOnline
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError

class RepoPost private constructor() {
    private val firebaseModel: FirebaseModelPost = FirebaseModelPost()
    private val cloudinaryModel: CloudinaryModel = CloudinaryModel.cloudinaryModel
    private val context: Context = MyApp.Globals.context
    private var roomPost: RoomPost = RoomPost.instance

    @SuppressLint("StaticFieldLeak")
    companion object {
        val instance = RepoPost()
    }

    fun getAllPosts(callback: PostsCallback) {
        if (isOnline(context)) {
            firebaseModel.getAllPosts { success, posts ->
                if (success) {
                    posts.forEach { post ->
                        roomPost.insertPost(post) { success ->
                            if (!success) logError("fail to save posts in room")
                        }
                    }
                    callback(true, posts)
                } else callback(false, emptyList())
            }
        } else roomPost.getAllPosts(callback)
    }

    fun getPostsByUserID(callback: PostsCallback) {
        var id = ""
        FirebaseAuth.getCurrentUser()?.uid?.let {
            id = it
        }

        if(id.isBlank()){
            callback(false, emptyList())
            logError("not find user id in search posts by id")
        }

        log(id)
        if (isOnline(context)) {
            firebaseModel.getPostsByUserID(id) { success, posts ->
                if (success) {
                    posts.forEach { post ->
                        roomPost.insertPost(post) { success ->
                            if (!success) logError("fail to save posts in room")
                        }
                    }
                    callback(true, posts)
                } else callback(false, emptyList())
            }
        } else roomPost.getPostsByUserID(id, callback)
    }


    fun getPostByID(id: String, callback: PostCallback) {
        roomPost.getPostByID(id) { success, post ->
            if (success) callback(true, post)
            else if (isOnline(context)) {
                firebaseModel.getPostByID(id) { success2, post2 ->
                    if (success2) {
                        post2?.let {
                            roomPost.insertPost(it) { flag ->
                                if (!flag) logError("fail to save post in room")
                            }
                        }
                        callback(true, post2)
                    } else callback(false, null)
                }
            } else callback(false, null)
        }
    }

    fun updatePost(post: Post, bitmap: Bitmap?, callback: ResultCallback) {
        if (isOnline(context)) {
            bitmap?.let {
                cloudinaryModel.uploadImg(
                    bitmap,
                    post.id,
                    onSuccess = { uri ->
                        if (!uri.isNullOrBlank()) {
                            val newPost = post.copy(imgURI = uri)
                            firebaseModel.updatePost(newPost) {
                                if (it) roomPost.updatePost(newPost) { success ->
                                    if (!success) logError("can't update post in room")
                                    callback(true)
                                }
                                else callback(false)
                            }
                        } else callback(false)
                    },
                    onError = { callback(false) }
                )
            } ?: firebaseModel.updatePost(post) {
                if (it) roomPost.updatePost(post) { success ->
                    if (!success) logError("can't update post in room")
                    callback(true)
                }
                else callback(false)
            }
        } else callback(false)
    }

    fun insertPost(post: Post, bitmap: Bitmap?, callback: ResultCallback) {
        if (isOnline(context)) {
            bitmap?.let {
                cloudinaryModel.uploadImg(
                    bitmap,
                    post.id,
                    onSuccess = { uri ->
                        if (!uri.isNullOrBlank()) {
                            val newPost = post.copy(imgURI = uri)
                            firebaseModel.insertPost(newPost) {
                                if (it) roomPost.insertPost(newPost) { success ->
                                    if (!success) logError("can't insert post to room")
                                    callback(true)
                                }
                                else callback(false)
                            }
                        } else callback(false)
                    },
                    onError = { callback(false) }
                )
            } ?: firebaseModel.insertPost(post) {
                if (it) roomPost.insertPost(post) { success ->
                    if (!success) logError("can't insert post to room")
                    callback(true)
                }
                else callback(false)
            }
        } else callback(false)
    }

    fun deletePost(post: Post, callback: ResultCallback) {
        if (isOnline(context)) {
            roomPost.deletePost(post) { success ->
                if (success) {
                    firebaseModel.deletePost(post.id, callback)
                    cloudinaryModel.deleteImg()
                } else callback(false)
            }
        } else callback(false)
    }
}
