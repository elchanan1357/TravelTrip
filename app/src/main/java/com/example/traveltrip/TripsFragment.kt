package com.example.traveltrip


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.adapter.GenericAdapter
import com.example.traveltrip.databinding.RowTripBinding
import com.example.traveltrip.databinding.TripsBinding
import com.example.traveltrip.model.googleApi.Place
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.log
import com.example.traveltrip.viewModel.TripsViewModel

class TripsFragment : Fragment() {
    private var viewModel: TripsViewModel? = null
    private var binding: TripsBinding? = null
    private var adapter: GenericAdapter<Place, RowTripBinding>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[TripsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TripsBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdapter()
        val recyclerView: RecyclerView? = binding?.RecyclerViewTrips
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        observeTravels()
    }


    override fun onResume() {
        super.onResume()
        when (arguments?.getString("mainCategory")) {
            "Trips" -> {
                fetchTravels()
            }

            "Hotels" -> {
                fetchHotels()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun createAdapter() {
        adapter = GenericAdapter(
            items = emptyList(),
            bindingInflater = RowTripBinding::inflate
        ) { itemBinding, item ->
            itemBinding.Title.text = item.name
            itemBinding.Information.text = item.formattedAddress

            val uri = item.photos?.get(0)?.photoReference?.let { getPhotoUrl(it) }
            log("the uri: ${uri.toString()}")
            getPicFromPicasso(itemBinding.imgBtn, uri)
        }
    }


    fun getPhotoUrl(photoReference: String): String {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=$photoReference&key=${BuildConfig.GOOGLE_API_KEY}"
    }


    private fun observeTravels() {
        viewModel?.travels?.observe(viewLifecycleOwner) { travel ->
            binding?.progressBar?.visibility = View.GONE
            adapter?.updateList(travel)
        }
    }


    private fun fetchHotels() {
        binding?.progressBar?.visibility = View.VISIBLE
        val israelLocations = listOf(
            Pair(32.0853, 34.7818),
//            Pair(31.7683, 35.2137),
//            Pair(32.7940, 34.9896),
//            Pair(29.5581, 34.9482),
//            Pair(32.7957, 35.5311),
//            Pair(33.0084, 35.1016),
//            Pair(40.7128, -74.0060),
//            Pair(34.0522, -118.2437)
        )


        israelLocations.forEach { (lat, lon) ->
            viewModel?.fetchHotels(lat, lon)
        }

    }


    private fun fetchTravels() {
        binding?.progressBar?.visibility = View.VISIBLE
        val israelLocations = listOf(
            Pair(32.0853, 34.7818),
            Pair(31.7683, 35.2137),
            Pair(32.7940, 34.9896),
            Pair(29.5581, 34.9482),
            Pair(32.7957, 35.5311),
            Pair(33.0084, 35.1016),
            Pair(40.7128, -74.0060),
            Pair(34.0522, -118.2437)
        )

        viewModel?.fetchTravels(32.0853, 34.7818)

//        israelLocations.forEach { (lat, lon) ->
//            viewModel?.searchAllPOIs(lat, lon, radius, limit)
//        }

    }
}