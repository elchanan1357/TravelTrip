package com.example.traveltrip

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.adapter.AllTripsAdapter
import com.example.traveltrip.databinding.TripsBinding
import com.example.traveltrip.model.Model
import com.example.traveltrip.model.Travel

class TripsFragment : Fragment() {
    private var travels: MutableList<Travel>? = null
    private var binding: TripsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TripsBinding.inflate(inflater, container, false)
        travels = Model.instance.travels
        val recyclerView: RecyclerView? = binding?.tripsRecyclerViewTrips

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = AllTripsAdapter(travels)

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}