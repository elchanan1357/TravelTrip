package com.example.traveltrip.model.firebase

import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.utils.Constants
import com.example.traveltrip.utils.EmptyCallback
import com.example.traveltrip.utils.PostCallback
import com.example.traveltrip.utils.PostsCallback
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError

class FirebaseModelPost {
    private val db = FirebaseProvider.getFirestoreInstance()
    private val postsCollection = Constants.Collection.POSTS


    fun getAllPosts(callback: PostsCallback) {
        db.collection(postsCollection).get()
            .addOnSuccessListener { postsJSON ->
                log("Get all post")
                val posts: MutableList<Post> = mutableListOf()
                for (json in postsJSON)
                    posts.add(Post.fromJSON(json.data))

                callback(posts)
            }
            .addOnFailureListener { e ->
                logError("Fail in get all posts\n $e")
                callback(listOf())
            }
    }


    fun getAllPostsByEmail(email: String, callback: PostsCallback) {
        db.collection(postsCollection).whereEqualTo("email", email).get()
            .addOnSuccessListener { postsJSON ->
                log("Get posts with email: $email")
                val posts: MutableList<Post> = mutableListOf()

                for (json in postsJSON) {
                    log(json.data.toString())
                    posts.add(Post.fromJSON(json.data))
                }

                callback(posts)
            }
            .addOnFailureListener { e ->
                logError("Fail in get all posts by email\n $e")
                callback(listOf())
            }
    }


    fun insertPost(post: Post, callback: EmptyCallback) {
        val ref = db.collection(postsCollection).document()
        val generatedId = ref.id
        val postWithId = post.copy(id = generatedId)
        ref.set(postWithId.json)
            .addOnSuccessListener {
                log("Add post: ${postWithId.id}")
                callback()
            }
            .addOnFailureListener { e ->
                logError("Fail in add post: ${postWithId.id} \n $e")
            }
    }


    fun deletePost(id: String, callback: EmptyCallback) {
        db.collection(postsCollection).document(id).delete()
            .addOnSuccessListener {
                log("Delete post: $id")
                callback()
            }
            .addOnFailureListener { e ->
                logError("Fail in delete post: $id \n $e")
            }
    }


    fun updatePost(post: Post, callback: EmptyCallback) {
        db.collection(postsCollection).document(post.id).update(post.updateJSON())
            .addOnSuccessListener {
                log("Update post with id: ${post.id}")
                callback()
            }
            .addOnFailureListener { e ->
                logError("Fail in update post with id: ${post.id} \n $e")
            }
    }


    fun getPostByID(id: String, callback: PostCallback) {
        db.collection(postsCollection).document(id).get()
            .addOnSuccessListener {
                log("Get post with id: $id")
                val post: Post? = it.data?.let { data -> Post.fromJSON(data) }
                callback(post)
            }
            .addOnFailureListener { e ->
                logError("Fail in get post by id: $id \n $e")
            }
    }


}