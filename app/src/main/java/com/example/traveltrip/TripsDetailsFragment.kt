package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveltrip.databinding.MyTripDetailseBinding


class TripsDetailsFragment : Fragment() {
    private var binding: MyTripDetailseBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyTripDetailseBinding.inflate(inflater, container, false)

        return binding?.root
    }

}