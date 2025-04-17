package com.example.traveltrip.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.traveltrip.model.entity.Travel
import java.util.concurrent.Executors


class ModelTravel private constructor() {
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: ModelTravel = ModelTravel()
    }

    fun getAllTravels(callback: TravelsCallback) {
        executor.execute {
            val travels = AppLocalDB.DB.TravelDao().getTravels()
            Thread.sleep(4000)
            mainHandler.post { callback(travels) }
        }
    }

    fun getTravelByTitle(title: String, callback: TravelCallback) {
        executor.execute {
            val travel = AppLocalDB.DB.TravelDao().getTravelByTitle(title)
            Thread.sleep(4000)
            mainHandler.post { callback(travel) }
        }
    }

    fun addTravel(travel: Travel, callback: EmptyCallback) {
        executor.execute {
            AppLocalDB.DB.TravelDao().insertTravels(travel)
            mainHandler.post { callback() }
        }
    }

    fun deleteTravel(travel: Travel, callback: EmptyCallback) {
        executor.execute {
            AppLocalDB.DB.TravelDao().deleteTravel(travel)
            mainHandler.post { callback() }
        }
    }
}