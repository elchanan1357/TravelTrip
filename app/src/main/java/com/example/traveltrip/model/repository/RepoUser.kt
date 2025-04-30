package com.example.traveltrip.model.repository

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.example.traveltrip.base.MyApp
import com.example.traveltrip.model.remote.cloudinary.CloudinaryModel
import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.model.remote.firebase.FirebaseAuth
import com.example.traveltrip.model.remote.firebase.FirebaseModelUser
import com.example.traveltrip.model.room.models.RoomUser
import com.example.traveltrip.utils.AuthCallback
import com.example.traveltrip.utils.ResultCallback
import com.example.traveltrip.utils.UserCallback
import com.example.traveltrip.utils.UsersCallback
import com.example.traveltrip.utils.isOnline
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError


class RepoUser private constructor() {
    private val context = MyApp.Globals.context
    private val firebaseUser = FirebaseModelUser()
    private val cloudinaryModel: CloudinaryModel = CloudinaryModel.cloudinaryModel
    private val roomUser = RoomUser.instance


    companion object {
        @SuppressLint("StaticFieldLeak")
        val instance: RepoUser = RepoUser()
    }

    fun getAllUsers(callback: UsersCallback) {
        if (isOnline(context)) {
            firebaseUser.getAllUsers { success, users ->
                if (success) {
                    users.forEach { user ->
                        roomUser.addUser(user) {
                            if (!it) logError("Fal to save user in room")
                        }
                    }
                    callback(true, users)
                } else callback(false, emptyList())
            }
        } else roomUser.getAllUsers(callback)
    }

    fun addUser(user: User, callback: ResultCallback) {
        if (isOnline(context)) {
            firebaseUser.addUser(user) { success ->
                if (success) {
                    roomUser.addUser(user) { if (!it) logError("Fail in save user into the room") }
                    callback(true)
                } else callback(false)
            }
        } else {
            logError("can't add user because user offline")
            callback(false)
        }
    }

    fun updateUser(
        user: User,
        bitmap: Bitmap?,
        callback: ResultCallback
    ) {
        bitmap?.let {
            cloudinaryModel.uploadImg(
                bitmap,
                user.uid,
                onSuccess = { uri ->
                    if (!uri.isNullOrBlank()) {
                        val newUser = user.copy(img = uri)
                        firebaseUser.updateUser(newUser) {
                            if (it) roomUser.updateUser(newUser) { success ->
                                if (!success) logError("can't update post in room")
                                callback(true)
                            }
                            else callback(false)
                        }
                    } else callback(false)
                },
                onError = { error ->
                    log(error.toString())
                    callback(false)
                }
            )
        } ?: firebaseUser.updateUser(user) {
            if (it) {
                roomUser.updateUser(user) { success ->
                    if (!success) logError("Fail update user in room")
                }
                callback(true)
            } else callback(false)
        }
    }


    fun getUser(callback: UserCallback) {
        var id = ""
        FirebaseAuth.getCurrentUser()?.uid.let {
            if (it != null) {
                id = it
            }
        }

        roomUser.getUser(id) { success, user ->
            if (success) callback(true, user)
            else if (isOnline(context)) {
                firebaseUser.getUserByID(id) { success2, user2 ->
                    if (success2) {
                        user2?.let {
                            roomUser.addUser(it) { flag ->
                                if (!flag) logError("fail to save user in room")
                            }
                        }
                        callback(true, user2)
                    } else callback(false, null)
                }
            } else callback(false, null)
        }
    }


    fun signIn(email: String, password: String, callback: AuthCallback) {
        if (isOnline(context)) {
            firebaseUser.signIn(email, password, callback)
        } else {
            logError("Fail in log out because user offline")
            callback(false, null)
        }

    }


    fun deleteUser(user: User, callback: ResultCallback) {
        if (isOnline(context))
            roomUser.deleteUser(user) {
                if (it) {
                    firebaseUser.deleteUser(user) { success ->
                        if (success)
                            signOut(callback)
                        else logError("Fail in delete user from firestore")
                    }

                    cloudinaryModel.deleteImg()
                } else callback(false)
            }
        else {
            logError("Fail in delete user because user offline")
            callback(false)
        }
    }


    fun signOut(callback: ResultCallback) {
        if (isOnline(context)) {
            firebaseUser.signOut()
            callback(true)
        } else {
            logError("Fail in log out because user offline")
            callback(false)
        }
    }
}