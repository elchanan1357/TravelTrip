package com.example.traveltrip.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.traveltrip.BuildConfig
import com.example.traveltrip.model.remote.googleApi.GoogleApiClient
import com.example.traveltrip.model.remote.googleApi.GoogleResponse
import com.example.traveltrip.model.remote.googleApi.Place
import com.example.traveltrip.model.remote.amadeus.networking.ApiClient
import com.example.traveltrip.model.remote.amadeus.networking.TripService
import com.example.traveltrip.utils.log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TripsViewModel : ViewModel() {
    private val _travels = MutableLiveData<List<Place>>()
    val travels: LiveData<List<Place>> = _travels

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val tripService: TripService = ApiClient.tripApiClient

    fun fetchHotels(
        latitude: Double,
        longitude: Double,
        type: String = "lodging",
        radius: Int = 50000,
    ) {
        GoogleApiClient.apiService.getHotels(
            "$latitude,$longitude",
            radius,
            type,
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
                        _error.value = "can't fetch data"
                    }
                }

                override fun onFailure(call: Call<GoogleResponse>, t: Throwable) {
                    Log.e("API_FAILURE", "Error: ${t.message}")
                    _travels.value = emptyList()
                    _error.value = "can't fetch data"
                }
            })
    }


    fun fetchMuseums(latitude: Double, longitude: Double, radius: Int = 50000) {
        GoogleApiClient.apiService.getMuseums(
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
    }


    fun fetchParks(latitude: Double, longitude: Double, radius: Int = 50000) {
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
    }
}



