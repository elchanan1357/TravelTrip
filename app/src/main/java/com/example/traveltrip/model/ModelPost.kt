package com.example.traveltrip.model

import android.graphics.Bitmap
import com.example.traveltrip.CloudinaryModel
import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.model.firebase.FirebaseModelPost
import com.example.traveltrip.utils.EmptyCallback
import com.example.traveltrip.utils.PostsCallback
import com.example.traveltrip.utils.UriCallback


class ModelPost {
    private val firebase: FirebaseModelPost = FirebaseModelPost()
    private val cloudinaryModel: CloudinaryModel = CloudinaryModel()

    companion object {
        val instance: ModelPost = ModelPost()
    }

    fun getAllPosts(callback: PostsCallback) {
        firebase.getAllPosts(callback)
    }

    fun getAllPostsByEmail(email: String, callback: PostsCallback) {
        firebase.getAllPostsByEmail(email, callback)
    }


    fun insertPost(post: Post, img: Bitmap?, callback: EmptyCallback) {
        firebase.insertPost(post) {
            img?.let {
                uploadImg(
                    img,
                    post.title,
                    onSuccess = { uri ->
                        if (!uri.isNullOrBlank()) {
                            val postImg = post.copy(imgURI = uri)
                            firebase.insertPost(postImg, callback)
                        } else callback()
                    },
                    onError = { callback() }
                )
            }
        }
    }


    private fun uploadImg(img: Bitmap, name: String, onSuccess: UriCallback, onError: UriCallback) {
        cloudinaryModel.uploadImg(img, name, onSuccess, onError)
    }


    fun deletePost(id: String, callback: EmptyCallback) {
        firebase.deletePost(id, callback)
    }


    fun updatePost(id: String, post: Post, callback: EmptyCallback) {
        firebase.updatePost(id, post, callback)
    }
}