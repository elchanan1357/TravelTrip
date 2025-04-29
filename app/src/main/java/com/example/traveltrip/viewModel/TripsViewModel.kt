package com.example.traveltrip.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveltrip.BuildConfig
import com.example.traveltrip.model.amadeusClasses.TripItem
import com.example.traveltrip.model.amadeusClasses.TripsAmadeus
import com.example.traveltrip.model.googleApi.GoogleApiClient
import com.example.traveltrip.model.googleApi.GoogleResponse
import com.example.traveltrip.model.googleApi.Place
import com.example.traveltrip.model.networking.ApiClient
import com.example.traveltrip.model.networking.TripService
import com.example.traveltrip.utils.log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TripsViewModel : ViewModel() {
    private val _travels = MutableLiveData<List<Place>>()
    val travels: LiveData<List<Place>>
        get() = _travels
    private val tripService: TripService = ApiClient.tripApiClient


    fun fetchTravels(latitude: Double, longitude: Double, radius: Int = 50000, limit: Int = 30) {
        GoogleApiClient.apiService.getParks(
            "$latitude,$longitude",
            radius,
            apiKey = BuildConfig.GOOGLE_API_KEY
        )
            .enqueue(object : Callback<GoogleResponse> {
                override fun onResponse(
                    call: Call<GoogleResponse>,
                    response: Response<GoogleResponse>
                ) {
                    log(response.toString())
                    if (response.isSuccessful) {
                        val results = response.body()?.results ?: emptyList()
                        _travels.value = _travels.value.orEmpty() + results

                    } else {
                        Log.e("API_ERROR", "Code: ${response.code()}")
                        _travels.value = emptyList()
                    }
                }

                override fun onFailure(call: Call<GoogleResponse>, t: Throwable) {
                    Log.e("API_FAILURE", "Error: ${t.message}")
                    _travels.value = emptyList()
                }
            })


//        tripService.getTrips(latitude, longitude, radius, limit)
//            .enqueue(object : Callback<TripsAmadeus> {
//                override fun onResponse(
//                    call: Call<TripsAmadeus>,
//                    response: Response<TripsAmadeus>
//                ) {
//                    if (response.isSuccessful) {
//                        val newList = response.body()?.data ?: emptyList()
//                        _travels.value = _travels.value.orEmpty() + newList
//
//                        log(_travels.value?.size.toString())
//                    } else {
//                        _travels.value = emptyList()
//                    }
//                }
//
//                override fun onFailure(call: Call<TripsAmadeus>, t: Throwable) {
//                    _travels.value = emptyList()
//                }
//            })
    }
}



