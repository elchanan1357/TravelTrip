package com.example.traveltrip.model.room.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.traveltrip.base.MyApp
import com.example.traveltrip.model.room.entity.Post
import com.example.traveltrip.model.room.entity.Travel
import com.example.traveltrip.model.room.entity.User

@Database(entities = [Travel::class, User::class, Post::class], version = 3)
abstract class AppLocalDbRepository : RoomDatabase() {
    abstract fun TravelDao(): TravelDao
    abstract fun UserDao(): UserDao
    abstract fun PostDao(): PostDao
}

object AppLocalDB {
    val DB: AppLocalDbRepository by lazy {
        Room.databaseBuilder(
            context = MyApp.Globals.context,
            klass = AppLocalDbRepository::class.java,
            name = "dbFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}