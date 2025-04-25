package com.example.traveltrip.model.firebase


object Auth {
    private val auth = FirebaseProvider.getAuthInstance()


    fun registerUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) callback(true, null)
                else callback(false, task.exception?.message)
            }
    }


    fun signIn(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) callback(true, null)
                else callback(false, task.exception?.message)
            }

    }


    fun sendEmailVerification(callback: (Boolean, String?) -> Unit) {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) callback(true, null)
                else callback(false, task.exception?.message)
            }
    }


    fun signup() {
        auth.signOut()
    }


    fun getCurrentUser() = auth.currentUser


    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }


}