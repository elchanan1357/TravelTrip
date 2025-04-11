package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.CategoriesBinding

class CategoriesFragment : Fragment() {
    private var binding: CategoriesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CategoriesBinding.inflate(inflater, container, false)
        binding?.categoriesKids?.setOnClickListener { onClick() }
        binding?.categoriesTrips?.setOnClickListener { onClick() }
        binding?.categoriesAmusementParks?.setOnClickListener { onClick() }
        binding?.categoriesMuseums?.setOnClickListener { onClick() }
        return binding?.root
    }

    private fun onClick() {
        findNavController().navigate(R.id.action_categories_trips)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}