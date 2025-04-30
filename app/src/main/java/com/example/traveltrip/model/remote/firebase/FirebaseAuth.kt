package com.example.traveltrip.model.remote.firebase

import com.example.traveltrip.utils.AuthCallback
import com.example.traveltrip.utils.log


object FirebaseAuth {
    private val auth = FirebaseProvider.getAuthInstance()


    fun registerUser(email: String, password: String, callback: AuthCallback) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnFailureListener { callback(false, it.message) }
            .addOnSuccessListener { callback(true, auth.currentUser?.uid) }
    }


    fun signIn(email: String, password: String, callback: AuthCallback) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) callback(true, null)
                else callback(false, task.exception?.message)
            }
    }


    fun sendEmailVerification(callback: AuthCallback) {
        //TODO
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) callback(true, null)
                else callback(false, task.exception?.message)
            }
    }


    fun signOut() {
        auth.signOut()
        log("sign out")
    }


    fun deleteUser(callback: AuthCallback) {
        val user = auth.currentUser
        user?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }


    fun getCurrentUser() = auth.currentUser


}