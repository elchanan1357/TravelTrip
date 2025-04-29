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
import com.example.traveltrip.model.amadeusClasses.POIItem
import com.example.traveltrip.model.amadeusClasses.TripItem
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.log
import com.example.traveltrip.viewModel.POIViewModel
import com.example.traveltrip.viewModel.TripsViewModel

class TripsFragment : Fragment() {
    private var viewModel: POIViewModel? = null
    private var binding: TripsBinding? = null
    private var adapter: GenericAdapter<POIItem, RowTripBinding>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[POIViewModel::class.java]
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
        fetchTravels()
    }


    override fun onResume() {
        super.onResume()
        fetchTravels()
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
            itemBinding.Information.text = item.category

//            val uri = item.pictures?.get(0)
//            log("the uri: ${uri.toString()}")
//            getPicFromPicasso(itemBinding.ImgBtn, uri)
        }
    }

    private fun observeTravels() {
        viewModel?.poi?.observe(viewLifecycleOwner) { poi ->
            binding?.progressBar?.visibility = View.GONE
            adapter?.updateList(poi)
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

        viewModel?.searchAllPOIs(32.0853, 34.7818)

//        israelLocations.forEach { (lat, lon) ->
//            viewModel?.searchAllPOIs(lat, lon, radius, limit)
//        }

    }
}