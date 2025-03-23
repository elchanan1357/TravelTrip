package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.HomePageBinding

class HomePageFragment : Fragment() {
    private var binding: HomePageBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomePageBinding.inflate(inflater, container, false)

        binding?.homeBlogIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_posts) }
        binding?.homeDiscoverIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_discover) }
        binding?.homeTripsIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_trips) }

        return binding?.root
    }
}