package com.example.traveltrip.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val imgURL: String,
    val location: String,
    val likes: Int = 0,
    val title: String,
    val text: String
) {

    companion object {
        private const val IMAGE_KEY = "imgURL"
        private const val LOCATION_KEY = "location"
        private const val LIKES_KEY = "likes"
        private const val TITLE_KEY = "title"
        private const val TEXT_KEY = "text"

        fun fromJSON(json: Map<String, Any>): Post {
            val url = json[IMAGE_KEY] as? String ?: ""
            val location = json[LOCATION_KEY] as? String ?: ""
            val likes = json[LIKES_KEY] as? Int ?: 0
            val title = json[TITLE_KEY] as? String ?: ""
            val text = json[TEXT_KEY] as? String ?: ""

            return Post(url, location, likes, title, text)
        }
    }


    fun updateJSON(): Map<String, Any> = hashMapOf(
        IMAGE_KEY to imgURL,
        LOCATION_KEY to location,
        LIKES_KEY to likes,
        TITLE_KEY to title,
        TEXT_KEY to text
    )


    val json: Map<String, Any>
        get() = hashMapOf(
            IMAGE_KEY to imgURL,
            LOCATION_KEY to location,
            LIKES_KEY to likes,
            TITLE_KEY to title,
            TEXT_KEY to text
        )

}
