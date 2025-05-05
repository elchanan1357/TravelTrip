package com.example.traveltrip.model.remote.firebase

import com.example.traveltrip.model.room.entity.User
import com.example.traveltrip.utils.*

class FirebaseModelUser {
    private val db = FirebaseProvider.getFirestoreInstance()
    private val userCollection = Constants.Collection.USERS


    fun getAllUsers(callback: UsersCallback) {
        db.collection(userCollection).get()
            .addOnSuccessListener { usersJSON ->
                log("Get all users from firestore")
                val users: MutableList<User> = mutableListOf()
                for (json in usersJSON)
                    users.add(User.fromJSON(json.data))

                callback(true, users)
            }
            .addOnFailureListener { e ->
                logError("Fail in get all users in firestore\n $e")
                callback(false, emptyList())
            }
    }


    fun addUser(user: User, callback: ResultCallback) {
        FirebaseAuth.registerUser(user.email, user.password) { success, uid ->
            if (success) {
                val userWithId = user.copy(uid = uid.toString())
                db.collection(userCollection).document(userWithId.email).set(userWithId.json)
                    .addOnSuccessListener {
                        log("Add user to firestore ${userWithId.email}")
                        callback(true)
                    }
                    .addOnFailureListener { e -> onFailRegister(e.message, callback) }
            } else onFailRegister(uid, callback)
        }
    }


    private fun onFailRegister(err: String?, callback: ResultCallback) {
        logError("Fail in register user\n $err")
        callback(false)
    }


    fun updateUser(user: User, callback: ResultCallback) {
        if (isCurrentUser(user.uid, FirebaseAuth.getCurrentUser()?.uid)) {
            db.collection(userCollection).document(user.email).update(user.updateJSON())
                .addOnSuccessListener {
                    log("Update user: ${user.email}")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    logError("Fail in update user with email: ${user.email} \n $e")
                    callback(false)
                }
        } else callback(false)
    }


    fun deleteUser(user: User, callback: ResultCallback) {
        if (isCurrentUser(user.uid, FirebaseAuth.getCurrentUser()?.uid)) {
            db.collection(userCollection).document(user.email).delete()
                .addOnSuccessListener {
                    log("Delete user: ${user.email}")
                    FirebaseAuth.deleteUser { success, err ->
                        if (success) {
                            FirebaseAuth.signOut()
                            callback(true)
                        } else {
                            logError("Fail in delete user from Auth\n $err")
                            callback(false)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    logError("Fail in delete user with email: ${user.email} \n $e")
                    callback(false)
                }
        } else callback(false)
    }


    private fun isCurrentUser(id1: String, id2: String?): Boolean {
        if (id1 == id2) return true

        logError("this is not current user")
        logError("$id1 != $id2")
        return false
    }


    fun signIn(email: String, password: String, callback: AuthCallback) {
        FirebaseAuth.signIn(email, password, callback)
    }



    fun signOut() {
        FirebaseAuth.signOut()
    }


    fun getUserByID(id: String, callback: UserCallback) {
        val currentUser = FirebaseAuth.getCurrentUser()
        if (isCurrentUser(id, currentUser?.uid)) {
            currentUser?.email?.let {
                db.collection(userCollection).document(it).get()
                    .addOnSuccessListener { document ->
                        val user: User? = document.data?.let { data -> User.fromJSON(data) }
                        callback(true, user)
                        log("get user: ${currentUser.email}")
                    }
                    .addOnFailureListener { e ->
                        logError("Fail in get user with email: ${currentUser.email} \n $e")
                        callback(false, null)
                    }
            }
        } else callback(false, null)
    }


}