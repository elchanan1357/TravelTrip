package com.example.traveltrip.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.traveltrip.model.amadeusClasses.POIItem
import com.example.traveltrip.model.amadeusClasses.POIResponse
import com.example.traveltrip.model.networking.ApiClient
import com.example.traveltrip.model.networking.POIService
import com.example.traveltrip.utils.log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class POIViewModel : ViewModel() {

    private val poiService: POIService = ApiClient.poiApiClient
    private val _poi = MutableLiveData<List<POIItem>>()
    val poi: LiveData<List<POIItem>>
        get() = _poi

    fun searchAllPOIs(
        latitude: Double,
        longitude: Double,
        radius: Int = 80000,
        limit: Int = 50
    ) {
        poiService.searchPOIs(latitude, longitude, radius, null, null, limit)
            .enqueue(object : Callback<POIResponse> {
                override fun onResponse(call: Call<POIResponse>, response: Response<POIResponse>) {
                  log(response.toString())
                    if (response.isSuccessful) {
                        _poi.value = response.body()?.data
                        log(_poi.value?.size.toString())
                    } else _poi.value = emptyList()
                }

                override fun onFailure(call: Call<POIResponse>, t: Throwable) {
                    log("enter out")
                    _poi.value = emptyList()
                }
            })
    }

    fun searchMuseums(
        latitude: Double,
        longitude: Double,
        radius: Int = 1000,
        limit: Int = 20
    ) {
        poiService.searchPOIs(latitude, longitude, radius, "SIGHTS-MUSEUMS", null, limit)
            .enqueue(object : Callback<POIResponse> {
                override fun onResponse(call: Call<POIResponse>, response: Response<POIResponse>) {
                    if (response.isSuccessful) _poi.value = response.body()?.data
                    else _poi.value = emptyList()
                }

                override fun onFailure(call: Call<POIResponse>, t: Throwable) {
                    _poi.value = emptyList()
                }
            })
    }

    fun searchParksForKids(radius: Int = 1000, limit: Int = 20) {
        val latitude = 32.0853
        val longitude = 34.7818

        poiService.searchPOIs(latitude, longitude, radius, "OUTDOORS", "children", limit)
            .enqueue(object : Callback<POIResponse> {
                override fun onResponse(call: Call<POIResponse>, response: Response<POIResponse>) {
                    if (response.isSuccessful) _poi.value = response.body()?.data
                    else _poi.value = emptyList()
                }

                override fun onFailure(call: Call<POIResponse>, t: Throwable) {
                    _poi.value = emptyList()
                }
            })
    }

    fun searchRestaurants(radius: Int = 1000, limit: Int = 20) {
        val latitude = 32.0853
        val longitude = 34.7818

        poiService.searchPOIs(latitude, longitude, radius, "RESTAURANT", null, limit)
            .enqueue(object : Callback<POIResponse> {
                override fun onResponse(call: Call<POIResponse>, response: Response<POIResponse>) {
                    if (response.isSuccessful) _poi.value = response.body()?.data
                    else _poi.value = emptyList()
                }

                override fun onFailure(call: Call<POIResponse>, t: Throwable) {
                    _poi.value = emptyList()
                }
            })
    }

}
