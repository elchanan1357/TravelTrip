package com.example.traveltrip.model

import android.graphics.Bitmap
import com.example.traveltrip.model.entity.User
import com.example.traveltrip.model.firebase.FirebaseAuth
import com.example.traveltrip.model.firebase.FirebaseModelUser
import com.example.traveltrip.utils.AuthCallback
import com.example.traveltrip.utils.EmptyCallback
import com.example.traveltrip.utils.UserCallback
import com.example.traveltrip.utils.UsersCallback
import com.example.traveltrip.utils.log

class ModelUser private constructor() {
    //    private val executor = Executors.newSingleThreadExecutor()
//    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModelUser = FirebaseModelUser()
    private val cloudinaryModel: CloudinaryModel = CloudinaryModel.cloudinaryModel

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

    fun updateUser(
        user: User,
        bitmap: Bitmap?,
        callback: EmptyCallback
    ) {
        bitmap?.let {
            cloudinaryModel.uploadImg(
                bitmap,
                user.uid,
                onSuccess = { uri ->
                    if (!uri.isNullOrBlank())
                        firebaseModelUser.updateUser(user.copy(img = uri), callback)
                    else callback()
                },
                onError = { error ->
                    log(error.toString())
                    callback()
                }
            )
        } ?: firebaseModelUser.updateUser(user, callback)
    }

    fun deleteUser(user: User, callback: EmptyCallback) {
        firebaseModelUser.deleteUser(user, callback)
    }


    fun getEmail(): String? {
        if (FirebaseAuth.isLoggedIn())
            return FirebaseAuth.getCurrentUser()?.email

        return null
    }


    fun signIn(email: String, password: String, callback: AuthCallback) {
        firebaseModelUser.signIn(email, password, callback)
    }

    fun signOut(){
        //TODO:
    }
}