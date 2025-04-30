package com.example.traveltrip.model.remote.firebase

import com.example.traveltrip.utils.AuthCallback


object FirebaseAuth {
    private val auth = FirebaseProvider.getAuthInstance()


    fun registerUser(email: String, password: String, callback: AuthCallback) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnFailureListener() {
                callback(false, it.message)
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) callback(true, auth.currentUser?.uid)
                else callback(false, task.exception?.message)
            }
    }


    fun signIn(email: String, password: String, callback: AuthCallback) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) callback(true, null)
                else callback(false, task.exception?.message)
            }
    }


    fun sendEmailVerification(callback: AuthCallback) {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) callback(true, null)
                else callback(false, task.exception?.message)
            }
    }


    fun signOut() {
        auth.signOut()
    }

    fun deleteUser(callback: AuthCallback) {
        val user = auth.currentUser
        user?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)  // מחיקה הצליחה
                } else {
                    callback(false, task.exception?.message)  // מחיקה נכשלה
                }
            }
    }


    fun getCurrentUser() = auth.currentUser


    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }


}