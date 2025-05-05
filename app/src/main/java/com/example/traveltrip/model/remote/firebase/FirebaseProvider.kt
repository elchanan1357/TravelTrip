package com.example.traveltrip.model.remote.firebase

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase

object FirebaseProvider {
    @SuppressLint("StaticFieldLeak")
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings { })
        }

        db.firestoreSettings = settings
    }

    fun getFirestoreInstance(): FirebaseFirestore {
        return db
    }

    fun getAuthInstance(): FirebaseAuth {
        return auth
    }
}

