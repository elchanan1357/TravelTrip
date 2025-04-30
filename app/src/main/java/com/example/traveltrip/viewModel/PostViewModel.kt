package com.example.traveltrip.viewModel


import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.traveltrip.model.repository.RepoPost
import com.example.traveltrip.model.room.entity.Post


class PostViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = this._errorMessage

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = this._posts

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = this._post

    fun getAllPost() {
        RepoPost.instance.getAllPosts { success, posts ->
            if (success) _posts.value = posts
            else _errorMessage.value = "Unable to get all posts"
        }
    }

    fun getPostByID(id: String) {
        RepoPost.instance.getPostByID(id) { success, post ->
            if (success) this._post.value = post
            else _errorMessage.value = "Unable to get post"
        }
    }

    fun updatePost(post: Post, bitmap: Bitmap?) {
        RepoPost.instance.updatePost(post, bitmap) { success ->
            if (!success) _errorMessage.value = "Unable to update post"
        }
    }

    fun insertPost(post: Post, bitmap: Bitmap?) {
        RepoPost.instance.insertPost(post, bitmap) { success ->
            if (!success) _errorMessage.value = "Unable to insert post"
        }
    }

    fun deletePost(post: Post) {
        RepoPost.instance.deletePost(post) { success ->
            if (!success) _errorMessage.value = "Unable to delete post"
        }
    }
}
