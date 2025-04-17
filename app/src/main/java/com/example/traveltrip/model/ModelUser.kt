package com.example.traveltrip.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.traveltrip.log
import com.example.traveltrip.logError
import com.example.traveltrip.model.entity.User
import java.util.concurrent.Executors

class ModelUser private constructor() {
    private var email: String? = null
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: ModelUser = ModelUser()
    }

    fun getAllUsers(callback: UsersCallback) {
        executor.execute {
            try {
                val users: List<User> = AppLocalDB.DB.UserDao().getUsers()
                log("get All users")
                Thread.sleep(4000)
                mainHandler.post { callback(users) }
            } catch (err: Exception) {
                logError("Fail in get all users")
                logError(err.toString())
            }
        }
    }

    fun getUserByEmail(email: String, callback: UserCallback) {
        executor.execute {
            try {
                val user: User? = AppLocalDB.DB.UserDao().getUserByEmail(email)
                log("Get user by email")
                mainHandler.post { callback(user) }
            } catch (err: Exception) {
                logError("Fail in get user by email")
                logError(err.toString())
            }
        }
    }

    fun addUser(user: User, callback: EmptyCallback) {
        executor.execute {
            try {
                AppLocalDB.DB.UserDao().insertUser(user)
                log("add user")
                mainHandler.post { callback() }
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
                logError("The user already exist")
                logError(e.message.toString())
            } catch (err: Exception) {
                logError("Fail in add user")
                logError(err.toString())
            }
        }
    }

    fun deleteUser(user: User, callback: EmptyCallback) {
        executor.execute {
            try {
                AppLocalDB.DB.UserDao().deleteUser(user)
                log("Delete user")
                mainHandler.post { callback() }
            } catch (err: Exception) {
                logError("Fail in delete user")
                logError(err.toString())
            }
        }
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getEmail(): String? {
        return this.email
    }

}