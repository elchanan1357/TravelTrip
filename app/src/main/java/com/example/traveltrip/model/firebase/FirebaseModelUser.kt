package com.example.traveltrip.model.firebase

import com.example.traveltrip.model.Constants
import com.example.traveltrip.model.EmptyCallback
import com.example.traveltrip.model.UserCallback
import com.example.traveltrip.model.UsersCallback
import com.example.traveltrip.model.entity.User
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase

class FirebaseModelUser {
    private val db = Firebase.firestore

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings { })
        }

        db.firestoreSettings = settings
//        db.collection("Users")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w("TAG", "Error adding document", e)
//            }
    }

    fun getAllUsers(callback: UsersCallback) {
        //1:48
    }

    fun getUserByEmail(email: String, callback: UserCallback) {

    }

    fun addUser(user: User, callback: EmptyCallback) {
        db.collection(Constants.Collection.USER).document().set(user.json)
            .addOnSuccessListener { callback() }
    }

    fun updateUser(user: User, callback: EmptyCallback) {

    }

    fun deleteUser(user: User, callback: EmptyCallback) {

    }

}