package com.example.traveltrip.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import java.util.concurrent.Executors

typealias TravelsCallback = (List<Travel>) -> Unit
typealias EmptyCallback = () -> Unit

class Model private constructor() {
    val travels: List<Travel> = ArrayList()
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: Model = Model()
    }

//    init {
//        for (i in 0..20)
//            travels.add(
//                Travel(
//                    "$i",
//                    "the trip is $i",
//                    "/"
//                )
//            )
//    }

    fun getAllTravels(callback: TravelsCallback) {
        executor.execute {
            val travels = AppLocalDB.DB.TravelDao().getTravels()
            Thread.sleep(4000)
            mainHandler.post { callback(travels) }
        }
    }

    fun addTravel(travel: Travel, callback: EmptyCallback) {
        executor.execute {
            AppLocalDB.DB.TravelDao().insertTravels(travel)
            mainHandler.post { callback() }
        }
    }

}