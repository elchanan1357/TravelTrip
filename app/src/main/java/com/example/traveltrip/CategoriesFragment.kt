package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.traveltrip.databinding.CategoriesBinding
import com.example.traveltrip.utils.log

class CategoriesFragment : Fragment() {
    private var binding: CategoriesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CategoriesBinding.inflate(inflater, container, false)
        binding?.Kids?.setOnClickListener { onClick("Kids") }
        binding?.Trips?.setOnClickListener { onClick("Trips") }
        binding?.AmusementParks?.setOnClickListener { onClick("AmusementParks") }
        binding?.Museums?.setOnClickListener { onClick("Museums") }
        return binding?.root
    }

    private fun onClick() {
        findNavController().navigate(R.id.action_categories_trips)
    }

    private fun onClick(subCategory: String) {
        val mainCategories = arguments?.getString("mainCategory") ?: ""
        findNavController().navigate(
            CategoriesFragmentDirections.actionCategoriesTrips(mainCategories, subCategory)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}




