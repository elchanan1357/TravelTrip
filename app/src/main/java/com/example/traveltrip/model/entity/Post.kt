package com.example.traveltrip.model.entity

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val email: String,
    val imgURL: String,
    val city: String,
    val state: String,
    val title: String,
    val text: String,
    val likes: Int = 0
) {

    companion object {
        private const val EMAIL_KEY = "email"
        private const val IMAGE_KEY = "imgURL"
        private const val CITY_KEY = "city"
        private const val STATE_KEY = "state"
        private const val TITLE_KEY = "title"
        private const val TEXT_KEY = "text"
        private const val LIKES_KEY = "likes"

        fun fromJSON(json: Map<String, Any>): Post {
            val email = json[EMAIL_KEY] as? String ?: ""
            val url = json[IMAGE_KEY] as? String ?: ""
            val city = json[CITY_KEY] as? String ?: ""
            val state = json[STATE_KEY] as? String ?: ""
            val title = json[TITLE_KEY] as? String ?: ""
            val text = json[TEXT_KEY] as? String ?: ""
            val likes = json[LIKES_KEY] as? Int ?: 0

            return Post(email, url, city, state, text, title, likes)
        }
    }


    fun updateJSON(): Map<String, Any> = hashMapOf(
        EMAIL_KEY to email,
        IMAGE_KEY to imgURL,
        CITY_KEY to city,
        STATE_KEY to state,
        TITLE_KEY to title,
        TEXT_KEY to text,
        LIKES_KEY to likes
    )


    val json: Map<String, Any>
        get() = hashMapOf(
            EMAIL_KEY to email,
            IMAGE_KEY to imgURL,
            CITY_KEY to city,
            STATE_KEY to state,
            TITLE_KEY to title,
            TEXT_KEY to text,
            LIKES_KEY to likes
        )

}
