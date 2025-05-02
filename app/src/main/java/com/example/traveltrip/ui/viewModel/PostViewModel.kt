package com.example.traveltrip.ui.viewModel


import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.traveltrip.model.repository.RepoPost
import com.example.traveltrip.model.repository.RepoUser
import com.example.traveltrip.model.room.entity.Post


class PostViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = this._errorMessage

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = this._posts

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = this._post

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = this._isSuccess

    private val _postsWithUsers = MutableLiveData<List<PostWithUser>>()
    val postsWithUsers: LiveData<List<PostWithUser>> = this._postsWithUsers


    fun getAllPostsWithUsers() {
        RepoPost.instance.getAllPosts { success, posts ->
            if (success) {
                val completePost = mutableListOf<PostWithUser>()
                var count = 0
                var flag = true
                posts.forEach { post ->
                    RepoUser.instance.getUserById(post.userId) { success, user ->
                        if (success && user != null) {
                            completePost.add(PostWithUser(post, user))
                            count++
                            if (count == posts.size && flag)
                                _postsWithUsers.value = completePost
                        } else {
                            _errorMessage.value = "Unable to get all posts"
                            flag = false
                        }
                    }
                }
            } else _errorMessage.value = "Unable to get all posts"
        }
    }


    fun getPostsByUserID() {
        RepoPost.instance.getPostsByUserID { success, posts ->
            if (success) _posts.value = posts
            else _errorMessage.value = "Unable to get posts by user id"
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
            if (success) this._isSuccess.value = true
            else _errorMessage.value = "Unable to update post"
        }
    }


    fun insertPost(post: Post, bitmap: Bitmap?) {
        RepoPost.instance.insertPost(post, bitmap) { success ->
            if (success) this._isSuccess.value = true
            else _errorMessage.value = "Unable to insert post"
        }
    }


    fun deletePost(post: Post) {
        RepoPost.instance.deletePost(post) { success ->
            if (success) this._isSuccess.value = true
            else _errorMessage.value = "Unable to delete post"
        }
    }
}
