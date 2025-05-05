package com.example.traveltrip.model.remote.firebase

import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.utils.Constants
import com.example.traveltrip.utils.PostCallback
import com.example.traveltrip.utils.PostsCallback
import com.example.traveltrip.utils.ResultCallback
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError

class FirebaseModelPost {
    private val db = FirebaseProvider.getFirestoreInstance()
    private val postsCollection = Constants.Collection.POSTS


    fun getAllPosts(callback: PostsCallback) {
        db.collection(postsCollection).get()
            .addOnSuccessListener { postsJSON ->
                val posts: MutableList<Post> = mutableListOf()
                for (json in postsJSON)
                    posts.add(Post.fromJSON(json.data))

                log("Get all post from firestore")
                callback(true, posts)
            }
            .addOnFailureListener { e ->
                logError("Fail in get all posts from firestore\n $e")
                callback(false, emptyList())
            }
    }


    fun getPostByID(id: String, callback: PostCallback) {
        db.collection(postsCollection).document(id).get()
            .addOnSuccessListener {
                log("Get post from firestore with id: $id")
                val post: Post? = it.data?.let { data -> Post.fromJSON(data) }
                callback(true, post)
            }
            .addOnFailureListener { e ->
                logError("Fail in get post by id: from firestore$id \n $e")
                callback(false, null)
            }
    }

    fun getPostsByUserID(id: String, callback: PostsCallback) {
        db.collection(postsCollection)
            .whereEqualTo("userId", id)
            .get()
            .addOnSuccessListener { postsJSON ->
                val posts: MutableList<Post> = mutableListOf()
                for (json in postsJSON)
                    posts.add(Post.fromJSON(json.data))

                log("Get all posts by user id from firestore")
                callback(true, posts)
            }
            .addOnFailureListener { e ->
                logError("Fail in get all posts by user id from firestore\n $e")
                callback(false, emptyList())
            }
    }


    fun insertPost(post: Post, callback: ResultCallback) {
        val collection = db.collection(postsCollection).document()
        val generatedId = collection.id
        val postWithId = post.copy(id = generatedId)
        collection.set(postWithId.json)
            .addOnSuccessListener {
                log("Add post to firestore: ${postWithId.id}")
                callback(true)
            }
            .addOnFailureListener { e ->
                logError("Fail in add post to firestore: ${postWithId.id} \n $e")
                callback(false)
            }
    }


    fun deletePost(id: String, callback: ResultCallback) {
        db.collection(postsCollection).document(id).delete()
            .addOnSuccessListener {
                log("Delete post from firestore: $id")
                callback(true)
            }
            .addOnFailureListener { e ->
                logError("Fail in delete post from firestore: $id \n $e")
                callback(false)
            }
    }


    fun updatePost(post: Post, callback: ResultCallback) {
        db.collection(postsCollection).document(post.id).update(post.updateJSON())
            .addOnSuccessListener {
                log("Update post in firestore with id: ${post.id}")
                callback(true)
            }
            .addOnFailureListener { e ->
                logError("Fail in update post to firestore with id: ${post.id} \n $e")
                callback(false)
            }
    }
}