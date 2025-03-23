package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.DiscoverBinding

class DiscoverFragment : Fragment() {
    private var binding: DiscoverBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DiscoverBinding.inflate(inflater, container, false)

        binding?.discoverHotels?.setOnClickListener { onClick() }
        binding?.discoverTrips?.setOnClickListener { onClick() }
        binding?.discoverFlights?.setOnClickListener { onClick() }
        binding?.discoverCarRental?.setOnClickListener { onClick() }

        return binding?.root
    }

    private fun onClick() {
        findNavController().navigate(R.id.action_discover_categories)
    }
}