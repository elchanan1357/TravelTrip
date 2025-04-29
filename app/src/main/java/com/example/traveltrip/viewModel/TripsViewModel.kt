package com.example.traveltrip.viewModel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveltrip.model.amadeusClasses.TripItem
import com.example.traveltrip.model.amadeusClasses.TripsAmadeus
import com.example.traveltrip.model.networking.ApiClient
import com.example.traveltrip.model.networking.TripService
import com.example.traveltrip.utils.log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TripsViewModel : ViewModel() {
    private val _travels = MutableLiveData<List<TripItem>>()
    val travels: LiveData<List<TripItem>>
        get() = _travels
    private val tripService: TripService = ApiClient.tripApiClient


    fun fetchTravels(latitude: Double, longitude: Double, radius: Int, limit: Int) {
        tripService.getTrips(latitude, longitude, radius, limit)
            .enqueue(object : Callback<TripsAmadeus> {
                override fun onResponse(
                    call: Call<TripsAmadeus>,
                    response: Response<TripsAmadeus>
                ) {
                    if (response.isSuccessful) {
                        val newList = response.body()?.data ?: emptyList()
                        _travels.value = _travels.value.orEmpty() + newList

                        log(_travels.value?.size.toString())
                    } else {
                        _travels.value = emptyList()
                    }
                }

                override fun onFailure(call: Call<TripsAmadeus>, t: Throwable) {
                    _travels.value = emptyList()
                }
            })
    }
}



