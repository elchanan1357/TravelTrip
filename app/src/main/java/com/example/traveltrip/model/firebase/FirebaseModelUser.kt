package com.example.traveltrip.model.firebase

import com.example.traveltrip.model.entity.User
import com.example.traveltrip.utils.*

class FirebaseModelUser {
    private val db = FirebaseProvider.getFirestoreInstance()
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
        //TODO VERIFY BY EMAIL
//        if (Auth.isLoggedIn() && Auth.getCurrentUser()?.email == email) {
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
//        }

    }

    fun addUser(user: User, callback: EmptyCallback) {
        Auth.registerUser(user.email, user.password) { success, uid ->
            if (success) {
                val userWithId = user.copy(uid = uid.toString())
                db.collection(userCollection).document(userWithId.email).set(userWithId.json)
                    .addOnSuccessListener {
                        log("Add user: ${userWithId.email}")
                        callback()
                    }
                    .addOnFailureListener { e ->
                        logError("Fail in add user with email: ${userWithId.email} \n $e")
                    }
            } else {
                uid?.let { logError(it) }
            }
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
                Auth.signOut()

                callback()
            }
            .addOnFailureListener { e ->
                logError("Fail in delete user with email: ${user.email} \n $e")
            }
    }

}