package com.example.traveltrip.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.traveltrip.base.MyApp
import com.example.traveltrip.model.dao.TripDao
import com.example.traveltrip.model.dao.UserDao
import com.example.traveltrip.model.entity.Trip
import com.example.traveltrip.model.entity.User

@Database(entities = [Trip::class, User::class], version = 3)
abstract class AppLocalDbRepository : RoomDatabase() {
    abstract fun TravelDao(): TripDao
    abstract fun UserDao(): UserDao
}

object AppLocalDB {
    val DB: AppLocalDbRepository by lazy {
        Room.databaseBuilder(
            context = MyApp.Globals.context
                ?: throw IllegalStateException("Application context not available"),
            klass = AppLocalDbRepository::class.java,
            name = "dbFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}