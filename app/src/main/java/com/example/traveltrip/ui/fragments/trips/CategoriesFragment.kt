package com.example.traveltrip.ui.fragments.trips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.CategoriesBinding

class CategoriesFragment : Fragment() {
    private var binding: CategoriesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CategoriesBinding.inflate(inflater, container, false)

        filter()
//        binding?.Kids?.setOnClickListener { onClick("Kids") }
//        binding?.AmusementParks?.setOnClickListener { onClick("AmusementParks") }
//        binding?.Museums?.setOnClickListener { onClick("Museums") }

        return binding?.root
    }


    private fun filter() {
        when (arguments?.getString("mainCategory")) {
            "Trips" -> {
                setTextOfBox("Parks", "Trips", "Museums", "kids")
                createOnClickBox("Parks", "Trips", "Museums", "kids")
            }

            "Hotels" -> {
                setTextOfBox("Hostels", "Guest Houses", "Apartments", "Hotels")
                createOnClickBox("Hostels", "Guest Houses", "Apartments", "Hotels")
            }
        }
    }


    private fun createOnClickBox(sub1: String, sub2: String, sub3: String, sub4: String) {
        binding?.oneBox?.setOnClickListener { onClick(sub1) }
        binding?.twoBox?.setOnClickListener { onClick(sub2) }
        binding?.threeBox?.setOnClickListener { onClick(sub3) }
        binding?.fourBox?.setOnClickListener { onClick(sub4) }
    }


    private fun onClick(sub: String) {
        val main: String = arguments?.getString("mainCategory") ?: ""
        findNavController().navigate(
            CategoriesFragmentDirections.actionCategoriesTrips(main, sub)
        )
    }


    private fun setImgDrawable(one: Int, two: Int, three: Int, four: Int) {
        binding?.oneBoxImg?.setImageResource(one)
        binding?.towBoxImg?.setImageResource(two)
        binding?.threeBoxImg?.setImageResource(three)
        binding?.fourBoxImg?.setImageResource(four)
    }


    private fun setTextOfBox(box1: String, box2: String, box3: String, box4: String) {
        binding?.oneBoxText?.text = box1
        binding?.towBoxText?.text = box2
        binding?.threeBoxText?.text = box3
        binding?.fourBoxText?.text = box4
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

