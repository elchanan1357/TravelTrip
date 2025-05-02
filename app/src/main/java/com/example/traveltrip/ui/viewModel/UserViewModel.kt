package com.example.traveltrip.ui.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.traveltrip.model.repository.RepoUser
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.utils.logError

class UserViewModel : ViewModel() {
    private val repoUser = RepoUser.instance

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = this._errorMessage

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = this._users

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = this._user


    private val _success = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = this._success

    fun getAllUsers() {
        repoUser.getAllUsers { success, users ->
            if (success) {
                this._users.value = users
            } else _errorMessage.value = "Unable to get all users"
        }
    }

    fun getCurrentUser() {
        repoUser.getCurrentUser { success, user ->
            if (success) {
                this._user.value = user
            } else _errorMessage.value = "Unable to get user"
        }
    }

    fun getUserById(id: String) {
        repoUser.getUserById(id) { success, user ->
            if (success) {
                this._user.value = user
            } else _errorMessage.value = "Unable to get user by id"
        }
    }

    fun updateUser(user: User, bitmap: Bitmap?) {
        repoUser.updateUser(user, bitmap) { success ->
            if (success) this._success.value = true
            else _errorMessage.value = "Unable to update user"
        }
    }

    fun insertUSer(user: User) {
        repoUser.addUser(user) { success ->
            if (success) this._success.value = true
            else _errorMessage.value = "Unable to insert user"
        }
    }

    fun deleteUser(user: User) {
        repoUser.deleteUser(user) { success ->
            if (success) this._success.value = true
            else _errorMessage.value = "Unable to delete user"
        }
    }

    fun signIn(email: String, password: String) {
        repoUser.signIn(email, password) { success, error ->
            if (success) this._success.value = true
            else {
                logError(error.toString())
                this._errorMessage.value = "Unable to  log in"
            }
        }
    }

    fun signOut() {
        repoUser.signOut {
            if (it) this._success.value = true
            else this._errorMessage.value = "Unable to  log out"
        }
    }
}