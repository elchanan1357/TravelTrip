package com.example.traveltrip.model.room.models

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.model.room.dao.AppLocalDB
import com.example.traveltrip.utils.ResultCallback
import com.example.traveltrip.utils.UserCallback
import com.example.traveltrip.utils.UsersCallback
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError
import java.util.concurrent.Executors

class RoomUser private constructor() {
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: RoomUser = RoomUser()
    }

    fun getAllUsers(callback: UsersCallback) {
        executor.execute {
            try {
                val users: List<User> = AppLocalDB.DB.UserDao().getUsers()
                if (users.isEmpty()) callback(false, emptyList())
                else {
                    log("get All users from room")
                    mainHandler.post { callback(true, users) }
                }
            } catch (err: Exception) {
                logError("Fail in get all users\n $err")
                callback(false, emptyList())
            }
        }
    }


    fun getUser(id: String, callback: UserCallback) {
        executor.execute {
            try {
                val user = AppLocalDB.DB.UserDao().getUserByUid(uid = id)
                if (user == null) callback(false, null)
                else {
                    log("Get user by id from room")
                    mainHandler.post { callback(true, user) }
                }

            } catch (err: Exception) {
                logError("Fail \"Get user by id from room\n $err")
                callback(false, null)
            }
        }
    }


    fun addUser(user: User, callback: ResultCallback) {
        executor.execute {
            try {
                AppLocalDB.DB.UserDao().insertUser(user)
                log("Add user to room")
                mainHandler.post { callback(true) }
            } catch (err: Exception) {
                logError("Fail in insert user from room\n $err")
                callback(false)
            }
        }
    }


    fun updateUser(user: User, callback: ResultCallback) {
        executor.execute {
            try {
                AppLocalDB.DB.UserDao().updateUser(user)
                log("update user in room")
                mainHandler.post { callback(true) }
            } catch (err: Exception) {
                logError("Fail in update user from room\n $err")
                callback(false)
            }
        }
    }


    fun deleteUser(user: User, callback: ResultCallback) {
        executor.execute {
            try {
                AppLocalDB.DB.UserDao().deleteUser(user)
                log("delete user from room")
                mainHandler.post { callback(true) }
            } catch (err: Exception) {
                logError("Fail in delete user from room\n $err")
                callback(false)
            }
        }
    }

    fun clearAll(callback: ResultCallback) {
        executor.execute {
            try {
                AppLocalDB.DB.clearAllTables()
                val allUsers = AppLocalDB.DB.UserDao().getUsers()
                val allPosts = AppLocalDB.DB.PostDao().getAllPosts()
                log("Users count: ${allUsers.size}")
                log("Posts count: ${allPosts.value?.size}")
                log("delete all data")
                Thread.sleep(3000)
                mainHandler.post { callback(true) }
            } catch (err: Exception) {
                logError("Fail in delete all data\n $err")
                callback(false)
            }
        }
    }
}