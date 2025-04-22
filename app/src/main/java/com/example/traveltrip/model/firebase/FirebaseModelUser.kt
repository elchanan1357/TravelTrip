package com.example.traveltrip.model.firebase


import com.example.traveltrip.model.entity.User
import com.example.traveltrip.utils.*
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase

class FirebaseModelUser {
    private val db = Firestore.getFirestoreInstance()
    private val userCollection = Constants.Collection.USERS

    //TODO on complete for all to stop progressbar
    fun getAllUsers(callback: UsersCallback) {
        db.collection(userCollection).get()
            .addOnSuccessListener { usersJSON ->
                log("Get all users")
                val users: MutableList<User> = mutableListOf()
                for (json in usersJSON)
                    users.add(User.fromJSON(json.data))

                callback(users)
            }
            .addOnFailureListener { e ->
                logError("Fail in get all users\n $e")
                callback(listOf())
            }
    }

    fun getUserByEmail(email: String, callback: UserCallback) {
        db.collection(userCollection).document(email).get()
            .addOnSuccessListener { user ->
                log("Find user $email")
                if (user.exists()) {
                    val u = User.fromJSON(user.data!!)
                    callback(u)
                } else
                    logError("Not find user $email")
            }
            .addOnFailureListener { e ->
                logError("Fail in get user by email: $email \n $e")
            }

    }

    fun addUser(user: User, callback: EmptyCallback) {
        db.collection(userCollection).document(user.email).set(user.json)
            .addOnSuccessListener {
                log("Add user: ${user.email}")
                callback()
            }
            .addOnFailureListener { e ->
                logError("Fail in add user with email: ${user.email} \n $e")
            }
    }

    fun updateUser(user: User, callback: EmptyCallback) {
        db.collection(userCollection).document(user.email).update(user.updateJSON())
            .addOnSuccessListener {
                log("Update user: ${user.email}")
                callback()
            }
            .addOnFailureListener { e ->
                logError("Fail in update user with email: ${user.email} \n $e")
            }
    }

    fun deleteUser(user: User, callback: EmptyCallback) {
        db.collection(userCollection).document(user.email).delete()
            .addOnSuccessListener {
                log("Delete user: ${user.email}")
                callback()
            }
            .addOnFailureListener { e ->
                logError("Fail in delete user with email: ${user.email} \n $e")
            }
    }

}