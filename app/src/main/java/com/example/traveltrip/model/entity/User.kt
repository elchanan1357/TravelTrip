package com.example.traveltrip.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    var name: String,
    var phone: String,
    @PrimaryKey var email: String,
    var password: String,
) {
    companion object {
        const val NAME_KEY = "name"
        const val PHONE_KEY = "phone"
        const val EMAIL_KEY = "email"
        const val PASSWORD_KEY = "password"

        fun fromJSON(json: Map<String, Any>): User {
            val name = json[NAME_KEY] as? String ?: ""
            val phone = json[PHONE_KEY] as? String ?: ""
            val email = json[EMAIL_KEY] as? String ?: ""
            val password = json[PASSWORD_KEY] as? String ?: ""

            return User(name, phone, email, password)
        }
    }

    val json: Map<String, Any>
        get() = hashMapOf(
            NAME_KEY to name,
            PHONE_KEY to phone,
            EMAIL_KEY to email,
            PASSWORD_KEY to password
        )

}
