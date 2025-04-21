package com.example.traveltrip.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.traveltrip.Utils.log
import com.example.traveltrip.Utils.logError
import com.example.traveltrip.model.entity.User
import com.example.traveltrip.model.firebase.FirebaseModelUser
import java.util.concurrent.Executors

class ModelUser private constructor() {
    private var email: String? = null
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
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
//                Thread.sleep(4000)
//                mainHandler.post { callback(users) }
//            } catch (err: Exception) {
//                logError("Fail in get all users")
//                logError(err.toString())
//            }
//        }
    }

    fun getUserByEmail(email: String, callback: UserCallback) {
        firebaseModelUser.getUserByEmail(email, callback)
//        executor.execute {
//            try {
//                val user: User? = AppLocalDB.DB.UserDao().getUserByEmail(email)
//                log("Get user by email")
//                mainHandler.post { callback(user) }
//            } catch (err: Exception) {
//                logError("Fail in get user by email")
//                logError(err.toString())
//            }
//        }
    }

    fun addUser(user: User, callback: EmptyCallback) {
        firebaseModelUser.addUser(user, callback)
//        executor.execute {
//            try {
//                AppLocalDB.DB.UserDao().insertUser(user)
//                log("add user")
//                mainHandler.post { callback() }
//            } catch (e: android.database.sqlite.SQLiteConstraintException) {
//                logError("The user already exist")
//                logError(e.message.toString())
//            } catch (err: Exception) {
//                logError("Fail in add user")
//                logError(err.toString())
//            }
//        }
    }

    fun updateUser(user: User, callback: EmptyCallback) {
        firebaseModelUser.updateUser(user, callback)
//        executor.execute {
//            try {
//                AppLocalDB.DB.UserDao().updateUser(user)
//                log("update user")
//                mainHandler.post { callback() }
//            } catch (err: Exception) {
//                logError("Fail in update user")
//                logError(err.toString())
//            }
//        }
    }

    fun deleteUser(user: User, callback: EmptyCallback) {
        firebaseModelUser.deleteUser(user, callback)
//        executor.execute {
//            try {
//                AppLocalDB.DB.UserDao().deleteUser(user)
//                log("Delete user")
//                mainHandler.post { callback() }
//            } catch (err: Exception) {
//                logError("Fail in delete user")
//                logError(err.toString())
//            }
//        }
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getEmail(): String? {
        return this.email
    }

}