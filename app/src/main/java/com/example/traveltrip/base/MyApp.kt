package com.example.traveltrip.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApp : Application() {

    @SuppressLint("StaticFieldLeak")
    object Globals {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        Globals.context = applicationContext
    }
}