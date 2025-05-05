package com.example.traveltrip.model.room.models

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.traveltrip.model.room.dao.AppLocalDB
import com.example.traveltrip.utils.log
import com.example.traveltrip.utils.logError
import com.example.traveltrip.model.room.entity.Travel
import com.example.traveltrip.utils.EmptyCallback
import com.example.traveltrip.utils.TravelCallback
import com.example.traveltrip.utils.TravelsCallback
import java.util.concurrent.Executors


class RoomTravel private constructor() {
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val roomDB = AppLocalDB.DB.TravelDao()

    companion object {
        val instance: RoomTravel = RoomTravel()
    }

    fun getAllTravels(callback: TravelsCallback) {
        executor.execute {
            try {
                val travels = roomDB.getTravels()
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
                val travel = roomDB.getTravelByTitle(title)
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
                roomDB.insertTravels(travel)
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
                roomDB.deleteTravel(travel)
                log("delete travel")
                mainHandler.post { callback() }
            } catch (err: Exception) {
                logError("Fail in delete travel")
                logError(err.toString())
            }
        }
    }
}