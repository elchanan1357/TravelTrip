package com.example.traveltrip.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.traveltrip.Utils.log
import com.example.traveltrip.Utils.logError
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
            try {
                val travels = AppLocalDB.DB.TravelDao().getTravels()
                log("Get all travels")
                Thread.sleep(4000)
                mainHandler.post { callback(travels) }
            } catch (err: Exception) {
                logError("Fail in get all travels")
                logError(err.toString())
            }
        }
    }

    fun getTravelByTitle(title: String, callback: TravelCallback) {
        executor.execute {
            try {
                val travel = AppLocalDB.DB.TravelDao().getTravelByTitle(title)
                log("Get travel by title")
                Thread.sleep(4000)
                mainHandler.post { callback(travel) }
            } catch (err: Exception) {
                logError("Fail in get travel by title")
                logError(err.toString())
            }
        }
    }

    fun addTravel(travel: Travel, callback: EmptyCallback) {
        executor.execute {
            try {
                AppLocalDB.DB.TravelDao().insertTravels(travel)
                log("Add travel")
                mainHandler.post { callback() }
            } catch (err: Exception) {
                logError("Fail in add travel")
                logError(err.toString())
            }

        }
    }

    fun deleteTravel(travel: Travel, callback: EmptyCallback) {
        executor.execute {
            try {
                AppLocalDB.DB.TravelDao().deleteTravel(travel)
                log("delete travel")
                mainHandler.post { callback() }
            } catch (err: Exception) {
                logError("Fail in delete travel")
                logError(err.toString())
            }
        }
    }
}