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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DiscoverBinding.inflate(inflater, container, false)

        binding?.Hotels?.setOnClickListener { onClick("Hotels") }
        binding?.Trips?.setOnClickListener { onClick("Trips") }
        binding?.Flights?.setOnClickListener { onClick("Flights") }
        binding?.CarRental?.setOnClickListener { onClick("CarRental") }

        return binding?.root
    }

    private fun onClick() {
        findNavController().navigate(R.id.action_discover_categories)
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






