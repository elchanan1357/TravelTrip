package com.example.traveltrip.model

import android.graphics.Bitmap
import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.model.firebase.FirebaseModelPost
import com.example.traveltrip.utils.EmptyCallback
import com.example.traveltrip.utils.PostCallback
import com.example.traveltrip.utils.PostsCallback
import com.example.traveltrip.utils.log


class ModelPost {
    private val firebase: FirebaseModelPost = FirebaseModelPost()
    private val cloudinaryModel: CloudinaryModel = CloudinaryModel.cloudinaryModel


    companion object {
        val instance: ModelPost = ModelPost()
    }


    fun getAllPosts(callback: PostsCallback) {
        firebase.getAllPosts(callback)
    }


    fun getAllPostsByEmail(email: String, callback: PostsCallback) {
        firebase.getAllPostsByEmail(email, callback)
    }


    fun insertPost(post: Post, bitmap: Bitmap?, callback: EmptyCallback) {
        bitmap?.let {
            cloudinaryModel.uploadImg(
                bitmap,
                post.id,
                onSuccess = { uri ->
                    if (!uri.isNullOrBlank())
                        firebase.insertPost(post.copy(imgURI = uri), callback)
                    else callback()
                },
                onError = { callback() }
            )
        } ?: firebase.insertPost(post, callback)
    }


    fun getPostByID(id: String, callback: PostCallback) {
        firebase.getPostByID(id, callback)
    }


    fun deletePost(id: String, callback: EmptyCallback) {
        firebase.deletePost(id, callback)
    }


    fun updatePost(post: Post, bitmap: Bitmap?, callback: EmptyCallback) {
        bitmap?.let {
            cloudinaryModel.uploadImg(
                bitmap,
                post.id,
                onSuccess = { uri ->
                    if (!uri.isNullOrBlank())
                        firebase.updatePost(post.copy(imgURI = uri), callback)
                    else callback()
                },
                onError = { error ->
                    log(error.toString())
                    callback()
                }
            )
        } ?: firebase.updatePost(post, callback)
    }
}