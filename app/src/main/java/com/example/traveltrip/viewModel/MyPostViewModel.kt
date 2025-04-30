package com.example.traveltrip.viewModel

import androidx.lifecycle.ViewModel
import com.example.traveltrip.model.room.entity.Post

class MyPostViewModel : ViewModel() {
    private var _posts: List<Post>? = null
    var posts: List<Post>?
        get() = this._posts
        private set(value) {
            this._posts = value
        }

    fun set(posts: List<Post>) {
        this.posts = posts
    }
}