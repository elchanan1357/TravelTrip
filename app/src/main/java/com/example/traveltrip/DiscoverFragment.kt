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

        binding?.Hotels?.setOnClickListener { onClick() }
        binding?.Trips?.setOnClickListener { onClick() }
        binding?.Flights?.setOnClickListener { onClick() }
        binding?.CarRental?.setOnClickListener { onClick() }

        return binding?.root
    }

    private fun onClick() {
        findNavController().navigate(R.id.action_discover_categories)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}