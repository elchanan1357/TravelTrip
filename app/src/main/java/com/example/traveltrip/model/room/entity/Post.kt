package com.example.traveltrip.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Posts")
data class Post(
    val imgURI: String,
    val city: String,
    val state: String,
    val title: String,
    val text: String,
    val userId: String,
    val id: String = "",
    val likes: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val autoId: Int = 1
) {

    companion object {
        private const val IMAGE_KEY = "imgURI"
        private const val CITY_KEY = "city"
        private const val STATE_KEY = "state"
        private const val TITLE_KEY = "title"
        private const val TEXT_KEY = "text"
        private const val USER_KEY = "userId"
        private const val ID_KEY = "id"
        private const val LIKES_KEY = "likes"

        fun fromJSON(json: Map<String, Any>): Post {
            val url = json[IMAGE_KEY] as? String ?: ""
            val city = json[CITY_KEY] as? String ?: ""
            val state = json[STATE_KEY] as? String ?: ""
            val title = json[TITLE_KEY] as? String ?: ""
            val text = json[TEXT_KEY] as? String ?: ""
            val userID = json[USER_KEY] as? String ?: ""
            val id = json[ID_KEY] as? String ?: ""
            val likes = json[LIKES_KEY] as? Int ?: 0

            return Post(url, city, state, title, text, userID, id, likes)
        }
    }


    fun updateJSON(): Map<String, Any> = hashMapOf(
        IMAGE_KEY to imgURI,
        CITY_KEY to city,
        STATE_KEY to state,
        TITLE_KEY to title,
        TEXT_KEY to text,
        ID_KEY to id,
        LIKES_KEY to likes
    )


    val json: Map<String, Any>
        get() = hashMapOf(
            IMAGE_KEY to imgURI,
            CITY_KEY to city,
            STATE_KEY to state,
            TITLE_KEY to title,
            TEXT_KEY to text,
            USER_KEY to userId,
            ID_KEY to id,
            LIKES_KEY to likes
        )
}
