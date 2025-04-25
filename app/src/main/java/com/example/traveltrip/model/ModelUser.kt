package com.example.traveltrip.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.traveltrip.model.entity.User
import com.example.traveltrip.model.firebase.Auth
import com.example.traveltrip.model.firebase.FirebaseModelUser
import com.example.traveltrip.model.firebase.FirebaseProvider

import com.example.traveltrip.utils.EmptyCallback
import com.example.traveltrip.utils.UserCallback
import com.example.traveltrip.utils.UsersCallback
import java.util.concurrent.Executors

class ModelUser private constructor() {
    //    private val executor = Executors.newSingleThreadExecutor()
//    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModelUser = FirebaseModelUser()

    companion object {
        val instance: ModelUser = ModelUser()
    }

    fun getAllUsers(callback: UsersCallback) {
        firebaseModelUser.getAllUsers(callback)
//        executor.execute {
//            try {
//                val users: List<User> = AppLocalDB.DB.UserDao().getUsers()
//                log("get All users")
////                Thread.sleep(4000)
//                mainHandler.post { callback(users) }
//            } catch (err: Exception) {
//                logError("Fail in get all users")
//                logError(err.toString())
//            }
//        }
    }

    fun getUserByEmail(email: String, callback: UserCallback) {
        firebaseModelUser.getUserByEmail(email, callback)
    }

    fun addUser(user: User, callback: EmptyCallback) {
        firebaseModelUser.addUser(user, callback)
    }

    fun updateUser(user: User, callback: EmptyCallback) {
        firebaseModelUser.updateUser(user, callback)
    }

    fun deleteUser(user: User, callback: EmptyCallback) {
        firebaseModelUser.deleteUser(user, callback)
    }


    fun getEmail(): String? {
        if (Auth.isLoggedIn())
            return Auth.getCurrentUser()?.email

        return null
    }

}