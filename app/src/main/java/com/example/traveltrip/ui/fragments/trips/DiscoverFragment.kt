package com.example.traveltrip.ui.fragments.trips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveltrip.databinding.DiscoverBinding
import androidx.navigation.fragment.findNavController


class DiscoverFragment : Fragment() {
    private var binding: DiscoverBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DiscoverBinding.inflate(inflater, container, false)

        binding?.Hotels?.setOnClickListener { onClick("Hotels") }
        binding?.trips?.setOnClickListener { onClick("Trips") }
        binding?.Flights?.setOnClickListener { onClick("Flights") }
        binding?.CarRental?.setOnClickListener { onClick("CarRental") }

        return binding?.root
    }

    private fun onClick(category: String) {
        findNavController().navigate(
            DiscoverFragmentDirections.actionDiscoverCategories(category)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}