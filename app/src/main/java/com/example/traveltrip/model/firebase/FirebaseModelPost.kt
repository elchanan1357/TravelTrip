package com.example.traveltrip.model.firebase

import com.example.traveltrip.model.entity.Post
import com.example.traveltrip.model.entity.User
import com.example.traveltrip.utils.Constants
import com.example.traveltrip.utils.EmptyCallback
import com.example.traveltrip.utils.PostsCallback
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError

class FirebaseModelPost {
    private val db = Firestore.getFirestoreInstance()
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


    fun insertPost(post: Post, callback: EmptyCallback) {
        db.collection(postsCollection).document().set(post.json)
            .addOnSuccessListener {
                log("Add post: ${post.title}")
                callback()
            }
            .addOnFailureListener { e ->
                logError("Fail in add post: ${post.title} \n $e")
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


    fun updatePost(id: String, post: Post, callback: EmptyCallback) {
        db.collection(postsCollection).document(id).update(post.updateJSON())
            .addOnSuccessListener {
                log("Update post with id: $id")
                callback()
            }
            .addOnFailureListener { e ->
                logError("Fail in update post with id: $id \n $e")
            }
    }

}