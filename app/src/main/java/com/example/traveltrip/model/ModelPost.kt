package com.example.traveltrip.model

import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.model.firebase.FirebaseModelPost
import com.example.traveltrip.utils.EmptyCallback
import com.example.traveltrip.utils.PostsCallback


class ModelPost {
    private val firebase: FirebaseModelPost = FirebaseModelPost()

    companion object {
        val instance: ModelPost = ModelPost()
    }

    fun getAllPosts(callback: PostsCallback) {
        firebase.getAllPosts(callback)
    }


    fun insertPost(post: Post, callback: EmptyCallback) {
        firebase.insertPost(post, callback)
    }


    fun deletePost(id: String, callback: EmptyCallback) {
        firebase.deletePost(id, callback)
    }

    fun updatePost(id: String, post: Post, callback: EmptyCallback) {
        firebase.updatePost(id, post, callback)
    }
}