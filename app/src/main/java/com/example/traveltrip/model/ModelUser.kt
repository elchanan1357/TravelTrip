package com.example.traveltrip.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.traveltrip.model.entity.User
import java.util.concurrent.Executors

class ModelUser private constructor() {
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: ModelUser = ModelUser()
    }

    fun getAllUsers(callback: UsersCallback) {
        executor.execute {
            val users: List<User> = AppLocalDB.DB.UserDao().getUsers()
            Thread.sleep(4000)
            mainHandler.post { callback(users) }
        }
    }

    fun getUserByName(name: String, callback: UserCallback) {
        executor.execute {
            val user: User? = AppLocalDB.DB.UserDao().getUserByName(name)
            mainHandler.post { callback(user) }
        }
    }

    fun addUser(user: User, callback: EmptyCallback) {
        executor.execute {
            AppLocalDB.DB.UserDao().insertUser(user)
            mainHandler.post { callback() }
        }
    }

    fun deleteUser(user: User, callback: EmptyCallback) {
        executor.execute {
            AppLocalDB.DB.UserDao().deleteUser(user)
            mainHandler.post { callback() }
        }
    }

}